package com.opensdk.eleme2.api.enumeration.order;

public enum ReplyReminderType {
    /**
     * 自定义回复
     */
    custom("custom"), 
    
    /**
     * 已送出
     */
    hasOut("hasOut"), 
    
    /**
     * 正在烹饪
     */
    inCooking("inCooking"), 
    
    /**
     * 天气原因
     */
    weather("weather"), 
    
    /**
     * 人手不齐
     */
    shortHand("shortHand");
    

    private String orderDesc;
    ReplyReminderType(String orderDesc) {
        this.orderDesc = orderDesc;
    }
}