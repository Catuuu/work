package com.framework.mapping.system;


import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;

@Alias("CdsChangeTypeList")
@RsTable(describe = "收费明细表", name = "cds_change_type_list")
public class CdsChangeTypeList  extends BaseMapping {

    @RsTableField(describe = "店铺id", name = "stores_id")
    private int stores_id;

    @RsTableField(describe = "店铺名称", name = "stores_name")
    private String stores_name;

    @RsTableField(describe = "类型", name = "change_type")
    private String change_type;

    @RsTableField(describe = "金额+", name = "money")
    private BigDecimal money;

    @RsTableField(describe = "时间", name = "time")
    private String time;

    @RsTableField(describe = "订单/出单编号", name = "order_sn")
    private String order_sn;

    @RsTableField(describe = "备注", name = "remarks")
    private String remarks;

    public int getStores_id() {
        return stores_id;
    }

    public void setStores_id(int stores_id) {
        this.stores_id = stores_id;
    }

    public String getStores_name() {
        return stores_name;
    }

    public void setStores_name(String stores_name) {
        this.stores_name = stores_name;
    }

    public String getChange_type() {
        return change_type;
    }

    public void setChange_type(String change_type) {
        this.change_type = change_type;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
