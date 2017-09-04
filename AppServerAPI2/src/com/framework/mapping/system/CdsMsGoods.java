package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * User: chenbin
 * Date: 12-12-31
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
@Alias("CdsMsGoods")
@RsTable(describe = "后台单菜品信息配置表", name = "cds_ms_goods")
public class CdsMsGoods extends BaseMapping {

    @RsTableField(describe = "主键", name = "good_id", primaryKey = true)
    private int good_id;

    @RsTableField(describe = "商品种类ID", name = "class_id")
    private int class_id;

    @RsTableField(describe = "商品编码", name = "good_num")
    private String good_num;

    @RsTableField(describe = "商品名称", name = "good_name")
    private String good_name;

    @RsTableField(describe = "商品拼音首字母", name = "pinxin")
    private String pinxin;

    @RsTableField(describe = "商品关键字", name = "good_key")
    private String good_key;

    @RsTableField(describe = "电子秤PLU编号", name = "plu_num")
    private int plu_num;

    @RsTableField(describe = "电子秤模式(称重、计件、定重)", name = "mode")
    private String mode;

    @RsTableField(describe = "所属电子秤类型", name = "est_type_id")
    private int est_type_id;

    @RsTableField(describe = "排序", name = "listorder")
    private int listorder;


    @RsTableField(describe = "状态(1.上架 2. 下架）", name = "status")
    private int status;

    @RsTableField(describe = "更新时间", name = "uptime")
    private Date uptime;

    @RsTableField(describe = "是否备餐(0.否1.是)", name = "isback")
    private int isback;

    @RsTableField(describe = "是否称重", name = "isweight")
    private int isweight;

    @RsTableField(describe = "菜品销售价", name = "good_price")
    private double good_price;

//    @RsTableField(describe = "菜品图片", name = "good_pic")
//    private String good_pic;

    @RsTableField(describe = "是否同步", name = "is_sync")
    private int is_sync;

    @RsTableField(describe = "起草人ID", name = "draftsman")
    private int draftsman;

    @RsTableField(describe = "审批人ID", name = "auditor")
    private int auditor;

    @RsTableField(describe = "批准人ID", name = "approver")
    private int approver;

    @RsTableField(describe = "成品重量(克)", name = "finished_weight")
    private int finished_weight;

    @RsTableField(describe = "操作要领", name = "gist")
    private String gist;

    @RsTableField(describe = "制作流程", name = "process")
    private String process;

    @RsTableField(describe = "出餐时间", name = "out_time")
    private int out_time;

    public int getOut_time() {
        return out_time;
    }

    public void setOut_time(int out_time) {
        this.out_time = out_time;
    }

    public int getDraftsman() {
        return draftsman;
    }

    public void setDraftsman(int draftsman) {
        this.draftsman = draftsman;
    }

    public int getAuditor() {
        return auditor;
    }

    public void setAuditor(int auditor) {
        this.auditor = auditor;
    }

    public int getApprover() {
        return approver;
    }

    public void setApprover(int approver) {
        this.approver = approver;
    }

    public int getFinished_weight() {
        return finished_weight;
    }

    public void setFinished_weight(int finished_weight) {
        this.finished_weight = finished_weight;
    }

    public String getGist() {
        return gist;
    }

    public void setGist(String gist) {
        this.gist = gist;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public int getIs_sync() {
        return is_sync;
    }

    public void setIs_sync(int is_sync) {
        this.is_sync = is_sync;
    }

    private List<CdsMsGoodStandard> cdsMsGoodStandards;

    public List<CdsMsGoodStandard> getCdsMsGoodStandards() {
        if(cdsMsGoodStandards==null){
            cdsMsGoodStandards = new ArrayList<CdsMsGoodStandard>();
        }
        return cdsMsGoodStandards;
    }

    public void setCdsMsGoodStandards(List<CdsMsGoodStandard> cdsMsGoodStandards) {
        this.cdsMsGoodStandards = cdsMsGoodStandards;
    }

    public int getGood_id() {
        return good_id;
    }

    public void setGood_id(int good_id) {
        this.good_id = good_id;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getGood_num() {
        return good_num;
    }

    public void setGood_num(String good_num) {
        this.good_num = good_num;
    }

    public String getGood_name() {
        return good_name;
    }

    public void setGood_name(String good_name) {
        this.good_name = good_name;
    }

    public String getPinxin() {
        return pinxin;
    }

    public void setPinxin(String pinxin) {
        this.pinxin = pinxin;
    }

    public String getGood_key() {
        return good_key;
    }

    public void setGood_key(String good_key) {
        this.good_key = good_key;
    }

    public int getPlu_num() {
        return plu_num;
    }

    public void setPlu_num(int plu_num) {
        this.plu_num = plu_num;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getEst_type_id() {
        return est_type_id;
    }

    public void setEst_type_id(int est_type_id) {
        this.est_type_id = est_type_id;
    }

    public int getListorder() {
        return listorder;
    }

    public void setListorder(int listorder) {
        this.listorder = listorder;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getUptime() {
        return uptime;
    }

    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }

    public int getIsback() {
        return isback;
    }

    public void setIsback(int isback) {
        this.isback = isback;
    }

    public int getIsweight() {
        return isweight;
    }

    public void setIsweight(int isweight) {
        this.isweight = isweight;
    }

//    public String getGood_pic() {
//        return good_pic;
//    }
//
//    public void setGood_pic(String good_pic) {
//        this.good_pic = good_pic;
//    }

    public double getGood_price() {
        return good_price;
    }

    public void setGood_price(double good_price) {
        this.good_price = good_price;
    }
}
