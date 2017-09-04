package com.opensdk.eleme2.api.entity.product;

import java.util.*;

public class OItemIdWithSpecPrice{

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
     * 商品规格Id以及对应的价格
     */
    private Map<String,Double> priceMap;
    public Map<String,Double> getPriceMap() {
        return priceMap;
    }
    public void setPriceMap(Map<String,Double> priceMap) {
        this.priceMap = priceMap;
    }
    
}