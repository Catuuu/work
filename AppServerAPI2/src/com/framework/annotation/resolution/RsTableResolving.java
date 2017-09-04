package com.framework.annotation.resolution;

import com.framework.annotation.RsTable;
import com.framework.mapping.BaseMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-12
 * Time: 上午10:40
 * To change this template use File | Settings | File Templates.
 */
public class RsTableResolving {
    /**
     * 获取表信息
     *
     * @param baseMapping
     * @return
     */
    public static Map getTable(BaseMapping baseMapping){
        Map hmp = new HashMap();
        Class cls = baseMapping.getClass();
        if (cls.isAnnotationPresent(RsTable.class)) { //定义表注解
            RsTable rsTable = (RsTable) cls.getAnnotation(RsTable.class);
            String tabName = rsTable.name();
            if ("".equalsIgnoreCase(tabName))
                tabName = cls.getSimpleName();
            hmp.put("tableName", tabName);
        }
        return hmp;
    }

    /**
     * 获取表信息
     *
     * @param baseMapping
     * @return
     * @throws Exception
     */
    public static String getTableStr(BaseMapping baseMapping) {
        Class cls = baseMapping.getClass();
        String returnStr = "";
        if (cls.isAnnotationPresent(RsTable.class)) { //定义表注解
            RsTable rsTable = (RsTable) cls.getAnnotation(RsTable.class);
            String tabName = rsTable.name();
            if ("".equalsIgnoreCase(tabName))
                tabName = cls.getSimpleName();
            returnStr = rsTable.describe() + "(" + tabName + ")";
        }
        return returnStr;
    }
}
