package com.classaffairs.service.impl;

import org.springframework.stereotype.Service;

import com.classaffairs.entity.User;
import com.classaffairs.service.UserService;
@Service
public class UserServiceImpl implements UserService {

	@Override
	public boolean addUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User findUserById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findUserByUserAccountNo(String userAccountNo) {
		// TODO Auto-generated method stub
		return null;
	}
/*	@Autowired
	private UserDao itsUserDao;
	@Override
	public boolean addUser(User user) {
		boolean  addUserResult = false;
		int i = 0;
		i = itsUserDao.addUser(user);
		if(i > 0)
			addUserResult = true;
		else
			addUserResult = false;
		return addUserResult;
	}

	@Override
	public User findUserById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findUserByUserAccountNo(String userAccountNo) {
		User user = itsUserDao.findUserByUserAccountNo(userAccountNo);
		return user;
	}
*/
}
