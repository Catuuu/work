package com.opensdk.eleme2.api.entity.finance;

import java.util.*;

public class OBalanceLogResponse{

    /**
     * 总页数
     */
    private int totalPage;
    public int getTotalPage() {
        return totalPage;
    }
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    
    /**
     * 当前页数
     */
    private int currentPage;
    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    
    /**
     * 总行数
     */
    private int totalNum;
    public int getTotalNum() {
        return totalNum;
    }
    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }
    
    /**
     * 每页行数
     */
    private int pageSize;
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    /**
     * 起始位置
     */
    private int startIndex;
    public int getStartIndex() {
        return startIndex;
    }
    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }
    
    /**
     * 数据明细
     */
    private List<OBalanceLogVO> pageList;
    public List<OBalanceLogVO> getPageList() {
        return pageList;
    }
    public void setPageList(List<OBalanceLogVO> pageList) {
        this.pageList = pageList;
    }
    
}