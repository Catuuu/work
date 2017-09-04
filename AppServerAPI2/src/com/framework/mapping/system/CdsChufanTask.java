package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

@Alias("CdsChufanTask")
@RsTable(describe = "下达机", name = "cds_chufan_task")
public class CdsChufanTask extends BaseMapping {
  @RsTableField(describe = "主键", name = "es_id", primaryKey = true)
  private Integer es_id;

  @RsTableField(describe = "", name = "stores_id")
  private Integer stores_id;

  @RsTableField(describe = "", name = "ct_name")
  private String ct_name;

  @RsTableField(describe = "", name = "status")
  private Integer status;

  @RsTableField(describe = "", name = "ct_user")
  private String ct_user;

  @RsTableField(describe = "", name = "ct_password")
  private String ct_password;

  @RsTableField(describe = "打印机IP", name = "print_ip")
  private String print_ip;

  public Integer getEs_id() {
    return es_id;
  }

  public void setEs_id(Integer es_id) {
    this.es_id = es_id;
  }

  public Integer getStores_id() {
    return stores_id;
  }

  public void setStores_id(Integer stores_id) {
    this.stores_id = stores_id;
  }

  public String getCt_name() {
    return ct_name;
  }

  public void setCt_name(String ct_name) {
    this.ct_name = ct_name;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getCt_user() {
    return ct_user;
  }

  public void setCt_user(String ct_user) {
    this.ct_user = ct_user;
  }

  public String getCt_password() {
    return ct_password;
  }

  public void setCt_password(String ct_password) {
    this.ct_password = ct_password;
  }

  public String getPrint_ip() {
    return print_ip;
  }

  public void setPrint_ip(String print_ip) {
    this.print_ip = print_ip;
  }
}
