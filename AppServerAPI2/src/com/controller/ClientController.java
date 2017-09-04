package com.controller;

import com.framework.annotation.ResourceMethod;
import com.framework.controller.BasicController;
import com.framework.mapping.system.CdsBrand;
import com.framework.mapping.system.CdsClass;
import com.framework.mapping.system.CdsClassType;
import com.framework.mapping.system.CdsStores;
import com.framework.system.SystemConstant;
import com.framework.util.DateUtil;
import com.framework.util.WebUtil;
import com.opensdk.meituan.vo.SystemParamMeituan;
import com.service.OrderService;
import com.service.PrintService;
import com.service.StoresService;
import org.apache.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

import static com.framework.annotation.CheckType.CHECK_LOGIN;

/**
 * Created by c on 2017-02-02.
 */
@Controller
@RequestMapping("Client")
public class ClientController extends BasicController {
    @Resource(name = "StoresService")
    protected StoresService storesService;

    @Resource(name = "PrintService")
    protected PrintService printService;

    @Resource(name = "OrderService")
    protected OrderService orderService;

    private final static SystemParamMeituan sysPram = new SystemParamMeituan("483", "a4afb2e41caad32336832a694509152b");


    @RequestMapping(value = "print", method = RequestMethod.GET)
    @ResourceMethod(name = "门店主界面", check = CHECK_LOGIN)
    public String print() {
        return "client/print";
    }

