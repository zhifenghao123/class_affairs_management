/**
 * 
 */
package com.classaffairs.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.classaffairs.common.CreatePrimaryKey;
import com.classaffairs.entity.School;
import com.classaffairs.entity.PrimaryKey;
import com.classaffairs.framework.core.init.SpringContextHelper;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.SchoolService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author Haozhifeng
 *
 */
@Controller
public class SchoolAction {

	private SchoolService init() {
		ApplicationContext ctx = SpringContextHelper.getApplicationContext();

		SchoolService itsSchoolManageService = (SchoolService) ctx.getBean("schoolServiceImpl");

		return itsSchoolManageService;
	}
	
	/**
	 * 分页获取所有学院信息
	 */
	@RequestMapping(value = "/admin/getSchool")
	@ResponseBody
	public String getRegion(HttpServletRequest request) {
		String page = request.getParameter("page");

		String rows = request.getParameter("rows");

		String name = request.getParameter("name");

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


		if (name != null && !name.trim().equals(""))

			name = name.trim();

		JsonObject result = new JsonObject();

		Page<School> RegionPage = init().getSchoolsByPageQuery(name, page, rows);

		result.addProperty("total", RegionPage.getTotalCount());

		JsonArray array = new JsonArray();

		if (RegionPage != null) {

			List<School> regionList = RegionPage.getResult();

			for (School school : regionList) {

				JsonObject re = new JsonObject();

				re.addProperty("schoolId", school.getSchoolId());

				re.addProperty("schoolNo", school.getSchoolNo());

				re.addProperty("name", school.getName());
				
				re.addProperty("abbreviation", school.getAbbreviation());

				array.add(re);

			}

		}
		result.add("rows", array);

		return result.toString();
	}
	
	/**
	 * 新增应用
	 */
	@RequestMapping(value = "/admin/addSchool")
	@ResponseBody
	public String addRole(School school, HttpServletRequest request) {

		String schoolNo = school.getSchoolNo();

		schoolNo = schoolNo == null ? "" : schoolNo.trim();

		JsonObject result = new JsonObject();

		School schoolExist = init().findSchoolBySchoolNo(schoolNo);

		if (schoolExist != null) {
			result.addProperty("exsit", true);

			return result.toString();

		} else {

			result.addProperty("exsit", false);

		}
		Long schoolId = CreatePrimaryKey.getInstnce().getObjectId(PrimaryKey.School_OBJECTTYPE);

		school.setSchoolId(schoolId);


		boolean success = init().addSchool(school);

		result.addProperty("success", success);

		return result.toString();
	}
	
	/**
	 * 通过应用id获取应用
	 */
	@RequestMapping(value = "/admin/getSchoolById")
	@ResponseBody
	public String getSchoolById(String id) {

		Long schoolId = Long.valueOf(id.trim());

		School school = init().findSchoolBySchoolId(schoolId);

		School app = new School();

		try {
			BeanUtils.copyProperties(app, school);
		} catch (Exception e) {
			Log.log.error("应用bean转换异常", e);
			e.printStackTrace();
		}

		Gson gson = new Gson();

		return gson.toJson(app);
	}

	/**
	 * 更新应用
	 */
	@RequestMapping(value = "/admin/updateSchool")
	@ResponseBody
	public String updateRole(School school) {

		JsonObject result = new JsonObject();

		boolean success = init().updateSchool(school);

		result.addProperty("success", success);

		return result.toString();
	}
	
	/**
	 * 获取所有权限
	 */
	@RequestMapping(value = "/admin/getAllSchoolList")
	@ResponseBody
	public String getAllSchoolList() {

		List<School> schoolList = init().getAllSchools();

		List<School> schools = new ArrayList<School>();
		for (School s : schoolList) {

			School school = new School();

			try {
				BeanUtils.copyProperties(school, s);
			} catch (Exception e) {
				Log.log.error("权限转换异常", e);
				e.printStackTrace();
			}
			schools.add(school);

		}
		Gson gson = new Gson();

		return gson.toJson(schools);
	}
	
}
