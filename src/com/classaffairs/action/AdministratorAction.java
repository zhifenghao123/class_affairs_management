package com.classaffairs.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.classaffairs.common.CreatePrimaryKey;
import com.classaffairs.common.md5.MD5Data;
import com.classaffairs.entity.Administrator;
import com.classaffairs.entity.PrimaryKey;
import com.classaffairs.framework.core.utils.DateUtil;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.AdministratorService;
import com.classaffairs.service.RoleService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Controller
public class AdministratorAction {
	
	@Resource
	private UserDetailsService classAffairUserDetailsService;
	@Autowired
	private AdministratorService itsAdministratorService;
	@Autowired
	private RoleService itsRoleService;
	
	@RequestMapping(value = "/admin/getAdministratorState.action")
	@ResponseBody
	public String getAdministratorState(String administratorNo) {
		administratorNo = administratorNo == null ? "" : administratorNo.trim();
		Administrator administrator = itsAdministratorService.findAdministratorByAdministratorNo(administratorNo);
		//Administrator administrator = new Administrator();
		JsonObject obj = new JsonObject();
		if (administrator == null) {
			obj.addProperty("msg", -1);
		} else {
			int state = administrator.getState();
			obj.addProperty("msg", state);
			obj.addProperty("msg", true);
		}
		return obj.toString();
	}
	
	@RequestMapping(value = "/admin/getJobNoToSession.action")
	public @ResponseBody
	String getJobNoToSession(HttpServletRequest request) {
		JsonObject obj = new JsonObject();
		String JobNo = request.getParameter("userId");
		if (JobNo == null || JobNo.trim().equals("")) {
			obj.addProperty("success", false);
		} else {
			String sessionJobNo = (String) request.getSession().getAttribute("jobNo");

			if (sessionJobNo != null && JobNo.trim().equals(sessionJobNo.trim())) {
				obj.addProperty("success", true);
			} else {
				obj.addProperty("success", false);
			}
		}
		return obj.toString();

	}
	
	/**
	 * 内部员工修改密码
	 */
	@RequestMapping(value = "/admin/updatePwd")
	@ResponseBody
	public String updatePwd(HttpServletRequest request) {
		String oldPwd = request.getParameter("oldPwd");

		Long jobId = (Long) request.getSession(false).getAttribute("jobId");

		JsonObject result = new JsonObject();

		Administrator administrator = itsAdministratorService.findAdministratorByAdministratorId(jobId);

		oldPwd = MD5Data.encryption(oldPwd.trim());

		if (administrator == null || !administrator.getPassword().equals(oldPwd)) {

			result.addProperty("noEqual", true);

			return result.toString();
		}

		String newPwd = request.getParameter("newPwd");

		newPwd = MD5Data.encryption(newPwd.trim());

		administrator.setPassword(newPwd);

		boolean success = itsAdministratorService.updateAdministrator(administrator);

		result.addProperty("success", success);

		return result.toString();
	}
	
	/**
	 * 分页获取所有内部员工信息
	 */
	@RequestMapping(value = "/admin/getAdministrator")
	@ResponseBody
	public String getAdministrator(HttpServletRequest request) {
		String page = request.getParameter("page");

		String rows = request.getParameter("rows");

		String name = request.getParameter("name");

		name = name == null ? "" : name.trim();

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

		JsonObject result = new JsonObject();

		Page<Administrator> AdministratorPage = itsAdministratorService.pageQueryAdministatorsByName(name, page, rows);

		result.addProperty("total", AdministratorPage.getTotalCount());

		JsonArray array = new JsonArray();

		if (AdministratorPage != null) {

			List<Administrator> administratorList = AdministratorPage.getResult();

			for (Administrator administrator : administratorList) {

				JsonObject em = new JsonObject();

				em.addProperty("administratorId", administrator.getAdministratorId());

				em.addProperty("administratorNo", administrator.getAdministratorNo());

				em.addProperty("name", administrator.getName());

				em.addProperty("roleName", itsRoleService.findRoleByRoleId(administrator.getRoleId()).getName());

				em.addProperty("createDate", DateUtil.formatDate(administrator.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));

				em.addProperty("lastUpdateDate", DateUtil.formatDate(administrator.getLastUpdateDate(), "yyyy-MM-dd HH:mm:ss"));

				em.addProperty("state", administrator.getState());

				array.add(em);

			}
		}

		result.add("rows", array);

		return result.toString();
	}
	
