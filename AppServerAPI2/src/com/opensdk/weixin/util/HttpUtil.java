package com.opensdk.weixin.util;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/2/17.
 */
public class HttpUtil {

    private static CloseableHttpClient httpClient = HttpClients.custom().build();

    /**
     * post请求
     * @param url   地址
     * @param params 参数
     * @return
     */
    public static String postRequest(String url,Map<String,String> params){

        HttpPost httpPost = new HttpPost(url);

        httpPost.addHeader("Content-type", "application/x-www-form-urlencoded");
        CloseableHttpResponse response = null;

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Set<String> keySet = params.keySet();
        for(String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
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

    public static String getRequest(String url){
        // 根据地址获取请求
        HttpGet httpGet = new HttpGet(url);//这里发送get请求
        // 获取当前客户端对象
        //HttpClient httpClient = new DefaultHttpClient();
        // 通过请求对象获取响应对象
        CloseableHttpResponse response = null;
        String result = "";
        try {
            response = httpClient.execute(httpGet);
            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result= EntityUtils.toString(response.getEntity(),"utf-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            httpGet.releaseConnection();
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

    public static String postRequest(String access_token,String postJson){
        Header jsonHeader = new BasicHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
        HttpUriRequest httpUriRequest = RequestBuilder
                .post()
                .setHeader(jsonHeader)
                .setUri("https://api.weixin.qq.com/cgi-bin/qrcode/create")
                .addParameter("access_token", access_token)
                .setEntity(new StringEntity(postJson, Charset.forName("utf-8")))
                .build();
        //HttpClient httpClient = new DefaultHttpClient();
        // 通过请求对象获取响应对象
        CloseableHttpResponse response = null;
        String result = "";
        try {
            response = httpClient.execute(httpUriRequest);
            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result= EntityUtils.toString(response.getEntity(),"utf-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
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

    public static String postRequest1(String access_token,String postJson){
        Header jsonHeader = new BasicHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
        HttpUriRequest httpUriRequest = RequestBuilder
                .post()
                .setHeader(jsonHeader)
                .setUri("https://api.weixin.qq.com/cgi-bin/message/template/send")
                .addParameter("access_token", access_token)
                .setEntity(new StringEntity(postJson, Charset.forName("utf-8")))
                .build();
        //HttpClient httpClient = new DefaultHttpClient();
        // 通过请求对象获取响应对象
        CloseableHttpResponse response = null;
        String result = "";
        try {
            response = httpClient.execute(httpUriRequest);
            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result= EntityUtils.toString(response.getEntity(),"utf-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
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
}
