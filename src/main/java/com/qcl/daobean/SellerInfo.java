package com.qcl.daobean;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

/**
 * 卖家信息表
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
public class SellerInfo {
    @Id
    @GeneratedValue
    private Integer sellerId;

    /**
     * 卖家用户名
     */
    private String username;

    /**
     * 卖家登陆密码
     */
    private String password;

    /**
     * 卖家登陆账号即手机号
     */
    private String phone;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
