package com.opensdk.eleme2.api.entity.order;

import java.util.*;

public class OGoodsItem{

    /**
     * 食物id
     */
    private long id;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    
    /**
     * 规格Id（根据篮子的类型取不同的值）
     */
    private Long skuId;
    public Long getSkuId() {
        return skuId;
    }
    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }
    
    /**
     * 商品名称
     */
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 订单中商品项的标识(注意，此处不是商品分类Id)
     */
    private long categoryId;
    public long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
    
    /**
     * 商品单价
     */
    private double price;
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
     * 商品数量
     */
    private int quantity;
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    /**
     * 总价
     */
    private double total;
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    
    /**
     * 多规格
     */
    private List<OGroupItemSpec> newSpecs;
    public List<OGroupItemSpec> getNewSpecs() {
        return newSpecs;
    }
    public void setNewSpecs(List<OGroupItemSpec> newSpecs) {
        this.newSpecs = newSpecs;
    }
    
    /**
     * 多属性
     */
    private List<OGroupItemAttribute> attributes;
    public List<OGroupItemAttribute> getAttributes() {
        return attributes;
    }
    public void setAttributes(List<OGroupItemAttribute> attributes) {
        this.attributes = attributes;
    }
    
    /**
     * 商品扩展码
     */
    private String extendCode;
    public String getExtendCode() {
        return extendCode;
    }
    public void setExtendCode(String extendCode) {
        this.extendCode = extendCode;
    }
    
    /**
     * 商品条形码
     */
    private String barCode;
    public String getBarCode() {
        return barCode;
    }
    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
    
    /**
     * 商品重量(单位克)
     */
    private Double weight;
    public Double getWeight() {
        return weight;
    }
    public void setWeight(Double weight) {
        this.weight = weight;
    }
    
}