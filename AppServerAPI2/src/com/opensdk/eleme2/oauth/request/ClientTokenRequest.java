package com.opensdk.eleme2.oauth.request;



import com.opensdk.eleme2.config.Config;
import com.opensdk.eleme2.oauth.BaseOAuthRequest;
import com.opensdk.eleme2.oauth.response.Token;

import java.util.Map;

public class ClientTokenRequest extends BaseOAuthRequest<Token> {

    private Config context;
    public ClientTokenRequest(Config context) {
        this.context = context;
    }

    public Class<Token> getResponseClass() {
        return Token.class;
    }

    public Map<String, String> getHeaderMap()  {
        setAuthorization(context.getApp_key(), context.getApp_secret());
        return super.headerMap;
    }

    public Map<String, String> getBodyMap() {
        putBodyParam("grant_type", "client_credentials");
        return super.bodyMap;
    }

    public Map<String, String> getBodyMap(String appKey) {
        return null;
    }
}
