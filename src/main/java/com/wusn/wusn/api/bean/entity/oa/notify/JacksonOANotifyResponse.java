package com.wusn.wusn.api.bean.entity.oa.notify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JacksonOANotifyResponse {

    @JsonProperty(value = "success")
    boolean success;

    @JsonProperty(value = "msg")
    String msg;

    @JsonProperty(value = "errorMsg")
    String errorMsg;

    @JsonProperty(value = "bytes")
    String bytes;

    @JsonProperty(value = "obj")
    JacksonOANotifyResponseObject obj;

    @JsonProperty(value = "obj2")
    JacksonOANotifyResponseObject obj2;

    @JsonProperty(value = "obj3")
    JacksonOANotifyResponseObject obj3;

}
