/**
 * 
 */
package com.classaffairs.common;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;

import com.classaffairs.entity.Administrator;
import com.classaffairs.entity.Application;
import com.classaffairs.entity.Role;
import com.classaffairs.framework.core.init.SpringContextHelper;
import com.classaffairs.service.AdministratorService;
import com.classaffairs.service.ApplicationService;
import com.classaffairs.service.RoleService;

/**
 * @author Haozhifeng
 *
 */
public class AuthorityUtil {
	
	private ApplicationService getApplicationService() {
		ApplicationContext ctx = SpringContextHelper.getApplicationContext();

		ApplicationService appService = (ApplicationService) ctx.getBean("applicationServiceImpl");

		return appService;
	}

	private AdministratorService getAdministratorService() {
		ApplicationContext ctx = SpringContextHelper.getApplicationContext();
		AdministratorService administratorService = (AdministratorService) ctx.getBean("administratorServiceImpl");
		return administratorService;
	}

	private RoleService getRoleService() {
		ApplicationContext ctx = SpringContextHelper.getApplicationContext();

		RoleService roleservice = (RoleService) ctx.getBean("roleServiceImpl");

		return roleservice;
	}

	private HttpSession session;

	public AuthorityUtil(HttpSession session) {
		this.session = session;
	}
	
	/**
	 * 判断当前用户是否可以访问某资源
	 * 
	 * @param url 资源地址
	 * @return
	 */
	public boolean haveAuthority(String url) {

		Application app = getApplicationService().getApplicationsByUrl(url);

		if ((app == null) || (app.getApplicationId() == null) || (app.getEntryAuthorityIds() == null) || (app.getEntryAuthorityIds().equals(""))) {
			return true;
		}

		if (session == null || session.getAttribute("jobNo") == null) {
			return false;
		}
		String jobNo = (String) session.getAttribute("jobNo");

		Administrator administrator = getAdministratorService().findAdministratorByAdministratorNo(jobNo);

		if ((null != administrator) && (administrator.getRoleId() != null)) {

			Long roleId = administrator.getRoleId();

			if (roleId != null && !(roleId.equals(""))) {
				Role role = getRoleService().findRoleByRoleId(roleId);
				if (role != null && (Role.ONUSE == role.getState())) {
					String authorityIds = role.getAuthorityCode();
					String[] ids = authorityIds.split(",");
					if (ids.length > 0) {
						String authoritys = app.getEntryAuthorityIds();
						String[] authority = authoritys.split(",");
						List<String> authList = Arrays.asList(ids);
						List<String> appAuthList = Arrays.asList(authority);
						for (String auth : appAuthList) {
							if (authList.contains(auth)) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
}
