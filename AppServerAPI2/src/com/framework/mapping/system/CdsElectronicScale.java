package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

/**
 * Created by chenbin on 2017/2/16.
 * 打印机表
 */
@Alias("CdsElectronicScale")
@RsTable(describe = "电子秤表", name = "cds_electronic_scale")
public class CdsElectronicScale extends BaseMapping {

    @RsTableField(describe = "主键", name = "es_id", primaryKey = true)
    private int es_id;

    @RsTableField(describe = "商铺ID", name = "stores_id")
    private int stores_id;

    @RsTableField(describe = "电子秤名称", name = "es_name")
    private String es_name;

    @RsTableField(describe = "电子秤IP", name = "es_ip")
    private String es_ip;

    @RsTableField(describe = "电子秤类型", name = "es_type_id")
    private int es_type_id;

    @RsTableField(describe = "备注", name = "es_remark")
    private String es_remark;

    @RsTableField(describe = "特殊信息1(厨师)", name = "special_info1")
    private int special_info1;

    @RsTableField(describe = "特殊信息2(配菜组)", name = "special_info2")
    private int special_info2;

    @RsTableField(describe = "特殊信息3(洗菜组)", name = "special_info3")
    private int special_info3;

    @RsTableField(describe = "状态(1.启用 2. 禁用）", name = "status")
    private int status;

    public int getEs_id() {
        return es_id;
    }

    public void setEs_id(int es_id) {
        this.es_id = es_id;
    }

    public int getStores_id() {
        return stores_id;
    }

    public void setStores_id(int stores_id) {
        this.stores_id = stores_id;
    }

    public String getEs_name() {
        return es_name;
    }

    public void setEs_name(String es_name) {
        this.es_name = es_name;
    }

    public String getEs_ip() {
        return es_ip;
    }

    public void setEs_ip(String es_ip) {
        this.es_ip = es_ip;
    }

    public int getEs_type_id() {
        return es_type_id;
    }

    public void setEs_type_id(int es_type_id) {
        this.es_type_id = es_type_id;
    }

    public String getEs_remark() {
        return es_remark;
    }

    public void setEs_remark(String es_remark) {
        this.es_remark = es_remark;
    }

    public int getSpecial_info1() {
        return special_info1;
    }

    public void setSpecial_info1(int special_info1) {
        this.special_info1 = special_info1;
    }

    public int getSpecial_info2() {
        return special_info2;
    }

    public void setSpecial_info2(int special_info2) {
        this.special_info2 = special_info2;
    }

    public int getSpecial_info3() {
        return special_info3;
    }

    public void setSpecial_info3(int special_info3) {
        this.special_info3 = special_info3;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
