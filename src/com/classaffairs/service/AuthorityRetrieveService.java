package com.classaffairs.service;

import java.util.List;

import com.classaffairs.entity.Authority;
import com.classaffairs.framework.sdp.orm.query.Page;

public interface AuthorityRetrieveService {

	/**
	 * 通过权限类型id获取权限
	 * @param authorityId 权限类型id
	 * @return 权限对象
	 */
	public Authority findAuthorityByAuthorityId(Long authorityId);

	/**
	 * 获取所有的权限类型
	 * @return 权限类型List
	 */
	public List<Authority> getAllAuthoritys();

	/**
	 * 通过状态获取权限类型
	 * @param isOnUse 开启状态
	 * @return 权限类型List
	 */
	public List<Authority> getAuthoritysByIsOnUse(int isOnUse);

	/**
	 * 通过权限代码获取权限对象
	 * @param code 权限代码
	 * @return 权限对象
	 */

	/**
	 * 分页获取权限类型
	 * @param page - 第page页
	 * @param pageSize - 页面大小(每页记录数)
	 * @param name - 参数：权限名称
	 * @return 权限类型Page
	 */
	public Page<Authority> getAllAuthoritysByName(String page, String pageSize, String name);

	/**
	 * 通过权限名称获取权限对象
	 * @param name - 权限名称
	 * @return 权限对象
	 */

	public Authority getAuthoritybyName(String name);

	public int getTotalCount();
}
