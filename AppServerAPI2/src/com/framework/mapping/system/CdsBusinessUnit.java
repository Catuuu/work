package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

@Alias("CdsBusinessUnit")
@RsTable(describe = "事业部", name = "cds_business_unit")
public class CdsBusinessUnit extends BaseMapping {

    @RsTableField(describe = "主键", name = "bu_id", primaryKey = true)
    private Integer bu_id;

    @RsTableField(describe = "事业部名称", name = "bu_name")
    private String bu_name;

    public Integer getBu_id() {
        return bu_id;
    }

    public void setBu_id(Integer bu_id) {
        this.bu_id = bu_id;
    }

    public String getBu_name() {
        return bu_name;
    }

    public void setBu_name(String bu_name) {
        this.bu_name = bu_name;
    }
}
