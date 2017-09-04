package com.api;

import com.alibaba.fastjson.JSONObject;
import com.framework.annotation.ResourceMethod;
import com.framework.controller.BasicController;
import com.framework.mapping.JsonMessage;
import com.framework.mapping.excel.ExcelFileExport;
import com.framework.util.DateUtil;
import com.framework.util.NumberUtil;
import com.framework.util.WebUtil;
import com.service.OrderService;
import com.service.PrintService;
import com.service.StoresService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.framework.annotation.CheckType.CHECK_LOGIN;


/**
 * Created by Administrator on 2017/2/4.订单查询接口
 */
@Controller
@RequestMapping("/ReportAPI")
public class ReportController extends BasicController {
    @Resource(name = "StoresService")
    protected StoresService storesService;

    @Resource(name = "PrintService")
    protected PrintService printService;

    @Resource(name = "OrderService")
    protected OrderService orderService;


    @RequestMapping(value = "report1", method = RequestMethod.POST)
    @ResourceMethod(name = "用户分析", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage report1(@RequestBody Map formInfo) throws Exception {
        formInfo.put("stores_id",WebUtil.getUser().getStores_id());
        if(WebUtil.getSession("stores_id") != null)
            formInfo.put("stores_id",WebUtil.getSession("stores_id"));
        String datatype = formInfo.get("datatype").toString();
        List<Map> resultList = sqlDao.getRecordList("cds_report.report1", formInfo);

        List<String> time_names = new <String>ArrayList();
        List<Integer> resutdata = new <Integer>ArrayList(); //合计
        List<Integer> resutdata1 = new <Integer>ArrayList(); //饿了么
        List<Integer> resutdata2 = new <Integer>ArrayList(); //美团
        List<Integer> resutdata3 = new <Integer>ArrayList(); //百度外卖
        List<Integer> resutdata4 = new <Integer>ArrayList(); //微信

        List goods = new ArrayList();
        for (int i = 0; i < resultList.size(); i++) {
            Map goodMap = resultList.get(i);

            String day = goodMap.get("report_time").toString();
            time_names.add(day);
            resutdata.add(Integer.parseInt(goodMap.get("member_count"+datatype).toString()));
            resutdata1.add(Integer.parseInt(goodMap.get("eleme_member_count"+datatype).toString()));
            resutdata2.add(Integer.parseInt(goodMap.get("meituan_member_count"+datatype).toString()));
            resutdata3.add(Integer.parseInt(goodMap.get("baidu_member_count"+datatype).toString()));
            resutdata4.add(Integer.parseInt(goodMap.get("weixin_member_count"+datatype).toString()));
        }

        Map good = new HashMap();
        good.put("name","总计");
        good.put("data",resutdata);
        goods.add(good);

        Map good1 = new HashMap();
        good1.put("name","饿了么");
        good1.put("data",resutdata1);
        goods.add(good1);

        Map good2 = new HashMap();
        good2.put("name","美团");
        good2.put("data",resutdata2);
        goods.add(good2);

        Map good3 = new HashMap();
        good3.put("name","百度外卖");
        good3.put("data",resutdata3);
        goods.add(good3);

        Map good4 = new HashMap();
        good4.put("name","微信");
        good4.put("data",resutdata4);
        goods.add(good4);

        String title = "新用户";
        if(datatype.equals("1")){
            title =  "新用户";
        }else if(datatype.equals("2")){
            title =  "活跃用户";
        }else if(datatype.equals("3")){
            title =  "沉默用户";
        }else if(datatype.equals("4")){
            title =  "流失用户";
        }else if(datatype.equals("5")){
            title =  "新增用户";
        }

        Map resultData = new HashMap();
        resultData.put("reporttitle", title);
        resultData.put("list", resultList);
        resultData.put("time_names", time_names);
        resultData.put("goods", goods);
        JsonMessage message = new JsonMessage(1, "success", resultData);
        return message;
    }


    @RequestMapping(value = "report2", method = RequestMethod.POST)
    @ResourceMethod(name = "出餐率统计", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage report2(@RequestBody Map formInfo) throws Exception {
        formInfo.put("stores_id",WebUtil.getUser().getStores_id());
        if(WebUtil.getSession("stores_id") != null)
            formInfo.put("stores_id",WebUtil.getSession("stores_id"));
        List<Map> list =  sqlDao.getRecordList("cds_stores.orderOnTimeStores", formInfo);

        //easyui 表头字符串循环
        StringBuffer str = new StringBuffer("[[");
        for(int i=0;i<list.size();i++){
            if(i==0){
                str.append("{\"field\":\"storescount\",\"title\":\"店铺合计\",\"width\":80,\"height\":50},");
                if(list.size()==1) str.append("{\"field\":\"cur_storescount"+i+"\",\"title\":\""+list.get(i).get("storesName")+"\",\"width\":100,\"height\":50}]]");
                else str.append("{\"field\":\"cur_storescount"+i+"\",\"title\":\""+list.get(i).get("storesName")+"\",\"width\":100,\"height\":50},");

            }else if(i==list.size()-1)str.append("{\"field\":\"cur_storescount"+i+"\",\"title\":\""+list.get(i).get("storesName")+"\",\"width\":100,\"height\":50}]]");
            else str.append("{\"field\":\"cur_storescount"+i+"\",\"title\":\""+list.get(i).get("storesName")+"\",\"width\":100,\"height\":50},");
        }


        formInfo.put("list",list);

        String datatype = formInfo.get("datatype").toString();
        List<Map> resultList = sqlDao.getRecordList("cds_report.report2", formInfo);

        List<String> time_names = new <String>ArrayList();
        BigDecimal[][] resutData = new BigDecimal[list.size()+1][resultList.size()];
        List goods = new ArrayList();

        //数据循环处理  其中i为列,x为行..查询店铺时没有合计店铺,展示合计店铺需要多加1列  故i+1
        int x = 0;
        for(Map goodMap : resultList){
            time_names.add(goodMap.get("order_date").toString());
            for(int i=0;i<list.size();i++){
                BigDecimal count = new BigDecimal(goodMap.get("cur_storescount"+i).toString()).multiply(new BigDecimal(100)).setScale(3,BigDecimal.ROUND_HALF_UP);
                if(i==0){
                    BigDecimal sunCount = new BigDecimal(goodMap.get("storescount").toString()).multiply(new BigDecimal(100)).setScale(3,BigDecimal.ROUND_HALF_UP);
                    resutData[i][x] = sunCount;
                    resutData[i+1][x] = count;
                    goodMap.put("storescount",sunCount+"%");
                    goodMap.put("cur_storescount"+i,count+"%");
                }else{
                    resutData[i+1][x] = count;
                    goodMap.put("cur_storescount"+i,count+"%");
                }
            }
            x++;
        }

        for(int i =0;i<list.size();i++){
            Map goodMap = new HashMap();
            if(i==0){
                goodMap.put("name","店铺合计");
                goodMap.put("data",resutData[i]);
                goods.add(goodMap);
                goodMap = new HashMap();
                goodMap.put("name",list.get(i).get("storesName"));
                goodMap.put("data",resutData[i+1]);
            }else{
                goodMap.put("name",list.get(i).get("storesName"));
                goodMap.put("data",resutData[i+1]);
            }
            goods.add(goodMap);
        }


        Map resultData = new HashMap();
        resultData.put("reporttitle", datatype);
        resultData.put("list", resultList);
        resultData.put("time_names", time_names);
        resultData.put("title_name", str);
        resultData.put("goods", goods);
        JsonMessage message = new JsonMessage(1, "success", resultData);
        return message;
    }

    @RequestMapping(value = "report2Excel", method = RequestMethod.POST)
    @ResourceMethod(name = "出餐率统计", check = CHECK_LOGIN)
    @ResponseBody
    public void report2Excel(String startday,String endday,String datatype) throws Exception {
        Map formInfo = new HashMap();
        formInfo.put("startday",startday);
        formInfo.put("endday",endday);
        formInfo.put("datatype",datatype);
        formInfo.put("stores_id",WebUtil.getUser().getStores_id());
        if(WebUtil.getSession("stores_id") != null)
            formInfo.put("stores_id",WebUtil.getSession("stores_id"));
        formInfo = getFormInfoExcel(formInfo);
        List list = (List)formInfo.get("list");
        List<Map> resultList = sqlDao.getRecordList("cds_report.report2", formInfo);
        int x = 0;
        for(Map goodMap : resultList){
            for(int i=0;i<list.size();i++){
                BigDecimal count = new BigDecimal(goodMap.get("cur_storescount"+i).toString()).multiply(new BigDecimal(100)).setScale(3,BigDecimal.ROUND_HALF_UP);
                if(i==0){
                    BigDecimal sunCount = new BigDecimal(goodMap.get("storescount").toString()).multiply(new BigDecimal(100)).setScale(3,BigDecimal.ROUND_HALF_UP);
                    goodMap.put("storescount",sunCount);
                    goodMap.put("cur_storescount"+i,count);
                }else{
                    goodMap.put("cur_storescount"+i,count);
                }
            }
            x++;
        }
        //创建导出excel文件线程
        ExcelFileExport fileExcel = new ExcelFileExport(sqlDao, "null", "null", formInfo);
        fileExcel.setResultList(resultList);
        fileExcel.setNumberFormat("#,##0.00");
        String fileName = fileExcel.getExpfileName();
        //给web前台输出文件名称
        responseText("{\"fileName\":\"" + fileName + "\"}");
        //本地生成文件名
        fileExcel.createFileDay();
    }

    public Map getFormInfoExcel(Map formInfo) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(sdf.parse(formInfo.get("startday").toString()));
        Map excelMap = new HashMap();
        List frozenColumns = new ArrayList<>();
        List<Map> frozenColumn1 = new ArrayList<>();
        Map columMap = new HashMap();
        columMap.put("field","order_date");
        columMap.put("title","时间");
        columMap.put("width","80");
        columMap.put("exp",true);
        frozenColumn1.add(columMap);
        columMap = new HashMap();
        columMap.put("field","storescount");
        columMap.put("title","店铺合计(%)");
        columMap.put("width","80");
        columMap.put("exp",true);
        frozenColumn1.add(columMap);
        List<Map> list =  sqlDao.getRecordList("cds_stores.orderOnTimeStores", formInfo);
        for (int i = 0;i<list.size();i++){
                Map temp1 = new HashMap();
                temp1.put("field","cur_storescount"+i);
                temp1.put("title",list.get(i).get("storesName")+"(%)");
                temp1.put("width","100");
                temp1.put("exp",true);
                frozenColumn1.add(temp1);
        }
        formInfo.put("list",list);
        frozenColumns.add(frozenColumn1);
        excelMap.put("frozenColumns",frozenColumns);
        List columns = new ArrayList();
        excelMap.put("columns",columns);
        formInfo.put("exportJsonParams", JSONObject.toJSON(excelMap).toString());
        return formInfo;
    }

}
