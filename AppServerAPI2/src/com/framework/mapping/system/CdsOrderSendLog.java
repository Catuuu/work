package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * Created by chenbin on 2017/2/16.
 * 打印机表
 */
@Alias("CdsOrderSendLog")
@RsTable(describe = "配送操作日志", name = "cds_order_send_log")
public class CdsOrderSendLog extends BaseMapping {

    @RsTableField(describe = "主键", name = "send_id", primaryKey = true)
    private String send_id;

    @RsTableField(describe = "订单编号", name = "order_id")
    private String order_id;

    @RsTableField(describe = "配送类型(生活半径、点我达)", name = "send_type")
    private String send_type;


    @RsTableField(describe = "操作类型(1、创建订单、取消订单)", name = "opt_type")
    private int opt_type;

    @RsTableField(describe = "备注", name = "opt_note")
    private String opt_note;

    @RsTableField(describe = "操作人名称", name = "opt_name")
    private String opt_name;

    @RsTableField(describe = "操作时间", name = "opt_time")
    private Date opt_time;

    @RsTableField(describe = "操作人ID", name = "opt_id")
    private int opt_id;

    @RsTableField(describe = "配送费用", name = "send_price")
    private float send_price;

    public String getSend_id() {
        return send_id;
    }

    public void setSend_id(String send_id) {
        this.send_id = send_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getSend_type() {
        return send_type;
    }

    public void setSend_type(String send_type) {
        this.send_type = send_type;
    }

    public int getOpt_type() {
        return opt_type;
    }

    public void setOpt_type(int opt_type) {
        this.opt_type = opt_type;
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

    public float getSend_price() {
        return send_price;
    }

    public void setSend_price(float send_price) {
        this.send_price = send_price;
    }
}
