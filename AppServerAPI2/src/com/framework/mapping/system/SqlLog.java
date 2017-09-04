package com.framework.mapping.system;

import java.util.Date;

/**
 * Created by Administrator on 2017/7/1.
 */
public class SqlLog {
    //主键
    private String SqlLogKey;

    //请求RequestLog主键
    private String requestLogKey;

    //mappedId
    private String mappedId;

    //开始时间
    private Date startTime;

    //结束时间
    private Date endTime;

    //用时(Ms)
    private long executeTime;

    //执行SQL
    private String sqlStr;

    public String getSqlLogKey() {
        return SqlLogKey;
    }

    public void setSqlLogKey(String sqlLogKey) {
        SqlLogKey = sqlLogKey;
    }

    public String getRequestLogKey() {
        return requestLogKey;
    }

    public void setRequestLogKey(String requestLogKey) {
        this.requestLogKey = requestLogKey;
    }

    public String getMappedId() {
        return mappedId;
    }

    public void setMappedId(String mappedId) {
        this.mappedId = mappedId;
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

    public String getSqlStr() {
        return sqlStr;
    }

    public void setSqlStr(String sqlStr) {
        this.sqlStr = sqlStr;
    }
}
