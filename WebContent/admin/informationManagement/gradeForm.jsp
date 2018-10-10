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
        <script type="text/javascript" src="/common/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript">
        	var gradeNo = '${param.id}';
          	var submitForm = function($dialog, $datagrid, $pjquery) {
	        	if ($('form').form('validate')) {
	            	$pjquery.messager.progress({ text: '数据提交中...', interval: 100});
	             	var url, checked = [];
	              	if (gradeNo.length > 0) {
	                	url = '/admin/updateGrade.action';
	              	} else {
	                	url = '/admin/addGrade.action';
	              	}
	              	$('input:checkbox:checked').each(function() {
	                	checked.push($(this).val());
	              });alert($("#enrollDate").val());
	              var data = {
	               /*  gradeNo: gradeNo, */
	                enrollDate:$("#enrollDate").val(),
	                /* gradeNo: $("#gradeNo").val(), */
	               /*  name: $("#name").val(), */
	               
	              };
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
          	if (gradeNo.length > 0) {
              $.post("/admin/getGradeByGradeNo.action", {
            	  gradelNo: gradeNo
              },
              function(result) {
                $("#gradeNo").val(result.gradeNo);
                  $("#enrollDate").val(result.enrollDate);
              },
              'json');
            }
          });
          
        </script>
    </head>
    
    <body>
      <div class="addForm">
        <div class="formbox">
          <form>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="77" class="tdleft gray3">
                  年级代号：
                </td>
                <td colspan="5">
                  <span>
                  <input id = "gradeNo" name="gradeNo" type="text" class="Wdate" onclick="WdatePicker({skin:'default',dateFmt:'yyyy'});"/>
                  </span>
                </td>
              </tr>
              <tr>
                <td width="77" class="tdleft gray3">
                  年级入学时间：
                </td>
                <td colspan="5">
                  <label>
                 	<input id = "enrollDate" name="enrollDate" type="text" class="Wdate" onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'});"/>
                 	<!-- <input id = "enrollDate2" name="enrollDate" type="text" class="Wdate" onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'gradeNo\')-01-01'});"/> -->
                 </label>
              </tr>
     
            </table>
          </form>
        </div>
      </div>
    </body>
  
  </html>