package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 *
 * User: chenbin
 * Date: 12-12-31
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
@Alias("CdsGoodsTaskRecord")
@RsTable(describe = "商品调度下达记录", name = "cds_goods_task_record")
public class CdsGoodsTaskRecord extends BaseMapping {

    @RsTableField(describe = "ID编号", name = "tar_id", primaryKey = true)
    private String tar_id;

    @RsTableField(describe = "商铺ID", name = "stores_id")
    private int stores_id;

    @RsTableField(describe = "商品ID", name = "good_id")
    private int good_id;

    @RsTableField(describe = "商品名称", name = "good_name")
    private String good_name;

    @RsTableField(describe = "操作之前份数", name = "before_good_count")
    private int before_good_count;

    @RsTableField(describe = "商品份数", name = "good_count")
    private int good_count;

    @RsTableField(describe = "操作之后份数", name = "after_good_count")
    private int after_good_count;

    @RsTableField(describe = "操作时间", name = "createtime")
    private Date createtime;

    @RsTableField(describe = "状态(1、下达2、下达取消3、出餐 4、出餐取消", name = "gtr_status")
    private int gtr_status;

    @RsTableField(describe = "下达机ID", name = "es_id")
    private int es_id;

    @RsTableField(describe = "出餐机ID", name = "cm_id")
    private int cm_id;

    @RsTableField(describe = "下达记录ID", name = "task_id")
    private String task_id;


    @RsTableField(describe = "是否处理(0、待处理 1、已处理)", name = "is_do")
    private int is_do;

    public String getTar_id() {
        return tar_id;
    }

    public void setTar_id(String tar_id) {
        this.tar_id = tar_id;
    }

    public int getStores_id() {
        return stores_id;
    }

    public void setStores_id(int stores_id) {
        this.stores_id = stores_id;
    }

    public int getGood_id() {
        return good_id;
    }

    public void setGood_id(int good_id) {
        this.good_id = good_id;
    }

    public String getGood_name() {
        return good_name;
    }

    public void setGood_name(String good_name) {
        this.good_name = good_name;
    }

    public int getGood_count() {
        return good_count;
    }

    public void setGood_count(int good_count) {
        this.good_count = good_count;
    }

    public int getBefore_good_count() {
        return before_good_count;
    }

    public void setBefore_good_count(int before_good_count) {
        this.before_good_count = before_good_count;
    }

    public int getAfter_good_count() {
        return after_good_count;
    }

    public void setAfter_good_count(int after_good_count) {
        this.after_good_count = after_good_count;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public int getGtr_status() {
        return gtr_status;
    }

    public void setGtr_status(int gtr_status) {
        this.gtr_status = gtr_status;
    }

    public int getEs_id() {
        return es_id;
    }

    public void setEs_id(int es_id) {
        this.es_id = es_id;
    }

    public int getCm_id() {
        return cm_id;
    }

    public void setCm_id(int cm_id) {
        this.cm_id = cm_id;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public int getIs_do() {
        return is_do;
    }

    public void setIs_do(int is_do) {
        this.is_do = is_do;
    }
}
