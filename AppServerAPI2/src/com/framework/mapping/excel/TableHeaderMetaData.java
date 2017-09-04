package com.framework.mapping.excel;

import com.framework.util.BeanUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 表头对象，用于创建excel表头
 * User: Administrator
 * Date: 13-1-14
 * Time: 下午5:34
 * To change this template use File | Settings | File Templates.
 */
public class TableHeaderMetaData {
    //固定表头的行
    private List<TableDataRow> frozenColumns;
    //非固定表头行
    private List<TableDataRow> columns;
    //保并后的行集合
    private List<TableDataRow> allColumns;
    //保存临时对象
    private Map mapHeaderMeta;

    public TableHeaderMetaData(String exportJsonParams){
        mapHeaderMeta = BeanUtil.createBean(exportJsonParams, Map.class);
        initFrozenColumns();
        initColumnsColumns();
        mergeDataCell();
    }

    private void initFrozenColumns(){
        frozenColumns = new ArrayList<TableDataRow>();
        List<List<Map>> frozenColumnsList = (List<List<Map>>) mapHeaderMeta.get("frozenColumns");
        for(List<Map> tr:frozenColumnsList){
            TableDataRow tableDataRow = new TableDataRow(tr);
            frozenColumns.add(tableDataRow);
        }
    }

    private void initColumnsColumns(){
        columns = new ArrayList<TableDataRow>();
        List<List<Map>> columnsList = (List<List<Map>>) mapHeaderMeta.get("columns");
        for(List<Map> tr:columnsList){
            TableDataRow tableDataRow = new TableDataRow(tr);
            columns.add(tableDataRow);
        }
    }



    public List<TableDataRow> getFrozenColumns() {
        return frozenColumns;
    }

    public void setFrozenColumns(List<TableDataRow> frozenColumns) {
        this.frozenColumns = frozenColumns;
    }

    public List<TableDataRow> getColumns() {
        return columns;
    }

    public void setColumns(List<TableDataRow> columns) {
        this.columns = columns;
    }

    public List<TableDataRow> getAllColumns() {
        return allColumns;
    }

    public void setAllColumns(List<TableDataRow> allColumns) {
        this.allColumns = allColumns;
    }

    //合并表头为一个集合
    private void mergeDataCell(){
        allColumns = new ArrayList<TableDataRow>();
        allColumns.addAll(frozenColumns);
        for (int trFrozen = 0; trFrozen < columns.size();trFrozen++){
            TableDataRow tableDataRow= columns.get(trFrozen);
            if(allColumns.get(trFrozen)!=null){
                allColumns.get(trFrozen).getTableDataCells().addAll(tableDataRow.getTableDataCells());
            }else{
                TableDataRow tableData = new TableDataRow();
                tableData.getTableDataCells().addAll(tableDataRow.getTableDataCells());
                allColumns.add(tableData);
            }
        }

    }
}
