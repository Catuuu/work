package com.framework.resource;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据资源类
 * .
 * User: chenbin
 * Date: 13-3-7
 * Time: 下午6:09
 * To change this template use File | Settings | File Templates.
 */
public class DataResource {
    /**
     * 数据资源对象
     */
    private Map dataMap;

    public DataResource() {
        dataMap = new HashMap();
    }

    public void put(Object key, Object value) {
        dataMap.put(key, value);
    }

    public Object get(Object key) {
        return dataMap.get(key);
    }

    public Map getAll() {
        return dataMap;
    }

    public void putAll(Map map) {
        dataMap.putAll(map);
    }

    public void putAll(DataResource dataResource) {
        dataMap.putAll(dataResource.getAll());
    }
}
