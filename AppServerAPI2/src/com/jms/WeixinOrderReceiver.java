package com.jms;

import com.alibaba.fastjson.JSONObject;
import com.framework.controller.BasicComponent;
import com.framework.mapping.system.CdsMember;
import com.framework.mapping.system.CdsOrderInfo;
import com.framework.mapping.system.CdsStores;
import com.framework.mapping.system.CdsWeixinOrderInfo;
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
 * 饿了么订单消息处理
 */
@Component
@EnableJms
public class WeixinOrderReceiver extends BasicComponent {

    @Resource(name = "MemberService")
    protected MemberService memberService;

    @Resource(name = "StoresService")
    protected StoresService storesService;

    @Resource(name = "OrderService")
    protected OrderService orderService;

    @Resource(name = "GiftService")
    protected GiftService giftService;

    /**
     * 微信订单处理(新版本)
     *
     * @param message
     */
    @JmsListener(containerFactory = "jmsListenerContainerFactory", destination = "wxOrder", concurrency = "1-2")
    public void onMessage(Message message) {
        try {
            String data = ((TextMessage) message).getText();
            JSONObject jo = JSONObject.parseObject(data);
            String order_id = jo.getString("orderId");
            int action = jo.getInteger("action");

            if (action == 1) {//新订单处理
                CdsWeixinOrderInfo weixinInfo = new CdsWeixinOrderInfo();
                weixinInfo.setOrder_id(order_id);
                weixinInfo.addConditionField("order_id");
                weixinInfo = sqlDao.getRecord(weixinInfo);
                if (weixinInfo != null) {
                    CdsMember member = memberService.GetMember(weixinInfo);
                    if (member != null) {
                        CdsStores stores = storesService.GetStores(weixinInfo.getStores_id(), member.getBrand_id());
                        if (stores != null) {
                            CdsOrderInfo orderInfo = new CdsOrderInfo();
                            orderInfo.setWeixinParam(member, weixinInfo, stores); //设置微信订单的相关信息
                            orderInfo.setOrder_no(storesService.getStoresNo(orderInfo, stores.getBrand_id(), weixinInfo.getCreate_date()));//获取订单流水号
                            orderInfo = giftService.addGiftGoods(member.getPhone(),orderInfo);
                            orderService.createOrder(orderInfo); //创建订单

                            if (orderInfo.getFromin().equals("微信") && orderInfo.getOrder_type() == 2) {
                                String key = "finsh_"+orderInfo.getOrder_id();
                                addRedis(3, key, "true", 20 * 60 * 60);
                            }
                        }
                    }
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
