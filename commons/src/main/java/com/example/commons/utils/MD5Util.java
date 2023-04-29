package com.example.commons.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @since 2023/4/26 21:56
 * @author by liangzj
 */
public class MD5Util {

    public static String getMd5(String string) {
        if (StringUtil.isBlank(string)) {
            return "";
        }

        try {
            // 获取md5
            MessageDigest md5 = MessageDigest.getInstance("md5");
            md5.update(string.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md5.digest();

            // 转换32位字符串
            StringBuilder builder = new StringBuilder();
            for (byte b : digest) builder.append(getHexString(b));

            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("get string md5 error", e);
        }
    }

    public static String getHexString(byte b) {
        int val = ((int) b) & 0xff;

        return ((val < 16) ? "0" : "") + Integer.toHexString(val);
    }
}
