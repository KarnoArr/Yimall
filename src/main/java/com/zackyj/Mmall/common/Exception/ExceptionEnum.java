package com.zackyj.Mmall.common.Exception;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.common.Exception ON 2021/7/26-周一.
 */
public enum ExceptionEnum {
    /**
     * 用户模块
     */
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

    UPDATE_FAILED(24000, "信息更新失败"),
    WRONG_PWD(24001, "原密码错误"),
    UPDATE_PWD_ERROR(24002, "密码修改失败"),
    UPDATE_EMAIL_EXIST(24003, "邮箱已被使用"),

    /**
     * 分类管理模块
     */
    ADD_CATEGORY_FAILED(30000, "添加品类失败"),
    WRONG_CATEGORY_ARGS(30001, "品类参数信息有误"),
    CATEGORY_NAME_EXIST(30002, "品类名称重复"),

    UPDATE_CATEGORY_FAILED(30003, "更新品类信息失败"),

    /**
     * 商品管理模块
     */
    UPDATE_PDT_FAILED(31000, "更新商品信息失败"),
    SET_STATUS_FAILED(31001, "修改商品状态失败"),
    INSERT_PDT_FAILED(32000, "新增商品信息失败"),
    NO_PDT_INFO(33000, "无商品信息"),

    /**
     * 收货地址模块
     */
    ADD_SHIPPING_ADDR_FAILED(40001, "添加收货地址失败"),
    DELETE_SHIPPING_ADDR_FAILED(40002, "删除收货地址失败"),
    UPDATE_SHIPPING_ADDR_FAILED(40003, "更新收货地址失败"),
    GET_SHIPPING_ADDR_DETAILE_FAILED(40004, "查询地址详情失败"),

    /**
     * 订单模块
     */
    EMPTY_CART(50001, "已勾选购物车为空"),
    NOT_ON_SALE(50002, "存在未上架商品"),
    NO_ENOUGH_STOCK(50003, "库存不足"),
    CREATE_ORDER_FAILED(50004, "创建订单失败"),
    NO_SUCH_ORDER(50005, "订单不存在"),
    PAID_ORDER_CANCEL(50006, "订单已付款，请联系客服退款"),
    CANCEL_ORDER_FAILED(50007, "订单取消失败"),

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
