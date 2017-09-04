package com.cheea.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.cheea.dao.CourseDao;
import com.cheea.dao.FailClassDao;
import com.cheea.dao.ReadyClassDao;
import com.cheea.entity.Course;
import com.cheea.entity.FailClass;
import com.cheea.entity.ReadyClass;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.factory.AutoObjectFactory;
import com.cheea.service.FailClassService;
import com.cheea.util.ClassRoles;
import com.cheea.util.ToString;
@ClassRoles(value=true)
public class FailClassServiceImpl implements FailClassService {

	@Override
	public List<FailClass> getAll() throws ServiceException, RutimeException,
			DataBaseException {
		FailClassDao dao=(FailClassDao)AutoObjectFactory.getInstance("FailClassDaoImpl");
		List<FailClass> list=dao.getAll();
		List<FailClass> l=new ArrayList<FailClass>(); 
		for(int i=0;i<list.size();i++){
			FailClass re=list.get(i);
			int t=Integer.parseInt(re.getTime().trim());
			String time=ToString.doString(t);
			re.setTime(time);
			l.add(re);
		}
		return l;
	}

	@Override
	public void updateFail(FailClass f) throws ServiceException,
			RutimeException, DataBaseException {
		FailClassDao dao=(FailClassDao)AutoObjectFactory.getInstance("FailClassDaoImpl");
		dao.update(f);
	}

}
