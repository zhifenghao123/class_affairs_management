/**
 * 
 */
package com.classaffairs.entity;

import com.classaffairs.common.region.AbstractNode;

/**
 * @author Haozhifeng
 *
 */
public class Region extends AbstractNode<Region> {
	public static final int COUNTRY = 1;//type为1，表示中国
	public static final int PROVINCE = 2;//type为2，表示省
	public static final int CITY = 3;//type为3，表示市
	public static final int COUNTYORDISTRICT = 4;//type为4，表示县区
	public static final int OPENED = 1;//state为1，表示该区域已开放
	public static final int CLOSED = 0;//state为0，表示该区域未开放
	
	public Long regionId;
	public Integer type;
	public String code;
	public String name;
	public String englishName;
	public Long parentRegionId;
	public Integer state;
	public Long getRegionId() {
		return regionId;
	}
	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public Long getParentRegionId() {
		return parentRegionId;
	}
	public void setParentRegionId(Long parentRegionId) {
		this.parentRegionId = parentRegionId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
}
