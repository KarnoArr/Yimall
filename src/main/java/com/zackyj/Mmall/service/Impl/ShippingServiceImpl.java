package com.zackyj.Mmall.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.common.Exception.ExceptionEnum;
import com.zackyj.Mmall.dao.ShippingMapper;
import com.zackyj.Mmall.model.pojo.Shipping;
import com.zackyj.Mmall.service.IShippingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.service.Impl ON 2021/7/29-周四.
 * 需要判断横向越权
 */
@Service
public class ShippingServiceImpl implements IShippingService {
    @Resource
    ShippingMapper shippingMapper;

    @Override
    public CommonResponse list(Integer userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        PageInfo pageinfo = new PageInfo(shippingList);
        return CommonResponse.success(pageinfo);
    }

    @Override
    public CommonResponse add(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        //自动生成主键
        int rowCount = shippingMapper.insert(shipping);
        if (rowCount == 0) {
            return CommonResponse.error(ExceptionEnum.ADD_SHIPPING_ADDR_FAILED);
        }
        Map result = Maps.newHashMap();
        result.put("shippingID", shipping.getId());
        return CommonResponse.success("新建地址成功", result);
    }

    @Override
    public CommonResponse delete(Integer id, Integer shippingId) {
        //可能出现横向越权
        int rowCount = shippingMapper.deleteByUserAndShippingID(id, shippingId);
        if (rowCount == 0) {
            return CommonResponse.error(ExceptionEnum.DELETE_SHIPPING_ADDR_FAILED);
        }
        return CommonResponse.success("删除地址成功");
    }

    @Override
    public CommonResponse update(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        int rowCount = shippingMapper.updateByUserAndShippingID(shipping);
        if (rowCount == 0) {
            return CommonResponse.error(ExceptionEnum.UPDATE_SHIPPING_ADDR_FAILED);
        }
        return CommonResponse.success("更新地址成功");
    }

    @Override
    public CommonResponse getDetail(Integer userId, Integer shippingId) {
        Shipping shipping = shippingMapper.getDetailByUserAndShippingId(userId, shippingId);
        if (shipping == null) {
            return CommonResponse.error(ExceptionEnum.GET_SHIPPING_ADDR_DETAILE_FAILED);
        }
        return CommonResponse.success(shipping);
    }
}
