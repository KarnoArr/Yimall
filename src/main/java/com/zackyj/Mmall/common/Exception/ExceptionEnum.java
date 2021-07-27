package com.zackyj.Mmall.common.Exception;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.common.Exception ON 2021/7/26-周一.
 */
public enum ExceptionEnum {
    BASE_ERROR_CODE(20000, "服务器异常"),
    WRONG_ARG(20001, "参数错误"),
    NEED_LOGIN(21000, "未登录"),
    NO_SUCH_USER(21001, "用户不存在"),
    LOGIN_FAILED(21002, "登录失败:用户名或密码有误"),
    NO_ACCESS(21003, "权限不足"),

    REGISTER_FAILED(20000, "注册失败"),
    NAME_EXIST(22001, "用户名已存在"),
    EMAIL_EXIST(22002, "邮箱已存在"),

    NO_FORGET_Q(23001, "未设置密码找回问题"),
    WRONG_ANSWER(23002, "答案错误"),
    NO_TOKEN(23003, "参数错误:无效的token"),
    RESET_PWD_ERROR(23004, "重置密码失败"),

    UPDATE_FAILED(24000, "更新失败"),
    WRONG_PWD(24001, "原密码错误"),
    UPDATE_PWD_ERROR(24002, "密码修改失败"),
    UPDATE_EMAIL_EXIST(24003, "邮箱已被使用"),

    ERROR_CODE(90000, "未知错误");

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
