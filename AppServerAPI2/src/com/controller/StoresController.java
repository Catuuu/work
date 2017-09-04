package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.framework.annotation.ChargeType;
import com.framework.annotation.ResourceMethod;
import com.framework.controller.BasicController;
import com.framework.mapping.JsonMessage;
import com.framework.mapping.system.*;
import com.framework.system.SystemConfig;
import com.framework.system.SystemConstant;
import com.framework.util.BeanUtil;
import com.framework.util.StringUtil;
import com.framework.util.WebUtil;
import com.opensdk.eleme.exception.ApiOpException;
import com.opensdk.eleme.exception.ApiSysException;
import com.opensdk.eleme.factory.APIFactoryEleme;
import com.opensdk.eleme2.api.entity.shop.OShop;
import com.opensdk.eleme2.api.enumeration.shop.OShopProperty;
import com.opensdk.eleme2.api.exception.ServiceException;
import com.opensdk.eleme2.api.service.ShopService;
import com.opensdk.meituan.factory.APIFactoryMeituan;
import com.opensdk.meituan.vo.PoiParam;
import com.service.StoresBrandService;
import org.apache.log4j.chainsaw.Main;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.framework.annotation.CheckType.CHECK_LOGIN;

/**
 * Created by c on 2017-02-02.
 */
@Controller
@RequestMapping("Stores")
public class StoresController extends BasicController {

    @Resource(name = "StoresBrandService")
    protected StoresBrandService storesbrandservice;

    @RequestMapping(value = "stores_grid", method = RequestMethod.GET)
    @ResourceMethod(name = "品牌列表", check = CHECK_LOGIN)
    public String stores_grid() throws Exception {
        CdsBrand cdsBrand = new CdsBrand();
        List<CdsBrand> brandlist = sqlDao.getRecordList(cdsBrand);
        WebUtil.getRequest().setAttribute("brandlist", brandlist);
        return "stores/stores_grid";
    }

    @RequestMapping(value = "GetStoresList", method = RequestMethod.POST)
    @ResourceMethod(name = "品牌管理列表", check = CHECK_LOGIN)
    @ResponseBody
    public void GetStoresList(@RequestParam HashMap formInfo) throws Exception {
        queryAndResponsePage("cds_stores.getPageRecord", formInfo);
    }

    @RequestMapping(value = "stores_charge", method = RequestMethod.GET)
    @ResourceMethod(name = "收费设置", check = CHECK_LOGIN)
    public String getStoresCharge() throws Exception {
        return "stores/stores_charge";
    }

