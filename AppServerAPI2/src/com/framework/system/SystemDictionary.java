package com.framework.system;

import com.framework.dao.SqlDao;
import com.framework.mapping.system.CdsOptions;
import com.framework.mapping.system.SysDict;
import com.framework.util.BeanUtil;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import java.util.*;

/**
 * 系统字典操作对象
 * User: chenbin
 * Date: 12-12-31
 * Time: 下午1:56
 */
public class SystemDictionary {
    private static SystemDictionary systemParameter = new SystemDictionary();

    private SystemDictionary() {
    }

    public static SystemDictionary getInstance() {
        return systemParameter;
    }

    private Map<String, SysDict> parameterMap;
    private List<SysDict> parameterList;
    private SqlDao sqlDao;
    private long timestamp;

    /**
     * 装载数据库参数
     */
    public void init(SqlDao sqlDao) {
        if (parameterMap == null) {
            parameterMap = new HashMap<String, SysDict>();
        }
        if (parameterList == null) {
            parameterList = new ArrayList<SysDict>();
        }

        this.sqlDao = sqlDao;

        CdsOptions cdsOptions = new CdsOptions();
        cdsOptions.setOption_name("site_options");
        cdsOptions = sqlDao.getRecord("sys_dict.getRecord",cdsOptions);
        Map configMap = BeanUtil.createBean(cdsOptions.getOption_value(),HashMap.class);

        Iterator entries = configMap.entrySet().iterator();

        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String key = (String)entry.getKey();
            String value = "";
            if(entry.getValue()!=null){
                value = entry.getValue().toString();
            }

            SysDict sysDict = new SysDict();
            sysDict.setSd_key(key);
            sysDict.setSd_val(value);
            parameterMap.put(key, sysDict);

        }
    }

    /**
     * 字典总数量
     *
     * @return
     */
    public int size() {
        return parameterMap.size();
    }

    /**
     * 字典是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return parameterMap.isEmpty();
    }

    /**
     * 是否存在 key
     *
     * @param key
     * @return
     */
    public boolean containsKey(String key) {
        return parameterMap.containsKey(key);
    }

    /**
     * 是否存在 value
     *
     * @param value
     * @return
     */
    public boolean containsValue(String value) {
        for (SysDict sysDict : parameterList) {
            if (sysDict.getSd_val().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 得到字典集合
     *
     * @return
     */
    public List<SysDict> getParameterList() {
        return parameterList;
    }

    /**
     * 得到字符串的字典值，不存在把默认值保存至数据库
     *
     * @param key          键
     * @param defaultValue 默认值
     * @param sd_remark    备注
     * @return 数据库存在值返回配置值，否则返回默认值
     */
    public String getProperty(String key, String defaultValue, String sd_remark) {
        SysDict sysDict = parameterMap.get(key);
        if (sysDict == null) {
            sysDict = new SysDict();
            sysDict.setSd_key(key);
            sysDict.setSd_val(defaultValue);
            sysDict.setSd_remark(sd_remark);
            sysDict.setSd_ismodify("T");
            insertProperty(sysDict);
            return defaultValue;
        } else {
            return sysDict.getSd_val();
        }
    }

    /**
     * 得到字符串的字典值，不存在把默认值保存至数据库
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 数据库存在值返回配置值，否则返回默认值
     */
    public String getProperty(String key, String defaultValue) {
        return getProperty(key, defaultValue, "");
    }

    /**
     * 得到字符串的字典值
     *
     * @param key
     * @return 数据库存在返回配置值，否则返回NULl
     */
    public String getProperty(String key) {
        SysDict sysDict = parameterMap.get(key);
        if (sysDict != null && sysDict.getSd_val() != null) {
            return sysDict.getSd_val();
        } else {
            return null;
        }
    }

    /**
     * 得到boolean 值的属性，不存在把值保存在数据库
     *
     * @param key          键
     * @param defaultValue 默认值
     * @param sd_remark    备注
     * @return 数据库存在值, 且值为boolean类型，返回配置值，否则返回默认值, 默认值保存至数据库
     */
    public boolean getBooleanProperty(String key, boolean defaultValue, String sd_remark) {
        SysDict sysDict = parameterMap.get(key);
        if (sysDict == null) {
            sysDict = new SysDict();
            sysDict.setSd_key(key);
            sysDict.setSd_val(Boolean.toString(defaultValue));
            sysDict.setSd_remark(sd_remark);
            sysDict.setSd_ismodify("T");
            insertProperty(sysDict);
            return defaultValue;
        } else {
            return Boolean.valueOf(sysDict.getSd_val());
        }
    }

    /**
     * 得到boolean 值的属性，不存在把值保存在数据库
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 数据库存在值, 且值为boolean类型，返回配置值，否则返回默认值, 默认值保存至数据库
     */
    public boolean getBooleanProperty(String key, boolean defaultValue) {
        return getBooleanProperty(key, defaultValue, "");
    }

    /**
     * 得到boolean 值的属性
     *
     * @param key
     * @return 数据库存在值, 且值为boolean类型，返回配置值，否则返回false
     */
    public boolean getBooleanProperty(String key) {
        SysDict sysDict = parameterMap.get(key);
        if (sysDict != null && sysDict.getSd_val() != null) {
            return Boolean.valueOf(sysDict.getSd_val());
        }
        return false;
    }

    /**
     * 得到一个字典对象
     *
     * @param key
     * @return
     */
    public SysDict getObjectProperty(String key) {
        return parameterMap.get(key);
    }


    /**
     * 插入一个新的字典值
     *
     * @param sysDict
     */
    public void insertProperty(SysDict sysDict) {
        sqlDao.insertRecord("sys_dict.insertRecord", sysDict);
        parameterMap.put(sysDict.getSd_key(), sysDict);
        parameterList = sqlDao.getRecordList("sys_dict.getRecordList");
    }

    /**
     * 修改一个字典值
     *
     * @param sysDict
     */
    public void updateProperty(SysDict sysDict) {
        sqlDao.updateRecord("sys_dict.updateRecord", sysDict);
        parameterMap.put(sysDict.getSd_key(), sysDict);
        parameterList = sqlDao.getRecordList("sys_dict.getRecordList");
    }

    /**
     * 删除一个字典值
     *
     * @param sysDict
     */
    public void deleteProperty(SysDict sysDict) {
        sqlDao.deleteRecord("sys_dict.deleteRecord", sysDict);
        parameterMap.remove(sysDict.getSd_key());
        parameterList = sqlDao.getRecordList("sys_dict.getRecordList");
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
