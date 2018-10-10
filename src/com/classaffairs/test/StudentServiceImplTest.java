package com.classaffairs.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.classaffairs.entity.Student;
import com.classaffairs.framework.core.springtestcase.SpringTestCase;
import com.classaffairs.service.StudentService;

public class StudentServiceImplTest extends SpringTestCase {
	@Autowired 
	private StudentService itsStudentService;
	@Test
	@Rollback(false)
	public void testAddStudent() {
		
	}
	
	@Rollback(false)
	public void testfindStudentListByExecutiveClassNames() {
		List<String> executiveClassNameList = new ArrayList<String>();
		executiveClassNameList.add("研1628班");
		executiveClassNameList.add("研1629班");
		List<Student> studentlist = itsStudentService.findStudentListByExecutiveClassNames(executiveClassNameList);
		System.out.print(studentlist.size());
	}
}
