package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

@Alias("CdsErpGoodList")
@RsTable(describe = "对应ERP商品明细表", name = "cds_erp_good_list")
public class CdsErpGoodList extends BaseMapping{
    @RsTableField(describe = "主键", name = "erp_good_id", primaryKey = true)
    private Integer erp_good_id;

    @RsTableField(describe = "ERP商品ID", name = "ms_good_id")
    private Integer ms_good_id;

    @RsTableField(describe = "商品ID", name = "good_id")
    private Integer good_id;

    @RsTableField(describe = "商品份数", name = "good_count")
    private Integer good_count;

    public Integer getErp_good_id() {
        return erp_good_id;
    }

    public void setErp_good_id(Integer erp_good_id) {
        this.erp_good_id = erp_good_id;
    }

    public Integer getMs_good_id() {
        return ms_good_id;
    }

    public void setMs_good_id(Integer ms_good_id) {
        this.ms_good_id = ms_good_id;
    }

    public Integer getGood_id() {
        return good_id;
    }

    public void setGood_id(Integer good_id) {
        this.good_id = good_id;
    }

    public Integer getGood_count() {
        return good_count;
    }

    public void setGood_count(Integer good_count) {
        this.good_count = good_count;
    }
}
