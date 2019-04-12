package com.classaffairs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.classaffairs.dao.RoleDao;
import com.classaffairs.entity.Role;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.RoleService;
@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDao itsRoleDao;
	@Override
	public boolean addRole(Role role) {
		int result = 0;
		try{
			result = itsRoleDao.mSave(role);
		}catch(DataAccessException dae){
			Log.log.error("新增角色访问数据库异常，角色名称：" + role.getName(), dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("新增角色异常，角色名称：" + role.getName() , e);
			e.printStackTrace();
		}
		return result == 1;
	}

	@Override
	public boolean updateRole(Role role) {
		int result = 0;
		try{
			result = itsRoleDao.mUpdate(role);
		}catch(DataAccessException dae){
			Log.log.error("修改角色访问数据库异常，角色名称：" + role.getName(), dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("修改角色异常，角色名称：" + role.getName() , e);
			e.printStackTrace();
		}
		return result == 1;
	}

	@Override
	public boolean deleteRole(Long roleId) {
		try{
			itsRoleDao.mDeleteById(roleId);
			return true;
		}catch(DataAccessException dae){
			Log.log.error("删除角色访问数据库异常，角色名称Id：" + roleId, dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("删除角色异常，角色名称Id：" + roleId , e);
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Role findRoleByRoleId(Long roleId) {
		Role role = null;
		try {
			role = (Role) itsRoleDao.mFindById(roleId);
		} catch (DataAccessException dae) {
			Log.log.error("通过角色id获取角色访问数据库异常roleId=" + roleId, dae);

			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过角色id获取角色异常roleId=" + roleId, e);

			e.printStackTrace();
		}

		return role;
	}

	@Override
	public Page<Role> pageQueryRole(String name, String page, String pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("name", "%" + name + "%");

		Page<Role> rolePge = null;
		try {
			int recordOffset = (Integer.valueOf(page) - 1) * Integer.valueOf(pageSize);

			rolePge = (Page<Role>) itsRoleDao.mPageQuery("getRoleByName", param, recordOffset, Integer.valueOf(pageSize));

		} catch (DataAccessException dae) {
			Log.log.error("通过参数（角色属性）分页获取角色操作数据库异常,角色名称：" + name, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过参数（角色属性）分页获取角色异常,角色名称：" + name, e);
			e.printStackTrace();
		}

		return rolePge;
	}

	@Override
	public List<Role> getRoleByState(int state) {
		List<Role> roleList = null;
		try {
			roleList = (List<Role>) itsRoleDao.mFind("getRoleByState", state);
		} catch (DataAccessException dae) {
			Log.log.error("通过角色状态获取角色操作数据库异常,角色状态：" + state, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过角色状态获取角色异常,角色状态称：" + state, e);
			e.printStackTrace();
		}
		return roleList;
		}
	
	@Override
	public Role getSingleRoleByName(String name) {
		Role role = null;
		try {
			role = (Role) itsRoleDao.mSelectOne("getSingleRoleByName", name);
		} catch (DataAccessException dae) {
			Log.log.error("通过角色名称获取角色操作数据库异常,角色名称：" + name, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过角色名称获取角色异常,角色名称：" + name, e);
			e.printStackTrace();
		}
		return role;
	}

}
