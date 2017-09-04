package com.opensdk.eleme2.api.entity.order;

import com.opensdk.eleme2.api.enumeration.order.CompensationStatus;

public class CompensationInfo{

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
     * 索赔金额
     */
    private Double amount;
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
    /**
     * 索赔状态
     */
    private CompensationStatus status;
    public CompensationStatus getStatus() {
        return status;
    }
    public void setStatus(CompensationStatus status) {
        this.status = status;
    }
    
}