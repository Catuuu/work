package com.opensdk.eleme2.api.entity.finance;

import java.util.*;

public class OQueryBalanceLogRequest{

    /**
     * 开始日期
     */
    private Date beginDate;
    public Date getBeginDate() {
        return beginDate;
    }
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }
    
    /**
     * 结束日期
     */
    private Date endDate;
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    /**
     * 开始页码数
     */
    private int pageIndex;
    public int getPageIndex() {
        return pageIndex;
    }
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
    
    /**
     * 记录数
     */
    private int pageSize;
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    /**
     * 店铺Id
     */
    private long shopId;
    public long getShopId() {
        return shopId;
    }
    public void setShopId(long shopId) {
        this.shopId = shopId;
    }
    
}