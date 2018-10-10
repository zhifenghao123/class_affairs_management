/**
 * 
 */
package com.classaffairs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.classaffairs.dao.DepartmentDao;
import com.classaffairs.entity.Department;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.DepartmentService;

/**
 * @author Haozhifeng
 *
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentDao itsDepartmentDao;
	/* (non-Javadoc)
	 * @see com.classaffairs.service.DepartmentService#addDepartment(com.classaffairs.entity.Department)
	 */
	@Override
	public boolean addDepartment(Department department) {
		int result = 0;
		try{
			result = itsDepartmentDao.mSave(department);
		}catch(DataAccessException dae){
			Log.log.error("新增系（所）访问数据库异常，系（所）名称：" + department.getName(), dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("新增系（所）异常，系（所）名称：" + department.getName() , e);
			e.printStackTrace();
		}
		return result == 1;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.DepartmentService#updateDepartment(com.classaffairs.entity.Department)
	 */
	@Override
	public boolean updateDepartment(Department department) {
		int result = 0;
		try{
			result = itsDepartmentDao.mUpdate(department);
		}catch(DataAccessException dae){
			Log.log.error("修改系（所）访问数据库异常，系（所）名称：" + department.getName(), dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("修改系（所）异常，系（所）名称：" + department.getName() , e);
			e.printStackTrace();
		}
		return result == 1;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.DepartmentService#deleteDepartment(java.lang.Long)
	 */
	@Override
	public boolean deleteDepartment(Long departmentId) {
		try{
			itsDepartmentDao.mDeleteById(departmentId);
			return true;
		}catch(DataAccessException dae){
			Log.log.error("删除系（所）访问数据库异常，系（所）名称Id：" + departmentId, dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("删除系（所）异常，系（所）名称Id：" + departmentId , e);
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.DepartmentService#findDepartmentByDepartmentId(java.lang.Long)
	 */
	@Override
	public Department findDepartmentByDepartmentId(Long departmentId) {
		Department Department = null;
		try {
			Department = (Department) itsDepartmentDao.mFindById(departmentId);
		} catch (DataAccessException dae) {
			Log.log.error("通过系（所）id获取系（所）访问数据库异常DepartmentId=" + departmentId, dae);

			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过系（所）id获取系（所）异常DepartmentId=" + departmentId, e);

			e.printStackTrace();
		}

		return Department;
	}

	@Override
	public Department findDepartmentByDepartmentNo(String departmentNo) {
		Department Department = null;
		try {
			Department = (Department) itsDepartmentDao.mSelectOne("findDepartmentByDepartmentNo", departmentNo);
		} catch (DataAccessException dae) {
			Log.log.error("通过系（所）id获取系（所）访问数据库异常DepartmentId=" + departmentNo, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过系（所）id获取系（所）异常DepartmentId=" + departmentNo, e);
			e.printStackTrace();
		}

		return Department;
	}
	
	/* (non-Javadoc)
	 * @see com.classaffairs.service.DepartmentService#getDepartmentsByPageQuery(java.lang.String, java.lang.Integer, int)
	 */
	@Override
	public Page<Department> getDepartmentsByPageQuery(String schoolNo,
			Integer page, int pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		if(schoolNo != null)
			param.put("schoolNo", "%" + schoolNo + "%");

		Page<Department> departmentPge = null;
		try {
			int recordOffset = (Integer.valueOf(page) - 1) * Integer.valueOf(pageSize);

			departmentPge = (Page<Department>) itsDepartmentDao.mPageQuery("findDepartmentByFuzzyInformation", param, recordOffset, Integer.valueOf(pageSize));

		} catch (DataAccessException dae) {
			Log.log.error("通过参数（系（所）属性）分页获取系（所）操作数据库异常,系（所）名称：" + schoolNo, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过参数（系（所）属性）分页获取系（所）异常,系（所）名称：" + schoolNo, e);
			e.printStackTrace();
		}

		return departmentPge;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.DepartmentService#findDepartmentListBySchoolNo(java.lang.String)
	 */
	@Override
	public List<Department> findDepartmentListBySchoolNo(String schoolNo) {
		List<Department> executiveClasses = (List<Department>) itsDepartmentDao.mFind("findDepartmentListBySchoolNo", schoolNo);
		return executiveClasses;
	}



}
