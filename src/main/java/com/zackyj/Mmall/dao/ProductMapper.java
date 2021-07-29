package com.zackyj.Mmall.dao;

import com.zackyj.Mmall.model.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectList(String name);

    List<Product> getListForUser(@Param("categoryId") Integer categoryId, @Param("keyword") String keyword,
                                 @Param("sortBy") String sortBy, @Param("sortOrder") String sortOrder);
}