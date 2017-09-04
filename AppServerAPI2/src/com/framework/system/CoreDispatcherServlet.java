package com.framework.system;

import com.framework.dao.SqlDao;
/*import com.framework.resource.interf.ButtonInterface;
import com.framework.resource.interf.RuleInterface;*/
import com.framework.util.BeanUtil;
import com.framework.util.TreeUtil;
import com.framework.util.WebUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.*;

import static com.framework.system.SystemConstant.*;

/**
 * 初始化springMVC及系统上下文参数
 * User: Administrator
 * Date: 12-12-29
 * Time: 下午6:14
 * To change this template use File | Settings | File Templates.
 */
public class CoreDispatcherServlet extends DispatcherServlet {
    private ServletContext application;

    @Override
    protected void onRefresh(ApplicationContext context) {
        //初始springMVC容器
        initStrategies(context);
        //初始化本地系统参数
        initSystem(context);
    }

    private void initSystem(ApplicationContext context) {
        try {
            this.application = getServletContext();
            SqlDao sqlDao = (SqlDao) context.getBean("sqlDao");
            JmsTemplate jmsTemplate = (JmsTemplate) context.getBean("jmsQueueTemplate");

            WebUtil.setSqlDao(sqlDao);
            WebUtil.setJmsTemplate(jmsTemplate);

            // 系统字典初始化
            SystemDictionary.getInstance().init(sqlDao);

            // 后台菜单初始化
         /*   initAdminMenu();

            // 系统机构
            application.setAttribute(SYS_ORG_KEY, sqlDao.getRecordList("sys_org.getSysOrgList"));*/

            //初始化系统图标样式
            //initSysICon();

            //初始化系统可用的数据规则
            /*initDataRule(context);

            //初始化系统可用的按钮
            initButtons(context);

            //系统资源解析
            ResourceResolving.resolving(context, application);*/

            //系统名称
            application.setAttribute(SYS_NAME_KEY, SystemDictionary.getInstance().getProperty("site_name"));

            //系统时间戳
            SystemDictionary.getInstance().setTimestamp(new Date().getTime());



            System.out.println("**************************************************");
            System.out.println("      " + SystemDictionary.getInstance().getProperty("site_name") + "初始化完成！");
            System.out.println("**************************************************");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("**************************************************");
            System.out.println("      " + "系统初始化过程中产生异常！");
            System.out.println("**************************************************");
        }
    }

   /* //后台菜单初始化
    private void initAdminMenu() throws Exception {
        String menuFile = application.getRealPath("/WEB-INF/sysMenu.xml");
        File file = new File(menuFile);
        if (file.exists()) {    //没有找到用户对应的配置文件　用户不存在
            SAXReader reader = new SAXReader();
            Document doc = null;
            try {
                doc = reader.read(file);
            } catch (DocumentException e) {
                throw new DocumentException("读取后台菜单错误:" + e);
            }
            Element root = doc.getRootElement();
            application.setAttribute("adminMenu", BeanUtil.toJsonString(TreeUtil.mapTreeFromDom4j(root)));
        } else {
            throw new FileNotFoundException("后台菜单文件\"" + menuFile + "\"不存在！");
        }
    }*/

    /**
     * 读取系统图标文件,添加至application
     *
     * @return
     * @throws Exception
     */
    private void initSysICon() throws Exception {
        String iconFilePath = application.getRealPath("/ui/icon.css");
        List<String> iconList;
        List<String> iconTextList;
        File file = new File(iconFilePath);
        iconTextList = new ArrayList<String>();
        iconList = new ArrayList<String>();
        LineNumberReader reader = null;
        try {
            reader = new LineNumberReader(new FileReader(file));
            String iconCss = "";
            String lineRead = "";
            //读取ICON文件中的所有样式定义
            while ((lineRead = reader.readLine()) != null) {
                int ch = 0;
                if (lineRead.startsWith(".")) {
                    iconCss = lineRead;
                } else if (lineRead.endsWith("}")) {
                    iconCss = iconCss + lineRead;
                    iconTextList.add(iconCss);
                } else {
                    iconCss = iconCss + lineRead;
                }
            }
            //提取样式名称
            for (String iconText : iconTextList) {
                int ch = 0;
                if (iconText.startsWith(".") && (ch = iconText.indexOf("{")) > 0) {
                    iconCss = iconText.substring(1, ch).trim();
                    if (!iconCss.equalsIgnoreCase("icon")) {
                        if (iconText.indexOf("background-position") > 0) {
                            iconCss = "icon " + iconCss;
                        }
                        iconList.add(iconCss);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("图标文件不存在:" + iconFilePath);
        } catch (IOException e) {
            throw new FileNotFoundException("图标文件读取产生异常:" + iconFilePath);
        }
        application.setAttribute("iconCssList", iconList);
    }

    /*//初始化数按钮
    private void initButtons(ApplicationContext context) throws Exception {
        Map<String, ButtonInterface> buttonMap = new HashMap<String, ButtonInterface>();
        Map buttons = context.getBeansOfType(ButtonInterface.class);
        for (Object obj : buttons.entrySet()) {
            Map.Entry me = (Map.Entry) obj;
            ButtonInterface bi = (ButtonInterface) me.getValue();
            buttonMap.put(bi.getName(), bi);
        }
        application.setAttribute(FRAME_BUTTON_KEY, buttonMap);
    }

    //初始化数据规则类
    private void initDataRule(ApplicationContext context) throws Exception {
        Map<String, RuleInterface> dataRuleMap = new HashMap<String, RuleInterface>();
        Map rules = context.getBeansOfType(RuleInterface.class);
        for (Object obj : rules.entrySet()) {
            Map.Entry me = (Map.Entry) obj;
            RuleInterface ri = (RuleInterface) me.getValue();
            dataRuleMap.put(ri.getName(), ri);
            dataRuleMap.put(ri.getKey(), ri);
        }
        application.setAttribute(FRAME_RULE_KEY, dataRuleMap);
    }*/
}
