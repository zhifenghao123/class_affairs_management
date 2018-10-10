<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.classaffairs.common.CreatePrimaryKey" %>
<%@ page import="com.classaffairs.entity.PrimaryKey" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN" class="ax-vertical-centered">
<head>
<meta charset="UTF-8">
<title>学生空间-通知</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/common/bootstrap-3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="/common/bootstrap-3.3.7/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="/common/bootstrap-3.3.7/css/bootstrap-admin-theme.css">
<script src="/common/jquery/jquery.min.js"></script>
<script src="/common/bootstrap-3.3.7/js/bootstrap.min.js"></script>

<!-- Buttons 库的核心文件 -->
  <link rel="stylesheet" href="/common/buttons/buttons.css">
  <link rel="stylesheet" href="/common/font-awesome-4.4.0/css/font-awesome.min.css">
  
<script type="text/javascript" src="/common/ueditor/ueditor.simple.config.js"></script>
<script type="text/javascript" src="/common/ueditor/ueditor.all.js"></script>

<script type="text/javascript" src="/js/home/notice/notice_detail.js"></script>
<% String noticeId = request.getParameter("noticeId").toString(); %>
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

                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default bootstrap-admin-no-table-panel">
                            <div class="panel-heading">
                                <div class="text-muted bootstrap-admin-box-title">查询</div>
                                 <div class="text-muted bootstrap-admin-box-title">我的通知</div>
                            </div>
                            <div class="bootstrap-admin-no-table-panel-content bootstrap-admin-panel-content collapse in">
                                <%-- <form class="form-horizontal" action="${pageContext.request.contextPath}/admin/bookManageAction_queryBook.action" method="post"> --%>
                               <!--  <form class="form-horizontal"> -->
                                    <div class="col-lg-12 form-group" align="center" style="border:1px">
                                         <label id="title"></label>
                                       	 <input name="noticeId" type="hidden" value="<%=noticeId %>" id="noticeId" />
                                    </div>
                                   <div class="col-lg-12 form-group"  align="center" style="border:1px">
                                      	  发布时间：<label id="publishTime"></label>&nbsp;&nbsp;&nbsp;
                                        	发布人：<label id="publisherName"></label>
                                    </div>
 
                                   <div class="col-lg-12 form-group" >
                                        <label class="col-lg-2 control-label" for="query_bname3"></label>
                                        <div class="col-lg-9" id="content">
                                        	<!-- <textarea id="content" name="content"  style="overflow-y:auto;height:300px;" onpropertychange="this.style.height=this.scrollHeight+'px';" oninput="this.style.height=this.scrollHeight+'px';"></textarea> -->
                                            <!-- <textarea id="content" name="content" style="overflow-y:hidden;width:100%;"></textarea> -->
                                       		<!--  style="overflow-x:hidden;overflow-y:scroll;height:310px;" onpropertychange="this.style.scrollTop=this.scrollHeight+'px';" oninput="this.style.scrollTop=this.scrollHeight+'px';" -->
                                        </div>
                                    </div>
                                     <div class="col-lg-12 form-group">
                                        <label class="col-lg-2 control-label" for="query_bname3">附件</label>
                                        <div class="col-lg-9">
                                            <!-- <input type="text" id="enclosureFile" name="enclosureFile" /> -->						   
										 	 <label class="col-lg-2 control-label" id="enclosureFile">附件</label>
										 </div>
                                    </div>
                                    
                                <!-- </form> -->
                            </div>
                        </div>
                    </div>
                </div>
                
                
                
            </div>
        </div>
    </div>
    
 
</body>
</html>
