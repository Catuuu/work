package com.jms;

import com.alibaba.fastjson.JSONObject;
import com.framework.controller.BasicComponent;
import com.framework.mapping.system.CdsOrderInfo;
import com.service.PrintService;
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
 * 打印客户小票
 */
@Component
@EnableJms
public class OrderPrintReceiver extends BasicComponent {

    @Resource(name = "PrintService")
    protected PrintService printService;
    /**
     * 打印客户小票
     * @param message
     */
    @JmsListener(containerFactory="jmsListenerContainerFactory",destination = "order.print",concurrency="1-2")
    public void onMessage(Message message) {
        try {
            //logger.info("OrderPrintReceiver接收到消息:"+((TextMessage)message).getText());
            String data = ((TextMessage)message).getText();

            CdsOrderInfo cdsOrderInfo = JSONObject.toJavaObject(JSONObject.parseObject(data), CdsOrderInfo.class);
            //logger.info("打印订单==="+cdsOrderInfo.getOrder_id()+","+cdsOrderInfo.getOrder_no());

            //获取数据库流水号
            /*cdsOrderInfo.addParamFields("order_no");
            cdsOrderInfo.addConditionField("order_id");
            CdsOrderInfo dbOrder = sqlDao.getRecord(cdsOrderInfo);
            cdsOrderInfo.setOrder_no(dbOrder.getOrder_no());*/
            //获取数据库流水号

            boolean printFlag = printService.printClient(cdsOrderInfo);
            if(printFlag==false){
                int print_count = cdsOrderInfo.getCount()+1;
                if(print_count<4){
                    cdsOrderInfo.setCount(print_count);
                    String orderstr = JSONObject.toJSONString(cdsOrderInfo);
                    //打印小票
                    jmsTemplate.send("order.print", new MessageCreator() {
                        @Override
                        public Message createMessage(Session session) throws JMSException {
                            return session.createTextMessage(orderstr);
                        }
                    });
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


}
