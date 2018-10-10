<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
 <script src="/common/bootstrap-3.3.7/js/bootstrap-dropdown.min.js"></script>
 
 <script type="text/javascript" src="/common/layer/layer.js"></script>
 
<script type="text/javascript" src="/js/home/headAndFoot/head.js"></script>
	 <nav class="navbar navbar-inverse navbar-fixed-top bootstrap-admin-navbar bootstrap-admin-navbar-under-small" role="navigation">
     	<div class="container">
        	<div class="row">
            	<div class="col-lg-12">
                	<div class="collapse navbar-collapse main-navbar-collapse">
                    	<a class="navbar-brand" href="javascript:void(0);" onclick="aboutSystem();"><strong>研究生班级事务系统</strong></a>
                        <ul class="nav navbar-nav navbar-right">
                            <li class="dropdown">
                                <a href="#" role="button" class="dropdown-toggle" data-hover="dropdown"> 
	                                <i class="glyphicon glyphicon-user"></i> 欢迎您， <span id="userName"></span>
	                                <i class="caret"></i>
                                </a>
                            	<ul class="dropdown-menu">
                                	<!-- <li><a href="#updateinfo" data-toggle="modal">个人资料</a></li>
                                    <li role="presentation" class="divider"></li> -->
                                    <li><a  onclick="updatePassword()" data-toggle="modal">修改密码</a></li>
                                    <li role="presentation" class="divider"></li>
                                    <!-- href="#identifier"  来指定要切换的特定的模态框（带有 id="identifier"）。-->  
                                    <li><a href="javascript:logout();">退出</a></li>
                                </ul>  
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
	</nav>
