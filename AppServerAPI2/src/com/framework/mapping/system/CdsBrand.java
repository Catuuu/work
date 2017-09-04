package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

/**
 * Created by chenbin on 2017/2/16.
 * 打印机表
 */
@Alias("CdsBrand")
@RsTable(describe = "品牌表", name = "cds_brand")
public class CdsBrand extends BaseMapping {

    @RsTableField(describe = "主键", name = "brand_id", primaryKey = true)
    private int brand_id;

    @RsTableField(describe = "品牌名称", name = "brand_name")
    private String brand_name;

    @RsTableField(describe = "备注", name = "brand_comment")
    private String brand_comment;

    @RsTableField(describe = "品牌起始流水号", name = "brand_fromno_start")
    private int brand_fromno_start;

    @RsTableField(describe = "微信公众号appid", name = "weixin_appid")
    private String weixin_appid;

    @RsTableField(describe = "微信公众号appsecret", name = "weixin_appsecret")
    private String weixin_appsecret;

    @RsTableField(describe = "微信公众号token", name = "weixin_token")
    private String weixin_token;

    @RsTableField(describe = "微信商户号", name = "weixin_mchid")
    private String weixin_mchid;

    @RsTableField(describe = "微信支付秘钥", name = "weixin_key")
    private String weixin_key;

    public String getWeixin_appid() {
        return weixin_appid;
    }

    public void setWeixin_appid(String weixin_appid) {
        this.weixin_appid = weixin_appid;
    }

    public String getWeixin_appsecret() {
        return weixin_appsecret;
    }

    public void setWeixin_appsecret(String weixin_appsecret) {
        this.weixin_appsecret = weixin_appsecret;
    }

    public String getWeixin_token() {
        return weixin_token;
    }

    public void setWeixin_token(String weixin_token) {
        this.weixin_token = weixin_token;
    }

    public String getWeixin_mchid() {
        return weixin_mchid;
    }

    public void setWeixin_mchid(String weixin_mchid) {
        this.weixin_mchid = weixin_mchid;
    }

    public String getWeixin_key() {
        return weixin_key;
    }

    public void setWeixin_key(String weixin_key) {
        this.weixin_key = weixin_key;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getBrand_comment() {
        return brand_comment;
    }

    public void setBrand_comment(String brand_comment) {
        this.brand_comment = brand_comment;
    }

    public int getBrand_fromno_start() {
        return brand_fromno_start;
    }

    public void setBrand_fromno_start(int brand_fromno_start) {
        this.brand_fromno_start = brand_fromno_start;
    }
}
