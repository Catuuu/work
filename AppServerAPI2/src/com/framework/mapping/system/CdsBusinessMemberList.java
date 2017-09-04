package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

@Alias("CdsBusinessMemberList")
@RsTable(describe = "对应用户表", name = "cds_business_member_list")
public class CdsBusinessMemberList extends BaseMapping {

    @RsTableField(describe = "主键", name = "bsl_id", primaryKey = true)
    private Integer bsl_id;

    @RsTableField(describe = "用户ID", name = "member_id")
    private Integer member_id;

    @RsTableField(describe = "事业部ID", name = "bu_id")
    private Integer bu_id;

    public Integer getBsl_id() {
        return bsl_id;
    }

    public void setBsl_id(Integer bsl_id) {
        this.bsl_id = bsl_id;
    }

    public Integer getMember_id() {
        return member_id;
    }

    public void setMember_id(Integer member_id) {
        this.member_id = member_id;
    }

    public Integer getBu_id() {
        return bu_id;
    }

    public void setBu_id(Integer bu_id) {
        this.bu_id = bu_id;
    }
}
