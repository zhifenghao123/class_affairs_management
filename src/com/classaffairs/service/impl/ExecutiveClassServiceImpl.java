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

import com.classaffairs.dao.ExecutiveClassDao;
import com.classaffairs.entity.ExecutiveClass;
import com.classaffairs.entity.Grade;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.ExecutiveClassService;

/**
 * @author Haozhifeng
 *
 */
@Service
public class ExecutiveClassServiceImpl implements ExecutiveClassService {
	@Autowired
	private ExecutiveClassDao itsExecutiveClassDao;
	/* (non-Javadoc)
	 * @see com.classaffairs.service.system.ExecutiveClassService#addExecutiveClass(com.classaffairs.entity.ExecutiveClass)
	 */
	@Override
	public boolean addExecutiveClass(ExecutiveClass executiveClass) {
		try {
			int result = itsExecutiveClassDao.mSave(executiveClass);
			return result == 1;
		} catch (DataAccessException dae) {
			Log.log.error("新增行政班级操作数据库异常,行政班级名称：" + executiveClass.getName(), dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("新增行政班级异常,行政班级名称：" + executiveClass.getName(), e);
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.system.ExecutiveClassService#updateExecutiveClass(com.classaffairs.entity.ExecutiveClass)
	 */
	@Override
	public boolean updateExecutiveClass(ExecutiveClass executiveClass) {
		try {
			int row = itsExecutiveClassDao.mUpdate(executiveClass);
			return row == 1;
		} catch (DataAccessException dae) {
			Log.log.error("修改行政班级操作数据库异常,行政班级名称：" + executiveClass.getName(), dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("修改行政班级异常,行政班级名称：" + executiveClass.getName(), e);
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.system.ExecutiveClassService#deleteExecutiveClass(java.lang.Long)
	 */
	@Override
	public boolean deleteExecutiveClass(Long executiveClassId) {
		try {
			itsExecutiveClassDao.mDeleteById(executiveClassId);
			return true;
		} catch (DataAccessException dae) {
			Log.log.error("通过行政班级id删除行政班级操作数据库异常,行政班级id:" + executiveClassId, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过行政班级id删除行政班级异常,行政班级id:" + executiveClassId, e);
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.system.ExecutiveClassService#findExecutiveClassByExecutiveClassId(java.lang.Long)
	 */
	@Override
	public ExecutiveClass findExecutiveClassByExecutiveClassId(
			Long executiveClassId) {
		try {
			ExecutiveClass executiveClass = (ExecutiveClass) itsExecutiveClassDao.mFindById(executiveClassId);

			return executiveClass;
		} catch (DataAccessException dae) {
			Log.log.error("通过行政班级id获取行政班级操作数据库异常,行政班级id:" + executiveClassId, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过行政班级id获取行政班级异常,行政班级id:" + executiveClassId, e);
			e.printStackTrace();
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.system.ExecutiveClassService#getExecutiveClassByPageQuery(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Page<ExecutiveClass> getExecutiveClassByPageQuery(String name,
			String page, String pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		if(name != null)
			param.put("name", "%" + name + "%");

		Page<ExecutiveClass> executiveClassPge = null;
		try {
			int recordOffset = (Integer.valueOf(page) - 1) * Integer.valueOf(pageSize);

			executiveClassPge = (Page<ExecutiveClass>) itsExecutiveClassDao.mPageQuery("findExecutiveClassByFuzzyInformation", param, recordOffset, Integer.valueOf(pageSize));

		} catch (DataAccessException dae) {
			Log.log.error("通过参数（行政班级属性）分页获取行政班级操作数据库异常,行政班级名称：" + name, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过参数（行政班级属性）分页获取行政班级异常,行政班级名称：" + name, e);
			e.printStackTrace();
		}

		return executiveClassPge;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.system.ExecutiveClassService#findExecutiveClassByExecutiveClassNo(java.lang.String)
	 */
	@Override
	public ExecutiveClass findExecutiveClassByExecutiveClassName(
			String executiveClassName) {
		try {
			ExecutiveClass executiveClass = (ExecutiveClass) itsExecutiveClassDao.mSelectOne("findExecutiveClassByExecutiveClassName", executiveClassName);

			return executiveClass;
		} catch (DataAccessException dae) {
			Log.log.error("通过行政班级id获取行政班级操作数据库异常,行政班级id:" + executiveClassName, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过行政班级id获取行政班级异常,行政班级id:" + executiveClassName, e);
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<ExecutiveClass> findExecutiveClassListByGradeNoAndSchoolNo(String gradeNo,String schoolNo,String executiveClassGroup) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("gradeNo", gradeNo);
		param.put("schoolNo", schoolNo);
		List<ExecutiveClass> executiveClasses = (List<ExecutiveClass>) itsExecutiveClassDao.mFind("findExecutiveClassListByGradeNoAndSchoolNo", param);
		return executiveClasses;
	}

	@Override
	public List<ExecutiveClass> getAllExecutiveClasses() {
		return (List<ExecutiveClass>) itsExecutiveClassDao.mFindAll();
	}

	@Override
	public List<ExecutiveClass> getAllSameGroupExecutiveClassListByOneExecutiveClassName(
			String executiveClassName) {
		try {
			List<ExecutiveClass> executiveClassList = (List<ExecutiveClass>) itsExecutiveClassDao.mFind("getAllSameGroupExecutiveClassListByOneExecutiveClassName", executiveClassName);

			return executiveClassList;
		} catch (DataAccessException dae) {
			Log.log.error("通过行政班级id获取行政班级操作数据库异常,行政班级id:" + executiveClassName, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过行政班级id获取行政班级异常,行政班级id:" + executiveClassName, e);
			e.printStackTrace();
		}

		return null;
	}

}
