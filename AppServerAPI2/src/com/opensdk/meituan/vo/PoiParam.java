package com.opensdk.meituan.vo;

/**
 * Created by chenbin on 17/02/05.
 */
public class PoiParam {

    private String app_poi_code;
    private String name;
    private String address;
    private Float latitude;
    private Float longitude;
    private String pic_url;
    private String pic_url_large;
    private String phone;
    private String standby_tel;
    private Float shipping_fee;
    private String shipping_time;
    private String promotion_info;
    private Integer open_level;
    private Integer is_online;
    private Integer invoice_support;
    private Integer invoice_min_price;
    private String invoice_description;
    private String third_tag_name;
    private String app_brand_code;
    private Integer pre_book;
    private Integer time_select;
    private Integer pre_book_min_days;
    private Integer pre_book_max_days;
    private Integer mt_type_id;
//    private String remark;
//    private String tag_name;

    public String getApp_poi_code() {
        return app_poi_code;
    }

    public void setApp_poi_code(String app_poi_code) {
        this.app_poi_code = app_poi_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStandby_tel() {
        return standby_tel;
    }

    public void setStandby_tel(String standby_tel) {
        this.standby_tel = standby_tel;
    }

    public Float getShipping_fee() {
        return shipping_fee;
    }

    public void setShipping_fee(Float shipping_fee) {
        this.shipping_fee = shipping_fee;
    }

    public String getShipping_time() {
        return shipping_time;
    }

    public void setShipping_time(String shipping_time) {
        this.shipping_time = shipping_time;
    }

    public String getPromotion_info() {
        return promotion_info;
    }

    public void setPromotion_info(String promotion_info) {
        this.promotion_info = promotion_info;
    }

    public Integer getOpen_level() {
        return open_level;
    }

    public void setOpen_level(Integer open_level) {
        this.open_level = open_level;
    }

    public Integer getIs_online() {
        return is_online;
    }

    public void setIs_online(Integer is_online) {
        this.is_online = is_online;
    }

    public String getThird_tag_name() {
        return third_tag_name;
    }

    public void setThird_tag_name(String third_tag_name) {
        this.third_tag_name = third_tag_name;
    }

    public String getApp_brand_code() {
        return app_brand_code;
    }

    public void setApp_brand_code(String app_brand_code) {
        this.app_brand_code = app_brand_code;
    }

    public Integer getPre_book_min_days() {
        return pre_book_min_days;
    }

    public void setPre_book_min_days(Integer pre_book_min_days) {
        this.pre_book_min_days = pre_book_min_days;
    }

    public Integer getPre_book_max_days() {
        return pre_book_max_days;
    }

    public void setPre_book_max_days(Integer pre_book_max_days) {
        this.pre_book_max_days = pre_book_max_days;
    }

    public String getPic_url_large() {
        return pic_url_large;
    }

    public void setPic_url_large(String pic_url_large) {
        this.pic_url_large = pic_url_large;
    }

    public Integer getInvoice_support() {
        return invoice_support;
    }

    public void setInvoice_support(Integer invoice_support) {
        this.invoice_support = invoice_support;
    }

    public Integer getInvoice_min_price() {
        return invoice_min_price;
    }

    public void setInvoice_min_price(Integer invoice_min_price) {
        this.invoice_min_price = invoice_min_price;
    }

    public String getInvoice_description() {
        return invoice_description;
    }

    public void setInvoice_description(String invoice_description) {
        this.invoice_description = invoice_description;
    }

    public Integer getPre_book() {
        return pre_book;
    }

    public void setPre_book(Integer pre_book) {
        this.pre_book = pre_book;
    }

    public Integer getTime_select() {
        return time_select;
    }

    public void setTime_select(Integer time_select) {
        this.time_select = time_select;
    }

    public Integer getMt_type_id() {
        return mt_type_id;
    }

    public void setMt_type_id(Integer mt_type_id) {
        this.mt_type_id = mt_type_id;
    }

    @Override
    public String toString() {
        return "PoiParam{" +
                "app_poi_code='" + app_poi_code + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", pic_url='" + pic_url + '\'' +
                ", pic_url_large='" + pic_url_large + '\'' +
                ", phone='" + phone + '\'' +
                ", standby_tel='" + standby_tel + '\'' +
                ", shipping_fee=" + shipping_fee +
                ", shipping_time='" + shipping_time + '\'' +
                ", promotion_info='" + promotion_info + '\'' +
                ", open_level=" + open_level +
                ", is_online=" + is_online +
                ", invoice_support=" + invoice_support +
                ", invoice_min_price=" + invoice_min_price +
                ", invoice_description='" + invoice_description + '\'' +
                ", third_tag_name='" + third_tag_name + '\'' +
                ", app_brand_code='" + app_brand_code + '\'' +
                ", pre_book=" + pre_book +
                ", time_select=" + time_select +
                ", pre_book_min_days=" + pre_book_min_days +
                ", pre_book_max_days=" + pre_book_max_days +
                ", mt_type_id=" + mt_type_id +
                '}';
    }
}
