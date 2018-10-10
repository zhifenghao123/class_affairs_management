/**
 * 
 */
package com.classaffairs.action;

import java.util.ArrayList;
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
import com.classaffairs.entity.Grade;
import com.classaffairs.entity.PrimaryKey;
import com.classaffairs.entity.School;
import com.classaffairs.entity.Student;
import com.classaffairs.framework.core.init.SpringContextHelper;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.ExecutiveClassService;
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
public class ExecutiveClassAction {
	@Resource 
	private SchoolService itsSchoolManageService;

	private ExecutiveClassService init() {
		ApplicationContext ctx = SpringContextHelper.getApplicationContext();

		ExecutiveClassService itsExecutiveClassManageService = (ExecutiveClassService) ctx.getBean("executiveClassServiceImpl");

		return itsExecutiveClassManageService;
	}
	
	private StudentService getStudentService() {
		ApplicationContext ctx = SpringContextHelper.getApplicationContext();

		StudentService itsStudentService = (StudentService) ctx.getBean("studentServiceImpl");

		return itsStudentService;
	}
	
	/**
	 * 分页获取所有行政班级信息
	 */
	@RequestMapping(value = "/admin/getExecutiveClass")
	@ResponseBody
	public String getExecutiveClass(HttpServletRequest request) {
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

		Page<ExecutiveClass> executiveClassPage = init().getExecutiveClassByPageQuery(name, page, rows);

		result.addProperty("total", executiveClassPage.getTotalCount());

		JsonArray array = new JsonArray();

		if (executiveClassPage != null) {

			List<ExecutiveClass> regionList = executiveClassPage.getResult();

			for (ExecutiveClass executiveClass : regionList) {

				JsonObject re = new JsonObject();

				re.addProperty("executiveClassId", executiveClass.getExecutiveClassId());

				re.addProperty("name", executiveClass.getName());
				String schoolName = null;
				String schoolNo = null;
				/*String monotorName = executiveClass.getMonitorName();
				String monotorNo = null;*/
				if(executiveClass.getSchoolNo() != null && !("").equals(executiveClass.getSchoolNo())){
					School school = itsSchoolManageService.findSchoolBySchoolNo(executiveClass.getSchoolNo());
					schoolName = school.getName();
					schoolNo = school.getSchoolNo();
				}
				/*if(executiveClass.getMonitorId() != null && !("").equals(executiveClass.getMonitorId())){
					Student monitor = getStudentService().findStudentByStudentNo(executiveClass.getMonitorName());
					monotorNo = monitor.getSchoolNo();
					
				}*/
				re.addProperty("gradeNo", executiveClass.getGradeNo() + "级");
				re.addProperty("schoolNo", schoolNo);
				re.addProperty("schoolName", schoolName);
				re.addProperty("executiveClassGroup", executiveClass.getExecutiveClassGroup());
				re.addProperty("monotorNo", executiveClass.getMonitorStudentNo());
				re.addProperty("monotorName", executiveClass.getMonitorName());
				array.add(re);

			}

		}
		result.add("rows", array);

		return result.toString();
	}
	
	/**
	 * 新增行政班级
	 */
	@RequestMapping(value = "/admin/addExecutiveClass")
	@ResponseBody
	public String addExecutiveClass(ExecutiveClass executiveClass, HttpServletRequest request) {

		String executiveClassNo = executiveClass.getName();

		executiveClassNo = executiveClassNo == null ? "" : executiveClassNo.trim();

		JsonObject result = new JsonObject();

		ExecutiveClass executiveClassExist = init().findExecutiveClassByExecutiveClassName(executiveClassNo);

		if (executiveClassExist != null) {
			result.addProperty("exsit", true);

			return result.toString();

		} else {
			result.addProperty("exsit", false);
		}
		Long executiveClassId = CreatePrimaryKey.getInstnce().getObjectId(PrimaryKey.ExecutiveClass_OBJECTTYPE);

		executiveClass.setExecutiveClassId(executiveClassId);


		boolean success = init().addExecutiveClass(executiveClass);

		result.addProperty("success", success);

		return result.toString();
	}
	
