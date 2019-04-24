package com.classaffairs.entity;

import java.util.Date;

public class Authority {
	public final static int ONUSE = 1;//权限正在使用
	public final static int UNUSE = 2;//权限未使用
	
	public Long authorityId;
    public String name;
    public Date createDate;
    public Date updateDate;
    public Integer state;
	public Long getAuthorityId() {
		return authorityId;
	}
	public void setAuthorityId(Long authorityId) {
		this.authorityId = authorityId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getupdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
}
