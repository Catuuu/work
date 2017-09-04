package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.framework.annotation.ResourceMethod;
import com.framework.controller.BasicController;
import com.framework.mapping.JsonMessage;
import com.framework.mapping.system.*;
import com.framework.system.SystemConfig;
import com.framework.util.DateUtil;
import com.framework.util.StringAlphas;
import com.framework.util.WebUtil;
import com.opensdk.eleme.exception.ApiOpException;
import com.opensdk.eleme.exception.ApiSysException;
import com.opensdk.eleme.factory.APIFactoryEleme;
import com.service.StoresBrandService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

import static com.framework.annotation.CheckType.CHECK_LOGIN;

/**
 * Created by c on 2017-02-02.
 */
@Controller
@RequestMapping("GoodsInfo")
public class GoodsInfoController extends BasicController {

    @Resource(name = "StoresBrandService")
    protected StoresBrandService storesbrandservice;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    @ResourceMethod(name = "平台商品", check = CHECK_LOGIN)
    public String index() throws Exception {
        CdsClass cc = new CdsClass();
        List<CdsClass> classList = sqlDao.getRecordList(cc);
        CdsClassType cct = new CdsClassType();
        List<CdsClassType> classTypeList = sqlDao.getRecordList(cct);
        CdsBrand cdsBrand = new CdsBrand();
        List<CdsBrand> brandlist = sqlDao.getRecordList(cdsBrand);
        WebUtil.getRequest().setAttribute("brandlist", brandlist);
        WebUtil.getRequest().setAttribute("classtypelist", classTypeList);
        WebUtil.getRequest().setAttribute("classlist", classList);
        return "goodsInfo/index";
    }

    @RequestMapping(value = "GetGoodsInfoLists", method = RequestMethod.POST)
    @ResourceMethod(name = "平台商品信息管理列表", check = CHECK_LOGIN)
    @ResponseBody
    public void GetGoodsInfoLists(@RequestParam HashMap formInfo) throws Exception {
        queryAndResponsePage("cds_stores.GetGoodsInfoLists", formInfo);
    }

    @RequestMapping(value = "GetGoodsInfoErp", method = RequestMethod.POST)
    @ResourceMethod(name = "平台商品对应的erp菜品列表", check = CHECK_LOGIN)
    @ResponseBody
    public List GetErpInfoLists(@RequestParam HashMap formInfo) throws Exception {
        return sqlDao.getRecordList("cds_stores.GetGoodsInfoErp",formInfo);
       // queryAndResponsePage("cds_stores.GetGoodsInfoErp", formInfo);
    }

