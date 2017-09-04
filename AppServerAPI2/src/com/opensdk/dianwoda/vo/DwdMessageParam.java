package com.opensdk.dianwoda.vo;

/**
 * Created by Administrator on 2017/3/2.
 */
public class DwdMessageParam {
    private String order_original_id; //渠道订单编号，派发订单接口中的order_original_id值
    private int order_status;   //订单状态
    private String cancel_reason; //订单取消原因
    private String abnormal_reason;//异常订单原因
    private String rider_code; //配送员编号
    private String rider_name;//配送员姓名
    private String rider_mobile; //配送员手机号
    private  Long time_status_update;//更新时间戳
    private  String sign;  //签名

    public String getOrder_original_id() {
        return order_original_id;
    }

    public void setOrder_original_id(String order_original_id) {
        this.order_original_id = order_original_id;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public String getCancel_reason() {
        return cancel_reason;
    }

    public void setCancel_reason(String cancel_reason) {
        this.cancel_reason = cancel_reason;
    }

    public String getAbnormal_reason() {
        return abnormal_reason;
    }

    public void setAbnormal_reason(String abnormal_reason) {
        this.abnormal_reason = abnormal_reason;
    }

    public String getRider_code() {
        return rider_code;
    }

    public void setRider_code(String rider_code) {
        this.rider_code = rider_code;
    }

    public String getRider_name() {
        return rider_name;
    }

    public void setRider_name(String rider_name) {
        this.rider_name = rider_name;
    }

    public String getRider_mobile() {
        return rider_mobile;
    }

    public void setRider_mobile(String rider_mobile) {
        this.rider_mobile = rider_mobile;
    }

    public Long getTime_status_update() {
        return time_status_update;
    }

    public void setTime_status_update(Long time_status_update) {
        this.time_status_update = time_status_update;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
