package com.classaffairs.common;

import javax.servlet.http.HttpServletRequest;

public class CommonPath {
	/**
	 * 获取Webapp根目录+subPath 的物理路径
	 * @param subPath 
	 * @return Webapp根目录+subPath 的物理路径
	 */
	public static String getWebappPath(String subPath) {
		return CommonPath.class.getResource("").getPath().replace("/WEB-INF/classes/com/classaffairs/common", subPath);
	}

	/**
	 * 获取项目基本路径
	 * @param request - HttpServletRequest
	 * @return 路径字符串（如：http://localhost:8080/qjs/）
	 */
	public static String getBasePath(HttpServletRequest request) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
		return basePath;
	}

	public static String getFtlPath() {
		return CommonPath.class.getResource("").getPath().replace("/WEB-INF/classes/com/classaffairs/common", "/template");
	}
}
