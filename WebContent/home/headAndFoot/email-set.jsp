<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>邮箱设置</title>
<link rel="stylesheet" type="text/css"  href="/css/base.css"/>
<link rel="stylesheet" type="text/css" href="/css/font.css" />
<link rel="stylesheet" type="text/css" href="/css/login.css" />
<script type="text/javascript" src="/common/jquery/jquery.min.js"></script>
<script type="text/javascript" src="/common/layer/layer.js"></script>
<script type="text/javascript" src="/js/home/headAndFoot/email-set.js"></script>
</head>
<body>
	<div class="loginbox" style="width:660px; margin:20px auto;">
 		<div class="register_in"  style="width:650px; margin:5px auto" >
     		<div style="width:620px; margin:10px auto;" >
				<div class="leiline" style=" width:620px; ">
					<span class="gray4">注：</span>
					<span class="red5">请输入旧密码和要设置的新密码！</span>
				</div>
				<table border="0" cellspacing="0" cellpadding="0" class="gray3 regtab" style=" width:600px; " >
			       <tr>
			         <td width="19%" align="right"><span class="red4">*&nbsp;&nbsp;</span>旧密码：</td>
			         <td width="28%"><input name="" type="text" class="regput " id="oldPassword" onblur="validateOldPassword($(this).val())"/></td>
			         <td><div class="tshwrong gray1" style="line-height:24px;" id="oldPasswordTip"></div></td>
			       </tr>
			       <tr>
			         <td align="right"><span class="red4">*&nbsp;&nbsp;</span>新密码：</td>
			         <td><input name="" type="text" class="regput" onblur="validateNewPassword($(this).val())" id="newPassword"/></td>
			         <td><div class="tshwrong gray1" style="line-height:24px;" id="newPasswordTip"></div></td>
			       </tr>
			        <tr>
			         <td align="right"><span class="red4">*&nbsp;&nbsp;</span>确认新密码：</td>
			         <td><input name="" type="text" class="regput" onblur="validateConfirmNewPassword($(this).val())" id="confirmNewPassword"/></td>
			         <td><div class="tshwrong gray1" style="line-height:24px;" id="confirmNewPasswordTip"></div></td>
			       </tr>
			   <!-- <tr>
			         <td align="right"><span class="red4">*&nbsp;&nbsp;</span>录入新邮箱：</td>
			         <td><input name="" type="text" class="regput " onblur="validateEmail($(this).val())" id="newEmail"/></td>
			         <td  ><input name="input2" type="button"  class="butt1 black"  value="获取验证码" style="font-weight:100;" onclick="getTestCode()"/></td>
			       	 <td><div class="tsh gray1" style="line-height:24px;" id="newEmailTip"></div></td>
			       </tr>
			        <tr id="newEmailTipTr" style="display:none">
			         <td></td>
			         <td colspan="2"><div class="tsh gray1" style="line-height:24px;" id="newEmailTip"></div></td>
			       </tr>
			       <tr>
			         <td align="right"><span class="red4">*&nbsp;&nbsp;</span>邮箱验证码：</td>
			         <td ><input name="" type="text" class="regput "  style="width:100px;" onblur="validateTestCode($(this).val())" id="testCode"/></td>
			         <td><div class="tsh gray1" style="line-height:24px;" id="testCodeTip"></div></td>
			       </tr> -->
			
			       <tr>
			         <td></td>
			         <td colspan="2" > 
			         	<input name="input2" type="button" value="确认修改 "  class="butt2" onclick="submit()"/>&nbsp;&nbsp;&nbsp;&nbsp;
			         	<input name="input2" type="button" value="取消 "  class="butt2" onclick="cancel()"/>
			         </td>
			       </tr>
			   </table>
			</div>
 		</div>
	</div>
</body>
</html>
