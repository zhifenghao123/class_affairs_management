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
            <div class="col-md-10"  style="height:540px;">
                <div style="padding: 10px 0px 10px;" >
					<form class="bs-example bs-example-form" role="form">
						<div class="input-group input-group-sm input-group-div">
							<span class="input-group-addon" >姓名</span>
							<div class="col-sm-4" style="padding-left:0px;padding-right:100px">
						      	<input type="text" id="name" class="form-control" disabled="disabled">
							</div>
							<button id="editStudentInfoBtn5" type="button" class="btn btn-default" onclick="updateInfo()">修改</button> 
						</div>
						<div class="input-group col-sm-4 input-group-div">
							<span class="input-group-addon">学号</span>
							<input class="form-control" type="text" id="studentNo" value="<%=studentNo %>" disabled="disabled">
							<input type="hidden" id="info_type" value="5">
						</div>
						<div class="input-group col-sm-9 input-group-div">
							<span class="input-group-addon">联系电话</span>
							<input class="form-control" type="text" id="telephoneNoFrequse" disabled="disabled">
							<span class="input-group-addon">公开权限</span>
							<select id="telephoneNoFrequseIsPublic"  class="form-control" name="telephoneNoFrequseIsPublic"  disabled="disabled">
								<option value="1">公开</option>
								<option value="2">保密</option>
							</select>
						</div>
						<div class="input-group col-sm-9 input-group-div">
							<span class="input-group-addon">备用联系电话</span>
							<input class="form-control" type="text" id="telephoneNoBackup" disabled="disabled">
							<span class="input-group-addon">公开权限</span>
							<select class="form-control" id="telephoneNoBackupIsPublic" name="telephoneNoBackupIsPublic"  disabled="disabled">
								<option value="1">公开</option>
								<option value="2">保密</option>
							</select>
						</div>
						<div class="input-group col-sm-9 input-group-div">
							<span class="input-group-addon">邮箱</span>
							<input class="form-control" type="text" id="emaiNo" disabled="disabled">
							<span class="input-group-addon">公开权限</span>
							<select class="form-control" id="emaiNoIspublic" name="emaiNoIspublic" disabled="disabled">
								<option value="1" selected="selected">公开</option>
								<option value="2">保密</option>
							</select>
						</div>
						<div class="input-group col-sm-9 input-group-div">
							<span class="input-group-addon">QQ</span>
							<input class="form-control" type="text" id="qqNo" disabled="disabled">
							<span class="input-group-addon">公开权限</span>
							<select class="form-control" id="qqNoIsPublic" name="qqNoIsPublic"  disabled="disabled">
								<option value="1">公开</option>
								<option value="2">保密</option>
							</select>
						</div>
						<div class="input-group col-sm-9 input-group-div">
							<span class="input-group-addon">微信</span>
							<input class="form-control" type="text" id="wechatNo" disabled="disabled">
							<span class="input-group-addon">公开权限</span>
							<select class="form-control" id="wechatNoIsPublic" name="wechatNoIsPublic"  disabled="disabled">
								<option value="1">公开</option>
								<option value="2">保密</option>
							</select>
						</div>
						<div class="input-group col-sm-9 input-group-div">
							<span class="input-group-addon">新浪微博</span>
							<input class="form-control" type="text" id="sinaweiboNo" disabled="disabled">
							<span class="input-group-addon">公开权限</span>
							<select class="form-control" id="sinaweiboNoIsPublic" name="sinaweiboNoIsPublic"  disabled="disabled">
								<option value="1">公开</option>
								<option value="2">保密</option>
							</select>
						</div>
						<div class="input-group input-group-div">
							<span style="color:#F00">&nbsp;&nbsp;&nbsp;&nbsp;亲，如果您将相应的社交账号公开权限设置为：公开，那么其他的小朋友就会看到您的账号，否则只有你自己本人能看得到自己的账号信息。</span>
						</div>
						<button id="saveUpdatedStudentInfoBtn5" type="button" class="btn btn-default" style="display:none" onclick="saveUpdateInfo()">保存</button>
				</form>
			</div>
          </div>
        </div>
    </div>
 
</body>
</html>
