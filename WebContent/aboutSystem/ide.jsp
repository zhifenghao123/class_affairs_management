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
     <p>数据库：一般的业务数据存放于Mysql数据库中，博客和论坛信息存放于MongoDB数据库中。</p>
     <p>集成开发环境：Eclipse Java EE IDE for Web Developers. Version: Kepler Service Release 2</p>
     <p>程序开发语言：Java(jdk 1.7)</p>
     <p>Web 应用服务器:Tomcat 7.0</p>
     <p></p>
     <p></p>
    </div>
 
</body>
</html>
