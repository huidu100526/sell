package com.qcl.service;

import com.qcl.daobean.ProductInfo;
import com.qcl.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    /**
     * 根据商品id查询商品
     */
    ProductInfo findOne(String productId);

    /**
     * 查询所有在架商品列表
     */
    List<ProductInfo> findUpAll();

    /**
     * 后台系统查询所有
     */
    Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 添加商品
     */
    ProductInfo save(ProductInfo productInfo);

    /**
     * 加库存
     */
    void increaseStock(List<CartDTO> cartDTOList);

    /**
     * 减库存
     */
    void decreaseStock(List<CartDTO> cartDTOList);

    /**
     * 上架
     */
    void onSale(String productId);

    /**
     * 下架
     */
    void offSale(String productId);
}
