package com.opensdk.eleme2.api.enumeration.order;

public enum OOrderRefundStatus {
    /**
     * 未申请退单
     */
    noRefund("noRefund"), 
    
    /**
     * 用户申请退单
     */
    applied("applied"), 
    
    /**
     * 店铺拒绝退单
     */
    rejected("rejected"), 
    
    /**
     * 客服仲裁中
     */
    arbitrating("arbitrating"), 
    
    /**
     * 退单失败
     */
    failed("failed"), 
    
    /**
     * 退单成功
     */
    successful("successful");
    

    private String orderDesc;
    OOrderRefundStatus(String orderDesc) {
        this.orderDesc = orderDesc;
    }
}