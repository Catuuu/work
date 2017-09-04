package com.opensdk.eleme2.api.enumeration.order;

public enum InvoiceType {
    /**
     * 个人
     */
    personal("personal"), 
    
    /**
     * 企业
     */
    company("company");
    

    private String orderDesc;
    InvoiceType(String orderDesc) {
        this.orderDesc = orderDesc;
    }
}