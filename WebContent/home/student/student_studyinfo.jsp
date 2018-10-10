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
<script src="/js/home/student/student_info.js"></script>
<style type="text/css">
br{
display:inline; line-height:3px;
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
        <!-- left, vertical navbar & content -->
        <div class="row">
  		<!-- content -->
            <div class="col-md-10" style="height:540px;">
                <div style="padding: 10px 0px 10px;">
					<form class="bs-example bs-example-form" role="form">
						<div class="input-group input-group-sm input-group-div">
							<span class="input-group-addon" >姓名</span>
							<div class="col-sm-4" style="padding-left:0px;padding-right:100px">
						      	<input type="text" id="name" class="form-control" disabled="disabled">
							</div>
							<button id="editStudentInfoBtn2" type="button" class="btn btn-default" onclick="updateInfo()">修改</button> 
						</div>
						<div class="input-group col-sm-4 input-group-div">
							<span class="input-group-addon">学号</span>
							<input type="text" class="form-control" id="studentNo" value="<%=studentNo %>" disabled="disabled">
							<input type="hidden" id="info_type" value="2">
						</div>
						<div class="input-group col-sm-5 input-group-div">
							<span class="input-group-addon">学院</span>
							<!-- <input type="text" id="school" disabled="disabled"> -->
							<select class="form-control" id="school" name="school" disabled="disabled">
							</select>
						</div>
						<div class="input-group col-sm-4 input-group-div">
							<span class="input-group-addon">年级</span>
							<!-- <input type="text" id="gradeNo" disabled="disabled"> -->
							<select class="form-control" id="gradeNo" name="gradeNo"  disabled="disabled">
							</select>
						</div>
						<div class="input-group col-sm-4 input-group-div">
							<span class="input-group-addon">行政班级</span>
							<!-- <input type="text" id="executiveClassName" disabled="disabled"> -->
							<select class="form-control" id="executiveClassName" name="executiveClassName"  disabled="disabled">
							</select>
						</div>
						<div class="input-group col-sm-5 input-group-div">
							<span class="input-group-addon">专业</span>
							<!-- <input type="text" id="majorName" disabled="disabled"> -->
							<select class="form-control" id="majorNo" name="majorNo"  disabled="disabled">
							</select>
						</div>
						<div class="input-group col-sm-5 input-group-div">
							<span class="input-group-addon">所（系）</span>
							<!-- <input type="text" id="departmentNo" disabled="disabled"> -->
							<select class="form-control" id="departmentNo" name="departmentNo"  disabled="disabled">
							</select>
						</div>
						<div class="input-group col-sm-4 input-group-div">
							<span class="input-group-addon">教研室</span>
							<!-- <input type="text" id="laboratoryNo" disabled="disabled"> -->
							<select class="form-control" id="laboratoryNo" name="laboratoryNo"  disabled="disabled">
							</select>
						</div>
						<div class="input-group col-sm-4 input-group-div">
							<span class="input-group-addon">导师姓名</span>
							<input class="form-control" type="text" id="tutorName" disabled="disabled">
						</div>
						<button id="saveUpdatedStudentInfoBtn2" type="button" class="btn btn-default" style="display:none" onclick="saveUpdateInfo()">保存</button>
				</form>
			</div>
          </div>
        </div>
    </div>
 
</body>
</html>
