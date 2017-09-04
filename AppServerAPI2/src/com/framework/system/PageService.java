package com.framework.system;

import com.framework.mapping.PageParam;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-8
 * Time: 下午4:59
 * To change this template use File | Settings | File Templates.
 */
public interface PageService {
    public abstract String getLimitString(String sql, PageParam pageParam);
}
