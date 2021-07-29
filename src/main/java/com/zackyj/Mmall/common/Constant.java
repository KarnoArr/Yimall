package com.zackyj.Mmall.common;

import org.springframework.stereotype.Component;

/**
 * @Description 全局常量类
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.common ON 2021/7/26-周一.
 */
@Component
public class Constant {
    public static final String CURRENT_USER = "CurrentUser";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String NO_USERNAME = "noUsername";


    /**
     * 权限标识
     * [内部接口进行常量分组，比枚举更加轻量]
     */
    public interface Role {
        int ROLE_CUSTOMER = 0; //普通用户
        int ROLE_ADMIN = 1;//管理员
    }

    /**
     * 购物车标识
     */
    public interface Cart {

        /**
         * 购物车记录选中状态
         */
        int CHECKED = 1;//即购物车选中状态
        int UN_CHECKED = 0;//购物车中未选中状态


        /**
         * 购物车记录商品数量是否被库存限制
         */
        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }

    /**
     * 商品状态标识
     */
    public enum ProductStatusEnum {
        ON_SALE(1, "上架");
        private int code;
        private String value;

        ProductStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }


}
