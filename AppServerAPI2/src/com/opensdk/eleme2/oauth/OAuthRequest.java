package com.opensdk.eleme2.oauth;



import com.opensdk.eleme2.oauth.response.ErrorResponse;

import java.util.Map;

/**
 * 请求接口
 */
public interface OAuthRequest<T extends ErrorResponse> {

    public Class<T> getResponseClass() ;

    public Map<String, String> getHeaderMap();

    public Map<String, String> getBodyMap();

}
