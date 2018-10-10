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

import com.classaffairs.dao.LaboratoryDao;
import com.classaffairs.entity.Laboratory;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.LaboratoryService;

/**
 * @author Haozhifeng
 *
 */
@Service
public class LaboratoryServiceImpl implements LaboratoryService {

	@Autowired
	private LaboratoryDao itsLaboratoryDao;
	/* (non-Javadoc)
	 * @see com.classaffairs.service.LaboratoryService#addLaboratory(com.classaffairs.entity.Laboratory)
	 */
	@Override
	public boolean addLaboratory(Laboratory laboratory) {
		int result = 0;
		try{
			result = itsLaboratoryDao.mSave(laboratory);
		}catch(DataAccessException dae){
			Log.log.error("新增研究室访问数据库异常，研究室名称：" + laboratory.getLaboratoryNo(), dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("新增研究室异常，研究室名称：" + laboratory.getLaboratoryNo() , e);
			e.printStackTrace();
		}
		return result == 1;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.LaboratoryService#updateLaboratory(com.classaffairs.entity.Laboratory)
	 */
	@Override
	public boolean updateLaboratory(Laboratory laboratory) {
		int result = 0;
		try{
			result = itsLaboratoryDao.mUpdate(laboratory);
		}catch(DataAccessException dae){
			Log.log.error("修改研究室访问数据库异常，研究室名称：" + laboratory.getLaboratoryNo(), dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("修改研究室异常，研究室名称：" + laboratory.getLaboratoryNo() , e);
			e.printStackTrace();
		}
		return result == 1;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.LaboratoryService#deleteLaboratory(java.lang.Long)
	 */
	@Override
	public boolean deleteLaboratory(Long laboratoryId) {
		try{
			itsLaboratoryDao.mDeleteById(laboratoryId);
			return true;
		}catch(DataAccessException dae){
			Log.log.error("删除研究室访问数据库异常，研究室名称Id：" + laboratoryId, dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("删除研究室异常，研究室名称Id：" + laboratoryId , e);
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.LaboratoryService#findLaboratoryByLaboratoryId(java.lang.Long)
	 */
	@Override
	public Laboratory findLaboratoryByLaboratoryId(Long laboratoryId) {
		Laboratory Laboratory = null;
		try {
			Laboratory = (Laboratory) itsLaboratoryDao.mFindById(laboratoryId);
		} catch (DataAccessException dae) {
			Log.log.error("通过研究室id获取研究室访问数据库异常LaboratoryId=" + laboratoryId, dae);

			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过研究室id获取研究室异常LaboratoryId=" + laboratoryId, e);

			e.printStackTrace();
		}

		return Laboratory;
	}
	
	@Override
	public Laboratory findLaboratoryByLaboratoryNo(String laboratoryNo) {
		Laboratory Laboratory = null;
		try {
			Laboratory = (Laboratory) itsLaboratoryDao.mSelectOne("findLaboratoryByLaboratoryNo", laboratoryNo);
		} catch (DataAccessException dae) {
			Log.log.error("通过研究室id获取研究室访问数据库异常laboratoryNo=" + laboratoryNo, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过研究室id获取研究室异常laboratoryNo=" + laboratoryNo, e);
			e.printStackTrace();
		}

		return Laboratory;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.LaboratoryService#getLaboratorysByPageQuery(java.lang.String, java.lang.Integer, int)
	 */
	@Override
	public Page<Laboratory> getLaboratorysByPageQuery(String schoolNo,
			Integer page, int pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		if(schoolNo != null)
			param.put("schoolNo", "%" + schoolNo + "%");

		Page<Laboratory> laboratoryPge = null;
		try {
			int recordOffset = (Integer.valueOf(page) - 1) * Integer.valueOf(pageSize);

			laboratoryPge = (Page<Laboratory>) itsLaboratoryDao.mPageQuery("findLaboratoryByFuzzyInformation", param, recordOffset, Integer.valueOf(pageSize));

		} catch (DataAccessException dae) {
			Log.log.error("通过参数（研究室属性）分页获取研究室操作数据库异常,研究室名称：" + schoolNo, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过参数（研究室属性）分页获取研究室异常,研究室名称：" + schoolNo, e);
			e.printStackTrace();
		}

		return laboratoryPge;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.LaboratoryService#findLaboratoryListBySchoolNo(java.lang.String)
	 */
	@Override
	public List<Laboratory> findLaboratoryListBySchoolNo(String schoolNo) {
		List<Laboratory> executiveClasses = (List<Laboratory>) itsLaboratoryDao.mFind("findLaboratoryListBySchoolNo", schoolNo);
		return executiveClasses;
	}

}
