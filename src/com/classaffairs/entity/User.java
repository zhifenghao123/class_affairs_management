/**
 * 
 */
package com.classaffairs.entity;

import org.springframework.stereotype.Component;

/**
 * @author Haozhifeng
 *
 */
@Component
public class User {
	public long userId;
	public String userAccountNo;
	public String password;
    public int isCommonStudent;
    public int isLaboratoryLeader;
    public int isInstituteLeader;
    public int isSchoolLeader;
    public int isAdministrator;
    public int isSuperAdministrator;
    public int state;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserAccountNo() {
		return userAccountNo;
	}
	public void setUserAccountNo(String userAccountNo) {
		this.userAccountNo = userAccountNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getIsCommonStudent() {
		return isCommonStudent;
	}
	public void setIsCommonStudent(int isCommonStudent) {
		this.isCommonStudent = isCommonStudent;
	}
	public int getIsLaboratoryLeader() {
		return isLaboratoryLeader;
	}
	public void setIsLaboratoryLeader(int isLaboratoryLeader) {
		this.isLaboratoryLeader = isLaboratoryLeader;
	}
	public int getIsInstituteLeader() {
		return isInstituteLeader;
	}
	public void setIsInstituteLeader(int isInstituteLeader) {
		this.isInstituteLeader = isInstituteLeader;
	}
	public int getIsSchoolLeader() {
		return isSchoolLeader;
	}
	public void setIsSchoolLeader(int isSchoolLeader) {
		this.isSchoolLeader = isSchoolLeader;
	}
	public int getIsAdministrator() {
		return isAdministrator;
	}
	public void setIsAdministrator(int isAdministrator) {
		this.isAdministrator = isAdministrator;
	}
	public int getIsSuperAdministrator() {
		return isSuperAdministrator;
	}
	public void setIsSuperAdministrator(int isSuperAdministrator) {
		this.isSuperAdministrator = isSuperAdministrator;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
}
