package com.zackyj.Mmall.controller.background;

import com.github.pagehelper.PageInfo;
import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.common.Constant;
import com.zackyj.Mmall.common.Exception.BusinessException;
import com.zackyj.Mmall.common.Exception.ExceptionEnum;
import com.zackyj.Mmall.model.pojo.User;
import com.zackyj.Mmall.service.IOrderService;
import com.zackyj.Mmall.service.IProductService;
import com.zackyj.Mmall.service.IUserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.function.ServerResponse;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.controller.background ON 2021/8/1-周日.
 */
@RestController
@RequestMapping("/admin/order")
public class AdminOrderController {
    @Resource
    IOrderService orderService;

    @GetMapping("/list")
    public CommonResponse<PageInfo> getOrderList(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            throw new BusinessException(ExceptionEnum.NEED_LOGIN);
        }
        return orderService.getOrderListForAdmin(pageNum, pageSize);
    }

    @GetMapping("/detail")
    public CommonResponse getDetail(HttpSession session, Long orderNo) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            throw new BusinessException(ExceptionEnum.NEED_LOGIN);
        }
        return orderService.getOrderDetailForAdmin(orderNo);
    }

    @GetMapping("search")
    @ResponseBody
    public CommonResponse<PageInfo> orderSearch(HttpSession session, Long orderNo, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            throw new BusinessException(ExceptionEnum.NEED_LOGIN);
        }
        return orderService.orderSearch(orderNo, pageNum, pageSize);
    }


    @PostMapping("deliver")
    public CommonResponse deliver(HttpSession session, Long orderNo) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            throw new BusinessException(ExceptionEnum.NEED_LOGIN);
        }
        return orderService.deliver(orderNo);
    }


}
