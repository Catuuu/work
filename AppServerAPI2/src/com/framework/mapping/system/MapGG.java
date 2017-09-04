package com.framework.mapping.system;

/**
 * 高德坐标
 * Created by Administrator on 2017/2/10.
 */
public class MapGG {
    private double gg_lon;
    private double gg_lat;

    public MapGG(){

    }

    public MapGG(double gg_lon,double gg_lat){
        this.gg_lon = gg_lon;
        this.gg_lat = gg_lat;
    }

    public double getGg_lon() {
        return gg_lon;
    }

    public void setGg_lon(double gg_lon) {
        this.gg_lon = gg_lon;
    }

    public double getGg_lat() {
        return gg_lat;
    }

    public void setGg_lat(double gg_lat) {
        this.gg_lat = gg_lat;
    }
}
