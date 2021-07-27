package com.zackyj.Mmall.common.Exception;

import com.zackyj.Mmall.common.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.common.Exception ON 2021/7/27-周二.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 拦截处理系统级异常，防止暴露
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e) {
        log.error("SYSTEM Exception：" + e);
        e.printStackTrace();
        return CommonResponse.error(ExceptionEnum.BASE_ERROR_CODE);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Object handleException(BusinessException e) {
        log.error("CommonException：" + e);
        return CommonResponse.error(e.getCode(), e.getMessage());
    }
}
