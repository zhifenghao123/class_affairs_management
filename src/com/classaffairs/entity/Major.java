package com.classaffairs.entity;

public class Major {
	public Long majorlId;
	public Long schoolId;
	public String majorlNo;
    public String name;

	public Long getMajorId() {
		return majorlId;
	}
	public void setMajorId(Long majorlId) {
		this.majorlId = majorlId;
	}
	public Long getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}
	public String getMajorNo() {
		return majorlNo;
	}
	public void setMajorNo(String majorlNo) {
		this.majorlNo = majorlNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
