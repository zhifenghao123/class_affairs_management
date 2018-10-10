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

    <!-- <div class="container" > -->
    <div>
     <p>后端框架：持久层选用Mybatis，业务层框架为Spring，而Web层框架为Spring MVC。</p>
     <p>前端框架：对于管理员子系统使用了EasyUI框架，而班长和普通学生所看到的系统使用了Bootstrap框架。</p>
     <p>相关技术：Excel处理、图片压缩（只做了测试，系统并未开放使用）等技术。</p>
    </div>
 
</body>
</html>
