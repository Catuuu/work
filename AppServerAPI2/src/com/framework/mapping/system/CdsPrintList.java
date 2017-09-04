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
@Alias("CdsPrintList")
@RsTable(describe = "打印记录", name = "cds_print_list")
public class CdsPrintList extends BaseMapping {

    @RsTableField(describe = "主键", name = "pl_id", primaryKey = true)
    private String pl_id;

    @RsTableField(describe = "打印机ID", name = "print_id")
    private int print_id;

    @RsTableField(describe = "商铺ID", name = "stores_id")
    private int stores_id;

    @RsTableField(describe = "打印终端号", name = "print_name")
    private String print_name;

    @RsTableField(describe = "终端秘钥", name = "print_address")
    private String print_address;

    @RsTableField(describe = "类型(1.厨房、2.客户）", name = "print_type")
    private int print_type;

    @RsTableField(describe = "打印内容", name = "print_remark")
    private String print_remark;

    @RsTableField(describe = "打印时间", name = "print_time")
    private Date print_time;

    public String getPl_id() {
        return pl_id;
    }

    public void setPl_id(String pl_id) {
        this.pl_id = pl_id;
    }

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

    public Date getPrint_time() {
        return print_time;
    }

    public void setPrint_time(Date print_time) {
        this.print_time = print_time;
    }
}
