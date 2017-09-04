package com.opensdk.eleme.util;

import com.opensdk.eleme.exception.ApiSysException;

import org.apache.http.client.config.RequestConfig;

/**
 * Created by chenbin on 17/02/05.
 */
public class PropertiesUtil {
    private static int socketTimeOut = 5000;
    private static int connectTimeOut = 3000;
    private static int connectRequestTimeOut = 3000;
    private static String env = "1";//0 表示测试环境 1 表示正式环境

    private static RequestConfig.Builder requestConfigBuilder = null;

    public static RequestConfig.Builder getRequestConfig() throws ApiSysException{
        requestConfigBuilder = RequestConfig.custom();
        requestConfigBuilder.setConnectTimeout(PropertiesUtil.getConnectTimeOut());
        requestConfigBuilder.setConnectionRequestTimeout(PropertiesUtil.getConnectRequestTimeOut());
        requestConfigBuilder.setSocketTimeout(PropertiesUtil.getSocketTimeOut());
        return requestConfigBuilder;
    }

    public static String getEnvironmentMode(){
        return env;
    }

    public static int getSocketTimeOut(){
        return socketTimeOut;
    }

    public static int getConnectTimeOut(){
        return connectTimeOut;
    }

    public static int getConnectRequestTimeOut(){
        return connectRequestTimeOut;
    }




}
