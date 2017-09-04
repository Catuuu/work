package com.opensdk.eleme.vo;

import java.util.List;

/**
 * Created by chenbin on 17/02/05.
 */
public class OrderFoodDetailParam {
    private int category_id; //分类ID
    private String name;   //售品名称
    private float price;   //售品价格（单位：元）
    private int id;        //售品ID
    private int quantity;  //售品数量
    private String tp_food_id; //第三方ID
    private List<String> specs; //规格名称
    //private List<OrderFoodDetailParam> garnish; // 就是浇头，可以添加到食物里，比如点一个荷包蛋，加到炒饭这个food的garnish里


    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTp_food_id() {
        return tp_food_id;
    }

    public void setTp_food_id(String tp_food_id) {
        this.tp_food_id = tp_food_id;
    }

    public List<String> getSpecs() {
        return specs;
    }

    public void setSpecs(List<String> specs) {
        this.specs = specs;
    }

   /* public List<OrderFoodDetailParam> getGarnish() {
        return garnish;
    }

    public void setGarnish(List<OrderFoodDetailParam> garnish) {
        this.garnish = garnish;
    }*/

    @Override
    public String toString() {
        return "OrderFoodDetailParam [" +
                "category_id='" + category_id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", price=" + price +
                ", id=" + id +
                ", quantity=" + quantity +
                ", tp_food_id=" + tp_food_id +
                ']';
    }
}
