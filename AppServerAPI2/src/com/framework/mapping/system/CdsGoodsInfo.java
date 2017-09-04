package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("CdsGoodsInfo")
@RsTable(describe = "商品信息表", name = "cds_goods_info")
public class CdsGoodsInfo extends BaseMapping {
    @RsTableField(describe = "主键", name = "good_id", primaryKey = true)
    private Integer good_id;

    @RsTableField(describe = "商品种类ID", name = "class_id")
    private Integer class_id;

    @RsTableField(describe = "商品名称", name = "good_name")
    private String good_name;

    @RsTableField(describe = "拼音", name = "pinxin")
    private String pinxin;

    @RsTableField(describe = "美团商品图片(oss)", name = "good_pic")
    private String good_pic;

    @RsTableField(describe = "饿了么商品图片(oss)", name = "egood_pic")
    private String egood_pic;

    @RsTableField(describe = "饿了么商品图片", name = "image_hash")
    private String image_hash;

    @RsTableField(describe = "添加时间", name = "add_date")
    private Date add_date;

    @RsTableField(describe = "商品详情", name = "introduce")
    private String introduce;

    @RsTableField(describe = "会员是否折扣", name = "vip_discounts")
    private Integer vip_discounts;

    @RsTableField(describe = "状态（1上架，0下架，2删除）", name = "status")
    private Integer status;

    @RsTableField(describe = "排序", name = "listorder")
    private Integer listorder;

    @RsTableField(describe = "默认规格", name = "default_standrad_id")
    private Integer default_standrad_id;

    @RsTableField(describe = "商品简介", name = "good_info")
    private String good_info;

    @RsTableField(describe = "商品价格", name = "market_price")
    private String market_price;

    @RsTableField(describe = "餐盒费", name = "box_price")
    private String box_price;

    public Integer getGood_id() {
        return good_id;
    }

    public void setGood_id(Integer good_id) {
        this.good_id = good_id;
    }

    public Integer getClass_id() {
        return class_id;
    }

    public void setClass_id(Integer class_id) {
        this.class_id = class_id;
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

    public Date getAdd_date() {
        return add_date;
    }

    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Integer getVip_discounts() {
        return vip_discounts;
    }

    public void setVip_discounts(Integer vip_discounts) {
        this.vip_discounts = vip_discounts;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getListorder() {
        return listorder;
    }

    public void setListorder(Integer listorder) {
        this.listorder = listorder;
    }

    public Integer getDefault_standrad_id() {
        return default_standrad_id;
    }

    public void setDefault_standrad_id(Integer default_standrad_id) {
        this.default_standrad_id = default_standrad_id;
    }

    public String getGood_info() {
        return good_info;
    }

    public void setGood_info(String good_info) {
        this.good_info = good_info;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getBox_price() {
        return box_price;
    }

    public void setBox_price(String box_price) {
        this.box_price = box_price;
    }

    public String getEgood_pic() {
        return egood_pic;
    }

    public void setEgood_pic(String egood_pic) {
        this.egood_pic = egood_pic;
    }

    public String getImage_hash() {
        return image_hash;
    }

    public void setImage_hash(String image_hash) {
        this.image_hash = image_hash;
    }
}