    @RequestMapping(value = "updateGoodsInfo", method = RequestMethod.POST)
    @ResourceMethod(name = "商品状态", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage updateGoodsInfo(String good_id,String status) {
        CdsGoodsInfo cgi = new CdsGoodsInfo();
        if(!StringUtils.isEmpty(good_id)&&!StringUtils.isEmpty(status)){
            cgi.setGood_id(Integer.valueOf(good_id));
            cgi.addConditionField("good_id");
            cgi = sqlDao.getRecord(cgi);
            cgi.setStatus(Integer.valueOf(status));
            cgi.addUnParamFields("good_id");
            cgi.addConditionField("good_id");
            sqlDao.updateRecord(cgi);
        }
        return new JsonMessage(1);
    }

    @RequestMapping(value = "updateGoodsInfo2", method = RequestMethod.POST)
    @ResourceMethod(name = "商品折扣", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage updateGoodsInfo2(String good_id,String vip_discounts) {
        CdsGoodsInfo cgi = new CdsGoodsInfo();
        if(!StringUtils.isEmpty(good_id)&&!StringUtils.isEmpty(vip_discounts)){
            cgi.setGood_id(Integer.valueOf(good_id));
            cgi.addConditionField("good_id");
            cgi = sqlDao.getRecord(cgi);
            cgi.setVip_discounts(Integer.valueOf(vip_discounts));
            cgi.addUnParamFields("good_id");
            cgi.addConditionField("good_id");
            sqlDao.updateRecord(cgi);
        }
        return new JsonMessage(1);
    }


    @RequestMapping(value = "saveGoodsInfo", method = RequestMethod.POST)
    @ResourceMethod(name = "保存平台菜品信息", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage saveGoodsInfo(@RequestParam HashMap hashMap, String opt) {
        Iterator it = hashMap.entrySet().iterator();
        CdsGoodsInfo cmg = new CdsGoodsInfo();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            if (!StringUtils.isEmpty(value)) {
                if ("good_id".equals(key)) {
                    cmg.setGood_id(Integer.valueOf(value));
                }
                if ("class_id".equals(key)) {
                    cmg.setClass_id(Integer.valueOf(value));
                }
                if ("good_name".equals(key)) {
                    cmg.setGood_name(value);
                    String pinxin = StringAlphas.String2Alpha(value);
                    cmg.setPinxin(pinxin);
                    cmg.setAdd_date(DateUtil.getToday());
                }
                if ("good_pic".equals(key)) {
                    cmg.setGood_pic(value);

                }
                if ("market_price".equals(key)) {
                    cmg.setMarket_price(value);
                }
                if ("elem_pic".equals(key)) {
                    cmg.setImage_hash(value);
                }
                if("egood_pic".equals(key)){
                    cmg.setEgood_pic(value);
                    try {
                        String result = APIFactoryEleme.getFoodAPI().eleUploadByUrl(SystemConfig.GetSystemParamEleme(), "http://caidashi.oss-cn-hangzhou.aliyuncs.com/"+value);
                        JSONObject jo = JSONObject.parseObject(result);
                        if ("200".equals(jo.getString("code")) && jo.getString("message").equals("ok")) {
                            Object ob = jo.get("data");
                            jo= JSONObject.parseObject(ob.toString());
                            String image_hash = jo.get("image_hash").toString();
                            cmg.setImage_hash(image_hash);
                        }
                    } catch (ApiOpException e) {
                        e.printStackTrace();
                    } catch (ApiSysException e) {
                        e.printStackTrace();
                    }
                }
                if ("box_price".equals(key)) {
                    cmg.setBox_price(value);
                }
                if ("status".equals(key)) {
                    cmg.setStatus(Integer.valueOf(value));
                }
//                if ("vip_discounts".equals(key)) {
//                    cmg.setVip_discounts(Integer.valueOf(value));
//                }
                if ("good_info".equals(key)) {
                    cmg.setGood_info(value);
                }
//                if ("introduce".equals(key)) {
//                    cmg.setIntroduce(value);
//                }
            }
        }
        if (opt.equals("add")) {
            cmg.setListorder(1);
            cmg.addUnParamFields("good_id,vip_discounts,introduce");
            sqlDao.insertRecord(cmg);
        } else {
            cmg.addUnParamFields("good_id,add_date,listorder,vip_discounts,introduce");
            cmg.addConditionField("good_id");
            sqlDao.updateRecord(cmg);
        }
        return new JsonMessage(1);
    }

    @RequestMapping(value = "saveErp", method = RequestMethod.POST)
    @ResourceMethod(name = "平台商品对应菜品信息保存", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage saveErp(@RequestParam HashMap hashMap,String good_id) {
        CdsErpGoodList rep = new CdsErpGoodList();
        Iterator it = hashMap.entrySet().iterator();
        List<CdsErpGoodList> list = new ArrayList<CdsErpGoodList>();
        int i =0;
        while (it.hasNext()) {
            rep.setGood_id(Integer.valueOf(good_id));
            Map.Entry entry = (Map.Entry) it.next();
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            if(!StringUtils.isEmpty(value)){
                if(("rows"+'['+i+']'+"[erp_good_id]").equals(key)){
                    rep.setErp_good_id(Integer.valueOf(value));
                }
                if(("rows"+'['+i+']'+"[good_id]").equals(key)){
                    rep.setMs_good_id(Integer.valueOf(value));
                }else if(("rows"+'['+i+']'+"[good_count]").equals(key)){
                    rep.setGood_count(Integer.valueOf(value.trim()));
                }
            }
            if(rep.getMs_good_id()!=null&&rep.getGood_count()!=null&&rep.getErp_good_id()!=null){
                list.add(rep);
                rep = new CdsErpGoodList();
                ++i;
            }
        }
        storesbrandservice.saveGoodList(list,good_id);
        return new JsonMessage(1);
    }


    @RequestMapping(value = "classIndex", method = RequestMethod.GET)
    @ResourceMethod(name = "平台商品类别", check = CHECK_LOGIN)
    public String classIndex() throws Exception {
        CdsBrand cdsBrand = new CdsBrand();
        List<CdsBrand> brandlist = sqlDao.getRecordList(cdsBrand);
        WebUtil.getRequest().setAttribute("brandlist", brandlist);
        return "goodsInfo/class_index";
    }

    @RequestMapping(value = "GetGoodsClassLists", method = RequestMethod.POST)
    @ResourceMethod(name = "平台商品类别管理列表", check = CHECK_LOGIN)
    @ResponseBody
    public void GetGoodsClassLists(@RequestParam HashMap formInfo) throws Exception {
        queryAndResponsePage("cds_stores.GetGoodsClassLists", formInfo);
    }

    @RequestMapping(value = "saveGoodsClass", method = RequestMethod.POST)
    @ResourceMethod(name = "保存平台商品类别信息", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage saveGoodsClass(@RequestParam HashMap hashMap, String opt) {
        CdsClass cds_class = new CdsClass();
        Iterator it = hashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            if (!StringUtils.isEmpty(value)) {
                if ("class_id".equals(key)) {
                    cds_class.setClass_id(Integer.valueOf(value));
                }
                if ("class_name".equals(key)) {
                    cds_class.setClass_name(value);
                }
                if ("brand_id".equals(key)) {
                    cds_class.setBrand_id(Integer.valueOf(value));
                }
                if ("class_desc".equals(key)) {
                    cds_class.setClass_desc(value);
                }
                if ("listorder".equals(key)) {
                    cds_class.setListorder(Integer.valueOf(value));
                }
            }
        }
        if (opt.equals("add")) {
            cds_class.addUnParamFields("class_id,class_pic1,class_pic,class_nick_name");
            sqlDao.insertRecord(cds_class);
        } else {
            cds_class.addUnParamFields("class_id,class_pic1,class_pic,class_nick_name");
            cds_class.addConditionField("class_id");
            CdsStoresClass csc = new CdsStoresClass();
            csc.setClass_id(cds_class.getClass_id());
            sqlDao.updateRecord("cds_ptstores_manage.updateStoresClassType",csc);
            sqlDao.updateRecord(cds_class);
        }
        return new JsonMessage(1);
    }

    @RequestMapping(value = "getSigna", method = RequestMethod.POST)
    @ResourceMethod(name = "生成oss签名", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage getSigna() {
        String endpoint = "oss-cn-hangzhou.aliyuncs.com";
        String accessId = "LTAIyKiRafcYxueU";
        String accessKey = "rLPpIfKoukKXKwgeUeG20KW5f28Hed";
        String bucket = "caidashi";
        String dir = "goodimg/";
        String host = "http://" + bucket + "." + endpoint;
        OSSClient client = new OSSClient(endpoint, accessId, accessKey);
        Map<String, String> respMap = new LinkedHashMap<String, String>();
        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            java.sql.Date expiration = new java.sql.Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = client.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = client.calculatePostSignature(postPolicy);
            respMap.put("accessid", accessId);
            respMap.put("accessKey",accessKey);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            //respMap.put("expire", formatISO8601Date(expiration));
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));

        } catch (Exception e) {

        }

        return new JsonMessage(1,"",respMap);
    }

    @RequestMapping(value = "getPicAddress", method = RequestMethod.GET)
    @ResourceMethod(name = "用户查询页面", check = CHECK_LOGIN)
    public String getPicAddress(String status) throws Exception {
        WebUtil.getRequest().setAttribute("status", status);
        return "common/pic_upload";
    }

    @RequestMapping(value = "checkName", method = RequestMethod.POST)
    @ResourceMethod(name = "检查商品、类别名称是否重复", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage checkName(String name,String status,String brand_id,String class_id) {
        CdsClass cc = new CdsClass();
        CdsGoodsInfo cgi = new CdsGoodsInfo();
        //1检查商品表name是否重复、2检查类别表name是否重复
        String isok ="0";
        if("1".equals(status)){
            if(!StringUtils.isEmpty(class_id)){
                cgi.setClass_id(Integer.valueOf(class_id));
            }
            cgi.setGood_name(name);
            cgi.addConditionFields("good_name,class_id");
            cgi =  sqlDao.getRecord(cgi);
            if(cgi!=null){
                isok = "1";
            }
        }else{
            cc.setClass_name(name);
            cc.setBrand_id(Integer.valueOf(brand_id));
            cc.addConditionField("class_name");
            cc.addConditionField("brand_id");
            cc = sqlDao.getRecord(cc);
            if(cc!=null){
                isok="1";
            }
        }
        return new JsonMessage(isok);
    }


    @RequestMapping(value = "GetBrandTree", method = RequestMethod.POST)
    @ResourceMethod(name = "获取品牌对应分类", check = CHECK_LOGIN)
    @ResponseBody
    public List GetBrandTree() throws Exception {
        CdsBrand cb = new CdsBrand();
        CdsClass cc = new CdsClass();
        List storesList = new ArrayList();
        List<CdsBrand> ccList = sqlDao.getRecordList(cb);
        List<CdsClass> cgiList = sqlDao.getRecordList(cc);
        List<JSONObject> jolist = new ArrayList<JSONObject>();
        if(ccList!=null&&ccList.size()>0){
            for(int i=0;i<ccList.size();i++){
                JSONObject jo = new JSONObject();
                List<JSONObject> goodslist = new ArrayList<JSONObject>();
                if(cgiList!=null&&cgiList.size()>0){
                    for (int j=0;j<cgiList.size();j++){
                        JSONObject jo2 = new JSONObject();
                        Integer brand_id1 = ccList.get(i).getBrand_id();
                        Integer brand_id2 = cgiList.get(j).getBrand_id();
                        if(brand_id1.equals(brand_id2)){
                            jo2.put("id",cgiList.get(j).getClass_id());
                            jo2.put("text",cgiList.get(j).getClass_name());
                            jo2.put("iconCls","icon icon-367");
                            goodslist.add(jo2);
                            jo.put("id",(ccList.get(i).getBrand_id()+"a"));
                            jo.put("text",ccList.get(i).getBrand_name());
                            jo.put("state","open");
                            jo.put("iconCls","icon icon-104");
                            jo.put("children",goodslist);
                            //jo.put("state","closed");
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

}
