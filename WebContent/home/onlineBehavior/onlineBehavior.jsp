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
<title>学生空间-在线行为记录</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/common/bootstrap-3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="/common/bootstrap-3.3.7/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="/common/bootstrap-3.3.7/css/bootstrap-admin-theme.css">

<script src="/common/jquery/jquery.min.js"></script>
<script src="/common/bootstrap-3.3.7/js/bootstrap.min.js"></script>

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
            <div class="col-md-10"  style="height:600px;">
            
             <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default bootstrap-admin-no-table-panel" style="margin-bottom:0">
                            <div class="panel-heading">
                                <div class="text-muted bootstrap-admin-box-title">
								亲，以下为您的系统操作记录，如果某些记录不是本人操作产生，极有可能您的密码已经不安全，被他人窃取，建议尽快修改密码，或者联系管理员处理。
								</div>
                            </div>
                        </div>
                    </div>
                </div>
                
                
                <div class="row">
                    <div class="col-lg-12">
                        <table id="data_list" class="table table-hover table-bordered" cellspacing="0" width="100%"  style="table-layout:fixed;margin-bottom:5;">
                            <thead>
                            <tr>
                                <th width="30%">操作名称</th>
                                <th width="20%">操作客户端Ip</th>
                                <th width="20%">操作时间</th>
                            </tr>
                            </thead>
                            
                            
                            <!---在此插入信息-->
                            <c:forEach var="onlineBehavior" items="${onlineBehaviorInfo.onlineBehaviorList }" varStatus="i">
                             <tbody>
	                         	   <td>${onlineBehavior.onlineBehaviorName}</td>
	                                <td>${onlineBehavior.userIp}</td>
	                                <td>${onlineBehavior.recordTime}</td>                                     
                          	  </tbody>
                            </c:forEach>
                        </table>
                        
                        <c:choose>
							<c:when test="${onlineBehaviorInfo.totalPage <= 5}">
								<c:set var="begin" value="1"/>
								<c:set var="end" value="${onlineBehaviorInfo.totalPage }"/>
							</c:when>
							<c:otherwise>
								<c:set var="begin" value="${onlineBehaviorInfo.currentPage-2 }"/>
								<c:set var="end" value="${onlineBehaviorInfo.currentPage+2 }"/>
								<c:if test="${begin<1 }">
									<c:set var="begin" value="1"/>
									<c:set var="end" value="5"/>
								</c:if>
								<c:if test="${end>onlineBehaviorInfo.totalPage }">
									<c:set var="begin" value="${onlineBehaviorInfo.totalPage-4 }"/>
									<c:set var="end" value="${onlineBehaviorInfo.totalPage }"/>
								</c:if>
							</c:otherwise>
						</c:choose>
                    
                        
                        <div class="pull-right" id="paginationFoot">
                           <ul class="pagination" style="margin-top:0;margin-bottom:0" id="paginationUl">
                           <li class="disabled"><a href="#">第${onlineBehaviorInfo.currentPage}页/共${onlineBehaviorInfo.totalPage}页</a></li>
                           <li><a href="${pageContext.request.contextPath}/student/onlineBehaviorList.action?page=1">首页</a></li>
                           <li><a href="${pageContext.request.contextPath}/student/onlineBehaviorList.action?page=${onlineBehaviorInfo.currentPage-1 }">&laquo;</a></li><!-- 上一页 -->
                         
								<c:forEach begin="${begin }" end="${end }" var="i">
								  <c:choose>
							
								  	<c:when test="${i eq onlineBehaviorInfo.currentPage }">							  	
								  			<li class="active"><a>${i }</a><li>							 
								  	</c:when>
								  	<c:otherwise>
								  		<li><a href="${pageContext.request.contextPath}/student/onlineBehaviorList.action?page=${i}">${i}</a></li>
								  	</c:otherwise>
								  </c:choose>
								</c:forEach>
				        	
							  <c:if test="${onlineBehaviorInfo.currentPage < onlineBehaviorInfo.totalPage }">
								  <li><a href="${pageContext.request.contextPath}/student/onlineBehaviorList.action?page=${onlineBehaviorInfo.currentPage+1}">&raquo;</a></li>
							</c:if>
							
							<li><a href="${pageContext.request.contextPath}/student/onlineBehaviorList.action?page=${onlineBehaviorInfo.totalPage}">尾页</a></li>
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
