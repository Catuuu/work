package com.opensdk.dianwoda.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.framework.mapping.system.CdsGood;
import com.framework.mapping.system.CdsOrderInfo;
import com.framework.mapping.system.CdsStores;
import com.framework.mapping.system.MapGG;
import com.framework.system.SystemConfig;
import com.framework.util.MapUtil;
import com.framework.util.StringUtil;
import com.opensdk.shenhou.util.HttpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/1.
 */
public class PoiAPI extends API {

    //派发订单
    public String order_send(CdsOrderInfo order, CdsStores stores){
        long timesLong = System.currentTimeMillis();
        String timestamp = timesLong + "";

        List<Map> order_products = new ArrayList<Map>();
        if(order.getGoods()!=null && !order.getGoods().equals("")){
            List<CdsGood> list =  JSONArray.parseArray(order.getGoods(),CdsGood.class);
            for (CdsGood good:list){
                Map product = new HashMap();
                if(good.getSpec()!=null&&!good.getSpec().equals("")){
                    product.put("item_name",good.getGood_name().trim()+good.getSpec().trim());
                }else {
                    product.put("item_name",good.getGood_name().trim());
                }

                product.put("unit","份");
                product.put("quantity",good.getQuantity());
                product.put("unit_price",(int)(good.getPrice()*100));
                product.put("discount_price",(int)(good.getPrice()*100));
                product.put("production_time",15*60);
                order_products.add(product);
            }
        }
        Map orderMap = new HashMap();
        String order_products_str = JSONArray.toJSONString(order_products);
        orderMap.put("items",order_products_str); //商品信息
        orderMap.put("service_type",String.valueOf(5));  //服务类型 0：骑手 5：骑士

        orderMap.put("order_original_id", order.getSend_id());//渠道订单编号
        orderMap.put("order_create_time", String.valueOf(timesLong));//订单创建时间
        orderMap.put("order_remark", order.getMember_desc());//订单备注

        orderMap.put("order_price", String.valueOf((long)order.getTotal_price()*100));//订单金额
        if(order.getService_time()==null) {//普通订单
            orderMap.put("order_is_reserve", String.valueOf(0));//是否是预约单(1:是，0:不是)
        }else{
            orderMap.put("order_is_reserve", String.valueOf(1));//是否是预约单(1:是，0:不是)
        }

        orderMap.put("serial_id", order.getOrder_no().substring(5));//订单流水号
        orderMap.put("cargo_weight", String.valueOf(0));            //订单商品重量
        orderMap.put("city_code", "420100");                        //行政区划代码

        //orderMap.put("cargo_type",1);//订单商品类型(默认传1)
        //orderMap.put("cargo_num",1);//商品份数，默认传1
        orderMap.put("seller_id", String.valueOf(stores.getStores_brand_id())); //商家编码
        orderMap.put("seller_name", stores.getName());                    //商家名称
        orderMap.put("seller_mobile", stores.getLinkman());              //商家电话
        orderMap.put("seller_address", stores.getAddress().trim());     //商家地址


        JSONObject jo = JSONObject.parseObject(stores.getPoints());
        MapGG mapgg = MapUtil.bd_decrypt(jo.getDoubleValue("lat"),jo.getDoubleValue("lng"));
        orderMap.put("seller_lng", mapgg.getGg_lon());//商家店铺地址高德经度
        orderMap.put("seller_lat", mapgg.getGg_lat());//商家店铺地址高德纬度

        if(order.getReceiver_name()==null||order.getReceiver_name().equals("")){
            order.setReceiver_name("菜大师用户");
        }
        order.setReceiver_name(StringUtil.StringFilter(order.getReceiver_name().trim()));

        orderMap.put("consignee_name",order.getReceiver_name());//收货人姓名
        orderMap.put("consignee_mobile",order.getReceiver_phone().trim().substring(0,11));//收货人手机号码
        orderMap.put("consignee_address",StringUtil.StringFilter(order.getReceiver_adress().trim()));//收货人地址
        orderMap.put("consignee_lat",order.getReceiver_lat());//收货人纬度坐标
        orderMap.put("consignee_lng",order.getReceiver_lng());//收货人经度坐标
        orderMap.put("money_rider_needpaid",String.valueOf(0));//配送员到店是否需要垫付订单金额  1：是 0：否


        if(order.getPay_type_id()==3) {//配送员到店是否需要垫付订单金额  1：是 0：否
            orderMap.put("money_rider_prepaid", String.valueOf((long)(order.getTotal_price()*100)));
            orderMap.put("money_rider_charge", String.valueOf((long)(order.getTotal_price()*100)));
        }else{
            orderMap.put("money_rider_prepaid", String.valueOf(0));
            orderMap.put("money_rider_charge", String.valueOf(0));
        }

        if(order.getService_time()==null){//普通订单
            //orderMap.put("time_ready_for_deliver", String.valueOf(timesLong + 1000 * 60 * stores.getPickup_time()));//配送员应到店时间，以毫秒计算时间，即unix-timestamp
           //orderMap.put("time_expected_arrival_end", String.valueOf(timesLong + 1000 * 60 * stores.getComplete_time()));//用户选择的期望最晚送达时间戳
        }else{//预订单
            //orderMap.put("time_ready_for_deliver", order.getService_time().getTime()-60*(stores.getComplete_time()-stores.getPickup_time()));//配送员应到店时间，以毫秒计算时间，即unix-timestamp
            //orderMap.put("time_expected_arrival_end", order.getService_time().getTime());//用户选择的期望最晚送达时间戳
            orderMap.put("time_expected_arrival", order.getService_time().getTime());   //用户选择的期望送达时间戳
            orderMap.put("order_is_reserve", String.valueOf(1));   //是否是预约单(1:是，0:不是)
        }


        orderMap.put("time_waiting_at_seller",String.valueOf((long)order.getTotal_price()*100));//配送员到店是否需要垫付订单金额  1：是 0：否
        orderMap.put("delivery_fee_from_seller",String.valueOf(0));//渠道支付配送费（分）
        orderMap.put("callback",SystemConfig.GetSystemParamDianWoDa().getCallUrl()+"/DianWoDan/OrderStatus");//回调url（url不能带参数）

        String sig = signObj(orderMap, SystemConfig.GetSystemParamDianWoDa().getApp_secret());


        orderMap.put("pk",SystemConfig.GetSystemParamDianWoDa().getApp_key());
        orderMap.put("timestamp",timestamp);
        orderMap.put("format","json");
        orderMap.put("sig",sig);

        String url = SystemConfig.GetSystemParamDianWoDa().getUrl()+"/api/v3/order-send.json";
        String result = HttpUtil.postRequest(url,orderMap);
        return result;
    }





