package com.api;

import com.framework.annotation.ResourceMethod;
import com.framework.controller.BasicController;
import com.framework.mapping.JsonMessage;
import com.framework.mapping.system.*;
import com.framework.util.StringUtil;
import com.service.ChufanXiadaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.framework.annotation.CheckType.CHECK_CHUFAN;


/**
 * Created by Administrator on 2017/2/4.厨房下达接口
 */
@Controller
@RequestMapping("/ChufanXiada")
public class ChufanXiadaController extends BasicController {

    @Resource(name = "ChufanXiadaService")
    protected ChufanXiadaService chufanXiadaService;

    private static String keypassword = "A622625ufdki12yui3";

    @RequestMapping(value = "login")
    @ResourceMethod(name = "登录接口", check = CHECK_CHUFAN)
    @ResponseBody
    public JsonMessage login(@RequestParam HashMap formInfo) throws Exception {
        if (formInfo.get("username") == null) {
            return new JsonMessage(0, "用户名不能为空");
        }
        if (formInfo.get("password") == null) {
            return new JsonMessage(0, "密码不能为空");
        }
        String username = formInfo.get("username").toString();
        String password = formInfo.get("password").toString();

        CdsChufanTask cdsChufanTask = new CdsChufanTask();
        cdsChufanTask.setCt_user(username);
        cdsChufanTask.setCt_password(password);
        cdsChufanTask.setStatus(1);
        cdsChufanTask.addConditionFields("ct_user,ct_password,status");
        cdsChufanTask = sqlDao.getRecord(cdsChufanTask);
        if (cdsChufanTask == null) {
            return new JsonMessage(0, "登录账户不存在");
        }

        CdsStores cdsStores = new CdsStores();
        cdsStores.setStores_id(cdsChufanTask.getStores_id());
        cdsStores.addConditionField("stores_id");
        cdsStores.addParamFields("name");
        cdsStores = sqlDao.getRecord(cdsStores);
        if (cdsStores == null) {
            return new JsonMessage(0, "商铺不存在");
        }

        Map resultMap = new HashMap();
        resultMap.put("es_id", cdsChufanTask.getEs_id());
        resultMap.put("username", cdsChufanTask.getCt_user());
        resultMap.put("password", cdsChufanTask.getCt_password());
        resultMap.put("stores_id", cdsChufanTask.getStores_id());
        resultMap.put("stores_name", cdsStores.getName());
        resultMap.put("print_ip", cdsChufanTask.getPrint_ip());


        //查询厨房对应的打印机
        CdsChufanMeal cdsChufanMeal = new CdsChufanMeal();
        cdsChufanMeal.setStores_id(cdsChufanTask.getStores_id());
        cdsChufanMeal.setStatus(1);
        cdsChufanMeal.addParamFields("cm_id,ct_num,cm_name,ct_user,wash_group,jardiniere_group,cook_group");
        cdsChufanMeal.addConditionFields("stores_id,status");
        List cdschufanMealList = sqlDao.getRecordList(cdsChufanMeal);

        resultMap.put("cdschufanMealList",cdschufanMealList);

        JsonMessage message = new JsonMessage(1, "success", resultMap);
        return message;
    }

    @RequestMapping(value = "storesGoods")
    @ResourceMethod(name = "获取商铺下达的商品", check = CHECK_CHUFAN)
    @ResponseBody
    public JsonMessage storesGoods(@RequestParam HashMap formInfo) throws Exception {
        if (formInfo.get("stores_id") == null) {
            return new JsonMessage(0, "缺少参数stores_id");
        }

        List resultList = sqlDao.getRecordList("cds_chufan_xiada.getStoresGood", formInfo);
        JsonMessage message = new JsonMessage(1, "success", resultList);
        return message;
    }

