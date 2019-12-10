package com.qcl.form;

import lombok.Data;

/**
 * 卖家表单对象
 */
@Data
public class SellerForm {
    private String username;

    private String password;

    private String phone;

    private Integer sellerId;
}
