package com.controller;

import com.framework.annotation.ResourceMethod;
import com.framework.controller.BasicController;
import com.framework.mapping.JsonMessage;
import com.framework.mapping.system.*;
import com.framework.system.SystemConstant;
import com.framework.util.DateUtil;
import com.framework.util.StringUtil;
import com.framework.util.WebUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.framework.annotation.CheckType.CHECK_LOGIN;
import static com.framework.system.SystemConstant.LOGIN_USER_KEY;

/**
 * Created by c on 2017-02-02.
 */
@Controller
@RequestMapping("ClientSet")
public class ClientSetController extends BasicController {
    @RequestMapping(value = "business", method = RequestMethod.GET)
    @ResourceMethod(name = "business", check = CHECK_LOGIN)
    public String business() throws Exception {
//        return "erp/test";
        return "business/index";
    }

    @RequestMapping(value = "erp", method = RequestMethod.GET)
    @ResourceMethod(name = "erp", check = CHECK_LOGIN)
    public String erp() throws Exception {
//        return "erp/test";
        return "erp/index";
    }

    @RequestMapping(value = "goodsManage", method = RequestMethod.GET)
    @ResourceMethod(name = "goodsManage", check = CHECK_LOGIN)
    public String goodsManage() throws Exception {
//        return "erp/test";
        return "goodsManage/index";
    }

    @RequestMapping(value = "cf_meal_set", method = RequestMethod.GET)
    @ResourceMethod(name = "出餐机设置", check = CHECK_LOGIN)
    public String cf_meal_set() throws Exception {
        return "new/cf_meal_set";
    }

    @RequestMapping(value = "cf_task_set", method = RequestMethod.GET)
    @ResourceMethod(name = "下达机设置", check = CHECK_LOGIN)
    public String cf_task_set() throws Exception {
        return "clientSet/cf_task_set";
    }

    @RequestMapping(value = "print_set", method = RequestMethod.GET)
    @ResourceMethod(name = "打印机设置", check = CHECK_LOGIN)
    public String print_set() throws Exception {
        getSelectStores();
        CdsBrand cdsBrand = new CdsBrand();
        List<CdsBrand> brandlist = sqlDao.getRecordList(cdsBrand);
        WebUtil.getRequest().setAttribute("brandlist", brandlist);
        return "clientSet/print_set";
    }

    @RequestMapping(value = "GetPrintList", method = RequestMethod.POST)
    @ResourceMethod(name = "打印机查询列表", check = CHECK_LOGIN)
    @ResponseBody
    public void print_set(@RequestParam HashMap formInfo) throws Exception {
        if(WebUtil.getSession("stores_id") != null)
            formInfo.put("stores_id",WebUtil.getSession("stores_id"));
        else formInfo.put("stores_id",WebUtil.getUser().getStores_id());

        queryAndResponsePage("cds_prints.getPageRecord", formInfo);
    }


