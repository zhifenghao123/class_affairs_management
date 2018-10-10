/**
 * 
 */
package com.classaffairs.service;

import java.util.List;

import com.classaffairs.entity.ExecutiveClass;
import com.classaffairs.entity.Major;
import com.classaffairs.framework.sdp.orm.query.Page;

/**
 * @author Haozhifeng
 *
 */
public interface MajorService {
	/**
	 * 由Major对象插入数据
	 * @param Major 专业对象
	 * @return 添加专业对象成功返回true，否则返回false
	 */
	public boolean addMajor(Major major);

	/**
	 * 更新专业对象
	 * @param Major 专业对象
	 * @return 修改专业对象成功返回true，否则返回false
	 */
	public boolean updateMajor(Major major);
	/**
	 * 根据专业Id删除该专业
	 * @param Major_id 专业id
	 * @return 删除成功返回true，否则返回false
	 */
	public boolean deleteMajor(Long majorId);
	/**
	 * 根据专业id从数据库获取该专业对象
	 * @param MajorId 专业对象id
	 * @return Major 专业对象
	 */
	public Major findMajorByMajorId(Long majorId);
	/**
	 * 根据父专业Id获取直接子专业页
	 * @param parentId 父专业id
	 * @param pageNum 第几页
	 * @param pageSize 页面大小
	 * @return Page 专业对象页
	 */
	public Page<Major> getMajorsByPageQuery(String name, String page, String pageSize);
	
	public Major findMajorByMajorNo(String MajorNo);
	
	public List<Major> findMajorListBySchoolNo(String schoolNo);
	/**
	 * 根据专业id从数据库获取该专业对象
	 * @param MajorId 专业对象id
	 * @return Major 专业对象
	 */
	public Major findMajorByMajorName(String majorName);
}
