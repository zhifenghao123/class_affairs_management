/**
 * 
 */
package com.classaffairs.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.classaffairs.dao.ParticipationDao;
import com.classaffairs.entity.Participation;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.service.ParticipationService;

/**
 * @author Haozhifeng
 *
 */
@Service
public class ParticipationServiceImpl implements ParticipationService {

	@Autowired
	private ParticipationDao itsParticipationDao;
	/* (non-Javadoc)
	 * @see com.classaffairs.service.ParticipationService#addParticipation(com.classaffairs.entity.Participation)
	 */
	@Override
	public boolean addParticipation(Participation participation) {
		try {
			int result = itsParticipationDao.mSave(participation);
			return result == 1;
		} catch (DataAccessException dae) {
			Log.log.error("新增活动参与记录操作数据库异常,", dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("新增活动参与记录异常,", e);
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.ParticipationService#updateParticipation(com.classaffairs.entity.Participation)
	 */
	@Override
	public boolean updateParticipation(Participation participation) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.ParticipationService#deleteParticipation(java.lang.Long)
	 */
	@Override
	public boolean deleteParticipation(Long participationId) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.ParticipationService#findParticipationByParticipationId(java.lang.Long)
	 */
	@Override
	public Participation findParticipationByParticipationId(Long participationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Participation findParticipationUserNoAndActivityId(String userNo,
			Long activityId) {
		Participation participation = null;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userNo", userNo);
		map.put("activityId", activityId);
		try{
			participation = itsParticipationDao.mSelectOne("findParticipationUserNoAndActivityId", map);
		} catch (DataAccessException dae) {
			Log.log.error("通过用户账号和活动Id获取活动参加记录失败，：userNo" + userNo +",activityId:"+activityId, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过用户账号和活动Id获取活动参加记录失败，：userNo" + userNo +",activityId:"+activityId, e);
			e.printStackTrace();
		}
		return participation;
	}

}
