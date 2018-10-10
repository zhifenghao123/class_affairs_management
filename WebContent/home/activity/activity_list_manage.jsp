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
<title>学生空间-活动</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/common/bootstrap-3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="/common/bootstrap-3.3.7/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="/common/bootstrap-3.3.7/css/bootstrap-admin-theme.css">
<script src="/common/jquery/jquery.min.js"></script>
<script src="/common/bootstrap-3.3.7/js/bootstrap.min.js"></script>

<script src="/js/home/activity/activity_list_manage.js"></script>
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
            <div class="col-md-10" style="height:600px;">

                 <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default bootstrap-admin-no-table-panel" style="margin-bottom:0">
                            <div class="panel-heading">
                                <div class="text-muted bootstrap-admin-box-title">查询</div>
                            </div>
                            <div class="bootstrap-admin-no-table-panel-content bootstrap-admin-panel-content collapse in">
                               <!--  <form class="form-horizontal"> -->
                                    <div class="col-lg-5 form-group">
                                        <label class="col-lg-4 control-label" for="query_bname">标题关键字</label>
                                        <div class="col-lg-8">
                                            <input class="form-control" id="activityTitle" name="activityTitle" type="text" value="">
                                            <label class="control-label" for="query_bname" style="display: none;"></label>
                                        </div>
                                    </div>
                                  
                                    <div class="col-lg-2 form-group">
                                        <button type="submit" class="btn btn-primary" id="btn_query" onclick="searchActivity(1)">查询</button>
                                    </div>         
                                    <div class="col-lg-2 form-group">
                                        <button type="submit" class="btn btn-primary" id="btn_query" onclick="publishActivity()">发布</button>
                                    </div>  
                            </div>
                        </div>
                    </div>
                </div>
                
                
                <div class="row">
                    <div class="col-lg-12">
                        <table id="data_list" class="table table-hover table-bordered" cellspacing="0" width="100%" style="table-layout:fixed;margin-bottom:5;">
                        <tr>
                             <td width="40%">活动名称</td>
	                         <td width="15%">活动负责人</td>
	                         <td width="20%">发布时间</td>
	                          <td width="25%">操作</td>
	                      </tr>
                            <!---在此插入信息-->
                            <c:forEach var="activity" items="${activityInfo.activityList }" varStatus="i">
                             <tbody>
	                         	   <td>${activity.name}</td>
	                                <td>${activity.responsorName}</td>
	                                <td>${activity.publishTime}</td>
	            					<td>
	            						<button type="button" class="btn btn-info btn-xs" data-toggle="modal" data-target="#findModal" onclick="detail('${activity.activityId}')" >查看</button>
	                                	<button type="button" class="btn btn-warning btn-xs" data-toggle="modal" data-target="#updateModal" id="btn_update" onclick="update('${activity.activityId}')">修改</button>
	                                	<button type="button" class="btn btn-danger btn-xs" onclick="deleteActivity('${activity.activityId}')">删除</button>
	                                	<button type="button" class="btn btn-success btn-xs" onclick="appointParticipateIn('${activity.activityId}')"  data-toggle="modal" data-target="#addNumModal">批量指派</button>
	                               	</td> 
	                                
	                               <%--  <td><c:property value="#activity.sex"/></td>
	                                 <td><c:property value="#activity.sex"/></td>
	                                  <td><c:date name="#activity.putdate" format="yyyy-MM-dd" /></td>
	                                    <td><c:property value="#activity.num"/></td>
	                                    <td><c:property value="#activity.currentNum"/></td>
	                                    <td><c:property value="#activity.price"/></td>
	                                <td>
	                                <button type="button" class="btn btn-info btn-xs" data-toggle="modal" data-target="#findModal" onclick="getBookInfo(<c:property value="#activity.bookId"/>)" >查看</button>
	                                	<button type="button" class="btn btn-warning btn-xs" data-toggle="modal" data-target="#updateModal" id="btn_update" onclick="updateBook(<c:property value="#activity.bookId"/>)">修改</button>
	                                	<button type="button" class="btn btn-danger btn-xs" onclick="deleteBook(<c:property value="#activity.bookId"/>)">删除</button>
	                                	<button type="button" class="btn btn-success btn-xs" onclick="addBookNum(<c:property value="#activity.bookId"/>,<c:property value="#activity.ISBN"/>)"  data-toggle="modal" data-target="#addNumModal">新增</button>
	                               	</td>       --%>                                        
                          	  </tbody>
                            </c:forEach>
                        </table>
                        
                        <c:choose>
							<c:when test="${activityInfo.totalPage <= 5}">
								<c:set var="begin" value="1"/>
								<c:set var="end" value="${activityInfo.totalPage }"/>
							</c:when>
							<c:otherwise>
								<c:set var="begin" value="${activityInfo.currentPage-2 }"/>
								<c:set var="end" value="${activityInfo.currentPage+2 }"/>
								<c:if test="${begin<1 }">
									<c:set var="begin" value="1"/>
									<c:set var="end" value="5"/>
								</c:if>
								<c:if test="${end>activityInfo.totalPage }">
									<c:set var="begin" value="${activityInfo.totalPage-4 }"/>
									<c:set var="end" value="${activityInfo.totalPage }"/>
								</c:if>
							</c:otherwise>
						</c:choose>
                    
                        
                        <div class="pull-right">
                           <ul class="pagination" style="margin-top:0;margin-bottom:0" id="paginationUl">
                           <li class="disabled"><a href="#">第${activityInfo.currentPage}页/共${activityInfo.totalPage}页</a></li>
                           <li><a href="${pageContext.request.contextPath}/activity/activityList.action?page=1">首页</a></li>
                           <li><a href="${pageContext.request.contextPath}/activity/activityList.action?page=${activityInfo.currentPage-1 }">&laquo;</a></li><!-- 上一页 -->
                         
								<c:forEach begin="${begin }" end="${end }" var="i">
								  <c:choose>
							
								  	<c:when test="${i eq activityInfo.currentPage }">							  	
								  			<li class="active"><a>${i }</a><li>							 
								  	</c:when>
								  	<c:otherwise>
								  		<li><a href="${pageContext.request.contextPath}/activity/activityList.action?page=${i}">${i}</a></li>
								  	</c:otherwise>
								  </c:choose>
								</c:forEach>
				        	
							  <c:if test="${activityInfo.currentPage < activityInfo.totalPage }">
								  <li><a href="${pageContext.request.contextPath}/activity/activityList.action?page=${activityInfo.currentPage+1}">&raquo;</a></li>
							</c:if>
							
							<li><a href="${pageContext.request.contextPath}/activity/activityList.action?page=${activityInfo.totalPage}">尾页</a></li>
							</ul>
                           </div>
                          
                    </div>
                </div>
            </div>
        </div>
    </div>
 <%@ include file="/home/headAndFoot/foot.jsp"%>
</body>
</html>
