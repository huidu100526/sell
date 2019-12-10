package com.qcl.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品类目返回列表
 */
@Data
public class ProductVO {
    @JsonProperty("name") // @JsonProperty可以将 categoryName 在返回前端时变为 name
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> categoryFoods;
}

