package com.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.framework.annotation.ResourceMethod;
import com.framework.controller.BasicController;
import com.framework.mapping.JsonMessage;
import com.framework.mapping.PageResult;
import com.framework.mapping.system.*;
import com.framework.util.HttpUtil;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

import static com.framework.annotation.CheckType.NO_CHECK;

/**
 * Created by Administrator on 2017/4/24 0024.Erp客服主页接口
 */
@Controller
@RequestMapping("/ErpApi")
public class KeFuController extends BasicController {

    /**
     * 初始化信息（店铺信息列表，员工列表，erp商品和菜品分类列表）
     */
    @RequestMapping(value = "getStoresInfo", method = RequestMethod.GET)
    @ResourceMethod(name = "获取初始化信息", check = NO_CHECK)
    @ResponseBody
    public JsonMessage getStoresInfo() throws Exception {
        CdsStores cdsStores = new CdsStores();
        cdsStores.setStatus(1);
        cdsStores.addParamFields("stores_id,name");
        cdsStores.addConditionField("status");
        List<CdsStores> storesList = sqlDao.getRecordList(cdsStores);
        List result = new ArrayList();
        for (CdsStores stores : storesList) {
            HashMap map = new HashMap();
            map.put("stores_id", stores.getStores_id());
            map.put("name", stores.getName());
            result.add(map);
        }
        List erpClassList = sqlDao.getRecordList("cds_kefu.getClassType");
        List goodsClassList = sqlDao.getRecordList("cds_kefu.getClass");
        List userList = sqlDao.getRecordList("cds_kefu.getUsers");
        Map map = new HashMap();
        map.put("erpClassList", erpClassList);
        map.put("goodsClassList", goodsClassList);
        map.put("userList", userList);
        map.put("storesList", result);
        return new JsonMessage(1, "获取初始化信息", map);

    }

    /**
     * 平板电脑设置（显示，添加，修改）
     */
    @RequestMapping(value = "getChufangTask", method = RequestMethod.POST)
    @ResourceMethod(name = "获取平板电脑", check = NO_CHECK)
    @ResponseBody
    public JsonMessage getChufangTask(Integer stores_id) throws Exception {
        CdsChufanTask task = new CdsChufanTask();
        task.setStores_id(stores_id);
        task.addConditionField("stores_id");
        List taskList = sqlDao.getRecordList(task);
        return new JsonMessage(1, "获取厨房下达机成功", taskList);
    }

    @RequestMapping(value = "addChufangTask", method = RequestMethod.POST)
    @ResourceMethod(name = "添加厨房下达机", check = NO_CHECK)
    @ResponseBody
    public JsonMessage addChufangTask(CdsChufanTask cdsChufanTask) throws Exception {
        cdsChufanTask.addParamFields();
        cdsChufanTask.removeParamField("es_id");
        sqlDao.insertRecord(cdsChufanTask);
        return new JsonMessage(1, "添加厨房下达机成功");
    }

    @RequestMapping(value = "updateChufangTask", method = RequestMethod.POST)
    @ResourceMethod(name = "修改厨房下达机", check = NO_CHECK)
    @ResponseBody
    public JsonMessage updateChufangTask(CdsChufanTask cdsChufanTask) throws Exception {
        if (cdsChufanTask.getStatus() == 2) {
            cdsChufanTask.setStatus(1);
            cdsChufanTask.resetParamField("status");
            cdsChufanTask.addConditionField("es_id");
            sqlDao.updateRecord(cdsChufanTask);
            return new JsonMessage(1, "启用下达机成功");

        } else if (cdsChufanTask.getStatus() == 3) {
            cdsChufanTask.setStatus(0);
            cdsChufanTask.resetParamField("status");
            cdsChufanTask.addConditionField("es_id");
            sqlDao.updateRecord(cdsChufanTask);
            return new JsonMessage(1, "停用下达机成功");
        }

        cdsChufanTask.addParamFields();
        cdsChufanTask.removeParamField("es_id");
        cdsChufanTask.addConditionField("es_id");
        sqlDao.updateRecord(cdsChufanTask);
        return new JsonMessage(1, "修改厨房下达机成功");
    }

