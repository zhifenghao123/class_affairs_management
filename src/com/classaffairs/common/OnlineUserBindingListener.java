/**
 * 
 */
package com.classaffairs.common;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 * @author Haozhifeng
 *
 */
public class OnlineUserBindingListener implements HttpSessionBindingListener {

	String userId;

	public OnlineUserBindingListener(String userId) {
		this.userId = userId;
	}
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionBindingListener#valueBound(javax.servlet.http.HttpSessionBindingEvent)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();
		// 把用户名放入在线列表 
		List<String> onlineUserList = (List<String>) application.getAttribute("onlineUserList");
		// 第一次使用前，需要初始化 
		if (onlineUserList == null) {
			onlineUserList = new ArrayList<String>();
			application.setAttribute("onlineUserList", onlineUserList);
		}
		int i = 0;
		for (i = 0; i < onlineUserList.size(); i++) {
			if (onlineUserList.get(i).equals(this.userId))
				break;
		}
		if (i == onlineUserList.size())
			onlineUserList.add(this.userId);

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionBindingListener#valueUnbound(javax.servlet.http.HttpSessionBindingEvent)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();

		List<String> onlineUserList = (List<String>) application.getAttribute("onlineUserList");
		onlineUserList.remove(this.userId);
	}

}
