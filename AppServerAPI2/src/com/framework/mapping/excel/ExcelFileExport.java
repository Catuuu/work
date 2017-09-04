package com.framework.mapping.excel;

import com.framework.dao.SqlDao;
import com.framework.mapping.PageInfo;
import com.framework.mapping.PageParam;
import com.framework.mapping.system.CdsUsers;
import com.framework.util.BeanUtil;
import com.framework.util.StringUtil;
import com.framework.util.WebUtil;
import com.opensdk.eleme2.utils.StringUtils;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import jxl.write.Label;
import jxl.write.Number;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.framework.system.SystemConstant.LOGIN_USER_KEY;

/**
 * Excel文件导出工具类
 * Created with IntelliJ IDEA.
 * User: chenbin
 * Date: 13-1-10
 * Time: 下午3:32
 * To change this template use File | Settings | File Templates.
 */
public class ExcelFileExport {
    private SqlDao sqlDao;
    //导出文件名
    private String expfileName;
    //页面传入参数
    private Map formInfo;
    //分页对象
    private PageParam pageParam;

    private PageInfo pageInfo;
    //查询结果集
    private List resultList;

    private String numberFormat;

    public void setNumberFormat(String numberFormat){
        this.numberFormat = numberFormat;
    }

    public void setResultList(List resultList){
        this.resultList = resultList;
    }


    //创建的Excel对象
    private WritableWorkbook book;

    public String getExpfileName() {
        return expfileName;
    }

    public ExcelFileExport(SqlDao sqlDao, String recordSqlID, String countSqlID, Map formInfo) {
        this.sqlDao = sqlDao;
        this.formInfo = formInfo;
        this.expfileName = StringUtil.getPrimaryKey();

        pageInfo = PageInfo.getPageInfo(recordSqlID, countSqlID, formInfo);

        pageParam = new PageParam();
        pageParam.setPageInfo(pageInfo);
        pageParam.setFormInfo(formInfo);
        pageParam.setLoginUser((CdsUsers) WebUtil.getSession(LOGIN_USER_KEY));
    }


    public ExcelFileExport(Map formInfo) {
        this.formInfo = formInfo;
        this.expfileName = StringUtil.getPrimaryKey();
    }

    //外部调用此方法生成Excel文件
    public void createFile() {
        try {
            String filePath = WebUtil.getServletContext().getRealPath("/") + "WEB-INF\\download\\";
            String fileName = BeanUtil.objectToString(formInfo.get("expfileName"), "导出文件");
            WebUtil.setSession("f" + this.getExpfileName(), fileName);
            File file = new File(filePath + expfileName + ".xls ");
            //  打开文件,创建一个excel文件
            book = Workbook.createWorkbook(file);
            if (pageParam != null) {
                loadExcelDataPage();//装载分页数据
            } else {
                loadExcelDataCurrentPage();//装载当前页数据
            }
            HttpServletResponse response = WebUtil.getResponse();
            InputStream in = new FileInputStream(file);
            OutputStream out = response.getOutputStream();

            //写文件
            int b;
            while((b=in.read())!= -1)
            {
                out.write(b);
            }

            in.close();
            out.close();
            //写入数据并关闭文件
            book.write();
            book.close();
            WebUtil.setSession("p" + this.getExpfileName(), "end");

        } catch (Exception e) {
            WebUtil.setSession("p" + this.getExpfileName(), "error");
            e.printStackTrace();
        }
    }

    public void createFileDay() {
        try {
            String filePath = WebUtil.getServletContext().getRealPath("/") + "WEB-INF\\download\\";
            String fileName = BeanUtil.objectToString(formInfo.get("expfileName"), "导出文件");
            WebUtil.setSession("f" + this.getExpfileName(), fileName);
            File file = new File(filePath + expfileName + ".xls ");
            //  打开文件,创建一个excel文件
            book = Workbook.createWorkbook(file);
            loadExcelDataCurrentPage();//装载当前页数据
            //写入数据并关闭文件
            book.write();
            book.close();
            WebUtil.setSession("p" + this.getExpfileName(), "end");

        } catch (Exception e) {
            WebUtil.setSession("p" + this.getExpfileName(), "error");
            e.printStackTrace();
        }
    }

