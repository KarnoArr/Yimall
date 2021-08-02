package com.zackyj.Mmall.dao;

import com.zackyj.Mmall.model.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    Cart selectByUserAndPdtId(@Param("userId") Integer userId, @Param("productId") Integer productId);

    List<Cart> selectListByUserId(Integer userId);

    int selectAllChecked(Integer userId);

    int deleteByUserAndPdtId(@Param("userId") Integer id, @Param("productIdList") List<String> productIdList);

    int getAllCount(Integer id);

    int changeCheckedStatus(@Param("userId") Integer id, @Param("productId") Integer productId, @Param("checked") int checked);

    List<Cart> getSelectedByUserId(Integer userId);
}