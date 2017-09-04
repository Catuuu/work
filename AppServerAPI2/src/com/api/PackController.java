package com.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.framework.annotation.ResourceMethod;
import com.framework.controller.BasicController;
import com.framework.mapping.JsonMessage;
import com.framework.mapping.excel.ExcelFileExport;
import com.framework.mapping.system.*;
import com.framework.system.SystemConstant;
import com.framework.util.CodeUtil;
import com.framework.util.DateUtil;
import com.framework.util.StringUtil;
import com.framework.util.WebUtil;
import com.opensdk.weixin.factory.APIFactoryWeixin;
import com.opensdk.weixin.vo.SystemParamWeixin;
import com.service.OrderService;
import com.service.PackService;
import com.service.SyncService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.framework.annotation.CheckType.NO_CHECK;

/**
 * Created by Administrator on 2017/4/15 0015.打包流程调用接口
 */
@Controller
@RequestMapping("/packApi")
public class PackController extends BasicController {

    @Resource(name = "OrderService")
    protected OrderService orderService;

    @Resource(name = "PackService")
    protected PackService packService;

    @Resource(name = "SyncService")
    protected SyncService syncService;

    private static String password = "A622625ufdki12yui3";

    @RequestMapping(value = "loginVerify", method = RequestMethod.POST)
    @ResourceMethod(name = "门店用户登录验证", check = NO_CHECK)
    @ResponseBody
    public JsonMessage LoginVerify(CdsStoresLogin userInfo) throws Exception {

        String password = "###" + StringUtil.MD5(StringUtil.MD5(SystemConstant.passKey + userInfo.getPass()));
        userInfo.addConditionField("username");
        userInfo.addParamFields("id,username,pass,stores_id,version_no,login_time,print_format_id");
        CdsStoresLogin cdsUsers = sqlDao.getRecord(userInfo);
        if (cdsUsers == null) {
            return new JsonMessage(0, "用户名或者密码不正确！");
        }
        if (!cdsUsers.getPass().equalsIgnoreCase(password)) { //密码错误
            return new JsonMessage(0, "用户名或者密码不正确！");
        }
        Integer stores_id = cdsUsers.getStores_id();
        userInfo.setId(cdsUsers.getId());
        userInfo.setLogin_time(new Date());
        userInfo.addConditionField("id");
        if (userInfo.getVersion_no() == null || userInfo.getVersion_no().equals("")) {
            userInfo.setVersion_no(cdsUsers.getVersion_no());
        }
        userInfo.resetParamFields("version_no,login_time");
        sqlDao.updateRecord(userInfo);


        //扫描枪信息
        CdsScanner cdsScanner = new CdsScanner();
        cdsScanner.setStores_id(stores_id);
        cdsScanner.setStatus(1);
        cdsScanner.addConditionFields("stores_id,status");
        cdsScanner.addParamFields("cs_id,sc_name,cs_num");

        List<CdsScanner> scannersList = sqlDao.getRecordList(cdsScanner);
        if (scannersList == null || scannersList.size() == 0) {
            return new JsonMessage(0, "请正确配置扫描枪后重新登录");
        }

        List scanners = new ArrayList();
        for (CdsScanner scanner : scannersList) {
            HashMap map = new HashMap();
            map.put("sc_id", scanner.getCs_id());
            map.put("sc_name", scanner.getSc_name());
            map.put("sc_num", scanner.getCs_num());
            scanners.add(map);
        }

        //打印机信息
        HashMap map = new HashMap();
        map.put("stores_id", stores_id);
        List prints = sqlDao.getRecordList("cds_stores_prints.getPrints", map);
        if (prints == null || prints.size() == 0) {
            return new JsonMessage(0, "请正确配置打印机后重新登录");
        }
        List<Map> orders = sqlDao.getRecordList("cds_stores_order_info.getAllOrdersId", map);

        Map result = new HashMap();
        result.put("scanners", scanners);
        result.put("prints", prints);
        result.put("stores_id", stores_id);
        result.put("print_format_id", cdsUsers.getPrint_format_id());
        result.put("orders", orders);

        CdsStores stores = new CdsStores();
        stores.setStores_id(stores_id);
        stores.addConditionField("stores_id");
        stores.addParamField("name,linkman");
        CdsStores cdsStores = sqlDao.getRecord(stores);
        result.put("stores_name", cdsStores.getName());
        if (cdsStores.getLinkman() == null) {
            result.put("tel", "400-1148-878");//客服电话
        }
        result.put("tel", cdsStores.getLinkman());


        HashMap mq = new HashMap();
        mq.put("date", DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        result.put("mqConfig", mq);

        result.put("server_time", DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));

        return new JsonMessage(1, "登录成功", result);
    }


