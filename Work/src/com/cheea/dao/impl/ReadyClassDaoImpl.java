package com.cheea.dao.impl;

import java.util.Collections;

import java.util.List;

import com.cheea.dao.ReadyClassDao;
import com.cheea.entity.Class;
import com.cheea.entity.Course;
import com.cheea.entity.FailClass;
import com.cheea.entity.ReadyClass;
import com.cheea.entity.Student;
import com.cheea.entity.Teacher;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.util.ClassRoles;
import com.cheea.util.Debug;
import com.cheea.util.HibernateTemple;
/**
 * 核心算法已改进,一个老师可以教多门课
 * @author yintao
 *
 */
@ClassRoles(value=true)
public class ReadyClassDaoImpl implements ReadyClassDao {

	@Override
	public void doPai() throws DataBaseException {
		int[] a = { 11, 12, 13, 14, 21, 22, 23, 24, 31, 32, 33, 34, 41, 42, 43,
				44, 51, 52, 53, 54 };
		List<Integer> list=(List<Integer>) HibernateTemple.query("select s.sid from Student s group by className)", null);
		for(int k=0;k<list.size();k++){
			List<Course> l = (List<Course>) HibernateTemple.query(
					"from Course where sid=?", list.get(k));// 取出课程
			List<Student> cl = (List<Student>) HibernateTemple.query(
					"from Student where sid=?",list.get(k));// 取出班级
			Collections.shuffle(cl);// 打乱
			int size = l.size();
			if (size > 20&&l!=null) {
				for (int i = 0; i < size; i++) {
					if (i <= 20) {
						cl.get(i).setCourseId(l.get(i).getCid());// 放入课程
					} else {
						System.out.println("课程大于20门啦。。。。。");
					}
				}
				for (int j = 0; j < cl.size(); j++) {
					Student stu = cl.get(j);
					HibernateTemple.save(stu);// 保存
				}
				System.out.println("大于20门课程");
			} else if(l!=null){
				for (int i = 0; i < size; i++) {
					cl.get(i).setCourseId(l.get(i).getCid());// 放入课程
				}
				for (int j = 0; j < cl.size(); j++) {
					Student stu = cl.get(j);
					HibernateTemple.save(stu);// 保存
				}
			}
		}
        int success=0;
        int fail=0;
		for (int i = 0; i < a.length; i++) {
			List<Student> student = (List<Student>) HibernateTemple.query(
					"from Student where timeId=? and courseId <> 0", a[i]);// 取出班级
			Collections.sort(student);
			List<Class> c = (List<Class>) HibernateTemple.query("from Class",
					null);// 取出教室
			Collections.sort(c);
			int num=0;
			for (int j = 0; j < student.size(); j++) {// 班级人数
				if (num<=c.size()&&student.get(j).getNumber() <= c.get(num).getClassNumber()) {					
					int courseId=student.get(j).getCourseId();
					List<Course> course = (List<Course>) HibernateTemple.query("from Course where cid=?",courseId);// 取出班级课程
					List<Teacher> teacher = (List<Teacher>) HibernateTemple.query("from Teacher where courseId=?",courseId);// 取出教师	
					Debug.Print("教师id："+courseId);
					if(teacher!=null){//如果有老师
						boolean flag=true;//控制循环
						for(int z=0;flag==true&&z<teacher.size();z++){//老师可能不止一名
							//查询出老师在该事件点是否已经有课
							Debug.Print("老师姓名："+teacher.get(z).getName());
							Debug.Print("时间片："+a[i]);
							List<ReadyClass> list1=(List<ReadyClass>) HibernateTemple.query("from ReadyClass where teacherName=? and time=?)",teacher.get(z).getName(),a[i]+"");
							Debug.Print("list size:"+list1.size());
							if(z==teacher.size()-1){//如果是最后条记录
								Debug.Print("最后条记录");
								if(list1.size()==0){//如果老师空闲的话
									ReadyClass r = ReadyClass.newInstance();
									r.setStudentName(student.get(j).getClassName());//班级名字
									r.setTime(a[i] + "");//时间片
									r.setCourseName(course.get(0).getName());//课程名
									r.setClassName(c.get(num).getClassName());//教室名字
									r.setTeacherName(teacher.get(z).getName());//教师名字
									num++;//计数自增
									success++;
									HibernateTemple.save(r);
									flag=false;//关闭循环
								}else{//最后个老师也不满足的话
									System.out.println("一个班级未找到教室！老师已有课！");
				                    FailClass r1 = FailClass.newInstance();
									r1.setStudentName(student.get(j).getClassName());//班级名字
									r1.setTime(a[i] + "");//时间片
									int courseId1=student.get(j).getCourseId();
									List<Course> course1 = (List<Course>) HibernateTemple.query("from Course where cid=?",courseId1);// 取出课程
									r1.setCourseName(course1.get(0).getName());//课程名
									fail++;
									HibernateTemple.save(r1);
								}
							}else{
								if(list1==null){
									ReadyClass r = ReadyClass.newInstance();
									r.setStudentName(student.get(j).getClassName());//班级名字
									r.setTime(a[i] + "");//时间片
									r.setCourseName(course.get(0).getName());//课程名
									r.setClassName(c.get(j).getClassName());//教室名字
									r.setTeacherName(teacher.get(z).getName());//教师名字
									success++;
									HibernateTemple.save(r);
									flag=false;//关闭循环
								}
							}
						}
					}else{//如果这门课没有老师
						System.out.println("一个班级未找到教室！这门课没有老师！");
	                    FailClass r1 = FailClass.newInstance();
						r1.setStudentName(student.get(j).getClassName());//班级名字
						r1.setTime(a[i] + "");//时间片
						int courseId1=student.get(j).getCourseId();
						List<Course> course1 = (List<Course>) HibernateTemple.query("from Course where cid=?",courseId1);// 取出课程
						r1.setCourseName(course1.get(0).getName());//课程名
						fail++;
						HibernateTemple.save(r1);
					}
				} else {
                    System.out.println("一个班级未找到教室！人数不对！");
                    FailClass r = FailClass.newInstance();
					r.setStudentName(student.get(j).getClassName());//班级名字
					r.setTime(a[i] + "");//时间片
					int courseId=student.get(j).getCourseId();
					List<Course> course = (List<Course>) HibernateTemple.query("from Course where cid=?",courseId);// 取出课程
					r.setCourseName(course.get(0).getName());//课程名
					fail++;
					HibernateTemple.save(r);
				}
			}
		}
		
		HibernateTemple.deleteAll("update Data set number=? where id=1" ,success);//图表数据
		HibernateTemple.deleteAll("update Data set number=? where id=2" ,fail);
	}

