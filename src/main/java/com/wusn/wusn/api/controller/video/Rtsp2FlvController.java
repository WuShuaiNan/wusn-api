package com.wusn.wusn.api.controller.video;

import com.wusn.wusn.api.service.video.RtspToFlvService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Controller RTSP 转 FLV 接口
 *
 * @author wusn
 * @since 1.0.0.a
 */
@RestController
@RequestMapping("/video")
@CrossOrigin
@Slf4j
public class Rtsp2FlvController {

    @Autowired
    private RtspToFlvService rtspToFlvService;

    @GetMapping("/rtsp2flv")
    public void rtsp2flv(
            @RequestParam String rtspUrl,
            @RequestParam int width,
            @RequestParam int height,
            HttpServletResponse response
    ) {
        rtspToFlvService.rtspToFlv(rtspUrl, width, height, response);
    }

}
