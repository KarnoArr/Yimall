package com.zackyj.Mmall.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.model.vo ON 2021/7/27-周二.
 */
@Data
public class ProductDetailVO {
    private Integer id;
    private Integer categoryId;
    private String name;
    private String subtitle;
    private String mainImage;
    private String detail;
    private BigDecimal price;
    private Integer status;
    private Integer stock;
    private String createTime;
    private String updateTime;

    private String imageHostPrefix;
    private Integer parentCategoryId;
}
