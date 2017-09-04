package com.framework.mapping.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import com.framework.util.BeanUtil;
import com.framework.util.DateUtil;
import com.framework.util.MapUtil;
import com.framework.util.StringUtil;
import com.opensdk.eleme.vo.OrderExtraParam;
import com.opensdk.eleme.vo.OrderFoodDetailParam;
import com.opensdk.eleme.vo.OrderParam;
import com.opensdk.meituan.vo.OrderDetailParam;
import org.apache.ibatis.type.Alias;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: chenbin
 * Date: 12-12-31
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
@Alias("CdsWeixinOrderInfo")
@RsTable(describe = "微信支付订单表", name = "cds_weixin_order_info")
public class CdsWeixinOrderInfo extends BaseMapping {

    @RsTableField(describe = "主键", name = "order_id", primaryKey = true)
    private String order_id;

    @RsTableField(describe = "用户ID", name = "member_id")
    private int member_id;

    @RsTableField(describe = "订单当前流水号", name = "order_no")
    private String order_no;

    @RsTableField(describe = "就餐方式(1.送货到门2.店堂就餐", name = "order_type")
    private int order_type;

    @RsTableField(describe = "订餐方式（1.个人订餐2协同订餐）", name = "bookdinner")
    private int bookdinner;

    @RsTableField(describe = "订单桌号", name = "desk_no")
    private String desk_no;

    @RsTableField(describe = "订单提交日期", name = "create_date")
    private Date create_date;

    @RsTableField(describe = "商品总价格", name = "goods_prcie")
    private float goods_prcie;

    @RsTableField(describe = "粮票面值（金额）", name = "uc_price")
    private float uc_price;

    @RsTableField(describe = "粮票ID", name = "uc_id")
    private long uc_id;

    @RsTableField(describe = "配送费", name = "ship_fee")
    private float ship_fee;

    @RsTableField(describe = "餐盒费", name = "box_price")
    private float box_price;


    @RsTableField(describe = "折扣金额", name = "deduction_price")
    private float deduction_price;

    @RsTableField(describe = "会员折扣金额", name = "vip_deduction_price")
    private float vip_deduction_price;

    @RsTableField(describe = "订单总费用(用户实付金额)", name = "total_price")
    private float total_price;

    @RsTableField(describe = "付款方式ID", name = "pay_type_id")
    private int pay_type_id;

    @RsTableField(describe = "付款方式", name = "pay_type_name")
    private String pay_type_name;

    @RsTableField(describe = "付款时间", name = "pay_time")
    private Date pay_time;

    @RsTableField(describe = "收货人名称", name = "receiver_name")
    private String receiver_name;

    @RsTableField(describe = "收货人电话", name = "receiver_phone")
    private String receiver_phone;

    @RsTableField(describe = "收货人地址", name = "receiver_adress")
    private String receiver_adress;

    @RsTableField(describe = "收货地址经度", name = "receiver_lng")
    private double receiver_lng;

    @RsTableField(describe = "收货地址纬度", name = "receiver_lat")
    private double receiver_lat;

    @RsTableField(describe = "百度经度", name = "baidu_lng")
    private double baidu_lng;

    @RsTableField(describe = "百度纬度", name = "baidu_lat")
    private double baidu_lat;

    @RsTableField(describe = "收货人性别", name = "receiver_sex")
    private String receiver_sex;

    @RsTableField(describe = "期望送达时间", name = "service_time")
    private Date service_time;

    @RsTableField(describe = "期望送达时间字符", name = "service_time_str")
    private String service_time_str;

    @RsTableField(describe = "订单状态(0.已下单1.已付款 99已取消", name = "order_status")
    private int order_status;

    @RsTableField(describe = "客户备注", name = "member_desc")
    private String member_desc;


    @RsTableField(describe = "商铺ID", name = "stores_id")
    private int stores_id;


    @RsTableField(describe = "微信订单号", name = "transaction_id")
    private String transaction_id;

