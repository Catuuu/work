package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import com.framework.mapping.CdsIncreaseOrder;
import org.apache.ibatis.type.Alias;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenbin on 2017/2/16.
 * 打印机表
 */
@Alias("CdsStoresBrand")
@RsTable(describe = "门店品牌明细", name = "cds_stores_brand")
public class CdsStoresBrand extends BaseMapping {

    @RsTableField(describe = "主键", name = "stores_brand_id", primaryKey = true)
    private int stores_brand_id;

    @RsTableField(describe = "品牌ID", name = "brand_id")
    private int brand_id;

    @RsTableField(describe = "商铺ID", name = "stores_id")
    private int stores_id;

    @RsTableField(describe = "品牌商铺名称", name = "stores_name")
    private String stores_name;

    @RsTableField(describe = "饿了么门店Id", name = "elem_restaurant_id")
    private String elem_restaurant_id;

    @RsTableField(describe = "饿了么订餐地址", name = "elem_url")
    private String elem_url;

    @RsTableField(describe = "美团门店Id", name = "meituan_restaurant_id")
    private String meituan_restaurant_id;

    @RsTableField(describe = "美团订餐地址", name = "meituan_url")
    private String meituan_url;

    @RsTableField(describe = "百度门店Id", name = "baidu_restaurant_id")
    private String baidu_restaurant_id;

    @RsTableField(describe = "百度订餐地址", name = "baidu_url")
    private String baidu_url;

    @RsTableField(describe = "客服电话", name = "service_phone")
    private String service_phone;

    @RsTableField(describe = "配送方式", name = "dispatch_extinfo")
    private String dispatch_extinfo;

    private String brand_name;
    public String getBrand_name() {
        return brand_name;
    }
    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }


    public String getDispatch_extinfo() {
        return dispatch_extinfo;
    }

    public void setDispatch_extinfo(String dispatch_extinfo) {
        this.dispatch_extinfo = dispatch_extinfo;
    }

    private List<CdsIncreaseOrder> increaseOrderList;

    public List<CdsIncreaseOrder> getIncreaseOrderList() {
        return increaseOrderList;
    }

    public void addCdsIncreaseOrder(CdsIncreaseOrder inc) {
        if (this.increaseOrderList == null) {
            this.increaseOrderList = new ArrayList<CdsIncreaseOrder>();
        }
        this.increaseOrderList.add(inc);
    }

    public String getService_phone() {
        return service_phone;
    }

    public void setService_phone(String service_phone) {
        this.service_phone = service_phone;
    }

    public int getStores_brand_id() {
        return stores_brand_id;
    }

    public void setStores_brand_id(int stores_brand_id) {
        this.stores_brand_id = stores_brand_id;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public int getStores_id() {
        return stores_id;
    }

    public void setStores_id(int stores_id) {
        this.stores_id = stores_id;
    }

    public String getStores_name() {
        return stores_name;
    }

    public void setStores_name(String stores_name) {
        this.stores_name = stores_name;
    }

    public String getElem_url() {
        return elem_url;
    }

    public void setElem_url(String elem_url) {
        this.elem_url = elem_url;
    }

    public String getMeituan_url() {
        return meituan_url;
    }

    public void setMeituan_url(String meituan_url) {
        this.meituan_url = meituan_url;
    }

    public String getElem_restaurant_id() {
        return elem_restaurant_id;
    }

    public void setElem_restaurant_id(String elem_restaurant_id) {
        this.elem_restaurant_id = elem_restaurant_id;
    }

    public String getMeituan_restaurant_id() {
        return meituan_restaurant_id;
    }

    public void setMeituan_restaurant_id(String meituan_restaurant_id) {
        this.meituan_restaurant_id = meituan_restaurant_id;
    }

    public String getBaidu_restaurant_id() {
        return baidu_restaurant_id;
    }

    public void setBaidu_restaurant_id(String baidu_restaurant_id) {
        this.baidu_restaurant_id = baidu_restaurant_id;
    }

    public String getBaidu_url() {
        return baidu_url;
    }

    public void setBaidu_url(String baidu_url) {
        this.baidu_url = baidu_url;
    }
}