    //取消订单
    /**
     * 取消订单
     * @param order  订单信息
     * @return
     */
    public String cancel(CdsOrderInfo order){
        long timesLong = System.currentTimeMillis();
        String timestamp = timesLong + "";
        String url = SystemConfig.GetSystemParamDianWoDa().getUrl()+"/api/v3/order-cancel.json";
        Map orderMap = new HashMap();
        orderMap.put("order_original_id",order.getSend_id());
        orderMap.put("cancle_reason","客服操作");

        String sig = signObj(orderMap, SystemConfig.GetSystemParamDianWoDa().getApp_secret());
        orderMap.put("pk",SystemConfig.GetSystemParamDianWoDa().getApp_key());
        orderMap.put("timestamp",timestamp);
        orderMap.put("format","json");
        orderMap.put("sig",sig);

        String result = HttpUtil.postRequest(url,orderMap);
        return result;
    }

    /**
     * 获取订单信息
     * @param order_original_id
     * @return
     */
    public String getOrderInfo(String order_original_id){
        long timesLong = System.currentTimeMillis();
        String timestamp = timesLong + "";
        String url = SystemConfig.GetSystemParamDianWoDa().getUrl()+"/api/v3/order-get.json";
        Map orderMap = new HashMap();
        orderMap.put("order_original_id",order_original_id);

        String sig = signObj(orderMap, SystemConfig.GetSystemParamDianWoDa().getApp_secret());
        orderMap.put("pk",SystemConfig.GetSystemParamDianWoDa().getApp_key());
        orderMap.put("timestamp",timestamp);
        orderMap.put("format","json");
        orderMap.put("sig",sig);

        String result = HttpUtil.postRequest(url,orderMap);
        return result;
    }


