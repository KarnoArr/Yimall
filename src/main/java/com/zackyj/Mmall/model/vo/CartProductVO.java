package com.zackyj.Mmall.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.model.vo ON 2021/7/29-周四.
 */
@Data
public class CartProductVO {
    private Integer id;
    private Integer userId;
    private Integer productId;
    private Integer quantity;
    private String productName;
    private String productSubtitle;
    private String productMainImage;
    private BigDecimal productPrice;
    private Integer productStatus;
    private BigDecimal productTotalPrice;
    private Integer productStock;
    private Integer productChecked;//此商品是否勾选

    private String limitQuantity;//是否被库存限制了数量：限制数量的一个返回结果
}
