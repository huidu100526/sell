package com.qcl.controller;

import com.qcl.VO.ResultVO;
import com.qcl.converter.OrderFormToOrderDTOConverter;
import com.qcl.dto.OrderDTO;
import com.qcl.enums.OrderStatusEnum;
import com.qcl.enums.ResultEnum;
import com.qcl.exception.SellException;
import com.qcl.form.OrderForm;
import com.qcl.service.BuyerService;
import com.qcl.service.OrderService;
import com.qcl.service.impl.PayService;
import com.qcl.utils.ResultVOUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 订单支付部分
 */
@RestController
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @Autowired
    private BuyerService buyerService;

    // 弹框去支付
    @GetMapping("/goPay")
    public ResultVO goPay(@RequestParam("orderId") String orderId) {
        // 查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        // 发起支付
        OrderDTO orderDTO1 = payService.goPay(orderDTO);
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW_PAYED.getCode())) {
            log.error("【取消订单】订单状态不正确,  orderStatus={}", orderDTO1.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        return ResultVOUtil.success(true);
    }

    // 取消支付
    @PostMapping("/cancel")
    public ResultVO cancel(@Valid OrderForm orderForm, BindingResult bindingResult, @RequestParam("openid") String openid) {
        // 判断是否有校验错误
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确, orderForm={}", orderForm);
            throw new SellException(ResultEnum.FORM_ERROR.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        // 将OrderForm转换为OrderDTO对象
        OrderDTO orderDTO = OrderFormToOrderDTOConverter.convert(orderForm);
        if (orderDTO.getOrderDetailList().isEmpty()) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_IS_NULL);
        }
        // 取消支付，还需创建未支付订单
        OrderDTO order = orderService.createOrderIsNew(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", order.getOrderId());
        return ResultVOUtil.success(map);
    }
}
