package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

/**
 * 角色与功能对应关系对象
 * User: chenbin
 * Date: 12-12-31
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
@Alias("SysRoleOperation")
@RsTable(describe = "角色对功能", name = "SYS_ROLE_OPERATION")
public class SysRoleOperation extends BaseMapping {
    @RsTableField(describe = "关键词", name = "SRO_ID", primaryKey = true)
    private String sro_id;
    @RsTableField(describe = "角色ID", name = "SR_ID")
    private String sr_id;
    @RsTableField(describe = "菜单ID", name = "SRO_MENU_ID")
    private String sro_menu_id;
    @RsTableField(describe = "功能ID", name = "SRO_ACTION_ID")
    private String sro_action_id;

    public String getSro_id() {
        return sro_id;
    }

    public void setSro_id(String sro_id) {
        this.sro_id = sro_id;
    }

    public String getSr_id() {
        return sr_id;
    }

    public void setSr_id(String sr_id) {
        this.sr_id = sr_id;
    }

    public String getSro_menu_id() {
        return sro_menu_id;
    }

    public void setSro_menu_id(String sro_menu_id) {
        this.sro_menu_id = sro_menu_id;
    }

    public String getSro_action_id() {
        return sro_action_id;
    }

    public void setSro_action_id(String sro_action_id) {
        this.sro_action_id = sro_action_id;
    }
}
