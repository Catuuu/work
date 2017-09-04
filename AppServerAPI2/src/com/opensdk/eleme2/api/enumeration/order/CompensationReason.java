package com.opensdk.eleme2.api.enumeration.order;

public enum CompensationReason {
    /**
     * 取餐超时，导致用户取消
     */
    TAKE_ORDER_TIMEOUT("TAKE_ORDER_TIMEOUT"), 
    
    /**
     * 取餐后未完成配送，导致用户取消
     */
    DELIVERY_NOT_FINISHED("DELIVERY_NOT_FINISHED"), 
    
    /**
     * 骑手虚假完成配送，导致用户退单
     */
    DRIVER_SUBMIT_BEFORE_FINISHED("DRIVER_SUBMIT_BEFORE_FINISHED"), 
    
    /**
     * 骑手导致外卖破损或错送漏送
     */
    FOOD_LOOKS_NOT_WELL("FOOD_LOOKS_NOT_WELL"), 
    
    /**
     * 骑手服务态度差
     */
    DRIVER_LOOKS_NOT_WELL("DRIVER_LOOKS_NOT_WELL"), 
    
    /**
     * 骑手诱导用户退单
     */
    ADVISED_TO_CANCEL_BY_DRIVER("ADVISED_TO_CANCEL_BY_DRIVER"), 
    
    /**
     * 骑手额外收取费用，导致用户退单
     */
    DRIVER_ASK_FOR_ADDITIONAL_COST("DRIVER_ASK_FOR_ADDITIONAL_COST"), 
    
    /**
     * 骑手虚假标记异常，导致订单取消
     */
    DRIVER_WRITE_WRONG_EXCEPTION_REASON("DRIVER_WRITE_WRONG_EXCEPTION_REASON"), 
    
    /**
     * 骑手虚假完成配送，实际由商户自行配送
     */
    DELIVERY_BY_SELF("DELIVERY_BY_SELF"), 
    
    /**
     * 系统故障
     */
    SYSTEM_ERROR("SYSTEM_ERROR"), 
    
    /**
     * 超时30分钟取消订单赔付
     */
    DELIVERY_TIMEOUT("DELIVERY_TIMEOUT");
    

    private String orderDesc;
    CompensationReason(String orderDesc) {
        this.orderDesc = orderDesc;
    }
}