package com.opensdk.eleme2.api.entity.finance;

import java.math.BigDecimal;

public class OQueryBalanceResponse{

    /**
     * 可用余额
     */
    private BigDecimal availableAmount;
    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }
    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
    }
    
    /**
     * 总余额
     */
    private BigDecimal totalAmount;
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
}