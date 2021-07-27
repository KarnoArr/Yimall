package com.zackyj.Mmall.service.Impl;

import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.common.Constant;
import com.zackyj.Mmall.common.Exception.ExceptionEnum;
import com.zackyj.Mmall.dao.UserMapper;
import com.zackyj.Mmall.model.pojo.User;
import com.zackyj.Mmall.service.IUserService;
import com.zackyj.Mmall.utils.BaseUtil;
import com.zackyj.Mmall.utils.MD5Util;
import com.zackyj.Mmall.utils.TokenCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.function.ServerResponse;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    UserMapper userMapper;

    /**
     * 登录业务
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public CommonResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if (resultCount == 0) {
            return CommonResponse.error(ExceptionEnum.NO_SUCH_USER);
        }
        String md5Passwd = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.checkLogin(username, md5Passwd);
        if (user == null) {
            return CommonResponse.error(ExceptionEnum.LOGIN_FAILED);
        }
        user.setPassword(StringUtils.EMPTY);
        return CommonResponse.success(user);
    }

    /**
     * 注册业务
     *
     * @param user
     * @return
     */
    @Override
    public CommonResponse<User> register(User user) {
        //检查用户名是否可用
        CommonResponse response = this.checkValid(user.getUsername(), Constant.USERNAME);
        if (!BaseUtil.operateValid(response)) {
            return response;
        }
        //检查邮箱是否可用
        response = this.checkValid(user.getEmail(), Constant.EMAIL);
        if (!BaseUtil.operateValid(response)) {
            return response;
        }
        //设置普通用户权限标识
        user.setRole(Constant.Role.ROLE_CUSTOMER);
        //MD5 加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userMapper.insert(user);
        if (resultCount == 0) {
            return CommonResponse.error(ExceptionEnum.REGISTER_FAILED);
        }
        return CommonResponse.success("注册成功");
    }

    /**
     * 注册信息校验业务
     *
     * @param str
     * @param type
     * @return
     */
    @Override
    public CommonResponse<String> checkValid(String str, String type) {
        if (StringUtils.isNotBlank(type)) {
            //开始校验
            if (Constant.USERNAME.equals(type)) {
                int resultCount = userMapper.checkUsername(str);
                if (resultCount > 0) {
                    return CommonResponse.error(ExceptionEnum.NAME_EXIST);
                }
            }
            if (Constant.EMAIL.equals(type)) {
                int resultCount = userMapper.checkEmail(str);
                if (resultCount > 0) {
                    return CommonResponse.error(ExceptionEnum.EMAIL_EXIST);
                }
            }
        } else {
            return CommonResponse.error(ExceptionEnum.WRONG_ARG);
        }
        return CommonResponse.success("校验成功");
    }

    @Override
    public CommonResponse<String> getForgetQuestion(String username) {
        CommonResponse<String> response = this.checkValid(username, Constant.USERNAME);
        if (BaseUtil.operateValid(response)) {
            //用户不存在
            return CommonResponse.error(ExceptionEnum.NO_SUCH_USER);
        }
        String question = userMapper.selectQuestionByUsername(username);
        if (StringUtils.isNoneBlank(question)) {
            return CommonResponse.success(question, 1);
        }
        return CommonResponse.error(ExceptionEnum.NO_FORGET_Q);
    }

    @Override
    public CommonResponse<String> checkAnswer(String username, String question, String answer) {
        int resultCount = userMapper.checkAnswer(username, question, answer);
        if (resultCount > 0) {
            //有效,生成 forgetToken
            String forgetToken = UUID.randomUUID().toString();
            //存入本地 Cache 中，设置有效期
            TokenCache.setKey(TokenCache.TOKEN_PREFIX + username, forgetToken);
            return CommonResponse.success(forgetToken, 1);
        }
        return CommonResponse.error(ExceptionEnum.WRONG_ANSWER);
    }

    @Override
    public CommonResponse<String> forgetResetPwd(String username, String newPwd, String forgetToken) {
        //参数校验：token 是否存在
        if (StringUtils.isBlank(forgetToken)) {
            return CommonResponse.error(ExceptionEnum.NO_TOKEN);
        }
        //参数校验：username 是否存在
        CommonResponse<String> response = this.checkValid(username, Constant.USERNAME);
        if (BaseUtil.operateValid(response)) {
            //用户不存在
            return CommonResponse.error(ExceptionEnum.NO_SUCH_USER);
        }
        //校验本地 cache 中是否为空
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
        if (StringUtils.isBlank(token)) {
            return CommonResponse.error(ExceptionEnum.NO_TOKEN);
        }
        //检查本地缓存 token 和传入的 token 是否一致
        if (StringUtils.equals(forgetToken, token)) {
            String md5Password = MD5Util.MD5EncodeUtf8(newPwd);
            int rowCount = userMapper.updatePasswordByUsername(username, md5Password);
            if (rowCount > 0) {
                return CommonResponse.success("重置密码成功");
            }
        } else {
            return CommonResponse.error(ExceptionEnum.NO_TOKEN);
        }
        return CommonResponse.error(ExceptionEnum.RESET_PWD_ERROR);
    }

    @Override
    public CommonResponse<String> resetPassword(String oldPwd, String newPwd, User user) {
        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(oldPwd), user.getId());
        if (resultCount == 0) {
            return CommonResponse.error("旧密码错误");
        }

        user.setPassword(MD5Util.MD5EncodeUtf8(newPwd));
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount > 0) {
            return CommonResponse.success("密码更新成功");
        }
        return CommonResponse.error(ExceptionEnum.UPDATE_PWD_ERROR);
    }

    @Override
    public CommonResponse<User> updateUserInfo(User user) {
        //禁止 username 的更新
        int resultCount = userMapper.checkEmailByUserId(user.getEmail(), user.getId());
        if (resultCount > 0) {
            return CommonResponse.error(ExceptionEnum.UPDATE_EMAIL_EXIST);
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());

        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if (updateCount > 0) {
            return CommonResponse.success("更新个人信息成功", updateUser);
        }
        return CommonResponse.error(ExceptionEnum.UPDATE_FAILED);
    }
}
