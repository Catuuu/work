package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

/**
 * 机构类型对象
 * User: chenbin
 * Date: 12-12-31
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
@Alias("SysOrgType")
@RsTable(describe = "机构类型", name = "SYS_ORG_TYPE")
public class SysOrgType extends BaseMapping {
    @RsTableField(describe = "关键词", name = "SO_TYPE", primaryKey = true)
    private String so_type;
    @RsTableField(describe = "类型名称", name = "SO_TYPE_NAME")
    private String so_type_name;
    @RsTableField(describe = "图标", name = "SO_ICON")
    private String so_icon;

    public String getSo_type() {
        return so_type;
    }

    public void setSo_type(String so_type) {
        this.so_type = so_type;
    }

    public String getSo_type_name() {
        return so_type_name;
    }

    public void setSo_type_name(String so_type_name) {
        this.so_type_name = so_type_name;
    }

    public String getSo_icon() {
        return so_icon;
    }

    public void setSo_icon(String so_icon) {
        this.so_icon = so_icon;
    }
}
