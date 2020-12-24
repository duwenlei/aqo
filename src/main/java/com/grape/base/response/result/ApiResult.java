package com.grape.base.response.result;

import com.google.gson.Gson;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * @author duwenlei
 **/
public class ApiResult<T> implements Serializable {
    private int code;
    private String message;
    private T data;

    public ApiResult() {
    }

    public ApiResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResult(ApiCode code) {
        this.code = code.getCode();
        this.message = code.getMsg();
    }

    public static ApiResult<Object> success() {
        return new ApiResult<>(ApiCode.SUCCESS);
    }

    public static ApiResult<Object> success(Object data) {
        ApiResult<Object> apiResult = new ApiResult<>(ApiCode.SUCCESS);
        apiResult.data = data;
        return apiResult;
    }

    public static ApiResult<Object> failure() {
        return new ApiResult<>(ApiCode.FAILURE);
    }

    public static ApiResult<Object> failure(String message) {
        return new ApiResult<>(ApiCode.FAILURE.getCode(), message);
    }


    public static ApiResult failure(ApiCode code) {
        return new ApiResult(code.getCode(), code.getMsg());
    }

    public static void response(HttpServletResponse response, ApiCode code, Object data) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        response.setContentType("application/json; charset=UTF-8");
        ApiResult<Object> apiResult = new ApiResult<>(code.getCode(), code.getMsg(), data);
        outputStream.write(apiResult.toJson().getBytes(StandardCharsets.UTF_8));
    }

    public static void response(HttpServletResponse response, ApiCode code) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        response.setContentType("application/json; charset=UTF-8");
        ApiResult<Object> apiResult = new ApiResult<>(code.getCode(), code.getMsg());
        outputStream.write(apiResult.toJson().getBytes(StandardCharsets.UTF_8));
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
