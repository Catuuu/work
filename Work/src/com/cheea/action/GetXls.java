package com.cheea.action;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.cheea.entity.ReadyClass;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.factory.AutoObjectFactory;
import com.cheea.service.ReadyClassService;
import com.cheea.util.ToString;

public class GetXls implements CommonAction {

	@Override
	public Object doService(HttpServletRequest request,
			HttpServletResponse response) throws ServiceException,
			RutimeException, DataBaseException {
		String type=request.getParameter("type");
		if("1".equals(type)){
			try {	
				String name=request.getParameter("name");
				if(name==null){
					return null;
				}
				OutputStream os = response.getOutputStream();//获得输出流
			    WritableWorkbook book  =  Workbook.createWorkbook(os);//创建工作簿
			    WritableSheet sheet=book.createSheet("课表",0);//创建工作表
			    
			    sheet.setColumnView(1, 30);
			    sheet.setColumnView(2, 30);
			    sheet.setColumnView(3, 30);
			    sheet.setColumnView(4, 30);
			    sheet.setColumnView(5, 30);
			    		    
			    
			    jxl.write.Label lab1 = new jxl.write.Label(0, 0, "");
			    jxl.write.Label lab2 = new jxl.write.Label(1, 0, "星期一");
			    jxl.write.Label lab3= new jxl.write.Label(2, 0, "星期二");
			    jxl.write.Label lab4 = new jxl.write.Label(3, 0, "星期三");
			    jxl.write.Label lab5 = new jxl.write.Label(4, 0, "星期四");
			    jxl.write.Label lab6 = new jxl.write.Label(5, 0, "星期五");
			    
			    sheet.addCell(lab1);
			    sheet.addCell(lab2);
			    sheet.addCell(lab3);
			    sheet.addCell(lab4);
			    sheet.addCell(lab5);
			    sheet.addCell(lab6);
			    
			    jxl.write.Label lab7 = new jxl.write.Label(0, 1, "第一节");
			    jxl.write.Label lab8 = new jxl.write.Label(0, 2, "第二节");
			    jxl.write.Label lab9 = new jxl.write.Label(0, 3, "第三节");
			    jxl.write.Label lab10 = new jxl.write.Label(0, 4, "第四节");
			    
			    sheet.addCell(lab7);
			    sheet.addCell(lab8);
			    sheet.addCell(lab9);
			    sheet.addCell(lab10);
			    
			    ReadyClassService service=(ReadyClassService) AutoObjectFactory.getInstance("ReadyClassServiceImpl");
				List<ReadyClass> list=service.searchPrint(name);
				for(int i=0;list!=null&&i<list.size();i++){
				    int number=Integer.parseInt(list.get(i).getTime().trim());//时间片
				    int x=number/10;//列
				    int y=number%10;//行
				    jxl.write.Label lab = new jxl.write.Label(x, y,list.get(i).getCourseName()+" "+list.get(i).getClassName()+" "+list.get(i).getTeacherName());
				    sheet.addCell(lab);
				}
				
				os.flush();
				book.write();
				book.close();
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			try {
				int t=Integer.parseInt(type.trim());
				String name=request.getParameter("name");
				ReadyClassService service=(ReadyClassService) AutoObjectFactory.getInstance("ReadyClassServiceImpl");
				List<ReadyClass> list=null;
				switch (t) {
		        case 2:
		        	list=service.searchWithTeacher(name);
					break;
		        case 3:
		        	list=service.searchWithClass(name);
					break;	
				default:
					break;
				}
				OutputStream os = response.getOutputStream();//获得输出流
			    WritableWorkbook book  =  Workbook.createWorkbook(os);//创建工作簿
			    WritableSheet sheet=book.createSheet("课表",0);//创建工作表
			    
			    sheet.setColumnView(2, 15);
			    sheet.setColumnView(5, 30);
			    
			    jxl.write.Label lab1 = new jxl.write.Label(0, 0, "序号");
			    jxl.write.Label lab2 = new jxl.write.Label(1, 0, "班级名字");
			    jxl.write.Label lab3= new jxl.write.Label(2, 0, "课程名字");
			    jxl.write.Label lab4 = new jxl.write.Label(3, 0, "教师名字");
			    jxl.write.Label lab5 = new jxl.write.Label(4, 0, "教室名字");
			    jxl.write.Label lab6 = new jxl.write.Label(5, 0, "时间");
			    
			    sheet.addCell(lab1);
			    sheet.addCell(lab2);
			    sheet.addCell(lab3);
			    sheet.addCell(lab4);
			    sheet.addCell(lab5);
			    sheet.addCell(lab6);
			    
				int j=0;
				for(int i=0;list!=null&&i<list.size();i++){
					j++;
					jxl.write.Number lab7 = new jxl.write.Number(0, j, j);
				    jxl.write.Label lab8 = new jxl.write.Label(1, j,list.get(i).getStudentName());
				    jxl.write.Label lab9= new jxl.write.Label(2, j, list.get(i).getCourseName());
				    jxl.write.Label lab10 = new jxl.write.Label(3, j,list.get(i).getTeacherName());
				    jxl.write.Label lab11 = new jxl.write.Label(4, j,list.get(i).getClassName());
				    //int number=Integer.parseInt(list.get(i).getTime().trim());//时间片
				    jxl.write.Label lab12 = new jxl.write.Label(5, j,list.get(i).getTime());
				    sheet.addCell(lab7);
				    sheet.addCell(lab8);
				    sheet.addCell(lab9);
				    sheet.addCell(lab10);
				    sheet.addCell(lab11);
				    sheet.addCell(lab12);
				}
				
				os.flush();
				book.write();
				book.close();
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}  	
}