    /**
     * 厨房出餐机设置（显示，添加，修改，删除）
     */
    @RequestMapping(value = "getMealErpInfo", method = RequestMethod.POST)
    @ResourceMethod(name = "获取出餐机信息包含erp商品信息", check = NO_CHECK)
    @ResponseBody
    public JsonMessage getMealErpInfo(Integer stores_id) throws Exception {
        List<HashMap> erpInfoList = sqlDao.getRecordList("cds_kefu.get_cf_meal", stores_id);
        for (HashMap erpInfo : erpInfoList) {
            if (erpInfo.get("cdsMsGoods") != null && !erpInfo.get("cdsMsGoods").equals("")) {
                String goods = erpInfo.get("cdsMsGoods").toString();
                JSONArray json = JSONArray.parseArray("[" + erpInfo.get("cdsMsGoods").toString() + "]");
                erpInfo.remove("cdsMsGoods");
                erpInfo.put("cdsMsGoods", json);
            }
        }
        return new JsonMessage(1, "获取出餐机信息成功", erpInfoList);
    }

    @RequestMapping(value = "getChuErpGoods", method = RequestMethod.GET)
    @ResourceMethod(name = "获取Erp商品", check = NO_CHECK)
    @ResponseBody
    public JsonMessage getChuErpGoods(@RequestParam HashMap formInfo) throws Exception {
        Integer count = sqlDao.getRecord("cds_stores_order_info.getErpPageCount");
        formInfo = getPageInfo(formInfo, count);
        List goodsList = sqlDao.getRecordList("cds_stores_order_info.searchGoods", formInfo);
        formInfo.put("goodsList", goodsList);
        return new JsonMessage(1, "获取ERP商品成功", formInfo);
    }

    @RequestMapping(value = "addChufangMeal", method = RequestMethod.POST)
    @ResourceMethod(name = "添加厨房出餐机", check = NO_CHECK)
    @ResponseBody
    public JsonMessage addChufangMeal(CdsChufanMeal cdsChufanMeal) throws Exception {
        Integer stores_id = cdsChufanMeal.getStores_id();
        Integer ct_num = sqlDao.getRecord("cds_kefu.getMealNum", stores_id);
        if (ct_num == null) {
            ct_num = 0;
        }
        ct_num = ct_num + 1;
        cdsChufanMeal.setCt_num(ct_num);
        cdsChufanMeal.addParamFields();
        cdsChufanMeal.removeParamField("cm_id");
        sqlDao.insertRecord(cdsChufanMeal);
        return new JsonMessage(1, "添加厨房出餐机成功");
    }

    @RequestMapping(value = "addMealErpGoods", method = RequestMethod.POST)
    @ResourceMethod(name = "添加厨房出餐机Erp菜品", check = NO_CHECK)
    @ResponseBody
    public JsonMessage addMealErpGoods(@RequestParam HashMap formInfo) throws Exception {
        List list = JSONArray.parseArray(formInfo.get("list").toString());

        for (Object meal : list) {
            JSONObject json = JSONObject.parseObject(meal.toString());
            CdsChufanMealGoods mealGoods = new CdsChufanMealGoods();
            mealGoods.setCm_id(json.getInteger("cm_id"));
            mealGoods.setGood_id(json.getInteger("good_id"));
            mealGoods.addParamFields("cm_id,good_id");
            sqlDao.insertRecord(mealGoods);
        }
        return new JsonMessage(1, "添加厨房出餐机Erp菜品成功");
    }

