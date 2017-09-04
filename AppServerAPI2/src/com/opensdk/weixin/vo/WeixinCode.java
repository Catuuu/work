package com.opensdk.weixin.vo;

import java.util.Date;

/**
 * Created by Administrator on 2017/5/9.
 */
public class WeixinCode {

    private String create_day;  //创建日期
    private int stores_id;      //门店ID
    private int no;             //订单流水号


    public WeixinCode(String create_day, int stores_id, int no) {
        this.create_day = create_day;
        this.stores_id = stores_id;
        this.no = no;
    }

    public String getCreate_day() {
        return create_day;
    }

    public void setCreate_day(String create_day) {
        this.create_day = create_day;
    }

    public int getStores_id() {
        return stores_id;
    }

    public void setStores_id(int stores_id) {
        this.stores_id = stores_id;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }
}
