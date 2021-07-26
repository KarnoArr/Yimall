package com.zackyj.Mmall.controller.foreground;

import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.common.Constant;
import com.zackyj.Mmall.common.Exception.ExceptionEnum;
import com.zackyj.Mmall.model.pojo.User;
import com.zackyj.Mmall.service.IUserService;
import com.zackyj.Mmall.service.Impl.UserServiceImpl;
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
    public CommonResponse<User> login(String username, String password,HttpSession session){
        CommonResponse<User> response = userService.login(username, password);
        if(response.getStatus()< ExceptionEnum.BASE_ERROR_CODE.getCode()){
              session.setAttribute(Constant.CURRENT_USER,response.getData());
        }
        return response;
    }

    @GetMapping("/logout")
    public CommonResponse logout(HttpSession session){
        session.removeAttribute(Constant.CURRENT_USER);
        return CommonResponse.success();
    }

}
