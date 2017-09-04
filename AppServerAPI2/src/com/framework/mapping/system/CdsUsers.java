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
@Alias("CdsUsers")
@RsTable(describe = "用户表", name = "cds_users")
public class CdsUsers extends BaseMapping {

    @RsTableField(describe = "主键", name = "id", primaryKey = true)
    private int id;
    @RsTableField(describe = "账号", name = "user_login")
    private String user_login;
    @RsTableField(describe = "密码", name = "user_pass")
    private String user_pass;
    @RsTableField(describe = "昵称", name = "user_nicename")
    private String user_nicename;

    @RsTableField(describe = "头像", name = "avatar")
    private String avatar;

    @RsTableField(describe = "性别", name = "sex")
    private int sex;

    @RsTableField(describe = "生日", name = "birthday")
    private Date birthday;

    @RsTableField(describe = "最后登录IP地址", name = "last_login_ip")
    private String last_login_ip;

    @RsTableField(describe = "最后一次登录时间", name = "last_login_time")
    private Date last_login_time;

    @RsTableField(describe = "用户注册时间", name = "create_time")
    private Date create_time;


    @RsTableField(describe = "用户状态", name = "user_status")
    private int user_status;

    @RsTableField(describe = "手机号码", name = "mobile")
    private String mobile;

    @RsTableField(describe = "udesk客户ID", name = "callid")
    private String callid;

    @RsTableField(describe = "商铺ID", name = "stores_id")
    private int stores_id;

    private String cur_date;
    public String getCur_date() {return cur_date;}
    public void setCur_date(String cur_date) {this.cur_date = cur_date;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_login() {
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }

    public String getUser_nicename() {
        return user_nicename;
    }

    public void setUser_nicename(String user_nicename) {
        this.user_nicename = user_nicename;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public int getUser_status() {
        return user_status;
    }

    public void setUser_status(int user_status) {
        this.user_status = user_status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCallid() {
        return callid;
    }

    public void setCallid(String callid) {
        this.callid = callid;
    }

    public int getStores_id() {
        return stores_id;
    }

    public void setStores_id(int stores_id) {
        this.stores_id = stores_id;
    }
}
