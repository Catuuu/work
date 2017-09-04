package com.jms;

import com.alibaba.fastjson.JSONObject;
import com.framework.controller.BasicComponent;
import com.framework.mapping.system.CdsDianwodaPhone;
import com.framework.mapping.system.CdsOrderInfo;
import com.framework.mapping.system.CdsStores;
import com.framework.util.StringUtil;
import com.opensdk.dianwoda.factory.APIFactoryDianWoDa;
import com.opensdk.shenhou.factory.APIFactoryShenHou;
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
 * 发送配送信息
 */
@Component
@EnableJms
public class OrderSendReceiver extends BasicComponent {

    @Resource(name = "StoresService")
    protected StoresService storesService;

    @Resource(name = "OrderService")
    protected OrderService orderService;

    /**
     * 发送配送信息
     *
     * @param message
     */
    @JmsListener(containerFactory = "jmsListenerContainerFactory", destination = "order.send", concurrency = "1-2")
    public void onMessage(Message message) {
        try {
            String data = ((TextMessage) message).getText();
            CdsOrderInfo cdsOrderInfo = JSONObject.toJavaObject(JSONObject.parseObject(data), CdsOrderInfo.class);

            cdsOrderInfo.setSend_id(StringUtil.getPrimaryOrderKey());
            if (cdsOrderInfo.getGoods() == null || cdsOrderInfo.getGoods().equals("")) {
                String result = "{\"code\":\"99999999\",\"message\":\"订单商品为空\"}";
                orderService.sendOrder(cdsOrderInfo, result);
                return;
            }
            if (cdsOrderInfo.getSend_name().equals("生活半径")) {
                CdsStores stores = storesService.GetStores(cdsOrderInfo.getStores_brand_id());
                String result = APIFactoryShenHou.getPoiAPI().create(cdsOrderInfo, stores);
                orderService.sendOrder(cdsOrderInfo, result);
            } else if (cdsOrderInfo.getSend_name().equals("点我达")) {
                CdsStores stores = storesService.GetStores(cdsOrderInfo.getStores_brand_id());
                CdsDianwodaPhone dianwoda = new CdsDianwodaPhone();
                dianwoda.setPhone(cdsOrderInfo.getReceiver_phone().trim());
                dianwoda.setStores_id(cdsOrderInfo.getStores_id());
                dianwoda.addConditionFields("phone,stores_id");
                dianwoda = sqlDao.getRecord(dianwoda);
                if (dianwoda == null) {
                    String result = APIFactoryDianWoDa.getPoiAPI().order_send(cdsOrderInfo, stores);
                    orderService.sendDwdOrder(cdsOrderInfo, result);
                } else {
                    cdsOrderInfo.setIs_sync(0);
                    cdsOrderInfo.addConditionField("order_id");
                    cdsOrderInfo.resetParamField("is_sync");
                    sqlDao.updateRecord(cdsOrderInfo);

                    //用于点我大骑手点餐，打包扫码时时，订单状态变为完成状态
                    String key = "finsh_"+cdsOrderInfo.getOrder_id();
                    addRedis(3, key, "true", 20 * 60 * 60);
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
