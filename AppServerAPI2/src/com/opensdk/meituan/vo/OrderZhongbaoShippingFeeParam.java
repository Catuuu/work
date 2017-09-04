package com.opensdk.meituan.vo;

/**
 * Created by chenbin on 17/02/05.
 */
public class OrderZhongbaoShippingFeeParam {

    private long wm_order_id;
    private double shipping_fee;
    private long wm_order_view_id;
    private String shipping_tips;

    public long getWm_order_id() {
        return wm_order_id;
    }

    public void setWm_order_id(long wm_order_id) {
        this.wm_order_id = wm_order_id;
    }

    public double getShipping_fee() {
        return shipping_fee;
    }

    public void setShipping_fee(double shipping_fee) {
        this.shipping_fee = shipping_fee;
    }

    public long getWm_order_view_id() {
        return wm_order_view_id;
    }

    public void setWm_order_view_id(long wm_order_view_id) {
        this.wm_order_view_id = wm_order_view_id;
    }

    public String getShipping_tips() {
        return shipping_tips;
    }

    public void setShipping_tips(String shipping_tips) {
        this.shipping_tips = shipping_tips;
    }
}
