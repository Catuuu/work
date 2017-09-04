package com.api;

import com.framework.annotation.ResourceMethod;
import com.framework.controller.BasicController;
import com.framework.mapping.JsonMessage;
import com.framework.mapping.system.*;
import com.framework.util.StringUtil;
import com.opensdk.weixin.util.HttpUtil;
import com.service.*;
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
import static com.framework.annotation.CheckType.NO_CHECK;


/**
 * 厨房接单机接口
 * Created by Administrator on 2017/2/4.
 */
@Controller
@RequestMapping("/ChufanTask")
public class ChufanTaskController extends BasicController {

    @Resource(name = "ChufanTaskService")
    protected ChufanTaskService chufanTaskService;

    //private static String keypassword = "A622625ufdki12yui3";


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

        CdsChufanMeal cdsChufanMeal = new CdsChufanMeal();
        cdsChufanMeal.setCt_user(username);
        cdsChufanMeal.setCt_password(password);
        cdsChufanMeal.setStatus(1);
        cdsChufanMeal.addConditionFields("ct_user,ct_password,status");
        cdsChufanMeal = sqlDao.getRecord(cdsChufanMeal);
        if (cdsChufanMeal == null) {
            return new JsonMessage(0, "登录账户不存在");
        }

        CdsStores cdsStores = new CdsStores();
        cdsStores.setStores_id(cdsChufanMeal.getStores_id());
        cdsStores.addConditionField("stores_id");
        cdsStores.addParamFields("name");
        cdsStores = sqlDao.getRecord(cdsStores);
        if (cdsStores == null) {
            return new JsonMessage(0, "商铺不存在");
        }

        Map resultMap = new HashMap();
        resultMap.put("cm_id", cdsChufanMeal.getCm_id());
        resultMap.put("ct_num", cdsChufanMeal.getCt_num());
        resultMap.put("username", cdsChufanMeal.getCt_user());
        resultMap.put("password", cdsChufanMeal.getCt_password());
        resultMap.put("stores_id", cdsChufanMeal.getStores_id());

        resultMap.put("wash_group", cdsChufanMeal.getWash_group());
        resultMap.put("jardiniere_group", cdsChufanMeal.getJardiniere_group());
        resultMap.put("cook_group", cdsChufanMeal.getCook_group());

