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
          var roleId = '${param.id}';
          var submitForm = function($dialog, $datagrid, $pjquery) {
            if ($('form').form('validate')) {
            	$pjquery.messager.progress({ text: '数据提交中...', interval: 100});
              var url, checked = [];
              if (roleId.length > 0) {
                url = '/admin/updateRole.action';
              } else {
                url = '/admin/addRole.action';
              }
              $('input:checkbox:checked').each(function() {
                checked.push($(this).val());
              });
            
              var data = {
                roleId: roleId,
                name: $("#name").val(),
                creatorType: $("#creatorType").val(),
                authorityCode: checked.join(",")
              };
              $.post(url, data,
              function(result) {$pjquery.messager.progress('close');
                if (result.exsit) {
                  parent.$.messager.alert('提示', '角色名称已存在', 'info');
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
            if (roleId.length > 0) {
              $.post("/admin/getRoleById.action", {
                id: roleId
              },
              function(result) {
                if (result.roleId != undefined) {
                  $("#name").val(result.name);
                  $("#creatorType").val(result.creatortype);
                  var codes = result.authorityCode;
                  $.post("/admin/getAllAuthorityList.action", "",
                  function(data) {
                    $.each(data,
                    function(index, value) {
                      if (codes.split(",").indexOf(value.authorityId) != -1) $("#ownAuthority").append("<li><input  type='checkbox' checked='checked' value='" + value.authorityId + "' />" + value.name + "</li>");
                      else $("#ownAuthority").append("<li><input  type='checkbox' value='" + value.authorityId + "' />" + value.name + "</li>");
                    });
                  },
                  "json");
                }
              },
              'json');
            } else {
              $.post("/admin/getAllAuthorityList.action", "",
              function(data) {
                $.each(data,
                function(index, value) {
                  $("#ownAuthority").append("<li><input  type='checkbox' value='" + value.authorityId + "' />" + value.name + "</li>");
                });
              },
              "json");
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
                		<td width="77" class="tdleft gray3"> 角色名称：</td>
                		<td colspan="5">
                  			<span>
                    			<input id="name" type="text" class="putin1 black easyui-validatebox" required="true"
                  				validType="length[1,10]" style="width:230px;" />
                  			</span>
                		</td>
              		</tr>
              		<tr>
                		<td width="77" class="tdleft gray3"> 创建类型</td>
                		<td colspan="5">
                  			<label>
                    			<select class="select1 black easyui-combobox" editable="false" panelHeight="50" id="creatorType" style="width:230px;">
                      				<option value="1"> 系统管理员</option>
                      				<option value="2">学院管理人员</option>
                    			</select>
                 			</label>
                 		</td>
              		</tr>
              		<tr>
                		<td width="77" class="tdleft gray3"> 拥有权限：</td>
                		<td colspan="5">
                  			<div class="edit_checkbox">
                    			<ul id="ownAuthority">
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