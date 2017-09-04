package com.framework.annotation.resolution;

import com.framework.annotation.RsTreeNode;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 对@RsTree注解的解析类
 * .
 * User: chenbin
 * Date: 13-3-6
 * Time: 上午11:43
 * To change this template use File | Settings | File Templates.
 */
public class RsTreeResolving {
    /**
     * 将任意使用@RsTreeNode注解的对象转换成TreeMapping对象
     *
     * @param obj
     * @return
     */
    public static Map convertTreeMapping(Object obj) {
        Map treeMap = new HashMap();
        Map nodeAttrs = new HashMap();
        try {
            Class cls = obj.getClass();
            while (!cls.equals(Object.class)) {
                for (Field field : cls.getDeclaredFields()) {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    if (field.isAnnotationPresent(RsTreeNode.class)) {
                        RsTreeNode rsTreeNode = field.getAnnotation(RsTreeNode.class);
                        String tagName = rsTreeNode.tagName().toString();
                        String defValue = rsTreeNode.defaultValue();
                        if ("attributes".equalsIgnoreCase(tagName)) {   //如果为节点属性值
                            Map nodeAttr = getNodeAttr(defValue, field.getName(), field.get(obj));
                            if (null != nodeAttr)
                                nodeAttrs.putAll(nodeAttr);
                        } else {  //否则为节点标记
                            Map nodeTag = getNodeTag(tagName, defValue, field.get(obj));
                            if (null != nodeTag)
                                treeMap.putAll(nodeTag);
                        }
                    }
                }
                cls = cls.getSuperclass();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isTreeMap(treeMap)) {
            treeMap.put("attributes", nodeAttrs);
            return treeMap;
        } else {
            return null;
        }
    }

    /**
     * 获得树节点元素
     *
     * @param tagName
     * @param defValue
     * @param fieldValue
     * @return
     */
    private static Map getNodeTag(String tagName, String defValue, Object fieldValue) {
        Map nodeTag = new HashMap();
        //对象属性值为空时则使用注解默认值
        if (null == fieldValue || "".equalsIgnoreCase(fieldValue.toString()))
            fieldValue = defValue;
        //如果属性值再次为空则终止本循环继续下一个循环
        if (null == fieldValue || "".equalsIgnoreCase(fieldValue.toString()))
            return null;
        if ("checked".equalsIgnoreCase(tagName))
            nodeTag.put(tagName, Boolean.parseBoolean(fieldValue.toString()));
        else
            nodeTag.put(tagName, fieldValue);
        return nodeTag;
    }

    /**
     * 获取节点属性
     *
     * @param defValue
     * @param fieldName
     * @param fieldValue
     * @return
     */
    private static Map getNodeAttr(String defValue, String fieldName, Object fieldValue) {
        Map nodeAttr = new HashMap();
        //对象属性值为空时则使用注解默认值
        if (null == fieldValue || "".equalsIgnoreCase(fieldValue.toString()))
            fieldValue = defValue;
        //如果属性值再次为空则终止本循环继续下一个循环
        if (null == fieldValue || "".equalsIgnoreCase(fieldValue.toString()))
            return null;
        nodeAttr.put(fieldName, fieldValue);
        return nodeAttr;
    }

    /**
     * 判断此对象是否符合树结构最低元素
     *
     * @param treeMap
     * @return
     */
    private static boolean isTreeMap(Map treeMap) {
        if (treeMap.containsKey("id") && treeMap.containsKey("parentid"))
            return true;
        else
            return false;
    }
}
