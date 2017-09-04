package com.opensdk.eleme.vo;

/**
 * Created by chenbin on 17/02/05.
 */
public class ShippingParam {
    private String app_poi_code;
    private String app_shipping_code;
    private Integer type;
    private String area;
    private Double min_price;
    private Double shipping_fee;

    public String getApp_poi_code() {
        return app_poi_code;
    }

    public void setApp_poi_code(String app_poi_code) {
        this.app_poi_code = app_poi_code;
    }

    public String getApp_shipping_code() {
        return app_shipping_code;
    }

    public void setApp_shipping_code(String app_shipping_code) {
        this.app_shipping_code = app_shipping_code;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Double getMin_price() {
        return min_price;
    }

    public void setMin_price(Double min_price) {
        this.min_price = min_price;
    }

    public Double getShipping_fee() {
        return shipping_fee;
    }

    public void setShipping_fee(Double shipping_fee) {
        this.shipping_fee = shipping_fee;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ShippingParam{" +
               "app_poi_code='" + app_poi_code + '\'' +
               ", app_shipping_code='" + app_shipping_code + '\'' +
               ", type=" + type +
               ", area='" + area + '\'' +
               ", min_price=" + min_price +
               ", shipping_fee=" + shipping_fee +
               '}';
    }
}
