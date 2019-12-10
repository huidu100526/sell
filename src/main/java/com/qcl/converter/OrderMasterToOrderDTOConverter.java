package com.qcl.converter;

import com.qcl.daobean.OrderMaster;
import com.qcl.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 对象转换类
 */
public class OrderMasterToOrderDTOConverter {
    private static OrderDTO convert(OrderMaster orderMaster) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList) {
        return orderMasterList.stream().map(OrderMasterToOrderDTOConverter::convert).collect(Collectors.toList());
    }
}
