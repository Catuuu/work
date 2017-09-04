package com.opensdk.eleme2.api.entity.finance;

import java.util.*;
import java.math.BigDecimal;

public class OBalanceLogVO{

    /**
     * 交易时间
     */
    private Date transTime;
    public Date getTransTime() {
        return transTime;
    }
    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }
    
    /**
     * 借贷方向
     */
    private String transDcMark;
    public String getTransDcMark() {
        return transDcMark;
    }
    public void setTransDcMark(String transDcMark) {
        this.transDcMark = transDcMark;
    }
    
    /**
     * 交易金额
     */
    private BigDecimal transAmount;
    public BigDecimal getTransAmount() {
        return transAmount;
    }
    public void setTransAmount(BigDecimal transAmount) {
        this.transAmount = transAmount;
    }
    
    /**
     * 交易后余额
     */
    private BigDecimal balance;
    public BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    
    /**
     * 交易状态
     */
    private Byte transStatus;
    public Byte getTransStatus() {
        return transStatus;
    }
    public void setTransStatus(Byte transStatus) {
        this.transStatus = transStatus;
    }
    
    /**
     * 备注
     */
    private String orderMes;
    public String getOrderMes() {
        return orderMes;
    }
    public void setOrderMes(String orderMes) {
        this.orderMes = orderMes;
    }
    
}