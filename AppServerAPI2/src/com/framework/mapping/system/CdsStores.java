package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: chenbin
 * Date: 12-12-31
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
@Alias("CdsStores")
@RsTable(describe = "商铺表", name = "cds_stores")
public class CdsStores extends BaseMapping {

    @RsTableField(describe = "商铺ID", name = "stores_id", primaryKey = true)
    private int stores_id;
    @RsTableField(describe = "商铺名称", name = "name")
    private String name;
    @RsTableField(describe = "简介", name = "intro")
    private String intro;
    @RsTableField(describe = "描述", name = "description")
    private String description;

    @RsTableField(describe = "店长电话", name = "linkman")
    private String linkman;

    @RsTableField(describe = "经营开始时间", name = "start_time")
    private String start_time;

    @RsTableField(describe = "经营结束时间", name = "end_time")
    private String end_time;

    @RsTableField(describe = "最后登录IP地址", name = "last_login_ip")
    private String last_login_ip;

    @RsTableField(describe = "最后一次登录时间", name = "last_login_time")
    private Date last_login_time;

    @RsTableField(describe = "省", name = "province")
    private int province;

    @RsTableField(describe = "市", name = "city")
    private int city;

    @RsTableField(describe = "区", name = "country")
    private int country;

    @RsTableField(describe = "商铺地址", name = "address")
    private String address;

    @RsTableField(describe = "商铺坐标", name = "points")
    private String points;

    @RsTableField(describe = "打印机配置信息", name = "print_info")
    private String print_info;


    @RsTableField(describe = "排序", name = "listorder")
    private int listorder;

    @RsTableField(describe = "配送范围描述", name = "dispatch_desc")
    private String dispatch_desc;

    @RsTableField(describe = "配送距离", name = "dispatch_distance")
    private int dispatch_distance;

    @RsTableField(describe = "配送范围", name = "dispatch")
    private String dispatch;

    @RsTableField(describe = "起送金额", name = "start_cost")
    private double start_cost;

    @RsTableField(describe = "配送费", name = "express_cost")
    private double express_cost;

    @RsTableField(describe = "满多少金额免配置费", name = "express_full_reduction")
    private double express_full_reduction;

    @RsTableField(describe = "创建时间", name = "createtime")
    private Date createtime;

    @RsTableField(describe = "更新时间", name = "posttime")
    private Date posttime;

    @RsTableField(describe = "店铺公告", name = "notice")
    private String notice;

    @RsTableField(describe = "最小经度", name = "min_lng")
    private double min_lng;

    @RsTableField(describe = "最小纬度", name = "min_lat")
    private double min_lat;

    @RsTableField(describe = "最大经度", name = "max_lng")
    private double max_lng;

    @RsTableField(describe = "最大纬度", name = "max_lat")
    private double max_lat;

    @RsTableField(describe = "门店Logo地址", name = "logo")
    private String logo;

    @RsTableField(describe = "门店状态", name = "status")
    private int status;

    @RsTableField(describe = "商铺排除厨房小票关键词", name = "no_print_key")
    private String no_print_key;

    @RsTableField(describe = "自动刷新时间", name = "reftime")
    private int reftime;

    @RsTableField(describe = "自动打印时间", name = "printtime")
    private int printtime;

    @RsTableField(describe = "门店信息", name = "stores_extinfo")
    private String stores_extinfo;

    @RsTableField(describe = "配送信息", name = "dispatch_extinfo")
    private String dispatch_extinfo;


    @RsTableField(describe = "", name = "auto_print")
    private String auto_print;

    @RsTableField(describe = "", name = "auto_in")
    private String auto_in;

    @RsTableField(describe = "", name = "auto_in_count")
    private int auto_in_count;

    @RsTableField(describe = "", name = "order_field")
    private String order_field;

    @RsTableField(describe = "", name = "order_type")
    private String order_type;

    @RsTableField(describe = "", name = "setmeal1")
    private String setmeal1;

    @RsTableField(describe = "", name = "setmeal2")
    private String setmeal2;

    @RsTableField(describe = "", name = "setmeal3")
    private String setmeal3;

    @RsTableField(describe = "取货时间", name = "pickup_time")
    private int pickup_time;

    @RsTableField(describe = "送达时间", name = "complete_time")
    private int complete_time;


    @RsTableField(describe = "短信配置", name = "code_extinfo")
    private String code_extinfo;

    @RsTableField(describe = "收费金额", name = "charge_type")
    private String charge_type;

    @RsTableField(describe = "饿了么商铺", name = "stores1_id")
    private String stores1_id;

    @RsTableField(describe = "美团商铺", name = "stores2_id")
    private String stores2_id;


    public String getStores1_id() {
        return stores1_id;
    }

    public void setStores1_id(String stores1_id) {
        this.stores1_id = stores1_id;
    }

    public String getStores2_id() {
        return stores2_id;
    }

    public void setStores2_id(String stores2_id) {
        this.stores2_id = stores2_id;
    }

    public String getCode_extinfo() {
        return code_extinfo;
    }

    public void setCode_extinfo(String code_extinfo) {
        this.code_extinfo = code_extinfo;
    }

    //品牌ID
    private int brand_id;

    //品牌门店ID
    private int stores_brand_id;

    //饿了么门店id
    private String elem_restaurant_id;

    //美团门店id
    private String meituan_restaurant_id;

    //百度门店id
    private String baidu_restaurant_id;

    //品牌起始流水号
    private int brand_fromno_start;

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public int getStores_brand_id() {
        return stores_brand_id;
    }

    public void setStores_brand_id(int stores_brand_id) {
        this.stores_brand_id = stores_brand_id;
    }

    public String getElem_restaurant_id() {
        return elem_restaurant_id;
    }

