package com.qcl.controller;

import com.qcl.VO.ResultVO;
import com.qcl.daobean.Comment;
import com.qcl.daobean.OrderMaster;
import com.qcl.dto.OrderDTO;
import com.qcl.enums.OrderStatusEnum;
import com.qcl.enums.ResultEnum;
import com.qcl.exception.SellException;
import com.qcl.repository.CommentRepository;
import com.qcl.repository.OrderMasterRepository;
import com.qcl.service.OrderService;
import com.qcl.utils.ResultVOUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 订单评论部分
 */
@RestController
public class CommentController {

    @Autowired
    private CommentRepository repository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMasterRepository masterRepository;

    //订单详情
    @PostMapping("/comment")
    public ResultVO detail(@RequestParam("openid") String openid, @RequestParam("orderId") String orderId,
                           @RequestParam("name") String name, @RequestParam("avatarUrl") String avatarUrl,
                           @RequestParam("content") String content) {
        if (StringUtils.isEmpty(openid) || StringUtils.isEmpty(orderId)) {
            throw new SellException(ResultEnum.FORM_ERROR);
        }
        // 提交评论
        Comment comment = new Comment();
        comment.setName(name);
        comment.setAvatarUrl(avatarUrl);
        comment.setOpenid(openid);
        comment.setContent(content);
        Comment save = repository.save(comment);

        // 修改订单状态
        OrderDTO orderDTO = orderService.findOne(orderId);
        orderDTO.setOrderStatus(OrderStatusEnum.COMMENT.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        masterRepository.save(orderMaster);
        return ResultVOUtil.success(save);
    }

    // 所有评论
    @GetMapping("/commentList")
    public ResultVO commentList() {
        List<Comment> all = repository.findAll();
        return ResultVOUtil.success(all);
    }

    // 单个用户的所有评论
    @GetMapping("/userCommentList")
    public ResultVO userCommentList(@RequestParam("openid") String openid) {
        List<Comment> all = repository.findAllByOpenid(openid);
        return ResultVOUtil.success(all);
    }
}
