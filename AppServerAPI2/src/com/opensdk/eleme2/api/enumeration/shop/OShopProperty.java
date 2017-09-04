package com.opensdk.eleme2.api.enumeration.shop;

public enum OShopProperty {
    /**
     * 店铺地址
     */
    addressText("addressText"), 
    
    /**
     * 经纬度，longitude和latitude用英文逗号分隔
     */
    geo("geo"), 
    
    /**
     * 配送费
     */
    agentFee("agentFee"), 
    
    /**
     * 关店描述信息
     */
    closeDescription("closeDescription"), 
    
    /**
     * 配送区域说明
     */
    deliverDescription("deliverDescription"), 
    
    /**
     * 配送范围
     */
    deliverGeoJson("deliverGeoJson"), 
    
    /**
     * 店铺简介
     */
    description("description"), 
    
    /**
     * 店铺名称
     */
    name("name"), 
    
    /**
     * 是否接受预定
     */
    isBookable("isBookable"), 
    
    /**
     * 店铺营业时间
     */
    openTime("openTime"), 
    
    /**
     * 店铺联系电话
     */
    phone("phone"), 
    
    /**
     * 店铺公告信息
     */
    promotionInfo("promotionInfo"), 
    
    /**
     * 店铺Logo的图片imageHash
     */
    logoImageHash("logoImageHash"), 
    
    /**
     * 是否支持开发票
     */
    invoice("invoice"), 
    
    /**
     * 支持的最小发票金额
     */
    invoiceMinAmount("invoiceMinAmount"), 
    
    /**
     * 满xx元免配送费
     */
    noAgentFeeTotal("noAgentFeeTotal"), 
    
    /**
     * 是否营业
     */
    isOpen("isOpen"), 
    
    /**
     * 订单打包费
     */
    packingFee("packingFee"), 
    
    /**
     * 餐厅的外部唯一标识
     */
    openId("openId");
    

    private String shopDesc;
    OShopProperty(String shopDesc) {
        this.shopDesc = shopDesc;
    }
}