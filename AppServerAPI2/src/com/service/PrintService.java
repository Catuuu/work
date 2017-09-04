package com.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.framework.mapping.system.*;
import com.framework.service.BasicService;
import com.framework.util.CodeUtil;
import com.framework.util.DateUtil;
import com.framework.util.PrintUtil;
import com.framework.util.StringUtil;
import com.opensdk.weixin.factory.APIFactoryWeixin;
import com.opensdk.weixin.vo.SystemParamWeixin;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.*;


/**
 * Created by c on 2017-01-30.
 */
@Service("PrintService")
public class PrintService extends BasicService {


    @Resource(name = "StoresService")
    protected StoresService storesService;

    /**
     * 打印客户小票
     * @param cdsOrderInfo
     */
    public boolean printClient(CdsOrderInfo cdsOrderInfo){
        return printClient(cdsOrderInfo,1);
    }

   public boolean printClient(CdsOrderInfo cdsOrderInfo,int type){
       boolean isdebug = false;
       if(isdebug){
           String content = GetPrintContent(cdsOrderInfo);//获取打印格式
           String title = "@@a"+cdsOrderInfo.getOrder_id()+"\r\n";
            title += "@@b"+cdsOrderInfo.getStores_id()+"\r\n";
            title += "@@c"+cdsOrderInfo.getOrder_no().substring(5)+"\r\n";
            title += "@@d"+DateUtil.dateToStr(cdsOrderInfo.getCreate_date())+"\r\n";
           String finalTitle = title;
           jmsTemplate.send("光谷店", new MessageCreator() {
               @Override
               public Message createMessage(Session session) throws JMSException {
                   return session.createTextMessage(finalTitle + content);
               }
           });
          /* boolean flag = PrintUtil.sendContent(content,"4004510537","vmzumbmuxphk");
           System.out.println("打印测试");*/
       }else{
           List<CdsPrints> cdsPrintsList = GetPrints2(cdsOrderInfo.getStores_id());
           for (CdsPrints prints:cdsPrintsList){
              if(prints.getStatus()==1&&prints.getPrint_model()==2){//网络打印机
                   cdsOrderInfo.setPrint_type(type);//1系统打印,2手动
                   String code = CodeUtil.getCode(cdsOrderInfo);//一维码
                   cdsOrderInfo.setCode(code.split("_")[0]);
                   Integer brand_id = cdsOrderInfo.getBrand_id();
                   cdsOrderInfo.setUrl(getWeixinUrl(code.split("_")[1],brand_id));//二维码url
                   String orderstr = JSONObject.toJSONString(cdsOrderInfo);
                   jmsTemplate.send("stores.print_" + cdsOrderInfo.getStores_id(), new MessageCreator() {
                       @Override
                       public Message createMessage(Session session) throws JMSException {
                           return session.createTextMessage(orderstr);
                       }
                   });
               }
           }
       }
       return true;
   }


    /**
     * 打印取消订单小票
     * @param cdsOrderInfo
     */
    public void printCancel(CdsOrderInfo cdsOrderInfo){
        List<CdsPrints> cdsPrintsList = GetPrints(cdsOrderInfo.getStores_id());
        for (CdsPrints prints:cdsPrintsList){
            if(prints.getStatus()==1&&prints.getPrint_model()==1){//易联云打印机
                String content = GetCancelPrintContent(cdsOrderInfo);
                boolean flag = PrintUtil.sendContent(content,prints.getPrint_name(),prints.getPrint_address());

                CdsPrintList cdsPrintList = new CdsPrintList();
                cdsPrintList.setPl_id(StringUtil.getPrimaryOrderKey());
                cdsPrintList.setPrint_id(prints.getPrint_id());
                cdsPrintList.setStores_id(prints.getStores_id());
                cdsPrintList.setPrint_name(prints.getPrint_name());
                cdsPrintList.setPrint_address(prints.getPrint_address());
                cdsPrintList.setPrint_type(2);
                cdsPrintList.setPrint_remark(content);
                cdsPrintList.setPrint_time(new Date());
                cdsPrintList.addParamFields();
                sqlDao.insertRecord(cdsPrintList);

            }else if(prints.getStatus()==1&&prints.getPrint_model()==2){//网络打印机

            }
        }
    }

