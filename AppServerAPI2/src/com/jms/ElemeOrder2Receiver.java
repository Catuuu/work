package com.jms;

import com.alibaba.fastjson.JSONObject;
import com.framework.controller.BasicComponent;
import com.framework.mapping.system.*;
import com.framework.system.SystemConfig;
import com.framework.util.DateUtil;
import com.framework.util.StringUtil;
import com.opensdk.dianwoda.factory.APIFactoryDianWoDa;
import com.opensdk.eleme.exception.ApiOpException;
import com.opensdk.eleme.exception.ApiSysException;
import com.opensdk.eleme.factory.APIFactoryEleme;
import com.opensdk.eleme.vo.MessageParam;
import com.opensdk.eleme.vo.OrderParam;
import com.opensdk.eleme2.api.entity.order.OOrder;
import com.opensdk.eleme2.api.entity.other.OMessage;
import com.opensdk.eleme2.api.enumeration.order.ReplyReminderType;
import com.opensdk.eleme2.api.exception.ServiceException;
import com.opensdk.eleme2.api.service.OrderService;
import com.opensdk.eleme2.utils.StringUtils;
import com.opensdk.shenhou.factory.APIFactoryShenHou;
import com.service.GiftService;
import com.service.MemberService;
/*import com.service.OrderService;*/
import com.service.StoresService;
import com.service.WeiXinService;
import com.sun.corba.se.impl.orbutil.concurrent.Sync;
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
 * 饿了么订单消息处理2.0
 */
@Component
@EnableJms
public class ElemeOrder2Receiver extends BasicComponent {

    @Resource(name = "MemberService")
    protected MemberService memberService;

    @Resource(name = "StoresService")
    protected StoresService storesService;

   @Resource(name = "OrderService")
    protected com.service.OrderService myorderService;

    @Resource(name = "GiftService")
    protected GiftService giftService;

