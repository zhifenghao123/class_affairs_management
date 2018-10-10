package com.classaffairs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.classaffairs.dao.AuthorityDao;
import com.classaffairs.entity.Authority;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.service.AuthorityWriteService;
@Service
public class AuthorityWriteServiceImpl implements AuthorityWriteService {
	@Autowired
	private AuthorityDao itsAuthorityDao;
	@Override
	public boolean addAuthority(Authority authority) {
		int result = 0;
		try{
			result = itsAuthorityDao.mSave(authority);
		}catch(DataAccessException dae){
			Log.log.error("新增权限访问数据库异常，权限名称：" + authority.getName(), dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("新增权限异常，权限名称：" + authority.getName() , e);
			e.printStackTrace();
		}
		return result == 1;
	}

	@Override
	public boolean updateAuthority(Authority authority) {
		int result = 0;
		try{
			result = itsAuthorityDao.mUpdate(authority);
		}catch(DataAccessException dae){
			Log.log.error("修改权限访问数据库异常，权限名称：" + authority.getName(), dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("修改权限异常，权限名称：" + authority.getName() , e);
			e.printStackTrace();
		}
		return result == 1;
	}

	@Override
	public boolean deleteAuthority(int authorityId) {
		try{
			itsAuthorityDao.mDeleteById(authorityId);
			return true;
		}catch(DataAccessException dae){
			Log.log.error("删除权限访问数据库异常，权限名称Id：" + authorityId, dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("删除权限异常，权限名称Id：" + authorityId , e);
			e.printStackTrace();
		}
		return false;
	}

}
