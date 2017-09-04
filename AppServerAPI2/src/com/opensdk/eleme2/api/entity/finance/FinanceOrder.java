package com.opensdk.eleme2.api.entity.finance;

import java.util.*;
import java.math.BigDecimal;

public class FinanceOrder{

    /**
     * 饿了么店铺id
     */
    private long shopId;
    public long getShopId() {
        return shopId;
    }
    public void setShopId(long shopId) {
        this.shopId = shopId;
    }
    
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
     * 账单日期
     */
    private Date billDate;
    public Date getBillDate() {
        return billDate;
    }
    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }
    
    /**
     * 入账日期
     */
    private Date checkoutDate;
    public Date getCheckoutDate() {
        return checkoutDate;
    }
    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }
    
    /**
     * 订单流水号
     */
    private String transNo;
    public String getTransNo() {
        return transNo;
    }
    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }
    
    /**
     * 订单类型
     */
    private Byte detailType;
    public Byte getDetailType() {
        return detailType;
    }
    public void setDetailType(Byte detailType) {
        this.detailType = detailType;
    }
    
    /**
     * 订单子类型
     */
    private Byte subDetailType;
    public Byte getSubDetailType() {
        return subDetailType;
    }
    public void setSubDetailType(Byte subDetailType) {
        this.subDetailType = subDetailType;
    }
    
    /**
     * 订单类型
     */
    private Integer orderType;
    public Integer getOrderType() {
        return orderType;
    }
    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }
    
    /**
     * 退单类型
     */
    private Integer refundType;
    public Integer getRefundType() {
        return refundType;
    }
    public void setRefundType(Integer refundType) {
        this.refundType = refundType;
    }
    
    /**
     * 结算规则
     */
    private Integer engineRule;
    public Integer getEngineRule() {
        return engineRule;
    }
    public void setEngineRule(Integer engineRule) {
        this.engineRule = engineRule;
    }
    
    /**
     * 接单序号
     */
    private Long daySn;
    public Long getDaySn() {
        return daySn;
    }
    public void setDaySn(Long daySn) {
        this.daySn = daySn;
    }
    
    /**
     * 订单创建时间
     */
    private Date createAt;
    public Date getCreateAt() {
        return createAt;
    }
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
    
    /**
     * 订单完成时间
     */
    private Date completeAt;
    public Date getCompleteAt() {
        return completeAt;
    }
    public void setCompleteAt(Date completeAt) {
        this.completeAt = completeAt;
    }
    
    /**
     * 结算金额
     */
    private BigDecimal checkoutAmount;
    public BigDecimal getCheckoutAmount() {
        return checkoutAmount;
    }
    public void setCheckoutAmount(BigDecimal checkoutAmount) {
        this.checkoutAmount = checkoutAmount;
    }
    
    /**
     * 货款
     */
    private BigDecimal foodAmount;
    public BigDecimal getFoodAmount() {
        return foodAmount;
    }
    public void setFoodAmount(BigDecimal foodAmount) {
        this.foodAmount = foodAmount;
    }
    
    /**
     * 餐盒费
     */
    private BigDecimal packingFee;
    public BigDecimal getPackingFee() {
        return packingFee;
    }
    public void setPackingFee(BigDecimal packingFee) {
        this.packingFee = packingFee;
    }
    
    /**
     * 赠品补贴
     */
    private BigDecimal elemeGiftSubsidyAmount;
    public BigDecimal getElemeGiftSubsidyAmount() {
        return elemeGiftSubsidyAmount;
    }
    public void setElemeGiftSubsidyAmount(BigDecimal elemeGiftSubsidyAmount) {
        this.elemeGiftSubsidyAmount = elemeGiftSubsidyAmount;
    }
    
    /**
     * 商户承担活动补贴
     */
    private BigDecimal merchantSubsidyAmount;
    public BigDecimal getMerchantSubsidyAmount() {
        return merchantSubsidyAmount;
    }
    public void setMerchantSubsidyAmount(BigDecimal merchantSubsidyAmount) {
        this.merchantSubsidyAmount = merchantSubsidyAmount;
    }
    
    /**
     * 商户承担代金券补贴
     */
    private BigDecimal merchantCashCoupon;
    public BigDecimal getMerchantCashCoupon() {
        return merchantCashCoupon;
    }
    public void setMerchantCashCoupon(BigDecimal merchantCashCoupon) {
        this.merchantCashCoupon = merchantCashCoupon;
    }
    
    /**
     * 用户支付配送费
     */
    private BigDecimal userPaidDeliveryFee;
    public BigDecimal getUserPaidDeliveryFee() {
        return userPaidDeliveryFee;
    }
    public void setUserPaidDeliveryFee(BigDecimal userPaidDeliveryFee) {
        this.userPaidDeliveryFee = userPaidDeliveryFee;
    }
    
    /**
     * 商户收取配送费
     */
    private BigDecimal merchantDeliveryFee;
    public BigDecimal getMerchantDeliveryFee() {
        return merchantDeliveryFee;
    }
    public void setMerchantDeliveryFee(BigDecimal merchantDeliveryFee) {
        this.merchantDeliveryFee = merchantDeliveryFee;
    }
    
    /**
     * 商户配送费补贴
     */
    private BigDecimal merchantDeliveryCost;
    public BigDecimal getMerchantDeliveryCost() {
        return merchantDeliveryCost;
    }
    public void setMerchantDeliveryCost(BigDecimal merchantDeliveryCost) {
        this.merchantDeliveryCost = merchantDeliveryCost;
    }
    
    /**
     * 商户呼单配送费
     */
    private BigDecimal merchantCallDeliveryFee;
    public BigDecimal getMerchantCallDeliveryFee() {
        return merchantCallDeliveryFee;
    }
    public void setMerchantCallDeliveryFee(BigDecimal merchantCallDeliveryFee) {
        this.merchantCallDeliveryFee = merchantCallDeliveryFee;
    }
    
    /**
     * 呼单小费
     */
    private BigDecimal callDeliveryTips;
    public BigDecimal getCallDeliveryTips() {
        return callDeliveryTips;
    }
    public void setCallDeliveryTips(BigDecimal callDeliveryTips) {
        this.callDeliveryTips = callDeliveryTips;
    }
    
    /**
     * 服务费费率
     */
    private BigDecimal commissionRate;
    public BigDecimal getCommissionRate() {
        return commissionRate;
    }
    public void setCommissionRate(BigDecimal commissionRate) {
        this.commissionRate = commissionRate;
    }
    
    /**
     * 满额保底价
     */
    private BigDecimal fullGuaranteeFee;
    public BigDecimal getFullGuaranteeFee() {
        return fullGuaranteeFee;
    }
    public void setFullGuaranteeFee(BigDecimal fullGuaranteeFee) {
        this.fullGuaranteeFee = fullGuaranteeFee;
    }
    
    /**
     * 服务费
     */
    private BigDecimal commissionFee;
    public BigDecimal getCommissionFee() {
        return commissionFee;
    }
    public void setCommissionFee(BigDecimal commissionFee) {
        this.commissionFee = commissionFee;
    }
    
    /**
     * 取消赔偿费率
     */
    private BigDecimal compensationRate;
    public BigDecimal getCompensationRate() {
        return compensationRate;
    }
    public void setCompensationRate(BigDecimal compensationRate) {
        this.compensationRate = compensationRate;
    }
    
    /**
     * 用户申请退单金额
     */
    private BigDecimal refundAmount;
    public BigDecimal getRefundAmount() {
        return refundAmount;
    }
    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }
    
    /**
     * 取消呼单赔付金额
     */
    private BigDecimal cancelledCallDeliveryAmount;
    public BigDecimal getCancelledCallDeliveryAmount() {
        return cancelledCallDeliveryAmount;
    }
    public void setCancelledCallDeliveryAmount(BigDecimal cancelledCallDeliveryAmount) {
        this.cancelledCallDeliveryAmount = cancelledCallDeliveryAmount;
    }
    
    /**
     * 饿了么承担活动补贴
     */
    private BigDecimal elemeSubsidyAmount;
    public BigDecimal getElemeSubsidyAmount() {
        return elemeSubsidyAmount;
    }
    public void setElemeSubsidyAmount(BigDecimal elemeSubsidyAmount) {
        this.elemeSubsidyAmount = elemeSubsidyAmount;
    }
    
    /**
     * 饿了么承担代金券补贴
     */
    private BigDecimal elemeCashCoupon;
    public BigDecimal getElemeCashCoupon() {
        return elemeCashCoupon;
    }
    public void setElemeCashCoupon(BigDecimal elemeCashCoupon) {
        this.elemeCashCoupon = elemeCashCoupon;
    }
    
    /**
     * 实际配送类型
     */
    private Integer actualDeliveryType;
    public Integer getActualDeliveryType() {
        return actualDeliveryType;
    }
    public void setActualDeliveryType(Integer actualDeliveryType) {
        this.actualDeliveryType = actualDeliveryType;
    }
    
    /**
     * 备注
     */
    private String comment;
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    
}