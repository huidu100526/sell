package com.qcl.form;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 编程小石头：2501902696（微信）
 */
@Data
public class UserForm {


    /**
     * 买家姓名
     */
    @NotNull(message = "姓名必填")
    private String username;

    /**
     * 买家手机号
     */
    @NotNull(message = "手机号必填")
    private String phone;


    /**
     * 买家微信openid
     */
    @NotNull(message = "openid必填")
    private String openid;

    private String zhuohao;
    private String renshu;
}
