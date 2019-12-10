package com.qcl.controller.buyyer;

import com.qcl.dto.OrderDTO;
import com.qcl.enums.ResultEnum;
import com.qcl.exception.SellException;
import com.qcl.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 卖家端订单
 */
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 订单列表
     * @param page 第几页, 从1页开始
     * @param size 一页有多少条数据
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size) {
        ModelAndView modelAndView = new ModelAndView();
        PageRequest request = PageRequest.of(page - 1, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(request);
        modelAndView.addObject("orderDTOPage", orderDTOPage);
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("size", size);
        modelAndView.setViewName("order/list");
        return modelAndView;
    }

    /**
     * 取消订单
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            // 查询订单
            OrderDTO orderDTO = orderService.findOne(orderId);
            // 进行取消
            orderService.cancel(orderDTO);
        } catch (SellException e) {
            log.error("【卖家取消订单】异常{}", e);
            // 发现错误将信息返回
            modelAndView.addObject("msg", e.getMessage());
            modelAndView.addObject("url", "/sell/seller/order/list");
            modelAndView.setViewName("common/prompt");
            return modelAndView;
        }
        modelAndView.addObject("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        modelAndView.addObject("url", "/sell/seller/order/list");
        modelAndView.setViewName("common/prompt");
        return modelAndView;
    }

    /**
     * 订单详情
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId) {
        ModelAndView modelAndView = new ModelAndView();
        OrderDTO orderDTO;
        try {
            orderDTO = orderService.findOne(orderId);
        }catch (SellException e) {
            log.error("【卖家订单详情列表】异常{}", e);
            // 发现错误将信息返回
            modelAndView.addObject("msg", e.getMessage());
            modelAndView.addObject("url", "/sell/seller/order/list");
            modelAndView.setViewName("common/prompt");
            return modelAndView;
        }
        modelAndView.addObject("orderDTO", orderDTO);
        modelAndView.setViewName("order/detail");
        return modelAndView;
    }

    /**
     * 完结订单
     */
    @GetMapping("/finish")
    public ModelAndView finished(@RequestParam("orderId") String orderId,
                                 Map<String, Object> map) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            // 查询订单
            OrderDTO orderDTO = orderService.findOne(orderId);
            // 进行完结
            orderService.finish(orderDTO);
        } catch (SellException e) {
            log.error("【卖家完结订单】异常{}", e);
            // 发现错误将信息返回
            modelAndView.addObject("msg", e.getMessage());
            modelAndView.addObject("url", "/sell/seller/order/list");
            modelAndView.setViewName("common/prompt");
            return modelAndView;
        }
        modelAndView.addObject("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        modelAndView.addObject("url", "/sell/seller/order/list");
        modelAndView.setViewName("common/prompt");
        return modelAndView;
    }
}
