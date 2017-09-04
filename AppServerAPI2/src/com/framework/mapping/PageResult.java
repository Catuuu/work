package com.framework.mapping;

import java.util.List;

/**
 * 分页返回对象
 * User: Administrator
 * Date: 13-1-8
 * Time: 上午10:39
 * To change this template use File | Settings | File Templates.
 */
public class PageResult {
    public long       total;        //总记录数
    public List<Object> rows;         //记录集
    public List<Object> footer;       //合计

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<Object> getRows() {
        return rows;
    }

    public void setRows(List<Object> rows) {
        this.rows = rows;
    }

    public List<Object> getFooter() {
        return footer;
    }

    public void setFooter(List<Object> footer) {
        this.footer = footer;
    }
}
