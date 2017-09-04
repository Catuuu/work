package com.opensdk.meituan.util;

import com.opensdk.eleme.vo.MessageParam;
import com.opensdk.meituan.constants.ErrorEnum;
import com.opensdk.meituan.exception.ApiSysException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenbin on 17/02/05.
 * 签名生成器
 */
public class SignGenerator {

    public static String genSig(String baseUrl) throws ApiSysException{
        String str = null;
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            str = byte2hex(md.digest(baseUrl.getBytes("utf-8")));
        }catch(NoSuchAlgorithmException e){
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }catch (UnsupportedEncodingException e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return str;
    }



    private static String byte2hex(byte[] b) {
        StringBuffer buf = new StringBuffer();
        int i;

        for (int offset=0; offset<b.length; offset++) {
            i = b[offset];
            if(i<0)
                i += 256;
            if(i<16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }

        return buf.toString();
    }

    private static String concatParams(Map<String, String> params2) {
        Object[] key_arr = params2.keySet().toArray();
        Arrays.sort(key_arr);
        String str = "";

        for (Object key : key_arr) {
            if(key.equals("appSecret")||key.equals("sig")){
                continue;
            }
            //String val = params2.get(key);
            String val = null;
            try {
                val = URLDecoder.decode(params2.get(key),"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            str += "&" + key + "=" + val;
        }
        return str.replaceFirst("&", "");
    }

    /**
     * 推送消息sig验证
     * @param pathUrl
     * @param params
     * @param consumerSecret
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String genSig(String pathUrl, Map<String, String> params,
                                String consumerSecret) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        String str = concatParams(params);
        String basedUrl = pathUrl + "?" + str + consumerSecret;
       // String sig = SignGenerator.genSig(basedUrl);

        try {
            return SignGenerator.genSig(basedUrl);
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
        return "";
    }



    /**
     * 进行接口签名验证
     * @param params
     * @param consumerSecret
     * @param url
     * @return
     */
    public static boolean getSigPush(Map params, String consumerSecret,String url){
        String path = "";
        String env = PropertiesUtil.getEnvironmentMode();
        if("0".equals(env)){//0 表示测试环境
            //path = "http://mycaidashi.51vip.biz";
        }else if("1".equals(env)){
            path = "http://shop.caidashi.pro";
            //path = "http://mycaidashi.51vip.biz";
        }
        path += url;
        try {
            if(params.get("sig")==null||params.get("timestamp")==null){
                return false;
            }
            String paramsig = params.get("sig").toString();
            String sig = genSig(path,params,consumerSecret);
            if(paramsig.equals(sig)){
                return true;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return false;

    }
}
