package com.qcl.service;

import com.qcl.daobean.User;

/**
 * 用户端
 */
public interface UserService {
    /**
     * 通过openid查询用户信息
     */
    User findByOpenid(String openid);
}
