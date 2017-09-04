package com.framework.resource.action;

import com.framework.annotation.RsBean;
import com.framework.annotation.RsBeanField;
import com.framework.annotation.RsTreeNode;
import com.framework.annotation.RsTreeNodeTag;
import com.framework.mapping.SerializeCloneable;

/**
 * 动作资源
 * .
 * User: chenbin
 * Date: 13-3-7
 * Time: 下午6:27
 * To change this template use File | Settings | File Templates.
 */
@RsBean(describe = "动作资源对象")
public class ActionResource extends SerializeCloneable {
    @RsBeanField(describe = "资源ID")
    @RsTreeNode(tagName = RsTreeNodeTag.id)
    private String id;
    @RsBeanField(describe = "资源名称")
    @RsTreeNode(tagName = RsTreeNodeTag.text)
    private String name;
    @RsBeanField(describe = "资源图标")
    @RsTreeNode(tagName = RsTreeNodeTag.iconCls)
    private String icon;
    @RsBeanField(describe = "资源父节点ID")
    @RsTreeNode(tagName = RsTreeNodeTag.parentid)
    private String parentid;
    @RsBeanField(describe = "资源排序")
    private int order;
    @RsBeanField(describe = "资源URL")
    @RsTreeNode(tagName = RsTreeNodeTag.attributes)
    private String url;
    @RsBeanField(describe = "资源备注信息")
    private String remark;
    @RsTreeNode(tagName = RsTreeNodeTag.nodeType)
    private String nodeType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }
}