    public void createFileDayFormat() {
        try {
            String filePath = WebUtil.getServletContext().getRealPath("/") + "WEB-INF\\download\\";
            String fileName = BeanUtil.objectToString(formInfo.get("expfileName"), "导出文件");
            WebUtil.setSession("f" + this.getExpfileName(), fileName);
            File file = new File(filePath + expfileName + ".xls ");
            //  打开文件,创建一个excel文件
            book = Workbook.createWorkbook(file);
            loadExcelDataFormat();//装载当前页数据
            //写入数据并关闭文件
            book.write();
            book.close();
            WebUtil.setSession("p" + this.getExpfileName(), "end");

        } catch (Exception e) {
            WebUtil.setSession("p" + this.getExpfileName(), "error");
            e.printStackTrace();
        }
    }

    //装载分页数据
    private void loadExcelDataPage() throws Exception {
        //设置每次查询的大小
        pageInfo.setRows(1000);
        //创建一个工作簿, 生成名为“第一页”的工作表，参数0表示这是第一页
        int sheetIndex = 0;
        WritableSheet sheet = book.createSheet("第" + (sheetIndex + 1) + "页", sheetIndex);
        //生成工作簿标题
        Map sheetMap = crateSheet(formInfo, sheet);

        long sumPage = 0;//总页数
        int row = 0;//当前工作簿已使用的行数
        while (resultList == null || pageInfo.getCurrentPage() < sumPage) {
            pageInfo.setCurrentPage(pageInfo.getCurrentPage() + 1);

            resultList = sqlDao.queryPageList(pageParam);
            row = writeDataInt(sheetMap, row, resultList, sheet);
            if (row > 10000) {
                sheetIndex++;
                //创建一个工作簿, 生成名为“第一页”的工作表，参数0表示这是第一页
                sheet = book.createSheet("第" + (sheetIndex + 1) + "页", sheetIndex);
                //生成工作簿标题
                sheetMap = crateSheet((Map) formInfo, sheet);
                row = 0;
            }
            sumPage = pageInfo.calcTotalPage();
            if (sumPage == 0) {
                WebUtil.setSession("p" + this.getExpfileName(), "empty");
                book.write();
                book.close();
                return;
            }
            long process = (pageInfo.getCurrentPage() * 100) / sumPage;
            WebUtil.setSession("p" + this.getExpfileName(), process + "");
        }
    }

    //装载当前页数据
    private void loadExcelDataCurrentPage() throws Exception {
        //创建一个工作簿, 生成名为“第一页”的工作表，参数0表示这是第一页
        WritableSheet sheet = book.createSheet("第1页", 0);
        //生成工作簿标题
        Map sheetMap = crateSheet(formInfo, sheet);
        if(null == resultList || resultList.isEmpty())resultList = BeanUtil.createBean((String)formInfo.get("rows"), ArrayList.class);
        //生成数据
        writeDataInt(sheetMap, 0, resultList, sheet);
        WebUtil.setSession("p" + this.getExpfileName(), 100 + "");
    }

    //装载当前页数据
    private void loadExcelDataFormat() throws Exception {
        //创建一个工作簿, 生成名为“第一页”的工作表，参数0表示这是第一页
        WritableSheet sheet = book.createSheet("第1页", 0);
        //生成工作簿标题
        Map sheetMap = crateSheet(formInfo, sheet);
        if(null == resultList || resultList.isEmpty())resultList = BeanUtil.createBean((String)formInfo.get("rows"), ArrayList.class);
        //生成数据
        writeDataFormat(sheetMap, 0, resultList, sheet);
        WebUtil.setSession("p" + this.getExpfileName(), 100 + "");
    }

