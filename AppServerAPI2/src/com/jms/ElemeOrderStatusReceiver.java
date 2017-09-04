package com.jms;

import com.alibaba.fastjson.JSONObject;
import com.framework.controller.BasicComponent;
import com.framework.mapping.system.*;
import com.framework.system.SystemConfig;
import com.framework.util.StringUtil;
import com.opensdk.dianwoda.factory.APIFactoryDianWoDa;
import com.opensdk.eleme.exception.ApiOpException;
import com.opensdk.eleme.exception.ApiSysException;
import com.opensdk.eleme.factory.APIFactoryEleme;
import com.opensdk.eleme.vo.MessageParam;
import com.opensdk.eleme.vo.OrderParam;
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
 * 饿了么推送订单处理
 */
@Component
@EnableJms
public class ElemeOrderStatusReceiver extends BasicComponent {

    @Resource(name = "StoresService")
    protected StoresService storesService;

    @Resource(name = "OrderService")
    protected OrderService orderService;

    @Resource(name = "MemberService")
    protected MemberService memberService;

    @Resource(name = "WeiXinService")
    protected WeiXinService weiXinService;

    /**
     * 饿了么推送订单处理
     *
     * @param message2
     */

    @JmsListener(containerFactory = "jmsListenerContainerFactory", destination = "elemeorderstatus.queue", concurrency = "1-2")
    public void onMessage(Message message2) {
        try {
            String data = ((TextMessage) message2).getText();
            MessageParam message = JSONObject.toJavaObject(JSONObject.parseObject(data), MessageParam.class);

            //业务操作代码
            if (message.getPush_action() == 2) {//订单状态变更
                Date d = new Date(message.getTimestamp() * 1000);
                if (message.getNew_status().equals("-1")) {//订单已取消
                    order_cancel(message);
                } else if (message.getNew_status().equals("1")) {//订单等待餐厅确认
                    order_wait_confirm(message);
                } else if (message.getNew_status().equals("2")) {//订单已处理
                    orderHasBeenProcessed(message);
                } else if (message.getNew_status().equals("9")) {//订单已完成
                    orderCompleted(message, d);
                }

            } else if (message.getPush_action() == 3) { //退单

                Date d = new Date(message.getTimestamp() * 1000);
                if (message.getRefund_status() == 2) {//用户申请退单
                    userForBack(message, d);
                } else if (message.getRefund_status() == 3) {//餐厅不同意退单
                    notAgreeToRefund(message, d);
                } else if (message.getRefund_status() == 4) {//退单仲裁中
                    arbitrationInArbitration(message, d);
                } else if (message.getRefund_status() == 5) {//退单失败
                    singleFailure(message, d);
                } else if (message.getRefund_status() == 6) {//退单成功
                    singleSuccess(message, d);
                }
            } else if (message.getPush_action() == 4) { //配送状态
                CdsOrderInfo order = new CdsOrderInfo();
                order.setOrder_desc(message.getEleme_order_id());
                order.addConditionField("order_desc");
                order.resetParamFields("order_status,stores_id,order_id,member_id,total_price,receiver_name,task_user_name,task_user_phone,goods,brand_id,fromin");
                order = sqlDao.getRecord(order);
                if (order != null) {
                    Date d = new Date(Long.parseLong(message.getUpdate_at()));

                    if (message.getStatus_code() == 1) { //待分配（物流系统已生成运单，待分配配送商）
                        toBeAssigned(order, d);
                    } else if (message.getStatus_code() == 2) { //待分配（配送系统已接单，待分配配送员）
                        toBeAssignedSend(order, d);
                    } else if (message.getStatus_code() == 3) { //待取餐（已分配给配送员，配送员未取餐）
                        waitingMeal(message, order, d);
                    } else if (message.getStatus_code() == 4) { //配送中（配送员已取餐，正在配送）
                        delivering(message, order, d);
                        weiXinService.sendTaskOrder(order);
                    } else if (message.getStatus_code() == 5) { //配送成功（配送员配送完成）
                        sendCompleted(order, d);
                        weiXinService.sendFinishOrder(order);
                    } else if (message.getStatus_code() == 6) { //配送取消（商家可以重新发起配送）
                        deliveryCancellation(message, order, d);
                    } else if (message.getStatus_code() == 7) { //配送异常（商家可以重新发起配送）
                        abnormalDistribution(message, order, d);
                    }
                } else {
                    create_order(message);
                }
            }

        } catch (JMSException e) {
            e.printStackTrace();
        }
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
        cdsOrderLog.setOpt_note("配送员已取餐：" + message.getName() + " " + message.getPhone());
        cdsOrderLog.setOpt_name("系统操作");
        cdsOrderLog.setOpt_time(d);
        cdsOrderLog.addParamFields();

        order.setOrder_status(3);
        order.setTask_user_time(d);
        order.setTask_user_name(message.getName());
        order.setTask_user_phone(message.getPhone());
        order.resetParamFields("order_status,task_user_name,task_user_phone,task_user_time");
        order.addConditionField("order_id");

        sqlDao.updateRecord(order);
        sqlDao.insertRecord(cdsOrderLog);//插入订单操作日志
    }

