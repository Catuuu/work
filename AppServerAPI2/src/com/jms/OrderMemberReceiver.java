package com.jms;

import com.alibaba.fastjson.JSONObject;
import com.framework.controller.BasicComponent;
import com.framework.mapping.system.CdsOrderInfo;
import com.service.MemberService;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * 更新用户信息
 */
@Component
@EnableJms
public class OrderMemberReceiver extends BasicComponent {
    @Resource(name = "MemberService")
    protected MemberService memberService;

    /**
     * 更新用户信息
     * @param message
     */
    @JmsListener(containerFactory="jmsListenerContainerFactory",destination = "order.member",concurrency="1-2")
    public void onMessage(Message message) {
        try {
            //logger.info("OrderMemberReceiver接收到消息:"+((TextMessage)message).getText());
            String data = ((TextMessage)message).getText();
            CdsOrderInfo cdsOrderInfo = JSONObject.toJavaObject(JSONObject.parseObject(data), CdsOrderInfo.class);
            memberService.updateMember(cdsOrderInfo);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


}
