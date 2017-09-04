package com.opensdk.meituan.vo;

/**
 * Created by chenbin on 17/02/05.
 */
public class OrderExtraParam {
    private Integer act_detail_id;
    private Float reduce_fee;
    private String remark;
    private Integer type;
    private String rider_fee;
    private Float mt_charge;
    private Float poi_charge;

    public Integer getAct_detail_id() {
        return act_detail_id;
    }

    public void setAct_detail_id(Integer act_detail_id) {
        this.act_detail_id = act_detail_id;
    }

    public Float getReduce_fee() {
        return reduce_fee;
    }

    public void setReduce_fee(Float reduce_fee) {
        this.reduce_fee = reduce_fee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRider_fee() {
        return rider_fee;
    }

    public void setRider_fee(String rider_fee) {
        this.rider_fee = rider_fee;
    }

    public Float getMt_charge() {
        return mt_charge;
    }

    public void setMt_charge(Float mt_charge) {
        this.mt_charge = mt_charge;
    }

    public Float getPoi_charge() {
        return poi_charge;
    }

    public void setPoi_charge(Float poi_charge) {
        this.poi_charge = poi_charge;
    }

    @Override
    public String toString() {
        return "OrderExtraParam [" +
                "act_detail_id=" + act_detail_id +
                ", reduce_fee=" + reduce_fee +
                ", remark='" + remark + '\'' +
                ", type=" + type +
                ", rider_fee='" + rider_fee + '\'' +
                ", mt_charge=" + mt_charge +
                ", poi_charge=" + poi_charge +
                ']';
    }
}

