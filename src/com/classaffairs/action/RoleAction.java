package com.classaffairs.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.classaffairs.common.CreatePrimaryKey;
import com.classaffairs.entity.Administrator;
import com.classaffairs.entity.Authority;
import com.classaffairs.entity.PrimaryKey;
import com.classaffairs.entity.Role;
import com.classaffairs.framework.core.init.SpringContextHelper;
import com.classaffairs.framework.core.utils.DateUtil;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.AdministratorService;
import com.classaffairs.service.AuthorityRetrieveService;
import com.classaffairs.service.RoleService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Controller
public class RoleAction {
	
	@Resource
	private UserDetailsService classAffairUserDetailsService;
	
	
	@Autowired
	private AdministratorService itsAdministratorService;

	@Resource
	private AuthorityRetrieveService authorityRetrieveServiceImpl;

	private RoleService init() {
		ApplicationContext ctx = SpringContextHelper.getApplicationContext();

		RoleService roleservice = (RoleService) ctx.getBean("roleServiceImpl");

		return roleservice;
	}
	
	/**
	 * 分页获取所有角色信息
	 */
	@RequestMapping(value = "/admin/getRole")
	@ResponseBody
	public String getRole(HttpServletRequest request) {
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

		Page<Role> rolePage = init().pageQueryRole(name, page, rows);

		result.addProperty("total", rolePage.getTotalCount());

		JsonArray array = new JsonArray();

		if (rolePage != null) {

			List<Role> roList = rolePage.getResult();

			for (Role ro : roList) {

				JsonObject role = new JsonObject();

				role.addProperty("name", ro.getName());

				role.addProperty("roleId", ro.getRoleId());

				//role.addProperty("creatorName", itsEmployeeRetrieveService.getInteriorEmployeeById((long) ro.getCreatorid()).getRealname());
				role.addProperty("creatorName", itsAdministratorService.findAdministratorByAdministratorId(Long.valueOf(ro.getCreatorId())).getName());
				
				String authoritys = "";

				if (ro.getAuthorityCode() != null && !(ro.getAuthorityCode().trim().equals(""))) {

					String[] authorityIdArray = ro.getAuthorityCode().split(",");

					for (String authorityId : authorityIdArray) {

						Authority authority = authorityRetrieveServiceImpl.findAuthorityByAuthorityId(Long.valueOf(authorityId));

						authoritys += authority.getName() + ",";
					}
					authoritys = authoritys.substring(0, authoritys.length() - 1);
				}

				role.addProperty("authorityCode", authoritys);

				role.addProperty("creatortype", ro.getCreatorType());

				role.addProperty("createdate", DateUtil.formatDate(ro.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));

				role.addProperty("state", ro.getState());

				array.add(role);

			}

		}
		result.add("rows", array);

		return result.toString();
	}
	
	/**
	 * 获取所有角色信息
	 */
	@RequestMapping(value = "/admin/getAllRoleList")
	@ResponseBody
	public String getAllAuthorityList() {

		List<Role> roleList = init().getRoleByState(Role.ONUSE);

		List<Role> roList = new ArrayList<Role>();

		for (Role role : roleList) {

			Role ro = new Role();

			try {
				ConvertUtils.register(new DateConverter(null), java.util.Date.class);
				BeanUtils.copyProperties(ro, role);
			} catch (Exception e) {
				Log.log.error("角色转换异常", e);
				e.printStackTrace();
			}
			roList.add(ro);

		}

		Gson gson = new Gson();

		return gson.toJson(roList);
	}
	
	/**
	 * 新增角色信息
	 */
	@RequestMapping(value = "/admin/addRole")
	@ResponseBody
	public String addRole(Role role, HttpServletRequest request) {

		String name = role.getName();

		name = name == null ? "" : name.trim();

		JsonObject result = new JsonObject();

		Role ro = init().getSingleRoleByName(name);

		if (ro != null) {
			result.addProperty("exsit", true);

			return result.toString();

		} else {

			result.addProperty("exsit", false);

		}
		Long roleId = (Long) CreatePrimaryKey.getInstnce().getObjectId(PrimaryKey.ROLE_OBJECTTYPE);

		role.setRoleId(roleId);

		role.setCreateDate(new Date());

		role.setState(Role.ONUSE);

		role.setCreatorId(Long.valueOf(request.getSession().getAttribute("jobId").toString().trim()));

		boolean success = init().addRole(role);

		result.addProperty("success", success);

		return result.toString();
	}
	
	/**
	 * 更新角色信息
	 */
	@RequestMapping(value = "/admin/updateRole")
	@ResponseBody
	public String updateRole(Role role, HttpServletRequest request) {

		JsonObject result = new JsonObject();

		role.setState(Role.ONUSE);

		boolean success = init().updateRole(role);

		

		if (success)
			classAffairUserDetailsService.loadUserByUsername((String) request.getSession().getAttribute("jobNo") + "-");
		
		result.addProperty("success", success);
		return result.toString();
	}

	/**
	 * 通过id获取角色信息
	 */
	@RequestMapping(value = "/admin/getRoleById")
	@ResponseBody
	public String getRoleById(String id) {

		id = id == null ? "" : id.trim();

		Long roleId = Long.valueOf(id);

		Role role = init().findRoleByRoleId(roleId);

		Role itsRole = new Role();

		try {
			ConvertUtils.register(new DateConverter(null), java.util.Date.class);
			BeanUtils.copyProperties(itsRole, role);
		} catch (Exception e) {
			Log.log.error("角色bean转换异常", e);
			e.printStackTrace();
		}
		Gson gson = new Gson();

		return gson.toJson(itsRole);
	}

	/**
	 * 删除角色信息
	 */
	@RequestMapping(value = "/admin/deleteRoleById")
	@ResponseBody
	public String deleteRoleById(String id) {

		id = id == null ? "" : id.trim();

		Long roleId = Long.valueOf(id);

		List<Administrator> employeeList = itsAdministratorService.getAdministratorsByRoleId(roleId);

		JsonObject result = new JsonObject();

		if (employeeList != null && employeeList.size() > 0) {

			result.addProperty("exsit", true);

			return result.toString();

		} else {

			result.addProperty("exsit", false);

		}

		boolean success = init().deleteRole(roleId);

		result.addProperty("success", success);

		return result.toString();
	}
	
}
