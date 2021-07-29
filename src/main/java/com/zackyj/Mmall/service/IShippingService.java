package com.zackyj.Mmall.service;

import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.model.pojo.Shipping;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.service ON 2021/7/29-周四.
 */
public interface IShippingService {
    CommonResponse list(Integer userId, int pageNum, int pageSize);

    CommonResponse add(Integer userId, Shipping shipping);

    CommonResponse delete(Integer id, Integer shippingId);

    CommonResponse update(Integer userId, Shipping shipping);

    CommonResponse getDetail(Integer userId, Integer shippingId);
}
