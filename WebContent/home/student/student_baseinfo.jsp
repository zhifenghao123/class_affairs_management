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
        <!-- left, vertical navbar & content -->
        <div  class="row">
  		<!-- content -->
            <div class="col-md-10"  style="height:540px;">
                <div style="padding: 10px 0px 10px;">
					<form class="bs-example bs-example-form" role="form">
						<div class="input-group input-group-sm input-group-div">
							<span class="input-group-addon" >姓名</span>
							<div class="col-sm-4" style="padding-left:0px;padding-right:100px">
						      	<input type="text" id="name" class="form-control" disabled="disabled">
							</div>
							<button id="editStudentInfoBtn1" type="button" class="btn btn-default" style="display:block" onclick="updateInfo()">修改</button> 
						</div>
						<div class="input-group col-sm-4 input-group-div">
							<span class="input-group-addon">学号</span>
							<input type="text" id="studentNo" class="form-control" value="<%=studentNo %>" disabled="disabled">
							<input type="hidden" id="info_type" value="1">
						</div>
						<div class="input-group col-sm-4 input-group-div">
							<span class="input-group-addon">性别</span>
							<select id="sex" name="sex"  class="form-control" disabled="disabled">
		                    	<option value="1" >男</option>
								<option value="2">女</option>                                    
		                     </select>
						</div>
						<div class="input-group col-sm-4 input-group-div">
							<span class="input-group-addon">出生日期</span>
							<input type="text" class="Wdate form-control" id="birthday" class="form-control" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',});" disabled="disabled">
						</div>
						<div class="input-group col-sm-4 input-group-div">
							<span class="input-group-addon">籍贯</span>
							<input type="text"  class="form-control" id="birthplace" disabled="disabled">
						</div>
						<div class="input-group col-sm-7 input-group-div">
							<span class="input-group-addon">家庭住址</span>
							<input type="text" class="form-control" id="homeAddress" disabled="disabled">
						</div>
						<div class="input-group col-sm-4 input-group-div">
							<span class="input-group-addon">民族</span>
							<input type="text" class="form-control" id="ethnic" disabled="disabled">
						</div>
						<div class="input-group col-sm-5 input-group-div">
							<span class="input-group-addon">政治面貌</span>
							<!-- <input type="text" id="politicalLandscape" disabled="disabled"> -->
							<select id="politicalLandscape" class="form-control" name="politicalLandscape" disabled="disabled">
		                    	<option value="1">中国共产党党员</option >
								<option value="2">中国共产党预备党员</option>
								<option value="3">中国共产主义青年团团员</option>
								<option value="4">中国国民党革命委员会会员</option>
								<option value="5">中国民主同盟盟员</option>
								<option value="6">中国民主建国会会员</option>
								<option value="7">中国民主促进会会员</option>
								<option value="8">中国农工民主党党员</option>
								<option value="9">中国致公党党员</option>
								<option value="10">九三学社社员</option>
								<option value="11">台湾民主自治同盟盟员</option>
								<option value="12">无党派民主人士</option>
								<option value="13">群众</option>                                  
		                     </select>
						</div>
						<div class="input-group col-sm-5 input-group-div">
							<span class="input-group-addon">身份证号</span>
							<input type="text" class="form-control" id="IdCardNo" disabled="disabled">
						</div>
						<div class="input-group col-sm-5 input-group-div">
							<span class="input-group-addon">本科毕业学校</span>
							<input type="text" class="form-control" id="undergraduateCollege" disabled="disabled">
						</div>
						<div class="input-group col-sm-4 input-group-div">
							<span class="input-group-addon">本科毕业日期</span>
							<input type="text" class="Wdate form-control" id="undergraduateEnddate" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',});" disabled="disabled">
						</div>
						<div class="input-group col-sm-4 input-group-div">
							<span class="input-group-addon">入学方式</span>
							<!-- <input type="text" id="cultivationType" disabled="disabled"> -->
							<select id="accessType" class="form-control" name="accessType"  disabled="disabled">
								<option value="1">推免</option>
								<option value="2">推免“2+2”</option>
								<option value="3">全国统考</option>
							</select>
						</div>
						<div class="input-group col-sm-5 input-group-div">
							<span class="input-group-addon">培养类别</span>
							<!-- <input type="text" id="cultivationType" disabled="disabled"> -->
							<select id="cultivationType" class="form-control" name="cultivationType"  disabled="disabled">
								<option value="1">学术型硕士研究生</option>
								<option value="2">专业型硕士研究生</option>
							</select>
							</div>
						<button id="saveUpdatedStudentInfoBtn1" type="button" class="btn btn-default" style="display:none" onclick="saveUpdateInfo()">保存</button>
				</form>
			</div>
          </div>
        </div>
    </div>
 
</body>
</html>
