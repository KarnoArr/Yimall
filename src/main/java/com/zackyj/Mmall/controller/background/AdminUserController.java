package com.zackyj.Mmall.controller.background;

import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.common.Constant;
import com.zackyj.Mmall.common.Exception.ExceptionEnum;
import com.zackyj.Mmall.model.pojo.User;
import com.zackyj.Mmall.service.IUserService;
import com.zackyj.Mmall.utils.BaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.controller.background ON 2021/7/27-周二.
 */
@RestController
@RequestMapping("/admin/user")
public class AdminUserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public CommonResponse<User> login(String username, String password, HttpSession session) {
        CommonResponse<User> response = userService.login(username, password);
        if (BaseUtil.operateValid(response)) {
            User user = response.getData();
            if (user.getRole() == Constant.Role.ROLE_ADMIN) {
                session.setAttribute(Constant.CURRENT_USER, user);
                return response;
            } else {
                return CommonResponse.error(ExceptionEnum.NO_ACCESS);
            }
        }
        return response;
    }
}
