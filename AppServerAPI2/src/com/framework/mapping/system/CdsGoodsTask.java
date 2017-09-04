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
@Alias("CdsGoodsTask")
@RsTable(describe = "商品调度下达记录", name = "cds_goods_task")
public class CdsGoodsTask extends BaseMapping {

    @RsTableField(describe = "主键", name = "task_id", primaryKey = true)
    private String task_id;

    @RsTableField(describe = "流水号", name = "task_num")
    private int task_num;

    @RsTableField(describe = "出餐机流水号", name = "meal_num")
    private int meal_num;

    @RsTableField(describe = "商铺ID", name = "stores_id")
    private int stores_id;

    @RsTableField(describe = "商品ID", name = "good_id")
    private int good_id;

    @RsTableField(describe = "商品名称", name = "good_name")
    private String good_name;

    @RsTableField(describe = "商品份数", name = "good_count")
    private int good_count;

    @RsTableField(describe = "完成份数", name = "finsh_count")
    private int finsh_count;

    @RsTableField(describe = "下达日期", name = "createtime")
    private Date createtime;

    @RsTableField(describe = "完成时间", name = "task_finsh_time")
    private Date task_finsh_time;

    @RsTableField(describe = "状态(1、出餐中2、已完成3、已取消)", name = "task_status")
    private int task_status;

    @RsTableField(describe = "下达机ID", name = "es_id")
    private int es_id;

    @RsTableField(describe = "出餐机ID", name = "cm_id")
    private int cm_id;

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public int getTask_num() {
        return task_num;
    }

    public void setTask_num(int task_num) {
        this.task_num = task_num;
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

    public int getFinsh_count() {
        return finsh_count;
    }

    public void setFinsh_count(int finsh_count) {
        this.finsh_count = finsh_count;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getTask_finsh_time() {
        return task_finsh_time;
    }

    public void setTask_finsh_time(Date task_finsh_time) {
        this.task_finsh_time = task_finsh_time;
    }

    public int getTask_status() {
        return task_status;
    }

    public void setTask_status(int task_status) {
        this.task_status = task_status;
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

    public int getMeal_num() {
        return meal_num;
    }

    public void setMeal_num(int meal_num) {
        this.meal_num = meal_num;
    }
}
