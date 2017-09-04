package com.framework.util;

import com.framework.annotation.resolution.RsTreeResolving;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成树结构对象工具类.
 * <p/>
 * User: chenbin
 * Date: 11-6-8
 * Time: 下午2:16
 * To change this template use File | Settings | File Templates.
 */
public class TreeUtil {
    /**
     * 生成树结构的Map集合(从父节点向下查找)
     *
     * @param lst
     * @param parentid
     * @return
     */
    public static List<Map> mapTreeFromParent(List lst, String parentid) {
        Map<String, List<Map>> lstMap = convertListObject(lst, null);
        return mapTreeParent(lstMap, parentid);
    }

    /**
     * 生成树结构的Map集合(从本节点向下查找)
     *
     * @param lst
     * @param id
     * @return
     */
    public static List<Map> mapTreeFormOneself(List lst, String id) {
        Map<String, List<Map>> lstMap = convertListObject(lst, id);
        return mapTreeParent(lstMap, id);
    }

    /**
     * 递归集合返回树结构集合
     *
     * @param lstMap
     * @param parentid
     * @return
     */
    private static List<Map> mapTreeParent(Map<String, List<Map>> lstMap, String parentid) {
        List children = new ArrayList();
        if (null != lstMap.get(parentid))
            for (Map<String, Object> treeMap : lstMap.get(parentid)) {
                boolean nodeValid = false; //节点的合法性
                String key = treeMap.get("id").toString();
                String nodeType = BeanUtil.objectToString(treeMap.get("nodeType"));
                if (!"bole".equalsIgnoreCase(nodeType))   //如果节点类型不是树干
                    nodeValid = true;
                if (!"leaf".equalsIgnoreCase(nodeType) && lstMap.containsKey(key)) {  //如果节点类型不是树叶
                    treeMap.put("children", mapTreeParent(lstMap, key));
                    nodeValid = true;
                }

                if (nodeValid)
                    children.add(treeMap);
            }
        return children;
    }

    /**
     * 生成树结构的Map集合(读取xml文件)
     *
     * @param em
     * @return
     */
    public static List<Map> mapTreeFromDom4j(Element em) {
        List<Element> elements = em.elements();
        List children = new ArrayList();
        for (Element element : elements) {
            Map hmp = new HashMap();//节点对象
            hmp.put("id", element.attributeValue("id"));
            hmp.put("text", element.attributeValue("text"));
            String iconCls = element.attributeValue("iconCls");
            String state = element.attributeValue("state");
            String checked = element.attributeValue("checked");
            String url = element.attributeValue("url");
            if (BeanUtil.notNullAndEmpty(iconCls)) hmp.put("iconCls", iconCls);
            if (BeanUtil.notNullAndEmpty(state)) hmp.put("state", state);
            if (BeanUtil.notNullAndEmpty(checked)) hmp.put("checked", checked);
            if (BeanUtil.notNullAndEmpty(url)) {
                Map attr = new HashMap();
                attr.put("url", url);
                hmp.put("attributes", attr);
            }
            if (!element.elements().isEmpty()) {
                List<Map> lstMap = mapTreeFromDom4j(element);
                if (BeanUtil.notNullAndEmpty(lstMap)) hmp.put("children", lstMap);
            }
            children.add(hmp);
        }
        return children;
    }

    /**
     * 集合对象转换（Map集合中存放同一父节点直接子节点集合）
     *
     * @param lst
     * @param id
     * @return
     */
    private static Map<String, List<Map>> convertListObject(List lst, String id) {
		if(null==lst) return new HashMap<String, List<Map>>();

        List<Map> oneselfList = new ArrayList<Map>();
        //树Map对象集合
        Map<String, List<Map>> treeMap = new HashMap<String, List<Map>>();
        for (Object obj : lst) {
            //将对象转换成树节点
            Map treeNode = convertTreeMap(obj);
            //如果节点为空则直接跳出本次循环，继续下次循环
            if (null == treeNode) continue;

            String nodeId = treeNode.get("id").toString();
            String parentId = treeNode.get("parentid").toString();

            //如果id值不为空则查找出此节点对象对应的父id
            if (null != id && id.equalsIgnoreCase(nodeId)) {
                id = parentId;
                oneselfList.add(treeNode);
            }

            treeMapAdd(treeMap, treeNode);
        }

        //清除指定ID值节点的同级节点
        if (!oneselfList.isEmpty()) treeMap.put(id, oneselfList);

        return treeMap;
    }

    /**
     * 获取树节点对象
     *
     * @param obj
     * @return
     */
    private static Map convertTreeMap(Object obj) {
        if (obj instanceof Map) return (Map) obj;
        else return RsTreeResolving.convertTreeMapping(obj);
    }

    /**
     * 向树节点集合中添加节点对象
     *
     * @param treeMap
     * @param treeNode
     */
    private static void treeMapAdd(Map<String, List<Map>> treeMap, Map treeNode) {
        //将父节点ID作为键值
        String key = treeNode.get("parentid").toString();
        //树直接子节点集合对象
        List<Map> lstMap = treeNodeList(treeMap, key);
        //向子节点集合中添加树节点
        lstMap.add(treeNode);
        //树Map对象集合添加子节点集合对象
        treeMap.put(key, lstMap);
    }

    /**
     * Map<String, List<Map>>集合中返回指定键值的List<Map>对象
     *
     * @param treeMap
     * @param key
     * @return
     */
    private static List<Map> treeNodeList(Map<String, List<Map>> treeMap, String key) {
        //树直接子节点集合对象
        List<Map> lstMap;
        //如果树集合对象中存在则取出此集合对象
        if (treeMap.containsKey(key)) lstMap = (List<Map>) treeMap.get(key);
            //如果不存在则new一个新集合对象
        else lstMap = new ArrayList<Map>();
        return lstMap;
    }
}
