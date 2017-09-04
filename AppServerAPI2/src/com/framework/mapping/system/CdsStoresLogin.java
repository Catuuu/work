package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

import java.util.Date;


/**
 * Created by chenbin on 2017/2/16.
 * 打包客户端配置列表
 */
@Alias("CdsStoresLogin")
@RsTable(describe = "打包客户端配置列表", name = "cds_stores_login")
public class CdsStoresLogin extends BaseMapping {

    @RsTableField(describe = "主键", name = "id", primaryKey = true)
    private int id;

    @RsTableField(describe = "商铺id", name = "stores_id")
    private int stores_id;

    @RsTableField(describe = "登陆账号", name = "username")
    private String username;

    @RsTableField(describe = "登陆密码", name = "pass")
    private String pass;

    @RsTableField(describe = "版本号", name = "version_no")
    private String version_no;

    @RsTableField(describe = "登陆时间", name = "login_time")
    private Date login_time;

    @RsTableField(describe = "打印格式", name = "print_format_id")
    private int print_format_id;

    public CdsStoresLogin() {
    }

    public CdsStoresLogin(int id, int stores_id, String username, String pass, String version_no, Date login_time) {
        this.id = id;
        this.stores_id = stores_id;
        this.username = username;
        this.pass = pass;
        this.version_no = version_no;
        this.login_time = login_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStores_id() {
        return stores_id;
    }

    public void setStores_id(int stores_id) {
        this.stores_id = stores_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getVersion_no() {
        return version_no;
    }

    public void setVersion_no(String version_no) {
        this.version_no = version_no;
    }

    public Date getLogin_time() {
        return login_time;
    }

    public void setLogin_time(Date login_time) {
        this.login_time = login_time;
    }

    public int getPrint_format_id() {
        return print_format_id;
    }

    public void setPrint_format_id(int print_format_id) {
        this.print_format_id = print_format_id;
    }
}
