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
     * 商品状态标识
     */
    public enum ProductStatusEnum {
        ON_SALE(1, "在线");
        private String value;
        private int code;

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