    /**
     * 通过商铺ID获取打包信息
     * @param stores_id
     * @return
     */
    public CdsStoresLogin getStoresLogin(int stores_id) {
        String key = "storesLogin_" + stores_id;
        CdsStoresLogin cdsstoresLogin = getRedisBean(key, CdsStoresLogin.class);
        if (cdsstoresLogin == null) {
            cdsstoresLogin = new CdsStoresLogin();
            cdsstoresLogin.setStores_id(stores_id);
            cdsstoresLogin.addConditionField("stores_id");
            cdsstoresLogin.addParamFields("id,username,pass,stores_id,version_no,login_time,print_format_id");
            cdsstoresLogin = sqlDao.getRecord(cdsstoresLogin);

            if (cdsstoresLogin != null) {
                addRedis(key, cdsstoresLogin, 10 * 60);
            }
        }
        return cdsstoresLogin;
    }


    @RequestMapping(value = "getOrderInfos", method = RequestMethod.GET)
    @ResourceMethod(name = "获取订单详情", check = NO_CHECK)
    @ResponseBody
    public JsonMessage getOrderInfos(String order_ids) {
        if (order_ids == null && order_ids.equals("")) {
            return new JsonMessage(0, "缺少参数order_id");
        }
        String[] orders = order_ids.split(",");

        List paramList = new ArrayList();
        for (int i = 0; i < orders.length; i++) {
            Map paramMap = new HashMap();
            paramMap.put("order_id", orders[i]);
            paramList.add(paramMap);
        }
        List<CdsOrderInfo> cdsOrderInfos = sqlDao.getRecordList("cds_stores_order_info.getAllOrdersInfo", paramList);

        List resultList = new ArrayList();
        for (CdsOrderInfo orderInfo : cdsOrderInfos) {
            String code = CodeUtil.getCode(orderInfo);//一维码
            orderInfo.setCode(code.split("_")[0]);
            if(orderInfo.getSend_name().equals("点我达")){
                orderInfo.setPrint_format(1);
            }else{
                orderInfo.setPrint_format(2);
            }

            CdsStoresLogin cdsstoresLogin = getStoresLogin(orderInfo.getStores_id());
            if(cdsstoresLogin!=null){
                orderInfo.setGood_print_format(cdsstoresLogin.getPrint_format_id());
            }
            //orderInfo.setUrl(getWeixinUrl(code.split("_")[1]));//二维码url
            String orderstr = JSONObject.toJSONString(orderInfo);
            resultList.add(orderstr);
        }



        return new JsonMessage(1, "获取订单详情成功", resultList);
    }

    @RequestMapping(value = "getOrderInfo", method = RequestMethod.GET)
    @ResourceMethod(name = "获取订单详情", check = NO_CHECK)
    @ResponseBody
    public JsonMessage getOrderInfo(CdsOrderInfo cdsOrderInfo) {
        if (cdsOrderInfo.getOrder_id() == null) {
            return new JsonMessage(0, "缺少参数order_id");
        }
        cdsOrderInfo.addParamFields();
        cdsOrderInfo.addConditionField("order_id");
        cdsOrderInfo = sqlDao.getRecord(cdsOrderInfo);
        if (cdsOrderInfo == null) {
            return new JsonMessage(0, "该订单不存在");
        }


        String code = CodeUtil.getCode(cdsOrderInfo);//一维码
        cdsOrderInfo.setCode(code.split("_")[0]);
        try {
            if(cdsOrderInfo.getBrand_id()==1 || cdsOrderInfo.getBrand_id()==2){
                cdsOrderInfo.setUrl(getWeixinUrl(code.split("_")[1], cdsOrderInfo.getBrand_id()));//二维码url
            }
        }catch(Exception e){
        }

        //设置标头打印格式
        if(cdsOrderInfo.getSend_name().equals("点我达")){
            cdsOrderInfo.setPrint_format(1);
        }else{
            cdsOrderInfo.setPrint_format(2);
        }

        //设置商品打印格式
        CdsStoresLogin cdsstoresLogin = getStoresLogin(cdsOrderInfo.getStores_id());
        if(cdsstoresLogin!=null){
            cdsOrderInfo.setGood_print_format(cdsstoresLogin.getPrint_format_id());
        }

        String orderstr = JSONObject.toJSONString(cdsOrderInfo);
        return new JsonMessage(1, "获取订单详情成功", orderstr);
    }


