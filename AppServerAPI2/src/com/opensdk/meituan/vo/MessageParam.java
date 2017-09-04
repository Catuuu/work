package com.opensdk.meituan.vo;

/**
 * Created by Administrator on 2017/2/25.
 * 美团消息推送
 */
public class MessageParam {
    /**
     * 推送动作类型
     * 2、推送已确认订单回调URL
     * 3、推送已完成订单回调URL
     * 4、美团用户或客服取消URL
     * 5、美团用户或客服退款流程操作URL
     * 6、订单配送状态回调URL
     * 7、美团用户或客服部分退款流程操作URL
     * 8、订单结算信息回调URL
     * 9、门店状态变更回调URL
     */
    private int push_action;

    //美团订单ID
    private String order_id;
    //系统级参数
    private String app_id;
    private long timestamp;
    private String sig;


    //2推送已确认订单 及 3推送已完成订单
    private int status; //订单状态 1用户已提交订单 2 可推送到APP方平台也可推送到商家 3 商家已收到 4、商家已确认 8、已完成 9、已取消
    private long utime; //订单更新时间

     //4、美团用户或客服取消URL
    private String reason_code;  //

    //5、美团用户或客服退款流程操作
    private String notify_type;//通知类型 apply：发起退款 agree：确认退款 reject：驳回退款 cancelRefund：用户取消退款申请 cancelRefundComplaint :取消退款申诉
    private String reason;     //原因 4\5

    //6订单配送状态回调
    private String dispatcher_name;   //配送员姓名
    private String dispatcher_mobile; //配送员手机号码
    private long time;                //配送时间
    private int logistics_status;     //配送状态列表 0、配送单发往配送 10、配送单已确认 20、骑手已取餐 40、骑手已送达 100、配送单已取消

    //8、订单结算信息回调
    private float offlineOrderSkPayAmount; //
    private float settleAmount;            //店铺预计收入
    private int shippingType;              //配送方式code列表 0000、商家自配  0002、趣活  0016、达达
                                          // 0033、E代送  1001、美团专送-加盟  1002 美团专送-自建
                                          //1003  美团配送-众包  1004  美团专送-城市代理
                                          //2001 角马  2002 快送
    private float shippingAmount;         //配送费
    private float userPayAmount;          //
    private float foodAmount;             //小计(包括餐盒费)
    private float commisionAmount;        //平台服务费
    private float totalActivityAmount;    //活动支出


    public String getReason_code() {
        return reason_code;
    }

    public void setReason_code(String reason_code) {
        this.reason_code = reason_code;
    }

    public int getPush_action() {
        return push_action;
    }

    public void setPush_action(int push_action) {
        this.push_action = push_action;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getUtime() {
        return utime;
    }

    public void setUtime(long utime) {
        this.utime = utime;
    }

    public String getDispatcher_name() {
        return dispatcher_name;
    }

    public void setDispatcher_name(String dispatcher_name) {
        this.dispatcher_name = dispatcher_name;
    }

    public String getDispatcher_mobile() {
        return dispatcher_mobile;
    }

    public void setDispatcher_mobile(String dispatcher_mobile) {
        this.dispatcher_mobile = dispatcher_mobile;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getLogistics_status() {
        return logistics_status;
    }

    public void setLogistics_status(int logistics_status) {
        this.logistics_status = logistics_status;
    }

    public float getOfflineOrderSkPayAmount() {
        return offlineOrderSkPayAmount;
    }

    public void setOfflineOrderSkPayAmount(float offlineOrderSkPayAmount) {
        this.offlineOrderSkPayAmount = offlineOrderSkPayAmount;
    }

    public float getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(float settleAmount) {
        this.settleAmount = settleAmount;
    }

    public int getShippingType() {
        return shippingType;
    }

    public void setShippingType(int shippingType) {
        this.shippingType = shippingType;
    }

    public float getShippingAmount() {
        return shippingAmount;
    }

    public void setShippingAmount(float shippingAmount) {
        this.shippingAmount = shippingAmount;
    }

    public float getUserPayAmount() {
        return userPayAmount;
    }

    public void setUserPayAmount(float userPayAmount) {
        this.userPayAmount = userPayAmount;
    }

    public float getFoodAmount() {
        return foodAmount;
    }

    public void setFoodAmount(float foodAmount) {
        this.foodAmount = foodAmount;
    }

    public float getCommisionAmount() {
        return commisionAmount;
    }

    public void setCommisionAmount(float commisionAmount) {
        this.commisionAmount = commisionAmount;
    }

    public float getTotalActivityAmount() {
        return totalActivityAmount;
    }

    public void setTotalActivityAmount(float totalActivityAmount) {
        this.totalActivityAmount = totalActivityAmount;
    }

    public String getNotify_type() {
        return notify_type;
    }

    public void setNotify_type(String notify_type) {
        this.notify_type = notify_type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
