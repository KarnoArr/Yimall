package com.zackyj.Mmall.model.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description 购物车中选中的商品详情
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.model.vo ON 2021/8/1-周日.
 */
public class OrderProductVO {
    private List<OrderItemVO> orderItemVoList;
    private BigDecimal productTotalPrice;

    public List<OrderItemVO> getOrderItemVoList() {
        return orderItemVoList;
    }

    public void setOrderItemVoList(List<OrderItemVO> orderItemVoList) {
        this.orderItemVoList = orderItemVoList;
    }

    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }
}
