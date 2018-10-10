<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%>
  <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
  <html>
    
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <%@include file="/admin/included.jsp" %>
        <title>
        </title>
        <script type="text/javascript">
          var department_datagrid;
          $(function() {
            department_datagrid = $("#department").datagrid({
              url: "/admin/getDepartment.action",
              fit: true,
              pagination: true,
              fitColumns: true,
              nowrap: false,
              border: false,
              singleSelect: true,
              idField: 'departmentId',
              columns: [[{
                field: 'departmentNo',
                title: '专业代码',
                width: 50
              },
              {
                field: 'name',
                title: '专业名称',
                width: 100
              },
              {
                  field: 'schoolAbbreviationName',
                  title: '所在学院名称',
                  width: 100
                },
                {
                    field: 'schoolNo',
                    title: '所在学院代码',
                    width: 50
                  },
              {
                field: 'departmentId',
                title: '操作',
                width: 150,
                formatter: function(value, row, index) {
                  var stateButton;
                  if (row.state == 1) {
                    stateButton = '<a href="javascript:void(0)" class="forbid" onclick="change_state(\'' + value + '\',2);"></a>';
                  } else {
                    stateButton = '<a href="javascript:void(0)" class="open" onclick="change_state(\'' + value + '\',1);"></a>';
                  }
                  var str = '<a href="javascript:void(0)" class="edit" onclick="edit_department(\'' + value + '\');"></a>';
                  str += '<a href="javascript:void(0)" class="delete" onclick="delete_department(\'' + value + '\');">|</a>';
                  str += stateButton;
                  return str;
                }

              }

              ]],
              onBeforeLoad: function(data) {
                parent.$.messager.progress({
                  text: '数据加载中...',
                  interval: 100
                });
              },
              onLoadSuccess: function(data) {
                parent.$.messager.progress('close');
                $('.edit').linkbutton({
                  text: '修改',
                  iconCls: 'icon-edit',
                  plain: true
                });
                $('.delete').linkbutton({
                  text: '删除',
                  iconCls: 'icon-cancel',
                  plain: true
                });
                $('.forbid').linkbutton({
                  text: '禁用',
                  iconCls: 'icon-no',
                  plain: true
                });
                $('.open').linkbutton({
                  text: '启用',
                  iconCls: 'icon-ok',
                  plain: true
                });
              }
              /*toolbar : [{
				 text : "新增",
				 iconCls : "icon-add",
				 handle : function(){
					 
				 }
				 
			}
			 ]*/

              //toolbar:'#toolbar'
            });
            department_datagrid.datagrid('getPager').pagination({
              afterPageText: '页 共 {pages} 页',
              displayMsg: '当前显示第 {from} - {to} 条记录 共{total}条记录',
              buttons: [{
                iconCls: 'icon-redo',
                text: '跳转',
                handler: function() {
                  var e = $.Event('keydown');
                  e.keyCode = 13;
                  $('input.pagination-num').trigger(e);
                }
              }]
            });
          });
          var reset_department = function() {
            $("#search_department_form")[0].reset();
            department_datagrid.datagrid('load', {});
          };
          var search_department = function() {
            department_datagrid.datagrid('load', {
              name: $("#name").val()
            });
          };
          var addDepartmentDialog;
          var add_department = function() {
            addDepartmentDialog = parent.qujan.modalDialog({
              title: '新增专业',
              url: '/admin/informationManagement/departmentForm.jsp',
              height: 450,
              width: 600,
              buttons: [{
                text: '返回',
                handler: function() {
                  addDepartmentDialog.dialog('close');
                }
              },
              {
                text: '确定',
                handler: function() {
                  addDepartmentDialog.find('iframe')[0].contentWindow.submitForm(addDepartmentDialog, department_datagrid, parent.$);
                }
              }]
            });
          };
          var editDepartmentDialog;
          var edit_department = function(id) {
            editDepartmentDialog = parent.qujan.modalDialog({
              title: '修改专业',
              url: '/admin/informationManagement/departmentForm.jsp?id=' + id,
              height: 450,
              width: 600,
              buttons: [{
                text: '返回',
                handler: function() {
                  editDepartmentDialog.dialog('close');
                }
              },
              {
                text: '确定',
                handler: function() {
                  editDepartmentDialog.find('iframe')[0].contentWindow.submitForm(editDepartmentDialog, department_datagrid, parent.$);
                }
              }]
            });
          };
          var delete_department = function(id) {
            parent.$.messager.confirm('删除确认', '您确认要删除吗?',
            function(r) {
              if (r) {
                $.post('/admin/deleteDepartmentById.action', {
                  id: id
                },
                function(msg) {
                  if (msg.exsit) {
                    parent.$.messager.alert('提示', '对不起，用户正在使用，请先解除关联关系！', 'info');
                  } else {

                    if (msg.success) {
                      department_datagrid.datagrid('reload');
                      parent.$.messager.show({
                        title: '提示',
                        msg: '删除成功',
                        timeout: 2000
                      });
                    } else {
                      parent.$.messager.show({
                        title: '提示',
                        msg: '删除失败',
                        timeout: 2000
                      });
                    }

                  }
                },
                'json');
              }
            });
          };
          var change_state = function(departmentId, state) {
            $.post('/admin/updateDepartmentState.action', {
              'id': departmentId,
              'state': state
            },
            function(msg) {
              if (msg.success) {
                department_datagrid.datagrid('reload');
                parent.$.messager.show({
                  title: '提示',
                  msg: '操作成功',
                  timeout: 2000
                });
              } else {
                parent.$.messager.show({
                  title: '提示',
                  msg: '操作失败',
                  timeout: 2000
                });
              }
            },
            'json');
          };
        </script>
    </head>
    
    <body>
      <div class="easyui-layout" fit="true" border="false">
        <div data-options="region:'north'" border="false" style="height:40px;overflow:hidden;">
          <form id="search_department_form">
            <div class="datagrid-toolbar search_1">
              <span>
                专业名称:
              </span>
              <input id="name" type="text" size="20" />
              <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search"
              style="margin-left:30px;" onclick="search_department();">
                查询
              </a>
              <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-redo"
              style="margin-left:10px;" onclick="reset_department();">
                清空
              </a>
              <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
              style="margin-left:10px;" onclick="add_department();">
                新增
              </a>
            </div>
          </form>
        </div>
        <div data-options="region:'center',border:false" style="overflow:hidden;">
          <table id="department" data-options="border:false">
          </table>
        </div>
      </div>
    </body>
  
  </html>