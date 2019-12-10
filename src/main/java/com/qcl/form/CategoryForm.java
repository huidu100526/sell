package com.qcl.form;

import lombok.Data;

/**
 * 类目表单对象
 */
@Data
public class CategoryForm {
    private Integer categoryId;

    private String categoryName;

    private Integer categoryType;
}
