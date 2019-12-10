package com.qcl.service;

import com.qcl.daobean.ProductCategory;

import java.util.List;

/**
 * 类目
 */
public interface CategoryService {
    // 根据类目id查询所在类目
    ProductCategory findOne(Integer categoryId);

    // 查询所有类目
    List<ProductCategory> findAll();

    // 根据多个类目id查询类目
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    // 添加类目
    ProductCategory save(ProductCategory productCategory);
}
