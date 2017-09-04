package com.framework.mapping.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
import com.opensdk.eleme2.api.entity.order.OGoodsGroup;
import com.opensdk.eleme2.api.entity.order.OGoodsItem;
import com.opensdk.eleme2.api.entity.order.OOrder;
import com.opensdk.eleme2.api.enumeration.order.OOrderDetailGroupType;
import com.opensdk.meituan.vo.OrderDetailParam;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.ibatis.type.Alias;

import java.text.DecimalFormat;
import java.util.*;

/**
 * User: chenbin
 * Date: 12-12-31
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
@Alias("CdsOrderInfo")
@RsTable(describe = "订单表", name = "cds_order_info")
public class CdsOrderInfo extends BaseMapping {

    @RsTableField(describe = "主键", name = "order_id", primaryKey = true)
    private String order_id;
    @RsTableField(describe = "操作人ID", name = "user_id")
    private int user_id;
    @RsTableField(describe = "用户ID", name = "member_id")
    private int member_id;
    @RsTableField(describe = "订单当前流水号", name = "order_no")
    private String order_no;
    @RsTableField(describe = "订单来源(微信、收银员、饿了么、美团等)", name = "fromin")
    private String fromin;
    @RsTableField(describe = "第三方平台订单号", name = "order_desc")
    private String order_desc;
    @RsTableField(describe = "来源流水号", name = "fromin_no")
    private String fromin_no;
    @RsTableField(describe = "来源商铺ID", name = "fromin_id")
    private String fromin_id;
    @RsTableField(describe = "来源商铺", name = "fromin_name")
    private String fromin_name;
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

    @RsTableField(describe = "平台服务费", name = "serviceFee")
    private float serviceFee;

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

    @RsTableField(describe = "店铺收入金额", name = "income")
    private float income;

    @RsTableField(describe = "店铺承担活动费用", name = "shop_part")
    private float shop_part;

    @RsTableField(describe = "平台承担活动费用", name = "platform_part")
    private float platform_part;

    @RsTableField(describe = "配送支出费用", name = "out_ship_fee")
    private float out_ship_fee;

    @RsTableField(describe = "短信支出", name = "message_price")
    private float message_price;

    @RsTableField(describe = "粮票ID", name = "uc_id")
    private long uc_id;

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

    @RsTableField(describe = "订单状态(0.商家待接单1.商家已接单2.配送员已接单3.配送中4.已送达,99已取消", name = "order_status")
    private int order_status;

    @RsTableField(describe = "申请退款(0.未申请1退款申请中2退款申请成功 3申请驳回", name = "back_status")
    private int back_status;

    @RsTableField(describe = "接单员code", name = "task_order_code")
    private String task_order_code;

    @RsTableField(describe = "接单员姓名", name = "task_order_name")
    private String task_order_name;

    @RsTableField(describe = "接单号电话", name = "task_order_phone")
    private String task_order_phone;

    @RsTableField(describe = "接单时间", name = "task_order_time")
    private Date task_order_time;

    @RsTableField(describe = "打包员ID", name = "pack_user_id")
    private int pack_user_id;

    @RsTableField(describe = "打包人姓名", name = "pack_user_name")
    private String pack_user_name;

    @RsTableField(describe = "打包人电话", name = "pack_user_phone")
    private String pack_user_phone;

    @RsTableField(describe = "打包时间", name = "pack_user_time")
    private Date pack_user_time;

    @RsTableField(describe = "配送员ID", name = "task_user_id")
    private int task_user_id;

    @RsTableField(describe = "配送员code", name = "task_user_code")
    private String task_user_code;

    @RsTableField(describe = "配送员姓名", name = "task_user_name")
    private String task_user_name;

    @RsTableField(describe = "配送员电话", name = "task_user_phone")
    private String task_user_phone;

    @RsTableField(describe = "配送员取货时间", name = "task_user_time")
    private Date task_user_time;

    @RsTableField(describe = "交货时间", name = "task_time")
    private Date task_time;

    @RsTableField(describe = "客户备注", name = "member_desc")
    private String member_desc;

    @RsTableField(describe = "商铺ID", name = "stores_id")
    private int stores_id;

    @RsTableField(describe = "微信订单号", name = "transaction_id")
    private String transaction_id;

    @RsTableField(describe = "是否发送生活半径 (0、不需要处理,1、未处理，2、已处理", name = "is_sync")
    private int is_sync;

    @RsTableField(describe = "配送方式", name = "send_name")
    private String send_name;

    @RsTableField(describe = "配送发送时间", name = "send_time")
    private Date send_time;

    @RsTableField(describe = "小票是否打印(0.未打印,1.已打印,2不打印", name = "isstoresprint")
    private int isstoresprint;

    @RsTableField(describe = "打印时间", name = "print_time")
    private Date print_time;

    @RsTableField(describe = "订单入库时间", name = "in_time")
    private Date in_time;

    @RsTableField(describe = "订单最后修改时间", name = "updatetime")
    private Date updatetime;

    @RsTableField(describe = "定单计划任务是否处理(1.已处理 0 未处理)", name = "battch_isdo")
    private int battch_isdo;

    @RsTableField(describe = "打包异常上次处理时间", name = "pack_exception_time")
    private Date pack_exception_time;

    @RsTableField(describe = "配送异常内容", name = "send_exception")
    private String send_exception;

    @RsTableField(describe = "配送异常上次处理时间", name = "send_exception_time")
    private Date send_exception_time;

    @RsTableField(describe = "配送订单编号", name = "send_id")
    private String send_id;

    @RsTableField(describe = "预订单处理时间", name = "service_exception_time")
    private Date service_exception_time;

    @RsTableField(describe = "商品信息JSON", name = "goods")
    private String goods;

    @RsTableField(describe = "商品明细JSON", name = "goods_info")
    private String goods_info;//商品明细信息


    @RsTableField(describe = "取消订单类型", name = "cancel_type")
    private String cancel_type;

    @RsTableField(describe = "取消订单备注", name = "cancel_remark")
    private String cancel_remark;

    @RsTableField(describe = "步行距离(米)", name = "distance")
    private int distance;

    @RsTableField(describe = "步行时间(秒)", name = "duration")
    private int duration;


    @RsTableField(describe = "计算公里数", name = "kilometre")
    private int kilometre;


    @RsTableField(describe = "配送费用", name = "send_price")
    private float send_price;

    @RsTableField(describe = "品牌ID", name = "brand_id")
    private int brand_id;

    //门店品牌ID
    private int stores_brand_id;

    private int count = 1;     //消息推送记数
    private int print_type = 1;//1、系统打印 2、用户手动打印
    private String url;
    private String code;
    private int print_format = 1;//标头打印格式
    private int good_print_format = 2;//商品打印格式 1、统计汇总 2、原商品名称

    private String ms_goods_info;//商品明细信息保存信息

    public String getMs_goods_info() {
        return ms_goods_info;
    }

    public void setMs_goods_info(String ms_goods_info) {
        this.ms_goods_info = ms_goods_info;
    }

    public int getStores_brand_id() {
        return stores_brand_id;
    }

    public void setStores_brand_id(int stores_brand_id) {
        this.stores_brand_id = stores_brand_id;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getPrint_type() {
        return print_type;
    }

    public void setPrint_type(int print_type) {
        this.print_type = print_type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //消息处理记数
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCancel_type() {
        return cancel_type;
    }

    public void setCancel_type(String cancel_type) {
        this.cancel_type = cancel_type;
    }

    public String getCancel_remark() {
        return cancel_remark;
    }

    public void setCancel_remark(String cancel_remark) {
        this.cancel_remark = cancel_remark;
    }

    public String getSend_id() {
        return send_id;
    }

    public void setSend_id(String send_id) {
        this.send_id = send_id;
    }

    public String getSend_exception() {
        return send_exception;
    }

    public void setSend_exception(String send_exception) {
        this.send_exception = send_exception;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getFromin() {
        return fromin;
    }

    public void setFromin(String fromin) {
        this.fromin = fromin;
    }

    public String getOrder_desc() {
        return order_desc;
    }

    public void setOrder_desc(String order_desc) {
        this.order_desc = order_desc;
    }

    public String getFromin_no() {
        return fromin_no;
    }

    public void setFromin_no(String fromin_no) {
        this.fromin_no = fromin_no;
    }

    public String getFromin_id() {
        return fromin_id;
    }

    public void setFromin_id(String fromin_id) {
        this.fromin_id = fromin_id;
    }

    public String getFromin_name() {
        return fromin_name;
    }

    public void setFromin_name(String fromin_name) {
        this.fromin_name = fromin_name;
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

    public float getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(float serviceFee) {
        this.serviceFee = serviceFee;
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

    public float getIncome() {
        return income;
    }

    public void setIncome(float income) {
        this.income = income;
    }

    public float getShop_part() {
        return shop_part;
    }

    public void setShop_part(float shop_part) {
        this.shop_part = shop_part;
    }

    public float getPlatform_part() {
        return platform_part;
    }

    public void setPlatform_part(float platform_part) {
        this.platform_part = platform_part;
    }

    public float getOut_ship_fee() {
        return out_ship_fee;
    }

    public void setOut_ship_fee(float out_ship_fee) {
        this.out_ship_fee = out_ship_fee;
    }

    public float getMessage_price() {
        return message_price;
    }

    public void setMessage_price(float message_price) {
        this.message_price = message_price;
    }

    public long getUc_id() {
        return uc_id;
    }

    public void setUc_id(long uc_id) {
        this.uc_id = uc_id;
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

    public int getBack_status() {
        return back_status;
    }

    public void setBack_status(int back_status) {
        this.back_status = back_status;
    }

    public String getTask_order_name() {
        return task_order_name;
    }

    public void setTask_order_name(String task_order_name) {
        this.task_order_name = task_order_name;
    }

    public String getTask_order_phone() {
        return task_order_phone;
    }

    public void setTask_order_phone(String task_order_phone) {
        this.task_order_phone = task_order_phone;
    }

    public Date getTask_order_time() {
        return task_order_time;
    }

    public void setTask_order_time(Date task_order_time) {
        this.task_order_time = task_order_time;
    }

    public int getPack_user_id() {
        return pack_user_id;
    }

    public void setPack_user_id(int pack_user_id) {
        this.pack_user_id = pack_user_id;
    }

    public String getPack_user_name() {
        return pack_user_name;
    }

    public void setPack_user_name(String pack_user_name) {
        this.pack_user_name = pack_user_name;
    }

    public String getPack_user_phone() {
        return pack_user_phone;
    }

    public void setPack_user_phone(String pack_user_phone) {
        this.pack_user_phone = pack_user_phone;
    }

    public Date getPack_user_time() {
        return pack_user_time;
    }

    public void setPack_user_time(Date pack_user_time) {
        this.pack_user_time = pack_user_time;
    }

    public int getTask_user_id() {
        return task_user_id;
    }

    public void setTask_user_id(int task_user_id) {
        this.task_user_id = task_user_id;
    }

    public String getTask_user_name() {
        return task_user_name;
    }

    public void setTask_user_name(String task_user_name) {
        this.task_user_name = task_user_name;
    }

    public String getTask_user_phone() {
        return task_user_phone;
    }

    public void setTask_user_phone(String task_user_phone) {
        this.task_user_phone = task_user_phone;
    }

    public Date getTask_user_time() {
        return task_user_time;
    }

    public void setTask_user_time(Date task_user_time) {
        this.task_user_time = task_user_time;
    }

    public Date getTask_time() {
        return task_time;
    }

    public void setTask_time(Date task_time) {
        this.task_time = task_time;
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

    public int getIs_sync() {
        return is_sync;
    }

    public void setIs_sync(int is_sync) {
        this.is_sync = is_sync;
    }

    public String getSend_name() {
        return send_name;
    }

    public void setSend_name(String send_name) {
        this.send_name = send_name;
    }

    public Date getSend_time() {
        return send_time;
    }

    public void setSend_time(Date send_time) {
        this.send_time = send_time;
    }

    public int getIsstoresprint() {
        return isstoresprint;
    }

    public void setIsstoresprint(int isstoresprint) {
        this.isstoresprint = isstoresprint;
    }

    public Date getPrint_time() {
        return print_time;
    }

    public void setPrint_time(Date print_time) {
        this.print_time = print_time;
    }

    public Date getIn_time() {
        return in_time;
    }

    public void setIn_time(Date in_time) {
        this.in_time = in_time;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public int getBattch_isdo() {
        return battch_isdo;
    }

    public void setBattch_isdo(int battch_isdo) {
        this.battch_isdo = battch_isdo;
    }

    public Date getPack_exception_time() {
        return pack_exception_time;
    }

    public void setPack_exception_time(Date pack_exception_time) {
        this.pack_exception_time = pack_exception_time;
    }

    public Date getSend_exception_time() {
        return send_exception_time;
    }

    public void setSend_exception_time(Date send_exception_time) {
        this.send_exception_time = send_exception_time;
    }

    public Date getService_exception_time() {
        return service_exception_time;
    }

    public void setService_exception_time(Date service_exception_time) {
        this.service_exception_time = service_exception_time;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getKilometre() {
        return kilometre;
    }

    public void setKilometre(int kilometre) {
        this.kilometre = kilometre;
    }

    public float getSend_price() {
        return send_price;
    }

    public void setSend_price(float send_price) {
        this.send_price = send_price;
    }

    public String getTask_order_code() {
        return task_order_code;
    }

    public void setTask_order_code(String task_order_code) {
        this.task_order_code = task_order_code;
    }

    public String getTask_user_code() {
        return task_user_code;
    }

    public void setTask_user_code(String task_user_code) {
        this.task_user_code = task_user_code;
    }

    public int getPrint_format() {
        return print_format;
    }

    public void setPrint_format(int print_format) {
        this.print_format = print_format;
    }

    public String getGoods_info() {
        return goods_info;
    }

    public void setGoods_info(String goods_info) {
        this.goods_info = goods_info;
    }

    public int getGood_print_format() {
        return good_print_format;
    }

    public void setGood_print_format(int good_print_format) {
        this.good_print_format = good_print_format;
    }

    /**
     * 饿了么设置订单信息2.0
     *
     * @param member
     * @param orderParam
     * @param cdsStores
     */
    public void setElemeParam(CdsMember member, OOrder orderParam, CdsStores cdsStores) {
        Date day = new Date();
        this.order_id = StringUtil.getPrimaryOrderKey();
        this.member_id = member.getMember_id();
        this.fromin = "饿了么";
        this.order_desc = orderParam.getId();
        this.fromin_no = Integer.toString(orderParam.getDaySn());
        this.fromin_id = String.valueOf(orderParam.getShopId());
        this.fromin_name = orderParam.getShopName();
        this.order_type = 1;
        this.bookdinner = 1;
        this.create_date = orderParam.getCreatedAt();

        List<CdsGood> goods = new ArrayList<CdsGood>();

        ////金额计算
        float good_price = 0; //(商品价格) 菜品价格之和  取group数组中category_id值为1的金额总和
        for (OGoodsGroup goodsGroup : orderParam.getGroups()) {
            if(goodsGroup.getType()== OOrderDetailGroupType.normal){
                for (OGoodsItem oGoodsItem:goodsGroup.getItems()){
                    //good_price += oGoodsItem.getPrice() * oGoodsItem.getQuantity();
                    CdsGood cdsGood = new CdsGood();
                    cdsGood.setClass_id(oGoodsItem.getCategoryId());
                    cdsGood.setGood_name(oGoodsItem.getName());

                    cdsGood.setPrice((float) oGoodsItem.getPrice());
                    cdsGood.setQuantity(oGoodsItem.getQuantity());
                    cdsGood.setId(oGoodsItem.getId());
                    if (oGoodsItem.getSkuId() != null) {
                        cdsGood.setGood_id(oGoodsItem.getSkuId());
                    }
                    cdsGood.setGood_type(1);
                    goods.add(cdsGood);
                }
            }else if(goodsGroup.getType()== OOrderDetailGroupType.discount){
                for (OGoodsItem oGoodsItem:goodsGroup.getItems()) {
                    CdsGood cdsGood = new CdsGood();
                    cdsGood.setClass_id(oGoodsItem.getCategoryId());
                    cdsGood.setGood_name(oGoodsItem.getName());

                    cdsGood.setPrice((float) oGoodsItem.getPrice());
                    cdsGood.setQuantity(oGoodsItem.getQuantity());
                    cdsGood.setId(oGoodsItem.getId());
                    if (oGoodsItem.getSkuId() != null) {
                        cdsGood.setGood_id(oGoodsItem.getSkuId());
                    }
                    cdsGood.setGood_type(2);
                    goods.add(cdsGood);
                }
            }else if(goodsGroup.getType()== OOrderDetailGroupType.extra){

            }
        }

        float ship_fee = (float)(orderParam.getDeliverFee()-orderParam.getVipDeliveryFeeDiscount()); //配送费      取(extra数组中category_id值为2的金额  - extra数组中category_id值为300的金额)


        this.box_price = (float) orderParam.getPackageFee(); //餐盒费
        this.ship_fee = ship_fee;                     //配送费 originalPrice
        this.goods_prcie = (float)(orderParam.getOriginalPrice()-ship_fee-box_price); //(商品价格)

        this.uc_price = (float)orderParam.getHongbao();//(红包金额)
        this.serviceFee = (float)orderParam.getServiceFee();//平台服务费


        this.deduction_price = (float)orderParam.getActivityTotal();//折扣金额
        this.vip_deduction_price = (float)orderParam.getVipDeliveryFeeDiscount();    //会员优惠金额,减配送费
        this.total_price = (float)orderParam.getTotalPrice();    //(订单金额)
        this.income = (float) orderParam.getIncome();              //店铺实际收入金额
        this.shop_part = (float)orderParam.getShopPart();  //(店铺承担活动费用)
        this.platform_part =(float) orderParam.getElemePart();   //(平台承担活动费用)
        this.out_ship_fee = 0;                             //配送支出费用
        this.message_price = 0;                            //短信支出费用
        this.pay_type_id = 6;
        this.pay_type_name = "三方平台";
        this.pay_time = orderParam.getActiveAt();

        this.receiver_name = orderParam.getConsignee();

        this.receiver_phone = "";
        for (String phone : orderParam.getPhoneList()) {
            this.receiver_phone += "," + phone;
        }
        this.receiver_phone = this.receiver_phone.substring(1);
        this.receiver_adress = orderParam.getAddress();

        if (!BeanUtil.isNullAndEmpty(orderParam.getDeliveryGeo())) {
            String[] geos = orderParam.getDeliveryGeo().split(",");
            this.receiver_lat = Double.parseDouble(geos[0]);
            this.receiver_lng = Double.parseDouble(geos[1]);

            MapBD mapBD = MapUtil.bd_encrypt(this.receiver_lat, this.receiver_lng);
            this.baidu_lng = mapBD.getBd_lon();
            this.baidu_lat = mapBD.getBd_lat();
        }

        if (orderParam.getBook()) {
            this.service_time = orderParam.getDeliverTime();
            this.service_time_str = DateUtil.dateToStr(orderParam.getDeliverTime(), "HH:mm");
        } else {
            this.service_time_str = "及时送达";
        }
        this.order_status = 0;
        this.back_status = 0;

        if (!orderParam.getInvoiced()) {//是否需要发票
            this.member_desc = orderParam.getDescription();
        } else {
            this.member_desc = "发票抬头:" + orderParam.getInvoice() + "  " + orderParam.getDescription();
        }
        this.stores_id = cdsStores.getStores_id();
        this.brand_id = cdsStores.getBrand_id();
        this.stores_brand_id = cdsStores.getStores_brand_id();

        JSONObject jodetail = JSONObject.parseObject(cdsStores.getDispatch_extinfo());
        int send_type = Integer.parseInt(jodetail.getString("send_type1"));//0饿了么配送 1人工分配 2自动给“生活半径”配送 3自动给“菜大师”配送
        if (send_type == 0) {
            this.send_name = "饿了么";  //配送方式
            this.is_sync = 0;   //是否发送配送 0、不需要,1、未发送，2、已发送
        } else if (send_type == 1) {
            this.send_name = "人工分配";  //配送方式
            this.is_sync = 0;   //是否发送配送 0、不需要,1、未发送，2、已发送
        } else if (send_type == 2) {
            this.send_name = "生活半径";  //配送方式
            this.is_sync = 1;   //是否发送配送 0、不需要,1、未发送，2、已发送
        } else if (send_type == 3) {
            this.send_name = "点我达";  //配送方式
            this.is_sync = 1;   //是否发送配送 0、不需要,1、未发送，2、已发送
        } else if (send_type == 4) {
            this.send_name = "运速达";  //配送方式
            this.is_sync = 1;   //是否发送配送 0、不需要,1、未发送，2、已发送
        }

        this.isstoresprint = 0; //店堂小票是否打印(0.未打印,1.已打印,2不打印

        this.in_time = day;    //入库时间
        this.updatetime = day; //最后修改时间

        this.goods = BeanUtil.toJsonString(goods);
    }

    /**
     * 饿了么设置订单信息
     *
     * @param member
     * @param orderParam
     * @param cdsStores
     */
    public void setElemeParam(CdsMember member, OrderParam orderParam, CdsStores cdsStores) {
        Date day = new Date();
        this.order_id = StringUtil.getPrimaryOrderKey();
        this.member_id = member.getMember_id();
        this.fromin = "饿了么";
        this.order_desc = orderParam.getOrder_id();
        this.fromin_no = orderParam.getRestaurant_number();
        this.fromin_id = String.valueOf(orderParam.getRestaurant_id());
        this.fromin_name = orderParam.getRestaurant_name();
        this.order_type = 1;
        this.bookdinner = 1;
        this.create_date = orderParam.getCreated_at();

        List<CdsGood> goods = new ArrayList<CdsGood>();

        ////金额计算
        float good_price = 0; //(商品价格) 菜品价格之和  取group数组中category_id值为1的金额总和
        for (OrderFoodDetailParam good : orderParam.getDetail().getGroup()) {
            if (good.getCategory_id() == 1) {
                good_price += good.getPrice() * good.getQuantity();

                CdsGood cdsGood = new CdsGood();
                cdsGood.setClass_id(good.getCategory_id());
                cdsGood.setGood_name(good.getName());

                cdsGood.setPrice((float) good.getPrice());
                cdsGood.setQuantity(good.getQuantity());
                cdsGood.setId(good.getId());
                if (good.getTp_food_id() != null) {
                    cdsGood.setGood_id(Integer.parseInt(good.getTp_food_id()));
                }
                cdsGood.setGood_type(1);
                goods.add(cdsGood);

            }
        }
        float uc_price = 0; //(红包金额)  取extra数组中category_id值为13的金额
        float ship_fee = 0; //配送费      取(extra数组中category_id值为2的金额  - extra数组中category_id值为300的金额)
        for (OrderExtraParam extra : orderParam.getDetail().getExtra()) {
            if (extra.getCategory_id() == 13) {
                uc_price += extra.getPrice();
            }
            if (extra.getCategory_id() == 2 || extra.getCategory_id() == 300) {
                ship_fee += extra.getPrice();
            }

            if (extra.getCategory_id() == 12 && extra.getType() == 106) {
                try {
                    CdsGood cdsGood = new CdsGood();
                    cdsGood.setClass_id(extra.getCategory_id());
                    cdsGood.setGood_name(extra.getName());

                    cdsGood.setPrice((float)extra.getPrice());
                    cdsGood.setQuantity(extra.getQuantity());
                    cdsGood.setId((int) extra.getId());

                    cdsGood.setGood_type(2);
                    goods.add(cdsGood);
                } catch (Exception e) {
                }
            }
        }


        this.goods_prcie = good_price;                //(商品价格)
        this.uc_price = uc_price;                     //(红包金额)
        this.serviceFee = orderParam.getService_fee();//平台服务费
        this.ship_fee = ship_fee;                     //配送费
        this.box_price = orderParam.getPackage_fee(); //餐盒费
        this.deduction_price = orderParam.getActivity_total();//折扣金额
        this.vip_deduction_price = 0;                         //会员优惠金额
        this.total_price = orderParam.getTotal_price();    //(订单金额)
        this.income = orderParam.getIncome();              //店铺实际收入金额
        this.shop_part = orderParam.getRestaurant_part();  //(店铺承担活动费用)
        this.platform_part = orderParam.getEleme_part();   //(平台承担活动费用)
        this.out_ship_fee = 0;                             //配送支出费用
        this.message_price = 0;                            //短信支出费用
        this.pay_type_id = 6;
        this.pay_type_name = "三方平台";
        this.pay_time = orderParam.getActive_at();

        this.receiver_name = orderParam.getConsignee();

        this.receiver_phone = "";
        for (String phone : orderParam.getPhone_list()) {
            this.receiver_phone += "," + phone;
        }
        this.receiver_phone = this.receiver_phone.substring(1);
        this.receiver_adress = orderParam.getAddress();

        if (!BeanUtil.isNullAndEmpty(orderParam.getDelivery_geo())) {
            String[] geos = orderParam.getDelivery_geo().split(",");
            this.receiver_lat = Double.parseDouble(geos[0]);
            this.receiver_lng = Double.parseDouble(geos[1]);

            MapBD mapBD = MapUtil.bd_encrypt(this.receiver_lat, this.receiver_lng);
            this.baidu_lng = mapBD.getBd_lon();
            this.baidu_lat = mapBD.getBd_lat();
        }

        if (orderParam.is_book()) {
            this.service_time = orderParam.getDeliver_time();
            this.service_time_str = DateUtil.dateToStr(orderParam.getDeliver_time(), "HH:mm");
        } else {
            this.service_time_str = "及时送达";
        }
        this.order_status = 0;
        this.back_status = 0;

        if (orderParam.getInvoiced() == 0) {//是否需要发票，0-不需要，1-需要
            this.member_desc = orderParam.getDescription();
        } else {
            this.member_desc = "发票抬头:" + orderParam.getInvoice() + "  " + orderParam.getDescription();
        }
        this.stores_id = cdsStores.getStores_id();
        this.brand_id = cdsStores.getBrand_id();
        this.stores_brand_id = cdsStores.getStores_brand_id();

        JSONObject jodetail = JSONObject.parseObject(cdsStores.getDispatch_extinfo());
        int send_type = Integer.parseInt(jodetail.getString("send_type1"));//0饿了么配送 1人工分配 2自动给“生活半径”配送 3自动给“菜大师”配送
        if (send_type == 0) {
            this.send_name = "饿了么";  //配送方式
            this.is_sync = 0;   //是否发送配送 0、不需要,1、未发送，2、已发送
        } else if (send_type == 1) {
            this.send_name = "人工分配";  //配送方式
            this.is_sync = 0;   //是否发送配送 0、不需要,1、未发送，2、已发送
        } else if (send_type == 2) {
            this.send_name = "生活半径";  //配送方式
            this.is_sync = 1;   //是否发送配送 0、不需要,1、未发送，2、已发送
        } else if (send_type == 3) {
            this.send_name = "点我达";  //配送方式
            this.is_sync = 1;   //是否发送配送 0、不需要,1、未发送，2、已发送
        } else if (send_type == 4) {
            this.send_name = "运速达";  //配送方式
            this.is_sync = 1;   //是否发送配送 0、不需要,1、未发送，2、已发送
        }

        this.isstoresprint = 0; //店堂小票是否打印(0.未打印,1.已打印,2不打印

        this.in_time = day;    //入库时间
        this.updatetime = day; //最后修改时间

        this.goods = BeanUtil.toJsonString(goods);
    }


    /**
     * 美团设置订单信息
     *
     * @param member
     * @param orderParam
     * @param cdsStores
     */
    public void setMeitianParam(CdsMember member, OrderDetailParam orderParam, CdsStores cdsStores) {
        Date day = new Date();
        this.order_id = StringUtil.getPrimaryOrderKey();
        this.member_id = member.getMember_id();
        this.fromin = "美团";
        this.order_desc = String.valueOf(orderParam.getOrder_id());
        this.fromin_no = String.valueOf(orderParam.getDay_seq());
        this.fromin_id = String.valueOf(orderParam.getWm_poi_id());
        this.fromin_name = orderParam.getWm_poi_name();
        this.order_type = 1;
        this.bookdinner = 1;

        Date d = new Date(orderParam.getCtime() * 1000);

        this.create_date = d;

        List<CdsGood> goods = new ArrayList<CdsGood>();


        ////金额计算
        float good_price = 0; //(商品价格) 菜品价格之和  取detail数组中quantity剩 price 的金额总和
        float box_price = 0;  //(餐盒费) 餐盒费之和  取detail数组中box_num剩 box_price的金额总和
        for (com.opensdk.meituan.vo.OrderFoodDetailParam good : orderParam.getDetail()) {

            good_price += good.getPrice() * good.getQuantity();
            box_price += good.getBox_num() * good.getBox_price();

            CdsGood cdsGood = new CdsGood();
            if (org.springframework.util.StringUtils.isEmpty(good.getSpec())) {
                cdsGood.setGood_name(good.getFood_name());
            } else {
                cdsGood.setGood_name(good.getFood_name() + "-" + good.getSpec());
            }
            cdsGood.setPrice(good.getPrice());
            cdsGood.setQuantity(good.getQuantity());
            //cdsGood.setId(good.getId());
            //cdsGood.setClass_id(good.getCategory_id());
            if (good.getSku_id() != null && !good.getSku_id().equals("")) {
                cdsGood.setGood_id(Integer.parseInt(good.getSku_id()));
            }
            cdsGood.setSpec(good.getSpec());
            cdsGood.setGood_type(1);
            goods.add(cdsGood);
        }
        float uc_price = 0;      //(红包金额)   extras 数组："reduce_fee":3, type为 9-使用红包
        float shop_part = 0;      //店铺承担费用 extras 数组："poi_charge":1.5, type为除9之外之和
        float platform_part = 0; //平台承担活动费用  extras 数组: "mt_charge":1.5,  type为除9之外之和
        float deduction_price = 0;
        for (com.opensdk.meituan.vo.OrderExtraParam extra : orderParam.getExtras()) {
            if (extra.getType() == 9) {//9-使用红包
                uc_price += extra.getReduce_fee();
            } else if (extra.getType() == 5) {//5-满赠
                CdsGood cdsGood = new CdsGood();

                cdsGood.setGood_name(extra.getRemark());
                cdsGood.setPrice(extra.getPoi_charge());
                cdsGood.setQuantity(1);
                //cdsGood.setId(good.getId());
                //cdsGood.setClass_id(good.getCategory_id());

                cdsGood.setSpec("");
                cdsGood.setGood_type(5);
                goods.add(cdsGood);
            } else {
                shop_part += extra.getPoi_charge();  //店铺承担费用
                platform_part += extra.getMt_charge();//平台承担活动费用
                deduction_price += extra.getReduce_fee(); //extras 数组："reduce_fee":3, type为除9之外之和
            }
        }
        this.goods_prcie = good_price;                //(商品价格)
        this.uc_price = uc_price;                     //(红包金额)

        if (orderParam.getPoi_receive_detail().equals("")) {//平台服务费
            this.serviceFee = (float) ((orderParam.getOriginal_price() - shop_part) * 0.06);  //poi_receive_detail空：(original_price - shop_part) * 0.06
            this.income = (float) (orderParam.getOriginal_price() - shop_part - serviceFee);//店铺实际收入金额
        } else { //poi_receive_detail不为空：取poi_receive_detail数组中foodShareFeeChargeByPoi的值
            JSONObject jo = JSON.parseObject(orderParam.getPoi_receive_detail());
            this.serviceFee = (float) jo.getIntValue("foodShareFeeChargeByPoi") / 100;
            this.income = (float) jo.getIntValue("wmPoiReceiveCent") / 100;//店铺实际收入金额
        }


        this.ship_fee = orderParam.getShipping_fee();         //配送费
        this.box_price = box_price;                            //餐盒费
        this.deduction_price = deduction_price;               //折扣金额
        this.vip_deduction_price = 0;                         //会员优惠金额
        this.total_price = orderParam.getTotal().floatValue();    //(订单金额)
        this.shop_part = shop_part;                        //(店铺承担活动费用)
        this.platform_part = platform_part;               //(平台承担活动费用)
        this.out_ship_fee = 0;                             //配送支出费用
        this.message_price = 0;                            //短信支出费用
        if (orderParam.getPay_type() == 2) {
            this.pay_type_id = 6;
            this.pay_type_name = "三方平台";
        } else {
            this.pay_type_id = 3;
            this.pay_type_name = "货到付款";
        }

        this.pay_time = d;

        this.receiver_name = orderParam.getRecipient_name();

        this.receiver_phone = orderParam.getRecipient_phone();

        this.receiver_adress = orderParam.getRecipient_address();

        if (orderParam.getLatitude() != null && orderParam.getLongitude() != null) {
            this.receiver_lat = orderParam.getLatitude();
            this.receiver_lng = orderParam.getLongitude();

            MapBD mapBD = MapUtil.bd_encrypt(this.receiver_lat, this.receiver_lng);
            this.baidu_lng = mapBD.getBd_lon();
            this.baidu_lat = mapBD.getBd_lat();
        }

        //判断是否为预订单
        if (orderParam.getDelivery_time() != 0) {
            Date dDelive = new Date(orderParam.getDelivery_time() * 1000);
            this.service_time = dDelive;
            this.service_time_str = DateUtil.dateToStr(dDelive, "HH:mm");
        } else {
            this.service_time_str = "及时送达";
        }

        this.order_status = 0;
        this.back_status = 0;

        if (orderParam.getHas_invoiced() == 0) {//是否需要发票，0-不需要，1-需要
            this.member_desc = orderParam.getCaution();
        } else {
            this.member_desc = "发票抬头:" + orderParam.getInvoice_title() + "  " + orderParam.getCaution();
        }
        this.stores_id = cdsStores.getStores_id();
        this.brand_id = cdsStores.getBrand_id();
        this.stores_brand_id = cdsStores.getStores_brand_id();

        JSONObject jodetail = JSONObject.parseObject(cdsStores.getDispatch_extinfo());
        int send_type = Integer.parseInt(jodetail.getString("send_type2"));//0饿了么配送 1人工分配 2自动给“生活半径”配送 3自动给“菜大师”配送
        if (send_type == 0) {
            this.send_name = "美团";  //配送方式
            this.is_sync = 0;   //是否发送配送 0、不需要,1、未发送，2、已发送
        } else if (send_type == 1) {
            this.send_name = "人工分配";  //配送方式
            this.is_sync = 0;   //是否发送配送 0、不需要,1、未发送，2、已发送
        } else if (send_type == 2) {
            this.send_name = "生活半径";  //配送方式
            this.is_sync = 1;   //是否发送配送 0、不需要,1、未发送，2、已发送
        } else if (send_type == 3) {
            this.send_name = "点我达";  //配送方式
            this.is_sync = 1;   //是否发送配送 0、不需要,1、未发送，2、已发送
        } else if (send_type == 4) {
            this.send_name = "运速达";  //配送方式
            this.is_sync = 1;   //是否发送配送 0、不需要,1、未发送，2、已发送
        }

        this.isstoresprint = 0; //店堂小票是否打印(0.未打印,1.已打印,2不打印

        this.in_time = day;    //入库时间
        this.updatetime = day; //最后修改时间

        this.goods = BeanUtil.toJsonString(goods);
    }


    /**
     * 设置微信订单信息
     *
     * @param member
     * @param weixinInfo
     * @param cdsStores
     */
    public void setWeixinParam(CdsMember member, CdsWeixinOrderInfo weixinInfo, CdsStores cdsStores) {
        Date day = new Date();
        this.order_id = StringUtil.getPrimaryOrderKey();
        this.member_id = member.getMember_id();
        this.fromin = "微信";
        this.order_desc = weixinInfo.getOrder_id();
        this.fromin_no = weixinInfo.getOrder_no();
        this.fromin_id = String.valueOf(weixinInfo.getStores_id());
        this.fromin_name = "";
        this.order_type = weixinInfo.getOrder_type();
        this.bookdinner = 1;
        this.create_date = weixinInfo.getPay_time();

        List<CdsGood> goods = new ArrayList<CdsGood>();


        JSONArray goodarr = JSONArray.parseArray(weixinInfo.getGoods());
        for (int i = 0; i < goodarr.size(); i++) {
            JSONObject jo = goodarr.getJSONObject(i);
            CdsGood cdsGood = new CdsGood();
            if (jo.getInteger("good_id") == null) {
                cdsGood.setGood_id(0);
                cdsGood.setClass_id(0);
            } else {
                cdsGood.setGood_id(jo.getInteger("good_id"));
                cdsGood.setClass_id(jo.getInteger("good_id"));
            }


            cdsGood.setGood_name(jo.getString("good_name"));

            if (jo.getFloat("price") == null) {
                cdsGood.setPrice(0);
            } else {
                cdsGood.setPrice(jo.getFloat("price"));
            }

            cdsGood.setQuantity(jo.getIntValue("quantity"));
            cdsGood.setGood_type(1);
            //cdsGood.setSpec(jo.getString("standard_name"));
            goods.add(cdsGood);
        }

        this.goods_prcie = weixinInfo.getGoods_prcie(); //(商品价格)
        this.uc_price = weixinInfo.getUc_price();                     //(红包金额)
        this.serviceFee = 0;//平台服务费
        this.ship_fee = weixinInfo.getShip_fee();                     //配送费
        this.box_price = weixinInfo.getBox_price(); //餐盒费
        this.deduction_price = weixinInfo.getDeduction_price();//折扣金额
        this.vip_deduction_price = weixinInfo.getVip_deduction_price();                         //会员优惠金额
        this.total_price = weixinInfo.getTotal_price();    //(订单金额)
        this.income = weixinInfo.getTotal_price();         //店铺实际收入金额
        this.shop_part = 0;  //(店铺承担活动费用)
        this.platform_part = 0;   //(平台承担活动费用)
        this.out_ship_fee = 0;                             //配送支出费用
        this.message_price = 0;                            //短信支出费用
        this.pay_type_id = weixinInfo.getPay_type_id();
        this.pay_type_name = weixinInfo.getPay_type_name();
        this.pay_time = weixinInfo.getPay_time();

        this.receiver_name = weixinInfo.getReceiver_name();
        this.receiver_phone = weixinInfo.getReceiver_phone();
        this.receiver_adress = weixinInfo.getReceiver_adress();
        this.baidu_lng = weixinInfo.getBaidu_lng();
        this.baidu_lat = weixinInfo.getBaidu_lat();

        MapGG mapGG = MapUtil.bd_decrypt(this.baidu_lat, this.baidu_lng);
        this.receiver_lng = mapGG.getGg_lon();
        this.receiver_lat = mapGG.getGg_lat();

        this.service_time = weixinInfo.getService_time();
        this.service_time_str = weixinInfo.getService_time_str();


        this.order_status = 1;
        this.back_status = 0;

       /* if (orderParam.getInvoiced() == 0) {//是否需要发票，0-不需要，1-需要
            this.member_desc = orderParam.getDescription();
        } else {
            this.member_desc = "发票抬头:" + orderParam.getInvoice() + "  " + orderParam.getDescription();
        }*/
        this.stores_id = cdsStores.getStores_id();
        this.brand_id = cdsStores.getBrand_id();
        this.stores_brand_id = cdsStores.getStores_brand_id();

        JSONObject jodetail = JSONObject.parseObject(cdsStores.getDispatch_extinfo());
        if (weixinInfo.getOrder_type() == 1) {
            int send_type = Integer.parseInt(jodetail.getString("send_type4"));//0微信配送 1人工分配 2自动给“生活半径”配送 3自动给“菜大师”配送
            if (send_type == 1) {
                this.send_name = "人工分配";  //配送方式
                this.is_sync = 0;   //是否发送配送 0、不需要,1、未发送，2、已发送
            } else if (send_type == 2) {
                this.send_name = "生活半径";  //配送方式
                this.is_sync = 1;   //是否发送配送 0、不需要,1、未发送，2、已发送
            } else if (send_type == 3) {
                this.send_name = "点我达";  //配送方式
                this.is_sync = 1;   //是否发送配送 0、不需要,1、未发送，2、已发送
            } else if (send_type == 4) {
                this.send_name = "运速达";  //配送方式
                this.is_sync = 1;   //是否发送配送 0、不需要,1、未发送，2、已发送
            }
        } else {
            this.is_sync = 0;
            this.send_name = "客户自取";
        }


        this.isstoresprint = 0; //店堂小票是否打印(0.未打印,1.已打印,2不打印

        this.in_time = day;    //入库时间
        this.updatetime = day; //最后修改时间

        this.goods = BeanUtil.toJsonString(goods);
    }


    /**
     * 微信设置订单信息-临时使用
     *
     * @param member
     * @param orderParam
     * @param cdsStores
     */
    public void setWeixinParam(CdsMember member, CdsOrderInfo orderParam, CdsStores cdsStores) {
        Date day = new Date();

        JSONObject jodetail = JSONObject.parseObject(cdsStores.getDispatch_extinfo());
        int send_type = Integer.parseInt(jodetail.getString("send_type4"));//0微信配送 1人工分配 2自动给“生活半径”配送 3自动给“菜大师”配送
        if (send_type == 1) {
            this.send_name = "人工分配";  //配送方式
            this.is_sync = 0;   //是否发送配送 0、不需要,1、未发送，2、已发送
        } else if (send_type == 2) {
            this.send_name = "生活半径";  //配送方式
            this.is_sync = 1;   //是否发送配送 0、不需要,1、未发送，2、已发送
        } else if (send_type == 3) {
            this.send_name = "点我达";  //配送方式
            this.is_sync = 1;   //是否发送配送 0、不需要,1、未发送，2、已发送
        } else if (send_type == 4) {
            this.send_name = "运速达";  //配送方式
            this.is_sync = 1;   //是否发送配送 0、不需要,1、未发送，2、已发送
        }

        this.isstoresprint = 0; //店堂小票是否打印(0.未打印,1.已打印,2不打印

        this.in_time = day;    //入库时间
        this.updatetime = day; //最后修改时间
    }


}
