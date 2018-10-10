/**
 * 登陆窗口的javascript文件
 */
$(function(){
	 $('#login_panel').panel({ 
		  width:500,    
		  height:260,  
		  title: '系统管理登陆窗口'   
		}); 

		//按下回车登录系统
		document.onkeydown = function(event){
			event = event?event:window.event;
			if (event.keyCode == 13) {
				login();
			}
		};

 });
 function reset(){
	 $("#login_form")[0].reset();
 }
 function login(){
	 var isValid = $("#login_form").form('validate');
	 if(isValid){
	 $.messager.progress({text:'数据加载中...'});
	 var userId = $("#userId").val();
	 var pwd = $("#password").val();
	 /*
	 $.post("/admin/getAdministratorState.action",{"jobNo":userId},function(data){
		 if(data.msg == 1){
			 $.post("/Md5Data.action",{password:pwd},function(password){
					var data = {
						j_username : userId+"-",
						j_password : password
					};
					$.post("/j_spring_security_check", data, function(){
						$.post("/admin/getJobNoToSession.action",{userId:userId},function(msg){
							if(msg.success == "1"){
								window.location="/admin/index.jsp";
							}else{
								$.messager.alert('提示','用户名或密码有误!');
							}
							$.messager.progress('close');
						},"json");
						
					});
				});
		 }else if(data.msg == 2){
			 $.messager.progress('close');
			 $.messager.alert('提示','对不起,您的账号已被禁用!');
		 }else{
			 $.messager.progress('close');
			 $.messager.alert('提示','对不起,用户名不存在!');
		 }
	 },"json");
	 */
	 $.post("/admin/getAdministratorState.action",{"administratorNo":userId},function(data){
		 if(data.msg == 1){
			 $.post("/Md5Data.action",{password:pwd},function(md5Password){
					var data = {
						j_username : userId+"-",
						j_password : md5Password.encryptionText
					};
					$.post("/j_spring_security_check", data, function(){
						$.post("/admin/getJobNoToSession.action",{userId:userId},function(msg){
							if(msg.success == "1"){
								window.location="/admin/index.jsp";
							}else{
								$.messager.alert('提示','用户名或密码有误!');
							}
							$.messager.progress('close');
						},"json");
						
					});
				},"json");
		 }else if(data.msg == 2){
			 $.messager.progress('close');
			 $.messager.alert('提示','对不起,您的账号已被禁用!');
		 }else{
			 $.messager.progress('close');
			 $.messager.alert('提示','对不起,用户名不存在!');
		 }
	 },"json");
	 }
 }