    @RequestMapping(value = "updateChufangMeal", method = RequestMethod.POST)
    @ResourceMethod(name = "修改厨房出餐机", check = NO_CHECK)
    @ResponseBody
    public JsonMessage updateChufangMeal(CdsChufanMeal cdsChufanMeal) throws Exception {

        if (cdsChufanMeal.getStatus() == 2) {
            cdsChufanMeal.setStatus(1);
            cdsChufanMeal.resetParamField("status");
            cdsChufanMeal.addConditionField("cm_id");
            sqlDao.updateRecord(cdsChufanMeal);
            return new JsonMessage(1, "启用出餐机成功");

        } else if (cdsChufanMeal.getStatus() == 3) {
            cdsChufanMeal.setStatus(0);
            cdsChufanMeal.resetParamField("status");
            cdsChufanMeal.addConditionField("cm_id");
            sqlDao.updateRecord(cdsChufanMeal);
            return new JsonMessage(1, "停用出餐机成功");
        }

        cdsChufanMeal.addParamFields();
        cdsChufanMeal.removeParamField("cm_id");
        cdsChufanMeal.addConditionField("cm_id");
        sqlDao.updateRecord(cdsChufanMeal);
        return new JsonMessage(1, "修改厨房出餐机成功");

    }

    @RequestMapping(value = "delMealErpGoods", method = RequestMethod.POST)
    @ResourceMethod(name = "删除出餐机单个菜品", check = NO_CHECK)
    @ResponseBody
    public JsonMessage delMealErpGoods(CdsChufanMeal cdsChufanMeal) throws Exception {

        CdsChufanMealGoods goods = new CdsChufanMealGoods();
        goods.setCm_id(cdsChufanMeal.getCm_id());
        goods.setGood_id(cdsChufanMeal.getGood_id());
        goods.addConditionFields("good_id,cm_id");
        sqlDao.deleteRecord(goods);

        return new JsonMessage(1, "删除单个菜品成功");
    }

    /**
     * Erp商品管理
     */
//同时用于出餐机配置商品
    @RequestMapping(value = "getErpGoods", method = RequestMethod.GET)
    @ResourceMethod(name = "获取Erp商品", check = NO_CHECK)
    @ResponseBody
    public JsonMessage getErpGoods(@RequestParam HashMap formInfo) throws Exception {

        //查询上下架商品：1上架，2下架
        formInfo = getFormInfo(formInfo);//过滤空字符
        if(formInfo.get("good_key")!=null){
            formInfo.remove("status");
            formInfo.remove("class_id");
            List pageResult = sqlDao.getRecordList("cds_kefu.getErpGoodsBySelect", formInfo);
            return new JsonMessage(1, "关键字筛选查询成功", pageResult);
        }

        if (formInfo.get("status") != null || formInfo.get("class_id") != null) {
            List pageResult = sqlDao.getRecordList("cds_kefu.getErpGoodsBySelect", formInfo);
            return new JsonMessage(1, "筛选查询成功", pageResult);
        }

        formInfo = getPageInfoByMybatis(formInfo, "good_id", "desc", 20);
        PageResult pageResult = queryReturnPageResult("cds_kefu.getErpGoods", formInfo);

        return new JsonMessage(1, "获取ERP商品成功", pageResult);
    }

    @RequestMapping(value = "addErpGoods", method = RequestMethod.POST)
    @ResourceMethod(name = "添加Erp商品", check = NO_CHECK)
    @ResponseBody
    public JsonMessage addErpGoods(CdsMsGoods cdsMsGoods) throws Exception {
        cdsMsGoods.setUptime(new Date());
        cdsMsGoods.addParamFields();
        String class_id = cdsMsGoods.getClass_id() + "%";
        HashMap map = new HashMap();
        map.put("class_id", class_id);

        Map result = sqlDao.getRecord("cds_kefu.getGoodsNum", map);
        String good_num = cdsMsGoods.getClass_id() + "000001";
        if (result.get("max_num") != null) {
            good_num = Long.parseLong(result.get("max_num").toString()) + 1L + "";
        }
        cdsMsGoods.setGood_num(good_num);
        cdsMsGoods.setIs_sync(0);
        cdsMsGoods.setPinxin(getPinYing(cdsMsGoods.getGood_name()));
        cdsMsGoods.removeParamFields("good_id,good_pic");
        sqlDao.insertRecord(cdsMsGoods);
        return new JsonMessage(1, "添加ERP商品成功");
    }

