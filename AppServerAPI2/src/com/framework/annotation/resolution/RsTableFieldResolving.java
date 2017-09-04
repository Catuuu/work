package com.framework.annotation.resolution;

import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import com.framework.util.BeanUtil;
import com.framework.util.StringUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-12
 * Time: 上午10:54
 * To change this template use File | Settings | File Templates.
 */
public class RsTableFieldResolving {
    /**
     * 获取表字段信息
     *
     * @param baseMapping
     * @return
     */
    public static Map getTableField(BaseMapping baseMapping) {
        Map hmp = new HashMap();
        List<Map> paramsList = new ArrayList<Map>();
        List<Map> conditionList = new ArrayList<Map>();
        Class cls = baseMapping.getClass();
        while (!cls.equals(BaseMapping.class)) {
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(RsTableField.class)) {//字段定义了注解
                    RsTableField rtf = field.getAnnotation(RsTableField.class);
                    if (rtf.primaryKey()) {
                        hmp.put("primaryKey", setMapField(field, baseMapping));
                    }
                    if (isContainsFieldName(baseMapping.getParamFields(), field)) { //更新列表中没有该字段,就跳过
                        paramsList.add(setMapField(field, baseMapping));
                    }
                    if (isContainsFieldName(baseMapping.getConditionFields(), field)) {
                        conditionList.add(setMapField(field, baseMapping));
                    }
                }
            }
            cls = cls.getSuperclass();
        }
        hmp.put("paramsList", paramsList);
        hmp.put("conditionList", conditionList);
        hmp.put("orderStr", baseMapping.getOrderStr());
        return hmp;
    }

    /**
     * 条件处理
     *
     * @param fieldMap
     * @return
     */
    public static boolean conditionHandle(Map fieldMap) {
        List<Map> conditionList = (List<Map>) fieldMap.get("conditionList");
        if (conditionList.isEmpty()) {
            Object primaryKey = fieldMap.get("primaryKey");
            if (null != primaryKey) {
                conditionList.add((Map) primaryKey);
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 条件处理
     *
     * @param fieldMap
     * @param baseMapping
     * @return
     */
    public static boolean primaryHandle(Map fieldMap,BaseMapping baseMapping) {
        List<Map> paramsList = (List<Map>) fieldMap.get("paramsList");
        if (paramsList.isEmpty())
            return false;
        Map primaryObj = (Map) fieldMap.get("primaryKey");
        if (BeanUtil.isNullAndEmpty(primaryObj.get("fieldValue"))) {
            for (Map m : paramsList) {
                if (primaryObj.get("fieldName").toString().equalsIgnoreCase(m.get("fieldName").toString())) {
                    String primaryKey = StringUtil.getPrimaryKey();
                    m.put("fieldValue", primaryKey);
                    baseMapping.setFieldValue(primaryObj.get("fieldName"), primaryKey);
                }
            }
        }
        return true;
    }

    /**
     * 排序处理
     *
     * @param fieldMap
     * @return
     */
    public static void orderHandle(Map fieldMap) {
        Object orderString = fieldMap.get("orderStr");
        //如果没有指定排序则将主键作为默认排序
        if (null == orderString) {
            Object primaryKey = fieldMap.get("primaryKey");
            if (null != primaryKey) {
                Map pk = (HashMap) primaryKey;
                fieldMap.put("orderStr", pk.get("fieldName"));
            }
        }
    }

    /**
     * 获取表字段信息
     *
     * @param baseMapping
     * @return
     */
    public static String getTableFieldStr(BaseMapping baseMapping) {
        String returnStr = "";
        Class cls = baseMapping.getClass();
        while (!cls.equals(BaseMapping.class)) {
            Field[] fields = cls.getDeclaredFields();
            String describe;  //获取属性描述
            String fieldName;  //获取字段名
            for (Field field : fields) {
                if (field.isAnnotationPresent(RsTableField.class)) {   //字段定义了注解
                    RsTableField rtf = field.getAnnotation(RsTableField.class);
                    describe = rtf.describe();
                    fieldName = rtf.name();
                    if ("".equalsIgnoreCase(fieldName)) fieldName = field.getName();
                    field.setAccessible(true);
                    try {
                        returnStr += "\n" + describe + "(" + fieldName + "):" + BeanUtil.objectToString(field.get(baseMapping));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            cls = cls.getSuperclass();
        }
        return returnStr;
    }

    /**
     * 设置字段值
     *
     * @param field
     * @return
     * @throws IllegalAccessException
     */
    private static Map setMapField(Field field, BaseMapping baseMapping) {
        Map hmp = new HashMap();
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        RsTableField rtf = field.getAnnotation(RsTableField.class);
        String fieldName = rtf.name();
        if ("".equalsIgnoreCase(fieldName)) fieldName = field.getName();
        hmp.put("fieldName", fieldName);
        try {
            hmp.put("fieldValue", field.get(baseMapping));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return hmp;
    }

    /**
     * 是否包含属性名称
     *
     * @param list
     * @param field
     * @return
     */
    private static boolean isContainsFieldName(List<String> list, Field field) {
        if (list == null || list.size() == 0) {
            return false;
        } else if (list.contains(field.getName())) {
            return true;
        } else {
            return false;
        }
    }
}
