package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * User: chenbin
 * Date: 12-12-31
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
@Alias("CdsBackMoney")
@RsTable(describe = "退款申请", name = "cds_back_money")
public class CdsBackMoney extends BaseMapping {

    @RsTableField(describe = "主键", name = "bp_id", primaryKey = true)
    private String bp_id;

    @RsTableField(describe = "订单编号", name = "order_id")
    private String order_id;

    @RsTableField(describe = "用户Id", name = "member_id")
    private int member_id;

    @RsTableField(describe = "交易流水号", name = "bp_number")
    private String bp_number;

    @RsTableField(describe = "退款状态(1.退款申请2.退款成功3.驳回退款4.退单仲裁中5.退单失败", name = "back_status")
    private int back_status;

    @RsTableField(describe = "微信退款状态(退款中、已退款）", name = "bp_weixin_status")
    private String bp_weixin_status;

    @RsTableField(describe = "退款金额", name = "bp_price")
    private float bp_price;

    @RsTableField(describe = "操作类型(用户、员工、系统)", name = "opt_type")
    private String opt_type;

    @RsTableField(describe = "操作人", name = "bp_operator")
    private String bp_operator;

    @RsTableField(describe = "操作时间", name = "bp_time")
    private Date bp_time;

    @RsTableField(describe = "申请备注", name = "bp_remark")
    private String bp_remark;

    @RsTableField(describe = "审核人", name = "bp_check_operator_id")
    private int bp_check_operator_id;

    @RsTableField(describe = "审核时间", name = "bp_check_time")
    private Date bp_check_time;

    @RsTableField(describe = "审核备注", name = "bp_check_remark")
    private String bp_check_remark;

    @RsTableField(describe = "商铺ID", name = "stores_id")
    private int stores_id;

    @RsTableField(describe = "处理状态", name = "do_status")
    private int do_status;

    @RsTableField(describe = "平台", name = "bp_fromin")
    private String fromin;

    @RsTableField(describe = "订单物流状态", name = "send_type")
    private String send_type;

    public String getSend_type() {
        return send_type;
    }

    public void setSend_type(String send_type) {
        this.send_type = send_type;
    }

    public String getFromin() {
        return fromin;
    }

    public void setFromin(String fromin) {
        this.fromin = fromin;
    }

    public int getDo_status() {
        return do_status;
    }

    public void setDo_status(int do_status) {
        this.do_status = do_status;
    }

    public String getBp_id() {
        return bp_id;
    }

    public void setBp_id(String bp_id) {
        this.bp_id = bp_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getBp_number() {
        return bp_number;
    }

    public void setBp_number(String bp_number) {
        this.bp_number = bp_number;
    }

    public int getBack_status() {
        return back_status;
    }

    public void setBack_status(int back_status) {
        this.back_status = back_status;
    }

    public String getBp_weixin_status() {
        return bp_weixin_status;
    }

    public void setBp_weixin_status(String bp_weixin_status) {
        this.bp_weixin_status = bp_weixin_status;
    }

    public float getBp_price() {
        return bp_price;
    }

    public void setBp_price(float bp_price) {
        this.bp_price = bp_price;
    }

    public String getOpt_type() {
        return opt_type;
    }

    public void setOpt_type(String opt_type) {
        this.opt_type = opt_type;
    }

    public String getBp_operator() {
        return bp_operator;
    }

    public void setBp_operator(String bp_operator) {
        this.bp_operator = bp_operator;
    }

    public Date getBp_time() {
        return bp_time;
    }

    public void setBp_time(Date bp_time) {
        this.bp_time = bp_time;
    }

    public String getBp_remark() {
        return bp_remark;
    }

    public void setBp_remark(String bp_remark) {
        this.bp_remark = bp_remark;
    }

    public int getBp_check_operator_id() {
        return bp_check_operator_id;
    }

    public void setBp_check_operator_id(int bp_check_operator_id) {
        this.bp_check_operator_id = bp_check_operator_id;
    }

    public Date getBp_check_time() {
        return bp_check_time;
    }

    public void setBp_check_time(Date bp_check_time) {
        this.bp_check_time = bp_check_time;
    }

    public String getBp_check_remark() {
        return bp_check_remark;
    }

    public void setBp_check_remark(String bp_check_remark) {
        this.bp_check_remark = bp_check_remark;
    }

    public int getStores_id() {
        return stores_id;
    }

    public void setStores_id(int stores_id) {
        this.stores_id = stores_id;
    }
}
