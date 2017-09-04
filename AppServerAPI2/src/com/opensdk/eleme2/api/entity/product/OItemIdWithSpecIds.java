package com.opensdk.eleme2.api.entity.product;

import java.util.*;

public class OItemIdWithSpecIds{

    /**
     * 商品Id
     */
    private long itemId;
    public long getItemId() {
        return itemId;
    }
    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
    
    /**
     * 商品规格Id的列表
     */
    private List<Long> itemSpecIds;
    public List<Long> getItemSpecIds() {
        return itemSpecIds;
    }
    public void setItemSpecIds(List<Long> itemSpecIds) {
        this.itemSpecIds = itemSpecIds;
    }
    
}