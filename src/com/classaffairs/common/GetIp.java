/**
 * 
 */
package com.classaffairs.common;

import javax.servlet.http.HttpServletRequest;

import com.classaffairs.framework.core.utils.Log;

/**
 * 由于request.getRemoteAddr()获取的Ip在分布式环境下出错，
 *  此处的方法可以在分布式环境下准确获取Ip
 * @author Haozhifeng
 *
 */
public class GetIp {
	public static String getRealIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		} else {
			Log.log.info("x-forwarded-for:" + ip);
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		} else {
			Log.log.info("Proxy-Client-IP:" + ip);
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		} else {
			Log.log.info("WL-Proxy-Client-IP:" + ip);
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("http_client_ip");
		} else {
			Log.log.info("request.getRemoteAddr():" + ip);
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		} else {
			Log.log.info("http_client_ip:" + ip);
		}

		// 如果是多级代理，那么取第一个ip为客户ip
		if (ip != null && ip.indexOf(",") != -1) {
			ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
		} else {
			Log.log.info("HTTP_X_FORWARDED_FOR:" + ip);
		}

		return ip;
	}
}
