/**
 * 
 */
package com.classaffairs.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.classaffairs.common.CreatePrimaryKey;
import com.classaffairs.entity.Department;
import com.classaffairs.entity.PrimaryKey;
import com.classaffairs.entity.School;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.DepartmentService;
import com.classaffairs.service.SchoolService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author Haozhifeng
 *
 */
@Controller
public class DepartmentAction {

	@Autowired
	private DepartmentService itsDepartmentService;
	@Autowired
	private SchoolService itsSchoolService;
	
	/**
	 * 分页获取所有系（所）信息
	 */
	@RequestMapping(value = "/admin/getDepartment")
	@ResponseBody
	public String getDepartment(HttpServletRequest request) {
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
		Page<Department> departmentPage = itsDepartmentService.getDepartmentsByPageQuery(name, Integer.valueOf(page), Integer.valueOf(rows));
		result.addProperty("total", departmentPage.getTotalCount());

		JsonArray array = new JsonArray();
		if (departmentPage != null) {

			List<Department> regionList = departmentPage.getResult();
			for (Department department : regionList) {
				JsonObject re = new JsonObject();
				re.addProperty("departmentId", department.getDepartmentId());
				re.addProperty("departmentNo", department.getDepartmentNo());
				re.addProperty("name", department.getName());
				String schoolAbbreviationName = null;
				String schoolNo = null;
				if(department.getSchoolNo() != null && !("").equals(department.getSchoolNo())){
					School school = itsSchoolService.findSchoolBySchoolNo(department.getSchoolNo());
					schoolAbbreviationName = school.getAbbreviation();
					schoolNo = school.getSchoolNo();
				}
				
				re.addProperty("schoolAbbreviationName", schoolAbbreviationName);
				re.addProperty("schoolNo", schoolNo);
				re.addProperty("departmentLeaderId", department.getDepartmentLeaderId());
				re.addProperty("departmentLeaderName", department.getDepartmentLeaderName());
				
				array.add(re);

			}

		}
		result.add("rows", array);

		return result.toString();
	}
	
	/**
	 * 新增系（所）
	 */
	@RequestMapping(value = "/admin/addDepartment")
	@ResponseBody
	public String addDepartment(Department department, HttpServletRequest request) {

		String departmentNo = department.getDepartmentNo();
		departmentNo = departmentNo == null ? "" : departmentNo.trim();
		
		JsonObject result = new JsonObject();
		Department departmentExist = itsDepartmentService.findDepartmentByDepartmentNo(departmentNo);

		if (departmentExist != null) {
			result.addProperty("exsit", true);
			return result.toString();
		} else {
			result.addProperty("exsit", false);
		}
		Long departmentId = CreatePrimaryKey.getInstnce().getObjectId(PrimaryKey.Department_OBJECTTYPE);
		department.setDepartmentId(departmentId);

		boolean success = itsDepartmentService.addDepartment(department);

		result.addProperty("success", success);

		return result.toString();
	}
	
	/**
	 * 通过应用id获取系（所）
	 */
	@RequestMapping(value = "/admin/getDepartmentById")
	@ResponseBody
	public String getDepartmentById(String id) {

		Long departmentId = Long.valueOf(id.trim());

		Department department = itsDepartmentService.findDepartmentByDepartmentId(departmentId);

		Department app = new Department();

		try {
			BeanUtils.copyProperties(app, department);
		} catch (Exception e) {
			Log.log.error("应用bean转换异常", e);
			e.printStackTrace();
		}

		Gson gson = new Gson();

		return gson.toJson(app);
	}

	/**
	 * 更新系（所）
	 */
	@RequestMapping(value = "/admin/updateDepartment")
	@ResponseBody
	public String updateRole(Department department) {

		JsonObject result = new JsonObject();

		boolean success = itsDepartmentService.updateDepartment(department);

		result.addProperty("success", success);

		return result.toString();
	}
	/**
	 * 根据学院获取所有系（所）
	 */
	@RequestMapping(value = "/admin/getDepartmentsBySchoolNo")
	@ResponseBody
	public String getDepartmentListBySchoolNo(String schoolNo) {

		JsonArray result = new JsonArray();

		List<Department> departments = itsDepartmentService.findDepartmentListBySchoolNo(schoolNo);

		for(Department m : departments){
			JsonObject jo = new JsonObject();
			jo.addProperty("departmentNo", m.getDepartmentNo());
			jo.addProperty("name", m.getName());
			result.add(jo);
		}

		return result.toString();
	}
	/**
	 * 删除系（所）
	 */
	@RequestMapping(value = "/admin/deleteDepartmentById")
	@ResponseBody
	public String deleteDepartmentById(String id) {

		JsonObject result = new JsonObject();

		Long departmentId = Long.valueOf(id.trim());

		boolean success = itsDepartmentService.deleteDepartment(departmentId);

		result.addProperty("success", success);

		return result.toString();
	}
}