    /**
     * 生成一个工作簿，并生成表头
     *
     * @param formInfo
     * @return
     */
    private Map crateSheet(Map formInfo, WritableSheet sheet) {
        Map result = new HashMap();
        try {

            //标题的样式
            WritableFont font1 = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD);
            WritableCellFormat formatTitle = new WritableCellFormat(font1);
            // 把水平对齐方式指定为居中
            formatTitle.setAlignment(jxl.format.Alignment.CENTRE);
            // 把垂直对齐方式指定为居中
            formatTitle.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
            formatTitle.setBorder(Border.ALL, BorderLineStyle.THIN);
            formatTitle.setBackground(Colour.GRAY_25);

            //得到前台页面table标头定义样式,并转为Map对象
            String exportJsonParams = (String)formInfo.get("exportJsonParams");
            TableHeaderMetaData tableHeaderMetaData = new TableHeaderMetaData(exportJsonParams);

            int row_num = 0;//行号
            int col_num = 0;//列号
            int maxcol = 1;//列的最大数

            Map columnMap = new HashMap();//装载已经被使用的例

            for (TableDataRow tableDataRow : tableHeaderMetaData.getAllColumns()) {//锁定列字段
                col_num = 1;
                for (TableDataCell tableDataCell : tableDataRow.getTableDataCells()) {
                    if (tableDataCell.isExp()) {
                        while (columnMap.get(col_num + "," + row_num) != null) {
                            col_num++;
                        }
                        if (!BeanUtil.isNullAndEmpty(tableDataCell.getField())) {
                            columnMap.put("key" + col_num, tableDataCell.getField());
                            columnMap.put("align" + col_num, tableDataCell.getAlign());
                            columnMap.put("txtformat" + col_num, tableDataCell.getTxtformat());
                        }
                        Label label = new Label(col_num, row_num, tableDataCell.getTitle(), formatTitle);
                        sheet.addCell(label);
                        sheet.setColumnView(col_num, tableDataCell.getWidth());
                        int colEnd = (tableDataCell.getColspan() == 0) ? col_num : tableDataCell.getColspan() + col_num - 1; //合并的列号
                        int rowEnd = (tableDataCell.getRowspan() == 0) ? row_num : row_num + tableDataCell.getRowspan() - 1;//合并的行号
                        sheet.mergeCells(col_num, row_num, colEnd, rowEnd);
                        columnMap.put(colEnd + "," + rowEnd, "true"); //添加此列已经被使用
                        col_num = (tableDataCell.getColspan() == 0) ? col_num + 1 : tableDataCell.getColspan() + col_num;
                        maxcol = (col_num > maxcol) ? col_num : maxcol;
                    }
                }
                row_num++;
            }
            sheet.setColumnView(0, 7);
            Label label = new Label(0, 0, "序号", formatTitle);
            sheet.addCell(label);
            sheet.mergeCells(0, 0, 0, row_num - 1);

            result.put("maxcol", maxcol);
            result.put("columnMap", columnMap);
            result.put("row", row_num);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return result;
    }



