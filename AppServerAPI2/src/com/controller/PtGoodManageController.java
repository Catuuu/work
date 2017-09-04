package com.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.framework.annotation.ResourceMethod;
import com.framework.controller.BasicController;
import com.framework.mapping.JsonMessage;
import com.framework.mapping.system.*;
import com.framework.system.SystemConfig;
import com.framework.util.DateUtil;
import com.framework.util.StringAlphas;
import com.framework.util.WebUtil;
import com.opensdk.baidu.api.Category;
import com.opensdk.baidu.api.Shop;
import com.opensdk.baidu.util.BaiduUtils;
import com.opensdk.baidu.util.HttpUtils;
import com.opensdk.eleme.exception.ApiOpException;
import com.opensdk.eleme.exception.ApiSysException;
import com.opensdk.eleme.factory.APIFactoryEleme;
import com.opensdk.eleme2.api.entity.product.*;
import com.opensdk.eleme2.api.enumeration.product.OItemCreateProperty;
import com.opensdk.eleme2.api.enumeration.product.OItemUpdateProperty;
import com.opensdk.eleme2.api.enumeration.product.OItemWeekEnum;
import com.opensdk.eleme2.api.exception.ServiceException;
import com.opensdk.eleme2.api.service.ProductService;
import com.opensdk.meituan.factory.APIFactoryMeituan;
import com.opensdk.meituan.vo.*;
import com.service.StoresBrandService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import static com.framework.annotation.CheckType.CHECK_LOGIN;

/**
 * Created by c on 2017-02-02.
 */
@Controller
@RequestMapping("PtGoodsManage")
public class PtGoodManageController extends BasicController {

    @Resource(name = "StoresBrandService")
    protected StoresBrandService storesbrandservice;


    @RequestMapping(value = "index", method = RequestMethod.GET)
    @ResourceMethod(name = "平台商品", check = CHECK_LOGIN)
    public String index() throws Exception {
        CdsClass cc = new CdsClass();
        CdsClassType cct = new CdsClassType();
        List<CdsClassType> classTypeList = sqlDao.getRecordList(cct);
        WebUtil.getRequest().setAttribute("classtypelist", classTypeList);
        List<CdsClass> classList = sqlDao.getRecordList(cc);
        CdsBrand cdsBrand = new CdsBrand();
        List<CdsBrand> brandlist = sqlDao.getRecordList(cdsBrand);
        WebUtil.getRequest().setAttribute("brandlist", brandlist);
        WebUtil.getRequest().setAttribute("classlist", classList);
        return "ptGoodsManage/index";
    }



    @RequestMapping(value = "GetStoresTree", method = RequestMethod.POST)
    @ResourceMethod(name = "获取品牌对应的商铺", check = CHECK_LOGIN)
    @ResponseBody
    public List GetStoresTree() throws Exception {
        CdsBrand cb = new CdsBrand();
        List storesList = new ArrayList();
        List<CdsBrand> cbList = sqlDao.getRecordList(cb);
        List<JSONObject> jolist = new ArrayList<JSONObject>();
        if(cbList!=null&&cbList.size()>0){
            for(int i=0;i<cbList.size();i++){
                JSONObject jo = new JSONObject();
                cb = cbList.get(i);
                jo.put("id",cb.getBrand_id());
                jo.put("text",cb.getBrand_name());
                jo.put("iconCls","icon icon-104");
                jo.put("state","open");
                storesList = sqlDao.getRecordList("cds_ptstores_manage.GetStoresTree",cb);
                jo.put("children",storesList);
                jolist.add(jo);
            }
        }
        return jolist;
    }

    @RequestMapping(value = "GetGoodsTree", method = RequestMethod.POST)
    @ResourceMethod(name = "获取上架的商品信息", check = CHECK_LOGIN)
    @ResponseBody
    public List GetGoodsTree() throws Exception {
        CdsGoodsInfo cgi = new CdsGoodsInfo();
        CdsClass cc = new CdsClass();
        cgi.setStatus(1);
        cgi.addConditionField("status");
        List storesList = new ArrayList();
        List<CdsGoodsInfo> cgiList = sqlDao.getRecordList(cgi);
        List<CdsClass> ccList = sqlDao.getRecordList(cc);
        List<JSONObject> jolist = new ArrayList<JSONObject>();
        if(ccList!=null&&ccList.size()>0){
            for(int i=0;i<ccList.size();i++){
                JSONObject jo = new JSONObject();
                List<JSONObject> goodslist = new ArrayList<JSONObject>();
                if(cgiList!=null&&cgiList.size()>0){
                    for (int j=0;j<cgiList.size();j++){
                        JSONObject jo2 = new JSONObject();
                        Integer class_id1 = ccList.get(i).getClass_id();
                        Integer class_id2 = cgiList.get(j).getClass_id();
                        if(class_id1.equals(class_id2)){
                            jo2.put("id",cgiList.get(j).getGood_id());
                            jo2.put("text",cgiList.get(j).getGood_name());
                            jo2.put("iconCls","icon icon-367");
                            goodslist.add(jo2);
                            jo.put("id",ccList.get(i).getClass_id());
                            jo.put("text",ccList.get(i).getClass_name());
                            jo.put("children",goodslist);
                            jo.put("state","closed");
                            jo.put("iconCls","icon icon-104");
                        }
                    }
                    if(jo.size()>0){
                        jolist.add(jo);
                    }
                }
            }
        }
        return jolist;
    }

    @RequestMapping(value = "GetStoresGoodsList", method = RequestMethod.POST)
    @ResourceMethod(name = "商铺包含商品明细", check = CHECK_LOGIN)
    @ResponseBody
    public void GetStoresGoodsList(@RequestParam HashMap formInfo) throws Exception {

        queryAndResponsePage("cds_ptstores_manage.GetStoresGoodsList",formInfo);
       // queryAndResponsePage("cds_stores.GetGoodsInfoErp", formInfo);
    }

    @RequestMapping(value = "updateStoresGoods", method = RequestMethod.POST)
    @ResourceMethod(name = "修改商品明细", check = CHECK_LOGIN)
    @ResponseBody
    public void updateStoresGoods(String sgl_id,String elem_count,String mt_count,String market_price,String box_price,String box_count,String wx_count,String all_count,String unit,String name,String good_info,String image_hash,String stores_brand_id,String class_name) {
        CdsStoresGoodsList csg = new CdsStoresGoodsList();
        int eisok = 0;
        int misok= 0;
        if(!StringUtils.isEmpty(sgl_id)){
            csg.setSgl_id(Integer.valueOf(sgl_id));
            csg.addConditionField("sgl_id");
            csg =  sqlDao.getRecord(csg);
            if(!StringUtils.isEmpty(csg.getFood_id())){
                //更新饿了么
                try {
                    String result = APIFactoryEleme.getFoodAPI().eleUpdateFood(SystemConfig.GetSystemParamEleme(),"",csg.getFood_id(),name,market_price,good_info,elem_count, image_hash,box_price);
                    JSONObject jo = JSONObject.parseObject(result);
                    if ("200".equals(jo.getString("code")) && jo.getString("message").equals("ok")) {
                        eisok = 1;
                    }
                } catch (ApiOpException e) {
                    e.printStackTrace();
                } catch (ApiSysException e) {
                    e.printStackTrace();
                }
            }
            //更新美团
            if(!StringUtils.isEmpty(csg.getMt_isband())){
                FoodParam foodParam = new FoodParam();
                foodParam.setApp_poi_code(csg.getStores_brand_id()+"");
                foodParam.setApp_food_code(csg.getMt_isband());
                foodParam.setName(name);
                foodParam.setPrice(Float.valueOf(market_price));
                foodParam.setUnit(unit);
                foodParam.setBox_price(Float.valueOf(box_price));
                foodParam.setBox_num(Float.valueOf(box_count));
                foodParam.setDescription(good_info);
                foodParam.setCategory_name(class_name);
                foodParam.setMin_order_count(1);
                try {
                    String result = APIFactoryMeituan.getFoodAPI().foodSave(SystemConfig.GetSystemParamMeituan(),foodParam);
                    //更新库存
                    List<skuStockParam> list = new ArrayList<skuStockParam>();
                    skuStockParam ssp = new skuStockParam();
                    ssp.setStock(mt_count);
                    ssp.setSku_id(csg.getSgl_id()+"");
                    list.add(ssp);
                    FoodSkuStockParam fsp = new FoodSkuStockParam();
                    fsp.setApp_food_code(csg.getMt_isband()+"");
                    fsp.setSkus(list);
                    List<FoodSkuStockParam> list2 = new ArrayList<FoodSkuStockParam>();
                    list2.add(fsp);
                    String result2 = APIFactoryMeituan.getFoodAPI().updateFoodSkuStock(SystemConfig.GetSystemParamMeituan(),csg.getStores_brand_id()+"",list2);
                    if ("ok".equals(result)) {
                        misok = 1;
                    }
                } catch (com.opensdk.meituan.exception.ApiOpException e) {
                    e.printStackTrace();
                } catch (com.opensdk.meituan.exception.ApiSysException e) {
                    e.printStackTrace();
                }
            }

            if(!StringUtils.isEmpty(elem_count)){
                csg.setElem_count(Integer.valueOf(elem_count));
            }
            if(!StringUtils.isEmpty(mt_count)){
                csg.setMt_count(Integer.valueOf(mt_count));
            }
            if(!StringUtils.isEmpty(wx_count)){
                csg.setWx_count(Integer.valueOf(wx_count));
            }
            if(!StringUtils.isEmpty(all_count)){
                csg.setAll_count(Integer.valueOf(all_count));
            }
            if(!StringUtils.isEmpty(market_price)){
                csg.setMarket_price(Float.valueOf(market_price));
            }
            if(!StringUtils.isEmpty(box_price)){
                csg.setBox_price(Float.valueOf(box_price));
            }
            if(!StringUtils.isEmpty(box_count)){
                csg.setBox_count(Integer.valueOf(box_count));
            }
            if(!StringUtils.isEmpty(unit)){
                csg.setUnit(unit);
            }
            csg.addUnParamFields("sgl_id");
            csg.addConditionField("sgl_id");
            sqlDao.updateRecord(csg);
        }


//        return new JsonMessage(1);
    }


    @RequestMapping(value = "queryGoods", method = RequestMethod.GET)
    @ResourceMethod(name = "用户查询页面", check = CHECK_LOGIN)
    public String queryGoods() throws Exception {
//        CdsClass cc = new CdsClass();
//        List<CdsClass> classList = sqlDao.getRecordList(cc);
//        CdsClassType cct = new CdsClassType();
//        List<CdsClassType> classTypeList = sqlDao.getRecordList(cct);
        CdsBrand cdsBrand = new CdsBrand();
        List<CdsBrand> brandlist = sqlDao.getRecordList(cdsBrand);
        WebUtil.getRequest().setAttribute("brandlist", brandlist);
//        WebUtil.getRequest().setAttribute("classtypelist", classTypeList);
//        WebUtil.getRequest().setAttribute("classlist", classList);
        return "ptGoodsManage/class_index";
    }


    @RequestMapping(value = "GetGoodsClassList", method = RequestMethod.POST)
    @ResourceMethod(name = "商品分类", check = CHECK_LOGIN)
    @ResponseBody
    public void GetGoodsClassList(@RequestParam HashMap formInfo) throws Exception {
        queryAndResponsePage("cds_ptstores_manage.GetGoodsClassList",formInfo);
        // queryAndResponsePage("cds_stores.GetGoodsInfoErp", formInfo);
    }

    @RequestMapping(value = "GetClassList", method = RequestMethod.POST)
    @ResourceMethod(name = "获取品牌下分类", check = CHECK_LOGIN)
    @ResponseBody
    public void GetClassList(@RequestParam HashMap formInfo) throws Exception {
//        List<CdsClass> list = new ArrayList<CdsClass>();
//        String ids =formInfo.get("ids").toString();
//        String[] class_ids = ids.split(",");
//        for (int i=0;i<class_ids.length;i++){
//            CdsClass cc = new CdsClass();
//            cc.setClass_id(Integer.valueOf(class_ids[i]));
//            list.add(cc);
//        }
//        formInfo.put("list",list);
        queryAndResponsePage("cds_ptstores_manage.GetClassList",formInfo);
        // queryAndResponsePage("cds_stores.GetGoodsInfoErp", formInfo);
    }

    @RequestMapping(value = "saveStoresClass", method = RequestMethod.POST)
    @ResourceMethod(name = "保存门店类别明细", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage saveStoresClass(@RequestParam HashMap hashMap) {
        CdsStoresClass csc = new CdsStoresClass();
        Iterator it = hashMap.entrySet().iterator();
        List<CdsStoresClass> list = new ArrayList<CdsStoresClass>();
        int i =0;
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            if(!StringUtils.isEmpty(value)){
                csc.setType(1);
                if(("rows"+'['+i+']'+"[stores_brand_id]").equals(key)){
                    csc.setStores_brand_id(Integer.valueOf(value));
                }
                if(("rows"+'['+i+']'+"[class_id]").equals(key)){
                    csc.setClass_id(Integer.valueOf(value));
                }
            }
            if(!"0".equals(csc.getStores_brand_id()+"")&&!"0".equals(csc.getClass_id()+"")){
                csc.addUnParamFields("stores_class_id");
                sqlDao.insertRecord(csc);
                csc = new CdsStoresClass();
                ++i;
            }
        }
        return new JsonMessage(1);
    }


