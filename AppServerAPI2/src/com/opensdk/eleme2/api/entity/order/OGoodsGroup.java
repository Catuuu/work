package com.opensdk.eleme2.api.entity.order;

import com.opensdk.eleme2.api.enumeration.order.OOrderDetailGroupType;

import java.util.*;

public class OGoodsGroup{

    /**
     * 分组名称
     */
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 分组类型
     */
    private OOrderDetailGroupType type;
    public OOrderDetailGroupType getType() {
        return type;
    }
    public void setType(OOrderDetailGroupType type) {
        this.type = type;
    }
    
    /**
     * 商品信息的列表
     */
    private List<OGoodsItem> items;
    public List<OGoodsItem> getItems() {
        return items;
    }
    public void setItems(List<OGoodsItem> items) {
        this.items = items;
    }
    
}