    @RequestMapping(value = "addMaterial", method = RequestMethod.POST)
    @ResourceMethod(name = "同步原料", check = NO_CHECK)
    @ResponseBody
    public JsonMessage addMaterial(@RequestParam HashMap formInfo) throws Exception {
        String url = "http://114.55.97.90:8018/Handle/SalesHandler.ashx?m=FoodSync";
        JSONObject json = JSON.parseObject(formInfo.get("json").toString());
        Map map = new HashMap();

        map.put("fid", json.getString("fid"));
        map.put("fname", json.getString("fname"));
        map.put("mates", json.getString("mates"));

        String result = HttpUtil.postRequest(url, map);
        int status = JSON.parseObject(result).getInteger("result");
        if (status == 1) {
            CdsMsGoods cdsMsGoods = new CdsMsGoods();
            cdsMsGoods.setGood_id(json.getInteger("fid"));
            cdsMsGoods.setIs_sync(1);
            cdsMsGoods.resetParamField("is_sync");
            cdsMsGoods.addConditionField("good_id");
            sqlDao.updateRecord(cdsMsGoods);
        } else if (status == -1) {
            return new JsonMessage(0, "同步原料失败");
        }

        return new JsonMessage(1, "同步原料成功");
    }


    //上下架
    @RequestMapping(value = "updateErpGoods", method = RequestMethod.POST)
    @ResourceMethod(name = "修改Erp商品", check = NO_CHECK)
    @ResponseBody
    public JsonMessage updateErpGoods(CdsMsGoods cdsMsGoods) throws Exception {

        cdsMsGoods.setUptime(new Date());
        if (cdsMsGoods.getStatus() == 0) {
            cdsMsGoods.setStatus(1);
            cdsMsGoods.setUptime(new Date());
            cdsMsGoods.resetParamFields("status,uptime");
            cdsMsGoods.addConditionField("good_id");
            sqlDao.updateRecord(cdsMsGoods);
            return new JsonMessage(1, "上架成功");

        } else if (cdsMsGoods.getStatus() == 3) {
            cdsMsGoods.setStatus(2);
            cdsMsGoods.setUptime(new Date());
            cdsMsGoods.resetParamFields("status,uptime");
            cdsMsGoods.addConditionField("good_id");
            sqlDao.updateRecord(cdsMsGoods);
            return new JsonMessage(1, "下架成功");
        }

        cdsMsGoods.addParamFields();
        cdsMsGoods.removeParamField("good_id,good_pic");
        cdsMsGoods.addConditionField("good_id");
        sqlDao.updateRecord(cdsMsGoods);
        return new JsonMessage(1, "修改erp商品成功");
    }

    /**
     * erp同步销售数据，获取销售数据
     */
    @RequestMapping(value = "saleSync", method = RequestMethod.GET)
    @ResourceMethod(name = "获取销售数据", check = NO_CHECK)
    @ResponseBody
    public JsonMessage saleSync(@RequestParam HashMap formInfo) throws Exception {
        if (formInfo.get("date") == null) {
            return new JsonMessage(0, "缺少参数date");
        }
        if (formInfo.get("stores_id") == null) {
            return new JsonMessage(0, "缺少参数stores_id");
        }
        List<HashMap> list = sqlDao.getRecordList("cds_kefu.getSaleInfo", formInfo);

        return new JsonMessage(1, "获取销售数据", list);
    }

    /**
     * 菜品管理
     */
    //菜品管理，添加对应erp商品到菜品 套餐对应多个erp商品
    @RequestMapping(value = "getGoodsInfo", method = RequestMethod.POST)
    @ResourceMethod(name = "获取菜品", check = NO_CHECK)
    @ResponseBody
    public JsonMessage getGoodsInfo() throws Exception {
        List goodsList = sqlDao.getRecordList("cds_kefu.getGoodsInfo");
        return new JsonMessage(1, "获取菜品成功", goodsList);
    }

    @RequestMapping(value = "getErpGoodsInfo", method = RequestMethod.POST)
    @ResourceMethod(name = "获取菜品erp商品详情", check = NO_CHECK)
    @ResponseBody
    public JsonMessage getErpGoodsInfo(@RequestParam HashMap formInfo) throws Exception {
        List goodsList = sqlDao.getRecordList("cds_kefu.getErpGoodsInfo", formInfo);
        return new JsonMessage(1, "获取菜品erp商品详情", goodsList);
    }

