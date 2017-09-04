package com.opensdk.shenhou;

import com.framework.util.StringUtil;

/**
 * Created by Administrator on 2017/2/18.
 */
public class Test {
    public static void main(String[] args) {
        String basedUrl = "http://api.jiahuanle.com/openapi/v1/order/create?app_key=1466611220160891&city_code=011101&consignee_address=湖北省武汉市武昌区中南路街办事处广州军区武汉总医院 神经外科专科楼十楼&consignee_latitude=30.531202&consignee_longitude=114.344755&consignee_name=范艳&consignee_phone=13297991995&order_number=B217170964206945268&order_price=2039&order_products=[{\"name\":\"雪花蛋酒\",\"unit_price\":350,\"quantity\":1,\"total_price\":350},{\"name\":\"红烧牛腩-份\",\"unit_price\":2390,\"quantity\":1,\"total_price\":2390}]&planned_complete_time=1487303100&planned_pickup_time=1487300700&store_address=傅家坡客运站旁边&store_consignee_fee=0&store_id=11&store_latitude=30.535224592014&store_longitude=114.32577114479&store_name=菜大师傅家坡厨房&store_phone=18064049086&store_sequence_number=0003&timestamp=148739464460db3c53dec5630321f9eabb84514c8b";

        String sing = StringUtil.MD5(basedUrl);
        System.out.println(sing);
    }
}
