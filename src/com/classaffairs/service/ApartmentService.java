/**
 * 
 */
package com.classaffairs.service;

import java.util.List;

import com.classaffairs.entity.Apartment;
import com.classaffairs.entity.Apartment;
import com.classaffairs.entity.School;
import com.classaffairs.framework.sdp.orm.query.Page;

/**
 * @author Haozhifeng
 *
 */
public interface ApartmentService {
	
	boolean addApartment(Apartment apartment);
	
	boolean updateApartment(Apartment apartment);
	
	boolean deleteApartment(Long apartmentId);
	
	Apartment findApartmentByApartmentId(Long apartmentId);
	
	Apartment findApartmentByApartmentNo(String apartmentNo);
	
	public Page<Apartment> getApartmentsByPageQuery(String name, Integer gender,Integer page,int pageSize);
	
	/**
	 * 获取所有的权限类型
	 * @return 权限类型List
	 */
	public List<Apartment> getAllApartments();
}
