package com.classaffairs.entity;

public class Department {
	public Long departmentId;
	public String departmentNo;
	public String schoolNo;
    public String name;
    public String departmentLeaderId;
    public String departmentLeaderName;
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentNo() {
		return departmentNo;
	}
	public void setDepartmentNo(String departmentNo) {
		this.departmentNo = departmentNo;
	}
	public String getSchoolNo() {
		return schoolNo;
	}
	public void setSchoolNo(String schoolNo) {
		this.schoolNo = schoolNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartmentLeaderId() {
		return departmentLeaderId;
	}
	public void setDepartmentLeaderId(String departmentLeaderId) {
		this.departmentLeaderId = departmentLeaderId;
	}
	public String getDepartmentLeaderName() {
		return departmentLeaderName;
	}
	public void setDepartmentLeaderName(String departmentLeaderName) {
		this.departmentLeaderName = departmentLeaderName;
	}
   
}
