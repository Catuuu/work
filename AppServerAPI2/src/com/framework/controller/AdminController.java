package com.framework.controller;

import com.framework.mapping.system.SysMenuDefine;
import com.framework.mapping.system.SysMenuMapping;
import com.framework.resource.action.ButtonAction;
import com.framework.resource.action.MenuAction;
import com.framework.resource.interf.RuleInterface;
import com.framework.util.WebUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.framework.system.SystemConstant.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-12
 * Time: 上午9:49
 * To change this template use File | Settings | File Templates.
 */
public class AdminController extends BasicController {

    /**
     * 获取定义菜单集合
     *
     * @param nodeType 'bole','leaf'
     * @return
     * @throws Exception
     */
    protected List<SysMenuDefine> getMenuDefines(String nodeType) throws Exception {
        SysMenuDefine smd = new SysMenuDefine();
        smd.addOrderStr("smd_parentid,smd_order");
        List<SysMenuDefine> sysMenuDefines = sqlDao.getRecordList(smd);
        if (null != nodeType && null != sysMenuDefines) {
            for (SysMenuDefine smdef : sysMenuDefines) {
                smdef.setNodeType(nodeType);
            }
        }

        return sysMenuDefines;
    }

    /**
     * 获取功能菜单集合
     *
     * @param isAttr
     * @return
     * @throws Exception
     */
    protected List<MenuAction> getMenuActions(boolean isAttr) throws Exception {
        List<MenuAction> menuActions = new ArrayList<MenuAction>();

        SysMenuMapping sysMenuMapping = new SysMenuMapping();
        sysMenuMapping.addOrderStr("smd_id,smm_order");
        List<SysMenuMapping> sysMenuMappings = sqlDao.getRecordList(sysMenuMapping);

        Map<String, MenuAction> menuActionMap = WebUtil.getApplication(SYS_MENU_KEY);
        Map<String, List<ButtonAction>> buttonMap = WebUtil.getApplication(SYS_BUTTON_KEY);
        Map<String, List<RuleInterface>> ruleMap = WebUtil.getApplication(SYS_RULE_KEY);
        if (null != sysMenuMapping) {
            for (SysMenuMapping smm : sysMenuMappings) {
                String menu_id = smm.getSmm_menu_id();
                MenuAction menu = menuActionMap.get(menu_id);
                if (null == menu) continue;
                if (isAttr) {
                    menu = (MenuAction) menu.clone();
                    if (null != buttonMap)
                        menu.setButtonList(buttonMap.get(menu_id));
                    if (null != ruleMap)
                        menu.setRuleList(ruleMap.get(menu_id));
                }
                menu.setParentid(smm.getSmd_id());
                menu.setOrder(smm.getSmm_order());
                menu.setNodeType("leaf");
                menuActions.add(menu);
            }
        }
        return menuActions;
    }
}
