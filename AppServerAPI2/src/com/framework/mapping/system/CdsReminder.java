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
@Alias("CdsReminder")
@RsTable(describe = "催单表", name = "cds_reminder")
public class CdsReminder extends BaseMapping {

    @RsTableField(describe = "主键", name = "cr_id", primaryKey = true)
    private String cr_id;

    @RsTableField(describe = "订单id", name = "order_id")
    private String order_id;

    @RsTableField(describe = "催单状态", name = "do_status")
    private int do_status;

    @RsTableField(describe = "申请备注", name = "cr_remark")
    private String cr_remark;

    @RsTableField(describe = "操作人", name = "cr_operator")
    private int cr_operator;

    @RsTableField(describe = "操作时间", name = "cr_time")
    private Date cr_time;

    @RsTableField(describe = "处理人", name = "cr_user_id")
    private String cr_user_id;

    @RsTableField(describe = "处理时间", name = "cr_check_time")
    private Date cr_check_time;

    @RsTableField(describe = "处理内容", name = "cr_check_remark")
    private String cr_check_remark;

    @RsTableField(describe = "商铺id", name = "stores_id")
    private int stores_id;

    @RsTableField(describe = "处理类型(1系统回复,2人工回复)", name = "type")
    private int type;

    @RsTableField(describe = "催单次数", name = "times")
    private int times;

    @RsTableField(describe = "配送信息", name = "address")
    private String address;

    @RsTableField(describe = "饿了么催单Id", name = "reminder_id")
    private int reminder_id;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getReminder_id() {
        return reminder_id;
    }

    public void setReminder_id(int reminder_id) {
        this.reminder_id = reminder_id;
    }

    public String getCr_id() {
        return cr_id;
    }

    public void setCr_id(String cr_id) {
        this.cr_id = cr_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public int getDo_status() {
        return do_status;
    }

    public void setDo_status(int do_status) {
        this.do_status = do_status;
    }

    public String getCr_remark() {
        return cr_remark;
    }

    public void setCr_remark(String cr_remark) {
        this.cr_remark = cr_remark;
    }

    public int getCr_operator() {
        return cr_operator;
    }

    public void setCr_operator(int cr_operator) {
        this.cr_operator = cr_operator;
    }

    public Date getCr_time() {
        return cr_time;
    }

    public void setCr_time(Date cr_time) {
        this.cr_time = cr_time;
    }

    public String getCr_user_id() {
        return cr_user_id;
    }

    public void setCr_user_id(String cr_user_id) {
        this.cr_user_id = cr_user_id;
    }

    public Date getCr_check_time() {
        return cr_check_time;
    }

    public void setCr_check_time(Date cr_check_time) {
        this.cr_check_time = cr_check_time;
    }

    public String getCr_check_remark() {
        return cr_check_remark;
    }

    public void setCr_check_remark(String cr_check_remark) {
        this.cr_check_remark = cr_check_remark;
    }

    public int getStores_id() {
        return stores_id;
    }

    public void setStores_id(int stores_id) {
        this.stores_id = stores_id;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
