/**
 * 
 */
package com.classaffairs.common;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.classaffairs.service.impl.oplog.EntityForOplogManager;

/**
 * @author Haozhifeng
 *
 */
public class LoadInfoListener implements ServletContextListener {

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		BaseInfoManager.getInstance().init();
		//初始化日志信息
		EntityForOplogManager.getInstance().init();
	}

}
