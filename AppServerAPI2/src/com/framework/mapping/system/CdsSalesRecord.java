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
@Alias("CdsSalesRecord")
@RsTable(describe = "销售记录表", name = "cds_sales_record")
public class CdsSalesRecord extends BaseMapping {

    @RsTableField(describe = "主键", name = "id", primaryKey = true)
    private int id;

    @RsTableField(describe = "商铺ID", name = "stores_id")
    private int stores_id;

    @RsTableField(describe = "商品ID", name = "good_id")
    private int good_id;

    @RsTableField(describe = "订单来源(微信、饿了么、美团)", name = "platform")
    private String platform;

    @RsTableField(describe = "销售数量", name = "count")
    private int count;

    @RsTableField(describe = "日期", name = "time")
    private Date time;

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

    public int getGood_id() {
        return good_id;
    }

    public void setGood_id(int good_id) {
        this.good_id = good_id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