    @RequestMapping(value = "delErpGoodsInfo", method = RequestMethod.POST)
    @ResourceMethod(name = "删除菜品erp商品详情", check = NO_CHECK)
    @ResponseBody
    public JsonMessage delErpGoodsInfo(CdsErpGoodList cdsErpGoodList) throws Exception {
        sqlDao.deleteRecord(cdsErpGoodList);
        return new JsonMessage(1, "删除菜品erp商品详情");
    }

    @RequestMapping(value = "addGoods", method = RequestMethod.POST)
    @ResourceMethod(name = "添加菜品", check = NO_CHECK)
    @ResponseBody
    public JsonMessage addGoods(CdsGoodsInfo cdsGoodsInfo, MultipartFile file) throws Exception {
        // 判断文件是否为空
        if (file != null) {
            if (!file.isEmpty()) {
                // 文件保存路径
                String filePath = "D:/file/" + file.getOriginalFilename();
                // 转存文件
                File img = new File(filePath);
                if (!img.exists()) {
                    img.mkdirs();
                }
                file.transferTo(img);
            }
        }
        cdsGoodsInfo.setGood_pic(file.getOriginalFilename());
        cdsGoodsInfo.setAdd_date(new Date());
        cdsGoodsInfo.setListorder(0);
        cdsGoodsInfo.setStatus(0);
        cdsGoodsInfo.setPinxin(getPinYing(cdsGoodsInfo.getGood_name()));
        cdsGoodsInfo.addParamFields();
        cdsGoodsInfo.removeParamField("good_id");
        sqlDao.insertRecord(cdsGoodsInfo);
        return new JsonMessage(1, "添加商品成功");
    }

    @RequestMapping(value = "addErpGoodsToGoods", method = RequestMethod.POST)
    @ResourceMethod(name = "添加erp商品到菜品", check = NO_CHECK)
    @ResponseBody
    public JsonMessage addErpGoodsToGoods(@RequestParam HashMap formInfo) throws Exception {
        List list = JSONArray.parseArray(formInfo.get("list").toString());
        CdsErpGoodList cdsErpGoodList = new CdsErpGoodList();
        cdsErpGoodList.setGood_id(Integer.parseInt(formInfo.get("good_id").toString()));
        cdsErpGoodList.addConditionField("good_id");
        sqlDao.deleteRecord(cdsErpGoodList);

        for (Object erp : list) {
            JSONObject json = JSONObject.parseObject(erp.toString());
            CdsErpGoodList erpGoods = new CdsErpGoodList();
            erpGoods.setGood_count(json.getInteger("good_count"));
            erpGoods.setGood_id(json.getInteger("good_id"));
            erpGoods.setMs_good_id(json.getInteger("ms_good_id"));
            erpGoods.addParamFields("ms_good_id,good_id,good_count");
            sqlDao.insertRecord(erpGoods);
        }
        return new JsonMessage(1, "添加erp商品到菜品");
    }


    @RequestMapping(value = "updateGoods", method = RequestMethod.POST)
    @ResourceMethod(name = "修改菜品", check = NO_CHECK)
    @ResponseBody
    public JsonMessage updateGoods(CdsGoodsInfo cdsGoodsInfo, MultipartFile file) throws Exception {
        if (cdsGoodsInfo.getStatus() == 3) {
            cdsGoodsInfo.setStatus(1);
            cdsGoodsInfo.resetParamFields("status");
            cdsGoodsInfo.addConditionField("good_id");
            sqlDao.updateRecord(cdsGoodsInfo);
            return new JsonMessage(1, "上架成功");

        } else if (cdsGoodsInfo.getStatus() == 4) {
            cdsGoodsInfo.setStatus(0);
            cdsGoodsInfo.resetParamFields("status");
            cdsGoodsInfo.addConditionField("good_id");
            sqlDao.updateRecord(cdsGoodsInfo);
            return new JsonMessage(1, "下架成功");
        }

        cdsGoodsInfo.addParamFields();

        // 判断文件是否为空
        if (file != null) {
            if (!file.isEmpty()) {
                // 文件保存路径
                String filePath = "D:/file/" + file.getOriginalFilename();//TODO:正式存放文件地址待修改
                // 转存文件
                File img = new File(filePath);
                if (!img.exists()) {
                    img.mkdirs();
                }
                file.transferTo(img);
                cdsGoodsInfo.setGood_pic(file.getOriginalFilename());
            } else {
                cdsGoodsInfo.removeParamField("good_id,good_pic");
            }
        }

        cdsGoodsInfo.removeParamField("good_id");
        cdsGoodsInfo.addConditionField("good_id");
        sqlDao.updateRecord(cdsGoodsInfo);
        return new JsonMessage(1, "修改erp商品成功");
    }

