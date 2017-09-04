package com.jms;

import com.alibaba.fastjson.JSONObject;
import com.framework.controller.BasicComponent;
import com.framework.mapping.system.CdsOrderInfo;
import com.framework.mapping.system.CdsOrderLog;
import com.framework.util.StringUtil;
import com.opensdk.dianwoda.vo.DwdMessageParam;
import com.service.WeiXinService;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.Date;

/**
 * 点我达消息处理
 */
@Component
@EnableJms
public class DianWoDanReceiver extends BasicComponent {
    @Resource(name = "WeiXinService")
    protected WeiXinService weiXinService;
    /**
     * 点我达订单状态推送处理
     * @param mess
     */
    @JmsListener(containerFactory="jmsListenerContainerFactory",destination = "dianWoDan.queue",concurrency="1-2")
    public void onMessage(Message mess) {

        try {
            String data = ((TextMessage)mess).getText();
            DwdMessageParam message = JSONObject.toJavaObject(JSONObject.parseObject(data), DwdMessageParam.class);

            CdsOrderInfo order = new CdsOrderInfo();
            order.setSend_id(message.getOrder_original_id());
            order.addConditionField("send_id");
            order.resetParamFields("order_status,stores_id,order_id,member_id,total_price,receiver_name,task_user_name,task_user_phone,goods,brand_id,fromin");
            order = sqlDao.getRecord(order);
            if(order!=null){
                Date d = new Date(message.getTime_status_update());
                if(message.getOrder_status()==0){//派单中 （物流系统已生成运单，待分配配送商）
                    toBeAssignedSend(order, d);
                }else if(message.getOrder_status()==5){//取餐中 （已分配给配送员，配送员未取餐）
                    waitingMeal(message, order, d);
                }else if(message.getOrder_status()==10){//已到店 （等餐中）

                }else if(message.getOrder_status()==15){//配送中
                    delivering(message, order, d);
                    weiXinService.sendTaskOrder(order);
                }else if(message.getOrder_status()==100){//已完成
                    sendCompleted(order, d);
                    weiXinService.sendFinishOrder(order);
                }else if(message.getOrder_status()==98){//异常
                    deliveryException(message, order, d);
                }else if(message.getOrder_status()==99){//已取消
                    deliveryCancellation(message, order, d);
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


    /**
     * 配送取消（商家可以重新发起配送）
     * @param message
     * @param order
     * @param d
     */
    private void deliveryCancellation(DwdMessageParam message, CdsOrderInfo order, Date d) {
        CdsOrderLog cdsOrderLog = new CdsOrderLog();
        cdsOrderLog.setOl_id(StringUtil.getPrimaryOrderKey());
        cdsOrderLog.setOrder_id(order.getOrder_id());
        cdsOrderLog.setOpt_type("系统");
        String send_exception = "配送取消:"+message.getCancel_reason();


        cdsOrderLog.setOrder_status_chi(" 配送取消");
        cdsOrderLog.setOpt_note(send_exception);
        cdsOrderLog.setOpt_name("系统操作");
        cdsOrderLog.setOpt_time(d);
        cdsOrderLog.addParamFields();

        order.setOrder_status(1);
        order.setSend_exception(send_exception);
        order.setService_exception_time(d);
        order.resetParamFields("order_status,send_exception,service_exception_time");
        order.addConditionField("order_id");

        sqlDao.updateRecord(order);
        sqlDao.insertRecord(cdsOrderLog);//插入订单操作日志
    }

    /**
     * 配送取消（商家可以重新发起配送）
     * @param message
     * @param order
     * @param d
     */
    private void deliveryException(DwdMessageParam message, CdsOrderInfo order, Date d) {
        CdsOrderLog cdsOrderLog = new CdsOrderLog();
        cdsOrderLog.setOl_id(StringUtil.getPrimaryOrderKey());
        cdsOrderLog.setOrder_id(order.getOrder_id());
        cdsOrderLog.setOpt_type("系统");
        String send_exception = "配送异常:"+message.getAbnormal_reason();


        cdsOrderLog.setOrder_status_chi(" 配送异常");
        cdsOrderLog.setOpt_note(send_exception);
        cdsOrderLog.setOpt_name("系统操作");
        cdsOrderLog.setOpt_time(d);
        cdsOrderLog.addParamFields();

        order.setOrder_status(1);
        order.setSend_exception(send_exception);
        order.setService_exception_time(d);
        order.resetParamFields("order_status,send_exception,service_exception_time");
        order.addConditionField("order_id");

        sqlDao.updateRecord(order);
        sqlDao.insertRecord(cdsOrderLog);//插入订单操作日志
    }

    /**
     * 待分配（配送系统已接单，待分配配送员）
     * @param order
     * @param d
     */
    private void toBeAssignedSend(CdsOrderInfo order, Date d) {
        CdsOrderLog cdsOrderLog = new CdsOrderLog();
        cdsOrderLog.setOl_id(StringUtil.getPrimaryOrderKey());
        cdsOrderLog.setOrder_id(order.getOrder_id());
        cdsOrderLog.setOpt_type("系统");
        cdsOrderLog.setOrder_status_chi("待分配配送员");
        cdsOrderLog.setOpt_note("配送系统已接单，待分配配送员");
        cdsOrderLog.setOpt_name("系统操作");
        cdsOrderLog.setOpt_time(d);
        cdsOrderLog.addParamFields();

        sqlDao.insertRecord(cdsOrderLog);//插入订单操作日志
    }

    /**
     * 待取餐（已分配给配送员，配送员未取餐）
     * @param message
     * @param order
     * @param d
     */
    private void waitingMeal(DwdMessageParam message, CdsOrderInfo order, Date d) {
        CdsOrderLog cdsOrderLog = new CdsOrderLog();
        cdsOrderLog.setOl_id(StringUtil.getPrimaryOrderKey());
        cdsOrderLog.setOrder_id(order.getOrder_id());
        cdsOrderLog.setOpt_type("系统");
        cdsOrderLog.setOrder_status_chi("待取餐");
        cdsOrderLog.setOpt_note("分配给配送员："+message.getRider_name()+" "+message.getRider_mobile());
        cdsOrderLog.setOpt_name("系统操作");
        cdsOrderLog.setOpt_time(d);
        cdsOrderLog.addParamFields();

        order.setOrder_status(2);
        order.setTask_order_code(message.getRider_code());
        order.setTask_order_name(message.getRider_name());
        order.setTask_order_phone(message.getRider_mobile());
        order.setTask_order_time(d);
        order.resetParamFields("order_status,task_order_code,task_order_name,task_order_phone,task_order_time");
        order.addConditionField("order_id");

        sqlDao.updateRecord(order);
        sqlDao.insertRecord(cdsOrderLog);//插入订单操作日志
    }

    /**
     * 配送中（配送员已取餐，正在配送）
     * @param message
     * @param order
     * @param d
     */
    private void delivering(DwdMessageParam message, CdsOrderInfo order, Date d) {
        CdsOrderLog cdsOrderLog = new CdsOrderLog();
        cdsOrderLog.setOl_id(StringUtil.getPrimaryOrderKey());
        cdsOrderLog.setOrder_id(order.getOrder_id());
        cdsOrderLog.setOpt_type("系统");
        cdsOrderLog.setOrder_status_chi(" 配送中");
        cdsOrderLog.setOpt_note("配送员已取餐："+message.getRider_name()+" "+message.getRider_mobile());
        cdsOrderLog.setOpt_name("系统操作");
        cdsOrderLog.setOpt_time(d);
        cdsOrderLog.addParamFields();

        order.setOrder_status(3);
        order.setTask_user_time(d);
        order.setTask_user_code(message.getRider_code());
        order.setTask_user_name(message.getRider_name());
        order.setTask_user_phone(message.getRider_mobile());
        order.resetParamFields("order_status,task_user_code,task_user_name,task_user_phone,task_user_time");
        order.addConditionField("order_id");

        sqlDao.updateRecord(order);
        sqlDao.insertRecord(cdsOrderLog);//插入订单操作日志
    }

    /**
     * 配送成功（配送员配送完成）
     * @param order
     * @param d
     */
    private void sendCompleted(CdsOrderInfo order, Date d) {
        CdsOrderLog cdsOrderLog = new CdsOrderLog();
        cdsOrderLog.setOl_id(StringUtil.getPrimaryOrderKey());
        cdsOrderLog.setOrder_id(order.getOrder_id());
        cdsOrderLog.setOpt_type("系统");
        cdsOrderLog.setOrder_status_chi(" 已送达");
        cdsOrderLog.setOpt_note("订单已送达");
        cdsOrderLog.setOpt_name("系统操作");
        cdsOrderLog.setOpt_time(d);
        cdsOrderLog.addParamFields();

        order.setOrder_status(4);
        order.setTask_time(d);

        order.resetParamFields("order_status,task_time");
        order.addConditionField("order_id");

        sqlDao.updateRecord(order);
        sqlDao.insertRecord(cdsOrderLog);//插入订单操作日志
    }
}
