/**
 * 
 */
package com.classaffairs.service;

import com.classaffairs.entity.OnlineBehavior;
import com.classaffairs.framework.sdp.orm.query.Page;

/**
 * @author Haozhifeng
 *
 */
public interface OnlineBehaviorService {

	boolean addOnlineBehavior(OnlineBehavior onlineBehavior);
	
	boolean updateOnlineBehavior(OnlineBehavior onlineBehavior);
	
	boolean deleteOnlineBehavior(Long onlineBehaviorId);
	
	OnlineBehavior findOnlineBehaviorByOnlineBehaviorId(Long onlineBehaviorId);
	
	public Page<OnlineBehavior> getOnlineBehaviorsByPageQuery(String userNo,Integer type,Integer page,int pageSize);
}
