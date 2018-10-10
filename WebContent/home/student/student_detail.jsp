<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String studentNo = (String)request.getSession().getAttribute("studentNo");
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

<script>
$(function(){
	toDetailInfo(1);
});
function toDetailInfo(info){
	var detailInfoUrl = "";
	switch (info){
	case 1:
		detailInfoUrl = "/home/student/student_baseinfo.jsp?studentNo="+<%=studentNo%>;
		break;
	case 2:
		detailInfoUrl = "/home/student/student_studyinfo.jsp?studentNo="+<%=studentNo%>;
		break;
	case 3:
		detailInfoUrl = "/home/student/student_scoreinfo.jsp?studentNo="+<%=studentNo%>;
		break;
	case 4:
		detailInfoUrl = "/home/student/student_lifeinfo.jsp?studentNo="+<%=studentNo%>;
		break;
	case 5:
		detailInfoUrl = "/home/student/student_socialcontactinfo.jsp?studentNo="+<%=studentNo%>;
		break;
	}
	
	$.ajax({
		  url: detailInfoUrl,
		  data:{
			  
		  },
		  cache: false,
		  success: function(data){
			  $("#detailinfo").empty();
		    $("#detailinfo").append(data);
		  },
		  dataType:"html"
	});
}
</script>
</head>

<body class="bootstrap-admin-with-small-navbar">
	<!-- 头部开始 -->
    <%@ include file="/home/headAndFoot/head.jsp" %>
    <!-- 头部结束-->

    <div class="container">
        <!-- left, vertical navbar & content -->
        <div class="row">
            <!-- left, vertical navbar -->
            <!-- 中间左侧菜单导航开始   left, vertical navbar -->
			<jsp:include page="/home/student/student_left.jsp"></jsp:include>
			 <!-- 中间左侧菜单导航结束 -->

  		<!-- content -->
            <div class="col-md-10">
                <div style="padding: 10px 100px 10px;">
                
                <!-- 标准的按钮 -->
					<button type="button" class="btn btn-warning" onclick="toDetailInfo(1)">基本信息</button>
					<!-- 提供额外的视觉效果，标识一组按钮中的原始动作 -->
					<button type="button" class="btn btn-primary" onclick="toDetailInfo(2)">学习信息</button>
					
					<button type="button" class="btn btn-danger" onclick="toDetailInfo(3)">成绩信息</button>
					<!-- 表示一个成功的或积极的动作 -->
					<button type="button" class="btn btn-success" onclick="toDetailInfo(4)">生活信息</button>
					<!-- 信息警告消息的上下文按钮 -->
					<button type="button" class="btn btn-info" onclick="toDetailInfo(5)">社交信息</button>

				<div id="detailinfo">
				</div>
				
			</div>
          </div>
        </div>
    </div>
  <%@ include file="/home/headAndFoot/foot.jsp"%>
</body>
</html>
