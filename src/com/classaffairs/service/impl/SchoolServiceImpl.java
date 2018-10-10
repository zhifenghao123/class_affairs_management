package com.classaffairs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.classaffairs.dao.SchoolDao;
import com.classaffairs.entity.School;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.SchoolService;
@Service
public class SchoolServiceImpl implements SchoolService {
	@Autowired
	private SchoolDao itsSchoolDao;
	@Override
	public boolean addSchool(School school) {
		int result = 0;
		try{
			result = itsSchoolDao.mSave(school);
		}catch(DataAccessException dae){
			Log.log.error("新增学院访问数据库异常，学院名称：" + school.getName(), dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("新增学院异常，学院名称：" + school.getName() , e);
			e.printStackTrace();
		}
		return result == 1;
	}

	@Override
	public boolean updateSchool(School school) {
		int result = 0;
		try{
			result = itsSchoolDao.mUpdate(school);
		}catch(DataAccessException dae){
			Log.log.error("修改学院访问数据库异常，学院名称：" + school.getName(), dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("修改学院异常，学院名称：" + school.getName() , e);
			e.printStackTrace();
		}
		return result == 1;
	}

	@Override
	public boolean deleteSchool(Long schoolId) {
		try{
			itsSchoolDao.mDeleteById(schoolId);
			return true;
		}catch(DataAccessException dae){
			Log.log.error("删除学院访问数据库异常，学院名称Id：" + schoolId, dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("删除学院异常，学院名称Id：" + schoolId , e);
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public School findSchoolBySchoolId(Long schoolId) {
		School School = null;
		try {
			School = (School) itsSchoolDao.mFindById(schoolId);
		} catch (DataAccessException dae) {
			Log.log.error("通过学院id获取学院访问数据库异常SchoolId=" + schoolId, dae);

			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过学院id获取学院异常SchoolId=" + schoolId, e);

			e.printStackTrace();
		}

		return School;
	}

	@Override
	public Page<School> getSchoolsByPageQuery(String name, String page,
			String pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		if(name != null)
			param.put("name", "%" + name + "%");

		Page<School> schoolPge = null;
		try {
			int recordOffset = (Integer.valueOf(page) - 1) * Integer.valueOf(pageSize);

			schoolPge = (Page<School>) itsSchoolDao.mPageQuery("findSchoolByFuzzyInformation", param, recordOffset, Integer.valueOf(pageSize));

		} catch (DataAccessException dae) {
			Log.log.error("通过参数（学院属性）分页获取学院操作数据库异常,学院名称：" + name, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过参数（学院属性）分页获取学院异常,学院名称：" + name, e);
			e.printStackTrace();
		}

		return schoolPge;
	}

	@Override
	public School findSchoolBySchoolNo(String schoolNo) {
		School school = null;
		try {
			Object obj = itsSchoolDao.mSelectOne("findSchoolBySchoolNo", schoolNo);
			if (obj != null) {
				school = (School) obj;
			}
		} catch (DataAccessException dae) {
			Log.log.error("通过学院代码获取学院访问数据库异常，应用链接:" + schoolNo, dae);

			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过学院代码获取学院异常，应用链接:" + schoolNo, e);

			e.printStackTrace();
		}

		return school;
	}

	@Override
	public List<School> getAllSchools() {
		return (List<School>) itsSchoolDao.mFindAll();
	}

}
