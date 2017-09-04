package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.annotation.RsTreeNode;
import com.framework.annotation.RsTreeNodeTag;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

/**
 * 系统角色对象
 * User: chenbin
 * Date: 12-12-31
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
@Alias("SysRole")
@RsTable(describe = "系统角色", name = "SYS_ROLE")
public class SysRole extends BaseMapping {
    @RsTableField(describe = "关键词", name = "SR_ID", primaryKey = true)
    @RsTreeNode(tagName = RsTreeNodeTag.id)
    private String sr_id;

    @RsTableField(describe = "机构ID", name = "SO_ID")
    private String so_id;

    @RsTableField(describe = "角色名称", name = "SR_NAME")
    @RsTreeNode(tagName = RsTreeNodeTag.text)
    private String sr_name;

    @RsTableField(describe = "备注", name = "SR_REMARK")
    private String sr_remark;

    @RsTreeNode(tagName = RsTreeNodeTag.parentid, defaultValue = "0")
    private String parentid;

    @RsTreeNode(tagName = RsTreeNodeTag.checked, defaultValue = "false")
    private String checked;

    @RsTreeNode(tagName = RsTreeNodeTag.iconCls, defaultValue = "icon-blank")
    private String iconCls;

    public String getSr_id() {
        return sr_id;
    }

    public void setSr_id(String sr_id) {
        this.sr_id = sr_id;
    }

    public String getSo_id() {
        return so_id;
    }

    public void setSo_id(String so_id) {
        this.so_id = so_id;
    }

    public String getSr_name() {
        return sr_name;
    }

    public void setSr_name(String sr_name) {
        this.sr_name = sr_name;
    }

    public String getSr_remark() {
        return sr_remark;
    }

    public void setSr_remark(String sr_remark) {
        this.sr_remark = sr_remark;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }
}
