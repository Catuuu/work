package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

@Alias("CdsChufanMealGoods")
@RsTable(describe = "出餐机对应商品明细", name = "cds_chufan_meal_goods")
public class CdsChufanMealGoods extends BaseMapping {
  @RsTableField(describe = "主键", name = "cmg_id", primaryKey = true)
  private Integer cmg_id;

  @RsTableField(describe = "", name = "cm_id")
  private Integer cm_id;

  @RsTableField(describe = "", name = "good_id")
  private Integer good_id;

  public Integer getCmg_id() {
    return cmg_id;
  }

  public void setCmg_id(Integer cmg_id) {
    this.cmg_id = cmg_id;
  }

  public Integer getCm_id() {
    return cm_id;
  }

  public void setCm_id(Integer cm_id) {
    this.cm_id = cm_id;
  }

  public Integer getGood_id() {
    return good_id;
  }

  public void setGood_id(Integer good_id) {
    this.good_id = good_id;
  }
}
