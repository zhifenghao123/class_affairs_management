<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      	<%@include file="/admin/included.jsp" %>
        <link rel="stylesheet" type="text/css" href="/css/admin/base.css" />
        <script src="/common/jquery/jquery.min.js" charset="UTF-8" type="text/javascript"></script>
        <script src="/common/ajaxfileupload/ajaxfileupload.js" type="text/javascript"></script>
        <title>
        </title>
       <!--  <script type="text/javascript">
        <script src="jquery-1.7.1.js" type="text/javascript"></script>
        <script src="ajaxfileupload.js" type="text/javascript"></script> -->
        <script type="text/javascript">
        var uploadedFile = "";
            $(function () {
               /*  $(":button").click(function () {
                    ajaxFileUpload();
                }); */
                getAllSchoolList();
            });
			
            function getAllSchoolList(){
            	$.post("/admin/getAllSchoolList.action", "",
                  		function(data) {
                   			 $.each(data,function(index, value) {
                      			//$("#schoolNo").append("<li><input  type='checkbox' value='" + value.authorityId + "' />" +'&nbsp;&nbsp;'+  value.name + "</li>");
                      			$("#schoolNo").append("<option value ="+value.schoolNo+">"+value.schoolNo+"   "+value.name+"</option>");
                    		});
                  		},
                  "json");
            }
            
            function uploadFile() {
            	var file = $("#file").val();
            	var now = new Date();
                var year = now.getFullYear();
                var month =(now.getMonth() + 1).toString();
                var day = (now.getDate()).toString();
                var hour = (now.getHours()).toString();
                var minute = (now.getMinutes()).toString();
                var second = (now.getSeconds()).toString();
                if (month.length == 1) {
                    month = "0" + month;
                }
                if (day.length == 1) {
                    day = "0" + day;
                }
                if (hour.length == 1) {
               	 hour = "0" + hour;
                }
                if (minute.length == 1) {
               	 minute = "0" + minute;
                }
                if (second.length == 1) {
               	 second = "0" + second;
                }
                var obId = year + month +  day+ hour +  minute+ second;
            	if(file == ""){
            		alert("请选要批量导入的信息文件！");
            	}else{
	               /*  $.ajaxFileUpload({
						url: '/uploadServlet?type=1&objectId='+obId, //用于文件上传的服务器端请求地址
	                    secureuri: false, //是否需要安全协议，一般设置为false
	                    fileElementId: 'file', //文件上传域的ID
	                    dataType: 'json', //返回值类型 一般设置为json
	                    success: function (data, status){ //服务器成功响应处理函数
	                       // $("#img1").attr("alt", data.filename);
	                    	uploadedFile = data.ServerFileRelativePath;
	                         $("#file").hide();
	                         $("#submit").hide();
	                        
	                         var uploadedFileDisplay = "<div><ul>";
	                         uploadedFileDisplay = uploadedFileDisplay+"<li><div >" + data.filename + "</div><input type='hidden' name='photoArray' value='" + data.ServerFileRelativePath + "'/><a class='now'>删除</a></li>";
	                         uploadedFileDisplay = uploadedFileDisplay + "</ul></div>";
	                         $("#fileUploaded").innerHTML = "hehda";//alert( $("#fileUploaded").name());
	                         $("#fileUploaded").append(uploadedFileDisplay);
	                         $("#fileUploaded li a").click(function(){
	                 			var deleteFile = $(this).parent().find("input").attr("value");
	                 			deleteFile = deleteFile.substring(deleteFile.indexOf("upload"));
	                 			
	                 			var data  = {
	                 					"resourcePath" : deleteFile,
	                 			};
	                 			$.post("/deleteResource.action",data,function(data){
	                 				if(data.msg==true){//alert($(this).parent());
	                 					uploadedFile = "";
	                 					$(this).parent().remove();
	                 				}
	                 			},"json");
	                 		});
	                    },
	                    error: function (data, status, e){//服务器响应失败处理函数
	                        alert(e);
	                     }
	                 }); */
	                 
            		  $.ajaxFileUpload({
            				url: '/uploadServlet?type=4&objectId='+obId, //用于文件上传的服务器端请求地址
            	            secureuri: false, //是否需要安全协议，一般设置为false
            	            fileElementId: 'file', //文件上传域的ID
            	            dataType: 'json', //返回值类型 一般设置为json
            	            success: function (data, status){ //服务器成功响应处理函数
            	               // $("#img1").attr("alt", data.filename);
            	            	//uploadedFile = data.ServerFileRelativePath;
            	            	 var dataImport = {
            	            			 graduationProjectGroup: $('#graduationProjectGroup').val(),
            	            			 graduationProjectBatchAddFile: data.ServerFileRelativePath,
            	             	      };
            	             	      $.post("/admin/batchAddGraduationProject.action",dataImport,
            	             	    		function(result) {
            	             	            	if (result.msg) {
            	             	                  	alert("毕业设计信息导入成功！");
            	             	                  	//window.location="/student/studentScoreList.action";
            	             	                 } else {
            	             	                	alert("毕业设计信息导入失败！");
            	             	                }
            	             	          },'json');
            	            },
            	            error: function (data, status, e){//服务器响应失败处理函数
            	                alert(e);
            	             }
            	         });
            	}
            }
            
      /*       var submitForm = function($dialog, $datagrid, $pjquery) {
            	//if ($('form').form('validate')) {
            		$pjquery.messager.progress({ text: '数据提交中...', interval: 100});
            		 var data = {
            			displayname: $('#name').val(),
            	     	studentBatchAddFile: uploadedFile,
            	      };
            	      $.post("/admin/batchAddStudent.action",data,
            	    		function(result) {
            	            	$pjquery.messager.progress('close');
            	                if (result.msg) {
            	                  	//parent.$.messager.alert('提示', '名称已存在', 'info');
            	                	$datagrid.datagrid('load');
            	                	$dialog.dialog('close');
	           	                   	$pjquery.messager.show({
	           	                    	title: '提示信息',
	           	                     	msg: '操作成功',
	           	                      	timeout: 2000
	           	                    });
            	                } else {
            	                   	parent.$.messager.alert('提示', '操作失败', 'error');
            	                }
            	          },'json');
            	//}
            }; */
        </script>
    </head>
    
	<body>
		<div class="addForm">
        	<div class="formbox">
         		<form>
           			<table width="100%" border="0" cellspacing="0" cellpadding="0">
           			      <tr>
               				<td width="50" class="tdleft gray3">
                  				毕业设计课题组名：
                			</td>
               				<td colspan="5">
                  				<!-- <label> -->
                    				<!-- <select class="select1 black" id="schoolNo" style="width:237px;" onchange = "getExecutiveClassesBySchoolNo($(this).val())"> -->
                     				<input type="text"  style="width:237px;" id="graduationProjectGroup" value="" />
                    			<!-- 	</select>
                  				</label> -->
                  			</td>
              			</tr>
              		<!-- 	<tr>
               				<td width="77" class="tdleft gray3">
	                 			<p><input type="file" id="file" name="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel"/></p>
							    <input type="button" id="submit" value="上传" onclick="uploadFile()"/>
							    <p><img id="img1" alt="上传成功啦" src="" /></p>
               				 </td>
             			</tr> -->
             			<tr>
               				<td width="50" class="tdleft gray3">
	                 			<input type="file" id="file" name="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel"/>						   
               				 </td>
               				 <td >
							    <input type="button" id="submit" value="上传文件 " onclick="uploadFile()"/>
               				 </td>
             			</tr>
             			<tr>
             				<td></td>
               				 <td >
							   <!--  <img id="img1" alt="还未上传批量导入的信息文件！" src="" /> -->
							   <div id="fileUploaded" name="fileupload"></div>
							   <span id="fileUploadedTip"></span>
               				 </td>
             			</tr>
                        
            </table>
          </form>
        </div>
      </div>
    </body>
  
  </html>