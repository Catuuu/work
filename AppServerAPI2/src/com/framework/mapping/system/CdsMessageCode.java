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
@Alias("CdsMessageCode")
@RsTable(describe = "短信记录表", name = "cds_message_code")
public class CdsMessageCode extends BaseMapping {

    @RsTableField(describe = "主键", name = "ID", primaryKey = true)
    private int id;
    @RsTableField(describe = "用户id", name = "MEMBER_ID")
    private int member_id;
    @RsTableField(describe = "电话", name = "PHONE")
    private String mc_mobile;

    @RsTableField(describe = "日期", name = "MC_DATE")
    private Date mc_date;

    @RsTableField(describe = "IP", name = "MC_ADDIP")
    private String mc_addip;

    @RsTableField(describe = "短信类型", name = "MC_TYPE")
    private String mc_type;

    @RsTableField(describe = "短信内容", name = "MC_CONTENT")
    private String mc_content;

    @RsTableField(describe = "验证码", name = "MC_CONTENT")
    private String mc_code;

    @RsTableField(describe = "是否使用", name = "ISUSE")
    private String isuse;

    @RsTableField(describe = "使用时间", name = "MC_CHECKTIME")
    private Date mc_checktime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getMc_mobile() {
        return mc_mobile;
    }

    public void setMc_mobile(String mc_mobile) {
        this.mc_mobile = mc_mobile;
    }

    public Date getMc_date() {
        return mc_date;
    }

    public void setMc_date(Date mc_date) {
        this.mc_date = mc_date;
    }

    public String getMc_addip() {
        return mc_addip;
    }

    public void setMc_addip(String mc_addip) {
        this.mc_addip = mc_addip;
    }

    public String getMc_type() {
        return mc_type;
    }

    public void setMc_type(String mc_type) {
        this.mc_type = mc_type;
    }

    public String getMc_content() {
        return mc_content;
    }

    public void setMc_content(String mc_content) {
        this.mc_content = mc_content;
    }

    public String getMc_code() {
        return mc_code;
    }

    public void setMc_code(String mc_code) {
        this.mc_code = mc_code;
    }

    public String getIsuse() {
        return isuse;
    }

    public void setIsuse(String isuse) {
        this.isuse = isuse;
    }

    public Date getMc_checktime() {
        return mc_checktime;
    }

    public void setMc_checktime(Date mc_checktime) {
        this.mc_checktime = mc_checktime;
    }
}
