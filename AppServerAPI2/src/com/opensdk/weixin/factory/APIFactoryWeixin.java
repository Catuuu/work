package com.opensdk.weixin.factory;


import com.opensdk.weixin.api.PoiAPI;

/**
 * Created by chenbin on 17/02/05.
 * 接口工厂
 */
public class APIFactoryWeixin {

    private static PoiAPI poiAPI = new PoiAPI();

    public static PoiAPI getPoiAPI() {
        return poiAPI;
    }

    public static void setPoiAPI(PoiAPI poiAPI) {
        APIFactoryWeixin.poiAPI = poiAPI;
    }
}
