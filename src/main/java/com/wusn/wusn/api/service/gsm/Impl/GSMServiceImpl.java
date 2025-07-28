package com.wusn.wusn.api.service.gsm.Impl;

import com.wusn.wusn.api.bean.entity.gsm.JacksonGSMResponse;
import com.wusn.wusn.api.bean.entity.gsm.JacksonSmsInfo;
import com.wusn.wusn.api.handler.gsm.GSMHandler;
import com.wusn.wusn.api.service.gsm.GSMService;
import org.springframework.stereotype.Service;

@Service
public class GSMServiceImpl implements GSMService {

    private final GSMHandler gsmHandler;

    public GSMServiceImpl(GSMHandler gsmHandler) {
        this.gsmHandler = gsmHandler;
    }

    @Override
    public JacksonGSMResponse sentMessage(JacksonSmsInfo smsInfo) {
        return gsmHandler.sentMessage(smsInfo);
    }

}
