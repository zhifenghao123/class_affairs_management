<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN" class="ax-vertical-centered">
<head>
<meta charset="UTF-8">
<title>研究生班级事务系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/common/bootstrap-3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="/common/bootstrap-3.3.7/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="/common/bootstrap-3.3.7/css/bootstrap-admin-theme.css">
<script src="/common/jquery/jquery.min.js"></script>
<script src="/common/bootstrap-3.3.7/js/bootstrap.min.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	introduce(1);
});
function introduce(type){
	var detailInfoUrl = "";
	switch (type){
	case 1:
		$("#name").html("系统简介");
		detailInfoUrl = "/aboutSystem/system.jsp";
		break;
	case 2:
		$("#name").html("功能实现");
		detailInfoUrl = "/aboutSystem/function.jsp";
		break;
	case 3:
		$("#name").html("开发环境");
		detailInfoUrl = "/aboutSystem/ide.jsp";
		break;
	case 4:
		$("#name").html("技术实现");
		detailInfoUrl = "/aboutSystem/technologyimplementation.jsp";
		break;
	case 5:
		$("#name").html("数据开放接口API");
		detailInfoUrl = "/aboutSystem/datapublic_api.jsp";
		break;
}
	
	$.ajax({
		  url: detailInfoUrl,
		  data:{
			  
		  },
		  cache: false,
		  success: function(data){
			  $("#detailinfo").empty();
		    $("#detailinfo").append(data);
		  },
		  dataType:"html"
	});
}
</script>
</head>
<!-- bootstrap-4.0.0-beta-dist -->
<body class="bootstrap-admin-with-small-navbar">
	<!-- 头部开始 -->
     <nav class="navbar navbar-inverse navbar-fixed-top bootstrap-admin-navbar bootstrap-admin-navbar-under-small" role="navigation">
     	<div class="container">
        	<div class="row">
            	<div class="col-lg-12">
                	<div class="collapse navbar-collapse main-navbar-collapse">
                    	<a class="navbar-brand"><strong>研究生班级事务系统</strong></a>
                    </div>
                </div>
            </div>
        </div>
	</nav>
    <!-- 头部结束-->
    <!-- 中间内容开始 -->
    <div class="container">
		<!-- left, vertical navbar & content -->
        <div class="row">
            <!-- 中间左侧菜单导航开始   left, vertical navbar -->
			<div class="col-md-2 bootstrap-admin-col-left">
                <ul class="nav navbar-collapse collapse bootstrap-admin-navbar-side">
                    <li>
                        <a href="javascript:void(0);" onclick="introduce(1);">
                        	<i class="glyphicon glyphicon-chevron-right"></i>系统简介
                        </a>
                    </li>
                     <li>
                        <a href="javascript:void(0);" onclick="introduce(2);">
                        	<i class="glyphicon glyphicon-chevron-right"></i>功能简介
                        </a>
                    </li>
                     <li>
                        <a href="javascript:void(0);" onclick="introduce(3);">
                        	<i class="glyphicon glyphicon-chevron-right"></i>开发环境
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0);" onclick="introduce(4);">
                        	<i class="glyphicon glyphicon-chevron-right"></i>技术实现
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0);" onclick="introduce(5);">
                        	<i class="glyphicon glyphicon-chevron-right"></i>数据开放接口API
                        </a>
                    </li>
                </ul>	      
            </div>
			 <!-- 中间左侧菜单导航结束 -->
            <!-- content -->
            <div class="col-md-10"  style="height:550px;">
				<div class="row">
                	<div class="col-md-15">
                    	<div class="panel panel-default">
                        	<div class="panel-heading">
                            	<div class="text-muted bootstrap-admin-box-title">
									<span id="name"></span>
								</div>
                            </div>
                            <div class="bootstrap-admin-panel-content" style="height:100%;" id="detailinfo">
                                <!-- <ul>
                                    <li>查看并导出班级所有同学的名单</li>
                                    <li>查看班级公开社交权限的社交账号</li>
                                </ul> -->
                            </div>
                        </div>
                    </div>
                </div>
        	</div>
    	</div>
	</div>
    
</body>

</html>