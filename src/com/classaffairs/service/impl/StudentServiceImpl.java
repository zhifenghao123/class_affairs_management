package com.classaffairs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.classaffairs.dao.StudentDao;
import com.classaffairs.entity.Student;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.StudentService;
@Service
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentDao itsStudentDao;
	@Override
	public boolean addStudent(Student student) {
		try {
			int result = itsStudentDao.mSave(student);
			return result == 1;
		} catch (DataAccessException dae) {
			Log.log.error("新增学生操作数据库异常,学生名称：" + student.getName(), dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("新增学生异常,学生名称：" + student.getName(), e);
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateStudent(Student student) {
		try {
			int row = itsStudentDao.mUpdate(student);
			return row == 1;
		} catch (DataAccessException dae) {
			Log.log.error("修改学生操作数据库异常,学生名称：" + student.getName(), dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("修改学生异常,学生名称：" + student.getName(), e);
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteStudent(Long studentId) {
		try {
			itsStudentDao.mDeleteById(studentId);
			return true;
		} catch (DataAccessException dae) {
			Log.log.error("通过学生id删除学生操作数据库异常,学生id:" + studentId, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过学生id删除学生异常,学生id:" + studentId, e);
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Student findStudentByStudentId(Long studentId) {
		try {
			Student student = (Student) itsStudentDao.mFindById(studentId);

			return student;
		} catch (DataAccessException dae) {
			Log.log.error("通过学生id获取学生操作数据库异常,学生id:" + studentId, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过学生id获取学生异常,学生id:" + studentId, e);
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Student findStudentByStudentNo(String studentNo) {
		Student student = null;
		try {
			Object obj =  itsStudentDao.mSelectOne("findStudentByStudentNo", studentNo);
			if (obj != null) {
				student = (Student) obj;
			}
		} catch (DataAccessException dae) {
			Log.log.error("通过学生代码获取学生访问数据库异常，应用链接:" + studentNo, dae);

			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过学生代码获取学生异常，应用链接:" + studentNo, e);

			e.printStackTrace();
		}

		return student;
	}

	@Override
	public Page<Student> getStudentsByPageQueryFromAdmin(String studentNo, String studentName,String gradeNo,
		 String schoolNo, String majorNo, String executiveClassNo,String page, String pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		if(studentNo != null && !("").equals(studentNo))
			param.put("studentNo", "%" + studentNo + "%");
		if(studentName != null && !("").equals(studentName))
			param.put("studentName", "%" + studentName + "%");
		param.put("gradeNo", gradeNo);
		param.put("schoolNo", schoolNo);
		param.put("majorNo", majorNo);
		param.put("executiveClassNo", executiveClassNo);
		Page<Student> studentPage = null;
		try {
			int recordOffset = (Integer.valueOf(page) - 1) * Integer.valueOf(pageSize);

			studentPage = (Page<Student>) itsStudentDao.mPageQuery("findStudentByFuzzyInformationFromAdmin", param, recordOffset, Integer.valueOf(pageSize));
			
		} catch (DataAccessException dae) {
			Log.log.error("通过参数（学生属性）分页获取学生操作数据库异常,学生名称：" + studentName, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过参数（学生属性）分页获取学生异常,学生名称：" + studentName, e);
			e.printStackTrace();
		}

		return studentPage;
	}
	
	@Override
	public List<Student> getStudentsByQueryFromAdmin(String studentNo,
			String studentName, String gradeNo, String schoolNo,
			String majorNo, String executiveClassNo) {
		Map<String, Object> param = new HashMap<String, Object>();
		if(studentNo != null && !("").equals(studentNo))
			param.put("studentNo", "%" + studentNo + "%");
		if(studentName != null && !("").equals(studentName))
			param.put("studentName", "%" + studentName + "%");
		param.put("gradeNo", gradeNo);
		param.put("schoolNo", schoolNo);
		param.put("majorNo", majorNo);
		param.put("executiveClassNo", executiveClassNo);
		List<Student> studentList = null;
		try {
			studentList = (List<Student>) itsStudentDao.mFind("findStudentByFuzzyInformationFromAdmin", param);
			
		} catch (DataAccessException dae) {
			Log.log.error("通过参数（学生属性）分页获取学生操作数据库异常,学生名称：" + studentName, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过参数（学生属性）分页获取学生异常,学生名称：" + studentName, e);
			e.printStackTrace();
		}

		return studentList;
	}
	@Override
	public Page<Student> getStudentsByPageQueryFromStudent(String studentNo, String studentName, String sex,
			String birthplace,String politicalLandscape, String accessType, String majorNo, String cultivationType, String executiveClassNo, String page, String pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		if(studentNo != null && !("").equals(studentNo))
			param.put("studentNo", "%" + studentNo + "%");
		if(studentName != null && !("").equals(studentName))
			param.put("studentName", "%" + studentName + "%");
		param.put("sex", sex);
		if(birthplace != null && !("").equals(birthplace))
			param.put("birthplace", "%" + birthplace + "%");
		param.put("politicalLandscape", politicalLandscape);
		param.put("accessType", accessType);
		param.put("majorNo", majorNo);
		param.put("cultivationType", cultivationType);
		param.put("executiveClassNo", executiveClassNo);
		Page<Student> studentPage = null;
		try {
			int recordOffset = (Integer.valueOf(page) - 1) * Integer.valueOf(pageSize);

			studentPage = (Page<Student>) itsStudentDao.mPageQuery("findStudentByFuzzyInformationFromStudent", param, recordOffset, Integer.valueOf(pageSize));
			
		} catch (DataAccessException dae) {
			Log.log.error("通过参数（学生属性）分页获取学生操作数据库异常,学生名称：" + studentName, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过参数（学生属性）分页获取学生异常,学生名称：" + studentName, e);
			e.printStackTrace();
		}

		return studentPage;
	}
	
	@Override
	public List<Student> getStudentsByQueryFromStudent(String studentNo, String studentName, String sex,
			String birthplace,String politicalLandscape, String accessType, String majorNo, String cultivationType, String executiveClassNo) {
		Map<String, Object> param = new HashMap<String, Object>();
		if(studentNo != null && !("").equals(studentNo))
			param.put("studentNo", "%" + studentNo + "%");
		if(studentName != null && !("").equals(studentName))
			param.put("studentName", "%" + studentName + "%");
		param.put("sex", sex);
		if(birthplace != null && !("").equals(birthplace))
			param.put("birthplace", "%" + birthplace + "%");
		param.put("politicalLandscape", politicalLandscape);
		param.put("accessType", accessType);
		param.put("majorNo", majorNo);
		param.put("cultivationType", cultivationType);
		param.put("executiveClassNo", executiveClassNo);
		List<Student> studentList = null;
		try {
			studentList = (List<Student>) itsStudentDao.mFind("findStudentByFuzzyInformationFromStudent", param);
			
		} catch (DataAccessException dae) {
			Log.log.error("通过参数（学生属性）分页获取学生操作数据库异常,学生名称：" + studentName, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过参数（学生属性）分页获取学生异常,学生名称：" + studentName, e);
			e.printStackTrace();
		}

		return studentList;
	}
	@Override
	public boolean insertStudentsByBatch(List<Student> studentList) {
		try {
			int result = itsStudentDao.mSaveByBatch(studentList);
			return result == studentList.size();
		} catch (DataAccessException dae) {
			Log.log.error("批量新增学生操作数据库异常,学生数量大小：" + studentList.size(), dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("批量新增学生异常,学生数量大小：" + studentList.size(), e);
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public boolean updateStudentsByBatch(List<Student> studentList) {
		try {
			int result = itsStudentDao.mUpdateByBatch(studentList);
			return result == studentList.size();
		} catch (DataAccessException dae) {
			Log.log.error("批量修改学生操作数据库异常,学生数量大小：" + studentList.size(), dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("批量修改学生异常,学生数量大小：" + studentList.size(), e);
			e.printStackTrace();
		}
		return false;
	}
	

	@Override
	public List<Student> findStudentListByExecutiveClassNames(
			List<String> executiveClassNameList) {
		try {
			List<Student> studentList= (List<Student>) itsStudentDao.mFind("findStudentListByExecutiveClassNames", executiveClassNameList);
			return studentList;
		} catch (DataAccessException dae) {
			Log.log.error("批量新增学生操作数据库异常,学生数量大小：", dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("批量新增学生异常,学生数量大小：", e);
			e.printStackTrace();
		}
		return null;
	}





}
