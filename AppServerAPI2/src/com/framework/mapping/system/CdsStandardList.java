package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * User: chenbin
 * Date: 12-12-31
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
@Alias("CdsStandardlist")
@RsTable(describe = "后台单菜品信息配置表", name = "cds_standard_list")
public class CdsStandardList extends BaseMapping {

    @RsTableField(describe = "主键", name = "standard_id", primaryKey = true)
    private int standard_id;

    @RsTableField(describe = "商品ID", name = "good_id")
    private int good_id;

    @RsTableField(describe = "商品明细ID", name = "sgl_id")
    private int sgl_id;

    @RsTableField(describe = "商品规格名称", name = "standard_name")
    private String standard_name;

    @RsTableField(describe = "原价", name = "market_price")
    private double market_price;

    @RsTableField(describe = "现价", name = "current_price")
    private double current_price;

//    @RsTableField(describe = "商品名称", name = "good_name")
//    private String good_name;

    @RsTableField(describe = "规格描述", name = "standard_desc")
    private String standard_desc;

//    @RsTableField(describe = "商品关键字", name = "good_key")
//    private String good_key;

    @RsTableField(describe = "商品份数", name = "count")
    private int count;

    @RsTableField(describe = "餐盒费", name = "box_price")
    private double box_price;

    @RsTableField(describe = "美团数量", name = "mt_count")
    private int mt_count;

    @RsTableField(describe = "饿了么数量", name = "elem_count")
    private int elem_count;

    @RsTableField(describe = "餐盒数量", name = "wx_count")
    private int wx_count;

    @RsTableField(describe = "餐盒数量", name = "box_count")
    private int box_count;

    @RsTableField(describe = "排序", name = "listorder")
    private int listorder;


    public int getWx_count() {
        return wx_count;
    }

    public void setWx_count(int wx_count) {
        this.wx_count = wx_count;
    }

    public int getStandard_id() {
        return standard_id;
    }

    public void setStandard_id(int standard_id) {
        this.standard_id = standard_id;
    }

    public int getGood_id() {
        return good_id;
    }

    public void setGood_id(int good_id) {
        this.good_id = good_id;
    }

    public int getSgl_id() {
        return sgl_id;
    }

    public void setSgl_id(int sgl_id) {
        this.sgl_id = sgl_id;
    }

    public String getStandard_name() {
        return standard_name;
    }

    public void setStandard_name(String standard_name) {
        this.standard_name = standard_name;
    }

    public double getMarket_price() {
        return market_price;
    }

    public void setMarket_price(double market_price) {
        this.market_price = market_price;
    }

    public double getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(double current_price) {
        this.current_price = current_price;
    }

//    public String getGood_name() {
//        return good_name;
//    }
//
//    public void setGood_name(String good_name) {
//        this.good_name = good_name;
//    }

    public String getStandard_desc() {
        return standard_desc;
    }

    public void setStandard_desc(String standard_desc) {
        this.standard_desc = standard_desc;
    }

//    public String getGood_key() {
//        return good_key;
//    }
//
//    public void setGood_key(String good_key) {
//        this.good_key = good_key;
//    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getBox_price() {
        return box_price;
    }

    public void setBox_price(double box_price) {
        this.box_price = box_price;
    }

    public int getMt_count() {
        return mt_count;
    }

    public void setMt_count(int mt_count) {
        this.mt_count = mt_count;
    }

    public int getElem_count() {
        return elem_count;
    }

    public void setElem_count(int elem_count) {
        this.elem_count = elem_count;
    }

    public int getBox_count() {
        return box_count;
    }

    public void setBox_count(int box_count) {
        this.box_count = box_count;
    }

    public int getListorder() {
        return listorder;
    }

    public void setListorder(int listorder) {
        this.listorder = listorder;
    }
}
