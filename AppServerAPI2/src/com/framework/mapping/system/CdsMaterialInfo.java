package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

@Alias("CdsMaterialInfo")
@RsTable(describe = "原料明细", name = "cds_material_info")
public class CdsMaterialInfo extends BaseMapping {
    @RsTableField(describe = "原料ID", name = "material_id", primaryKey = true)
    private Integer material_id;

    @RsTableField(describe = "商品ID", name = "good_id")
    private Integer good_id;

    @RsTableField(describe = "原料名称", name = "material_name")
    private String material_name;

    @RsTableField(describe = "原料数量", name = "material_num")
    private Integer material_num;

    @RsTableField(describe = "原料单位", name = "material_unit")
    private String material_unit;

    @RsTableField(describe = "erp系统物料id", name = "erp_material_id")
    private Integer erp_material_id;

    @RsTableField(describe = "类型", name = "mi_type")
    private Integer mi_type;


    public Integer getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(Integer material_id) {
        this.material_id = material_id;
    }

    public Integer getGood_id() {
        return good_id;
    }

    public void setGood_id(Integer good_id) {
        this.good_id = good_id;
    }

    public String getMaterial_name() {
        return material_name;
    }

    public void setMaterial_name(String material_name) {
        this.material_name = material_name;
    }

    public Integer getMaterial_num() {
        return material_num;
    }

    public void setMaterial_num(Integer material_num) {
        this.material_num = material_num;
    }

    public String getMaterial_unit() {
        return material_unit;
    }

    public void setMaterial_unit(String material_unit) {
        this.material_unit = material_unit;
    }

    public Integer getErp_material_id() {
        return erp_material_id;
    }

    public void setErp_material_id(Integer erp_material_id) {
        this.erp_material_id = erp_material_id;
    }

    public Integer getMi_type() {
        return mi_type;
    }

    public void setMi_type(Integer mi_type) {
        this.mi_type = mi_type;
    }
}
