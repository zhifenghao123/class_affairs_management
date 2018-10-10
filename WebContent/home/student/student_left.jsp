<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<%
String userType = (String)request.getSession().getAttribute("userType");
%>
            <!-- left, vertical navbar -->
       <div class="col-md-2 bootstrap-admin-col-left">
                <ul class="nav navbar-collapse collapse bootstrap-admin-navbar-side">
                <%if(userType.equals("student")) {%>
                    <li>
                        <a href="${pageContext.request.contextPath}/student/studentList.action">
                        	<i class="glyphicon glyphicon-chevron-right"></i>班级同学
                        </a>
                    </li>
                   <%} %>
                   <%if(userType.equals("monitor")) {%>
                    <li>
                        <a href="${pageContext.request.contextPath}/student/studentManageList.action">
                        	<i class="glyphicon glyphicon-chevron-right"></i>班级同学
                        </a>
                    </li>
                    
                    <li>
                        <a href="${pageContext.request.contextPath}/student/studentScoreList.action">
                        	<i class="glyphicon glyphicon-chevron-right"></i>班级成绩
                        </a>
                    </li>
                   <%} %>
                   <li>
                        <a href="${pageContext.request.contextPath}/student/graduationProjectList.action">
                        	<i class="glyphicon glyphicon-chevron-right"></i>研究生学位论文
                        </a>
                    </li>
                     <li>
                        <a href="${pageContext.request.contextPath}/student/noticeList.action">
                        	<i class="glyphicon glyphicon-chevron-right"></i>班级事务通知
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/student/activityList.action">
                        	<i class="glyphicon glyphicon-chevron-right"></i>班级事务活动
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/blog/blogList.action">
                        	<i class="glyphicon glyphicon-chevron-right"></i>博客论坛
                        </a>
                    </li>
                   <li>
                        <a href="${pageContext.request.contextPath}/home/student/student_detail.jsp">
                        	<i class="glyphicon glyphicon-chevron-right"></i>个人信息
                        </a>
                    </li>

                    <li>
                        <a href="${pageContext.request.contextPath}/student/myParticipatedActivityList.action">
                        	<i class="glyphicon glyphicon-chevron-right"></i>我参加的事务活动
                        </a>
                    </li>
                    <%if(userType.equals("monitor")) {%>
                     <li>
                        <a href="${pageContext.request.contextPath}/admin/myPublishedNoticeList.action">
                        	<i class="glyphicon glyphicon-chevron-right"></i>发布班级事务通知
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/student/userPublishedActivityList.action">
                        	<i class="glyphicon glyphicon-chevron-right"></i>发布班级事务活动
                        </a>
                    </li>
                    <%} %>
                    <%-- <li>
                        <a href="${pageContext.request.contextPath}/student/onlineBehaviorList.action">
                        	<i class="glyphicon glyphicon-chevron-right"></i>在线行为记录
                        </a>
                    </li> --%>
                </ul>	      
            </div>

            <!-- content -->
            
         <%--    <div class="col-md-2 bootstrap-admin-col-left">
            <div >
            <div >fff</div>
                <ul class="nav navbar-collapse collapse bootstrap-admin-navbar-side">
                    <li>
                        <a href="${pageContext.request.contextPath}/student/studentList.action">
                        	<i class="glyphicon glyphicon-chevron-right"></i>班级同学
                        </a>
                    </li>
                     <li>
                        <a href="${pageContext.request.contextPath}/student/noticeList.action">
                        	<i class="glyphicon glyphicon-chevron-right"></i>班级事务通知
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/student/activityList.action">
                        	<i class="glyphicon glyphicon-chevron-right"></i>班级事务活动
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/blog/blogList.action">
                        	<i class="glyphicon glyphicon-chevron-right"></i>博客论坛
                        </a>
                    </li>
                   <li>
                        <a href="${pageContext.request.contextPath}/home/student/student_detail.jsp">
                        	<i class="glyphicon glyphicon-chevron-right"></i>个人信息
                        </a>
                    </li>
                </ul>	 
                </div>  
                
                  <div>
                <ul class="nav navbar-collapse collapse bootstrap-admin-navbar-side">
                    <li>
                        <a href="${pageContext.request.contextPath}/student/studentList.action">
                        	<i class="glyphicon glyphicon-chevron-right"></i>班级同学
                        </a>
                    </li>
                     <li>
                        <a href="${pageContext.request.contextPath}/student/noticeList.action">
                        	<i class="glyphicon glyphicon-chevron-right"></i>班级事务通知
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/student/activityList.action">
                        	<i class="glyphicon glyphicon-chevron-right"></i>班级事务活动
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/blog/blogList.action">
                        	<i class="glyphicon glyphicon-chevron-right"></i>博客论坛
                        </a>
                    </li>
                   <li>
                        <a href="${pageContext.request.contextPath}/home/student/student_detail.jsp">
                        	<i class="glyphicon glyphicon-chevron-right"></i>个人信息
                        </a>
                    </li>
                </ul>	 
                </div>     
            </div> --%>