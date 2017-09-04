package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

/**
 * 系统用户类型对象
 * User: chenbin
 * Date: 12-12-31
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
@Alias("SysUserType")
@RsTable(describe = "系统用户类型", name = "SYS_USER_TYPE")
public class SysUserType extends BaseMapping {
    @RsTableField(describe = "关键词", name = "SU_TYPE", primaryKey = true)
    private String su_type;
    @RsTableField(describe = "类型名称", name = "SU_TYPE_NAME")
    private String su_type_name;

    public String getSu_type() {
        return su_type;
    }

    public void setSu_type(String su_type) {
        this.su_type = su_type;
    }

    public String getSu_type_name() {
        return su_type_name;
    }

    public void setSu_type_name(String su_type_name) {
        this.su_type_name = su_type_name;
    }
}
