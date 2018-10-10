/**
 * 
 */
package com.classaffairs.service;

import com.classaffairs.entity.Participation;

/**
 * @author Haozhifeng
 *
 */
public interface ParticipationService {
	boolean addParticipation(Participation participation);
	
	boolean updateParticipation(Participation participation);
	
	boolean deleteParticipation(Long participationId);
	
	Participation findParticipationByParticipationId(Long participationId);
	
	Participation findParticipationUserNoAndActivityId(String userNo,Long activityId);
}
