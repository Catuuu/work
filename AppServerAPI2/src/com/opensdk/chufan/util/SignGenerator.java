package com.opensdk.chufan.util;

import com.framework.util.StringUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Map;

/**
 * 生成厨房点餐的签名
 * Created by Administrator on 2017/4/28.
 */
public class SignGenerator {

    public static String keypassword = "A622625ufdki12yui3"; //签名密钥

    public static boolean isSign(Map<String, String> params) {
        String paramStr = concatParams(params);
        String timestamp = params.get("timestamp");
        String sign_param = params.get("sign");
        String sign = StringUtil.MD5(paramStr + timestamp + keypassword);
        if (sign_param.equals(sign)) {
            return true;
        }
        return false;
    }


    //对参数进行排序生成字符串
    private static String concatParams(Map<String, String> params) {
        Object[] key_arr = params.keySet().toArray();
        Arrays.sort(key_arr);
        String str = "";

        for (Object key : key_arr) {
            if (key.equals("timestamp") || key.equals("sign")) {
                continue;
            }
            String val = null;
            try {
                val = URLDecoder.decode(params.get(key), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            str += "&" + key + "=" + val;
        }
        return str.replaceFirst("&", "");
    }
}
