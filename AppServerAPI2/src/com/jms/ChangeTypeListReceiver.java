package com.jms;


import com.alibaba.fastjson.JSONObject;
import com.framework.controller.BasicComponent;
import com.framework.mapping.system.CdsChangeTypeList;
import com.framework.mapping.system.CdsStores;
import com.service.StoresService;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 收费明细
 */
@Component
@EnableJms
public class ChangeTypeListReceiver extends BasicComponent {

    @Resource(name = "StoresService")
    protected StoresService storesService;

    @JmsListener(containerFactory = "jmsListenerContainerFactory", destination = "caidashi_order_budan.increased", concurrency = "1")
    public void onMessage(Message message) {
        try {
            //logger.info("OrderGoodReceiver接收到消息:"+((TextMessage)message).getText());
            String data = ((TextMessage)message).getText();
            CdsChangeTypeList cdsChangeTypeList = JSONObject.toJavaObject(JSONObject.parseObject(data), CdsChangeTypeList.class);
            CdsStores stores  = storesService.GetStoresById(cdsChangeTypeList.getStores_id());
//            cdsChangeTypeList.setMoney(stores.getCharge_type());
            sqlDao.insertRecord(cdsChangeTypeList);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
