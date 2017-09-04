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
@Alias("CdsPrints")
@RsTable(describe = "打印机表", name = "cds_prints")
public class CdsPrints extends BaseMapping {

    @RsTableField(describe = "主键", name = "print_id", primaryKey = true)
    private int print_id;

    @RsTableField(describe = "商铺ID", name = "stores_id")
    private int stores_id;

    @RsTableField(describe = "打印终端号", name = "print_name")
    private String print_name;

    @RsTableField(describe = "终端秘钥", name = "print_address")
    private String print_address;

    @RsTableField(describe = "类型(1.厨房、2.客户）", name = "print_type")
    private int print_type;

    @RsTableField(describe = "备注", name = "print_remark")
    private String print_remark="";

    @RsTableField(describe = "打印商品关键词", name = "print_key")
    private String print_key="";

    @RsTableField(describe = "是否最终打印机", name = "isdefault")
    private int isdefault=0;

    @RsTableField(describe = "状态(1.启用 2. 禁用）", name = "status")
    private int status;

    @RsTableField(describe = "品牌ID", name = "brand_id")
    private int brand_id;


    @RsTableField(describe = "打印机类型(1、云打印机 2、网络打印机)", name = "print_model")
    private int print_model;



    public int getPrint_id() {
        return print_id;
    }

    public void setPrint_id(int print_id) {
        this.print_id = print_id;
    }

    public int getStores_id() {
        return stores_id;
    }

    public void setStores_id(int stores_id) {
        this.stores_id = stores_id;
    }

    public String getPrint_name() {
        return print_name;
    }

    public void setPrint_name(String print_name) {
        this.print_name = print_name;
    }

    public String getPrint_address() {
        return print_address;
    }

    public void setPrint_address(String print_address) {
        this.print_address = print_address;
    }

    public int getPrint_type() {
        return print_type;
    }

    public void setPrint_type(int print_type) {
        this.print_type = print_type;
    }

    public String getPrint_remark() {
        return print_remark;
    }

    public void setPrint_remark(String print_remark) {
        this.print_remark = print_remark;
    }

    public String getPrint_key() {
        return print_key;
    }

    public void setPrint_key(String print_key) {
        this.print_key = print_key;
    }

    public int getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(int isdefault) {
        this.isdefault = isdefault;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPrint_model() {
        return print_model;
    }

    public void setPrint_model(int print_model) {
        this.print_model = print_model;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }
}
