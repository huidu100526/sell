package com.qcl.service.impl;

import com.qcl.daobean.SellerInfo;
import com.qcl.repository.SellerInfoRepository;
import com.qcl.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellerInfoRepository repository;

    @Override
    public SellerInfo findSellerInfoByPhone(String phone) {
        return repository.findByPhone(phone);
    }
}
