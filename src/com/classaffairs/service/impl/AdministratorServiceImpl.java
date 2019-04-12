package com.classaffairs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.classaffairs.dao.AdministratorDao;
import com.classaffairs.entity.Administrator;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.AdministratorService;
@Service
public class AdministratorServiceImpl implements AdministratorService {
	@Autowired
	private AdministratorDao itsAdministratorDao;

	@Override
	public boolean addAdministrator(Administrator administrator) {
		int result = 0;
		try{
			result = itsAdministratorDao.mSave(administrator);
		}catch(DataAccessException dae){
			Log.log.error("新增管理员访问数据库异常，管理员名称：" + administrator.getName(), dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("新增管理员异常，管理员名称：" + administrator.getName() , e);
			e.printStackTrace();
		}
		return result == 1;
	}

	@Override
	public boolean updateAdministrator(Administrator administrator) {
		int result = 0;
		try{
			result = itsAdministratorDao.mUpdate(administrator);
		}catch(DataAccessException dae){
			Log.log.error("修改管理员访问数据库异常，管理员名称：" + administrator.getName(), dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("修改管理员异常，管理员名称：" + administrator.getName() , e);
			e.printStackTrace();
		}
		return result == 1;
	}

	@Override
	public boolean deleteAdministrator(Long administratorId) {
		try{
			itsAdministratorDao.mDeleteById(administratorId);
			return true;
		}catch(DataAccessException dae){
			Log.log.error("删除管理员访问数据库异常，管理员名称Id：" + administratorId, dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("删除管理员异常，管理员名称Id：" + administratorId , e);
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Administrator findAdministratorByAdministratorId(Long administratorId) {
		Administrator Administrator = null;
		try {
			Administrator = (Administrator) itsAdministratorDao.mFindById(administratorId);
		} catch (DataAccessException dae) {
			Log.log.error("通过管理员id获取管理员访问数据库异常AdministratorId=" + administratorId, dae);

			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过管理员id获取管理员异常AdministratorId=" + administratorId, e);

			e.printStackTrace();
		}

		return Administrator;
	}

	@Override
	public Administrator findAdministratorByAdministratorNo(
			String administratorNo) {
		Administrator administrator = (Administrator) itsAdministratorDao.mSelectOne("findAdministratorByAdministratorNo", administratorNo);
		return administrator;
	}

	@Override
	public Page<Administrator> pageQueryAdministatorsByName(String name,
			String page, String pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("name", "%" + name + "%");
		try {
			int recordOffset = (Integer.valueOf(page) - 1) * Integer.valueOf(pageSize);

			Page<Administrator> administrators = (Page<Administrator>) itsAdministratorDao.mPageQuery("findAllByName", param, recordOffset, Integer.valueOf(pageSize));

			return administrators;
		} catch (DataAccessException e) {
			e.printStackTrace();
			Log.log.error("分页获得内部成员数据库访问异常", e);
			return null;
		} catch (Exception ee) {
			Log.log.error("分页获得所有内部成员异常", ee);
			ee.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Administrator> getAdministratorsByRoleId(Long roleId) {
		List<Administrator> administrators = null;
		try {
			administrators = (List<Administrator>) itsAdministratorDao.mFind("getByRoleId", roleId);
			return administrators;
		} catch (DataAccessException e) {
			Log.log.error("通过角色Id获得内部成员数据库访问异常,角色id:" + roleId, e);
			e.printStackTrace();

		} catch (Exception ee) {
			Log.log.error("通过角色Id获得内部成员异常,角色id:" + roleId, ee);
			ee.printStackTrace();

		}
		return administrators;
	}
}
