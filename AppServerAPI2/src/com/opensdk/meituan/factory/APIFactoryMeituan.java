package com.opensdk.meituan.factory;

import com.opensdk.meituan.api.*;

/**
 * Created by chenbin on 17/02/05.
 * 接口工厂
 */
public class APIFactoryMeituan {

    private static PoiAPI poiAPI = new PoiAPI();
    private static ShippingAPI shippingAPI= new ShippingAPI();
    private static FoodAPI foodAPI = new FoodAPI();
    private static OrderAPI orderAPI = new OrderAPI();
    private static MedicineAPI medicineAPI = new MedicineAPI();
    private static ImageApi imageApi = new ImageApi();
    private static CommentAPI commentAPI = new CommentAPI();

    public static FoodAPI getFoodAPI() {
        return foodAPI;
    }

    public static ImageApi getImageApi() {
        return imageApi;
    }

    public static MedicineAPI getMedicineAPI() {
        return medicineAPI;
    }

    public static OrderAPI getOrderAPI() {
        return orderAPI;
    }

    public static PoiAPI getPoiAPI() {
        return poiAPI;
    }

    public static ShippingAPI getShippingAPI() {
        return shippingAPI;
    }

    public static CommentAPI getCommentAPI() {
        return commentAPI;
    }
}
