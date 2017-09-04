package com.jms;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.framework.controller.BasicComponent;
import com.framework.mapping.system.CdsOrderInfo;
import com.service.WeiXinService;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/16 0016.
 * 微信打包消息推送处理
 */
@Component
@EnableJms
public class PackOrderReceiver extends BasicComponent {

    @Resource(name = "WeiXinService")
    protected WeiXinService weiXinService;

    @JmsListener(containerFactory = "jmsListenerContainerFactory", destination = "order.packService", concurrency = "1-2")
    public void onMessage(Message message) {
        List orders = new ArrayList();
        try {
            String data = ((TextMessage) message).getText();
            List orderList = JSONArray.parseArray(data);
            for (Object order : orderList) {
                Map map = new HashMap();
                JSONObject json = JSONObject.parseObject(order.toString());
                String order_id = json.getString("order_id");
                map.put("order_id", order_id);
                orders.add(map);
            }

            List<CdsOrderInfo> resultList = sqlDao.getRecordList("cds_pack.getPackOrders",orders);
            for(CdsOrderInfo cdsOrderInfo : resultList){
                String finsh_order = getRedisString(3, "finsh_" + cdsOrderInfo.getOrder_id());
                if(finsh_order==null){
                    weiXinService.sendOutOrder(cdsOrderInfo,5); //正常单
                }else{
                    weiXinService.sendOutOrder(cdsOrderInfo,6); //完成单
                }

            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
