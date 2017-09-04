package com.jms;

import com.alibaba.fastjson.JSONObject;
import com.framework.controller.BasicComponent;
import com.framework.mapping.system.CdsMember;
import com.framework.mapping.system.CdsOrderInfo;
import com.framework.mapping.system.CdsStores;
import com.framework.system.SystemConfig;
import com.opensdk.eleme.exception.ApiOpException;
import com.opensdk.eleme.exception.ApiSysException;
import com.opensdk.eleme.factory.APIFactoryEleme;
import com.opensdk.eleme.vo.MessageParam;
import com.opensdk.eleme.vo.OrderParam;
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
public class ElemeOrderReceiver extends BasicComponent {

    @Resource(name = "MemberService")
    protected MemberService memberService;

    @Resource(name = "StoresService")
    protected StoresService storesService;

    @Resource(name = "OrderService")
    protected OrderService orderService;

    @Resource(name = "GiftService")
    protected GiftService giftService;
    /**
     * 饿了么订单及状态推送处理
     * @param message
     */
    @JmsListener(containerFactory="jmsListenerContainerFactory",destination = "elemeorder.queue",concurrency="1-2")
    public void onMessage(Message message) {
        try {
            String data = ((TextMessage)message).getText();
            MessageParam messageParam = JSONObject.toJavaObject(JSONObject.parseObject(data), MessageParam.class);

            //新订单处理
            if(messageParam.getPush_action()==1){
                OrderParam orderParam = APIFactoryEleme.getOrderAPI().orderGetOrderDetail(SystemConfig.GetSystemParamEleme(),messageParam.getEleme_order_id());
                CdsStores stores = storesService.GetStores(orderParam.getTp_restaurant_id());
                if(stores!=null){
                    CdsMember member = memberService.GetMember(orderParam,stores);
                    CdsOrderInfo orderInfo = new CdsOrderInfo();
                    orderInfo.setElemeParam(member,orderParam,stores); //设置饿了么订单的相关信息
                    orderInfo.setOrder_no(storesService.getStoresNo(orderInfo,stores.getBrand_fromno_start(),orderInfo.getCreate_date()));//获取订单流水号
                    orderInfo = giftService.addGiftGoods(member.getPhone(),orderInfo);
                    orderService.createOrder(orderInfo); //创建订单
                }
            }else{
                logger.info("ElemeOrderReceiver接收到消息:"+((TextMessage)message).getText());
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