    @RequestMapping(value = "savePrint", method = RequestMethod.POST)
    @ResourceMethod(name = "保存打印机信息", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage savePrint(CdsPrints cdsPrints, String opt) {
        if (opt.equals("add")) {
            cdsPrints.addUnParamFields("print_id");
            sqlDao.insertRecord(cdsPrints);
        } else {
            cdsPrints.addUnParamFields("print_id");
            cdsPrints.addConditionField("print_id");
            sqlDao.updateRecord(cdsPrints);
        }
        return new JsonMessage(1);
    }


    @RequestMapping(value = "destroyprint", method = RequestMethod.POST)
    @ResourceMethod(name = "删除打印机信息", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage destroyprint(CdsPrints cdsPrints) {
        cdsPrints.addConditionField("pring_id");
        sqlDao.deleteRecord(cdsPrints);
        return new JsonMessage(1);
    }

    @RequestMapping(value = "toggle", method = RequestMethod.POST)
    @ResourceMethod(name = "更改打印机状态", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage toggle(CdsPrints cdsPrints) {
        cdsPrints.addParamField("status,pring_id");
        cdsPrints.addConditionField("pring_id");
        cdsPrints = sqlDao.getRecord(cdsPrints);
        if (cdsPrints.getStatus() == 1) {
            cdsPrints.setStatus(0);
        } else {
            cdsPrints.setStatus(1);
        }
        cdsPrints.addParamField("status");
        cdsPrints.addConditionField("pring_id");
        sqlDao.updateRecord(cdsPrints);
        return new JsonMessage(1);
    }


    @RequestMapping(value = "scanner_set", method = RequestMethod.GET)
    @ResourceMethod(name = "扫描枪设置", check = CHECK_LOGIN)
    public String scanner_set() throws Exception {
        getSelectStores();
        return "clientSet/scanner_set";
    }

    @RequestMapping(value = "GetScannerList", method = RequestMethod.POST)
    @ResourceMethod(name = "扫描枪查询列表", check = CHECK_LOGIN)
    @ResponseBody
    public void GetScannerList(@RequestParam HashMap formInfo) throws Exception {
        if(WebUtil.getSession("stores_id") != null)
            formInfo.put("stores_id",WebUtil.getSession("stores_id"));
        else formInfo.put("stores_id",WebUtil.getUser().getStores_id());
        queryAndResponsePage("cds_scanner.getPageRecord", formInfo);
    }

    @RequestMapping(value = "saveScenner", method = RequestMethod.POST)
    @ResourceMethod(name = "保存扫描枪信息", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage saveScenner(CdsScanner scanner, String opt) {
        if (opt.equals("add")) {
            scanner.addUnParamFields("cs_id");
            sqlDao.insertRecord(scanner);
        } else {
            scanner.addUnParamFields("cs_id");
            scanner.addConditionField("cs_id");
            sqlDao.updateRecord(scanner);
        }
        return new JsonMessage(1);
    }


    @RequestMapping(value = "destroyScanner", method = RequestMethod.POST)
    @ResourceMethod(name = "删除扫描枪信息", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage destroyScanner(CdsScanner scanner) {
        scanner.addConditionField("cs_id");
        sqlDao.deleteRecord(scanner);
        return new JsonMessage(1);
    }


    @RequestMapping(value = "scnnerToggle", method = RequestMethod.POST)
    @ResourceMethod(name = "更改扫描枪状态", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage scnnerToggle(CdsScanner scanner) {
        scanner.addParamField("status,cs_id");
        scanner.addConditionField("cs_id");
        scanner = sqlDao.getRecord(scanner);
        if (scanner.getStatus() == 1) {
            scanner.setStatus(0);
        } else {
            scanner.setStatus(1);
        }
        scanner.addParamField("status");
        scanner.addConditionField("cs_id");
        sqlDao.updateRecord(scanner);
        return new JsonMessage(1);
    }


    @RequestMapping(value = "electronic_set", method = RequestMethod.GET)
    @ResourceMethod(name = "电子秤设置", check = CHECK_LOGIN)
    public String electronic_set() throws Exception {
        return "clientSet/electronic_set";
    }

    @RequestMapping(value = "GetElectronicScaleList", method = RequestMethod.POST)
    @ResourceMethod(name = "电子秤查询列表", check = CHECK_LOGIN)
    @ResponseBody
    public void GetElectronicScaleList(@RequestParam HashMap formInfo) throws Exception {
        queryAndResponsePage("cds_electronic_scale.getPageRecord", formInfo);
    }

    @RequestMapping(value = "saveElectronicScale", method = RequestMethod.POST)
    @ResourceMethod(name = "保存电子秤信息", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage saveElectronicScale(CdsElectronicScale scale, String opt) {
        if (opt.equals("add")) {
            scale.setStores_id(WebUtil.getUser().getStores_id());
            scale.addUnParamFields("es_id");
            sqlDao.insertRecord(scale);
        } else {
            scale.setStores_id(WebUtil.getUser().getStores_id());
            scale.addUnParamFields("es_id");
            scale.addConditionField("es_id");
            sqlDao.updateRecord(scale);
        }
        return new JsonMessage(1);
    }


    @RequestMapping(value = "destroyElectronicScale", method = RequestMethod.POST)
    @ResourceMethod(name = "删除电子秤信息", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage destroyElectronicScale(CdsElectronicScale scale) {
        scale.addConditionField("es_id");
        sqlDao.deleteRecord(scale);
        return new JsonMessage(1);
    }


    @RequestMapping(value = "electronicScaleToggle", method = RequestMethod.POST)
    @ResourceMethod(name = "更改电子秤状态", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage electronicScaleToggle(CdsElectronicScale scale) {
        scale.addParamField("status,es_id");
        scale.addConditionField("es_id");
        scale = sqlDao.getRecord(scale);
        if (scale.getStatus() == 1) {
            scale.setStatus(0);
        } else {
            scale.setStatus(1);
        }
        scale.addParamField("status");
        scale.addConditionField("es_id");
        sqlDao.updateRecord(scale);
        return new JsonMessage(1);
    }


    @RequestMapping(value = "dianwoda_set", method = RequestMethod.GET)
    @ResourceMethod(name = "点我达手机设置", check = CHECK_LOGIN)
    public String dianwoda_set() throws Exception {
        getSelectStores();
        return "clientSet/dianwoda_set";
    }

    @RequestMapping(value = "GetDianwodaList", method = RequestMethod.POST)
    @ResourceMethod(name = "点我达查询列表", check = CHECK_LOGIN)
    @ResponseBody
    public void GetDianwodaList(@RequestParam HashMap formInfo) throws Exception {
        if(WebUtil.getSession("stores_id") != null)
            formInfo.put("stores_id",WebUtil.getSession("stores_id"));
        else formInfo.put("stores_id",WebUtil.getUser().getStores_id());
        queryAndResponsePage("cds_dianwoda_phone.getPageRecord", formInfo);
    }

    @RequestMapping(value = "saveDianwoda", method = RequestMethod.POST)
    @ResourceMethod(name = "保存点我达电话信息", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage saveDianwoda(CdsDianwodaPhone dianwodaPhone, String opt) {
        dianwodaPhone.setPhone(dianwodaPhone.getPhone().trim());
        if (opt.equals("add")) {
            dianwodaPhone.addConditionFields("phone,stores_id");
            List phones = sqlDao.getRecordList(dianwodaPhone);

            if (phones != null && phones.size() > 0) {
                return new JsonMessage(2);
            }
            dianwodaPhone.setOpt_time(new Date());
            dianwodaPhone.addUnParamFields("id");
            sqlDao.insertRecord(dianwodaPhone);
        } else {
            List phones = sqlDao.getRecordList("cds_dianwoda_phone.getRecords", dianwodaPhone);
            if (phones.size() > 0) {
                return new JsonMessage(2);
            }
            dianwodaPhone.setOpt_time(new Date());
            dianwodaPhone.addUnParamFields("id");
            dianwodaPhone.resetConditionFields("id");
            sqlDao.updateRecord(dianwodaPhone);
        }
        return new JsonMessage(1);
    }


    @RequestMapping(value = "destroyDianwoda", method = RequestMethod.POST)
    @ResourceMethod(name = "删除点我达信息", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage destroyDianwoda(CdsDianwodaPhone dianwodaPhone) {
        dianwodaPhone.addConditionField("id");
        sqlDao.deleteRecord(dianwodaPhone);
        return new JsonMessage(1);
    }


    @RequestMapping(value = "password_set", method = RequestMethod.GET)
    @ResourceMethod(name = "修改密码", check = CHECK_LOGIN)
    public String password_set() throws Exception {
        return "clientSet/password_set";
    }

    @RequestMapping(value = "savePassword", method = RequestMethod.POST)
    @ResourceMethod(name = "保存密码", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage savePassword(String oldpass, String password, String repassword) {
        CdsUsers user = WebUtil.getUser();

        user.addConditionField("id");
        user = sqlDao.getRecord(user);
        if (user == null) {
            return new JsonMessage(0, "用户未登录！");
        }
        String pass = "###" + StringUtil.MD5(StringUtil.MD5(SystemConstant.passKey + oldpass));
        if (!user.getUser_pass().equals(pass)) {
            return new JsonMessage(0, "原始密码不正确！");
        }

        if (!password.equals(repassword)) {
            return new JsonMessage(0, "两次输入密码不一致！");
        }
        pass = "###" + StringUtil.MD5(StringUtil.MD5(SystemConstant.passKey + password));
        user.setUser_pass(pass);
        user.addConditionField("id");
        user.addParamFields("user_pass");

        sqlDao.updateRecord(user);
        return new JsonMessage(1);
    }

    @RequestMapping(value = "giftlist", method = RequestMethod.GET)
    @ResourceMethod(name = "赠品查询", check = CHECK_LOGIN)
    public String giftlist() throws Exception {
        CdsUsers user = WebUtil.getSession(LOGIN_USER_KEY);
        Integer stores_id = user.getStores_id();

        CdsStoresClass cc = new CdsStoresClass();
        CdsStores shop = new CdsStores();
        CdsBrand cdsBrand = new CdsBrand();
        CdsClassType cct = new CdsClassType();

        shop.addParamFields("stores_id,name");

        List cdsshops = sqlDao.getRecordList(shop);
        List cdsbrands = sqlDao.getRecordList(cdsBrand);
        List<CdsClass> classList = sqlDao.getRecordList(cc);
        List<CdsClassType> classTypeList = sqlDao.getRecordList(cct);

        WebUtil.setRequest("cdsshops", cdsshops);
        WebUtil.setRequest("cdsbrands", cdsbrands);
        WebUtil.setRequest("stores_id", stores_id);
        WebUtil.getRequest().setAttribute("classtypelist", classTypeList);
        WebUtil.getRequest().setAttribute("classlist", classList);
        WebUtil.getRequest().setAttribute("curday", DateUtil.getDate());

        return "clientSet/giftlist1";
    }

    @RequestMapping(value = "commentList", method = RequestMethod.GET)
    @ResourceMethod(name = "评价查询", check = CHECK_LOGIN)
    public String commentList() throws Exception {
        CdsUsers user = WebUtil.getSession(LOGIN_USER_KEY);
        Integer stores_id = user.getStores_id();
        WebUtil.setRequest("stores_id", stores_id);

        CdsBrand cdsBrand = new CdsBrand();
        List cdsbrands = sqlDao.getRecordList(cdsBrand);
        WebUtil.setRequest("cdsbrands", cdsbrands);
        WebUtil.getRequest().setAttribute("curday", DateUtil.getDate());

        CdsStores shop = new CdsStores();
        shop.addParamFields("stores_id,name,stores1_id,stores2_id");
        List cdsshops = sqlDao.getRecordList(shop);
        WebUtil.setRequest("cdsshops", cdsshops);

        return "clientSet/commentlist";
    }

    public void getSelectStores(){
        int stores_id = WebUtil.getUser().getStores_id();
        if(stores_id==0){
            CdsStores shop = new CdsStores();
            shop.addParamFields("stores_id,name");
            List cdsshops = sqlDao.getRecordList(shop);
            WebUtil.setRequest("cdsshops", cdsshops);
        }else{
            Map param = new HashMap<>();
            param.put("stores_id",stores_id);
            List cdsshops = sqlDao.getRecordList("cds_stores.orderOnTimeStores", param);
            WebUtil.setRequest("cdsshops", cdsshops);
        }
    }

}
