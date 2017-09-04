package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

@Alias("CdsBusinessStoresList")
@RsTable(describe = "对应商铺明细", name = "cds_business_stores_list")
public class CdsBusinessStoresList extends BaseMapping {

    @RsTableField(describe = "主键", name = "bsl_id", primaryKey = true)
    private Integer bsl_id;

    @RsTableField(describe = "事业部ID", name = "bu_id")
    private Integer bu_id;

    @RsTableField(describe = "商铺ID", name = "stores_id")
    private Integer stores_id;

    public Integer getBsl_id() {
        return bsl_id;
    }

    public void setBsl_id(Integer bsl_id) {
        this.bsl_id = bsl_id;
    }

    public Integer getBu_id() {
        return bu_id;
    }

    public void setBu_id(Integer bu_id) {
        this.bu_id = bu_id;
    }

    public Integer getStores_id() {
        return stores_id;
    }

    public void setStores_id(Integer stores_id) {
        this.stores_id = stores_id;
    }
}