        resultMap.put("stores_name", cdsStores.getName());
        JsonMessage message = new JsonMessage(1, "success", resultMap);
        return message;
    }

    @RequestMapping(value = "taskGoods")
    @ResourceMethod(name = "获取生产中任务", check = CHECK_CHUFAN)
    @ResponseBody
    public JsonMessage storesGoods(@RequestParam HashMap formInfo) throws Exception {
        if (formInfo.get("cm_id") == null) {
            return new JsonMessage(0, "缺少参数cm_id");
        }
        List resultList = sqlDao.getRecordList("cds_chufan_task.getTaskGoods", formInfo);
        JsonMessage message = new JsonMessage(1, "success", resultList);
        return message;
    }

    @RequestMapping(value = "task_add")
    @ResourceMethod(name = "任务出餐", check = CHECK_CHUFAN)
    @ResponseBody
    public JsonMessage task_add(@RequestParam HashMap formInfo) {
        if (formInfo.get("task_id") == null) {
            return new JsonMessage(0, "缺少参数task_id");
        }
        if (formInfo.get("good_count") == null) {
            return new JsonMessage(0, "缺少参数good_count");
        }
        if (formInfo.get("finished_weight") == null) {
            return new JsonMessage(0, "缺少参数finished_weight");
        }

        String task_id = formInfo.get("task_id").toString();
        int good_count = Integer.parseInt(formInfo.get("good_count").toString());
        int finished_weight = Integer.parseInt(formInfo.get("finished_weight").toString());

        CdsGoodsTask cdsGoodsTask = new CdsGoodsTask();
        cdsGoodsTask.setTask_id(task_id);
        cdsGoodsTask.addConditionField("task_id");
        cdsGoodsTask = sqlDao.getRecord(cdsGoodsTask);
        cdsGoodsTask.setTask_finsh_time(new Date());

        try {
            if (cdsGoodsTask != null) {
                chufanTaskService.task_add(cdsGoodsTask, good_count);
            } else {
                return new JsonMessage(0, "出餐错误，出餐任务不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonMessage(0, "出餐错误，请稍候再试");
        }
        Map resultMap = new HashMap();
        resultMap.put("meal_num", cdsGoodsTask.getMeal_num());
        resultMap.put("task_num", cdsGoodsTask.getTask_num());
        resultMap.put("good_name", cdsGoodsTask.getGood_name());
        resultMap.put("good_count", good_count);
        resultMap.put("createdate", cdsGoodsTask.getTask_finsh_time());
        resultMap.put("finished_weight", finished_weight);

        JsonMessage message = new JsonMessage(1, "success", resultMap);
        return message;
    }

    @RequestMapping(value = "task_cancel")
    @ResourceMethod(name = "任务取消", check = CHECK_CHUFAN)
    @ResponseBody
    public JsonMessage task_cancel(@RequestParam HashMap formInfo) {
        if (formInfo.get("tar_id") == null) {
            return new JsonMessage(0, "缺少参数tar_id");
        }
        String tar_id = formInfo.get("tar_id").toString();

        CdsGoodsTaskRecord cdsGoodsTaskRecord = new CdsGoodsTaskRecord();
        cdsGoodsTaskRecord.setTar_id(tar_id);
        cdsGoodsTaskRecord.addConditionField("tar_id");
        cdsGoodsTaskRecord = sqlDao.getRecord(cdsGoodsTaskRecord);
        if(cdsGoodsTaskRecord==null){
            return new JsonMessage(0,"操作记录不存在");
        }

        CdsGoodsTask cdsGoodsTask = new CdsGoodsTask();
        cdsGoodsTask.setTask_id(cdsGoodsTaskRecord.getTask_id());
        cdsGoodsTask.addConditionField("task_id");

        cdsGoodsTask = sqlDao.getRecord(cdsGoodsTask);
        if (cdsGoodsTask == null) {
            return new JsonMessage(0, "任务不存在");
        }

        try {
            chufanTaskService.task_cancel(cdsGoodsTask, cdsGoodsTaskRecord);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonMessage(0, "任务取消失败，请稍候再试");
        }
        return new JsonMessage(1, "success");
    }

    @RequestMapping(value = "doningTaskRecord")
    @ResourceMethod(name = "获取未处理的接单机任务", check = CHECK_CHUFAN)
    @ResponseBody
    public JsonMessage doningTaskRecord(@RequestParam HashMap formInfo) throws Exception {
        if (formInfo.get("cm_id") == null) {
            return new JsonMessage(0, "缺少参数cm_id");
        }

        List resultList = sqlDao.getRecordList("cds_chufan_task.doningTaskRecord", formInfo);
        JsonMessage message = new JsonMessage(1, "success", resultList);
        return message;
    }


    @RequestMapping(value = "do_task_Record")
    @ResourceMethod(name = "出餐记录任务已处理", check = CHECK_CHUFAN)
    @ResponseBody
    public JsonMessage do_task_Record(@RequestParam HashMap formInfo) {
        if (formInfo.get("tar_id") == null) {
            return new JsonMessage(0, "缺少参数tar_id");
        }
        String tar_id = formInfo.get("tar_id").toString();
        CdsGoodsTaskRecord cdsGoodsTaskRecord = new CdsGoodsTaskRecord();

        cdsGoodsTaskRecord.setIs_do(1);
        cdsGoodsTaskRecord.setTar_id(tar_id);
        cdsGoodsTaskRecord.addConditionFields("tar_id");
        cdsGoodsTaskRecord.addParamFields("is_do");
        sqlDao.updateRecord(cdsGoodsTaskRecord);
        return new JsonMessage(1, "success");
    }

    @RequestMapping(value = "productionList")
    @ResourceMethod(name = "获取操作记录", check = CHECK_CHUFAN)
    @ResponseBody
    public JsonMessage productionList(@RequestParam HashMap formInfo) throws Exception {
        if (formInfo.get("cm_id") == null) {
            return new JsonMessage(0, "缺少参数cm_id");
        }

        List resultList = sqlDao.getRecordList("cds_chufan_task.getProductionList", formInfo);
        JsonMessage message = new JsonMessage(1, "success", resultList);
        return message;
    }


    @RequestMapping(value = "recordList")
    @ResourceMethod(name = "获取下达记录商品", check = CHECK_CHUFAN)
    @ResponseBody
    public JsonMessage recordList(@RequestParam HashMap formInfo) throws Exception {
        if (formInfo.get("cm_id") == null) {
            return new JsonMessage(0, "缺少参数cm_id");
        }
        List resultList = sqlDao.getRecordList("cds_chufan_task.recordList", formInfo);
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


    @RequestMapping(value = "reportList")
    @ResourceMethod(name = "获取报表数据", check = CHECK_CHUFAN)
    @ResponseBody
    public JsonMessage reportList(@RequestParam HashMap formInfo) throws Exception {
        if (formInfo.get("stores_id") == null) {
            return new JsonMessage(0, "缺少参数stores_id");
        }
        if (formInfo.get("cm_id") == null) {
            return new JsonMessage(0, "缺少参数cm_id");
        }
        List resultList = sqlDao.getRecordList("cds_chufan_task.reportList", formInfo);
        JsonMessage message = new JsonMessage(1, "success", resultList);
        return message;
    }



    @RequestMapping(value = "getVersion")
    @ResourceMethod(name = "获取系统版本", check = CHECK_CHUFAN)
    @ResponseBody
    public JsonMessage getVersion(@RequestParam HashMap formInfo) {
        CdsAppVersion cdsAppVersion = new CdsAppVersion();
        cdsAppVersion.setName("厨房接单");
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