    @RequestMapping(value = "orderlist", method = RequestMethod.POST)
    @ResourceMethod(name = "出餐服务接口", check = NO_CHECK)
    @ResponseBody
    public JsonMessage getOrderList(@RequestParam HashMap formInfo) {
        if (formInfo.get("timestamp") == null) {
            return new JsonMessage(0, "缺少参数timestamp");
        }
        if (formInfo.get("sign") == null) {
            return new JsonMessage(0, "缺少参数sign");
        }
        if (formInfo.get("stores_id") == null) {
            return new JsonMessage(0, "缺少参数stores_id");
        }

        String stores_id = formInfo.get("stores_id").toString();
        String timestamp = formInfo.get("timestamp").toString();
        String paramsign = formInfo.get("sign").toString();
        //签名验证
        String sign = StringUtil.MD5(stores_id + timestamp + password);
        if (!paramsign.equals(sign)) {
            return new JsonMessage(0, "签名错误");
        }
        //同步打印数据
        int psync = 0;
        try {
            if (formInfo.get("prints") != null && !formInfo.get("prints").equals("")) {
                List printList = JSONArray.parseArray(formInfo.get("prints").toString());
                if (printList.size() > 0) {
                    syncService.SyncPrints(printList);
                    psync = 1;
                }
            }

        } catch (Exception e) {
            psync = 0;
        }

        //同步扫码数据
        int ssync = 0;
        if (formInfo.get("scanners") != null && !formInfo.get("scanners").equals("")) {

            List scannerList = JSONArray.parseArray(formInfo.get("scanners").toString());
            try {
                if (scannerList.size() > 0) {
                    syncService.SyncScanners(scannerList);
                    ssync = 1;
                }
            } catch (Exception e) {
                ssync = 0;
            }

        }

        //获取出餐订单列表
        HashMap resultMap = new HashMap();
        //同步打印数据是否成功
        resultMap.put("psync", psync);
        //同步打包数据是否成功
        resultMap.put("ssync", ssync);
        Map countParam = sqlDao.getRecord("cds_stores_order_info.getOrderCount", formInfo);

        List<Map> orderList = sqlDao.getRecordList("cds_stores_order_info.getStoresPageRecord", formInfo);

        List<Map> orderList1 = new ArrayList<>();
        List<Map> orderList2 = new ArrayList<>();
        List<Map> orderList3 = new ArrayList<>();


        for (Map map : orderList) {
            Map result = new HashMap();

            result.put("timeout", map.get("service_orver_time"));
            result.put("task_name", map.get("task_order_name"));
            result.put("task_phone", map.get("task_order_phone"));
            result.put("service_time", map.get("service_time"));
            result.put("order_id", map.get("order_id"));
            result.put("stores_name", map.get("stores_name"));
            result.put("order_no", map.get("order_no"));
            result.put("fromin", map.get("fromin"));
            result.put("order_desc", map.get("order_desc"));
            result.put("create_date", map.get("create_date"));
            result.put("fromin_no", map.get("fromin_no"));
            result.put("brand_id", map.get("brand_id"));
            result.put("timeout", map.get("timeout"));

            int mtype = Integer.parseInt(map.get("mtype").toString());
            if (mtype == 1) {//待打包
                orderList1.add(result);
            } else if (mtype == 2) {//待取餐
                orderList2.add(result);
            } else if (mtype == 3) {//预订单
                orderList3.add(result);
            }
        }


        resultMap.put("count1", countParam.get("count1"));
        resultMap.put("count2", countParam.get("count2"));
        resultMap.put("count3", countParam.get("count3"));


        resultMap.put("orderList1", orderList1);
        resultMap.put("orderList2", orderList2);
        resultMap.put("orderList3", orderList3);


        String stores_key = "stores_print_order_" + stores_id;
        String orders = getRedisString(stores_key);
        if (orders != null) {
            deleteRedis(stores_key);
            resultMap.put("printorders", orders);
        }
        return new JsonMessage(1, "获取订单列表成功", resultMap);
    }


