package com.api;

import com.alibaba.fastjson.JSONObject;
import com.framework.annotation.ResourceMethod;
import com.framework.controller.BasicController;
import com.framework.mapping.JsonMessage;
import com.framework.mapping.PageResult;
import com.framework.mapping.excel.ExcelFileExport;
import com.framework.util.DateUtil;
import com.framework.util.WebUtil;
import com.service.OrderService;
import com.service.PrintService;
import com.service.StoresService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.framework.annotation.CheckType.CHECK_LOGIN;


/**
 * Created by Administrator on 2017/2/4.订单查询接口
 */
@Controller
@RequestMapping("/GoodAPI")
public class GoodController extends BasicController {
    @Resource(name = "StoresService")
    protected StoresService storesService;

    @Resource(name = "PrintService")
    protected PrintService printService;

    @Resource(name = "OrderService")
    protected OrderService orderService;


    @RequestMapping(value = "goodReport",method = RequestMethod.POST)
    @ResourceMethod(name = "商品销售统计", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage goodReport(@RequestBody Map formInfo) throws Exception {
        if(WebUtil.getSession("stores_id") != null)
            formInfo.put("stores_id",WebUtil.getSession("stores_id"));
        else formInfo.put("stores_id",WebUtil.getUser().getStores_id());
       /* String endday = formInfo.get("endday").toString();
        long daysum = DateUtil.getBetweenDay(DateUtil.strToDate(endday), DateUtil.getToday());
        List resultList;
        if(daysum==0){
            resultList = sqlDao.getRecordList("cds_good.getReportGood",formInfo);
        }else{
            resultList = sqlDao.getRecordList("cds_good.getReportGoodHistory",formInfo);
        }*/
        List resultList = sqlDao.getRecordList("cds_good.getReportGood",formInfo);
        JsonMessage message = new JsonMessage(1,"success",resultList);
        return message;
    }

    @RequestMapping(value = "getReportAnalyse",method = RequestMethod.POST)
    @ResourceMethod(name = "商品分析", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage getReportAnalyse(@RequestBody Map formInfo) throws Exception {
        if(WebUtil.getSession("stores_id") != null)
            formInfo.put("stores_id",WebUtil.getSession("stores_id"));
            else formInfo.put("stores_id",WebUtil.getUser().getStores_id());
        List goods = new ArrayList();
        formInfo = editReportAnalyse(formInfo);
        List<Map>  recordList = sqlDao.getRecordList("cds_good.getEditReportAnalyse",formInfo);
        int max  = ((List)formInfo.get("list")).size();
        List titleList = (List)formInfo.get("titlelist");
        StringBuffer str = new StringBuffer("[[");
        for(int i = 0;i<titleList.size();i++){
            if(i==0){
                str.append("{\"field\":\"sum_count\",\"title\":\"合计\",\"width\":80,\"height\":50},");
                if(titleList.size()==1) str.append("{\"field\":\"good_count_"+i+"\",\"title\":\""+titleList.get(i)+"\",\"width\":100,\"height\":50}]]");
                else str.append("{\"field\":\"good_count_"+i+"\",\"title\":\""+titleList.get(i)+"\",\"width\":100,\"height\":50},");

            }else if(i==titleList.size()-1)str.append("{\"field\":\"good_count_"+i+"\",\"title\":\""+titleList.get(i)+"\",\"width\":100,\"height\":50}]]");
            else str.append("{\"field\":\"good_count_"+i+"\",\"title\":\""+titleList.get(i)+"\",\"width\":100,\"height\":50},");

        }
        int s = 0;
        for(Map goodMap : recordList){
            List row = new ArrayList();
            for(int i=0;i<max;i++){
                row.add(goodMap.get("good_count_"+i));
            }
            Map good = new HashMap();
            good.put("name",goodMap.get("good_name"));
            good.put("data",row);
            goods.add(good);
            if(s>5){
                good.put("visible",false);
            }
            s++;
        }
        Map resultData = new HashMap();
        resultData.put("list",recordList);
        resultData.put("time_names",str);
        resultData.put("titleList",titleList);
        resultData.put("goods",goods);
        JsonMessage message = new JsonMessage(1,"success",resultData);
        return message;

    }


    public Map editReportAnalyse(Map formInfo){
        int startTime = Integer.parseInt(formInfo.get("startTime").toString());
        int endTime = Integer.parseInt(formInfo.get("endTime").toString());
        int splitTime = Integer.parseInt(formInfo.get("splitTime").toString());
        List sqllist = new ArrayList();
        List titlelist = new ArrayList();
        for(;startTime<endTime;startTime+=splitTime){
            Map onTime = new HashMap();
            if(startTime + splitTime > endTime){
                onTime.put("mix",startTime);
                onTime.put("max",startTime+(endTime-startTime)%60);
                sqllist.add(onTime);
                if((startTime%60<10||startTime%60==0)&&(endTime%60<10||endTime%60==0))
                    titlelist.add(startTime/60+":0"+startTime%60+"-"+endTime/60+":0"+ endTime%60);
                else if(startTime%60<10||startTime%60==0)
                    titlelist.add(startTime/60+":0"+startTime%60+"-"+endTime/60+":"+ endTime%60);
                else if(endTime%60<10||endTime%60==0)
                    titlelist.add(startTime/60+":"+startTime%60+"-"+endTime/60+":0"+ endTime%60);
                else
                    titlelist.add(startTime/60+":"+startTime%60+"-"+endTime/60+":"+ endTime%60);
            } else{
                if(((startTime+splitTime)%60<10||(startTime+splitTime)%60==0)&&(startTime%60<10||startTime%60==0))
                    titlelist.add(startTime/60+":0"+startTime%60+"-"+(startTime+splitTime)/60+":0"+(startTime+splitTime)%60);
                else if((startTime+splitTime)%60<10||(startTime+splitTime)%60==0)
                    titlelist.add(startTime/60+":"+startTime%60+"-"+(startTime+splitTime)/60+":0"+(startTime+splitTime)%60);
                else if(startTime%60<10||startTime%60==0)
                    titlelist.add(startTime/60+":0"+startTime%60+"-"+(startTime+splitTime)/60+":"+(startTime+splitTime)%60);
                else
                    titlelist.add(startTime/60+":"+startTime%60+"-"+(startTime+splitTime)/60+":"+(startTime+splitTime)%60);
                onTime.put("mix",startTime);
                onTime.put("max",startTime+splitTime);
                sqllist.add(onTime);
            }
        }
        formInfo.put("list",sqllist);
        formInfo.put("titlelist",titlelist);
        return formInfo;
    }

    @RequestMapping(value = "goodReportDays",method = RequestMethod.POST)
    @ResourceMethod(name = "菜品日分析", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage getReportAnalyseDays(@RequestBody Map formInfo) throws Exception {
        if(WebUtil.getSession("stores_id") != null){
            formInfo.put("stores_id",WebUtil.getSession("stores_id"));
        }else{
            formInfo.put("stores_id",WebUtil.getUser().getStores_id());
        }
        formInfo = getFormInfoMap(formInfo);
        List resultList = sqlDao.getRecordList("cds_good.getReportAnalyseDays",formInfo);
        resultList.add(resultList.get(0));
        resultList.remove(0);
        Map resultData = new HashMap();
        resultData.put("list",resultList);
        resultData.put("colums", formInfo.get("colums"));
        JsonMessage message = new JsonMessage(1,"success",resultData);
        return message;
    }

    @RequestMapping(value = "goodReportDaysExcel",method = RequestMethod.POST)
    @ResourceMethod(name = "菜品日分析导出", check = CHECK_LOGIN)
    @ResponseBody
    public void getReportAnalyseDaysExcel(String startday,String endday,String storesId) throws Exception {
        Map formInfo = new HashMap();
        if(WebUtil.getSession("stores_id") != null){
            formInfo.put("stores_id",WebUtil.getSession("stores_id"));
        }else{
            formInfo.put("stores_id",WebUtil.getUser().getStores_id());
        }
        formInfo.put("startday",startday);
        formInfo.put("endday",endday);
        formInfo.put("storesId",storesId);
        formInfo = getFormInfoExcel(formInfo);
        List resultList = sqlDao.getRecordList("cds_good.getReportAnalyseDays",formInfo);
        resultList.add(resultList.get(0));
        resultList.remove(0);
        //创建导出excel文件线程
        ExcelFileExport fileExcel = new ExcelFileExport(sqlDao, "null", "null", formInfo);
        fileExcel.setResultList(resultList);
        String fileName = fileExcel.getExpfileName();
        //给web前台输出文件名称
        responseText("{\"fileName\":\"" + fileName + "\"}");
        //本地生成文件名
        fileExcel.createFileDayFormat();
    }


    public Map getFormInfoMap(Map formInfo) throws Exception {
        String str = "[";
        String titleStr = "[[";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(sdf.parse(formInfo.get("startday").toString()));
        List list = new ArrayList();
        long day = DateUtil.getBetweenDay(sdf.parse(formInfo.get("startday").toString()),sdf.parse(formInfo.get("endday").toString()));
        for (int i = 0;i<=day;i++){
            if(i==day){
                titleStr += "{\"title\":\""+sdf.format(calendar.getTime())+"\",\"colspan\":2},";
                titleStr += "{\"title\":\"合计\",\"colspan\":2}],";
                str += "{\"field\":\"count"+i+"\",\"title\":\"份数\",\"width\":80,\"height\":50,\"align\":\"center\",\"rowspan\":1},";
                str += "{\"field\":\"price"+i+"\",\"title\":\"价格\",\"width\":80,\"height\":50,\"align\":\"center\",\"rowspan\":1},";
                str += "{\"field\":\"count\",\"title\":\"份数合计\",\"width\":80,\"height\":50,\"align\":\"center\",\"rowspan\":1},";
                str += "{\"field\":\"price\",\"title\":\"价格合计\",\"width\":80,\"height\":50,\"align\":\"center\",\"rowspan\":1}]]";
            }else{
                titleStr += "{\"title\":\""+sdf.format(calendar.getTime())+"\",\"colspan\":2},";
                str += "{\"field\":\"count"+i+"\",\"title\":\"份数\",\"width\":80,\"height\":50,\"align\":\"center\",\"rowspan\":1},";
                str += "{\"field\":\"price"+i+"\",\"title\":\"价格\",\"width\":80,\"height\":50,\"align\":\"center\",\"rowspan\":1},";
            }
            list.add(sdf.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        formInfo.put("list",list);
        formInfo.put("colums",titleStr+str);
        return formInfo;
    }


    public Map getFormInfoExcel(Map formInfo) throws Exception {
        Map excelMap = new HashMap();
        List<List<Map>> frozenColumns = new ArrayList<>();
        List<Map> frozenColumn1 = new ArrayList<>();
        List<Map> frozenColumn2 = new ArrayList<>();
        Map columMap = new HashMap();
        columMap.put("field","good_name");
        columMap.put("title","菜品名");
        columMap.put("width","180");
        columMap.put("rowspan","2");
        columMap.put("exp",true);
        frozenColumn1.add(columMap);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(sdf.parse(formInfo.get("startday").toString()));
        List list = new ArrayList();
        list.add(formInfo.get("startday").toString());
        long day = DateUtil.getBetweenDay(sdf.parse(formInfo.get("startday").toString()),sdf.parse(formInfo.get("endday").toString()));
        for (int i = 0;i<=day;i++){
            Map tempMap1 = new HashMap();
            tempMap1.put("title",sdf.format(calendar.getTime()));
            tempMap1.put("colspan","2");
            tempMap1.put("exp",true);
            frozenColumn1.add(tempMap1);
            Map tempMap2 = new HashMap();
            tempMap2.put("field","count"+i);
            tempMap2.put("title","份数");
            tempMap2.put("width","70");
            tempMap2.put("rowspan","1");
            tempMap2.put("exp",true);
            frozenColumn2.add(tempMap2);
            Map tempMap3 = new HashMap();
            tempMap3.put("field","price"+i);
            tempMap3.put("title","价格");
            tempMap3.put("width","80");
            tempMap3.put("rowspan","1");
            tempMap3.put("exp",true);
            frozenColumn2.add(tempMap3);
            if(i==day){
                Map tempMap6 = new HashMap();
                tempMap6.put("field","count");
                tempMap6.put("title","合计");
                tempMap6.put("width","100");
                tempMap6.put("colspan","2");
                tempMap6.put("exp",true);
                frozenColumn1.add(tempMap6);
                Map tempMap4 = new HashMap();
                tempMap4.put("field","count");
                tempMap4.put("title","份数合计");
                tempMap4.put("width","80");
                tempMap4.put("rowspan","1");
                tempMap4.put("exp",true);
                frozenColumn2.add(tempMap4);
                Map tempMap5 = new HashMap();
                tempMap5.put("field","price");
                tempMap5.put("title","价格合计");
                tempMap5.put("width","100");
                tempMap5.put("rowspan","1");
                tempMap5.put("exp",true);
                frozenColumn2.add(tempMap5);
            }else{
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                list.add(sdf.format(calendar.getTime()));
            }
        }
        formInfo.put("list",list);
//      下面数据用于导出excel时定义表头
        frozenColumns.add(frozenColumn1);
        frozenColumns.add(frozenColumn2);
        excelMap.put("frozenColumns",frozenColumns);
        List columns = new ArrayList();
        excelMap.put("columns",columns);
        formInfo.put("exportJsonParams",JSONObject.toJSON(excelMap).toString());
        return formInfo;
    }


}
