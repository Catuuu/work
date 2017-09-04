package com.opensdk.eleme2.api.entity.product;

import java.util.*;

public class OBackCategory{

    /**
     * 类目Id
     */
    private long id;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    
    /**
     * 类目名称
     */
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 父节点ID
     */
    private long parentId;
    public long getParentId() {
        return parentId;
    }
    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
    
    /**
     * 类目级别
     */
    private int lev;
    public int getLev() {
        return lev;
    }
    public void setLev(int lev) {
        this.lev = lev;
    }
    
    /**
     * 子节点
     */
    private List<OBackCategory> children;
    public List<OBackCategory> getChildren() {
        return children;
    }
    public void setChildren(List<OBackCategory> children) {
        this.children = children;
    }
    
    /**
     * 是否叶子节点
     */
    private boolean leaf;
    public boolean getLeaf() {
        return leaf;
    }
    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }
    
}