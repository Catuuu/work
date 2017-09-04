package com.framework.mapping.system;

/**
 * Created by Administrator on 2017/3/9.
 */
public class HomeData {
    private String key;
    private double curser;//当天值
    private double yesterday;//昨天值
    private double last_week;//上周同天值

    public HomeData(){

    }
    public HomeData(String key){
        this.key = key;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getCurser() {
        return curser;
    }

    public void setCurser(double curser) {
        this.curser = curser;
    }

    public double getYesterday() {
        return yesterday;
    }

    public void setYesterday(double yesterday) {
        this.yesterday = yesterday;
    }

    public double getLast_week() {
        return last_week;
    }

    public void setLast_week(double last_week) {
        this.last_week = last_week;
    }
}
