package com.opensdk.eleme2.api.entity.finance;

public class Paging{

    /**
     * 起始位置
     */
    private int offset;
    public int getOffset() {
        return offset;
    }
    public void setOffset(int offset) {
        this.offset = offset;
    }
    
    /**
     * 每页大小
     */
    private int limit;
    public int getLimit() {
        return limit;
    }
    public void setLimit(int limit) {
        this.limit = limit;
    }
    
}