    @RequestMapping(value = "task_add")
    @ResourceMethod(name = "任务下达", check = CHECK_CHUFAN)
    @ResponseBody
    public JsonMessage task_add(@RequestParam HashMap formInfo) {
        if (formInfo.get("stores_id") == null) {
            return new JsonMessage(0, "缺少参数stores_id");
        }
        if (formInfo.get("good_id") == null) {
            return new JsonMessage(0, "缺少参数good_id");
        }
        if (formInfo.get("good_name") == null) {
            return new JsonMessage(0, "缺少参数good_name");
        }
        if (formInfo.get("good_count") == null) {
            return new JsonMessage(0, "缺少参数good_count");
        }
        if (formInfo.get("es_id") == null) {
            return new JsonMessage(0, "缺少参数es_id");
        }
        if (formInfo.get("cm_id") == null) {
            return new JsonMessage(0, "缺少参数cm_id");
        }

        String good_name = formInfo.get("good_name").toString();
        int stores_id = Integer.parseInt(formInfo.get("stores_id").toString());
        int good_id = Integer.parseInt(formInfo.get("good_id").toString());
        int good_count = Integer.parseInt(formInfo.get("good_count").toString());
        int es_id = Integer.parseInt(formInfo.get("es_id").toString());
        int cm_id = Integer.parseInt(formInfo.get("cm_id").toString());

        CdsGoodsTask cdsGoodsTask = new CdsGoodsTask();
        cdsGoodsTask.setTask_id(StringUtil.getPrimaryOrderKey());
        cdsGoodsTask.setStores_id(stores_id);
        cdsGoodsTask.setGood_id(good_id);
        cdsGoodsTask.setGood_name(good_name);
        cdsGoodsTask.setGood_count(good_count);
        cdsGoodsTask.setFinsh_count(0);
        cdsGoodsTask.setCreatetime(new Date());
        cdsGoodsTask.setTask_status(1);
        cdsGoodsTask.setEs_id(es_id);
        cdsGoodsTask.setCm_id(cm_id);

        CdsGoodsTaskRecord cdsGoodsTaskRecord = null;
        try {
            cdsGoodsTaskRecord = chufanXiadaService.task_add(cdsGoodsTask);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonMessage(0, "任务下达错误，请稍候再试");
        }
        Map resultMap = new HashMap();
        resultMap.put("cm_id", cm_id);
        if (cdsGoodsTaskRecord != null) {
            cdsGoodsTask.addConditionField("task_id");
            cdsGoodsTask = sqlDao.getRecord(cdsGoodsTask);
            resultMap.put("task_num", cdsGoodsTask.getTask_num());
            resultMap.put("meal_num", cdsGoodsTask.getMeal_num());
            resultMap.put("tar_id", cdsGoodsTaskRecord.getTar_id());
            resultMap.put("good_name", cdsGoodsTaskRecord.getGood_name());
            resultMap.put("good_count", cdsGoodsTaskRecord.getGood_count());
            resultMap.put("createtime", cdsGoodsTaskRecord.getCreatetime());
        }

        JsonMessage message = new JsonMessage(1, "success", resultMap);
        return message;
    }

    @RequestMapping(value = "task_cancel")
    @ResourceMethod(name = "任务取消", check = CHECK_CHUFAN)
    @ResponseBody
    public JsonMessage task_cancel(@RequestParam HashMap formInfo) {
        if (formInfo.get("task_id") == null) {
            return new JsonMessage(0, "缺少参数task_id");
        }

        if (formInfo.get("cancel_count") == null) {
            return new JsonMessage(0, "缺少参数cancel_count");
        }

        String task_id = formInfo.get("task_id").toString();
        int cancel_count = Integer.parseInt(formInfo.get("cancel_count").toString());

        CdsGoodsTask cdsGoodsTask = new CdsGoodsTask();
        cdsGoodsTask.setTask_id(task_id);
        cdsGoodsTask.addConditionField("task_id");

        cdsGoodsTask = sqlDao.getRecord(cdsGoodsTask);
        if (cdsGoodsTask == null) {
            return new JsonMessage(0, "任务不存在");
        }
        CdsGoodsTaskRecord cdsGoodsTaskRecord = null;
        try {
            cdsGoodsTaskRecord = chufanXiadaService.task_cancel(cdsGoodsTask, cancel_count);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonMessage(0, "任务取消失败，请稍候再试");
        }
        Map resultMap = new HashMap();
        resultMap.put("cm_id", cdsGoodsTask.getCm_id());
        if (cdsGoodsTaskRecord != null) {
            resultMap.put("tar_id", cdsGoodsTaskRecord.getTar_id());
            resultMap.put("task_num", cdsGoodsTask.getTask_num());
            resultMap.put("meal_num", cdsGoodsTask.getMeal_num());
            resultMap.put("good_name", cdsGoodsTaskRecord.getGood_name());
            resultMap.put("good_count", cdsGoodsTaskRecord.getGood_count());
            resultMap.put("before_good_count", cdsGoodsTaskRecord.getBefore_good_count());
            resultMap.put("after_good_count", cdsGoodsTaskRecord.getAfter_good_count());
            resultMap.put("createtime", cdsGoodsTaskRecord.getCreatetime());
            resultMap.put("cm_id", cdsGoodsTaskRecord.getCm_id());
        }
        JsonMessage message = new JsonMessage(1, "success", resultMap);
        return message;
    }