    @RequestMapping(value = "home", method = RequestMethod.GET)
    @ResourceMethod(name = "门店主界面", check = CHECK_LOGIN)
    public String home(Model model, Integer stores_id) throws Exception {
        DecimalFormat df = new DecimalFormat("######0.00");
        WebUtil.getRequest().setAttribute("curday", DateUtil.getDate());
        if (WebUtil.getUser().getCur_date() != null) {
            WebUtil.getRequest().setAttribute("curday", WebUtil.getUser().getCur_date());
        }

//        stores_id = WebUtil.getUser().getStores_id();
        stores_id = WebUtil.getSession("stores_id");
        if(stores_id == null){
            stores_id = WebUtil.getUser().getStores_id();
        }

        Map paramMap = new HashMap();
        paramMap.put("cur_date", DateUtil.getDate());
        paramMap.put("yesterday", DateUtil.dateToStr(DateUtil.addDay(new Date(), -1)));
        paramMap.put("last_week", DateUtil.dateToStr(DateUtil.addDay(new Date(), -7)));
        paramMap.put("stores_id", stores_id);

        List<Map> list = sqlDao.getRecordList("cds_home.getdoActionCount", paramMap);
        for (Map map : list) {
            String keyname = map.get("keyname").toString();
            String docount = map.get("docount").toString();
            model.addAttribute(keyname, docount);
        }

        Map paramMap1 = new HashMap();
        paramMap1.put("cur_date", DateUtil.getDate());
        paramMap1.put("yesterday", DateUtil.dateToStr(DateUtil.addDay(new Date(), -1)));
        paramMap1.put("last_week", DateUtil.dateToStr(DateUtil.addDay(new Date(), -7)));
        paramMap1.put("stores_id", stores_id);
        if (WebUtil.getUser().getCur_date() != null) {
            String date = WebUtil.getUser().getCur_date();
            paramMap1.put("cur_date", date);
            paramMap1.put("yesterday", DateUtil.dateToStr(DateUtil.addDay(DateUtil.strToDate(date), -1)));
            paramMap1.put("last_week", DateUtil.dateToStr(DateUtil.addDay(DateUtil.strToDate(date), -7)));
        }

        list = sqlDao.getRecordList("cds_home.shop_data", paramMap1);

        HashMap result_map = new HashMap();

        List<Map> curser_list = new ArrayList();
        List<Map> yesterday_list = new ArrayList();
        List<Map> last_week_list = new ArrayList();

        double curser_income = 0;
        double curser_sum1 = 0;
        Integer curser_sum2 = 0;
        double curser_shop_part = 0;
        double curser_platform_part = 0;
        double curser_uc_price = 0;
        double curser_serviceFee = 0;
        double curser_deduction_price = 0;
        double curser_total_price = 0;
        double curser_box_price = 0;
        double curser_ship_fee = 0;
        Integer curser_sum4 = 0;
        double curser_goods_prcie = 0;


        double yesterday_income = 0;
        double yesterday_sum1 = 0;
        Integer yesterday_sum2 = 0;
        double yesterday_shop_part = 0;
        double yesterday_platform_part = 0;
        double yesterday_uc_price = 0;
        double yesterday_serviceFee = 0;
        double yesterday_deduction_price = 0;
        double yesterday_total_price = 0;
        double yesterday_box_price = 0;
        double yesterday_ship_fee = 0;
        Integer yesterday_sum4 = 0;
        double yesterday_goods_prcie = 0;

        double last_week_income = 0;
        double last_week_sum1 = 0;
        Integer last_week_sum2 = 0;
        double last_week_shop_part = 0;
        double last_week_platform_part = 0;
        double last_week_uc_price = 0;
        double last_week_serviceFee = 0;
        double last_week_deduction_price = 0;
        double last_week_total_price = 0;
        double last_week_box_price = 0;
        double last_week_ship_fee = 0;
        Integer last_week_sum4 = 0;
        double last_week_goods_prcie = 0;

        for (Map map : list) {

            String fromin = map.get("fromin").toString();
            String type = map.get("type").toString(); //类型 curser当天 yesterday昨天 last_week 上周
            double sum1 = ((BigDecimal) map.get("sum1")).doubleValue();//交易额
            Integer sum2 = (Integer) map.get("sum2");//有效订单数
            double sum3 = 0;
            if(sum2!=0){
                sum3 = sum1/sum2;
            }
//            double sum3 = ((BigDecimal) map.get("sum3")).doubleValue();//客单价
            map.put("sum3", df.format(sum3));
            double income = ((BigDecimal) map.get("income")).doubleValue();//店铺收入
            Integer sum4 = (Integer) map.get("sum4");//无效订单数
            double serviceFee = ((BigDecimal) map.get("serviceFee")).doubleValue();//平台服务费
            double box_price = ((BigDecimal) map.get("box_price")).doubleValue();//餐盒费
            double ship_fee = ((BigDecimal) map.get("ship_fee")).doubleValue();//配送费
            double shop_part = ((BigDecimal) map.get("shop_part")).doubleValue();//店铺承担
            double platform_part = ((BigDecimal) map.get("platform_part")).doubleValue();//平台承担
            double deduction_price = ((BigDecimal) map.get("deduction_price")).doubleValue();//优惠金额
            double uc_price = ((BigDecimal) map.get("uc_price")).doubleValue();//红包金额
            double total_price = ((BigDecimal) map.get("total_price")).doubleValue();//用户实付
            Integer brand_id = (Integer) map.get("brand_id");
            double goods_prcie = ((BigDecimal) map.get("goods_prcie")).doubleValue();//商品总价

            if (type.equals("curser")) {
                curser_income += income;
                curser_sum1 += sum1;
                curser_sum2 += sum2;
                curser_shop_part += shop_part;
                curser_platform_part += platform_part;
                curser_uc_price += uc_price;
                curser_serviceFee += serviceFee;
                curser_deduction_price += deduction_price;
                curser_total_price += total_price;
                curser_box_price += box_price;
                curser_ship_fee += ship_fee;
                curser_sum4 += sum4;
                curser_goods_prcie += goods_prcie;

                curser_list.add(map);

            } else if (type.equals("yesterday")) {
                yesterday_income += income;
                yesterday_sum1 += sum1;
                yesterday_sum2 += sum2;
                yesterday_shop_part += shop_part;
                yesterday_platform_part += platform_part;
                yesterday_uc_price += uc_price;
                yesterday_serviceFee += serviceFee;
                yesterday_deduction_price += deduction_price;
                yesterday_total_price += total_price;
                yesterday_box_price += box_price;
                yesterday_ship_fee += ship_fee;
                yesterday_sum4 += sum4;
                yesterday_goods_prcie += goods_prcie;

                yesterday_list.add(map);

            } else if (type.equals("last_week")) {
                last_week_income += income;
                last_week_sum1 += sum1;
                last_week_sum2 += sum2;
                last_week_shop_part += shop_part;
                last_week_platform_part += platform_part;
                last_week_uc_price += uc_price;
                last_week_serviceFee += serviceFee;
                last_week_deduction_price += deduction_price;
                last_week_total_price += total_price;
                last_week_box_price += box_price;
                last_week_ship_fee += ship_fee;
                last_week_sum4 += sum4;
                last_week_goods_prcie += goods_prcie;

                last_week_list.add(map);

            }

        }

        List cds_list1 = new ArrayList();
        List sxg_list1 = new ArrayList();
        HashMap brand_map1 = new HashMap();

        for (Map map : curser_list) {
            if (map.get("brand_id").toString().equals("1")) {
                cds_list1.add(map);
            } else if (map.get("brand_id").toString().equals("2")) {
                sxg_list1.add(map);
            }
        }

        brand_map1.put("income", df.format(curser_income));
        brand_map1.put("sum1", df.format(curser_sum1));
        brand_map1.put("sum2", curser_sum2);
        brand_map1.put("shop_part", df.format(curser_shop_part));
        brand_map1.put("platform_part", df.format(curser_platform_part));
        brand_map1.put("uc_price", df.format(curser_uc_price));
        brand_map1.put("serviceFee", df.format(curser_serviceFee));
        brand_map1.put("deduction_price", df.format(curser_deduction_price));
        brand_map1.put("total_price", df.format(curser_total_price));
        brand_map1.put("box_price", df.format(curser_box_price));
        brand_map1.put("ship_fee", df.format(curser_ship_fee));
        brand_map1.put("sum4", curser_sum4);
        brand_map1.put("goods_prcie", df.format(curser_goods_prcie));
        brand_map1.put("sum3", df.format(curser_sum1 / curser_sum2));
        if (curser_sum2 == 0) {
            brand_map1.put("sum3", 0.00);
        }


        brand_map1.put("cds", cds_list1);
        brand_map1.put("sxg", sxg_list1);


        List cds_list2 = new ArrayList();
        List sxg_list2 = new ArrayList();
        HashMap brand_map2 = new HashMap();

        for (Map map : yesterday_list) {
            if (map.get("brand_id").toString().equals("1")) {
                cds_list2.add(map);
            } else if (map.get("brand_id").toString().equals("2")) {
                sxg_list2.add(map);
            }
        }
        brand_map2.put("income", df.format(yesterday_income));
        brand_map2.put("sum1", df.format(yesterday_sum1));
        brand_map2.put("sum2", yesterday_sum2);
        brand_map2.put("shop_part", df.format(yesterday_shop_part));
        brand_map2.put("platform_part", df.format(yesterday_platform_part));
        brand_map2.put("uc_price", df.format(yesterday_uc_price));
        brand_map2.put("serviceFee", df.format(yesterday_serviceFee));
        brand_map2.put("deduction_price", df.format(yesterday_deduction_price));
        brand_map2.put("total_price", df.format(yesterday_total_price));
        brand_map2.put("box_price", df.format(yesterday_box_price));
        brand_map2.put("ship_fee", df.format(yesterday_ship_fee));
        brand_map2.put("sum4", yesterday_sum4);
        brand_map2.put("goods_prcie", df.format(yesterday_goods_prcie));
        brand_map2.put("sum3", df.format(yesterday_sum1 / yesterday_sum2));
        if (yesterday_sum2 == 0) {
            brand_map2.put("sum3", 0.00);
        }

        brand_map2.put("cds", cds_list2);
        brand_map2.put("sxg", sxg_list2);


        List cds_list3 = new ArrayList();
        List sxg_list3 = new ArrayList();
        HashMap brand_map3 = new HashMap();

        for (Map map : last_week_list) {
            if (map.get("brand_id").toString().equals("1")) {
                cds_list3.add(map);
            } else if (map.get("brand_id").toString().equals("2")) {
                sxg_list3.add(map);
            }
        }
        brand_map3.put("income", df.format(last_week_income));
        brand_map3.put("sum1", df.format(last_week_sum1));
        brand_map3.put("sum2", last_week_sum2);
        brand_map3.put("shop_part", df.format(last_week_shop_part));
        brand_map3.put("platform_part", df.format(last_week_platform_part));
        brand_map3.put("uc_price", df.format(last_week_uc_price));
        brand_map3.put("serviceFee", df.format(last_week_serviceFee));
        brand_map3.put("deduction_price", df.format(last_week_deduction_price));
        brand_map3.put("total_price", df.format(last_week_total_price));
        brand_map3.put("box_price", df.format(last_week_box_price));
        brand_map3.put("ship_fee", df.format(last_week_ship_fee));
        brand_map3.put("sum4", last_week_sum4);
        brand_map3.put("goods_prcie", df.format(last_week_goods_prcie));
        brand_map3.put("sum3", df.format(last_week_sum1 / last_week_sum2));
        if (last_week_sum2 == 0) {
            brand_map3.put("sum3", 0.00);
        }

        brand_map3.put("cds", cds_list3);
        brand_map3.put("sxg", sxg_list3);


        result_map.put("curser", brand_map1);
        result_map.put("yesterday", brand_map2);
        result_map.put("last_week", brand_map3);

        model.addAttribute("map", result_map);
        return "client/home";
    }

