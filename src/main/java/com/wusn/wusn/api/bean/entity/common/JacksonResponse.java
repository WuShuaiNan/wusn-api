package com.wusn.wusn.api.bean.entity.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JacksonResponse {

    @JsonProperty(value = "data")
    Object data;

    @JsonProperty(value = "code")
    String code;

    @JsonProperty(value = "message")
    String message;

}
