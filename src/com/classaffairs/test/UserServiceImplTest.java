package com.classaffairs.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.classaffairs.entity.User;
import com.classaffairs.framework.core.springtestcase.SpringTestCase;
import com.classaffairs.service.UserService;

public class UserServiceImplTest extends SpringTestCase {
	@Autowired 
	private UserService itsUserService;
	@Test
	@Rollback(false)
	public void testAddUser() {
		User user = new User();
		user.setUserId(1001l);
		user.setUserAccountNo("3120921052");
		user.setPassword("111111");
		user.setIsCommonStudent(1);
		user.setIsLaboratoryLeader(0);
		user.setIsInstituteLeader(0);
		user.setIsSchoolLeader(0);
		user.setIsAdministrator(0);
		user.setIsSuperAdministrator(0);
		user.setState(1);
		boolean result = itsUserService.addUser(user);
		System.out.println(result);
	}
	@Test
	@Rollback(false)
	public void testfindUserByUserAccountNo() {
		User user = itsUserService.findUserByUserAccountNo("3120921052");
		System.out.println(user);
	}
}
