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
 *
 * User: chenbin
 * Date: 12-12-31
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
@Alias("CdsOrderSend")
@RsTable(describe = "订单配送明细", name = "cds_order_send")
public class CdsOrderSend extends BaseMapping {

    @RsTableField(describe = "主键", name = "order_id", primaryKey = true)
    private String order_id;

    @RsTableField(describe = "商铺ID", name = "stores_id")
    private int stores_id;

    @RsTableField(describe = "商铺名称", name = "name")
    private String name;

    @RsTableField(describe = "订单时间", name = "create_date")
    private Date create_date;

    @RsTableField(describe = "收货地址经度", name = "receiver_lng")
    private double receiver_lng;

    @RsTableField(describe = "收货地址纬度", name = "receiver_lat")
    private double receiver_lat;

    @RsTableField(describe = "收货人名称", name = "receiver_name")
    private String receiver_name;

    @RsTableField(describe = "收货人地址", name = "receiver_adress")
    private String receiver_adress;

    @RsTableField(describe = "步行距离(米)", name = "distance")
    private int distance;

    @RsTableField(describe = "步行时间(秒)", name = "duration")
    private int duration;


    @RsTableField(describe = "计算公里数", name = "kilometre")
    private int kilometre;


    @RsTableField(describe = "配送费用", name = "send_price")
    private float send_price;

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStores_id() {
        return stores_id;
    }

    public void setStores_id(int stores_id) {
        this.stores_id = stores_id;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
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

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getReceiver_adress() {
        return receiver_adress;
    }

    public void setReceiver_adress(String receiver_adress) {
        this.receiver_adress = receiver_adress;
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
}
