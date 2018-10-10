/**
 * 
 */
package com.classaffairs.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.classaffairs.common.BaseInfoManager;
import com.classaffairs.dao.BaseTypeDao;
import com.classaffairs.entity.BaseInfo;
import com.classaffairs.entity.BaseType;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.service.BaseTypeService;

/**
 * @author Haozhifeng
 *
 */
@Service
public class BaseTypeServiceImpl implements BaseTypeService {

	@Autowired
	private BaseTypeDao itsBaseTypeDao;
	/* (non-Javadoc)
	 * @see com.classaffairs.service.BaseTypeService#addBaseType(com.classaffairs.entity.BaseType)
	 */
	@Override
	public boolean addBaseType(BaseType baseType) {
		try {
			int result = itsBaseTypeDao.mSave(baseType);

			if (result == 1)
				BaseInfoManager.getInstance().init();

			return result == 1;
		} catch (DataAccessException dae) {
			Log.log.error("新增基本信息操作数据库异常,信息名称：" + baseType.getTypeName(), dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("新增基本信息异常,信息名称：" + baseType.getTypeName(), e);
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.BaseTypeService#updateBaseType(com.classaffairs.entity.BaseType)
	 */
	@Override
	public boolean updateBaseType(BaseType baseType) {
		try {
			int row = itsBaseTypeDao.mUpdate(baseType);

			if (row == 1)
				BaseInfoManager.getInstance().init();

			return row == 1;
		} catch (DataAccessException dae) {
			Log.log.error("修改基本信息操作数据库异常,信息名称：" + baseType.getTypeName(), dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("修改基本信息异常,信息名称：" + baseType.getTypeName(), e);
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.BaseTypeService#deleteBaseType(java.lang.Long)
	 */
	@Override
	public boolean deleteBaseType(Long baseTypeId) {
		try {
			itsBaseTypeDao.mDeleteById(baseTypeId);

			BaseInfoManager.getInstance().init();

			return true;
		} catch (DataAccessException dae) {
			Log.log.error("通过基本信息id删除基本信息操作数据库异常,基本信息id:" + baseTypeId, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过基本信息id删除基本信息异常,基本信息id:" + baseTypeId, e);
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> findAllBaseType() {
		List<Map<String, Object>>  resultList = new ArrayList<Map<String,Object>>();
		try {
			resultList = (List<Map<String, Object>>) itsBaseTypeDao.mFind("findAllBaseType", "");
		} catch (DataAccessException dae) {
			Log.log.error("查找所有基本类型操作数据库异常,基本信息id", dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("查找所有基本类型基本信息异常,基本信息id", e);
			e.printStackTrace();
		}
		return resultList;
	}

	@Override
	public BaseType findBaseTypeByBaseTypeId(Long baseTypeId) {
		BaseType baseType = null;
		try {
			baseType = (BaseType) itsBaseTypeDao.mFindById(baseTypeId);
		} catch (DataAccessException dae) {
			Log.log.error("操作数据库异常,基本信息baseTypeId:" + baseTypeId, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("基本信息异常,基本信息baseTypeId:" + baseTypeId, e);
			e.printStackTrace();
		}
		return baseType;
	}

}
