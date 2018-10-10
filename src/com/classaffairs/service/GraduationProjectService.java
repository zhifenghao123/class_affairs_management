/**
 * 
 */
package com.classaffairs.service;

import java.util.List;

import com.classaffairs.entity.GraduationProject;
import com.classaffairs.entity.Student;
import com.classaffairs.framework.sdp.orm.query.Page;

/**
 * @author Haozhifeng
 *
 */
public interface GraduationProjectService {

	boolean addGraduationProject(GraduationProject graduationProject);
	
	boolean updateGraduationProject(GraduationProject graduationProject);
	
	boolean deleteGraduationProject(Long graduationProjectId);
	
	GraduationProject findGraduationProjectByGraduationProjectId(Long graduationProjectId);
	
	/**
	 * 批量导入数据库
	 * */
	boolean insertGraduationProjectsByBatch(List<GraduationProject> graduationProjectList);
	
	
	public Page<GraduationProject> getGraduationProjectsByPageQuery(String graduationProjectGroup, String studentNo, String studentName, String page, String pageSize);
	
	public Page<GraduationProject> getGraduationProjectsByStudentNo(String studentNo, String page, String pageSize);
}
