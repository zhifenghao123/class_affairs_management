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
        	var applicationId = '${param.id}';
          	var submitForm = function($dialog, $datagrid, $pjquery) {
            	if ($('form').form('validate')) {
            		$pjquery.messager.progress({ text: '数据提交中...', interval: 100});
             		 var url, checked = [];
             		 if (applicationId.length > 0) {
		             	url = '/admin/updateApplication.action';
		             } else {
		                url = '/admin/addApplication.action';
		             }
	              	$('input:checkbox:checked').each(function() {
	                	checked.push($(this).val());
	              	});
	             	var data = {
	             		applicationId: applicationId,
	             		displayName: $('#name').val(),
	             		url: $('#url').val(),
	               		type:$('#type').val(),
	               		parentApplicationId: $('#parent').combobox('getValue'),
	               		entryAuthorityIds: checked.join(",")
	              	};
	              	$.post(url, data,function(result) {
	            	  	$pjquery.messager.progress('close');
	                	if (result.exsit) {
	                  		parent.$.messager.alert('提示', '名称已存在', 'info');
	                	} else {
	                  		if (result.success) {
	                    		if (applicationId.length > 0) 
	                    			$datagrid.datagrid('reload');
	                    		else 
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
	              	},'json');
            	}
         };
         $(function() {
			if (applicationId.length > 0) {
            	$.post("/admin/getApplicationById.action", {id: applicationId},
            			function(result) {
                			if (result.applicationId != undefined) {
		                  		$("#name").val(result.displayName);
		                  		$("#url").val(result.url);
		                 		$("#type").val(result.type);
		                  		var codes = result.entryAuthorityIds;
		                  		if (result.pid == 0) { //菜单
				                    $("#hidden_app").hide();
				                    $('#parent_app').hide();
				                    $('#url').val(' ');
				                    $('#parent').combobox('setValue', 0);
				                    $('#parent').combobox('setText', '主页');
				                    $('#appType').combobox('setValue', 0);
		                  		} else if (result.pid == undefined) { //应用
				                    $('#parent_app').hide();
				                    $('#hidden_app').show();
				                    $('#parent').val(' ');
				                    $('#appType').combobox('setValue', 2);
				                    $('#parent').combobox('setValue', ' ');
			                  	} else { //应用菜单
			                    	$('#parent').combobox('setValue', result.pid);
			                  	}
		                  		//$('#appType').combobox('disable');
		                 		if (codes != undefined) 
		                 			$.post("/admin/getAllAuthorityList.action", "",
		                 				function(data) {
		                    				$.each(data, function(index, value) {
		                      					if (codes.split(",").indexOf(value.authorityId) != -1) 
		                      						$("#ownAuthority").append("<li><input  type='checkbox' checked='checked' value='" + value.authorityId + "' />" + value.name + "</li>");
		                      					else 
		                      						$("#ownAuthority").append("<li><input  type='checkbox' value='" + value.authorityId + "' />" +'&nbsp;&nbsp;'+ value.name + "</li>");
		                    				});
		                  				},
		                  			"json");
		                	}
		             },
              	'json');
            } else {
              	$.post("/admin/getAllAuthorityList.action", "",
              		function(data) {
               			 $.each(data,function(index, value) {
                  			$("#ownAuthority").append("<li><input  type='checkbox' value='" + value.authorityId + "' />" +'&nbsp;&nbsp;'+  value.name + "</li>");
                		});
              		},
              "json");
            }
			$('#appType').combobox({
				editable: false,
              	panelHeight: 70,
              	onSelect: function(rec) {
	                if (rec.value == 0) {
	                	$("#hidden_app").hide();
	                  	$('#parent_app').hide();
	                  	$('#url').val(' ');
	                  	$('#parent').combobox('setValue', 0);
	                  	$('#parent').combobox('setText', '主页');
	                } else if (rec.value == 1) {
	                  	$('#hidden_app').show();
	                  	$('#parent_app').show();
	                  	loadParentApp(0);
	                } else {
	                  	$('#parent_app').hide();
	                  	$('#hidden_app').show();
	                  	$('#parent').combobox('setValue', ' ');
	                }
              }
           });
           loadParentApp(0);
		});
        var loadParentApp = function(pid) {
        	$('#parent').combobox({
              	editable: false,
            	panelHeight: 'auto',
              	required: true,
              	url: '/admin/getApplicationByPid.action?pid=' + pid,
              	valueField: 'applicationId',
              	textField: 'displayName'
            });
         };
        </script>
    </head>
    
    <body>
      <div class="addForm">
        <div class="formbox">
          <form>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="77" class="tdleft gray3">
                  应用名称：
                </td>
                <td colspan="5">
                  <span>
                    <input id="name" type="text" class="putin1 black easyui-validatebox" required="true"
                    validType="length[1,20]" style="width:230px;" />
                  </span>
                </td>
              </tr>
              <tr>
                <td width="77" class="tdleft gray3">
                  应用类型：
                </td>
                <td colspan="5">
                  <label>
                    <select class="select1 black" id="appType" style="width:237px;">
                      <option value="1">
                        菜单应用
                      </option>
                      <option value="0">
                        菜单
                      </option>
                      <option value="2">
                        应用
                      </option>
                    </select>
                  </label>
              </tr>
              <tr>
                <td width="77" class="tdleft gray3">
                  类型：
                </td>
                <td colspan="5">
                  <label>
                    <select class="select1 black" id="type" style="width:237px;">
                      <option value="1">
                        班级事务管理
                      </option>
                     <!--  <option value="2">
                        商务系统
                      </option>
                      <option value="3">
                        支付系统
                      </option> -->
                    </select>
                  </label>
              </tr>
            </table>
            <div id="parent_app">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="77" class="tdleft gray3">
                    上级应用：
                  </td>
                  <td colspan="5">
                    <label>
                      <input class="black easyui-validatebox" id="parent" style="width:237px;"/>
                    </label>
                </tr>
              </table>
            </div>
            <div id="hidden_app">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="77" class="tdleft gray3">
                    url：
                  </td>
                  <td colspan="5">
                    <span>
                      <input id="url" type="text" class="putin1 black easyui-validatebox" required="true"
                     validType="length[0,200]"  style="width:230px;" />
                    </span>
                  </td>
                </tr>
                <tr>
                  <td width="77" class="tdleft gray3">
                    权限种类：
                  </td>
                  <td colspan="5">
                    <div class="edit_checkbox">
                      <ul id="ownAuthority">
                      </ul>
                    </div>
                </tr>
              </table>
            </div>
          </form>
        </div>
      </div>
    </body>
  
  </html>