    /**
     * 待取餐（已分配给配送员，配送员未取餐）
     *
     * @param message
     * @param order
     * @param d
     */
    private void waitingMeal(MessageParam message, CdsOrderInfo order, Date d) {
        CdsOrderLog cdsOrderLog = new CdsOrderLog();
        cdsOrderLog.setOl_id(StringUtil.getPrimaryOrderKey());
        cdsOrderLog.setOrder_id(order.getOrder_id());
        cdsOrderLog.setOpt_type("系统");
        cdsOrderLog.setOrder_status_chi("待取餐");
        cdsOrderLog.setOpt_note("分配给配送员：" + message.getName() + " " + message.getPhone());
        cdsOrderLog.setOpt_name("系统操作");
        cdsOrderLog.setOpt_time(d);
        cdsOrderLog.addParamFields();

        order.setOrder_status(2);
        order.setTask_order_name(message.getName());
        order.setTask_order_phone(message.getPhone());
        order.setTask_order_time(d);
        order.resetParamFields("order_status,task_order_name,task_order_phone,task_order_time");
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
        if (message.getSub_status_code() == null) {
            send_exception = "";
        } else if (message.getSub_status_code().equals("1")) {
            send_exception = "商家取消";
        } else if (message.getSub_status_code().equals("2")) {
            send_exception = "配送商取消";
        } else if (message.getSub_status_code().equals("3")) {
            send_exception = "用户取消";
        } else if (message.getSub_status_code().equals("4")) {
            send_exception = "物流系统取消";
        } else if (message.getSub_status_code().equals("5")) {
            send_exception = "呼叫配送晚";
        } else if (message.getSub_status_code().equals("6")) {
            send_exception = "餐厅出餐问题";
        } else if (message.getSub_status_code().equals("7")) {
            send_exception = "商户中断配送";
        } else if (message.getSub_status_code().equals("8")) {
            send_exception = "用户不接电话";
        } else if (message.getSub_status_code().equals("9")) {
            send_exception = "用户退单";
        } else if (message.getSub_status_code().equals("10")) {
            send_exception = "用户地址错误";
        } else if (message.getSub_status_code().equals("11")) {
            send_exception = "超出服务范围";
        } else if (message.getSub_status_code().equals("12")) {
            send_exception = " 骑手标记异常";
        } else if (message.getSub_status_code().equals("13")) {
            send_exception = "订单超过3小时未送达";
        } else {
            send_exception = "其他异常";
        }

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
     * 配送异常（商家可以重新发起配送）
     *
     * @param message
     * @param order
     * @param d
     */
    private void abnormalDistribution(MessageParam message, CdsOrderInfo order, Date d) {
        CdsOrderLog cdsOrderLog = new CdsOrderLog();
        cdsOrderLog.setOl_id(StringUtil.getPrimaryOrderKey());
        cdsOrderLog.setOrder_id(order.getOrder_id());
        cdsOrderLog.setOpt_type("系统");
        String send_exception = "配送异常:";
        if (message.getSub_status_code() == null) {
            send_exception = "";
        } else if (message.getSub_status_code().equals("1")) {
            send_exception = "商家取消";
        } else if (message.getSub_status_code().equals("2")) {
            send_exception = "配送商取消";
        } else if (message.getSub_status_code().equals("3")) {
            send_exception = "用户取消";
        } else if (message.getSub_status_code().equals("4")) {
            send_exception = "物流系统取消";
        } else if (message.getSub_status_code().equals("5")) {
            send_exception = "呼叫配送晚";
        } else if (message.getSub_status_code().equals("6")) {
            send_exception = "餐厅出餐问题";
        } else if (message.getSub_status_code().equals("7")) {
            send_exception = "商户中断配送";
        } else if (message.getSub_status_code().equals("8")) {
            send_exception = "用户不接电话";
        } else if (message.getSub_status_code().equals("9")) {
            send_exception = "用户退单";
        } else if (message.getSub_status_code().equals("10")) {
            send_exception = "用户地址错误";
        } else if (message.getSub_status_code().equals("11")) {
            send_exception = "超出服务范围";
        } else if (message.getSub_status_code().equals("12")) {
            send_exception = " 骑手标记异常";
        } else if (message.getSub_status_code().equals("13")) {
            send_exception = "订单超过3小时未送达";
        } else {
            send_exception = "其他异常";
        }

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
     * 退单成功
     *
     * @param message
     * @param d
     */
    private void singleSuccess(MessageParam message, Date d) {
        CdsOrderInfo order = new CdsOrderInfo();
        order.setOrder_desc(message.getEleme_order_id());
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
                cdsBackMoney.setBp_check_time(d);
                cdsBackMoney.setBp_check_remark("退单成功");
                cdsBackMoney.addParamFields("back_status,bp_check_time,bp_check_remark");
                sqlDao.updateRecord(cdsBackMoney);
            }
        }
    }

