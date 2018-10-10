<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>页面找不到啦</title>
<link rel="stylesheet" type="text/css"  href="/css/base.css"/>
<link rel="stylesheet" type="text/css" href="/css/font.css" />
<link rel="stylesheet" type="text/css" href="/css/login.css" />
<script type="text/javascript" src="/js/common/jquery.min.js"></script>
<script type="text/javascript">$(function(){$("#container").load("/home/foot.html");});</script>
</head>
<body>

<!--LOGO行开始-->
<div class="logo-tr" style="margin:10px auto ; width:900px;">
  <div class="logo-tr-l"><a href="/" target="_parent"><img src="/img/logo2.jpg"  alt="去健身网" width="190" height="85" border="0" /></a></div>
    <div class="logo-tr-m" style="width:400px;"> <span class="black3">页面找不到啦！</span></div>
</div>
<!--LOGO行结束-->


<!--文章盒子开始-->
<div class="loginbox" style=" margin:20px auto;">
 <div class="register_in" >
     <!--文章开始-->
     <div style="width:860px; margin:10px auto;">
     <!--文章标题开始--><!--文章标题结束-->
	     <!--文章内容开始-->
	     <div style="line-height:25px; padding:50px;" class="gray1">
			<table width="600" border="0" align="center" cellpadding="0" cellspacing="0">
			  <tr>
			    <td width="265" align="center"><img src="/img/wrong-ico.jpg" width="160" height="130"  /></td>
			    <td width="335" style="text-indent:0px;">
			    <p ><Span class="black2" style=" line-height:50px;">哎呀！对不起，您访问的页面找不到。</Span><br />
			    无法找到该页！<br />您正在搜索的页面可能已经删除、更名、或暂时不可用。<br />我们会尽快查找，提供您所需要的页面，请返回等待信息。</p>
			    <p style=" padding:20px; text-align:center;"><input name="errorb" onclick="javascript:window.location='/';" type="button" value="首&nbsp;&nbsp;页"  class="buttred"/></p>
			    </td>
			  </tr>
			</table>
		</div>
     <!--文章内容结束-->
     </div>
     <!--文章结束-->
 </div>
</div>
<!--文章盒子结束-->


<!--页尾开始-->
<div id='container'></div> 
<!--页尾结束-->
</body>
</html>