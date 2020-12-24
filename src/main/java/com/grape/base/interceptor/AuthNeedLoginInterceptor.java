package com.grape.base.interceptor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author duwenlei
 **/
@Slf4j
@Component
public class AuthNeedLoginInterceptor implements HandlerInterceptor {
    @Value("${server.servlet.context-path:}")
    private String contextPath;
    private static final Map<String, Map<String, List<String>>> WHITE_LIST_MAP;
    private static final String CONTENT_TYPE = "application/json;charset=utf-8";

    static {
        String filePath = Objects.requireNonNull(AuthNeedLoginInterceptor.class.getClassLoader().getResource("config/white-list.json")).getPath();
        StringBuilder whiteListStr = new StringBuilder();
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(filePath);
            bufferedReader = new BufferedReader(fileReader);

            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                whiteListStr.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        String jsonStr = whiteListStr.toString();
        log.info(jsonStr);
        Gson gson = new GsonBuilder().setLenient().create();
        WHITE_LIST_MAP = gson.fromJson(jsonStr, HashMap.class);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("check if the user is login in.");
        String reqUri = request.getRequestURI();
        return authorityCheck(reqUri);
    }

    private static final Pattern COMPILE = Pattern.compile("(\\w+)");

    private boolean authorityCheck(String reqUri) {
        Matcher matcher = COMPILE.matcher(reqUri);
        int groupId = 0;
        String projectName = "";
        String controllerName = "";
        String actionName = "";
        while (matcher.find()) {
            String group = matcher.group(0);
            switch (groupId) {
                case 0:
                    projectName = group;
                    break;
                case 1:
                    controllerName = group;
                    break;
                case 2:
                    actionName = group;
                    break;
                default:
            }
            groupId++;
        }
        // 允许匿名 API
        Map<String, List<String>> anonymous = WHITE_LIST_MAP.get("anonymous");
        if (anonymous.containsKey(controllerName)) {
            List<String> uris = anonymous.get(controllerName);
            if (uris.contains(actionName)) {
                return true;
            }
        }
        // grape API
        Map<String, List<String>> erp = WHITE_LIST_MAP.get("erp");
        if (erp.containsKey(controllerName)) {
            List<String> uris = erp.get(controllerName);
            if (uris.contains(actionName)) {
                return true;
            }
        }
        // api
        Map<String, List<String>> api = WHITE_LIST_MAP.get("api");
        if (api.containsKey(controllerName)) {
            List<String> uris = api.get(controllerName);
            return uris.contains(actionName);
        }
        // dashboard
        Map<String, List<String>> dashboard = WHITE_LIST_MAP.get("dashboard");
        if (dashboard.containsKey(controllerName)) {
            List<String> uris = dashboard.get(controllerName);
            return uris.contains(actionName);
        }
        return false;
    }
}
