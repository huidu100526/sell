package com.qcl.service;

import com.qcl.daobean.SellerInfo;

public interface SellerService {
    /**
     * 通过openid查询卖家端信息
     */
    SellerInfo findSellerInfoByPhone(String phone);
}
