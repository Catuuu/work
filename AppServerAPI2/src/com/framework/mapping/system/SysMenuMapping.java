package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

/**
 * 定义菜单对象
 * User: chenbin
 * Date: 12-12-31
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
@Alias("SysMenuMapping")
@RsTable(describe = "定义菜单", name = "SYS_MENU_MAPPING")
public class SysMenuMapping extends BaseMapping {
    @RsTableField(describe = "关键词", name = "SMM_ID", primaryKey = true)
    private String smm_id;
    @RsTableField(describe = "定义菜单ID", name = "SMD_ID")
    private String smd_id;
    @RsTableField(describe = "排序", name = "SMM_ORDER")
    private int smm_order;
    @RsTableField(describe = "功能菜单ID", name = "SMM_MENU_ID")
    private String smm_menu_id;

    public String getSmm_id() {
        return smm_id;
    }

    public void setSmm_id(String smm_id) {
        this.smm_id = smm_id;
    }

    public String getSmd_id() {
        return smd_id;
    }

    public void setSmd_id(String smd_id) {
        this.smd_id = smd_id;
    }

    public int getSmm_order() {
        return smm_order;
    }

    public void setSmm_order(int smm_order) {
        this.smm_order = smm_order;
    }

    public String getSmm_menu_id() {
        return smm_menu_id;
    }

    public void setSmm_menu_id(String smm_menu_id) {
        this.smm_menu_id = smm_menu_id;
    }
}
