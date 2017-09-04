package com.opensdk.eleme.vo;

import java.util.List;

/**
 * Created by chenbin on 17/02/05.
 */
public class OrderSubsidyParam {
    String order_id;
    String order_total;
    String subsidy;
    List<OrderSubsidyExtraParam> extras;

    public List<OrderSubsidyExtraParam> getExtras() {
        return extras;
    }

    public void setExtras(
        List<OrderSubsidyExtraParam> extras) {
        this.extras = extras;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_total() {
        return order_total;
    }

    public void setOrder_total(String order_total) {
        this.order_total = order_total;
    }

    public String getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(String subsidy) {
        this.subsidy = subsidy;
    }
}
