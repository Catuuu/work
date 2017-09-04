package com.opensdk.dianwoda.api;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/1.
 */
public class API {
    /* 签名加密 */
    public static String signObj(Map<String, Object> paramValues, String secret) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(secret);

            if (paramValues != null) {
                List<String> paramNames = new ArrayList<String>(paramValues.size());
                paramNames.addAll(paramValues.keySet());
                Collections.sort(paramNames);

                for (String paramName : paramNames) {
                    sb.append(paramName).append(paramValues.get(paramName) == null ? "" : paramValues.get(paramName));
                }
            }

            sb.append(secret);
            byte[] sha1Digest = getSHA1Digest(sb.toString());
            return byte2hex(sha1Digest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static byte[] getSHA1Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            bytes = md.digest(data.getBytes("utf-8"));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
        return bytes;
    }

    /**
     * 二进制转十六进制字符串
     *
     * @param bytes
     * @return
     */
    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }
}
