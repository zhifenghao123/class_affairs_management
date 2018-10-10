<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="com.classaffairs.common.AuthorityUtil"%>
<%
AuthorityUtil authUtil = new AuthorityUtil(session);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>系统管理主页</title>
		<%@include file="/admin/included.jsp"%>
		<script type="text/javascript" src="/js/admin/index.js">
		</script> 
		<script type="text/javascript" src="/admin/easyui/layer.min.js"></script>
	
	</head>
	<%
		String jobName = (String) request.getSession().getAttribute("jobName");
	%>
	
	<body id="cc" class="easyui-layout" fit="true">
		<div data-options="region:'north',split:false" style="height: 62px; overflow: hidden;">
			<div style="background: #E4E4E4;">
				<img src="/img/admin/admin_logo.gif" alt="研究生班级事务后台管理系统" />
			</div>
			<div style="position: absolute; right: 10px; bottom: 0;">
				<a href="javascript:void(0)" class="easyui-menubutton" data-options="menu:'#control_menu',iconCls:'icon-help'"> 控制面板 </a>
			</div>
			<div id="control_menu" style="width: 150px;">
				<%--  <div data-options="iconCls:'icon-undo'">个人信息</div>--%>
				<div data-options="iconCls:'icon-redo'" onclick="changePwd();">
					修改密码</div>
				<div class="menu-sep"></div>
				<div data-options="iconCls:'icon-back'" onclick="logout();">
					退出系统</div>
			</div>
		</div>
		<div data-options="region:'west',split:false" style="width: 200px; overflow: hidden;">
			<div class="easyui-panel" fit="true" border="false">
				<div div class="easyui-accordion" fit="true" border="false">
					<div title="系统菜单" iconCls="icon-tip">
						<div class="easyui-layout" fit="true">
							<div region="north" border="false" style="overflow: hidden;">
								<a href="javascript:void(0);" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="expandAll();"> 展开</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" plain="true" iconCls="icon-undo" onclick="collapseAll();"> 折叠 </a> 
								<a href="javascript:void(0);" class="easyui-linkbutton" plain="true" iconCls="icon-reload" onclick="flush();"> 刷新 </a>
								<hr style="border-color: #fff;" />
							</div>
							<div region="center" border="false">
								<ul id="tree" style="margin-top: 5px;">
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div data-options="region:'center'" title="[<%=jobName%>], 欢迎您" style="overflow-x:visible;">
			<div id="tabsMenu" class="easyui-menu" style="width: 120px;">
				<div  name="close" closable:true>关闭</div>
			</div>
			<div class="easyui-tabs" id="centerTabs" fit="true">
		 	<%if (authUtil.haveAuthority("/admin/checkFirstPage.action")) {%>
				<div title="审核综合页" id="firstpage" >
				<%if (authUtil.haveAuthority("/admin/chceckClub.action")) {%>
					<div style="width: 47%; height: 325px; padding: 10px; background: #fafafa;float:left;">      
						<div id="yellowclub" title="黄页俱乐部认领" style="width: 100%; height: 325px; padding: 10px; background: #fafafa;" data-options="iconCls:'icon-save',tools:'#tt15'"></div>
						<div id="tt15">
			   				<a  href="javascript:more15();">更多>></a>
						</div>
					</div>
				<%}%>
			    <%if (authUtil.haveAuthority("/admin/checkCardPage.action")) {%>
					<div  id="clubCardss" style="width: 48%; height: 325px; padding: 10px; background: #fafafa;float:left;">                
			        	<div  id="check_card" title="审核健身卡" style="width:100%;height:325px;padding:10px;background:#fafafa;overflow:auto;" data-options="iconCls:'icon-save',tools:'#tt'"></div>
				        <div id="tt">
					    	<a  href="javascript:more();">更多>></a>
				        </div>       
			     	</div>
			    <%}%>
				<%if (authUtil.haveAuthority("/admin/checkBlogPage.action")) {%>
					<div style="width: 47%; height: 325px; padding: 10px; background: #fafafa;float:left;">      
						<div id="blog" title="审核日志" style="width: 100%; height: 325px; padding: 10px; background: #fafafa;" data-options="iconCls:'icon-save',tools:'#tt2'">	</div>
						<div id="tt2">
				   			<a  href="javascript:more2();">更多>></a>
						</div>
					</div>
				<%}%>
				<%if (authUtil.haveAuthority("/admin/checkCoursePage.action")) {%>
					<div style="width: 47%; height: 325px; padding: 10px; background: #fafafa;float:left;">      
						<div id="check_course" title="审核课程" style="width: 100%; height: 325px; padding: 10px; background: #fafafa;" data-options="iconCls:'icon-save',tools:'#tt4'">	</div>
						<div id="tt4">
							<a  href="javascript:more4();">更多>></a>
						</div>
					</div>
				<%}%>
				<%if (authUtil.haveAuthority("/admin/checkSitePage.action")) {%>
					<div style="width: 47%; height: 325px; padding: 10px; background: #fafafa;float:left;">      
						<div id="check_site" title="审核场地" style="width: 100%; height: 325px; padding: 10px; background: #fafafa;" data-options="iconCls:'icon-save',tools:'#tt5'"></div>
						<div id="tt5">
							<a  href="javascript:more5();">更多>></a>
						</div>
					</div>
				<%}%>
				<%if (authUtil.haveAuthority("/admin/checkNewsPage.action")) {%>
					<div style="width: 47%; height: 325px; padding: 10px; background: #fafafa;float:left;">      
						<div id="news" title="审核新闻" style="width: 100%; height: 325px; padding: 10px; background: #fafafa;" data-options="iconCls:'icon-save',tools:'#tt6'"></div>
						<div id="tt6">
							<a  href="javascript:more6();">更多>></a>
						</div>
					</div>
				<%}%>
				<%if (authUtil.haveAuthority("/admin/checkAnnouncementPage.action")) {%>
					<div style="width: 47%; height: 325px; padding: 10px; background: #fafafa;float:left;">      
						<div id="announcement" title="审核公告"  style="width:100%; height: 325px; padding: 10px; background: #fafafa;" data-options="iconCls:'icon-save',tools:'#tt7'">	</div>
						<div id="tt7">
							<a  href="javascript:more7();">更多>></a>
						</div>
					</div>
				<%}%>
				<%if (authUtil.haveAuthority("/admin/checkArticlePage.action")) {%>
					<div style="width: 47%; height: 325px; padding: 10px; background: #fafafa;float:left;">      
						<div id="sportArticle" title="审核知识文章" style="width: 100%; height: 325px; padding: 10px; background: #fafafa;" data-options="iconCls:'icon-save',tools:'#tt8'"></div>
						<div id="tt8">
							<a  href="javascript:more8();">更多>></a>
						</div>
					</div>
				<%}%>
				<%if (authUtil.haveAuthority("/admin/checkPicturePage.action")) {%>
					<div style="width: 47%; height: 325px; padding: 10px; background: #fafafa;float:left;">      
						<div id="picture" title="审核图片" style="width: 100%; height: 325px; padding: 10px; background: #fafafa;" data-options="iconCls:'icon-save',tools:'#tt9'"></div>
						<div id="tt9">
							<a  href="javascript:more9();">更多>></a>
						</div>
					</div>
				<%}%>
				<%if (authUtil.haveAuthority("/admin/checkVideoPage.action")) {%>
					<div style="width: 47%; height: 325px; padding: 10px; background: #fafafa;float:left;">      
						<div id="video" title="审核视频" style="width: 100%; height: 325px; padding: 10px; background: #fafafa;" data-options="iconCls:'icon-save',tools:'#tt10'"></div>
						<div id="tt10">
							<a  href="javascript:more10();">更多>></a>
						</div>
					</div>
				<%}%>
				<%if (authUtil.haveAuthority("/admin/checkCommentPage.action")) {%>
					<div style="width: 47%; height: 325px; padding: 10px; background: #fafafa;float:left;">      
						<div id="check_comment" title="审核评论" style="width:100%; height: 325px; padding: 10px; background: #fafafa;" data-options="iconCls:'icon-save',tools:'#tt11'">		</div>
						<div id="tt11">
							<a  href="javascript:more11();">更多>></a>
						</div>
					</div>
				<%}%>
				<%if (authUtil.haveAuthority("/admin/checkCommodityPage.action")) {%>
					<div style="width: 47%; height: 325px; padding: 10px; background: #fafafa;float:left;">      
						<div id="check_commodity" title="审核商品" style="width: 100%; height: 325px; padding: 10px; background: #fafafa;" data-options="iconCls:'icon-save',tools:'#tt12'">	</div>
						<div id="tt12">
							<a  href="javascript:more12();">更多>></a>
						</div>				
					</div>
				<%}%>
				<%if (authUtil.haveAuthority("/admin/checkAdvertisingPage.action")) {%>
					<div style="width:47%; height: 325px; padding: 10px; background: #fafafa;float:left;">      
						<div id="advertising" title="审核广告" style="width: 100%; height: 325px; padding: 10px; background: #fafafa;" data-options="iconCls:'icon-save',tools:'#tt13'">	</div>
						<div id="tt13">
							<a  href="javascript:more13();">更多>></a>
						</div>
					</div>
				<%}%>
				<%if (authUtil.haveAuthority("/admin/checkActivityPage.action")) {%>
				 	<div style="width: 47%; height: 325px; padding: 10px; background: #fafafa;float:left;">
				        <div  id="check_activity" title="审核活动" style="width:100%;height:325px;padding:10px;background:#fafafa;overflow:auto;" data-options="iconCls:'icon-save',tools:'#tt3'" ></div>
				        <div id="tt3">
							<a  href="javascript:more3();">更多>></a>
						</div>
				 	</div>  
				 <%}%>
				<%if (authUtil.haveAuthority("/admin/checkClubPage.action")) {%> 
				 	<div style="width: 47%; height: 325px; padding: 10px; background: #fafafa;float:left;">               
				 		<div  id="check_club" title="审核俱乐部" style="width:100%;height:325px;padding:10px;background:#fafafa;overflow:auto;" data-options="fit:true,iconCls:'icon-save',tools:'#tt1'"></div>
				        <div id="tt1">
							<a  href="javascript:more1();">更多>></a>
						</div>
					 </div> 
				<%}%>
				<%if (authUtil.haveAuthority("/admin/checkHeadImagePage.action")) {%> 
				 	<div style="width: 47%; height: 325px; padding: 10px; background: #fafafa;float:left;">                
				        <div  id="headImage" title="头像管理" style="width:100%;height:325px;padding:10px;background:#fafafa;overflow:auto;"  data-options="fit:true,iconCls:'icon-save',tools:'#tt14'"></div>
				        <div id="tt14">
							<a  href="javascript:more14();">更多>></a>
						</div>
				 	</div> 
		 		<%}%>
				</div>
			<%}%>
			</div>
		</div>
	<script type="text/javascript">
	//绑定tabs的右键菜单
	$("#centerTabs").tabs({
	    onContextMenu : function (e, title) {
	        e.preventDefault();
	        $('#tabsMenu').menu('show', {
	            left : e.pageX,
	            top : e.pageY
	        }).data("tabTitle", title);
	    }
	});
	
	//实例化menu的onClick事件
	$("#tabsMenu").menu({
	    onClick : function (item) {
	        CloseTab(this, item.name);
	    }
	});
	
	//关闭事件的实现
	function CloseTab(menu, type) {
	    var curTabTitle = $(menu).data("tabTitle");
	    var tabs = $("#centerTabs");
	    
	    if (type === "close") {
	        tabs.tabs("close", curTabTitle);
	        return;
	    }
	}
		</script>
	</body>
</html>