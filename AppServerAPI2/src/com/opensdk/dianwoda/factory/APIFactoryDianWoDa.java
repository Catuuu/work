package com.opensdk.dianwoda.factory;

import com.opensdk.dianwoda.api.PoiAPI;

/**
 * Created by Administrator on 2017/3/1.
 */
public class APIFactoryDianWoDa {
    private static PoiAPI poiAPI = new PoiAPI();

    public static PoiAPI getPoiAPI() {
        return poiAPI;
    }

    public static void setPoiAPI(PoiAPI poiAPI) {
        APIFactoryDianWoDa.poiAPI = poiAPI;
    }
}
