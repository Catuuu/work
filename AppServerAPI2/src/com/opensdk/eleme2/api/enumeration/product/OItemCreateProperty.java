package com.opensdk.eleme2.api.enumeration.product;

public enum OItemCreateProperty {
    /**
     * 商品名称
     */
    name("name"),

    /**
     * 商品描述
     */
    description("description"),

    /**
     * 图片imageHash
     */
    imageHash("imageHash"),

    /**
     * 标签属性集合
     */
    labels("labels"),

    /**
     * 规格
     */
    specs("specs"),

    /**
     * 售卖时间
     */
    sellingTime("sellingTime"),

    /**
     * 属性
     */
    attributes("attributes"),

    /**
     * 后台类目ID
     */
    backCategoryId("backCategoryId"),

    /**
     * 商品最小起购量
     */
    minPurchaseQuantity("minPurchaseQuantity"),

    /**
     * 商品单位
     */
    unit("unit");

    private String productDesc;
    OItemCreateProperty(String productDesc) {
        this.productDesc = productDesc;
    }
}