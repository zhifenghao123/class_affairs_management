/**
 * 
 */
package com.classaffairs.cxf;

import com.classaffairs.entity.Student;
import com.classaffairs.framework.sdp.orm.query.Page;

/**
 * @author Haozhifeng
 *
 */
public interface StudentCXFService {
	/**
	 * @author Haozhifeng
	 * @param args 测试的输出字符串
	 * @return Success、Fail
	 */
	public Page<Student> getStudentsByPageQueryFromStudent(String studentNo,
			String studentName, String sex, String birthplace,
			String politicalLandscape, String accessType, String majorNo,
			String cultivationType, String executiveClassNo, String page,
			String pageSize);
}
