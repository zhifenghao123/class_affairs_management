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
      	<p>研究生班级事务系统设计了三种主要的角色：系统管理员、班长、普通的研究生同学，我们也将本系统按照不同的角色划分为三个相应的子系统：系统管理员子系统、班长子系统、普通学生子系统。</p>
        <p>系统管理员子系统：维护系统用到的基础数据，包括系统中的学院信息、专业信息、年级信息、班级信息，学生基本信息等基础数据的增加、更新修改等操作。</p>
        <p>班长子系统：首先班长拥有普通学生子系统所拥有的一切系统功能，除此之外呢，班长作为管理班级事务的主体，还拥有着发布班级通知、发布班级事务活动等的权力。</p>
        <p>普通学生子系统:查询自己班级学生的信息、查看自己班级的班级事务通知、查看并报名参加自己班级的活动、查看自己已经参加过得班级活动、查看并发布自己的学术博客，或者发布自己对班级管理的意见和看法</p>
    </div>
 
</body>
</html>
