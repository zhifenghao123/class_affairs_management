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

import com.classaffairs.entity.Authority;
import com.classaffairs.framework.core.init.SpringContextHelper;
import com.classaffairs.framework.core.utils.DateUtil;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.ApplicationService;
import com.classaffairs.service.AuthorityRetrieveService;
import com.classaffairs.service.AuthorityWriteService;
import com.classaffairs.service.RoleService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author Haozhifeng
 *
 */
@Controller
public class AuthorityAction {
	@Resource
	private AuthorityRetrieveService authorityRetrieveServiceImpl;


	private AuthorityWriteService init() {
		ApplicationContext ctx = SpringContextHelper.getApplicationContext();
		AuthorityWriteService ars = (AuthorityWriteService) ctx.getBean("authorityWriteServiceImpl");
		return ars;
	}
	
	private ApplicationService getApplicationService() {
		ApplicationContext ctx = SpringContextHelper.getApplicationContext();

		ApplicationService appService = (ApplicationService) ctx.getBean("applicationServiceImpl");

		return appService;
	}
	
	private RoleService getRoleService(){
		ApplicationContext ctx = SpringContextHelper.getApplicationContext();
		RoleService roles = (RoleService) ctx.getBean("roleServiceImpl");
		return roles;
	}
	
	/**
	 * 分页获取所有权限
	 */
	@RequestMapping(value = "/admin/getAuthority")
	@ResponseBody
	public String getAuthority(HttpServletRequest request) {
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

		Page<Authority> authorityPage = authorityRetrieveServiceImpl.getAllAuthoritysByName(page, rows, name);

		result.addProperty("total", authorityPage.getTotalCount());

		JsonArray array = new JsonArray();

		if (authorityPage != null) {

			List<Authority> authList = authorityPage.getResult();

			for (Authority auth : authList) {

				JsonObject em = new JsonObject();

				em.addProperty("authorityId", auth.getAuthorityId());

				em.addProperty("name", auth.getName());

				em.addProperty("state", auth.getState());

				em.addProperty("createdate", DateUtil.formatDate(auth.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));

				array.add(em);
			}

		}
		result.add("rows", array);

		return result.toString();

	}
	
	
	/**
	 * 获取所有权限
	 */
	@RequestMapping(value = "/admin/getAllAuthorityList")
	@ResponseBody
	public String getAllAuthorityList() {

		List<Authority> authorityList = authorityRetrieveServiceImpl.getAuthoritysByIsOnUse(Authority.ONUSE);

		List<Authority> authList = new ArrayList<Authority>();
		for (Authority auth : authorityList) {

			Authority authority = new Authority();

			try {
				BeanUtils.copyProperties(authority, auth);
			} catch (Exception e) {
				Log.log.error("权限转换异常", e);
				e.printStackTrace();
			}
			authList.add(authority);

		}
		Gson gson = new Gson();

		return gson.toJson(authList);
	}
	
	/**
	 * 通过权限id获取权限
	 */
	@RequestMapping(value = "/admin/getAuthorityById")
	@ResponseBody
	public String getAuthorityById(String id) {

		id = id == null ? "" : id.trim();

		Long authorityId = Long.valueOf(id);

		Authority authority = authorityRetrieveServiceImpl.findAuthorityByAuthorityId(authorityId);

		Gson gson = new Gson();

		return gson.toJson(authority);
	}
	
}
