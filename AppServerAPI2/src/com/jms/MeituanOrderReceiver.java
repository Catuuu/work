package com.jms;

import com.alibaba.fastjson.JSONObject;
import com.framework.controller.BasicComponent;
import com.framework.mapping.system.CdsMember;
import com.framework.mapping.system.CdsOrderInfo;
import com.framework.mapping.system.CdsStores;
import com.framework.system.SystemConfig;
import com.opensdk.meituan.exception.ApiOpException;
import com.opensdk.meituan.exception.ApiSysException;
import com.opensdk.meituan.factory.APIFactoryMeituan;
import com.opensdk.meituan.vo.OrderDetailParam;
import com.service.GiftService;
import com.service.MemberService;
import com.service.OrderService;
import com.service.StoresService;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * 美团订单消息处理
 */
@Component
@EnableJms
public class MeituanOrderReceiver extends BasicComponent {

    @Resource(name = "MemberService")
    protected MemberService memberService;

    @Resource(name = "StoresService")
    protected StoresService storesService;

    @Resource(name = "OrderService")
    protected OrderService orderService;

    @Resource(name = "GiftService")
    protected GiftService giftService;
    /**
     * 美团订单推送处理
     * @param message
     */
    @JmsListener(containerFactory="jmsListenerContainerFactory",destination = "meituanorder.queue",concurrency="1-2")
    public void onMessage(Message message) {
        try {
            //logger.info("MeituanOrderReceiver接收到消息:"+((TextMessage)message).getText());
            String data = ((TextMessage)message).getText();
            JSONObject jo =JSONObject.parseObject(data);

            long order_id =jo.getLongValue("order_id");
            double latitude = jo.getDoubleValue("latitude");
            double longitude = jo.getDoubleValue("longitude");

            //新订单处理
            OrderDetailParam orderParam = APIFactoryMeituan.getOrderAPI().orderGetOrderDetail(SystemConfig.GetSystemParamMeituan(),order_id,1);

            orderParam.setLatitude(latitude);
            orderParam.setLongitude(longitude);

            CdsStores stores = storesService.GetStores(Integer.parseInt(orderParam.getApp_poi_code()));

            if(stores!=null){
                CdsMember member = memberService.GetMember(orderParam,stores);
                CdsOrderInfo orderInfo = new CdsOrderInfo();
                orderInfo.setMeitianParam(member,orderParam,stores); //设置美团订单的相关信息
                orderInfo.setOrder_no(storesService.getStoresNo(orderInfo,stores.getBrand_fromno_start(), orderInfo.getCreate_date()));//获取订单流水号
                orderInfo = giftService.addGiftGoods(member.getPhone(),orderInfo);
                orderService.createOrder(orderInfo); //创建订单

            }

        } catch (JMSException e) {
            e.printStackTrace();
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }
}
