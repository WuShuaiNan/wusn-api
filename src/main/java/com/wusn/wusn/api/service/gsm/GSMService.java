package com.wusn.wusn.api.service.gsm;


import com.wusn.wusn.api.bean.entity.gsm.JacksonGSMResponse;
import com.wusn.wusn.api.bean.entity.gsm.JacksonSmsInfo;

/**
 * GSM短信发送服务。
 *
 * @author wusn
 * @since 1.0.0
 */
public interface GSMService {

    /**
     * 发送短信。
     *
     * @param smsInfo 短信
     * @return 发送响应
     */
    JacksonGSMResponse sentMessage(JacksonSmsInfo smsInfo);

}
