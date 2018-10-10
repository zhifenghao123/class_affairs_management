function validateOldPassword(oldPassword){
	$("#oldPasswordTip").empty();
	if(oldPassword == ""){
		$("#oldPasswordTip").html("请输入旧密码！");
		return false;
	}else
		return true;
}
function validateNewPassword(newPassword){
	$("#newPasswordTip").empty();
	if(newPassword == ""){
		$("#newPasswordTip").html("请输入要设置的新密码！");
		return false;
	}else
		return true;
}
function validateConfirmNewPassword(confirmNewPassword){
	$("#confirmNewPasswordTip").empty();
	if(confirmNewPassword == ""){
		$("#confirmNewPasswordTip").html("请输入并确认要设置的新密码！");
		return false;
	}else if(confirmNewPassword != $("#newPassword").val()){
		$("#confirmNewPasswordTip").html("确认输入的密码不一致！");
		return false;
	}else
		return true;
}
function submit(){
	if(validateOldPassword($("#oldPassword").val()) == false || validateNewPassword($("#newPassword").val()) == false || validateConfirmNewPassword($("#confirmNewPassword").val()) == false){
		return;
	}else{
		$.ajax({
			url:"/student/updateStudentPassword.action",
			async:false,
			data:{
				oldPassword:$("#oldPassword").val(),
				newPassword:$("#newPassword").val()
			},
			dataType: 'json',
			success: function(data){
				if(data.msg==1){
					layer.confirm('修改密码成功。', {
						  btn: ['确定'] //按钮
						}, function(){
							cancel();
						});
				}else if(data.msg == 2){
					layer.confirm('修改密码失败。', {
						  btn: ['确定'] //按钮
						});
				}else if(data.msg == 3){
					layer.confirm('输入的密码错误。', {
						  btn: ['确定'] //按钮
						});
				}
			}
		});
	}
	
}

function cancel(){
	 var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
	 parent.layer.close(index); //执行关闭
	 parent.location.reload();
}