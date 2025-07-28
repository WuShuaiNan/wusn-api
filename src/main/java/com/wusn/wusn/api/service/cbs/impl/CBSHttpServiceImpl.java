package com.wusn.wusn.api.service.cbs.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wusn.wusn.api.bean.entity.cbs.JacksonTokenResponse;
import com.wusn.wusn.api.service.cbs.CBSHttpService;
import com.wusn.wusn.api.utils.Constants;
import com.wusn.wusn.api.utils.SM2Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

@Service
@Slf4j
public class CBSHttpServiceImpl implements CBSHttpService {

    @Value("${cbs.body.encryption.key}")
    private String bodyEncryptionKey;

    @Value("${cbs.sign.encryption.private.key}")
    private String signEncryptionPrivateKey;

    @Value("${cbs.body.decryption.key}")
    private String bodyDecryptionKey;

    @Value("${cbs.url.token}")
    private String tokenUrl;

    @Value("${cbs.app.id}")
    private String appId;

    @Value("${cbs.app.secret}")
    private String appSecret;

    @Value("${cbs.app.grant.type}")
    private String grantType;

    @Override
    public String post(String url, String requestData) throws Exception {
        CloseableHttpClient client = HttpClients.custom()
                .disableContentCompression()
                .build();
        HttpPost httpPost = setupRequest(url, requestData, getToken());
        try (CloseableHttpResponse response = client.execute(httpPost)) {
            byte[] finalResponseData = handleResponse(response);
            String responseData = new String(finalResponseData);
            log.info("\n返回结果：{}", responseData);
            client.close();
            return responseData;
        }
    }

    public String getToken() throws Exception {
        CloseableHttpClient client = HttpClients.custom()
                .disableContentCompression()
                .build();
        HttpPost httpPost = new HttpPost(tokenUrl);
        String json = "{\n" +
                "    \"app_id\": \"" + appId + "\",\n" +
                "    \"app_secret\": \"" + appSecret + "\",\n" +
                "    \"grant_type\": \"" + grantType + "\"\n" +
                "}";
        httpPost.setEntity(new StringEntity(json));
        try (CloseableHttpResponse response = client.execute(httpPost)) {
            InputStream content = response.getEntity().getContent();
            byte[] responseData = IOUtils.toByteArray(content);
            if (responseData == null) {
                responseData = new byte[0];
            }
            String responseDataString = new String(responseData);
            ObjectMapper objectMapper = new ObjectMapper();
            JacksonTokenResponse tokenResponse = objectMapper.readValue(responseDataString, JacksonTokenResponse.class);
            client.close();
            return tokenResponse.getData().getToken();
        }
    }

    public HttpPost setupRequest(String url, String requestData, String token) {
        long timestamp = System.currentTimeMillis();
        byte[] requestDataBytes = requestData.getBytes(StandardCharsets.UTF_8);
        byte[] timestampBytes = ("&timestamp=" + timestamp).getBytes(StandardCharsets.UTF_8);
        byte[] newBytes = new byte[requestDataBytes.length + timestampBytes.length];
        System.arraycopy(requestDataBytes, 0, newBytes, 0, requestDataBytes.length);
        System.arraycopy(timestampBytes, 0, newBytes, requestDataBytes.length, timestampBytes.length);
        byte[] signature = SM2Util.sign(signEncryptionPrivateKey, newBytes);
        String sign = Base64.encodeBase64String(SM2Util.encodeDERSignature(signature));
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(Constants.SIGN_HEADER_NAME, sign);
        httpPost.setHeader(Constants.TIMESTAMP_HEADER, Long.toString(timestamp));
        httpPost.setHeader(HTTP.CONTENT_TYPE, Constants.TARGET_CONTENT_TYPE);
        httpPost.setHeader(Constants.AUTHORIZATION, Constants.BEARER + token);
        byte[] encryptedData = SM2Util.encrypt(bodyEncryptionKey, requestDataBytes);
        httpPost.setEntity(new ByteArrayEntity(encryptedData));
        log.info("网址:{}", url);
        log.info("请求体:{}", requestData);
        log.info("签名:{}", sign);
        log.info("加密后请求体:{}", encryptedData);
        return httpPost;
    }

    private byte[] handleResponse(HttpResponse response) throws Exception {
        InputStream content = response.getEntity().getContent();
        byte[] responseData = IOUtils.toByteArray(content);
        if (responseData == null || responseData.length == 0) {
            return responseData == null ? new byte[0] : responseData;
        }
        Boolean encryptionEnable = getHeader(response, Constants.ENCRYPTION_ENABLED_HEADER_NAME);
        if (Boolean.TRUE.equals(encryptionEnable)) {
            responseData = SM2Util.decrypt(bodyDecryptionKey, responseData);
        }
        Boolean xMbcloudCompress = getHeader(response, Constants.X_MBCLOUD_COMPRESS);
        if (Boolean.TRUE.equals(xMbcloudCompress)) {
            responseData = decompress(responseData);
        }
        return responseData;
    }

    private static Boolean getHeader(HttpMessage message, String name) {
        Header header = message.getFirstHeader(name);
        return header != null;
    }

    public static byte[] decompress(byte[] data) throws IOException {
        ByteArrayInputStream input = new ByteArrayInputStream(data);
        GZIPInputStream gzipInput = new GZIPInputStream(input);
        return IOUtils.toByteArray(gzipInput);
    }
}
