package com.jms;

import com.alibaba.fastjson.JSONObject;
import com.framework.controller.BasicComponent;
import com.framework.mapping.system.CdsOrderInfo;
import com.framework.system.SystemConfig;
import com.opensdk.eleme.exception.ApiOpException;
import com.opensdk.eleme.exception.ApiSysException;
import com.opensdk.eleme.factory.APIFactoryEleme;
import com.opensdk.eleme2.api.exception.ServiceException;
import com.opensdk.meituan.factory.APIFactoryMeituan;
import com.service.OrderService;
import com.service.WeiXinService;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * 发送商家接单信息
 */
@Component
@EnableJms
public class OrderReceiveReceiver extends BasicComponent {
    @Resource(name = "OrderService")
    protected OrderService orderService;

    @Resource(name = "WeiXinService")
    protected WeiXinService weiXinService;

    /**
     * 发送商家接单信息
     *
     * @param message
     */
    @JmsListener(containerFactory = "jmsListenerContainerFactory", destination = "order.receive", concurrency = "1-2")
    public void onMessage(Message message) {
        CdsOrderInfo cdsOrderInfo = null;
        boolean flag = false;
        try {
            String data = ((TextMessage) message).getText();
            cdsOrderInfo = JSONObject.toJavaObject(JSONObject.parseObject(data), CdsOrderInfo.class);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        if (cdsOrderInfo == null) {
            return;
        }
        try {
            if (cdsOrderInfo.getFromin().equals("饿了么")) {
                String content = APIFactoryEleme.getOrderAPI().orderReceived(SystemConfig.GetSystemParamEleme(), cdsOrderInfo.getOrder_desc());
                JSONObject jo = JSONObject.parseObject(content);
                if (jo.getString("code").equals("200")) {
                    flag = true;
                    orderService.shopReceiveOrder(cdsOrderInfo);
                }
            } else if (cdsOrderInfo.getFromin().equals("美团")) {
                String content = APIFactoryMeituan.getOrderAPI().orderConfirm(SystemConfig.GetSystemParamMeituan(), Long.parseLong(cdsOrderInfo.getOrder_desc()));
                if (content.equals("ok")) {
                    flag = true;
                    orderService.shopReceiveOrder(cdsOrderInfo);
                }
            }
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        } catch (com.opensdk.meituan.exception.ApiOpException e) {
            if (e.getCode() == 808) {
                flag = true;
                orderService.shopReceiveOrder(cdsOrderInfo);
            }
        } catch (com.opensdk.meituan.exception.ApiSysException e) {
            e.printStackTrace();
        }

        if (flag == false) {
            int receiver_count = cdsOrderInfo.getCount() + 1;
            if (receiver_count < 4) {
                cdsOrderInfo.setCount(receiver_count);
                String orderstr = JSONObject.toJSONString(cdsOrderInfo);
                jmsTemplate.send("order.receive", new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        return session.createTextMessage(orderstr);
                    }
                });
            }
        }
    }



    /**
     * 发送商家接单信息
     *
     * @param message
     */
    @JmsListener(containerFactory = "jmsListenerContainerFactory", destination = "order.receiveNew", concurrency = "1-2")
    public void onMessageNew(Message message) {
        CdsOrderInfo cdsOrderInfo = null;
        boolean flag = false;
        try {
            String data = ((TextMessage) message).getText();
            cdsOrderInfo = JSONObject.toJavaObject(JSONObject.parseObject(data), CdsOrderInfo.class);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        if (cdsOrderInfo == null) {
            return;
        }
        try {
            if (cdsOrderInfo.getFromin().equals("饿了么")) {
                com.opensdk.eleme2.api.service.OrderService orderServiceE = new com.opensdk.eleme2.api.service.OrderService(SystemConfig.GetEleme2Config(), getElemeToken());
                orderServiceE.confirmOrderLite(cdsOrderInfo.getOrder_desc());
                flag = true;
                orderService.shopReceiveOrder(cdsOrderInfo);
            } else if (cdsOrderInfo.getFromin().equals("美团")) {
                String content = APIFactoryMeituan.getOrderAPI().orderConfirm(SystemConfig.GetSystemParamMeituan(), Long.parseLong(cdsOrderInfo.getOrder_desc()));
                if (content.equals("ok")) {
                    flag = true;
                    orderService.shopReceiveOrder(cdsOrderInfo);
                }
            }
        } catch (com.opensdk.meituan.exception.ApiOpException e) {
            if (e.getCode() == 808) {
                flag = true;
                orderService.shopReceiveOrder(cdsOrderInfo);
            }
        } catch (com.opensdk.meituan.exception.ApiSysException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        if (flag == false) {
            int receiver_count = cdsOrderInfo.getCount() + 1;
            if (receiver_count < 4) {
                cdsOrderInfo.setCount(receiver_count);
                String orderstr = JSONObject.toJSONString(cdsOrderInfo);
                jmsTemplate.send("order.receive", new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        return session.createTextMessage(orderstr);
                    }
                });
            }
        }
    }


}
