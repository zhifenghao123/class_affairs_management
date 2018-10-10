package com.classaffairs.common.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
//这里是拦截器拦截http请求的入口
/**
* 
* @author Haozhifeng
* AbstractSecurityInterceptor调用SecurityMetadataSource取得资源的所有可访问权限，
* 然后再调用AccessDecisionManager来实现决策，确定用户是否有权限访问该资源。 
*/
public class ClassAffairAbstractSecurityInterceptor extends
		AbstractSecurityInterceptor implements Filter {

	private FilterInvocationSecurityMetadataSource securityMetadataSource;
	
	public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
		return this.securityMetadataSource;
	}
	public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource newSource) {
		this.securityMetadataSource = newSource;
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		FilterInvocation fi = new FilterInvocation(request, response, chain);
		invoke(fi);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public Class<? extends Object> getSecureObjectClass() {
		return FilterInvocation.class;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	//这是具体的拦截调用
		public void invoke(FilterInvocation fi) throws IOException, ServletException {
			//这里是做安全检查的地方  
			InterceptorStatusToken token = super.beforeInvocation(fi);
			try {
				//接着向拦截器链执行 
				fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
			} finally {
				super.afterInvocation(token, null);
			}
		}
}
