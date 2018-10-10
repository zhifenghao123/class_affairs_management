/**
 * 
 */
package com.classaffairs.entity;

import java.util.Date;

/**
 * @author Haozhifeng
 *
 */
public class Participation {
	
	public static final int Participate_Type_Active = 1;//主动报名
	public static final int Participate_Type_Appointed = 2;//被指定安排
	
	public static final int Participate_State_Apply = 1;//已主动申请或者已指派，但未审核状态
	public static final int Participate_State_ExamineUnpass = 2;//审核未通过
	public static final int Participate_State_ExaminePass = 3;//审核通过
	public static final int Participate_State_Finish = 4;//按时成功参加活动
	public static final int Participate_State_Fail = 5;//未按时参加活动
	
	public Long participationId;
	public String userNo;
	public Long activityId;
	public Integer participateType;
	public Date time;
	public Integer state;
	public Long getParticipationId() {
		return participationId;
	}
	public void setParticipationId(Long participationId) {
		this.participationId = participationId;
	}
	public String getUserId() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public Integer getParticipateType() {
		return participateType;
	}
	public void setParticipateType(Integer participateType) {
		this.participateType = participateType;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
}
