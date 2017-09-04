package com.opensdk.eleme2.oauth;


import com.opensdk.eleme2.config.Constants;
import com.opensdk.eleme2.oauth.response.ErrorResponse;
import com.opensdk.eleme2.utils.Base64;
import com.opensdk.eleme2.utils.EleHashMap;

import java.util.Map;

public abstract class BaseOAuthRequest<T extends ErrorResponse> implements OAuthRequest<T> {

    protected Map<String, String> headerMap; // HTTP请求头参数
    protected Map<String, String> bodyMap; // HTTP Body参数
    protected EleHashMap customParams; // 自定义表单参数

    protected void setAuthorization(String appKey, String appSecret) {
        String headOauthKey = Constants.HEAD_OAUTH_KEY;
        this.putHeaderParam(headOauthKey, this.getBasic(appKey, appSecret));
    }

    public void putHeaderParam(String key, String value) {
        if (headerMap == null) {
            headerMap = new EleHashMap();
        }
        this.headerMap.put(key, value);
    }

    public void putBodyParam(String key, String value) {
        if (bodyMap == null) {
            bodyMap = new EleHashMap();
        }
        this.bodyMap.put(key, value);
    }

    private String getBasic(String appKey, String appSecret) {
        StringBuilder sb = new StringBuilder();
        StringBuilder basicContent = new StringBuilder();
        basicContent.append(appKey).append(":").append(appSecret);
        String encodeToString = Base64.encodeToString(basicContent.toString().getBytes(), false);
        sb.append("Basic").append(" ").append(encodeToString);
        return sb.toString();
    }
}
