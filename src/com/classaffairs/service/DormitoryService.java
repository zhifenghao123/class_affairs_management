/**
 * 
 */
package com.classaffairs.service;

import com.classaffairs.entity.Dormitory;
import com.classaffairs.entity.Dormitory;
import com.classaffairs.framework.sdp.orm.query.Page;

/**
 * @author Haozhifeng
 *
 */
public interface DormitoryService {

	boolean addDormitory(Dormitory dormitory);
	
	boolean updateDormitory(Dormitory dormitory);
	
	boolean deleteDormitory(Long dormitoryId);
	
	Dormitory findDormitoryByDormitoryId(Long dormitoryId);
	
	Dormitory findDormitoryByDormitoryNo(String dormitoryNo);
	
	public Page<Dormitory> getDormitorysByPageQuery(String roomNo, Integer page,int pageSize);
}
