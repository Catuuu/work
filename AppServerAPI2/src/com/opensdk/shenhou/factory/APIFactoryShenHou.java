package com.opensdk.shenhou.factory;


import com.opensdk.shenhou.api.PoiAPI;

/**
 * Created by chenbin on 17/02/05.
 * 接口工厂
 */
public class APIFactoryShenHou {

    private static PoiAPI poiAPI = new PoiAPI();

    public static PoiAPI getPoiAPI() {
        return poiAPI;
    }

    public static void setPoiAPI(PoiAPI poiAPI) {
        APIFactoryShenHou.poiAPI = poiAPI;
    }
}
