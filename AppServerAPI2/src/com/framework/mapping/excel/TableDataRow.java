package com.framework.mapping.excel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Excel行对象
 * User: Administrator
 * Date: 13-1-14
 * Time: 下午5:35
 * To change this template use File | Settings | File Templates.
 */
public class TableDataRow {
    private List<TableDataCell> tableDataCells;

    public TableDataRow(){
        tableDataCells = new ArrayList<TableDataCell>();
    }

    public TableDataRow(List<Map> tr){
        tableDataCells = new ArrayList<TableDataCell>();
        for(Map tdMap:tr){
            TableDataCell tableDataCell = new TableDataCell(tdMap);
            tableDataCells.add(tableDataCell);
        }
    }

    public List<TableDataCell> getTableDataCells() {
        return tableDataCells;
    }

    public void setTableDataCells(List<TableDataCell> tableDataCells) {
        this.tableDataCells = tableDataCells;
    }
}
