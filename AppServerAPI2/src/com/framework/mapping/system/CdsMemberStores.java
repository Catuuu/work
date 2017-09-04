package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * Created by Administrator on 2017/2/10.
 */
@Alias("CdsMemberStores")
@RsTable(describe = "用户各商铺下单明细", name = "cds_member_stores")
public class CdsMemberStores  extends BaseMapping {
    @RsTableField(describe = "主键", name = "id", primaryKey = true)
    private long id;

    @RsTableField(describe = "首次下单时间", name = "first_order_time")
    private Date first_order_time;

    @RsTableField(describe = "最后下单时间", name = "last_order_time")
    private Date  last_order_time;

    @RsTableField(describe = "总下单数", name = "all_order_count")
    private int  all_order_count;

    @RsTableField(describe = "饿了么首次下单时间", name = "first_order_time1")
    private Date  first_order_time1;

    @RsTableField(describe = "饿了么最后下单时间", name = "last_order_time1")
    private Date  last_order_time1;

    @RsTableField(describe = "饿了么下单数", name = "all_order_count1")
    private int  all_order_count1;

    @RsTableField(describe = "美团首次下单时间", name = "first_order_time2")
    private Date  first_order_time2;

    @RsTableField(describe = "美团最后下单时间", name = "last_order_time2")
    private Date  last_order_time2;

    @RsTableField(describe = "美团下单数", name = "all_order_count2")
    private int  all_order_count2;

    @RsTableField(describe = "百度首次下单时间", name = "first_order_time3")
    private Date  first_order_time3;

    @RsTableField(describe = "百度最后下单时间", name = "last_order_time3")
    private Date  last_order_time3;

    @RsTableField(describe = "百度下单数", name = "all_order_count3")
    private int  all_order_count3;

    @RsTableField(describe = "微信首次下单时间", name = "first_order_time4")
    private Date  first_order_time4;

    @RsTableField(describe = "微信最后下单时间", name = "last_order_time4")
    private Date  last_order_time4;

    @RsTableField(describe = "微信下单数", name = "all_order_count4")
    private int  all_order_count4;


    @RsTableField(describe = "商铺ID", name = "stores_id")
    private int  stores_id;

    @RsTableField(describe = "用户ID", name = "member_id")
    private int  member_id;

    @RsTableField(describe = "品牌ID", name = "brand_id")
    private int  brand_id;

    public CdsMemberStores(){

    }
    public CdsMemberStores(int member_id,int stores_id,int brand_id,Date d){
        this.member_id = member_id;
        this.stores_id = stores_id;
        this.brand_id = brand_id;
        this.all_order_count = 0;
        this.all_order_count1 = 0;
        this.all_order_count2 = 0;
        this.all_order_count3 = 0;
        this.all_order_count4 = 0;
        this.first_order_time = d;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFirst_order_time() {
        return first_order_time;
    }

    public void setFirst_order_time(Date first_order_time) {
        this.first_order_time = first_order_time;
    }

    public Date getLast_order_time() {
        return last_order_time;
    }

    public void setLast_order_time(Date last_order_time) {
        this.last_order_time = last_order_time;
    }

    public int getAll_order_count() {
        return all_order_count;
    }

    public void setAll_order_count(int all_order_count) {
        this.all_order_count = all_order_count;
    }

    public Date getFirst_order_time1() {
        return first_order_time1;
    }

    public void setFirst_order_time1(Date first_order_time1) {
        this.first_order_time1 = first_order_time1;
    }

    public Date getLast_order_time1() {
        return last_order_time1;
    }

    public void setLast_order_time1(Date last_order_time1) {
        this.last_order_time1 = last_order_time1;
    }

    public int getAll_order_count1() {
        return all_order_count1;
    }

    public void setAll_order_count1(int all_order_count1) {
        this.all_order_count1 = all_order_count1;
    }

    public Date getFirst_order_time2() {
        return first_order_time2;
    }

    public void setFirst_order_time2(Date first_order_time2) {
        this.first_order_time2 = first_order_time2;
    }

    public Date getLast_order_time2() {
        return last_order_time2;
    }

    public void setLast_order_time2(Date last_order_time2) {
        this.last_order_time2 = last_order_time2;
    }

    public int getAll_order_count2() {
        return all_order_count2;
    }

    public void setAll_order_count2(int all_order_count2) {
        this.all_order_count2 = all_order_count2;
    }

    public Date getFirst_order_time3() {
        return first_order_time3;
    }

    public void setFirst_order_time3(Date first_order_time3) {
        this.first_order_time3 = first_order_time3;
    }

    public Date getLast_order_time3() {
        return last_order_time3;
    }

    public void setLast_order_time3(Date last_order_time3) {
        this.last_order_time3 = last_order_time3;
    }

    public int getAll_order_count3() {
        return all_order_count3;
    }

    public void setAll_order_count3(int all_order_count3) {
        this.all_order_count3 = all_order_count3;
    }

    public Date getFirst_order_time4() {
        return first_order_time4;
    }

    public void setFirst_order_time4(Date first_order_time4) {
        this.first_order_time4 = first_order_time4;
    }

    public Date getLast_order_time4() {
        return last_order_time4;
    }

    public void setLast_order_time4(Date last_order_time4) {
        this.last_order_time4 = last_order_time4;
    }

    public int getAll_order_count4() {
        return all_order_count4;
    }

    public void setAll_order_count4(int all_order_count4) {
        this.all_order_count4 = all_order_count4;
    }

    public int getStores_id() {
        return stores_id;
    }

    public void setStores_id(int stores_id) {
        this.stores_id = stores_id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }
}
