package com.classaffairs.entity;

public class Laboratory {
	public Long laboratoryId;
	public String laboratoryNo;
	public String schoolNo;
    public String address;
    public String laboratoryLeaderId;
    public String laboratoryLeaderName;
	public Long getLaboratoryId() {
		return laboratoryId;
	}
	public void setLaboratoryId(Long laboratoryId) {
		this.laboratoryId = laboratoryId;
	}
	public String getLaboratoryNo() {
		return laboratoryNo;
	}
	public void setLaboratoryNo(String laboratoryNo) {
		this.laboratoryNo = laboratoryNo;
	}
	public String getSchoolNo() {
		return schoolNo;
	}
	public void setSchoolNo(String schoolNo) {
		this.schoolNo = schoolNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLaboratoryLeaderId() {
		return laboratoryLeaderId;
	}
	public void setLaboratoryLeaderId(String laboratoryLeaderId) {
		this.laboratoryLeaderId = laboratoryLeaderId;
	}
	public String getLaboratoryLeaderName() {
		return laboratoryLeaderName;
	}
	public void setLaboratoryLeaderName(String laboratoryLeaderName) {
		this.laboratoryLeaderName = laboratoryLeaderName;
	}
	
}
