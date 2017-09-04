package com.jms;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.framework.controller.BasicComponent;
import com.framework.mapping.system.CdsDianwodaPhone;
import com.framework.mapping.system.CdsOrderInfo;
import com.framework.mapping.system.CdsStores;
import com.framework.mapping.system.MapGG;
import com.framework.system.SystemConfig;
import com.framework.util.MapUtil;
import com.framework.util.StringUtil;
import com.opensdk.dianwoda.factory.APIFactoryDianWoDa;
import com.opensdk.shenhou.factory.APIFactoryShenHou;
import com.opensdk.weixin.util.HttpUtil;
import com.service.OrderService;
import com.service.StoresService;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.List;

/**
 * 计算订单的距离
 */
@Component
@EnableJms
public class OrderSendDistanceReceiver extends BasicComponent {

    @Resource(name = "StoresService")
    protected StoresService storesService;

    @Resource(name = "OrderService")
    protected OrderService orderService;
    /**
     * 计算订单的步行距离及步行时间
     * @param message
     */
    @JmsListener(containerFactory="jmsListenerContainerFactory",destination = "order.sendDistance",concurrency="1-2")
    public void onMessage(Message message) {
        try {
            String key = SystemConfig.GetSystemParamGaoDe().getKey();
            String data = ((TextMessage)message).getText();
            CdsOrderInfo cdsOrderInfo = JSONObject.toJavaObject(JSONObject.parseObject(data), CdsOrderInfo.class);

            CdsStores stores = storesService.GetStores(cdsOrderInfo.getStores_id(),cdsOrderInfo.getBrand_id());
            JSONObject jo = JSONObject.parseObject(stores.getPoints());
            MapGG gg = MapUtil.bd_decrypt(jo.getDoubleValue("lat"),jo.getDoubleValue("lng"));

            double gg_log = gg.getGg_lon();
            double gg_lat = gg.getGg_lat();

            String url = "http://restapi.amap.com/v3/distance?origins="+gg_log+","+gg_lat+"&destination="+cdsOrderInfo.getReceiver_lng()+","+cdsOrderInfo.getReceiver_lat()+"&output=json&key="+key+"&type=3";

            String result = HttpUtil.getRequest(url);
            jo = JSONObject.parseObject(result);
            if(jo.getJSONArray("results")!=null){
                JSONArray jarr = jo.getJSONArray("results");

                int distance = 0;
                int duration = 0;
                for (int j=0;j<jarr.size();j++){
                    JSONObject obj =  (JSONObject)jarr.get(j);
                    if(distance<obj.getIntValue("distance")){
                        distance = obj.getIntValue("distance");//行驶距离(米)
                        duration = obj.getIntValue("duration");//行驶时间(秒)
                    }
                }
                cdsOrderInfo.setDistance(distance);
                cdsOrderInfo.setDuration(duration);
                if(cdsOrderInfo.getSend_name().equals("点我达")){
                    float price = 2f;
                    int kilometre = 0; //计算倍数
                    if(distance>0){
                        double b = (float)distance/500;
                        kilometre = (int)Math.ceil(b);
                        price += 1*kilometre;
                    }
                    cdsOrderInfo.setSend_price(price);
                    cdsOrderInfo.setKilometre(kilometre);
                }else {
                    cdsOrderInfo.setSend_price(0);
                    cdsOrderInfo.setKilometre(0);
                }

                cdsOrderInfo.addParamFields("distance,duration,kilometre,send_price");
                cdsOrderInfo.addConditionField("order_id");
                sqlDao.updateRecord(cdsOrderInfo);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
