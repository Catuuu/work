package com.framework.system;

/**
 * 系统常量值.
 * User: chenbin
 * Date: 13-3-13
 * Time: 上午11:38
 * To change this template use File | Settings | File Templates.
 */
public class SystemConstant {
    /**
     * 系统名称
     */
    public static final String SYS_NAME_KEY = "systemName";

    /**
     * 后台登录用户信息在会话中放置的key
     */
    public static final String LOGIN_ADMIN_KEY = "loginAdmin";

    /**
     * 登录用户信息在会话中放置的key
     */
    public static final String LOGIN_USER_KEY = "loginUser";


    /**
     * 登录用户信息在会话中放置的key
     */
    public static final String LOGIN_USER_CODE = "loginUserCode";



    /**
     * 登录用户拥有的角色在会话中放置的key
     */
    public static final String USER_ROLE_KEY = "userRole";

    /**
     * 登录用户拥有的规则在会话中放置的key
     */
    public static final String USER_RULE_KEY = "userRule";

    /**
     * 登录用户拥有的功能在会话中放置的key
     */
    public static final String USER_OPERATIONS_KEY = "userOperation";

    /**
     * 登录用户拥有的菜单在会话中放置的key
     */
    public static final String USER_MENU_KEY = "userMenu";

    /**
     * 登录用户拥有的功能在会话中放置的key
     */
    public static final String USER_BUTTON_KEY = "userButton";



    /**
     * 系统所有的菜单
     */
    public static final String SYS_MENU_KEY = "systemMenu";

    /**
     * 系统所有的规则
     */
    public static final String SYS_RULE_KEY = "systemRule";

    /**
     * 系统所有的按钮
     */
    public static final String SYS_BUTTON_KEY = "systemButton";

    /**
     * 系统所有机构
     */
    public static final String SYS_ORG_KEY = "systemOrg";



    /**
     * 框架数据过滤规则
     */
    public static final String FRAME_RULE_KEY = "frameRule";

    /**
     * 框架按钮
     */
    public static final String FRAME_BUTTON_KEY = "frameButton";

    /**
     * 用户密码加密秘钥
     */
    public static  String passKey = "hwdwbLQzM2l2k9qHhO";

    /**
     * 业务实时数据，保存天数
     */
    public static int DATA_DAY = 3;



}
