package com.classaffairs.entity;

public class GraduationProject { 
	private Long graduationProjectId;
	private String graduationProjectGroup;
	private String studentNo;
	private String studentName;
	private String reseachTitle;
	private Float topicSelectingReportScore;
	private String guideTeacher;
	private String memo;
	public Long getGraduationProjectId() {
		return graduationProjectId;
	}
	public void setGraduationProjectId(Long graduationProjectId) {
		this.graduationProjectId = graduationProjectId;
	}
	public String getGraduationProjectGroup() {
		return graduationProjectGroup;
	}
	public void setGraduationProjectGroup(String graduationProjectGroup) {
		this.graduationProjectGroup = graduationProjectGroup;
	}
	public String getStudentNo() {
		return studentNo;
	}
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getReseachTitle() {
		return reseachTitle;
	}
	public void setReseachTitle(String reseachTitle) {
		this.reseachTitle = reseachTitle;
	}
	public Float getTopicSelectingReportScore() {
		return topicSelectingReportScore;
	}
	public void setTopicSelectingReportScore(Float topicSelectingReportScore) {
		this.topicSelectingReportScore = topicSelectingReportScore;
	}
	public String getGuideTeacher() {
		return guideTeacher;
	}
	public void setGuideTeacher(String guideTeacher) {
		this.guideTeacher = guideTeacher;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
