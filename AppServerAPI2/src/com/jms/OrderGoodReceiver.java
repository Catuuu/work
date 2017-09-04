package com.jms;

import com.alibaba.fastjson.JSONObject;
import com.framework.controller.BasicComponent;
import com.framework.mapping.system.CdsMsGoodsList;
import com.framework.mapping.system.CdsOrderInfo;
import com.service.OrderService;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.List;

/**
 * 订单商品进行处理
 */
@Component
@EnableJms
public class OrderGoodReceiver extends BasicComponent {

    @Resource(name = "OrderService")
    protected OrderService orderService;

    /**
     * 订单商品进行处理
     * @param message
     */
    @JmsListener(containerFactory="jmsListenerContainerFactory",destination = "order.good",concurrency="2-5")
    public void onMessage(Message message) {
        try {
            //logger.info("OrderGoodReceiver接收到消息:"+((TextMessage)message).getText());
            String data = ((TextMessage)message).getText();
            CdsOrderInfo cdsOrderInfo = JSONObject.toJavaObject(JSONObject.parseObject(data), CdsOrderInfo.class);
           // orderService.orderGoodDo(cdsOrderInfo);


            List<CdsMsGoodsList> insertList = JSONObject.parseArray(cdsOrderInfo.getMs_goods_info(),CdsMsGoodsList.class);
            sqlDao.insertRecord("cds_good.insertBatGoodRecord", insertList);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
