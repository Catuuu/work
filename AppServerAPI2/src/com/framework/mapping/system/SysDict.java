package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

/**
 * 系统字典对象
 * User: chenbin
 * Date: 12-12-31
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
public class SysDict extends BaseMapping {
    @RsTableField(describe = "关键词", name = "SD_KEY", primaryKey = true)
    private String sd_key;
    @RsTableField(describe = "内容", name = "SD_VAL")
    private String sd_val;
    @RsTableField(describe = "备注", name = "SD_REMARK")
    private String sd_remark;
    @RsTableField(describe = "是否可修改", name = "SD_ISMODIFY")
    private String sd_ismodify;

    public String getSd_key() {
        return sd_key;
    }

    public void setSd_key(String sd_key) {
        this.sd_key = sd_key;
    }

    public String getSd_val() {
        return sd_val;
    }

    public void setSd_val(String sd_val) {
        this.sd_val = sd_val;
    }

    public String getSd_remark() {
        return sd_remark;
    }

    public void setSd_remark(String sd_remark) {
        this.sd_remark = sd_remark;
    }

    public String getSd_ismodify() {
        return sd_ismodify;
    }

    public void setSd_ismodify(String sd_ismodify) {
        this.sd_ismodify = sd_ismodify;
    }
}
