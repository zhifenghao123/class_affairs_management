/**
 * 
 */
package com.classaffairs.entity;

import java.util.Date;

/**
 * @author Haozhifeng
 *
 */
public class Student {
	
	public static final int SEX_MALE = 1; //性别男
	public static final int SEX_FEMALE = 2; //性别女
	
	public static final int POLITICALLANDSCAPE_CCP = 1;//中国共产党党员
	public static final int POLITICALLANDSCAPE_PROBATIONARY_CCP = 2;//中国共产党预备党员
	public static final int POLITICALLANDSCAPE_CCYL = 3;//中国共产主义青年团团员
	public static final int POLITICALLANDSCAPE_RCCK = 4;//中国国民党革命委员会会员
	public static final int POLITICALLANDSCAPE_CDA = 5;//中国民主同盟盟员
	public static final int POLITICALLANDSCAPE_CDNCA = 6;//中国民主建国会会员
	public static final int POLITICALLANDSCAPE_CAPD = 7;//中国民主促进会会员
	public static final int POLITICALLANDSCAPE_CPWDP = 8;//中国农工民主党党员
	public static final int POLITICALLANDSCAPE_CZGD = 9;//中国致公党党员
	public static final int POLITICALLANDSCAPE_JSS = 10;//九三学社社员
	public static final int POLITICALLANDSCAPE_TWDSGL = 11;//台湾民主自治同盟盟员
	public static final int POLITICALLANDSCAPE_NPD = 12;//无党派民主人士
	public static final int POLITICALLANDSCAPE_MASS = 13;//群众
	
	public static final int ACCESSTYPE_RECOMMEND = 1; //入学方式：推免
	public static final int ACCESSTYPE_RECOMMEND_COUNSOLER = 2; //入学方式：推免"2+2"
	public static final int ACCESSTYPE_GENERALEXAM = 3; //入学方式：统考
	
	public static final int STATE_NORMAL = 1; //状态：正常
	public static final int STATE_ABNORMAL = 2; //状态：未正常办理入学手续
	
	public static final int INFORMATION_AUTHORITY_PUBLIC = 1;//信息的权限：公开
	public static final int INFORMATION_AUTHORITY_SECRECY = 2;//信息的权限：公开
	
	public static final int CULTIVATIONTYPE_ACADEMIC = 1;//培养类别：学术性研究生
	public static final int CULTIVATIONTYPE_PROFESSIONAL = 2;//培养类别：专业性研究生
	
