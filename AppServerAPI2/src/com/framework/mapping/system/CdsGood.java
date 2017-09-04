package com.framework.mapping.system;

/**
 * Created by Administrator on 2017/2/14.
 * 订单商品
 */
public class CdsGood {

    private long class_id;      //分类ID
    private String good_name;  //商品名称
    private float price;       //商品价格
    private int quantity;      //商品数量
    private long id;            //第三方平台（饿了么、百度、美团）商品ID
    private long good_id;       //内部商品ID
    private int good_type;     //商品类型 (1、正常订餐  2、赠品 3、用户加餐)
    private String note;       //商品备注
    private String spec;       //规格

    public long getClass_id() {
        return class_id;
    }

    public void setClass_id(long class_id) {
        this.class_id = class_id;
    }

    public String getGood_name() {
        return good_name;
    }

    public void setGood_name(String good_name) {
        this.good_name = good_name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGood_id() {
        return good_id;
    }

    public void setGood_id(long good_id) {
        this.good_id = good_id;
    }

    public int getGood_type() {
        return good_type;
    }

    public void setGood_type(int good_type) {
        this.good_type = good_type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }
}
