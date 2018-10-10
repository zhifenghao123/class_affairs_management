package com.classaffairs.framework.core.init;

import org.springframework.context.ApplicationContext;

import com.classaffairs.dao.PrimaryKeyDao;

public class SpringContextHelper {
	private static ApplicationContext applicationContext = null;
	public static ApplicationContext getApplicationContext(){
	   return applicationContext;
	}
	
	public static void setApplicationContext(ApplicationContext appContext){
	   applicationContext = appContext;
	}
	
	 /* public static BaseDaoImpl getBaseDao()
	  {
	    BaseDaoImpl baseDao = (BaseDaoImpl)applicationContext.getBean("baseDaoImpl");
	    return baseDao;
	  }*/
	public static PrimaryKeyDao getPrimaryKeyDao()
	  {
		PrimaryKeyDao primaryKeyDao = (PrimaryKeyDao)applicationContext.getBean("primaryKeyDao");
	    return primaryKeyDao;
	  }
}
