package com.jms;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.framework.controller.BasicComponent;
import com.framework.mapping.system.*;
import com.framework.util.StringUtil;
import com.opensdk.weixin.util.HttpUtil;
import com.service.MemberService;
import com.service.StoresService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 发送配送信息
 */
@Component
@EnableJms
public class mySendReceiver extends BasicComponent {

    @Resource(name = "MemberService")
    protected MemberService memberService;

    @Resource(name = "StoresService")
    protected StoresService storesService;

    /**
     * 计算
     * @param message
     */
    //@JmsListener(containerFactory="jmsListenerContainerFactory",destination = "cds_order_send.queue",concurrency="5-60")
    public void onMessage(Message message) {
        try {
            logger.info("OrderSendReceiver接收到消息:"+((TextMessage)message).getText());
            String data = ((TextMessage)message).getText();
            CdsOrderInfo send = JSONObject.toJavaObject(JSONObject.parseObject(data), CdsOrderInfo.class);
            String result = HttpUtil.getRequest(send.getUrl());
            JSONObject jo = JSONObject.parseObject(result);
            if(jo.getJSONArray("results")!=null){
                JSONArray jarr = jo.getJSONArray("results");

                int distance = 0;
                int duration = 0;
                for (int j=0;j<jarr.size();j++){
                    JSONObject obj =  (JSONObject)jarr.get(j);
                    if(distance<obj.getIntValue("distance")){
                        distance = obj.getIntValue("distance");//行驶距离(米)
                        duration = obj.getIntValue("duration");//行驶时间(秒)
                    }
                }
                send.setDistance(distance);
                send.setDuration(duration);
                float price = 2f;
                int kilometre = 0; //计算公里数
                if(distance>0){
                    double b = (float)distance/500;
                    kilometre = (int)Math.ceil(b);
                    price += 1*kilometre;
                }
                send.setSend_price(price);
                send.setKilometre(kilometre);

                send.addParamFields("distance,duration,kilometre,send_price");
                send.addConditionField("order_id");
                sqlDao.updateRecord(send);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }



    //发送手机短信信息
    @JmsListener(containerFactory="jmsListenerContainerFactory",destination = "messageCode",concurrency="5-10")
    public void messageCode(Message message1) {
        try {
            String data = ((TextMessage)message1).getText();
            MessageCode messageCode = JSONObject.toJavaObject(JSONObject.parseObject(data), MessageCode.class);
            String phone = messageCode.getPhone();
            String param = "un=10690177&pw=mayi2016&da="+phone+"&sm="+ StringUtil.bin2hex(messageCode.getContent())+"&dc=15&rd=1";
            String url = "http://101.227.68.49:7891/mt?"+param;

            CloseableHttpClient httpClient = HttpClients.createDefault();

            HttpGet httpGet = new HttpGet(url);
            try {
                CloseableHttpResponse response = httpClient.execute(httpGet);
                /*CdsMessageCode message = new CdsMessageCode();
                Date d = new Date();

                message.setMc_mobile(messageCode.getPhone());
                message.setMc_date(d);
                message.setMc_addip("127.0.0.1");
                message.setMc_type("9");
                message.setMc_content(messageCode.getContent());

                sqlDao.insertRecord("cds_message_code.saveEntity",message);*/
            }catch (IOException e) {
                e.printStackTrace();
            }



        } catch (JMSException e) {
            e.printStackTrace();
        }
    }



    //空订单处理
    //@JmsListener(containerFactory="jmsListenerContainerFactory",destination = "testorder.queue",concurrency="5-15")
    public void messageCod11e(Message message1) {
        try {
            String data = ((TextMessage)message1).getText();
            CdsMember member = JSONObject.toJavaObject(JSONObject.parseObject(data), CdsMember.class);


            CdsMember member_sel = sqlDao.getRecord("cds_member.getMemberCount",member);
            if(member_sel==null){
                member.setIsdo(1);
                member.resetParamFields("isdo");
                member.addConditionField("member_id");
                sqlDao.updateRecord(member);
                return;
            }
            member = member_sel;
            member.setIsdo(1);

            member.resetParamFields("first_order_time,last_order_time,all_order_count,first_order_time1,last_order_time1,all_order_count1,first_order_time2,last_order_time2,all_order_count2,first_order_time3,last_order_time3,all_order_count3,first_order_time4,last_order_time4,all_order_count4,isdo");
            member.addConditionField("member_id");
            sqlDao.updateRecord(member);

            List<CdsMemberStores> cds_member_stores = sqlDao.getRecordList("cds_member.getMemberStoresCount",member);

            CdsMemberStores memberStores = new CdsMemberStores();
            memberStores.setMember_id(member.getMember_id());
            memberStores.addConditionField("member_id");
            sqlDao.deleteRecord(memberStores);

            for (CdsMemberStores memberStore:cds_member_stores) {
                memberStore.addUnParamFields("id");
                sqlDao.insertRecord(memberStore);
            }




        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
