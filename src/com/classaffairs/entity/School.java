package com.classaffairs.entity;

public class School {
	public Long schoolId;
	public String schoolNo;
    public String name;
    public String abbreviation;
    public String executiveClassCadreMode;

	public Long getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
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
    public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public String getExecutiveClassCadreMode() {
		return executiveClassCadreMode;
	}
	public void setExecutiveClassCadreMode(String executiveClassCadreMode) {
		this.executiveClassCadreMode = executiveClassCadreMode;
	}
}
