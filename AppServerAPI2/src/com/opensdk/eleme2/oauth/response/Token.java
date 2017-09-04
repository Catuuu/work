package com.opensdk.eleme2.oauth.response;


import com.opensdk.eleme2.oauth.mapping.TokenField;

public class Token extends ErrorResponse {
    @TokenField("access_token")
    private String accessToken;

    @TokenField("token_type")
    private String tokenType;

    @TokenField("expires_in")
    private long expires;

    @TokenField("refresh_token")
    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "Token{" +
                "accessToken='" + accessToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", expires=" + expires +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
