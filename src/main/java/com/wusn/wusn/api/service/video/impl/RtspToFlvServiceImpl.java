package com.wusn.wusn.api.service.video.impl;

import com.wusn.wusn.api.service.video.RtspToFlvService;
import com.wusn.wusn.api.utils.FFmpegUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@Service
public class RtspToFlvServiceImpl implements RtspToFlvService {

    public void rtspToFlv(String rtspUrl, int width, int height, HttpServletResponse response) {
        response.setContentType("video/x-flv");
        response.setHeader("Connection", "keep-alive");
        response.setHeader("Cache-Control", "no-cache");

        try (OutputStream outputStream = response.getOutputStream()) {
            FFmpegUtil.convertRtspToFlv(rtspUrl, width, height, outputStream);
        } catch (Exception e) {
            throw new RuntimeException("Error streaming RTSP to FLV", e);
        }
    }

}