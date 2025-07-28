package com.wusn.wusn.api.bean.entity.cbs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JacksonPaymentResponseData {

    @JsonProperty(value = "successed")
    Boolean successed;

    @JsonProperty(value = "busNum")
    String busNum;

    @JsonProperty(value = "referenceNum")
    String referenceNum;

    @JsonProperty(value = "freezeFlowNum")
    String freezeFlowNum;

    @JsonProperty(value = "errorMsg")
    String errorMsg;

    @JsonProperty(value = "errorCode")
    String errorCode;

    @JsonProperty(value = "recordNum")
    String recordNum;

}