    /**
     * 退单失败（用户取消退款）
     *
     * @param message
     * @param d
     */
    private void singleFailure(MessageParam message, Date d) {
        CdsOrderInfo order = new CdsOrderInfo();
        order.setOrder_desc(message.getEleme_order_id());
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
                cdsBackMoney.setBp_check_time(d);
                cdsBackMoney.setDo_status(1);
                cdsBackMoney.setBp_check_remark("退单失败");
                cdsBackMoney.addParamFields("back_status,bp_check_time,bp_check_remark,do_status");
                sqlDao.updateRecord(cdsBackMoney);
            }
        }
    }

    /**
     * 退单仲裁中
     *
     * @param message
     * @param d
     */
    private void arbitrationInArbitration(MessageParam message, Date d) {
        CdsOrderInfo order = new CdsOrderInfo();
        order.setOrder_desc(message.getEleme_order_id());
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
                cdsBackMoney.setBack_status(4);
                cdsBackMoney.setBp_check_time(d);
                cdsBackMoney.setBp_check_remark("退单仲裁中");
                cdsBackMoney.addParamFields("back_status,bp_check_time,bp_check_remark");
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
        order.setOrder_desc(message.getEleme_order_id());
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
                cdsBackMoney.setBp_check_time(d);
                cdsBackMoney.setBp_check_remark("餐厅不同意退单");
                cdsBackMoney.addParamFields("back_status,bp_check_time,bp_check_remark");
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
        order.setOrder_desc(message.getEleme_order_id());
        order.addConditionField("order_desc");
        order.resetParamFields("order_status,stores_id,order_id,member_id,total_price,receiver_name");
        order = sqlDao.getRecord(order);
        if (order != null) {
            CdsBackMoney cdsBackMoney = new CdsBackMoney();
            cdsBackMoney.setDo_status(0);
            cdsBackMoney.setBp_id(StringUtil.getPrimaryOrderKey());
            cdsBackMoney.setOrder_id(order.getOrder_id());
            cdsBackMoney.setMember_id(order.getMember_id());
            cdsBackMoney.setBp_number(StringUtil.getPrimaryOrderKey());
            cdsBackMoney.setBack_status(1);
            cdsBackMoney.setBp_price(order.getTotal_price());
            cdsBackMoney.setOpt_type("系统");
            cdsBackMoney.setBp_operator(order.getReceiver_name());
            cdsBackMoney.setBp_time(d);
            cdsBackMoney.setStores_id(order.getStores_id());
            cdsBackMoney.addParamFields();
            sqlDao.insertRecord(cdsBackMoney);
        } else {
            create_order(message);
        }
    }

    /**
     * 订单已完成
     *
     * @param message
     * @param d
     */
    private void orderCompleted(MessageParam message, Date d) {
        CdsOrderInfo order = new CdsOrderInfo();
        order.setOrder_desc(message.getEleme_order_id());
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
        order.setOrder_desc(message.getEleme_order_id());
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
     * 订单等待餐厅确认
     *
     * @param message
     */
    private void order_wait_confirm(MessageParam message) {
        CdsOrderInfo order = new CdsOrderInfo();
        order.setOrder_desc(message.getEleme_order_id());
        order.addConditionField("order_desc");
        order = sqlDao.getRecord(order);
        if (order != null) {
            String content = null;
            try {
                content = APIFactoryEleme.getOrderAPI().orderReceived(SystemConfig.GetSystemParamEleme(), order.getOrder_desc());
                JSONObject jo = JSONObject.parseObject(content);
                if (jo.getString("code").equals("200")) {
                    orderService.shopReceiveOrder(order);
                }
            } catch (ApiOpException e) {
                e.printStackTrace();
            } catch (ApiSysException e) {
                e.printStackTrace();
            }
        } else {
            create_order(message);
        }
    }

    /**
     * 创建不存在的订单
     *
     * @param message
     */
    private void create_order(MessageParam message) {
        try {
            OrderParam orderParam = APIFactoryEleme.getOrderAPI().orderGetOrderDetail(SystemConfig.GetSystemParamEleme(), message.getEleme_order_id());
            CdsStores stores = storesService.GetStores(orderParam.getTp_restaurant_id());
            if (stores != null) {
                CdsMember member = memberService.GetMember(orderParam, stores);
                CdsOrderInfo orderInfo = new CdsOrderInfo();
                orderInfo.setElemeParam(member, orderParam, stores); //设置饿了么订单的相关信息
                orderInfo.setOrder_no(storesService.getStoresNo(orderInfo, stores.getBrand_fromno_start(), orderInfo.getCreate_date()));//获取订单流水号
                orderService.createOrder(orderInfo); //创建订单
            }

            jmsTemplate.send("elemeorderstatus.queue", new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage(JSONObject.toJSONString(message));
                }
            });
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
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
        order.setOrder_desc(message.getEleme_order_id());
        order.addConditionField("order_desc");
        order.resetParamFields("order_status,cancel_type,cancel_remark,order_id,send_id,send_name,is_sync,create_date,total_price,goods,member_id");
        order = sqlDao.getRecord(order);

        if (message.getExtra() != null && !message.getExtra().equals("")) {
            JSONObject jo = JSONObject.parseObject(message.getExtra());
            order.setCancel_type(jo.getString("description"));
        } else {
            order.setCancel_type("其他原因");
        }

        if (order != null) {
            String messageStr = "其他原因";
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

            order.setCancel_remark(messageStr);

            //取消订单
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
                cdsBackMoney.setFromin("饿了么");
                cdsBackMoney.setOpt_type("平台");
                cdsBackMoney.setBp_operator("平台操作");
                cdsBackMoney.setBp_time(new Date());
                cdsBackMoney.setStores_id(order.getStores_id());
                cdsBackMoney.setBp_remark(order.getCancel_remark());
                cdsBackMoney.addParamFields();
                sqlDao.insertRecord(cdsBackMoney);
            }
        } else {
            create_order(message);//创建不存在的订单
        }
    }
}
