package com.opensdk.eleme2.api.entity.order;

import java.util.*;

public class OrderList{

    /**
     * 订单列表
     */
    private List<OOrder> list;
    public List<OOrder> getList() {
        return list;
    }
    public void setList(List<OOrder> list) {
        this.list = list;
    }
    
    /**
     * 总条数
     */
    private int total;
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    
}