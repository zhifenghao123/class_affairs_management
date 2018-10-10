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
          var departmentId = '${param.id}';
          var submitForm = function($dialog, $datagrid, $pjquery) {
            if ($('form').form('validate')) {
            	$pjquery.messager.progress({ text: '数据提交中...', interval: 100});
              var url, checked = [];
              if (departmentId.length > 0) {
                url = '/admin/updateDepartment.action';
              } else {
                url = '/admin/addDepartment.action';
              }
              $('input:checkbox:checked').each(function() {
                checked.push($(this).val());
              });
              var data = {
                departmentId: departmentId,
                departmentNo: $("#departmentNo").val(),
                schoolNo:$("#schoolNo").val(),
                name: $("#name").val(),
               
              };
              $.post(url, data,
              function(result) {$pjquery.messager.progress('close');
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

            $.ajax({
  				url : "/admin/getAllSchoolList.action",
  				type : "post",
  				async : false,
  				dataType : "json",
  				data : {},
  				success : function(data) {
  					 $.each(data,function(index, value) {
  						$("#schoolNo").append("<option value ="+value.schoolNo+">"+value.schoolNo+"   "+value.name+"</option>");
  	         		});
  				}
  			});
            if (departmentId.length > 0) {
              $.ajax({
        			url : "/admin/getDepartmentById.action",
        			type : "post",
        			async : false,
        			dataType : "json",
        			data : {"id": departmentId},
        			success : function(result) {
        				 $("#departmentNo").val(result.departmentNo);
                         $("#name").val(result.name);
                         $("#schoolNo").val(result.schoolNo);
        			}
        		});
              
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
                  系(所)代码：
                </td>
                <td colspan="5">
                  <span>
                    <input id="departmentNo" type="text" class="putin1 black easyui-validatebox" required="true"
                    validType="length[1,10]" style="width:230px;" />
                  </span>
                </td>
              </tr>
              <tr>
                <td width="77" class="tdleft gray3">
                  系(所)名称：
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
                  所在学院：
                </td>
                <td colspan="5">
                  <label>
                    <select class="select1 black" id="schoolNo" style="width:237px;">
                     <!--  <option value="1">
                        
                      </option> -->

                    </select>
                  </label>
              </tr>
            </table>
          </form>
        </div>
      </div>
    </body>
  
  </html>