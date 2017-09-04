package com.opensdk.eleme.vo;

import java.util.Date;
import java.util.List;

/**
 * Created by chenbin on 17/02/05.
 */
public class OrderParam {


    private OrderDetailParam detail; //订单详细类目列表
    private String address;  //顾客送餐地址
    private Date created_at; //下单时间
    private Date active_at;  //订单生效时间(即支付时间)
    private float deliver_fee; //配送费
    private Date deliver_time; //送餐时间
    private int deliver_status; //配送状态（仅用于第三方配送）
    private String description; //订单备注
    private String invoice;//发票抬头
    private boolean is_book; //是否预订单
    private boolean is_online_paid;//是否在线支付
    private String order_id;//饿了么订单id
    private List<String> phone_list; //顾客联系电话
    private int restaurant_id; //餐厅id,接口调用时使用的ID
    private int inner_id; //饿了么内部餐厅id,提交给业务人员绑定时使用的ID
    private String restaurant_name;//餐厅名称
    private String restaurant_number;//餐厅当日订单序号
    private  int status_code;//订单状态
    private int refund_code; //退单状态
    private int user_id; //用户id
    private String user_name;//用户名
    private float total_price;//订单总价（单位：元）
    private float original_price;//原始价格（优惠前的价格，即菜价加上配送费和打包费，单位：元）
    private String consignee;//订单收货人，例如：张三
    private String delivery_geo;//订单收货地址经纬度，例如：31.2538,121.4185
    private String delivery_poi_address;//顾客送餐详情地址，例如：近铁城市广场（普陀区金沙江路1518弄)
    private int invoiced; //是否需要发票，0-不需要，1-需要
    private float income;//店铺实收
    private float service_rate;//饿了么服务费率
    private float service_fee;//饿了么服务费
    private float hongbao;   //订单中红包金额
    private float package_fee; //餐盒费
    private float activity_total; //订单活动总额
    private float restaurant_part;//店铺承担活动费用
    private float eleme_part; //饿了么承担活动费用

    private int tp_restaurant_id;//商户餐厅 ID

    public OrderDetailParam getDetail() {
        return detail;
    }

    public void setDetail(OrderDetailParam detail) {
        this.detail = detail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getActive_at() {
        return active_at;
    }

    public void setActive_at(Date active_at) {
        this.active_at = active_at;
    }

    public float getDeliver_fee() {
        return deliver_fee;
    }

    public void setDeliver_fee(float deliver_fee) {
        this.deliver_fee = deliver_fee;
    }

    public Date getDeliver_time() {
        return deliver_time;
    }

    public void setDeliver_time(Date deliver_time) {
        this.deliver_time = deliver_time;
    }

    public int getDeliver_status() {
        return deliver_status;
    }

    public void setDeliver_status(int deliver_status) {
        this.deliver_status = deliver_status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public boolean is_book() {
        return is_book;
    }

    public void setIs_book(boolean is_book) {
        this.is_book = is_book;
    }

    public boolean is_online_paid() {
        return is_online_paid;
    }

    public void setIs_online_paid(boolean is_online_paid) {
        this.is_online_paid = is_online_paid;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public List<String> getPhone_list() {
        return phone_list;
    }

    public void setPhone_list(List<String> phone_list) {
        this.phone_list = phone_list;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public int getInner_id() {
        return inner_id;
    }

    public void setInner_id(int inner_id) {
        this.inner_id = inner_id;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getRestaurant_number() {
        return restaurant_number;
    }

    public void setRestaurant_number(String restaurant_number) {
        this.restaurant_number = restaurant_number;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public int getRefund_code() {
        return refund_code;
    }

    public void setRefund_code(int refund_code) {
        this.refund_code = refund_code;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public float getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(float original_price) {
        this.original_price = original_price;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getDelivery_geo() {
        return delivery_geo;
    }

    public void setDelivery_geo(String delivery_geo) {
        this.delivery_geo = delivery_geo;
    }

    public String getDelivery_poi_address() {
        return delivery_poi_address;
    }

    public void setDelivery_poi_address(String delivery_poi_address) {
        this.delivery_poi_address = delivery_poi_address;
    }

    public int getInvoiced() {
        return invoiced;
    }

    public void setInvoiced(int invoiced) {
        this.invoiced = invoiced;
    }

    public float getIncome() {
        return income;
    }

    public void setIncome(float income) {
        this.income = income;
    }

    public float getService_rate() {
        return service_rate;
    }

    public void setService_rate(float service_rate) {
        this.service_rate = service_rate;
    }

    public float getService_fee() {
        return service_fee;
    }

    public void setService_fee(float service_fee) {
        this.service_fee = service_fee;
    }

    public float getHongbao() {
        return hongbao;
    }

    public void setHongbao(float hongbao) {
        this.hongbao = hongbao;
    }

    public float getPackage_fee() {
        return package_fee;
    }

    public void setPackage_fee(float package_fee) {
        this.package_fee = package_fee;
    }

    public float getActivity_total() {
        return activity_total;
    }

    public void setActivity_total(float activity_total) {
        this.activity_total = activity_total;
    }

    public float getRestaurant_part() {
        return restaurant_part;
    }

    public void setRestaurant_part(float restaurant_part) {
        this.restaurant_part = restaurant_part;
    }

    public float getEleme_part() {
        return eleme_part;
    }

    public void setEleme_part(float eleme_part) {
        this.eleme_part = eleme_part;
    }

    public int getTp_restaurant_id() {
        return tp_restaurant_id;
    }

    public void setTp_restaurant_id(int tp_restaurant_id) {
        this.tp_restaurant_id = tp_restaurant_id;
    }

    @Override
    public String toString() {
        return "OrderParam [" +
                "address='" + address + '\'' +
                ", created_at='" + created_at + '\'' +
                ", active_at=" + active_at +
                ", deliver_fee='" + deliver_fee + '\'' +
                ", deliver_time=" + deliver_time +
                ", deliver_status=" + deliver_status +
                ", description=" + description +
                ", invoice=" + invoice +
                ", detail=" + detail +
                ", is_book=" + is_book +
                ", is_online_paid=" + is_online_paid +
                ", order_id=" + order_id +
                ", phone_list=" + phone_list +
                ", restaurant_id='" + restaurant_id + '\'' +
                ", inner_id=" + inner_id +
                ", restaurant_name=" + restaurant_name +
                ", restaurant_number=" + restaurant_number +
                ", status_code=" + status_code +
                ", refund_code=" + refund_code +
                ", user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", total_price=" + total_price +
                ", original_price=" + original_price +
                ", consignee='" + consignee + '\'' +
                ", delivery_geo='" + delivery_geo + '\'' +
                ", delivery_poi_address=" + delivery_poi_address +
                ", invoiced=" + invoiced +
                ", income='" + income + '\'' +
                ", service_rate=" + service_rate +
                ", service_fee=" + service_fee +
                ", hongbao=" + hongbao +
                ", package_fee=" + package_fee +
                ", activity_total=" + activity_total +
                ", restaurant_part=" + restaurant_part +
                ", order_id=" + order_id +
                ", eleme_part=" + eleme_part + "]";
    }
}
