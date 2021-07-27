package com.zackyj.Mmall.service;

import com.zackyj.Mmall.common.CommonResponse;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.service ON 2021/7/27-周二.
 */
public interface ICategoryService {
    CommonResponse addCategory(String categoryName, Integer parentId);

    CommonResponse updateName(Integer categoryId, String categoryName);

    CommonResponse updateParent(Integer categoryId, Integer parentId);
}
