package com.wusn.wusn.api.utils;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameRecorder;

import java.io.OutputStream;

public class FFmpegUtil {

    public static void convertRtspToFlv(String rtspUrl, OutputStream outputStream) throws Exception {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(rtspUrl);
        grabber.setOption("rtsp_transport", "tcp");
        grabber.setImageWidth(640);
        grabber.setImageHeight(480);
        grabber.start();

        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputStream, grabber.getImageWidth(), grabber.getImageHeight(), grabber.getAudioChannels());
        recorder.setFormat("flv");
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        recorder.setFrameRate(grabber.getFrameRate());
        recorder.setVideoBitrate(grabber.getVideoBitrate());
        recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
        recorder.start();

        Frame frame;
        while ((frame = grabber.grabFrame()) != null) {
            try {
                recorder.record(frame);
            } catch (FrameRecorder.Exception e) {
                System.err.println("Error recording frame: " + e.getMessage());
            }
        }

        recorder.close();
        grabber.close();
    }
}