package com.controller;

import com.framework.annotation.ResourceMethod;
import com.framework.controller.AdminController;
import com.framework.exception.NotLoginException;
import com.framework.mapping.JsonMessage;
import com.framework.mapping.system.CdsStores;
import com.framework.mapping.system.CdsUsers;
import com.framework.mapping.system.SysUser;
import com.framework.system.SystemConstant;
import com.framework.system.SystemDictionary;
import com.framework.util.StringUtil;
import com.framework.util.WebUtil;
import com.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.framework.annotation.CheckType.NO_CHECK;
import static com.framework.system.SystemConstant.*;

/**
 * 登录控制器
 * User: chenbin
 * Date: 12-12-26
 * Time: 下午3:19
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("login")
public class LoginController extends AdminController {
    @Resource(name = "loginService")
    protected LoginService loginService;

    @RequestMapping("adminLogin")
    @ResourceMethod(name = "转发至管理员登陆界面", check = NO_CHECK)
    public String adminLogin() throws Exception {
        if (null != WebUtil.getSession(LOGIN_ADMIN_KEY)) return "systemManager/adminMain";
        else return "systemManager/adminLogin";
    }

    @RequestMapping("adminLoginVerify")
    @ResourceMethod(name = "后台登录验证", check = NO_CHECK)
    public String adminLoginVerify(SysUser sysUser, ModelMap modelMap) throws Exception {
        if (null != WebUtil.getSession(LOGIN_ADMIN_KEY)) return "systemManager/adminMain";
        if (sysUser.getSu_password() == null) return "systemManager/adminLogin";

        sysUser.setSu_password(StringUtil.MD5(sysUser.getSu_password().toUpperCase()));
        String pass = SystemDictionary.getInstance().getProperty(sysUser.getSu_code());
        if (pass == null) {
            modelMap.put("message", "用户名不存在");
            return "systemManager/adminLogin";
        } else if (!pass.equals(sysUser.getSu_password())) {
            modelMap.put("message", "密码不正确");
            return "systemManager/adminLogin";
        } else {
            WebUtil.setSession(LOGIN_ADMIN_KEY, sysUser);
            return "systemManager/adminMain";
        }
    }

    @RequestMapping("userLogin")
    @ResourceMethod(name = "转发至用户登陆界面", check = NO_CHECK)
    public String userLogin() throws Exception {

        if (null != WebUtil.getSession(LOGIN_USER_KEY)) {
            CdsStores shop = new CdsStores();
            shop.addParamFields("stores_id,name");
            List cdsshops = sqlDao.getRecordList(shop);

            WebUtil.setRequest("cdsshops",cdsshops);

            //WebUtil.getSession(LOGIN_USER_KEY);
            // CdsUsers cdsUsers = WebUtil.getUser();
            //加载用户对应功能信息
           /* List userMenus = loginService.loadUserMenu(351,cdsUsers,0);
            WebUtil.setRequest("userMenus",userMenus);*/
            return "systemManager/userMain";
        } else {
            return "systemManager/userLogin";
        }
    }

    @RequestMapping("userLoginOut")
    @ResourceMethod(name = "转发至用户登陆界面", check = NO_CHECK)
    public String userLoginOut() throws Exception {
        loginService.clearLoginSession();
        WebUtil.removeSession("stores_id");
        return "systemManager/userLogin";
    }

    @RequestMapping("userExitOut")
    @ResourceMethod(name = "注销用户所有信息", check = NO_CHECK)
    @ResponseBody
    public JsonMessage userExitOut() throws Exception {
        loginService.clearLoginSession();
        JsonMessage jsonMessage = new JsonMessage(1);
        return jsonMessage;
    }

    @RequestMapping("LoginVerify")
    @ResourceMethod(name = "门店用户登录验证", check = NO_CHECK)
    @ResponseBody
    public JsonMessage LoginVerify(String username, String pass, String ValidateCode, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        String sessionid = session.getId();
        // if (WebUtil.getSession().getAttribute(LOGIN_USER_CODE)==null || ValidateCode==null || !(WebUtil.getSession().getAttribute(LOGIN_USER_CODE).toString().equalsIgnoreCase(ValidateCode) ) )
        if (session.getAttribute(LOGIN_USER_CODE) == null || ValidateCode == null || !(session.getAttribute(LOGIN_USER_CODE).toString().equalsIgnoreCase(ValidateCode))) {
            return new JsonMessage(0, "验证码不正确！");
        }
        CdsUsers sysUser = new CdsUsers();
        sysUser.setUser_login(username);
        sysUser.setUser_pass(pass);


        String password = "###" + StringUtil.MD5(StringUtil.MD5(SystemConstant.passKey + sysUser.getUser_pass()));

        sysUser.addConditionField("user_login");
        sysUser.addParamField("id,user_login,user_pass,user_nicename,stores_id");
        CdsUsers cdsUsers = sqlDao.getRecord(sysUser);

        //List role = sqlDao.getRecordList("cds_stores_user.getRole", user_id);

        if (cdsUsers == null) {            //此用户不存在
            return new JsonMessage(0, "用户不存在！");
        }
        Integer user_id = cdsUsers.getId();
        if (cdsUsers.getUser_status() == 0) { //用户已禁用
            return new JsonMessage(0, "用户已被禁用！");
        }
        if (!cdsUsers.getUser_pass().equalsIgnoreCase(password)) { //密码错误
            return new JsonMessage(0, "密码不正确！");
        }

        List userMenus = loginService.loadUserMenu(351, cdsUsers, 0);//加载用户对应功能信息
        if (userMenus == null || userMenus.size() == 0) {
            return new JsonMessage(0, "该用户没有此权限，请联系管理员！");
        }

        WebUtil.setSession(LOGIN_USER_KEY, cdsUsers);
        WebUtil.setSession("userMenus", userMenus);

        CdsStores cdsStores = new CdsStores();
        cdsStores.setStores_id(cdsUsers.getStores_id());
        cdsStores.addConditionField("stores_id");
        cdsStores = sqlDao.getRecord(cdsStores);

        WebUtil.setSession(SYS_ORG_KEY, cdsStores);
        return new JsonMessage(1, "登录成功");
    }

    @RequestMapping("userLoginVerify")
    @ResourceMethod(name = "用户登录验证", check = NO_CHECK)
    public String userLoginVerify(CdsUsers sysUser, ModelMap modelMap) throws Exception {
        if (null != WebUtil.getSession(LOGIN_USER_KEY)) {
            CdsStores shop = new CdsStores();
            shop.addParamFields("stores_id,name");
            List cdsshops = sqlDao.getRecordList(shop);

            WebUtil.setRequest("cdsshops",cdsshops);
            return "systemManager/userMain";
        }
        //用户登录验证
        int status = loginService.userCheckLogin(sysUser);
        modelMap.put("status", status);
        if (1 == status) {
            WebUtil.setSession(LOGIN_USER_KEY, sysUser);
        }
        return "systemManager/userLogin";
    }

    @RequestMapping("isLogon")
    @ResourceMethod(name = "判断用户是否已经登录(datagrid,treegrid加载异常后判断调用)", check = NO_CHECK)
    @ResponseBody
    public JsonMessage isLogon() throws Exception {
        JsonMessage jsonMessage = new JsonMessage(1);
        if (null == WebUtil.getSession(LOGIN_USER_KEY))
            throw new NotLoginException("您未登录系统或者登录已超时,请重新登录");
        return jsonMessage;
    }
}
