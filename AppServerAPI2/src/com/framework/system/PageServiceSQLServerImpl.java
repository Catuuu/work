package com.framework.system;

import com.framework.mapping.PageInfo;
import com.framework.mapping.PageParam;
import com.framework.util.BeanUtil;
import com.framework.util.StringUtil;

/**
 * SQlServer成生分页查询SQL.
 * User: chenbin
 * Date: 13-1-8
 * Time: 下午5:03
 * To change this template use File | Settings | File Templates.
 */
public class PageServiceSQLServerImpl implements PageService {
    /* (non-Javadoc)
    * @see org.mybatis.extend.interceptor.IDialect#getLimitString(java.lang.String, com.framework.mapping.PageParam)
    */
    
    public String getLimitString(String sql, PageParam pageParam) {
        sql = sql.trim();
        PageInfo pageInfo = pageParam.getPageInfo();
        StringBuffer pagingSelect = new StringBuffer(sql.length() + 200);


        String fieldSort = "";

        int offset=0;
        if(pageInfo.getRecordCount()>0){
            if (pageInfo.getCurrentPage() == 0) pageInfo.setCurrentPage(1);
            if (BeanUtil.notNullAndEmpty(pageInfo.getFixedOrder())) fieldSort += "," + pageInfo.getFixedOrder();
            if (BeanUtil.notNullAndEmpty(pageInfo.getSort())) fieldSort += "," + pageInfo.getSort() +" "+pageInfo.getOrder();
            if (fieldSort.length() > 0) fieldSort = "order by " + fieldSort.substring(1);

            long beginNum = (pageInfo.getCurrentPage() - 1) * pageInfo.getRows() + 1;
            long endNum = (pageInfo.getCurrentPage() * pageInfo.getRows() > pageInfo.getRecordCount()) ?
                    pageInfo.getRecordCount() : pageInfo.getCurrentPage() * pageInfo.getRows();

            String head = "select tmp.* from (";
            String foot = ") tmp where tmp.rownumber between " + beginNum + " and " + endNum;
            pagingSelect.append(head);
            pagingSelect.append(StringUtil.IgnoreCaseReplaceFirst(sql,"select","select (row_number() Over(" + fieldSort + ")) as rownumber,"));
            pagingSelect.append(foot);
        }else{
            pagingSelect.append(sql);
        }
        return pagingSelect.toString();
    }
}
