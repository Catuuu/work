package com.api;

import com.framework.annotation.ResourceMethod;
import com.framework.controller.BasicController;
import com.framework.mapping.JsonMessage;
import com.framework.mapping.system.CdsBackMoney;
import com.framework.mapping.system.CdsUsers;
import com.framework.system.SystemConfig;
import com.framework.util.DateUtil;
import com.framework.util.WebUtil;
import com.opensdk.meituan.factory.APIFactoryMeituan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;

import static com.framework.annotation.CheckType.CHECK_LOGIN;
import static com.framework.system.SystemConstant.LOGIN_USER_KEY;

/**
 * 退单处理模块
 * Created by Administrator on 2017/7/3 0003.
 */
@Controller
@RequestMapping("/BackMoneyApi")
public class BackMoneyController extends BasicController {

    @RequestMapping(value = "backlist", method = RequestMethod.POST)
    @ResourceMethod(name = "退款列表查询", check = CHECK_LOGIN)
    @ResponseBody
    public void backlist(@RequestParam HashMap formInfo) throws Exception {
        if (formInfo.get("sl_date_begin") == null) {
            formInfo.put("sl_date_begin", DateUtil.getDate());
        }
        if (formInfo.get("sl_date_end") == null) {
            formInfo.put("sl_date_end", DateUtil.getDate());
        }
        if (formInfo.get("back_status").toString().equals("0")) {
            formInfo.put("back_status", "");
        }
//        if (formInfo.get("stores_id") == null) {
//            formInfo.put("stores_id", WebUtil.getUser().getStores_id());
//        }
        if (formInfo.get("sort") != null && formInfo.get("sort").toString().equals("fromin_no")) {
            formInfo.put("sort", "CAST(fromin_no as int)");
        }
        queryAndResponsePage("cds_backmoney.getPageRecord", formInfo);
    }

    @RequestMapping(value = "historybacklist", method = RequestMethod.POST)
    @ResourceMethod(name = "历史退款列表查询", check = CHECK_LOGIN)
    @ResponseBody
    public void historybacklist(@RequestParam HashMap formInfo) throws Exception {
        if (formInfo.get("sl_date_begin") == null) {
            formInfo.put("sl_date_begin", DateUtil.getDate());
        }
        if (formInfo.get("sl_date_end") == null) {
            formInfo.put("sl_date_end", DateUtil.getDate());
        }
        if (formInfo.get("back_status").toString().equals("0")) {
            formInfo.put("back_status", "");
        }
        if (formInfo.get("do_status") != null) {
            if (formInfo.get("do_status").toString().equals("1")) {
                formInfo.put("do_status", "");
            }
        }
//        if (formInfo.get("stores_id") == null) {
//            formInfo.put("stores_id", WebUtil.getUser().getStores_id());
//        }
        if (formInfo.get("sort") != null && formInfo.get("sort").toString().equals("fromin_no")) {
            formInfo.put("sort", "CAST(fromin_no as int)");
        }
        queryAndResponsePage("cds_backmoney.getHistoryPageRecord", formInfo);
    }

    @RequestMapping(value = "bmCount", method = RequestMethod.POST)
    @ResourceMethod(name = "轮询退款申请", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage bmCount(@RequestParam HashMap formInfo) throws Exception {
        if (formInfo.get("sl_date_begin") == null) {
            formInfo.put("sl_date_begin", DateUtil.getDate());
        }
        if (formInfo.get("sl_date_end") == null) {
            formInfo.put("sl_date_end", DateUtil.getDate());
        }
        Integer stores_id = WebUtil.getUser().getStores_id();
        if (stores_id == 0) {
            formInfo.put("stores_id", "");
        } else {
            formInfo.put("stores_id", stores_id);
        }


        HashMap map = sqlDao.getRecord("cds_backmoney.bmCount", formInfo);
        return new JsonMessage(1, "获取成功", map);
    }

    @RequestMapping(value = "refund", method = RequestMethod.POST)
    @ResourceMethod(name = "退款处理", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage refund(@RequestParam HashMap formInfo) {
        Integer status = 0;
        String mess = "操作失败";
        String result = "";
        Integer agree_type = 0;
        String bp_id = formInfo.get("bp_id").toString();
        String reason = formInfo.get("reason").toString();
        String send_type = formInfo.get("send_type").toString();

        CdsUsers user = WebUtil.getSession(LOGIN_USER_KEY);


        try {
            if (formInfo.get("agree_type") != null) {//美团退单申请处理
                agree_type = Integer.parseInt(formInfo.get("agree_type").toString());
                Long meituan_order_id = Long.parseLong(formInfo.get("order_desc").toString());

                if (agree_type == 1) { //美团同意退款
                    result = APIFactoryMeituan.getOrderAPI().orderRefundAgree(SystemConfig.GetSystemParamMeituan(), meituan_order_id, reason);
                } else if (agree_type == 2) {//美团驳回退款
                    result = APIFactoryMeituan.getOrderAPI().orderRefundReject(SystemConfig.GetSystemParamMeituan(), meituan_order_id, reason);
                }

                if (result.equals("ok")) {
                    status = 1;
                    mess = "操作成功";
                    CdsBackMoney cdsBackMoney = new CdsBackMoney();
                    cdsBackMoney.setBp_id(bp_id);
                    cdsBackMoney.setDo_status(1);
                    cdsBackMoney.setBp_check_time(new Date());
                    cdsBackMoney.setBp_check_operator_id(user.getId());
                    cdsBackMoney.setSend_type(send_type);
                    cdsBackMoney.addConditionField("bp_id");
                    cdsBackMoney.resetParamFields("do_status,bp_check_time,bp_check_operator_id,send_type");
                    sqlDao.updateRecord(cdsBackMoney);
                }

            } else {//美团，饿了么取消审核
                status = 1;
                mess = "操作成功";
                CdsBackMoney cdsBackMoney = new CdsBackMoney();
                cdsBackMoney.setBp_id(bp_id);
                cdsBackMoney.setDo_status(1);
                cdsBackMoney.setBp_check_remark(reason);
                cdsBackMoney.setBp_check_time(new Date());
                cdsBackMoney.setBp_check_operator_id(user.getId());
                cdsBackMoney.setSend_type(send_type);
                cdsBackMoney.addConditionField("bp_id");
                cdsBackMoney.resetParamFields("do_status,bp_check_remark,bp_check_time,bp_check_operator_id,send_type");
                sqlDao.updateRecord(cdsBackMoney);
            }

        } catch (Exception e) {
            mess = e.toString();
            return new JsonMessage(status, mess);
        }

        return new JsonMessage(status, mess);
    }
}
