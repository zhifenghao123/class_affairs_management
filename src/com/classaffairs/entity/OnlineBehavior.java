/**
 * 
 */
package com.classaffairs.entity;

import java.util.Date;

/**
 * @author Haozhifeng
 *
 */
public class OnlineBehavior {
	public static final int Delete_No = 0;
	public static final int Deleter_Yes = 1;
	
	public static final int Type_Login = 1;
	public static final int Type_Update = 2;
	
	public Long onlineBehaviorId;
	public String userNo;
	public String userIp;
	public String onlineBehaviorName;
	public Integer type;
	public Integer state;
	public Date recordTime;
	public Long getOnlineBehaviorId() {
		return onlineBehaviorId;
	}
	public void setOnlineBehaviorId(Long onlineBehaviorId) {
		this.onlineBehaviorId = onlineBehaviorId;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getOnlineBehaviorName() {
		return onlineBehaviorName;
	}
	public void setOnlineBehaviorName(String onlineBehaviorName) {
		this.onlineBehaviorName = onlineBehaviorName;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
}
