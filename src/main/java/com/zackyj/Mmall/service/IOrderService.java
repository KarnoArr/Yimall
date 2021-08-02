package com.zackyj.Mmall.service;

import com.github.pagehelper.PageInfo;
import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.model.vo.OrderVO;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.function.ServerResponse;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.service ON 2021/7/31-周六.
 */
public interface IOrderService {
    CommonResponse createOrder(Integer Id, Integer shippingId);

    CommonResponse cancelOrder(Integer userId, Long orderNo);

    CommonResponse getOrderCartProduct(Integer userId);

    CommonResponse<OrderVO> getOrderDetail(Integer userId, Long orderNo);

    CommonResponse<PageInfo> getOrderList(Integer userId, int pageNum, int pageSize);

    CommonResponse<PageInfo> getOrderListForAdmin(int pageNum, int pageSize);

    CommonResponse getOrderDetailForAdmin(Long orderNo);

    CommonResponse orderSearch(Long orderNo, int pageNum, int pageSize);

    CommonResponse deliver(Long orderNo);
}
