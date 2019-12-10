package com.qcl.aspect;

import com.qcl.constant.CookieConstant;
import com.qcl.constant.RedisConstant;
import com.qcl.exception.SellerAuthorizeException;
import com.qcl.utils.CookieUtil;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 卖家登陆页面控制切面
 */
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * com.qcl.controller.buyyer.Seller*.*(..))")
    public void verify() { }

    @Before("verify()")
    public void doVerify() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        //查询cookie
        Cookie cookie = CookieUtil.getCookie(request, CookieConstant.TOKEN);
        if (cookie == null) {
            log.error("【登录校验】Cookie中查不到token");
            throw new SellerAuthorizeException();
        }

        // 去redis中查询
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)) {
            log.error("【登陆校验】Redis中查询不到token");
            throw new SellerAuthorizeException();
        }
    }
}
