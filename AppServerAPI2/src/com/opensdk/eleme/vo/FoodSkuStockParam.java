package com.opensdk.eleme.vo;

import java.util.List;

/**
 * Created by chenbin on 17/02/05.
 */
public class FoodSkuStockParam {
    String app_food_code;
    List<skuStockParam> skus;

    public String getApp_food_code() {
        return app_food_code;
    }

    public void setApp_food_code(String app_food_code) {
        this.app_food_code = app_food_code;
    }

    public List<skuStockParam> getSkus() {
        return skus;
    }

    public void setSkus(List<skuStockParam> skus) {
        this.skus = skus;
    }
}
