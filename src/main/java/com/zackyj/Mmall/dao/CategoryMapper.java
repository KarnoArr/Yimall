package com.zackyj.Mmall.dao;

import com.zackyj.Mmall.model.pojo.Category;

import java.util.List;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    int checkCategoryExist(String categoryName);

    List<Category> selectCategoryChildrenByParentId(Integer parentId);

    int discard(Integer categoryId, Integer status);
}