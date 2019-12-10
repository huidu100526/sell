package com.qcl.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 订单表单对象
 */
@Data
public class OrderForm {
    @NotNull(message = "姓名必填")
    private String name;

    @NotNull(message = "手机号必填")
    private String phone;

    @NotNull(message = "请填写桌号")
    private String address;

    @NotNull(message = "openid必填")
    private String openid;

    @NotNull(message = "购物车不能为空")
    private String items;
}
