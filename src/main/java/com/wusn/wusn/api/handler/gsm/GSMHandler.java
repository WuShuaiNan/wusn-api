package com.wusn.wusn.api.handler.gsm;

import com.wusn.wusn.api.bean.entity.gsm.JacksonGSMResponse;
import com.wusn.wusn.api.bean.entity.gsm.JacksonSmsInfo;

public interface GSMHandler {

    /**
     * 发送短信。
     *
     * @param smsInfo 短信
     * @return 发送响应
     */
    JacksonGSMResponse sentMessage(JacksonSmsInfo smsInfo);

}