    /**
     * 通过商铺ID获取打印机信息
     *
     * @param stores_id  商品关键词
     * @return
     * @throws Exception
     */
    public List<CdsPrints> GetPrints2(int stores_id){
        String key = "print_key_"+stores_id;
        String lock_key = "print_key_lock_"+stores_id;
        String content = getRedisString(key);
        if(content==null){
            CdsPrints cdsPrints = new CdsPrints();
            cdsPrints.setStores_id(stores_id);
            cdsPrints.addConditionField("stores_id");
            List<CdsPrints> cdsPrintsList =  sqlDao.getRecordList(cdsPrints);
            CdsStores stores = new CdsStores();

            for (CdsPrints print:cdsPrintsList){
                stores.getPrintsList().add(print);
            }

            addRedis(key,JSONArray.toJSONString(stores.getPrintsList()),10*60);
            return stores.getPrintsList();
        }
        List<CdsPrints> cdsprints = new ArrayList<CdsPrints>();
        if(content !=null){
            cdsprints =  JSONArray.parseArray(content,CdsPrints.class);
        }
        return cdsprints;
    }


    /**
     * 通过商铺ID获取打印机信息
     *
     * @param stores_id  商品关键词
     * @return
     * @throws Exception
     */
    public List<CdsPrints> GetPrints(int stores_id)  {
        this.loadMsGoodsRadis();//装载商品信息
        String key = "stores_print_key_"+stores_id;
        String content = getRedisString(key);
        List<CdsPrints> cdsprints = new ArrayList<CdsPrints>();
        if(content !=null){
            cdsprints =  JSONArray.parseArray(content,CdsPrints.class);
        }
        return cdsprints;
    }



    /**
     * 装载商铺信息至redis缓存
     */
    private void loadMsGoodsRadis(){
        CdsStores stores = new CdsStores();
        Date d = new Date();
        String printupdatetime = this.getRedisString("printupdatetime");
        if(printupdatetime==null){
            Map<String,String> printMap = getAllPrintMap();
            for (String key : printMap.keySet()) {
                String content = printMap.get(key);
                addRedis(key,content);
            }
            this.addRedis("printupdatetime",Long.toString(d.getTime()));
        }else{
            long supdatetime = Long.parseLong(printupdatetime);

            if((d.getTime()-supdatetime)>10*60*1000){
                Map<String,String> printMap = getAllPrintMap();
                for (String key : printMap.keySet()) {
                    String content = printMap.get(key);
                    updateRedis(key,content);
                }
                this.updateRedis("printupdatetime",Long.toString(d.getTime()));
            }
        }
    }

    /**
     * 获取所有后台商品信息
     * @return
     */
    private Map<String,String> getAllPrintMap(){
        Map<String,String> resultMap = new HashMap<String,String>();

        List<CdsPrints> cdsPrintsList =  sqlDao.getRecordList(new CdsPrints());
        List<CdsStores> cdsStoresList = sqlDao.getRecordList(new CdsStores());

        for (CdsStores stores:cdsStoresList) {
            for (CdsPrints print:cdsPrintsList){
                if(stores.getStores_id()==print.getStores_id()) {
                    stores.getPrintsList().add(print);
                }
            }
            String key = "stores_print_key_"+stores.getStores_id();
            resultMap.put(key,JSONArray.toJSONString(stores.getPrintsList()));
        }
        return resultMap;
    }



