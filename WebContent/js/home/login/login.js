var urlCurrent = window.location.href;
// user:1
// club:2
var userOrClub = 0;
// friend:1
// coach = 0;
var friendOrCoach = 2;
$(document).ready(function() {
	$('#j_username').val("学号");
	if ($.cookie("remember_accountId") == "true") {
		$("#remember_accountId").attr("checked", true);
		$("#j_username").val($.cookie("j_username"));
		$("#j_password").val($.cookie("j_password"));
	}
});

// 记住用户名密码

function Save() {
	var rs = Math.ceil(Math.random(Math.round(9.5)));
	
	var studentNo = $("#j_username").val();
	var password = $("#j_password").val();
	studentNo = $.trim(studentNo);
	$.ajax({
		url : "/getStudentByStudentNo.action",
		type : "post",
		async : false,
		dataType : "json",
		data : {
			"studentNo" : studentNo
		},
		success : function(accountData) {
			if (accountData.exist) {
				var studentId = accountData.studentId;
				$.ajax({
					url : "/getStudentState.action",
					type : "post",
					data : "studentId=" + studentId,
					async : false,
					dataType : "json",
					success : function(data) {
						if (data.openOrNot == 1) {
							alert("对不起，您未正常办理入学手续，不能登录！");
						} else {
							$.post("/Md5Data.action",{password : password},
								function(md5Password) {//alert(md5Password.encryptionText);
									var data = {
										j_username : studentNo,
										j_password : md5Password.encryptionText
									};
									$.post("/j_spring_security_check",data,
										function() {
									/*		$.ajax({
												url : "/getEPath.action",
												type : "post",
												async : false,
												dataType : "json",
												success : function(data) {
													var url = data.ePath+ "/JSONPlogin.htm";
													$.ajax({
														type : "POST",
														dataType : "jsonp",
														crossDomain : true,
														async : false,
														url : url,
														data : {
															"username" : accountId,
															"password" : password
														},
														jsonpCallback : "success_jsonpCallback",
														success : function(data){*/
															$.post("/getLoginMessage.action",{},
																function(msg) {
																	if (msg.hasLogined == 1) {
																	/*	if ($("#auto_login").prop("checked")) {
																			$.cookie("remember_accountId","true",{expires : 7}); // 存储一个带7天期限的cookie
																			$.cookie("j_username",userId,{expires : 7});
																			$.cookie("j_password",password,{expires : 7});
																			$.cookie("auto_login","true",{expires : 7});
																		} else if ($("#remember_accountId").prop("checked")) {
																			$.cookie("remember_accountId","true",{expires : 7}); // 存储一个带7天期限的cookie
																			$.cookie("j_username",userId,{expires : 7});
																			$.cookie("j_password","",{expires : -1});
																			$.cookie("auto_login","false",{expires : -1});
																		} else {
																			$.cookie("remember_accountId","false",{expire : -1});
																			$.cookie("j_username","",{expires : -1});
																			$.cookie("j_password","",{expires : -1});
																			$.cookie("auto_login","false",{expires : -1});
																		}*/
																		$.post("/loginRecord.action",{},function() {},"json");
																		
																		var url = $.cookie("sessionId");
																		$.post("/removeloadinfo.action",{"url" : url},function() {},"json");
																		
																		window.location = "/home/student/student_manage.jsp";
																
																	} else {
																		var tips = "<span class='red'>密码错误！</span>";
																		$("#Logintips").empty();
																		$("#Logintips").append(tips);
																	}
																},
														"json");
													
												/*		
														
														}
												});
											}
										});
									*/
									
									
									
									
									});
								}
							,"json");
						}
					}
				});
			} else {
				var tips = "<span class='red'>账号不存在！</span>";
				$("#Logintips").empty();
				$("#Logintips").append(tips);
			}
		}
	});

}

function Save1() {
	var rs = Math.ceil(Math.random(Math.round(9.5)));
	
	var studentNo = $("#j_username").val();
	var password = $("#j_password").val();
	studentNo = $.trim(studentNo);
	$.ajax({
		url : "/getStudentByStudentNo.action",
		type : "post",
		async : false,
		dataType : "json",
		data : {
			"studentNo" : studentNo
		},
		success : function(accountData) {
			if (accountData.exist) {
				/*var studentId = accountData.studentId;
				$.ajax({
					url : "/getStudentState.action",
					type : "post",
					data : "studentId=" + studentId,
					async : false,
					dataType : "json",
					success : function(data) {
						if (data.openOrNot == 1) {
							alert("对不起，您未正常办理入学手续，不能登录！");
						} else {*/
				if (accountData.state == 1) {
					alert("对不起，您未正常办理入学手续，不能登录！");
				} else {
							$.ajax({
								url : "/Md5Data.action",
								type : "post",
								data : "password=" + password,
								async : false,
								dataType : "json",
								success : function(md5Password) {alert(md5Password.encryptionText);
									$.ajax({
										url : "/j_spring_security_check",
										type : "post",
										async : false,
										//dataType : "json",
										data : {
											j_username : studentNo,
											j_password : md5Password.encryptionText,
										},
										success : function() {
											$.ajax({
												url : "/getLoginMessage.action",
												type : "post",
												async : false,
												dataType : "json",
												data : {
												},
												success : function(msg) {alert(msg.hasLogined);
													if (msg.hasLogined == 1) {
														$.ajax({
															url : "/loginRecord.action",
															type : "post",
															async : false,
															dataType : "json",
															data : {},
															success : function() {alert(00);
																var url = $.cookie("sessionId");
																$.post("/removeloadinfo.action",{"url" : url},function(data) {},"json");
																window.location = "/home/student/student_manage.jsp";
															}
														});
													} else {
														var tips = "<span class='red'>密码错误！</span>";
														$("#Logintips").empty();
														$("#Logintips").append(tips);
													}
												}
											});
										}
									});
								}
							});
						}
					//}
				//});
			} else {
				var tips = "<span class='red'>账号不存在！</span>";
				$("#Logintips").empty();
				$("#Logintips").append(tips);
			}
		}
	});

}

