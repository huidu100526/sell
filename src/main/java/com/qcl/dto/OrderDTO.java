package com.qcl.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qcl.daobean.OrderDetail;
import com.qcl.enums.OrderStatusEnum;
import com.qcl.utils.EnumUtil;
import com.qcl.utils.serializer.DateToStringSerializer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import lombok.Data;

/**
 * 订单传输对象
 */
@Data
public class OrderDTO {
    /**
     * 订单id
     */
    private String orderId;

    /**
     * 买家名字
     */
    private String buyerName;

    /**
     * 买家手机号
     */
    private String buyerPhone;

    /**
     * 买家桌号
     */
    private String buyerAddress;

    /**
     * 买家微信Openid
     */
    private String buyerOpenid;

    /**
     * 订单总金额
     */
    private BigDecimal orderAmount;

    /**
     * 订单状态, 默认为0新下单.
     * NEW(0, "新订单，未支付"),
     * NEW_PAYED(0, "新订单，已支付"),
     * FINISHED(1, "完结"),
     * CANCEL(2, "已取消"),
     */
    private Integer orderStatus;

    private String orderStatusStr;

    @JsonIgnore
    public String getOrderStatusStr(Integer orderStatus2) {
        return Objects.requireNonNull(EnumUtil.getByCode(orderStatus2, OrderStatusEnum.class)).getMessage();
    }

    /**
     * 创建时间
     */
    @JsonSerialize(using = DateToStringSerializer.class) //用于把date类型转换为string类型
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonSerialize(using = DateToStringSerializer.class) //用于把date类型转换为string类型
    private Date updateTime;

    List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }
}
