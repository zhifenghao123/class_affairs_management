package com.classaffairs.entity;

import java.util.Date;

public class NoticeReadRecord {
    private Long noticeReadId;
    private Long noticeId;
    private String userNo;
    private Date readTime;
	public Long getNoticeReadId() {
		return noticeReadId;
	}
	public void setNoticeReadId(Long noticeReadId) {
		this.noticeReadId = noticeReadId;
	}
	public Long getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public Date getReadTime() {
		return readTime;
	}
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}
}
