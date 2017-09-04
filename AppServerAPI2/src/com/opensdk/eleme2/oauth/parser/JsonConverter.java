package com.opensdk.eleme2.oauth.parser;



import com.opensdk.eleme2.oauth.response.ErrorResponse;
import com.opensdk.eleme2.utils.json.ExceptionErrorListener;
import com.opensdk.eleme2.utils.json.JSONReader;
import com.opensdk.eleme2.utils.json.JSONValidatingReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonConverter implements Converter {
    public <T extends ErrorResponse> T toResponse(String rsp, Class<T> clazz) {
        JSONReader reader = new JSONValidatingReader(new ExceptionErrorListener());
        Object rootObj = reader.read(rsp);
        if (rootObj instanceof Map<?, ?>) {
            Map<?, ?> rootJson = (Map<?, ?>) rootObj;
            return fromJson(rootJson, clazz);
        }
        if (rootObj instanceof List<?>) {
            List<?> rootJson = (List<?>) rootObj;
            for (Object o : rootJson) {
                Map<?, ?> rj = (Map<?, ?>) o;
                return fromJson(rj, clazz);
            }
        }
        return null;
    }

    /**
     * 把JSON格式的数据转换为对象。
     *
     * @param <T>   泛型领域对象
     * @param json  JSON格式的数据
     * @param clazz 泛型领域类型
     * @return 领域对象
     */
    public <T> T fromJson(final Map<?, ?> json, Class<T> clazz)  {
        return Converters.convert(clazz, new Reader() {
            public boolean hasReturnField(Object name) {
                return json.containsKey(name);
            }

            public Object getPrimitiveObject(Object name) {
                return json.get(name);
            }

            public Object getObject(Object name, Class<?> type)  {
                Object tmp = json.get(name);
                if (tmp instanceof Map<?, ?>) {
                    Map<?, ?> map = (Map<?, ?>) tmp;
                    return fromJson(map, type);
                } else {
                    return tmp;
                }
            }

            public List<?> getListObjects(Object listName, Object itemName, Class<?> subType)  {
                List<Object> listObjs = null;

                Object listTmp = json.get(listName);
                if (listTmp instanceof Map<?, ?>) {
                    Map<?, ?> jsonMap = (Map<?, ?>) listTmp;
                    Object itemTmp = jsonMap.get(itemName);
                    if (itemTmp == null && listName != null) {
                        String listNameStr = listName.toString();
                        itemTmp = jsonMap.get(listNameStr.substring(0, listNameStr.length() - 1));
                    }
                    if (itemTmp instanceof List<?>) {
                        listObjs = new ArrayList<Object>();
                        List<?> tmpList = (List<?>) itemTmp;
                        for (Object subTmp : tmpList) {
                            if (subTmp instanceof Map<?, ?>) {// object
                                Map<?, ?> subMap = (Map<?, ?>) subTmp;
                                Object subObj = fromJson(subMap, subType);
                                if (subObj != null) {
                                    listObjs.add(subObj);
                                }
                            } else if (subTmp instanceof List<?>) {// array
                                // TODO not support yet
                            } else {// boolean, long, double, string, null
                                listObjs.add(subTmp);
                            }
                        }
                    }
                }

                return listObjs;
            }
        });
    }
}