    //线下送订单手动完成
    @RequestMapping(value = "optOrder", method = RequestMethod.POST)
    @ResourceMethod(name = "线下送订单手动完成", check = NO_CHECK)
    @ResponseBody
    public JsonMessage optOrder(@RequestParam HashMap formInfo) throws Exception {
        if (formInfo.get("timestamp") == null) {
            return new JsonMessage(0, "缺少参数timestamp");
        }
        if (formInfo.get("sign") == null) {
            return new JsonMessage(0, "缺少参数sign");
        }
        if (formInfo.get("stores_id") == null) {
            return new JsonMessage(0, "缺少参数stores_id");
        }
        if (formInfo.get("order_id") == null || formInfo.get("order_id").equals("")) {
            return new JsonMessage(0, "缺少参数order_id");
        }
        String stores_id = formInfo.get("stores_id").toString();
        String timestamp = formInfo.get("timestamp").toString();
        String paramsign = formInfo.get("sign").toString();
        String order_id = formInfo.get("order_id").toString();
        //签名验证
        String sign = StringUtil.MD5(stores_id + timestamp + password);
        if (!paramsign.equals(sign)) {
            return new JsonMessage(0, "签名错误");
        }

        CdsOrderInfo orderInfo = new CdsOrderInfo();
        orderInfo.setOrder_id(order_id);
        orderInfo.setOrder_status(4);
        orderInfo.resetParamField("order_status");
        orderInfo.addConditionField("order_id");
        sqlDao.updateRecord(orderInfo);//修改订单信息

        CdsOrderLog cdsOrderLog = new CdsOrderLog();
        cdsOrderLog.setOl_id(StringUtil.getPrimaryOrderKey());
        cdsOrderLog.setOrder_id(order_id);
        cdsOrderLog.setOpt_type("员工");
        cdsOrderLog.setOrder_status(4);
        cdsOrderLog.setOrder_status_chi("订单已完成");
        cdsOrderLog.setOpt_note("自配送手动完成订单");
        cdsOrderLog.setOpt_name("员工操作");
        cdsOrderLog.setOpt_time(new Date());
        cdsOrderLog.addParamFields();
        sqlDao.insertRecord(cdsOrderLog);//插入订单操作日志

        return new JsonMessage(1, "手动操作成功");
    }

    public String getWeixinUrl(String order_id, Integer brand_id) {
        Map params = getRedisMap("params_brand" + brand_id);
        if (params == null) {
            CdsBrand brand = new CdsBrand();
            brand.setBrand_id(brand_id);
            brand.addConditionField("brand_id");
            brand.addParamFields();
            brand = sqlDao.getRecord(brand);
            HashMap map = new HashMap();
            map.put("appid", brand.getWeixin_appid());
            map.put("appsecrest", brand.getWeixin_appsecret());
            addRedis("params_brand" + brand_id, map,60 * 60);
        }
        SystemParamWeixin wxParams = new SystemParamWeixin(params.get("appid").toString(), params.get("appsecrest").toString());

        String result = "";
        String accesstoken_key = "weixin_accesstoken" + brand_id;
        String accesstoken = getRedisString(accesstoken_key);

        if (accesstoken == null) {
            accesstoken = APIFactoryWeixin.getPoiAPI().accessToken(wxParams);
            addRedis(accesstoken_key, accesstoken, 60 * 60);
        }
        if (!accesstoken.equals("")) {
            result = APIFactoryWeixin.getPoiAPI().createcode(accesstoken, order_id);
        }
        return result;
    }