    /**
     * 事业部设置
     */
    @RequestMapping(value = "business/{action}", method = RequestMethod.POST)
    @ResourceMethod(name = "事业部管理接口", check = NO_CHECK)
    @ResponseBody
    public JsonMessage businessMg(@PathVariable("action") String action, CdsBusinessUnit cdsBusinessUnit) throws Exception {
        switch (action) {
            case "add":
                cdsBusinessUnit.addParamFields();
                cdsBusinessUnit.removeParamField("bu_id");
                sqlDao.insertRecord(cdsBusinessUnit);
                break;

            case "del":
                cdsBusinessUnit.addConditionField("bu_id");
                sqlDao.deleteRecord(cdsBusinessUnit);
                break;

            case "up":
                cdsBusinessUnit.addConditionField("bu_id");
                cdsBusinessUnit.addParamFields();
                cdsBusinessUnit.removeParamField("bu_id");
                sqlDao.updateRecord(cdsBusinessUnit);
                break;

            case "sel":
                List<HashMap> resultList = sqlDao.getRecordList("cds_kefu.getBusinessInfo");
                List list = new ArrayList();
                for (HashMap result : resultList) {
                    if (result.get("stores") == null) {
                        result.put("stores", list);
                    } else {
                        result.put("stores", JSONArray.parseArray("[" + result.get("stores") + "]"));
                    }
                    if (result.get("users") == null) {
                        result.put("users", list);
                    } else {
                        result.put("users", JSONArray.parseArray("[" + result.get("users") + "]"));
                    }

                }
                return new JsonMessage(1, "获取事业部信息成功", resultList);
        }
        return new JsonMessage(1, action + "操作成功");
    }

