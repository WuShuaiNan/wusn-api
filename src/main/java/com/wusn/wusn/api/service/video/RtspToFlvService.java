package com.wusn.wusn.api.service.video;

import javax.servlet.http.HttpServletResponse;

public interface RtspToFlvService {

    void rtspToFlv(String rtspUrl, HttpServletResponse response);

}
