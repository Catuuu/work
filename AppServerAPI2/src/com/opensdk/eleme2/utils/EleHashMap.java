package com.opensdk.eleme2.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 纯字符串字典
 */
public class EleHashMap extends HashMap<String, String> {

    public EleHashMap() {
    }

    public EleHashMap(Map<? extends String, ? extends String> m) {
        super(m);
    }

    public String put(String key, Object value) {
        String strValue;

        if (value == null) {
            strValue = null;
        } else if (value instanceof String) {
            strValue = (String) value;
        } else if (value instanceof Integer) {
            strValue = ((Integer) value).toString();
        } else if (value instanceof Long) {
            strValue = ((Long) value).toString();
        } else if (value instanceof Float) {
            strValue = ((Float) value).toString();
        } else if (value instanceof Double) {
            strValue = ((Double) value).toString();
        } else if (value instanceof Boolean) {
            strValue = ((Boolean) value).toString();
        } else if (value instanceof Date) {
            strValue = StringUtils.formatDateTime((Date) value);
        } else {
            strValue = value.toString();
        }
        return this.put(key, strValue);
    }

    public String put(String key, String value) {
        if (StringUtils.areNotEmpty(key, value)) {
            return super.put(key, value);
        } else {
            return null;
        }
    }
}
