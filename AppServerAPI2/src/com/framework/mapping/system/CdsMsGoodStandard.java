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
@Alias("CdsMsGoodStandard")
@RsTable(describe = "ERP菜品规格", name = "cds_ms_good_standard")
public class CdsMsGoodStandard extends BaseMapping {

    @RsTableField(describe = "主键", name = "standard_id", primaryKey = true)
    private int standard_id;

    @RsTableField(describe = "商品ID", name = "good_id")
    private int good_id;

    @RsTableField(describe = "规格名称", name = "standard_name")
    private String standard_name;

    @RsTableField(describe = "商品别名关键字", name = "good_key")
    private String good_key;

    @RsTableField(describe = "商品毛重(克)", name = "good_gw")
    private int good_gw;

    @RsTableField(describe = "商品毛量误差", name = "good_gw_error")
    private int good_gw_error;

    @RsTableField(describe = "菜品销售价", name = "good_price")
    private double good_price;

    public int getStandard_id() {
        return standard_id;
    }

    public void setStandard_id(int standard_id) {
        this.standard_id = standard_id;
    }

    public int getGood_id() {
        return good_id;
    }

    public void setGood_id(int good_id) {
        this.good_id = good_id;
    }

    public String getStandard_name() {
        return standard_name;
    }

    public void setStandard_name(String standard_name) {
        this.standard_name = standard_name;
    }

    public String getGood_key() {
        return good_key;
    }

    public void setGood_key(String good_key) {
        this.good_key = good_key;
    }

    public int getGood_gw() {
        return good_gw;
    }

    public void setGood_gw(int good_gw) {
        this.good_gw = good_gw;
    }

    public int getGood_gw_error() {
        return good_gw_error;
    }

    public void setGood_gw_error(int good_gw_error) {
        this.good_gw_error = good_gw_error;
    }

    public double getGood_price() {
        return good_price;
    }

    public void setGood_price(double good_price) {
        this.good_price = good_price;
    }
}
