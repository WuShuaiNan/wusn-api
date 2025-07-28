package com.wusn.wusn.api.bean.entity.cbs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JacksonPaymentResponse {

    @JsonProperty(value = "code")
    String code;

    @JsonProperty(value = "data")
    List<JacksonPaymentResponseData> data;

    @JsonProperty(value = "msg")
    String msg;

}
