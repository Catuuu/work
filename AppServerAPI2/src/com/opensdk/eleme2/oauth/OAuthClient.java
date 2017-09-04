package com.opensdk.eleme2.oauth;


import com.opensdk.eleme2.config.Config;
import com.opensdk.eleme2.oauth.impl.DefaultIOAuthClient;
import com.opensdk.eleme2.oauth.impl.ServerOAuthCodeImpl;
import com.opensdk.eleme2.oauth.request.ClientTokenRequest;
import com.opensdk.eleme2.oauth.request.ServerRefreshTokenRequest;
import com.opensdk.eleme2.oauth.request.ServerTokenRequest;
import com.opensdk.eleme2.oauth.response.Token;

public class OAuthClient {

    private Config config;
    public OAuthClient(Config config){
        this.config = config;
    }

    private IOAuthClient ioAuthClient = null;

    /**
     * 客户端授权模式获取Token
     *
     * @return Token信息
     */
    public Token getTokenInClientCredentials() {
        ioAuthClient = new DefaultIOAuthClient(config);
        ClientTokenRequest oAuthRequest = new ClientTokenRequest(config);
        Token token = ioAuthClient.execute(oAuthRequest);
        return token;
    }

    /**
     * 构造授权URL
     *
     * @param redirect_uri 重定向地址
     * @param scope        申请的权限范围
     * @param state        客户端当前状态
     * @return 授权URL
     */
    public String getAuthUrl(String redirect_uri, String scope, String state){
        ServerOAuthCodeImpl serverOAuthCode = new ServerOAuthCodeImpl(
                config.getOauthCodeUrl(),
                config.getApp_key());
        return serverOAuthCode.getAuthUrl(redirect_uri, scope, state);
    }

    /**
     * 授权码模式获取Token
     *
     * @param authCode     授权码
     * @param redirect_uri 重定向地址
     * @return Token信息
     */
    public Token getTokenByCode(String authCode, String redirect_uri) {
        ioAuthClient = new DefaultIOAuthClient(config);
        ServerTokenRequest serverTokenRequest = new ServerTokenRequest(config);
        serverTokenRequest.setCode(authCode);
        serverTokenRequest.setRedirectUri(redirect_uri);
        Token token = ioAuthClient.execute(serverTokenRequest);
        return token;
    }

    /**
     * 刷新Token
     *
     * @param refreshToken refreshToken
     * @return Token信息
     */
    public Token getTokenByRefreshToken(String refreshToken) {
        ioAuthClient = new DefaultIOAuthClient(config);
        ServerRefreshTokenRequest refreshTokenRequest = new ServerRefreshTokenRequest(config);
        refreshTokenRequest.setRefreshToken(refreshToken);
        Token token = ioAuthClient.execute(refreshTokenRequest);
        return token;
    }
}