    @RsTableField(describe = "商品信息JSON", name = "goods")
    private String goods;

    @RsTableField(describe = "品牌ID", name = "brand_id")
    private int brand_id;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getOrder_type() {
        return order_type;
    }

    public void setOrder_type(int order_type) {
        this.order_type = order_type;
    }

    public int getBookdinner() {
        return bookdinner;
    }

    public void setBookdinner(int bookdinner) {
        this.bookdinner = bookdinner;
    }

    public String getDesk_no() {
        return desk_no;
    }

    public void setDesk_no(String desk_no) {
        this.desk_no = desk_no;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public float getGoods_prcie() {
        return goods_prcie;
    }

    public void setGoods_prcie(float goods_prcie) {
        this.goods_prcie = goods_prcie;
    }

    public float getUc_price() {
        return uc_price;
    }

    public void setUc_price(float uc_price) {
        this.uc_price = uc_price;
    }

    public long getUc_id() {
        return uc_id;
    }

    public void setUc_id(long uc_id) {
        this.uc_id = uc_id;
    }

    public float getShip_fee() {
        return ship_fee;
    }

    public void setShip_fee(float ship_fee) {
        this.ship_fee = ship_fee;
    }

    public float getBox_price() {
        return box_price;
    }

    public void setBox_price(float box_price) {
        this.box_price = box_price;
    }

    public float getDeduction_price() {
        return deduction_price;
    }

    public void setDeduction_price(float deduction_price) {
        this.deduction_price = deduction_price;
    }

    public float getVip_deduction_price() {
        return vip_deduction_price;
    }

    public void setVip_deduction_price(float vip_deduction_price) {
        this.vip_deduction_price = vip_deduction_price;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public int getPay_type_id() {
        return pay_type_id;
    }

    public void setPay_type_id(int pay_type_id) {
        this.pay_type_id = pay_type_id;
    }

    public String getPay_type_name() {
        return pay_type_name;
    }

    public void setPay_type_name(String pay_type_name) {
        this.pay_type_name = pay_type_name;
    }

    public Date getPay_time() {
        return pay_time;
    }

    public void setPay_time(Date pay_time) {
        this.pay_time = pay_time;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getReceiver_phone() {
        return receiver_phone;
    }

    public void setReceiver_phone(String receiver_phone) {
        this.receiver_phone = receiver_phone;
    }

    public String getReceiver_adress() {
        return receiver_adress;
    }

    public void setReceiver_adress(String receiver_adress) {
        this.receiver_adress = receiver_adress;
    }

    public double getReceiver_lng() {
        return receiver_lng;
    }

    public void setReceiver_lng(double receiver_lng) {
        this.receiver_lng = receiver_lng;
    }

    public double getReceiver_lat() {
        return receiver_lat;
    }

    public void setReceiver_lat(double receiver_lat) {
        this.receiver_lat = receiver_lat;
    }

    public double getBaidu_lng() {
        return baidu_lng;
    }

    public void setBaidu_lng(double baidu_lng) {
        this.baidu_lng = baidu_lng;
    }

    public double getBaidu_lat() {
        return baidu_lat;
    }

    public void setBaidu_lat(double baidu_lat) {
        this.baidu_lat = baidu_lat;
    }

    public String getReceiver_sex() {
        return receiver_sex;
    }

    public void setReceiver_sex(String receiver_sex) {
        this.receiver_sex = receiver_sex;
    }

    public Date getService_time() {
        return service_time;
    }

    public void setService_time(Date service_time) {
        this.service_time = service_time;
    }

    public String getService_time_str() {
        return service_time_str;
    }

    public void setService_time_str(String service_time_str) {
        this.service_time_str = service_time_str;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public String getMember_desc() {
        return member_desc;
    }

    public void setMember_desc(String member_desc) {
        this.member_desc = member_desc;
    }

    public int getStores_id() {
        return stores_id;
    }

    public void setStores_id(int stores_id) {
        this.stores_id = stores_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }
}
