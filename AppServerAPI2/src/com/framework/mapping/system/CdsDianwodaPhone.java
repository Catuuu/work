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
@Alias("CdsDianwodaPhone")
@RsTable(describe = "点我达过滤手机号", name = "cds_dianwoda_phone")
public class CdsDianwodaPhone extends BaseMapping {

    @RsTableField(describe = "主键", name = "id", primaryKey = true)
    private int id;

    @RsTableField(describe = "商铺ID", name = "stores_id")
    private int stores_id;

    @RsTableField(describe = "骑手姓名", name = "name")
    private String name;

    @RsTableField(describe = "骑手电话", name = "phone")
    private String phone;

    @RsTableField(describe = "操作时间", name = "opt_time")
    private Date opt_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStores_id() {
        return stores_id;
    }

    public void setStores_id(int stores_id) {
        this.stores_id = stores_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getOpt_time() {
        return opt_time;
    }

    public void setOpt_time(Date opt_time) {
        this.opt_time = opt_time;
    }
}
