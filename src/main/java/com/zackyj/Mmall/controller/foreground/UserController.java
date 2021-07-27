package com.zackyj.Mmall.controller.foreground;

import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.common.Constant;
import com.zackyj.Mmall.common.Exception.BusinessException;
import com.zackyj.Mmall.common.Exception.ExceptionEnum;
import com.zackyj.Mmall.model.pojo.User;
import com.zackyj.Mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;
    /**
     * 前台用户登录接口
     * @param username
     * @param password
     * @param session
     * @return
     */
    @PostMapping("/login")
    public CommonResponse<User> login(String username, String password, HttpSession session) {
        CommonResponse<User> response = userService.login(username, password);
        session.setAttribute(Constant.CURRENT_USER, response.getData());
        return response;
    }

    /**
     * 注销接口
     */
    @GetMapping("/logout")
    public CommonResponse logout(HttpSession session) {
        session.removeAttribute(Constant.CURRENT_USER);
        return CommonResponse.success();
    }

    @PostMapping("/register")
    public CommonResponse register(User user) {
        return userService.register(user);
    }

    @PostMapping("/getUser")
    public CommonResponse<User> getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            throw new BusinessException(ExceptionEnum.NEED_LOGIN);
        }
        return CommonResponse.success(user);
    }

    @PostMapping("/forgetQuestion")
    public CommonResponse<String> getForgetQuestion(String username) {
        return userService.getForgetQuestion(username);
    }

    @PostMapping("/checkAnswer")
    public CommonResponse<String> checkAnswer(String username, String question, String answer) {
        return userService.checkAnswer(username, question, answer);
    }

    @PostMapping("/forgetResetPwd")
    public CommonResponse<String> forgetRestPwd(String username, String newPwd, String forgetToken) {
        return userService.forgetResetPwd(username, newPwd, forgetToken);
    }

    @PostMapping("/resetPwd")
    public CommonResponse<String> resetPwd(String oldPwd, String newPwd, HttpSession session) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            throw new BusinessException(ExceptionEnum.NEED_LOGIN);
        }
        return userService.resetPassword(oldPwd, newPwd, user);
    }

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
