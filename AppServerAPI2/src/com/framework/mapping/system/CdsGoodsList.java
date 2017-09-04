package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

/**
 * Created by Administrator on 2017/2/14.
 * 订单商品明细  -旧格式，新版本不使用
 */
@Alias("CdsGoodsList")
@RsTable(describe = "订单商品明细", name = "cds_goods_list")
public class CdsGoodsList extends BaseMapping {

    @RsTableField(describe = "主键", name = "good_list_id", primaryKey = true)
    private String good_list_id;

    @RsTableField(describe = "订单编号", name = "order_id")
    private String order_id;

    @RsTableField(describe = "商品ID", name = "good_id")
    private int good_id;

    @RsTableField(describe = "商品种类ID", name = "class_id")
    private int class_id;

    @RsTableField(describe = "商品规格ID", name = "standard_id")
    private int standard_id;

    @RsTableField(describe = "商品规格名称", name = "standard_name")
    private String standard_name;

    @RsTableField(describe = "商品名称", name = "good_name")
    private String good_name;

    @RsTableField(describe = "商品中文拼音", name = "pinxin")
    private String pinxin;

    @RsTableField(describe = "商品图标", name = "good_pic")
    private String good_pic;

    @RsTableField(describe = "商品原价", name = "market_price")
    private float market_price;

    @RsTableField(describe = "销售价", name = "current_price")
    private float current_price;

    @RsTableField(describe = "数量", name = "count")
    private int count;

    @RsTableField(describe = "排序", name = "listorder")
    private int listorder;

    @RsTableField(describe = "后菜菜品分类", name = "ms_class_id")
    private int ms_class_id;

    public String getGood_list_id() {
        return good_list_id;
    }

    public void setGood_list_id(String good_list_id) {
        this.good_list_id = good_list_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public int getGood_id() {
        return good_id;
    }

    public void setGood_id(int good_id) {
        this.good_id = good_id;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
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

    public String getGood_name() {
        return good_name;
    }

    public void setGood_name(String good_name) {
        this.good_name = good_name;
    }

    public String getPinxin() {
        return pinxin;
    }

    public void setPinxin(String pinxin) {
        this.pinxin = pinxin;
    }

    public String getGood_pic() {
        return good_pic;
    }

    public void setGood_pic(String good_pic) {
        this.good_pic = good_pic;
    }

    public float getMarket_price() {
        return market_price;
    }

    public void setMarket_price(float market_price) {
        this.market_price = market_price;
    }

    public float getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(float current_price) {
        this.current_price = current_price;
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

    public int getMs_class_id() {
        return ms_class_id;
    }

    public void setMs_class_id(int ms_class_id) {
        this.ms_class_id = ms_class_id;
    }
}
