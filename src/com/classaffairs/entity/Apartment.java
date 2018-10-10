/**
 * 
 */
package com.classaffairs.entity;

/**
 * @author Haozhifeng
 *
 */
public class Apartment {
	public static final int SEX_MALE = 1; //性别男
	public static final int SEX_FEMALE = 2; //性别女
	
	private Long apartmentId;
	private String apartmentNo;
	private Integer gender;
	public Long getApartmentId() {
		return apartmentId;
	}
	public void setApartmentId(Long apartmentId) {
		this.apartmentId = apartmentId;
	}
	public String getApartmentNo() {
		return apartmentNo;
	}
	public void setApartmentNo(String apartmentNo) {
		this.apartmentNo = apartmentNo;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}

}
