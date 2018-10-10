package com.classaffairs.entity;

import java.util.HashSet;
import java.util.Set;

public class Application {
	public Long applicationId;
    public String displayName;
    public String url;
    public String iconPath;
    public String entryAuthorityIds;
    public Integer flag;
    public Long parentApplicationId;//NULL-应用    0-菜单   1-应用菜单
    public Integer type;
    
    private Set<Application> application = new HashSet(0);
    
	public Long getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIconPath() {
		return iconPath;
	}
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	public String getEntryAuthorityIds() {
		return entryAuthorityIds;
	}
	public void setEntryAuthorityIds(String entryAuthorityIds) {
		this.entryAuthorityIds = entryAuthorityIds;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Long getParentApplicationId() {
		return parentApplicationId;
	}
	public void setParentApplicationId(Long parentApplicationId) {
		this.parentApplicationId = parentApplicationId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Set<Application> getApplication() {
		return application;
	}
	public void setApplication(Set<Application> application) {
		this.application = application;
	}
}
