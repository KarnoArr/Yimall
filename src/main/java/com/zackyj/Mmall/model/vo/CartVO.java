package com.zackyj.Mmall.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.model.vo ON 2021/7/29-周四.
 */
@Data
public class CartVO {
    private List<CartProductVO> cartProductList;
    private BigDecimal cartTotalPrice;
    private Boolean isAllChecked;
    private String imageHost;

    public List<CartProductVO> getCartProductList() {
        return cartProductList;
    }

    public void setCartProductList(List<CartProductVO> cartProductList) {
        this.cartProductList = cartProductList;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }
}
