package com.opensdk.eleme2.api.enumeration.order;

public enum OOrderDetailGroupType {
    /**
     * 正常的菜品
     */
    normal("normal"), 
    
    /**
     * 配送费等
     */
    extra("extra"), 
    
    /**
     * 赠品
     */
    discount("discount");
    

    private String orderDesc;
    OOrderDetailGroupType(String orderDesc) {
        this.orderDesc = orderDesc;
    }
}