package com.framework.mapping;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 *
 * User: chenbin
 * Date: 12-12-31
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
@Alias("CdsIncreaseOrder")
@RsTable(describe = "店铺当前的订单数据", name = "CdsIncreaseOrder")
public class CdsIncreaseOrder extends BaseMapping {

    @RsTableField(describe = "商铺ID", name = "stores_id")
    private int stores_id;

    @RsTableField(describe = "门店品牌ID", name = "stores_brand_id")
    private int stores_brand_id;

    @RsTableField(describe = "品牌ID", name = "brand_id")
    private int brand_id;

    @RsTableField(describe = "来源", name = "fromin")
    private String fromin;

    @RsTableField(describe = "总的订单数量", name = "all_count")
    private int all_count;

    @RsTableField(describe = "当前订单最大流水号", name = "max_num")
    private int max_num;

    @RsTableField(describe = "日期", name = "cday")
    private String cday;

    @RsTableField(describe = "最新单与系统相差秒数", name = "dfsecond")
    private int dfsecond;

    public int getStores_id() {
        return stores_id;
    }

    public void setStores_id(int stores_id) {
        this.stores_id = stores_id;
    }

    public String getFromin() {
        return fromin;
    }

    public void setFromin(String fromin) {
        this.fromin = fromin;
    }

    public int getAll_count() {
        return all_count;
    }

    public void setAll_count(int all_count) {
        this.all_count = all_count;
    }

    public int getMax_num() {
        return max_num;
    }

    public void setMax_num(int max_num) {
        this.max_num = max_num;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public int getStores_brand_id() {
        return stores_brand_id;
    }

    public void setStores_brand_id(int stores_brand_id) {
        this.stores_brand_id = stores_brand_id;
    }

    public String getCday() {
        return cday;
    }

    public void setCday(String cday) {
        this.cday = cday;
    }

    public int getDfsecond() {
        return dfsecond;
    }

    public void setDfsecond(int dfsecond) {
        this.dfsecond = dfsecond;
    }
}
