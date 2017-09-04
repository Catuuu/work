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
@Alias("CdsOptions")
@RsTable(describe = "系统字典", name = "cds_options")
public class CdsOptions extends BaseMapping {
    @RsTableField(describe = "主键", name = "OPTION_ID", primaryKey = true)
    private int option_id;
    @RsTableField(describe = "键", name = "OPTION_NAME")
    private String option_name;
    @RsTableField(describe = "值", name = "OPTION_VALUE")
    private String option_value;
    @RsTableField(describe = "是否自动加载", name = "AUTOLOAD")
    private String autoload;
    @RsTableField(describe = "创建时间", name = "UPTIME")
    private String uptime;

    public int getOption_id() {
        return option_id;
    }

    public void setOption_id(int option_id) {
        this.option_id = option_id;
    }

    public String getOption_name() {
        return option_name;
    }

    public void setOption_name(String option_name) {
        this.option_name = option_name;
    }

    public String getOption_value() {
        return option_value;
    }

    public void setOption_value(String option_value) {
        this.option_value = option_value;
    }

    public String getAutoload() {
        return autoload;
    }

    public void setAutoload(String autoload) {
        this.autoload = autoload;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }
}
