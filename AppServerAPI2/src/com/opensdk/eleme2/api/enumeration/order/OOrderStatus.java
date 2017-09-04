package com.opensdk.eleme2.api.enumeration.order;

public enum OOrderStatus {
    /**
     * 未生效订单
     */
    pending("pending"), 
    
    /**
     * 未处理订单
     */
    unprocessed("unprocessed"), 
    
    /**
     * 退单处理中
     */
    refunding("refunding"), 
    
    /**
     * 已处理的有效订单
     */
    valid("valid"), 
    
    /**
     * 无效订单
     */
    invalid("invalid"), 
    
    /**
     * 已完成订单
     */
    settled("settled");
    

    private String orderDesc;
    OOrderStatus(String orderDesc) {
        this.orderDesc = orderDesc;
    }
}