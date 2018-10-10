/**
 * 
 */
package com.classaffairs.service;

import java.util.List;

import com.classaffairs.entity.ExecutiveClass;
import com.classaffairs.entity.Grade;
import com.classaffairs.framework.sdp.orm.query.Page;

/**
 * @author Haozhifeng
 *
 */
public interface ExecutiveClassService {
	/**
	 * 由ExecutiveClass对象插入数据
	 * @param ExecutiveClassDao 行政班级对象
	 * @return 添加行政班级对象成功返回true，否则返回false
	 */
	public boolean addExecutiveClass(ExecutiveClass executiveClass);

	/**
	 * 更新行政班级对象
	 * @param ExecutiveClassDao 行政班级对象
	 * @return 修改行政班级对象成功返回true，否则返回false
	 */
	public boolean updateExecutiveClass(ExecutiveClass executiveClass);
	/**
	 * 根据行政班级Id删除该行政班级
	 * @param ExecutiveClass_id 行政班级id
	 * @return 删除成功返回true，否则返回false
	 */
	public boolean deleteExecutiveClass(Long executiveClassId);
	/**
	 * 根据行政班级id从数据库获取该行政班级对象
	 * @param ExecutiveClassId 行政班级对象id
	 * @return ExecutiveClass 行政班级对象
	 */
	public ExecutiveClass findExecutiveClassByExecutiveClassId(Long executiveClassId);
	/**
	 * 根据父行政班级Id获取直接子行政班级页
	 * @param parentId 父行政班级id
	 * @param pageNum 第几页
	 * @param pageSize 页面大小
	 * @return Page 行政班级对象页
	 */
	public Page<ExecutiveClass> getExecutiveClassByPageQuery(String name, String page, String pageSize);
	
	public ExecutiveClass findExecutiveClassByExecutiveClassName(String executiveClassName);
	
	public List<ExecutiveClass> findExecutiveClassListByGradeNoAndSchoolNo(String gradeNo,String schoolNo,String executiveClassGroup);
	
	public List<ExecutiveClass> getAllExecutiveClasses();
	
	public List<ExecutiveClass> getAllSameGroupExecutiveClassListByOneExecutiveClassName(String executiveClassName);
	
}
