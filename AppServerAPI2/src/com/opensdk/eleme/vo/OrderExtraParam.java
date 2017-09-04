package com.opensdk.eleme.vo;



/**
 * Created by chenbin on 17/02/05.
 */
public class OrderExtraParam {
    private String name; //订单项的名称
    private Float price; //金额（单位：元），金额分正负，比如优惠为负，配送费为正
    private String description; //说明
    private long id;           //id唯一标识这个实体（可能是活动，可能是打包费、配送费等）
    private int category_id; //唯一标识该订单项目的ID
    private int type;       //项目类型，不同category_id下的type会不一样
    private int quantity;  //数量

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderExtraParam [" +
                "name=" + name +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", category_id='" + category_id + '\'' +
                ", type=" + type +
                ", quantity=" + quantity +
                ']';
    }
}

