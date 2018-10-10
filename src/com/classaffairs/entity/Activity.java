/**
 * 
 */
package com.classaffairs.entity;

import java.util.Date;

/**
 * @author Haozhifeng
 *
 */
public class Activity {
	public Long activityId;
	public String name;
	public String description;
	public String responsorNo;
	public String responsorName;
	public Integer type;
	public Date starTime;
	public Date endTime;
	public Date enrollEndTime;
	public String publisherNo;
	public String publisherName;
	public Date publishTime;
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getResponsorNo() {
		return responsorNo;
	}
	public void setResponsorNo(String responsorNo) {
		this.responsorNo = responsorNo;
	}
	public String getResponsorName() {
		return responsorName;
	}
	public void setResponsorName(String responsorName) {
		this.responsorName = responsorName;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getStarTime() {
		return starTime;
	}
	public void setStarTime(Date starTime) {
		this.starTime = starTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getEnrollEndTime() {
		return enrollEndTime;
	}
	public void setEnrollEndTime(Date enrollEndTime) {
		this.enrollEndTime = enrollEndTime;
	}
	public String getPublisherNo() {
		return publisherNo;
	}
	public void setPublisherNo(String publisherNo) {
		this.publisherNo = publisherNo;
	}
	public String getPublisherName() {
		return publisherName;
	}
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
}
