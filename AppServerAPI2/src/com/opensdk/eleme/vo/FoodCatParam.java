package com.opensdk.eleme.vo;

/**
 * Created by chenbin on 17/02/05.
 */
public class FoodCatParam {
    private String food_category_id;
    private String is_valid;
    private int weight;
    private String name;
    private String mstatus;

    public String getFood_category_id() {
        return food_category_id;
    }

    public void setFood_category_id(String food_category_id) {
        this.food_category_id = food_category_id;
    }

    public String getIs_valid() {
        return is_valid;
    }

    public void setIs_valid(String is_valid) {
        this.is_valid = is_valid;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getMstatus() {
        return mstatus;
    }

    public void setMstatus(String mstatus) {
        this.mstatus = mstatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
