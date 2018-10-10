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
          var schoolId = '${param.id}';
          var submitForm = function($dialog, $datagrid, $pjquery) {
            if ($('form').form('validate')) {
            	$pjquery.messager.progress({ text: '数据提交中...', interval: 100});
              var url, checked = [];
              if (schoolId.length > 0) {
                url = '/admin/updateSchool.action';
              } else {
                url = '/admin/addSchool.action';
              }
              $('input:checkbox:checked').each(function() {
                checked.push($(this).val());
              });
              var data = {
                schoolId: schoolId,
                schoolNo: $("#schoolNo").val(),
                name: $("#name").val(),
                abbreviation:$("#abbreviation").val(),
              };
              $.post(url, data,
              function(result) {$pjquery.messager.progress('close');
                if (result.exsit) {
                  parent.$.messager.alert('提示', '学院名称已存在', 'info');
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
            if (schoolId.length > 0) {
              $.post("/admin/getSchoolById.action", {
                id: schoolId
              },
              function(result) {
                if (result.schoolId != undefined) {
                  $("#schoolNo").val(result.schoolNo);
                  $("#name").val(result.name);
                  $("#abbreviation").val(result.abbreviation);
                }
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
                  学院代码：
                </td>
                <td colspan="5">
                  <span>
                    <input id="schoolNo" type="text" class="putin1 black easyui-validatebox" required="true"
                    validType="length[1,3]" style="width:230px;" />
                  </span>
                </td>
              </tr>
              <tr>
                <td width="77" class="tdleft gray3">
                  学院名称：
                </td>
                <td colspan="5">
                  <span>
                    <input id="name" type="text" class="putin1 black easyui-validatebox" required="true"
                    validType="length[1,15]" style="width:230px;" />
                  </span>
                </td>
              </tr>
              <tr>
                <td width="77" class="tdleft gray3">
                  学院简称：
                </td>
                <td colspan="5">
                  <span>
                    <input id="abbreviation" type="text" class="putin1 black easyui-validatebox" required="true"
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