package com.qcl.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qcl.daobean.OrderDetail;
import com.qcl.dto.OrderDTO;
import com.qcl.enums.ResultEnum;
import com.qcl.exception.SellException;
import com.qcl.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 对象转换类
 */
@Slf4j
public class OrderFormToOrderDTOConverter {
    public static OrderDTO convert(OrderForm orderForm) {
        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList;
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {}.getType());
        } catch (Exception e) {
            log.error("【对象转换】错误, string={}", orderForm.getItems());
            throw new SellException(ResultEnum.FORM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