    @RequestMapping(value = "doorderlist", method = RequestMethod.GET)
    @ResourceMethod(name = "订单处理列表", check = CHECK_LOGIN)
    public String doorderlist() {
        return "client/doorderlist";
    }

    @RequestMapping(value = "comment", method = RequestMethod.GET)
    @ResourceMethod(name = "顾客评价", check = CHECK_LOGIN)
    public String comment() {
        return "client/comment";
    }

    @RequestMapping(value = "business_data", method = RequestMethod.GET)
    @ResourceMethod(name = "营业数据", check = CHECK_LOGIN)
    public String business_data() {
        WebUtil.getRequest().setAttribute("curday", DateUtil.getDate());
        return "client/business_data";
    }

    @RequestMapping(value = "good_data", method = RequestMethod.GET)
    @ResourceMethod(name = "菜品统计", check = CHECK_LOGIN)
    public String good_data() {
        WebUtil.getRequest().setAttribute("curday", DateUtil.getDate());
        return "client/good_data";
    }

    @RequestMapping(value = "good_analyse", method = RequestMethod.GET)
    @ResourceMethod(name = "菜品分析", check = CHECK_LOGIN)
    public String good_analyse() {
        WebUtil.getRequest().setAttribute("curday", DateUtil.dateToStr(DateUtil.addDay(new Date(),  - 1)));
        return "client/good_analyse";
    }

