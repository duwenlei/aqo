package com.grape.base.aspect;

import com.google.gson.Gson;
import com.grape.base.response.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author duwenlei
 **/
@Slf4j
@Aspect
@Component
public class ApiAspectHolder {

    @Pointcut("execution(* com.grape.v1.controller..*.*(..))")
    public void endpoint() {

    }

    @Before("endpoint()")
    public void doBeforeController(JoinPoint joinPoint) {
        Object[] obj = joinPoint.getArgs();
        Gson gson = new Gson();
        log.info("reqData:{}", reduce(gson.toJson(obj)));
    }

    @AfterReturning(pointcut = "endpoint()", returning = "retValue")
    public void doAfterController(JoinPoint joinPoint, Object retValue) {
        if (retValue instanceof ApiResult) {
            log.info("respData:{}", reduce(((ApiResult) retValue).toJson()));
        } else {
            log.info("respData:{}", retValue);
        }
    }

    public String reduce(String str) {
        str = str.replaceAll("fileBytes\":\".*?\"", "fileBytes\":\"...\"");
        str = str.replaceAll("licensePhotoImg\":\".*?\"", "licensePhotoImg\":\"...\"");
        str = str.replaceAll("idCardFront\":\".*?\"", "idCardFront\":\"...\"");
        str = str.replaceAll("idCardReverse\":\".*?\"", "idCardReverse\":\"...\"");
        str = str.replaceAll("idCardPhoto\":\".*?\"", "idCardPhoto\":\"...\"");
        str = str.replaceAll("shopPhoto\":\".*?\"", "shopPhoto\":\"...\"");
        str = str.replaceAll("captchaImgB6\":\".*?\"", "captchaImgB6\":\"...\"");
        str = str.replaceAll("licenseImg\":\".*?\"", "licenseImg\":\"...\"");
        return str;
    }

}
