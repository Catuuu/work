package com.opensdk.eleme.vo;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by chenbin on 17/02/05.
 */
public class FoodParam {

    private Integer food_id;
    private String app_poi_code;
    private String app_food_code;
    private Integer operation;
    private String name;
    private String description;
    private Float price;
    private Integer min_order_count;
    private String unit;
    private Float box_num;
    private Float box_price;
    private String category_name;
    private Integer is_sold_out;
    private String picture;
    private Integer sequence;
//    private String skus;
    private List<FoodSkuParam> skus;
    private Float special_price;
    private Integer max_order_count;

    public Integer getFood_id() {
        return food_id;
    }

    public void setFood_id(Integer food_id) {
        this.food_id = food_id;
    }

    public String getApp_poi_code() {
        return app_poi_code;
    }

    public void setApp_poi_code(String app_poi_code) {
        this.app_poi_code = app_poi_code;
    }

    public String getApp_food_code() {
        return app_food_code;
    }

    public void setApp_food_code(String app_food_code) {
        this.app_food_code = app_food_code;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getMin_order_count() {
        return min_order_count;
    }

    public void setMin_order_count(Integer min_order_count) {
        this.min_order_count = min_order_count;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getBox_num() {
        return box_num;
    }

    public void setBox_num(Float box_num) {
        this.box_num = box_num;
    }

    public Float getBox_price() {
        return box_price;
    }

    public void setBox_price(Float box_price) {
        this.box_price = box_price;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public Integer getIs_sold_out() {
        return is_sold_out;
    }

    public void setIs_sold_out(Integer is_sold_out) {
        this.is_sold_out = is_sold_out;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public List<FoodSkuParam> getSkus() {
        return skus;
    }

    public void setSkus(List<FoodSkuParam> skus) {
        this.skus = skus;
    }

    public Float getSpecial_price() {
        return special_price;
    }

    public void setSpecial_price(Float special_price) {
        this.special_price = special_price;
    }

    public Integer getMax_order_count() {
        return max_order_count;
    }

    public void setMax_order_count(Integer max_order_count) {
        this.max_order_count = max_order_count;
    }

    public FoodParam() {
    }



    @Override
    public String toString() {
        return "FoodParam: [food_id=" + food_id + ", app_poi_code="
               + app_poi_code + ", app_food_code=" + app_food_code
               + ", operation=" + operation + ", name=" + name
               + ", description=" + description + ", price=" + price
               + ", min_order_count=" + min_order_count + ", unit=" + unit
               + ", box_num=" + box_num + ", box_price=" + box_price
               + ", category_name=" + category_name + ", is_sold_out="
               + is_sold_out + ", picture=" + picture + ", sequence="
               + sequence +", special_price="+special_price+", max_order_count="+max_order_count
               + ",skus=" + JSONObject.toJSONString(skus) + "]";

    }

    public String toStringVo(){
        return " app_food_code=" + app_food_code
               + "\n name=" + name
               + "\n description=" + description + "\n price=" + price
               + "\n min_order_count=" + min_order_count + "\n unit=" + unit
               + "\n box_num=" + box_num + "\n box_price=" + box_price
               + "\n category_name=" + category_name + "\n is_sold_out="
               + is_sold_out + "\n picture=" + picture + "\n sequence="
               + sequence +"\n special_price="+special_price+"\n max_order_count="+max_order_count
               + "\n skus=" + skus;
    }

}
