package com.qcl.VO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 商品信息返回列表
 */
@Data
public class ProductInfoVO {
    @JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("stock")
    private Integer productStock;

    @JsonProperty("desc")
    private String productDescription;

    @JsonProperty("icon")
    private String productIcon;
}
