package com.opensdk.eleme2.api.enumeration.order;

public enum OSubState {
    /**
     * 商家取消
     */
    merchantReason("merchantReason"), 
    
    /**
     * 配送商取消
     */
    carrierReason("carrierReason"), 
    
    /**
     * 用户取消
     */
    userReason("userReason"), 
    
    /**
     * 物流系统取消
     */
    systemReason("systemReason"), 
    
    /**
     * 呼叫配送晚
     */
    merchantCallLateError("merchantCallLateError"), 
    
    /**
     * 餐厅出餐问题
     */
    merchantFoodError("merchantFoodError"), 
    
    /**
     * 商户中断配送
     */
    merchantInterruptDeliveryError("merchantInterruptDeliveryError"), 
    
    /**
     * 用户不接电话
     */
    userNotAnswerError("userNotAnswerError"), 
    
    /**
     * 用户退单
     */
    userReturnOrderError("userReturnOrderError"), 
    
    /**
     * 用户地址错误
     */
    userAddressError("userAddressError"), 
    
    /**
     * 超出服务范围
     */
    deliveryOutOfService("deliveryOutOfService"), 
    
    /**
     * 骑手标记异常
     */
    carrierRemarkExceptionError("carrierRemarkExceptionError"), 
    
    /**
     * 系统自动标记异常--订单超过3小时未送达
     */
    systemMarkedError("systemMarkedError"), 
    
    /**
     * 其他异常
     */
    otherError("otherError"), 
    
    /**
     * 配送超时，系统标记异常
     */
    deliveryTimeout("deliveryTimeout"), 
    
    /**
     * 只支持在线订单
     */
    onlinePayError("onlinePayError"), 
    
    /**
     * 超出服务范围
     */
    consumerLocationTooFarError("consumerLocationTooFarError"), 
    
    /**
     * 请求配送过晚, 无法呼叫
     */
    merchantPushTooLateError("merchantPushTooLateError"), 
    
    /**
     * 系统异常
     */
    systemError("systemError");
    

    private String orderDesc;
    OSubState(String orderDesc) {
        this.orderDesc = orderDesc;
    }
}