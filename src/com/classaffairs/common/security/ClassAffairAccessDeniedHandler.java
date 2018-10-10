/**
 * 
 */
package com.classaffairs.common.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.classaffairs.common.CommonPath;

/**
 * @author Haozhifeng
 *
 */
public class ClassAffairAccessDeniedHandler implements AccessDeniedHandler {
	/**
	 * 用户访问资源时，发生授权异常（AuthenticationException）或认证异（AccessDeniedException），
	 * ExceptionTranslationFilter通过调用AuthenticationEntryPoint的commence方法发起认证过程。
	 * 如果ExceptionTranslationFilter接收到的是授权异常，并且当前认证过的票据不是匿名票据
	 * （AnonymousAuthenticationToken），将不会发起认证过程，而是交给AccessDeniedHandler处理
	 * （一般会直接提示用户拒绝访问）。
	 */
	@Override
	public void handle(HttpServletRequest req, HttpServletResponse resp,
			AccessDeniedException arg2) throws IOException, ServletException {
		boolean isAjax = "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));

		String url = req.getRequestURI();
		// 如果是ajax请求
		if (isAjax) {

			String jsonObject;
			if (url.indexOf("admin") != -1) {//返回跳转到后台登录页面的标记

				jsonObject = "{\"togym-access-denied\":true,\"togym-no-right\":true,\"togym-admin\":true}";
			} else {//返回跳转到首页的标记
				jsonObject = "{\"togym-access-denied\":true,\"togym-no-right\":true,\"togym-admin\":false}";
			}
			String contentType = "application/json";
			resp.setContentType(contentType);
			PrintWriter out = resp.getWriter();
			out.print(jsonObject);
			out.flush();
			out.close();
			return;
		} else {
			String targetUrl = null;

			if (url.indexOf("admin") != -1) {
				//跳转到后台登录页面
				targetUrl = "admin/login.jsp";
				targetUrl = CommonPath.getBasePath(req) + targetUrl;
				resp.setContentType("text/html; charset=UTF-8");
				PrintWriter pw = resp.getWriter();
				pw.println("<script>window.top.location.href='" + targetUrl + "';</script>");
				pw.flush();
			} else {
				//跳转到首页
				targetUrl = CommonPath.getBasePath(req);
				resp.sendRedirect(targetUrl);
			}
		}

	}

}
