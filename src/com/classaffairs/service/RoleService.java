package com.classaffairs.service;

import java.util.List;

import com.classaffairs.entity.Role;
import com.classaffairs.framework.sdp.orm.query.Page;

public interface RoleService {
	
	boolean addRole(Role role);
	
	boolean updateRole(Role role);
	
	boolean deleteRole(Long roleId);
	
	Role findRoleByRoleId(Long roleId);
	/**
	 * 通过所给参数分页获取角色
	 * @param name -  参数（角色名称）
	 * @param offset - page 第page页
	 * @param pageSize - 页面大小（每页显示条数）
	 * @return
	 */
	public Page<Role> pageQueryRole(String name, String page, String pageSize);
	/**
	 * 通过角色状态获取角色
	 * @param state - 角色状态
	 * @return List<Role> - 角色List
	 */
	public List<Role> getRoleByState(int state);
	/**
	 * 通过角色名称获取角色
	 * @param name - 角色名称
	 * @return Role - 角色对象
	 */
	public Role getSingleRoleByName(String name);
}
