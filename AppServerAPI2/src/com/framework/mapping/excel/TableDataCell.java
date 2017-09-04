package com.framework.mapping.excel;

import com.framework.util.BeanUtil;

import java.util.Map;

/**
 * Excel列对象.
 * User: Administrator
 * Date: 13-1-14
 * Time: 下午5:30
 * To change this template use File | Settings | File Templates.
 */
public class TableDataCell {

    private String title;
    private String field;
    private String align;
    private String txtformat;
    private int colspan;
    private int rowspan;
    private int width;
    private boolean exp;

    public TableDataCell(){
        super();
    }

    public TableDataCell(Map colmap){
        this.title = BeanUtil.objectToString(colmap.get("title"));
        this.field = BeanUtil.objectToString(colmap.get("field"));
        this.align = BeanUtil.objectToString(colmap.get("align"));
        this.txtformat = BeanUtil.objectToString(colmap.get("txtformat"));

        if(colmap.get("expcolspan")!=null){
            this.colspan = Integer.parseInt(BeanUtil.objectToString(colmap.get("expcolspan"), "0"));
        }else{
            this.colspan = Integer.parseInt(BeanUtil.objectToString(colmap.get("colspan"), "0"));
        }

        if(colmap.get("exprowspan")!=null){
            this.rowspan = Integer.parseInt(BeanUtil.objectToString(colmap.get("exprowspan"), "0"));
        }else{
            this.rowspan = Integer.parseInt(BeanUtil.objectToString(colmap.get("rowspan"), "0"));
        }

        int width = Integer.parseInt(BeanUtil.objectToString(colmap.get("width"), "0")) * 15 / 100;
        this.width = (width == 0) ? 15 : width;
        this.exp = Boolean.parseBoolean(BeanUtil.objectToString(colmap.get("exp")));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getTxtformat() {
        return txtformat;
    }

    public void setTxtformat(String txtformat) {
        this.txtformat = txtformat;
    }

    public int getColspan() {
        return colspan;
    }

    public void setColspan(int colspan) {
        this.colspan = colspan;
    }

    public int getRowspan() {
        return rowspan;
    }

    public void setRowspan(int rowspan) {
        this.rowspan = rowspan;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean isExp() {
        return exp;
    }

    public void setExp(boolean exp) {
        this.exp = exp;
    }
}
