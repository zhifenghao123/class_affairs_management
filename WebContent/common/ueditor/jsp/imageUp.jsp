    <%@page import="com.classaffairs.common.FilePath"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
             pageEncoding="UTF-8"%>
        <%@ page import="java.util.Properties" %>
        <%@ page import="java.util.List" %>
        <%@ page import="java.util.Iterator" %>
        <%@ page import="java.util.Arrays" %>
        <%@ page import="java.io.FileInputStream" %>
        <%@ page import="com.classaffairs.common.Uploader" %>

<%
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
	
	
	//获取存储目录结构
	if ( request.getParameter( "fetch" ) != null ) {
	
	    response.setHeader( "Content-Type", "text/javascript" );
	
	    response.getWriter().print( "updateSavePath( ['upload']  );" );
	    return;
	
	}
	Uploader up = new Uploader(request);
	
	//获取前端提交的path路径
	String type = request.getParameter( "type" );
	String objectId = request.getParameter("objectId");
	String dir = FilePath.getPath(type, objectId);
	//普通请求中拿不到参数，默认为"content"即大文本
	if ( dir == null || "".equals( dir ) ) {
		dir = "/upload/content";
	}
	
	up.setSavePath(dir);
	String[] fileType = {".gif" , ".png" , ".jpg" , ".jpeg" , ".bmp"};
	up.setAllowFiles(fileType);
	up.setMaxSize(1024); //单位KB
	up.upload();
	response.getWriter().print("{'original':'"+up.getOriginalName()+"','url':'"+request.getScheme()+"://"+request.getServerName()+"/"+up.getUrl()+"','title':'"+up.getTitle()+"','state':'"+up.getState()+"'}");
%>
