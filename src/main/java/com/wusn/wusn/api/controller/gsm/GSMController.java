package com.wusn.wusn.api.controller.gsm;

import com.wusn.wusn.api.bean.entity.common.JacksonResponse;
import com.wusn.wusn.api.bean.entity.gsm.JacksonGSMResponse;
import com.wusn.wusn.api.bean.entity.gsm.JacksonSmsInfo;
import com.wusn.wusn.api.service.gsm.GSMService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class GSMController {

    GSMService gsmService;

    public GSMController(GSMService gsmService) {
        this.gsmService = gsmService;
    }

    /**
     * 发送短信。
     *
     * @param smsInfo 短信
     * @return 发送响应
     */
    @PostMapping("/sent-message")
    public JacksonResponse sentMessage(
            HttpServletRequest request,
            @RequestBody JacksonSmsInfo smsInfo
    ) {
        try {
            JacksonResponse response = new JacksonResponse();
            JacksonGSMResponse gsmResponse = gsmService.sentMessage(smsInfo);
            response.setData(gsmResponse);
            if (gsmResponse.getSuccessFlag()){
                response.setCode("200");
                response.setMessage("GSM短信发送成功！");
            } else {
                response.setCode("500");
                response.setMessage("GSM短信发送失败！");
            }
                return response;
        } catch (Exception e){
            return new JacksonResponse(
                    e.getMessage(),
                    "500",
                    "GSM短信发送失败！详情请见 data "
            );
        }
    }

}