    @RequestMapping(value = "elemClassBind", method = RequestMethod.POST)
    @ResourceMethod(name = "饿了么绑定", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage elemClassBind(@RequestParam String stores_class_id,String elem_restaurant_id,String class_name,String listorder) {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        try {
            String result = APIFactoryEleme.getFoodAPI().eleGoodClassBind(SystemConfig.GetSystemParamEleme(), elem_restaurant_id,class_name);
            JSONObject jo = JSONObject.parseObject(result);
            if ("200".equals(jo.getString("code")) && jo.getString("message").equals("ok")) {
                jmsg.setStatus("1");
                JSONObject jo2 = JSONObject.parseObject(jo.getString("data"));
                jmsg.setMessage(jo2.getString("food_category_id"));
                CdsStoresClass csc = new CdsStoresClass();
                if(!StringUtils.isEmpty(stores_class_id)){
                    csc.setStores_class_id(Integer.valueOf(stores_class_id));
                    csc.addConditionField(stores_class_id);
                    csc = sqlDao.getRecord(csc);
                    csc.setFood_category_id(jo2.getString("food_category_id"));
                    csc.addUnParamFields("stores_class_id");
                    csc.addConditionField("stores_class_id");
                    sqlDao.updateRecord(csc);
                }
            }
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
        return jmsg;
    }


    @RequestMapping(value = "eleGoodClassBindUpdate", method = RequestMethod.POST)
    @ResourceMethod(name = "饿了么食物分类更新", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage eleGoodClassBindUpdate(@RequestParam String food_category_id,String class_name) {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        try {
            String result = APIFactoryEleme.getFoodAPI().eleGoodClassBindUpdate(SystemConfig.GetSystemParamEleme(), food_category_id,class_name);
            JSONObject jo = JSONObject.parseObject(result);
            if ("200".equals(jo.getString("code")) && jo.getString("message").equals("ok")) {
                jmsg.setStatus("1");
            }
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
        return jmsg;
    }

    @RequestMapping(value = "eleGoodClassBindQuery", method = RequestMethod.POST)
    @ResourceMethod(name = "饿了么食物分类查询", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage eleGoodClassBindQuery(@RequestParam String food_category_id) {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        try {
            String result = APIFactoryEleme.getFoodAPI().eleGoodClassBindQuery(SystemConfig.GetSystemParamEleme(), food_category_id);
            JSONObject jo = JSONObject.parseObject(result);
            if ("200".equals(jo.getString("code")) && jo.getString("message").equals("ok")) {
                jmsg.setStatus("1");
            }
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
        return jmsg;
    }


    @RequestMapping(value = "eleGoodClassBindDelete", method = RequestMethod.POST)
    @ResourceMethod(name = "饿了么食物分类解绑", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage eleGoodClassBindDelete(@RequestParam String food_category_id,String stores_class_id) {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        try {
            String result = APIFactoryEleme.getFoodAPI().eleGoodClassBindDelete(SystemConfig.GetSystemParamEleme(), food_category_id);
            JSONObject jo = JSONObject.parseObject(result);
            if ("200".equals(jo.getString("code")) && jo.getString("message").equals("ok")) {
                jmsg.setStatus("1");
                CdsStoresClass csc = new CdsStoresClass();
                if(!StringUtils.isEmpty(stores_class_id)){
                    csc.setStores_class_id(Integer.valueOf(stores_class_id));
                    csc.addConditionField(stores_class_id);
                    csc = sqlDao.getRecord(csc);
                    csc.setFood_category_id("");
                    csc.addUnParamFields("stores_class_id");
                    csc.addConditionField("stores_class_id");
                    sqlDao.updateRecord(csc);
                }
            }
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
        return jmsg;
    }


    @RequestMapping(value = "meituanClassQuery", method = RequestMethod.POST)
    @ResourceMethod(name = "查询美团食物分类信息", check = CHECK_LOGIN)
    @ResponseBody
    public List meituanClassQuery(@RequestParam String stores_brand_id) {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        List<JSONObject> listjo = new ArrayList<JSONObject>();
        try {
            List<FoodCatParam> list = APIFactoryMeituan.getFoodAPI().foodCatList(SystemConfig.GetSystemParamMeituan(), stores_brand_id);
            if(list!=null&&list.size()>0){
                for (int i=0;i<list.size();i++){
                    JSONObject jo = new JSONObject();
                    CdsStoresClass csc = new CdsStoresClass();
                    csc.setStores_brand_id(Integer.valueOf(list.get(i).getApp_poi_code()));
                    csc.setMeituan_cat_id(list.get(i).getName());
                    csc.addConditionFields("stores_brand_id,meituan_cat_id");
                    csc=sqlDao.getRecord(csc);
                    if(csc!=null){
                        jo.put("mstatus","0");
                    }else{
                        jo.put("mstatus","1");
                    }
                    jo.put("stores_brand_id",list.get(i).getApp_poi_code());
                    jo.put("name",list.get(i).getName());
                    jo.put("sequence",list.get(i).getSequence());
                    jo.put("c_id",i);
                    listjo.add(jo);
                }
            }

        } catch (com.opensdk.meituan.exception.ApiOpException e) {
            e.printStackTrace();
        } catch (com.opensdk.meituan.exception.ApiSysException e) {
            e.printStackTrace();
        }
        return listjo;
    }


    @RequestMapping(value = "mtClassBind", method = RequestMethod.POST)
    @ResourceMethod(name = "美团绑定", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage mtClassBind(@RequestParam String stores_class_id,String stores_brand_id,String class_name,String listorder,String category_name_origin) {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        int sequence=0;
        if(!StringUtils.isEmpty(listorder)){
            sequence = Integer.valueOf(listorder);
        }
        String result = null;
        try {
            result = APIFactoryMeituan.getFoodAPI().foodCatUpdate(SystemConfig.GetSystemParamMeituan(), stores_brand_id,category_name_origin,class_name,sequence);
            if ("ok".equals(result)) {
                jmsg.setStatus("1");
                CdsStoresClass csc = new CdsStoresClass();
                if(!StringUtils.isEmpty(stores_class_id)){
                    csc.setStores_class_id(Integer.valueOf(stores_class_id));
                    csc.addConditionField(stores_class_id);
                    csc = sqlDao.getRecord(csc);
                    csc.setMeituan_cat_id(class_name);
                    csc.addUnParamFields("stores_class_id");
                    csc.addConditionFields("stores_class_id");
                    sqlDao.updateRecord(csc);
                }
            }
        } catch (com.opensdk.meituan.exception.ApiOpException e) {
            e.printStackTrace();
        } catch (com.opensdk.meituan.exception.ApiSysException e) {
            e.printStackTrace();
        }
        return jmsg;
    }

    @RequestMapping(value = "mtClassRemoveBind", method = RequestMethod.POST)
    @ResourceMethod(name = "美团解绑", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage mtClassRemoveBind(@RequestParam String stores_class_id,String stores_brand_id,String class_name) {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        try {
            String result = APIFactoryMeituan.getFoodAPI().foodCatDelete(SystemConfig.GetSystemParamMeituan(), stores_brand_id,class_name);
            if ("ok".equals(result)) {
                jmsg.setStatus("1");
                CdsStoresClass csc = new CdsStoresClass();
                if(!StringUtils.isEmpty(stores_class_id)){
                    csc.setStores_class_id(Integer.valueOf(stores_class_id));
                    csc.addConditionField(stores_class_id);
                    csc = sqlDao.getRecord(csc);
                    csc.setMeituan_cat_id("");
                    csc.addUnParamFields("stores_class_id");
                    csc.addConditionFields("stores_class_id");
                    sqlDao.updateRecord(csc);
                }
            }
        } catch (com.opensdk.meituan.exception.ApiOpException e) {
            e.printStackTrace();
        } catch (com.opensdk.meituan.exception.ApiSysException e) {
            e.printStackTrace();
        }
        return jmsg;
    }

    @RequestMapping(value = "deleteMT", method = RequestMethod.POST)
    @ResourceMethod(name = "美团分类删除", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage deleteMT(@RequestParam String stores_brand_id,String class_name) {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        try {
            String result = APIFactoryMeituan.getFoodAPI().foodCatDelete(SystemConfig.GetSystemParamMeituan(), stores_brand_id,class_name);
            if ("ok".equals(result)) {
                jmsg.setStatus("1");
            }
        } catch (com.opensdk.meituan.exception.ApiOpException e) {
            e.printStackTrace();
        } catch (com.opensdk.meituan.exception.ApiSysException e) {
            e.printStackTrace();
        }
        return jmsg;
    }

    @RequestMapping(value = "reverseBindMT", method = RequestMethod.POST)
    @ResourceMethod(name = "美团反向绑定", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage reverseBindMT(@RequestParam String stores_brand_id,String class_name,String class_id,String category_name,String sequence) {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        CdsStoresClass csc = new CdsStoresClass();
        csc.setStores_brand_id(Integer.valueOf(stores_brand_id));
        int sequence2 =0;
        if(!StringUtils.isEmpty(sequence)){
           sequence2 = Integer.valueOf(sequence);
        }
        try {
            String result = APIFactoryMeituan.getFoodAPI().foodCatUpdate(SystemConfig.GetSystemParamMeituan(), stores_brand_id,class_name,category_name,sequence2);
            if ("ok".equals(result)) {
                int class_id2 = 0;
                if(!StringUtils.isEmpty(class_id)){
                    class_id2 = Integer.valueOf(class_id);
                }
                csc.setClass_id(class_id2);
                csc.addConditionFields("stores_brand_id,class_id");
                csc = sqlDao.getRecord(csc);
                if(csc==null){
                    csc = new CdsStoresClass();
                    csc.setClass_id(class_id2);
                    csc.setMeituan_cat_id(category_name);
                    csc.setStores_brand_id(Integer.valueOf(stores_brand_id));
                    csc.addUnParamFields("stores_class_id");
                    sqlDao.insertRecord(csc);
                    jmsg.setStatus("1");
                }else{
                    csc.setMeituan_cat_id(category_name);
                    csc.addUnParamFields("stores_class_id");
                    csc.addConditionFields("stores_class_id");
                    sqlDao.updateRecord(csc);
                    jmsg.setStatus("1");
                }
            }
        } catch (com.opensdk.meituan.exception.ApiOpException e) {
            e.printStackTrace();
        } catch (com.opensdk.meituan.exception.ApiSysException e) {
            e.printStackTrace();
        }

        return jmsg;
    }

    /**
     * 根据类别名称获取id
     * @param name
     * @param stores_brand_id
     * @return id
     */
    public int getClassId(String name,String stores_brand_id){
        int class_id = 0;
        CdsClass cc = new CdsClass();
        CdsStoresBrand csb = new CdsStoresBrand();
        csb.setStores_brand_id(Integer.valueOf(stores_brand_id));
        csb.addConditionField("stores_brand_id");
        csb = sqlDao.getRecord(csb);
        int bid = csb.getBrand_id();
        cc.setBrand_id(bid);
        cc.setClass_name(name);
        cc.addConditionFields("brand_id,class_name");
        cc = sqlDao.getRecord(cc);
        if(cc!=null){
            class_id = cc.getClass_id();
        }

        return class_id;
    }


    @RequestMapping(value = "getElemClassList", method = RequestMethod.POST)
    @ResourceMethod(name = "获取饿了么分类列表", check = CHECK_LOGIN)
    @ResponseBody
    public List getElemClassList(@RequestParam String restaurant_id) {
        List<JSONObject> listjo = new ArrayList<JSONObject>();
        if(!StringUtils.isEmpty(restaurant_id)){
            try {
                String result  = APIFactoryEleme.getFoodAPI().eleStoresGoodClassQuery(SystemConfig.GetSystemParamEleme(), restaurant_id);
                JSONObject jo = JSONObject.parseObject(result);
                if ("200".equals(jo.getString("code")) && jo.getString("message").equals("ok")) {
                    String str = JSONObject.parseObject(jo.get("data").toString()).get("food_categories").toString();
                    List<com.opensdk.eleme.vo.FoodCatParam> foodCatParams = JSONArray.parseArray(str, com.opensdk.eleme.vo.FoodCatParam.class);
                    if(foodCatParams!=null&&foodCatParams.size()>0){
                        for (int i = 0; i <foodCatParams.size() ; i++) {
                            JSONObject jos = new JSONObject();
                            jos.put("food_category_id",foodCatParams.get(i).getFood_category_id());
                            jos.put("name",foodCatParams.get(i).getName());
                            jos.put("weight",foodCatParams.get(i).getWeight());
                            String food_category_id = foodCatParams.get(i).getFood_category_id();
                            CdsStoresClass csc = new CdsStoresClass();
                            csc.setFood_category_id(food_category_id);
                            csc.addConditionField("food_category_id");
                            csc = sqlDao.getRecord(csc);
                            if(csc!=null){
                                //已绑定
                                jos.put("estatus","0");
                            }else{
                                //未绑定
                                jos.put("estatus","1");
                            }
                            listjo.add(jos);
                        }
                    }

                }
            } catch (ApiOpException e) {
                e.printStackTrace();
            } catch (ApiSysException e) {
                e.printStackTrace();
            }
        }
        return listjo;
    }


    @RequestMapping(value = "deleteElem", method = RequestMethod.POST)
    @ResourceMethod(name = "饿了么分类删除", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage deleteElem(@RequestParam String food_category_id) {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        try {
            String result = APIFactoryEleme.getFoodAPI().eleGoodClassBindDelete(SystemConfig.GetSystemParamEleme(),food_category_id);
            JSONObject jo = JSONObject.parseObject(result);
            if ("200".equals(jo.getString("code")) && jo.getString("message").equals("ok")) {
                jmsg.setStatus("1");
            }
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }

        return jmsg;
    }


    @RequestMapping(value = "reverseBindElem", method = RequestMethod.POST)
    @ResourceMethod(name = "饿了么反向绑定", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage reverseBindElem(@RequestParam String stores_brand_id,String class_name,String food_category_id,String class_id) {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        CdsStoresClass csc = new CdsStoresClass();
        csc.setStores_brand_id(Integer.valueOf(stores_brand_id));

        try {
            String result = APIFactoryEleme.getFoodAPI().eleGoodClassBindUpdate(SystemConfig.GetSystemParamEleme(), food_category_id,class_name);
            JSONObject jo = JSONObject.parseObject(result);
            if ("200".equals(jo.getString("code")) && jo.getString("message").equals("ok")) {
                csc.setClass_id(Integer.valueOf(class_id));
                csc.addConditionFields("stores_brand_id,class_id");
                csc = sqlDao.getRecord(csc);
                if(csc==null){
                    csc = new CdsStoresClass();
                    csc.setClass_id(Integer.valueOf(class_id));
                    csc.setFood_category_id(food_category_id);
                    csc.setStores_brand_id(Integer.valueOf(stores_brand_id));
                    csc.setType(1);
                    csc.addUnParamFields("stores_class_id");
                    sqlDao.insertRecord(csc);
                    jmsg.setStatus("1");
                }else{
                    csc.addUnParamFields("stores_class_id");
                    csc.addConditionFields("stores_class_id");
                    csc.setFood_category_id(food_category_id);
                    csc.setType(1);
                    sqlDao.updateRecord(csc);
                    jmsg.setStatus("1");
                }
            }
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
        return jmsg;
    }


    @RequestMapping(value = "GetBrandTree", method = RequestMethod.POST)
    @ResourceMethod(name = "获取品牌对应分类", check = CHECK_LOGIN)
    @ResponseBody
    public List GetBrandTree(String brand_id) throws Exception {
        CdsBrand cb = new CdsBrand();
        CdsClass cc = new CdsClass();
        List storesList = new ArrayList();
        cb.setBrand_id(Integer.valueOf(brand_id));
        cb.addConditionField("brand_id");
        cb=sqlDao.getRecord(cb);
        cc.setBrand_id(Integer.valueOf(brand_id));
        cc.addConditionField("brand_id");
//        List<CdsBrand> ccList = sqlDao.getRecordList(cb);
        List<CdsClass> cgiList = sqlDao.getRecordList(cc);
        List<JSONObject> jolist = new ArrayList<JSONObject>();
        if(cb!=null){
            JSONObject jo = new JSONObject();
            if(cgiList!=null&&cgiList.size()>0){
                List<JSONObject> goodslist = new ArrayList<JSONObject>();
                for (int j=0;j<cgiList.size();j++){
                    JSONObject jo2 = new JSONObject();
                    jo2.put("id",cgiList.get(j).getClass_id());
                    jo2.put("text",cgiList.get(j).getClass_name());
                    jo2.put("sequence",cgiList.get(j).getListorder());
                    jo2.put("iconCls","icon icon-367");
                    goodslist.add(jo2);
                }
                jo.put("id",cb.getBrand_id());
                jo.put("text",cb.getBrand_name());
                jo.put("state","open");
                jo.put("iconCls","icon icon-104");
                jo.put("children",goodslist);
            }
            if(jo.size()>0){
                jolist.add(jo);
            }
        }
        return jolist;
    }


    @RequestMapping(value = "checkBind", method = RequestMethod.POST)
    @ResourceMethod(name = "判断分类是否绑定", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage checkBind(@RequestParam String class_id,String status) {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        CdsStoresClass csc = new CdsStoresClass();
        if(!StringUtils.isEmpty(class_id)){
            csc.setClass_id(Integer.valueOf(class_id));
            csc.addConditionField("class_id");
            csc = sqlDao.getRecord(csc);
            if("elem".equals(status)){
                if(!StringUtils.isEmpty(csc.getFood_category_id())){
                    jmsg.setStatus("1");
                }
            }else{
                if(!StringUtils.isEmpty(csc.getMeituan_cat_id())){
                    jmsg.setStatus("1");
                }
            }
        }
        return jmsg;
    }

    @RequestMapping(value = "sellStatus", method = RequestMethod.POST)
    @ResourceMethod(name = "修改售卖状态", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage sellStatus(@RequestParam String sgl_id,String status,String good_name,String class_name,String sock) {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        CdsStoresGoodsList csgd = new CdsStoresGoodsList();
        if(!StringUtils.isEmpty(sgl_id)) {
            csgd.setSgl_id(Integer.valueOf(sgl_id));
            csgd.addConditionField("sgl_id");
            csgd = sqlDao.getRecord(csgd);
            int is_sold_out;
            if("1".equals(status)){
                is_sold_out=0;
            }else{
                sock="0";
                is_sold_out=1;
            }
            if(!StringUtils.isEmpty(csgd.getFood_id())){
                //更新饿了么
                try {
                    String result = APIFactoryEleme.getFoodAPI().eleUpdateFood(SystemConfig.GetSystemParamEleme(),"",csgd.getFood_id(),"","","",sock, "","");
                    JSONObject jo = JSONObject.parseObject(result);
                    if ("200".equals(jo.getString("code")) && jo.getString("message").equals("ok")) {
//                        sock = "6";
                    }
                } catch (ApiOpException e) {
                    e.printStackTrace();
                } catch (ApiSysException e) {
                    e.printStackTrace();
                }
            }
            if(!StringUtils.isEmpty(csgd.getMt_isband())){
                FoodParam foodParam = new FoodParam();
                foodParam.setApp_poi_code(csgd.getStores_brand_id()+"");
                foodParam.setApp_food_code(csgd.getMt_isband());
                foodParam.setIs_sold_out(is_sold_out);
                foodParam.setPrice(Float.valueOf(csgd.getMarket_price()+""));
                foodParam.setUnit(csgd.getUnit());
                foodParam.setMin_order_count(1);
                foodParam.setBox_num(Float.valueOf(csgd.getBox_count()+""));
                foodParam.setBox_price(Float.valueOf(csgd.getBox_price()+""));
                foodParam.setName(good_name);
                foodParam.setCategory_name(class_name);
                try {
                    String result = APIFactoryMeituan.getFoodAPI().foodSave(SystemConfig.GetSystemParamMeituan(),foodParam);
                    List<skuStockParam> skus = new ArrayList<>();
                    skuStockParam skuStockParam = new skuStockParam();
                    skuStockParam.setSku_id(sgl_id);
                    skuStockParam.setStock(csgd.getMt_count()+"");
                    skus.add(skuStockParam);
                    List<FoodSkuStockParam> list = new ArrayList<FoodSkuStockParam>();
                    FoodSkuStockParam foodSkuStockParam = new FoodSkuStockParam();
                    foodSkuStockParam.setApp_food_code(sgl_id);
                    foodSkuStockParam.setSkus(skus);
                    list.add(foodSkuStockParam);
                    String result2 = APIFactoryMeituan.getFoodAPI().updateFoodSkuStock(SystemConfig.GetSystemParamMeituan(),csgd.getStores_brand_id()+"",list);
                    if ("ok".equals(result)) {
//                        is_sold_out = 6;
                    }
                } catch (com.opensdk.meituan.exception.ApiOpException e) {
                    e.printStackTrace();
                } catch (com.opensdk.meituan.exception.ApiSysException e) {
                    e.printStackTrace();
                }
            }
            if (!StringUtils.isEmpty(status)) {
                csgd.setStatus(Integer.valueOf(status));
                csgd.addConditionField("sgl_id");
                csgd.addUnParamFields("sgl_id");
                sqlDao.updateRecord(csgd);
                jmsg.setStatus("1");
            }
        }
        return jmsg;
    }

//    @RequestMapping(value = "deleteClass", method = RequestMethod.POST)
//    @ResourceMethod(name = "删除门店类别明细", check = CHECK_LOGIN)
//    @ResponseBody
//    public JsonMessage deleteClass(@RequestParam String stores_class_id) {
//        JsonMessage jmsg = new JsonMessage();
//        jmsg.setStatus("0");
//        CdsStoresClass csc =  new CdsStoresClass();
//       if(!StringUtils.isEmpty(stores_class_id)){
//           csc.setStores_class_id(Integer.valueOf(stores_class_id));
//           sqlDao.deleteRecord("cds_ptstores_manage.deleteClass",csc);
//           jmsg.setStatus("1");
//       }
//        return jmsg;
//    }

    @RequestMapping(value = "getGood_ids", method = RequestMethod.POST)
    @ResourceMethod(name = "根据门店id获取已有的商品id", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage getGood_ids(@RequestParam String stores_id,String brand_id) {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        jmsg.setMessage("");
        CdsStoresGoodsList csgd = new CdsStoresGoodsList();
        if(!StringUtils.isEmpty(stores_id)) {
            csgd.setStores_id(Integer.valueOf(stores_id));
            csgd.setBrand_id(Integer.valueOf(brand_id));
            csgd.addConditionFields("stores_id,brand_id");
            List<CdsStoresGoodsList> list = sqlDao.getRecordList(csgd);
            String good_ids="and a.good_id not in (";
            if (list!=null&&list.size()>0){
                for (int i = 0; i <list.size() ; i++) {
                    good_ids = (good_ids+list.get(i).getGood_id()+"")+',';
                    if(i==list.size()-1){
                        good_ids=good_ids.substring(0,(good_ids.length()-1));
                        good_ids=good_ids+')';
                        jmsg.setStatus("1");
                        jmsg.setMessage(good_ids);
                    }
                }
            }
        }
        return jmsg;
    }

    @RequestMapping(value = "getCombobox", method = RequestMethod.POST)
    @ResourceMethod(name = "根据门店id获取已有的商品id", check = CHECK_LOGIN)
    @ResponseBody
    public List getCombobox(@RequestParam String brand_id) {
        List<JSONObject> listjo = new ArrayList<JSONObject>();
        CdsClass cc = new CdsClass();
        if(!StringUtils.isEmpty(brand_id)) {
            cc.setBrand_id(Integer.valueOf(brand_id));
            cc.addConditionField("brand_id");
            List<CdsClass> list = sqlDao.getRecordList(cc);
            if (list!=null&&list.size()>0){
                for (int i = 0; i <list.size() ; i++) {
                    JSONObject jo = new JSONObject();
                    jo.put("id",list.get(i).getClass_id());
                    jo.put("text",list.get(i).getClass_name());
                    listjo.add(jo);
                }
            }
        }
        return listjo;
    }


    @RequestMapping(value = "saveGoods", method = RequestMethod.POST)
    @ResourceMethod(name = "保存商铺包含商品明细", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage saveGoods(@RequestParam HashMap hashMap) {
        CdsStoresClass csc = new CdsStoresClass();
//        Iterator it = hashMap.entrySet().iterator();
        List<CdsStoresClass> list = new ArrayList<CdsStoresClass>();


        if (hashMap!=null&&hashMap.size()>0){
            String json = hashMap.get("jsondata").toString();

            JSONArray array = JSONArray.parseArray(json.toString());
            for (int i = 0; i < array.size(); i++) {
                CdsStoresGoodsList obj = JSONObject.parseObject( JSONObject.toJSONString( array.getJSONObject(i)),CdsStoresGoodsList.class);
                obj.addUnParamFields("sgl_id");
                sqlDao.insertRecord(obj);
            }
        }
        return new JsonMessage(1);
    }

    @RequestMapping(value = "deleteGoods", method = RequestMethod.POST)
    @ResourceMethod(name = "删除商铺包含商品明细", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage deleteGoods(@RequestParam String sgl_id) {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        CdsStoresGoodsList cgc =  new CdsStoresGoodsList();
        if(!StringUtils.isEmpty(sgl_id)){
            cgc.setSgl_id(Integer.valueOf(sgl_id));
            cgc.addConditionField("sgl_id");
            sqlDao.deleteRecord(cgc);
            jmsg.setStatus("1");
        }
        return jmsg;
    }

    @RequestMapping(value = "indexPt", method = RequestMethod.GET)
    @ResourceMethod(name = "平台商品", check = CHECK_LOGIN)
    public String indexPt(String brand_id,String stores_id,String brand_name,String stores_name,String status,String class_id,String good_id){
        CdsClass cc = new CdsClass();
        CdsClassType cct = new CdsClassType();
        List<CdsClassType> classTypeList = sqlDao.getRecordList(cct);
        WebUtil.getRequest().setAttribute("classtypelist", classTypeList);
        List<CdsClass> classList = sqlDao.getRecordList(cc);
        CdsBrand cdsBrand = new CdsBrand();
        List<CdsBrand> brandlist = sqlDao.getRecordList(cdsBrand);
        WebUtil.getRequest().setAttribute("brandlist", brandlist);
        WebUtil.getRequest().setAttribute("classlist", classList);
        WebUtil.getRequest().setAttribute("brand_id", brand_id);
        WebUtil.getRequest().setAttribute("stores_id", stores_id);
        WebUtil.getRequest().setAttribute("brand_name", brand_name);
        WebUtil.getRequest().setAttribute("stores_name", stores_name);
        WebUtil.getRequest().setAttribute("status", status);
        WebUtil.getRequest().setAttribute("class_id", class_id);
        WebUtil.getRequest().setAttribute("good_id", good_id);
        return "ptGoodsManage/indexPt";
    }





    @RequestMapping(value = "getStoresGoodsClass", method = RequestMethod.POST)
    @ResourceMethod(name = "获取门店下面所有分类", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage getStoresGoodsClass(@RequestParam String brand_id,String stores_id) {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        CdsStoresBrand csb = new CdsStoresBrand();
        List<CdsStoresClass> list = new ArrayList<CdsStoresClass>();
        List<JSONObject> listjo = new ArrayList<JSONObject>();
        if (!StringUtils.isEmpty(brand_id)){
            csb.setBrand_id(Integer.valueOf(brand_id));
        }
        if(!StringUtils.isEmpty(stores_id)){
            csb.setStores_id(Integer.valueOf(stores_id));
        }
        if(StringUtils.isEmpty(stores_id)&&StringUtils.isEmpty(brand_id)){
            //展示所有分类
            jmsg.setStatus("3");
            CdsClass cc = new CdsClass();
            List<CdsClass> cclist = sqlDao.getRecordList(cc);
            if(cclist!=null&&cclist.size()>0){
                for (int j = 0; j <cclist.size() ; j++) {
                    JSONObject jo = new JSONObject();
                    jo.put("class_id",cclist.get(j).getClass_id());
                    jo.put("class_name",cclist.get(j).getClass_name());
                    listjo.add(jo);
                }
                jmsg.setObj(listjo);
            }

        }else if(StringUtils.isEmpty(stores_id)&&!StringUtils.isEmpty(brand_id)) {
            //点击品牌展示品牌下所有分类
            jmsg.setStatus("2");
            CdsClass cc = new CdsClass();
            cc.setBrand_id(Integer.valueOf(brand_id));
            cc.addConditionField("brand_id");
            List<CdsClass> cclist = sqlDao.getRecordList(cc);
            if(cclist!=null&&cclist.size()>0){
                for (int j = 0; j <cclist.size() ; j++) {
                    JSONObject jo = new JSONObject();
                    jo.put("class_id",cclist.get(j).getClass_id());
                    jo.put("class_name",cclist.get(j).getClass_name());
                    listjo.add(jo);
                }
                jmsg.setObj(listjo);
            }
        }else{
            //品牌、门店下分类
            csb.addConditionFields("stores_id,brand_id");
            csb = sqlDao.getRecord(csb);
            if(csb!=null){
                int stores_brand_id = csb.getStores_brand_id();
                String elem_restaurant_id = csb.getElem_restaurant_id();
                CdsStoresClass csc = new CdsStoresClass();
                csc.setStores_brand_id(stores_brand_id);
                csc.addConditionField("stores_brand_id");
                list = sqlDao.getRecordList(csc);
                if(list!=null&&list.size()>0){
                    CdsClass cc = new CdsClass();
                    cc.setBrand_id(Integer.valueOf(brand_id));
                    cc.addConditionField("brand_id");
                    List<CdsClass> cclist = sqlDao.getRecordList(cc);
                    for (int i = 0; i <list.size(); i++) {
                        jmsg.setStatus("1");
                        int class_id = list.get(i).getClass_id();
                        String food_category_id = list.get(i).getFood_category_id();
                        String meituan_cat_id = list.get(i).getMeituan_cat_id();
                        for (int j = 0; j <cclist.size() ; j++) {
                            if(class_id==cclist.get(j).getClass_id()){
                                JSONObject jo = new JSONObject();
                                jo.put("class_id",class_id);
                                jo.put("class_name",cclist.get(j).getClass_name());
                                jo.put("stores_brand_id",stores_brand_id);
                                jo.put("food_category_id",food_category_id);
                                jo.put("meituan_cat_id",meituan_cat_id);
                                jo.put("elem_restaurant_id",elem_restaurant_id);
                                listjo.add(jo);
                            }
                        }
                    }
                    jmsg.setObj(listjo);
                }
            }
        }

        return jmsg;
    }


    @RequestMapping(value = "indexElem", method = RequestMethod.GET)
    @ResourceMethod(name = "平台商品", check = CHECK_LOGIN)
    public String indexElem(String brand_id,String stores_id,String restaurant_id) throws Exception {
//        CdsClass cc = new CdsClass();
//        CdsClassType cct = new CdsClassType();
//        List<CdsClassType> classTypeList = sqlDao.getRecordList(cct);
//        WebUtil.getRequest().setAttribute("classtypelist", classTypeList);
//        List<CdsClass> classList = sqlDao.getRecordList(cc);
//        CdsBrand cdsBrand = new CdsBrand();
//
//        List<CdsBrand> brandlist = sqlDao.getRecordList(cdsBrand);
//        WebUtil.getRequest().setAttribute("brandlist", brandlist);
//        WebUtil.getRequest().setAttribute("classlist", classList);
        if("undefined".equals(restaurant_id)){
            restaurant_id = "";
        }
        List<JsonMessage> listjo = getElemClassList(restaurant_id);
        WebUtil.getRequest().setAttribute("brand_id", brand_id);
        WebUtil.getRequest().setAttribute("stores_id", stores_id);
        WebUtil.getRequest().setAttribute("listjo", listjo);
//        WebUtil.getRequest().setAttribute("brand_name", brand_name);
//        WebUtil.getRequest().setAttribute("stores_name", stores_name);
        return "ptGoodsManage/indexElem";
    }



    @RequestMapping(value = "getElemGood", method = RequestMethod.POST)
    @ResourceMethod(name = "饿了么查询食物详情", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage getElemGood(@RequestParam String food_id) {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        try {
            String result = APIFactoryEleme.getFoodAPI().eleGoodQuery(SystemConfig.GetSystemParamEleme(), food_id);
            JSONObject jo = JSONObject.parseObject(result);
            if ("200".equals(jo.getString("code")) && jo.getString("message").equals("ok")) {

            }
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
        return jmsg;
    }

    @RequestMapping(value = "eleClassGoodsQuery", method = RequestMethod.POST)
    @ResourceMethod(name = "饿了么查询分类食物列表", check = CHECK_LOGIN)
    @ResponseBody
    public List<JSONObject> eleClassGoodsQuery(@RequestParam String food_category_id) {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        List<JSONObject> listjo = new ArrayList<JSONObject>();
        try {
            String result = APIFactoryEleme.getFoodAPI().eleClassGoodsQuery(SystemConfig.GetSystemParamEleme(), food_category_id);
            JSONObject jo = JSONObject.parseObject(result);
            if ("200".equals(jo.getString("code")) && jo.getString("message").equals("ok")) {
                Object ob = jo.get("data");
                jo = JSONObject.parseObject(ob.toString());
                ob = jo.get("foods");
                JSONArray ja = JSONArray.parseArray(ob.toString());
                if(ja!=null&&ja.size()>0){
                    for (int i = 0; i < ja.size(); i++) {
                        JSONObject job = new JSONObject();
                        job.put("name",ja.getJSONObject(i).get("food_name"));
                        job.put("stores_name",ja.getJSONObject(i).get("restaurant_name"));
                        String food_id = ja.getJSONObject(i).get("food_id").toString();
                        job.put("food_id",food_id);
                        CdsStoresGoodsList csgl = new CdsStoresGoodsList();
                        csgl.setFood_id(food_id);
                        csgl.addConditionField("food_id");
                        csgl = sqlDao.getRecord(csgl);
                        if(csgl==null){
                            job.put("isband","0");
                        }else{
                            job.put("isband","1");
                        }
                        listjo.add(job);
                    }
                }
            }
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
        return listjo;
    }

    @RequestMapping(value = "elemBandGood", method = RequestMethod.POST)
    @ResourceMethod(name = "饿了么食物绑定", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage elemBandGood(@RequestParam String food_category_id,String name,String price,String description,String stock,String sgl_id,String image_hash) {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        try {
            String result = APIFactoryEleme.getFoodAPI().eleSaveFood(SystemConfig.GetSystemParamEleme(), food_category_id,name,price,description,"10000",stock,image_hash);
            JSONObject jo = JSONObject.parseObject(result);
            if ("200".equals(jo.getString("code")) && jo.getString("message").equals("ok")) {
                Object ob = jo.get("data");
                jo = JSONObject.parseObject(ob.toString());
                String food_id = jo.getString("food_id");
                if(!StringUtils.isEmpty(sgl_id)){
                    CdsStoresGoodsList csgl = new CdsStoresGoodsList();
                    csgl.setSgl_id(Integer.valueOf(sgl_id));
                    csgl.addConditionField("sgl_id");
                    csgl = sqlDao.getRecord(csgl);
                    csgl.setFood_id(food_id);
                    csgl.addConditionField("sgl_id");
                    csgl.addUnParamFields("sgl_id");
                    sqlDao.updateRecord(csgl);
                    jmsg.setStatus("1");
                }
            }
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
        return jmsg;
    }

    @RequestMapping(value = "getGoodTree", method = RequestMethod.POST)
    @ResourceMethod(name = "获取食物", check = CHECK_LOGIN)
    @ResponseBody
    public List getGoodTree(String brand_id,String food_category_id,String stores_id) throws Exception {
        CdsStoresClass csc = new CdsStoresClass();
        csc.setFood_category_id(food_category_id);
        csc.addConditionField("food_category_id");
        csc = sqlDao.getRecord(csc);
        List<JSONObject> jolist =new ArrayList<JSONObject>();
        if(csc==null){
            //没有绑定分类
        }else{
            int class_id = csc.getClass_id();
            Map map = new HashMap();
            map.put("brand_id",Integer.valueOf(brand_id));
            map.put("food_category_id",Integer.valueOf(food_category_id));
            map.put("stores_id",Integer.valueOf(stores_id));
            map.put("class_id",class_id);
            jolist = sqlDao.getRecordList("cds_ptstores_manage.getGoodTree",map);
        }
        return jolist;
    }

    @RequestMapping(value = "eleUpdateFood", method = RequestMethod.POST)
    @ResourceMethod(name = "饿了么食物更新", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage eleUpdateFood(@RequestParam String food_category_id,String name,String price,String description,String stock,String food_id,String sgl_id,String image_hash,String packing_fee) {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        try {
            String result = APIFactoryEleme.getFoodAPI().eleUpdateFood(SystemConfig.GetSystemParamEleme(),food_category_id,food_id, name,price,description,stock, image_hash,packing_fee);
            JSONObject jo = JSONObject.parseObject(result);
            if ("200".equals(jo.getString("code")) && jo.getString("message").equals("ok")) {
                if(!StringUtils.isEmpty(sgl_id)){
                    CdsStoresGoodsList csgl = new CdsStoresGoodsList();
                    csgl.setSgl_id(Integer.valueOf(sgl_id));
                    csgl.addConditionField("sgl_id");
                    csgl = sqlDao.getRecord(csgl);
                    csgl.setFood_id(food_id);
                    if("0".equals(stock)){
                       csgl.setStatus(2);
                    }else{
                        csgl.setStatus(1);
                    }
                    csgl.addConditionField("sgl_id");
                    csgl.addUnParamFields("sgl_id");
                    sqlDao.updateRecord(csgl);
                    jmsg.setStatus("1");
                }
            }
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
        return jmsg;
    }


    @RequestMapping(value = "eleDeleteFood", method = RequestMethod.POST)
    @ResourceMethod(name = "饿了么食物删除", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage eleDeleteFood(@RequestParam String food_id) {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        try {
            String result = APIFactoryEleme.getFoodAPI().eleDeleteFood(SystemConfig.GetSystemParamEleme(),food_id);
            JSONObject jo = JSONObject.parseObject(result);
            if ("200".equals(jo.getString("code")) && jo.getString("message").equals("ok")) {
                if(!StringUtils.isEmpty(food_id)){
                    CdsStoresGoodsList csgl = new CdsStoresGoodsList();
                    csgl.setFood_id(food_id);
                    csgl.addConditionField("food_id");
                    csgl = sqlDao.getRecord(csgl);
                    csgl.setFood_id("");
                    csgl.addConditionField("sgl_id");
                    csgl.addUnParamFields("sgl_id");
                    sqlDao.updateRecord(csgl);
                    jmsg.setStatus("1");
                }
            }
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
        return jmsg;
    }


    @RequestMapping(value = "indexMt", method = RequestMethod.GET)
    @ResourceMethod(name = "美团平台商品", check = CHECK_LOGIN)
    public String indexMt(String brand_id,String stores_id,String stores_brand_id) throws Exception {
        List<JsonMessage> listjo = meituanClassQuery(stores_brand_id);
        WebUtil.getRequest().setAttribute("brand_id", brand_id);
        WebUtil.getRequest().setAttribute("stores_id", stores_id);
        WebUtil.getRequest().setAttribute("listjo", listjo);
        return "ptGoodsManage/indexMt";
    }

    @RequestMapping(value = "mtClassGoodsQuery", method = RequestMethod.POST)
    @ResourceMethod(name = "美团查询分类食物列表", check = CHECK_LOGIN)
    @ResponseBody
    public List<JSONObject> mtClassGoodsQuery(@RequestParam String stores_brand_id,String class_name) {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        List<JSONObject> listjo = new ArrayList<JSONObject>();
        try {
            List<FoodParam> foodList = APIFactoryMeituan.getFoodAPI().foodList(SystemConfig.GetSystemParamMeituan(), stores_brand_id);
            if (foodList!=null&&foodList.size()>0){
                for (int i = 0; i <foodList.size() ; i++) {
                    if(class_name.equals(foodList.get(i).getCategory_name())){
                       JSONObject jo = new JSONObject();
                       jo.put("app_food_code",foodList.get(i).getApp_food_code());
                       jo.put("name",foodList.get(i).getName());
                       jo.put("price",foodList.get(i).getPrice());
                       jo.put("unit",foodList.get(i).getUnit());
                       jo.put("box_num",foodList.get(i).getBox_num());
                       jo.put("box_price",foodList.get(i).getBox_price());
                       jo.put("is_sold_out",foodList.get(i).getIs_sold_out());
                       if(foodList.get(i).getSkus()!=null){
                           if(foodList.get(i).getSkus().get(0).getStock()==null||foodList.get(i).getSkus().get(0).getStock()==""){
                               jo.put("stock","0");
                           }else{
                               jo.put("stock",foodList.get(i).getSkus().get(0).getStock());
                           }
                       }else{
                           jo.put("stock","0");
                       }
                        String good_id = foodList.get(i).getApp_food_code();
                            CdsGoodsInfo csg = new CdsGoodsInfo();
                            int class_id=0;
                            class_id = getClassId(class_name,stores_brand_id);
                            if(class_id!=0){
                                csg.setClass_id(class_id);
                                csg.setGood_name(foodList.get(i).getName());
                                csg.addConditionFields("class_id,good_name");
                                csg = sqlDao.getRecord(csg);
                                //判断是否绑定
                                jo.put("is_band",0);
//                                if(csg!=null){
//                                    jo.put("is_band",1);
//                                }else{
//                                    jo.put("is_band",0);
//                                }
                            }else{
                                jo.put("is_band",0);
                            }
                        listjo.add(jo);
                    }
                }
            }
        } catch (com.opensdk.meituan.exception.ApiOpException e) {
            e.printStackTrace();
        } catch (com.opensdk.meituan.exception.ApiSysException e) {
            e.printStackTrace();
        }

        return listjo;
    }


    @RequestMapping(value = "mtBandGood", method = RequestMethod.POST)
    @ResourceMethod(name = "美团绑定", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage mtBandGood(@RequestParam String sgl_id,String category_name,String good_name,String good_info,String good_pic,String app_food_code) {
        JsonMessage jmsg = new JsonMessage();
        FoodParam foodParam = new FoodParam();
        CdsStoresGoodsList csg = new CdsStoresGoodsList();
        if(!StringUtils.isEmpty(sgl_id)){
            csg.setSgl_id(Integer.valueOf(sgl_id));
            csg.addConditionField("sgl_id");
            csg = sqlDao.getRecord(csg);
            if(csg!=null){
                foodParam.setApp_poi_code(csg.getStores_brand_id()+"");
                if(!StringUtils.isEmpty(app_food_code)){
                    //反向绑定
                    foodParam.setApp_food_code(app_food_code);
                }else{
                    //新增或者修改
                    if(!StringUtils.isEmpty(csg.getMt_isband())){
                        foodParam.setApp_food_code(csg.getMt_isband());
                    }else{
                        foodParam.setApp_food_code(sgl_id);
                    }
                }
                foodParam.setBox_num(Float.valueOf(csg.getBox_count()));
                foodParam.setCategory_name(category_name);
                foodParam.setDescription(good_info);
                foodParam.setName(good_name);
                foodParam.setBox_price(Float.valueOf(csg.getBox_price()+""));
                foodParam.setPrice(Float.valueOf(csg.getMarket_price()+""));
                foodParam.setMin_order_count(1);
                foodParam.setUnit(csg.getUnit());
                if(csg.getStatus()==1){
                    //售卖中
                    foodParam.setIs_sold_out(0);
                }else{
                    //暂停售卖
                    foodParam.setIs_sold_out(1);
                }
                String ulr= "http://caidashi.oss-cn-hangzhou.aliyuncs.com/"+good_pic;
                String image_hash = getMtimage_hash(ulr,csg.getStores_brand_id()+"",csg.getSgl_id()+"");
                foodParam.setPicture(image_hash);
                try {
                    String result = APIFactoryMeituan.getFoodAPI().foodSave(SystemConfig.GetSystemParamMeituan(),foodParam);
                    //创建sku信息
                    List<FoodSkuParam> list =  new ArrayList<FoodSkuParam>();
                    FoodSkuParam foodSkuParam = new FoodSkuParam();
                    foodSkuParam.setSku_id(csg.getSgl_id()+"");
                    foodSkuParam.setStock(csg.getMt_count()+"");
                    foodSkuParam.setSpec("默认");
                    foodSkuParam.setPrice(csg.getMarket_price()+"");
                    list.add(foodSkuParam);
                    String result2 = APIFactoryMeituan.getFoodAPI().foodSkuSave(SystemConfig.GetSystemParamMeituan(),csg.getStores_brand_id()+"",csg.getSgl_id()+"",list);
                    if ("ok".equals(result)) {
                        jmsg.setStatus("1");
                        if(StringUtils.isEmpty(csg.getMt_isband())){
                            csg.setMt_isband(sgl_id);
                        }
                        csg.setStatus(1);
                        csg.setMt_image_hash(image_hash);
                        csg.addConditionField("sgl_id");
                        csg.addUnParamFields("sgl_id");
                        sqlDao.updateRecord(csg);
                    }
                } catch (com.opensdk.meituan.exception.ApiOpException e) {
                    e.printStackTrace();
                } catch (com.opensdk.meituan.exception.ApiSysException e) {
                    e.printStackTrace();
                }
            }
        }

        return jmsg;
    }

    @RequestMapping(value = "mtDeleteGood", method = RequestMethod.POST)
    @ResourceMethod(name = "美团食物解绑", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage mtDeleteGood(@RequestParam String sgl_id) {
        JsonMessage jmsg = new JsonMessage();
        CdsStoresGoodsList csg = new CdsStoresGoodsList();
        if(!StringUtils.isEmpty(sgl_id)){
            csg.setSgl_id(Integer.valueOf(sgl_id));
            csg.addConditionField("sgl_id");
            csg = sqlDao.getRecord(csg);
            try {
                String result = APIFactoryMeituan.getFoodAPI().foodDelete(SystemConfig.GetSystemParamMeituan(),csg.getStores_brand_id()+"",csg.getMt_isband());
                if ("ok".equals(result)) {
                    jmsg.setStatus("1");
                    csg.setMt_isband("");
                    csg.addConditionField("sgl_id");
                    csg.addUnParamFields("sgl_id");
                    sqlDao.updateRecord(csg);
                }
            } catch (com.opensdk.meituan.exception.ApiOpException e) {
                e.printStackTrace();
            } catch (com.opensdk.meituan.exception.ApiSysException e) {
                e.printStackTrace();
            }

        }
        return jmsg;
    }

    @RequestMapping(value = "getMtGoodTree", method = RequestMethod.POST)
    @ResourceMethod(name = "获取食物", check = CHECK_LOGIN)
    @ResponseBody
    public List getMtGoodTree(String brand_id,String meituan_cat_id,String stores_id) throws Exception {
        CdsStoresClass csc = new CdsStoresClass();
        csc.setMeituan_cat_id(meituan_cat_id);
        csc.addConditionField("meituan_cat_id");
        csc = sqlDao.getRecord(csc);
        List<JSONObject> jolist =new ArrayList<JSONObject>();
        if(csc==null){
            //没有绑定分类
        }else{
            int class_id = csc.getClass_id();
            Map map = new HashMap();
            map.put("brand_id",Integer.valueOf(brand_id));
            map.put("meituan_cat_id",meituan_cat_id);
            map.put("stores_id",Integer.valueOf(stores_id));
            map.put("class_id",class_id);
            jolist = sqlDao.getRecordList("cds_ptstores_manage.getMtGoodTree",map);
        }
        return jolist;
    }

    /**
     * 美团通过url获取image_hash
     * @param url
     * @param stores_brand_id
     * @param good_name
     * @return
     */
    public String getMtimage_hash(String url,String stores_brand_id,String good_name){
        File f = new File(url);
        String image_hash="";
        try {
            URL url2 = new URL(url);
            URLConnection uc = url2.openConnection();
            InputStream in = uc.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] b = new byte[8 * 1024];
            int i = 0;
            while ((i = in.read(b)) != -1) {
                out.write(b, 0, i);
            }
            out.flush();
            byte[] r  = out.toByteArray();
            System.out.println(r);
            out.close();
            in.close();
            image_hash = APIFactoryMeituan.getImageApi().imageUpload(SystemConfig.GetSystemParamMeituan(),stores_brand_id,r,good_name+".jpg");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        } catch (com.opensdk.meituan.exception.ApiOpException e) {
            e.printStackTrace();
        } catch (com.opensdk.meituan.exception.ApiSysException e) {
            e.printStackTrace();
        }
        return image_hash;
    }

    @RequestMapping(value = "checkBindMt", method = RequestMethod.POST)
    @ResourceMethod(name = "判断美团是否绑定", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage checkBindMt(@RequestParam String sgl_id) {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        CdsStoresGoodsList csg = new CdsStoresGoodsList();
        if(!StringUtils.isEmpty(sgl_id)){
            csg.setSgl_id(Integer.valueOf(sgl_id));
            csg.addConditionField("sgl_id");
            csg = sqlDao.getRecord(csg);
            if(csg!=null){
                if(csg.getMt_isband()!=null&&csg.getMt_isband()!=""){
                    jmsg.setStatus("1");
                }
            }
        }
        return jmsg;
    }

























    @RequestMapping(value = "index2", method = RequestMethod.GET)
    @ResourceMethod(name = "门店商品管理", check = CHECK_LOGIN)
    public String index2() throws Exception {
        Integer stores_id = WebUtil.getSession("stores_id");
        if(!StringUtils.isEmpty(stores_id)){
            WebUtil.getRequest().setAttribute("stores_id", stores_id);
        }else{
            WebUtil.getRequest().setAttribute("stores_id", "0");
        }
        CdsClassType cct = new CdsClassType();
        List<CdsClassType> classTypeList = sqlDao.getRecordList(cct);
        WebUtil.getRequest().setAttribute("classtypelist", classTypeList);
        return "ptGoodsManage/index2";
    }




    @RequestMapping(value = "getClassTree", method = RequestMethod.POST)
    @ResourceMethod(name = "获取品牌类别信息", check = CHECK_LOGIN)
    @ResponseBody
    public List getClassTree(@RequestParam HashMap hashMap) throws Exception {
        //判断是商品管理页面还是分类管理页面
        String type = hashMap.get("type").toString();
        Integer stores_id = WebUtil.getSession("stores_id");
        List<JSONObject> jolist = new ArrayList<JSONObject>();
        CdsBrand cbs = new CdsBrand();
        List<CdsBrand> listcb = sqlDao.getRecordList(cbs);
        CdsStores cs = new CdsStores();
        List<CdsStores> lists = sqlDao.getRecordList(cs);
        if(!StringUtils.isEmpty(stores_id)&&"1".equals(type)){
            CdsStoresBrand  csb = new CdsStoresBrand();
            for (int j = 0; j < listcb.size(); j++) {
                csb.setBrand_id(listcb.get(j).getBrand_id());
                csb.setStores_id(Integer.valueOf(stores_id));
                List list_class = sqlDao.getRecordList("cds_ptstores_manage.getClassTree",csb);
                JSONObject jo2 = new JSONObject();
                if (list_class!=null&&list_class.size()>0){
                    jo2.put("id",listcb.get(j).getBrand_id());
                    jo2.put("text",listcb.get(j).getBrand_name());
                    jo2.put("iconCls","icon icon-104");
                    jo2.put("isok","1a");
                    jo2.put("state","opens");
                    jo2.put("children",list_class);
                    jolist.add(jo2);
                }
            }
        }else {
            List<Map> list = sqlDao.getRecordList("cds_ptstores_manage.getClassTree2");
            if (lists != null) {
                for (int j = 0; j < lists.size(); j++) {
                    List<JSONObject> listBrand = new ArrayList<JSONObject>();
                    JSONObject joStores = new JSONObject();
                    joStores.put("id", lists.get(j).getStores_id());
                    joStores.put("text", lists.get(j).getName());
                    joStores.put("iconCls", "icon icon-288");
                    joStores.put("isok", "0");
                    joStores.put("state", "closed");
                    if (listcb != null && listcb.size() > 0) {
                        for (int k = 0; k < listcb.size(); k++) {
                            //类别
                            List<JSONObject> listClass = new ArrayList<JSONObject>();
                            if (list != null && list.size() > 0) {
                                for (int i = 0; i < list.size(); i++) {
                                    String stores_class_id = list.get(i).get("stores_class_id").toString();
                                    String class_name = list.get(i).get("class_name").toString();
                                    String brand_name = list.get(i).get("brand_name").toString();
                                    String brand_id2 = (list.get(i).get("brand_id").toString());
                                    int brand_id = Integer.valueOf(brand_id2);
                                    String stores_id2 = list.get(i).get("stores_id").toString();
                                    int stores_ids = Integer.valueOf(stores_id2);
                                    String name = list.get(i).get("name").toString();
                                    if (stores_ids == lists.get(j).getStores_id() && brand_id == listcb.get(k).getBrand_id()) {
                                        JSONObject joClass = new JSONObject();
                                        joClass.put("id", stores_class_id);
                                        joClass.put("text", class_name);
                                        joClass.put("iconCls", "icon icon-367");
                                        listClass.add(joClass);
                                    }
                                }

                            }
                            //品牌
                            JSONObject joBrand = new JSONObject();
                            if (listClass != null && listClass.size() > 0) {
                                joBrand.put("children", listClass);
                                joBrand.put("id", listcb.get(k).getBrand_id());
                                joBrand.put("text", listcb.get(k).getBrand_name());
                                joBrand.put("iconCls", "icon icon-104");
                                joBrand.put("isok", "1");
                                joBrand.put("state", "closed");
                                listBrand.add(joBrand);
                            }
                        }
                    }
                    if (listBrand != null && listBrand.size() > 0) {
                        joStores.put("children", listBrand);
                    }
                    jolist.add(joStores);
                }
            }
        }

        return jolist;
    }


    @RequestMapping(value = "getClassTree2", method = RequestMethod.POST)
    @ResourceMethod(name = "获取品牌类别信息", check = CHECK_LOGIN)
    @ResponseBody
    public List getClassTree2() throws Exception {
        List<JSONObject> jolist = new ArrayList<JSONObject>();
        List<Map> list = sqlDao.getRecordList("cds_ptstores_manage.getClassTree2");
        CdsStores cs = new CdsStores();
        List<CdsStores> lists = sqlDao.getRecordList(cs);
        CdsBrand cb = new CdsBrand();
        List<CdsBrand> listcb = sqlDao.getRecordList(cb);
        if(lists!=null){
            for (int j = 0; j <lists.size() ; j++) {
                List<JSONObject> listBrand= new ArrayList<JSONObject>();
                JSONObject joStores = new JSONObject();
                joStores.put("id",lists.get(j).getStores_id());
                joStores.put("text",lists.get(j).getName());
                joStores.put("iconCls","icon icon-288");
                joStores.put("isok","0");
                joStores.put("state","closed");
                if(listcb!=null&&listcb.size()>0){
                    for (int k = 0; k <listcb.size() ; k++) {
                        //类别
                        List<JSONObject> listClass = new ArrayList<JSONObject>();
                        if(list!=null&&list.size()>0){
                            for (int i = 0; i <list.size() ; i++) {
                                String stores_class_id = list.get(i).get("stores_class_id").toString();
                                String class_name = list.get(i).get("class_name").toString();
                                String brand_name = list.get(i).get("brand_name").toString();
                                String brand_id2 = (list.get(i).get("brand_id").toString());
                                int brand_id = Integer.valueOf(brand_id2);
                                String stores_id2 = list.get(i).get("stores_id").toString();
                                int stores_id = Integer.valueOf(stores_id2);
                                String name = list.get(i).get("name").toString();
                                if(stores_id==lists.get(j).getStores_id()&&brand_id==listcb.get(k).getBrand_id()){
                                    JSONObject joClass = new JSONObject();
                                    joClass.put("id",stores_class_id);
                                    joClass.put("text",class_name);
                                    joClass.put("iconCls","icon icon-367");
                                    listClass.add(joClass);
                                }
                            }

                        }
                        //品牌
                        JSONObject joBrand = new JSONObject();
                        if(listClass!=null&&listClass.size()>0){
                            joBrand.put("children",listClass);
                            joBrand.put("id",listcb.get(k).getBrand_id());
                            joBrand.put("text",listcb.get(k).getBrand_name());
                            joBrand.put("iconCls","icon icon-104");
                            joBrand.put("isok","1");
                            joBrand.put("state","closed");
                            listBrand.add(joBrand);
                        }
                    }
                }
                if (listBrand!=null&&listBrand.size()>0){
                    joStores.put("children",listBrand);
                }
                jolist.add(joStores);
            }
            return jolist;
        }


//        if (map!=null&&map.size()>0){
//            for (Map.Entry<Object,Object> entry : map.entrySet()) {
//                joClass.put("id"," entry.getValue()");
//                System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
//            }
//        }
        return jolist;
    }


    @RequestMapping(value = "getGoodsList", method = RequestMethod.POST)
    @ResourceMethod(name = "商品", check = CHECK_LOGIN)
    @ResponseBody
    public void getGoodsList(@RequestParam HashMap formInfo) throws Exception {
        Integer stores_id = WebUtil.getSession("stores_id");
        if (!formInfo.containsKey("stores_id")){
            formInfo.put("stores_id",stores_id);
        }
        queryAndResponsePage("cds_ptstores_manage.getGoodsList",formInfo);
    }

    @RequestMapping(value = "classIndex2", method = RequestMethod.GET)
    @ResourceMethod(name = "门店类别管理", check = CHECK_LOGIN)
    public String classIndex2() throws Exception {
        Integer stores_id = WebUtil.getSession("stores_id");
        if (!StringUtils.isEmpty(stores_id)){
            WebUtil.getRequest().setAttribute("stores_id", stores_id);
        }
        CdsBrand cdsBrand = new CdsBrand();
        List<CdsBrand> brandlist = sqlDao.getRecordList(cdsBrand);
        WebUtil.getRequest().setAttribute("brandlist", brandlist);
        return "ptGoodsManage/class_index2";
    }

    @RequestMapping(value = "getGoodsClassList", method = RequestMethod.POST)
    @ResourceMethod(name = "商品类别", check = CHECK_LOGIN)
    @ResponseBody
    public void getGoodsClassList(@RequestParam HashMap formInfo) throws Exception {
        Integer stores_id = WebUtil.getSession("stores_id");
        if(!StringUtils.isEmpty(stores_id)){
            formInfo.put("stores_id",stores_id);
        }
        queryAndResponsePage("cds_ptstores_manage.getGoodsClassList",formInfo);
    }

    @RequestMapping(value = "getStoresCombobox", method = RequestMethod.POST)
    @ResourceMethod(name = "获取品牌类别信息", check = CHECK_LOGIN)
    @ResponseBody
    public List getStoresCombobox() throws Exception {
        List<JSONObject> jolist = new ArrayList<JSONObject>();
        CdsBrand cb = new CdsBrand();
        List<CdsBrand> listcb = sqlDao.getRecordList(cb);
        if(listcb!=null&&listcb.size()>0){
            for (int i = 0; i < listcb.size(); i++) {
                JSONObject jo = new JSONObject();
                jo.put("id",listcb.get(i).getBrand_id());
                jo.put("text",listcb.get(i).getBrand_name());
                jo.put("iconCls","icon icon-104");
                jo.put("state","open");
                List list = sqlDao.getRecordList("cds_ptstores_manage.getStoresCombobox",listcb.get(i));
                if (list!=null&&list.size()>0){
                    jo.put("children",list);
                }
                jolist.add(jo);
            }
        }
        return jolist;
    }

    @RequestMapping(value = "saveClass", method = RequestMethod.POST)
    @ResourceMethod(name = "保存分类", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage saveClass(String brand_id,String class_name,String stores_brand_id,String listorder,String class_desc,String opt,String stores_class_id) throws Exception {
        JsonMessage jm = new JsonMessage();
        jm.setStatus("0");
        CdsStoresClass csc = new CdsStoresClass();
        if ("add".equals(opt)){
            if(!StringUtils.isEmpty(stores_brand_id)){
                csc.setStores_brand_id(Integer.valueOf(stores_brand_id));
            }
            csc.setClass_name(class_name);
            csc.addConditionFields("class_name,stores_brand_id");
            csc = sqlDao.getRecord(csc);
            if (csc!=null){
                jm.setStatus("2");
                return jm;
            }
        }
        csc = new CdsStoresClass();
        csc.setClass_id(0);//有非空约束
        if(!StringUtils.isEmpty(stores_class_id)){
            csc.setStores_class_id(Integer.valueOf(stores_class_id));
        }
        if(!StringUtils.isEmpty(brand_id)){
            csc.setBrand_id(Integer.valueOf(brand_id));
        }
        if(!StringUtils.isEmpty(stores_brand_id)){
            csc.setStores_brand_id(Integer.valueOf(stores_brand_id));
        }
        csc.setClass_name(class_name);
        csc.setClass_desc(class_desc);
        csc.setType(0);
        if (!StringUtils.isEmpty(listorder)){
            csc.setListorder(Integer.valueOf(listorder));
        }
        if ("add".equals(opt)){
            csc.addUnParamFields("stores_class_id,food_category_id,food_category_id,baidu_cat_id");
            sqlDao.insertRecord(csc);
            jm.setStatus("1");

        }else{
            csc.setType(2);
            csc.addConditionField("stores_class_id");
            csc.addUnParamFields("stores_class_id,food_category_id,food_category_id,baidu_cat_id");
            sqlDao.updateRecord(csc);
            jm.setStatus("1");
        }

        return jm;
    }

    @RequestMapping(value = "saveClassList", method = RequestMethod.POST)
    @ResourceMethod(name = "保存商铺包含商品明细", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage saveClassList(@RequestParam HashMap hashMap) {
        JsonMessage jm = new JsonMessage();
        String stores_brand_id = hashMap.get("stores_brand_id")+"";
        jm.setStatus("0");
        if(!StringUtils.isEmpty(stores_brand_id)){
            if (hashMap!=null&&hashMap.size()>0){
                String json = hashMap.get("jsondata").toString();
                JSONArray array = JSONArray.parseArray(json.toString());
                for (int i = 0; i < array.size(); i++) {
                    CdsStoresClass csc = JSONObject.parseObject( JSONObject.toJSONString( array.getJSONObject(i)),CdsStoresClass.class);
                    csc.addUnParamFields("stores_class_id");
                    csc.setStores_brand_id(Integer.valueOf(stores_brand_id));
                    csc.addConditionFields("stores_brand_id,class_name");
                    CdsStoresClass csc2 = sqlDao.getRecord(csc);
                    if(csc2==null){
                        sqlDao.insertRecord(csc);
                    }
                }
                jm.setStatus("1");
            }
        }

        return jm;
    }

    @RequestMapping(value = "getBrandCombobox", method = RequestMethod.POST)
    @ResourceMethod(name = "获取品牌类别信息", check = CHECK_LOGIN)
    @ResponseBody
    public List getBrandCombobox() throws Exception {
        List<JSONObject> jolist = new ArrayList<JSONObject>();
        CdsBrand cb = new CdsBrand();
        List<CdsBrand> listcb = sqlDao.getRecordList(cb);
        if(listcb!=null&&listcb.size()>0){
            for (int i = 0; i < listcb.size(); i++) {
                JSONObject jo = new JSONObject();
                jo.put("id",listcb.get(i).getBrand_id());
                jo.put("text",listcb.get(i).getBrand_name());
                jolist.add(jo);
            }
        }
        return jolist;
    }

    @RequestMapping(value = "getStoresClass", method = RequestMethod.POST)
    @ResourceMethod(name = "获取类别信息", check = CHECK_LOGIN)
    @ResponseBody
    public List getStoresClass(String brand_id,String stores_id) throws Exception {
        List<JSONObject> jolist = new ArrayList<JSONObject>();
        CdsStoresBrand csb = new CdsStoresBrand();
        if(!StringUtils.isEmpty(brand_id)&&!StringUtils.isEmpty(stores_id)){
            csb.setBrand_id(Integer.valueOf(brand_id));
            csb.setStores_id(Integer.valueOf(stores_id));
            csb.addConditionFields("brand_id,stores_id");
        }
        csb = sqlDao.getRecord(csb);
        CdsStoresClass csc = new CdsStoresClass();
        if(csb!=null){
            csc.setStores_brand_id(csb.getStores_brand_id());
            csc.addConditionFields("stores_brand_id");
            List<CdsStoresClass> listcsc = sqlDao.getRecordList(csc);
            if(listcsc!=null&&listcsc.size()>0){
                for (int j = 0; j <listcsc.size() ; j++) {
                    JSONObject jo2 = new JSONObject();
                    jo2.put("id",listcsc.get(j).getStores_class_id());
                    jo2.put("text",listcsc.get(j).getClass_name());
                    jo2.put("stores_brand_id",listcsc.get(j).getStores_brand_id());
                    jo2.put("stores_id",stores_id);
                    jolist.add(jo2);
                }
            }

        }
        return jolist;
    }


    @RequestMapping(value = "getClassTreeLazy", method = RequestMethod.POST)
    @ResourceMethod(name = "获取品牌类别信息", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage getClassTreeLazy(String brand_id,String stores_id) throws Exception {
        List<JSONObject> jolist = new ArrayList<JSONObject>();
        JsonMessage jm = new JsonMessage();
        CdsStoresBrand cc = new CdsStoresBrand();
        if(!StringUtils.isEmpty(brand_id)){
            cc.setBrand_id(Integer.valueOf(brand_id));
        }
        if(!StringUtils.isEmpty(stores_id)){
            cc.setStores_id(Integer.valueOf(stores_id));
        }
        cc.addConditionFields("stores_id,brand_id");
        cc = sqlDao.getRecord(cc);
        if (cc!=null){
            int stores_brand_id = cc.getStores_brand_id();
            CdsStoresClass csc = new CdsStoresClass();
            csc.setStores_brand_id(Integer.valueOf(stores_brand_id));
            csc.addConditionFields("stores_brand_id");
            List<CdsStoresClass> list = sqlDao.getRecordList(cc);
            if(list!=null&&list.size()>0){
                for (int i = 0; i < list.size(); i++) {
                    JSONObject jo = new JSONObject();
                    jo.put("id",list.get(i).getStores_class_id());
                    jo.put("text",list.get(i).getClass_name());
                    jolist.add(jo);
                }
            }
        }
        jm.setObj(jolist);
        return jm;
    }


//    @RequestMapping(value = "checkName", method = RequestMethod.POST)
//    @ResourceMethod(name = "验证名称不能重复", check = CHECK_LOGIN)
//    @ResponseBody
//    public JsonMessage checkName(@RequestParam String class_name, String stores_brand_id) {
//        JsonMessage jm = new JsonMessage();
//        jm.setStatus("0");
//        CdsStoresClass csc = new CdsStoresClass();
//        if (StringUtils.isEmpty(stores_brand_id)){
//            csc.setStores_class_id(Integer.valueOf(stores_brand_id));
//        }
//        csc.setClass_name(class_name);
//        csc.addConditionFields("class_name,stores_brand_id");
//        csc = sqlDao.getRecord(csc);
//        if (csc!=null){
//            jm.setStatus("1");
//        }
//        return jm;
//    }

    @RequestMapping(value = "saveGood", method = RequestMethod.POST)
    @ResourceMethod(name = "保存商品明细", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage saveGood(@RequestParam HashMap hashMap) {
        JsonMessage jm = new JsonMessage();
        jm.setStatus("0");
        CdsStoresGoodsList csg = new CdsStoresGoodsList();
        String stores_class_id = hashMap.get("stores_class_id").toString();
        String stores_brand_id = hashMap.get("stores_brand_id").toString();
        String stores_id = hashMap.get("stores_id").toString();
        String opt = hashMap.get("opt").toString();
        String egood_pic = hashMap.get("egood_pic").toString();
        String good_pic = hashMap.get("good_pic").toString();
        String brand_id = hashMap.get("brand_id").toString();
        String good_name = hashMap.get("good_name").toString();
        String status = hashMap.get("status").toString();
        String good_info = hashMap.get("good_info").toString();
        String market_price = hashMap.get("market_price").toString();
        if (!StringUtils.isEmpty(stores_class_id)){
            csg.setStores_class_id(Integer.valueOf(stores_class_id));
        }
        csg.setGood_name(good_name);
        csg.addConditionFields("good_name,stores_class_id");
        csg = sqlDao.getRecord(csg);
        if (csg!=null){
            //名称重复
            jm.setStatus("2");
        }else{
            //名称不重复
            csg = new CdsStoresGoodsList();
            csg.setGood_pic(good_pic);
            csg.setEgood_pic(egood_pic);
            if (!StringUtils.isEmpty(stores_brand_id)){
                csg.setStores_brand_id(Integer.valueOf(stores_brand_id));
            }
            if (!StringUtils.isEmpty(brand_id)){
                csg.setBrand_id(Integer.valueOf(brand_id));
            }
            if (!StringUtils.isEmpty(stores_class_id)){
                csg.setStores_class_id(Integer.valueOf(stores_class_id));
            }
            if (!StringUtils.isEmpty(stores_id)){
                csg.setStores_id(Integer.valueOf(stores_id));
            }
            if (!StringUtils.isEmpty(status)){
                csg.setStatus(Integer.valueOf(status));
            }
            csg.setGood_info(good_info);
            if (!StringUtils.isEmpty(market_price)){
                csg.setMarket_price(Double.valueOf(market_price));
            }
            if(!StringUtils.isEmpty(good_name)){
                csg.setGood_name(good_name);
                String pinxin = StringAlphas.String2Alpha(good_name);
                csg.setPinxin(pinxin);
                csg.setAdd_date(DateUtil.getToday());
            }

            HashMap map = sqlDao.getRecord("cds_ptstores_manage.queryGoodId",csg);
            int sgl_id = 1;
            if (map!=null&&map.size()>0){
                String sgl_ids = map.get("sgl_id").toString();
                if (!StringUtils.isEmpty(sgl_ids)){
                    sgl_id = Integer.valueOf(sgl_ids)+1;
                }
            }
            csg.setSgl_id(sgl_id);
            if (hashMap!=null&&hashMap.size()>0){
                String json = hashMap.get("standardList").toString();
                String json2 = hashMap.get("erpList").toString();
                if("[]".equals(json)){
                    csg.addParamFields();
                    sqlDao.insertRecord(csg);
                    jm.setStatus("1");
                }else{
                    String sta = storesbrandservice.saveGood(csg,json,json2);
                    jm.setStatus(sta);
                }
            }
        }
        return jm;
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    @ResourceMethod(name = "商品管理", check = CHECK_LOGIN)
    public String edit(String sgl_id,String brand_id,String stores_id) throws Exception {
        WebUtil.getRequest().setAttribute("sgl_id", sgl_id);
        WebUtil.getRequest().setAttribute("brand_id", brand_id);
        WebUtil.getRequest().setAttribute("stores_id", stores_id);
        CdsStoresGoodsList csg = new CdsStoresGoodsList();
        if(!StringUtils.isEmpty(sgl_id)){
            csg.setSgl_id(Integer.valueOf(sgl_id));
            csg.addConditionField("sgl_id");
        }
        csg = sqlDao.getRecord(csg);
        WebUtil.getRequest().setAttribute("csg", csg);
        CdsClassType cct = new CdsClassType();
        List<CdsClassType> classTypeList = sqlDao.getRecordList(cct);
        WebUtil.getRequest().setAttribute("classtypelist", classTypeList);
        return "ptGoodsManage/edit";
    }

    @RequestMapping(value = "getStandardList", method = RequestMethod.POST)
    @ResourceMethod(name = "规格表", check = CHECK_LOGIN)
    @ResponseBody
    public void getStandardList(@RequestParam HashMap formInfo) throws Exception {
        formInfo.put("page","1");
        formInfo.put("rows","15");
        queryAndResponsePage("cds_ptstores_manage.getStandardList",formInfo);
    }

    @RequestMapping(value = "getStandardGoods", method = RequestMethod.POST)
    @ResourceMethod(name = "ERP商品表", check = CHECK_LOGIN)
    @ResponseBody
    public void getStandardGoods(@RequestParam HashMap formInfo) throws Exception {
        formInfo.put("page","1");
        formInfo.put("rows","15");
        queryAndResponsePage("cds_ptstores_manage.getStandardGoods",formInfo);
    }

    @RequestMapping(value = "saveStandardGood", method = RequestMethod.POST)
    @ResourceMethod(name = "保存商铺包含商品明细", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage saveStandardGood(@RequestParam String good_id,String standard_id,String good_count) {
        JsonMessage jm = new JsonMessage();
        jm.setStatus("0");
        CdsStandardGoodList csc = new CdsStandardGoodList();
        if(!StringUtils.isEmpty(good_id)){
            csc.setGood_id(Integer.valueOf(good_id));
            csc.setGood_count(Integer.valueOf(good_count));
        }
        if(!StringUtils.isEmpty(standard_id)){
            csc.setStandard_id(Integer.valueOf(standard_id));
        }
        csc.addUnParamFields("erp_good_id");
        sqlDao.insertRecord(csc);
        jm.setStatus("1");
        return jm;
    }


    @RequestMapping(value = "editErp", method = RequestMethod.POST)
    @ResourceMethod(name = "保存商铺包含商品明细", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage editErp(@RequestParam String erp_good_id,String good_count) {
        JsonMessage jm = new JsonMessage();
        jm.setStatus("0");
        CdsStandardGoodList csc = new CdsStandardGoodList();
        if(!StringUtils.isEmpty(erp_good_id)){
            csc.setErp_good_id(Integer.valueOf(erp_good_id));
            csc.setGood_count(Integer.valueOf(good_count));
            csc.addConditionField("erp_good_id");
        }

        csc.addUnParamFields("erp_good_id,standard_id,good_id");
        sqlDao.updateRecord(csc);
        jm.setStatus("1");
        return jm;
    }

    @RequestMapping(value = "deleErp", method = RequestMethod.POST)
    @ResourceMethod(name = "保存商铺包含商品明细", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage deleErp(@RequestParam String erp_good_id) {
        JsonMessage jm = new JsonMessage();
        jm.setStatus("0");
        CdsStandardGoodList csc = new CdsStandardGoodList();
        if(!StringUtils.isEmpty(erp_good_id)){
            csc.setErp_good_id(Integer.valueOf(erp_good_id));
            csc.addConditionField("erp_good_id");
        }

        csc.addUnParamFields("erp_good_id");
        sqlDao.deleteRecord(csc);
        jm.setStatus("1");
        return jm;
    }


    @RequestMapping(value = "editStandard", method = RequestMethod.POST)
    @ResourceMethod(name = "保存或者修改规格", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage editStandard(@RequestParam HashMap hashMap) {
        JsonMessage jm = new JsonMessage();
        String sgl_id = hashMap.get("sgl_id").toString();
        String opt = hashMap.get("opt").toString();
        jm.setStatus("0");
        if(!StringUtils.isEmpty(sgl_id)){
            String json = hashMap.get("standard").toString();
            JSONArray array = JSONArray.parseArray(json.toString());
            if(array!=null&&array.size()>0){
                CdsStandardList csc = JSONObject.parseObject( JSONObject.toJSONString( array.getJSONObject(0)),CdsStandardList.class);
                csc.setSgl_id(Integer.valueOf(sgl_id));
                if ("add".equals(opt)){
                    HashMap map = sqlDao.getRecord("cds_ptstores_manage.queryStandardId");
                    int standard_id=1;
                    if (map!=null&&map.size()>0){
                        String standard_ids = map.get("standard_id").toString();
                        if (!StringUtils.isEmpty(standard_ids)){
                            standard_id = Integer.valueOf(standard_ids)+1;
                        }
                    }
                    csc.setStandard_id(standard_id);
                    csc.addParamFields();
                    sqlDao.insertRecord(csc);
                    jm.setStatus("1");
                }else{
                    csc.addUnParamFields("standard_id");
                    csc.addConditionField("standard_id");
                    sqlDao.updateRecord(csc);
                    jm.setStatus("1");
                }

            }

        }

        return jm;
    }


    @RequestMapping(value = "deleteStandard", method = RequestMethod.POST)
    @ResourceMethod(name = "保存商铺包含商品明细", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage deleteStandard(@RequestParam String standard_id) {
        JsonMessage jm = new JsonMessage();
        jm.setStatus("0");
        CdsStandardList csc = new CdsStandardList();
        if(!StringUtils.isEmpty(standard_id)){
            csc.setStandard_id(Integer.valueOf(standard_id));
            storesbrandservice.deleteStandard(csc);
            jm.setStatus("1");
        }
        return jm;
    }


    @RequestMapping(value = "updateGood", method = RequestMethod.POST)
    @ResourceMethod(name = "修改商品", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage updateGood(@RequestParam HashMap hashMap) {
        JsonMessage jm = new JsonMessage();
        jm.setStatus("0");
        CdsStoresGoodsList csg = new CdsStoresGoodsList();
        String sgl_id = hashMap.get("sgl_id").toString();
        String stores_id = hashMap.get("stores_id").toString();
        String stores_class_id = hashMap.get("stores_class_id").toString();
        String stores_brand_id = hashMap.get("stores_brand_id").toString();
        String opt = hashMap.get("opt").toString();
        String egood_pic = hashMap.get("egood_pic").toString();
        String good_pic = hashMap.get("good_pic").toString();
        String brand_id = hashMap.get("brand_id").toString();
        String good_name = hashMap.get("good_name").toString();
        String status = hashMap.get("status").toString();
        String good_info = hashMap.get("good_info").toString();
        String market_price = hashMap.get("market_price").toString();
        if (!StringUtils.isEmpty(stores_class_id)){
            csg.setStores_class_id(Integer.valueOf(stores_class_id));
        }
        csg.setGood_name(good_name);
        csg.addConditionFields("good_name,stores_class_id");
        List<CdsStoresGoodsList> list= sqlDao.getRecordList(csg);
        if (list!=null&&list.size()>1){
            //名称重复
            jm.setStatus("2");
        }else{
            //名称不重复
            csg = new CdsStoresGoodsList();
            if("1".equals(good_pic)){
                csg.addUnParamFields("good_pic");
            }else{
                csg.setGood_pic(good_pic);
            }
            if("1".equals(egood_pic)){
                csg.addUnParamFields("egood_pic");
            }else{
                csg.setEgood_pic(egood_pic);
            }
            if (!StringUtils.isEmpty(stores_brand_id)){
                csg.setStores_brand_id(Integer.valueOf(stores_brand_id));
            }
            if (!StringUtils.isEmpty(brand_id)){
                csg.setBrand_id(Integer.valueOf(brand_id));
            }
            if (!StringUtils.isEmpty(sgl_id)){
                csg.setSgl_id(Integer.valueOf(sgl_id));
            }
            if (!StringUtils.isEmpty(stores_class_id)){
                csg.setStores_class_id(Integer.valueOf(stores_class_id));
            }
            if (!StringUtils.isEmpty(stores_id)){
                csg.setStores_id(Integer.valueOf(stores_id));
            }
            if (!StringUtils.isEmpty(status)){
                csg.setStatus(Integer.valueOf(status));
            }
            csg.setGood_info(good_info);
            if (!StringUtils.isEmpty(market_price)){
                csg.setMarket_price(Double.valueOf(market_price));
            }
            if(!StringUtils.isEmpty(good_name)){
                csg.setGood_name(good_name);
            }
            csg.addUnParamFields("sgl_id,pinxin,add_date");
            csg.addConditionField("sgl_id");
            sqlDao.updateRecord(csg);
            jm.setStatus("1");

        }
        return jm;
    }


    @RequestMapping(value = "getClassCombobox", method = RequestMethod.POST)
    @ResourceMethod(name = "获取品牌类别信息", check = CHECK_LOGIN)
    @ResponseBody
    public List getClassCombobox(String stores_brand_id) throws Exception {
        List<JSONObject> jolist = new ArrayList<JSONObject>();
        CdsStoresClass cc = new CdsStoresClass();
        if (!StringUtils.isEmpty(stores_brand_id)){
            cc.setStores_brand_id(Integer.valueOf(stores_brand_id));
        }
        cc.addConditionFields("stores_brand_id");
        List<CdsStoresClass> list = sqlDao.getRecordList(cc);
        if(list!=null&&list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                JSONObject jo = new JSONObject();
                jo.put("id",list.get(i).getStores_class_id());
                jo.put("text",list.get(i).getClass_name());
                jolist.add(jo);
            }
        }
        return jolist;
    }


    @RequestMapping(value = "getClassName", method = RequestMethod.POST)
    @ResourceMethod(name = "保存商铺包含商品明细", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage getClassName(@RequestParam String stores_brand_id,String class_name) {
        JsonMessage jm = new JsonMessage();
        CdsStoresClass cc = new CdsStoresClass();
        if (!StringUtils.isEmpty(stores_brand_id)){
            cc.setStores_brand_id(Integer.valueOf(stores_brand_id));
        }
        cc.setClass_name(class_name);
        cc.addConditionFields("stores_brand_id,class_name");
        cc = sqlDao.getRecord(cc);
        jm.setStatus("1");
        jm.setObj(cc);
        return jm;
    }


    @RequestMapping(value = "saveGoodList", method = RequestMethod.POST)
    @ResourceMethod(name = "保存商铺包含商品明细", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage saveGoodList(@RequestParam HashMap hashMap) {
        JsonMessage jm = new JsonMessage();
        String stores_brand_id = hashMap.get("stores_brand_id").toString();
        jm.setStatus("0");
        if(!StringUtils.isEmpty(stores_brand_id)){
            if (hashMap!=null&&hashMap.size()>0){
                String json = hashMap.get("jsondata").toString();
                JSONArray array = JSONArray.parseArray(json.toString());
                for (int i = 0; i < array.size(); i++) {
                    String class_name = array.getJSONObject(i).getString("class_name");
                    int stores_class_id = getStoresClassId(class_name,stores_brand_id);
                    if (stores_class_id!=0){
                        CdsStoresGoodsList csc = JSONObject.parseObject( JSONObject.toJSONString( array.getJSONObject(i)),CdsStoresGoodsList.class);
                        csc.setStores_brand_id(Integer.valueOf(stores_brand_id));
                        csc.addConditionFields("stores_brand_id,good_name");
                        CdsStoresGoodsList csc2 = sqlDao.getRecord(csc);
                        if(csc2==null){
                            CdsStoresBrand csb= new CdsStoresBrand();
                            csb.setStores_brand_id(Integer.valueOf(stores_brand_id));
                            csb.addConditionFields("stores_brand_id");
                            csb = sqlDao.getRecord(csb);
                            if (csb!=null){
                                csc.setBrand_id(csb.getBrand_id());
                                csc.setStores_id(csb.getStores_id());
                            }
                            int sgl_idc = csc.getSgl_id();
                            int sgl_id = getSglId();
                            csc.setSgl_id(sgl_id);
                            csc.setStores_class_id(stores_class_id);
                            storesbrandservice.saveGoodsList(csc,sgl_idc);
                            jm.setStatus("1");
                        }else{
                            jm.setStatus("1");
                        }
                    }

                }

            }
        }

        return jm;
    }


    public int getSglId(){
        HashMap map = sqlDao.getRecord("cds_ptstores_manage.queryGoodId");
        int sgl_id = 1;
        if (map!=null&&map.size()>0){
            String sgl_ids = map.get("sgl_id").toString();
            if (!StringUtils.isEmpty(sgl_ids)){
                sgl_id = Integer.valueOf(sgl_ids)+1;
            }
        }
        return sgl_id;
    }


    public int getStoresClassId(String class_name,String stores_brand_id){
        int stores_class_id = 0;
        CdsStoresClass csc = new CdsStoresClass();
        csc.setClass_name(class_name);
        csc.setStores_brand_id(Integer.valueOf(stores_brand_id));
        csc.addConditionFields("class_name,stores_brand_id");
        csc = sqlDao.getRecord(csc);
        if (csc!=null){
            stores_class_id = csc.getStores_class_id();
        }
        return stores_class_id;
    }

    public int checkName(CdsStoresGoodsList csg){
        int isok = 0;
//        CdsStoresGoodsList csg = new CdsStoresGoodsList();
//        csg.setGood_name(good_name);
//        csg.setStores_brand_id(stores_class_id);
        csg.addConditionFields("good_name,stores_class_id");
        csg = sqlDao.getRecord(csg);
        if(csg!=null){
            isok = 1;
        }
        return isok;
    }


    @RequestMapping(value = "deleteGood", method = RequestMethod.POST)
    @ResourceMethod(name = "删除", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage deleteGood(@RequestParam String sgl_id) {
        JsonMessage jm = new JsonMessage();
        if (!StringUtils.isEmpty(sgl_id)){
            storesbrandservice.deleteGood(sgl_id);
            jm.setStatus("1");
        }
        return jm;
    }


    @RequestMapping(value = "getElemeClassList", method = RequestMethod.POST)
    @ResourceMethod(name = "获取门店饿了么类别", check = CHECK_LOGIN)
    @ResponseBody
    public List getElemeClassList(@RequestParam HashMap hashMap) {
        JsonMessage jm = new JsonMessage();
        String restaurant_id = hashMap.get("restaurant_id").toString();
        List<OCategory> list = null;
        ProductService productService = new ProductService(SystemConfig.GetEleme2Config(),getElemeToken());
//        if (!StringUtils.isEmpty(restaurant_id)){
//            long shopId = Long.valueOf(restaurant_id);
//            try {
//                 list = productService.getShopCategories(157110517);
//            } catch (ServiceException e) {
//                e.printStackTrace();
//            }
//        }
        try {
            list = productService.getShopCategories(157110517);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return list;
    }


    @RequestMapping(value = "getElemeClassList2", method = RequestMethod.POST)
    @ResourceMethod(name = "获取门店饿了么类别", check = CHECK_LOGIN)
    @ResponseBody
    public List getElemeClassList2(@RequestParam HashMap hashMap) {
        JsonMessage jm = new JsonMessage();
        String stores_id = hashMap.get("stores_id").toString();
        String brand_id = hashMap.get("brand_id").toString();
        CdsStoresBrand csb = new CdsStoresBrand();
        List<OCategory> list = new ArrayList<OCategory>();
        if (!StringUtils.isEmpty(stores_id)&&!StringUtils.isEmpty(brand_id)){
            csb.setStores_id(Integer.valueOf(stores_id));
            csb.setBrand_id(Integer.valueOf(brand_id));
            csb.addConditionFields("stores_id,brand_id");
            csb = sqlDao.getRecord(csb);
            if (csb!=null){
                ProductService productService = new ProductService(SystemConfig.GetEleme2Config(),getElemeToken());
                long shopId=0;
                if(!StringUtils.isEmpty(csb.getElem_restaurant_id())){
                     shopId = Long.valueOf(csb.getElem_restaurant_id());
                }
                try {
                    list = productService.getShopCategories(157110517);
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }




    @RequestMapping(value = "elemeAll", method = RequestMethod.POST)
    @ResourceMethod(name = "饿了么(分类)绑定、更新、解绑", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage elemeAll(@RequestParam HashMap hashMap) {
        JsonMessage jm = new JsonMessage();
        jm.setStatus("0");
        String type = hashMap.get("type").toString();
        String stores_class_id = hashMap.get("stores_class_id").toString();
        String elem_restaurant_id = hashMap.get("elem_restaurant_id").toString();
        String food_category_id = hashMap.get("food_category_id").toString();
        CdsStoresClass csc = new CdsStoresClass();
        if (!StringUtils.isEmpty(stores_class_id)) {
            csc.setStores_class_id(Integer.valueOf(stores_class_id));
            csc.addConditionFields("stores_class_id");
            csc = sqlDao.getRecord(csc);
            if (csc != null) {
                ProductService productService = new ProductService(SystemConfig.GetEleme2Config(),getElemeToken());
                OCategory ocg = null;
                if ("1".equals(type)&&!StringUtils.isEmpty(elem_restaurant_id)) {
                    //绑定
                    long shopId = Long.valueOf(elem_restaurant_id);
                    try {
                        ocg = productService.createCategory(157110517, csc.getClass_name(), csc.getClass_desc());
                        if(ocg!=null){
                            csc.setFood_category_id(ocg.getId()+"");
                            csc.setType(1);
                            jm.setStatus("1");
                        }
                    } catch (ServiceException e) {
//                        jm.setStatus(e.getMessage());
                        e.printStackTrace();
                    }
                } else if ("2".equals(type)&&!StringUtils.isEmpty(food_category_id)) {
                    //更新
                    long categoryId = Long.valueOf(food_category_id);
                    try {
                        ocg = productService.updateCategory(categoryId, csc.getClass_name(), csc.getClass_desc());
                        if (ocg!=null){
                            csc.setFood_category_id(ocg.getId()+"");
                            csc.setType(1);
                            jm.setStatus("1");
                        }
                    } catch (ServiceException e) {
//                        jm.setStatus(e.getMessage());
                        e.printStackTrace();
                    }

                } else if ("3".equals(type)&&!StringUtils.isEmpty(food_category_id)) {
                    //解绑
                    long categoryId = Long.valueOf(food_category_id);
                    try {
                        ocg = productService.removeCategory(categoryId);
                        if (ocg!=null){
                            csc.setFood_category_id("");
                            csc.setType(0);
                            jm.setStatus("1");
                        }
                    } catch (ServiceException e) {
//                        jm.setStatus(e.getMessage());
                        e.printStackTrace();
                    }
                }
                csc.addConditionFields("stores_class_id");
                csc.addUnParamFields("stores_class_id");
                sqlDao.updateRecord(csc);
            }
        }
        return jm;
    }


    @RequestMapping(value = "mtAll", method = RequestMethod.POST)
    @ResourceMethod(name = "美团绑定、更新、解绑", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage mtAll(@RequestParam  HashMap hashMap) {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        String stores_class_id = hashMap.get("stores_class_id").toString();
        String stores_brand_id = hashMap.get("stores_brand_id").toString();
        String type = hashMap.get("type").toString();
        CdsStoresClass csc = new CdsStoresClass();
        if(!StringUtils.isEmpty(stores_class_id)){
            csc.setStores_class_id(Integer.valueOf(stores_class_id));
            csc.addConditionField(stores_class_id);
            csc = sqlDao.getRecord(csc);
            String result = null;
            if(csc!=null){
                if("1".equals(type)){
                    //绑定、更新
                    try {
                        result = APIFactoryMeituan.getFoodAPI().foodCatUpdate(SystemConfig.GetSystemParamMeituan(), stores_brand_id,csc.getMeituan_cat_id(),csc.getClass_name(),csc.getListorder());
                        if ("ok".equals(result)) {
                            csc.setMeituan_cat_id(csc.getClass_name());
                        }
                    } catch (com.opensdk.meituan.exception.ApiOpException e) {
                        e.printStackTrace();
                    } catch (com.opensdk.meituan.exception.ApiSysException e) {
                        e.printStackTrace();
                    }
                }else if("2".equals(type)){
                    //解绑
                    try {
                        result = APIFactoryMeituan.getFoodAPI().foodCatDelete(SystemConfig.GetSystemParamMeituan(), stores_brand_id,csc.getClass_name());
                        if ("ok".equals(result)) {
                            csc.setMeituan_cat_id("");
                        }
                    } catch (com.opensdk.meituan.exception.ApiOpException e) {
                        e.printStackTrace();
                    } catch (com.opensdk.meituan.exception.ApiSysException e) {
                        e.printStackTrace();
                    }
                }else if("3".equals(type)){
                    //美团反向绑定
                    String name = hashMap.get("name").toString();
                    csc.setMeituan_cat_id(name);
                    try {
                        result = APIFactoryMeituan.getFoodAPI().foodCatUpdate(SystemConfig.GetSystemParamMeituan(), stores_brand_id,csc.getMeituan_cat_id(),csc.getClass_name(),csc.getListorder());
                        if ("ok".equals(result)) {
                            csc.setMeituan_cat_id(csc.getClass_name());
                        }
                    } catch (com.opensdk.meituan.exception.ApiOpException e) {
                        e.printStackTrace();
                    } catch (com.opensdk.meituan.exception.ApiSysException e) {
                        e.printStackTrace();
                    }
                }
                if("ok".equals(result)){
                    csc.addUnParamFields("stores_class_id");
                    csc.addConditionFields("stores_class_id");
                    sqlDao.updateRecord(csc);
                    jmsg.setStatus("1");
                }
            }

        }
        return jmsg;
    }


    @RequestMapping(value = "elemeClassQuery", method = RequestMethod.POST)
    @ResourceMethod(name = "保存商铺包含商品明细", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage elemeClassQuery(@RequestParam String food_category_id) {
        JsonMessage jm = new JsonMessage();
        if (!StringUtils.isEmpty(food_category_id)){
           CdsStoresClass csc = new CdsStoresClass();
           csc.setFood_category_id(food_category_id);
           csc.addConditionFields("food_category_id");
           csc = sqlDao.getRecord(csc);
           if (csc!=null){
               jm.setStatus("0");
           }else{
               jm.setStatus("1");
           }

        }
        return jm;
    }

    @RequestMapping(value = "getElemeClass", method = RequestMethod.POST)
    @ResourceMethod(name = "保存商铺包含商品明细", check = CHECK_LOGIN)
    @ResponseBody
    public List getElemeClass(@RequestParam  String brand_id,String stores_id) {
        List list = null;
        if (!StringUtils.isEmpty(brand_id)&&!StringUtils.isEmpty(stores_id)){
            CdsStoresBrand csb = new CdsStoresBrand();
            csb.setBrand_id(Integer.valueOf(brand_id));
            csb.setStores_id(Integer.valueOf(stores_id));
            list = sqlDao.getRecordList("cds_ptstores_manage.getElemeClass",csb);
        }
        return list;
    }


    @RequestMapping(value = "deleteClass", method = RequestMethod.POST)
    @ResourceMethod(name = "保存商铺包含商品明细", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage deleteClass(@RequestParam  String stores_class_id) {
        JsonMessage jm = new JsonMessage();
        jm.setStatus("0");
        if (!StringUtils.isEmpty(stores_class_id)){
            CdsStoresClass csc = new CdsStoresClass();
            csc.setStores_class_id(Integer.valueOf(stores_class_id));
            csc.addConditionFields("stores_class_id");
            csc = sqlDao.getRecord(csc);
            //饿了么解绑
            if (!StringUtils.isEmpty(csc.getFood_category_id())){
                ProductService productService = new ProductService(SystemConfig.GetEleme2Config(),getElemeToken());
                long categoryId = Long.valueOf(csc.getFood_category_id());
                try {
                    OCategory ocg = productService.removeCategory(categoryId);
                    if (ocg!=null){
                        csc.setFood_category_id("");
                    }
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
            }
            //美团解绑
            if (!StringUtils.isEmpty(csc.getMeituan_cat_id())){
                try {
                   String result = APIFactoryMeituan.getFoodAPI().foodCatDelete(SystemConfig.GetSystemParamMeituan(), csc.getStores_brand_id()+"",csc.getClass_name());
                    if ("ok".equals(result)) {
                        csc.setMeituan_cat_id("");
                    }
                } catch (com.opensdk.meituan.exception.ApiOpException e) {
                    e.printStackTrace();
                } catch (com.opensdk.meituan.exception.ApiSysException e) {
                    e.printStackTrace();
                }
            }
            csc.addConditionFields("stores_class_id");
            sqlDao.deleteRecord(csc);
            jm.setStatus("1");
        }
        return jm;
    }

    @RequestMapping(value = "getMtClassList", method = RequestMethod.POST)
    @ResourceMethod(name = "查询美团食物分类信息", check = CHECK_LOGIN)
    @ResponseBody
    public List getMtClassList(@RequestParam String brand_id,String stores_id) {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        List<JSONObject> listjo = new ArrayList<JSONObject>();
        CdsStoresBrand csb = new CdsStoresBrand();
        if (!StringUtils.isEmpty(brand_id)&&!StringUtils.isEmpty(stores_id)){
            csb.setStores_id(Integer.valueOf(stores_id));
            csb.setBrand_id(Integer.valueOf(brand_id));
            csb.addConditionFields("stores_id,brand_id");
            csb = sqlDao.getRecord(csb);
            try {
                List<FoodCatParam> list= APIFactoryMeituan.getFoodAPI().foodCatList(SystemConfig.GetSystemParamMeituan(), csb.getStores_brand_id()+"");
                if(list!=null&&list.size()>0){
                    for (int i=0;i<list.size();i++){
                        JSONObject jo = new JSONObject();
//                    CdsStoresClass csc = new CdsStoresClass();
//                    csc.setStores_brand_id(Integer.valueOf(list.get(i).getApp_poi_code()));
//                    csc.setMeituan_cat_id(list.get(i).getName());
//                    csc.addConditionFields("stores_brand_id,meituan_cat_id");
//                    csc=sqlDao.getRecord(csc);
//                    if(csc!=null){
//                        jo.put("mstatus","0");
//                    }else{
//                        jo.put("mstatus","1");
//                    }
                        jo.put("stores_brand_id",list.get(i).getApp_poi_code());
                        jo.put("name",list.get(i).getName());
                        jo.put("sequence",list.get(i).getSequence());
                        jo.put("id",i+"a");
                        listjo.add(jo);
                    }
                }

            } catch (com.opensdk.meituan.exception.ApiOpException e) {
                e.printStackTrace();
            } catch (com.opensdk.meituan.exception.ApiSysException e) {
                e.printStackTrace();
            }
        }
        return listjo;
    }

    @RequestMapping(value = "mtClassQuery", method = RequestMethod.POST)
    @ResourceMethod(name = "保存商铺包含商品明细", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage mtClassQuery(@RequestParam String name,String stores_brand_id) {
        JsonMessage jm = new JsonMessage();
        if (!StringUtils.isEmpty(name)&&!StringUtils.isEmpty(stores_brand_id)){
            CdsStoresClass csc = new CdsStoresClass();
            csc.setStores_brand_id(Integer.valueOf(stores_brand_id));
            csc.setMeituan_cat_id(name);
            csc.addConditionFields("stores_brand_id,meituan_cat_id");
            csc = sqlDao.getRecord(csc);
            if (csc!=null){
                jm.setStatus("0");
            }else{
                jm.setStatus("1");
            }
        }
        return jm;
    }

    @RequestMapping(value = "getMtClass", method = RequestMethod.POST)
    @ResourceMethod(name = "获取美团分类", check = CHECK_LOGIN)
    @ResponseBody
    public List getMtClass(@RequestParam  String stores_brand_id) {
        List list = null;
        if (!StringUtils.isEmpty(stores_brand_id)){
            CdsStoresBrand csb = new CdsStoresBrand();
            csb.setStores_brand_id(Integer.valueOf(stores_brand_id));
            list = sqlDao.getRecordList("cds_ptstores_manage.getMtClass",csb);
        }
        return list;
    }


    @RequestMapping(value = "getElemeGoods", method = RequestMethod.POST)
    @ResourceMethod(name = "获取饿了么类别下商品信息", check = CHECK_LOGIN)
    @ResponseBody
    public List getElemeGoods(@RequestParam  HashMap hashMap) {
        List <OItem> list = new ArrayList<OItem>();
        String stores_class_id = hashMap.get("stores_class_id").toString();
        if (!StringUtils.isEmpty(stores_class_id)){
            CdsStoresClass csc = new CdsStoresClass();
            csc.setStores_class_id(Integer.valueOf(stores_class_id));
            csc.addConditionFields("stores_class_id");
            csc = sqlDao.getRecord(csc);
            if (csc!=null){
                String ids = csc.getFood_category_id();
                if (!StringUtils.isEmpty(ids)){
                    ProductService productService = new ProductService(SystemConfig.GetEleme2Config(), getElemeToken());
                    try {
                        Long id = Long.valueOf(ids);
                        Map<Long,OItem> map= productService.getItemsByCategoryId(id);
                        if (map!=null&&map.size()>0){
                            for (Long in : map.keySet()) {
                                //map.keySet()返回的是所有key的值
                                OItem oItem = map.get(in);//得到每个key多对用value的值
                                list.add(oItem);
                                System.out.println(in + "     " + oItem.getName());
                            }
                        }

                    } catch (ServiceException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        return list;
    }


    /**
     * 饿了么通过url获取image_hash
     * @param url
     * @return
     */
    public String getElemimage_hash(String url){
        File f = new File(url);
        url = "http://caidashi.oss-cn-hangzhou.aliyuncs.com/"+url;
        String image_hash="";
        ProductService productService = new ProductService(SystemConfig.GetEleme2Config(), getElemeToken());
        try {
             image_hash = productService.uploadImageWithRemoteUrl(url);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return image_hash;
    }

    /**
     * 获取商品规格(饿了么)
     * @param sgl_id
     * @return
     */
    private List<OSpec> getOSpecs(String sgl_id){
        List<OSpec> oSpecs = new ArrayList<OSpec>();
        CdsStandardList cs = new CdsStandardList();
        if (!StringUtils.isEmpty(sgl_id)){
            cs.setSgl_id(Integer.valueOf(sgl_id));
            cs.addConditionFields("sgl_id");
            List<CdsStandardList> list= sqlDao.getRecordList(cs);
            if (list!=null&&list.size()>0){
                for (int i = 0; i <list.size() ; i++) {
                    OSpec oSpec = new OSpec();
                    int id = list.get(i).getStandard_id();
                    oSpec.setSpecId(Long.valueOf(id));
                    oSpec.setName(list.get(i).getStandard_name());
                    oSpec.setPrice(list.get(i).getCurrent_price());
//                    oSpec.setStock(list.get(i).getElem_count());//库存
                    oSpec.setStock(100);//库存
                    oSpec.setMaxStock(1000);//最大库存
                    oSpec.setPackingFee(list.get(i).getBox_price());//包装费
                    oSpec.setOnShelf(1);//上下架
//                    oSpec.setExtendCode("1234567890");//商品扩展码
//                    oSpec.setBarCode("X148948686356666");//商品条形码
//                    oSpec.setWeight(123);//商品重量
//                    oSpec.setActivityLevel(0);//商品活动信息
//                    OSupplyLink supplyLink = new OSupplyLink();
//                    oSpec.setSupplyLink(supplyLink);
                    oSpecs.add(oSpec);
                }
            }
        }
        return oSpecs;
    }


    @RequestMapping(value = "elemeGoodsAll", method = RequestMethod.POST)
    @ResourceMethod(name = "饿了么(商品)绑定、更新、解绑", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage elemeGoodsAll(@RequestParam HashMap hashMap) {
        JsonMessage jm = new JsonMessage();
        jm.setStatus("0");
        String type = hashMap.get("type").toString();
        String sgl_id = hashMap.get("sgl_id").toString();
        String food_category_id = hashMap.get("food_category_id").toString();
        CdsStoresGoodsList csg = new CdsStoresGoodsList();
        if (!StringUtils.isEmpty(sgl_id)){
            csg.setSgl_id(Integer.valueOf(sgl_id));
            csg.addConditionField("sgl_id");
            csg = sqlDao.getRecord(csg);
            if (csg!=null){
                if ("1".equals(type)){
                    //绑定商品
                    ProductService productService = new ProductService(SystemConfig.GetEleme2Config(), getElemeToken());
                    Map<OItemCreateProperty,Object> properties = new HashMap<OItemCreateProperty,Object>();
                    properties.put(OItemCreateProperty.name,csg.getGood_name());
                    properties.put(OItemCreateProperty.description,csg.getGood_info());
                    if (!StringUtils.isEmpty(csg.getEgood_pic())){
                        String imageHash = getElemimage_hash(csg.getEgood_pic());
                        csg.setImage_hash(imageHash);
                        properties.put(OItemCreateProperty.imageHash,imageHash);
                    }
                    List<OSpec> oSpecs = getOSpecs(sgl_id); //获取商品规格

                    properties.put(OItemCreateProperty.specs,oSpecs);
                    try {
                        OItem oItem = productService.createItem(Long.valueOf(food_category_id), properties);
                        if (oItem!=null){
                            long id = oItem.getId();
                            csg.setFood_id(id+"");
                            jm.setStatus("1");
                        }

                    } catch (ServiceException e) {
                        e.printStackTrace();
                    }
                }else if ("2".equals(type)){
                    //跟新商品
                }else if("3".equals(type)){
                    //删除商品
                }
                csg.addConditionField("sgl_id");
                csg.addUnParamFields("sgl_id");
                sqlDao.updateRecord(csg);

            }
        }

        return jm;
    }



    /**
     * 获取商品规格(美团)
     * @param sgl_id
     * @return
     */
    private List<FoodSkuParam> getSku(String sgl_id){
        List<FoodSkuParam> foodSkuParams =  new ArrayList<FoodSkuParam>();
        CdsStandardList cs = new CdsStandardList();
        if (!StringUtils.isEmpty(sgl_id)){
            cs.setSgl_id(Integer.valueOf(sgl_id));
            cs.addConditionFields("sgl_id");
            List<CdsStandardList> list= sqlDao.getRecordList(cs);
            if (list!=null&&list.size()>0){
                for (int i = 0; i <list.size() ; i++) {
                    FoodSkuParam foodSkuParam = new FoodSkuParam();
                    foodSkuParam.setSku_id(list.get(i).getStandard_id()+"");
                    foodSkuParam.setStock(list.get(i).getMt_count()+"");
                    if (!StringUtils.isEmpty(list.get(i).getStandard_name())){
                        //规格名称不为空
                        foodSkuParam.setSpec(list.get(i).getStandard_name());
                    }else{
                        foodSkuParam.setSpec("默认");
                    }
                    foodSkuParam.setPrice(list.get(i).getCurrent_price()+"");
                    foodSkuParam.setBox_num(list.get(i).getBox_count()+"");
                    foodSkuParam.setBox_price(list.get(i).getBox_price()+"");
                    foodSkuParams.add(foodSkuParam);
                }
            }
        }
        return foodSkuParams;
    }



    @RequestMapping(value = "mtGoodsAll", method = RequestMethod.POST)
    @ResourceMethod(name = "美团(绑定、更新、删除)", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage mtGoodsAll(@RequestParam HashMap hashMap) {
        JsonMessage jmsg = new JsonMessage();
        CdsStoresGoodsList csg = new CdsStoresGoodsList();
        String type = hashMap.get("type").toString();
        String sgl_id = hashMap.get("sgl_id").toString();
        String category_name =  hashMap.get("category_name").toString();
        if (!StringUtils.isEmpty(sgl_id)){
            csg.setSgl_id(Integer.valueOf(sgl_id));
        }
        csg.addConditionField("sgl_id");
        csg = sqlDao.getRecord(csg);
        if (csg!=null){
            FoodParam foodParam = getFoodParam(csg,category_name);//获取饿了么食物参数
            if ("1".equals(type)){
                //绑定
                try {
                   String result =  APIFactoryMeituan.getFoodAPI().foodInitData(SystemConfig.GetSystemParamMeituan(),foodParam);
                   if ("ok".equals(result)){
                       jmsg.setStatus("1");
                       csg.setMt_isband("ok");
                       csg.addConditionField("sgl_id");
                       csg.addUnParamFields("sgl_id");
                       sqlDao.updateRecord(csg);
                   }
                } catch (com.opensdk.meituan.exception.ApiOpException e) {
                    e.printStackTrace();
                } catch (com.opensdk.meituan.exception.ApiSysException e) {
                    e.printStackTrace();
                }
            }else if ("2".equals(type)){
                //更新
                try {
                    String result =  APIFactoryMeituan.getFoodAPI().foodInitData(SystemConfig.GetSystemParamMeituan(),foodParam);
                    if ("ok".equals(result)){
                        jmsg.setStatus("1");
                        csg.setMt_isband("ok");
                        csg.addConditionField("sgl_id");
                        csg.addUnParamFields("sgl_id");
                        sqlDao.updateRecord(csg);
                    }
                } catch (com.opensdk.meituan.exception.ApiOpException e) {
                    e.printStackTrace();
                } catch (com.opensdk.meituan.exception.ApiSysException e) {
                    e.printStackTrace();
                }
            }else if ("3".equals(type)){
                //删除
                String stores_brand_id =  hashMap.get("stores_brand_id").toString();
                try {
                    String result = APIFactoryMeituan.getFoodAPI().foodDelete(SystemConfig.GetSystemParamMeituan(),stores_brand_id,sgl_id);
                    if ("ok".equals(result)){
                        jmsg.setStatus("1");
                        csg.setMt_isband("");
                        csg.addConditionField("sgl_id");
                        csg.addUnParamFields("sgl_id");
                        sqlDao.updateRecord(csg);
                    }
                } catch (com.opensdk.meituan.exception.ApiOpException e) {
                    e.printStackTrace();
                } catch (com.opensdk.meituan.exception.ApiSysException e) {
                    e.printStackTrace();
                }
            }

        }
        return jmsg;
    }


    /**
     * 获取饿了么食物绑定参数
     * @param csg
     * @param category_name
     * @return
     */
    private  FoodParam getFoodParam(CdsStoresGoodsList csg,String category_name){
        FoodParam foodParam = new FoodParam();
        String ulr= "http://caidashi.oss-cn-hangzhou.aliyuncs.com/"+csg.getGood_pic();
        String image_hash = getMtimage_hash(ulr,csg.getStores_brand_id()+"",csg.getSgl_id()+"");
        csg.setMt_image_hash(image_hash);
        foodParam.setPicture(image_hash);
        foodParam.setApp_food_code(csg.getSgl_id()+"");//商品id
        foodParam.setApp_poi_code(csg.getStores_brand_id()+"");//商铺id
        foodParam.setName(csg.getGood_name());
        foodParam.setDescription(csg.getGood_info());//商品描述
        foodParam.setCategory_name(category_name);//类别名称
        foodParam.setMin_order_count(1);//最小购买量
        foodParam.setUnit("份");//单位
        foodParam.setBox_num(Float.valueOf(1));
        foodParam.setBox_price(Float.valueOf(2));
        if(csg.getStatus()==1){
            //售卖中
            foodParam.setIs_sold_out(0);
        }else{
            //暂停售卖
            foodParam.setIs_sold_out(1);
        }
        foodParam.setSequence(csg.getListorder());//排序
        List<FoodSkuParam> foodSkuParams = getSku(csg.getSgl_id()+"");//获取规格
        foodParam.setSkus(foodSkuParams);
        return foodParam;
    }



    @RequestMapping(value = "mtPlBind", method = RequestMethod.POST)
    @ResourceMethod(name = "美团批量绑定", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage mtPlBind(@RequestParam  HashMap hashMap) {
        JsonMessage jm = new JsonMessage();
        jm.setStatus("0");
        String json = hashMap.get("allData").toString();
        JSONArray array = JSONArray.parseArray(json.toString());
        String stores_brand_id = "";
        if (array!=null&&array.size()>0){
            List<FoodParam> foodParams = new ArrayList<FoodParam>();
            String sgl_ids = " sgl_id in ("; //用于批量修改本地数据
            for (int i = 0; i <array.size() ; i++) {
                JSONObject jo  =array.getJSONObject(i);
                String sgl_id = jo.getString("sgl_id");
                String class_name = jo.getString("class_name");
                stores_brand_id = jo.getString("stores_brand_id");
                if (!StringUtils.isEmpty(sgl_id)&&!StringUtils.isEmpty(stores_brand_id)){
                    sgl_ids = sgl_ids+sgl_id+",";
                    if(i==array.size()-1){
                        sgl_ids=sgl_ids.substring(0,(sgl_ids.length()-1));
                        sgl_ids=sgl_ids+')';
                    }
                    CdsStoresGoodsList csg = new CdsStoresGoodsList();
                    csg.setSgl_id(Integer.valueOf(sgl_id));
                    csg.addConditionField("sgl_id");
                    csg = sqlDao.getRecord(csg);
                    if (csg!=null){
                        FoodParam foodParam = getFoodParam(csg,class_name);
                        foodParams.add(foodParam);
                    }
                }
            }
            try {
                String result = APIFactoryMeituan.getFoodAPI().foodBatchInitData(SystemConfig.GetSystemParamMeituan(),stores_brand_id,foodParams);
                if ("ok".equals(result)){
                    if (!" sgl_id in (".equals(sgl_ids)){
                        CdsStoresClass csc = new CdsStoresClass();
                        csc.setClass_desc(sgl_ids);
                        sqlDao.updateRecord("cds_ptstores_manage.mtPlBind",csc);
                    }
                    jm.setStatus("1");
                }
            } catch (com.opensdk.meituan.exception.ApiOpException e) {
                e.printStackTrace();
            } catch (com.opensdk.meituan.exception.ApiSysException e) {
                e.printStackTrace();
            }
        }
        return jm;
    }



    @RequestMapping(value = "baiduClasssAll", method = RequestMethod.POST)
    @ResourceMethod(name = "百度(绑定、更新、删除)", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage baiduClasssAll(@RequestParam HashMap hashMap) {
        JsonMessage jmsg = new JsonMessage();
        CdsStoresClass csg = new CdsStoresClass();
        String type = hashMap.get("type").toString();
        String stores_class_id = hashMap.get("stores_class_id").toString();
        if (!StringUtils.isEmpty(stores_class_id)){
            csg.setStores_class_id(Integer.valueOf(stores_class_id));
        }
        csg.addConditionField("stores_class_id");
        csg = sqlDao.getRecord(csg);
        if (csg!=null){
//            Category category = new Category();
//            category.setNambai_shop_ide("2095976649");
//            category.setName(csg.getClass_name());
//            category.setMust(1);
//            category.setRank(csg.getListorder());
            Shop shop = new Shop();
            shop.setBaidu_shop_id("2095976649");
            if ("1".equals(type)){
                //绑定
                String method="shop.close";
                String str = HttpUtils.postRequest(method,shop);
                System.out.println(str);

            }else if ("2".equals(type)){
                //更新


            }else if ("3".equals(type)){
                //删除

            }

        }
        return jmsg;
    }

































}
