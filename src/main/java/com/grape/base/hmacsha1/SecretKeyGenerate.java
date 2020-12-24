package com.grape.base.hmacsha1;

import java.security.SecureRandom;

/**
 * 密钥生成
 *
 * @author duwenlei
 **/
public class SecretKeyGenerate {

    /**
     * 密钥生成
     *
     * @return
     */
    public static byte[] generate() {
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(System.currentTimeMillis());
        return secureRandom.generateSeed(16);
    }
}
