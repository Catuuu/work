package com.opensdk.weixin.vo;

/**
 * Created by chenbin on 17/02/05.
 * 每个接口都需要传入的参数
 */
public class SystemParamWeixin {

    private String appId;
    private String appSecret;

    public SystemParamWeixin(String appId, String appSecret){
        this.setAppId(appId);
        this.setAppSecret(appSecret);
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
