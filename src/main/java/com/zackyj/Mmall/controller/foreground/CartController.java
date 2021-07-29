package com.zackyj.Mmall.controller.foreground;

import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.common.Constant;
import com.zackyj.Mmall.common.Exception.ExceptionEnum;
import com.zackyj.Mmall.model.pojo.User;
import com.zackyj.Mmall.service.ICartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.controller.foreground ON 2021/7/29-周四.
 */
@Api(tags = "购物车模块")
@RequestMapping("/cart")
@RestController
public class CartController {
    @Resource
    ICartService cartService;

    @ApiOperation(value = "获取当前用户购物车列表")
    @GetMapping("/list")
    public CommonResponse list(@ApiIgnore HttpSession session) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return CommonResponse.error(ExceptionEnum.NEED_LOGIN);
        }
        return cartService.list(user.getId());
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加购物车")
    public CommonResponse add(Integer count, Integer productId, @ApiIgnore HttpSession session) {
        //鉴权
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return CommonResponse.error(ExceptionEnum.NEED_LOGIN);
        }
        return cartService.add(user.getId(), count, productId);
    }

    @PostMapping("update")
    @ApiOperation(value = "修改购物车商品数量")
    public CommonResponse update(@ApiIgnore HttpSession session, Integer productId, Integer count) {
        //鉴权
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return CommonResponse.error(ExceptionEnum.NEED_LOGIN);
        }
        return cartService.update(user.getId(), productId, count);
    }

    @PostMapping("update")
    @ApiOperation(value = "删除购物车记录")
    public CommonResponse delete(@ApiIgnore HttpSession session, Integer productId) {
        //鉴权
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return CommonResponse.error(ExceptionEnum.NEED_LOGIN);
        }
        return cartService.delete(user.getId(), productId);
    }

    @GetMapping("getCount")
    @ApiOperation(value = "获取购物车产品总数")
    public CommonResponse getCount(@ApiIgnore HttpSession session) {
        //鉴权
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return CommonResponse.error(ExceptionEnum.NEED_LOGIN);
        }
        return cartService.getCount(user.getId());
    }

    //================勾选相关接口=====================
    @PostMapping("check")
    @ApiOperation(value = "选中指定商品")
    public CommonResponse checked(@ApiIgnore HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return CommonResponse.error(ExceptionEnum.NEED_LOGIN);
        }
        return cartService.changeCheckedStatus(user.getId(), productId, Constant.Cart.CHECKED);
    }

    @PostMapping("unCheck")
    @ApiOperation(value = "取消选中指定商品")
    public CommonResponse unCheck(@ApiIgnore HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return CommonResponse.error(ExceptionEnum.NEED_LOGIN);
        }
        return cartService.changeCheckedStatus(user.getId(), productId, Constant.Cart.UN_CHECKED);
    }

    @PostMapping("checkALL")
    @ApiOperation(value = "全选")
    public CommonResponse checkALL(@ApiIgnore HttpSession session) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return CommonResponse.error(ExceptionEnum.NEED_LOGIN);
        }
        return cartService.changeCheckedStatus(user.getId(), null, Constant.Cart.CHECKED);
    }

    @PostMapping("unCheckALL")
    @ApiOperation(value = "全不选")
    public CommonResponse unCheckALL(@ApiIgnore HttpSession session) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return CommonResponse.error(ExceptionEnum.NEED_LOGIN);
        }
        return cartService.changeCheckedStatus(user.getId(), null, Constant.Cart.UN_CHECKED);
    }
}
