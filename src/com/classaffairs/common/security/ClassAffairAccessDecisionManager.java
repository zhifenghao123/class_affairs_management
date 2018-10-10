package com.classaffairs.common.security;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class ClassAffairAccessDecisionManager implements AccessDecisionManager {
	//这里定义了决策机制，需要全票才能通过 
	@Override
	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes) throws AccessDeniedException,
			InsufficientAuthenticationException {
		if (configAttributes == null) {
			return;
		}

		Iterator<ConfigAttribute> ite = configAttributes.iterator();
		while (ite.hasNext()) {
			ConfigAttribute ca = ite.next();
			String needRole = ((SecurityConfig) ca).getAttribute();
			for (GrantedAuthority ga : authentication.getAuthorities()) {
				if (needRole.trim().equals(ga.getAuthority().trim())) { //ga is user's role.
					return;
				}
			}
		}
		throw new AccessDeniedException("no right");

	}

	@Override
	public boolean supports(ConfigAttribute arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return true;
	}

}
