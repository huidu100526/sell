package com.qcl.controller.seller;

import com.qcl.VO.ResultVO;
import com.qcl.converter.OrderFormToOrderDTOConverter;
import com.qcl.dto.OrderDTO;
import com.qcl.enums.ResultEnum;
import com.qcl.exception.SellException;
import com.qcl.form.OrderForm;
import com.qcl.service.BuyerService;
import com.qcl.service.OrderService;
import com.qcl.utils.ResultVOUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

/**
 * 买家订单
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    // 创建订单
    @PostMapping("/create")
    public ResultVO create(@Valid OrderForm orderForm, BindingResult bindingResult) {
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
        OrderDTO order = orderService.createOrder(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", order.getOrderId());

        return ResultVOUtil.success(map);
    }

    // 订单列表
    @GetMapping("/listByStatus")
    public ResultVO listByStatus(@RequestParam("openid") String openid,
                                 @RequestParam(value = "orderStatus", defaultValue = "0") Integer orderStatus) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.FORM_ERROR);
        }

        List<OrderDTO> orderList = buyerService.findOrderList(openid, orderStatus);
        return ResultVOUtil.success(orderList);
    }

    // 订单详情
    @GetMapping("/detail")
    public ResultVO detail(@RequestParam("openid") String openid, @RequestParam("orderId") String orderId) {
        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(orderDTO);
    }

    // 取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("orderId") String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        // 取消订单
        orderService.cancel(orderDTO);
        return ResultVOUtil.success();
    }
}
