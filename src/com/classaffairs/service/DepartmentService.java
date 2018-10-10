/**
 * 
 */
package com.classaffairs.service;

import java.util.List;

import com.classaffairs.entity.Department;
import com.classaffairs.entity.Major;
import com.classaffairs.framework.sdp.orm.query.Page;

/**
 * @author Haozhifeng
 *
 */
public interface DepartmentService {

	boolean addDepartment(Department department);
	
	boolean updateDepartment(Department department);
	
	boolean deleteDepartment(Long departmentId);
	
	public Department findDepartmentByDepartmentId(Long departmentId);
	
	public Department findDepartmentByDepartmentNo(String departmentNo);
	
	public Page<Department> getDepartmentsByPageQuery(String schoolNo, Integer page,int pageSize);
	
	public List<Department> findDepartmentListBySchoolNo(String schoolNo);
}
