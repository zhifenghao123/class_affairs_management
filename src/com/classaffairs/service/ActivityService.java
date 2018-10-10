/**
 * 
 */
package com.classaffairs.service;

import com.classaffairs.entity.Activity;
import com.classaffairs.framework.sdp.orm.query.Page;

/**
 * @author Haozhifeng
 *
 */
public interface ActivityService {
	boolean addActivity(Activity activity);
	
	boolean updateActivity(Activity activity);
	
	boolean deleteActivity(Long activityId);
	
	Activity findActivityByActivityId(Long activityId);
	
	public Page<Activity> getActivitysByPageQuery(String name, String startTimeToSearchPublishTime, String endTimeToSearchPublishTime,String currentExecutiveClassName,Integer page,int pageSize);
	
	public Page<Activity> getUserParticipatedActivitysByPageQuery(String name, String startTimeToSearchPublishTime, String endTimeToSearchPublishTime,String participatorUserNo,Integer page,int pageSize);
	
	public Page<Activity> getUserPublishedActivityListsByPageQuery(String name, String startTimeToSearchPublishTime, String endTimeToSearchPublishTime,String publisherUserNo,Integer page,int pageSize);
}
