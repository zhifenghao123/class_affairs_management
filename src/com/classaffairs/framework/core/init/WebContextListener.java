package com.classaffairs.framework.core.init;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.classaffairs.framework.core.exception.CoreException;
import com.classaffairs.framework.core.utils.Log;

public class WebContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		 SpringContextHelper.setApplicationContext(null);
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		ServletContext servletContext = sce.getServletContext();
	    try{
	      initSpringContext(servletContext);
	    }catch (CoreException e){
	      Log.log.error("系统启动初始化异常：【" + e.getMessage() + "】。");
	      e.printStackTrace();
	    }
	}
	
	private void initSpringContext(ServletContext servletContext)
		throws CoreException{
		    try{
		      Thread.currentThread().getContextClassLoader().loadClass("com.classaffairs.framework.core.init.SpringContextHelper");
		      WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		      SpringContextHelper.setApplicationContext(wc);
		    }catch (Exception e){
		      throw new CoreException("初始化Spring上下文出现异常！", e);
		    }
		  }

}
