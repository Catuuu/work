package com.opensdk.eleme2.api.entity.product;

import java.util.*;

public class OItem{

    /**
     * 商品描述
     */
    private String description;
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * 商品Id
     */
    private long id;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    
    /**
     * 商品名
     */
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 是否有效
     */
    private int isValid;
    public int getIsValid() {
        return isValid;
    }
    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }
    
    /**
     * 最近一个月的售出份数
     */
    private int recentPopularity;
    public int getRecentPopularity() {
        return recentPopularity;
    }
    public void setRecentPopularity(int recentPopularity) {
        this.recentPopularity = recentPopularity;
    }
    
    /**
     * 商品分类Id
     */
    private long categoryId;
    public long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
    
    /**
     * 店铺Id
     */
    private long shopId;
    public long getShopId() {
        return shopId;
    }
    public void setShopId(long shopId) {
        this.shopId = shopId;
    }
    
    /**
     * 店铺名称
     */
    private String shopName;
    public String getShopName() {
        return shopName;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    
    /**
     * 商品图片
     */
    private String imageUrl;
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    /**
     * 标签
     */
    private OLabel labels;
    public OLabel getLabels() {
        return labels;
    }
    public void setLabels(OLabel labels) {
        this.labels = labels;
    }
    
    /**
     * 规格的列表
     */
    private List<OSpec> specs;
    public List<OSpec> getSpecs() {
        return specs;
    }
    public void setSpecs(List<OSpec> specs) {
        this.specs = specs;
    }
    
    /**
     * 售卖时间
     */
    private OItemSellingTime sellingTime;
    public OItemSellingTime getSellingTime() {
        return sellingTime;
    }
    public void setSellingTime(OItemSellingTime sellingTime) {
        this.sellingTime = sellingTime;
    }
    
    /**
     * 属性
     */
    private List<OItemAttribute> attributes;
    public List<OItemAttribute> getAttributes() {
        return attributes;
    }
    public void setAttributes(List<OItemAttribute> attributes) {
        this.attributes = attributes;
    }
    
    /**
     * 后台类目ID
     */
    private long backCategoryId;
    public long getBackCategoryId() {
        return backCategoryId;
    }
    public void setBackCategoryId(long backCategoryId) {
        this.backCategoryId = backCategoryId;
    }
    
}