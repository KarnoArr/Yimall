package com.zackyj.Mmall.service;

import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.model.pojo.Product;
import com.zackyj.Mmall.model.vo.ProductDetailVO;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.service ON 2021/7/27-周二.
 */
public interface IProductService {
    CommonResponse addOrUpdateProduct(Product product);

    CommonResponse setSaleStatus(Integer productId, Integer status);

    CommonResponse<ProductDetailVO> getProductDetailForAdmin(Integer productId);

    CommonResponse getProductDetail(Integer productId);

    CommonResponse getProductListForAdmin(Integer pageNum, Integer pageSize, String keyword);

    CommonResponse getListForUser(Integer categoryId, String keyword, String sortBy, String sortOrder, Integer pageNumber, Integer pageSize);
}
