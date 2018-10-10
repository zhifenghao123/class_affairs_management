package com.classaffairs.service;

import java.util.List;

import com.classaffairs.entity.Application;
import com.classaffairs.framework.sdp.orm.query.Page;

public interface ApplicationService {
	
	boolean addApplication(Application application);
	
	boolean updateApplication(Application application);
	
	boolean deleteApplication(Long applicationId);
	
	Application findApplicationByApplicationId(Long applicationId);
	
	List<Application> finAllApplication();

	/**
	 * 通过应用链接获取应用对象
	 * @param displayname - 应用链接
	 * @return Application - 应用对象
	 */
	public Application getApplicationsByUrl(String url);
	/**
	 * 通过父节点id获取其子节点应用
	 * @param pid - 父节点id
	 * @return 子节点应用对象
	 */
	public List<Application> getApplicationByPareantAppId(Long pareantAppId);
	
	public Page<Application> getAllApplications(String page, String rows, String displayName);
	
	public Application getApplicationsByDisplayName(String displayname);
}
