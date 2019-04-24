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
        	var administratorId = "${param.id}";
          	$(function() {
            	if (administratorId.length > 0) {
             		 $.post("/admin/getAdministratorById.action", {administratorId: administratorId},
              			function(result) {
               				// if (result.administratorId != undefined) {
                 				$("#name").val(result.name);
                  				$("#administratorNo").val(result.administratorNo);
                  				var roleId = result.roleId;
                  					$.post("/admin/getAllRoleList.action", "",
                  						function(data) {
                   							$.each(data,function(index, value) {
                     							if (roleId == value.roleId) 
                     								$("#ownRole").append("<li><input  type='radio' name='role' checked='checked' value='" + value.roleId + "' />" + value.name + "</li>");
                     							else 
                     								$("#ownRole").append("<li><input  type='radio' name='role' value='" + value.roleId + "' />" + value.name + "</li>");
                    						});
                  					},"json");
                			//}
              		},'json');
			} else {
            	$.post("/admin/getAllRoleList.action", "",
              		function(data) {
                		$.each(data,function(index, value) {
                  			$("#ownRole").append("<li><input  type='radio' name='role' value='" + value.roleId + "' />" + value.name + "</li>");
                		});
              	},"json");
            }
          });
          var submitForm = function($dialog, $datagrid, $pjquery) {
          	if ($('form').form('validate')) {
            	$pjquery.messager.progress({ text: '数据提交中...', interval: 100});
              	var url;
              	if (administratorId.length > 0) {
                	url = '/admin/updateAdministrator.action';
             	} else {
                	url = '/admin/addAdministrator.action';
              	}

              	var data = {
                	administratorId: administratorId,
                	name: $("#name").val(),
                	administratorNo: $("#administratorNo").val(),
                	roleId: $('input:radio:checked').val()
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
              },'json');
         }
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
                  登陆账号：
                </td>
                <td colspan="5">
                  <input id="administratorNo" name="input" type="text" class="putin1 black easyui-validatebox"
                  validType="account" required="true" validType="length[1,20]" style="width:230px;" />
                </td>
              </tr>
              <tr>
                <td width="77" class="tdleft gray3">
                  真实姓名：
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
                  拥有角色：
                </td>
                <td colspan="5">
                  <div class="edit_checkbox">
                    <ul id="ownRole">
                    </ul>
                  </div>
                </td>
              </tr>
            </table>
          </form>
        </div>
      </div>
    </body>
  
  </html>