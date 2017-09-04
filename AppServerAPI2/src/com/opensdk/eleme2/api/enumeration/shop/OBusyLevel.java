package com.opensdk.eleme2.api.enumeration.shop;

public enum OBusyLevel {
    /**
     * 未知状态
     */
    others("others"), 
    
    /**
     * 店铺正常营业
     */
    busyLevelFree("busyLevelFree"), 
    
    /**
     * 店铺休息中
     */
    busyLevelClosed("busyLevelClosed"), 
    
    /**
     * 店铺网络不稳定/电话订餐
     */
    busyLevelNetworkUnstable("busyLevelNetworkUnstable"), 
    
    /**
     * 店铺休假中
     */
    busyLevelHoliday("busyLevelHoliday");
    

    private String shopDesc;
    OBusyLevel(String shopDesc) {
        this.shopDesc = shopDesc;
    }
}