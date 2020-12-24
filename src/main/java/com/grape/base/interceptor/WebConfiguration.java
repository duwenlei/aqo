package com.grape.base.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author duwenlei
 **/
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    private AuthNeedLoginInterceptor authNeedLoginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authNeedLoginInterceptor);
    }
}
