package com.opensdk.eleme2.api.entity.user;

import java.util.*;

public class OUser{

    /**
     * 商户Id
     */
    private long userId;
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    
    /**
     * 商户账号名称
     */
    private String userName;
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    /**
     * 授权的店铺列表
     */
    private List<OAuthorizedShop> authorizedShops;
    public List<OAuthorizedShop> getAuthorizedShops() {
        return authorizedShops;
    }
    public void setAuthorizedShops(List<OAuthorizedShop> authorizedShops) {
        this.authorizedShops = authorizedShops;
    }
    
}