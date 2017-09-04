package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * Created by Administrator on 2017/2/14.
 */
@Alias("CdsOrderLog")
@RsTable(describe = "订单日志", name = "cds_order_log")
public class CdsOrderLog extends BaseMapping {
    @RsTableField(describe = "主键", name = "ol_id", primaryKey = true)
    private String ol_id;

    @RsTableField(describe = "订单ID", name = "order_id")
    private String order_id;

    @RsTableField(describe = "操作类型", name = "opt_type")
    private String opt_type;  //用户、客服小妹、三方平台、扫码打包、微信支付、商家接单

    @RsTableField(describe = "订单状态", name = "order_status")
    private int order_status;  //(0.商家待接单1.商家已接单2.配送员已接单3.配送中4.已送达,99已取消

    @RsTableField(describe = "订单状态中文", name = "order_status_chi")
    private String order_status_chi;

    @RsTableField(describe = "备注说明", name = "opt_note")
    private String opt_note;

    @RsTableField(describe = "操作人名称", name = "opt_name")
    private String opt_name;

    @RsTableField(describe = "操作时间", name = "opt_time")
    private Date opt_time;

    @RsTableField(describe = "操作人ID", name = "opt_id")
    private int opt_id;

    public String getOl_id() {
        return ol_id;
    }

    public void setOl_id(String ol_id) {
        this.ol_id = ol_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOpt_type() {
        return opt_type;
    }

    public void setOpt_type(String opt_type) {
        this.opt_type = opt_type;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public String getOrder_status_chi() {
        return order_status_chi;
    }

    public void setOrder_status_chi(String order_status_chi) {
        this.order_status_chi = order_status_chi;
    }

    public String getOpt_note() {
        return opt_note;
    }

    public void setOpt_note(String opt_note) {
        this.opt_note = opt_note;
    }

    public String getOpt_name() {
        return opt_name;
    }

    public void setOpt_name(String opt_name) {
        this.opt_name = opt_name;
    }

    public Date getOpt_time() {
        return opt_time;
    }

    public void setOpt_time(Date opt_time) {
        this.opt_time = opt_time;
    }

    public int getOpt_id() {
        return opt_id;
    }

    public void setOpt_id(int opt_id) {
        this.opt_id = opt_id;
    }
}
