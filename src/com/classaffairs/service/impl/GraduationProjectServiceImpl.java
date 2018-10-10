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

import com.classaffairs.dao.GraduationProjectDao;
import com.classaffairs.entity.GraduationProject;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.GraduationProjectService;

/**
 * @author Haozhifeng
 *
 */
@Service
public class GraduationProjectServiceImpl implements GraduationProjectService {
	@Autowired
	private GraduationProjectDao itsGraduationProjectDao;
	/* (non-Javadoc)
	 * @see com.classaffairs.service.GraduationProjectService#addGraduationProject(com.classaffairs.entity.GraduationProject)
	 */
	@Override
	public boolean addGraduationProject(GraduationProject graduationProject) {
		try {
			int result = itsGraduationProjectDao.mSave(graduationProject);
			return result == 1;
		} catch (DataAccessException dae) {
			Log.log.error("新增毕业设计操作数据库异常,毕业设计名称：" + graduationProject.getStudentName(), dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("新增毕业设计异常,毕业设计名称：" + graduationProject.getStudentName(), e);
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.GraduationProjectService#updateGraduationProject(com.classaffairs.entity.GraduationProject)
	 */
	@Override
	public boolean updateGraduationProject(GraduationProject graduationProject) {
		try {
			int row = itsGraduationProjectDao.mUpdate(graduationProject);
			return row == 1;
		} catch (DataAccessException dae) {
			Log.log.error("修改毕业设计操作数据库异常,毕业设计名称：" + graduationProject.getStudentName(), dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("修改毕业设计异常,毕业设计名称：" + graduationProject.getStudentName(), e);
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.GraduationProjectService#deleteGraduationProject(java.lang.Long)
	 */
	@Override
	public boolean deleteGraduationProject(Long graduationProjectId) {
		try {
			itsGraduationProjectDao.mDeleteById(graduationProjectId);
			return true;
		} catch (DataAccessException dae) {
			Log.log.error("通过毕业设计id删除毕业设计操作数据库异常,毕业设计id:" + graduationProjectId, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过毕业设计id删除毕业设计异常,毕业设计id:" + graduationProjectId, e);
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.GraduationProjectService#findGraduationProjectByGraduationProjectId(java.lang.Long)
	 */
	@Override
	public GraduationProject findGraduationProjectByGraduationProjectId(
			Long graduationProjectId) {
		try {
			GraduationProject graduationProject = (GraduationProject) itsGraduationProjectDao.mFindById(graduationProjectId);

			return graduationProject;
		} catch (DataAccessException dae) {
			Log.log.error("通过毕业设计id获取毕业设计操作数据库异常,毕业设计id:" + graduationProjectId, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过毕业设计id获取毕业设计异常,毕业设计id:" + graduationProjectId, e);
			e.printStackTrace();
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.GraduationProjectService#insertGraduationProjectsByBatch(java.util.List)
	 */
	@Override
	public boolean insertGraduationProjectsByBatch(
			List<GraduationProject> graduationProjectList) {
		try {
			int result = itsGraduationProjectDao.mSaveByBatch(graduationProjectList);
			return result == graduationProjectList.size();
		} catch (DataAccessException dae) {
			Log.log.error("批量新增毕业设计操作数据库异常,毕业设计数量大小：" + graduationProjectList.size(), dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("批量新增毕业设计异常,毕业设计数量大小：" + graduationProjectList.size(), e);
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Page<GraduationProject> getGraduationProjectsByPageQuery(
			String graduationProjectGroup, String studentNo,
			String studentName, String page, String pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		if(studentNo != null && !("").equals(studentNo))
			param.put("studentNo", "%" + studentNo + "%");
		if(studentName != null && !("").equals(studentName))
			param.put("studentName", "%" + studentName + "%");
		if(graduationProjectGroup != null && !("").equals(graduationProjectGroup))
			param.put("graduationProjectGroup", "%" + graduationProjectGroup + "%");
		Page<GraduationProject> studentPage = null;
		try {
			int recordOffset = (Integer.valueOf(page) - 1) * Integer.valueOf(pageSize);

			studentPage = (Page<GraduationProject>) itsGraduationProjectDao.mPageQuery("findGraduationProjectByFuzzyInformation", param, recordOffset, Integer.valueOf(pageSize));
			
		} catch (DataAccessException dae) {
			Log.log.error("通过参数（学生属性）分页获取学生操作数据库异常,学生名称：" + studentName, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过参数（学生属性）分页获取学生异常,学生名称：" + studentName, e);
			e.printStackTrace();
		}

		return studentPage;
	}


	@Override
	public Page<GraduationProject> getGraduationProjectsByStudentNo(
			String studentNo, String page, String pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		Page<GraduationProject> studentPage = null;
		try {
			int recordOffset = (Integer.valueOf(page) - 1) * Integer.valueOf(pageSize);

			studentPage = (Page<GraduationProject>) itsGraduationProjectDao.mPageQuery("findGraduationProjectByStudentNo", studentNo, recordOffset, Integer.valueOf(pageSize));
			
		} catch (DataAccessException dae) {
			Log.log.error("通过参数（学生属性）分页获取学生操作数据库异常,学生名称：" + studentNo, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过参数（学生属性）分页获取学生异常,学生名称：" + studentNo, e);
			e.printStackTrace();
		}

		return studentPage;
	}

}
