package com.classaffairs.entity;

import java.io.Serializable;

public class BaseType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2948661849625081177L;

	/**
	 * base_type.base_type_id (信息类型码)
	 * @ibatorgenerated 2013-11-26 21:47:16
	 */
	private Long baseTypeId;

	/**
	 * base_type.typeName (类型名)
	 * @ibatorgenerated 2013-11-26 21:47:16
	 */
	private String typeName;

	/**
	 * base_type.memo (备注)
	 * @ibatorgenerated 2013-11-26 21:47:16
	 */
	private String memo;

	public Long getBaseTypeId() {
		return baseTypeId;
	}

	public void setBaseTypeId(Long baseTypeId) {
		this.baseTypeId = baseTypeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}