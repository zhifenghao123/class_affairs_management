//在各自的页面添加即可实现登录后调回上一个页面，注意url的截取范围，最后从后面截取
//var url = window.location.href;
//url = url.substring(url.length-19,url.length);
var userOrClub;
//friend:1
//coach = 0;
var friendOrCoach = 2;
$(function(){
	$.post("/user/getIfLogin.action",{},function(data){
		alert(data.logined);
		if(data.logined){
			$("#logined").show();
		}else{
			$("#notLogined").show();
		}
	},"json");
	//判断是否已经登陆过
	ifLoginAndGetMsg();
	 //结束实现自动登录

});

function ifLoginAndGetMsg(){alert(333);
	// 判断是否已经登陆过
	$.post("/user/getIfLogin.action",{},function(data){
		alert(data.logined);
		if(data.logined){
			$("#logined").show();
		}else{
			$("#notLogined").show();
		}
	},"json");
}


function memeberLogin(){
	window.location = "/toLogin.action";
}

function showTime(){
		var now = new Date();
		var year = ((now.getFullYear()>0&&now.getFullYear()<2000)?1900:0)+now.getFullYear();// + 1900;
		var month = ((now.getMonth()>=0&&now.getMonth()<9)?'0':'') +(now.getMonth() + 1);
		var day = ((now.getDate()<10&&now.getDate()>0)?'0':'') +(now.getDate());
		var hour = ((now.getHours()&&now.getHours()<10)?'0':'')+now.getHours();
		var minute = ((now.getMinutes()&&now.getMinutes()<10)?'0':'')+now.getMinutes();
		var second = ((now.getSeconds()&&now.getSeconds()<10)?'0':'')+now.getSeconds();
		var week = '星期'+'日一二三四五六'.charAt(new Date().getDay());
		var date = year+'年';
		date += month+'月';
		date += day+'日 ';
		var time = hour+':';
		time += minute+':';
		time += second;
		$('#date').html(date);
		$('#time').html(time);
		$('#week').html(week);
	}
	setInterval("showTime()",1000);
	
	function showHotSports(){
		$('#hotSports').toggle();
	}
	
	function showSport(code){
		var top = 61 + (code - 1) * 32 + "px";
		$('#'+code).show();
		$('#'+code).css('top',top);
	}
	
	function hideSport(code){
		$('#'+code).hide();
	}
	
	function showSportSmall(code){
		$("#"+code).show();
	}
	function hideSportSmall(code){
		$("#"+code).hide();
	}
	
	function getSportsTypeByCode(code){
		$.post("/getSportsTypeByCode.action",{"code" : code},function(data){
			$(data).each(function(index,sport){
				$("#"+code).append($("<a href='javascript:pageForSport("+sport.id+");'>"+sport.name+"</a>"));
				if((index+1)%6==0){
					$("#"+code).append("<br/>");
				}
			});
		},"json");
	}
	
	function logout(){
		$.ajax({url:"/j_spring_security_logout",async:false,success:function(data){},dataType:"json"});
		$.ajax({
			url:"/getEPath.action",
			type:"post",
			async:false,
			dataType:"json",
			success:function(data){
				var url = data.ePath+"/JSONPloginOut.htm";
				$.ajax({
					type: "POST",
					dataType:"jsonp",
					crossDomain:true,
					async: false,
					url: url,
					data: {},
					jsonpCallback:"success_jsonpCallback",
					success: function(data){
						if(data.result){
							clearCookie();
							$("#notLogined").show();
							$("#logined").hide();
							window.location='/';
						}
					}
				});
			}
		});
	}
	function clearCookie(){ 
//		document.cookie='auto_login=0;expires=' + new Date(0).toUTCString()+';path=/';
//		document.cookie='remember_accountId=0;expires=' + new Date(0).toUTCString()+';path=/';
//		document.cookie='j_username=0;expires=' + new Date(0).toUTCString()+';path=/';
//		document.cookie='j_password=0;expires=' + new Date(0).toUTCString()+';path=/';
		if($.cookie('remember_accountId')){
			document.cookie='auto_login=0;expires=' + new Date(0).toUTCString()+';path=/';
			document.cookie='j_password=0;expires=' + new Date(0).toUTCString()+';path=/';
		}
		else{
			document.cookie='auto_login=0;expires=' + new Date(0).toUTCString()+';path=/';
			//document.cookie='remember_accountId=0;expires=' + new Date(0).toUTCString()+';path=/';
			document.cookie='j_username=0;expires=' + new Date(0).toUTCString()+';path=/';
			document.cookie='j_password=0;expires=' + new Date(0).toUTCString()+';path=/';
		}
	}
	function toMyZoneUnique(){
		$.post("/getLoginMessage.action",{},function(msg){						
			if(msg.userId != "0"){
				window.location = "/home/member_Manage/member_manage.jsp?type="+msg.friendOrCoach;
			}else{
				window.location = "/home/club/club_manage.jsp";
			}
		},"json");	
	}
	function pageForSport(sportsId){
		window.open("/home/sports.jsp?sportsId="+sportsId);
	}
	function AddFavorite()  
	{
	     if (document.all){  
	    	 window.external.addFavorite(window.location, document.title);
	     }else {
	    	 alertMessage("请通过快捷键 Ctrl + D 加入到收藏夹");
	     }  
	}
	function showCity(){
		$("#headIndexCity").show();
		$("#headIndexCity").mouseover(function(){
			$("#headIndexCity").show();
		});
		$("#headIndexCity").mouseout(function(){
			$("#headIndexCity").hide();
		});
	}
	function hideCity(){
		$("#headIndexCity").hide();
	}
	function xznum(num){
		switch(num){
			case 1:
				window.location.href="/";
				break;
			case 2:
				window.location.href="/home/card/homePage_cardList.jsp";
				break;
			case 3:
				window.location.href="/home/course/homePage_courseList.jsp";
				break;
			case 4:
				window.location.href="/home/site/homePageSiteList.jsp";
				break;
			case 5:
				//window.location.href="/home/activity/homePageActivity.jsp";
				window.location=ePath+"/home/activity/getActivityPageList.jsp";
				break;
			case 6:
				window.location.href="/home/member_Manage/homepage_coachlist.jsp?type=2";
				break;
			case 7:
				window.location.href="/home/member_Manage/homepage_coachlist.jsp?type=1";
				break;
			case 8:
				window.location.href="/home/club/clublist.jsp";
				break;
			case 9:
				window.location.href="/home/groups/frontpage_groupsList.jsp";
				break;
			case 10:
				window.location.href="/home/article/homePage_articleList.jsp";
				break;
			case 11:
				window.location.href="/home/video/videolist_front.jsp";
				break;
		}
			
	}
	function alertMessage(content){
		var tipsContent="<span>"+content+"</span>";
		$("#tipsContent").append(tipsContent);
		openRemindDiv();
	}
	function mymsg() {
		if (userOrClub == "1") {// 健友or教练
			$.ajax({
				url : "/getLoginMessage.action",
				async : false,
				success : function(login_data) {
					if (login_data.accountId != "0") {
						login_accountId = login_data.accountId;
						if (login_data.friendOrCoach == "1") {
							friendOrCoach = 1;
						}
					}
				},
				dataType : "json"
			});
			window.location = "/home/member_Manage/myMessage_more.jsp?type="+ friendOrCoach;
		} else {// 俱乐部
			window.location = "/home/club/zone_news.jsp?";
		}
	}
	
	function toPay(index){
		$.post("/getPayPath.action",{},function(data){
			//window.location=data.ePath+"/home/order/personOrderList.jsp?type="+userOrCoach;
			if(index==0){
				window.location.href=data.payPath+"/home/login/login.jsp";
			}else{
				$.post("/toPayLogin.action",
					function (dataInfo){
						if(dataInfo.success==1){
							window.location.href=data.payPath+"/home/login/login.jsp?flag=1&accountId="+dataInfo.accountId+"&password="+dataInfo.password+"&sessionId="+dataInfo.sessionId;
						}else{
							window.location.href=data.payPath+"/home/login/login.jsp";
						}
					},"json");
			}
			
		},"json");
		
	}