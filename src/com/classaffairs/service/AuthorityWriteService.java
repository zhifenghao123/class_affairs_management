package com.classaffairs.service;

import com.classaffairs.entity.Authority;

public interface AuthorityWriteService {

	/**
	 * 新增权限
	 * @param authority 权限对象
	 * @return
	 */
	public boolean addAuthority(Authority authority);

	/**
	 * 修改权限
	 * @param authority 被修改权限对象
	 * @return 修改是否成功
	 */
	public boolean updateAuthority(Authority authority);

	/**
	 * 删除权限
	 * @param authorityId - 删除权限id
	 * @return 删除是否成功
	 */
	public boolean deleteAuthority(int authorityId);
}
