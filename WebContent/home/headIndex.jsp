<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- <%@include file="/home/keywords.jsp"%> --%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="/js/headAndFoot/headIndex.js" ></script>
<style type="text/css">
.dalei-box {
	width: 300px;
	height: auto;
	line-height: 30px;
	line-height: 25px;
	margin: -2px;
	padding: 15px;
	position: absolute;
	left: 198px;
	top: 80px;
	z-index: 3;
	border: 2px solid #A32322;
	border-left: none;
	background: url(/img/line-red1.jpg) no-repeat 0 0;
	background-color: #FFF;
}

.menu-l {
	width: 200px;
	height: 40px;
	line-height: 40px;
	float: left;
	text-align: left;
	vertical-align: middle;
	position: relative;
	z-index: 1;
	background-color: #FFF;
	background: none;
	margin: 0;
}

.menu-l h1:hover {
	background: none;
	color: #FFF;
}

.dalei-box gray1 {
	position: relative;
	z-index: 1000;
}

.rslides_tabs {
	clear: both;
	left: 240;
	position: absolute;
	text-align: center;
	z-index: 0;
}
</style>
<!--首行开始-->
<script type="text/javascript">
	var clr = "${param.clr}";
	if(clr==1){
		logout();
	}
</script>
<div class="h-top gray1" id="logined" style="display: none;">
	<div class="h-box">
		<div class="h-box-l">
			<span class="red1" id="accountId"></span><span>您好，欢迎您来到去健身网！</span>
		</div>
		<div class="h-box-r">
			<ul id="headUnique">
				<li><a href="javascript:logout();">退出登录</a></li>
				<li><a href="javascript:AddFavorite()">加入收藏</a>|</li>
				<li><a href="javascript:toPay(1)">健身钱包</a>|</li>
				<li><a href="javascript:toMyZoneUnique();">我的空间</a>|</li>
				 <li><a href="javascript:mymsg()">我的消息(<span id = "msgcount"></span>)</a>|</li></ul>
			</ul>
		</div>
	</div>
</div>
<!--首行结束-->

<!--首行2开始-->
<div class="h-top gray1" style="display: none;" id="notLogined">
	<div class="h-box">
		<div class="h-box-l">
			<span id="date"></span><span id="time"></span><span id="week"></span>
		</div>
		<div class="h-box-r">
			<ul>
				<li><a href="javascript:AddFavorite()">加入收藏</a>|</li>
				<li><a href="javascript:toPay(0)">健身钱包</a>|</li>
				<li><a href="/home/login/password.jsp">找回密码</a>|</li>
                <li><a href="/home/register/registerClub.jsp">俱乐部注册</a>|</li>
				<li><a href="/home/register/registerUser.jsp">用户注册</a>|</li>
                <li><a href="javascript:memeberLogin();">会员登录</a>|</li>
			</ul>
		</div>
	</div>
</div>
<!--首行2结束-->



<!--菜单行开始-->
<div class="menu">

	<div class="menu-l">
		<div>
			<h1 class="white">热门运动项目</h1>
		</div>
		<!--类盒子开始-->
		<div class="menu-lei" id="hotSports">
			<!--热门开始-->
			<div class="xiaolei red1" id="hotSport"></div>
			<!--热门结束-->
			<!--大类开始-->
			<div class="dalei">
				<ul class="gray3" id="sportsCategory">
				</ul>
			</div>
			<!--大类结束-->
		</div>
		<!--类盒子结束-->
		<!--对应小类弹出层开始-->

		<!--对应小类弹出层结束-->
	</div>

	<!--菜单开始-->
	<div class="menu-r white2">
		<a  id="a1" onclick="xznum(1)" style="cursor:pointer;">首页</a>
      <a  id="a2"  onclick="xznum(2)" style="cursor:pointer;">健身卡</a>
      <a id="a3" onclick="xznum(3)" style="cursor:pointer;">课程</a>
      <a id="a4"  onclick="xznum(4)" style="cursor:pointer;">场地</a>
      <a id="a5" onclick="xznum(5)" style="cursor:pointer;">活动</a>
      <a id="a6" onclick="xznum(6)" style="cursor:pointer;">教练</a>
      <a id="a7" onclick="xznum(7)" style="cursor:pointer;">健友</a>
      <a id="a8" onclick="xznum(8)" style="cursor:pointer;">俱乐部</a>
      <a id="a9" onclick="xznum(9)" style="cursor:pointer;">圈子</a>
      <a id="a10" onclick="xznum(10)" style="cursor:pointer;">知识</a>
      <a id="a11" onclick="xznum(11)" style="cursor:pointer;">视频</a>
	</div>
	<!--菜单结束-->
	<form>
		<input type="hidden" id="j_username"></input> <input type="hidden"
			id="j_password"></input> <input type="hidden" id="url"
			value="${url }"></input>
	</form>

</div>
<!--菜单行结束-->





