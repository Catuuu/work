package com.framework.controller;

import com.framework.annotation.CheckType;
import com.framework.annotation.ResourceMethod;
import com.framework.file.FileDownLoad;
import com.framework.file.FileDownLoadImpl;
import com.framework.mapping.excel.ExcelFileExport;
import com.framework.util.FileUtil;
import com.framework.util.WebUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Map;


/**
 * Excel导出控制器.
 * User: Administrator
 * Date: 13-1-10
 * Time: 下午5:13
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("emp")
public class ExpController extends AdminController {

    @RequestMapping("getCreateProgress")
    @ResourceMethod(name = "得到创建Excel的进度", check = CheckType.NO_CHECK)
    public void getCreateProgress() throws Exception {
        HttpServletRequest request = WebUtil.getRequest();
        String expfileName = request.getParameter("expfileName");//系统自动生成的文件名
        String pValue = WebUtil.getSession("p" + expfileName);
        responseText(pValue);
    }

    @RequestMapping("downFile")
    @ResourceMethod(name = "按照文件名称下载文件", check = CheckType.NO_CHECK)
    public void downFile() throws Exception {
        HttpServletResponse response = WebUtil.getResponse();
        HttpServletRequest request = WebUtil.getRequest();
        String expfileName = request.getParameter("expfileName");//系统自动生成的文件名
        String fileName = WebUtil.getSession("f" + expfileName);//导出时的文件名
        String path = WebUtil.getServletContext().getRealPath("/") + "WEB-INF\\download\\";
        FileDownLoad down = new FileDownLoadImpl(response);
        File file = new File(path + expfileName + ".xls ");
        down.write(file, fileName + ".xls");
        WebUtil.removeSession("f" + expfileName);
        WebUtil.removeSession("p" + expfileName);
        file.delete();
        FileUtil.delFile(path);
    }

    /**
     * easyui datagrid 当前页导出EXCEL
     *
     * @param formInfo
     * @throws Exception
     */
    @RequestMapping("expCurrentPage")
    @ResourceMethod(name = "导出当前页", check = CheckType.NO_CHECK)
    private void responseExcelPage(@RequestParam Map formInfo) throws Exception {
        //创建导出excel文件线程
        ExcelFileExport fileExcel = new ExcelFileExport(formInfo);
        String fileName = fileExcel.getExpfileName();
        //给web前台输出文件名称
        responseText("{\"fileName\":\"" + fileName + "\"}");
        //开始创建Excel文件
        fileExcel.createFile();
    }
}