    @RequestMapping(value = "order_day", method = RequestMethod.GET)
    @ResourceMethod(name = "订单日统计", check = CHECK_LOGIN)
    public String order_day() {
        WebUtil.getRequest().setAttribute("curday", DateUtil.getDate());
        return "client/order_day";
    }

    @RequestMapping(value = "good_day", method = RequestMethod.GET)
    @ResourceMethod(name = "菜品日统计", check = CHECK_LOGIN)
    public String good_day() {
        WebUtil.getRequest().setAttribute("curday", DateUtil.getDate());
        return "client/good_day";
    }

    @RequestMapping(value = "order_ontime", method = RequestMethod.GET)
    @ResourceMethod(name = "准时率统计", check = CHECK_LOGIN)
    public String orderOnTime() {
        WebUtil.getRequest().setAttribute("curday", DateUtil.getDate());
        return "client/order_ontime";
    }

    @RequestMapping(value = "rider_count", method = RequestMethod.GET)
    @ResourceMethod(name = "骑手统计", check = CHECK_LOGIN)
    public String riderCount() {
        WebUtil.getRequest().setAttribute("curday", DateUtil.getDate());
        return "client/rider_count";
    }

    @RequestMapping(value = "report1", method = RequestMethod.GET)
    @ResourceMethod(name = "用户分析", check = CHECK_LOGIN)
    public String report1() {
        CdsBrand cdsBrand = new CdsBrand();
        List cdsbrands = sqlDao.getRecordList(cdsBrand);
        WebUtil.setRequest("cdsbrands", cdsbrands);
        WebUtil.getRequest().setAttribute("curday", DateUtil.dateToStr(DateUtil.addDay(new Date(), -1)));
        return "client/report1";
    }

