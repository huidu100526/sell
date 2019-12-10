package com.qcl.enums;

import lombok.Getter;

/**
 * 返回结果类型
 */
@Getter
public enum ResultEnum {
    SUCCESS(0, "成功"),

    FORM_ERROR(1, "参数不正确"),

    PRODUCT_UPSALE_SUCCESS(4, "商品上架成功"),

    PRODUCT_DOWNSALE_SUCCESS(5, "商品下架成功"),

    PRODUCT_CATEGORY_SUCCESS(6, "类目新增修改成功"),

    PRODUCT_UPDATE_SUCCESS(7, "商品新增修改成功"),

    PICTURE_UPDATE_SUCCESS(8, "轮播图新增修改成功"),

    ADMIN_UPDATE_SUCCESS(9, "管理员新增修改成功"),

    PRODUCT_NOT_EXIST(10, "商品不存在"),

    PRODUCT_STOCK_ERROR(11, "商品库存不正确"),

    ORDER_NOT_EXIST(12, "订单不存在"),

    ORDERDETAIL_NOT_EXIST(13, "订单详情不存在"),

    ORDER_STATUS_ERROR(14, "订单状态不正确"),

    ORDER_UPDATE_FAIL(15, "订单更新失败"),

    ORDER_DETAIL_EMPTY(16, "订单详情为空"),

    ORDER_PAY_STATUS_ERROR(17, "订单支付状态不正确"),

    CART_IS_NULL(18, "购物车为空"),

    ORDER_OWNER_ERROR(19, "该订单不属于当前用户"),

    ORDER_NO_PAY(20, "用户还没有支付，提示用户支付"),

    WXPAY_NOTIFY_MONEY_VERIFY_ERROR(21, "微信支付异步通知金额校验不通过"),

    ORDER_CANCEL_SUCCESS(22, "订单取消成功"),

    ORDER_FINISH_SUCCESS(23, "订单完结成功"),

    PRODUCT_STATUS_ERROR(24, "商品状态不正确"),

    LOGIN_FAIL(25, "登录失败, 登录信息不正确"),

    LOGOUT_SUCCESS(26, "登出成功");

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