    /**
     * 生成取消订单小票打印模板
     * @param order
     * @return
     */
    private String GetCancelPrintContent(CdsOrderInfo order){
        CdsStores cdsStores = storesService.GetStores(order.getStores_id());
        StringBuffer sb = new StringBuffer("");

        sb.append(StringUtil.dispRepair(cdsStores.getName(),"*")+"\r\r");
        sb.append("@@2"+StringUtil.dispRepair("#"+order.getOrder_no().substring(5)+" 订单"," ")+"\r\r");

        String fromin_name = "";
        if(order.getFromin_name()!=null && !order.getFromin_name().equals("")){
            fromin_name = " " + order.getFromin_name();
        }

        if(order.getOrder_type()==2){//店堂就餐
            sb.append("@@2"+StringUtil.dispRepairCenter(order.getFromin()+fromin_name+"#"+order.getFromin_no(),"店堂就餐"," ")+"\r");
        }else{
            sb.append("@@2"+order.getFromin()+fromin_name+"#"+order.getFromin_no()+"\r");
        }

        if(!order.getFromin().equals("微信")){
            sb.append("订单号:"+order.getOrder_desc()+"\r");
        }
        sb.append("下单时间:"+ DateUtil.dateToStr(order.getCreate_date(),"yyyy-MM-dd HH:mm")+"\r");
        sb.append("--------------------------------"+ "\r");

        sb.append("@@2订单取消:"+order.getCancel_type()+"\r");
        String cancel_desc = "";
        if(order.getCancel_remark()!=null && !order.getCancel_remark().equals("")){
            cancel_desc = order.getCancel_remark().replaceAll("‼","");
            sb.append("\r\r");
            try {
                if(cancel_desc.getBytes("GBK").length >32){
                    List<String> member_desc_arr = StringUtil.cut_str(cancel_desc);

                    for(int i=0;i<member_desc_arr.size();i++){
                        sb.append("@@2"+member_desc_arr.get(i)+"\r");
                    }
                }else{
                    sb.append("@@2"+cancel_desc+"\r");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


        }

        sb.append("--------------------------------"+ "\r");
        return sb.toString();
    }

    /**
     * 生成云打印机小票打印模板
     * @param order
     * @return
     *//*
    private String GetPrintContent(CdsOrderInfo order){
        CdsStores cdsStores = storesService.GetStores(order.getStores_id());
        StringBuffer sb = new StringBuffer("");

        sb.append(StringUtil.dispRepair(cdsStores.getName(),"*")+"\r\r");
        sb.append("@@2"+StringUtil.dispRepair("#"+order.getOrder_no().substring(5)+" 订单"," ")+"\r\r");

        String fromin_name = "";
        if(order.getFromin_name()!=null && !order.getFromin_name().equals("")){
            fromin_name = " " + order.getFromin_name();
        }

        if(order.getOrder_type()==2){//店堂就餐
            sb.append("@@2"+StringUtil.dispRepairCenter(order.getFromin()+fromin_name+"#"+order.getFromin_no(),"店堂就餐"," ")+"\r");
        }else{
            sb.append("@@2"+order.getFromin()+fromin_name+"#"+order.getFromin_no()+"\r");
        }

        if(!order.getFromin().equals("微信")){
            sb.append("订单号:"+order.getOrder_desc()+"\r");
        }
        sb.append("下单时间:"+ DateUtil.dateToStr(order.getCreate_date(),"yyyy-MM-dd HH:mm")+"\r");
        sb.append("--------------------------------"+ "\r");
        if(order.getGoods()!=null && !order.getGoods().equals("")){
            List<CdsGood> list =  JSONArray.parseArray(order.getGoods(),CdsGood.class);
            for (CdsGood good:list){
                sb.append(formatGoodName(good));
            }
        }
        sb.append("\r\n\r\n");
        DecimalFormat df1 = new DecimalFormat("0.00");
        if(order.getBox_price()>0){
            sb.append(StringUtil.dispRepairCenter("餐盒费",df1.format(order.getBox_price())," "));
        }
        if(order.getShip_fee()>0){
            sb.append(StringUtil.dispRepairCenter("配送费",df1.format(order.getShip_fee())," "));
        }

        if(order.getUc_price()>0){
            sb.append(StringUtil.dispRepairCenter("红  包",df1.format(order.getUc_price())," "));
        }

        if(order.getDeduction_price()!= 0){
            sb.append(StringUtil.dispRepairCenter("订单优惠",df1.format(order.getDeduction_price())," "));
        }

        sb.append("--------------------------------"+ "\r");

        sb.append(StringUtil.dispRepairCenter("[合计]",df1.format(order.getTotal_price())," "));
        if(order.getPay_type_id()==3){
            sb.append("@@2付款方式：货到付款"+"\r\r");
        }
            //service_time_str
        if(order.getService_time_str()!=null&&!order.getService_time_str().equals("及时送达")){
            sb.append("@@2预订单，送达时间："+order.getService_time_str()+"\r");
        }

        String member_desc = "";
        if(order.getMember_desc()!=null && !order.getMember_desc().equals("")){
            //member_desc = order.getMember_desc().replaceAll("‼","");
            member_desc = StringUtil.StringFilter(order.getMember_desc());
            sb.append("\r\r");
            try {
                if(member_desc.getBytes("GBK").length >32){
                    List<String> gmember_desc_arr = StringUtil.cut_str(member_desc);

                    for(int i=0;i<gmember_desc_arr.size();i++){
                        sb.append("@@2"+gmember_desc_arr.get(i)+"\r");
                    }
                }else{
                    sb.append("@@2"+member_desc+"\r");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


        }

        sb.append("--------------------------------"+ "\r");

        //收货地址格式
       // String address = order.getReceiver_name()+" "+order.getReceiver_phone()+" "+order.getReceiver_adress();
        String address = order.getReceiver_name()+" "+order.getReceiver_phone()+" "+StringUtil.StringFilter(order.getReceiver_adress());
        List<String> address_arr = StringUtil.cut_str(address);
        for(String val:address_arr){
            sb.append("@@2"+val+"\r\n");
        }
        sb.append("\r\n");

        sb.append("@@2"+StringUtil.dispRepair("扫描二维码，关注菜大师"," ")+"\r\n");


        String winxin_no = DateUtil.dateToStr(order.getCreate_date(),"MMdd")
                +StringUtil.dispRepairLeft(String.valueOf(order.getStores_id()),"0",2)+
                order.getOrder_no().substring(5);
        String url = getWeixinUrl(winxin_no);
        sb.append("<q>"+url+"</q>");
        sb.append("\r\n");

        sb.append("<b>"+StringUtil.dispRepairLeft(winxin_no,"1",12)+"</b>");
        sb.append(StringUtil.dispRepair("生产过程控制专用"," ")+"\r\n\r\n");
        sb.append("*******菜大师社区中央厨房*******"+"\r\n\r\n");
        sb.append(StringUtil.dispRepair("纯净水洗菜的网络直播厨房","-")+"\r\n");//标语
        return sb.toString();
    }*/



    /**
     * 生成网络打印机小票打印模板
     * @param order
     * @return
     */
    private String GetPrintContent(CdsOrderInfo order){
        CdsStores cdsStores = storesService.GetStores(order.getStores_id());
        StringBuffer sb = new StringBuffer("");

        sb.append(StringUtil.dispRepair(cdsStores.getName(),"*")+"\r\n\r\n");
        sb.append("@@2"+StringUtil.dispRepair("#"+order.getOrder_no().substring(5)+" 订单"," ")+"\r\n\r\n");

        String fromin_name = "";
        if(order.getFromin_name()!=null && !order.getFromin_name().equals("")){
            fromin_name = " " + order.getFromin_name();
        }

        if(order.getOrder_type()==2){//店堂就餐
            sb.append("@@2"+StringUtil.dispRepairCenter(order.getFromin()+fromin_name+"#"+order.getFromin_no(),"店堂就餐"," ")+"\r\n");
        }else{
            sb.append("@@2"+order.getFromin()+fromin_name+"#"+order.getFromin_no()+"\r\n");
        }

        if(!order.getFromin().equals("微信")){
            sb.append("订单号:"+order.getOrder_desc()+"\r\n");
        }
        sb.append("下单时间:"+ DateUtil.dateToStr(order.getCreate_date(),"yyyy-MM-dd HH:mm")+"\r\n");
        sb.append("--------------------------------"+ "\r\n");
        if(order.getGoods()!=null && !order.getGoods().equals("")){
            List<CdsGood> list =  JSONArray.parseArray(order.getGoods(),CdsGood.class);
            for (CdsGood good:list){
                sb.append(formatGoodName(good));
            }
        }
        sb.append("\r\n\r\n");
        DecimalFormat df1 = new DecimalFormat("0.00");
        if(order.getBox_price()>0){
            sb.append(StringUtil.dispRepairCenter("餐盒费",df1.format(order.getBox_price())," ")+"\r\n");
        }
        if(order.getShip_fee()>0){
            sb.append(StringUtil.dispRepairCenter("配送费",df1.format(order.getShip_fee())," ")+"\r\n");
        }

        if(order.getUc_price()>0){
            sb.append(StringUtil.dispRepairCenter("红  包",df1.format(order.getUc_price())," ")+"\r\n");
        }

        if(order.getDeduction_price()!= 0){
            sb.append(StringUtil.dispRepairCenter("订单优惠",df1.format(order.getDeduction_price())," ")+"\r\n");
        }

        sb.append("--------------------------------"+ "\r\n");

        sb.append(StringUtil.dispRepairCenter("[合计]",df1.format(order.getTotal_price())," ")+"\r\n");
        if(order.getPay_type_id()==3){
            sb.append("@@2付款方式：货到付款"+"\r\n\r\n");
        }
        //service_time_str
        if(order.getService_time_str()!=null&&!order.getService_time_str().equals("及时送达")){
            sb.append("@@2预订单，送达时间："+order.getService_time_str()+"\r\n");
        }

        String member_desc = "";
        if(order.getMember_desc()!=null && !order.getMember_desc().equals("")){
            //member_desc = order.getMember_desc().replaceAll("‼","");
            member_desc = StringUtil.StringFilter(order.getMember_desc());
            sb.append("\r\n\r\n");
            try {
                if(member_desc.getBytes("GBK").length >32){
                    List<String> gmember_desc_arr = StringUtil.cut_str(member_desc);

                    for(int i=0;i<gmember_desc_arr.size();i++){
                        sb.append("@@2"+gmember_desc_arr.get(i)+"\r\n");
                    }
                }else{
                    sb.append("@@2"+member_desc+"\r\n");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


        }

        sb.append("--------------------------------"+ "\r\n");

        //收货地址格式
        // String address = order.getReceiver_name()+" "+order.getReceiver_phone()+" "+order.getReceiver_adress();
        String address = "";
        if(order.getOrder_type()==2){
            address = StringUtil.StringFilter(order.getReceiver_name())+" "+StringUtil.StringFilter(order.getReceiver_phone());
        }else{
            address = StringUtil.StringFilter(order.getReceiver_name())+" "+StringUtil.StringFilter(order.getReceiver_phone())+" "+StringUtil.StringFilter(order.getReceiver_adress());
        }
        List<String> address_arr = StringUtil.cut_str(address);
        for(String val:address_arr){
            sb.append("@@2"+val+"\r\n");
        }
        sb.append("\r\n");

        sb.append("@@2"+StringUtil.dispRepair("扫描二维码，关注菜大师"," ")+"\r\n");


        String winxin_no = DateUtil.dateToStr(order.getCreate_date(),"MMdd")
                +StringUtil.dispRepairLeft(String.valueOf(order.getStores_id()),"0",2)+
                order.getOrder_no().substring(5);
        String url = getWeixinUrl(winxin_no,order.getBrand_id());//TODO 改动2017/06/13
        sb.append("<q>"+url+"</q>");
        sb.append("\r\n");

        sb.append("<b>"+StringUtil.dispRepairLeft(winxin_no,"1",12)+"</b>");
        sb.append(StringUtil.dispRepair("生产过程控制专用"," ")+"\r\n\r\n");
        sb.append("*******菜大师社区中央厨房*******"+"\r\n\r\n");
        sb.append(StringUtil.dispRepair("纯净水洗菜的网络直播厨房","-")+"\r\n");//标语
        return sb.toString();
    }


    /**
     * 客户小票-格式化打印商品名称格式
     */
    private String formatGoodName(CdsGood good){
        String result_str = "";
        try {
            String good_name = good.getGood_name().replaceAll("☆","");
            if(good.getSpec()!=null&&!good.getSpec().equals("")){
                good_name+="("+good.getSpec()+")";
            }
            DecimalFormat df1 = new DecimalFormat("0.00");
            String good_count= String.valueOf(good.getQuantity());
            String good_price =  df1.format(good.getPrice());

            good_price = "x"+StringUtil.dispRepairRight(good_count," ",2)+" "+StringUtil.dispRepairLeft(good_price," ",5);

            String msg = " ";
            if(good.getQuantity()>1){
                msg="-";
            }

            if(good_name.getBytes("GBK").length >22){
                List<String> good_name_arr = StringUtil.cut_str(good_name);

                for(int i=0;i<good_name_arr.size()-1;i++){
                    result_str +="@@2"+StringUtil.dispRepairRight(good_name_arr.get(i),msg)+"\r\n";
                }
                if(good_name_arr.get(good_name_arr.size()-1).getBytes("GBK").length>22){

                    result_str+="@@2"+StringUtil.dispRepairRight(good_name_arr.get(good_name_arr.size()-1),msg)+"\r\n";
                    result_str+="@@2"+StringUtil.dispRepairLeft(good_price,msg)+"\r\n";
                }else{
                    result_str+= "@@2"+StringUtil.dispRepairCenter(good_name_arr.get(good_name_arr.size()-1),good_price,msg)+"\r\n";
                }
            }else{
                result_str+= "@@2"+StringUtil.dispRepairCenter(good_name,good_price,msg)+"\r\n";
            }

       } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return result_str;
    }

    public String getWeixinUrl(String order_id,Integer brand_id){
        CdsBrand brand = new CdsBrand();
        brand.setBrand_id(brand_id);
        brand.addConditionField("brand_id");
        brand.addParamFields();
        brand = sqlDao.getRecord(brand);
        SystemParamWeixin wxParams = new SystemParamWeixin(brand.getWeixin_appid(),brand.getWeixin_appsecret());

        String result = "";
        String lock_key = "weixin_accesstoken_no_lock"+brand_id;
        String accesstoken_key = "weixin_accesstoken"+brand_id;
        /*while (!lock(lock_key)){ //判断是否加锁,如已加锁，则等待1秒钟
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/

        String accesstoken = getRedisString(accesstoken_key);

        if(accesstoken==null){

            accesstoken = APIFactoryWeixin.getPoiAPI().accessToken(wxParams);
            addRedis(accesstoken_key,accesstoken,60*60);
        }
        unlock(lock_key);
        if(!accesstoken.equals("")){
            result = APIFactoryWeixin.getPoiAPI().createcode(accesstoken,order_id);
        }
        return result;
    }


}
