package com.opensdk.eleme2.api.entity.product;

public class OItemTime{

    /**
     * 售卖开始时间，格式：HH:mm
     */
    private String beginTime;
    public String getBeginTime() {
        return beginTime;
    }
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }
    
    /**
     * 售卖结束时间，格式：HH:mm
     */
    private String endTime;
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    
}