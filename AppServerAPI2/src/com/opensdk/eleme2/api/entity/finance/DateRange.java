package com.opensdk.eleme2.api.entity.finance;

import java.util.*;

public class DateRange{

    /**
     * 入账开始时间
     */
    private Date start;
    public Date getStart() {
        return start;
    }
    public void setStart(Date start) {
        this.start = start;
    }
    
    /**
     * 入账结束时间
     */
    private Date end;
    public Date getEnd() {
        return end;
    }
    public void setEnd(Date end) {
        this.end = end;
    }
    
}