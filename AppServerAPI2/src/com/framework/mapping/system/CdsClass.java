package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

/**
 *
 * 平台商品类别
 */
@Alias("CdsClass")
@RsTable(describe = "平台商品类别", name = "cds_class")
public class CdsClass extends BaseMapping {

    @RsTableField(describe = "主键", name = "class_id", primaryKey = true)
    private int class_id;

    @RsTableField(describe = "商品种类名称", name = "class_name")
    private String class_name;

    @RsTableField(describe = "类型别名", name = "class_nick_name")
    private String class_nick_name;

    @RsTableField(describe = "类别图标(选中)", name = "class_pic")
    private String class_pic;

    @RsTableField(describe = "类别图标(未选中)", name = "class_pic1")
    private String class_pic1;

    @RsTableField(describe = "类别描述", name = "class_desc")
    private String class_desc;

    @RsTableField(describe = "排序", name = "listorder")
    private int listorder;

    @RsTableField(describe = "品牌id", name = "brand_id")
    private int brand_id;

    public CdsClass() {
    }

    public CdsClass(int class_id, String class_name, String class_nick_name, String class_pic, String class_pic1, String class_desc, int listorder,int brand_id) {
        this.class_id = class_id;
        this.class_name = class_name;
        this.class_nick_name = class_nick_name;
        this.class_pic = class_pic;
        this.class_pic1 = class_pic1;
        this.class_desc = class_desc;
        this.listorder = listorder;
        this.brand_id = brand_id;
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

    public String getClass_nick_name() {
        return class_nick_name;
    }

    public void setClass_nick_name(String class_nick_name) {
        this.class_nick_name = class_nick_name;
    }

    public String getClass_pic() {
        return class_pic;
    }

    public void setClass_pic(String class_pic) {
        this.class_pic = class_pic;
    }

    public String getClass_pic2() {
        return class_pic1;
    }

    public void setClass_pic2(String class_pic2) {
        this.class_pic1 = class_pic2;
    }

    public String getClass_desc() {
        return class_desc;
    }

    public void setClass_desc(String class_desc) {
        this.class_desc = class_desc;
    }

    public int getListorder() {
        return listorder;
    }

    public void setListorder(int listorder) {
        this.listorder = listorder;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }
}
