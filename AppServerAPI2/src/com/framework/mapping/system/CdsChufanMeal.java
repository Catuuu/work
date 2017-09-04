package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

@Alias("CdsChufanMeal")
@RsTable(describe = "出餐机", name = "cds_chufan_meal")
public class CdsChufanMeal extends BaseMapping {
    @RsTableField(describe = "主键", name = "cm_id", primaryKey = true)
    private Integer cm_id;

    @RsTableField(describe = "", name = "stores_id")
    private Integer stores_id;

    @RsTableField(describe = "", name = "cm_name")
    private String cm_name;

    @RsTableField(describe = "", name = "ct_user")
    private String ct_user;

    @RsTableField(describe = "", name = "ct_password")
    private String ct_password;

    @RsTableField(describe = "", name = "ct_num")
    private Integer ct_num;

    @RsTableField(describe = "", name = "status")
    private Integer status;

    @RsTableField(describe = "洗菜组", name = "wash_group")
    private String wash_group;

    @RsTableField(describe = "配菜组", name = "jardiniere_group")
    private String jardiniere_group;

    @RsTableField(describe = "厨师", name = "cook_group")
    private String cook_group;

    public String getWash_group() {return wash_group;}

    public void setWash_group(String wash_group) {this.wash_group = wash_group;}

    public String getJardiniere_group() {return jardiniere_group;}

    public void setJardiniere_group(String jardiniere_group) {this.jardiniere_group = jardiniere_group;}

    public String getCook_group() {return cook_group;}

    public void setCook_group(String cook_group) {this.cook_group = cook_group;}

    private Integer good_id;

    public Integer getGood_id() {return good_id;}

    public void setGood_id(Integer good_id) {this.good_id = good_id;}

    public Integer getCm_id() {
        return cm_id;
    }

    public void setCm_id(Integer cm_id) {
        this.cm_id = cm_id;
    }

    public Integer getStores_id() {
        return stores_id;
    }

    public void setStores_id(Integer stores_id) {
        this.stores_id = stores_id;
    }

    public String getCm_name() {
        return cm_name;
    }

    public void setCm_name(String cm_name) {
        this.cm_name = cm_name;
    }

    public String getCt_user() {
        return ct_user;
    }

    public void setCt_user(String ct_user) {
        this.ct_user = ct_user;
    }

    public String getCt_password() {
        return ct_password;
    }

    public void setCt_password(String ct_password) {
        this.ct_password = ct_password;
    }

    public Integer getCt_num() {
        return ct_num;
    }

    public void setCt_num(Integer ct_num) {
        this.ct_num = ct_num;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
