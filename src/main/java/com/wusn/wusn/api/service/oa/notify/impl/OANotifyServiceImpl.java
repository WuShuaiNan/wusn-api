package com.wusn.wusn.api.service.oa.notify.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wusn.wusn.api.bean.entity.oa.notify.JacksonOANotifyResponse;
import com.wusn.wusn.api.bean.entity.oa.notify.WebInputOANotify;
import com.wusn.wusn.api.service.oa.notify.OANotifyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class OANotifyServiceImpl implements OANotifyService {

    @Value("${oa.notify.url}")
    private String notifyUrl;

    @Override
    public JacksonOANotifyResponse notify(WebInputOANotify webInputOANotify) throws IOException {
        CloseableHttpClient client = HttpClients.custom()
                .disableContentCompression()
                .build();
        HttpPost httpPost = new HttpPost(notifyUrl);
        String json = "{\n" +
                "    \"code\": \"" + webInputOANotify.getCode() + "\",\n" +
                "    \"loginIdList\": \"" + webInputOANotify.getLoginIdList() + "\",\n" +
                "    \"title\": \"" + webInputOANotify.getTitle() + "\",\n" +
                "    \"context\": \"" + webInputOANotify.getContext() + "\",\n" +
                "    \"linkUrl\": \"" + webInputOANotify.getLinkUrl() + "\",\n" +
                "    \"linkMobileUrl\": \"" + webInputOANotify.getLinkMobileUrl() + "\"\n" +
                "}";
        httpPost.setEntity(new StringEntity(json));
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Accept", "*/*");
        httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");
        httpPost.setHeader("Connection", "keep-alive");
        httpPost.setHeader("Cache-Control", "no-cache");
        try (CloseableHttpResponse response = client.execute(httpPost)) {
            InputStream content = response.getEntity().getContent();
            byte[] responseData = IOUtils.toByteArray(content);
            if (responseData == null) {
                responseData = new byte[0];
            }
            String responseDataString = new String(responseData);
            ObjectMapper objectMapper = new ObjectMapper();
            JacksonOANotifyResponse jacksonOANotifyResponse = objectMapper.readValue(responseDataString, JacksonOANotifyResponse.class);
            client.close();
            return jacksonOANotifyResponse;
        }
    }

}
