package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("CdsGiftRegisterList")
@RsTable(describe = "赠品明细表", name = "cds_gift_register_list")
public class CdsGiftRegisterList extends BaseMapping{
  @RsTableField(describe = "主键", name = "id", primaryKey = true)
  private Integer id;

  @RsTableField(describe = "问题订单id", name = "order_id")
  private String order_id;

  @RsTableField(describe = "品牌id", name = "brand_id")
  private Integer brand_id;

  @RsTableField(describe = "客户手机号", name = "phone")
  private String phone;

  @RsTableField(describe = "是否赠送", name = "send_type")
  private Integer send_type;

  @RsTableField(describe = "客户姓名", name = "name")
  private String name;

  @RsTableField(describe = "赠送原因", name = "send_reason")
  private String send_reason;

  @RsTableField(describe = "商品信息", name = "goods")
  private String goods;

  @RsTableField(describe = "登记时间", name = "create_time")
  private Date create_time;

  @RsTableField(describe = "登记员id", name = "opt_id")
  private Integer opt_id;

  @RsTableField(describe = "登记员姓名", name = "opt_name")
  private String opt_name;

  @RsTableField(describe = "登记方式", name = "opt_type")
  private String opt_type;

  @RsTableField(describe = "赠送订单ID", name = "send_order_id")
  private String send_order_id;


  @RsTableField(describe = "修改时间", name = "update_time")
  private Date update_time;

  @RsTableField(describe = "领取时间", name = "get_time")
  private Date get_time;

  @RsTableField(describe = "赠送原因二级分类", name = "send_reason_sub")
  private String send_reason_sub;

  @RsTableField(describe = "图证", name = "img")
  private String img;

  @RsTableField(describe = "店铺ID", name = "stores_id")
  private Integer stores_id;

  @RsTableField(describe = "是否审核", name = "is_check")
  private Integer is_check;

  @RsTableField(describe = "审核备注", name = "check_info")
  private String check_info;

  @RsTableField(describe = "审核人ID", name = "check_id")
  private Integer check_id;

  @RsTableField(describe = "审核人姓名", name = "check_name")
  private String check_name;

  @RsTableField(describe = "审核时间", name = "check_time")
  private Date check_time;

  public Integer getStores_id() {
    return stores_id;
  }

  public void setStores_id(Integer stores_id) {
    this.stores_id = stores_id;
  }

  public Integer getIs_check() {
    return is_check;
  }

  public void setIs_check(Integer is_check) {
    this.is_check = is_check;
  }

  public String getCheck_info() {
    return check_info;
  }

  public void setCheck_info(String check_info) {
    this.check_info = check_info;
  }

  public Integer getCheck_id() {
    return check_id;
  }

  public void setCheck_id(Integer check_id) {
    this.check_id = check_id;
  }

  public String getCheck_name() {
    return check_name;
  }

  public void setCheck_name(String check_name) {
    this.check_name = check_name;
  }

  public Date getCheck_time() {
    return check_time;
  }

  public void setCheck_time(Date check_time) {
    this.check_time = check_time;
  }

  public String getSend_reason_sub() {
    return send_reason_sub;
  }

  public void setSend_reason_sub(String send_reason_sub) {
    this.send_reason_sub = send_reason_sub;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public String getSend_order_id() {
    return send_order_id;
  }

  public void setSend_order_id(String send_order_id) {
    this.send_order_id = send_order_id;
  }

  public Date getUpdate_time() {
    return update_time;
  }

  public void setUpdate_time(Date update_time) {
    this.update_time = update_time;
  }

  public Date getGet_time() {
    return get_time;
  }

  public void setGet_time(Date get_time) {
    this.get_time = get_time;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getOrder_id() {
    return order_id;
  }

  public void setOrder_id(String order_id) {
    this.order_id = order_id;
  }

  public Integer getBrand_id() {
    return brand_id;
  }

  public void setBrand_id(Integer brand_id) {
    this.brand_id = brand_id;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Integer getSend_type() {
    return send_type;
  }

  public void setSend_type(Integer send_type) {
    this.send_type = send_type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSend_reason() {
    return send_reason;
  }

  public void setSend_reason(String send_reason) {
    this.send_reason = send_reason;
  }

  public String getGoods() {
    return goods;
  }

  public void setGoods(String goods) {
    this.goods = goods;
  }

  public Date getCreate_time() {
    return create_time;
  }

  public void setCreate_time(Date create_time) {
    this.create_time = create_time;
  }

  public Integer getOpt_id() {
    return opt_id;
  }

  public void setOpt_id(Integer opt_id) {
    this.opt_id = opt_id;
  }

  public String getOpt_name() {
    return opt_name;
  }

  public void setOpt_name(String opt_name) {
    this.opt_name = opt_name;
  }

  public String getOpt_type() {
    return opt_type;
  }

  public void setOpt_type(String opt_type) {
    this.opt_type = opt_type;
  }
}
