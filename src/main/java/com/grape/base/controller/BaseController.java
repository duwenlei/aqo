package com.grape.base.controller;

import com.grape.base.exception.GrapeException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


/**
 * @author duwenlei
 */
public class BaseController {
    private static final String API_HEADER_NAME = "signature";

    @Autowired
    public HttpServletRequest request;

    /**
     * Api 验证
     *
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    protected JSONObject getJsonData() throws IOException, NoSuchAlgorithmException, InvalidKeyException, GrapeException {
        String signature = request.getHeader(API_HEADER_NAME);
        ServletInputStream requestInputStream = request.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        IOUtils.copy(requestInputStream, byteArrayOutputStream);
        byte[] reqDataBytes = byteArrayOutputStream.toByteArray();
        String reqDataStr = new String(reqDataBytes, StandardCharsets.UTF_8);
        JSONObject reqData = new JSONObject(reqDataStr);
        String apiId = reqData.optString("apiId");
        long timestamp = reqData.optLong("timestamp");

//        // request time out
//        if (System.currentTimeMillis() - timestamp > 30000) {
//            throw new GrapeException("认证失败。");
//        }
//
//        ApiUser apiUser = apiUserService.findByApiId(apiId, 0);
//        if (apiUser == null) {
//            throw new GrapeException("请求身份异常。");
//        }
//
//        boolean verify = MacUtil.verify(apiUser.getApiSecret(), signature, reqDataBytes);
//        if (!verify) {
//            throw new GrapeException("认证字段缺失。");
//        }
        return reqData;
    }

}
