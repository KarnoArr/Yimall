package com.zackyj.Mmall.common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zackyj.Mmall.common.Exception.ExceptionEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

/**
 * @Description 接口公共返回对象
 * @author zackyj
 */
@Data
public class CommonResponse<T> implements Serializable {
    private Integer status;
    private String msg;
    private T data;

    private static final int OK_CODE = 10000;
    private static final String OK_MSG = "SUCCESS";
    private static final int ERROR_CODE = 90000;

    //私有构造器：不允许创建实例
    private CommonResponse(){}

    private CommonResponse(int status,String msg){
        this.status = status;
        this.msg = msg;
    }

//=================返回成功的方法============================

    //成功：无参数
    public static <T> CommonResponse<T> success(){
        CommonResponse response = new CommonResponse();
        response.setStatus(OK_CODE);
        response.setMsg(OK_MSG);
        return response;
    }

    public static <T> CommonResponse<T> success(String msg) {
        CommonResponse response = new CommonResponse();
        response.setStatus(OK_CODE);
        response.setMsg(msg);
        return response;
    }

    public static <T> CommonResponse<T> success(T data, int flag) {
        CommonResponse response = new CommonResponse();
        response.setStatus(OK_CODE);
        response.setData(data);
        return response;
    }

    //成功：有参数
    public static <T> CommonResponse<T> success(T data) {
        CommonResponse response = new CommonResponse();
        response.setStatus(OK_CODE);
        response.setMsg(OK_MSG);
        response.setData(data);
        return response;
    }

    public static <T> CommonResponse<T> success(String msg,T data){
         CommonResponse response = new CommonResponse();
         response.setStatus(OK_CODE);
         response.setMsg(msg);
         response.setData(data);
         return response;
    }

    //=================返回失败的方法============================
    public static <T> CommonResponse<T> error(){
        CommonResponse response = new CommonResponse();
        response.setStatus(ERROR_CODE);
        return response;
    }

    public static <T> CommonResponse<T> error(ExceptionEnum ee){
        CommonResponse response = new CommonResponse();
        response.setStatus(ee.getCode());
        response.setMsg(ee.getMsg());
        return response;
    }
    public static <T> CommonResponse<T> error(String msg){
        CommonResponse response = new CommonResponse();
        response.setStatus(ERROR_CODE);
        response.setMsg(msg);
        return response;
    }
}
