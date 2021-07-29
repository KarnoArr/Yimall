package com.zackyj.Mmall.service.Impl;

import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.service.ICartService;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.service.Impl ON 2021/7/29-周四.
 */
public class CartService implements ICartService {
    @Override
    public CommonResponse list(Integer id) {
        return null;
    }

    @Override
    public CommonResponse add(Integer id, Integer count, Integer productId) {
        return null;
    }

    @Override
    public CommonResponse update(Integer id, Integer productId, Integer count) {
        return null;
    }

    @Override
    public CommonResponse delete(Integer id, Integer productId) {
        return null;
    }

    @Override
    public CommonResponse getCount(Integer id) {
        return null;
    }

    @Override
    public CommonResponse changeCheckedStatus(Integer id, Integer productId, int checked) {
        return null;
    }
}
