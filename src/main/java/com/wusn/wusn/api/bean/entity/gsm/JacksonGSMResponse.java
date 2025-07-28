package com.wusn.wusn.api.bean.entity.gsm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 响应信息。
 *
 * @author wusn
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JacksonGSMResponse {

    @JsonProperty(value = "phone_num")
    String phoneNum;

    @JsonProperty(value = "message")
    String message;

    @JsonProperty(value = "send_time")
    Date sendTime;

    @JsonProperty(value = "success_flag")
    Boolean successFlag;

    @JsonProperty(value = "error")
    String error;

}
