package com.opensdk.eleme2.api.entity.product;


import java.util.*;
import java.math.BigDecimal;

public class OSupplyLinkSpec{

    /**
     * 支持冷链的属性名
     */
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 支持冷链的属性值
     */
    private List<String> value;
    public List<String> getValue() {
        return value;
    }
    public void setValue(List<String> value) {
        this.value = value;
    }
    
}