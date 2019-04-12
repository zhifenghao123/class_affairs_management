package com.classaffairs.service;

import java.util.List;

import com.classaffairs.entity.Administrator;
import com.classaffairs.framework.sdp.orm.query.Page;

public interface AdministratorService {

	boolean addAdministrator(Administrator administrator);
	
	boolean updateAdministrator(Administrator administrator);
	
	boolean deleteAdministrator(Long administratorId);
	
	Administrator findAdministratorByAdministratorId(Long administratorId);
	
	Administrator findAdministratorByAdministratorNo(String administratorNo);
	
	/**
	 * 通过参数分页获取所有内部成员对象
	 * @param name - 参数（真实姓名）
	 * @param page - 第page页
	 * @param pageSize - 页面大小（每页记录数）
	 * @return Page<Interioremployee> - 内部成员Page
	 */
	public Page<Administrator> pageQueryAdministatorsByName(String name, String page, String pageSize);
	
	/**
	 * 通过角色id获取内部成员对象List
	 * @return 员工对象List
	 */
	public List<Administrator> getAdministratorsByRoleId(Long roleId);
	
}
