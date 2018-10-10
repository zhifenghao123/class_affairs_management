/**
 * 
 */
package com.classaffairs.service;

import java.util.List;

import com.classaffairs.entity.BaseInfo;
import com.classaffairs.framework.sdp.orm.query.Page;

/**
 * @author Haozhifeng
 *
 */
public interface BaseInfoService {
	
	public boolean addBaseInfo(BaseInfo baseInfo);
	
	public boolean updateBaseInfo(BaseInfo baseInfo);
	
	public boolean deleteBaseInfo(Long baseInfoId);
	
	public BaseInfo findBaseInfoById(Long baseInfoId);
	
	public  String  getDisplaysByTypeAndCodes( Long type, String codes);
	
	public BaseInfo getCodesByType(Long typeid);
	
	public List<BaseInfo> findBaseInfoByBaseTypeId(Long baseTypeId);
	
	public List<BaseInfo> findAllBaseInfo();
	
	public Page<BaseInfo> getBaseInfosByPageQuery(String page,String pageSize);
	
}
