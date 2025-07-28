package com.wusn.wusn.api.service.oa.notify;

import com.wusn.wusn.api.bean.entity.oa.notify.JacksonOANotifyResponse;
import com.wusn.wusn.api.bean.entity.oa.notify.WebInputOANotify;

import java.io.IOException;

public interface OANotifyService {

    JacksonOANotifyResponse notify(WebInputOANotify webInputOANotify) throws IOException;

}
