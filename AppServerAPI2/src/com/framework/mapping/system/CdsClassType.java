package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

/**
 * Created by Administrator on 2017/2/14.
 * 订单商品明细  -旧格式，新版本不使用
 */
@Alias("CdsClassType")
@RsTable(describe = "订单商品明细", name = "cds_class_type")
public class CdsClassType extends BaseMapping {

    @RsTableField(describe = "主键", name = "class_id", primaryKey = true)
    private int class_id;

    @RsTableField(describe = "商品名称", name = "class_name")
    private String class_name;

    @RsTableField(describe = "排序", name = "listorder")
    private int listorder;

    public CdsClassType() {
    }

    public CdsClassType(int class_id, String class_name, int listorder) {
        this.class_id = class_id;
        this.class_name = class_name;
        this.listorder = listorder;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public int getListorder() {
        return listorder;
    }

    public void setListorder(int listorder) {
        this.listorder = listorder;
    }
}
