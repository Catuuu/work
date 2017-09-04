package com.opensdk.eleme2.oauth;


import com.opensdk.eleme2.oauth.response.ErrorResponse;

public interface IOAuthClient {
    <T extends ErrorResponse> T execute(OAuthRequest<T> request);
}
