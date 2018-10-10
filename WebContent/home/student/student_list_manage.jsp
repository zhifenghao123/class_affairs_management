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

<script src="/js/home/student/student_list_manage.js"></script>
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
                                <div class="text-muted bootstrap-admin-box-title">查询</div>
                            </div>
                            <div class="bootstrap-admin-no-table-panel-content bootstrap-admin-panel-content collapse in">
                               <!--  <form class="form-horizontal" role="form"> -->
                                <div >
                                	<div  style="float:left;">
	                                	<span>学号:</span>
							            <input id="studentNo" type="text" size="10" onblur="searchStudent(1)"/>
						            </div>
						            <div  style="float:left;">
							            <span>姓名:</span>
							            <input id="studentName" type="text" size="6" onblur="searchStudent(1)"/>
						            </div>
						            <div  style="float:left;">
							            <span>性别:</span>
							            <select class="select1 black" id="sex" style="width:60px;" onchange="searchStudent(1)">
							             	<option value =''>---</option>
							             	<option value="1" >男</option>
											<option value="2">女</option>
							            </select>
						            </div>
						            <div  style="float:left;">
							            <span>政治面貌:</span>
							            <select class="select1 black" id="politicalLandscape" style="width:150px;" onchange="searchStudent(1)">
							            	<option value =''>— — —</option>
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
						            <div  style="float:left;">
							            <span>培养类别:</span>
							            <select class="select1 black" id="cultivationType" style="width:80px;" onchange="searchStudent(1)"> 
							             	<option value =''>---</option>
							             	<option value="1">学硕</option>
											<option value="2">专硕</option>
							            </select>
						             </div>
						            <div  style="float:left;">
							            <span>所在专业:</span>
							            <select class="select1 black" id="majorNo" style="width:150px;" onchange="searchStudent(1)"> 
							             	<option value =''>— — —</option>
							            </select>
						             </div>
						             <div  style="float:left;">
							            <span>系（所）:</span>
							            <select class="select1 black" id="departmentNo" style="width:150px;" onchange="searchStudent(1)"> 
							             	<option value =''>— — —</option>
							            </select>
						             </div>
						              <div  style="float:left;">
							            <span>公寓:</span>
							            <select class="select1 black" id="apartmentNo" style="width:150px;" onchange="searchStudent(1)"> 
							             	<option value =''>— — —</option>
							            </select>
						             </div>
						             <div  style="float:left;">
							            <span>出生地:</span>
							            <input id="birthplace" type="text" size="16" onblur="searchStudent(1)"/>
                                   </div>
                                   <div  style="float:left;">
										<button type="submit" id="btn_query" onclick="searchStudent(1)">查询</button>
                                    </div>
								</div>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-12" >
                        <table id="data_list" class="table table-hover table-bordered" cellspacing="0" width="100%"  style="table-layout:fixed;margin-bottom:5;font-size:13px;">
                            <thead>
                            <tr>
                                <th width="11%" >学号</th>
                                <th width="9%">姓名</th>
                                <th width="6%">性别</th>
                                <th width="26%">专业</th>
                                <th width="12%">手机号</th>
                                <th width="12%">QQ</th>
                                <th width="12%">微信</th>
                                <th width="12%">微博</th>
                            </tr>
                            </thead>
                            
                            
                            <!---在此插入信息-->
                            <c:forEach var="student" items="${studentInfo.studentList }" varStatus="i">
                             <tbody>
	                         	   <td>${student.studentNo}</td>
	                                <td>${student.name}</td>
	                                <td>${student.sex}</td>
	                                <td>${student.majorName}</td>
	                                <td>${student.telephone}</td>
	                                <td>${student.qq}</td>
	                                <td>${student.wechat}</td>
	                                <td>${student.sinaweibo}</td>                                     
                          	  </tbody>
                            </c:forEach>
                        </table>
                        
                        <c:choose>
							<c:when test="${studentInfo.totalPage <= 5}">
								<c:set var="begin" value="1"/>
								<c:set var="end" value="${studentInfo.totalPage }"/>
							</c:when>
							<c:otherwise>
								<c:set var="begin" value="${studentInfo.currentPage-2 }"/>
								<c:set var="end" value="${studentInfo.currentPage+2 }"/>
								<c:if test="${begin<1 }">
									<c:set var="begin" value="1"/>
									<c:set var="end" value="5"/>
								</c:if>
								<c:if test="${end>studentInfo.totalPage }">
									<c:set var="begin" value="${studentInfo.totalPage-4 }"/>
									<c:set var="end" value="${studentInfo.totalPage }"/>
								</c:if>
							</c:otherwise>
						</c:choose>
                    
                        
                        <div class="pull-right" id="paginationFoot">
                           <ul class="pagination" style="margin-top:0;margin-bottom:0" id="paginationUl">
                           <li>符合条件的学生记录共有${studentInfo.totalCount}条</li>
                           <li class="disabled"><a href="#">第${studentInfo.currentPage}页/共${studentInfo.totalPage}页</a></li>
                           <li><a href="${pageContext.request.contextPath}/student/studentManageList.action?page=1">首页</a></li>
                           <li><a href="${pageContext.request.contextPath}/student/studentManageList.action?page=${studentInfo.currentPage-1 }">&laquo;</a></li><!-- 上一页 -->
                         
								<c:forEach begin="${begin }" end="${end }" var="i">
								  <c:choose>
							
								  	<c:when test="${i eq studentInfo.currentPage }">							  	
								  			<li class="active"><a>${i }</a><li>							 
								  	</c:when>
								  	<c:otherwise>
								  		<li><a href="${pageContext.request.contextPath}/student/studentManageList.action?page=${i}">${i}</a></li>
								  	</c:otherwise>
								  </c:choose>
								</c:forEach>
				        	
							  <c:if test="${studentInfo.currentPage < studentInfo.totalPage }">
								  <li><a href="${pageContext.request.contextPath}/student/studentManageList.action?page=${studentInfo.currentPage+1}">&raquo;</a></li>
							</c:if>
							
							<li><a href="${pageContext.request.contextPath}/student/studentManageList.action?page=${studentInfo.totalPage}">尾页</a></li>
							<li>
								<a href="javascript:exportSearchedStudentList();">导出Excel</a>
							</li>
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