	//学生Id
	public Long studentId;
	//学号
	public String studentNo;
	//密码
	public String password;
	//姓名
	public String name;
	//性别
	public Integer sex;
	//生日
	public Date birthday;
	//出生地
	public String birthplace;
	//家庭详细住址
	public String homeAddress;
	//名族
	public String ethnic;
	//政治背景
	public Integer politicalLandscape;
	//身份证号
	public String IdCardNo;
	//入学方式
	public Integer accessType;
	//本科毕业院校
	public String undergraduateCollege;
	//本科毕业日期
	public Date undergraduateEnddate;
	//年级号 
	public String gradeNo;
	//所在学院编号
	public String schoolNo;
	//所在专业编号
	public String majorNo;
	//培养类别
	public Integer cultivationType;
	//行政班级
	public String executiveClassName;
	//系（所） 号
	public String departmentNo;
	//教研室号
	public String laboratoryNo;
	//导师姓名
	public String tutorName;
	//研究方向
	public String researchDirection;
	//学位课成绩
	public Float degreeCourseScore;
	//学位课排名
	public Integer degreeCourseRank;
	//总课程成绩
	public Float allCourseScore;
	//总课程排名
	public Integer allCourseRank;
	//英语四级成绩
	public Integer cet4Score;
	//英语六级成绩
	public Integer cet6Score;
	//学生当前状态，   0-否-未正常报到   1-是-正常入学
	public Integer state;
	//是否是实验室负责人
	public Integer isLaboratorLeader;
	//是否是研究所负责人
	public Integer isDepartmentLeader;
	//是否是班长
	public Integer isMonitor;
	//是否是学院负责人
	public Integer isSchoolLeader;
	//常用联系电话
	public String telephoneNoFrequse;
	//常用联系电话是否公开
	public Integer telephoneNoFrequseIsPublic;
	//备用联系电话
	public String telephoneNoBackup;
	//备用联系电话是否公开
	public Integer telephoneNoBackupIsPublic;
	//电子邮件
	public String emaiNo;
	//电子邮件是否公开
	public Integer emaiNoIspublic;
	//qq号
	public String qqNo;
	//qq号是否公开
	public Integer qqNoIsPublic;
	//微信号
	public String wechatNo;
	//微信号是否公开
	public Integer wechatNoIsPublic;
	//新浪微博号
	public String sinaweiboNo;
	//新浪微博号是否公开
	public Integer sinaweiboNoIsPublic;
	//公寓楼好
	public String apartmentNo;
	//房间号
	public String roomNo;
	
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public String getStudentNo() {
		return studentNo;
	}
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getBirthplace() {
		return birthplace;
	}
	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}
	public String getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	public String getEthnic() {
		return ethnic;
	}
	public void setEthnic(String ethnic) {
		this.ethnic = ethnic;
	}
	public Integer getPoliticalLandscape() {
		return politicalLandscape;
	}
	public void setPoliticalLandscape(Integer politicalLandscape) {
		this.politicalLandscape = politicalLandscape;
	}
	public String getIdCardNo() {
		return IdCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		IdCardNo = idCardNo;
	}
	public Integer getAccessType() {
		return accessType;
	}
	public void setAccessType(Integer accessType) {
		this.accessType = accessType;
	}
	public String getUndergraduateCollege() {
		return undergraduateCollege;
	}
	public void setUndergraduateCollege(String undergraduateCollege) {
		this.undergraduateCollege = undergraduateCollege;
	}
	public Date getUndergraduateEnddate() {
		return undergraduateEnddate;
	}
	public void setUndergraduateEnddate(Date date) {
		this.undergraduateEnddate = date;
	}
	public String getResearchDirection() {
		return researchDirection;
	}
	public void setResearchDirection(String researchDirection) {
		this.researchDirection = researchDirection;
	}
	public String getGradeNo() {
		return gradeNo;
	}
	public void setGradeNo(String gradeNo) {
		this.gradeNo = gradeNo;
	}
	public String getSchoolNo() {
		return schoolNo;
	}
	public void setSchoolNo(String schoolNo) {
		this.schoolNo = schoolNo;
	}

	public String getMajorNo() {
		return majorNo;
	}
	public void setMajorNo(String majorNo) {
		this.majorNo = majorNo;
	}
	public Integer getCultivationType() {
		return cultivationType;
	}
	public void setCultivationType(Integer cultivationType) {
		this.cultivationType = cultivationType;
	}
	public String getExecutiveClassName() {
		return executiveClassName;
	}
	public void setExecutiveClassName(String executiveClassName) {
		this.executiveClassName = executiveClassName;
	}
	public String getDepartmentNo() {
		return departmentNo;
	}
	public void setDepartmentNo(String departmentNo) {
		this.departmentNo = departmentNo;
	}
	public String getLaboratoryNo() {
		return laboratoryNo;
	}
	public void setLaboratoryNo(String laboratoryNo) {
		this.laboratoryNo = laboratoryNo;
	}
	public String getTutorName() {
		return tutorName;
	}
	public void setTutorName(String tutorName) {
		this.tutorName = tutorName;
	}
	public Float getDegreeCourseScore() {
		return degreeCourseScore;
	}
	public void setDegreeCourseScore(Float degreeCourseScore) {
		this.degreeCourseScore = degreeCourseScore;
	}
	public Integer getDegreeCourseRank() {
		return degreeCourseRank;
	}
	public void setDegreeCourseRank(Integer degreeCourseRank) {
		this.degreeCourseRank = degreeCourseRank;
	}
	public Float getAllCourseScore() {
		return allCourseScore;
	}
	public void setAllCourseScore(Float allCourseScore) {
		this.allCourseScore = allCourseScore;
	}
	public Integer getAllCourseRank() {
		return allCourseRank;
	}
	public void setAllCourseRank(Integer allCourseRank) {
		this.allCourseRank = allCourseRank;
	}
	public Integer getCet4Score() {
		return cet4Score;
	}
	public void setCet4Score(Integer cet4Score) {
		this.cet4Score = cet4Score;
	}
	public Integer getCet6Score() {
		return cet6Score;
	}
	public void setCet6Score(Integer cet6Score) {
		this.cet6Score = cet6Score;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getIsLaboratorLeader() {
		return isLaboratorLeader;
	}
	public void setIsLaboratorLeader(Integer isLaboratorLeader) {
		this.isLaboratorLeader = isLaboratorLeader;
	}
	public Integer getIsDepartmentLeader() {
		return isDepartmentLeader;
	}
	public void setIsDepartmentLeader(Integer isDepartmentLeader) {
		this.isDepartmentLeader = isDepartmentLeader;
	}
	public Integer getIsMonitor() {
		return isMonitor;
	}
	public void setIsMonitor(Integer isMonitor) {
		this.isMonitor = isMonitor;
	}
	public Integer getIsSchoolLeader() {
		return isSchoolLeader;
	}
	public void setIsSchoolLeader(Integer isSchoolLeader) {
		this.isSchoolLeader = isSchoolLeader;
	}
	public String getTelephoneNoFrequse() {
		return telephoneNoFrequse;
	}
	public void setTelephoneNoFrequse(String telephoneNoFrequse) {
		this.telephoneNoFrequse = telephoneNoFrequse;
	}
	public Integer getTelephoneNoFrequseIsPublic() {
		return telephoneNoFrequseIsPublic;
	}
	public void setTelephoneNoFrequseIsPublic(Integer telephoneNoFrequseIsPublic) {
		this.telephoneNoFrequseIsPublic = telephoneNoFrequseIsPublic;
	}
	public String getTelephoneNoBackup() {
		return telephoneNoBackup;
	}
	public void setTelephoneNoBackup(String telephoneNoBackup) {
		this.telephoneNoBackup = telephoneNoBackup;
	}
	public Integer getTelephoneNoBackupIsPublic() {
		return telephoneNoBackupIsPublic;
	}
	public void setTelephoneNoBackupIsPublic(Integer telephoneNoBackupIsPublic) {
		this.telephoneNoBackupIsPublic = telephoneNoBackupIsPublic;
	}
	public String getEmaiNo() {
		return emaiNo;
	}
	public void setEmaiNo(String emaiNo) {
		this.emaiNo = emaiNo;
	}
	public Integer getEmaiNoIspublic() {
		return emaiNoIspublic;
	}
	public void setEmaiNoIspublic(Integer emaiNoIspublic) {
		this.emaiNoIspublic = emaiNoIspublic;
	}
	public String getQqNo() {
		return qqNo;
	}
	public void setQqNo(String qqNo) {
		this.qqNo = qqNo;
	}
	public Integer getQqNoIsPublic() {
		return qqNoIsPublic;
	}
	public void setQqNoIsPublic(Integer qqNoIsPublic) {
		this.qqNoIsPublic = qqNoIsPublic;
	}
	public String getWechatNo() {
		return wechatNo;
	}
	public void setWechatNo(String wechatNo) {
		this.wechatNo = wechatNo;
	}
	public Integer getWechatNoIsPublic() {
		return wechatNoIsPublic;
	}
	public void setWechatNoIsPublic(Integer wechatNoIsPublic) {
		this.wechatNoIsPublic = wechatNoIsPublic;
	}
	public String getSinaweiboNo() {
		return sinaweiboNo;
	}
	public void setSinaweiboNo(String sinaweiboNo) {
		this.sinaweiboNo = sinaweiboNo;
	}
	public Integer getSinaweiboNoIsPublic() {
		return sinaweiboNoIsPublic;
	}
	public void setSinaweiboNoIsPublic(Integer sinaweiboNoIsPublic) {
		this.sinaweiboNoIsPublic = sinaweiboNoIsPublic;
	}
	public String getApartmentNo() {
		return apartmentNo;
	}
	public void setApartmentNo(String apartmentNo) {
		this.apartmentNo = apartmentNo;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
}
