package com.opensdk.eleme2.api.enumeration.order;

public enum CompensationStatus {
    /**
     * 未发起索赔
     */
    NOT_COMPENSATED("NOT_COMPENSATED"), 
    
    /**
     * 索赔审核中
     */
    UNDER_REVIEW("UNDER_REVIEW"), 
    
    /**
     * 索赔成功
     */
    COMPENSATION_SUCCESS("COMPENSATION_SUCCESS"), 
    
    /**
     * 索赔失败
     */
    COMPENSATION_FAILED("COMPENSATION_FAILED");
    

    private String orderDesc;
    CompensationStatus(String orderDesc) {
        this.orderDesc = orderDesc;
    }
}