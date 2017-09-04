package com.opensdk.baidu.util;

import com.alibaba.fastjson.JSONObject;
import com.framework.mapping.system.RequestDianwoDaLog;
import com.framework.util.WebUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensdk.baidu.api.Cmd;
import com.opensdk.baidu.api.CmdSerializer;
import com.opensdk.baidu.api.Shop;
import com.opensdk.baidu.api.ShopSerializer;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static com.opensdk.baidu.util.BaiduUtils.chinaToUnicode;
import static com.opensdk.baidu.util.BaiduUtils.getMD5;

/**
 * Created by Administrator on 2017/9/4.
 */
public class HttpUtils {

    //百度账号
    public static final String SOURCE = "64294";

    //百度秘钥
    public static final String SECRET = "570728d16e7a60b4";

    //百度接口地址
    public static final String url = "https://api.waimai.baidu.com";

    private static CloseableHttpClient httpClient = HttpClients.custom().build();

    /**
     * post请求
     * @param method   地址
     * @param obj 参数
     * @return
     */
    public static String postRequest(String method,Object obj){

        long startTime = System.currentTimeMillis();

        Cmd cmd = new Cmd();
        cmd.setCmd(method);
        cmd.setSource(SOURCE);
        cmd.setSecret(SECRET);
        cmd.setTicket(UUID.randomUUID().toString().toUpperCase());
        cmd.setTimestamp(String.valueOf(System.currentTimeMillis() / 1000));
        cmd.setVersion("2");
        cmd.setBody(obj);
        cmd.setSign(null);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Cmd.class, new CmdSerializer())
                .registerTypeAdapter(Shop.class, new ShopSerializer())
                .disableHtmlEscaping()
                .create();
        String signJson = gson.toJson(cmd);
        signJson = signJson.replace("/", "\\/");

        signJson = chinaToUnicode(signJson);
        System.out.println(signJson);
        String sign = getMD5(signJson);

        cmd.setSign(sign);
        cmd.setSecret(null);
        String requestJson = gson.toJson(cmd);

        requestJson = requestJson.replace("/", "\\/");

        requestJson = chinaToUnicode(requestJson);

        System.out.println(requestJson);
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-type", "application/x-www-form-urlencoded");
        CloseableHttpResponse response = null;

        HashMap<String,String> params = new HashMap<String,String>();
        params.put("body",cmd.getBody().toString());
        params.put("cmd",cmd.getCmd());
        params.put("source",cmd.getSource());
        params.put("sign",cmd.getSign());
        params.put("timestamp",cmd.getTimestamp());
        params.put("ticket",cmd.getTicket());
        params.put("version",cmd.getVersion());
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Set<String> keySet = params.keySet();
        for(String key : keySet) {
            if(params.get(key)==null){
                nvps.add(new BasicNameValuePair(key, ""));
            }else {
                nvps.add(new BasicNameValuePair(key, params.get(key).toString()));
            }

        }
        String result = "";
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            httpPost.releaseConnection();
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * ����MD5
     * @param input
     * @return
     */
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext.toUpperCase();
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ������ת��Unicode��
     * @param str
     * @return
     */
    public static String chinaToUnicode(String str){
        String result="";
        for (int i = 0; i < str.length(); i++){
            int chr1 = (char) str.charAt(i);
            if(chr1>=19968&&chr1<=171941){//���ַ�Χ \u4e00-\u9fa5 (����)
                result+="\\u" + Integer.toHexString(chr1);
            }else{
                result+=str.charAt(i);
            }
        }
        return result;
    }

}
