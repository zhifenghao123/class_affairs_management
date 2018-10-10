/**
 * 
 */
package com.classaffairs.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.classaffairs.dao.OnlineBehaviorDao;
import com.classaffairs.entity.OnlineBehavior;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.OnlineBehaviorService;

/**
 * @author Haozhifeng
 *
 */
@Service
public class OnlineBehaviorServiceImpl implements OnlineBehaviorService {
	@Autowired
	private OnlineBehaviorDao itsOnlineBehaviorDao;
	/* (non-Javadoc)
	 * @see com.classaffairs.service.OnlineBehaviorService#addOnlineBehavior(com.classaffairs.entity.OnlineBehavior)
	 */
	@Override
	public boolean addOnlineBehavior(OnlineBehavior onlineBehavior) {
		try {
			int result = itsOnlineBehaviorDao.mSave(onlineBehavior);
			return result == 1;
		} catch (DataAccessException dae) {
			Log.log.error("新增在线行为记录操作数据库异常,", dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("新增在线行为记录异常,", e);
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.OnlineBehaviorService#updateOnlineBehavior(com.classaffairs.entity.OnlineBehavior)
	 */
	@Override
	public boolean updateOnlineBehavior(OnlineBehavior onlineBehavior) {
		int result = 0;
		try{
			result = itsOnlineBehaviorDao.mUpdate(onlineBehavior);
		}catch(DataAccessException dae){
			Log.log.error("修改在线行为记录访问数据库异常，在线行为记录名称：" + onlineBehavior.getOnlineBehaviorName(), dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("修改在线行为记录异常，在线行为记录名称：" + onlineBehavior.getOnlineBehaviorName() , e);
			e.printStackTrace();
		}
		return result == 1;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.OnlineBehaviorService#deleteOnlineBehavior(java.lang.Long)
	 */
	@Override
	public boolean deleteOnlineBehavior(Long onlineBehaviorId) {
		try{
			itsOnlineBehaviorDao.mDeleteById(onlineBehaviorId);
			return true;
		}catch(DataAccessException dae){
			Log.log.error("删除在线行为记录访问数据库异常，在线行为记录名称Id：" + onlineBehaviorId, dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("删除在线行为记录异常，在线行为记录名称Id：" + onlineBehaviorId , e);
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.OnlineBehaviorService#findOnlineBehaviorByOnlineBehaviorId(java.lang.Long)
	 */
	@Override
	public OnlineBehavior findOnlineBehaviorByOnlineBehaviorId(
			Long onlineBehaviorId) {
		OnlineBehavior onlineBehavior = null;
		try {
			onlineBehavior = (OnlineBehavior) itsOnlineBehaviorDao.mFindById(onlineBehaviorId);
		} catch (DataAccessException dae) {
			Log.log.error("通过在线行为记录id获取在线行为记录访问数据库异常OnlineBehaviorId=" + onlineBehaviorId, dae);

			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过在线行为记录id获取在线行为记录异常OnlineBehaviorId=" + onlineBehaviorId, e);

			e.printStackTrace();
		}

		return onlineBehavior;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.OnlineBehaviorService#getOnlineBehaviorsByPageQuery(java.lang.Long, java.lang.Integer, java.lang.Integer, int)
	 */
	@Override
	public Page<OnlineBehavior> getOnlineBehaviorsByPageQuery(String userNo,
			Integer type, Integer page, int pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		if(null != userNo && !("").equals(userNo)){
			param.put("userNo", "%" +userNo +"%");
		}
		
		param.put("type", type);
		Page<OnlineBehavior> onlineBehaviorPge = null;
		try {
			int recordOffset = (Integer.valueOf(page) - 1) * Integer.valueOf(pageSize);

			onlineBehaviorPge = (Page<OnlineBehavior>) itsOnlineBehaviorDao.mPageQuery("findOnlineBehaviorByFuzzyInformation", param, recordOffset, Integer.valueOf(pageSize));

		} catch (DataAccessException dae) {
			Log.log.error("通过参数（在线行为属性）分页获取在线行为操作数据库异常,在线行为所属用户名称：" + userNo, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过参数（在线行为属性）分页获取在线行为异常,在线行为所属用户名称：" + userNo, e);
			e.printStackTrace();
		}

		return onlineBehaviorPge;
	}

}
