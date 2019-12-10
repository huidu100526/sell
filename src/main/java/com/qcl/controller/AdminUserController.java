package com.qcl.controller;

import com.qcl.constant.CookieConstant;
import com.qcl.constant.RedisConstant;
import com.qcl.daobean.SellerInfo;
import com.qcl.enums.ResultEnum;
import com.qcl.exception.SellException;
import com.qcl.form.SellerForm;
import com.qcl.repository.SellerInfoRepository;
import com.qcl.utils.CookieUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminUserController {
    private final SellerInfoRepository repository;

    @Autowired
    public AdminUserController(SellerInfoRepository repository) {
        this.repository = repository;
    }

    /**
     * 往redis写入String类型的值
     */
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 登陆
     */
    @GetMapping("/loginAdmin")
    public String loginAdmin(@RequestParam("phone") String phone, @RequestParam("password") String password, HttpServletResponse response) {
        SellerInfo sellerInfo = repository.findByPhone(phone);
        log.info("商家信息={}", sellerInfo);
        if (sellerInfo != null && sellerInfo.getPassword().equals(password)) {
            String token = UUID.randomUUID().toString();
            log.info("登录成功的token={}", token);

            // 设置token至redis
            Integer expire = RedisConstant.EXPIRE; // 设置过期时间
            // 将电话设为值，添加过期时间，时间单位秒
            redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), phone, expire, TimeUnit.SECONDS);

            // 设置token至cookie
            CookieUtil.setCookie(response, CookieConstant.TOKEN, token, CookieConstant.EXPIRE);
            return "登录成功";
        } else {
            throw new SellException(ResultEnum.LOGIN_FAIL);
        }
    }

    /**
     * 退出
     */
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        // 从cookie里查询
        Cookie cookie = CookieUtil.getCookie(request, CookieConstant.TOKEN);
        if (cookie != null) {
            // 清除redis中的信息
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));

            // 清除cookie，将值设置为空，过期时间设置为0就可以
            CookieUtil.setCookie(response, CookieConstant.TOKEN, null, 0);
        }
        modelAndView.addObject("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        modelAndView.addObject("url", "/sell/seller/order/list");
        modelAndView.setViewName("common/logout");
        return modelAndView;
    }

    /**
     * 管理员列表
     */
    @GetMapping("/list")
    public ModelAndView list(Map<String, Object> map) {
        List<SellerInfo> categoryList = repository.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("admin/list", map);
    }

    /**
     * 到修改管理员信息页面
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "sellerId", required = false) Integer sellerId) {
        ModelAndView modelAndView = new ModelAndView();
        SellerInfo sellerInfo = repository.findBySellerId(sellerId);
        modelAndView.addObject("category", sellerInfo);
        modelAndView.setViewName("admin/index");
        return modelAndView;
    }

    /**
     * 修改/增加管理员信息
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid SellerForm form, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        log.info("SellerForm={}", form);
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("msg", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
            modelAndView.addObject("url", "/sell/admin/index");
            modelAndView.setViewName("common/prompt");
            return modelAndView;
        }
        SellerInfo sellerInfo = new SellerInfo();
        try {
            if (form.getSellerId() != null) {
                // 有传id则为修改
                sellerInfo = repository.findBySellerId(form.getSellerId());
            }
            BeanUtils.copyProperties(form, sellerInfo);
            repository.save(sellerInfo);
        } catch (SellException e) {
            modelAndView.addObject("msg", e.getMessage());
            modelAndView.addObject("url", "/sell/admin/index");
            modelAndView.setViewName("common/prompt");
            return modelAndView;
        }
        modelAndView.addObject("msg", ResultEnum.ADMIN_UPDATE_SUCCESS.getMessage());
        modelAndView.addObject("url", "/sell/admin/list");
        modelAndView.setViewName("common/prompt");
        return modelAndView;
    }
}