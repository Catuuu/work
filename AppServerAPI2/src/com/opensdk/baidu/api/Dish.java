package com.opensdk.baidu.api;

public class Dish {
	private String dish_id;//baidu 菜品唯一编号
	private String shop_id;//商户ID
    private String baidu_shop_id;//百度门店ID与shop_id二选一
	private String name;//菜品名称

    private String price;//菜品价格，单位：分
    private String pic;//菜品图片
    private String min_order_num;//最小起订份数
    private String package_box_num;//单份所需餐盒数

    private String description;//描述
//    private String available_times[];//可售时间(同营业时间);二维数组;
    private String stock;  //菜品每日库存,每日0点恢复库存
    private Category[] categorys;//分类信息;数组;参见下方body-category说明;

    private Norms[] norms; //菜品规格;数组;参见下方body-norms说明;
//    private String attr[];//菜品属性;数组;参见下方body-attr说明;


    public String getDish_id() {
        return dish_id;
    }

    public void setDish_id(String dish_id) {
        this.dish_id = dish_id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getBaidu_shop_id() {
        return baidu_shop_id;
    }

    public void setBaidu_shop_id(String baidu_shop_id) {
        this.baidu_shop_id = baidu_shop_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getMin_order_num() {
        return min_order_num;
    }

    public void setMin_order_num(String min_order_num) {
        this.min_order_num = min_order_num;
    }

    public String getPackage_box_num() {
        return package_box_num;
    }

    public void setPackage_box_num(String package_box_num) {
        this.package_box_num = package_box_num;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public Category[] getCategorys() {
        return categorys;
    }

    public void setCategorys(Category[] categorys) {
        this.categorys = categorys;
    }

    public Norms[] getNorms() {
        return norms;
    }

    public void setNorms(Norms[] norms) {
        this.norms = norms;
    }
}
