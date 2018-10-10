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
 <script src="/common/ajaxfileupload/ajaxfileupload.js" type="text/javascript"></script>
<script src="/js/home/student/student_score_list.js"></script>
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
<%-- 
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default bootstrap-admin-no-table-panel">
                            <div class="panel-heading">
                                <div class="text-muted bootstrap-admin-box-title">查询</div>
                            </div>
                            <div class="bootstrap-admin-no-table-panel-content bootstrap-admin-panel-content collapse in">
                                <form class="form-horizontal" action="${pageContext.request.contextPath}/admin/bookManageAction_queryBook.action" method="post">
                                    <div class="col-lg-5 form-group">
                                        <label class="col-lg-4 control-label" for="query_bno">图书ISBN号</label>
                                        <div class="col-lg-8">
                                            <input class="form-control" id="bookId" name="ISBN" type="text" value="">
                                            <label class="control-label" for="query_bno" style="display: none;"></label>
                                        </div>
                                    </div>
                                  
                                     <div class="col-lg-5 form-group">
                                        <label class="col-lg-4 control-label" for="query_bno1">图书分类</label>
                                          <div class="col-lg-8">
                                        <select class="form-control" id="bookTypeId" name="bookTypeId">
                                            <option value="-1">请选择</option>                                         
                                        </select>
                                        
                                    </div>
                                    </div>
                                       
                                    <div class="col-lg-5 form-group">
                                        <label class="col-lg-4 control-label" for="query_bname">图书名称</label>
                                        <div class="col-lg-8">
                                            <input class="form-control" id="bookName" name="bookName" type="text" value="">
                                            <label class="control-label" for="query_bname" style="display: none;"></label>
                                        </div>
                                    </div>
                                    
                                  <div class="col-lg-5 form-group">
                                        <label class="col-lg-4 control-label" for="query_bname2">作者名称</label>
                                        <div class="col-lg-8">
                                            <input class="form-control" id="autho" name="autho" type="text" value="">
                                            <label class="control-label" for="query_bname2" style="display: none;"></label>
                                        </div>
                                    </div>
                                    
                                     <div class="col-lg-5 form-group">
                                        <label class="col-lg-4 control-label" for="query_bname3">出版社名称</label>
                                        <div class="col-lg-8">
                                            <input class="form-control" id="press" name="press" type="text" value="">
                                            <label class="control-label" for="query_bname2" style="display: none;"></label>
                                        </div>
                                    </div>
                                    
                                  
                                    
                                  
                                    <div class="col-lg-2 form-group">

                                        <button type="submit" class="btn btn-primary" id="btn_query" onclick="query()">查询</button>
                                    </div>
                                    <div class="col-lg-2 form-group">

                                        <button type="button" class="btn btn-primary" id="btn_add" data-toggle="modal" data-target="#addModal">添加图书</button>
                                    </div>
                                    
                                      <div class="col-lg-2 form-group">
											 <button type="button" class="btn btn-primary"   data-toggle="modal" data-target="#batchAddModal">批量添加</button>
										 </div>
										 
										  <div class="col-lg-2 form-group">
											 <button type="button" class="btn btn-primary" onclick="exportBook()">导出</button>
										 </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                 --%>
                
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default bootstrap-admin-no-table-panel" style="margin-bottom:0">
                            <div class="panel-heading">
                                <div class="text-muted bootstrap-admin-box-title">${graduationProjectInfo.graduationProjectGoup}</div>
                                 
                            </div>
                           <!--  <div class="bootstrap-admin-no-table-panel-content bootstrap-admin-panel-content collapse in">
                                <form class="form-horizontal">
                                    <div class="col-lg-4 form-group">
                                        <label class="col-lg-4 control-label" for="query_bname">学生姓名</label>
                                        <div class="col-lg-8">
                                            <input class="form-control" id="studentName" name="studentName" type="text" value="">
                                            <label class="control-label" for="query_bname" style="display: none;"></label>
                                        </div>
                                    </div>
                                  
                                    <div class="col-lg-2 form-group">
										<button type="submit" class="btn btn-primary" id="btn_query" onclick="searchStudent(1)">查询</button>
                                    </div>
                        </div> -->
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-12">
                        <table id="data_list" class="table table-hover table-bordered" cellspacing="0" width="100%"  style="table-layout:fixed;margin-bottom:5;font-size:13px;">
                            <thead>
                            <tr>
                               <th width="15%">排名</th>
                                <th width="15%">学号</th>
                                <th width="15%">姓名</th>
                                <th width="40%">毕业设计研究题目</th>
                                <th width="15%">开题答辩得分</th>
                   <!--              <th width="11%">全部课程成绩</th>
                                <th width="11%">全部课程排名</th>
                                <th width="11%">CET-4</th>
                                <th width="11%">CET-6</th> -->
                            </tr>
                            </thead>
                            
                            
                            <!---在此插入信息-->
                            <c:forEach var="student" items="${graduationProjectInfo.graduationProjectList }" varStatus="i">
                             <tbody>
	                         	   <td>${student.seqNo}</td>
	                         	   <td>${student.studentNo}</td>
	                                <td>${student.studentName}</td>
	                                <td>${student.reseachTitle}</td>
	                                <td>${student.topicSelectingReportScore}</td>
	               <%--                  <td>${student.allCourseScore}</td>
	                                <td>${student.allCourseRank}</td>
	                                <td>${student.cet4Score}</td>
	                                <td>${student.cet6Score}</td> --%>
	                                
	                               <%--  <td><c:property value="#student.sex"/></td>
	                                 <td><c:property value="#student.sex"/></td>
	                                  <td><c:date name="#student.putdate" format="yyyy-MM-dd" /></td>
	                                    <td><c:property value="#student.num"/></td>
	                                    <td><c:property value="#student.currentNum"/></td>
	                                    <td><c:property value="#student.price"/></td>
	                                <td>
	                                <button type="button" class="btn btn-info btn-xs" data-toggle="modal" data-target="#findModal" onclick="getBookInfo(<c:property value="#student.bookId"/>)" >查看</button>
	                                	<button type="button" class="btn btn-warning btn-xs" data-toggle="modal" data-target="#updateModal" id="btn_update" onclick="updateBook(<c:property value="#student.bookId"/>)">修改</button>
	                                	<button type="button" class="btn btn-danger btn-xs" onclick="deleteBook(<c:property value="#student.bookId"/>)">删除</button>
	                                	<button type="button" class="btn btn-success btn-xs" onclick="addBookNum(<c:property value="#student.bookId"/>,<c:property value="#student.ISBN"/>)"  data-toggle="modal" data-target="#addNumModal">新增</button>
	                               	</td>       --%>                                        
                          	  </tbody>
                            </c:forEach>
                        </table>
                        
                        <c:choose>
							<c:when test="${graduationProjectInfo.totalPage <= 5}">
								<c:set var="begin" value="1"/>
								<c:set var="end" value="${graduationProjectInfo.totalPage }"/>
							</c:when>
							<c:otherwise>
								<c:set var="begin" value="${graduationProjectInfo.currentPage-2 }"/>
								<c:set var="end" value="${graduationProjectInfo.currentPage+2 }"/>
								<c:if test="${begin<1 }">
									<c:set var="begin" value="1"/>
									<c:set var="end" value="5"/>
								</c:if>
								<c:if test="${end>graduationProjectInfo.totalPage }">
									<c:set var="begin" value="${graduationProjectInfo.totalPage-4 }"/>
									<c:set var="end" value="${graduationProjectInfo.totalPage }"/>
								</c:if>
							</c:otherwise>
						</c:choose>
                    
                        
                        <div class="pull-right" id="paginationFoot">
                           <ul class="pagination" style="margin-top:0;margin-bottom:0" id="paginationUl">
                           <li class="disabled"><a href="#">第${graduationProjectInfo.currentPage}页/共${graduationProjectInfo.totalPage}页</a></li>
                           <li><a href="${pageContext.request.contextPath}/student/graduationProjectList.action?page=1">首页</a></li>
                           <li><a href="${pageContext.request.contextPath}/student/graduationProjectList.action?page=${graduationProjectInfo.currentPage-1 }">&laquo;</a></li><!-- 上一页 -->
                         
								<c:forEach begin="${begin }" end="${end }" var="i">
								  <c:choose>
							
								  	<c:when test="${i eq graduationProjectInfo.currentPage }">							  	
								  			<li class="active"><a>${i }</a><li>							 
								  	</c:when>
								  	<c:otherwise>
								  		<li><a href="${pageContext.request.contextPath}/student/graduationProjectList.action?page=${i}">${i}</a></li>
								  	</c:otherwise>
								  </c:choose>
								</c:forEach>
				        	
							  <c:if test="${graduationProjectInfo.currentPage < graduationProjectInfo.totalPage }">
								  <li><a href="${pageContext.request.contextPath}/student/graduationProjectList.action?page=${graduationProjectInfo.currentPage+1}">&raquo;</a></li>
							</c:if>
							
							<li><a href="${pageContext.request.contextPath}/student/graduationProjectList.action?page=${graduationProjectInfo.totalPage}">尾页</a></li>
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
