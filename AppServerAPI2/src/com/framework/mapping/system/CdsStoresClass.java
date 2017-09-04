package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

/**
 * User: chenbin
 * Date: 17-02-15
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
@Alias("CdsStoresClass")
@RsTable(describe = "商铺包含商品明细", name = "cds_stores_class")
public class CdsStoresClass extends BaseMapping {

    @RsTableField(describe = "主键", name = "stores_class_id", primaryKey = true)
    private int stores_class_id;

    @RsTableField(describe = "门店品牌ID", name = "stores_brand_id")
    private int stores_brand_id;

    @RsTableField(describe = "分类ID", name = "class_id")
    private int class_id;

    @RsTableField(describe = "饿了么类别绑定id", name = "food_category_id")
    private String  food_category_id;

    @RsTableField(describe = "美团分类绑定id", name = "meituan_cat_id")
    private String meituan_cat_id;

    @RsTableField(describe = "是否更新同步(1.已更新，0未更新 )", name = "type")
    private int type;

    @RsTableField(describe = "百度分类绑定id", name = "baidu_cat_id")
    private String  baidu_cat_id;

    @RsTableField(describe = "分类名称", name = "class_name")
    private String  class_name;

    @RsTableField(describe = "分类排序", name = "listorder")
    private int  listorder;

    @RsTableField(describe = "分类描述", name = "class_desc")
    private String  class_desc;

    @RsTableField(describe = "品牌id", name = "brand_id")
    private int  brand_id;

    public CdsStoresClass() {
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public int getListorder() {
        return listorder;
    }

    public void setListorder(int listorder) {
        this.listorder = listorder;
    }

    public String getClass_desc() {
        return class_desc;
    }

    public void setClass_desc(String class_desc) {
        this.class_desc = class_desc;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public CdsStoresClass(int stores_class_id, int stores_brand_id, int class_id, String food_category_id, String meituan_cat_id, int type, String baidu_cat_id) {
        this.stores_class_id = stores_class_id;
        this.stores_brand_id = stores_brand_id;
        this.class_id = class_id;
        this.food_category_id = food_category_id;
        this.meituan_cat_id = meituan_cat_id;
        this.type = type;
        this.baidu_cat_id = baidu_cat_id;
    }

    public int getStores_class_id() {
        return stores_class_id;
    }

    public void setStores_class_id(int stores_class_id) {
        this.stores_class_id = stores_class_id;
    }

    public int getStores_brand_id() {
        return stores_brand_id;
    }

    public void setStores_brand_id(int stores_brand_id) {
        this.stores_brand_id = stores_brand_id;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getFood_category_id() {
        return food_category_id;
    }

    public void setFood_category_id(String food_category_id) {
        this.food_category_id = food_category_id;
    }

    public String getMeituan_cat_id() {
        return meituan_cat_id;
    }

    public void setMeituan_cat_id(String meituan_cat_id) {
        this.meituan_cat_id = meituan_cat_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBaidu_cat_id() {
        return baidu_cat_id;
    }

    public void setBaidu_cat_id(String baidu_restaurant_id) {
        this.baidu_cat_id = baidu_cat_id;
    }
}
