package com.qcl.daobean;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * 评论表
 */
@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;

    /**
     * 用户openid
     */
    private String openid;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    private Date createTime;
}