    @RequestMapping(value = "packJob", method = RequestMethod.POST)
    @ResourceMethod(name = "打包计件查询", check = NO_CHECK)
    @ResponseBody
    public JsonMessage packJob(@RequestBody HashMap formInfo) throws Exception {
        Map result_map = getPackResult(formInfo);
        List<Map> storesTitle1 = sqlDao.getRecordList("cds_stores.easyTitleName1",formInfo);
        StringBuffer title1 = new StringBuffer("[[");
        for (Map stores : storesTitle1){
            title1.append("{\"title\":\""+stores.get("storesName")+"\",\"colspan\":"+stores.get("peopleCount")+"},");
        }
        title1.replace(title1.length()-1,title1.length(),"],");
        List<Map> storesTitle2 = sqlDao.getRecordList("cds_stores.easyTitleName2",formInfo);
        StringBuffer title2 = new StringBuffer("[");
        for(Map stores : storesTitle2){
            title2.append("{\"field\":\""+stores.get("packUserName")+"\",\"title\":\""+stores.get("packUserName")+"\",\"width\":80,\"height\":50,\"align\":\"center\",\"rowspan\":1},");
        }
        title2.replace(title2.length()-1,title2.length(),"]]");
        Map resultData = new HashMap();
        resultData.put("list",result_map.get("list"));
        resultData.put("colums", title1.append(title2).toString());
        JsonMessage message = new JsonMessage(1,"success",resultData);
        return message;
    }

    public HashMap getPackResult(HashMap formInfo){
        if(WebUtil.getSession("stores_id") != null)
            formInfo.put("stores_id",WebUtil.getSession("stores_id"));
        else formInfo.put("stores_id",WebUtil.getUser().getStores_id());
        HashMap result_map = new HashMap();
        List<HashMap> resultList = sqlDao.getRecordList("cds_pack.packJob", formInfo);
        TreeSet set_name = new TreeSet();
        TreeSet set_date = new TreeSet();
        List sum_list = new ArrayList();
        int count2 = 0;
        for (HashMap result : resultList) {
            if (result.get("order_date") == null && result.get("sc_name") != null) {
                set_name.add(result.get("sc_name").toString());
                sum_list.add(result);
                count2 = count2 + Integer.parseInt(result.get("count").toString());
            }

            if (result.get("order_date") != null) {
                set_date.add(result.get("order_date").toString());
            }

        }

//        for (HashMap result : resultList) {
//
//        }

        Iterator it_date = set_date.iterator();
        List<Map> list = new ArrayList();

        while (it_date.hasNext()) {
            HashMap map = new HashMap();
            String order_date = it_date.next().toString();
            List<HashMap> list_date = new ArrayList();
            int count1 = 0;
            for (HashMap result : resultList) {
                HashMap map_date = new HashMap();

                if (result.get("order_date") != null && result.get("sc_name") != null) {
                    if (order_date.equals(result.get("order_date").toString())) {
                        map_date.put("sc_name", result.get("sc_name").toString());
                        map_date.put("count", result.get("count").toString());
                        count1 = count1 + Integer.parseInt(result.get("count").toString());

//                        map_date.put("order_date", result.get("order_date").toString());
                        list_date.add(map_date);
                        map.put("date", result.get("order_date").toString());
                        map.put("scanner", list_date);
                    }
                }

            }
            map.put("count1", count1);


            Iterator it_name = set_name.iterator();
            while (it_name.hasNext()) {//1.3.2.4
                boolean flag = false;
                String name = it_name.next().toString();

                for (HashMap date : list_date) {//2.3.1
                    if (name.equals(date.get("sc_name").toString())) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    HashMap map1 = new HashMap();
                    map1.put("sc_name", name);
                    map1.put("count", 0);
                    list_date.add(map1);
                    map.put("scanner", list_date);
                }

            }


            list.add(map);
        }

        HashMap map2 = new HashMap();
        map2.put("date", "合计");
        map2.put("scanner", sum_list);
        map2.put("count1", count2);
        list.add(map2);

        result_map.put("title", set_name);
        for(Map result : list){
            int i = 0;
            List<Map> scnner = (List<Map>)result.get("scanner");
            for(Map sc : scnner){
                result.put(sc.get("sc_name"),sc.get("count"));
                if(i==0){
                    result.put("order_date",result.get("date"));
                    result.put("count1",result.get("count1"));
                    i++;
                }
            }
        }
        result_map.put("list", list);

        return result_map;
    }

