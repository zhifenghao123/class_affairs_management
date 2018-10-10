/**
 * 
 */
package com.classaffairs.test.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.classaffairs.entity.Student;
import com.classaffairs.entity.User;
import com.classaffairs.framework.core.springtestcase.SpringTestCase;
import com.classaffairs.service.StudentService;

/**
 * @author Haozhifeng
 *
 */
public class StudentServiceImplTest extends SpringTestCase {
	@Autowired
	private StudentService itsStudentService;
	@Test
	@Rollback(false)
	public void testInsertStudentsByBatch() {
		List<Student> studentList = new ArrayList<Student>();
		Student student1 = new Student();
		student1.setStudentId(1000l);
		student1.setStudentNo("11111");
		student1.setPassword("11111");
		student1.setName("zhang");
		
		Student student2 = new Student();
		student2.setStudentId(1001l);
		student2.setStudentNo("22222");
		student2.setPassword("11111");
		student2.setName("li");

		studentList.add(student1);
		studentList.add(student2);
		boolean result = itsStudentService.insertStudentsByBatch(studentList);
		System.out.println(result);
	}
}
