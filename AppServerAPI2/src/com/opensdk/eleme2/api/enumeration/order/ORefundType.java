package com.opensdk.eleme2.api.enumeration.order;

public enum ORefundType {
    /**
     * 全额退单
     */
    normal("normal"), 
    
    /**
     * 部分退款
     */
    part("part");
    

    private String orderDesc;
    ORefundType(String orderDesc) {
        this.orderDesc = orderDesc;
    }
}