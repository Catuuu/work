package com.opensdk.meituan.util;

import com.alibaba.fastjson.JSONObject;
import com.framework.mapping.system.RequestFormLog;
import com.framework.util.WebUtil;
import com.opensdk.meituan.constants.ErrorEnum;
import com.opensdk.meituan.constants.RequestMethodTypeEnum;
import com.opensdk.meituan.exception.ApiOpException;
import com.opensdk.meituan.exception.ApiSysException;
import com.opensdk.meituan.factory.URLFactoryMeituan;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by chenbin on 17/02/05.
 */
public class HttpUtil {

    private static CloseableHttpClient httpClient = HttpClients.custom().build();

    public static String request(String urlPrefix, String urlHasParamsNoSig, String sig, Map<String, String> systemParamsMap,
                                 Map<String, String> applicationParamsMap, String requestMethodType,
                                 RequestConfig.Builder requestConfigBuilder)
            throws ApiSysException {
        long startTime = System.currentTimeMillis();
        String url = urlHasParamsNoSig.substring(0, urlHasParamsNoSig.indexOf("?"));
        String path = "";
        String resultStr = "";

        if (RequestMethodTypeEnum.POST.getCode().equals(requestMethodType)) {
            path = URLFactoryMeituan.genOnlyHasSysParamsAndSigUrl(urlPrefix, systemParamsMap, sig);
            resultStr = requestOfPost(path, applicationParamsMap, requestConfigBuilder);
        } else {
            path = URLFactoryMeituan.genUrlForGetRequest(urlHasParamsNoSig, sig);
            resultStr = requestOfGet(path, requestConfigBuilder);
        }
        try {
            long endTime = System.currentTimeMillis();
            long executeTime = endTime - startTime;

            RequestFormLog requestElemeLog = new RequestFormLog();
            requestElemeLog.setForm("美团");
            requestElemeLog.setElemeLogKey(com.framework.util.StringUtil.getPrimaryKey());
            if (WebUtil.getRequest() != null && WebUtil.getRequest("request_primary_key") != null) {
                requestElemeLog.setRequestLogKey(WebUtil.getRequest("request_primary_key"));
            }
            requestElemeLog.setUrlId(url);
            requestElemeLog.setPath(path);
            requestElemeLog.setSig(sig);
            requestElemeLog.setSystemParamsMap(systemParamsMap);
            requestElemeLog.setApplicationParamsMap(applicationParamsMap);
            requestElemeLog.setRequestMethodType(requestMethodType);
            requestElemeLog.setStartTime(new Date(startTime));
            requestElemeLog.setEndTime(new Date(endTime));
            requestElemeLog.setExecuteTime(executeTime);
            requestElemeLog.setReturnValue(resultStr);
            //发送日志信息
            WebUtil.getJmsTemplate().send("request.form.log", new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage(JSONObject.toJSONString(requestElemeLog));
                }
            });
        }catch (Exception e){}
        return resultStr;
    }

    public static String request(String urlPrefix, String urlHasParamsNoSig, String sig, Map<String, String> systemParamsMap,
                                 Map<String, String> applicationParamsMap, byte[] fileData, String imgName, String requestMethodType,
                                 RequestConfig.Builder requestConfigBuilder) throws ApiSysException {
        long startTime = System.currentTimeMillis();
        String url = urlHasParamsNoSig.substring(0, urlHasParamsNoSig.indexOf("?"));
        String path = "";
        String resultStr = "";

        if (RequestMethodTypeEnum.POST.getCode().equals(requestMethodType)) {
            path = URLFactoryMeituan.genOnlyHasSysParamsAndSigUrl(urlPrefix, systemParamsMap, sig);
            resultStr = requestOfPost(path, applicationParamsMap, fileData, imgName, requestConfigBuilder);
        } else {
            path = URLFactoryMeituan.genUrlForGetRequest(urlHasParamsNoSig, sig);
            resultStr = requestOfGet(path, requestConfigBuilder);
        }
        try {
            long endTime = System.currentTimeMillis();
            long executeTime = endTime - startTime;

            RequestFormLog requestElemeLog = new RequestFormLog();
            requestElemeLog.setForm("美团");
            requestElemeLog.setElemeLogKey(com.framework.util.StringUtil.getPrimaryKey());
            if (WebUtil.getRequest() != null && WebUtil.getRequest("request_primary_key") != null) {
                requestElemeLog.setRequestLogKey(WebUtil.getRequest("request_primary_key"));
            }
            requestElemeLog.setUrlId(url);
            requestElemeLog.setPath(path);
            requestElemeLog.setSig(sig);
            requestElemeLog.setSystemParamsMap(systemParamsMap);
            requestElemeLog.setApplicationParamsMap(applicationParamsMap);
            requestElemeLog.setRequestMethodType(requestMethodType);
            requestElemeLog.setStartTime(new Date(startTime));
            requestElemeLog.setEndTime(new Date(endTime));
            requestElemeLog.setExecuteTime(executeTime);
            requestElemeLog.setReturnValue(resultStr);
            //发送日志信息
            WebUtil.getJmsTemplate().send("request.form.log", new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage(JSONObject.toJSONString(requestElemeLog));
                }
            });
        }catch (Exception e){}
        return resultStr;
    }

    /**
     * 请求以POST方式
     *
     * @param url                  美团的接口url
     * @param applicationParamsMap 参数列表
     * @return
     */
    private static String requestOfPost(String url, Map<String, String> applicationParamsMap, RequestConfig.Builder requestConfigBuilder)
            throws ApiSysException {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            //设置参数
            List<BasicNameValuePair> nameValuePairList = ConvertUtil.convertToEntity(applicationParamsMap);
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nameValuePairList, "UTF-8");
            httpPost.setConfig(requestConfigBuilder.build());
            httpPost.setEntity(uefEntity);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, "UTF-8");
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
     * 请求以POST方式
     *
     * @param url                  美团的接口url
     * @param applicationParamsMap 参数列表
     * @return
     */
    private static String requestOfPost(String url, Map<String, String> applicationParamsMap, byte[] fileData,
                                        String imgName, RequestConfig.Builder requestConfigBuilder) throws ApiSysException {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            //设置参数
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            List<BasicNameValuePair> nameValuePairList = ConvertUtil.convertToEntity(applicationParamsMap);
            nameValuePairs.addAll(nameValuePairList);

            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, Charset.forName("utf-8"));
            ByteArrayBody byteArray = new ByteArrayBody(fileData, imgName);
            entity.addPart("file", byteArray);

            URLEncodedUtils.format(nameValuePairs, "UTF-8");
            Iterator<NameValuePair> it = nameValuePairs.iterator();
            while (it.hasNext()) {
                NameValuePair param = it.next();
                entity.addPart(param.getName(), new StringBody(param.getValue(), Charset.forName("utf8")));
            }

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
     * 请求以GET方式
     *
     * @param url
     * @return
     */
    private static String requestOfGet(String url, RequestConfig.Builder requestConfigBuilder) throws ApiSysException {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfigBuilder.build());
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            throw new ApiSysException(e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                } else {
                    throw new ApiSysException("请检查environment.properties文件是否配置正确");
                }
                httpGet.releaseConnection();
                response.close();
            } catch (IOException e) {
                throw new ApiSysException(e);
            }
        }

    }

    public static String httpResultHandler(String httpResult) throws ApiOpException, ApiSysException {
        if (httpResult == null || httpResult.equals("")) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        JSONObject resultObj = null;
        try {
            resultObj = JSONObject.parseObject(httpResult);
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        if (resultObj == null || resultObj.get("data") == null) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }

        String dataStr = resultObj.get("data").toString();
        if (dataStr.equals("ng") || dataStr.equalsIgnoreCase("null")) {
            Object errObject = resultObj.get("error");
            if (errObject == null || errObject.toString().equals("")
                    || errObject.toString().equalsIgnoreCase("null")) {
                throw new ApiSysException(ErrorEnum.SYS_ERR);
            }

            JSONObject errJsonObject = null;
            try {
                errJsonObject = JSONObject.parseObject(errObject.toString());
            } catch (Exception e) {
                throw new ApiSysException(ErrorEnum.SYS_ERR);
            }
            if (errJsonObject == null || errJsonObject.get("code") == null || errJsonObject.get("code").equals("")
                    || errJsonObject.get("code").toString().equalsIgnoreCase("null")) {
                throw new ApiSysException(ErrorEnum.SYS_ERR);
            }

            Integer errorCode = null;
            try {
                errorCode = Integer.parseInt(errJsonObject.get("code").toString());
            } catch (Exception e) {
                throw new ApiSysException(ErrorEnum.SYS_ERR);
            }
            if (errorCode == null) {
                throw new ApiSysException(ErrorEnum.SYS_ERR);
            } else {
                if (errJsonObject.get("msg") == null || errJsonObject.get("msg").equals("")
                        || errJsonObject.get("msg").toString().equalsIgnoreCase("null")) {
                    throw new ApiSysException(ErrorEnum.SYS_ERR);
                } else {
                    String errorMsg = errJsonObject.get("msg").toString();
                    throw new ApiOpException(errorCode.intValue(), errorMsg);
                }

            }
        }
        return dataStr;
    }
}
