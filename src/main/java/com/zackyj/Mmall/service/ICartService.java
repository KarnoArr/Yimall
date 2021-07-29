package com.zackyj.Mmall.service;

import com.zackyj.Mmall.common.CommonResponse;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.service ON 2021/7/29-周四.
 */
public interface ICartService {
    CommonResponse list(Integer userId);

    CommonResponse add(Integer userId, Integer count, Integer productId);

    CommonResponse update(Integer userId, Integer productId, Integer count);

    CommonResponse delete(Integer userId, String productId);

    CommonResponse getCount(Integer userId);

    CommonResponse changeCheckedStatus(Integer userId, Integer productId, int checked);
}
