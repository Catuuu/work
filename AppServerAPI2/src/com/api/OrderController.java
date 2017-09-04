package com.api;

import com.alibaba.fastjson.JSONObject;
import com.framework.annotation.ResourceMethod;
import com.framework.controller.BasicController;
import com.framework.mapping.JsonMessage;
import com.framework.mapping.PageResult;
import com.framework.mapping.excel.ExcelFileExport;
import com.framework.mapping.system.*;
import com.framework.system.SystemConfig;
import com.framework.system.SystemConstant;
import com.framework.util.BeanUtil;
import com.framework.util.DateUtil;
import com.framework.util.StringUtil;
import com.framework.util.WebUtil;
import com.opensdk.dianwoda.factory.APIFactoryDianWoDa;
import com.opensdk.eleme.exception.ApiOpException;
import com.opensdk.eleme.exception.ApiSysException;
import com.opensdk.eleme.factory.APIFactoryEleme;
import com.opensdk.meituan.factory.APIFactoryMeituan;
import com.opensdk.shenhou.factory.APIFactoryShenHou;
import com.service.OrderService;
import com.service.PrintService;
import com.service.StoresService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.framework.annotation.CheckType.CHECK_LOGIN;


/**
 * Created by Administrator on 2017/2/4.订单查询接口
 */
@Controller
@RequestMapping("/orderSelectAPI")
public class OrderController extends BasicController {
    @Resource(name = "StoresService")

    protected StoresService storesService;

    @Resource(name = "PrintService")
    protected PrintService printService;

    @Resource(name = "OrderService")
    protected OrderService orderService;

    @RequestMapping(value = "orderlist", method = RequestMethod.POST)
    @ResourceMethod(name = "订单查询列表", check = CHECK_LOGIN)
    @ResponseBody
    public void orderlist(@RequestParam HashMap formInfo) throws Exception {
        if (formInfo.get("sl_date_begin") == null) {
            formInfo.put("sl_date_begin", DateUtil.getDate());
        }
        if (formInfo.get("sl_date_end") == null) {
            formInfo.put("sl_date_end", DateUtil.getDate());
        }

        if (formInfo.get("stores_id") == null) {
            formInfo.put("stores_id", WebUtil.getUser().getStores_id());
        }
        if (formInfo.get("sort") != null && formInfo.get("sort").toString().equals("fromin_no")) {
            formInfo.put("sort", "CAST(fromin_no as int)");
        }
        queryAndResponsePage("cds_order_info.getPageRecord", formInfo);
    }


    @RequestMapping(value = "orderlisthistory", method = RequestMethod.POST)
    @ResourceMethod(name = "历史订单查询列表", check = CHECK_LOGIN)
    @ResponseBody
    public void orderlisthistory(@RequestParam HashMap formInfo) throws Exception {
        if (formInfo.get("sl_date_begin") == null) {
            formInfo.put("sl_date_begin", DateUtil.dateToStr(DateUtil.addDay(new Date(), -SystemConstant.DATA_DAY)));
        }
        if (formInfo.get("sl_date_end") == null) {
            formInfo.put("sl_date_end", DateUtil.dateToStr(DateUtil.addDay(new Date(), -SystemConstant.DATA_DAY)));
        }

        if (formInfo.get("stores_id") == null) {
            formInfo.put("stores_id", WebUtil.getUser().getStores_id());
        }
        if (formInfo.get("sort") != null && formInfo.get("sort").toString().equals("fromin_no")) {
            formInfo.put("sort", "CAST(fromin_no as int)");
        }
        queryAndResponsePage("cds_order_info.getHistoryPageRecord", formInfo);
    }

