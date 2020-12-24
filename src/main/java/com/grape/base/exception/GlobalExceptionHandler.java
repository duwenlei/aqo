package com.grape.base.exception;

import com.grape.base.response.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author duwenlei
 **/
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ApiResult<Object> defaultExceptionHandle(HttpServletRequest request, Exception e) {
        log.error("Global Exception handle : message=[{}]", e.getMessage(), e);
        return ApiResult.failure(e.getMessage());
    }

}
