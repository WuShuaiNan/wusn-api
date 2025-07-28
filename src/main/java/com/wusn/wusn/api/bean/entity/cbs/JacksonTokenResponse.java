package com.wusn.wusn.api.bean.entity.cbs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JacksonTokenResponse {

    @JsonProperty(value = "code")
    String code;

    @JsonProperty(value = "data")
    JacksonTokenResponseData data;

    @JsonProperty(value = "msg")
    String msg;

}
