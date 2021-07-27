package com.zackyj.Mmall.common.Exception;

import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.common.Exception ON 2021/7/27-周二.
 */
@Component
public class BusinessException extends RuntimeException {
    private Integer code;
    private String message;

    public BusinessException() {
    }

    ;

    public BusinessException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException(ExceptionEnum ee) {
        this(ee.getCode(), ee.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
