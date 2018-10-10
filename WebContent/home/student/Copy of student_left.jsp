<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/js/home/headAndFoot/head.js" charset=gb2312></script>
<%
  String studentNo=String.valueOf(session.getAttribute("userNo"));  
%>
<script type="text/javascript">

function tog1(){
	$("#businessMember").toggle();
}
function tog2(){
	$("#selfInfo").toggle();	
}
function tog3(){
	$("#resourceMember").toggle();	
}
function tog4(){
	$("#taskMember").toggle();	
}
</script>

<!-- 左侧开始 -->
<div class="main_l">
	<!-- 左菜单开始 -->
	<div class="leftbox">
		<div class="nav1 black">
			<a href='javascript:void(0);' onclick="tog1()">
				<strong>班级通知</strong>
			</a>
		</div>
		<div class="nav2 gray5" id="businessMember">
			<ul>
				<li><a href="/home/classTeaching/classTeachingDetailForStudent.jsp?studentNo=<%=studentNo%>">班级事务通知</a></li>
				<li><a href="/home/student/studentScore.jsp?studentNo=<%=studentNo%>">学术报告通知</a></li>
			</ul>
		</div>	
		<div class="nav1 black">
			<a href='javascript:void(0);' onclick="tog3()"><strong>申请报名</strong></a>
		</div>
		<div class="nav2 gray5" id="resourceMember">
			<ul>
				<li><a href="/noticeTeaching/noticeTeachingPageByStudentNo.action?studentNo=<%=studentNo%>">班级事务活动</a></li>
				<li><a href="/courseware/coursewarePageByStudentNo.action?studentNo=<%=studentNo%>">学术会议</a></li>
				<li><a href="/chapter/chapterPageOfVideoForStu.action?studentNo=<%=studentNo%>">竞赛</a></li>
				<li><a href="/chapter/chapterPageOfVideoForStu.action?studentNo=<%=studentNo%>">考试</a></li>
			</ul>
		</div>
		<div class="nav1 black">
			<a href='javascript:void(0);' onclick="tog4()"><strong>班务公开</strong></a>
		</div>
		<div class="nav2 gray5" id="taskMember">
			<ul>
				<li><a href="/homework/homeworkPageByStudentNo.action?studentNo=<%=studentNo%>">班级同学名单</a></li>
				<li><a href="/homeworkSubmit/homeworkSubmitPageByStudentNo.action?studentNo=<%=studentNo%>">班级公共活动</a></li>
				<li><a href="/problem/problemPageByStudentNo.action?studentNo=<%=studentNo%>">班级事务论坛</a></li>
			</ul>
		</div>
		<div class="nav1 black">
			<a href='javascript:void(0);' onclick="tog4()"><strong>博客论坛</strong></a>
		</div>
		<div class="nav2 gray5" id="forumMember">
			<ul>
				<li><a href="/home/blog/blog.jsp">博客论坛</a></li>
			</ul>
		</div>
		<div class="nav1 black">
			<a href="javascript:void(0);" onclick="tog2()"><strong>个人信息</strong></a>
		</div>
		<div class="nav2 gray5" id="selfInfo">
			<ul>
				<li><a href="/home/student/studentDetailForStudent.jsp?studentNo=<%=studentNo%>">基本信息</a></li>
			</ul>
		</div>
	</div>
	<!-- 左菜单结束 -->
</div>
<!-- 左侧结束 -->