    /**
     * 2.10接受订单（测试接口仅测试环境有效）
     * @param order_original_id
     * @return
     */
    public  String Test_accepted(String order_original_id){
        long timesLong = System.currentTimeMillis();
        String timestamp = timesLong + "";
        String url = SystemConfig.GetSystemParamDianWoDa().getUrl()+"/api/v3/order-accepted-test.json";
        Map orderMap = new HashMap();
        orderMap.put("order_original_id",order_original_id);

        String sig = signObj(orderMap, SystemConfig.GetSystemParamDianWoDa().getApp_secret());
        orderMap.put("pk",SystemConfig.GetSystemParamDianWoDa().getApp_key());
        orderMap.put("timestamp",timestamp);
        orderMap.put("format","json");
        orderMap.put("sig",sig);

        String result = HttpUtil.postRequest(url,orderMap);
        return result;
    }
    /**
     * 2.11完成到店（测试接口仅测试环境有效）
     * @param order_original_id
     * @return
     */
    public String Test_arrive(String order_original_id){
        long timesLong = System.currentTimeMillis();
        String timestamp = timesLong + "";
        String url = SystemConfig.GetSystemParamDianWoDa().getUrl()+"/api/v3/order-arrive-test.json";
        Map orderMap = new HashMap();
        orderMap.put("order_original_id",order_original_id);

        String sig = signObj(orderMap, SystemConfig.GetSystemParamDianWoDa().getApp_secret());
        orderMap.put("pk",SystemConfig.GetSystemParamDianWoDa().getApp_key());
        orderMap.put("timestamp",timestamp);
        orderMap.put("format","json");
        orderMap.put("sig",sig);

        String result = HttpUtil.postRequest(url,orderMap);
        return result;
    }

     /**
     * 2.12完成取货（测试接口仅测试环境有效）
     * @param order_original_id
     * @return
     */
    public  String Test_fetch(String order_original_id){
        long timesLong = System.currentTimeMillis();
        String timestamp = timesLong + "";
        String url = SystemConfig.GetSystemParamDianWoDa().getUrl()+"/api/v3/order-fetch-test.json";
        Map orderMap = new HashMap();
        orderMap.put("order_original_id",order_original_id);

        String sig = signObj(orderMap, SystemConfig.GetSystemParamDianWoDa().getApp_secret());
        orderMap.put("pk",SystemConfig.GetSystemParamDianWoDa().getApp_key());
        orderMap.put("timestamp",timestamp);
        orderMap.put("format","json");
        orderMap.put("sig",sig);

        String result = HttpUtil.postRequest(url,orderMap);
        return result;
    }

    /**
     * 2.13配送完成（测试接口仅测试环境有效）
     * @param order_original_id
     * @return
     */
    public  String Test_finish(String order_original_id){
        long timesLong = System.currentTimeMillis();
        String timestamp = timesLong + "";
        String url = SystemConfig.GetSystemParamDianWoDa().getUrl()+"/api/v3/order-finish-test.json";
        Map orderMap = new HashMap();
        orderMap.put("order_original_id",order_original_id);

        String sig = signObj(orderMap, SystemConfig.GetSystemParamDianWoDa().getApp_secret());
        orderMap.put("pk",SystemConfig.GetSystemParamDianWoDa().getApp_key());
        orderMap.put("timestamp",timestamp);
        orderMap.put("format","json");
        orderMap.put("sig",sig);

        String result = HttpUtil.postRequest(url,orderMap);
        return result;
    }





    public static void main(String[] args) {
        PoiAPI api = new PoiAPI();
        String order_original_id = "B3161659083819871065";
        //String result = api.order_send(order_original_id);

        // String result =  api.Test_accepted(order_original_id);//接受订单
        //String result = api.Test_arrive(order_original_id); //完成到店
        //String result = api.Test_fetch(order_original_id); //完成取货
        String result = api.getOrderInfo(order_original_id); //配送完成
        JSONObject jo = JSON.parseObject(result);
        System.out.println(result);
    }
}
