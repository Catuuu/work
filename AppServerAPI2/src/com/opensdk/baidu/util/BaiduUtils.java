package com.opensdk.baidu.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensdk.baidu.api.Cmd;
import com.opensdk.baidu.api.CmdSerializer;
import com.opensdk.baidu.api.Shop;
import com.opensdk.baidu.api.ShopSerializer;
import com.opensdk.eleme.constants.ErrorEnum;
import com.opensdk.eleme.util.ConvertUtil;
import com.opensdk.meituan.exception.ApiSysException;
import org.apache.http.HttpEntity;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


public  class BaiduUtils {

    private static CloseableHttpClient httpClient = HttpClients.custom().build();

    //百度账号
    public static final String SOURCE = "64294";

    //百度秘钥
    public static final String SECRET = "570728d16e7a60b4";

    //百度接口地址
    public static final String url = "https://api.waimai.baidu.com";

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

    public static String chinaToUnicode(String str){
        String result="";
        for (int i = 0; i < str.length(); i++){
            int chr1 = (char) str.charAt(i);
            if(chr1>=19968&&chr1<=171941){
                result+="\\u" + Integer.toHexString(chr1);
            }else{
                result+=str.charAt(i);
            }
        }
        return result;
    }

    public static Cmd baiduSend(String method,Object obj){

        Cmd cmd = new Cmd();
        cmd.setCmd(method);
        cmd.setSource(SOURCE);
        cmd.setSecret(SECRET);
        cmd.setTicket(UUID.randomUUID().toString().toUpperCase());
        cmd.setTimestamp(String.valueOf(System.currentTimeMillis() / 1000));
        cmd.setVersion("3");
        cmd.setBody(obj);
        cmd.setSign(null);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Cmd.class, new CmdSerializer())
                .registerTypeAdapter(Shop.class, new ShopSerializer())
                .disableHtmlEscaping()
                .create();
        String signJson = gson.toJson(cmd);


        //获取签名
        signJson = signJson.replace("/", "\\/");
        signJson = chinaToUnicode(signJson);
        System.out.println(signJson);
        HashMap<String,String> map2 = new HashMap<String,String>();
        map2.put("body",cmd.getBody().toString());
        map2.put("cmd",cmd.getCmd());
        map2.put("source",cmd.getSource()+"");
        map2.put("timestamp",cmd.getTimestamp()+"");
        map2.put("ticket",cmd.getTicket());
        map2.put("version",cmd.getVersion()+"");
        String jmqStr = concatParams(map2);
        System.out.println(jmqStr);
        String sign = getMD5(jmqStr);
//        String sign = getMD5(signJson);
        cmd.setSign(sign);
        cmd.setSecret(null);
        String requestJson = gson.toJson(cmd);

        requestJson = requestJson.replace("/", "\\/");

        requestJson = chinaToUnicode(requestJson);


        System.out.println(requestJson);
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("body",cmd.getBody().toString());
        map.put("cmd",cmd.getCmd());
        map.put("source",cmd.getSource()+"");
        map.put("sign",cmd.getSign());
        map.put("timestamp",cmd.getTimestamp()+"");
        map.put("ticket",cmd.getTicket());
        map.put("version",cmd.getVersion()+"");

        return cmd;
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



    /**
     * 请求以POST方式
     *
     * @param method 请求路径
     * @param obj  请求参数
     * @return
     */
    public static String requestOfPost( String method,Object obj) throws ApiSysException {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        Cmd cmd = baiduSend(method,obj);
        try {
            //设置参数
//            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//            List<BasicNameValuePair> nameValuePairList = ConvertUtil.convertToEntity(cmd);
//            nameValuePairs.addAll(nameValuePairList);
//
//            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, Charset.forName("utf-8"));
////            ByteArrayBody byteArray = new ByteArrayBody(fileData, imgName);
////            entity.addPart("file", byteArray);
////
//                URLEncodedUtils.format(nameValuePairs, "UTF-8");
//            Iterator<NameValuePair> it = nameValuePairs.iterator();
//            while (it.hasNext()) {
//                NameValuePair param = it.next();
//                entity.addPart(param.getName(), new StringBody(param.getValue(), Charset.forName("utf8")));
//            }
            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, Charset.forName("utf-8"));
            NameValuePair np = new  BasicNameValuePair("body",cmd.getBody().toString());
            NameValuePair np2 = new  BasicNameValuePair("cmd",cmd.getCmd());
            NameValuePair np8 = new  BasicNameValuePair("secret",SECRET);
            NameValuePair np3 = new  BasicNameValuePair("source",cmd.getSource()+"");
//            NameValuePair np4 = new  BasicNameValuePair("sign",cmd.getSign());
            NameValuePair np6 = new  BasicNameValuePair("ticket",cmd.getBody().toString());
            NameValuePair np5 = new  BasicNameValuePair("timestamp",cmd.getTimestamp()+"");
            NameValuePair np7 = new  BasicNameValuePair("version",cmd.getVersion()+"");
            entity.addPart(np.getName(),new StringBody(np.getValue(), Charset.forName("utf8")));
            entity.addPart(np2.getName(),new StringBody(np2.getValue(), Charset.forName("utf8")));
            entity.addPart(np8.getName(),new StringBody(np8.getValue(), Charset.forName("utf8")));
            entity.addPart(np3.getName(),new StringBody(np3.getValue(), Charset.forName("utf8")));
//            entity.addPart(np4.getName(),new StringBody(np4.getValue(), Charset.forName("utf8")));
            entity.addPart(np5.getName(),new StringBody(np5.getValue(), Charset.forName("utf8")));
            entity.addPart(np6.getName(),new StringBody(np6.getValue(), Charset.forName("utf8")));
            entity.addPart(np7.getName(),new StringBody(np7.getValue(), Charset.forName("utf8")));
//            httpPost.addHeader("Content-type","application/x-www-form-urlencoded; charset=utf-8");
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            return EntityUtils.toString(responseEntity, "UTF-8");
        } catch (Exception e) {
            throw new ApiSysException(e);
        } finally {
            httpPost.releaseConnection();
            try {
                if (response != null) {
                    response.close();
                } else {
                    throw new ApiSysException("请检查environment.properties文件是否配置正确");
                }
            } catch (IOException e) {
                throw new ApiSysException(e);
            }
        }

    }


    /**
     * 对象转化为参数列表
     * @param applicationParamsMap
     * @return
     */
    public static List<BasicNameValuePair> convertToEntity(Map<String,String> applicationParamsMap)
            throws com.opensdk.eleme.exception.ApiSysException {
        List<BasicNameValuePair> formParam = new ArrayList<BasicNameValuePair>();
        try{
            if(applicationParamsMap != null){
                Iterator<Map.Entry<String, String>> iterator = applicationParamsMap.entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry<String, String> entry = iterator.next();
                    if(entry.getValue() != null && !"".equals(entry.getValue()) &&
                            !"null".equals(entry.getValue()) && !"NULL".equals(entry.getValue())) {
                        BasicNameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
                        formParam.add(nameValuePair);
                    }
                }
            }
        }catch (Exception e){
            throw new com.opensdk.eleme.exception.ApiSysException(ErrorEnum.SYS_ERR);
        }

        return formParam;
    }



}
