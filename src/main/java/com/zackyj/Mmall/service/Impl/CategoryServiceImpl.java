package com.zackyj.Mmall.service.Impl;

import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.common.Exception.BusinessException;
import com.zackyj.Mmall.common.Exception.ExceptionEnum;
import com.zackyj.Mmall.dao.CategoryMapper;
import com.zackyj.Mmall.model.pojo.Category;
import com.zackyj.Mmall.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.service.Impl ON 2021/7/27-周二.
 */
@Slf4j
@Service
public class CategoryServiceImpl implements ICategoryService {
    @Resource
    CategoryMapper categoryMapper;

    @Override
    public CommonResponse addCategory(String categoryName, Integer parentId) {
        //参数校验
        if (parentId == null || StringUtils.isBlank(categoryName)) {
            throw new BusinessException(ExceptionEnum.WRONG_CATEGORY_ARGS);
        }
        //检查分类名是否重名
        int count = categoryMapper.checkCategoryExist(categoryName);
        if (count > 0) {
            throw new BusinessException(ExceptionEnum.CATEGORY_NAME_EXIST);
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);

        int rowCount = categoryMapper.insert(category);
        if (rowCount == 0) {
            throw new BusinessException(ExceptionEnum.ADD_CATEGORY_FAILED);
        }
        return CommonResponse.success("添加品类成功");
    }

    @Override
    public CommonResponse updateName(Integer categoryId, String categoryName) {
        return null;
    }

    @Override
    public CommonResponse updateParent(Integer categoryId, Integer parentId) {
        return null;
    }
}
