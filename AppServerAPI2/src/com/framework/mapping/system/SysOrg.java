package com.framework.mapping.system;

import com.framework.annotation.*;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

/**
 * 系统机构对象
 * User: chenbin
 * Date: 12-12-31
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
@Alias("SysOrg")
@RsTable(name = "SYS_ORG", describe = "系统机构")
public class SysOrg extends BaseMapping {
    @RsTableField(name = "SO_ID", describe = "关键词", primaryKey = true)
    @RsTreeNode(tagName = RsTreeNodeTag.id)
    private String so_id;

    @RsTableField(name = "SO_TYPE", describe = "机构类型")
    private String so_type;

    @RsTableField(name = "SO_NAME", describe = "机构名称")
    @RsTreeNode(tagName = RsTreeNodeTag.text)
    private String so_name;

    @RsTableField(name = "SO_PARENTID", describe = "父机构ID")
    @RsTreeNode(tagName = RsTreeNodeTag.parentid)
    private String so_parentid;

    @RsTableField(name = "SO_ORDER", describe = "排序")
    private int so_order;

    @RsTableField(name = "SO_CODE", describe = "层级码")
    @RsTreeNode(tagName = RsTreeNodeTag.attributes)
    private String so_code;

    @RsTableField(name = "SO_CONTACT", describe = "联系人")
    private String so_contact;

    @RsTableField(name = "SO_TEL", describe = "联系电话")
    private String so_tel;

    @RsTableField(name = "SO_REMARK", describe = "备注")
    private String so_remark;


    @RsBeanField(describe = "图标")
    @RsTreeNode(tagName = RsTreeNodeTag.iconCls)
    private String so_icon;

    @RsBeanField(describe = "机构类型名称")
    private String so_type_name;

    public String getSo_id() {
        return so_id;
    }

    public void setSo_id(String so_id) {
        this.so_id = so_id;
    }

    public String getSo_type() {
        return so_type;
    }

    public void setSo_type(String so_type) {
        this.so_type = so_type;
    }

    public String getSo_name() {
        return so_name;
    }

    public void setSo_name(String so_name) {
        this.so_name = so_name;
    }

    public String getSo_parentid() {
        return so_parentid;
    }

    public void setSo_parentid(String so_parentid) {
        this.so_parentid = so_parentid;
    }

    public int getSo_order() {
        return so_order;
    }

    public void setSo_order(int so_order) {
        this.so_order = so_order;
    }

    public String getSo_code() {
        return so_code;
    }

    public void setSo_code(String so_code) {
        this.so_code = so_code;
    }

    public String getSo_contact() {
        return so_contact;
    }

    public void setSo_contact(String so_contact) {
        this.so_contact = so_contact;
    }

    public String getSo_tel() {
        return so_tel;
    }

    public void setSo_tel(String so_tel) {
        this.so_tel = so_tel;
    }

    public String getSo_remark() {
        return so_remark;
    }

    public void setSo_remark(String so_remark) {
        this.so_remark = so_remark;
    }

    public String getSo_icon() {
        return so_icon;
    }

    public void setSo_icon(String so_icon) {
        this.so_icon = so_icon;
    }

    public String getSo_type_name() {
        return so_type_name;
    }

    public void setSo_type_name(String so_type_name) {
        this.so_type_name = so_type_name;
    }
}
