package com.classaffairs.entity;

import java.util.Date;

public class Notice {
    private Long noticeId;
    private Integer type;
    private String receivingUnitNo;
    private String title;
    private String content;
    private String enclosureFile;
    private String path;
    private Float size;
    private String publisherNo;
    private String publisherName;
    private Date publishTime;
	public Long getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getReceivingUnitNo() {
		return receivingUnitNo;
	}
	public void setReceivingUnitNo(String receivingUnitNo) {
		this.receivingUnitNo = receivingUnitNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getEnclosureFile() {
		return enclosureFile;
	}
	public void setEnclosureFile(String enclosureFile) {
		this.enclosureFile = enclosureFile;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Float getSize() {
		return size;
	}
	public void setSize(Float size) {
		this.size = size;
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
