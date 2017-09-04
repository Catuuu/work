package com.framework.mapping.system;

import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/1.
 */
public class RequestDianwoDaLog {

    //主键
    private String diaowoDaLogKey;

    //请求RequestLog主键
    private String requestLogKey;

    //UrlId
    private String url;

    //参数
    private Map params;


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

    public String getDiaowoDaLogKey() {
        return diaowoDaLogKey;
    }

    public void setDiaowoDaLogKey(String diaowoDaLogKey) {
        this.diaowoDaLogKey = diaowoDaLogKey;
    }

    public String getRequestLogKey() {
        return requestLogKey;
    }

    public void setRequestLogKey(String requestLogKey) {
        this.requestLogKey = requestLogKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map getParams() {
        return params;
    }

    public void setParams(Map params) {
        this.params = params;
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
}
