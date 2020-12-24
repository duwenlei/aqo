package com.grape.base.hmacsha1;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * 通用验证类
 *
 * @author duwenlei
 **/
public class MacUtil {
    private static final String ALGORITHM = "HMACSHA1";
    private static final String SECRET_KEY_ALGORITHM = "AES";

    public static String doFinal(byte[] apiSecret, byte[] originData) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec aes = new SecretKeySpec(apiSecret, SECRET_KEY_ALGORITHM);
        Mac hmac = Mac.getInstance(ALGORITHM);
        hmac.init(aes);
        hmac.update(originData);
        byte[] bytes = hmac.doFinal();
        return Hex.encodeHexString(bytes);
    }

    /**
     * 验证请求是否合法
     *
     * @param apiSecret   密钥
     * @param reqDataHash 请求Hash
     * @param originData  请求原文
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public static boolean verify(byte[] apiSecret, String reqDataHash, byte[] originData) throws InvalidKeyException, NoSuchAlgorithmException {
        Objects.requireNonNull(apiSecret, "验证失败。");
        Objects.requireNonNull(reqDataHash, "无校验数据。");
        Objects.requireNonNull(originData, "请求数据为空。");
        String originDataHash = doFinal(apiSecret, originData);
        return originDataHash.equals(reqDataHash);
    }
}
