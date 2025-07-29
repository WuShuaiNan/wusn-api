package com.wusn.wusn.api.utils;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameRecorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.OutputStream;

public class FFmpegUtil {

    private static final Logger logger = LoggerFactory.getLogger(FFmpegUtil.class);

    @Value("${app.video.rtsp2flv.timeout}")
    static long rtsp2flvTimeout;

    public static void convertRtspToFlv(String rtspUrl, int width, int height, OutputStream outputStream) throws Exception {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(rtspUrl);
        grabber.setOption("rtsp_transport", "tcp");
        grabber.setImageWidth(width);
        grabber.setImageHeight(height);
        grabber.start();

        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputStream, grabber.getImageWidth(), grabber.getImageHeight(), grabber.getAudioChannels());
        recorder.setFormat("flv");
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        recorder.setFrameRate(grabber.getFrameRate());
        recorder.setVideoBitrate(grabber.getVideoBitrate());
        recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
        recorder.start();

        Frame frame;
        long lastSuccessTime = System.currentTimeMillis();
        final long TIMEOUT = rtsp2flvTimeout;

        while ((frame = grabber.grabFrame()) != null) {
            try {
                // 检查输出流是否仍然可用
                if (!isOutputStreamAlive(outputStream)) {
                    logger.info("前端客户端被关闭, 关闭视频流服务！" );
                    break;
                }

                recorder.record(frame);
                lastSuccessTime = System.currentTimeMillis();

                // 超时检查
                if (System.currentTimeMillis() - lastSuccessTime > TIMEOUT) {
                    logger.info("视频流传输超时，关闭转换服务！" );
                    break;
                }
            } catch (FrameRecorder.Exception e) {
                logger.error("FrameRecorder 报错，报错详情: {}", e.getMessage());
                if (System.currentTimeMillis() - lastSuccessTime > TIMEOUT) {
                    break;
                }
            }
        }

        recorder.close();
        grabber.close();
    }

    // 检查输出流是否存活
    private static boolean isOutputStreamAlive(OutputStream outputStream) {
        try {
            outputStream.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}