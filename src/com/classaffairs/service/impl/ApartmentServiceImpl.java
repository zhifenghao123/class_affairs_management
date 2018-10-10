/**
 * 
 */
package com.classaffairs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.classaffairs.dao.ApartmentDao;
import com.classaffairs.entity.Apartment;
import com.classaffairs.entity.Apartment;
import com.classaffairs.entity.Apartment;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.ApartmentService;

/**
 * @author Haozhifeng
 *
 */
@Service
public class ApartmentServiceImpl implements ApartmentService {
	@Autowired
	private ApartmentDao itsApartmentDao;
	/* (non-Javadoc)
	 * @see com.classaffairs.service.ApartmentService#addApartment(com.classaffairs.entity.Apartment)
	 */
	@Override
	public boolean addApartment(Apartment apartment) {
		// TODO Auto-generated method stub
		try {
			int result = itsApartmentDao.mSave(apartment);
			return result == 1;
		} catch (DataAccessException dae) {
			Log.log.error("新增公寓操作数据库异常,", dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("新增公寓异常,", e);
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.ApartmentService#updateApartment(com.classaffairs.entity.Apartment)
	 */
	@Override
	public boolean updateApartment(Apartment apartment) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			result = itsApartmentDao.mUpdate(apartment);
		}catch(DataAccessException dae){
			Log.log.error("修改公寓访问数据库异常，公寓名称：" + apartment.getApartmentNo(), dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("修改公寓异常，公寓名称：" + apartment.getApartmentNo() , e);
			e.printStackTrace();
		}
		return result == 1;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.ApartmentService#deleteApartment(java.lang.Long)
	 */
	@Override
	public boolean deleteApartment(Long apartmentId) {
		// TODO Auto-generated method stub
		try{
			itsApartmentDao.mDeleteById(apartmentId);
			return true;
		}catch(DataAccessException dae){
			Log.log.error("删除公寓访问数据库异常，公寓名称Id：" + apartmentId, dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("删除公寓异常，公寓名称Id：" + apartmentId , e);
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.ApartmentService#findApartmentByApartmentId(java.lang.Long)
	 */
	@Override
	public Apartment findApartmentByApartmentId(Long apartmentId) {
		// TODO Auto-generated method stub
		Apartment apartment = null;
		try {
			apartment = (Apartment) itsApartmentDao.mFindById(apartmentId);
		} catch (DataAccessException dae) {
			Log.log.error("通过公寓id获取公寓访问数据库异常ApartmentId=" + apartmentId, dae);

			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过公寓id获取公寓异常ApartmentId=" + apartmentId, e);

			e.printStackTrace();
		}

		return apartment;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.ApartmentService#findApartmentByApartmentNo(java.lang.String)
	 */
	@Override
	public Apartment findApartmentByApartmentNo(String apartmentNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Apartment> getApartmentsByPageQuery(String apartmentNo, Integer gender, Integer page,
			int pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> param = new HashMap<String, Object>();
		if(apartmentNo != null)
			param.put("apartmentNo", "%" + apartmentNo + "%");
		param.put("gender", gender);
		Page<Apartment> apartmentPage = null;
		try {
			int recordOffset = (Integer.valueOf(page) - 1) * Integer.valueOf(pageSize);

			apartmentPage = (Page<Apartment>) itsApartmentDao.mPageQuery("findApartmentByFuzzyInformation", param, recordOffset, Integer.valueOf(pageSize));

		} catch (DataAccessException dae) {
			Log.log.error("通过参数（公寓属性）分页获取公寓操作数据库异常,公寓名称：" + apartmentNo, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过参数（公寓属性）分页获取公寓异常,公寓名称：" + apartmentNo, e);
			e.printStackTrace();
		}

		return apartmentPage;
	}

	@Override
	public List<Apartment> getAllApartments() {
		return (List<Apartment>) itsApartmentDao.mFindAll();
	}

}
