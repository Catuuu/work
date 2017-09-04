package com.framework.system;
import com.framework.mapping.PageInfo;
import com.framework.mapping.PageParam;
import com.framework.util.BeanUtil;

/**
 * MySql成生分页查询SQL.
 * User: chenbin
 * Date: 13-1-8
 * Time: 下午4:56
 * To change this template use File | Settings | File Templates.
 */
public class PageServiceMySqlImpl implements PageService {
    /* (non-Javadoc)
     * @see org.mybatis.extend.interceptor.IDialect#getLimitString(java.lang.String, com.framework.mapping.PageParam)
     */

    public String getLimitString(String sql, PageParam pageParam) {
        sql = sql.trim();
        PageInfo pageInfo = pageParam.getPageInfo();
        StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
        pagingSelect.append(sql);

        String fieldSort = "";
        if (BeanUtil.notNullAndEmpty(pageInfo.getFixedOrder())) fieldSort += "," + pageInfo.getFixedOrder();
        if (BeanUtil.notNullAndEmpty(pageInfo.getSort())) fieldSort += "," + pageInfo.getSort() +" "+pageInfo.getOrder();
        if (fieldSort.length() > 0) pagingSelect.append("\norder by " + fieldSort.substring(1));

        long offset=0;
        if(pageInfo.getRecordCount()>0){
            if (pageInfo.getCurrentPage() == 0) pageInfo.setCurrentPage(1);
            offset = (pageInfo.getCurrentPage() - 1) * pageInfo.getRows();
        }
        pagingSelect.append(" limit " + offset + "," + pageInfo.getRows());
        return pagingSelect.toString();
    }
}
