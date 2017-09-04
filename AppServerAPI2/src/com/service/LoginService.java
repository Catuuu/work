package com.service;


import com.framework.mapping.system.*;
import com.framework.resource.action.ButtonAction;
import com.framework.resource.action.MenuAction;
import com.framework.resource.rule.RuleItem;
import com.framework.service.BasicService;
import com.framework.system.SystemConstant;
import com.framework.util.BeanUtil;
import com.framework.util.StringUtil;
import com.framework.util.WebUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.framework.system.SystemConstant.*;

/**
 * Created by c on 2017-01-30.
 */
@Service("loginService")
public class LoginService extends BasicService {
    /**
     * 用户登录验证
     *
     * @param cdsUsers
     * @return
     * @throws Exception
     */
    public int userCheckLogin(CdsUsers cdsUsers) throws Exception {

        String  password = "###" + StringUtil.MD5(StringUtil.MD5(SystemConstant.passKey+cdsUsers.getUser_pass()));

        cdsUsers.addConditionField("user_login");
        cdsUsers.addParamField("id,user_login,user_pass,user_nicename,stores_id");
        CdsUsers result = sqlDao.getRecord(cdsUsers);

        if (result == null) {            //此用户不存在
            return 2;
        }
        if (result.getUser_status()==0) { //用户已禁用
            return 6;
        }
        if (!result.getUser_pass().equalsIgnoreCase(password)) { //密码错误
            return 7;
        }
        return 1;
    }

    /**
     * 加载登录用户的角色
     *
     * @param sysUser
     * @return
     * @throws Exception
     */
    public List<SysRole> loadUserRole(SysUser sysUser) throws Exception {
        List<SysRole> sysRoleList = sqlDao.getRecordList("sys_role.getUserRole", sysUser);
        return sysRoleList;
    }

    /**
     * 加载用户功能信息
     *
     * @param sysUserRole
     * @return
     * @throws Exception
     */
    public Map<String, String> loadUserOperation(List<SysRole> sysUserRole) throws Exception {
        Map params = new HashMap();
        params.put("lst", sysUserRole);
        List<SysRoleOperation> sysRoleOperationList = sqlDao.getRecordList("sys_role_operation.getUserOperation", params);

        Map<String, String> operMap = new HashMap<String, String>();
        if (null != sysRoleOperationList) {
            for (SysRoleOperation sro : sysRoleOperationList) {
                operMap.put(sro.getSro_action_id(), sro.getSro_menu_id());
            }
        }
        return operMap;
    }


    /**
     * 加载登录用户的菜单
     *

     * @return
     * @throws Exception
     */
    public List<Map> loadUserMenu(int parentid, CdsUsers cdsUsers,int lev) throws Exception {
        Map paramMap = new HashMap();
        paramMap.put("parentid",parentid);
        paramMap.put("user_id",cdsUsers.getId());
        List<Map> useMenus = sqlDao.getRecordList("cds_role_menu.getUserMenu",paramMap);

        List<Map> resultList = new ArrayList<Map>();
        //功能菜单集
        for (Map menu : useMenus) {
            int id = Integer.parseInt(menu.get("id").toString());
            if(lev==0){
                menu.put("childs",loadUserMenu(id,cdsUsers,1));
            }
            resultList.add(menu);
        }
        return resultList;
    }




    /**
     * 加载登录用户的菜单
     *
     * @param menuActions
     * @param roleOperations
     * @return
     * @throws Exception
     */
    public List<MenuAction> loadUserMenu(List<MenuAction> menuActions, Map<String, String> roleOperations) throws Exception {
        List<MenuAction> useMenu = new ArrayList<MenuAction>();
        //功能菜单集
        for (MenuAction menu : menuActions) {
            String menu_id = roleOperations.get(menu.getId());
            if (null != menu_id)
                useMenu.add(menu);
        }
        return useMenu;
    }

    /**
     * 加载登录用户的菜单按钮
     *
     * @param roleOperations
     * @return
     * @throws Exception
     */
    public Map<String, List<ButtonAction>> loadUserButton(Map<String, String> roleOperations) throws Exception {
        Map<String, List<ButtonAction>> useMenuButton = new HashMap<String, List<ButtonAction>>();
        //功能菜单集
        Map<String, List<ButtonAction>> buttonActions = WebUtil.getApplication(SYS_BUTTON_KEY);

        for (Map.Entry me : roleOperations.entrySet()) {
            String key = (String) me.getKey();
            List<ButtonAction> baList = buttonActions.get(key);
            if (null == baList)
                continue;
            List<ButtonAction> useBaList = new ArrayList<ButtonAction>();
            for (ButtonAction ba : baList) {
                String menu_id = roleOperations.get(ba.getId());
                if (null != menu_id)
                    useBaList.add(ba);
            }
            useMenuButton.put(key, useBaList);
        }
        return useMenuButton;
    }

    /**
     * 加载登录用户的规则
     *
     * @param sysUserRole
     * @return
     * @throws Exception
     */
    public Map loadUserRule(List<SysRole> sysUserRole) throws Exception {
        Map params = new HashMap();
        params.put("lst", sysUserRole);
        List<SysRoleRule> sysRoleRules = sqlDao.getRecordList("sys_role_rule.getUserRule", params);

        params.clear();
        if (null == sysRoleRules)
            return params;
        //循环用户规则
        for (SysRoleRule srr : sysRoleRules) {
            Map<String, List<RuleItem>> ruleMap = new HashMap();
            List<RuleItem> itemList = new ArrayList<RuleItem>();
            String menu_id = srr.getSrr_menu_id();
            String rule_key = srr.getSrr_rule_key();
            if (params.containsKey(menu_id)) ruleMap = (Map<String, List<RuleItem>>) params.get(menu_id);
            if (ruleMap.containsKey(rule_key)) itemList = (List<RuleItem>) ruleMap.get(rule_key);
            itemList.add(new RuleItem(srr.getSrr_rule_value(), ""));
            ruleMap.put(rule_key, itemList);
            params.put(menu_id, ruleMap);
        }
        return params;
    }

    /**
     * 登录回话清理
     */
    public void clearLoginSession() {
        WebUtil.removeSession(LOGIN_USER_KEY);
        WebUtil.removeSession(USER_ROLE_KEY);
        WebUtil.removeSession(USER_RULE_KEY);
        WebUtil.removeSession(USER_OPERATIONS_KEY);
        WebUtil.removeSession(USER_MENU_KEY);
        WebUtil.removeSession(USER_BUTTON_KEY);
    }
}
