package com.framework.util;

import com.framework.mapping.system.MapBD;
import com.framework.mapping.system.MapGG;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.StrictMath.sin;
import static java.lang.StrictMath.sqrt;

/**
 * 地图转换坐标
 * Created by Administrator on 2017/2/10.
 */
public class MapUtil {

    //GCJ-02(火星，高德) 坐标转换成 BD-09(百度) 坐标
    //@param bd_lon 百度经度
    //@param bd_lat 百度纬度
    public static MapBD bd_encrypt(double gg_lat, double gg_lon){
        double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
        double x = gg_lon, y = gg_lat;
        double z = sqrt(x * x + y * y) + 0.00002 * sin(y * x_pi);
        double theta = atan2(y, x) + 0.000003 * cos(x * x_pi);
        double bd_lon = z * cos(theta) + 0.0065;
        double bd_lat = z * sin(theta) + 0.006;
        return new MapBD(bd_lon,bd_lat);
    }


    //BD-09(百度) 坐标转换成  GCJ-02(火星，高德) 坐标
    //@param bd_lon 百度经度
    //@param bd_lat 百度纬度
    public static MapGG bd_decrypt(double bd_lat, double bd_lon){
        double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = sqrt(x * x + y * y) - 0.00002 * sin(y * x_pi);
        double theta = atan2(y, x) - 0.000003 * cos(x * x_pi);
        double gg_lon = z * cos(theta);
        double gg_lat = z * sin(theta);
        return new MapGG(gg_lon,gg_lat);
    }
}
