package com.opensdk.eleme2.api.enumeration.product;

public enum OItemWeekEnum {
    /**
     * 周一
     */
    MONDAY("MONDAY"), 
    
    /**
     * 周二
     */
    TUESDAY("TUESDAY"), 
    
    /**
     * 周三
     */
    WEDNESDAY("WEDNESDAY"), 
    
    /**
     * 周四
     */
    THURSDAY("THURSDAY"), 
    
    /**
     * 周五
     */
    FRIDAY("FRIDAY"), 
    
    /**
     * 周六
     */
    SATURDAY("SATURDAY"), 
    
    /**
     * 周日
     */
    SUNDAY("SUNDAY");
    

    private String productDesc;
    OItemWeekEnum(String productDesc) {
        this.productDesc = productDesc;
    }
}