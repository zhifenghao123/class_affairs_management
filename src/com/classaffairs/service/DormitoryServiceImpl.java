/**
 * 
 */
package com.classaffairs.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.classaffairs.dao.DormitoryDao;
import com.classaffairs.entity.Dormitory;
import com.classaffairs.entity.Dormitory;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;

/**
 * @author Haozhifeng
 *
 */
@Service
public class DormitoryServiceImpl implements DormitoryService {
	@Autowired
	private DormitoryDao itsDormitoryDao;
	/* (non-Javadoc)
	 * @see com.classaffairs.service.DormitoryService#addDormitory(com.classaffairs.entity.Dormitory)
	 */
	@Override
	public boolean addDormitory(Dormitory dormitory) {
		// TODO Auto-generated method stub
		try {
			int result = itsDormitoryDao.mSave(dormitory);
			return result == 1;
		} catch (DataAccessException dae) {
			Log.log.error("新增宿舍房间号操作数据库异常,", dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("新增宿舍房间号异常,", e);
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.DormitoryService#updateDormitory(com.classaffairs.entity.Dormitory)
	 */
	@Override
	public boolean updateDormitory(Dormitory dormitory) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			result = itsDormitoryDao.mUpdate(dormitory);
		}catch(DataAccessException dae){
			Log.log.error("修改宿舍房间号访问数据库异常，宿舍房间号名称：" + dormitory.getRoomNo(), dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("修改宿舍房间号异常，宿舍房间号名称：" + dormitory.getRoomNo() , e);
			e.printStackTrace();
		}
		return result == 1;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.DormitoryService#deleteDormitory(java.lang.Long)
	 */
	@Override
	public boolean deleteDormitory(Long dormitoryId) {
		// TODO Auto-generated method stub
		try{
			itsDormitoryDao.mDeleteById(dormitoryId);
			return true;
		}catch(DataAccessException dae){
			Log.log.error("删除宿舍房间号访问数据库异常，宿舍房间号名称Id：" + dormitoryId, dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("删除宿舍房间号异常，宿舍房间号名称Id：" + dormitoryId , e);
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.DormitoryService#findDormitoryByDormitoryId(java.lang.Long)
	 */
	@Override
	public Dormitory findDormitoryByDormitoryId(Long dormitoryId) {
		// TODO Auto-generated method stub
		Dormitory dormitory = null;
		try {
			dormitory = (Dormitory) itsDormitoryDao.mFindById(dormitoryId);
		} catch (DataAccessException dae) {
			Log.log.error("通过宿舍房间号id获取宿舍房间号访问数据库异常DormitoryId=" + dormitoryId, dae);

			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过宿舍房间号id获取宿舍房间号异常DormitoryId=" + dormitoryId, e);

			e.printStackTrace();
		}

		return dormitory;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.DormitoryService#findDormitoryByDormitoryNo(java.lang.String)
	 */
	@Override
	public Dormitory findDormitoryByDormitoryNo(String dormitoryNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Dormitory> getDormitorysByPageQuery(String roomNo, Integer page,
			int pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		if(roomNo != null)
			param.put("roomNo", "%" + roomNo + "%");

		Page<Dormitory> dormitoryPage = null;
		try {
			int recordOffset = (Integer.valueOf(page) - 1) * Integer.valueOf(pageSize);

			dormitoryPage = (Page<Dormitory>) itsDormitoryDao.mPageQuery("findDormitoryByFuzzyInformation", param, recordOffset, Integer.valueOf(pageSize));

		} catch (DataAccessException dae) {
			Log.log.error("通过参数（宿舍属性）分页获取宿舍操作数据库异常,宿舍名称：" + roomNo, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过参数（宿舍属性）分页获取宿舍异常,宿舍名称：" + roomNo, e);
			e.printStackTrace();
		}

		return dormitoryPage;
	}

}
