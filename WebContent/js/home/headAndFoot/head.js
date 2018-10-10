
$(function(){
	$.post("/getLoginMessage.action",{},function(data){
		if(data.hasLogined == 1){
			$("#userName").html(data.studentName);
		}else{
			window.location = "/home/login/login.jsp";
			//$("#userName").html("");
		}
	},"json");
});

function logout() {
	/*$.ajax({
		url : "/j_spring_security_logout",
		type:"post",
		async : false,
		success : function(data) {alert(data);
			window.location = '/home/login/login.jsp';
		},
		dataType : "json"
	});*/
	$.post("/j_spring_security_logout", {}, function(data) {
		window.location = '/home/login/login.jsp';
	});
}

function updatePassword(){
	layer.open({
        title:'修改密码',
        type: 2,
        area: ['700px', '410px'],
        fix: false, //不固定
        maxmin: true,
        content:'/home/headAndFoot/email-set.jsp',
        success:function(layero,index){
        },
        end:function(){
            var handle_status = $("#handle_status").val();
            if ( handle_status == '1' ) {
                layer.msg('添加成功！',{
                    icon: 1,
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                },function(){
                    history.go(0);
                });
            } else if ( handle_status == '2' ) {
                layer.msg('添加失败！',{
                    icon: 2,
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                },function(){
                    history.go(0);
                });
            }
        }
    });
}

/*function aboutSystem() {
	window.location.href="../aboutSystem/about_me.jsp";
	window.open("../../aboutSystem/about_me.jsp");
}*/