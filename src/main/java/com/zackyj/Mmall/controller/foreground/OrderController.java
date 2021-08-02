package com.zackyj.Mmall.controller.foreground;

import com.github.pagehelper.PageInfo;
import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.common.Constant;
import com.zackyj.Mmall.common.Exception.BusinessException;
import com.zackyj.Mmall.common.Exception.ExceptionEnum;
import com.zackyj.Mmall.model.pojo.User;
import com.zackyj.Mmall.model.vo.OrderVO;
import com.zackyj.Mmall.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.controller.foreground ON 2021/7/31-周六.
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Resource
    IOrderService orderService;

    @PostMapping("/create")
    public CommonResponse createOrder(HttpSession session, Integer shippingId) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            throw new BusinessException(ExceptionEnum.NEED_LOGIN);
        }
        return orderService.createOrder(user.getId(), shippingId);
    }

    @PostMapping("/cancel")
    public CommonResponse cancelOrder(HttpSession session, Long orderId) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            throw new BusinessException(ExceptionEnum.NEED_LOGIN);
        }
        return orderService.cancelOrder(user.getId(), orderId);
    }

    @GetMapping("/getCartSelectPdt")
    public CommonResponse getSelectedPdt(HttpSession session) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            throw new BusinessException(ExceptionEnum.NEED_LOGIN);
        }
        return orderService.getOrderCartProduct(user.getId());
    }

    @GetMapping("/detail")
    public CommonResponse<OrderVO> getOrderDetail(HttpSession session, Long orderNo) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            throw new BusinessException(ExceptionEnum.NEED_LOGIN);
        }
        return orderService.getOrderDetail(user.getId(), orderNo);
    }

    @GetMapping("/list")
    public CommonResponse<PageInfo> getOrderList(HttpSession session,
                                                 @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            throw new BusinessException(ExceptionEnum.NEED_LOGIN);
        }
        return orderService.getOrderList(user.getId(), pageNum, pageSize);
    }
}