    @Resource(name = "WeiXinService")
    protected WeiXinService weiXinService;
    /**
     * 饿了么订单及状态推送处理
     * @param message
     */
    @JmsListener(containerFactory="jmsListenerContainerFactory",destination = "elemeorder2.queue",concurrency="1-2")
    public void onMessage(Message message) {
        try {
            String data = ((TextMessage)message).getText();
            OMessage oMessage = JSONObject.toJavaObject(JSONObject.parseObject(data), OMessage.class);
            orderStatusManage(oMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


    /**
     * 订单状态处理
     * @param oMessage
     */
    public void orderStatusManage(OMessage oMessage){
        OrderService orderService = new OrderService(SystemConfig.GetEleme2Config(), getElemeToken());
        switch (oMessage.getType()){
            //新订单处理
            case (10):{
                OOrder orderParam = JSONObject.toJavaObject(JSONObject.parseObject(oMessage.getMessage()),OOrder.class);
                CdsStores stores = storesService.GetStores(Integer.parseInt(orderParam.getOpenId()));
                if(stores!=null){
                    CdsMember member = memberService.GetMember(orderParam,stores);
                    CdsOrderInfo orderInfo = new CdsOrderInfo();
                    orderInfo.setElemeParam(member,orderParam,stores); //设置饿了么订单的相关信息
                    orderInfo.setOrder_no(storesService.getStoresNo(orderInfo,stores.getBrand_fromno_start(),orderInfo.getCreate_date()));//获取订单流水号
                    orderInfo = giftService.addGiftGoods(member.getPhone(),orderInfo);
                    myorderService.createOrderNew(orderInfo); //创建订单
                }
                break;
            }
            //商户已接单
            case (12):{

                break;
            }
            //订单被取消
            case (14):{
                order_cancel(oMessage);
                System.out.println(oMessage.getMessage());
                System.out.println("1");
                System.out.println("2");
                break;
            }
            //订单置为无效
            case (15):{

                break;
            }
            //订单强制无效
            case (17):{
//                JSONObject jo = JSONObject.parseObject(oMessage.getMessage());
//                order_cancel(oMessage,jo.get("orderId").toString(),"商户强制取消");
                cacelOrder(oMessage,2);
                break;
            }
            //订单完结
            case (18):{
                orderCompleted(oMessage);
                break;
            }
            //用户申请取消单
            case (20):{
                if (oMessage!=null){
                    //申请取消单1
                    cacelOrder(oMessage,1);
                }
                break;
            }
            //用户取消取消单申请
            case (21):{
                if (oMessage!=null){
                    //申请取消取消单6
                    cacelOrder(oMessage,6);
                }
                break;
            }
            //商户拒绝取消单
            case (22):{
                if (oMessage!=null){
                    cacelOrder(oMessage,3);
                }
                break;
            }
            //商户同意取消单
            case (23):{
                if (oMessage!=null){
                    cacelOrder(oMessage,2);
                }
                break;
            }
            //用户申请仲裁取消单
            case (24):{
                if (oMessage!=null){
                    cacelOrder(oMessage,4);
                }
                break;
            }
            //客服仲裁取消单申请有效
            case (25):{
                if (oMessage!=null){
                    cacelOrder(oMessage,2);
                }
                break;
            }
            //客服仲裁取消单申请无效
            case (26):{
                if (oMessage!=null){
                    cacelOrder(oMessage,5);
                }
                break;
            }
            //用户申请退单
            case (30):{
                if (oMessage!=null){
                    cacelOrder(oMessage,30);
                }
                break;
            }
            //用户取消退单
            case (31):{
                if (oMessage!=null){
                    cacelOrder(oMessage,31);
                }
                break;
            }
            //商户拒绝退单
            case (32):{
                if (oMessage!=null){
                    cacelOrder(oMessage,32);
                }
                break;
            }
            //商户同意退单
            case (33):{
                if (oMessage!=null){
                    cacelOrder(oMessage,2);
                }
                break;
            }
            //用户申请仲裁
            case (34):{
                if (oMessage!=null){
                    cacelOrder(oMessage,34);
                }
                break;
            }
            //客服仲裁退单有效
            case (35):{
                if (oMessage!=null){
                    cacelOrder(oMessage,2);
                }
                break;
            }
            //客服仲裁退单无效
            case (36):{
                if (oMessage!=null){
                    cacelOrder(oMessage,36);
                }
                break;
            }
            //用户催单
            case (45):{
                saveReminder(oMessage);
                break;
            }
            //商家回复用户催单
            case (46):{
                updateReminder(oMessage);
                break;
            }
            //订单待分配配送商
            case (51):{
                Date d = new Date(oMessage.getTimestamp());
                OOrder orderParam = JSONObject.toJavaObject(JSONObject.parseObject(oMessage.getMessage()),OOrder.class);
                CdsOrderInfo order = new CdsOrderInfo();
                order.setOrder_desc(orderParam.getId());
                order.addConditionField("order_desc");
                order = sqlDao.getRecord(order);
                toBeAssigned(order,d);
                break;
            }
            //订单待分配配送员
            case (52):{
                Date d = new Date(oMessage.getTimestamp());
                OOrder orderParam = JSONObject.toJavaObject(JSONObject.parseObject(oMessage.getMessage()),OOrder.class);
                CdsOrderInfo order = new CdsOrderInfo();
                order.setOrder_desc(orderParam.getId());
                order.addConditionField("order_desc");
                order = sqlDao.getRecord(order);
                toBeAssignedSend(order,d);
                break;
            }
            //配送员取餐中
            case (53):{
//                waitingMeal(OMessage oMessage)
                break;
            }
            //配送员已到店
            case (54):{

                break;
            }
            //配送员配送中
            case (55):{

                break;
            }
            //配送成功
            case (56):{

                break;
            }
            //配送取消，商户取消
            case (57):{

                break;
            }
            //配送取消，用户取消
            case (58):{

                break;
            }
            //配送取消，物流系统取消
            case (59):{

                break;
            }
            //配送失败，呼叫配送晚
            case (60):{

                break;
            }
            //配送失败，餐厅出餐问题
            case (61):{

                break;
            }
            //配送失败，商户中断配送
            case (62):{

                break;
            }
            //配送失败，用户不接电话
            case (63):{

                break;
            }
            //配送失败，用户退单
            case (64):{

                break;
            }
            //配送失败，用户地址错误
            case (65):{

                break;
            }
            //配送失败，超出服务范围
            case (66):{

                break;
            }
            //配送失败，骑手标记异常
            case (67):{

                break;
            }
            //配送失败，系统自动标记异常
            case (68):{

                break;
            }
            //配送失败，其他异常
            case (69):{

                break;
            }
            //配送失败，超时标记异常
            case (70):{

                break;
            }
            //自行配送
            case (71):{

                break;
            }
            //不再配送
            case (72):{

                break;
            }
            //物流拒单，仅支持在线支付
            case (73):{

                break;
            }
            //物流拒单，超出服务范围
            case (74):{

                break;
            }
            //物流拒单，请求配送晚
            case (75):{

                break;
            }
            //物流拒单，系统异常
            case (76):{

                break;
            }
            //店铺营业状态通知
            case (91):{

                break;
            }
            //店铺有效性通知
            case (92):{

                break;
            }

            default:{
//                logger.info("ElemeOrderReceiver接收到消息:"+((TextMessage)message).getText());
            }
        }
    }



    /**
     * 创建不存在的订单
     *
     * @param oMessage
     */
    private void create_order(OMessage oMessage) {

        OrderService orderService = new OrderService(SystemConfig.GetEleme2Config(), getElemeToken());
        OOrder orderParam = JSONObject.toJavaObject(JSONObject.parseObject(oMessage.getMessage()),OOrder.class);

        CdsStores stores = storesService.GetStores(Integer.parseInt(orderParam.getOpenId()));
        if(stores!=null){
            CdsMember member = memberService.GetMember(orderParam,stores);
            CdsOrderInfo orderInfo = new CdsOrderInfo();
            orderInfo.setElemeParam(member,orderParam,stores); //设置饿了么订单的相关信息
            orderInfo.setOrder_no(storesService.getStoresNo(orderInfo,stores.getBrand_fromno_start(),orderInfo.getCreate_date()));//获取订单流水号
            orderInfo = giftService.addGiftGoods(member.getPhone(),orderInfo);
            myorderService.createOrderNew(orderInfo); //创建订单
            orderStatusManage(oMessage);
        }

    }


    /**
     * 订单已取消
     *
     * @param oMessage
     */
    private void order_cancel(OMessage oMessage) {
        JSONObject jom = JSONObject.parseObject(oMessage.getMessage());
        Date d = new Date(oMessage.getTimestamp());
        CdsOrderInfo order = new CdsOrderInfo();
        order.setOrder_desc(jom.getString("orderId"));
        order.addConditionField("order_desc");
        order.resetParamFields("order_status,cancel_type,cancel_remark,order_id,send_id,send_name,is_sync,create_date,total_price,goods,member_id");
        order = sqlDao.getRecord(order);

        if (jom.getString("reason") != null && !"".equals(jom.getString("reason"))) {
            order.setCancel_type(jom.getString("reason"));
        } else {
            order.setCancel_type("其他原因");
        }

        if (order != null) {
            String messageStr = "其他原因";
            if (order.getSend_name().equals("点我达") && order.getIs_sync() == 2) {
                String result = APIFactoryDianWoDa.getPoiAPI().cancel(order);

                JSONObject jo = JSONObject.parseObject(result);
                if (jo.getString("success").equals("true")) {
                    myorderService.sendCancel(order, null);
                } else {
                    messageStr = jo.getString("message");
                }
            } else if (order.getSend_name().equals("生活半径") && order.getIs_sync() == 2) {
                String result = APIFactoryShenHou.getPoiAPI().cancel(order);

                JSONObject jo = JSONObject.parseObject(result);
                if (jo.getString("code").equals("100000")) {
                    myorderService.sendCancel(order, null);
                } else {
                    messageStr = jo.getString("message");
                }
            }

            order.setCancel_remark(messageStr);

            //取消订单
            myorderService.cancelorder(order, null);
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
            create_order(oMessage);//创建不存在的订单
        }
}


    /**
     * 商家回复催单后修改催单内容
     * @param oMessage
     */
    private void updateReminder(OMessage oMessage){
        JSONObject jom = JSONObject.parseObject(oMessage.getMessage());
        Date d = new Date(oMessage.getTimestamp());
        String remindId = jom.getString("remindId");//催单id
        CdsReminder cr = new CdsReminder();
        if (!org.springframework.util.StringUtils.isEmpty(remindId)){
            cr.setReminder_id(Integer.valueOf(remindId));
            cr.addConditionField("remind_id");
            cr = sqlDao.getRecord(cr);
            if (cr!=null){
                cr.setDo_status(2);//催单已处理
                cr.setCr_check_remark("");//催单回复内容
                cr.addConditionField("cr_id");
                cr.addUnParamFields("cr_id");
                sqlDao.updateRecord(cr);
            }
        }
    }

    /**
     * 催单自动回复
     * @param oMessage
     */
    private void saveReminder(OMessage oMessage){
        JSONObject jom = JSONObject.parseObject(oMessage.getMessage());
        Date d = new Date(oMessage.getTimestamp());
        CdsOrderInfo order = new CdsOrderInfo();
        order.setOrder_desc(jom.getString("orderId"));
        order.addConditionField("order_desc");
        order.resetParamFields("order_status,cancel_type,cancel_remark,order_id,task_user_name,task_user_phone,stores_id");
        order = sqlDao.getRecord(order);
        if (order!=null){
            String remindId = jom.getString("remindId");//催单id
            CdsReminder cr = new CdsReminder();
            if (!org.springframework.util.StringUtils.isEmpty(remindId)){
                cr.setReminder_id(Integer.valueOf(remindId));
            }

            cr.addConditionField("remindId");
            cr = sqlDao.getRecord(cr);
            if (cr!=null){
                //多次催单
                cr.setDo_status(1);//未处理
                cr.setTimes(2); //催单次数
                cr.setCr_time(d);   //催单时间
                cr.setCr_check_remark("");
                cr.addUnParamFields("cr_id");
                cr.addConditionField("cr_id");
                sqlDao.updateRecord(cr);
            }else{
                //第一次催单
                cr = new CdsReminder();
                cr.setCr_time(d);   //催单时间
                cr.setOrder_id(order.getOrder_id());
                cr.setStores_id(order.getStores_id());
                cr.setTimes(1);  //催单次数
                String cr_check_remark = ""; //回复信息(配送信息)
                String name = order.getTask_user_name();
                String phone = order.getTask_user_phone();
                if(name!=null&&phone!=null){
                    cr_check_remark = "您的外卖已送出，配送员"+name+",电话"+phone;
                }
                if ("".equals(cr_check_remark)){     //配送信息为空
                    cr.setDo_status(1);//未处理
                }else{
                    cr.setDo_status(2);//已处理
                    cr.setCr_check_remark(cr_check_remark);
                    cr.setType(1);   //回复方式（1系统回复、2人工回复）
                    cr.setCr_check_time(DateUtil.getToday());
                    OrderService orderServiceElme = new OrderService(SystemConfig.GetEleme2Config(), getElemeToken());
                    try {
                        orderServiceElme.replyReminder(remindId, ReplyReminderType.custom, cr_check_remark);
                    } catch (ServiceException e) {
                        cr.setDo_status(1);//未处理
                        cr.setCr_check_remark("系统回复失败");
                        cr.setType(0);
                        e.printStackTrace();
                    }
                }
                cr.addParamFields();
                cr.addUnParamFields("cr_id");
                sqlDao.insertRecord(cr);

            }

        }
    }


    /**
     * 订单已完成
     *
     * @param oMessage
     *
     */
    private void orderCompleted(OMessage oMessage) {
        JSONObject jom = JSONObject.parseObject(oMessage.getMessage());
        Date d = new Date(Long.valueOf(jom.get("updateTime").toString()));
        CdsOrderInfo order = new CdsOrderInfo();
        order.setOrder_desc(jom.getString("orderId"));
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
            create_order(oMessage);
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
     * 订单等待餐厅确认
     *
     * @param message
     */
    private void order_wait_confirm(OMessage oMessage,String orderId,String message) {
        CdsOrderInfo order = new CdsOrderInfo();
        order.setOrder_desc(orderId);
        order.addConditionField("order_desc");
        order = sqlDao.getRecord(order);
        if (order != null) {
            String content = null;
            try {
                content = APIFactoryEleme.getOrderAPI().orderReceived(SystemConfig.GetSystemParamEleme(), order.getOrder_desc());
                JSONObject jo = JSONObject.parseObject(content);
                if (jo.getString("code").equals("200")) {
                    myorderService.shopReceiveOrder(order);
                }
            } catch (ApiOpException e) {
                e.printStackTrace();
            } catch (ApiSysException e) {
                e.printStackTrace();
            }
        } else {
            create_order(oMessage);
        }
    }

    /**
     * 用户申请退单
     *
     * @param message
     * @param
     */
    private void userForBack(OMessage oMessage,String orderId,String message) {
        CdsOrderInfo order = new CdsOrderInfo();
        order.setOrder_desc(orderId);
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
//            cdsBackMoney.setBp_time(d);
            cdsBackMoney.setStores_id(order.getStores_id());
            cdsBackMoney.addParamFields();
            sqlDao.insertRecord(cdsBackMoney);
        } else {
            create_order(oMessage);
        }
    }

    private void cacelOrder(OMessage oMessage,int type){
        JSONObject jom = JSONObject.parseObject(oMessage.getMessage());
        Date d = new Date(oMessage.getTimestamp());
        CdsOrderInfo order = new CdsOrderInfo();
        order.setOrder_desc(jom.getString("orderId"));
        order.addConditionField("order_desc");
        order.resetParamFields("order_status,stores_id,order_id,member_id,total_price,receiver_name");
        order = sqlDao.getRecord(order);
        if (jom.getString("reason") != null && !"".equals(jom.getString("reason"))) {
            order.setCancel_type(jom.getString("reason"));
        } else {
            order.setCancel_type("其他原因");
        }

        //配送平台取消订单
        if (order != null&&type==2) {
            String messageStr = "其他原因";
            if ("点我达".equals(order.getSend_name()) && order.getIs_sync() == 2) {
                String result = APIFactoryDianWoDa.getPoiAPI().cancel(order);

                JSONObject jo = JSONObject.parseObject(result);
                if ("true".equals(jo.getString("success"))) {
                    myorderService.sendCancel(order, null);
                } else {
                    messageStr = jo.getString("message");
                }
            } else if ("生活半径".equals(order.getSend_name()) && order.getIs_sync() == 2) {
                String result = APIFactoryShenHou.getPoiAPI().cancel(order);

                JSONObject jo = JSONObject.parseObject(result);
                if ("100000".equals(jo.getString("code"))) {
                    myorderService.sendCancel(order, null);
                } else {
                    messageStr = jo.getString("message");
                }
            }
            order.setCancel_remark(messageStr);
        }
        //平台取消订单
        if (order != null) {
            CdsBackMoney cdsBackMoney = new CdsBackMoney();
            cdsBackMoney.setOrder_id(order.getOrder_id());
            cdsBackMoney.setBack_status(1);
            cdsBackMoney.addConditionFields("order_id,back_status");
            cdsBackMoney = sqlDao.getRecord(cdsBackMoney);
            //首次取消单（退单）
            if (cdsBackMoney==null){
                cdsBackMoney = new CdsBackMoney();
                if (type==1){
                    //申请取消订单(只有申请取消是未处理，其他都是已处理)
                    cdsBackMoney.setDo_status(0);
                }else{
                    cdsBackMoney.setDo_status(1);
                }
                cdsBackMoney.setBp_remark(jom.getString("reason"));
                cdsBackMoney.setBp_id(StringUtil.getPrimaryOrderKey());
                cdsBackMoney.setOrder_id(order.getOrder_id());
                cdsBackMoney.setMember_id(order.getMember_id());
                cdsBackMoney.setBp_number(StringUtil.getPrimaryOrderKey());
                cdsBackMoney.setBack_status(type);
                cdsBackMoney.setBp_price(order.getTotal_price());
                cdsBackMoney.setOpt_type("用户");
                cdsBackMoney.setFromin("饿了么 ");
                cdsBackMoney.setBp_operator(order.getReceiver_name());
                cdsBackMoney.setBp_time(DateUtil.getToday());
                cdsBackMoney.setStores_id(order.getStores_id());
                cdsBackMoney.setBp_id(StringUtil.getPrimaryOrderKey());
                cdsBackMoney.addParamFields();
                sqlDao.insertRecord(cdsBackMoney);
             //取消单（退单）状态变更
            }else{
                cdsBackMoney.setBp_time(DateUtil.getToday());
                cdsBackMoney.setBack_status(type);
                if (type==1){
                    //申请取消订单(只有申请取消是未处理，其他都是已处理)
                    cdsBackMoney.setDo_status(0);
                }else{
                    cdsBackMoney.setDo_status(1);
                }
                cdsBackMoney.setBp_remark(jom.getString("reason"));
                cdsBackMoney.addConditionField("bp_id");
                cdsBackMoney.addUnParamFields("bp_id");
                sqlDao.updateRecord(cdsBackMoney);
            }
            if (type==2){
                //平台同意取消订单(改变订单状态)
                myorderService.cancelorder(order,null);
            }
        } else {
            create_order(oMessage);
        }
    }


}
