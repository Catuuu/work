package com.opensdk.eleme2.oauth.parser;


import com.opensdk.eleme2.oauth.response.ErrorResponse;

public interface OAuthParser<T extends ErrorResponse> {

    /**
     * 把响应字符串解释成相应的领域对象。
     *
     * @param rsp 响应字符串
     * @return 领域对象
     */
    public T parse(String rsp) ;

    public Class<T> getResponseClass() ;

}
