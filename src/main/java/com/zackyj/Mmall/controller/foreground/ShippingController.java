package com.zackyj.Mmall.controller.foreground;

import com.github.pagehelper.PageInfo;
import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.common.Constant;
import com.zackyj.Mmall.common.Exception.ExceptionEnum;
import com.zackyj.Mmall.model.pojo.Shipping;
import com.zackyj.Mmall.model.pojo.User;
import com.zackyj.Mmall.service.IShippingService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.controller.foreground ON 2021/7/29-周四.
 */

@Api(tags = "收货地址模块")
@RequestMapping("/ship")
@RestController
public class ShippingController {
    @Resource
    IShippingService shippingService;

    @GetMapping("/list")
    public CommonResponse<PageInfo> list(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                                         @ApiIgnore HttpSession session) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return CommonResponse.error(ExceptionEnum.NEED_LOGIN);
        }
        return shippingService.list(user.getId(), pageNum, pageSize);
    }

    @GetMapping("/detail")
    public CommonResponse detail(Integer shippingId, @ApiIgnore HttpSession session) {
        //权限判断
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return CommonResponse.error(ExceptionEnum.NEED_LOGIN);
        }
        return shippingService.getDetail(user.getId(), shippingId);
    }

    @PostMapping("/add")
    public CommonResponse add(Shipping shipping, @ApiIgnore HttpSession session) {
        //权限判断
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return CommonResponse.error(ExceptionEnum.NEED_LOGIN);
        }
        return shippingService.add(user.getId(), shipping);
    }

    @PostMapping("/del")
    public CommonResponse delete(Integer shippingId, @ApiIgnore HttpSession session) {
        //权限判断
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return CommonResponse.error(ExceptionEnum.NEED_LOGIN);
        }
        return shippingService.delete(user.getId(), shippingId);
    }

    @PostMapping("/update")
    public CommonResponse update(Shipping shipping, @ApiIgnore HttpSession session) {
        //权限判断
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return CommonResponse.error(ExceptionEnum.NEED_LOGIN);
        }
        return shippingService.update(user.getId(), shipping);
    }
}
