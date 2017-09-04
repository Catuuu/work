package com.opensdk.eleme2.api.enumeration.order;

public enum OState {
    /**
     * 待分配（物流系统已生成运单，待分配配送商）
     */
    tobeAssignedMerchant("tobeAssignedMerchant"), 
    
    /**
     * 待分配（配送系统已接单，待分配配送员）
     */
    tobeAssignedCourier("tobeAssignedCourier"), 
    
    /**
     * 待取餐（已分配给配送员，配送员未取餐）
     */
    tobeFetched("tobeFetched"), 
    
    /**
     * 配送中（配送员已取餐，正在配送）
     */
    delivering("delivering"), 
    
    /**
     * 配送成功（配送员配送完成）
     */
    completed("completed"), 
    
    /**
     * 配送取消（商家可以重新发起配送）
     */
    cancelled("cancelled"), 
    
    /**
     * 配送异常
     */
    exception("exception"), 
    
    /**
     * 已到店(配送员已到店)
     */
    arrived("arrived"), 
    
    /**
     * 商家自行配送
     */
    selfDelivery("selfDelivery"), 
    
    /**
     * 商家不再配送
     */
    noMoreDelivery("noMoreDelivery"), 
    
    /**
     * 物流拒单
     */
    reject("reject");
    

    private String orderDesc;
    OState(String orderDesc) {
        this.orderDesc = orderDesc;
    }
}