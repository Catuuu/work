package com.opensdk.eleme2.api.entity.product;

import java.util.*;

public class OItemAttribute{

    /**
     * 属性名称
     */
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 属性明细
     */
    private List<String> details;
    public List<String> getDetails() {
        return details;
    }
    public void setDetails(List<String> details) {
        this.details = details;
    }
    
}