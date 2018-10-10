package com.classaffairs.service;

import com.classaffairs.entity.User;

public interface UserService {
	public boolean addUser(User user);

	public User findUserById(long id);
	
	public User findUserByUserAccountNo(String userAccountNo);
}
