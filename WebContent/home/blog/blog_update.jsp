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
<title>学生空间-博客</title>
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

<script type="text/javascript" src="/js/home/blog/blog_update.js"></script>
<% String blogId = request.getParameter("blogId").toString(); %>
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
                                 <div class="text-muted bootstrap-admin-box-title">我的博客</div>
                            </div>
                            <div class="bootstrap-admin-no-table-panel-content bootstrap-admin-panel-content collapse in">
                                <%-- <form class="form-horizontal" action="${pageContext.request.contextPath}/admin/bookManageAction_queryBook.action" method="post"> --%>
                               <!--  <form class="form-horizontal"> -->
                                    <div class="col-lg-12 form-group">
                                        <label class="col-lg-2 control-label" for="query_bno">博文标题</label>
                                        <div class="col-lg-5">
                                            <input class="form-control" id="title" name="title" type="text" value="">
                                            <input name="blogId" type="hidden" value="<%=blogId %>" id="blogId" />
                                            <label class="control-label" for="query_bno" style="display: none;"></label>
                                        </div>
                                    </div>
                                  
                                     <!-- <div class="col-lg-7 form-group">
                                        <label class="col-lg-4 control-label" for="query_bno1">博文类型</label>
                                          <div class="col-lg-8">
		                                        <select class="form-control" id="type" name="type">
		                                              <option value="0" selected="selected">请选择</option>
										              <option value="1">Java</option>
										              <option value="2">Python</option>
										              <option value="3">C</option>
										              <option value="4">C++</option>
										              <option value="5">数据库</option>                                       
		                                        </select>
                                        
                                    	</div>
                                    </div> -->
                                    <div class="col-lg-12 form-group">
                                        <label class="col-lg-2 control-label" for="query_bno1">博文标签</label>
                                        <div class="col-lg-10 form-group" id="tagDiv">
		                                   <!-- <div style = "width:13%;float:left">
		                                      <input class="form-control" id="tags" name="tags" type="text" value="">
                                    		</div>
                                    		<div style = "width:13%;float:left">
		                                      <input class="form-control" id="tags" name="tags" type="text" value="">
                                    		</div>
                                    		<div style = "width:13%;float:left">
		                                      <input class="form-control" id="tags" name="tags" type="text" value="">
                                    		</div>   
                                    		<>	 -->
                                    		<div style = "width:13%;float:left" id="tagHasInputed">
		                                     
                                    		</div> 
                                    	</div>
                                    	<div class="col-lg-10 form-group" id="tagDiv">
                                    		<div style = "width:13%;float:left" id="tagInputDiv">
		                                      <input class="form-control" id="tagInput" name="tags" type="text" value="">
                                    		</div> 
                                          <div style = "width:13%;float:left">
		                                      <button class="button button-primary button-square button-small" onclick="addTag()"><i class="fa fa-plus"></i></button>
                                    	 </div>
                                    </div>
                           
                                    <div class="col-lg-12 form-group" style="height:370px;">
                                        <label class="col-lg-2 control-label" for="query_bname3">正文</label>
                                        <div class="col-lg-9" style="height:370px;">
                                        	<!-- <textarea id="content" name="content"  style="overflow-y:auto;height:300px;" onpropertychange="this.style.height=this.scrollHeight+'px';" oninput="this.style.height=this.scrollHeight+'px';"></textarea> -->
                                            <textarea id="content" name="content" style="height:310px;"></textarea>
                                       		<!--  style="overflow-x:hidden;overflow-y:scroll;height:310px;" onpropertychange="this.style.scrollTop=this.scrollHeight+'px';" oninput="this.style.scrollTop=this.scrollHeight+'px';" -->
                                        </div>
                                    </div>
                                     <div class="col-lg-12 form-group">
                                        <label class="col-lg-2 control-label" for="query_bname3">参考博文链接url</label>
                                        <div class="col-lg-9">
                                            <input class="form-control" id="referrenceBlogUrl" name="referrenceBlogUrl" type="text" value="">
                                            <label class="control-label" id="linkUrl" for="query_bname2" style="display: none;"></label>
                                        </div>
                                    </div>
                                    
                                    <div class="col-lg-7 form-group"><!--  class="btn btn-primary" -->
                                        <button  id="btn_query" onclick="submit()">保存</button>
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
