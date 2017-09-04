package com.framework.mapping.system;

import com.framework.annotation.RsBeanField;
import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 系统文件对象
 * User: chenbin
 * Date: 12-12-31
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
@Alias("SysFiles")
@RsTable(describe = "系统文件类型", name = "SYS_FILES")
public class SysFiles extends BaseMapping {
    @RsTableField(describe = "关键词", name = "SF_ID", primaryKey = true)
    private String sf_id;
    @RsTableField(describe = "文件名", name = "SF_NAME")
    private String sf_name;
    @RsTableField(describe = "文件类", name = "SF_TYPE")
    private String sf_type;
    @RsTableField(describe = "文件大小", name = "SF_SIZE")
    private long sf_size;
    @RsTableField(describe = "上传时间", name = "SF_DATE")
    private Date sf_date;
    @RsTableField(describe = "业务键值", name = "SF_TRANSACTION")
    private String sf_transaction;

    @RsBeanField(describe = "已存在还是临时的")
    private String exist;

    public String getSf_id() {
        return sf_id;
    }

    public void setSf_id(String sf_id) {
        this.sf_id = sf_id;
    }

    public String getSf_name() {
        return sf_name;
    }

    public void setSf_name(String sf_name) {
        this.sf_name = sf_name;
    }

    public String getSf_type() {
        return sf_type;
    }

    public void setSf_type(String sf_type) {
        this.sf_type = sf_type;
    }

    public long getSf_size() {
        return sf_size;
    }

    public void setSf_size(long sf_size) {
        this.sf_size = sf_size;
    }

    public Date getSf_date() {
        return sf_date;
    }

    public void setSf_date(Date sf_date) {
        this.sf_date = sf_date;
    }

    public String getSf_transaction() {
        return sf_transaction;
    }

    public void setSf_transaction(String sf_transaction) {
        this.sf_transaction = sf_transaction;
    }

    public String getExist() {
        return exist;
    }

    public void setExist(String exist) {
        this.exist = exist;
    }
}
