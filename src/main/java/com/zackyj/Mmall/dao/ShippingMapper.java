package com.zackyj.Mmall.dao;

import com.zackyj.Mmall.model.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    int deleteByUserAndShippingID(@Param("userId") Integer id, @Param("ShippingId") Integer shippingId);

    int updateByUserAndShippingID(Shipping shipping);

    Shipping getDetailByUserAndShippingId(@Param("userId") Integer userId, @Param("ShippingId") Integer shippingId);

    List<Shipping> selectByUserId(Integer userId);
}