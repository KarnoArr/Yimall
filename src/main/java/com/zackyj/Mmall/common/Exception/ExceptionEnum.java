package com.zackyj.Mmall.common.Exception;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.common.Exception ON 2021/7/26-周一.
 */
public enum ExceptionEnum {
        BASE_ERROR_CODE(20000,"服务器异常"),
        NO_SUCH_USER(20001,"用户不存在"),
        LOGIN_FAILED(20002,"登录失败:用户名或密码有误");

        Integer code;
        String msg;

        ExceptionEnum(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
}