    @RequestMapping(value = "productionList")
    @ResourceMethod(name = "获取商铺生产中商品", check = CHECK_CHUFAN)
    @ResponseBody
    public JsonMessage productionList(@RequestParam HashMap formInfo) throws Exception {
        if (formInfo.get("stores_id") == null) {
            return new JsonMessage(0, "缺少参数stores_id");
        }

        List resultList = sqlDao.getRecordList("cds_chufan_xiada.getProductionList", formInfo);
        JsonMessage message = new JsonMessage(1, "success", resultList);
        return message;
    }


    @RequestMapping(value = "recordList")
    @ResourceMethod(name = "获取操作记录商品", check = CHECK_CHUFAN)
    @ResponseBody
    public JsonMessage recordList(@RequestParam HashMap formInfo) throws Exception {
        if (formInfo.get("stores_id") == null) {
            return new JsonMessage(0, "缺少参数stores_id");
        }

        List resultList = sqlDao.getRecordList("cds_chufan_xiada.recordList", formInfo);
        JsonMessage message = new JsonMessage(1, "success", resultList);
        return message;
    }


    @RequestMapping(value = "taskrecordList")
    @ResourceMethod(name = "获取操作记录单个商品明细", check = CHECK_CHUFAN)
    @ResponseBody
    public JsonMessage taskrecordList(@RequestParam HashMap formInfo) throws Exception {
        if (formInfo.get("task_id") == null) {
            return new JsonMessage(0, "缺少参数task_id");
        }
        String task_id = formInfo.get("task_id").toString();

        formInfo.put("task_id", task_id);

        List resultList = sqlDao.getRecordList("cds_chufan_xiada.taskrecordList", formInfo);
        JsonMessage message = new JsonMessage(1, "success", resultList);
        return message;
    }


    @RequestMapping(value = "chufanMealList")
    @ResourceMethod(name = "获取门店出餐机", check = CHECK_CHUFAN)
    @ResponseBody
    public JsonMessage chufanMealList(@RequestParam HashMap formInfo) throws Exception {
        if (formInfo.get("stores_id") == null) {
            return new JsonMessage(0, "缺少参数stores_id");
        }
        List resultList = sqlDao.getRecordList("cds_chufan_xiada.getChufanMealList", formInfo);
        JsonMessage message = new JsonMessage(1, "success", resultList);
        return message;
    }


    @RequestMapping(value = "reportList")
    @ResourceMethod(name = "获取报表数据", check = CHECK_CHUFAN)
    @ResponseBody
    public JsonMessage reportList(@RequestParam HashMap formInfo) throws Exception {
        if (formInfo.get("stores_id") == null) {
            return new JsonMessage(0, "缺少参数stores_id");
        }
        List resultList = sqlDao.getRecordList("cds_chufan_xiada.reportList", formInfo);
        JsonMessage message = new JsonMessage(1, "success", resultList);
        return message;
    }


    @RequestMapping(value = "getVersion")
    @ResourceMethod(name = "获取系统版本", check = CHECK_CHUFAN)
    @ResponseBody
    public JsonMessage getVersion(@RequestParam HashMap formInfo) {
        CdsAppVersion cdsAppVersion = new CdsAppVersion();
        cdsAppVersion.setName("厨房下达");
        cdsAppVersion.addConditionField("name");
        cdsAppVersion = sqlDao.getRecord(cdsAppVersion);
        Map resultMap = new HashMap();
        resultMap.put("version", 1);
        resultMap.put("version_url", "http://192.168.1.233:8080/static/app-debug.apk");
        if(cdsAppVersion!=null){
            resultMap.put("version", cdsAppVersion.getVersion());
            resultMap.put("version_url", cdsAppVersion.getVersion_url());
        }
        JsonMessage message = new JsonMessage(1, "success", resultMap);
        return message;
    }
}
