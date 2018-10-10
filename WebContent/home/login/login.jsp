<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>用户登录</title>
	<link rel="stylesheet" type="text/css"  href="/css/base.css"/>
	<link rel="stylesheet" type="text/css" href="/css/font.css" />
	<link rel="stylesheet" type="text/css" href="/css/login.css" />
	
	<script type="text/javascript" src="/common/jquery/jquery.min.js"></script>
	
	<link rel="stylesheet" type="text/css" href="/common/jquery-plugins/jquery-ui/jquery-ui.min.css"/>
	<script type="text/javascript" src="/common/jquery-plugins/jquery-ui/jquery-ui.min.js"></script>
	
	<script type="text/javascript" src="/common/jquery-plugins/jquery-cookie/jquery.cookie.js"></script>
	
	<script type="text/javascript" src="/js/home/login/login.js"></script>
	<script type="text/javascript">
		$(function(){
			$("#sosmsg1").load("/home/login/commonlogin.html");
		});
	</script>
	<style type="text/css">
		.log_in{
			width:260px; 
			height:30px; 
			line-height:30px; 
			padding:0; 
			margin:0 0 20px 0;
			padding-top:4px!important; 
			font-size:12px; 
			border:1px solid #ccc; 
			padding-left:17px;
		}
		.log_in{
			width:260px; 
			height:30px; 
			line-height:30px;
			padding:0; 
			margin:0 0 20px 0;
			padding-top:4px!important; 
			font-size:12px; 
			border:1px solid #ccc; 
			padding-left:17px;
		}
	</style>
</head>
<body>
	<!--LOGO行开始-->
	<div class="logo-tr" style="margin:10px auto ; width:900px;">
	    <div class="logo-tr-l">
	    	<a href="/" target="_parent"><img src="/img/logo.gif"  alt="西理工研究生" width="190" height="85" border="0" /></a>
	    </div>
	    <div class="logo-tr-m"> 
	    	<span class="black3">用户登录</span>
	    </div>
	   <!--  <div class="logo-tr-m">  -->
	    	<span class="red1">该系统目前只支持兼容各种版本的PC浏览器，不能很好地适配手机浏览器！给您带来的不便尽请谅解，后续会升级本系统版本，以支持手机浏览器服务。</span>
	    <!-- </div> -->
	</div>
	<!--LOGO行结束-->
	
	
	<!--用户登录盒子开始-->
	<div class="loginbox">
	 <div class="login_in">
	     <div class="loginl "></div>
	     <!--登录开始-->
	     <div class="loginr">
	     <form id="login_form">
			<input type="hidden" id="url" value="${param.url }"></input>
		   	<table  border="0" cellspacing="0" cellpadding="0" class="logtab" >
		  		<tr>
		    		<td width="79" class="gray1">账号</td>
		    		<td width="183">&nbsp;</td>
		  		</tr>
		  		<tr>
		    		<td colspan="2"> <input style="color:#ccc" name="j_username" id="j_username" type="text" class="log_in " onfocus="clearTipInfo()" onblur="ifNull()"/></td>
		    	</tr>
		  		<tr>
		    		<td class="gray1">密码</td>
		  		</tr>
		  		<tr>
		    		<td colspan="2"> <input name="j_password" id="j_password" type="password" class="log_mm " onfocus="enterLogin();"/></td>
		    	</tr>
			    <!-- 
			  	<tr>
			    	<td class="gray1" align="left"><input name="" type="checkbox" value="" class="check" id="remember_accountId"/>记住账号</td>
			    	<td class="gray1" align="left"><input name="" type="checkbox" value="" class="check" id="auto_login" />下次自动登录
			    	<span class="red1"><a href="/home/login/password.jsp">忘记密码</a></span>
			    	</td>  
			  	</tr>--> 
			   	<tr>
			     	<td colspan="2"><span class="red1">登录账号和初始登录密码均为本人学号。</span></td> 
			  	</tr>
			  	<tr>
			     	<td colspan="2"><span id="Logintips" class="red1"></span></td> 
			  	</tr>
			  	<tr>
		        	<td style="padding:20px 0;"><input name="" type="button" value="登录"  class="buttred" onclick="Save();"/></td> 
			    	<!-- <td style="padding:20px 0;"><input name="" type="button" value="登录测试"  class="buttred" onclick="SaveTest1();"/></td> 
			    	<td  style="padding:20px 0;"><span class="black">还不是会员，</span><span class="blue"><a href="/home/register/registerUser.jsp">立即注册&nbsp;>></a></span></td>
			 	 	-->
			  	</tr>
			
				<!--   <tr>
				   <td class="gray1" align="left">使用合作网站账号登录</td> 
				    <td class="gray1" align="left">
				    <div id="sosmsg1"></div> 
			      </td>
				   </tr> -->
				</table>
			</form>
		</div>
	   <!--登录结束-->
	 </div>
	</div>
	<!--用户登录盒子结束-->
	
	<!--页尾开始-->
		<%@ include file="/home/headAndFoot/foot.jsp"%>
	<!--页尾结束-->
</body>
</html>
