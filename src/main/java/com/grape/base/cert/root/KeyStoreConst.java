package com.grape.base.cert.root;

/**
 * ROOT jks 常量
 *
 * @author duwenlei
 */
public interface KeyStoreConst {
    /**
     * root jks prop
     */
    String ROOT_CA_ALIAS = "root";
    String ROOT_PRIVATE_KEY_ALIAS = "privateKey";
    String PASSWORD = "1234567890";

    /**
     * 项目使用的 jks prop
     */
    String SUB_CA_ALIAS = "sub";
    String SUB_PRIVATE_KEY_ALIAS = "privateKey";
    String SUB_PASSWORD = "aqo";
}