    @RequestMapping(value = "storesCharge", method = RequestMethod.POST)
    @ResourceMethod(name = "收费设置", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage getStoresChargeList() throws Exception {
//        StringBuffer str = new StringBuffer("[[");
//        str.append("{\"field\":\"name\",\"title\":\"店铺名\",\"width\":80,\"height\":50,\"align\":\"center\",\"editor\":\"{type: 'numberbox',options: {min: 0,precision: 2}}\"},");
//        for(ChargeType ct : ChargeType.values()){
//            str.append("{\"field\":\""+ct+"\",\"title\":\""+ct+"\",\"width\":80,\"height\":50,\"align\":\"center\",\"editor\": \"{type: 'numberbox',options: {min: 0,precision: 2}}\"},");
//        }
//        str.replace(str.length()-1,str.length(),"]]");
        List<HashMap> storesList = sqlDao.getRecordList("cds_stores.orderOnTimeStores",null);
        List<Map> list = new ArrayList<>();
        for(HashMap stores :storesList){
            Map tempMap = new HashMap();
            tempMap.put("stores_id",stores.get("storesId"));
            tempMap.put("name",stores.get("storesName"));
            String chargeType = stores.get("chargeType").toString();
//            JSONObject jobj=JSON.parseObject(jsonText);
            JSONObject  chargeMap = JSON.parseObject(chargeType);
            for(int i=1;i<=5;i++) {
                if (chargeMap.containsKey("change_type"+i)) {
                    tempMap.put("t"+i, chargeMap.get("money"+i));
                }
            }
            list.add(tempMap);
        }
        Map resultData = new HashMap();
        resultData.put("list", list);
        JsonMessage message = new JsonMessage(1, "success", resultData);
        return message;
    }


    @RequestMapping(value = "storesChargeSave", method = RequestMethod.POST)
    @ResourceMethod(name = "收费设置", check = CHECK_LOGIN)
    @ResponseBody
    public int storesChargeSave(@RequestBody Map formInfo) throws Exception {
        Map tempMap = new HashMap();
        for(int i=1;i<=5;i++){
            if(formInfo.get("t"+i)!=""){
                tempMap.put("change_type"+i,i);
                tempMap.put("money"+i,new BigDecimal(formInfo.get("t"+i).toString()));
            }
        }
        Map paramMap = new HashMap();
        paramMap.put("stores_id",formInfo.get("stores_id"));
        paramMap.put("charge_type",JSONObject.toJSON(tempMap).toString());
        sqlDao.updateRecord("cds_stores.updateChargeType",paramMap);

//        sqlDao.getRecordList("cds_change_type_list.saveOrUpdate",formInfo);
        return 1;
    }



    @RequestMapping(value = "saveStores", method = RequestMethod.POST)
    @ResourceMethod(name = "保存品牌信息", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage saveStores(CdsBrand cds_brand, String opt) {
        if (opt.equals("add")) {
            cds_brand.addUnParamFields("brand_id");
            sqlDao.insertRecord(cds_brand);
        } else {

            cds_brand.addUnParamFields("brand_id");
            cds_brand.addConditionField("brand_id");
            sqlDao.updateRecord(cds_brand);
        }
        return new JsonMessage(1);
    }

    @RequestMapping(value = "storesbrand_list", method = RequestMethod.GET)
    @ResourceMethod(name = "区域及门店明细列表", check = CHECK_LOGIN)
    public String storesbrand_list(String parentid) throws Exception {
        CdsStores cs = new CdsStores();
        //武汉id:9001
//        cs.setCity(9001);
        CdsArea ca = new CdsArea();
//        ca.setParentid(9001);
//        cs.addConditionField("city");
//        ca.addConditionField("parentid");
        List<CdsArea> calist = sqlDao.getRecordList(ca);
        List<CdsStores> cslist = sqlDao.getRecordList(cs);
        CdsBrand cdsBrand = new CdsBrand();
        List<CdsBrand> brandlist = sqlDao.getRecordList(cdsBrand);
        //查询美团绑定信息
//        String meituanids = meituanBindQuery();
//        WebUtil.getRequest().setAttribute("meituanids",meituanids);

        List<CdsArea> list = sqlDao.getRecordList("cds_stores.getCity");
        WebUtil.getRequest().setAttribute("citylist", list);
        WebUtil.getRequest().setAttribute("arealist", calist);
        WebUtil.getRequest().setAttribute("storeslist", cslist);
        WebUtil.getRequest().setAttribute("brandlist", brandlist);


        return "stores/storesbrand_list";
    }

    @RequestMapping(value = "getCityStores", method = RequestMethod.POST)
    @ResourceMethod(name = "根据所选城市显示店铺", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage getCityStores(@RequestParam String city) {
        JsonMessage jmsg = new JsonMessage();
        CdsStores cs = new CdsStores();
        List<JSONObject> list = new ArrayList<JSONObject>();
        if(!StringUtils.isEmpty(city)){
            cs.setCity(Integer.valueOf(city));
            cs.addConditionField("city");
            List<CdsStores> cslist = sqlDao.getRecordList(cs);
            for (int i = 0; i <cslist.size() ; i++) {
                JSONObject jo = new JSONObject();
                jo.put("stores_id",cslist.get(i).getStores_id());
                jo.put("country",cslist.get(i).getCountry());
                jo.put("name",cslist.get(i).getName());
                list.add(jo);
            }
        }else{
            List<CdsStores> cslist = sqlDao.getRecordList(cs);
            for (int i = 0; i <cslist.size() ; i++) {
                JSONObject jo = new JSONObject();
                jo.put("stores_id",cslist.get(i).getStores_id());
                jo.put("country",cslist.get(i).getCountry());
                jo.put("name",cslist.get(i).getName());
                list.add(jo);
            }
        }
        jmsg.setStatus("1");
        jmsg.setObj(list);
        return jmsg;
    }

//    /**
//     * list去重并保留原先的顺序
//     * @param list
//     * @return
//     */
//    private List<CdsArea> removeDuplicate(List<CdsArea> list) {
//        Set<CdsArea> set = new HashSet<CdsArea>();
//        List<CdsArea> newList = new ArrayList<CdsArea>();
////                for (Iterator<CdsArea> iter = list.iterator(); iter.hasNext();) {
////                    CdsArea element = (CdsArea) iter.next();
////                         if (set.add(element))
////                                 newList.add(element);
////                     }
//        newList.add(list.get(0));
//        for (int i = 0; i <list.size(); i++) {
//                    int j = 1+i;
//            if(j<list.size()-1){
//                String name = list.get(i).getDataname();
//                String name2 = list.get(j).getDataname();
//                if(name.equals(name2)){
//                    newList.add(list.get(j));
//                }
//            }
//
//        }
//                 return newList;
//    }


    @RequestMapping(value = "getstoresBrandList", method = RequestMethod.POST)
    @ResourceMethod(name = "门店品牌明细列表", check = CHECK_LOGIN)
    @ResponseBody
    public void getstoresBrandList(@RequestParam HashMap formInfo) throws Exception {
        queryAndResponsePage("cds_stores.getStoresBrand", formInfo);
    }

    @RequestMapping(value = "getstoresBrandList2", method = RequestMethod.POST)
    @ResourceMethod(name = "门店品牌明细列表", check = CHECK_LOGIN)
    @ResponseBody
    public void getstoresBrandList2(@RequestParam HashMap formInfo) throws Exception {
        queryAndResponsePage("cds_stores.getStoresBrand2", formInfo);
    }

    @RequestMapping(value = "stores_list", method = RequestMethod.GET)
    @ResourceMethod(name = "地区列表", check = CHECK_LOGIN)
    public String stores_list(String id) throws Exception {
        CdsStores cs = new CdsStores();
        if (StringUtils.isEmpty(id)) {
            int ids = Integer.valueOf(id);
            cs.setCountry(ids);
        }
        cs.addConditionField("country");
        List<CdsStores> calist = sqlDao.getRecordList(cs);
        WebUtil.getRequest().setAttribute("calist", calist);
        return "stores/area_list";
    }

    @RequestMapping(value = "saveBrand", method = RequestMethod.POST)
    @ResourceMethod(name = "保存门店品牌明细", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage saveBrand(@RequestParam HashMap hashMap, String opt) {
        Iterator it = hashMap.entrySet().iterator();
        CdsStoresBrand cssb = new CdsStoresBrand();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            if (!StringUtils.isEmpty(value)) {
                if ("stores_brand_id".equals(key)) {
                    cssb.setStores_brand_id(Integer.valueOf(value));
                    //cssb = sqlDao.getRecord(cssb);
                }

                if ("brand_id".equals(key)) {
                    cssb.setBrand_id(Integer.valueOf(value));
                }
                if ("stores_id".equals(key)) {
                    cssb.setStores_id(Integer.valueOf(value));
                }
                if ("service_phone".equals(key)) {
                    cssb.setService_phone(value);
                }
                if ("stores_name".equals(key)) {
                    cssb.setStores_name(value);
                }
                if ("elem_restaurant_id".equals(key)) {
                    cssb.setElem_restaurant_id(value);
                }
                if ("elem_url".equals(key)) {
                    cssb.setElem_url(value);
                }
                if ("meituan_restaurant_id".equals(key)) {
                    cssb.setMeituan_restaurant_id(value);
                }
                if ("meituan_url".equals(key)) {
                    cssb.setMeituan_url(value);
                }
                if ("baidu_restaurant_id".equals(key)) {
                    cssb.setBaidu_restaurant_id(value);
                }
                if ("baidu_url".equals(key)) {
                    cssb.setBaidu_url(value);
                }
                if ("dispatch_extinfo".equals(key)) {
                    cssb.setDispatch_extinfo(value);
                }

            }

        }
        String stores_id = cssb.getStores_id() + "";
        String stores_brand_id = cssb.getStores_brand_id() + "";
        String restaurant_id = cssb.getElem_restaurant_id();
        int i = 0;
        String message = "操作失败";
        if (opt.equals("add")) {
            //默认配送方式为平台配送
            JSONObject jo = new JSONObject();
            jo.put("send_type1", "0");
            jo.put("send_type2", "0");
            jo.put("send_type3", "0");
            jo.put("send_type4", "0");
            cssb.setDispatch_extinfo(jo.toJSONString());
            cssb.addUnParamFields("stores_brand_id");
            sqlDao.insertRecord(cssb);
            message = "保存成功";
            i = 1;
        } else {
            //绑定饿了么
            if (opt.equals("bind")) {
                if (!StringUtils.isEmpty(cssb.getElem_restaurant_id())) {
                    try {
                        String result = APIFactoryEleme.getShippingAPI().elestoresBind(SystemConfig.GetSystemParamEleme(), stores_brand_id, restaurant_id.trim());
                        JSONObject jo = JSONObject.parseObject(result);
                        if (jo.getString("message").equals("ok")) {
                            storesbrandservice.elestoresBind(cssb);
                            i = 1;
                            message = "绑定成功";
                        } else if (jo.getString("code").equals("1000")) {
                            i = 2;
                            message = "请检查您的饿了么绑定id是否输入正确";
                        } else if (jo.getString("code").equals("1004")) {
                            i = 3;
                            message = "已经绑定";
                        }
                    } catch (ApiOpException e) {
                        e.printStackTrace();
                    } catch (ApiSysException e) {
                        e.printStackTrace();
                    }
                }
                //重新绑定
            } else if (opt.equals("bindaresh")) {
                if (!StringUtils.isEmpty(cssb.getElem_restaurant_id())) {
                    try {
                        String result = APIFactoryEleme.getShippingAPI().elestoresBindAresh(SystemConfig.GetSystemParamEleme(), stores_brand_id, restaurant_id.trim());
                        JSONObject jo = JSONObject.parseObject(result);
                        if (jo.getString("message").equals("ok")) {
                            storesbrandservice.elestoresBind(cssb);
                            i = 1;
                            message = "重新绑定成功";
                        } else if (jo.getString("code").equals("1004")) {
                            i = 2;
                            message = "已经重新绑定该商户了";
                        } else if (jo.getString("code").equals("1000")) {
                            message = "请检查您的饿了么绑定id是否输入正确";
                        }
                    } catch (ApiOpException e) {
                        e.printStackTrace();
                    } catch (ApiSysException e) {
                        e.printStackTrace();
                    }
                }
                //美团绑定
            } else if (opt.equals("meituanbind")) {
                try {
                    String result = APIFactoryMeituan.getPoiAPI().poiGetIds(SystemConfig.GetSystemParamMeituan());

                    CdsStores cs = new CdsStores();
                    cs.setStores_id(cssb.getStores_id());
                    cs.addConditionField("stores_id");
                    cs = sqlDao.getRecord(cs);
                    PoiParam param = BeanUtil.createBean(JSONObject.toJSONString(cs), PoiParam.class);
//                BeanUtils.copyProperties(param,cs);
//                APIFactoryMeituan.getPoiAPI().poiSave(SystemConfig.GetSystemParamMeituan(), );
                } catch (com.opensdk.meituan.exception.ApiOpException e) {
                    e.printStackTrace();
                } catch (com.opensdk.meituan.exception.ApiSysException e) {
                    e.printStackTrace();
                }
            } else {
                //修改
                cssb.addUnParamFields("stores_brand_id,dispatch_extinfo");
                cssb.addConditionField("stores_brand_id");
                sqlDao.updateRecord(cssb);
                i = 1;
                message = "修改成功";
            }
        }
        return new JsonMessage(i, message);
    }


    @RequestMapping(value = "elestoresBindQuery", method = RequestMethod.POST)
    @ResourceMethod(name = "查询饿了么绑定信息", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage elestoresBindQuery(@RequestParam String stores_brand_id) {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        try {
            String result = APIFactoryEleme.getShippingAPI().elestoresBindQuery(SystemConfig.GetSystemParamEleme(), stores_brand_id);
            JSONObject jo = JSONObject.parseObject(result);
            if ("200".equals(jo.getString("code")) && jo.getString("message").equals("ok")) {
                jmsg.setStatus("1");
                JSONObject jo2 = JSONObject.parseObject(jo.getString("data"));
                jmsg.setMessage(jo2.getString("restaurant_id"));
            }
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
        return jmsg;
    }

    @RequestMapping(value = "meituanstoresBindQuery", method = RequestMethod.POST)
    @ResourceMethod(name = "查询美团绑定信息", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage meituanstoresBindQuery(@RequestParam String stores_brand_id) {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        try {
            List<PoiParam> list = APIFactoryMeituan.getPoiAPI().poiMget(SystemConfig.GetSystemParamMeituan(), stores_brand_id);
//                StringBuffer sb = new StringBuffer();
//                sb.append(result);
//            result = result.replace("[", "");
//            result = result.replace("]", "");
//            String ids[] = result.split(",");
//            for (int i = 0; i < ids.length; i++) {
//                String id = ids[i].substring(1, (ids[i].length() - 1));
//                if (stores_id.equals(id)) {
//                    jmsg.setStatus("1");
//                    break;
//                }
//            }
            if (list != null && list.size() > 0) {
                jmsg.setStatus("1");
            }
        } catch (com.opensdk.meituan.exception.ApiOpException e) {
            e.printStackTrace();
        } catch (com.opensdk.meituan.exception.ApiSysException e) {
            e.printStackTrace();
        }
        return jmsg;
    }

    @RequestMapping(value = "elestoresModeQuery", method = RequestMethod.POST)
    @ResourceMethod(name = "查询饿了接单模式", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage elestoresModeQuery(@RequestParam String elem_restaurant_id) {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        try {
            String result = APIFactoryEleme.getShippingAPI().elestoresModeQuery(SystemConfig.GetSystemParamEleme(), elem_restaurant_id);
            JSONObject jo = JSONObject.parseObject(result);
            if ("200".equals(jo.getString("code")) && jo.getString("message").equals("ok")) {
                JSONObject jo2 = JSONObject.parseObject(jo.getString("data"));
                String obj = jo2.getString("batch_status");
                JSONObject jo3 = JSONObject.parseObject(obj);
                boolean isok = jo3.containsKey(elem_restaurant_id);
                if (isok) {
                    String obj2 = jo3.getString(elem_restaurant_id);
                    JSONObject jo4 = JSONObject.parseObject(obj2);
                    String order_mode = jo4.getString("order_mode");
                    if ("1".equals(order_mode)) {
                        jmsg.setMessage("开放平台接单");
                        jmsg.setStatus("1");
                    } else if ("2".equals(order_mode)) {
                        jmsg.setMessage("饿了么商家版后台接单");
                        jmsg.setStatus("2");
                    } else if ("3".equals(order_mode)) {
                        jmsg.setMessage("饿了么商家版的android客户端接单");
                        jmsg.setStatus("3");
                    } else if ("4".equals(order_mode)) {
                        jmsg.setMessage("饿了么商家版的ios客户端接单");
                        jmsg.setStatus("4");
                    }
                }
            }
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
        return jmsg;
    }

    @RequestMapping(value = "elestoresBindMode", method = RequestMethod.POST)
    @ResourceMethod(name = "绑定饿了接单模式", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage elestoresBindMode(@RequestParam String restaurant_id, @RequestParam String order_mode) {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        try {
            String result = APIFactoryEleme.getShippingAPI().elestoresMode(SystemConfig.GetSystemParamEleme(), restaurant_id, order_mode);
            JSONObject jo = JSONObject.parseObject(result);
            if ("200".equals(jo.getString("code")) && jo.getString("message").equals("ok")) {
                jmsg.setStatus("1");
                jmsg.setMessage("操作成功");
            }
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
        return jmsg;
    }

    @RequestMapping(value = "stores_login", method = RequestMethod.GET)
    @ResourceMethod(name = "打包客户端配置", check = CHECK_LOGIN)
    public String stores_login() throws Exception {
        int stores_id = WebUtil.getUser().getStores_id();
        if(stores_id==0){
            List cdsshops = sqlDao.getRecordList("cds_stores.orderOnTimeStores", null);
            WebUtil.setRequest("cdsshops", cdsshops);
        }else{
            Map param = new HashMap<>();
            param.put("stores_id",stores_id);
            List cdsshops = sqlDao.getRecordList("cds_stores.orderOnTimeStores", param);
            WebUtil.setRequest("cdsshops", cdsshops);
        }
        return "stores/stores_login";
    }

    @RequestMapping(value = "stores_login_grid", method = RequestMethod.POST)
    @ResourceMethod(name = "打包客户端列表", check = CHECK_LOGIN)
    @ResponseBody
    public void stores_login_grid(@RequestParam HashMap formInfo) throws Exception {
        if(WebUtil.getSession("stores_id") != null)
            formInfo.put("stores_id",WebUtil.getSession("stores_id"));
        else formInfo.put("stores_id",WebUtil.getUser().getStores_id());
        queryAndResponsePage("cds_stores.getStoresLoginPageRecord", formInfo);
    }

    @RequestMapping(value = "saveStoresLogin", method = RequestMethod.POST)
    @ResourceMethod(name = "保存打包客户端信息", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage saveStoresLogin(String opt, String id, String stores_id, String username, String pass,String print_format_id) {
        CdsStoresLogin csl = new CdsStoresLogin();
        if (!StringUtils.isEmpty(stores_id)) {
            csl.setStores_id(Integer.valueOf(stores_id));
        }
        csl.setUsername(username);
        if(!StringUtils.isEmpty(pass)){
            String password = "###" + StringUtil.MD5(StringUtil.MD5(SystemConstant.passKey + pass));
            csl.setPass(password);
        }
        csl.setPrint_format_id(Integer.valueOf(print_format_id));
        if (!StringUtils.isEmpty(id)) {
            csl.setId(Integer.valueOf(id));
        }
        if (opt.equals("add")) {
            csl.addUnParamFields("id");
            sqlDao.insertRecord(csl);
        } else {
            if(StringUtils.isEmpty(pass)){
                csl.addUnParamFields("id,pass");
            }else{
                csl.addUnParamFields("id");
            }
            csl.addConditionField("id");
            sqlDao.updateRecord(csl);
        }
        return new JsonMessage(1);
    }

    @RequestMapping(value = "updateDispatchExtinfo", method = RequestMethod.POST)
    @ResourceMethod(name = "修改配送方式", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage updateDispatchExtinfo(String stores_brand_id, String dispatch_extinfo, String sendtype) {
        CdsStoresBrand cs = new CdsStoresBrand();
        if (!StringUtils.isEmpty(stores_brand_id)) {
            cs.setStores_brand_id(Integer.valueOf(stores_brand_id));
            cs = sqlDao.getRecord(cs);
            if (cs != null) {
                if (cs.getDispatch_extinfo() != null) {
                    JSONObject jo = JSONObject.parseObject(cs.getDispatch_extinfo());
                    jo.put(sendtype, dispatch_extinfo);
                    dispatch_extinfo = jo.toJSONString();
                } else {
                    JSONObject jo = new JSONObject();
                    jo.put("send_type1", "0");
                    jo.put("send_type2", "0");
                    jo.put("send_type3", "0");
                    jo.put("send_type4", "0");
                    jo.put(sendtype, dispatch_extinfo);
                    dispatch_extinfo = jo.toJSONString();
                }
                cs.setDispatch_extinfo(dispatch_extinfo);
                cs.addConditionField("stores_brand_id");
                cs.addParamField("dispatch_extinfo");
                sqlDao.updateRecord(cs);
            } else {
                new JsonMessage(0);
            }
            sqlDao.updateRecord(cs);
        }
        return new JsonMessage(1);
    }


    @RequestMapping(value = "validateStores", method = RequestMethod.POST)
    @ResourceMethod(name = "验证是否重复", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage validateStores(String brand_id, String stores_id) {
        CdsStoresBrand csb = new CdsStoresBrand();
        JsonMessage jm = new JsonMessage();
        if (!StringUtils.isEmpty(stores_id)) {
            csb.setStores_id(Integer.valueOf(stores_id));
        }
        if(!StringUtils.isEmpty(brand_id)){
            csb.setBrand_id(Integer.valueOf(brand_id));
        }
        csb.addConditionFields("stores_id,brand_id");
        csb = sqlDao.getRecord(csb);
        if(csb!=null){
            jm.setStatus("0");
        }else{
            jm.setStatus("1");
        }

        return jm;
    }









    @RequestMapping(value = "elemeQuery", method = RequestMethod.POST)
    @ResourceMethod(name = "查询饿了么绑定信息", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage elemeQuery(@RequestParam String elem_restaurant_id,String stores_brand_id) {
        JsonMessage jm = new JsonMessage();
        jm.setStatus("0");
        if (!StringUtils.isEmpty(elem_restaurant_id)&&!StringUtils.isEmpty(stores_brand_id)){
            CdsStoresBrand csb = new CdsStoresBrand();
            csb.setElem_restaurant_id(elem_restaurant_id);
            csb.setStores_brand_id(Integer.valueOf(stores_brand_id));
            csb.addConditionFields("elem_restaurant_id,stores_brand_id");
            csb = sqlDao.getRecord(csb);
            if (csb!=null){
                jm.setStatus("1");
            }
        }
        return jm;
    }


    @RequestMapping(value = "elemeBind", method = RequestMethod.POST)
    @ResourceMethod(name = "饿了么绑定", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage elemeBind(@RequestParam HashMap hashMap) {
        JsonMessage jm = new JsonMessage();
        jm.setStatus("0");
        ShopService shopService = new ShopService(SystemConfig.GetEleme2Config(), getElemeToken());
        String stores_brand_id = hashMap.get("stores_brand_id").toString();
        String shopIds = hashMap.get("shopId").toString();
        long shopId = 157110517;
       CdsStoresBrand csb = new CdsStoresBrand();
       if (!StringUtils.isEmpty(stores_brand_id)){
           csb.setStores_brand_id(Integer.valueOf(stores_brand_id));
           csb.addConditionFields("stores_brand_id");
           csb = sqlDao.getRecord(csb);
           if (csb!=null){
               try {
                   OShop oShop = shopService.getShop(shopId);
                   Map<OShopProperty,Object> properties = new HashMap<OShopProperty,Object>();
//                   properties.put(OShopProperty.openId,Integer.valueOf(stores_brand_id));

//                   shopService.updateShop(shopId, properties);
                   csb.setElem_restaurant_id(shopId+"");
                   csb.addConditionFields("stores_brand_id");
                   csb.addUnParamFields("stores_brand_id,stores_id,brand_id");
//                   sqlDao.updateRecord(csb);
                   jm.setStatus("1");
               } catch (ServiceException e) {
                   String message = e.getMessage();
                   String code = e.getCode();
                   if("UNAUTHORIZED".equals(code)||"token认证失败,请重新申请token".equals(message)){
                       deleteRedis("eleme_token");
                   }
                   e.printStackTrace();
               }
           }
       }
        return jm;
    }






}
