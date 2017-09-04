package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.annotation.RsTreeNode;
import com.framework.annotation.RsTreeNodeTag;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

/**
 * 定义菜单对象
 * User: chenbin
 * Date: 12-12-31
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
@Alias("SysMenuDefine")
@RsTable(describe = "定义菜单", name = "SYS_MENU_DEFINE")
public class SysMenuDefine extends BaseMapping {
    @RsTableField(describe = "关键词", name = "SMD_ID", primaryKey = true)
    @RsTreeNode(tagName = RsTreeNodeTag.id)
    private String smd_id;

    @RsTableField(describe = "菜单名称", name = "SMD_NAME")
    @RsTreeNode(tagName = RsTreeNodeTag.text)
    private String smd_name;

    @RsTableField(describe = "父菜单ID", name = "SMD_PARENTID")
    @RsTreeNode(tagName = RsTreeNodeTag.parentid)
    private String smd_parentid;

    @RsTableField(describe = "排序", name = "SMD_ORDER")
    private int smd_order;

    @RsTableField(describe = "系统图标", name = "SMD_ICON")
    @RsTreeNode(tagName = RsTreeNodeTag.iconCls)
    private String smd_icon;

    @RsTreeNode(tagName = RsTreeNodeTag.nodeType)
    private String nodeType;

    /**
     *
     * @param smd_id
     * @param smd_name
     * @param smd_parentid
     * @param smd_order
     * @param smd_icon
     * @param nodeType
     */
    public SysMenuDefine(String smd_id,String smd_name,String smd_parentid,int smd_order,String smd_icon,String nodeType){
        super();
        this.smd_id = smd_id;
        this.smd_name = smd_name;
        this.smd_parentid = smd_parentid;
        this.smd_order = smd_order;
        this.smd_icon= smd_icon;
        this.nodeType = nodeType;
    }

    public  SysMenuDefine(){
        super();
    }

    public String getSmd_id() {
        return smd_id;
    }

    public void setSmd_id(String smd_id) {
        this.smd_id = smd_id;
    }

    public String getSmd_name() {
        return smd_name;
    }

    public void setSmd_name(String smd_name) {
        this.smd_name = smd_name;
    }

    public String getSmd_parentid() {
        return smd_parentid;
    }

    public void setSmd_parentid(String smd_parentid) {
        this.smd_parentid = smd_parentid;
    }

    public int getSmd_order() {
        return smd_order;
    }

    public void setSmd_order(int smd_order) {
        this.smd_order = smd_order;
    }

    public String getSmd_icon() {
        return smd_icon;
    }

    public void setSmd_icon(String smd_icon) {
        this.smd_icon = smd_icon;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }
}
