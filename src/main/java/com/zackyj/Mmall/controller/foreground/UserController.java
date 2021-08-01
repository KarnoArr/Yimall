package com.zackyj.Mmall.controller.foreground;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.common.Constant;
import com.zackyj.Mmall.common.Exception.BusinessException;
import com.zackyj.Mmall.common.Exception.ExceptionEnum;
import com.zackyj.Mmall.model.pojo.User;
import com.zackyj.Mmall.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
@Api(tags = "前台用户模块")
@ApiSort(101)
public class UserController {
    @Resource
    IUserService userService;
    @Resource
    RestTemplate restTemplate;

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public CommonResponse<User> login(String username, String password, @ApiIgnore HttpSession session) {
        CommonResponse<User> response = userService.login(username, password);
        session.setAttribute(Constant.CURRENT_USER, response.getData());
        restTemplate.getForObject("https://api.day.app/bnCK3nyeGN56tvGKJnEMYn/YIMALL/{1}", String.class, username + "登录了");
        return response;
    }

    @ApiOperation("注销")
    @GetMapping("/logout")
    public CommonResponse logout(@ApiIgnore HttpSession session) {
        session.removeAttribute(Constant.CURRENT_USER);
        return CommonResponse.success();
    }

    @ApiOperation("注册")
    @PostMapping("/register")
    public CommonResponse register(User user) {
        return userService.register(user);
    }

    @ApiOperation("获取当前登录用户信息")
    @PostMapping("/getUser")
    public CommonResponse<User> getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            throw new BusinessException(ExceptionEnum.NEED_LOGIN);
        }
        return CommonResponse.success(user);
    }

    @ApiOperation("获取密码提示问题")
    @PostMapping("/forgetQuestion")
    public CommonResponse<String> getForgetQuestion(String username) {
        return userService.getForgetQuestion(username);
    }

    @ApiOperation("验证问题答案")
    @PostMapping("/checkAnswer")
    public CommonResponse<String> checkAnswer(String username, String question, String answer) {
        return userService.checkAnswer(username, question, answer);
    }

    @ApiOperation("重置密码(忘记密码状态)")
    @PostMapping("/forgetResetPwd")
    public CommonResponse<String> forgetRestPwd(String username, String newPwd, String forgetToken) {
        return userService.forgetResetPwd(username, newPwd, forgetToken);
    }

    @ApiOperation("重置密码(登录状态)")
    @PostMapping("/resetPwd")
    public CommonResponse<String> resetPwd(String oldPwd, String newPwd, HttpSession session) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            throw new BusinessException(ExceptionEnum.NEED_LOGIN);
        }
        return userService.resetPassword(oldPwd, newPwd, user);
    }

    @ApiOperation("更新用户信息")
    @PostMapping("/updateInfo")
    public CommonResponse<User> updateInfo(HttpSession session, User user) {
        //检查是否登录
        User currentUser = (User) session.getAttribute(Constant.CURRENT_USER);
        if (currentUser == null) {
            throw new BusinessException(ExceptionEnum.NEED_LOGIN);
        }
        //给传入的user 参数设置当前登录的用户 id 和用户名
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());

        CommonResponse<User> response = userService.updateUserInfo(user);

        response.getData().setUsername(currentUser.getUsername());
        session.setAttribute(Constant.CURRENT_USER, response.getData());
        return response;
    }
}
