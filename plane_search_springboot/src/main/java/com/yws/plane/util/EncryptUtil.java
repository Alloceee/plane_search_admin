package com.yws.plane.util;


import org.springframework.util.Base64Utils;
import org.springframework.util.DigestUtils;

import java.util.Arrays;

/**
 * md5加密
 */
public class EncryptUtil {

    /**
     * 加密
     *
     * @param text 加密字符串
     * @param key  加密密钥
     * @return 加密后的md5字符串
     */
    public static String Md5(String text, String key) {
        return DigestUtils.md5DigestAsHex((text + key).getBytes());
    }

    /**
     * 验证
     *
     * @param text 加密的字符串
     * @param key  加密密钥
     * @param md5  加密的md5字符串
     * @return 是否匹配
     */
    public static boolean verify(String text, String key, String md5) {
        String md5Text = Md5(text, key);
        return md5Text.equalsIgnoreCase(md5);
    }

    public static String base64Decode(String str){
        return new String(Base64Utils.decode(str.getBytes()));
    }

    public static String base64Encode(String str){
        return new String(Base64Utils.encode(str.getBytes()));
    }

}
