package com.framework.mapping;

import com.alibaba.fastjson.JSONObject;
import com.framework.util.BeanUtil;

import java.util.Map;

/**
 * 分页对象
 * User: Administrator
 * Date: 13-1-7
 * Time: 下午6:04
 * To change this template use File | Settings | File Templates.
 */
public class PageInfo extends SerializeCloneable{
    private String recordSql;   //翻页记录集sql
    private String countSql;    //翻页记录数sql
    private String fixedOrder;  //固定排序

    private long recordCount;    //总记录数
    private long rows;  //每页显示的记录数(easyui)
    private long page; // 当前的页数(easyui)
    private String sort; //排序字段(easyui)
    private String order;//排序方式(easyui)

    public static PageInfo getPageInfo(String recordSqlID, String countSqlID, Map map) {

        PageInfo pageInfo = BeanUtil.createBean(map, PageInfo.class);
        pageInfo.setRecordSql(recordSqlID);
        pageInfo.setCountSql(countSqlID);
        return pageInfo;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public long getRows() {
        return rows;
    }

    public void setRows(long rows) {
        this.rows = rows;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }


    public String getRecordSql() {
        return recordSql;
    }

    public void setRecordSql(String recordSql) {
        this.recordSql = recordSql;
    }

    public String getCountSql() {
        return countSql;
    }

    public void setCountSql(String countSql) {
        this.countSql = countSql;
    }



    public long getCurrentPage() {
        long totalPage = calcTotalPage();
        if (totalPage > 0) {
            if (this.page <= 0) return 1;
            else if (this.page > totalPage) return totalPage;
            else return this.page;
        } else {
            return 0;
        }
    }

    public void setCurrentPage(long currentPage) {
        this.page = currentPage;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public long calcTotalPage() {
        if (this.recordCount > 0 && this.rows > 0) {
            if (this.recordCount > this.rows) {
                return (recordCount % rows == 0) ? recordCount / rows : recordCount / rows + 1;
            } else {
                return 1;
            }
        } else {
            return 0;
        }
    }

    public String getFixedOrder() {
        return fixedOrder;
    }

    public void setFixedOrder(String fixedOrder) {
        this.fixedOrder = fixedOrder;
    }
}
