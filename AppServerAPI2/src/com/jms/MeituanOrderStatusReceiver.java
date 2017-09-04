package com.jms;

import com.alibaba.fastjson.JSONObject;
import com.framework.controller.BasicComponent;
import com.framework.mapping.system.*;
import com.framework.system.SystemConfig;
import com.framework.util.StringUtil;
import com.opensdk.dianwoda.factory.APIFactoryDianWoDa;
import com.opensdk.meituan.exception.ApiOpException;
import com.opensdk.meituan.exception.ApiSysException;
import com.opensdk.meituan.factory.APIFactoryMeituan;
import com.opensdk.meituan.vo.MessageParam;
import com.opensdk.meituan.vo.OrderDetailParam;
import com.opensdk.shenhou.factory.APIFactoryShenHou;
import com.service.MemberService;
import com.service.OrderService;
import com.service.StoresService;
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
import java.util.Date;

/**
 * 美团推送订单处理
 */
@Component
@EnableJms
public class MeituanOrderStatusReceiver extends BasicComponent {

    @Resource(name = "StoresService")
    protected StoresService storesService;

    @Resource(name = "OrderService")
    protected OrderService orderService;

    @Resource(name = "MemberService")
    protected MemberService memberService;

    @Resource(name = "WeiXinService")
    protected WeiXinService weiXinService;

    /**
     * 美团推送订单处理
     *
     * @param message2
     */

