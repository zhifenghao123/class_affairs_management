package com.classaffairs.common.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;

import com.classaffairs.entity.Application;
import com.classaffairs.entity.Authority;
import com.classaffairs.service.ApplicationService;
import com.classaffairs.service.AuthorityRetrieveService;

public class ClassAffairFilterInvocationSecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource {
	
	private UrlMatcher urlMatcher = new AntUrlPathMatcher();
	private static Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

	private static ApplicationService itsApplicationService;

	//private static AuthorityService itsAuthorityService;
	private static AuthorityRetrieveService itsAuthorityService;
	
	/*@SuppressWarnings("static-access")
	public ClassAffairFilterInvocationSecurityMetadataSource(ApplicationService itsApplicationService, AuthorityService itsAuthorityService) {
		this.itsApplicationService = itsApplicationService;
		this.itsAuthorityService = itsAuthorityService;
		loadResourceDefine();
	}*/
	@SuppressWarnings("static-access")
	public ClassAffairFilterInvocationSecurityMetadataSource(ApplicationService itsApplicationService, AuthorityRetrieveService itsAuthorityService) {
		this.itsApplicationService = itsApplicationService;
		this.itsAuthorityService = itsAuthorityService;
		loadResourceDefine();
	}
	public static void loadResourceDefine() {

		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		List<Application> allApplications = itsApplicationService.finAllApplication();

		for (Application application : allApplications) {
			Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
			String codes = application.getEntryAuthorityIds();
			if (codes == null || codes.trim().equals(""))
				continue;
			for (String temp : codes.split(",")) {
				Authority Authority = itsAuthorityService.findAuthorityByAuthorityId(Long.valueOf(temp));

				if (Authority != null && 1 == Authority.getState()) {
					String code = Authority.getAuthorityId().toString();
					ConfigAttribute ca = new SecurityConfig(code);
					atts.add(ca);
				}
			}

			resourceMap.put(application.getUrl(), atts);
		}
	}
	
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
		}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		String url = ((FilterInvocation) object).getRequestUrl();
		Iterator<String> ite = resourceMap.keySet().iterator();
		while (ite.hasNext()) {
			String resURL = ite.next();
			//resURL：为需要拦截的url，：为用户请求url
			if (urlMatcher.pathMatchesUrl(resURL, url)) {
				return resourceMap.get(resURL);
			}
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
