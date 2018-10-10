<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN" class="ax-vertical-centered">
<head>
<meta charset="UTF-8">
<title>学生空间-主页</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/common/bootstrap-3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="/common/bootstrap-3.3.7/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="/common/bootstrap-3.3.7/css/bootstrap-admin-theme.css">
<script src="/common/jquery/jquery.min.js"></script>
<script src="/common/bootstrap-3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/common/My97DatePicker/WdatePicker.js"></script>
<script src="/js/home/student/student_info.js"></script>
<script type="text/javascript">
 	$(document).ready(function() {
	/* 	$.ajax({
			url : "/student/getStudentByStudentNo.action",
			type : "post",
			async : false,
			dataType : "json",
			data : {
				"studentNo" : $("#studentNo").val(),
				"infoType" : infoType,
			},
			success : function(studentInfo) {
				$("#name").val(studentInfo.name);
				
			}
		}); */
		$("#api").click();
	}); 
</script>
<style type="text/css">
br{
display:inline; line-height:12px;
} 
.input-group-div{
margin-bottom:5px;
}
</style>
</head>
<%
String studentNo = (String)request.getParameter("studentNo");
%>
<body class="bootstrap-admin-with-small-navbar">
	<div>
       	为了让更多的学生使用系统中的数据，后续将陆陆续续提供更多的数据开放接口API，为有兴趣的开发人员提供json或者xml格式的系统数据。
       	<br><a id="api" href="/cxf" target="_blank" >点击查看API</a>
       	
	</div>
 
</body>
</html>
