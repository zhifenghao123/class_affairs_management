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

    <div>
        	<p>系统名称：研究生班级事务</p>
        	<p>系统开发及搭建人：郝志锋【西安理工大学计算机科学院工程学院2016级研究生，研1628班、研1629班班长】</p>
        	<p>系统开发时间：2017年10月-2018年1月【在学术论文科研之余挤时间完成】</p>
        	<p>系统开发动机：系统设计及开发人郝志锋彼时担任西安理工大学2016级计算机学院研究生班长，不同于其他学院，其所处于的计算机学院研究生有两个班级：研1628班、研1629班，
        	但是按照计算机学院历年的惯例，都是由一个班长管理两个研究生班级，2016级计算机学院两个研究生班级总共有82人，为了准确、高效地管理班级的事务活动，为学院的学生统计工作提供
        	准确的数据，特此在闲暇时间设计并开发了本系统。&nbsp;&nbsp;&nbsp;&nbsp;本系统的设计之初，就是一个处在班长角色的计算机专业学生为解决自身的班级业务而设计开发的
        	系统，所以具有相当的可用性。
        	</p>
    </div>
 
</body>
</html>
