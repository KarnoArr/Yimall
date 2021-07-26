package com.zackyj.Mmall.service.Impl;

import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.common.Exception.ExceptionEnum;
import com.zackyj.Mmall.dao.UserMapper;
import com.zackyj.Mmall.model.pojo.User;
import com.zackyj.Mmall.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public CommonResponse<User> login(String username, String password){
        int resultCount = userMapper.checkUsername(username);
        if(resultCount == 0){
            return CommonResponse.error(ExceptionEnum.NO_SUCH_USER);
        }
        User user = userMapper.checkLogin(username, password);
        if(user == null){
            return CommonResponse.error(ExceptionEnum.LOGIN_FAILED);
        }
        user.setPassword(StringUtils.EMPTY);
        return CommonResponse.success(user);
    }
}
