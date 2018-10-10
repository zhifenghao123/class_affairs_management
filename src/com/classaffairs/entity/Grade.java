/**
 * 
 */
package com.classaffairs.entity;

import java.util.Date;

/**
 * @author Haozhifeng
 *
 */
public class Grade {
	public String gradeNo;
	public Date enrollDate;

	public String getGradeNo() {
		return gradeNo;
	}
	public void setGradeNo(String gradeNo) {
		this.gradeNo = gradeNo;
	}
	public Date getEnrollDate() {
		return enrollDate;
	}
	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}
}
