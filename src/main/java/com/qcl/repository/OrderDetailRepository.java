package com.qcl.repository;

import com.qcl.daobean.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
    /**
     * 根据订单号查询订单详情
     */
    List<OrderDetail> findByOrderId(String orderId);
}