    @RequestMapping(value = "report2", method = RequestMethod.GET)
    @ResourceMethod(name = "出餐率统计", check = CHECK_LOGIN)
    public String report2() {
        WebUtil.getRequest().setAttribute("curday", DateUtil.getDate());
        return "client/report2";
    }

    @RequestMapping(value = "comment_data", method = RequestMethod.GET)
    @ResourceMethod(name = "评价投诉", check = CHECK_LOGIN)
    public String comment_data() {

        return "client/comment_data";
    }

    @RequestMapping(value = "member_data", method = RequestMethod.GET)
    @ResourceMethod(name = "顾客分析", check = CHECK_LOGIN)
    public String member_data() {

        return "client/member_data";
    }

    @RequestMapping(value = "shop_part", method = RequestMethod.GET)
    @ResourceMethod(name = "自营销占比", check = CHECK_LOGIN)
    public String shop_part() {
        WebUtil.getRequest().setAttribute("curday", DateUtil.getDate());
        return "client/shop_part";
    }

    @RequestMapping(value = "pack_job", method = RequestMethod.GET)
    @ResourceMethod(name = "打包计件", check = CHECK_LOGIN)
    public String pack_job() {
        WebUtil.getRequest().setAttribute("curday", DateUtil.getDate());
        return "client/pack_job";
    }


    @RequestMapping(value = "stores_count", method = RequestMethod.GET)
    @ResourceMethod(name = "店铺战报", check = CHECK_LOGIN)
    public String stores_count() {
        WebUtil.getRequest().setAttribute("curday", DateUtil.getDate());
        WebUtil.getRequest().setAttribute("storesList", sqlDao.getRecordList("cds_stores_income.select"));
        return "client/stores_count";
    }

    @RequestMapping(value = "refund", method = RequestMethod.GET)
    @ResourceMethod(name = "退款处理", check = CHECK_LOGIN)
    public String refund() {
        CdsBrand cdsBrand = new CdsBrand();
        List cdsbrands = sqlDao.getRecordList(cdsBrand);
        WebUtil.setRequest("cdsbrands", cdsbrands);
        WebUtil.getRequest().setAttribute("curday", DateUtil.getDate());

        CdsStores shop = new CdsStores();
        shop.addParamFields("stores_id,name");
        List cdsshops = sqlDao.getRecordList(shop);
        WebUtil.setRequest("cdsshops", cdsshops);

        return "client/refund";
    }

    @RequestMapping(value = "historyRefund", method = RequestMethod.GET)
    @ResourceMethod(name = "退款处理", check = CHECK_LOGIN)
    public String historyRefund() {
        CdsBrand cdsBrand = new CdsBrand();
        List cdsbrands = sqlDao.getRecordList(cdsBrand);
        WebUtil.setRequest("cdsbrands", cdsbrands);
        WebUtil.getRequest().setAttribute("curday", DateUtil.getDate());

        CdsStores shop = new CdsStores();
        shop.addParamFields("stores_id,name");
        List cdsshops = sqlDao.getRecordList(shop);
        WebUtil.setRequest("cdsshops", cdsshops);

        return "client/historyRefund";
    }

    @RequestMapping(value = "orderlist", method = RequestMethod.GET)
    @ResourceMethod(name = "订单查询列表", check = CHECK_LOGIN)
    public String orderlist() {
        CdsBrand cdsBrand = new CdsBrand();
        List cdsbrands = sqlDao.getRecordList(cdsBrand);
        WebUtil.setRequest("cdsbrands", cdsbrands);
        WebUtil.getRequest().setAttribute("curday", DateUtil.getDate());
        return "client/orderlist";
    }

    @RequestMapping(value = "exception_orderlist/{action}", method = RequestMethod.GET)
    @ResourceMethod(name = "首页异常订单查询", check = CHECK_LOGIN)
    public String exception_orderlist(@PathVariable("action") String action) {
        WebUtil.getRequest().setAttribute("action", action);
        CdsBrand cdsBrand = new CdsBrand();
        List cdsbrands = sqlDao.getRecordList(cdsBrand);
        WebUtil.setRequest("cdsbrands", cdsbrands);
        WebUtil.getRequest().setAttribute("curday", DateUtil.getDate());
        return "client/exception_orderlist";
    }


