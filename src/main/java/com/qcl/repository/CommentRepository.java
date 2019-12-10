package com.qcl.repository;

import com.qcl.daobean.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    /**
     * 根据openid查询用户评论信息
     */
    List<Comment> findAllByOpenid(String openid);
}
