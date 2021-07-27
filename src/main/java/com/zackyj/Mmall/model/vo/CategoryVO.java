package com.zackyj.Mmall.model.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.model.vo ON 2021/7/27-周二.
 */
@Data
public class CategoryVO {
    private Integer id;

    private Integer parentId;

    private String name;

    private Boolean status;

    private Integer sortOrder;

    private List<CategoryVO> childCategory = new ArrayList<>();
}
