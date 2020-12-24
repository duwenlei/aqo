package com.grape.base.jwt.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 存放认证信息主体类
 *
 * @author duwenlei
 **/
@Getter
@Setter
public class AuthenticationUserEntity {
    private int userId;
    private String username;
    private String passwordHash;

    private List<String> authorities;


    public Map<String, Object> getMap() {
        Map<String, Object> entityMap = new HashMap<>(2);
        entityMap.put("username", username);
        entityMap.put("userId", userId);
        return entityMap;
    }
}
