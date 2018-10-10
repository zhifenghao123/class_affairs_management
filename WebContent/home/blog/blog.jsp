<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"	type="text/css" href="/css/base.css">
<link rel="stylesheet" type="text/css" href="/css/font.css">
<link rel="stylesheet" type="text/css" href="/css/manage.css">
<title>学生空间-博客</title>
<script type="text/javascript" src="/common/jquery/jquery.min.js"></script>

<script type="text/javascript" src="/common/ueditor/ueditor.simple.config.js"></script>
<script type="text/javascript" src="/common/ueditor/ueditor.all.js"></script>

<script type="text/javascript" src="/js/home/blog/blog.js"></script>
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
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
			    	<td  class="tdleft gray1">博文名称&nbsp;&nbsp;<b class=" red3">*</b></td>
			        <td  valign="middle" class="gray1"   >
			        	<input name="name" type="text"  class="putin1 black" value="" id="title" onblur="nameV($(this).val());" style="width:500px"/>
			        	<span class="gray1 tdspan"></span>         
				        <input name="cardId" type="hidden" value="" id="cardId" />
				        <input name="tp" type="hidden" id="tp"/>
				        <input name="updateOrAdd" type="hidden" id="add" value="add"/>
				        &nbsp;&nbsp;<span id="nameTip" style="padding-top:5px;"></span> 
			        </td>
	        	</tr>
	        	<tr id="card_type_tr">
          			<td  class="tdleft gray1">博文类型</td>
          			<td id="card_type_td">
	          			<span class="gray1">类型&nbsp;&nbsp;</span>
	          			<label id="card_type_label">
				            <select name="type" class="select1 black" id="type"    style="width:70px;" onchange="changeType();" >
				              <option value="0" selected="selected">请选择</option>
				              <option value="1">1</option>
				              <option value="2">2</option>
				              <option value="3">3</option>
				              <option value="4">4</option>
				              <option value="5">5</option>
				              
				            </select>
			            	<span id="typeTip" style="padding-top:5px;"></span>  
			          </label>
	         	 	</td>
	          </tr>
	           <tr>
		          <td  class="tdleft gray1">原文链接地址<b class=" red3">*</b></td>
		          <td   style="text-indent:0; " class="gray1" >
		            <input id="linkUrl" name="linkUrl" style="width:500px"/>
		            <span id="linkUrlTip" style="padding-top:5px;"></span>  
		            </td>
		      </tr>
	          <tr>
		          <td  class="tdleft gray1">博文<b class=" red3">*</b></td>
		          <td   style="text-indent:0; " class="gray1">
		           <!--  <div class="tdboxpt" > -->
		            <textarea id="content" name="content"  style="width: 900px; height: 500px;"></textarea>
		            <!-- </div> -->
		            <span id="contentTip" style="padding-top:5px;"></span>  
		            </td>
		      </tr>
		       <tr>
		          <td  class="tdleft gray1" ></td>
				 <td  class="tdleft gray1" >
		          	<input name="input2" id="buttonSave" type="button" class="butt3" value="保存"   onclick="showResult()"/>
				 </td>
		      </tr>
			 </table>
		</div>
		 <!-- 中间结束 -->
	</div>
	<!-- 主体结束 -->
	<!-- 尾部开始 -->
	<%@ include file="/home/headAndFoot/foot.jsp" %>
	<!-- 尾部结束 -->
</body>
</html>