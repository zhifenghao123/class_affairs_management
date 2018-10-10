package com.classaffairs.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.classaffairs.common.md5.MD5Data;
import com.classaffairs.entity.Administrator;
import com.classaffairs.framework.core.utils.DateUtil;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.AdministratorService;
import com.classaffairs.service.RoleService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Controller
public class AdministratorAction {
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

				em.addProperty("interioradministratorId", administrator.getAdministratorId());

				em.addProperty("jobNo", administrator.getAdministratorNo());

				em.addProperty("realname", administrator.getName());

				em.addProperty("roleName", itsRoleService.findRoleByRoleId(administrator.getRoleId()).getName());

				em.addProperty("createdate", DateUtil.formatDate(administrator.getCreatedate(), "yyyy-MM-dd HH:mm:ss"));

				em.addProperty("lastdate", DateUtil.formatDate(administrator.getLastdate(), "yyyy-MM-dd HH:mm:ss"));

				em.addProperty("state", administrator.getState());

				array.add(em);

			}
		}

		result.add("rows", array);

		return result.toString();
	}
	
}
