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
          var authorityId = '${param.id}';
          if (authorityId == null) {
            authorityId = '';
          }
          var submitForm = function($dialog, $datagrid, $pjquery) {
            if ($('form').form('validate')) {
            	$pjquery.messager.progress({ text: '数据提交中...', interval: 100});
             	var url;
              	if (authorityId.length > 0) {
               		url = '/admin/updateAuthority.action';
              	} else {
               		url = '/admin/addAuthority.action';
              	}
              	$.post(url, $("form").serialize(),
              		function(result) { 
              			$pjquery.messager.progress('close');
                		if (result.exsit) {
                  			parent.$.messager.alert('提示', '权限名称已存在', 'info');
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
        	if (authorityId.length > 0) {
        		$("#authorityId").attr("value", authorityId);
        		$.post("/admin/getAuthorityById.action", {id: authorityId},
              		function(result) {
                		if (result.authorityId != undefined) {
                  			$('form').form('load', {'name': result.name});
                		}
              		},'json');
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
                  权限名称：
                </td>
                <td colspan="5">
                  <span>
                    <input name="name" type="text" class="putin1 black easyui-validatebox"
                    required="true" validType="length[1,10]" style="width:230px;" />
                    <input name="id" id="authorityId" type="hidden" value="" />
                  </span>
                </td>
              </tr>
            </table>
          </form>
        </div>
      </div>
    </body>
  
  </html>