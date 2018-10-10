/**
 * 
 */
package com.classaffairs.service;

import java.util.List;

import com.classaffairs.entity.School;
import com.classaffairs.entity.Student;
import com.classaffairs.framework.sdp.orm.query.Page;

/**
 * @author Haozhifeng
 *
 */
public interface StudentService {

	boolean addStudent(Student student);
	
	boolean updateStudent(Student student);
	
	boolean deleteStudent(Long studentId);
	
	Student findStudentByStudentId(Long studentId);
	
	Student findStudentByStudentNo(String studentNo);
	/**
	 * 根据学生Id获取直接子学院页
	 * @param studentNo 学生id
	 * @param studentName 学生id
	 * @param schoolNo 学生id
	 * @param majorNo 学生id
	 * @param executiveClassNo 学生id
	 * @param pageNum 第几页
	 * @param pageSize 页面大小
	 * @return Page 学院对象页
	 */
	//public Page<Student> getStudentsByPageQuery(String name, String page, String pageSize);
	public Page<Student> getStudentsByPageQueryFromAdmin(String studentNo, String studentName, String gradeNo,String schoolNo, String majorNo, String executiveClassNo, String page, String pageSize);
	/**
	 * 根据学生Id获取直接子学院页
	 * @param studentNo 学生id
	 * @param studentName 学生id
	 * @param schoolNo 学生id
	 * @param majorNo 学生id
	 * @param executiveClassNo 学生id
	 * @param pageNum 第几页
	 * @param pageSize 页面大小
	 * @return Page 学院对象页
	 */
	public List<Student> getStudentsByQueryFromAdmin(String studentNo, String studentName, String gradeNo,String schoolNo, String majorNo, String executiveClassNo);
	/**
	 * 根据学生Id获取直接子学院页
	 * @param studentNo 学生id
	 * @param studentName 学生id
	 * @param schoolNo 学生id
	 * @param majorNo 学生id
	 * @param executiveClassNo 学生id
	 * @param pageNum 第几页
	 * @param pageSize 页面大小
	 * @return Page 学院对象页
	 */
	//public Page<Student> getStudentsByPageQuery(String name, String page, String pageSize);
	public Page<Student> getStudentsByPageQueryFromStudent(String studentNo, String studentName, String sex,String birthplace,String politicalLandscape, String accessType, String majorNo, String cultivationType, String executiveClassNo, String page, String pageSize);
	/**
	 * 根据学生Id获取直接子学院页
	 * @param studentNo 学生id
	 * @param studentName 学生id
	 * @param schoolNo 学生id
	 * @param majorNo 学生id
	 * @param executiveClassNo 学生id
	 * @param pageNum 第几页
	 * @param pageSize 页面大小
	 * @return Page 学院对象页
	 */
	public List<Student> getStudentsByQueryFromStudent(String studentNo, String studentName, String sex,String birthplace,String politicalLandscape, String accessType, String majorNo, String cultivationType, String executiveClassNo);
	/**
	 * 批量导入数据库
	 * */
	boolean insertStudentsByBatch(List<Student> studentList);
	/**
	 * 批量导入数据库
	 * */
	boolean updateStudentsByBatch(List<Student> studentList);
	
	public List<Student> findStudentListByExecutiveClassNames(List<String> executiveClassNameList);
}