    public void setElem_restaurant_id(String elem_restaurant_id) {
        this.elem_restaurant_id = elem_restaurant_id;
    }

    public String getMeituan_restaurant_id() {
        return meituan_restaurant_id;
    }

    public void setMeituan_restaurant_id(String meituan_restaurant_id) {
        this.meituan_restaurant_id = meituan_restaurant_id;
    }

    public String getBaidu_restaurant_id() {
        return baidu_restaurant_id;
    }

    public void setBaidu_restaurant_id(String baidu_restaurant_id) {
        this.baidu_restaurant_id = baidu_restaurant_id;
    }

    public int getBrand_fromno_start() {
        return brand_fromno_start;
    }

    public void setBrand_fromno_start(int brand_fromno_start) {
        this.brand_fromno_start = brand_fromno_start;
    }

    private List<CdsPrints> printsList;

    public int getStores_id() {
        return stores_id;
    }

    public void setStores_id(int stores_id) {
        this.stores_id = stores_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getLast_login_ip() {
        return last_login_ip;
    }

    public void setLast_login_ip(String last_login_ip) {
        this.last_login_ip = last_login_ip;
    }

    public Date getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(Date last_login_time) {
        this.last_login_time = last_login_time;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getPrint_info() {
        return print_info;
    }

    public void setPrint_info(String print_info) {
        this.print_info = print_info;
    }

    public int getListorder() {
        return listorder;
    }

    public void setListorder(int listorder) {
        this.listorder = listorder;
    }

    public String getDispatch_desc() {
        return dispatch_desc;
    }

    public void setDispatch_desc(String dispatch_desc) {
        this.dispatch_desc = dispatch_desc;
    }

    public int getDispatch_distance() {
        return dispatch_distance;
    }

    public void setDispatch_distance(int dispatch_distance) {
        this.dispatch_distance = dispatch_distance;
    }

    public String getDispatch() {
        return dispatch;
    }

    public void setDispatch(String dispatch) {
        this.dispatch = dispatch;
    }

    public double getStart_cost() {
        return start_cost;
    }

    public void setStart_cost(double start_cost) {
        this.start_cost = start_cost;
    }

    public double getExpress_cost() {
        return express_cost;
    }

    public void setExpress_cost(double express_cost) {
        this.express_cost = express_cost;
    }

    public double getExpress_full_reduction() {
        return express_full_reduction;
    }

    public void setExpress_full_reduction(double express_full_reduction) {
        this.express_full_reduction = express_full_reduction;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getPosttime() {
        return posttime;
    }

    public void setPosttime(Date posttime) {
        this.posttime = posttime;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public double getMin_lng() {
        return min_lng;
    }

    public void setMin_lng(double min_lng) {
        this.min_lng = min_lng;
    }

    public double getMin_lat() {
        return min_lat;
    }

    public void setMin_lat(double min_lat) {
        this.min_lat = min_lat;
    }

    public double getMax_lng() {
        return max_lng;
    }

    public void setMax_lng(double max_lng) {
        this.max_lng = max_lng;
    }

    public double getMax_lat() {
        return max_lat;
    }

    public void setMax_lat(double max_lat) {
        this.max_lat = max_lat;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNo_print_key() {
        return no_print_key;
    }

    public void setNo_print_key(String no_print_key) {
        this.no_print_key = no_print_key;
    }

    public int getReftime() {
        return reftime;
    }

    public void setReftime(int reftime) {
        this.reftime = reftime;
    }

    public int getPrinttime() {
        return printtime;
    }

    public void setPrinttime(int printtime) {
        this.printtime = printtime;
    }

    public String getStores_extinfo() {
        return stores_extinfo;
    }

    public void setStores_extinfo(String stores_extinfo) {
        this.stores_extinfo = stores_extinfo;
    }

    public String getDispatch_extinfo() {
        return dispatch_extinfo;
    }

    public void setDispatch_extinfo(String dispatch_extinfo) {
        this.dispatch_extinfo = dispatch_extinfo;
    }

    public String getAuto_print() {
        return auto_print;
    }

    public void setAuto_print(String auto_print) {
        this.auto_print = auto_print;
    }

    public String getAuto_in() {
        return auto_in;
    }

    public void setAuto_in(String auto_in) {
        this.auto_in = auto_in;
    }

    public int getAuto_in_count() {
        return auto_in_count;
    }

    public void setAuto_in_count(int auto_in_count) {
        this.auto_in_count = auto_in_count;
    }

    public String getOrder_field() {
        return order_field;
    }

    public void setOrder_field(String order_field) {
        this.order_field = order_field;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getSetmeal1() {
        return setmeal1;
    }

    public void setSetmeal1(String setmeal1) {
        this.setmeal1 = setmeal1;
    }

    public String getSetmeal2() {
        return setmeal2;
    }

    public void setSetmeal2(String setmeal2) {
        this.setmeal2 = setmeal2;
    }

    public String getSetmeal3() {
        return setmeal3;
    }

    public void setSetmeal3(String setmeal3) {
        this.setmeal3 = setmeal3;
    }

    public int getPickup_time() {
        return pickup_time;
    }

    public void setPickup_time(int pickup_time) {
        this.pickup_time = pickup_time;
    }

    public int getComplete_time() {
        return complete_time;
    }

    public void setComplete_time(int complete_time) {
        this.complete_time = complete_time;
    }

    public String getCharge_type() {
        return charge_type;
    }

    public void setCharge_type(String charge_type) {
        this.charge_type = charge_type;
    }

    public List<CdsPrints> getPrintsList() {
        if (printsList == null) {
            printsList = new ArrayList<CdsPrints>();
        }
        return printsList;
    }

    public void setPrintsList(List<CdsPrints> printsList) {
        this.printsList = printsList;
    }
}
