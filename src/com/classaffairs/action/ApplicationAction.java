/**
 * 
 */
package com.classaffairs.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.ApplicationService;
import com.classaffairs.service.AuthorityRetrieveService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * @author Haozhifeng
 *
 */
@Controller
public class ApplicationAction {
	@Resource
	private AuthorityRetrieveService authorityRetrieveServiceImpl;

	private ApplicationService init() {
		ApplicationContext ctx = SpringContextHelper.getApplicationContext();

		ApplicationService appService = (ApplicationService) ctx.getBean("applicationServiceImpl");

		return appService;
	}
	
	/**
	 * 分页获取所有应用
	 */
	@RequestMapping(value = "/admin/application")
	public @ResponseBody
	String getApplication(HttpServletRequest request) {

		String page = request.getParameter("page");

		String rows = request.getParameter("rows");

		String displayName = request.getParameter("displayName");

		displayName = displayName == null ? "" : displayName.trim();

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

		Gson gson = new Gson();

		Map<String, Object> result = new HashMap<String, Object>();

		Page<Application> applicationPage = init().getAllApplications(page, rows, displayName);

		result.put("total", applicationPage.getTotalCount());

		List<Application> appList = new ArrayList<Application>();

		if (applicationPage != null) {

			List<Application> applicationList = applicationPage.getResult();

			for (Application app : applicationList) {

				Application application = new Application();
				try {
					BeanUtils.copyProperties(application, app);
				} catch (Exception e) {
					Log.log.error("获取应用转换异常", e);
					e.printStackTrace();
				}
				String authoritys = "";

				if (application.getEntryAuthorityIds() != null && !(application.getEntryAuthorityIds().trim().equals(""))) {

					String[] authorityIdArray = application.getEntryAuthorityIds().split(",");

					for (String authorityId : authorityIdArray) {

						Authority authority = authorityRetrieveServiceImpl.findAuthorityByAuthorityId(Long.valueOf(authorityId));

						authoritys += authority.getName() + ",";
					}
					authoritys = authoritys.substring(0, authoritys.length() - 1);
				}

				application.setEntryAuthorityIds(authoritys);

				appList.add(application);
			}

		}
		result.put("rows", appList);
		return gson.toJson(result);
	}
	
	/**
	 * 新增应用
	 */
	@RequestMapping(value = "/admin/addApplication")
	@ResponseBody
	public String addRole(Application app, HttpServletRequest request) {

		String name = app.getDisplayName();

		name = name == null ? "" : name.trim();

		JsonObject result = new JsonObject();

		Application application = init().getApplicationsByDisplayName(name);

		if (application != null) {
			result.addProperty("exsit", true);

			return result.toString();

		} else {

			result.addProperty("exsit", false);

		}
		Long applicationId = CreatePrimaryKey.getInstnce().getObjectId(PrimaryKey.APPLICATION_OBJECTTYPE);

		app.setApplicationId(applicationId);

		app.setFlag(0);

		boolean success = init().addApplication(app);

		result.addProperty("success", success);

		return result.toString();
	}

	//通过菜单的id获取其子菜单
		@RequestMapping(value = "/admin/getApplicationByPid")
		@ResponseBody
		public String getApplicationByPid(String pid) {

			Long pId = Long.valueOf(pid.trim());

			List<Application> applicationList = init().getApplicationByPareantAppId(pId);

			List<Application> appList = new ArrayList<Application>();

			for (Application app : applicationList) {

				Application application = new Application();

				try {
					BeanUtils.copyProperties(application, app);
				} catch (Exception e) {
					Log.log.error("应用bean转换异常", e);
					e.printStackTrace();
				}

				appList.add(application);
			}

			Gson gson = new Gson();

			return gson.toJson(appList);
		}
		
		/**
		 * 通过应用id获取应用
		 */
		@RequestMapping(value = "/admin/getApplicationById")
		@ResponseBody
		public String getApplicationById(String id) {

			Long applicationId = Long.valueOf(id.trim());

			Application application = init().findApplicationByApplicationId(applicationId);

			Application app = new Application();

			try {
				BeanUtils.copyProperties(app, application);
			} catch (Exception e) {
				Log.log.error("应用bean转换异常", e);
				e.printStackTrace();
			}
			if (application.getParentApplicationId() == null)
				app.setParentApplicationId(null);;

			Gson gson = new Gson();

			return gson.toJson(app);
		}

		/**
		 * 更新应用
		 */
		@RequestMapping(value = "/admin/updateApplication")
		@ResponseBody
		public String updateRole(Application app) {

			JsonObject result = new JsonObject();

			boolean success = init().updateApplication(app);

			result.addProperty("success", success);

			return result.toString();
		}
}