    @JmsListener(containerFactory = "jmsListenerContainerFactory", destination = "meituanorderstatus.queue", concurrency = "1-2")
    public void onMessage(Message message2) {
        try {
            String data = ((TextMessage) message2).getText();
            MessageParam message = JSONObject.toJavaObject(JSONObject.parseObject(data), MessageParam.class);

            //业务操作代码
            if (message.getPush_action() == 2) {//订单已确认
                Date d = new Date(message.getUtime() * 1000);
                orderHasBeenProcessed(message);
            } else if (message.getPush_action() == 3) {//订单已完成
                Date d = new Date(message.getUtime() * 1000);
                orderCompleted(message, d);
            } else if (message.getPush_action() == 4) {//用户取消订单
                Date d = new Date(message.getTimestamp() * 1000);
                order_cancel(message);
            } else if (message.getPush_action() == 5) {//美团用户或客服退款流程操作
                Date d = new Date(message.getTimestamp() * 1000);
                if (message.getNotify_type().equals("apply")) {//发起退款
                    userForBack(message, d);
                } else if (message.getNotify_type().equals("agree")) {//确认退款
                    singleSuccess(message, d);
                } else if (message.getNotify_type().equals("reject")) {//驳回退款
                    notAgreeToRefund(message, d);
                } else if (message.getNotify_type().equals("cancelRefund")) {//用户取消退款申请
                    singleFailure(message, d);
                } else if (message.getNotify_type().equals("cancelRefundComplaint")) {//取消退款申诉
                    singleFailure(message, d);
                }

            } else if (message.getPush_action() == 6) {//订单配送状态
                CdsOrderInfo order = new CdsOrderInfo();
                order.setOrder_desc(message.getOrder_id());
                order.addConditionField("order_desc");
                order.resetParamFields("order_status,stores_id,order_id,member_id,total_price,receiver_name,goods,task_user_name,task_user_phone,brand_id,fromin");
                order = sqlDao.getRecord(order);
                if (order != null) {
                    Date d = new Date(message.getTime() * 1000);
                    if (message.getLogistics_status() == 0) {//0、配送单发往配送
                        toBeAssigned(order, d);
                    } else if (message.getLogistics_status() == 10) {//10、配送单已确认
                        toBeAssignedSend(order, d);
                    } else if (message.getLogistics_status() == 20) {//20、骑手已取餐
                        delivering(message, order, d);
                        weiXinService.sendTaskOrder(order);
                    } else if (message.getLogistics_status() == 40) {//40、骑手已送达
                        sendCompleted(order, d);
                        weiXinService.sendFinishOrder(order);
                    } else if (message.getLogistics_status() == 100) {//100、配送单已取消
                        deliveryCancellation(message, order, d);
                    }
                } else {
                    create_order(message);
                }
            } else if (message.getPush_action() == 8) {//订单结算信息回调
                //OrderSettlement(message);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


    /**
     * 订单已取消
     *
     * @param message
     */
    private void order_cancel(MessageParam message) {
        CdsOrderInfo order = new CdsOrderInfo();
        order.setOrder_desc(message.getOrder_id());
        order.addConditionField("order_desc");
        order.resetParamFields("order_status,cancel_type,cancel_remark,order_id,send_id,send_name,is_sync,create_date,goods,total_price,member_id");
        order = sqlDao.getRecord(order);
        //reason_code
        if (message.getReason_code().equals("1001")) {
            order.setCancel_type("系统,超时未确认");
        } else if (message.getReason_code().equals("1002")) {
            order.setCancel_type("系统,30分钟未支付");
        } else if (message.getReason_code().equals("1101")) {
            order.setCancel_type("支付中取消");
        } else if (message.getReason_code().equals("1102")) {
            order.setCancel_type("商家确认前取消");
        } else if (message.getReason_code().equals("1103")) {
            order.setCancel_type("用户退款取消");
        } else if (message.getReason_code().equals("1201")) {
            order.setCancel_type("客服取消,用户下错单");
        } else if (message.getReason_code().equals("1202")) {
            order.setCancel_type("客服取消,用户测试");
        } else if (message.getReason_code().equals("1203")) {
            order.setCancel_type("客服取消,重复订单");
        } else if (message.getReason_code().equals("1204")) {
            order.setCancel_type("客服取消,其他原因");
        } else {
            order.setCancel_type("其他原因");
        }
        order.setCancel_remark(message.getReason());

        if (order != null) {
            String messageStr = null;
            if (order.getSend_name().equals("点我达") && order.getIs_sync() == 2) {
                String result = APIFactoryDianWoDa.getPoiAPI().cancel(order);

                JSONObject jo = JSONObject.parseObject(result);
                if (jo.getString("success").equals("true")) {
                    orderService.sendCancel(order, null);
                } else {
                    messageStr = jo.getString("message");
                }
            } else if (order.getSend_name().equals("生活半径") && order.getIs_sync() == 2) {
                String result = APIFactoryShenHou.getPoiAPI().cancel(order);

                JSONObject jo = JSONObject.parseObject(result);
                if (jo.getString("code").equals("100000")) {
                    orderService.sendCancel(order, null);
                } else {
                    messageStr = jo.getString("message");
                }
            }
            if (messageStr == null) {
                order.setCancel_remark(message.getReason());
            } else {
                order.setCancel_remark(message.getReason() + ",配送取消异常：" + messageStr);
            }
            orderService.cancelorder(order, null);
            weiXinService.sendCancelOrder(order);

            //平台取消--保存信息到退款表(排除系统取消)
            CdsBackMoney bm = new CdsBackMoney();
            bm.setOrder_id(order.getOrder_id());
            bm.addConditionFields("order_id");
            bm = sqlDao.getRecord(bm);
            if (bm == null) {
                CdsBackMoney cdsBackMoney = new CdsBackMoney();
                cdsBackMoney.setDo_status(0);
                cdsBackMoney.setBp_id(StringUtil.getPrimaryOrderKey());
                cdsBackMoney.setOrder_id(order.getOrder_id());
                cdsBackMoney.setMember_id(order.getMember_id());
                cdsBackMoney.setBp_number(StringUtil.getPrimaryOrderKey());
                cdsBackMoney.setBack_status(7);//平台取消
                cdsBackMoney.setBp_price(order.getTotal_price());
                cdsBackMoney.setFromin("美团");
                cdsBackMoney.setOpt_type("平台");
                cdsBackMoney.setBp_operator("平台操作");
                cdsBackMoney.setBp_time(new Date());
                cdsBackMoney.setStores_id(order.getStores_id());
                cdsBackMoney.setBp_remark(order.getCancel_remark());
                cdsBackMoney.addParamFields();
                sqlDao.insertRecord(cdsBackMoney);
            }else if(bm!=null && bm.getBack_status()==1 && bm.getDo_status()==0){

                CdsBackMoney cdsBackMoney = new CdsBackMoney();
                cdsBackMoney.setBp_id(bm.getBp_id());
                cdsBackMoney.setDo_status(1);
                cdsBackMoney.setBack_status(2);
                cdsBackMoney.setBp_check_remark(order.getCancel_remark());
                cdsBackMoney.setBp_check_time(new Date());
                cdsBackMoney.setSend_type("其他原因");
                cdsBackMoney.resetParamFields("do_status,bp_check_time,bp_check_remark,back_status,send_type");
                sqlDao.updateRecord(cdsBackMoney);
            }

        } else {
            create_order(message);//创建不存在的订单
        }
    }


    /**
     * 退单失败(用户取消退款)
     *
     * @param message
     * @param d
     */
    private void singleFailure(MessageParam message, Date d) {
        CdsOrderInfo order = new CdsOrderInfo();
        order.setOrder_desc(message.getOrder_id());
        order.addConditionField("order_desc");
        order.resetParamFields("order_status,stores_id,order_id,member_id,total_price,receiver_name");
        order = sqlDao.getRecord(order);
        if (order != null) {
            CdsBackMoney cdsBackMoney = new CdsBackMoney();
            cdsBackMoney.setOrder_id(order.getOrder_id());
            cdsBackMoney.setBack_status(1);
            cdsBackMoney.addConditionFields("order_id,back_status");
            cdsBackMoney = sqlDao.getRecord(cdsBackMoney);
            if (cdsBackMoney != null) {
                cdsBackMoney.addConditionField("bp_id");
                cdsBackMoney.setBack_status(5);
                cdsBackMoney.setDo_status(1);
                cdsBackMoney.setBp_check_time(d);
                cdsBackMoney.setBp_check_remark(message.getReason());
                if (message.getReason() == null || message.getReason().equals("")) {
                    cdsBackMoney.setBp_check_remark("用户取消退款");
                }
                cdsBackMoney.addParamFields("back_status,bp_check_time,bp_check_remark,do_status");
                sqlDao.updateRecord(cdsBackMoney);
            }
        }
    }

    /**
     * 餐厅不同意退单
     *
     * @param message
     * @param d
     */
    private void notAgreeToRefund(MessageParam message, Date d) {
        CdsOrderInfo order = new CdsOrderInfo();
        order.setOrder_desc(message.getOrder_id());
        order.addConditionField("order_desc");
        order.resetParamFields("order_status,stores_id,order_id,member_id,total_price,receiver_name");
        order = sqlDao.getRecord(order);
        if (order != null) {
            CdsBackMoney cdsBackMoney = new CdsBackMoney();
            cdsBackMoney.setOrder_id(order.getOrder_id());
            cdsBackMoney.setBack_status(1);
            cdsBackMoney.addConditionFields("order_id,back_status");
            cdsBackMoney = sqlDao.getRecord(cdsBackMoney);
            if (cdsBackMoney != null) {
                cdsBackMoney.addConditionField("bp_id");
                cdsBackMoney.setBack_status(3);
                cdsBackMoney.setDo_status(1);
                cdsBackMoney.setBp_check_time(d);
                cdsBackMoney.setBp_check_remark(message.getReason());
                if (message.getReason() == null || message.getReason().equals("")) {
                    cdsBackMoney.setBp_check_remark("客服驳回退款");
                }
                cdsBackMoney.addParamFields("back_status,bp_check_time,bp_check_remark,do_status");
                sqlDao.updateRecord(cdsBackMoney);
            }
        }
    }

    /**
     * 退单成功
     *
     * @param message
     * @param d
     */
    private void singleSuccess(MessageParam message, Date d) {
        CdsOrderInfo order = new CdsOrderInfo();
        order.setOrder_desc(message.getOrder_id());
        order.addConditionField("order_desc");
        order.resetParamFields("order_status,stores_id,order_id,member_id,total_price,receiver_name");
        order = sqlDao.getRecord(order);
        if (order != null) {
            CdsBackMoney cdsBackMoney = new CdsBackMoney();
            cdsBackMoney.setOrder_id(order.getOrder_id());
            cdsBackMoney.setBack_status(1);
            cdsBackMoney.addConditionFields("order_id,back_status");
            cdsBackMoney = sqlDao.getRecord(cdsBackMoney);
            if (cdsBackMoney != null) {
                cdsBackMoney.addConditionField("bp_id");
                cdsBackMoney.setBack_status(2);
                cdsBackMoney.setDo_status(1);
                cdsBackMoney.setBp_check_time(d);
                cdsBackMoney.setBp_check_remark(message.getReason());
                if (message.getReason() == null || message.getReason().equals("")) {
                    cdsBackMoney.setBp_check_remark("客服同意退款");
                }
                cdsBackMoney.addParamFields("back_status,bp_check_time,bp_check_remark,do_status");
                sqlDao.updateRecord(cdsBackMoney);
            }
        }
    }

    /**
     * 用户申请退单
     *
     * @param message
     * @param d
     */
    private void userForBack(MessageParam message, Date d) {
        CdsOrderInfo order = new CdsOrderInfo();
        order.setOrder_desc(message.getOrder_id());
        order.addConditionField("order_desc");
        order.resetParamFields("order_status,stores_id,order_id,member_id,total_price,receiver_name");
        order = sqlDao.getRecord(order);

        CdsBackMoney bm = new CdsBackMoney();
        bm.setOrder_id(order.getOrder_id());
        bm.addConditionFields("order_id");
        bm = sqlDao.getRecord(bm);
        if (bm == null) {
            if (order != null) {
                CdsBackMoney cdsBackMoney = new CdsBackMoney();
                cdsBackMoney.setDo_status(0);
                cdsBackMoney.setBp_id(StringUtil.getPrimaryOrderKey());
                cdsBackMoney.setOrder_id(order.getOrder_id());
                cdsBackMoney.setMember_id(order.getMember_id());
                cdsBackMoney.setBp_number(StringUtil.getPrimaryOrderKey());
                cdsBackMoney.setBack_status(1);
                cdsBackMoney.setBp_price(order.getTotal_price());
                cdsBackMoney.setOpt_type("用户");
                cdsBackMoney.setFromin("美团");
                cdsBackMoney.setBp_remark(message.getReason());
                cdsBackMoney.setBp_operator("用户操作");
                cdsBackMoney.setBp_time(d);
                cdsBackMoney.setStores_id(order.getStores_id());
                cdsBackMoney.addParamFields();
                sqlDao.insertRecord(cdsBackMoney);
            } else {
                create_order(message);
            }
        }
    }

    /**
     * 订单结算信息
     *
     * @param message
     */
    private void OrderSettlement(MessageParam message) {
        //logger.info("订单结算====="+JSONObject.toJSONString(message));
        try {
            OrderDetailParam orderParam = APIFactoryMeituan.getOrderAPI().orderGetOrderDetail(SystemConfig.GetSystemParamMeituan(), Long.parseLong(message.getOrder_id()), 1);

            CdsOrderInfo order = new CdsOrderInfo();
            order.setOrder_desc(String.valueOf(orderParam.getOrder_id()));
            order.addConditionField("order_desc");
            order.resetParamFields("order_id");
            order = sqlDao.getRecord(order);
            if (order != null) {
                order.setIncome(message.getSettleAmount());//更新店铺实际收入
                order.setServiceFee(message.getCommisionAmount());//平台服务费
                order.setShip_fee(message.getShippingAmount());//用户支出配送费
                order.resetParamFields("income,serviceFee,ship_fee");

                order.addConditionField("order_id");
                sqlDao.updateRecord(order);
            } else {
                create_order(message);
            }

        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }


    /**
     * 配送取消（商家可以重新发起配送）
     *
     * @param message
     * @param order
     * @param d
     */
    private void deliveryCancellation(MessageParam message, CdsOrderInfo order, Date d) {
        CdsOrderLog cdsOrderLog = new CdsOrderLog();
        cdsOrderLog.setOl_id(StringUtil.getPrimaryOrderKey());
        cdsOrderLog.setOrder_id(order.getOrder_id());
        cdsOrderLog.setOpt_type("系统");
        String send_exception = "配送取消:";
        /*if(message.getSub_status_code()==null){
            send_exception = "";
        }else if(message.getSub_status_code().equals("1")){
            send_exception = "商家取消";
        }else if(message.getSub_status_code().equals("2")){
            send_exception = "配送商取消";
        }else if(message.getSub_status_code().equals("3")){
            send_exception = "用户取消";
        }else if(message.getSub_status_code().equals("4")){
            send_exception = "物流系统取消";
        }else if(message.getSub_status_code().equals("5")){
            send_exception = "呼叫配送晚";
        }else if(message.getSub_status_code().equals("6")){
            send_exception = "餐厅出餐问题";
        }else if(message.getSub_status_code().equals("7")){
            send_exception = "商户中断配送";
        }else if(message.getSub_status_code().equals("8")){
            send_exception = "用户不接电话";
        }else if(message.getSub_status_code().equals("9")){
            send_exception = "用户退单";
        }else if(message.getSub_status_code().equals("10")){
            send_exception = "用户地址错误";
        }else if(message.getSub_status_code().equals("11")){
            send_exception = "超出服务范围";
        }else if(message.getSub_status_code().equals("12")){
            send_exception = " 骑手标记异常";
        }else if(message.getSub_status_code().equals("13")){
            send_exception = "订单超过3小时未送达";
        }else {
            send_exception = "其他异常";
        }*/

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
     * 配送中（配送员已取餐，正在配送）
     *
     * @param message
     * @param order
     * @param d
     */
    private void delivering(MessageParam message, CdsOrderInfo order, Date d) {
        CdsOrderLog cdsOrderLog = new CdsOrderLog();
        cdsOrderLog.setOl_id(StringUtil.getPrimaryOrderKey());
        cdsOrderLog.setOrder_id(order.getOrder_id());
        cdsOrderLog.setOpt_type("系统");
        cdsOrderLog.setOrder_status_chi(" 配送中");
        cdsOrderLog.setOpt_note("配送员已取餐：" + message.getDispatcher_name() + " " + message.getDispatcher_mobile());
        cdsOrderLog.setOpt_name("系统操作");
        cdsOrderLog.setOpt_time(d);
        cdsOrderLog.addParamFields();

        order.setOrder_status(3);
        order.setTask_user_time(d);
        order.setTask_user_name(message.getDispatcher_name());
        order.setTask_user_phone(message.getDispatcher_mobile());
        order.resetParamFields("order_status,task_user_name,task_user_phone,task_user_time");
        order.addConditionField("order_id");

        sqlDao.updateRecord(order);
        sqlDao.insertRecord(cdsOrderLog);//插入订单操作日志
    }

    /**
     * 待分配（配送系统已接单，待分配配送员）
     *
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
     * 待分配（物流系统已生成运单，待分配配送商）
     *
     * @param order
     * @param d
     */
    private void toBeAssigned(CdsOrderInfo order, Date d) {
        CdsOrderLog cdsOrderLog = new CdsOrderLog();
        cdsOrderLog.setOl_id(StringUtil.getPrimaryOrderKey());
        cdsOrderLog.setOrder_id(order.getOrder_id());
        cdsOrderLog.setOpt_type("系统");
        cdsOrderLog.setOrder_status_chi("待分配配送商");
        cdsOrderLog.setOpt_note("物流系统已生成运单，待分配配送商");
        cdsOrderLog.setOpt_name("系统操作");
        cdsOrderLog.setOpt_time(d);
        cdsOrderLog.addParamFields();

        order.setOrder_status(1);
        order.resetParamFields("order_status");
        order.addConditionField("order_id");
        sqlDao.updateRecord(order);
        sqlDao.insertRecord(cdsOrderLog);//插入订单操作日志
    }

    /**
     * 配送成功（配送员配送完成）
     *
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


    /**
     * 订单已完成
     *
     * @param message
     * @param d
     */
    private void orderCompleted(MessageParam message, Date d) {
        CdsOrderInfo order = new CdsOrderInfo();
        order.setOrder_desc(message.getOrder_id());
        order.addConditionField("order_desc");
        order.resetParamFields("order_status,cancel_type,cancel_remark,order_id");
        order = sqlDao.getRecord(order);
        if (order != null) {
            CdsOrderLog cdsOrderLog = new CdsOrderLog();
            cdsOrderLog.setOl_id(StringUtil.getPrimaryOrderKey());
            cdsOrderLog.setOrder_id(order.getOrder_id());
            cdsOrderLog.setOpt_type("系统");
            cdsOrderLog.setOrder_status_chi("订单已完成");
            cdsOrderLog.setOpt_note("订单已完成");
            cdsOrderLog.setOpt_name("系统操作");
            cdsOrderLog.setOpt_time(d);
            cdsOrderLog.addParamFields();

            order.setOrder_status(4);
            order.setTask_time(d);

            //order.resetParamFields("order_status,task_time");
            order.resetParamFields("order_status");
            order.addConditionField("order_id");

            sqlDao.updateRecord(order);
            sqlDao.insertRecord(cdsOrderLog);//插入订单操作日志
        } else {
            create_order(message);
        }
    }

    /**
     * 订单已处理
     *
     * @param message
     */
    private void orderHasBeenProcessed(MessageParam message) {
        CdsOrderInfo order = new CdsOrderInfo();
        order.setOrder_desc(message.getOrder_id());
        order.addConditionField("order_desc");
        order.resetParamFields("order_status,order_id");
        order = sqlDao.getRecord(order);
        if (order != null) {
            order.setOrder_status(1);
            order.resetParamFields("order_status");
            order.addConditionField("order_id");
            sqlDao.updateRecord(order);
        } else {
            create_order(message);//创建不存在的订单
        }
    }


    /**
     * 创建不存在的订单
     *
     * @param message
     */
    private void create_order(MessageParam message) {
        try {
            OrderDetailParam orderParam = APIFactoryMeituan.getOrderAPI().orderGetOrderDetail(SystemConfig.GetSystemParamMeituan(), Long.parseLong(message.getOrder_id()), 1);
            CdsStores stores = storesService.GetStores(Integer.parseInt(orderParam.getApp_poi_code()));

            int from_no = orderParam.getDay_seq();

            if (stores != null && from_no != 0) {
                CdsMember member = memberService.GetMember(orderParam, stores);
                CdsOrderInfo orderInfo = new CdsOrderInfo();
                orderInfo.setMeitianParam(member, orderParam, stores); //设置美团订单的相关信息
                orderInfo.setOrder_no(storesService.getStoresNo(orderInfo, stores.getBrand_fromno_start(), orderInfo.getCreate_date()));//获取订单流水号
                orderService.createOrder(orderInfo); //创建订单

                jmsTemplate.send("meituanorderstatus.queue", new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        return session.createTextMessage(JSONObject.toJSONString(message));
                    }
                });
            }
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }


}