function enterLogin() {
	document.onkeydown = function(event) {
		event = event ? event : window.event;
		if (event.keyCode == 13) {
			Save();
		}
	}
};

function clearTipInfo() {
	if ($('#j_username').val() == "学号") {
		$('#j_username').css('color', '#000000');
		$('#j_username').val("");
	}
}

function ifNull() {
	if ($('#j_username').val() == "") {
		$('#j_username').css('color', '#ccc');
		$('#j_username').val("学号");
	}
}

/*
//记住用户名密码
function Save() {
	var studentNo = $("#j_username").val();
	var password = $("#j_password").val();
	userId = $.trim(userId);
	$.ajax({
		url : "/getStudentByStudentNo.action",
		type : "post",
		async : false,
		dataType : "json",
		data : {
			"studentNo" : studentNo
		},
		success : function(accountData) {
			if (accountData.exist) {
				var studentId = accountData.studentId;
				$.ajax({
					url : "/getStudentState.action",
					type : "post",
					data : "studentId=" + studentId,
					async : false,
					dataType : "json",
					success : function(data) {
						if (data.openOrNot == 1) {
							alert("对不起，您未正常办理入学手续，不能登录！");
						} else {
							$.post("/Md5Data.action",{password : password},
								function(md5Password) {
									var data = {
										j_username : studentId,
										j_password : md5Password
									};
									$.post("/j_spring_security_check",data,
										function() {
											$.ajax({
												url : "/getEPath.action",
												type : "post",
												async : false,
												dataType : "json",
												success : function(data) {
													var url = data.ePath+ "/JSONPlogin.htm";
													$.ajax({
														type : "POST",
														dataType : "jsonp",
														crossDomain : true,
														async : false,
														url : url,
														data : {
															"username" : accountId,
															"password" : password
														},
														jsonpCallback : "success_jsonpCallback",
														success : function(data){
															$.post("/getIfLogin.action",{},
																function(msg) {
																	if (msg.success == "1") {
																		if ($("#auto_login").prop("checked")) {
																			$.cookie("remember_accountId","true",{expires : 7}); // 存储一个带7天期限的cookie
																			$.cookie("j_username",userId,{expires : 7});
																			$.cookie("j_password",password,{expires : 7});
																			$.cookie("auto_login","true",{expires : 7});
																		} else if ($("#remember_accountId").prop("checked")) {
																			$.cookie("remember_accountId","true",{expires : 7}); // 存储一个带7天期限的cookie
																			$.cookie("j_username",userId,{expires : 7});
																			$.cookie("j_password","",{expires : -1});
																			$.cookie("auto_login","false",{expires : -1});
																		} else {
																			$.cookie("remember_accountId","false",{expire : -1});
																			$.cookie("j_username","",{expires : -1});
																			$.cookie("j_password","",{expires : -1});
																			$.cookie("auto_login","false",{expires : -1});
																		}
																		var url = $.cookie("sessionId");
																		$.post("/removeloadinfo.action",{"url" : url},function(data) {},"json");
																		$.post("/getLoginMessage.action",{},
																			function(login_data) {
																				if (login_data.accountId != "0") {
																					login_accountId = login_data.accountId;
																					if (login_data.userId != "0") {
																						userOrClub = 1;
																					} else {
																						userOrClub = 2;
																					}
																					if (login_data.friendOrCoach == "1") {
																						friendOrCoach = 1;
																					}
																				}
																				if (userOrClub == 1) {
																					window.location = "/home/member_Manage/member_manage.jsp?type="+ friendOrCoach+ "&flag="+ userOrClub;
																				} else {
																					window.location = "/home/club/club_manage.jsp?flag="+ userOrClub;
																				}
																			},
																		"json");
																	} else {
																		var tips = "<span class='red'>密码错误！</span>";
																		$("#Logintips").empty();
																		$("#Logintips").append(tips);
																	}
																},
														"json");
													}
												});
											}
										});
									});
								}
							);
						}
					}
				});
			} else {
				var tips = "<span class='red'>账号不存在！</span>";
				$("#Logintips").empty();
				$("#Logintips").append(tips);
			}
		}
	});

}
*/