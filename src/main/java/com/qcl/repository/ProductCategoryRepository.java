package com.qcl.repository;

import com.qcl.daobean.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    /**
     * 根据类目类型查询商品类目
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
