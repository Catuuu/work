package com.opensdk.eleme2.api.entity.finance;

import java.util.*;

public class FinanceOrders{

    /**
     * 订单数
     */
    private int count;
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    
    /**
     * 订单列表
     */
    private List<FinanceOrder> orders;
    public List<FinanceOrder> getOrders() {
        return orders;
    }
    public void setOrders(List<FinanceOrder> orders) {
        this.orders = orders;
    }
    
}