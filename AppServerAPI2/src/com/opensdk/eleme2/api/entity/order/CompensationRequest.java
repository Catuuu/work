package com.opensdk.eleme2.api.entity.order;

import com.opensdk.eleme2.api.enumeration.order.CompensationReason;

public class CompensationRequest{

    /**
     * 订单Id
     */
    private String orderId;
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    /**
     * 请求索赔的原因
     */
    private CompensationReason reason;
    public CompensationReason getReason() {
        return reason;
    }
    public void setReason(CompensationReason reason) {
        this.reason = reason;
    }
    
    /**
     * 请求索赔的具体描述
     */
    private String description;
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
}