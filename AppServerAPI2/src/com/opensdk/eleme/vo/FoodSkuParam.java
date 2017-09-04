package com.opensdk.eleme.vo;

/**
 * Created by chenbin on 17/02/05.
 */
public class FoodSkuParam {
    private String sku_id;
    private String spec;
    private String upc;
    private String stock;
    private String price;
    private String location_code;
    private AvailableTimeParam available_times;
//    private String available_times;


    public String getSku_id() {
        return sku_id;
    }

    public void setSku_id(String sku_id) {
        this.sku_id = sku_id;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation_code() {
        return location_code;
    }

    public void setLocation_code(String location_code) {
        this.location_code = location_code;
    }

    public AvailableTimeParam getAvailable_times() {
        return available_times;
    }

    public void setAvailable_times(AvailableTimeParam available_times) {
        this.available_times = available_times;
    }

    @Override
    public String toString(){
        return "[{"+"sku_id"+":" + sku_id + ","+ "spec"+":" + spec + ","+ "upc" + ":" + upc + ","
               + "stock" + ":" + stock + "," +  "price" + ":" + price
               + "," +  "location_code" + ":" + location_code + "," + "available_times"
               +  ":" + available_times + "}]";
    }






//    public String getAvailable_times() {
//        return available_times;
//    }
//
//    public void setAvailable_times(String available_times) {
//        this.available_times = available_times;
//    }
//
//    @Override
//    public String toString() {
//        return "FoodSkuParam{" +
//                "sku_id='" + sku_id + '\'' +
//                ", spec='" + spec + '\'' +
//                ", upc='" + upc + '\'' +
//                ", stock='" + stock + '\'' +
//                ", price='" + price + '\'' +
//                ", location_code='" + location_code + '\'' +
//                ", available_times='" + available_times + '\'' +
//                '}';
//    }
}