    public static void main(String[] args) {
        String password = "###" + StringUtil.MD5(StringUtil.MD5(SystemConstant.passKey + "123456"));
        System.out.print(password);
    }


    @RequestMapping(value = "getMtCookie", method = RequestMethod.POST)
    @ResourceMethod(name = "获取订单详情", check = NO_CHECK)
    @ResponseBody
    public void getMtCookie(String cookie) {
        String mtCookie = getRedisString("mtCookie");
        if(mtCookie==null){
            addRedis("mtCookie",mtCookie);
        }else{
            updateRedis("mtCookie",mtCookie);
        }
        System.out.println("====================================================="+mtCookie);
//        return new JsonMessage(1, "获取订单详情成功", orderstr);
    }

    @RequestMapping(value = "packJobExcel", method = RequestMethod.POST)
    @ResourceMethod(name = "打包计件导出", check = NO_CHECK)
    @ResponseBody
    public void getPackJobExcel(String startday,String endday,String ordertype,String datatype) throws Exception{
        HashMap formInfo = new HashMap();
        formInfo.put("startday",startday);
        formInfo.put("endday",endday);
        formInfo.put("ordertype",ordertype);
        formInfo.put("datatype",datatype);
        HashMap packResult = getPackResult(formInfo);
        getFormInfoExcel(formInfo,packResult);
        //创建导出excel文件线程
        ExcelFileExport fileExcel = new ExcelFileExport(sqlDao, "null", "null", formInfo);
        fileExcel.setResultList((ArrayList)packResult.get("list"));
        fileExcel.setNumberFormat("#,##0");
        String fileName = fileExcel.getExpfileName();
        //给web前台输出文件名称
        responseText("{\"fileName\":\"" + fileName + "\"}");
        //本地生成文件名
        fileExcel.createFileDay();
    }


    public void getFormInfoExcel(HashMap formInfo,HashMap packResult) throws Exception {
        Map excelMap = new HashMap();
        List<List<Map>> frozenColumns = new ArrayList<>();
        List<Map> frozenColumn1 = new ArrayList<>();
        List<Map> frozenColumn2 = new ArrayList<>();
        Map columMap = new HashMap();
        columMap.put("field","order_date");
        columMap.put("title","日期");
        columMap.put("rowspan","2");
        columMap.put("width","80");
        columMap.put("exp",true);
        frozenColumn1.add(columMap);
        columMap = new HashMap();
        columMap.put("field","count1");
        columMap.put("title","合计");
        columMap.put("rowspan","2");
        columMap.put("width","80");
        columMap.put("exp",true);
        frozenColumn1.add(columMap);
        List<Map> storesTitle1 = sqlDao.getRecordList("cds_stores.easyTitleName1",formInfo);
        for (Map stores : storesTitle1){
            Map tempMap = new HashMap();
            tempMap.put("title",stores.get("storesName"));
            tempMap.put("colspan",stores.get("peopleCount"));
            tempMap.put("exp",true);
            frozenColumn1.add(tempMap);
        }
        List<Map> storesTitle2 = sqlDao.getRecordList("cds_stores.easyTitleName2",formInfo);
        for(Map stores : storesTitle2){
            Map tempMap = new HashMap();
            tempMap.put("title",stores.get("packUserName"));
            tempMap.put("field",stores.get("packUserName"));
            tempMap.put("rowspan",1);
            tempMap.put("width",80);
            tempMap.put("align","center");
            tempMap.put("exp",true);
            frozenColumn2.add(tempMap);
        }

//      下面数据用于导出excel时定义表头
        frozenColumns.add(frozenColumn1);
        frozenColumns.add(frozenColumn2);
        excelMap.put("frozenColumns",frozenColumns);
        List columns = new ArrayList();
        excelMap.put("columns",columns);
        formInfo.put("exportJsonParams",JSONObject.toJSON(excelMap).toString());
    }

}
