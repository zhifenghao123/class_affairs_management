/**
 * 
 */
package com.classaffairs.service;

import java.util.List;

import com.classaffairs.entity.School;
import com.classaffairs.framework.sdp.orm.query.Page;

/**
 * @author Haozhifeng
 *
 */
public interface SchoolService {
	/**
	 * 由School对象插入数据
	 * @param School 学院对象
	 * @return 添加学院对象成功返回true，否则返回false
	 */
	public boolean addSchool(School school);

	/**
	 * 更新学院对象
	 * @param School 学院对象
	 * @return 修改学院对象成功返回true，否则返回false
	 */
	public boolean updateSchool(School school);
	/**
	 * 根据学院Id删除该学院
	 * @param School_id 学院id
	 * @return 删除成功返回true，否则返回false
	 */
	public boolean deleteSchool(Long schoolId);
	/**
	 * 根据学院id从数据库获取该学院对象
	 * @param SchoolId 学院对象id
	 * @return School 学院对象
	 */
	public School findSchoolBySchoolId(Long schoolId);
	/**
	 * 根据父学院Id获取直接子学院页
	 * @param parentId 父学院id
	 * @param pageNum 第几页
	 * @param pageSize 页面大小
	 * @return Page 学院对象页
	 */
	public Page<School> getSchoolsByPageQuery(String name, String page, String pageSize);
	
	public School findSchoolBySchoolNo(String SchoolNo);
	
	/**
	 * 获取所有的权限类型
	 * @return 权限类型List
	 */
	public List<School> getAllSchools();
}
