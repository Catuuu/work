package com.framework.mapping.system;

import java.util.Map;

/**
 * 百度坐标
 * Created by Administrator on 2017/2/10.
 */
public class MapBD {
    private double bd_lon;
    private double bd_lat;

    public MapBD(){}

    public MapBD(double bd_lon,double bd_lat){
        this.bd_lon = bd_lon;
        this.bd_lat = bd_lat;
    }

    public double getBd_lon() {
        return bd_lon;
    }

    public void setBd_lon(double bd_lon) {
        this.bd_lon = bd_lon;
    }

    public double getBd_lat() {
        return bd_lat;
    }

    public void setBd_lat(double bd_lat) {
        this.bd_lat = bd_lat;
    }
}
