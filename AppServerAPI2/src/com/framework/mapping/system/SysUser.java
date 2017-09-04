package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

/**
 * 系统用户对象
 * User: chenbin
 * Date: 12-12-25
 * Time: 下午5:39
 * To change this template use File | Settings | File Templates.
 */
@Alias("SysUser")
@RsTable(describe = "系统用户", name = "SYS_USER")
public class SysUser extends BaseMapping {
    @RsTableField(describe = "用户id", name = "SU_ID", primaryKey = true)
    private String su_id;

    @RsTableField(describe = "用户类型", name = "SU_TYPE")
    private String su_type;

    @RsTableField(describe = "机构ID", name = "SO_ID")
    private String so_id;

    @RsTableField(describe = "登录账号", name = "SU_CODE")
    private String su_code;

    @RsTableField(describe = "用户名称", name = "SU_NAME")
    private String su_name;

    @RsTableField(describe = "用户密码", name = "SU_PASSWORD")
    private String su_password;

    @RsTableField(describe = "联系电话", name = "SU_TEL")
    private String su_tel;

    @RsTableField(describe = "备注", name = "SU_REMARK")
    private String su_remark;

    @RsTableField(describe = "账号状态（T启用 F禁用）", name = "SU_STATUS")
    private String su_status;

    public String getSu_id() {
        return su_id;
    }

    public void setSu_id(String su_id) {
        this.su_id = su_id;
    }

    public String getSu_type() {
        return su_type;
    }

    public void setSu_type(String su_type) {
        this.su_type = su_type;
    }

    public String getSo_id() {
        return so_id;
    }

    public void setSo_id(String so_id) {
        this.so_id = so_id;
    }

    public String getSu_code() {
        return su_code;
    }

    public void setSu_code(String su_code) {
        this.su_code = su_code;
    }

    public String getSu_name() {
        return su_name;
    }

    public void setSu_name(String su_name) {
        this.su_name = su_name;
    }

    public String getSu_password() {
        return su_password;
    }

    public void setSu_password(String su_password) {
        this.su_password = su_password;
    }

    public String getSu_tel() {
        return su_tel;
    }

    public void setSu_tel(String su_tel) {
        this.su_tel = su_tel;
    }

    public String getSu_remark() {
        return su_remark;
    }

    public void setSu_remark(String su_remark) {
        this.su_remark = su_remark;
    }

    public String getSu_status() {
        return su_status;
    }

    public void setSu_status(String su_status) {
        this.su_status = su_status;
    }
}
