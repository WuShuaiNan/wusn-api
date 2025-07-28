package com.wusn.wusn.api.controller.oa.notify;

import com.wusn.wusn.api.bean.entity.common.JacksonResponse;
import com.wusn.wusn.api.bean.entity.oa.notify.JacksonOANotifyResponse;
import com.wusn.wusn.api.bean.entity.oa.notify.WebInputOANotify;
import com.wusn.wusn.api.service.oa.notify.OANotifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller OA 通知接口
 *
 * @author wusn
 * @since 1.0.0.a
 */
@RestController
@RequestMapping("/oa")
@CrossOrigin
@Slf4j
public class OANotifyController {

    @Autowired
    private OANotifyService oaNotifyService;

    @PostMapping("/notify")
    public JacksonResponse JacksonOANotifyResponse (
            @RequestBody WebInputOANotify request
    ) {
        try {
            JacksonOANotifyResponse response = oaNotifyService.notify(request);
            return new JacksonResponse(response, "200", "");
        } catch (Exception e) {
            return new JacksonResponse(null, "500", e.getMessage());
        }
    }

}
