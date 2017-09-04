package com.opensdk.eleme2.api.entity.order;

import com.opensdk.eleme2.api.enumeration.order.OOrderRefundStatus;
import com.opensdk.eleme2.api.enumeration.order.ORefundType;

import java.util.*;

public class ORefundOrder{

    /**
     * 订单id
     */
    private String orderId;
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    /**
     * 退单状态
     */
    private OOrderRefundStatus refundStatus;
    public OOrderRefundStatus getRefundStatus() {
        return refundStatus;
    }
    public void setRefundStatus(OOrderRefundStatus refundStatus) {
        this.refundStatus = refundStatus;
    }
    
    /**
     * 退单类型
     */
    private ORefundType refundType;
    public ORefundType getRefundType() {
        return refundType;
    }
    public void setRefundType(ORefundType refundType) {
        this.refundType = refundType;
    }
    
    /**
     * 退款总额
     */
    private double totalPrice;
    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    /**
     * 订单下商品列表
     */
    private List<Item> goodsList;
    public List<Item> getGoodsList() {
        return goodsList;
    }
    public void setGoodsList(List<Item> goodsList) {
        this.goodsList = goodsList;
    }
    
}