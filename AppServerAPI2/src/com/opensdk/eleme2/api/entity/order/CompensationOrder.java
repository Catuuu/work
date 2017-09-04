package com.opensdk.eleme2.api.entity.order;

import com.opensdk.eleme2.api.enumeration.order.CompensationReason;

import java.util.*;

public class CompensationOrder{

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
     * 是否支持索赔
     */
    private Boolean compensable;
    public Boolean getCompensable() {
        return compensable;
    }
    public void setCompensable(Boolean compensable) {
        this.compensable = compensable;
    }
    
    /**
     * 可选的索赔原因
     */
    private List<CompensationReason> applyReasons;
    public List<CompensationReason> getApplyReasons() {
        return applyReasons;
    }
    public void setApplyReasons(List<CompensationReason> applyReasons) {
        this.applyReasons = applyReasons;
    }
    
    /**
     * 拒绝索赔原因
     */
    private String deniedReason;
    public String getDeniedReason() {
        return deniedReason;
    }
    public void setDeniedReason(String deniedReason) {
        this.deniedReason = deniedReason;
    }
    
}