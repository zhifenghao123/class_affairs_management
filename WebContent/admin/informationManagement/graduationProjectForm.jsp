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
        	var graduationProjectId = '${param.graduationProjectId}';
          	var submitForm = function($dialog, $datagrid, $pjquery) {
	        	if ($('form').form('validate')) {
	            	$pjquery.messager.progress({ text: '数据提交中...', interval: 100});
	             	var url, checked = [];
	              	if (graduationProjectId.length > 0) {
	                	url = '/admin/updateGraduationProject.action';
	              	} else {
	                	url = '/admin/addGraduationProject.action';
	              	}
	              	$('input:checkbox:checked').each(function() {
	                	checked.push($(this).val());
	              });
	              var data = {
	                graduationProjectId: graduationProjectId,
	                graduationProjectGroup: $("#graduationProjectGroup").val(),
	                studentNo: $("#studentNo").val(),
	                studentName: $("#studentName").val(),
	                reseachTitle: $("#reseachTitle").val(),
	                topicSelectingReportScore: $("#topicSelectingReportScore").val(),
	                guideTeacher: $("#guideTeacher").val(),
	              };
	              $.post(url, data,
	              	function(result) {
	            		$pjquery.messager.progress('close');
	            
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
	              },
	              'json');
	            }
          };
          $(function() {
        	  infoPreparations();
        	  if(graduationProjectId.length > 0){
        	  $.ajax({
        			url : "/admin/getGraduationProjectById.action",
        			type : "post",
        			async : false,
        			dataType : "json",
        			data : {
        				graduationProjectId: graduationProjectId
        			},
        			success : function(result) {
		                if (result.exist) {
		                  $("#graduationProjectGroup").val(result.graduationProjectGroup);
		                  $("#studentNo").val(result.studentNo);
		                  $("#studentName").val(result.studentName);
		                  $("#reseachTitle").val(result.reseachTitle);
		                  $("#topicSelectingReportScore").val(result.topicSelectingReportScore);
		                  $("#guideTeacher").val(result.guideTeacher);
		                 
		               }
              		}
        	  });
        	  }

          });
          
          function infoPreparations(){
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
        	}
          
          function getExecutiveClassListBySchoolNo(schoolNo,gradeNo){
        		$.ajax({
        			url : "/admin/getExecutiveClassesBySchoolNo.action",
        			type : "post",
        			async : false,
        			dataType : "json",
        			data : {"gradeNo":gradeNo,
        				"schoolNo":schoolNo
        				},
        			success : function(allExecutiveClass) {
        				$.each(allExecutiveClass,function(index, value) {
        	      			$("#executiveClassNo").append("<option value ="+value.name+">" + value.name + "</option>");
        	    		});
        			}
        		});
        	}
          
          function getMajorListBySchoolNo(schoolNo){
        		$.ajax({
        			url : "/admin/getMajorsBySchoolNo.action",
        			type : "post",
        			async : false,
        			dataType : "json",
        			data : {"schoolNo":schoolNo},
        			success : function(allMajor) {
        				$.each(allMajor,function(index, value) {
        	      			$("#majorNo").append("<option value ="+value.majorNo+">" + value.name + "</option>");
        	    		});
        			}
        		});
        	}
          
          function changeMajorAndExcutiveClassBySelectedSchool(selectedSchoolNo){
         	 $("#majorNo option").remove();
         	 $("#executiveClassNo option").remove();
         	// $("#majorNo").append("<option value =''>— — —</option>");
         	 $.post("/admin/getMajorsBySchoolNo.action", {schoolNo:selectedSchoolNo},
                  	function(data) {
                           $.each(data,function(index, value) {
                              $("#majorNo").append("<option value ="+value.majorNo+">"+value.majorNo+"   "+value.name+"</option>");
                           });
                       },
                  "json");
         	// $("#executiveClassNo").append("<option value =''>— — —</option>");
         	 $.post("/admin/getExecutiveClassesBySchoolNo.action", {gradeNo:$("#gradeNo").val(),schoolNo:selectedSchoolNo},
                  	function(data) {
                           $.each(data,function(index, value) {
                              $("#executiveClassNo").append("<option value ="+value.name+">"+value.name+"</option>");
                           });
                       },
                  "json");

          } 
          
          function changeExcutiveClassBySelectedGrade(selectedGradeNo){
         	 $("#executiveClassNo option").remove();
         	// $("#executiveClassNo").append("<option value =''>— — —</option>");
         	 $.post("/admin/getExecutiveClassesBySchoolNo.action", {gradeNo:selectedGradeNo,schoolNo:$("#schoolNo").val()},
                  	function(data) {
                           $.each(data,function(index, value) {
                              $("#executiveClassNo").append("<option value ="+value.name+">"+value.name+"</option>");
                           });
                       },
                  "json");
         	 
          }
          
        </script>
    </head>
    
	<body>
		<div class="addForm">
        	<div class="formbox">
         		<form>
           			<table width="100%" border="0" cellspacing="0" cellpadding="0">
              			<tr>
               				<td width="77" class="tdleft gray3">
                 				 毕业设计答辩组名：
                			</td>
                			<td colspan="5">
                 				 <span>
                   					 <input id="graduationProjectGroup" type="text" class="putin1 black easyui-validatebox" required="true"
                   						 validType="length[1,30]" style="width:230px;" />
                 				 </span>
               				 </td>
             			</tr>
             			<tr>
               				<td width="77" class="tdleft gray3">
                 				 姓名：
                			</td>
                			<td colspan="5">
                 				 <span>
                   					 <input id="studentName" type="text" class="putin1 black easyui-validatebox" required="true"
                   						 validType="length[1,10]" style="width:230px;" />
                 				 </span>
               				 </td>
             			</tr>
               			<tr>
                			<td width="77" class="tdleft gray3">
                 				 学号：
               				</td>
                			<td colspan="5">
                  				<span>
                    				<input id="studentNo" type="text" class="putin1 black" required="true"
                    					validType="length[1,15]" style="width:230px;" />
                  				</span>
                			</td>
              			</tr>
              			<tr>
                			<td width="77" class="tdleft gray3">
                 				 研究课题名：
               				</td>
                			<td colspan="5">
                  				<span>
                    				<input id="reseachTitle" type="text" class="putin1 black" required="true"
                    					validType="length[1,15]" style="width:230px;" />
                  				</span>
                			</td>
              			</tr>
              			<tr>
                			<td width="77" class="tdleft gray3">
                 				 开题报告得分：
               				</td>
                			<td colspan="5">
                  				<span>
                    				<input id="topicSelectingReportScore" type="text" class="putin1 black" required="true"
                    					validType="length[1,15]" style="width:230px;" />
                  				</span>
                			</td>
              			</tr>
              			<tr>
                			<td width="77" class="tdleft gray3">
                 				 指导老师：
               				</td>
                			<td colspan="5">
                  				<span>
                    				<input id="guideTeacher" type="text" class="putin1 black" required="true"
                    					validType="length[1,15]" style="width:230px;" />
                  				</span>
                			</td>
              			</tr>
                        
            </table>
          </form>
        </div>
      </div>
    </body>
  
  </html>