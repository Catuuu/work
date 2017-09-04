package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

/**
 * Created by chenbin on 2017/2/16.
 * 打印机表
 */
@Alias("CdsArea")
@RsTable(describe = "省市区表", name = "cds_area")
public class CdsArea extends BaseMapping {

    @RsTableField(describe = "主键", name = "id", primaryKey = true)
    private int id;

    @RsTableField(describe = "父节点ID", name = "parentid")
    private int parentid;

    @RsTableField(describe = "级联数据名称", name = "dataname")
    private String dataname;

    @RsTableField(describe = "级联数据层次", name = "are_level")
    private int are_level;

    @RsTableField(describe = "排序ID", name = "listorder")
    private int listorder;

    @RsTableField(describe = "扩展字段", name = "extend")
    private String extend;

    public CdsArea() {
    }

    public CdsArea(int id, int parentid, String dataname, int are_level, int listorder, String extend) {
        this.id = id;
        this.parentid = parentid;
        this.dataname = dataname;
        this.are_level = are_level;
        this.listorder = listorder;
        this.extend = extend;
    }

    public int getId() {
        return id;
    }

    public int getParentid() {
        return parentid;
    }

    public String getDataname() {
        return dataname;
    }

    public int getAre_level() {
        return are_level;
    }

    public int getListorder() {
        return listorder;
    }

    public String getExtend() {
        return extend;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public void setDataname(String dataname) {
        this.dataname = dataname;
    }

    public void setAre_level(int are_level) {
        this.are_level = are_level;
    }

    public void setListorder(int listorder) {
        this.listorder = listorder;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }
}
