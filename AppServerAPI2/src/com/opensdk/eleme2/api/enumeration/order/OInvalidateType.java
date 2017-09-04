package com.opensdk.eleme2.api.enumeration.order;

public enum OInvalidateType {
    /**
     * 其他原因
     */
    others("others"), 
    
    /**
     * 用户信息不符
     */
    fakeOrder("fakeOrder"), 
    
    /**
     * 联系不上用户
     */
    contactUserFailed("contactUserFailed"), 
    
    /**
     * 商品已经售完
     */
    foodSoldOut("foodSoldOut"), 
    
    /**
     * 商家已经打烊
     */
    restaurantClosed("restaurantClosed"), 
    
    /**
     * 超出配送范围
     */
    distanceTooFar("distanceTooFar"), 
    
    /**
     * 商家现在太忙
     */
    restaurantTooBusy("restaurantTooBusy"), 
    
    /**
     * 用户申请取消
     */
    forceRejectOrder("forceRejectOrder"), 
    
    /**
     * 配送出现问题
     */
    deliveryFault("deliveryFault"), 
    
    /**
     * 不满足起送要求
     */
    notSatisfiedDeliveryRequirement("notSatisfiedDeliveryRequirement");
    

    private String orderDesc;
    OInvalidateType(String orderDesc) {
        this.orderDesc = orderDesc;
    }
}