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
@Alias("CdsStoresGoodsList")
@RsTable(describe = "商铺包含商品明细", name = "cds_stores_goods_list")
public class CdsStoresGoodsList extends BaseMapping {

    @RsTableField(describe = "主键", name = "sgl_id", primaryKey = true)
    private int sgl_id;

    @RsTableField(describe = "商铺ID", name = "stores_id")
    private int stores_id;

    @RsTableField(describe = "商品ID", name = "good_id")
    private int good_id;

    @RsTableField(describe = "品牌id", name = "brand_id")
    private int  brand_id;

    @RsTableField(describe = "商品销售份数", name = "good_number")
    private int good_number;

    @RsTableField(describe = "添加类型(1.商品，最新全为1 )", name = "type")
    private int type;

    @RsTableField(describe = "饿了么数量", name = "elem_count")
    private int  elem_count;


    @RsTableField(describe = "美团数量", name = "mt_count")
    private int mt_count;

    @RsTableField(describe = "微信数量", name = "wx_count")
    private int wx_count;

    @RsTableField(describe = "餐盒数量", name = "box_count")
    private int  box_count;

    @RsTableField(describe = "售卖状态（1售卖中、2暂停售卖）", name = "status")
    private int status;

    @RsTableField(describe = "餐盒费", name = "box_price")
    private double box_price;

    @RsTableField(describe = "商品价格", name = "market_price")
    private double market_price;

    @RsTableField(describe = "单位", name = "unit")
    private String unit;

    @RsTableField(describe = "默认规格ID", name = "default_standrad_id")
    private int default_standrad_id;

    @RsTableField(describe = "(是、否)后期全部使用默认规格", name = "isdefault")
    private String isdefault;

    @RsTableField(describe = "商品总份数(所有规格之和)", name = "all_count")
    private int all_count;

    @RsTableField(describe = "门店品牌ID", name = "stores_brand_id")
    private int stores_brand_id;

    @RsTableField(describe = "饿了么食物id", name = "food_id")
    private String food_id;

    @RsTableField(describe = "美团食物绑定", name = "mt_isband")
    private String mt_isband;

    @RsTableField(describe = "美团图片hash值", name = "mt_image_hash")
    private String mt_image_hash;

    @RsTableField(describe = "饿了么图片hash值", name = "image_hash")
    private String image_hash;

    @RsTableField(describe = "商品名称", name = "good_name")
    private String good_name;

    @RsTableField(describe = "中文拼音", name = "pinxin")
    private String pinxin;

    @RsTableField(describe = "美团图片", name = "good_pic")
    private String good_pic;

    @RsTableField(describe = "饿了么图片", name = "egood_pic")
    private String egood_pic;

    @RsTableField(describe = "添加时间", name = "add_date")
    private Date add_date;

    @RsTableField(describe = "商品简介", name = "good_info")
    private String good_info;

    @RsTableField(describe = "排序", name = "listorder")
    private int listorder;

    @RsTableField(describe = "门店类别ID", name = "stores_class_id")
    private int stores_class_id;

    public CdsStoresGoodsList() {
    }

    public int getStores_class_id() {
        return stores_class_id;
    }

    public void setStores_class_id(int stores_class_id) {
        this.stores_class_id = stores_class_id;
    }

    public String getGood_name() {
        return good_name;
    }

    public void setGood_name(String good_name) {
        this.good_name = good_name;
    }

    public String getImage_hash() {
        return image_hash;
    }

    public void setImage_hash(String image_hash) {
        this.image_hash = image_hash;
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

    public String getEgood_pic() {
        return egood_pic;
    }

    public void setEgood_pic(String egood_pic) {
        this.egood_pic = egood_pic;
    }

    public Date getAdd_date() {
        return add_date;
    }

    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

    public String getGood_info() {
        return good_info;
    }

    public void setGood_info(String good_info) {
        this.good_info = good_info;
    }

    public int getListorder() {
        return listorder;
    }

    public void setListorder(int listorder) {
        this.listorder = listorder;
    }

    public String getMt_image_hash() {
        return mt_image_hash;
    }

    public void setMt_image_hash(String mt_image_hash) {
        this.mt_image_hash = mt_image_hash;
    }

    public String getMt_isband() {
        return mt_isband;
    }

    public void setMt_isband(String mt_isband) {
        this.mt_isband = mt_isband;
    }

    public int getStores_brand_id() {
        return stores_brand_id;
    }

    public void setStores_brand_id(int stores_brand_id) {
        this.stores_brand_id = stores_brand_id;
    }

    public int getSgl_id() {
        return sgl_id;
    }

    public void setSgl_id(int sgl_id) {
        this.sgl_id = sgl_id;
    }

    public int getStores_id() {
        return stores_id;
    }

    public void setStores_id(int stores_id) {
        this.stores_id = stores_id;
    }

    public int getGood_id() {
        return good_id;
    }

    public void setGood_id(int good_id) {
        this.good_id = good_id;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public int getGood_number() {
        return good_number;
    }

    public void setGood_number(int good_number) {
        this.good_number = good_number;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getElem_count() {
        return elem_count;
    }

    public void setElem_count(int elem_count) {
        this.elem_count = elem_count;
    }

    public int getMt_count() {
        return mt_count;
    }

    public void setMt_count(int mt_count) {
        this.mt_count = mt_count;
    }

    public int getWx_count() {
        return wx_count;
    }

    public void setWx_count(int wx_count) {
        this.wx_count = wx_count;
    }

    public int getBox_count() {
        return box_count;
    }

    public void setBox_count(int box_count) {
        this.box_count = box_count;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getBox_price() {
        return box_price;
    }

    public void setBox_price(double box_price) {
        this.box_price = box_price;
    }

    public double getMarket_price() {
        return market_price;
    }

    public void setMarket_price(double market_price) {
        this.market_price = market_price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getDefault_standrad_id() {
        return default_standrad_id;
    }

    public void setDefault_standrad_id(int default_standrad_id) {
        this.default_standrad_id = default_standrad_id;
    }

    public String getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault;
    }

    public int getAll_count() {
        return all_count;
    }

    public void setAll_count(int all_count) {
        this.all_count = all_count;
    }

    public String getFood_id() {
        return food_id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
    }
}
