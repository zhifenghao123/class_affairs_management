/**
 * 
 */
package com.classaffairs.service;

import java.util.List;

import com.classaffairs.entity.Laboratory;
import com.classaffairs.framework.sdp.orm.query.Page;

/**
 * @author Haozhifeng
 *
 */
public interface LaboratoryService {

boolean addLaboratory(Laboratory laboratory);
	
	boolean updateLaboratory(Laboratory laboratory);
	
	boolean deleteLaboratory(Long laboratoryId);
	
	public Laboratory findLaboratoryByLaboratoryId(Long laboratoryId);
	
	public Laboratory findLaboratoryByLaboratoryNo(String laboratoryNo);
	
	public Page<Laboratory> getLaboratorysByPageQuery(String schoolNo, Integer page,int pageSize);
	
	public List<Laboratory> findLaboratoryListBySchoolNo(String schoolNo);
}
