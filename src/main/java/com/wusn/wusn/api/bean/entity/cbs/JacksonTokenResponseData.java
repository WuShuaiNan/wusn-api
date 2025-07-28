package com.wusn.wusn.api.bean.entity.cbs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JacksonTokenResponseData {

    @JsonProperty(value = "expires")
    int expires;

    @JsonProperty(value = "token")
    String token;

    @JsonProperty(value = "token_type")
    String tokenType;

}
