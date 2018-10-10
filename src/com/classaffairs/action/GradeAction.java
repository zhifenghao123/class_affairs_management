/**
 * 
 */
package com.classaffairs.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.classaffairs.common.CreatePrimaryKey;
import com.classaffairs.entity.Grade;
import com.classaffairs.entity.PrimaryKey;
import com.classaffairs.entity.School;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.GradeService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author Haozhifeng
 *
 */
@Controller
public class GradeAction {
	@Resource 
	private GradeService itsGradeService;
	
	/**
	 * 分页获取所有年级信息
	 */
	@RequestMapping(value = "/admin/getGrade")
	@ResponseBody
	public String getGrade(HttpServletRequest request) {
		String page = request.getParameter("page");

		String rows = request.getParameter("rows");

		String gradeNo = request.getParameter("gradeNo");

		if (page == null || page.trim().equals("")) {

			page = "1";
		} else {
			page = page.trim();
		}
		if (rows == null || rows.trim().equals("")) {

			rows = "10";
		} else {
			rows = rows.trim();
		}


		if (gradeNo != null && !gradeNo.trim().equals(""))

			gradeNo = gradeNo.trim();

		JsonObject result = new JsonObject();

		Page<Grade> GradePage = itsGradeService.getGradesByPageQuery(gradeNo, page, rows);

		result.addProperty("total", GradePage.getTotalCount());

		JsonArray array = new JsonArray();

		if (GradePage != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			List<Grade> regionList = GradePage.getResult();

			for (Grade grade : regionList) {

				JsonObject re = new JsonObject();

				re.addProperty("gradeNo", grade.getGradeNo());
				re.addProperty("enrollDate", sdf.format(grade.getEnrollDate()));
				
				array.add(re);

			}

		}
		result.add("rows", array);

		return result.toString();
	}
	
	/**
	 * 新增年级
	 */
	@RequestMapping(value = "/admin/addGrade")
	@ResponseBody
	public String addGrade(Grade grade, HttpServletRequest request) {

		String gradeNo = grade.getGradeNo();

		gradeNo = gradeNo == null ? "" : gradeNo.trim();

		JsonObject result = new JsonObject();

		Grade gradeExist = itsGradeService.findGradeByGradeNo(gradeNo);

		if (gradeExist != null) {
			result.addProperty("exsit", true);

			return result.toString();

		} else {

			result.addProperty("exsit", false);

		}
//		Long gradeId = CreatePrimaryKey.getInstnce().getObjectId(PrimaryKey.GRADE_OBJECTTYPE);
//
//		grade.setGradeId(gradeId);
		grade.setGradeNo(gradeNo);
		//grade.setEnrollDate(enrollDate);


		boolean success = itsGradeService.addGrade(grade);

		result.addProperty("success", success);

		return result.toString();
	}
	
	/**
	 * 通过应用id获取年级
	 */
	@RequestMapping(value = "/admin/getGradeById")
	@ResponseBody
	public String getGradeById(String id) {

		Long gradeId = Long.valueOf(id.trim());

		Grade grade = itsGradeService.findGradeByGradeId(gradeId);

		Grade app = new Grade();

		try {
			BeanUtils.copyProperties(app, grade);
		} catch (Exception e) {
			Log.log.error("应用bean转换异常", e);
			e.printStackTrace();
		}

		Gson gson = new Gson();

		return gson.toJson(app);
	}

	/**
	 * 更新年级
	 */
	@RequestMapping(value = "/admin/updateGrade")
	@ResponseBody
	public String updateGrade(Grade grade) {

		JsonObject result = new JsonObject();

		boolean success = itsGradeService.updateGrade(grade);

		result.addProperty("success", success);

		return result.toString();
	}
	/**
	 * 根据年级号获取年级
	 */
	@RequestMapping(value = "/admin/getGradeByGradeNo")
	@ResponseBody
	public String getGradeByGradeNo(String gradelNo) {

		JsonObject result = new JsonObject();

		Grade grade = itsGradeService.findGradeByGradeNo(gradelNo);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//result.addProperty("gradeId", grade.getGradeId());
		result.addProperty("gradeNo", grade.getGradeNo());
		result.addProperty("enrollDate", sdf.format(grade.getEnrollDate()));

		return result.toString();
	}
	/**
	 * 删除年级
	 */
	@RequestMapping(value = "/admin/deleteGradeById")
	@ResponseBody
	public String deleteGradeById(String id) {

		JsonObject result = new JsonObject();

		Long gradeId = Long.valueOf(id.trim());

		boolean success = itsGradeService.deleteGrade(gradeId);

		result.addProperty("success", success);

		return result.toString();
	}
	/**
	 * 获取所有权年级
	 */
	@RequestMapping(value = "/admin/getAllGradeList")
	@ResponseBody
	public String getAllGradeList() {

		List<Grade> gradeList = itsGradeService.getAllGrades();

		List<Grade> grades = new ArrayList<Grade>();
		for (Grade s : gradeList) {

			Grade grade = new Grade();

			try {
				BeanUtils.copyProperties(grade, s);
			} catch (Exception e) {
				Log.log.error("年级转换异常", e);
				e.printStackTrace();
			}
			grades.add(grade);

		}
		Gson gson = new Gson();

		return gson.toJson(grades);
	}
}
