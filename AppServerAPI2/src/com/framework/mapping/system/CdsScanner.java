package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

/**
 * Created by chenbin on 2017/2/16.
 * 打印机表
 */
@Alias("CdsScanner")
@RsTable(describe = "扫描枪表", name = "cds_scanner")
public class CdsScanner extends BaseMapping {

    @RsTableField(describe = "主键", name = "cs_id", primaryKey = true)
    private int cs_id;

    @RsTableField(describe = "商铺ID", name = "stores_id")
    private int stores_id;

    @RsTableField(describe = "扫描枪名称", name = "sc_name")
    private String sc_name;

    @RsTableField(describe = "扫描枪ID编号", name = "cs_num")
    private String cs_num;

    @RsTableField(describe = "类型(1.打包、2 出餐）", name = "cs_type")
    private int cs_type;

    @RsTableField(describe = "备注", name = "cs_remark")
    private String cs_remark;


    @RsTableField(describe = "状态(1.启用 2. 禁用）", name = "status")
    private int status;

    public int getCs_id() {
        return cs_id;
    }

    public void setCs_id(int cs_id) {
        this.cs_id = cs_id;
    }

    public int getStores_id() {
        return stores_id;
    }

    public void setStores_id(int stores_id) {
        this.stores_id = stores_id;
    }

    public String getSc_name() {
        return sc_name;
    }

    public void setSc_name(String sc_name) {
        this.sc_name = sc_name;
    }

    public String getCs_num() {
        return cs_num;
    }

    public void setCs_num(String cs_num) {
        this.cs_num = cs_num;
    }

    public int getCs_type() {
        return cs_type;
    }

    public void setCs_type(int cs_type) {
        this.cs_type = cs_type;
    }

    public String getCs_remark() {
        return cs_remark;
    }

    public void setCs_remark(String cs_remark) {
        this.cs_remark = cs_remark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
