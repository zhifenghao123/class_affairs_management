/**
 * 
 */
package com.classaffairs.service;

import java.util.List;
import java.util.Map;

import com.classaffairs.entity.BaseType;

/**
 * @author Haozhifeng
 *
 */
public interface BaseTypeService {
	
	public boolean addBaseType(BaseType baseType);
	
	public boolean updateBaseType(BaseType baseType);
	
	public boolean deleteBaseType(Long baseTypeId);
	
	public BaseType findBaseTypeByBaseTypeId(Long baseTypeId);
	
	public List<Map<String,Object>> findAllBaseType();
}
