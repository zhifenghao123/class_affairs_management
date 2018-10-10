/**
 * 
 */
package com.classaffairs.service;

import java.util.List;

import com.classaffairs.entity.Grade;
import com.classaffairs.framework.sdp.orm.query.Page;

/**
 * @author Haozhifeng
 *
 */
public interface GradeService {
	/**
	 * 由Grade对象插入数据
	 * @param Grade 年级对象
	 * @return 添加年级对象成功返回true，否则返回false
	 */
	public boolean addGrade(Grade grade);

	/**
	 * 更新年级对象
	 * @param Grade 年级对象
	 * @return 修改年级对象成功返回true，否则返回false
	 */
	public boolean updateGrade(Grade grade);
	/**
	 * 根据年级Id删除该年级
	 * @param Grade_id 年级id
	 * @return 删除成功返回true，否则返回false
	 */
	public boolean deleteGrade(Long gradeId);
	/**
	 * 根据年级id从数据库获取该年级对象
	 * @param GradeId 年级对象id
	 * @return Grade 年级对象
	 */
	public Grade findGradeByGradeId(Long gradeId);
	/**
	 * 根据父年级Id获取直接子年级页
	 * @param parentId 父年级id
	 * @param pageNum 第几页
	 * @param pageSize 页面大小
	 * @return Page 年级对象页
	 */
	public Page<Grade> getGradesByPageQuery(String gradeNo, String page, String pageSize);
	
	public Grade findGradeByGradeNo(String GradeNo);
	
	public List<Grade> getAllGrades();
}
