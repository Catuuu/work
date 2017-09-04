package com.opensdk.udesk.util;



import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by chenbin on 17/02/05.
 * 签名生成器
 */
public class SignGenerator {

    public static String genSig(String baseUrl) {
        String str = null;
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            str = byte2hex(md.digest(baseUrl.getBytes("utf-8")));
        }catch(NoSuchAlgorithmException e){

        }catch (UnsupportedEncodingException e) {

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

    /**
     * 进行接口签名验证
     * @param params
     * @param consumerSecret
     * @return
     */
    public static boolean getSigPush(Map params, String consumerSecret){
        String baseUrl = "";
        if(params.get("sign")==null||params.get("timestamp")==null||params.get("mobile")==null){
            return false;
        }
        String mobile = params.get("mobile").toString();
        String timestamp = params.get("timestamp").toString();
        String request_str = "mobile="+mobile+"&timestamp="+timestamp+"&"+consumerSecret;
        String paramsig = params.get("sign").toString();
        String sig = genSig(request_str);
        if(paramsig.equals(sig)){
            return true;
        }
        return false;
    }
}