    @RequestMapping(value = "printOrder", method = RequestMethod.POST)
    @ResourceMethod(name = "打印客户小票", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage printOrder(@RequestBody Map formInfo) throws Exception {
        if (formInfo.get("order_id") == null) {
            JsonMessage message = new JsonMessage(0, "当前订单不存在");
            return message;
        }
        String order_id = formInfo.get("order_id").toString();
        CdsOrderInfo cdsOrderInfo = new CdsOrderInfo();
        cdsOrderInfo.setOrder_id(order_id);
        cdsOrderInfo.addConditionField("order_id");
        cdsOrderInfo = sqlDao.getRecord(cdsOrderInfo);
        if (cdsOrderInfo == null) {
            JsonMessage message = new JsonMessage(0, "当前订单不存在");
            return message;
        }
        String stores_key = "stores_print_order_" + cdsOrderInfo.getStores_id();
        String orders = getRedisString(stores_key);

        if (orders == null) {
            addRedis(stores_key, order_id, 10 * 60);
        } else {
            updateRedis(stores_key, orders + "," + order_id, 10 * 60);
        }

        //printService.printClient(cdsOrderInfo,2);
        JsonMessage message = new JsonMessage(1);
        return message;
    }

    @RequestMapping(value = "sendShOrder", method = RequestMethod.POST)
    @ResourceMethod(name = "发送生活半径配送信息", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage sendShOrder(@RequestBody Map formInfo) throws Exception {
        if (formInfo.get("order_id") == null) {
            JsonMessage message = new JsonMessage(0, "当前订单不存在");
            return message;
        }
        String order_id = formInfo.get("order_id").toString();

        CdsOrderInfo cdsOrderInfo = new CdsOrderInfo();
        cdsOrderInfo.setOrder_id(order_id);
        cdsOrderInfo.addConditionField("order_id");
        cdsOrderInfo = sqlDao.getRecord(cdsOrderInfo);
        if (cdsOrderInfo == null) {
            JsonMessage message = new JsonMessage(0, "当前订单不存在");
        }

        cdsOrderInfo.setSend_id(StringUtil.getPrimaryOrderKey());
        CdsStores stores = storesService.GetStores(cdsOrderInfo.getStores_id());
        String result = APIFactoryShenHou.getPoiAPI().create(cdsOrderInfo, stores);
        cdsOrderInfo.setSend_name("生活半径");
        orderService.sendOrder(cdsOrderInfo, result, WebUtil.getUser());

        JsonMessage message = new JsonMessage(1);
        return message;
    }


    @RequestMapping(value = "sendDwdOrder", method = RequestMethod.POST)
    @ResourceMethod(name = "发送点我达配送信息", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage sendDwdOrder(@RequestBody Map formInfo) throws Exception {
        if (formInfo.get("order_id") == null) {
            JsonMessage message = new JsonMessage(0, "当前订单不存在");
            return message;
        }
        String order_id = formInfo.get("order_id").toString();

        CdsOrderInfo cdsOrderInfo = new CdsOrderInfo();
        cdsOrderInfo.setOrder_id(order_id);
        cdsOrderInfo.addConditionField("order_id");
        cdsOrderInfo = sqlDao.getRecord(cdsOrderInfo);
        if (cdsOrderInfo == null) {
            JsonMessage message = new JsonMessage(0, "当前订单不存在");
        }
        try {
            cdsOrderInfo.setSend_id(StringUtil.getPrimaryOrderKey());
            CdsStores stores = storesService.GetStores(cdsOrderInfo.getStores_id(), cdsOrderInfo.getBrand_id());
            String result = APIFactoryDianWoDa.getPoiAPI().order_send(cdsOrderInfo, stores);
            cdsOrderInfo.setSend_name("点我达");
            orderService.sendDwdOrder(cdsOrderInfo, result, WebUtil.getUser());

            JsonMessage message = new JsonMessage(1);
            return message;
        } catch (Exception e) {
            JsonMessage message = new JsonMessage(0, "点我达发送异常");
            return message;
        }

    }

    @RequestMapping(value = "cancelShsend", method = RequestMethod.POST)
    @ResourceMethod(name = "取消生活半径配送信息", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage cancelShsend(@RequestBody Map formInfo) throws Exception {
        JsonMessage message = null;
        if (formInfo.get("order_id") == null) {
            message = new JsonMessage(0, "当前订单不存在");
            return message;
        }
        String order_id = formInfo.get("order_id").toString();

        CdsOrderInfo cdsOrderInfo = new CdsOrderInfo();
        cdsOrderInfo.setOrder_id(order_id);
        cdsOrderInfo.addConditionField("order_id");
        cdsOrderInfo = sqlDao.getRecord(cdsOrderInfo);
        if (cdsOrderInfo == null) {
            message = new JsonMessage(0, "当前订单不存在");
            return message;
        }

        String result = APIFactoryShenHou.getPoiAPI().cancel(cdsOrderInfo);

        JSONObject jo = JSONObject.parseObject(result);
        if (jo.getString("code").equals("100000")) {
            orderService.sendCancel(cdsOrderInfo, WebUtil.getUser());
            message = new JsonMessage(1);
        } else {
            message = new JsonMessage(0, jo.getString("message"));

        }
        return message;
    }

    @RequestMapping(value = "cancelDwdsend", method = RequestMethod.POST)
    @ResourceMethod(name = "取消点我达配送信息", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage cancelDwdsend(@RequestBody Map formInfo) throws Exception {
        JsonMessage message = null;
        if (formInfo.get("order_id") == null) {
            message = new JsonMessage(0, "当前订单不存在");
            return message;
        }
        String order_id = formInfo.get("order_id").toString();

        CdsOrderInfo cdsOrderInfo = new CdsOrderInfo();
        cdsOrderInfo.setOrder_id(order_id);
        cdsOrderInfo.addConditionField("order_id");
        cdsOrderInfo = sqlDao.getRecord(cdsOrderInfo);
        if (cdsOrderInfo == null) {
            message = new JsonMessage(0, "当前订单不存在");
            return message;
        }

        // CdsStores stores =  storesService.GetStores(cdsOrderInfo.getStores_id());
        //String result = APIFactoryShenHou.getPoiAPI().cancel(cdsOrderInfo,stores);
        String result = APIFactoryDianWoDa.getPoiAPI().cancel(cdsOrderInfo);


        JSONObject jo = JSONObject.parseObject(result);
        if (jo.getString("success").equals("true")) {
            orderService.sendCancel(cdsOrderInfo, WebUtil.getUser());
            message = new JsonMessage(1);
        } else {
            message = new JsonMessage(0, jo.getString("message"));
        }
        return message;
    }

    @RequestMapping(value = "viewsendlog", method = RequestMethod.POST)
    @ResourceMethod(name = "查看配送发送日志", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage viewsendlog(String order_id) {
        CdsOrderSendLog log = new CdsOrderSendLog();
        log.setOrder_id(order_id);
        log.addConditionField("order_id");
        List list = sqlDao.getRecordList(log);
        return new JsonMessage(1, "配送日志", list);
    }


    @RequestMapping(value = "vieworderlog", method = RequestMethod.POST)
    @ResourceMethod(name = "查看订单状态日志", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage vieworderlog(String order_id) {
        CdsOrderLog log = new CdsOrderLog();
        log.setOrder_id(order_id);
        log.addConditionField("order_id");
        log.resetOrderStr("opt_time");
        List list = sqlDao.getRecordList(log);
        return new JsonMessage(1, "订单日志", list);
    }


    @RequestMapping(value = "viewhistorysendlog", method = RequestMethod.POST)
    @ResourceMethod(name = "查看配送发送日志", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage viewhistorysendlog(String order_id, String create_date) {

        Map params = new HashMap();
        params.put("order_id", order_id);
        params.put("cday", create_date.substring(0, 10));
        List list = sqlDao.getRecordList("cds_order_info.viewhistorysendlog", params);
        return new JsonMessage(1, "配送日志", list);
    }


    @RequestMapping(value = "viewhistoryorderlog", method = RequestMethod.POST)
    @ResourceMethod(name = "查看订单状态日志", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage viewhistoryorderlog(String order_id, String create_date) {
        Map params = new HashMap();
        params.put("order_id", order_id);
        params.put("cday", create_date.substring(0, 10));
        List list = sqlDao.getRecordList("cds_order_info.viewhistoryorderlog", params);
        return new JsonMessage(1, "订单日志", list);
    }


    @RequestMapping(value = "cancelorder", method = RequestMethod.POST)
    @ResourceMethod(name = "取消订单", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage cancelorder(CdsOrderInfo cdsOrderInfo) {
        CdsOrderInfo order = new CdsOrderInfo();
        order.setOrder_id(cdsOrderInfo.getOrder_id());
        order.addConditionField("order_id");
        order.removeParamFields();
        order = sqlDao.getRecord(cdsOrderInfo);
        order.setCancel_type(cdsOrderInfo.getCancel_type());
        order.setCancel_remark(cdsOrderInfo.getCancel_remark());
        if (order.getFromin().equals("饿了么")) {
            try {
                String result = APIFactoryEleme.getOrderAPI().orderCancel(SystemConfig.GetSystemParamEleme(), order.getOrder_desc(), order.getCancel_type());
                JSONObject jo = JSONObject.parseObject(result);
                if (jo.getString("message").equals("ok")) {
                    orderService.cancelorder(order, WebUtil.getUser());//更新订单状态
                    printService.printCancel(order);//打印取消订单小票
                }
            } catch (ApiOpException e) {
                e.printStackTrace();
            } catch (ApiSysException e) {
                e.printStackTrace();
            }
        } else if (order.getFromin().equals("美团")) {
            try {
                APIFactoryMeituan.getOrderAPI().orderCancel(SystemConfig.GetSystemParamMeituan(), Long.parseLong(order.getOrder_desc()), order.getCancel_remark(), order.getCancel_type());
            } catch (com.opensdk.meituan.exception.ApiOpException e) {
                e.printStackTrace();
            } catch (com.opensdk.meituan.exception.ApiSysException e) {
                e.printStackTrace();
            }
        }
        CdsBackMoney bm = new CdsBackMoney();
        bm.setOrder_id(order.getOrder_id());
        bm.addConditionFields("order_id");
        bm = sqlDao.getRecord(bm);
        if (bm == null) {
            //系统取消--保存信息到退款表
            CdsBackMoney cdsBackMoney = new CdsBackMoney();
            cdsBackMoney.setDo_status(1);
            cdsBackMoney.setBp_id(StringUtil.getPrimaryOrderKey());
            cdsBackMoney.setOrder_id(order.getOrder_id());
            cdsBackMoney.setMember_id(order.getMember_id());
            cdsBackMoney.setBp_number(StringUtil.getPrimaryOrderKey());
            cdsBackMoney.setBack_status(6);//员工取消
            cdsBackMoney.setBp_price(order.getTotal_price());
            cdsBackMoney.setOpt_type("员工");
            cdsBackMoney.setFromin(order.getFromin());
            cdsBackMoney.setBp_remark(order.getCancel_type());
            cdsBackMoney.setBp_check_operator_id(WebUtil.getUser().getId());
            cdsBackMoney.setBp_check_remark(order.getCancel_remark());
            cdsBackMoney.setBp_operator(WebUtil.getUser().getUser_nicename());
            cdsBackMoney.setBp_time(new Date());
            cdsBackMoney.setBp_check_time(new Date());
            cdsBackMoney.setStores_id(order.getStores_id());
            cdsBackMoney.setSend_type("其他原因");
            cdsBackMoney.addParamFields();
            sqlDao.insertRecord(cdsBackMoney);
        }

        return new JsonMessage(1);
    }


    @RequestMapping(value = "order_count", method = RequestMethod.POST)
    @ResourceMethod(name = "订单日统计", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage orderDayCount(@RequestBody Map formInfo) throws Exception {
        if(WebUtil.getSession("stores_id") != null){
            formInfo.put("stores_id",WebUtil.getSession("stores_id"));
        }else{
            formInfo.put("stores_id",WebUtil.getUser().getStores_id());
        }
        String datatype = formInfo.get("datatype").toString();
        List<Map> resultList = sqlDao.getRecordList("cds_order_info.orderCountDay", formInfo);
        List<Map> brand = sqlDao.getRecordList("cds_order_info.orderCountBrand",formInfo);

        List<String> time_names = new <String>ArrayList();
        List<BigDecimal> resutdata = new <BigDecimal>ArrayList(); //合计
        List<BigDecimal> resutdata1 = new <BigDecimal>ArrayList(); //饿了么
        List<BigDecimal> resutdata2 = new <BigDecimal>ArrayList(); //美团
        List<BigDecimal> resutdata3 = new <BigDecimal>ArrayList(); //微信
        List<BigDecimal> resutdata4 = new <BigDecimal>ArrayList(); //百度外卖

        List goods = new ArrayList();
        for (Map goodMap : resultList) {
            String day = goodMap.get("timeDay").toString();
            time_names.add(day);
            if ("2".equals(datatype)) {
                if ("0.00".equals(goodMap.get("sum3").toString())) resutdata.add(new BigDecimal(0));
                else resutdata.add(new BigDecimal(goodMap.get("sum3").toString()).divide(new BigDecimal(goodMap.get("sum1").toString()), 2, BigDecimal.ROUND_HALF_UP));
                if ("0.00".equals(goodMap.get("elm3").toString())) resutdata1.add(new BigDecimal(0));
                else resutdata1.add(new BigDecimal(goodMap.get("elm3").toString()).divide(new BigDecimal(goodMap.get("elm1").toString()), 2, BigDecimal.ROUND_HALF_UP));
                if ("0.00".equals(goodMap.get("mt3").toString())) resutdata2.add(new BigDecimal(0));
                else resutdata2.add(new BigDecimal(goodMap.get("mt3").toString()).divide(new BigDecimal(goodMap.get("mt1").toString()), 2, BigDecimal.ROUND_HALF_UP));
                if ("0.00".equals(goodMap.get("wx3").toString())) resutdata3.add(new BigDecimal(0));
                else resutdata3.add(new BigDecimal(goodMap.get("wx3").toString()).divide(new BigDecimal(goodMap.get("wx1").toString()), 2, BigDecimal.ROUND_HALF_UP));
                if ("0.00".equals(goodMap.get("bdwm3").toString())) resutdata4.add(new BigDecimal(0));
                else resutdata4.add(new BigDecimal(goodMap.get("bdwm3").toString()).divide(new BigDecimal(goodMap.get("bdwm1").toString()), 2, BigDecimal.ROUND_HALF_UP));
            } else {
                resutdata.add(new BigDecimal(goodMap.get("sum" + datatype).toString()));
                resutdata1.add(new BigDecimal(goodMap.get("elm" + datatype).toString()));
                resutdata2.add(new BigDecimal(goodMap.get("mt" + datatype).toString()));
                resutdata3.add(new BigDecimal(goodMap.get("wx" + datatype).toString()));
                resutdata4.add(new BigDecimal(goodMap.get("bdwm" + datatype).toString()));
            }
        }

        Map good = new HashMap();
        good.put("name", "总计");
        good.put("data", resutdata);
        goods.add(good);

        Map good1 = new HashMap();
        good1.put("name", "饿了么");
        good1.put("data", resutdata1);
        goods.add(good1);

        Map good2 = new HashMap();
        good2.put("name", "美团");
        good2.put("data", resutdata2);
        goods.add(good2);

        Map good3 = new HashMap();
        good3.put("name", "百度外卖");
        good3.put("data", resutdata3);
        goods.add(good3);

        Map good4 = new HashMap();
        good4.put("name", "微信");
        good4.put("data", resutdata4);
        goods.add(good4);

        String title = "新用户";
        if (datatype.equals("1")) {
            title = "订单数";
        } else if (datatype.equals("2")) {
            title = "客单价";
        } else if (datatype.equals("3")) {
            title = "销售额";
        }

        Map resultData = new HashMap();
        resultData.put("reporttitle", title);
        resultData.put("list", resultList);
        resultData.put("time_names", time_names);
        resultData.put("goods", goods);
        resultData.put("brand", brand);
        JsonMessage message = new JsonMessage(1, "success", resultData);
        return message;
    }


    @RequestMapping(value = "orderOntimeCount", method = RequestMethod.POST)
    @ResourceMethod(name = "订单准时率统计", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage orderTimeCount(@RequestBody Map formInfo) throws Exception {
        if(WebUtil.getSession("stores_id") != null) formInfo.put("stores_id",WebUtil.getSession("stores_id"));
        else formInfo.put("stores_id",WebUtil.getUser().getStores_id());
        List<Map> list =  sqlDao.getRecordList("cds_stores.orderOnTimeStores", formInfo);
        formInfo.put("list",list);
        List<Map> resultList = new ArrayList<>();
        //orderType:1为常规订单,2为预送订单
        if("1".equals(formInfo.get("orderStatus").toString()))resultList = sqlDao.getRecordList("cds_order_info.orderOnTimeCount1", formInfo);
        else if("2".equals(formInfo.get("orderStatus").toString()))resultList = sqlDao.getRecordList("cds_order_info.orderOnTimeCount2", formInfo);

        List goods = new ArrayList();
        BigDecimal[][] showMapList = new BigDecimal[list.size()+1][resultList.size()];

        String key = "";
        if("1".equals(formInfo.get("datatype").toString()))key = "dwd";
        if("2".equals(formInfo.get("datatype").toString()))key = "mt";
        if("3".equals(formInfo.get("datatype").toString()))key = "elm";
        StringBuffer titleStr = new StringBuffer("[[");
        StringBuffer str =  new StringBuffer("[");
        for(int i=0;i<list.size();i++){
            if(i==list.size()-1){
                titleStr.append("{\"title\":\""+list.get(i).get("storesName")+"\",\"colspan\":3},");
                titleStr.append("{\"title\":\"店铺合计\",\"colspan\":3}],");
                str.append("{\"field\":\"dwdOntime"+i+"\",\"title\":\"点我达\",\"width\":80,\"height\":50,\"align\":\"center\",\"rowspan\":1},");
                str.append("{\"field\":\"mtOntime"+i+"\",\"title\":\"美团\",\"width\":80,\"height\":50,\"align\":\"center\",\"rowspan\":1},");
                str.append("{\"field\":\"elmOntime"+i+"\",\"title\":\"饿了么\",\"width\":80,\"height\":50,\"align\":\"center\",\"rowspan\":1},");
                str.append("{\"field\":\"dwdOntimeCount\",\"title\":\"点我达合计\",\"width\":80,\"height\":50,\"align\":\"center\",\"rowspan\":1},");
                str.append("{\"field\":\"mtOntimeCount\",\"title\":\"美团合计\",\"width\":80,\"height\":50,\"align\":\"center\",\"rowspan\":1},");
                str.append("{\"field\":\"elmOntimeCount\",\"title\":\"饿了么合计\",\"width\":80,\"height\":50,\"align\":\"center\",\"rowspan\":1}]]");
            }else{
                titleStr.append("{\"title\":\""+list.get(i).get("storesName")+"\",\"colspan\":3},");
                str.append("{\"field\":\"dwdOntime"+i+"\",\"title\":\"点我达\",\"width\":80,\"height\":50,\"align\":\"center\",\"rowspan\":1},");
                str.append("{\"field\":\"mtOntime"+i+"\",\"title\":\"美团\",\"width\":80,\"height\":50,\"align\":\"center\",\"rowspan\":1},");
                str.append("{\"field\":\"elmOntime"+i+"\",\"title\":\"饿了么\",\"width\":80,\"height\":50,\"align\":\"center\",\"rowspan\":1},");
            }
        }
        int i = 0;
        for(Map goodMap : resultList){
            for(int x=0;x<list.size();x++){
                if(0==Integer.parseInt(goodMap.get(key+"AllOrder"+x).toString()))showMapList[x][i] = new BigDecimal(0);
                else showMapList[x][i] = new BigDecimal(goodMap.get(key+"NiceOrder"+x).toString()).divide(new BigDecimal(goodMap.get(key+"AllOrder"+x).toString()), 5, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
                if(x == list.size()-1)showMapList[x+1][i]=new BigDecimal(goodMap.get(key+"NiceOrder").toString()).divide(new BigDecimal(goodMap.get(key+"AllOrder").toString()), 5, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));

                if(0==Integer.parseInt(goodMap.get("dwdAllOrder"+x).toString()))goodMap.put("dwdOntime"+x,0);
                else goodMap.put("dwdOntime"+x,new BigDecimal(goodMap.get("dwdNiceOrder"+x).toString()).divide(
                        new BigDecimal(goodMap.get("dwdAllOrder"+x).toString()), 5, BigDecimal.ROUND_HALF_UP).multiply(
                                new BigDecimal(100)).doubleValue()+"%");
                 if(0==Integer.parseInt(goodMap.get("mtAllOrder"+x).toString()))goodMap.put("mtOntime"+x,0);
                else goodMap.put("mtOntime"+x,new BigDecimal(goodMap.get("mtNiceOrder"+x).toString()).divide(
                        new BigDecimal(goodMap.get("mtAllOrder"+x).toString()), 5, BigDecimal.ROUND_HALF_UP).multiply(
                                new BigDecimal(100)).doubleValue()+"%");
                if(0==Integer.parseInt(goodMap.get("elmAllOrder"+x).toString()))goodMap.put("elmOntime"+x,0);
                else goodMap.put("elmOntime"+x,new BigDecimal(goodMap.get("elmNiceOrder"+x).toString()).divide(
                        new BigDecimal(goodMap.get("elmAllOrder"+x).toString()), 5, BigDecimal.ROUND_HALF_UP).multiply(
                                new BigDecimal(100)).doubleValue()+"%");


                if(x==0){
                    if(0==Integer.parseInt(goodMap.get("dwdAllOrder").toString()))goodMap.put("dwdOntimeCount",0);
                    else goodMap.put("dwdOntimeCount",new BigDecimal(goodMap.get("dwdNiceOrder").toString()).divide(
                            new BigDecimal(goodMap.get("dwdAllOrder").toString()), 5, BigDecimal.ROUND_HALF_UP).multiply(
                                    new BigDecimal(100)).doubleValue()+"%");
                    if(0==Integer.parseInt(goodMap.get("mtAllOrder").toString()))goodMap.put("mtOntimeCount",0);
                    else goodMap.put("mtOntimeCount",new BigDecimal(goodMap.get("mtNiceOrder").toString()).divide(
                            new BigDecimal(goodMap.get("mtAllOrder").toString()), 5, BigDecimal.ROUND_HALF_UP).multiply(
                                    new BigDecimal(100)).doubleValue()+"%");
                    if(0==Integer.parseInt(goodMap.get("elmAllOrder").toString()))goodMap.put("elmOntimeCount",0);
                    else goodMap.put("elmOntimeCount",new BigDecimal(goodMap.get("elmNiceOrder").toString()).divide(
                            new BigDecimal(goodMap.get("elmAllOrder").toString()), 5, BigDecimal.ROUND_HALF_UP).multiply(
                                    new BigDecimal(100)).doubleValue()+"%");
                }
            }
            i++;
        }
        for(int y=0;y<list.size();y++) {
            Map good = new HashMap();
            good.put("name", list.get(y).get("storesName"));
            good.put("data", showMapList[y]);
            if(y>3) good.put("visible",false);

            goods.add(good);
            if (y == list.size() - 1) {
                good = new HashMap();
                good.put("name", "合计");
                good.put("data", showMapList[y+1]);
                goods.add(good);
            }
        }
        Map resultData = new HashMap();
        resultData.put("list", resultList);
        resultData.put("goods", goods);
        resultData.put("titleStr", titleStr.append(str));
        JsonMessage message = new JsonMessage(1, "success", resultData);
        return message;
    }

    @RequestMapping(value = "orderOntimeSelect", method = RequestMethod.POST)
    @ResourceMethod(name = "订单准时率详细", check = CHECK_LOGIN)
    @ResponseBody
    public void orderOntimeSelect(@RequestParam HashMap formInfo) throws Exception {
        String send_name = "";
        String field = formInfo.get("field").toString();
        if("d".equals(field.substring(0,1))){
            field = field.replace("dwd","");
            send_name = "点我达";
        } else if("e".equals(field.substring(0,1))){
            field = field.replace("elm","");
            send_name = "饿了么";
        } else if("m".equals(field.substring(0,1))){
            field = field.replace("mt","");
            send_name = "美团";
        }
        Object check = field.replace("Ontime","");
        List<Map> storesList =  sqlDao.getRecordList("cds_stores.orderOnTimeStores", null);
        if(!"Count".equals(check)){
            formInfo.put("stores_id",storesList.get(Integer.parseInt(check.toString())).get("storesId"));
        }
        formInfo.put("send_name",send_name);
        if("1".equals(formInfo.get("orderStatus").toString()))queryAndResponsePage("cds_order_info.orderOnTimeCheck1",formInfo);
        else if("2".equals(formInfo.get("orderStatus").toString()))queryAndResponsePage("cds_order_info.orderOnTimeCheck2",formInfo);

    }

    @RequestMapping(value = "orderRiderCount", method = RequestMethod.POST)
    @ResourceMethod(name = "骑手统计", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage orderRiderCount(@RequestBody Map formInfo) throws Exception {
        if(WebUtil.getSession("stores_id") != null) formInfo.put("stores_id",WebUtil.getSession("stores_id"));
        else formInfo.put("stores_id",WebUtil.getUser().getStores_id());
        formInfo = getFormInfoMap(formInfo);
        List title = (List)formInfo.get("list");
        List<Map> resultList = new ArrayList<>();
        //orderStatus:1为常规订单,2为预送订单
        if("1".equals(formInfo.get("orderStatus").toString()))resultList = sqlDao.getRecordList("cds_order_info.orderRiderCount1", formInfo);
        else if("2".equals(formInfo.get("orderStatus").toString()))resultList = sqlDao.getRecordList("cds_order_info.orderRiderCount2", formInfo);
        int i=0;
        for(Map result : resultList){
            for(int x = 0;x<title.size();x++){
                if(Integer.parseInt(result.get("sumTime"+x).toString())==0)result.put("avgTime"+x,0);
                else result.put("avgTime"+x,new BigDecimal(result.get("sumTime"+x).toString()).divide(new BigDecimal(result.get("orderCount"+x).toString()),2, BigDecimal.ROUND_HALF_UP));
                if(x==0){
                    if(Integer.parseInt(result.get("sumTime").toString())==0)result.put("avgTime",0);
                    else result.put("avgTime",new BigDecimal(result.get("sumTime").toString()).divide(new BigDecimal(result.get("orderCount").toString()),2, BigDecimal.ROUND_HALF_UP));
                }
            }
            i++;
        }
        Map resultData = new HashMap();
        resultData.put("list", resultList);
        resultData.put("titleStr", formInfo.get("colums"));
        JsonMessage message = new JsonMessage(1, "success", resultData);
        return message;
    }

    @RequestMapping(value = "orderRiderSelect", method = RequestMethod.POST)
    @ResourceMethod(name = "骑手详细", check = CHECK_LOGIN)
    @ResponseBody
    public void orderRiderSelect(@RequestParam HashMap formInfo) throws Exception {
        if("1".equals(formInfo.get("orderStatus").toString()))queryAndResponsePage("cds_order_info.orderRiderCheck1",formInfo);
        else if("2".equals(formInfo.get("orderStatus").toString()))queryAndResponsePage("cds_order_info.orderRiderCheck2",formInfo);
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
                str += "{\"field\":\"orderCount"+i+"\",\"title\":\"跑单数\",\"width\":80,\"height\":50,\"align\":\"center\",\"rowspan\":1},";
                str += "{\"field\":\"avgTime"+i+"\",\"title\":\"平均送达时间\",\"width\":80,\"height\":50,\"align\":\"center\",\"rowspan\":1},";
                str += "{\"field\":\"orderCount\",\"title\":\"跑单数\",\"width\":80,\"height\":50,\"align\":\"center\",\"rowspan\":1},";
                str += "{\"field\":\"avgTime\",\"title\":\"平均送达时间\",\"width\":80,\"height\":50,\"align\":\"center\",\"rowspan\":1}]]";
            }else{
                titleStr += "{\"title\":\""+sdf.format(calendar.getTime())+"\",\"colspan\":2},";
                str += "{\"field\":\"orderCount"+i+"\",\"title\":\"跑单数\",\"width\":80,\"height\":50,\"align\":\"center\",\"rowspan\":1},";
                str += "{\"field\":\"avgTime"+i+"\",\"title\":\"平均送达时间\",\"width\":80,\"height\":50,\"align\":\"center\",\"rowspan\":1},";
            }
            list.add(sdf.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        formInfo.put("list",list);
        formInfo.put("colums",titleStr+str);
        return formInfo;
    }

    @RequestMapping(value = "orderRiderCountExcel", method = RequestMethod.POST)
    @ResourceMethod(name = "骑手统计导出", check = CHECK_LOGIN)
    @ResponseBody
    public void orderRiderCountExcel(String startday,String endday,String orderStatus) throws Exception {
        Map formInfo = new HashMap();
        formInfo.put("startday",startday);
        formInfo.put("endday",endday);
        if(WebUtil.getSession("stores_id") != null) formInfo.put("stores_id",WebUtil.getSession("stores_id"));
        else formInfo.put("stores_id",WebUtil.getUser().getStores_id());
        formInfo = getFormInfoRiderExcel(formInfo);
        List title = (List)formInfo.get("list");
        List<Map> resultList = new ArrayList<>();
        //orderStatus:1为常规订单,2为预送订单
        if("1".equals(orderStatus))resultList = sqlDao.getRecordList("cds_order_info.orderRiderCount1", formInfo);
        else if("2".equals(orderStatus))resultList = sqlDao.getRecordList("cds_order_info.orderRiderCount2", formInfo);
        int i=0;
        for(Map result : resultList){
            for(int x = 0;x<title.size();x++){
                if(Integer.parseInt(result.get("sumTime"+x).toString())==0)result.put("avgTime"+x,0);
                else result.put("avgTime"+x,new BigDecimal(result.get("sumTime"+x).toString()).divide(new BigDecimal(result.get("orderCount"+x).toString()),2, BigDecimal.ROUND_HALF_UP));
                if(x==0){
                    if(Integer.parseInt(result.get("sumTime").toString())==0)result.put("avgTime",0);
                    else result.put("avgTime",new BigDecimal(result.get("sumTime").toString()).divide(new BigDecimal(result.get("orderCount").toString()),2, BigDecimal.ROUND_HALF_UP));
                }
            }
            i++;
        }
        //创建导出excel文件线程
        ExcelFileExport fileExcel = new ExcelFileExport(sqlDao, "null", "null", formInfo);
        fileExcel.setResultList(resultList);
        fileExcel.setNumberFormat("#,##0");
        String fileName = fileExcel.getExpfileName();
        //给web前台输出文件名称
        responseText("{\"fileName\":\"" + fileName + "\"}");
        //本地生成文件名
        fileExcel.createFileDay();
    }

    public Map getFormInfoRiderExcel(Map formInfo) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(sdf.parse(formInfo.get("startday").toString()));
        List list = new ArrayList();
        long day = DateUtil.getBetweenDay(sdf.parse(formInfo.get("startday").toString()),sdf.parse(formInfo.get("endday").toString()));
        Map excelMap = new HashMap();
        List<List<Map>> frozenColumns = new ArrayList<>();
        List<Map> frozenColumn1 = new ArrayList<>();
        List<Map> frozenColumn2 = new ArrayList<>();
        Map columMap = new HashMap();
        columMap.put("field","storesName");
        columMap.put("title","店铺名");
        columMap.put("rowspan","2");
        columMap.put("width","80");
        columMap.put("exp",true);
        frozenColumn1.add(columMap);
        columMap = new HashMap();
        columMap.put("field","taskOrderName");
        columMap.put("title","骑手姓名");
        columMap.put("rowspan","2");
        columMap.put("width","80");
        columMap.put("exp",true);
        frozenColumn1.add(columMap);
        columMap = new HashMap();
        columMap.put("field","taskOrderPhone");
        columMap.put("title","手机号");
        columMap.put("rowspan","2");
        columMap.put("width","140");
        columMap.put("exp",true);
        frozenColumn1.add(columMap);
        for (int i = 0;i<=day;i++){
            if(i==day){
                Map temp1 = new HashMap();
                temp1.put("title",sdf.format(calendar.getTime()));
                temp1.put("colspan","2");
                temp1.put("exp",true);
                frozenColumn1.add(temp1);
                temp1 = new HashMap();
                temp1.put("title","合计");
                temp1.put("colspan","2");
                temp1.put("width","80");
                temp1.put("exp",true);
                frozenColumn1.add(temp1);
                Map temp2 = new HashMap();
                temp2.put("field","orderCount"+i);
                temp2.put("title","跑单数");
                temp2.put("rowspan","1");
                temp2.put("width","80");
                temp2.put("align","center");
                temp2.put("exp",true);
                frozenColumn2.add(temp2);
                temp2 = new HashMap();
                temp2.put("field","avgTime"+i);
                temp2.put("title","平均送达时间");
                temp2.put("rowspan","1");
                temp2.put("width","140");
                temp2.put("align","center");
                temp2.put("exp",true);
                frozenColumn2.add(temp2);
                temp2 = new HashMap();
                temp2.put("field","orderCount");
                temp2.put("title","跑单数");
                temp2.put("rowspan","1");
                temp2.put("width","80");
                temp2.put("align","center");
                temp2.put("exp",true);
                frozenColumn2.add(temp2);
                temp2 = new HashMap();
                temp2.put("field","avgTime");
                temp2.put("title","平均送达时间");
                temp2.put("rowspan","1");
                temp2.put("width","140");
                temp2.put("align","center");
                temp2.put("exp",true);
                frozenColumn2.add(temp2);
            }else{
                Map temp1 = new HashMap();
                temp1.put("title",sdf.format(calendar.getTime()));
                temp1.put("colspan","2");
                temp1.put("exp",true);
                frozenColumn1.add(temp1);
                Map temp2 = new HashMap();
                temp2.put("field","orderCount"+i);
                temp2.put("title","跑单数");
                temp2.put("rowspan","1");
                temp2.put("width","80");
                temp2.put("align","center");
                temp2.put("exp",true);
                frozenColumn2.add(temp2);
                temp2 = new HashMap();
                temp2.put("field","avgTime"+i);
                temp2.put("title","平均送达时间");
                temp2.put("rowspan","1");
                temp2.put("width","140");
                temp2.put("align","center");
                temp2.put("exp",true);
                frozenColumn2.add(temp2);
            }
            list.add(sdf.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        formInfo.put("list",list);
        frozenColumns.add(frozenColumn1);
        frozenColumns.add(frozenColumn2);
        excelMap.put("frozenColumns",frozenColumns);
        List columns = new ArrayList();
        excelMap.put("columns",columns);
        formInfo.put("exportJsonParams",JSONObject.toJSON(excelMap).toString());
        return formInfo;
    }


    @RequestMapping(value = "orderOntimeExcel", method = RequestMethod.POST)
    @ResourceMethod(name = "准时率导出", check = CHECK_LOGIN)
    @ResponseBody
    public void orderOntimeExcel(String startday,String endday,String orderStatus) throws Exception {
        Map formInfo = new HashMap();
        formInfo.put("startday",startday);
        formInfo.put("endday",endday);
        formInfo.put("orderStatus",orderStatus);
        formInfo.put("stores_id",WebUtil.getUser().getStores_id());
        if(WebUtil.getSession("stores_id") != null)
            formInfo.put("stores_id",WebUtil.getSession("stores_id"));
        List<Map> list =  sqlDao.getRecordList("cds_stores.orderOnTimeStores", formInfo);
        formInfo.put("list",list);
        formInfo = getFormInfoOntimeExcel(formInfo);
        List<Map> resultList = new ArrayList<>();
        //orderType:1为常规订单,2为预送订单
        if("1".equals(formInfo.get("orderStatus").toString()))resultList = sqlDao.getRecordList("cds_order_info.orderOnTimeCount1", formInfo);
        else if("2".equals(formInfo.get("orderStatus").toString()))resultList = sqlDao.getRecordList("cds_order_info.orderOnTimeCount2", formInfo);
        int i = 0;
        for(Map goodMap : resultList){
            for(int x=0;x<list.size();x++){
                if(0==Integer.parseInt(goodMap.get("dwdAllOrder"+x).toString()))goodMap.put("dwdOntime"+x,0);
                else goodMap.put("dwdOntime"+x,new BigDecimal(goodMap.get("dwdNiceOrder"+x).toString()).divide(
                        new BigDecimal(goodMap.get("dwdAllOrder"+x).toString()), 5, BigDecimal.ROUND_HALF_UP).multiply(
                        new BigDecimal(100)).doubleValue());
                if(0==Integer.parseInt(goodMap.get("mtAllOrder"+x).toString()))goodMap.put("mtOntime"+x,0);
                else goodMap.put("mtOntime"+x,new BigDecimal(goodMap.get("mtNiceOrder"+x).toString()).divide(
                        new BigDecimal(goodMap.get("mtAllOrder"+x).toString()), 5, BigDecimal.ROUND_HALF_UP).multiply(
                        new BigDecimal(100)).doubleValue());
                if(0==Integer.parseInt(goodMap.get("elmAllOrder"+x).toString()))goodMap.put("elmOntime"+x,0);
                else goodMap.put("elmOntime"+x,new BigDecimal(goodMap.get("elmNiceOrder"+x).toString()).divide(
                        new BigDecimal(goodMap.get("elmAllOrder"+x).toString()), 5, BigDecimal.ROUND_HALF_UP).multiply(
                        new BigDecimal(100)).doubleValue());


                if(x==0){
                    if(0==Integer.parseInt(goodMap.get("dwdAllOrder").toString()))goodMap.put("dwdOntimeCount",0);
                    else goodMap.put("dwdOntimeCount",new BigDecimal(goodMap.get("dwdNiceOrder").toString()).divide(
                            new BigDecimal(goodMap.get("dwdAllOrder").toString()), 5, BigDecimal.ROUND_HALF_UP).multiply(
                            new BigDecimal(100)).doubleValue());
                    if(0==Integer.parseInt(goodMap.get("mtAllOrder").toString()))goodMap.put("mtOntimeCount",0);
                    else goodMap.put("mtOntimeCount",new BigDecimal(goodMap.get("mtNiceOrder").toString()).divide(
                            new BigDecimal(goodMap.get("mtAllOrder").toString()), 5, BigDecimal.ROUND_HALF_UP).multiply(
                            new BigDecimal(100)).doubleValue());
                    if(0==Integer.parseInt(goodMap.get("elmAllOrder").toString()))goodMap.put("elmOntimeCount",0);
                    else goodMap.put("elmOntimeCount",new BigDecimal(goodMap.get("elmNiceOrder").toString()).divide(
                            new BigDecimal(goodMap.get("elmAllOrder").toString()), 5, BigDecimal.ROUND_HALF_UP).multiply(
                            new BigDecimal(100)).doubleValue());
                }
            }
            i++;
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

    public Map getFormInfoOntimeExcel(Map formInfo) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(sdf.parse(formInfo.get("startday").toString()));
        Map excelMap = new HashMap();
        List<List<Map>> frozenColumns = new ArrayList<>();
        List<Map> frozenColumn1 = new ArrayList<>();
        List<Map> frozenColumn2 = new ArrayList<>();
        Map columMap = new HashMap();
        columMap.put("field","create_date");
        columMap.put("title","时间");
        columMap.put("rowspan","2");
        columMap.put("width","80");
        columMap.put("exp",true);
        frozenColumn1.add(columMap);
        List<Map> list = (List)formInfo.get("list");
        for (int i = 0;i<list.size();i++){
                Map temp1 = new HashMap();
                temp1.put("title",list.get(i).get("storesName"));
                temp1.put("colspan","3");
                temp1.put("exp",true);
                frozenColumn1.add(temp1);
                Map temp2 = new HashMap();
                temp2.put("field","dwdOntime"+i);
                temp2.put("title","点我达(%)");
                temp2.put("rowspan","1");
                temp2.put("width","80");
                temp2.put("exp",true);
                frozenColumn2.add(temp2);
                temp2 = new HashMap();
                temp2.put("field","mtOntime"+i);
                temp2.put("title","美团(%)");
                temp2.put("rowspan","1");
                temp2.put("width","80");
                temp2.put("exp",true);
                frozenColumn2.add(temp2);
                temp2 = new HashMap();
                temp2.put("field","elmOntime"+i);
                temp2.put("title","饿了么(%)");
                temp2.put("rowspan","1");
                temp2.put("width","80");
                temp2.put("exp",true);
                frozenColumn2.add(temp2);
            if(i==list.size()-1){
                temp1 = new HashMap();
                temp1.put("title","店铺合计");
                temp1.put("colspan","3");
                temp1.put("exp",true);
                frozenColumn1.add(temp1);
                temp2 = new HashMap();
                temp2.put("field","dwdOntimeCount");
                temp2.put("title","点我达(%)");
                temp2.put("rowspan","1");
                temp2.put("width","80");
                temp2.put("exp",true);
                frozenColumn2.add(temp2);
                temp2 = new HashMap();
                temp2.put("field","mtOntimeCount");
                temp2.put("title","美团(%)");
                temp2.put("rowspan","1");
                temp2.put("width","80");
                temp2.put("exp",true);
                frozenColumn2.add(temp2);
                temp2 = new HashMap();
                temp2.put("field","elmOntimeCount");
                temp2.put("title","饿了么(%)");
                temp2.put("rowspan","1");
                temp2.put("width","80");
                temp2.put("exp",true);
                frozenColumn2.add(temp2);
            }
        }
        frozenColumns.add(frozenColumn1);
        frozenColumns.add(frozenColumn2);
        excelMap.put("frozenColumns",frozenColumns);
        List columns = new ArrayList();
        excelMap.put("columns",columns);
        formInfo.put("exportJsonParams",JSONObject.toJSON(excelMap).toString());
        return formInfo;
    }

    @RequestMapping(value = "stores_orderCount", method = RequestMethod.POST)
    @ResourceMethod(name = "店铺战报", check = CHECK_LOGIN)
    @ResponseBody
    public Map<String, Object> storesOrderCount(String startday,String endday) throws Exception {
        Map formInfo = new HashMap();
        formInfo.put("startday",startday);
        formInfo.put("endday",endday);
        if(WebUtil.getSession("stores_id") != null) formInfo.put("stores_id",WebUtil.getSession("stores_id"));
        else formInfo.put("stores_id",WebUtil.getUser().getStores_id());
        List<Map> resultList = sqlDao.getRecordList("cds_order_info.storesOrderCount", formInfo);
        BigDecimal count  = new BigDecimal(0);
        int i = 0;
        for(Map result : resultList){
            if(i==resultList.size()-1){
                result.put("monthIncome",count.setScale(3,BigDecimal.ROUND_HALF_UP));
                break;
            }
            if(!result.containsKey("monthIncome"))break;
            count = count.add(new BigDecimal(result.get("monthIncome").toString()));
            i++;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows", resultList);
        return map;
    }


    @RequestMapping(value = "storesIncome", method = RequestMethod.POST)
    @ResourceMethod(name = "店铺战报读取店铺", check = CHECK_LOGIN)
    @ResponseBody
    public List storesIncome(@RequestBody Map paramMap) throws Exception {
        return sqlDao.getRecordList("cds_stores_income.select",paramMap);
    }


    @RequestMapping(value = "storesIncomeSave", method = RequestMethod.POST)
    @ResourceMethod(name = "店铺战报", check = CHECK_LOGIN)
    @ResponseBody
    public int storesIncomeSave(String[] stores_id,String[] month_income,String create_date,String year) throws Exception {
        List list = new ArrayList();
        Map paramMap = new HashMap();
        paramMap.put("create_date",create_date);
        for(int i = 0;i<stores_id.length; i++){
            Map tempMap = new HashMap();
            tempMap.put("stores_id",stores_id[i]);
            tempMap.put("month_income",month_income[i]);
            list.add(tempMap);
        }
        ArrayList mycopy=new ArrayList(Arrays.asList(new Integer[list.size()]));
        Collections.copy(mycopy, list);
        paramMap.put("create_date",create_date);
        paramMap.put("year",year);
        paramMap.put("updateList",mycopy);
        paramMap.put("insertList",list);
        sqlDao.getRecordList("cds_stores_income.saveOrUpdate",paramMap);
        return 1;
    }

    @RequestMapping(value = "storesIncomeExcel", method = RequestMethod.POST)
    @ResourceMethod(name = "店铺战报导出", check = CHECK_LOGIN)
    @ResponseBody
    public void storesIncomeExcel(String startday,String endday) throws Exception {
        Map formInfo = new HashMap();
        formInfo.put("startday",startday);
        formInfo.put("endday",endday);
        if(WebUtil.getSession("stores_id") != null) formInfo.put("stores_id",WebUtil.getSession("stores_id"));
        else formInfo.put("stores_id",WebUtil.getUser().getStores_id());
        List<Map> resultList = sqlDao.getRecordList("cds_order_info.storesOrderCount", formInfo);
        BigDecimal count  = new BigDecimal(0);
        int i = 0;
        Calendar now = Calendar.getInstance();
        BigDecimal day = new BigDecimal(now.get(Calendar.DAY_OF_MONTH));
        BigDecimal divDay = new BigDecimal(now.getActualMaximum(Calendar.DATE));
        BigDecimal number = day.divide(divDay, 5, BigDecimal.ROUND_HALF_UP);
        for(Map result : resultList){
            if(i==resultList.size()-1){
                result.put("monthIncome",count.setScale(3,BigDecimal.ROUND_HALF_UP));
            }
            BigDecimal income = new BigDecimal(result.get("income").toString());
            BigDecimal sendPrice = new BigDecimal(result.get("sendPrice").toString());
            BigDecimal monthIncome = new BigDecimal(result.get("monthIncome").toString());
            BigDecimal orderCount = new BigDecimal(result.get("orderCount").toString());
            BigDecimal shopPart = new BigDecimal(result.get("shopPart").toString());
            BigDecimal price = new BigDecimal(result.get("price").toString());
            BigDecimal definde1 = (income.subtract(sendPrice).divide(monthIncome, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100))).setScale(4,BigDecimal.ROUND_HALF_UP);
            result.put("defined1",definde1);
            result.put("defined2",number.multiply(new BigDecimal(100)));
            result.put("defined3",definde1.divide(number,4, BigDecimal.ROUND_HALF_UP));
            result.put("defined4",(number.subtract((income.subtract(sendPrice)).divide(monthIncome, 4, BigDecimal.ROUND_HALF_UP)).multiply(monthIncome).setScale(2,BigDecimal.ROUND_HALF_UP)));
            result.put("defined5",(income.divide(orderCount, 4, BigDecimal.ROUND_HALF_UP).setScale(4,BigDecimal.ROUND_HALF_UP)));
            result.put("defined6","");
            result.put("defined7","");
            result.put("defined8",(shopPart.divide(price, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(4,BigDecimal.ROUND_HALF_UP)));
            count = count.add(new BigDecimal(result.get("monthIncome").toString()));
            i++;
        }
        formInfo = getFormInfoStoresMap(formInfo);
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

    public Map getFormInfoStoresMap(Map formInfo){
        Map excelMap = new HashMap();
        List<List<Map>> frozenColumns = new ArrayList<>();
        List<Map> frozenColumn1 = new ArrayList<>();
        List<Map> frozenColumn2 = new ArrayList<>();
        Map columMap = new HashMap();
        columMap.put("field","storesName");
        columMap.put("title","门店");
        columMap.put("width","120");
        columMap.put("rowspan",2);
        columMap.put("exp",true);
        frozenColumn1.add(columMap);
        columMap = new HashMap();
        columMap.put("field","defined1");
        columMap.put("title","营业额完成率(%)");
        columMap.put("width","120");
        columMap.put("rowspan",2);
        columMap.put("exp",true);
        frozenColumn1.add(columMap);
        columMap = new HashMap();
        columMap.put("field","defined2");
        columMap.put("title","时间进度(%)");
        columMap.put("width","70");
        columMap.put("rowspan",2);
        columMap.put("exp",true);
        frozenColumn1.add(columMap);
        columMap = new HashMap();
        columMap.put("field","defined3");
        columMap.put("title","预计完成度(%)");
        columMap.put("width","80");
        columMap.put("rowspan",2);
        columMap.put("exp",true);
        frozenColumn1.add(columMap);
        columMap = new HashMap();
        columMap.put("field","defined4");
        columMap.put("title","累计营业额缺口");
        columMap.put("width","120");
        columMap.put("rowspan",2);
        columMap.put("exp",true);
        frozenColumn1.add(columMap);
        columMap = new HashMap();
        columMap.put("title","基础数据(仅饿了么,美团)");
        columMap.put("width","120");
        columMap.put("colspan",12);
        columMap.put("align","center");
        columMap.put("exp",true);
        frozenColumn1.add(columMap);
        columMap = new HashMap();
        columMap.put("field","income");
        columMap.put("title","累计营业额");
        columMap.put("width","120");
        columMap.put("rowspan",1);
        columMap.put("align","center");
        columMap.put("exp",true);
        frozenColumn2.add(columMap);
        columMap.put("field","elmOrderCount");
        columMap.put("title","饿了单量");
        columMap.put("width","120");
        columMap.put("rowspan",1);
        columMap.put("exp",true);
        frozenColumn2.add(columMap);
        columMap = new HashMap();
        columMap.put("field","mtOrderCount");
        columMap.put("title","美团单量");
        columMap.put("width","120");
        columMap.put("rowspan",1);
        columMap.put("exp",true);
        frozenColumn2.add(columMap);
        columMap = new HashMap();
        columMap.put("field","orderCount");
        columMap.put("title","总有效订单");
        columMap.put("width","120");
        columMap.put("rowspan",1);
        columMap.put("exp",true);
        frozenColumn2.add(columMap);
        columMap = new HashMap();
        columMap.put("field","sendPrice");
        columMap.put("title","配送费预估");
        columMap.put("width","120");
        columMap.put("rowspan",1);
        columMap.put("exp",true);
        frozenColumn2.add(columMap);
        columMap = new HashMap();
        columMap.put("field","defined5");
        columMap.put("title","客单价");
        columMap.put("width","120");
        columMap.put("rowspan",1);
        columMap.put("exp",true);
        frozenColumn2.add(columMap);
        columMap = new HashMap();
        columMap.put("field","defined6");
        columMap.put("title","饿了么平台抽佣");
        columMap.put("width","120");
        columMap.put("rowspan",1);
        columMap.put("exp",true);
        frozenColumn2.add(columMap);
        columMap = new HashMap();
        columMap.put("field","defined7");
        columMap.put("title","美团平台抽佣");
        columMap.put("width","120");
        columMap.put("rowspan",1);
        columMap.put("align","center");
        columMap.put("exp",true);
        frozenColumn2.add(columMap);
        columMap = new HashMap();
        columMap.put("field","shopPart");
        columMap.put("title","自营销金额");
        columMap.put("width","120");
        columMap.put("rowspan",1);
        columMap.put("exp",true);
        frozenColumn2.add(columMap);
        columMap = new HashMap();
        columMap.put("field","price");
        columMap.put("title","交易额");
        columMap.put("width","120");
        columMap.put("rowspan",1);
        columMap.put("exp",true);
        frozenColumn2.add(columMap);
        columMap = new HashMap();
        columMap.put("field","defined8");
        columMap.put("title","自营销力度(%)");
        columMap.put("width","120");
        columMap.put("rowspan",1);
        columMap.put("exp",true);
        frozenColumn2.add(columMap);
        frozenColumns.add(frozenColumn1);
        frozenColumns.add(frozenColumn2);
        excelMap.put("frozenColumns",frozenColumns);
        List columns = new ArrayList();
        excelMap.put("columns",columns);
        formInfo.put("exportJsonParams",JSONObject.toJSON(excelMap).toString());
        return formInfo;
    }

    @RequestMapping(value = "orderShop", method = RequestMethod.POST)
    @ResourceMethod(name = "自营销占比", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage orderShop(@RequestBody Map paramMap) throws Exception {
        if(WebUtil.getSession("stores_id") != null)
            paramMap.put("stores_id",WebUtil.getSession("stores_id"));
        else paramMap.put("stores_id",WebUtil.getUser().getStores_id());
        List<String> list = sqlDao.getRecordList("cds_order_info.orderSelectFromin");
        paramMap.put("list",list);
        List<HashMap> resultList = sqlDao.getRecordList("cds_order_info.orderShop",paramMap);

        StringBuffer titleStr = new StringBuffer("[[");
        titleStr.append("{\"title\":\"交易额\",\"colspan\":"+(list.size()+2)+"},");
        titleStr.append("{\"title\":\"自营销金额\",\"colspan\":"+(list.size()+1)+"},");
        titleStr.append("{\"title\":\"自营销占比\",\"colspan\":"+list.size()+"}],");
        StringBuffer str = new StringBuffer("[");
        str.append("{\"field\":\"storesName\",\"title\":\"店铺\",\"width\":80,\"height\":50,\"align\":\"center\",\"rowspan\":1},");
        int i = 0;
        for(HashMap result : resultList){
                for(int index=0;index<list.size();index++){
                    if("0.00".equals(result.get("price"+index).toString())){
                        result.put("count"+index,0);
                    }else{
                    result.put("count"+index,new BigDecimal(result.get("shopPart"+index).toString()).multiply(new BigDecimal(100)).divide(new BigDecimal(result.get("price"+index).toString()),2,BigDecimal.ROUND_HALF_UP)+"%");
                    }
                }
            i++;
        }
        for(int x=0;x<list.size();x++){
            str.append("{\"field\":\"price"+x+"\",\"title\":\""+list.get(x)+"\",\"width\":80,\"height\":50,\"align\":\"center\",\"rowspan\":1},");
            if(x==list.size()-1)str.append("{\"field\":\"price\",\"title\":\"合计\",\"width\":80,\"height\":50,\"align\":\"center\",\"rowspan\":1},");
        }
        for(int y=0;y<list.size();y++){
            str.append("{\"field\":\"shopPart"+y+"\",\"title\":\""+list.get(y)+"\",\"width\":80,\"height\":50,\"align\":\"center\",\"rowspan\":1},");
            if(y==list.size()-1) str.append("{\"field\":\"shopPart\",\"title\":\"合计\",\"width\":80,\"height\":50,\"align\":\"center\",\"rowspan\":1},");
        }
        for(int z=0;z<list.size();z++){
            str.append("{\"field\":\"count"+z+"\",\"title\":\""+list.get(z)+"比例\",\"width\":80,\"height\":50,\"align\":\"center\",\"rowspan\":1},");
        }
        str.replace(str.length()-1,str.length(),"]]");
        Map resultData = new HashMap();
        resultData.put("list", resultList);
        resultData.put("colums", titleStr.append(str));
        JsonMessage message = new JsonMessage(1, "success", resultData);
        return message;
    }

}
