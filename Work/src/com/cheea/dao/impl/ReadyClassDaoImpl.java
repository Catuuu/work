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
 * �����㷨�ѸĽ�,һ����ʦ���Խ̶��ſ�
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
					"from Course where sid=?", list.get(k));// ȡ���γ�
			List<Student> cl = (List<Student>) HibernateTemple.query(
					"from Student where sid=?",list.get(k));// ȡ���༶
			Collections.shuffle(cl);// ����
			int size = l.size();
			if (size > 20&&l!=null) {
				for (int i = 0; i < size; i++) {
					if (i <= 20) {
						cl.get(i).setCourseId(l.get(i).getCid());// ����γ�
					} else {
						System.out.println("�γ̴���20��������������");
					}
				}
				for (int j = 0; j < cl.size(); j++) {
					Student stu = cl.get(j);
					HibernateTemple.save(stu);// ����
				}
				System.out.println("����20�ſγ�");
			} else if(l!=null){
				for (int i = 0; i < size; i++) {
					cl.get(i).setCourseId(l.get(i).getCid());// ����γ�
				}
				for (int j = 0; j < cl.size(); j++) {
					Student stu = cl.get(j);
					HibernateTemple.save(stu);// ����
				}
			}
		}
        int success=0;
        int fail=0;
		for (int i = 0; i < a.length; i++) {
			List<Student> student = (List<Student>) HibernateTemple.query(
					"from Student where timeId=? and courseId <> 0", a[i]);// ȡ���༶
			Collections.sort(student);
			List<Class> c = (List<Class>) HibernateTemple.query("from Class",
					null);// ȡ������
			Collections.sort(c);
			int num=0;
			for (int j = 0; j < student.size(); j++) {// �༶����
				if (num<=c.size()&&student.get(j).getNumber() <= c.get(num).getClassNumber()) {					
					int courseId=student.get(j).getCourseId();
					List<Course> course = (List<Course>) HibernateTemple.query("from Course where cid=?",courseId);// ȡ���༶�γ�
					List<Teacher> teacher = (List<Teacher>) HibernateTemple.query("from Teacher where courseId=?",courseId);// ȡ����ʦ	
					Debug.Print("��ʦid��"+courseId);
					if(teacher!=null){//�������ʦ
						boolean flag=true;//����ѭ��
						for(int z=0;flag==true&&z<teacher.size();z++){//��ʦ���ܲ�ֹһ��
							//��ѯ����ʦ�ڸ��¼����Ƿ��Ѿ��п�
							Debug.Print("��ʦ������"+teacher.get(z).getName());
							Debug.Print("ʱ��Ƭ��"+a[i]);
							List<ReadyClass> list1=(List<ReadyClass>) HibernateTemple.query("from ReadyClass where teacherName=? and time=?)",teacher.get(z).getName(),a[i]+"");
							Debug.Print("list size:"+list1.size());
							if(z==teacher.size()-1){//������������¼
								Debug.Print("�������¼");
								if(list1.size()==0){//�����ʦ���еĻ�
									ReadyClass r = ReadyClass.newInstance();
									r.setStudentName(student.get(j).getClassName());//�༶����
									r.setTime(a[i] + "");//ʱ��Ƭ
									r.setCourseName(course.get(0).getName());//�γ���
									r.setClassName(c.get(num).getClassName());//��������
									r.setTeacherName(teacher.get(z).getName());//��ʦ����
									num++;//��������
									success++;
									HibernateTemple.save(r);
									flag=false;//�ر�ѭ��
								}else{//������ʦҲ������Ļ�
									System.out.println("һ���༶δ�ҵ����ң���ʦ���пΣ�");
				                    FailClass r1 = FailClass.newInstance();
									r1.setStudentName(student.get(j).getClassName());//�༶����
									r1.setTime(a[i] + "");//ʱ��Ƭ
									int courseId1=student.get(j).getCourseId();
									List<Course> course1 = (List<Course>) HibernateTemple.query("from Course where cid=?",courseId1);// ȡ���γ�
									r1.setCourseName(course1.get(0).getName());//�γ���
									fail++;
									HibernateTemple.save(r1);
								}
							}else{
								if(list1==null){
									ReadyClass r = ReadyClass.newInstance();
									r.setStudentName(student.get(j).getClassName());//�༶����
									r.setTime(a[i] + "");//ʱ��Ƭ
									r.setCourseName(course.get(0).getName());//�γ���
									r.setClassName(c.get(j).getClassName());//��������
									r.setTeacherName(teacher.get(z).getName());//��ʦ����
									success++;
									HibernateTemple.save(r);
									flag=false;//�ر�ѭ��
								}
							}
						}
					}else{//������ſ�û����ʦ
						System.out.println("һ���༶δ�ҵ����ң����ſ�û����ʦ��");
	                    FailClass r1 = FailClass.newInstance();
						r1.setStudentName(student.get(j).getClassName());//�༶����
						r1.setTime(a[i] + "");//ʱ��Ƭ
						int courseId1=student.get(j).getCourseId();
						List<Course> course1 = (List<Course>) HibernateTemple.query("from Course where cid=?",courseId1);// ȡ���γ�
						r1.setCourseName(course1.get(0).getName());//�γ���
						fail++;
						HibernateTemple.save(r1);
					}
				} else {
                    System.out.println("һ���༶δ�ҵ����ң��������ԣ�");
                    FailClass r = FailClass.newInstance();
					r.setStudentName(student.get(j).getClassName());//�༶����
					r.setTime(a[i] + "");//ʱ��Ƭ
					int courseId=student.get(j).getCourseId();
					List<Course> course = (List<Course>) HibernateTemple.query("from Course where cid=?",courseId);// ȡ���γ�
					r.setCourseName(course.get(0).getName());//�γ���
					fail++;
					HibernateTemple.save(r);
				}
			}
		}
		
		HibernateTemple.deleteAll("update Data set number=? where id=1" ,success);//ͼ������
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
