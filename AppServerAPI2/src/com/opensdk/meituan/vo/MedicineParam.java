package com.opensdk.meituan.vo;

/**
 * Created by chenbin on 17/02/05.
 */
public class MedicineParam {

    public String app_medicine_code;
    public String app_poi_code;
    public String upc;
    public String medicine_no;
    public String spec;
    public Float price;
    public Integer stock;
    public String category_code;
    public String category_name;
    public Integer is_sold_out;
    public Integer sequence;


    public String getApp_medicine_code() {
        return app_medicine_code;
    }

    public void setApp_medicine_code(String app_medicine_code) {
        this.app_medicine_code = app_medicine_code;
    }

    public String getApp_poi_code() {
        return app_poi_code;
    }

    public void setApp_poi_code(String app_poi_code) {
        this.app_poi_code = app_poi_code;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getMedicine_no() {
        return medicine_no;
    }

    public void setMedicine_no(String medicine_no) {
        this.medicine_no = medicine_no;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getCategory_code() {
        return category_code;
    }

    public void setCategory_code(String category_code) {
        this.category_code = category_code;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public Integer getIs_sold_out() {
        return is_sold_out;
    }

    public void setIs_sold_out(Integer is_sold_out) {
        this.is_sold_out = is_sold_out;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return "MedicineParam{" +
                "app_medicine_code='" + app_medicine_code + '\'' +
                ", app_poi_code='" + app_poi_code + '\'' +
                ", upc='" + upc + '\'' +
                ", medicine_no='" + medicine_no + '\'' +
                ", spec='" + spec + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", category_code='" + category_code + '\'' +
                ", category_name='" + category_name + '\'' +
                ", is_sold_out=" + is_sold_out +
                ", sequence=" + sequence +
                '}';
    }
}
