/**
 * 
 */
package com.classaffairs.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.classaffairs.dao.UserDao;
import com.classaffairs.entity.User;
import com.classaffairs.framework.core.springtestcase.SpringTestCase;

/**
 * @author Haozhifeng
 *
 */
public class BaseDaoTest extends SpringTestCase {
	@Autowired 
	private UserDao userDao;
	
	@Test
	@Rollback(false)
	public void testAddUser() {
		//BaseDaoT<User> userDao = new BaseDaoTImpl<User>();
		System.out.println(BaseDaoTest.class.getSimpleName());
		System.out.println(BaseDaoTest.class.getName());
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
		int result = userDao.mSave(user);
		System.out.println(result);
		
	}
	
	@Test
	@Rollback(false)
	public void testInsertStudentsByBatch(){
		
		List userList = new ArrayList<User>();
		User user = new User();
		user.setUserId(1002l);
		user.setUserAccountNo("3120921052");
		user.setPassword("111111");
		user.setIsCommonStudent(1);
		user.setIsLaboratoryLeader(0);
		user.setIsInstituteLeader(0);
		user.setIsSchoolLeader(0);
		user.setIsAdministrator(0);
		user.setIsSuperAdministrator(0);
		user.setState(1);
		
		User user2 = new User();
		user2.setUserId(1003l);
		user2.setUserAccountNo("3120921053");
		user2.setPassword("111111");
		user2.setIsCommonStudent(1);
		user2.setIsLaboratoryLeader(0);
		user2.setIsInstituteLeader(0);
		user2.setIsSchoolLeader(0);
		user2.setIsAdministrator(0);
		user2.setIsSuperAdministrator(0);
		user2.setState(1);
		userList.add(user);
		userList.add(user2);
		int result = userDao.mSaveByBatch(userList);
		System.out.println(result);
	}
}
