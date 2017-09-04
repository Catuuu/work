package com.opensdk.eleme.factory;

import com.opensdk.eleme.api.*;

/**
 * Created by chenbin on 17/02/05.
 * 接口工厂
 */
public class APIFactoryEleme {

    private static PoiAPI poiAPI = new PoiAPI();
    private static ShippingAPI shippingAPI= new ShippingAPI();
    private static FoodAPI foodAPI = new FoodAPI();
    private static OrderAPI orderAPI = new OrderAPI();
    private static CommentAPI commentAPI = new CommentAPI();

    private static ImageApi imageApi = new ImageApi();

    public static FoodAPI getFoodAPI() {
        return foodAPI;
    }

    public static ImageApi getImageApi() {
        return imageApi;
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

    public static CommentAPI getCommentAPI(){return commentAPI;}
}
