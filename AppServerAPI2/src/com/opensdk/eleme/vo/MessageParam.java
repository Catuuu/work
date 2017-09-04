package com.opensdk.eleme.vo;

/**
 * Created by Administrator on 2017/2/9.
 * 饿了么消息推送
 */
public class MessageParam {
    private long timestamp;       //进行接口调用时的时间戳，即当前时间戳 （时间戳：当前距离Epoch（1970年1月1日） 以秒计算的时间，即unix-timestamp）
    private String consumer_key; //eleme分配给APP的consumer_key
    private String sig;         //输入参数计算后的签名结果


    private int push_action;  //推送动作类型  1、new_order（新订单）:  2、order_status_update（订单状态变更）:  3、refund_order(退单) 4、delivery(配送状态)

    //新订单参数
    //push_action: 1
    private String eleme_order_ids; //eleme_order_ids是英文逗号分隔的饿了么订单ID，如果一次推送只有一个order，那么就是单个ID没有逗号。

    //订单状态变更推送
    //push_action: 2
    private  String eleme_order_id;//饿了么订单ID
    private String tp_order_id;//
    private String new_status;//订单状态  -1 订单已取消  0 订单未处理 1 订单等待餐厅确认 2 订单已处理 9 订单已完成
    private String extra; //用于传递额外的参数，是一个JSON字典  如果是订单状态变为取消，则extra会是如下的形式：{"invalid_type": 1, "description": "太忙"}

    //退单
    //eleme_order_id: 12637645858619090
    //push_action: 3
    private int refund_status; //退单订单状态   0未申请退单 2 (用户申请退单) 3餐厅不同意退单  4退单仲裁中 5退单失败 6退单成功

    // 订单配送状态推送
    // push_action: 4
    // eleme_order_id: 12637645858619090
    private int status_code; //配送状态

    private String sub_status_code;//配送子状态

    private String name;      //配送员姓名
    private String phone;     //配送员电话
    private String update_at; //上传时间

/* 配送状态
1 	TO_BE_ASSIGNED_MERCHANT 待分配（物流系统已生成运单，待分配配送商）
2 	TO_BE_ASSIGNED_COURIER 待分配（配送系统已接单，待分配配送员）
3 	TO_BE_FETCHED 待取餐（已分配给配送员，配送员未取餐）
4 	DELIVERING 配送中（配送员已取餐，正在配送）
5 	COMPLETED 配送成功（配送员配送完成）
6 	CANCELLED 配送取消（商家可以重新发起配送）
7 	EXCEPTION 配送异常*/

/*
配送子状态
1 	MERCHANT_REASON 商家取消
2 	CARRIER_REASON 配送商取消
3 	USER_REASON 用户取消
4 	SYSTEM_REASON 物流系统取消
5 	MERCHANT_CALL_LATE_ERROR 呼叫配送晚
6 	MERCHANT_FOOD_ERROR 餐厅出餐问题
7 	MERCHANT_INTERRUPT_DELIVERY_ERROR 商户中断配送
8 	USER_NOT_ANSWER_ERROR 用户不接电话
9 	USER_RETURN_ORDER_ERROR 用户退单
10 	USER_ADDRESS_ERROR 用户地址错误
11 	DELIVERY_OUT_OF_SERVICE 超出服务范围
12 	CARRIER_REMARK_EXCEPTION_ERROR 骑手标记异常
13 	SYSTEM_MARKED_ERROR 系统自动标记异常–订单超过3小时未送达
14 	OTHER_ERROR 其他异常*/

    public int getPush_action() {
        return push_action;
    }

    public void setPush_action(int push_action) {
        this.push_action = push_action;
    }

    public String getEleme_order_ids() {
        return eleme_order_ids;
    }

    public void setEleme_order_ids(String eleme_order_ids) {
        this.eleme_order_ids = eleme_order_ids;
    }

    public String getEleme_order_id() {
        return eleme_order_id;
    }

    public void setEleme_order_id(String eleme_order_id) {
        this.eleme_order_id = eleme_order_id;
    }

    public String getTp_order_id() {
        return tp_order_id;
    }

    public void setTp_order_id(String tp_order_id) {
        this.tp_order_id = tp_order_id;
    }

    public String getNew_status() {
        return new_status;
    }

    public void setNew_status(String new_status) {
        this.new_status = new_status;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public int getRefund_status() {
        return refund_status;
    }

    public void setRefund_status(int refund_status) {
        this.refund_status = refund_status;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getSub_status_code() {
        return sub_status_code;
    }

    public void setSub_status_code(String sub_status_code) {
        this.sub_status_code = sub_status_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getConsumer_key() {
        return consumer_key;
    }

    public void setConsumer_key(String consumer_key) {
        this.consumer_key = consumer_key;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }
}
