<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改密码</title>
 <%@include file="/admin/included.jsp" %>
 <script type="text/javascript">
 $(function(){
	 $('.save').linkbutton({
         text: '保存',
         iconCls: 'icon-save',
         plain: false
       });
       $('.cancel').linkbutton({
         text: '取消',
         iconCls: 'icon-cancel',
         plain: false
       });
 });
 function cancel(){
	 window.parent.$('#centerTabs').tabs('close', '修改密码');
 }
 var savePwd = function(){
	 if ($('form').form('validate')) {
		 $.post('/admin/updatePwd.action',{oldPwd:$('#oldPwd').val(),newPwd:$('#newPwd').val()},function(data){
			 if(data.noEqual){
				 parent.$.messager.alert('提示','旧密码错误，请重新输入');
			 }else{
				 if (data.success) {
					 parent.$.messager.show({
	                    title: '提示信息',
	                    msg: '操作成功',
	                    timeout: 2000
	                  });
	                  //密码修改后，重新登录
	                  $.post("/j_spring_security_logout", {}, function(data){
	              		top.location.href = "/admin/login.jsp";
	              		});
	                } else {
	                  parent.$.messager.alert('提示', '操作失败', 'error');
	                }
			 }
		 },"json");
	 }
 };
 </script>
</head>
<body>
	<div style="margin:100px;">
	<form>
        <table width="100%" border="0" cellspacing="0" cellpadding="0"  style="line-height:30px;" class="gray1">
           <tr>
              <td width="135">请您输入您的旧密码&nbsp;&nbsp;</span><b class="red3">*</b></td>
              <td width="600" ><input id="oldPwd" type="password" class="putin1 black easyui-validatebox" data-options="required:true,validType:['pwd','length[6,16]']"  style="width:180px;" maxlength="16" value=""/></td>
           </tr>
           <tr>
              <td >请您输入您的新密码&nbsp;&nbsp;</span><b class=" red3">*</b></td>
              <td ><input name="newPwd" id="newPwd" type="password" class="putin1 black easyui-validatebox" data-options="required:true,validType:['pwd','length[6,16]']" style="width:180px;"  maxlength="16" value=""/></td>
           </tr>
           <tr>
              <td  class="gray1">请您确认您的新密码&nbsp;&nbsp;</span><b class=" red3">*</b></td>
              <td ><input name="confirmPwd"  type="password" class="putin1 black easyui-validatebox" data-options="required:true,validType:['pwd','length[6,16]','equals[\'#newPwd\']']" style="width:180px;"  maxlength="16" value=""/></td>
           </tr>
           <tr>
              <td></td>
              <td style="height:50px; line-height:50px;">
              	<a href="javascript:void(0)" class="save" onclick="savePwd();"></a>
              	<a href="javascript:void(0)" class="cancel" onclick="cancel()"></a>
           </tr>
        </table>
        </form>
    </div>
</body>
</html>