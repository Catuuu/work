package com.opensdk.eleme2.api.entity.product;

import com.opensdk.eleme2.api.enumeration.product.OItemWeekEnum;

import java.util.*;

public class OItemSellingTime{

    /**
     * 商品售卖的星期列表
     */
    private List<OItemWeekEnum> weeks;
    public List<OItemWeekEnum> getWeeks() {
        return weeks;
    }
    public void setWeeks(List<OItemWeekEnum> weeks) {
        this.weeks = weeks;
    }
    
    /**
     * 商品售卖的开始日期，格式：yyyy-MM-dd
     */
    private String beginDate;
    public String getBeginDate() {
        return beginDate;
    }
    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }
    
    /**
     * 商品售卖的结束日期，格式：yyyy-MM-dd
     */
    private String endDate;
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
    /**
     * 商品售卖时间段，最多可以设置3个
     */
    private List<OItemTime> times;
    public List<OItemTime> getTimes() {
        return times;
    }
    public void setTimes(List<OItemTime> times) {
        this.times = times;
    }
    
}