	/**
	 * 新增内部员工信息
	 */
	@RequestMapping(value = "/admin/addAdministrator")
	@ResponseBody
	public String addAdministrator(Administrator administrator, HttpServletRequest request) {

		JsonObject result = new JsonObject();

		Long administratorId = CreatePrimaryKey.getInstnce().getObjectId(PrimaryKey.ADMINISTRATOR_OBJECTTYPE);

		administrator.setAdministratorId(administratorId);
		
		administrator.setPassword(MD5Data.encryption(administrator.getAdministratorNo()));

		administrator.setCreateDate(new Date());

		administrator.setLastUpdateDate(new Date());

		administrator.setState(Administrator.STATE_ONUSER);

		boolean success = itsAdministratorService.addAdministrator(administrator);

		if (success)
			classAffairUserDetailsService.loadUserByUsername((String) request.getSession().getAttribute("jobNo") + "-");

		result.addProperty("success", success);

		return result.toString();
	}

	/**
	 * 更新内部员工信息
	 */
	@RequestMapping(value = "/admin/updateAdministrator")
	@ResponseBody
	public String updateAdministrator(Administrator administrator, HttpServletRequest request) {

		JsonObject result = new JsonObject();

		Administrator itsadministrator = itsAdministratorService.findAdministratorByAdministratorId(administrator.getAdministratorId());

		itsadministrator.setAdministratorNo(administrator.getAdministratorNo());

		itsadministrator.setName(administrator.getName());

		itsadministrator.setLastUpdateDate(new Date());

		itsadministrator.setRoleId(administrator.getRoleId());

		boolean success = itsAdministratorService.updateAdministrator(itsadministrator);

		if (success)
			classAffairUserDetailsService.loadUserByUsername((String) request.getSession().getAttribute("jobNo") + "-");
			
       result.addProperty("success", success);
		return result.toString();
	}

	/**
	 * 删除内部员工信息
	 */
	@RequestMapping(value = "/admin/deleteAdministratorById")
	@ResponseBody
	public String deleteAuthorityById(String id) {

		id = id == null ? "" : id.trim();

		Long administratorId = Long.valueOf(id);

		//boolean success = init().deleteadministrator(administratorId);//haozhifeng20170409将删除内部员工有物理删除修改为逻辑删除
		Administrator administrator = itsAdministratorService.findAdministratorByAdministratorId(administratorId);
		administrator.setState(3);
		administrator.setLastUpdateDate(new Date());
		boolean success = itsAdministratorService.updateAdministrator(administrator);
		
		JsonObject result = new JsonObject();

		result.addProperty("success", success);

		return result.toString();
	}
	
	/**
	 * 通过员工id获取内部员工信息
	 */
	@RequestMapping(value = "/admin/getAdministratorById")
	@ResponseBody
	public String getAdministratorById(String administratorId) {

		administratorId = administratorId == null ? "" : administratorId.trim();
		
		JsonObject jo = new JsonObject();
		
		Long adminId = Long.valueOf(administratorId);

		Administrator administrator = itsAdministratorService.findAdministratorByAdministratorId(adminId);


		/*try {
			BeanUtils.copyProperties(itsEmployee, administrator);//对外暴露太对信息
		} catch (Exception e) {
			Log.log.error("内部员工bean转换异常", e);
			e.printStackTrace();
		}
		Gson gson = new Gson();
		return gson.toJson(itsEmployee);*/
		
		//jo.addProperty("administratorId", administrator.getAdministratorId());
		jo.addProperty("administratorNo", administrator.getAdministratorNo());
		jo.addProperty("name", administrator.getName());
		jo.addProperty("roleId", administrator.getRoleId());

		return jo.toString();
	}
	
}
