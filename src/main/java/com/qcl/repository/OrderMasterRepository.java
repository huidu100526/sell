package com.qcl.repository;

import com.qcl.daobean.OrderMaster;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {
    /**
     * 根据用户openid查询订单列表
     */
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);

    /**
     * 根据用户openid和订单状态查询订单列表
     */
    List<OrderMaster> findByBuyerOpenidAndOrderStatus(String buyerOpenid, Integer orderStatus);
}
