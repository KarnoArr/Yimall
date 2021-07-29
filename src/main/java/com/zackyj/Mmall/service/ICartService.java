package com.zackyj.Mmall.service;

import com.zackyj.Mmall.common.CommonResponse;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.service ON 2021/7/29-周四.
 */
public interface ICartService {
    CommonResponse list(Integer id);

    CommonResponse add(Integer id, Integer count, Integer productId);

    CommonResponse update(Integer id, Integer productId, Integer count);

    CommonResponse delete(Integer id, Integer productId);

    CommonResponse getCount(Integer id);

    CommonResponse changeCheckedStatus(Integer id, Integer productId, int checked);
}
