/**
 * 
 */
package com.classaffairs.entity;

/**
 * @author Haozhifeng
 *
 */
public class Dormitory {
	private Long dormitoryId;
	private String roomNo;
	private Long apartmentId;
	private String dormitoryGender;
	public Long getDormitoryId() {
		return dormitoryId;
	}
	public void setDormitoryId(Long dormitoryId) {
		this.dormitoryId = dormitoryId;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public Long getApartmentId() {
		return apartmentId;
	}
	public void setApartmentId(Long apartmentId) {
		this.apartmentId = apartmentId;
	}
	public String getDormitoryGender() {
		return dormitoryGender;
	}
	public void setDormitoryGender(String dormitoryGender) {
		this.dormitoryGender = dormitoryGender;
	}
}
