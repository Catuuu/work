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
@Alias("CdsAppVersion")
@RsTable(describe = "app版本更新表", name = "cds_app_version")
public class CdsAppVersion extends BaseMapping {

    @RsTableField(describe = "主键", name = "cav_id", primaryKey = true)
    private int cav_id;

    @RsTableField(describe = "头像", name = "version")
    private int version;

    @RsTableField(describe = "更新URL", name = "version_url")
    private String version_url;

    @RsTableField(describe = "app名称", name = "name")
    private String name;

    public int getCav_id() {
        return cav_id;
    }

    public void setCav_id(int cav_id) {
        this.cav_id = cav_id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getVersion_url() {
        return version_url;
    }

    public void setVersion_url(String version_url) {
        this.version_url = version_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
