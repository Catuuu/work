package com.controller;

import com.MessageRunnable;
import com.alibaba.fastjson.JSONObject;
import com.framework.annotation.ResourceMethod;
import com.framework.controller.BasicController;
import com.framework.mapping.JsonMessage;
import com.framework.mapping.system.*;
import com.framework.system.SystemConstant;
import com.framework.util.*;
import com.opensdk.meituan.vo.SystemParamMeituan;
import com.service.OrderService;
import com.service.PrintService;
import com.service.StoresService;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

import static com.framework.annotation.CheckType.CHECK_LOGIN;
import static com.framework.annotation.CheckType.CHECK_UDESK;
import static com.framework.annotation.CheckType.NO_CHECK;
import static com.framework.system.SystemConstant.LOGIN_USER_KEY;
import static com.framework.system.SystemConstant.SYS_ORG_KEY;

/**
 * Created by c on 2017-02-02.
 */
@Controller
@RequestMapping("Udesk")
public class UdeskController extends BasicController {
    @Resource(name = "StoresService")
    protected StoresService storesService;

    @Resource(name = "PrintService")
    protected PrintService printService;

    @Resource(name = "OrderService")
    protected OrderService orderService;


    @RequestMapping(value = "orderlist", method = RequestMethod.GET)
    @ResourceMethod(name = "用户定单列表", check = CHECK_UDESK)
    public String doorderlist(HttpServletRequest request) {
        Map params = BeanUtil.createBean(request, HashMap.class);
        String agent_email = params.get("agent_email").toString();
        String mobile = params.get("mobile").toString();
        if (mobile.equals("")) {
            String openid = params.get("weixin_openid").toString();
            if (openid != null && !openid.equals("")){
                CdsMember cdsMember = new CdsMember();
                cdsMember.setOpenid(openid);
                cdsMember.addConditionField("openid");
                cdsMember  = sqlDao.getRecord(cdsMember);
                if(cdsMember!=null){
                    mobile = cdsMember.getPhone();
                }
            }

        }
        WebUtil.setRequest("mobile", mobile);
        WebUtil.setRequest("curday",DateUtil.getDate());
        WebUtil.setRequest("startday",DateUtil.getDate());
        //WebUtil.setRequest("startday",DateUtil.dateToStr(DateUtil.addDay(new Date(),-7)));

        //获取用户信息
        if (null == WebUtil.getSession(LOGIN_USER_KEY)){
            CdsUsers sysUser = new CdsUsers();
            sysUser.setCallid(agent_email);

            sysUser.addConditionField("callid");
            sysUser.addParamField("id,user_login,user_pass,user_nicename,stores_id");
            CdsUsers cdsUsers = sqlDao.getRecord(sysUser);

            if (cdsUsers != null) {            //此客服存在
                WebUtil.setSession(LOGIN_USER_KEY, cdsUsers);
                CdsStores cdsStores = new CdsStores();
                cdsStores.setStores_id(cdsUsers.getStores_id());
                cdsStores.addConditionField("stores_id");
                cdsStores = sqlDao.getRecord(cdsStores);

                WebUtil.setSession(SYS_ORG_KEY, cdsStores);
            }
        }
        return "udesk/orderlist";
    }
}
