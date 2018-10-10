/**
 * 
 */
package com.classaffairs.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.classaffairs.common.CreatePrimaryKey;
import com.classaffairs.entity.ExecutiveClass;
import com.classaffairs.entity.Major;
import com.classaffairs.entity.PrimaryKey;
import com.classaffairs.entity.School;
import com.classaffairs.framework.core.init.SpringContextHelper;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.MajorService;
import com.classaffairs.service.SchoolService;
import com.classaffairs.service.StudentService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author Haozhifeng
 *
 */
@Controller
public class MajorAction {
	@Resource 
	private SchoolService itsSchoolManageService;
	@Resource 
	private StudentService itsStudentService;

	private MajorService init() {
		ApplicationContext ctx = SpringContextHelper.getApplicationContext();

		MajorService itsMajorManageService = (MajorService) ctx.getBean("majorServiceImpl");

		return itsMajorManageService;
	}
	
	/**
	 * 分页获取所有专业信息
	 */
	@RequestMapping(value = "/admin/getMajor")
	@ResponseBody
	public String getMajor(HttpServletRequest request) {
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

		Page<Major> RegionPage = init().getMajorsByPageQuery(name, page, rows);

		result.addProperty("total", RegionPage.getTotalCount());

		JsonArray array = new JsonArray();

		if (RegionPage != null) {

			List<Major> regionList = RegionPage.getResult();

			for (Major major : regionList) {

				JsonObject re = new JsonObject();

				re.addProperty("majorId", major.getMajorId());

				re.addProperty("majorNo", major.getMajorNo());

				re.addProperty("name", major.getName());
				String schoolAbbreviationName = null;
				String schoolNo = null;
				if(major.getSchoolId() != null && !("").equals(major.getSchoolId())){
					School school = itsSchoolManageService.findSchoolBySchoolId(major.getSchoolId());
					schoolAbbreviationName = school.getAbbreviation();
					schoolNo = school.getSchoolNo();
				}
				
				re.addProperty("schoolAbbreviationName", schoolAbbreviationName);
				re.addProperty("schoolNo", schoolNo);
				
				array.add(re);

			}

		}
		result.add("rows", array);

		return result.toString();
	}
	
	/**
	 * 新增专业
	 */
	@RequestMapping(value = "/admin/addMajor")
	@ResponseBody
	public String addMajor(Major major, HttpServletRequest request) {

		String majorNo = major.getMajorNo();

		majorNo = majorNo == null ? "" : majorNo.trim();

		JsonObject result = new JsonObject();

		Major majorExist = init().findMajorByMajorNo(majorNo);

		if (majorExist != null) {
			result.addProperty("exsit", true);

			return result.toString();

		} else {

			result.addProperty("exsit", false);

		}
		Long majorId = CreatePrimaryKey.getInstnce().getObjectId(PrimaryKey.Major_OBJECTTYPE);

		major.setMajorId(majorId);


		boolean success = init().addMajor(major);

		result.addProperty("success", success);

		return result.toString();
	}
	
	/**
	 * 通过应用id获取专业
	 */
	@RequestMapping(value = "/admin/getMajorById")
	@ResponseBody
	public String getMajorById(String id) {

		Long majorId = Long.valueOf(id.trim());

		Major major = init().findMajorByMajorId(majorId);

		Major app = new Major();

		try {
			BeanUtils.copyProperties(app, major);
		} catch (Exception e) {
			Log.log.error("应用bean转换异常", e);
			e.printStackTrace();
		}

		Gson gson = new Gson();

		return gson.toJson(app);
	}

	/**
	 * 更新专业
	 */
	@RequestMapping(value = "/admin/updateMajor")
	@ResponseBody
	public String updateRole(Major major) {

		JsonObject result = new JsonObject();

		boolean success = init().updateMajor(major);

		result.addProperty("success", success);

		return result.toString();
	}
	/**
	 * 根据学院代码获取所有专业
	 */
	@RequestMapping(value = "/admin/getMajorsBySchoolNo")
	@ResponseBody
	public String getMajorListBySchoolNo(String schoolNo) {

		JsonArray result = new JsonArray();

		List<Major> majors = init().findMajorListBySchoolNo(schoolNo);

		for(Major m : majors){
			JsonObject jo = new JsonObject();
			jo.addProperty("majorNo", m.getMajorNo());
			jo.addProperty("name", m.getName());
			result.add(jo);
		}

		return result.toString();
	}
	/**
	 * 删除专业
	 */
	@RequestMapping(value = "/admin/deleteMajorById")
	@ResponseBody
	public String deleteMajorById(String id) {

		JsonObject result = new JsonObject();

		Long majorId = Long.valueOf(id.trim());

		boolean success = init().deleteMajor(majorId);

		result.addProperty("success", success);

		return result.toString();
	}
	
	/**
	 * 获取当前学生所在学院的所有专业
	 */
	@RequestMapping(value = "/student/getMajors")
	@ResponseBody
	public String getMajorListByCurrentStudent(HttpServletRequest request) {

		JsonArray result = new JsonArray();
		String currentStudentNo = request.getSession().getAttribute("studentNo").toString();
		List<Major> majors = init().findMajorListBySchoolNo(itsStudentService.findStudentByStudentNo(currentStudentNo).getSchoolNo());

		for(Major m : majors){
			JsonObject jo = new JsonObject();
			jo.addProperty("majorNo", m.getMajorNo());
			jo.addProperty("name", m.getName());
			result.add(jo);
		}

		return result.toString();
	}
}
