package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

/**
 * 系统用户对应角色对象
 * User: chenbin
 * Date: 12-12-31
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
@Alias("SysUserRole")
@RsTable(describe = "系统用户对应角色", name = "SYS_USER_ROLE")
public class SysUserRole extends BaseMapping {
    @RsTableField(describe = "关键词", name = "SUR_ID", primaryKey = true)
    private String sur_id;
    @RsTableField(describe = "角色ID", name = "SR_ID")
    private String sr_id;
    @RsTableField(describe = "用户ID", name = "SU_ID")
    private String su_id;

    public String getSur_id() {
        return sur_id;
    }

    public void setSur_id(String sur_id) {
        this.sur_id = sur_id;
    }

    public String getSr_id() {
        return sr_id;
    }

    public void setSr_id(String sr_id) {
        this.sr_id = sr_id;
    }

    public String getSu_id() {
        return su_id;
    }

    public void setSu_id(String su_id) {
        this.su_id = su_id;
    }
}
