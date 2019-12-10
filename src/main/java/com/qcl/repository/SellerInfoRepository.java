package com.qcl.repository;

import com.qcl.daobean.SellerInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerInfoRepository extends JpaRepository<SellerInfo, Integer> {
    /**
     * 根据用户手机号查询卖家信息
     */
    SellerInfo findByPhone(String phone);

    /**
     * 根据用户id查询卖家信息
     */
    SellerInfo findBySellerId(Integer sellerId);
}
