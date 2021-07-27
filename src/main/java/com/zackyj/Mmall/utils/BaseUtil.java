package com.zackyj.Mmall.utils;

import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.common.Exception.ExceptionEnum;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.utils ON 2021/7/26-周一.
 */
public class BaseUtil {
    public static boolean operateValid(CommonResponse response) {
        return response.getStatus() < ExceptionEnum.BASE_ERROR_CODE.getCode();
    }
}