    //Excel中写数据
    private int writeDataInt(Map param, int row, List list, WritableSheet sheet) throws Exception {
        //列标题的样式
        WritableFont font1 = new WritableFont(WritableFont.createFont("微软雅黑"), 10,WritableFont.BOLD);
        WritableFont font2= new WritableFont(WritableFont.createFont("微软雅黑"), 10,WritableFont.NO_BOLD);
        NumberFormat fontNumber= new NumberFormat(numberFormat);
        WritableCellFormat formatTitle = new WritableCellFormat(font1,fontNumber);
        WritableCellFormat formatRow1 = new WritableCellFormat(font2,fontNumber);
        WritableCellFormat formatRow2 = new WritableCellFormat(font2,fontNumber);
        formatTitle.setAlignment(jxl.format.Alignment.CENTRE);
        formatRow1.setAlignment(jxl.format.Alignment.CENTRE);
        formatRow2.setAlignment(jxl.format.Alignment.CENTRE);
        formatTitle.setBorder(Border.ALL, BorderLineStyle.THIN);
        formatRow1.setBorder(Border.ALL, BorderLineStyle.THIN);
        formatRow2.setBorder(Border.ALL, BorderLineStyle.THIN);
        formatTitle.setBackground(Colour.GRAY_25);

        //背景色
        Color color = Color.decode("#F2F2F2");//自定义的颜色
        //重写一个颜色值，以Colour.ORANGE为例
        book.setColourRGB(Colour.LIGHT_TURQUOISE, color.getRed(), color.getGreen(), color.getBlue());
        Colour testColour = Colour.LIGHT_TURQUOISE;

        formatRow2.setBackground(testColour);

        int index = Integer.parseInt(BeanUtil.objectToString(param.get("index"), "0"));
        int maxcol = Integer.parseInt(BeanUtil.objectToString(param.get("maxcol"), "0"));
        int rown = Integer.parseInt(BeanUtil.objectToString(param.get("row"), "0"));
        row = rown > row ? rown : row;
        Map columnMap = (Map) param.get("columnMap");
        //循环list写入数据
        for (int i = 0; list != null && i < list.size(); i++) {
            Map map = (Map) list.get(i);
            String rownumber = String.valueOf(i+1);
            Label label = new Label(0, row, rownumber, formatTitle);
            sheet.addCell(label);
            for (int j = 1; j < maxcol; j++) {
                String field = BeanUtil.objectToString(columnMap.get("key" + j));
                String align = BeanUtil.objectToString(columnMap.get("align" + j), "LEFT");
                String txtformat = BeanUtil.objectToString(columnMap.get("txtformat" + j), "");
                WritableCellFormat formatContent = new WritableCellFormat(font1);
                formatContent.setBorder(Border.ALL, BorderLineStyle.THIN);
                formatContent.setAlignment(jxl.format.Alignment.LEFT);
                if (align.equalsIgnoreCase("CENTER")) {
                    formatContent.setAlignment(jxl.format.Alignment.CENTRE);
                } else if (align.equalsIgnoreCase("RIGHT")) {
                    formatContent.setAlignment(jxl.format.Alignment.RIGHT);
                }
                Object objValue = map.get(field);
                String value = ObjTOStr(objValue, txtformat);
//                label = new Label(j, row, value, formatContent);
                if(i%2==1){
                    if(isNumeric(value)){
                        Number number = new Number(j,row,Double.valueOf(value),formatRow2);
                        sheet.addCell(number);
                    }else{
                        label = new Label(j, row, value, formatRow2);
                        sheet.addCell(label);
                    }
                }else{
                    if(isNumeric(value)){
                        Number number = new Number(j,row,Double.valueOf(value),formatRow1);
                        sheet.addCell(number);
                    }else{
                        label = new Label(j, row, value, formatRow1);
                        sheet.addCell(label);
                    }
                }


            }
            row++;
        }
        return row;
    }

    //Excel中写数据
    private int writeDataFormat(Map param, int row, List list, WritableSheet sheet) throws Exception {
        //列标题的样式
        WritableFont fontTitle = new WritableFont(WritableFont.createFont("微软雅黑"), 10,WritableFont.BOLD);
        WritableFont fontRow = new WritableFont(WritableFont.createFont("微软雅黑"), 10,WritableFont.NO_BOLD);
        WritableFont countStyle = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false, UnderlineStyle.NO_UNDERLINE,Colour.ROSE);
        NumberFormat fontNumber= new NumberFormat("#,##0.00");
        NumberFormat fontFs= new NumberFormat("#,##0");
        WritableCellFormat formatTitle = new WritableCellFormat(fontTitle,fontNumber);
        WritableCellFormat formatTitle2 = new WritableCellFormat(fontTitle,fontNumber);
        WritableCellFormat formatCountStyle = new WritableCellFormat(countStyle,fontNumber);
        WritableCellFormat formatCountStyle2 = new WritableCellFormat(countStyle,fontNumber);
        WritableCellFormat formatCountStyleFs = new WritableCellFormat(countStyle,fontFs);
        WritableCellFormat formatCountStyle2Fs = new WritableCellFormat(countStyle,fontFs);
        WritableCellFormat formatRow1 = new WritableCellFormat(fontRow,fontNumber);
        WritableCellFormat formatRow1Fs = new WritableCellFormat(fontRow,fontFs);
        WritableCellFormat formatRow2 = new WritableCellFormat(fontRow,fontNumber);
        WritableCellFormat formatRow2Fs = new WritableCellFormat(fontRow,fontFs);
        //字体位置
        formatTitle.setAlignment(jxl.format.Alignment.CENTRE);
        formatTitle2.setAlignment(jxl.format.Alignment.CENTRE);
        formatCountStyle.setAlignment(jxl.format.Alignment.CENTRE);
        formatCountStyle2.setAlignment(jxl.format.Alignment.CENTRE);
        formatCountStyleFs.setAlignment(jxl.format.Alignment.CENTRE);
        formatCountStyle2Fs.setAlignment(jxl.format.Alignment.CENTRE);
        formatRow1.setAlignment(jxl.format.Alignment.LEFT);
        formatRow1Fs.setAlignment(jxl.format.Alignment.LEFT);
        formatRow2.setAlignment(jxl.format.Alignment.LEFT);
        formatRow2Fs.setAlignment(jxl.format.Alignment.LEFT);
        //边框
        formatTitle.setBorder(Border.ALL, BorderLineStyle.THIN);
        formatTitle2.setBorder(Border.ALL, BorderLineStyle.THIN);
        formatCountStyle.setBorder(Border.ALL, BorderLineStyle.THIN);
        formatCountStyle2.setBorder(Border.ALL, BorderLineStyle.THIN);
        formatCountStyleFs.setBorder(Border.ALL, BorderLineStyle.THIN);
        formatCountStyle2Fs.setBorder(Border.ALL, BorderLineStyle.THIN);
        formatRow1.setBorder(Border.ALL, BorderLineStyle.THIN);
        formatRow1Fs.setBorder(Border.ALL, BorderLineStyle.THIN);
        formatRow2.setBorder(Border.ALL, BorderLineStyle.THIN);
        formatRow2Fs.setBorder(Border.ALL, BorderLineStyle.THIN);
        //背景色
        Color color = Color.decode("#F2F2F2");//自定义的颜色
        //重写一个颜色值，以Colour.ORANGE为例
        book.setColourRGB(Colour.LIGHT_TURQUOISE, color.getRed(), color.getGreen(), color.getBlue());
        Colour testColour = Colour.LIGHT_TURQUOISE;
        formatTitle.setBackground(testColour);
        formatCountStyle2.setBackground(testColour);
        formatCountStyle2Fs.setBackground(testColour);
        formatRow2.setBackground(testColour);
        formatRow2Fs.setBackground(testColour);



