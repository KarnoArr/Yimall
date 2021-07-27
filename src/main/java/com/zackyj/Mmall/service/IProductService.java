package com.zackyj.Mmall.service;

import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.model.pojo.Product;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.service ON 2021/7/27-周二.
 */
public interface IProductService {
    CommonResponse addOrUpdateProduct(Product product);
}
