package com.opensdk.eleme2.api.entity.finance;

public class BranchQuery{

    /**
     * 查询日期闭合区间
     */
    private DateRange range;
    public DateRange getRange() {
        return range;
    }
    public void setRange(DateRange range) {
        this.range = range;
    }
    
    /**
     * 分页
     */
    private Paging paging;
    public Paging getPaging() {
        return paging;
    }
    public void setPaging(Paging paging) {
        this.paging = paging;
    }
    
}