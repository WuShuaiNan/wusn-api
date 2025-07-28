package com.wusn.wusn.api.controller.hello.world;

import com.wusn.wusn.api.bean.entity.common.JacksonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller Hello World 接口
 *
 * @author wusn
 * @since 1.0.0.a
 */
@RestController
@RequestMapping("/hello-world")
@CrossOrigin
@Slf4j
public class HelloWorldController {

    @GetMapping("/")
    public JacksonResponse HelloWorldResponse() {
        try {
            return new JacksonResponse("Hello world! Hello you! Nice to greet you, fresh and new! (Shuainan Wu)", "200", "");
        } catch (Exception e) {
            return new JacksonResponse(null, "500", e.getMessage());
        }
    }

}
