package com.opensdk;

/**
 * Created by Administrator on 2017/2/23.
 */
public class OrderStatusCode {
    private String reasonCode;//原因code
    private String reason;   //原因描述

    public OrderStatusCode(){

    }

    public OrderStatusCode(String reasonCode,String reason){
        this.reason = reason;
        this.reasonCode = reasonCode;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
