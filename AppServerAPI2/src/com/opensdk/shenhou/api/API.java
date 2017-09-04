package com.opensdk.shenhou.api;

import com.framework.system.SystemConfig;
import com.framework.util.StringUtil;
import com.framework.util.WebUtil;
import com.opensdk.shenhou.util.HttpUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/17.
 */
public class API {

    private static String concatParams(Map params2){
        Object[] key_arr = params2.keySet().toArray();
        Arrays.sort(key_arr);
        String str = "";

        for (Object key : key_arr) {
            if(key.equals("appSecret")){
                continue;
            }
            String val = params2.get(key).toString();
            str += "&" + key + "=" + val;
        }
        return str.replaceFirst("&", "");
    }

    private static Map buildParams(Map params2){
        Map map = new HashMap();
        Object[] key_arr = params2.keySet().toArray();
        for (Object key : key_arr) {
            if(key.equals("appSecret") ){
                continue;
            }
            String val = null;
            val = params2.get(key).toString();

            map.put(key,val);
        }
        return map;
    }

    protected String requestpost(String url,Map params){
        params.put("app_key", SystemConfig.GetSystemParamShenhou().getApp_key());
        params.put("timestamp",String.valueOf(System.currentTimeMillis() / 1000));


        String str = concatParams(params);
        String basedUrl = url+ "?" + str + SystemConfig.GetSystemParamShenhou().getApp_secret();

        String sing = StringUtil.MD5(basedUrl);

        try {
            params.put("sign", URLEncoder.encode(sing,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map postBody = buildParams(params);
        String result = HttpUtil.postRequest(url,postBody);
        return result;
    }
}
