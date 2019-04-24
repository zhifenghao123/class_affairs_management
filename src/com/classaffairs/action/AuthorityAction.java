/**
 * 
 */
package com.classaffairs.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.classaffairs.common.CreatePrimaryKey;
import com.classaffairs.entity.Application;
import com.classaffairs.entity.Authority;
import com.classaffairs.entity.PrimaryKey;
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
				
				em.addProperty("updateDate", DateUtil.formatDate(auth.getupdateDate(), "yyyy-MM-dd HH:mm:ss"));

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
	
	/**
	 * 更新权限
	 * */
	@RequestMapping(value="/admin/updateAuthority")
	@ResponseBody
	public String updateAuthority(HttpServletRequest request){
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		id = id == null ? "" : id.trim();
		name =name == null ? "" : name.trim();
		Long authorityId = Long.valueOf(id);
		JsonObject result = new JsonObject();
		Authority authority = authorityRetrieveServiceImpl.findAuthorityByAuthorityId(authorityId);

		if (authority == null) {
			result.addProperty("success", false);
		} else {
			Authority auth = authorityRetrieveServiceImpl.getAuthoritybyName(name);
			if (auth != null) {
				result.addProperty("exsit", true);
				return result.toString();
			} else {
				result.addProperty("exsit", false);
			}
			authority.setAuthorityId(authorityId);
			authority.setName(name);
			authority.setState(Authority.ONUSE);
			authority.setUpdateDate(new Date());
			boolean success = init().updateAuthority(authority);
			result.addProperty("success", success);
		}
		return result.toString();
	}
	
	/**
	 * 新增权限
	 */
	@RequestMapping(value = "/admin/addAuthority")
	@ResponseBody
	public String addAuthority(String name) {

		name = name == null ? "" : name.trim();

		JsonObject result = new JsonObject();

		Authority auth = authorityRetrieveServiceImpl.getAuthoritybyName(name);

		if (auth != null) {
			result.addProperty("exsit", true);

			return result.toString();

		} else {

			result.addProperty("exsit", false);

		}
		Long authorityId = (Long) CreatePrimaryKey.getInstnce().getObjectId(PrimaryKey.AUTHORITY_OBJECTTYPE);

		Authority authority = new Authority();

		authority.setAuthorityId(authorityId);

		authority.setName(name);

		authority.setState(Authority.ONUSE);

		authority.setCreateDate(new Date());
		
		boolean success = init().addAuthority(authority);

		result.addProperty("success", success);
		
		List<Application> applications =  getApplicationService().getapplicationByflag("1");
        
		for(Application app:applications){
			
			String authorityIdArray = app.getEntryAuthorityIds();
			String authoritys = authorityIdArray+","+String.valueOf(authorityId);
			app.setEntryAuthorityIds(authoritys);
			 getApplicationService().updateApplication(app);
		}
		
		return result.toString();
	}
	
}
