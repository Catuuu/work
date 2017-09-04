package com.opensdk.eleme.util;

import com.alibaba.fastjson.JSONObject;
import com.framework.mapping.system.RequestFormLog;
import com.framework.util.WebUtil;
import com.opensdk.eleme.constants.ErrorEnum;
import com.opensdk.eleme.constants.RequestMethodTypeEnum;
import com.opensdk.eleme.exception.ApiOpException;
import com.opensdk.eleme.exception.ApiSysException;
import com.opensdk.eleme.factory.URLFactoryEleme;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
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

    public static String request(String urlHasParamsNoSig, String sig, Map<String, String> systemParamsMap,
                                 Map<String, String> applicationParamsMap, String requestMethodType,
                                 RequestConfig.Builder requestConfigBuilder)
            throws ApiSysException {
        long startTime = System.currentTimeMillis();
        String url = "";
        String path = "";
        String resultStr = "";
        if (RequestMethodTypeEnum.POST.getCode().equals(requestMethodType)) {
            url = urlHasParamsNoSig.substring(0, urlHasParamsNoSig.indexOf("?"));
            path = URLFactoryEleme.genOnlyHasSysParamsAndSigUrl(url, systemParamsMap, sig);
            resultStr = requestOfPost(path, applicationParamsMap, requestConfigBuilder);
        } else if (RequestMethodTypeEnum.PUT.getCode().equals(requestMethodType)) {
            url = urlHasParamsNoSig.substring(0, urlHasParamsNoSig.indexOf("?"));
            path = URLFactoryEleme.genOnlyHasSysParamsAndSigUrl(url, systemParamsMap, sig);
            resultStr = requestOfPut(path, applicationParamsMap, requestConfigBuilder);
        } else if (RequestMethodTypeEnum.DELETE.getCode().equals(requestMethodType)) {
            url = urlHasParamsNoSig.substring(0, urlHasParamsNoSig.indexOf("?"));
            path = URLFactoryEleme.genOnlyHasSysParamsAndSigUrl(url, systemParamsMap, sig);
            resultStr = requestOfDelete(path, applicationParamsMap, requestConfigBuilder);
        } else {
            url = urlHasParamsNoSig.substring(0, urlHasParamsNoSig.indexOf("?"));
            path = URLFactoryEleme.genUrlForGetRequest(urlHasParamsNoSig, sig);
            resultStr = requestOfGet(path, requestConfigBuilder);
        }

        try {
            long endTime = System.currentTimeMillis();
            long executeTime = endTime - startTime;

            RequestFormLog requestElemeLog = new RequestFormLog();
            requestElemeLog.setElemeLogKey(com.framework.util.StringUtil.getPrimaryKey());
            if (WebUtil.getRequest() != null && WebUtil.getRequest("request_primary_key") != null) {
                requestElemeLog.setRequestLogKey(WebUtil.getRequest("request_primary_key"));
            }
            requestElemeLog.setForm("饿了么");
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
        } catch (Exception e) {
        }

        return resultStr;
    }

    public static String request(String urlHasParamsNoSig, String sig, Map<String, String> systemParamsMap,
                                 Map<String, String> applicationParamsMap, byte[] fileData, String imgName, String requestMethodType,
                                 RequestConfig.Builder requestConfigBuilder) throws ApiSysException {
        long startTime = System.currentTimeMillis();
        String url = urlHasParamsNoSig.substring(0, urlHasParamsNoSig.indexOf("?"));
        String path = "";
        String resultStr = "";

        if (RequestMethodTypeEnum.POST.getCode().equals(requestMethodType)) {

            path = URLFactoryEleme.genUrlForGetRequest(urlHasParamsNoSig, sig);
            resultStr = requestOfPost(path, applicationParamsMap, fileData, imgName, requestConfigBuilder);
        } else if (RequestMethodTypeEnum.PUT.getCode().equals(requestMethodType)) {
            path = URLFactoryEleme.genUrlForGetRequest(urlHasParamsNoSig, sig);
            resultStr = requestOfPut(path, applicationParamsMap, requestConfigBuilder);
        } else if (RequestMethodTypeEnum.DELETE.getCode().equals(requestMethodType)) {
            path = URLFactoryEleme.genUrlForGetRequest(urlHasParamsNoSig, sig);
            resultStr = requestOfDelete(path, applicationParamsMap, requestConfigBuilder);
        } else {
            path = URLFactoryEleme.genUrlForGetRequest(urlHasParamsNoSig, sig);
            resultStr = requestOfGet(path, requestConfigBuilder);
        }

        if (RequestMethodTypeEnum.POST.getCode().equals(requestMethodType)) {
            url = urlHasParamsNoSig.substring(0, urlHasParamsNoSig.indexOf("?"));
            path = URLFactoryEleme.genOnlyHasSysParamsAndSigUrl(url, systemParamsMap, sig);
            resultStr = requestOfPost(path, applicationParamsMap, requestConfigBuilder);
        } else if (RequestMethodTypeEnum.PUT.getCode().equals(requestMethodType)) {
            url = urlHasParamsNoSig.substring(0, urlHasParamsNoSig.indexOf("?"));
            path = URLFactoryEleme.genOnlyHasSysParamsAndSigUrl(url, systemParamsMap, sig);
            resultStr = requestOfPut(path, applicationParamsMap, requestConfigBuilder);
        } else if (RequestMethodTypeEnum.DELETE.getCode().equals(requestMethodType)) {
            url = urlHasParamsNoSig.substring(0, urlHasParamsNoSig.indexOf("?"));
            path = URLFactoryEleme.genOnlyHasSysParamsAndSigUrl(url, systemParamsMap, sig);
            resultStr = requestOfDelete(path, applicationParamsMap, requestConfigBuilder);
        } else {
            url = urlHasParamsNoSig.substring(0, urlHasParamsNoSig.indexOf("?"));
            path = URLFactoryEleme.genUrlForGetRequest(urlHasParamsNoSig, sig);
            resultStr = requestOfGet(path, requestConfigBuilder);
        }

        try {
            long endTime = System.currentTimeMillis();
            long executeTime = endTime - startTime;

            RequestFormLog requestElemeLog = new RequestFormLog();
            requestElemeLog.setElemeLogKey(com.framework.util.StringUtil.getPrimaryKey());
            if (WebUtil.getRequest() != null && WebUtil.getRequest("request_primary_key") != null) {
                requestElemeLog.setRequestLogKey(WebUtil.getRequest("request_primary_key"));
            }
            requestElemeLog.setForm("饿了么");
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
        } catch (Exception e) {
        }

        return resultStr;
    }

    /**
     * 请求以POST方式
     *
     * @param url                  饿了么的接口url
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
     * @param url                  饿了么的接口url
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


    /**
     * 请求以POST方式
     *
     * @param url                  美团的接口url
     * @param applicationParamsMap 参数列表
     * @return
     */
    private static String requestOfPut(String url, Map<String, String> applicationParamsMap, RequestConfig.Builder requestConfigBuilder)
            throws ApiSysException {
        HttpPut httpPut = new HttpPut(url);
        CloseableHttpResponse response = null;
        try {
            //设置参数
            List<BasicNameValuePair> nameValuePairList = ConvertUtil.convertToEntity(applicationParamsMap);
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nameValuePairList, "UTF-8");
            httpPut.setConfig(requestConfigBuilder.build());
            httpPut.setEntity(uefEntity);
            response = httpClient.execute(httpPut);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            throw new ApiSysException(e);
        } finally {
            httpPut.releaseConnection();
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
     * 请求以Delete方式
     *
     * @param url                  饿了么的接口url
     * @param applicationParamsMap 参数列表
     * @return
     */
    private static String requestOfDelete(String url, Map<String, String> applicationParamsMap, RequestConfig.Builder requestConfigBuilder)
            throws ApiSysException {
        HttpDelete httpDelete = new HttpDelete(url);
        CloseableHttpResponse response = null;
        try {
            //设置参数
            List<BasicNameValuePair> nameValuePairList = ConvertUtil.convertToEntity(applicationParamsMap);
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nameValuePairList, "UTF-8");
            httpDelete.setConfig(requestConfigBuilder.build());
//            httpDelete.setEntity(uefEntity);
            response = httpClient.execute(httpDelete);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            throw new ApiSysException(e);
        } finally {
            httpDelete.releaseConnection();
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
        if (resultObj == null) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        if (resultObj.get("data") == null) {
            return resultObj.toJSONString();
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
