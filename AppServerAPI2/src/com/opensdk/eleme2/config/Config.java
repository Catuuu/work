package com.opensdk.eleme2.config;


import com.opensdk.eleme2.utils.StringUtils;

public class Config {

    private String app_key;
    private String app_secret;
    private String oauthCodeUrl;
    private String oauthTokenUrl;
    private String apiUrl;
    private ElemeSdkLogger elemeSdkLogger;

    public Config(boolean isSandbox, String appKey, String appSecret) {
        if (StringUtils.areNotEmpty(appKey, appKey)) {
            System.out.println("init Config ...");
        } else {
            System.out.println("appKey and appSecret is required.");
        }
        app_key = appKey;
        app_secret = appSecret;
        if (isSandbox) {
            oauthCodeUrl = BasicURL.OAuth.SANDBOX_AUTHORIZE;
            oauthTokenUrl = BasicURL.OAuth.SANDBOX_TOKEN;
            apiUrl = BasicURL.OpenApi.SANDBOX_Api;
        } else {
            oauthCodeUrl = BasicURL.OAuth.PRODUCTION_AUTHORIZE;
            oauthTokenUrl = BasicURL.OAuth.PRODUCTION_TOKEN;
            apiUrl = BasicURL.OpenApi.PRODUCTION_Api;
        }
    }

    public void setLog(ElemeSdkLogger elemeSdkLogger) {
        this.elemeSdkLogger = elemeSdkLogger;
    }

    public ElemeSdkLogger getElemeSdkLogger() {
        return elemeSdkLogger;
    }

    public  void setOauthCodeUrl(String oauthCodeUrl) {
        this.oauthCodeUrl = oauthCodeUrl;
    }

    public  void setOauthTokenUrl(String oauthTokenUrl) {
        this.oauthTokenUrl = oauthTokenUrl;
    }

    public  void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public  String getOauthCodeUrl() {
        return oauthCodeUrl;
    }

    public  String getOauthTokenUrl() {
        return oauthTokenUrl;
    }

    public  String getApiUrl() {
        return apiUrl;
    }

    public  String getApp_key() {
        return app_key;
    }

    public  String getApp_secret() {
        return app_secret;
    }

}
