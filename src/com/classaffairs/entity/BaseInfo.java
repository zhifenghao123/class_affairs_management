package com.classaffairs.entity;

import java.io.Serializable;

public class BaseInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2305552794857733398L;

	/**
	 * base_info.base_info_id (基础信息表对象id)
	 * @ibatorgenerated 2013-11-26 21:47:18
	 */
	private Long baseInfoId;

	/**
	 * base_info.base_type_id (信息类型码)
	 * @ibatorgenerated 2013-11-26 21:47:18
	 */
	private Long baseTypeId;

	/**
	 * base_info.code (顺序代码)
	 * @ibatorgenerated 2013-11-26 21:47:18
	 */
	private Integer code;

	/**
	 * base_info.display (描述)
	 * @ibatorgenerated 2013-11-26 21:47:18
	 */
	private String display;

	public Long getBaseInfoId() {
		return baseInfoId;
	}

	public void setBaseInfoId(Long baseInfoId) {
		this.baseInfoId = baseInfoId;
	}

	public Long getBaseTypeId() {
		return baseTypeId;
	}

	public void setBaseTypeId(Long baseTypeId) {
		this.baseTypeId = baseTypeId;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

}