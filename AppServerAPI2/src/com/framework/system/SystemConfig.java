package com.framework.system;

import com.framework.util.BeanUtil;
import com.framework.util.TreeUtil;
import com.framework.util.WebUtil;
import com.opensdk.dianwoda.vo.SystemParamDianWoDa;
import com.opensdk.eleme.vo.SystemParamEleme;
import com.opensdk.eleme2.config.Config;
import com.opensdk.gaode.vo.SystemParamGaoDe;
import com.opensdk.meituan.vo.SystemParamMeituan;
import com.opensdk.shenhou.vo.SystemParamShenhou;
import com.opensdk.weixin.vo.SystemParamWeixin;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/3.
 */
public class SystemConfig {
    //饿了么秘钥
    private static SystemParamEleme sysPramEleme;

    public static SystemParamEleme GetSystemParamEleme(){
        if(sysPramEleme==null){
            if(!loadConfigInfo()){
                sysPramEleme = new SystemParamEleme("0845014911","d54f311f145a99c7a619e65b93ea77892ae5ac92");
            }
        }
        return sysPramEleme;
    }

    //饿了么秘钥2.0
    private static Config elemeConfig;
    public static Config GetEleme2Config(){
        if(elemeConfig==null){
            if(!loadConfigInfo()){
                elemeConfig = new Config(true,"GqAq6BjUpp","10070e73654bd33d418ad0ee7d7f9a0442744342");
            }
        }
        return elemeConfig;
    }

    //美团秘钥
    private static SystemParamMeituan sysPramMeituan;
    public static SystemParamMeituan GetSystemParamMeituan(){
        if (sysPramMeituan==null){
            if(!loadConfigInfo()) {
                sysPramMeituan = new SystemParamMeituan("483", "a4afb2e41caad32336832a694509152b");
            }
        }
        return sysPramMeituan;
    }

    //微信秘钥
    private static SystemParamWeixin sysPramWeixin;
    public static SystemParamWeixin GetSystemParamWeixin(){
        if (sysPramWeixin==null){
            if(!loadConfigInfo()) {
                sysPramWeixin = new SystemParamWeixin("wxb1366cee9583ba3a", "692f6f246bf84dc408f66d555fea300b");
            }
        }
        return sysPramWeixin;
    }

    //生活半径秘钥
    private static SystemParamShenhou sysPramShenhou;
    public static SystemParamShenhou GetSystemParamShenhou(){
        if(sysPramShenhou==null){
            if(!loadConfigInfo()) {
                sysPramShenhou = new SystemParamShenhou("1467561620160869", "59004d2352fce8cb6b9ab517f2c82799", 1);
            }
        }
        return sysPramShenhou;
    }


    //点我达秘钥
    private static SystemParamDianWoDa sysPramDianWoDa;
    public static SystemParamDianWoDa GetSystemParamDianWoDa(){
        if(sysPramDianWoDa==null){
            if(!loadConfigInfo()) {
                sysPramDianWoDa = new SystemParamDianWoDa("10062", "d7e9f4f5d10602161ec8ccc6f4c51248", 1);
            }
        }
        return sysPramDianWoDa;
    }

    //高德key
    private static SystemParamGaoDe systemParamGaoDe;
    public static SystemParamGaoDe GetSystemParamGaoDe(){
        if(systemParamGaoDe==null){
            if(!loadConfigInfo()) {
                systemParamGaoDe = new SystemParamGaoDe("5b30e5f159c008aeb008e09a99ccbda4");
            }
        }
        return systemParamGaoDe;
    }

    private static boolean loadConfigInfo(){
        boolean flag = false;
        Element em = GetConfigInfo();
        if(em!=null) {
            List<Element> elements = em.elements();
            for (Element element : elements) {
                String typeid = element.attributeValue("id");
                String key = element.attributeValue("key");
                String secret = element.attributeValue("secret");
                String type = element.attributeValue("type");
                if(typeid.equals("eleme")){
                    sysPramEleme = new SystemParamEleme(key,secret);
                }else if(typeid.equals("eleme2")){
                    if(type.equals("true")){
                        elemeConfig = new Config(true,key,secret);
                    }else{
                        elemeConfig = new Config(false,key,secret);
                    }
                }else if(typeid.equals("meituan")){
                    sysPramMeituan = new SystemParamMeituan(key,secret);
                }else if(typeid.equals("weixin")){
                    sysPramWeixin = new SystemParamWeixin(key,secret);
                }else if(typeid.equals("shenhou")){
                    sysPramShenhou = new SystemParamShenhou(key,secret,Integer.parseInt(type));
                }else if(typeid.equals("dianwoda")){
                    sysPramDianWoDa = new SystemParamDianWoDa(key,secret,Integer.parseInt(type));
                }else if(typeid.equals("gaode")){
                    systemParamGaoDe = new SystemParamGaoDe(key);
                }

            }
            flag = true;
        }
        return flag;
    }

    /**
     * 读取配置文件
     * @return
     */
    private static Element GetConfigInfo(){

        String path = new SystemConfig().getClass().getResource("/").getPath().replace("classes/","");
        Element root = null;
        String menuFile = path + "sysConfig.xml";
        File file = new File(menuFile);
        if (file.exists()) {    //没有找到用户对应的配置文件　用户不存在
            SAXReader reader = new SAXReader();
            Document doc = null;
            try {
                doc = reader.read(file);
            } catch (DocumentException e) {
            }
            root = doc.getRootElement();
        }
        return root;
    }


}
