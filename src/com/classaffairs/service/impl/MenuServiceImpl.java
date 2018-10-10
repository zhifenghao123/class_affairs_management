package com.classaffairs.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.classaffairs.entity.Administrator;
import com.classaffairs.entity.Application;
import com.classaffairs.entity.Authority;
import com.classaffairs.entity.Role;
import com.classaffairs.entity.UITreeNode;
import com.classaffairs.service.AdministratorService;
import com.classaffairs.service.ApplicationService;
import com.classaffairs.service.AuthorityRetrieveService;
import com.classaffairs.service.MenuService;
import com.classaffairs.service.RoleService;
@Service
public class MenuServiceImpl implements MenuService {
	
	@Resource
	private ApplicationService applicationServiceImpl;
	@Resource
	private AuthorityRetrieveService authorityRetrieveServiceImpl;
	@Autowired
	private AdministratorService itsAdministratorService;
	@Autowired
	private RoleService itsRoleService;

	@Override
	public List<UITreeNode> tree(String id, HttpServletRequest request) {
		Long pid = id == null ? 0L : (Long.valueOf(id));

		List<Application> appList = applicationServiceImpl.getApplicationByPareantAppId(pid);

		List<UITreeNode> treeNodeList = new ArrayList<UITreeNode>();

		for (Application app : appList) {

			if (!authority(app, request))
				continue;

			treeNodeList.add(tree(app));
		}

		return treeNodeList;
	}
	
	private UITreeNode tree(Application app) {

		UITreeNode node = new UITreeNode();

		node.setId(String.valueOf(app.getApplicationId()));

		node.setText(app.getDisplayName());

		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("url", app.getUrl());

		node.setAttributes(attributes);

		if (app.getApplication() != null && app.getApplication().size() > 0) {

			node.setState("closed");

			List<UITreeNode> childNodeList = new ArrayList<UITreeNode>();

			for (Application childApp : app.getApplication()) {

				UITreeNode childnode = tree(childApp);

				childNodeList.add(childnode);
			}
			node.setChild(childNodeList);
		}

		return node;
	}
	
	private boolean authority(Application app, HttpServletRequest request) {

		HttpSession session = request.getSession();

		if (session == null || session.getAttribute("jobNo") == null) {
			return false;
		}
		String jobNo = (String) session.getAttribute("jobNo");

		Administrator administrator = itsAdministratorService.findAdministratorByAdministratorNo(jobNo);

		if ((null != administrator) && (administrator.getAdministratorNo() != null)) {

			Long roleId = administrator.getRoleId();

			if (roleId != null && !(roleId.equals(""))) {
				Role role = itsRoleService.findRoleByRoleId(roleId);

				if (role != null && (Role.ONUSE == role.getState())) {
					String authorityIds = role.getAuthorityCode();
					Set<Long> authorityIdSet = new HashSet<Long>();
					String[] ids = authorityIds.split(",");
					if (ids.length > 0) {
						if (app.getParentApplicationId() == 0) {
							Set<Application> childApp = app.getApplication();
							for (Application application : childApp) {
								String codes = application.getEntryAuthorityIds();
								if (codes == null || codes.trim().equals(""))
									return true;
								for (String temp : codes.trim().split(",")) {
									Authority authority = authorityRetrieveServiceImpl.findAuthorityByAuthorityId(Long.valueOf(temp));

									if (Authority.ONUSE == authority.getState()) {
										Long code = authority.getAuthorityId();

										authorityIdSet.add(code);
									}
								}
							}

						} else {
							//if(!app.getType().equals("3")){
							String codes = app.getEntryAuthorityIds();
							if (codes == null || codes.trim().equals("")||app.getType().equals("3"))
								return true;
							for (String temp : codes.trim().split(",")) {
								Authority authority = authorityRetrieveServiceImpl.findAuthorityByAuthorityId(Long.valueOf(temp));

								if (Authority.ONUSE == authority.getState()) {
									Long code = authority.getAuthorityId();

									authorityIdSet.add(code);
								}
							}
							//}
						}
						for (String id : ids) {
							Authority a = authorityRetrieveServiceImpl.findAuthorityByAuthorityId(Long.valueOf(id));
							if (Authority.ONUSE == a.getState()) {
								if (authorityIdSet.contains(a.getAuthorityId())) {
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

}
