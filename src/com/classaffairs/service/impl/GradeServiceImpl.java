/**
 * 
 */
package com.classaffairs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.classaffairs.dao.GradeDao;
import com.classaffairs.entity.Grade;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.GradeService;

/**
 * @author Haozhifeng
 *
 */
@Service
public class GradeServiceImpl implements GradeService {
	@Autowired
	private GradeDao itsGradeDao;
	@Override
	public boolean addGrade(Grade grade) {
		int result = 0;
		try{
			result = itsGradeDao.mSave(grade);
		}catch(DataAccessException dae){
			Log.log.error("新增年级访问数据库异常，年级名称：" + grade.getGradeNo(), dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("新增年级异常，年级名称：" + grade.getGradeNo() , e);
			e.printStackTrace();
		}
		return result == 1;
	}

	@Override
	public boolean updateGrade(Grade grade) {
		int result = 0;
		try{
			result = itsGradeDao.mUpdate(grade);
		}catch(DataAccessException dae){
			Log.log.error("修改年级访问数据库异常，年级名称：" + grade.getGradeNo(), dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("修改年级异常，年级名称：" + grade.getGradeNo() , e);
			e.printStackTrace();
		}
		return result == 1;
	}

	@Override
	public boolean deleteGrade(Long gradeId) {
		try{
			itsGradeDao.mDeleteById(gradeId);
			return true;
		}catch(DataAccessException dae){
			Log.log.error("删除年级访问数据库异常，年级名称Id：" + gradeId, dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("删除年级异常，年级名称Id：" + gradeId , e);
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Grade findGradeByGradeId(Long gradeId) {
		Grade Grade = null;
		try {
			Grade = (Grade) itsGradeDao.mFindById(gradeId);
		} catch (DataAccessException dae) {
			Log.log.error("通过年级id获取年级访问数据库异常GradeId=" + gradeId, dae);

			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过年级id获取年级异常GradeId=" + gradeId, e);

			e.printStackTrace();
		}

		return Grade;
	}

	@Override
	public Page<Grade> getGradesByPageQuery(String gradeNo, String page,
			String pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		if(gradeNo != null)
			param.put("gradeNo", "%" + gradeNo + "%");

		Page<Grade> gradePge = null;
		try {
			int recordOffset = (Integer.valueOf(page) - 1) * Integer.valueOf(pageSize);

			gradePge = (Page<Grade>) itsGradeDao.mPageQuery("findGradeByFuzzyInformation", param, recordOffset, Integer.valueOf(pageSize));

		} catch (DataAccessException dae) {
			Log.log.error("通过参数（年级属性）分页获取年级操作数据库异常,年级名称：" + gradeNo, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过参数（年级属性）分页获取年级异常,年级名称：" + gradeNo, e);
			e.printStackTrace();
		}

		return gradePge;
	}

	@Override
	public Grade findGradeByGradeNo(String gradeNo) {
		Grade grade = null;
		try {
			Object obj = itsGradeDao.mSelectOne("findGradeByGradeNo", gradeNo);
			if (obj != null) {
				grade = (Grade) obj;
			}
		} catch (DataAccessException dae) {
			Log.log.error("通过年级代码获取年级访问数据库异常，应用链接:" + gradeNo, dae);

			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过年级代码获取年级异常，应用链接:" + gradeNo, e);

			e.printStackTrace();
		}

		return grade;
	}

	@Override
	public List<Grade> getAllGrades() {
		return (List<Grade>) itsGradeDao.mFindAll();
	}
}
