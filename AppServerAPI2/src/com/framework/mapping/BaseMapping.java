package com.framework.mapping;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-22
 * Time: 下午4:57
 * To change this template use File | Settings | File Templates.
 */
public class BaseMapping extends SerializeCloneable {
    //参数集
    private List<String> paramFields = new ArrayList<String>();

    public List<String> getParamFields() {
        return paramFields;
    }

    public void addParamFields() {
        if (null == paramFields) {
            paramFields = new ArrayList<String>();
        }
        paramFields.clear();
        paramFields.addAll(getFields());

    }

    public void addUnParamFields(String params) {
        addParamFields();
        removeParamFields(params);
    }

    public void addParamField(String param) {
        if (null == paramFields) {
            paramFields = new ArrayList<String>();
        }
        if (!paramFields.contains(param)) {
            paramFields.add(param);
        }
    }

    public void addParamFields(String[] params) {
        for (String param : params) {
            addParamField(param.trim());
        }
    }

    public void addParamFields(String params) {
        addParamFields(params.split(","));
    }


    public void resetParamField(String param) {
        if (null == paramFields) {
            paramFields = new ArrayList<String>();
        }
        paramFields.clear();
        paramFields.add(param);
    }

    public void resetParamFields(String[] params) {
        paramFields.clear();
        for (String param : params) {
            addParamField(param.trim());
        }
    }

    public void resetParamFields(String params) {
        resetParamFields(params.split(","));
    }


    public void removeParamFields() {
        if (paramFields != null) {
            paramFields.clear();
        }
    }

    public void removeParamField(String param) {
        if (null != paramFields) {
            paramFields.remove(param);
        }
    }

    public void removeParamFields(String[] params) {
        if (null != paramFields) {
            for (String param : params) {
                removeParamField(param.trim());
            }
        }
    }

    public void removeParamFields(String params) {
        removeParamFields(params.split(","));
    }


    //条件集
    private List<String> conditionFields = new ArrayList<String>();

    public List<String> getConditionFields() {
        return conditionFields;
    }

    public void addConditionFields() {
        if (null == conditionFields) {
            conditionFields = new ArrayList<String>();
        }
        conditionFields.clear();
        conditionFields.addAll(getFields());

    }

    public void addUnConditionFields(String conditions) {
        addConditionFields();
        removeConditionFields(conditions);
    }

    public void addConditionField(String condition) {
        if (null == conditionFields) {
            conditionFields = new ArrayList<String>();
        }
        if (!conditionFields.contains(condition)) {
            conditionFields.add(condition);
        }
    }

    public void addConditionFields(String[] conditions) {
        for (String condition : conditions) {
            addConditionField(condition.trim());
        }
    }

    public void addConditionFields(String conditions) {
        addConditionFields(conditions.split(","));
    }


    public void resetConditionField(String condition) {
        if (null == conditionFields) {
            conditionFields = new ArrayList<String>();
        }
        conditionFields.clear();
        conditionFields.add(condition);
    }

    public void resetConditionFields(String[] conditions) {
        conditionFields.clear();
        for (String condition : conditions) {
            addConditionField(condition.trim());
        }
    }

    public void resetConditionFields(String conditions) {
        resetConditionFields(conditions.split(","));
    }


    public void removeConditionFields() {
        if (conditionFields != null) {
            conditionFields.clear();
        }
    }

    public void removeConditionField(String condition) {
        if (null != conditionFields) {
            conditionFields.remove(condition);
        }
    }

    public void removeConditionFields(String[] conditions) {
        if (null != conditionFields) {
            for (String condition : conditions) {
                removeConditionField(condition.trim());
            }
        }
    }

    public void removeConditionFields(String conditions) {
        removeConditionFields(conditions.split(","));
    }

    //排序字符串
    private String orderStr;

    public String getOrderStr() {
        return orderStr;
    }

    public void addOrderStr(String orderStr) {
        if (null == this.orderStr) {
            this.orderStr = "";
        }
        this.orderStr += orderStr;
    }

    public void resetOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public void removeOrderStr() {
        this.orderStr = null;
    }

    /**
     * 赋值指定对象属性
     *
     * @param fieldName
     * @return
     */
    public void setFieldValue(Object fieldName, Object obj) {
        Field field = null;
        try {
            String fn = fieldName.toString().toLowerCase();
            field = this.getClass().getDeclaredField(fn);
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            field.set(this, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回指定对象属性
     *
     * @param fieldName
     * @return
     */
    public Object getFieldValue(String fieldName) {
        Field field = null;
        try {
            field = this.getClass().getDeclaredField(fieldName);
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            return field.get(this);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 获取此对象所有属性名称
     *
     * @return
     */
    private List<String> getFields() {
        List<String> fieldList = new ArrayList<String>();
        Class cls = this.getClass();
        while (!cls.equals(BaseMapping.class)) {
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                if (!fieldList.contains(fieldName)) {
                    fieldList.add(fieldName);
                }
            }
            cls = cls.getSuperclass();
        }
        return fieldList;
    }
}
