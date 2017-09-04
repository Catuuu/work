package com.opensdk.eleme2.api.entity.product;

import java.util.*;

public class OCategory{

    /**
     * 商品分类Id
     */
    private long id;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    
    /**
     * 商品分类名称
     */
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 商品分类描述
     */
    private String description;
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * 是否有效
     */
    private int isValid;
    public int getIsValid() {
        return isValid;
    }
    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }
    
    /**
     * 一级分类父分类ID为0，二级分类父分类ID为一级分类ID
     */
    private long parentId;
    public long getParentId() {
        return parentId;
    }
    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
    
    /**
     * 下级分类信息
     */
    private List<OCategory> children;
    public List<OCategory> getChildren() {
        return children;
    }
    public void setChildren(List<OCategory> children) {
        this.children = children;
    }
    
}