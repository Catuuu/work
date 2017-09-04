package com.opensdk.eleme2.api.entity.order;

public class OCommodity{

    /**
     * 食物id
     */
    private long id;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    
    /**
     * 用户侧价格
     */
    private double userPrice;
    public double getUserPrice() {
        return userPrice;
    }
    public void setUserPrice(double userPrice) {
        this.userPrice = userPrice;
    }
    
    /**
     * 商户侧价格
     */
    private double shopPrice;
    public double getShopPrice() {
        return shopPrice;
    }
    public void setShopPrice(double shopPrice) {
        this.shopPrice = shopPrice;
    }
    
    /**
     * skuId
     */
    private String skuId;
    public String getSkuId() {
        return skuId;
    }
    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }
    
}