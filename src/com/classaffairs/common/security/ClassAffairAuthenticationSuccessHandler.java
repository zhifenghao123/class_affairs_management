package com.classaffairs.common.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.classaffairs.common.OnlineUserBindingListener;
import com.classaffairs.entity.Administrator;
import com.classaffairs.entity.Student;
import com.classaffairs.service.AdministratorService;
import com.classaffairs.service.StudentService;
import com.classaffairs.servlet.SessionContext;

public class ClassAffairAuthenticationSuccessHandler extends
		SimpleUrlAuthenticationSuccessHandler implements
		AuthenticationSuccessHandler {
	@Autowired
	private StudentService itsStudentService;
	@Autowired
	private AdministratorService itsAdministratorService;

	public ClassAffairAuthenticationSuccessHandler() {
	}
	public ClassAffairAuthenticationSuccessHandler(String defaultTargetUrl) {
		setDefaultTargetUrl(defaultTargetUrl);
	}
	
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		String account = request.getParameter("j_username");
		// 获取登录后的用户名
		ClassAffairUserDetails attributePrincipal = (ClassAffairUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    if (attributePrincipal!=null){
	    	account=attributePrincipal.getUsername();
	    	
			HttpSession session = SessionContext.getInstance().getSession(request.getParameter("sessionId"));
			if(session ==null)
				session = request.getSession(true);
			OnlineUserBindingListener online = null;
			if(!account.contains("-")) {
					Student student = (Student) itsStudentService.findStudentByStudentNo(account);
					session.setAttribute("studentId", student.getStudentId());
					session.setAttribute("studentNo", account);
					session.setAttribute("studentName", student.getName());
					//session.setAttribute("type", "student");
					online = new OnlineUserBindingListener(student.getStudentId().toString());
					session.setAttribute("online", online);
					session.setAttribute("login", "true");
					if(null != student.getIsMonitor()&&student.getIsMonitor().equals(1)){
						session.setAttribute("userType", "monitor");
						session.setAttribute("isMonitor", true);
					}else{
						session.setAttribute("userType", "student");
						session.setAttribute("isMonitor", false);
					}
			
			} else {
				account = account.substring(0,account.length()-1);
				Administrator administrator = itsAdministratorService.findAdministratorByAdministratorNo(account);
				session.setAttribute("admin", "true");
				session.setAttribute("jobNo", account);
				session.setAttribute("jobId", administrator.getAdministratorId());
				session.setAttribute("jobName", administrator.getName());
				session.setAttribute("type", "admin");
			}
		    session.setAttribute("authentication", authentication);
		}

		super.onAuthenticationSuccess(request, response, authentication); 

	}
}
