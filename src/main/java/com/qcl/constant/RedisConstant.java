package com.qcl.constant;

/**
 * redis常量
 */
public interface RedisConstant {
    /**
     * 过期时间两小时
     */
    Integer EXPIRE = 7200;

    /**
     * 前缀
     */
    String TOKEN_PREFIX = "token_%s";
}
