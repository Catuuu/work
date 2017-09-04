package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 *
 * User: chenbin
 * Date: 12-12-31
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
@Alias("CdsNosetGoodsList")
@RsTable(describe = "未配置商品信息列表", name = "cds_noset_goods_list")
public class CdsNosetGoodsList extends BaseMapping {

    @RsTableField(describe = "主键", name = "ngl_id", primaryKey = true)
    private String ngl_id;

    @RsTableField(describe = "订单ID(订单编号)", name = "order_id")
    private String order_id;

    @RsTableField(describe = "商品名称", name = "good_name")
    private String good_name;

    @RsTableField(describe = "商品详情json", name = "good_info")
    private String good_info;

    @RsTableField(describe = "订单日期", name = "create_date")
    private Date create_date;

    @RsTableField(describe = "商铺ID", name = "stores_id")
    private int stores_id;

    @RsTableField(describe = "品牌ID", name = "brand_id")
    private int brand_id;

    public String getNgl_id() {
        return ngl_id;
    }

    public void setNgl_id(String ngl_id) {
        this.ngl_id = ngl_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getGood_name() {
        return good_name;
    }

    public void setGood_name(String good_name) {
        this.good_name = good_name;
    }

    public String getGood_info() {
        return good_info;
    }

    public void setGood_info(String good_info) {
        this.good_info = good_info;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public int getStores_id() {
        return stores_id;
    }

    public void setStores_id(int stores_id) {
        this.stores_id = stores_id;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }
}
