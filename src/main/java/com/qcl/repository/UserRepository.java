package com.qcl.repository;

import com.qcl.daobean.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    /**
     * 根据openid查询用户
     */
    User findByOpenid(String openid);
}