    @RequestMapping(value = "userAndShop/{action}", method = RequestMethod.POST)
    @ResourceMethod(name = "事业部人员和店铺管理接口", check = NO_CHECK)
    @ResponseBody
    public JsonMessage usersMg(@PathVariable("action") String action, @RequestParam HashMap formInfo) throws Exception {
        switch (action) {
            case "getUser":
                CdsMember member = new CdsMember();
                member.setPhone(formInfo.get("phone").toString());
                member.setBrand_id(1);//菜大师品牌
                member.addParamFields("name,head_pic,member_id");
                member.addConditionFields("phone,brand_id");
                member = sqlDao.getRecord(member);
                if (member == null) {
                    return new JsonMessage(0, "该用户还没有绑定菜微信账号");
                }
                formInfo.put("member_id", member.getMember_id());
                formInfo.put("head_pic", member.getHead_pic());
                formInfo.put("name", member.getName());
                return new JsonMessage(1, "获取用户信息成功", formInfo);

            case "getShops":
                CdsStores cdsStores = new CdsStores();
                cdsStores.setStatus(1);
                cdsStores.addParamFields("stores_id,name");
                cdsStores.addConditionField("status");
                List<CdsStores> storesList = sqlDao.getRecordList(cdsStores);
                List result = new ArrayList();
                for (CdsStores stores : storesList) {
                    HashMap map = new HashMap();
                    map.put("stores_id", stores.getStores_id());
                    map.put("name", stores.getName());
                    result.add(map);
                }
                return new JsonMessage(1, "获取用户信息成功", result);

            case "addUsersOrShops":
                if (formInfo.get("users") != null) {
                    List users = JSONArray.parseArray(formInfo.get("users").toString());
                    for (Object user : users) {
                        JSONObject json = JSONObject.parseObject(user.toString());
                        CdsBusinessMemberList business_users = new CdsBusinessMemberList();
                        business_users.setBu_id(json.getInteger("bu_id"));
                        business_users.setMember_id(json.getInteger("member_id"));
                        business_users.addParamFields("bu_id,member_id");
                        sqlDao.insertRecord(business_users);
                    }
                }

                if (formInfo.get("shops") != null) {
                    List shops = JSONArray.parseArray(formInfo.get("shops").toString());
                    for (Object shop : shops) {
                        JSONObject json = JSONObject.parseObject(shop.toString());
                        CdsBusinessStoresList business_shops = new CdsBusinessStoresList();
                        business_shops.setBu_id(json.getInteger("bu_id"));
                        business_shops.setStores_id(json.getInteger("stores_id"));
                        business_shops.addParamFields("bu_id,stores_id");
                        sqlDao.insertRecord(business_shops);
                    }
                }

                break;

            case "delUsersOrShops":
                if (formInfo.get("users") != null) {
                    List users = JSONArray.parseArray(formInfo.get("users").toString());
                    for (Object user : users) {
                        JSONObject json = JSONObject.parseObject(user.toString());
                        CdsBusinessMemberList business_users = new CdsBusinessMemberList();
                        business_users.setBsl_id(json.getInteger("bsl_id"));
                        sqlDao.deleteRecord(business_users);
                    }
                }

                if (formInfo.get("shops") != null) {
                    List shops = JSONArray.parseArray(formInfo.get("shops").toString());
                    for (Object shop : shops) {
                        JSONObject json = JSONObject.parseObject(shop.toString());
                        CdsBusinessStoresList business_shops = new CdsBusinessStoresList();
                        business_shops.setBsl_id(json.getInteger("bsl_id"));
                        sqlDao.deleteRecord(business_shops);
                    }
                }

                break;
        }
        return new JsonMessage(1, action + "操作成功");
    }


    public String getPinYing(String name) {
        String pinxin = "";
        char[] nameChar = name.toCharArray();
        HanyuPinyinOutputFormat defaultFormat =
                new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    pinxin += PinyinHelper.toHanyuPinyinStringArray
                            (nameChar[i], defaultFormat)[0];

                    //pinxin = pinxin.toUpperCase();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return pinxin;
    }

    //获取分页信息
    public HashMap getPageInfo(HashMap formInfo, Integer count) {
        int totalNum = 0;
        if (formInfo.get("page") == null) {
            formInfo.put("page", 1);
        }
        if (formInfo.get("rows") == null) {
            formInfo.put("rows", 20);
        }

        Integer page = Integer.parseInt(formInfo.get("page").toString());
        Integer rows = Integer.parseInt(formInfo.get("rows").toString());
        Integer num1 = (page - 1) * rows + 1;
        Integer num2 = page * rows;
        if (count != null) {
            totalNum = count / rows + (count % rows == 0 ? 0 : 1);
        }

        formInfo.put("num1", num1);
        formInfo.put("num2", num2);
        formInfo.put("totalNum", totalNum);

        return formInfo;
    }

    //获取分页信息通过分页插件
    public HashMap getPageInfoByMybatis(HashMap formInfo, String sort, String order, Integer rows) {
        if (formInfo.get("page") == null) {
            formInfo.put("page", 1);
        }
        if (formInfo.get("rows") == null) {
            formInfo.put("rows", rows);
        }
        formInfo.put("sort", sort);
        formInfo.put("order", order);

        return formInfo;
    }

    //去空字符
    public HashMap getFormInfo(HashMap formInfo) {
        Iterator iter = formInfo.entrySet().iterator();
        while (iter.hasNext()) {
            HashMap.Entry entry = (HashMap.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            if(val.toString().equals("")){
                iter.remove();
            }
        }
        return formInfo;
    }

}



