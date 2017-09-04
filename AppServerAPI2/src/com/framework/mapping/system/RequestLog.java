package com.framework.mapping.system;

import java.util.Date;

/**
 * Created by Administrator on 2017/7/1.
 */
public class RequestLog {
    //主键
    private String requestLogKey;

    //主机名
    private String remoteHost;

    //RemoteAddr
    private String remoteAddr;

    //请求URL
    private String requestURL;

    //类名
    private String classname;

    //方法名
    private String methodname;

    //方法参数
    private String methodParametersstr;

    //开始时间
    private Date startTime;

    //结束时间
    private Date endTime;

    //用时(Ms)
    private long executeTime;

    //请求参数
    private String paramstr;

    //返回值
    private String returnValue;

    public String getRequestLogKey() {
        return requestLogKey;
    }

    public void setRequestLogKey(String requestLogKey) {
        this.requestLogKey = requestLogKey;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getMethodname() {
        return methodname;
    }

    public void setMethodname(String methodname) {
        this.methodname = methodname;
    }

    public String getMethodParametersstr() {
        return methodParametersstr;
    }

    public void setMethodParametersstr(String methodParametersstr) {
        this.methodParametersstr = methodParametersstr;
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

    public String getParamstr() {
        return paramstr;
    }

    public void setParamstr(String paramstr) {
        this.paramstr = paramstr;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }
}