	@Override
	public void doClean() throws DataBaseException {
		HibernateTemple.deleteAll("Delete FROM ReadyClass" ,null);
		HibernateTemple.deleteAll("Delete FROM FailClass" ,null);
		HibernateTemple.deleteAll("update Student set courseId=0" ,null);
		HibernateTemple.deleteAll("update Data set number=0" ,null);
	}

	@Override
	public List<ReadyClass> getAll() throws DataBaseException, RutimeException {
		List<ReadyClass> list=(List<ReadyClass>) HibernateTemple.query("from ReadyClass order by studentName)", null);
		return list;
	}

	@Override
	public List<ReadyClass> doFind(String name) throws DataBaseException, RutimeException {
		List<ReadyClass> list=(List<ReadyClass>) HibernateTemple.query("from ReadyClass where studentName=?)", name);
		return list;	
	}

	@Override
	public List<ReadyClass> doFindWithTeacher(String name)
			throws DataBaseException, RutimeException {
		List<ReadyClass> list=(List<ReadyClass>) HibernateTemple.query("from ReadyClass where teacherName=?)", name);
		return list;
	}

	@Override
	public List<ReadyClass> doFindWithClass(String name)
			throws DataBaseException, RutimeException {
		List<ReadyClass> list=(List<ReadyClass>) HibernateTemple.query("from ReadyClass where className=?)", name);
		return list;
	}

	@Override
	public void update(ReadyClass f) throws DataBaseException, RutimeException {
		List<?> u=HibernateTemple.query("from ReadyClass where id=?",f.getId());
		ReadyClass fail=(ReadyClass) u.get(0);
		fail.setClassName(f.getClassName());
		fail.setCourseName(f.getCourseName());
		fail.setStudentName(f.getStudentName());
		fail.setTeacherName(f.getTeacherName());
		fail.setTime(f.getTime());
		HibernateTemple.update(fail);	
	}

}
