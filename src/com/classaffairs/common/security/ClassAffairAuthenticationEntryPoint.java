package com.classaffairs.common.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.classaffairs.common.CommonPath;

public class ClassAffairAuthenticationEntryPoint implements AuthenticationEntryPoint {

	/**
	 * 用户访问资源时，发生授权异常（AuthenticationException）或认证异（AccessDeniedException），
	 * ExceptionTranslationFilter通过调用AuthenticationEntryPoint的commence方法发起认证过程。
	 * 如果ExceptionTranslationFilter接收到的是授权异常，并且当前认证过的票据不是匿名票据
	 * （AnonymousAuthenticationToken），将不会发起认证过程，而是交给AccessDeniedHandler处理
	 * （一般会直接提示用户拒绝访问）。
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,AuthenticationException authException) throws IOException, ServletException {
		String targetUrl = null;
		String url = request.getRequestURI();

		if (url.indexOf("admin") != -1) {
			//未登录而访问后台受控资源时，跳转到后台登录页面   
			targetUrl = "admin/login.jsp";
			targetUrl = CommonPath.getBasePath(request) + targetUrl;
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter pw = response.getWriter();
			pw.println("<script>window.top.location.href='" + targetUrl + "';</script>");
			pw.flush();
		} else {
			//未登录而访问前台受控资源时，跳转到前台登录页面    
			targetUrl = CommonPath.getBasePath(request);
			response.sendRedirect(targetUrl);
		}
	}

}
