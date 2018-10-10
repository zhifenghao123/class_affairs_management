package com.classaffairs.entity;

public class PrimaryKey {
	public static final int APPLICATION_OBJECTTYPE = 1;//应用对象类型
	public static final int School_OBJECTTYPE = 2;//学院对象类型
	public static final int Major_OBJECTTYPE = 3;//专业对象类型
	public static final int ExecutiveClass_OBJECTTYPE = 4;//专业对象类型
	public static final int Student_OBJECTTYPE = 5;//对象类型
	public static final int BLOG_OBJECTTYPE = 6;//对象类型
	public static final int ACTIVITY_OBJECTTYPE = 7;
	public static final int GRADE_OBJECTTYPE = 8;
	public static final int NOTICE_OBJECTTYPE = 9;
	public static final int APARTMENT_OBJECTTYPE = 10;
	public static final int DORMITORY_OBJECTTYPE = 11;
	public static final int Department_OBJECTTYPE = 12;
	public static final int Laboratory_OBJECTTYPE = 13;
	public static final int Participation_OBJECTTYPE = 14;
	public static final int OperationLog_OBJECTTYPE = 15;
	public static final int OnlineBehavior_ObjectType = 16;
	public static final int GraduationProject_OBJECTTYPE = 17;
    private Integer primaryKeyId;
    private Integer objectType;
    private Long objectId;
	public Integer getPrimarykeyId() {
		return primaryKeyId;
	}
	public void setPrimarykeyId(Integer primarykeyId) {
		this.primaryKeyId = primarykeyId;
	}
	public Integer getObjectType() {
		return objectType;
	}
	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}
	public Long getObjectId() {
		return objectId;
	}
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
}
