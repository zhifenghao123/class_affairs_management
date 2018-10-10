/**
 * 
 */
package com.classaffairs.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.classaffairs.dao.ActivityDao;
import com.classaffairs.entity.Activity;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.ActivityService;

/**
 * @author Haozhifeng
 *
 */
@Service
public class ActivityServiceImpl implements ActivityService {

	@Autowired
	private ActivityDao itsActivityDao;
	/* (non-Javadoc)
	 * @see com.classaffairs.service.ActivityService#addActivity(com.classaffairs.entity.Activity)
	 */
	@Override
	public boolean addActivity(Activity activity) {
		try {
			int result = itsActivityDao.mSave(activity);
			return result == 1;
		} catch (DataAccessException dae) {
			Log.log.error("新增活动操作数据库异常,", dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("新增活动异常,", e);
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.ActivityService#updateActivity(com.classaffairs.entity.Activity)
	 */
	@Override
	public boolean updateActivity(Activity activity) {
		int result = 0;
		try{
			result = itsActivityDao.mUpdate(activity);
		}catch(DataAccessException dae){
			Log.log.error("修改活动访问数据库异常，活动名称：" + activity.getName(), dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("修改活动异常，活动名称：" + activity.getName() , e);
			e.printStackTrace();
		}
		return result == 1;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.ActivityService#deleteActivity(java.lang.Long)
	 */
	@Override
	public boolean deleteActivity(Long activityId) {
		try{
			itsActivityDao.mDeleteById(activityId);
			return true;
		}catch(DataAccessException dae){
			Log.log.error("删除活动访问数据库异常，活动名称Id：" + activityId, dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("删除活动异常，活动名称Id：" + activityId , e);
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.ActivityService#findActivityByActivityId(java.lang.Long)
	 */
	@Override
	public Activity findActivityByActivityId(Long activityId) {
		Activity activity = null;
		try {
			activity = (Activity) itsActivityDao.mFindById(activityId);
		} catch (DataAccessException dae) {
			Log.log.error("通过活动id获取活动访问数据库异常ActivityId=" + activityId, dae);

			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过活动id获取活动异常ActivityId=" + activityId, e);

			e.printStackTrace();
		}

		return activity;
	}
	
	@Override
	public Page<Activity> getActivitysByPageQuery(String name, String startTimeToSearchPublishTime, String endTimeToSearchPublishTime,
			String currentExecutiveClassName,Integer page,int pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		if(name != null)
			param.put("name", "%" + name + "%");
		param.put("currentExecutiveClassName", currentExecutiveClassName);
		Page<Activity> activityPge = null;
		try {
			int recordOffset = (Integer.valueOf(page) - 1) * Integer.valueOf(pageSize);

			activityPge = (Page<Activity>) itsActivityDao.mPageQuery("findActivityByFuzzyInformation", param, recordOffset, Integer.valueOf(pageSize));

		} catch (DataAccessException dae) {
			Log.log.error("通过参数（专业属性）分页获取专业操作数据库异常,专业名称：" + name, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过参数（专业属性）分页获取专业异常,专业名称：" + name, e);
			e.printStackTrace();
		}

		return activityPge;
	}

	@Override
	public Page<Activity> getUserParticipatedActivitysByPageQuery(String name,
			String startTimeToSearchPublishTime,
			String endTimeToSearchPublishTime, String participatorUserNo,
			Integer page, int pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		if(name != null)
			param.put("name", "%" + name + "%");
		param.put("participatorUserNo", participatorUserNo);
		Page<Activity> activityPge = null;
		try {
			int recordOffset = (Integer.valueOf(page) - 1) * Integer.valueOf(pageSize);

			activityPge = (Page<Activity>) itsActivityDao.mPageQuery("findUserParticipatedActivitysByFuzzyInformation", param, recordOffset, Integer.valueOf(pageSize));

		} catch (DataAccessException dae) {
			Log.log.error("通过参数（专业属性）分页获取专业操作数据库异常,专业名称：" + name, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过参数（专业属性）分页获取专业异常,专业名称：" + name, e);
			e.printStackTrace();
		}

		return activityPge;
	}

	@Override
	public Page<Activity> getUserPublishedActivityListsByPageQuery(String name,
			String startTimeToSearchPublishTime,
			String endTimeToSearchPublishTime, String publisherUserNo,
			Integer page, int pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		if(name != null)
			param.put("name", "%" + name + "%");
		param.put("publisherUserNo", publisherUserNo);
		Page<Activity> activityPge = null;
		try {
			int recordOffset = (Integer.valueOf(page) - 1) * Integer.valueOf(pageSize);

			activityPge = (Page<Activity>) itsActivityDao.mPageQuery("findUserPublishedActivityListsByFuzzyInformation", param, recordOffset, Integer.valueOf(pageSize));

		} catch (DataAccessException dae) {
			Log.log.error("通过参数（专业属性）分页获取专业操作数据库异常,专业名称：" + name, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过参数（专业属性）分页获取专业异常,专业名称：" + name, e);
			e.printStackTrace();
		}

		return activityPge;
	}

}
