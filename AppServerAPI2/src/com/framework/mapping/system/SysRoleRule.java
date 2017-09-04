package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

/**
 * 角色与规则对应关系对象
 * User: chenbin
 * Date: 12-12-31
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
@Alias("SysRoleRule")
@RsTable(describe = "角色对规则", name = "SYS_ROLE_RULE")
public class SysRoleRule extends BaseMapping {
    @RsTableField(describe = "关键词", name = "SRR_ID", primaryKey = true)
    private String srr_id;
    @RsTableField(describe = "角色ID", name = "SR_ID")
    private String sr_id;
    @RsTableField(describe = "菜单ID", name = "SRR_MENU_ID")
    private String srr_menu_id;
    @RsTableField(describe = "规则KEY", name = "SRR_RULE_KEY")
    private String srr_rule_key;
    @RsTableField(describe = "规则VALUE", name = "SRR_RULE_VALUE")
    private String srr_rule_value;

    public String getSrr_id() {
        return srr_id;
    }

    public void setSrr_id(String srr_id) {
        this.srr_id = srr_id;
    }

    public String getSr_id() {
        return sr_id;
    }

    public void setSr_id(String sr_id) {
        this.sr_id = sr_id;
    }

    public String getSrr_menu_id() {
        return srr_menu_id;
    }

    public void setSrr_menu_id(String srr_menu_id) {
        this.srr_menu_id = srr_menu_id;
    }

    public String getSrr_rule_key() {
        return srr_rule_key;
    }

    public void setSrr_rule_key(String srr_rule_key) {
        this.srr_rule_key = srr_rule_key;
    }

    public String getSrr_rule_value() {
        return srr_rule_value;
    }

    public void setSrr_rule_value(String srr_rule_value) {
        this.srr_rule_value = srr_rule_value;
    }
}
