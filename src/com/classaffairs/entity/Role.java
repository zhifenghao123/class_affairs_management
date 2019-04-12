package com.classaffairs.entity;

import java.util.Date;

public class Role {
	public static final int ONUSE = 1;//角色正在使用中
	public static final int UNUSE = 2;//角色没有使用
	public Long roleId;
	public String name;
	public Integer creatorType;
	public Long creatorId;
	public Date createDate;
	public Date lastUpdateDate;
	public String authorityCode;
	public Integer state;
	
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCreatorType() {
		return creatorType;
	}
	public void setCreatorType(Integer creatorType) {
		this.creatorType = creatorType;
	}
	public Long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getAuthorityCode() {
		return authorityCode;
	}
	public void setAuthorityCode(String authorityCode) {
		this.authorityCode = authorityCode;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
}
