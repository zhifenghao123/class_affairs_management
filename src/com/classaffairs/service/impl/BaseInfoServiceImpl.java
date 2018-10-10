package com.classaffairs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.classaffairs.common.BaseInfoManager;
import com.classaffairs.dao.BaseInfoDao;
import com.classaffairs.dao.base.BaseDao;
import com.classaffairs.entity.BaseInfo;
import com.classaffairs.entity.Student;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.BaseInfoService;
@Service
public class BaseInfoServiceImpl implements BaseInfoService {

	@Autowired
	private BaseInfoDao itsBaseInfoDao;
	
	@Override
	public boolean addBaseInfo(BaseInfo baseInfo) {
		try {
			int result = itsBaseInfoDao.mSave(baseInfo);

			if (result == 1)
				BaseInfoManager.getInstance().init();

			return result == 1;
		} catch (DataAccessException dae) {
			Log.log.error("新增基本信息操作数据库异常,信息名称：" + baseInfo.getDisplay(), dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("新增基本信息异常,信息名称：" + baseInfo.getDisplay(), e);
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateBaseInfo(BaseInfo baseInfo) {
		try {
			int row = itsBaseInfoDao.mUpdate(baseInfo);

			if (row == 1)
				BaseInfoManager.getInstance().init();

			return row == 1;
		} catch (DataAccessException dae) {
			Log.log.error("修改基本信息操作数据库异常,信息名称：" + baseInfo.getDisplay(), dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("修改基本信息异常,信息名称：" + baseInfo.getDisplay(), e);
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteBaseInfo(Long baseInfoId) {
		try {
			itsBaseInfoDao.mDeleteById(baseInfoId);

			BaseInfoManager.getInstance().init();

			return true;
		} catch (DataAccessException dae) {
			Log.log.error("通过基本信息id删除基本信息操作数据库异常,基本信息id:" + baseInfoId, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过基本信息id删除基本信息异常,基本信息id:" + baseInfoId, e);
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public BaseInfo findBaseInfoById(Long baseInfoId){
		BaseInfo baseInfo = null;
		try {
			baseInfo = (BaseInfo) itsBaseInfoDao.mFindById(baseInfoId);
		} catch (DataAccessException dae) {
			Log.log.error("通过基本信息id删除基本信息操作数据库异常,基本信息id:" + baseInfoId, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过基本信息id删除基本信息异常,基本信息id:" + baseInfoId, e);
			e.printStackTrace();
		}
		return baseInfo;
	}
	@Override
	public String getDisplaysByTypeAndCodes(Long type, String codes) {
		String displays = "";
		if (null != codes && !codes.equals("codes")) {
			String[] code = codes.trim().split(",");
			for (int i = 0; i < code.length; i++){
				String item = code[i];
				displays+= BaseInfoManager.getInstance().getDisplayByTypeAndCode(type, Integer.valueOf(item));
				if(!(i==code.length-1)){
					displays+=" ";
				}
			}
		}
		return displays;
	}

	@Override
	public BaseInfo getCodesByType(Long typeid) {
		 List<BaseInfo> baseinfo = BaseInfoManager.getInstance().getBaseInfoByType(typeid);
		 BaseInfo baseinfos =null;
		 for (int i = 0; i < baseinfo.size(); i++){
			 baseinfos = baseinfo.get(i);
			}
		 return baseinfos;
	}

	@Override
	public List<BaseInfo> findBaseInfoByBaseTypeId(Long baseTypeId) {
		List<BaseInfo> baseInfoList = null;
		try {
			baseInfoList = (List<BaseInfo>) itsBaseInfoDao.mFind( "selectBaseInfoByType", baseTypeId);
		} catch (DataAccessException dae) {
			Log.log.error("操作数据库异常,基本信息baseTypeId:" + baseTypeId, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("基本信息异常,基本信息baseTypeId:" + baseTypeId, e);
			e.printStackTrace();
		}
		return baseInfoList;
	}

	@Override
	public List<BaseInfo> findAllBaseInfo() {
		List<BaseInfo> baseInfoAllList = null;
		try {
			baseInfoAllList = (List<BaseInfo>) itsBaseInfoDao.mFindAll();
		} catch (DataAccessException dae) {
			Log.log.error("操作数据库异常," , dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("基本信息异常", e);
			e.printStackTrace();
		}
		return baseInfoAllList;
	}

	@Override
	public Page<BaseInfo> getBaseInfosByPageQuery(String page, String pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		Page<BaseInfo> baseInfoPage = null;
		try {
			int recordOffset = (Integer.valueOf(page) - 1) * Integer.valueOf(pageSize);
			baseInfoPage = (Page<BaseInfo>) itsBaseInfoDao.mPageQuery("findBaseInfo", param, recordOffset, Integer.valueOf(pageSize));
		} catch (DataAccessException dae) {
			Log.log.error("分页获取baseType访问数据库异常" , dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("分页获取baseType访问数据库异常" , e);
			e.printStackTrace();
		}

		return baseInfoPage;
	}

}
