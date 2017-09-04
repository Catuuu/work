package com.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.framework.annotation.ResourceMethod;
import com.framework.controller.BasicController;
import com.framework.mapping.JsonMessage;
import com.framework.mapping.system.*;
import com.framework.util.DateUtil;
import com.framework.util.WebUtil;
import com.service.WeiXinService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

import static com.framework.annotation.CheckType.CHECK_LOGIN;
import static com.framework.system.SystemConstant.LOGIN_USER_KEY;

/**
 * Created by Administrator on 2017/6/22 0022.
 */
@Controller
@RequestMapping("/GiftApi")
public class GiftController extends BasicController {
    @Resource
    private WeiXinService weiXinService;

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

        if(WebUtil.getSession("stores_id") != null)
            formInfo.put("stores_id",WebUtil.getSession("stores_id"));
        else formInfo.put("stores_id",WebUtil.getUser().getStores_id());
        if (formInfo.get("sort") != null && formInfo.get("sort").toString().equals("fromin_no")) {
            formInfo.put("sort", "CAST(fromin_no as int)");
        }
        queryAndResponsePage("cds_gift.getPageRecord", formInfo);
    }

    @RequestMapping(value = "orderlisthistory", method = RequestMethod.POST)
    @ResourceMethod(name = "历史订单查询列表", check = CHECK_LOGIN)
    @ResponseBody
    public void orderlisthistory(@RequestParam HashMap formInfo) throws Exception {
        if (formInfo.get("sl_date_begin") == null) {
            formInfo.put("sl_date_begin", DateUtil.getDate());
        }
        if (formInfo.get("sl_date_end") == null) {
            formInfo.put("sl_date_end", DateUtil.getDate());
        }

        if(WebUtil.getSession("stores_id") != null)
            formInfo.put("stores_id",WebUtil.getSession("stores_id"));
        else formInfo.put("stores_id",WebUtil.getUser().getStores_id());
        if (formInfo.get("sort") != null && formInfo.get("sort").toString().equals("fromin_no")) {
            formInfo.put("sort", "CAST(fromin_no as int)");
        }
        queryAndResponsePage("cds_gift.getHistoryPageRecord", formInfo);
    }

    @RequestMapping(value = "orderInfo", method = RequestMethod.POST)
    @ResourceMethod(name = "订单穿透", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage orderInfo(CdsOrderInfo cdsOrderInfo) throws Exception {
        cdsOrderInfo = sqlDao.getRecord("cds_gift.orderInfo", cdsOrderInfo);
        return new JsonMessage(1, "获取订单信息成功", cdsOrderInfo);
    }

    @RequestMapping(value = "viewgiftlog", method = RequestMethod.POST)
    @ResourceMethod(name = "查看赠品状态", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage viewgiftlog(String phone) {
        CdsGiftRegisterList gift = new CdsGiftRegisterList();
        gift.setPhone(phone);
        gift.setSend_type(0);
        gift.addConditionFields("phone,send_type");
        List<CdsGiftRegisterList> giftlist = sqlDao.getRecordList(gift);
        Set goodslist = new HashSet();
        if (giftlist == null) {
            return new JsonMessage(0, "该用户没有赠品");
        }
        for (CdsGiftRegisterList gift1 : giftlist) {
            List goodsList = JSONArray.parseArray(gift1.getGoods().toString());
            for (Object goods : goodsList) {
                JSONObject json = JSONObject.parseObject(goods.toString());
                json.put("quantity", 1);
                if (goodslist.contains(json)) {
                    goodslist.remove(json);
                    json.put("quantity", json.getInteger("quantity") + 1);
                }
                goodslist.add(json);
            }

        }

        return new JsonMessage(1, "赠品详情", goodslist);
    }

    @RequestMapping(value = "giftlist", method = RequestMethod.POST)
    @ResourceMethod(name = "赠品列表", check = CHECK_LOGIN)
    @ResponseBody
    public void giftlist(@RequestParam HashMap formInfo) throws Exception {
        if (formInfo.get("sl_date_begin") == null) {
            formInfo.put("sl_date_begin", DateUtil.getDate());
        }
        if (formInfo.get("sl_date_end") == null) {
            formInfo.put("sl_date_end", DateUtil.getDate());
        }
        if(WebUtil.getSession("stores_id") != null)
            formInfo.put("stores_id",WebUtil.getSession("stores_id"));
        else formInfo.put("stores_id",WebUtil.getUser().getStores_id());
//        formInfo.put("stores_id", "0");int类型0时会导致stores_id在映射文件中为null
        formInfo = getPageInfoByMybatis(formInfo, "create_time", "desc", 15);
        queryAndResponsePage("cds_gift.getGiftPageRecord", formInfo);
    }

    @RequestMapping(value = "getTreeGoods", method = RequestMethod.GET)
    @ResourceMethod(name = "获取tree商品信息", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage getGoods() {
        List<HashMap> goodsLsit = sqlDao.getRecordList("cds_gift.getTreeGoods");
        for (HashMap class_goods : goodsLsit) {
            if (class_goods.get("children") != null && !class_goods.get("children").equals("")) {
                String goods = class_goods.get("children").toString();
                JSONArray json = JSONArray.parseArray("[" + goods + "]");
                class_goods.put("children", json);
            }

        }
        return new JsonMessage(1, "获取商品信息成功", goodsLsit);
    }

    @RequestMapping(value = "getTableGoods", method = RequestMethod.GET)
    @ResourceMethod(name = "获取table商品信息", check = CHECK_LOGIN)
    @ResponseBody
    public void getTableGoods(@RequestParam HashMap formInfo) throws Exception {
        formInfo = getPageInfoByMybatis(formInfo, "good_name", "desc", 10);
        queryAndResponsePage("cds_gift.getTableGoods", formInfo);
    }

    @RequestMapping(value = "getBrandClass", method = RequestMethod.GET)
    @ResourceMethod(name = "获取商品类别通过brand", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage getBrandClass(CdsClass cdsClass) throws Exception {
        cdsClass.addConditionField("brand_id");
        List classTypes = sqlDao.getRecordList(cdsClass);
        return new JsonMessage(1, "获取商品类别成功", classTypes);
    }

    @RequestMapping(value = "{action}", method = RequestMethod.POST)
    @ResourceMethod(name = "登记信息管理", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage saveGift(@PathVariable("action") String action, CdsGiftRegisterList giftInfo) {
        switch (action) {
            case "save":
                CdsUsers user = WebUtil.getSession(LOGIN_USER_KEY);
                giftInfo.setCreate_time(new Date());
                giftInfo.setOpt_id(user.getId());
                giftInfo.setOpt_name(user.getUser_nicename());
                giftInfo.setOpt_type("订单登记");
                if (giftInfo.getOrder_id() == null) {
                    giftInfo.setOpt_type("手动登记");
                }
                giftInfo.setSend_type(0);
                //总部客服登记 无需审核
                if (user.getStores_id() == 0) {
                    //审核信息
                    giftInfo.setIs_check(1);//已审核
                    giftInfo.setStores_id(0);//店铺id=0,总部登记
                    giftInfo.setCheck_info("总部登记");
                    giftInfo.setCheck_time(new Date());
                    giftInfo.setCheck_id(user.getId());
                    giftInfo.setCheck_name(user.getUser_nicename());

                } else {
                    giftInfo.setIs_check(0);//未审核
                    giftInfo.setStores_id(user.getStores_id());
                }
                giftInfo.addParamFields();
                giftInfo.removeParamField("id");
                sqlDao.insertRecord(giftInfo);

                if (giftInfo.getIs_check() == 1) {
                    //发送礼品登记微信消息
                    CdsOrderInfo orderInfo = new CdsOrderInfo();
                    CdsMember member = new CdsMember();
                    member.setPhone(giftInfo.getPhone());
                    member.addParamField("member_id");
                    member.addConditionField("phone");
                    member = sqlDao.getRecord(member);

                    orderInfo.setMember_id(member.getMember_id());
                    orderInfo.setGoods(giftInfo.getGoods());
                    orderInfo.setBrand_id(giftInfo.getBrand_id());
                    weiXinService.saveGiftOrder(orderInfo);
                }
                break;

            case "del":
                sqlDao.deleteRecord(giftInfo);
                break;

            case "update"://TODO 增加修改项
                giftInfo.setUpdate_time(new Date());
                giftInfo.resetParamFields("name,phone,brand_id,send_reason,send_reason_sub,goods,update_time,img");
                giftInfo.addConditionField("id");
                sqlDao.updateRecord(giftInfo);
                break;

            case "check"://审核
                CdsUsers user1 = WebUtil.getSession(LOGIN_USER_KEY);
                giftInfo.setCheck_time(new Date());
                giftInfo.setCheck_id(user1.getId());
                giftInfo.setCheck_name(user1.getUser_nicename());

                giftInfo.resetParamFields("is_check,check_time,check_name,check_id,check_info");
                giftInfo.addConditionField("id");
                sqlDao.updateRecord(giftInfo);

                if (giftInfo.getIs_check() == 1) {
                    //发送礼品登记微信消息
                    CdsGiftRegisterList giftInfo1 = new CdsGiftRegisterList();
                    giftInfo1.setId(giftInfo.getId());
                    giftInfo1.addConditionField("id");
                    giftInfo1 = sqlDao.getRecord(giftInfo1);

                    CdsOrderInfo orderInfo = new CdsOrderInfo();
                    CdsMember member = new CdsMember();
                    member.setPhone(giftInfo1.getPhone());
                    member.addParamField("member_id");
                    member.addConditionField("phone");
                    member = sqlDao.getRecord(member);

                    orderInfo.setMember_id(member.getMember_id());
                    orderInfo.setGoods(giftInfo1.getGoods());
                    orderInfo.setBrand_id(giftInfo1.getBrand_id());
                    weiXinService.saveGiftOrder(orderInfo);
                }
                break;
        }
        return new JsonMessage(1, action + "操作成功");
    }

    @RequestMapping(value = "getPicAddress", method = RequestMethod.GET)
    @ResourceMethod(name = "用户查询页面", check = CHECK_LOGIN)
    public String getPicAddress(String status) throws Exception {
        WebUtil.getRequest().setAttribute("status", status);
        return "common/gift_pic_upload";
    }

    @RequestMapping(value = "GetErpGoodsLists", method = RequestMethod.POST)
    @ResourceMethod(name = "erp菜品信息管理列表", check = CHECK_LOGIN)
    @ResponseBody
    public void GetErpGoodsLists(@RequestParam HashMap formInfo) throws Exception {
        queryAndResponsePage("cds_gift.getMsGoodsPageRecord", formInfo);
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
}