    @RequestMapping(value = "orderlisthistory", method = RequestMethod.GET)
    @ResourceMethod(name = "订单查询列表", check = CHECK_LOGIN)
    public String orderlisthistory() {
        CdsBrand cdsBrand = new CdsBrand();
        List cdsbrands = sqlDao.getRecordList(cdsBrand);
        WebUtil.setRequest("cdsbrands", cdsbrands);
        WebUtil.getRequest().setAttribute("curday", DateUtil.dateToStr(DateUtil.addDay(new Date(), -SystemConstant.DATA_DAY + 1)));
        return "client/orderlisthistory";
    }

    @RequestMapping(value = "all_orderlist", method = RequestMethod.GET)
    @ResourceMethod(name = "总部订单查询列表", check = CHECK_LOGIN)
    public String all_orderlist(String keyword) {
        CdsClass cc = new CdsClass();
        List<CdsClass> classList = sqlDao.getRecordList(cc);
        CdsClassType cct = new CdsClassType();
        List<CdsClassType> classTypeList = sqlDao.getRecordList(cct);


        WebUtil.getRequest().setAttribute("classtypelist", classTypeList);
        WebUtil.getRequest().setAttribute("classlist", classList);

        CdsBrand cdsBrand = new CdsBrand();
        List cdsbrands = sqlDao.getRecordList(cdsBrand);
        CdsStores shop = new CdsStores();
        shop.addParamFields("stores_id,name");
        List cdsshops = sqlDao.getRecordList(shop);

        WebUtil.setRequest("cdsshops", cdsshops);
        WebUtil.setRequest("cdsbrands", cdsbrands);
        WebUtil.setRequest("keyword", keyword);
        WebUtil.getRequest().setAttribute("curday", DateUtil.getDate());
        return "client/all_orderlist";
    }

    @RequestMapping(value = "shop_orderlist", method = RequestMethod.GET)
    @ResourceMethod(name = "门店订单查询列表", check = CHECK_LOGIN)
    public String shop_orderlist() {
        CdsBrand cdsBrand = new CdsBrand();
        List cdsbrands = sqlDao.getRecordList(cdsBrand);
        WebUtil.setRequest("cdsbrands", cdsbrands);
        WebUtil.getRequest().setAttribute("curday", DateUtil.getDate());
        return "client/shop_orderlist";
    }

    @RequestMapping(value = "all_orderlisthistory", method = RequestMethod.GET)
    @ResourceMethod(name = "总部历史订单查询列表", check = CHECK_LOGIN)
    public String all_orderlisthistory() {
        CdsBrand cdsBrand = new CdsBrand();
        List cdsbrands = sqlDao.getRecordList(cdsBrand);
        CdsStores shop = new CdsStores();
        shop.addParamFields("stores_id,name");
        List cdsshops = sqlDao.getRecordList(shop);

        WebUtil.setRequest("cdsshops", cdsshops);
        WebUtil.setRequest("cdsbrands", cdsbrands);
        WebUtil.getRequest().setAttribute("curday", DateUtil.dateToStr(DateUtil.addDay(new Date(), -SystemConstant.DATA_DAY + 1)));
        return "client/all_orderlisthistory";
    }

    @RequestMapping(value = "shop_orderlisthistory", method = RequestMethod.GET)
    @ResourceMethod(name = "门店历史订单查询列表", check = CHECK_LOGIN)
    public String shop_orderlisthistory() {
        CdsBrand cdsBrand = new CdsBrand();
        List cdsbrands = sqlDao.getRecordList(cdsBrand);
        WebUtil.setRequest("cdsbrands", cdsbrands);
        WebUtil.getRequest().setAttribute("curday", DateUtil.dateToStr(DateUtil.addDay(new Date(), -SystemConstant.DATA_DAY + 1)));
        return "client/shop_orderlisthistory";
    }
}
