<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"	type="text/css" href="/css/base.css">
<link rel="stylesheet" type="text/css" href="/css/font.css">
<link rel="stylesheet" type="text/css" href="/css/manage.css">
<title>学生空间-主页</title>
<script type="text/javascript" src="/js/common/jquery.min.js"></script>
</head>
<body>
	<!-- 头部开始 -->
	<%@ include file="/home/headAndFoot/head.jsp" %>
	<!-- 头部结束 -->
	<!-- 主体开始 -->
	<div class="magage_main">
		<!-- 左菜单开始 -->
		<jsp:include page="/home/student/student_left.jsp"></jsp:include>
		<!-- 左菜单结束 -->
		<!-- 中间开始 -->
		<div class="main_m">
			<!-- 个人基本信息开始 -->
			<div class="jlbinfobox">
			<!--  <h2>个人基本信息</h2> -->
			</div>
			<!-- 个人基本信息结束 -->
		</div>
		 <!-- 中间结束 -->
	</div>
	<!-- 主体结束 -->
	<!-- 尾部开始 -->
	<%@ include file="/home/headAndFoot/foot.jsp" %>
	<!-- 尾部结束 -->
</body>
</html>