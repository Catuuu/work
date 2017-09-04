package com.framework.controller;

import com.framework.mapping.PageInfo;
import com.framework.mapping.PageParam;
import com.framework.mapping.PageResult;
import com.framework.mapping.excel.ExcelFileExport;
import com.framework.mapping.system.CdsUsers;
import com.framework.mapping.system.SysUser;
import com.framework.util.BeanUtil;
import com.framework.util.WebUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import static com.framework.system.SystemConstant.LOGIN_USER_KEY;
import static com.framework.system.SystemConstant.SYS_ORG_KEY;

/**
 * 基本控制器
 * User: chenbin
 * Date: 13-1-8
 * Time: 下午5:23
 * To change this template use File | Settings | File Templates.
 */
public class BasicController extends BasicComponent {

    /**
     * 查询返回PageResult
     *
     * @param recordSqlID
     * @param formInfo
     * @throws Exception
     */
    public PageResult queryReturnPageResult(String recordSqlID, Map formInfo) throws Exception {
        return queryReturnPageResult(recordSqlID, null, formInfo);
    }

    /**
     * 查询返回PageResult
     *
     * @param recordSqlID
     * @param countSqlID
     * @param formInfo
     * @throws Exception
     */
    public PageResult queryReturnPageResult(String recordSqlID, String countSqlID, Map formInfo) throws Exception {
        PageInfo pageInfo = PageInfo.getPageInfo(recordSqlID, countSqlID, formInfo);

        PageParam pageParam = new PageParam();
        pageParam.setPageInfo(pageInfo);
        pageParam.setFormInfo(formInfo);
        pageParam.setLoginUser((CdsUsers) WebUtil.getSession(LOGIN_USER_KEY));

        List resultList = sqlDao.queryPageList(pageParam);

        PageResult pageResult = new PageResult();
        pageResult.setRows(resultList);
        pageResult.setTotal(pageInfo.getRecordCount());

        return pageResult;
    }

    /**
     * 分页方法
     *
     * @param recordSqlID
     * @param formInfo
     * @throws Exception
     */
    protected void queryAndResponsePage(String recordSqlID, Map formInfo) throws Exception {
        queryAndResponsePage(recordSqlID, null, formInfo);
    }

    /**
     * 分页方法
     *
     * @param recordSqlID
     * @param countSqlID
     * @param formInfo
     * @throws Exception
     */
    protected void queryAndResponsePage(String recordSqlID, String countSqlID, Map formInfo) throws Exception {
        String xh_return_type = (String) ((Map) formInfo).get("xh_return_type");
        if (xh_return_type == null) {
            responseJsonPage(recordSqlID, countSqlID, formInfo);
        } else if (xh_return_type.equalsIgnoreCase("excel")) {
            responseExcelPage(recordSqlID, countSqlID, formInfo);
        }
    }

    /**
     * 分页返回EXCEL
     *
     * @param recordSqlID
     * @param countSqlID
     * @param formInfo
     * @throws Exception
     */
    private void responseExcelPage(String recordSqlID, String countSqlID, Map formInfo) throws Exception {
        //创建导出excel文件线程
        ExcelFileExport fileExcel = new ExcelFileExport(sqlDao, recordSqlID, countSqlID, formInfo);
        String fileName = fileExcel.getExpfileName();
        //给web前台输出文件名称
        responseText("{\"fileName\":\"" + fileName + "\"}");
        //本地生成文件名
        fileExcel.createFile();
    }

    /**
     * 分页返回JSON格式
     *
     * @param recordSqlID
     * @param countSqlID
     * @param formInfo
     * @throws Exception
     */
    private void responseJsonPage(String recordSqlID, String countSqlID, Map formInfo) throws Exception {
        PageInfo pageInfo = PageInfo.getPageInfo(recordSqlID, countSqlID, formInfo);

        PageParam pageParam = new PageParam();
        pageParam.setPageInfo(pageInfo);
        pageParam.setFormInfo(formInfo);
        pageParam.setLoginUser((CdsUsers) WebUtil.getSession(LOGIN_USER_KEY));

        List resultList = sqlDao.queryPageList(pageParam);

        PageResult pageResult = new PageResult();
        pageResult.setRows(resultList);
        pageResult.setTotal(pageInfo.getRecordCount());

        responseJson(BeanUtil.toJsonString(pageResult));
    }

    /**
     * 响应文本
     *
     * @param text 输出文本
     * @throws IOException 异常
     */
    protected void responseText(String text) throws IOException {
        WebUtil.setRequest("returnValue",text);
        HttpServletResponse response = WebUtil.getResponse();
        PrintWriter out = null; // 输出对象
        try {
            response.reset();
            response.setContentType("text/html; charset=UTF-8");
            out = response.getWriter();
            out.write(text);
            out.flush();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 响应文本
     *
     * @param json 输出文本
     * @throws IOException 异常
     */
    protected void responseJson(String json) throws IOException {
        WebUtil.setRequest("returnValue",json);
        HttpServletResponse response = WebUtil.getResponse();
        PrintWriter out = null; // 输出对象
        try {
            response.reset();
            response.setContentType("application/json; charset=UTF-8");
            out = response.getWriter();
            out.write(json);
            out.flush();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 将字符串以xml形式输出到客户端
     *
     * @param xml 输出的xml字符串
     * @throws IOException 抛出异常
     */
    protected void responseXML(String xml) throws IOException {
        WebUtil.setRequest("returnValue",xml);
        HttpServletResponse response = WebUtil.getResponse();
        PrintWriter out = null; // 输出对象
        try {
            response.reset();
            response.setContentType("application/xml; charset=UTF-8");
            out = response.getWriter();
            out.write(xml);
            out.flush();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }


    /**
     * 初始化系统机构
     *
     * @throws Exception
     */
    public void initSysOrg() throws Exception {
        WebUtil.setApplication(SYS_ORG_KEY, sqlDao.getRecordList("sys_org.getSysOrgList"));
    }
}
