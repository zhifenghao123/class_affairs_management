package com.classaffairs.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.classaffairs.common.security.ClassAffairFilterInvocationSecurityMetadataSource;
import com.classaffairs.dao.AuthorityDao;
import com.classaffairs.entity.Authority;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.AuthorityRetrieveService;
@Service
public class AuthorityRetrieveServiceImpl implements AuthorityRetrieveService {
	@Autowired
	private AuthorityDao itsAuthorityDao;
	@Override
	public Authority findAuthorityByAuthorityId(Long authorityId) {
		Authority authority = null;
		try {
			authority = (Authority) itsAuthorityDao.mFindById(authorityId);
		} catch (DataAccessException dae) {
			Log.log.error("通过应用id获取权限访问数据库异常authorityId=" + authorityId, dae);

			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过应用id获取应用异常authorityId=" + authorityId, e);

			e.printStackTrace();
		}

		return authority;
	}

	@Override
	public List<Authority> getAllAuthoritys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Authority> getAuthoritysByIsOnUse(int isOnUse) {
		List<Authority> Authoritys = new ArrayList<Authority>();
		try {
			Authoritys = (List<Authority>) itsAuthorityDao.mFind("getByIsOnUse", isOnUse);
			return Authoritys;
		} catch (DataAccessException e) {
			Log.log.error("通过状态获得权限类型访问数据库异常cn.togym.services.system.impl.AuthorityRetrieveServiceImpl", e);
			e.printStackTrace();
		} catch (Exception ee) {
			Log.log.error("通过状态获得所有的权限异常cn.togym.services.system.impl.AuthorityRetrieveServiceImpl", ee);
			ee.printStackTrace();
		}
		return Authoritys;
	}

	@Override
	public Page<Authority> getAllAuthoritysByName(String page, String pageSize,String name) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("name", "%" + name + "%");
		Page<Authority> Authoritys = null;
		try {
			int recordOffset = (Integer.valueOf(page) - 1) * Integer.valueOf(pageSize);
			Authoritys = (Page<Authority>) itsAuthorityDao.mPageQuery("getAllByName", param, recordOffset, Integer.valueOf(pageSize));
			return Authoritys;
		} catch (DataAccessException e) {
			Log.log.error("分页获得所有的权限类型访问数据库异常cn.togym.services.system.impl.AuthorityRetrieveServiceImpl", e);
			e.printStackTrace();
		} catch (Exception ee) {
			Log.log.error("分页获得所有的权限异常cn.togym.services.system.impl.AuthorityRetrieveServiceImpl", ee);
			ee.printStackTrace();
		}
		return Authoritys;
	}

	@Override
	public Authority getAuthoritybyName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
