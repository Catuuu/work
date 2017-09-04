package com.framework.system;

import com.framework.mapping.PageInfo;
import com.framework.mapping.PageParam;
import com.framework.util.BeanUtil;

/**
 * Oracle 成生分页查询SQL.
 * User: chenbin
 * Date: 13-1-8
 * Time: 下午5:05
 * To change this template use File | Settings | File Templates.
 */
public class PageServiceOracleImpl implements PageService {
    /* (non-Javadoc)
     * @see org.mybatis.extend.interceptor.IDialect#getLimitString(java.lang.String, int, int)
     */

    public String getLimitString(String sql,PageParam pageParam) {
        sql = sql.trim();
        PageInfo pageInfo = pageParam.getPageInfo();
        StringBuffer pagingSelect = new StringBuffer(sql.length() + 200);


        String fieldSort = "";

        int offset=0;
        if(pageInfo.getRecordCount()>0){
            if (pageInfo.getCurrentPage() == 0) pageInfo.setCurrentPage(1);

            if (BeanUtil.notNullAndEmpty(pageInfo.getFixedOrder())) fieldSort += "," + pageInfo.getFixedOrder();
            if (BeanUtil.notNullAndEmpty(pageInfo.getSort())) fieldSort += "," + pageInfo.getSort() +" "+pageInfo.getOrder();
            if (fieldSort.length() > 0) fieldSort = " order by " + fieldSort.substring(1);

            long beginNum = (pageInfo.getCurrentPage() - 1) * pageInfo.getRows();
            long endNum = (pageInfo.getCurrentPage() * pageInfo.getRows() > pageInfo.getRecordCount()) ?
                    pageInfo.getRecordCount() + 1 : pageInfo.getCurrentPage() * pageInfo.getRows() + 1;

            String head = "select * from (select tmp.*, rownum as row_num from (\n";
            String foot = ") tmp where rownum < " + endNum + ") where row_num > " + beginNum+"\n";
            pagingSelect.append(head);
            pagingSelect.append(sql);
            pagingSelect.append(foot);
            pagingSelect.append(fieldSort);
        }else{
            pagingSelect.append(sql);
        }
        return pagingSelect.toString();
    }
}
