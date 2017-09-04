package com.framework.mapping.system;

import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/1.
 */
public class RequestFormLog {
    //来源
    private String form;
    //主键
    private String elemeLogKey;

    //请求RequestLog主键
    private String requestLogKey;

    //UrlId
    private String UrlId;

    //path
    private String path;

    //sig
    private String sig;

    //系统级参数
    private Map<String, String> systemParamsMap;

    //应用级参数
    private Map<String, String> applicationParamsMap;

    //请求类型
    private String requestMethodType;

    //开始时间
    private Date startTime;

    //结束时间
    private Date endTime;

    //用时(Ms)
    private long executeTime;

    //返回值
    private String returnValue;

    public String getElemeLogKey() {
        return elemeLogKey;
    }

    public void setElemeLogKey(String elemeLogKey) {
        this.elemeLogKey = elemeLogKey;
    }

    public String getRequestLogKey() {
        return requestLogKey;
    }

    public void setRequestLogKey(String requestLogKey) {
        this.requestLogKey = requestLogKey;
    }

    public String getUrlId() {
        return UrlId;
    }

    public void setUrlId(String urlId) {
        UrlId = urlId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    public Map<String, String> getSystemParamsMap() {
        return systemParamsMap;
    }

    public void setSystemParamsMap(Map<String, String> systemParamsMap) {
        this.systemParamsMap = systemParamsMap;
    }

    public Map<String, String> getApplicationParamsMap() {
        return applicationParamsMap;
    }

    public void setApplicationParamsMap(Map<String, String> applicationParamsMap) {
        this.applicationParamsMap = applicationParamsMap;
    }

    public String getRequestMethodType() {
        return requestMethodType;
    }

    public void setRequestMethodType(String requestMethodType) {
        this.requestMethodType = requestMethodType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public long getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(long executeTime) {
        this.executeTime = executeTime;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }
}
