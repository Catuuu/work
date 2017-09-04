package com.opensdk.eleme2.api.entity.product;

public class OSpec{
    /**
     * 规格Id
     */
    private long specId;
    public long getSpecId() {
        return specId;
    }
    public void setSpecId(long specId) {
        this.specId = specId;
    }

    /**
     * 名称
     */
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 商品价格
     */
    private double price;
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * 库存量
     */
    private int stock;
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * 最大库存量
     */
    private int maxStock;
    public int getMaxStock() {
        return maxStock;
    }
    public void setMaxStock(int maxStock) {
        this.maxStock = maxStock;
    }

    /**
     * 包装费
     */
    private double packingFee;
    public double getPackingFee() {
        return packingFee;
    }
    public void setPackingFee(double packingFee) {
        this.packingFee = packingFee;
    }

    /**
     * 是否上架
     */
    private int onShelf;
    public int getOnShelf() {
        return onShelf;
    }
    public void setOnShelf(int onShelf) {
        this.onShelf = onShelf;
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
     * 商品重量，单位为克。如果商品的店铺的类型是新零售，那么该属性必选
     */
    private Integer weight;
    public Integer getWeight() {
        return weight;
    }
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     * 商品活动信息，1:有活动，0:无活动，活动商品不支持修改，如需修改请先取消活动，activityLevel字段只能查询，不能更新
     */
    private int activityLevel;
    public int getActivityLevel() {
        return activityLevel;
    }
    public void setActivityLevel(int activityLevel) {
        this.activityLevel = activityLevel;
    }

    /**
     * 配送链路配置
     */
    private OSupplyLink supplyLink;
    public OSupplyLink getSupplyLink() {
        return supplyLink;
    }
    public void setSupplyLink(OSupplyLink supplyLink) {
        this.supplyLink = supplyLink;
    }

}