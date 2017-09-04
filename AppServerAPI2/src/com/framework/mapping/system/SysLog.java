package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 系统日志对象
 * User: chenbin
 * Date: 12-12-31
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
@Alias("SysLog")
@RsTable(describe = "系统日志", name = "SYS_LOG")
public class SysLog extends BaseMapping {
    @RsTableField(describe = "关键词", name = "SL_ID", primaryKey = true)
    private String sl_id;
    @RsTableField(describe = "记录时间", name = "SL_DATE")
    private Date sl_date;
    @RsTableField(describe = "用户编码", name = "SL_USERCODE")
    private String sl_usercode;
    @RsTableField(describe = "用户名称", name = "SL_USERNAME")
    private String sl_username;
    @RsTableField(describe = "表名称", name = "SL_TABLE")
    private String sl_table;
    @RsTableField(describe = "日志内容", name = "SL_CONTENT")
    private String sl_content;

    public String getSl_id() {
        return sl_id;
    }

    public void setSl_id(String sl_id) {
        this.sl_id = sl_id;
    }

    public Date getSl_date() {
        return sl_date;
    }

    public void setSl_date(Date sl_date) {
        this.sl_date = sl_date;
    }

    public String getSl_usercode() {
        return sl_usercode;
    }

    public void setSl_usercode(String sl_usercode) {
        this.sl_usercode = sl_usercode;
    }

    public String getSl_username() {
        return sl_username;
    }

    public void setSl_username(String sl_username) {
        this.sl_username = sl_username;
    }

    public String getSl_table() {
        return sl_table;
    }

    public void setSl_table(String sl_table) {
        this.sl_table = sl_table;
    }

    public String getSl_content() {
        return sl_content;
    }

    public void setSl_content(String sl_content) {
        this.sl_content = sl_content;
    }
}