	/**
	 * 通过应用id获取行政班级
	 */
	@RequestMapping(value = "/admin/getExecutiveClassById")
	@ResponseBody
	public String getExecutiveClassById(String id) {

		Long executiveClassId = Long.valueOf(id.trim());

		ExecutiveClass executiveClass = init().findExecutiveClassByExecutiveClassId(executiveClassId);

		ExecutiveClass app = new ExecutiveClass();

		try {
			BeanUtils.copyProperties(app, executiveClass);
		} catch (Exception e) {
			Log.log.error("应用bean转换异常", e);
			e.printStackTrace();
		}

		Gson gson = new Gson();

		return gson.toJson(app);
	}

	/**
	 * 更新行政班级
	 */
	@RequestMapping(value = "/admin/updateExecutiveClass")
	@ResponseBody
	public String updateRole(ExecutiveClass executiveClass) {

		JsonObject result = new JsonObject();

		//boolean success = init().updateExecutiveClass(executiveClass);
		boolean flag = true;
		List<ExecutiveClass> executiveClassList = init().findExecutiveClassListByGradeNoAndSchoolNo(executiveClass.getGradeNo(), executiveClass.getSchoolNo(), executiveClass.getExecutiveClassGroup());
		for(ExecutiveClass e : executiveClassList){
			e.setExecutiveClassGroup(executiveClass.getExecutiveClassGroup());
			e.setMonitorStudentNo(executiveClass.getMonitorStudentNo());
			e.setMonitorName(executiveClass.getMonitorName());
			flag = init().updateExecutiveClass(e);
		}
		Student monitor = getStudentService().findStudentByStudentNo(executiveClass.getMonitorStudentNo());
		monitor.setIsMonitor(1);
		flag = getStudentService().updateStudent(monitor);
		//result.addProperty("success", success);
		result.addProperty("success", true);
		return result.toString();
	}
	
	/**
	 * 删除行政班级
	 */
	@RequestMapping(value = "/admin/deleteExecutiveClassById")
	@ResponseBody
	public String deleteExecutiveClassById(String executiveClassId, HttpServletRequest request) {

		JsonObject result = new JsonObject();
		
		boolean success = init().deleteExecutiveClass(Long.valueOf(executiveClassId));

		result.addProperty("success", success);

		return result.toString();
	}
	/**
	 * 根据学员代码获取是所有行政班级
	 */
	@RequestMapping(value = "/admin/getExecutiveClassesBySchoolNo")
	@ResponseBody
	public String getExecutiveClassListBySchoolNo(String gradeNo,String schoolNo) {

		JsonArray result = new JsonArray();

		List<ExecutiveClass> executiveClasses = init().findExecutiveClassListByGradeNoAndSchoolNo(gradeNo,schoolNo,null);

		for(ExecutiveClass ec : executiveClasses){
			JsonObject jo = new JsonObject();
			jo.addProperty("name", ec.getName());
			result.add(jo);
		}

		return result.toString();
	}
	
	@RequestMapping(value = "/admin/getAllExecutiveClassList")
	@ResponseBody
	public String getAllExecutiveClassList() {
		List<ExecutiveClass> executiveClassList = init().getAllExecutiveClasses();
		List<ExecutiveClass> executiveClasses = new ArrayList<ExecutiveClass>();
		for (ExecutiveClass s : executiveClassList) {
			ExecutiveClass executiveClass = new ExecutiveClass();
			try {
				BeanUtils.copyProperties(executiveClass, s);
			} catch (Exception e) {
				Log.log.error("班级转换异常", e);
				e.printStackTrace();
			}
			executiveClasses.add(executiveClass);

		}
		Gson gson = new Gson();
		return gson.toJson(executiveClasses);
	}
}
