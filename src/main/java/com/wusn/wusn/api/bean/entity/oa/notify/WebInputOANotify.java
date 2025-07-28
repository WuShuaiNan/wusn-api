package com.wusn.wusn.api.bean.entity.oa.notify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebInputOANotify {

    @JsonProperty(value = "code")
    String code;

    @JsonProperty(value = "loginIdList")
    String loginIdList;

    @JsonProperty(value = "title")
    String title;

    @JsonProperty(value = "context")
    String context;

    @JsonProperty(value = "linkUrl")
    String linkUrl;

    @JsonProperty(value = "linkMobileUrl")
    String linkMobileUrl;

}
