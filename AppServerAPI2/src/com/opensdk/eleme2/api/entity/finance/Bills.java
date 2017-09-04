package com.opensdk.eleme2.api.entity.finance;

import java.util.*;

public class Bills{

    /**
     * 账单数
     */
    private int count;
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    
    /**
     * 账单列表
     */
    private List<Bill> bills;
    public List<Bill> getBills() {
        return bills;
    }
    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }
    
}