        int maxcol = Integer.parseInt(BeanUtil.objectToString(param.get("maxcol"), "0"));
        int rown = Integer.parseInt(BeanUtil.objectToString(param.get("row"), "0"));
        row = rown > row ? rown : row;
        Map columnMap = (Map) param.get("columnMap");
        //循环list写入数据
        for (int i = 0; list != null && i < list.size(); i++) {
            Map map = (Map) list.get(i);
            String rownumber = String.valueOf(i+1);
            Label label = new Label(0, row, rownumber, formatTitle2);
            sheet.addCell(label);
            for (int j = 1; j < maxcol; j++) {
                String field = BeanUtil.objectToString(columnMap.get("key" + j));
                String align = BeanUtil.objectToString(columnMap.get("align" + j), "LEFT");
                String txtformat = BeanUtil.objectToString(columnMap.get("txtformat" + j), "");
                if (align.equalsIgnoreCase("CENTER")) {
                    formatRow1.setAlignment(jxl.format.Alignment.CENTRE);
                    formatRow2.setAlignment(jxl.format.Alignment.CENTRE);
                    formatRow2Fs.setAlignment(jxl.format.Alignment.CENTRE);
                } else if (align.equalsIgnoreCase("RIGHT")) {
                    formatRow1.setAlignment(jxl.format.Alignment.RIGHT);
                    formatRow2.setAlignment(jxl.format.Alignment.RIGHT);
                    formatRow2Fs.setAlignment(jxl.format.Alignment.RIGHT);
                }
                Object objValue = map.get(field);
                String value = ObjTOStr(objValue, txtformat);
//                label = new Label(j, row, value, formatContent);
                if(i==list.size()-1){
                    if(isNumeric(value)){
                        if(i%2==1){
                            Number number = new Number(j,row,Double.valueOf(value),formatTitle);
                            sheet.addCell(number);
                        }else{
                            Number number = new Number(j,row,Double.valueOf(value),formatTitle2);
                            sheet.addCell(number);
                        }
                    }else{
                        if(i%2==1){
                            label = new Label(j, row, value, formatRow2);
                            sheet.addCell(label);
                        }else{
                            label = new Label(j, row, value, formatRow1);
                            sheet.addCell(label);
                        }
                    }
                    continue;
                }
                if(j==maxcol-1||j==maxcol-2){
                    if(isNumeric(value)){
                        if(i%2==1){
                            if(value.contains(".")){
                                Number number = new Number(j,row,Double.valueOf(value),formatCountStyle2);
                                sheet.addCell(number);
                            }else{
                                Number number = new Number(j,row,Double.valueOf(value),formatCountStyle2Fs);
                                sheet.addCell(number);
                            }
                        }else{
                            if(value.contains(".")){
                                Number number = new Number(j,row,Double.valueOf(value),formatCountStyle);
                                sheet.addCell(number);
                            }else{
                                Number number = new Number(j,row,Double.valueOf(value),formatCountStyleFs);
                                sheet.addCell(number);
                            }
                        }
                    }else{
                        if(i%2==1){
                            label = new Label(j, row, value, formatRow2);
                            sheet.addCell(label);
                        }else{
                            label = new Label(j, row, value, formatRow1);
                            sheet.addCell(label);
                        }
                    }
                    continue;
                }
                if(isNumeric(value)){
                    if(i%2==1){
                        if(value.contains(".")){
                            Number number = new Number(j,row,Double.valueOf(value),formatRow2);
                            sheet.addCell(number);
                        }else{
                            Number number = new Number(j,row,Double.valueOf(value),formatRow2Fs);
                            sheet.addCell(number);
                        }
                    }else{
                        if(value.contains(".")){
                            Number number = new Number(j,row,Double.valueOf(value),formatRow1);
                            sheet.addCell(number);
                        }else{
                            Number number = new Number(j,row,Double.valueOf(value),formatRow1Fs);
                            sheet.addCell(number);
                        }
                    }
                }else{
                    if(i%2==1){
                        label = new Label(j, row, value, formatRow2);
                        sheet.addCell(label);
                    }else{
                        label = new Label(j, row, value, formatRow1);
                        sheet.addCell(label);
                    }
                }
            }
            row++;
        }
        return row;
    }



    public boolean isNumeric(String str){
        if(str.contains("-"))return false;
        if(StringUtils.isEmpty(str))return false;
        if(".".equals(str.substring(0,1)))return true;
//        if(str.matches("^((13[0-9])|(15[^4,\\\\D])|(18[0,5-9]))\\\\d{8}$"))return false;
        return str.substring(0,1).matches("[0-9]");
    }


    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final DecimalFormat doubleFormat = new DecimalFormat("####.0000");
    private final DecimalFormat longFormat = new DecimalFormat("####");
    private final Log logger = LogFactory.getLog(getClass());


    /**
     * 对象全部转换成字符串类型
     *
     * @param value
     */
    private String ObjTOStr(Object value, String txtformat) {
        if (value != null) {
            String valueType = value.getClass().getName();
            if (valueType.equals("java.lang.String")) {
                return (String) value;
            } else if (valueType.equals("java.lang.Integer")) {
                return longFormat.format(value);
            } else if (valueType.equals("java.lang.Long")) {
                return longFormat.format(value);
            } else if (valueType.equals("java.lang.Float") ||
                    valueType.equals("java.lang.Double") ||
                    valueType.equals("java.math.BigDecimal")) {
                if (txtformat.equals("")) {
                    return doubleFormat.format(value);
                } else {
                    DecimalFormat format = new DecimalFormat(txtformat);
                    return format.format(value);
                }
            } else if (valueType.equals("java.util.Date") ||
                    valueType.equals("java.sql.Date")) {
                if (txtformat.equals("")) {
                    return dateFormat.format(value);
                } else {
                    SimpleDateFormat format = new SimpleDateFormat(txtformat);
                    return format.format(value);
                }
            } else if (valueType.equals("java.sql.Timestamp")) {
                if (txtformat.equals("")) {
                    return timeFormat.format(value);
                } else {
                    SimpleDateFormat format = new SimpleDateFormat(txtformat);
                    return format.format(value);
                }
            } else if (valueType.equals("java.lang.Boolean")) {
                return value.toString();
            } else if (valueType.equals("java.lang.Byte")) {
                return value.toString();
            } else if (valueType.equals("java.lang.Short")) {
                return value.toString();
            }
        }
        return "";
    }


}