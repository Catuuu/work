package com.opensdk.meituan.vo;

/**
 * Created by chenbin on 17/02/05.
 */
public class OrderFoodDetailParam {
    private String app_food_code;
    private String food_name;
    private Integer quantity;       // 菜品数量
    private Float price;
    private Float box_num;          // 餐盒数量
    private Float box_price;        // 餐盒价格
    private String unit;
    private Float food_discount;
    private String sku_id;
    private String food_property;   // 菜品属性
    private String spec;            // 规格名称

    public String getApp_food_code() {
        return app_food_code;
    }

    public void setApp_food_code(String app_food_code) {
        this.app_food_code = app_food_code;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getFood_discount() {
        return food_discount;
    }

    public void setFood_discount(Float food_discount) {
        this.food_discount = food_discount;
    }

    public String getSku_id() {
        return sku_id;
    }

    public void setSku_id(String sku_id) {
        this.sku_id = sku_id;
    }

    public String getFood_property() {
        return food_property;
    }

    public void setFood_property(String food_property) {
        this.food_property = food_property;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    @Override
    public String toString() {
        return "OrderFoodDetailParam [" +
                "app_food_code='" + app_food_code + '\'' +
                ", food_name='" + food_name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", box_num=" + box_num +
                ", box_price=" + box_price +
                ", unit='" + unit + '\'' +
                ", food_discount=" + food_discount +
                ", sku_id='" + sku_id + '\'' +
                ", food_property='" + food_property + '\'' +
                ", spec='" + spec + '\'' +
                ']';
    }
}
