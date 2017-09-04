package com.opensdk.shenhou.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.framework.mapping.system.CdsGood;
import com.framework.mapping.system.CdsOrderInfo;
import com.framework.mapping.system.CdsStores;
import com.framework.mapping.system.MapGG;
import com.framework.system.SystemConfig;
import com.framework.util.MapUtil;
import com.framework.util.NumberUtil;
import com.framework.util.StringUtil;
import com.framework.util.WebUtil;
import com.opensdk.shenhou.util.HttpUtil;


import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by Administrator on 2017/2/17.
 */
public class PoiAPI extends API{
    /**
     * 发送订单派送数据
     * @param order
     * @param stores
     * @return
     */
    public String create(CdsOrderInfo order, CdsStores stores){
        List<Map> order_products = new ArrayList<Map>();
        if(order.getGoods()!=null && !order.getGoods().equals("")){
            List<CdsGood> list =  JSONArray.parseArray(order.getGoods(),CdsGood.class);
            for (CdsGood good:list){
                Map product = new HashMap();
                if(good.getSpec()!=null&&!good.getSpec().equals("")){
                    product.put("name",good.getGood_name().trim()+good.getSpec().trim());
                }else {
                    product.put("name",good.getGood_name().trim());
                }

                product.put("unit_price",(int)(good.getPrice()*100));
                product.put("quantity",good.getQuantity());
                product.put("total_price",(int)(good.getQuantity()*good.getPrice()*100));
                order_products.add(product);
            }
        }

        Map orderMap = new HashMap();
        String order_products_str = JSONArray.toJSONString(order_products);

        orderMap.put("order_products",order_products_str);
        orderMap.put("order_number",order.getSend_id());
        orderMap.put("order_price",(long)(order.getTotal_price()*100));

        if(order.getPay_type_id()==3) {
            orderMap.put("store_consignee_fee", (long)(order.getTotal_price()*100));
        }else{
            orderMap.put("store_consignee_fee", 0);
        }
        Date d = new Date();
        if(order.getService_time()==null){//普通订单
            orderMap.put("planned_pickup_time", d.getTime()/1000+60*stores.getPickup_time());//订单预计取货时间
            orderMap.put("planned_complete_time", d.getTime()/1000+60*stores.getComplete_time());//订单预计送达时间
        }else{
            orderMap.put("planned_pickup_time", order.getService_time().getTime()/1000-60*(stores.getComplete_time()-stores.getPickup_time()));//订单预计取货时间
            orderMap.put("planned_complete_time", order.getService_time().getTime()/1000);//订单预计送达时间
        }

        if(order.getReceiver_name()==null||order.getReceiver_name().equals("")){
            order.setReceiver_name("客户");
        }
        orderMap.put("store_id", order.getStores_id());//商铺ID
        orderMap.put("store_name", stores.getName().trim());//商铺名称
        orderMap.put("store_address", stores.getAddress().trim());//商铺地址

        JSONObject jo = JSONObject.parseObject(stores.getPoints());
        MapGG mapgg = MapUtil.bd_decrypt(jo.getDoubleValue("lat"),jo.getDoubleValue("lng"));
        orderMap.put("store_longitude", mapgg.getGg_lon());//商家店铺地址高德经度
        orderMap.put("store_latitude", mapgg.getGg_lat());//商家店铺地址高德纬度

        orderMap.put("store_phone", stores.getLinkman());//商家电话
        orderMap.put("consignee_address", order.getReceiver_adress().trim());//收货地址

        orderMap.put("consignee_name", order.getReceiver_name().trim());//姓名
        orderMap.put("consignee_phone", order.getReceiver_phone().trim().substring(0,11));//电话
        orderMap.put("consignee_longitude", order.getReceiver_lng());//经度
        orderMap.put("consignee_latitude", order.getReceiver_lat());//纬度

        orderMap.put("store_sequence_number", order.getOrder_no().substring(5));//流水号
        orderMap.put("city_code", "011101");//城市code

        if(order.getMember_desc()!=null&&!order.getMember_desc().equals("")){
            orderMap.put("consignee_note", order.getMember_desc().trim());//买家备注
        }

        String url = SystemConfig.GetSystemParamShenhou().getUrl()+"/openapi/v1/order/create";

        String result = requestpost(url,orderMap);
        return result;
    }

    /**
     * 生活半径取消订单
     * @param order  订单信息
     * @return
     */
    public String cancel(CdsOrderInfo order){
        String url = SystemConfig.GetSystemParamShenhou().getUrl()+"/openapi/v1/order/cancel";
        Map orderMap = new HashMap();
        orderMap.put("order_number",order.getSend_id());
        String result = requestpost(url,orderMap);
        return result;
    }
}
