package com.qcl.utils;

import java.util.Random;

public class GetKeyUtil {
    /**
     * 生成唯一随机主键方法
     * 时间 + 随机数
     */
    public static synchronized String getKey() {
        Random random = new Random();
        int number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}
