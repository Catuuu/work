package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * User: chenbin
 * Date: 17-02-15
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
@Alias("CdsMsGoodsList")
@RsTable(describe = "商品销售(订单商品)明细", name = "cds_ms_goods_list")
public class CdsMsGoodsList extends BaseMapping {

    @RsTableField(describe = "主键", name = "good_list_id", primaryKey = true)
    private String good_list_id;

    @RsTableField(describe = "商铺ID", name = "stores_id")
    private int stores_id;

    @RsTableField(describe = "订单ID(订单编号)", name = "order_id")
    private String order_id;

    @RsTableField(describe = "后台分类ID", name = "class_id")
    private int class_id;

    @RsTableField(describe = "后台单菜品商品ID", name = "good_id")
    private int good_id;

    @RsTableField(describe = "原商品名称", name = "good_name")
    private String good_name;

    @RsTableField(describe = "后台商品名称", name = "class_good_name")
    private String class_good_name;

    @RsTableField(describe = "规格ID", name = "standard_id")
    private int standard_id;

    @RsTableField(describe = "规格名称", name = "standard_name")
    private String standard_name;

    @RsTableField(describe = "销售时间", name = "sale_time")
    private Date sale_time;

    @RsTableField(describe = "商品销售金额", name = "price")
    private float price;

    @RsTableField(describe = "打包时间", name = "pack_time")
    private Date pack_time;

    @RsTableField(describe = "份数", name = "count")
    private int count;

    @RsTableField(describe = "排序", name = "listorder")
    private int  listorder;

    @RsTableField(describe = "出餐机id", name = "cm_id")
    private int  cm_id;

    @RsTableField(describe = "品牌id", name = "brand_id")
    private int  brand_id;

    private int good_type; //商品类型

    public int getCm_id() {return cm_id; }

    public void setCm_id(int cm_id) {this.cm_id = cm_id; }

    public String getGood_list_id() {
        return good_list_id;
    }

    public void setGood_list_id(String good_list_id) {
        this.good_list_id = good_list_id;
    }

    public int getStores_id() {
        return stores_id;
    }

    public void setStores_id(int stores_id) {
        this.stores_id = stores_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public int getGood_id() {
        return good_id;
    }

    public void setGood_id(int good_id) {
        this.good_id = good_id;
    }

    public String getGood_name() {
        return good_name;
    }

    public void setGood_name(String good_name) {
        this.good_name = good_name;
    }

    public String getClass_good_name() {
        return class_good_name;
    }

    public void setClass_good_name(String class_good_name) {
        this.class_good_name = class_good_name;
    }

    public int getStandard_id() {
        return standard_id;
    }

    public void setStandard_id(int standard_id) {
        this.standard_id = standard_id;
    }

    public String getStandard_name() {
        return standard_name;
    }

    public void setStandard_name(String standard_name) {
        this.standard_name = standard_name;
    }

    public Date getSale_time() {
        return sale_time;
    }

    public void setSale_time(Date sale_time) {
        this.sale_time = sale_time;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getPack_time() {
        return pack_time;
    }

    public void setPack_time(Date pack_time) {
        this.pack_time = pack_time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getListorder() {
        return listorder;
    }

    public void setListorder(int listorder) {
        this.listorder = listorder;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public int getGood_type() {
        return good_type;
    }

    public void setGood_type(int good_type) {
        this.good_type = good_type;
    }
}
