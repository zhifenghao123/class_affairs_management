<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      	<%@include file="/admin/included.jsp" %>
        <link rel="stylesheet" type="text/css" href="/css/admin/base.css" />
        <title>
        </title>
        <script type="text/javascript">
        	var executiveClassId = '${param.id}';
          	var submitForm = function($dialog, $datagrid, $pjquery) {
	        	if ($('form').form('validate')) {
	            	$pjquery.messager.progress({ text: '数据提交中...', interval: 100});
	             	var url, checked = [];
	              	if (executiveClassId.length > 0) {
	                	url = '/admin/updateExecutiveClass.action';
	              	} else {
	                	url = '/admin/addExecutiveClass.action';
	              	}
	              	$('input:checkbox:checked').each(function() {
	                	checked.push($(this).val());
	              });
	              	var data;
	              	if($("#monitorStudentNo").val() != "班长学号" && $("#monitorName").val() != "该用户不存在！"){
	            	  data = {
	      	                executiveClassId: executiveClassId,
	      	                gradeNo:$("#gradeNo").val(),
	      	                schoolNo:$("#schoolNo").val(),
	      	                name: $("#name").val(),
	      	                monitorStudentNo:$("#monitorStudentNo").val(),
	      	                monitorName:$("#monitorName").val(),
	      	                executiveClassGroup:$("#executiveClassGroup").val(),
	      	              };
	              } else{
	            	  data = {
	      	                executiveClassId: executiveClassId,
	      	                gradeNo:$("#gradeNo").val(),
	      	                schoolNo:$("#schoolNo").val(),
	      	                name: $("#name").val(),
	      	                executiveClassGroup:$("#executiveClassGroup").val(),
	      	              };
	              } 
	              
	              $.post(url, data,
	              	function(result) {
	            		$pjquery.messager.progress('close');
	                	if (result.exsit) {
	                  		parent.$.messager.alert('提示', '专业名称已存在', 'info');
	                	} else {
	                  	if (result.success) {
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
	                }
	              },
	              'json');
	            }
          };
          $(function() {
        	 /*  $.post("/admin/getAllSchoolList.action", "",
                		function(data) {
                 			 $.each(data,function(index, value) {
                    			//$("#schoolNo").append("<li><input  type='checkbox' value='" + value.authorityId + "' />" +'&nbsp;&nbsp;'+  value.name + "</li>");
                    			$("#schoolNo").append("<option value ="+value.schoolNo+">"+value.schoolNo+"   "+value.name+"</option>");
                  		});
                		},
                "json");
        	  
          	if (executiveClassId.length > 0) {
              $.post("/admin/getExecutiveClassById.action", {
                id: executiveClassId
              },
              function(result) {
                  $("#executiveClassNo").val(result.executiveClassNo);
                  $("#name").val(result.name);
                  $("#schoolNo").val(result.schoolNo);
                  $("#executiveClassGroup").val(result.executiveClassGroup);       
              },
              'json');
            } */
        	  $.ajax({
      			url : "/admin/getAllSchoolList.action",
      			type : "post",
      			async : false,
      			dataType : "json",
      			data : {},
      			success : function(allSchool) {
      				$.each(allSchool,function(index, value) {
                			$("#schoolNo").append("<option value ="+value.schoolNo+">" + value.name + "</option>");
              		});
      			}
      		});
            $.ajax({
				url : "/admin/getAllGradeList.action",
				type : "post",
				async : false,
				dataType : "json",
				data : {},
				success : function(allGrade) {
					 $.each(allGrade,function(index, value) {
	           			$("#gradeNo").append("<option value ="+value.gradeNo+">" + value.gradeNo + "</option>");
	         		});
				}
			});
            
            if (executiveClassId.length > 0) {
            	$.ajax({
          			url : "/admin/getExecutiveClassById.action",
          			type : "post",
          			async : false,
          			dataType : "json",
          			data : {"id": executiveClassId},
          			success : function(result) {
          				 $("#executiveClassNo").val(result.executiveClassNo);
                         $("#name").val(result.name);
                         $("#gradeNo").val(result.gradeNo);
                         $("#schoolNo").val(result.schoolNo);
                         $("#executiveClassGroup").val(result.executiveClassGroup); 
                         if(result.monitorStudentNo != null && monitorStudentNo !=""){
                        	 $("#monitorStudentNo").val(result.monitorStudentNo); 
                        	 $("#monitorName").val(result.monitorName);
                         }
          			}
          		});
              }
            
          });
          
          
          
          
          function clearTipInfo() {
        		if ($('#monitorStudentNo').val() == "班长学号") {
        			$('#monitorStudentNo').css('color', '#000000');
        			$('#monitorStudentNo').val("");
        		}
        	}

        	function ifNull() {
        		if ($('#monitorStudentNo').val() == "") {
        			$('#monitorStudentNo').css('color', '#ccc');
        			$('#monitorStudentNo').val("班长学号");
        		}else{
        			$.post("/student/getStudentByStudentNo.action",{"studentNo":$('#monitorStudentNo').val(),"infoType":0},function(data){
        				if(data.exist){
        					$("#monitorName").val(data.name);
        			    }else{
        			    	$("#monitorName").val("该用户不存在！");
        				}		
        			},"json");
        		}
        	}
        </script>
    </head>
    
    <body>
      <div class="addForm">
        <div class="formbox">
          <form>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="150" class="tdleft gray3">
                  行政班级名称：
                </td>
                <td colspan="2">
                  <span>
                    <input id="name" type="text" class="putin1 black easyui-validatebox" required="true"
                    validType="length[1,10]" style="width:230px;" />
                  </span>
                </td>
              </tr>
                <tr>
                <td width="150" class="tdleft gray3">
                年级：
                </td>
                <td colspan="2">
                  <label>
                    <select class="select1 black" id="gradeNo" style="width:237px;">
                     <!--  <option value="1">
                        
                      </option> -->

                    </select>
                  </label>
              </tr>
              <tr>
                <td width="150" class="tdleft gray3">
                  所在学院：
                </td>
                <td colspan="2">
                  <label>
                    <select class="select1 black" id="schoolNo" style="width:237px;">
                     <!--  <option value="1">
                        
                      </option> -->

                    </select>
                  </label>
              </tr>
              <tr>
              <td width="150" class="tdleft gray3">
                  学院管理组号：
                </td>
                <td colspan="2"> 
                  <span>
                    <input id="executiveClassGroup" type="text" class="putin1 black easyui-validatebox" required="true"
                    validType="length[1,10]" style="width:230px;" value="1"/>
                  </span>
                </td>
              </tr>
              <tr>
                <td width="150" class="tdleft gray3">
                  班长：
                </td>
                <td colspan="2">
                  <span>
                    <input id="monitorStudentNo" type="text" class="putin1 black" required="true" value="班长学号" onfocus="clearTipInfo()" onblur="ifNull()" 
                    validType="length[1,15]" style="width:230px;" />
                    <input id="monitorName" type="text" class="putin1 black" style="width: 150px;border:none;" />
                  </span>
                </td>
              </tr>
            </table>
          </form>
        </div>
      </div>
    </body>
  
  </html>