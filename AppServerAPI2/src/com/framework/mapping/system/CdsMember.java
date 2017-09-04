package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 *
 * User: chenbin
 * Date: 12-12-31
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
@Alias("CdsMember")
@RsTable(describe = "会员表", name = "cds_member")
public class CdsMember extends BaseMapping {

    @RsTableField(describe = "主键", name = "member_id", primaryKey = true)
    private int member_id;
    @RsTableField(describe = "头像", name = "head_pic")
    private String head_pic;
    @RsTableField(describe = "电话", name = "phone")
    private String phone;
    @RsTableField(describe = "用户昵称", name = "name")
    private String name;
    @RsTableField(describe = "用户密码", name = "password")
    private String password;
    @RsTableField(describe = "微信ID", name = "openid")
    private String openid;
    @RsTableField(describe = "创建时间", name = "create_time")
    private Date create_time;
    @RsTableField(describe = "最后登录时间", name = "last_login_time")
    private Date last_login_time;
    @RsTableField(describe = "最后登录IP", name = "last_login_ip")
    private String last_login_ip;
    @RsTableField(describe = "登录次数", name = "login_count")
    private int login_count;
    @RsTableField(describe = "用户状态 0：禁用； 1：正常", name = "user_status")
    private int user_status;
    @RsTableField(describe = "是否已处理", name = "isdo")
    private int isdo;

    @RsTableField(describe = "余额", name = "balance_money")
    private double balance_money;

    @RsTableField(describe = "邀请人ID", name = "Invite_id")
    private int Invite_id;

    @RsTableField(describe = "定位信息", name = "location")
    private String  location;

    @RsTableField(describe = "首次下单时间", name = "first_order_time")
    private Date  first_order_time;

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

    @RsTableField(describe = "会员星级ID", name = "level_id")
    private int  level_id;

    @RsTableField(describe = "会员星级到期时间", name = "level_date")
    private Date  level_date;


    @RsTableField(describe = "品牌ID", name = "brand_id")
    private int  brand_id;

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(Date last_login_time) {
        this.last_login_time = last_login_time;
    }

    public String getLast_login_ip() {
        return last_login_ip;
    }

    public void setLast_login_ip(String last_login_ip) {
        this.last_login_ip = last_login_ip;
    }

    public int getLogin_count() {
        return login_count;
    }

    public void setLogin_count(int login_count) {
        this.login_count = login_count;
    }

    public int getUser_status() {
        return user_status;
    }

    public void setUser_status(int user_status) {
        this.user_status = user_status;
    }

    public double getBalance_money() {
        return balance_money;
    }

    public void setBalance_money(double balance_money) {
        this.balance_money = balance_money;
    }

    public int getInvite_id() {
        return Invite_id;
    }

    public void setInvite_id(int invite_id) {
        Invite_id = invite_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public int getLevel_id() {
        return level_id;
    }

    public void setLevel_id(int level_id) {
        this.level_id = level_id;
    }

    public Date getLevel_date() {
        return level_date;
    }

    public void setLevel_date(Date level_date) {
        this.level_date = level_date;
    }

    public int getIsdo() {
        return isdo;
    }

    public void setIsdo(int isdo) {
        this.isdo = isdo;
    }
}
