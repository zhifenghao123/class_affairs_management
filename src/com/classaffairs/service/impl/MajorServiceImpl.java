package com.classaffairs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.classaffairs.dao.MajorDao;
import com.classaffairs.entity.ExecutiveClass;
import com.classaffairs.entity.Major;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.MajorService;
@Service
public class MajorServiceImpl implements MajorService {
	@Autowired
	private MajorDao itsMajorDao;
	@Override
	public boolean addMajor(Major major) {
		int result = 0;
		try{
			result = itsMajorDao.mSave(major);
		}catch(DataAccessException dae){
			Log.log.error("新增专业访问数据库异常，专业名称：" + major.getName(), dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("新增专业异常，专业名称：" + major.getName() , e);
			e.printStackTrace();
		}
		return result == 1;
	}

	@Override
	public boolean updateMajor(Major major) {
		int result = 0;
		try{
			result = itsMajorDao.mUpdate(major);
		}catch(DataAccessException dae){
			Log.log.error("修改专业访问数据库异常，专业名称：" + major.getName(), dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("修改专业异常，专业名称：" + major.getName() , e);
			e.printStackTrace();
		}
		return result == 1;
	}

	@Override
	public boolean deleteMajor(Long majorId) {
		try{
			itsMajorDao.mDeleteById(majorId);
			return true;
		}catch(DataAccessException dae){
			Log.log.error("删除专业访问数据库异常，专业名称Id：" + majorId, dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("删除专业异常，专业名称Id：" + majorId , e);
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Major findMajorByMajorId(Long majorId) {
		Major Major = null;
		try {
			Major = (Major) itsMajorDao.mFindById(majorId);
		} catch (DataAccessException dae) {
			Log.log.error("通过专业id获取专业访问数据库异常MajorId=" + majorId, dae);

			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过专业id获取专业异常MajorId=" + majorId, e);

			e.printStackTrace();
		}

		return Major;
	}

	@Override
	public Page<Major> getMajorsByPageQuery(String name, String page,
			String pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		if(name != null)
			param.put("name", "%" + name + "%");

		Page<Major> majorPge = null;
		try {
			int recordOffset = (Integer.valueOf(page) - 1) * Integer.valueOf(pageSize);

			majorPge = (Page<Major>) itsMajorDao.mPageQuery("findMajorByFuzzyInformation", param, recordOffset, Integer.valueOf(pageSize));

		} catch (DataAccessException dae) {
			Log.log.error("通过参数（专业属性）分页获取专业操作数据库异常,专业名称：" + name, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过参数（专业属性）分页获取专业异常,专业名称：" + name, e);
			e.printStackTrace();
		}

		return majorPge;
	}

	@Override
	public Major findMajorByMajorNo(String majorNo) {
		Major major = null;
		try {
			Object obj = itsMajorDao.mSelectOne("findMajorByMajorNo", majorNo);
			if (obj != null) {
				major = (Major) obj;
			}
		} catch (DataAccessException dae) {
			Log.log.error("通过专业代码获取专业访问数据库异常，应用链接:" + majorNo, dae);

			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过专业代码获取专业异常，应用链接:" + majorNo, e);

			e.printStackTrace();
		}

		return major;
	}

	@Override
	public List<Major> findMajorListBySchoolNo(String schoolNo) {
		List<Major> executiveClasses = (List<Major>) itsMajorDao.mFind("findMajorListBySchoolNo", schoolNo);
		return executiveClasses;
	}

	@Override
	public Major findMajorByMajorName(String majorName) {
		Major major = null;
		try {
			Object obj = itsMajorDao.mSelectOne("findMajorByMajorName", majorName);
			if (obj != null) {
				major = (Major) obj;
			}
		} catch (DataAccessException dae) {
			Log.log.error("通过专业名称获取专业访问数据库异常，应用链接:" + majorName, dae);

			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过专业名称获取专业异常，应用链接:" + majorName, e);

			e.printStackTrace();
		}

		return major;
	}

}
