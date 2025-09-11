package com.wusn.wusn.api.service.iot.impl;

import com.wusn.wusn.api.service.iot.RSAResponseService;
import com.wusn.wusn.api.utils.RSAUtil;
import org.springframework.stereotype.Service;

@Service
public class RSAResponseServiceImpl implements RSAResponseService {
    @Override
    public String encrypt(String publicKey, String data) {
        return RSAUtil.encrypt(publicKey, data);
    }
}
