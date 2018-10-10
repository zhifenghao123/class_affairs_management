package com.classaffairs.entity;

import java.util.Date;

public class Administrator {

	public Long administratorId;
	public String administratorNo;
	public String name;
    public String password;
    public Integer type;
    public Date createdate;
    public Date lastdate;
    public Integer state;
    public String managingSchool;
    public Long roleId;
    
	public Long getAdministratorId() {
		return administratorId;
	}
	public void setAdministratorId(Long administratorId) {
		this.administratorId = administratorId;
	}
	public String getAdministratorNo() {
		return administratorNo;
	}
	public void setAdministratorNo(String administratorNo) {
		this.administratorNo = administratorNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public Date getLastdate() {
		return lastdate;
	}
	public void setLastdate(Date lastdate) {
		this.lastdate = lastdate;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getManagingSchool() {
		return managingSchool;
	}
	public void setManagingSchool(String managingSchool) {
		this.managingSchool = managingSchool;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}
