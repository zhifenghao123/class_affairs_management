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
          var interioremployee_datagrid;
          $(function() {
            interioremployee_datagrid = $("#interioremployee").datagrid({
              url: "/admin/getAdministrator.action",
              fit: true,
              pagination: true,
              fitColumns: true,
              nowrap: false,
              border: false,
              singleSelect: true,
              columns: [[{
                  field: 'jobNo',
                  title: '账号',
                  width: 100
                },
                {
                field: 'realname',
                title: '真实姓名',
                width: 100
              },
              {
                field: 'roleName',
                title: '角色名称',
                width: 200
              },
              {
                field: 'createdate',
                title: '创建时间',
                width: 100
              },
              {
                field: 'lastdate',
                title: '最后修改时间',
                width: 100
              },
              {
                field: 'state',
                title: '状态',
                width: 50,
                formatter: function(value, row, index) {
                  if (value == 1) {
                    return "使用中";
                  } else {
                    return "禁用";
                  }
                }
              },
              {
                field: 'interioremployeeId',
                title: '操作',
                width: 150,
                formatter: function(value, row, index) {
                  var stateButton;
                  if (row.state == 1) {
                    stateButton = '<a href="javascript:void(0)" class="forbid" onclick="change_state(\'' + value + '\',2);"></a>';
                  } else {
                    stateButton = '<a href="javascript:void(0)" class="open" onclick="change_state(\'' + value + '\',1);"></a>';
                  }
                  var str = '<a href="javascript:void(0)" class="edit" onclick="edit_employee(\'' + value + '\');"></a>';
                  str += '<a href="javascript:void(0)" class="delete" onclick="delete_employee(\'' + value + '\');">|</a>';
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
            });
            interioremployee_datagrid.datagrid('getPager').pagination({
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
          function reset_interioremployee() {
            $("#search_interioremployee_form")[0].reset();
            interioremployee_datagrid.datagrid('load', {});
          }
          function search_interioremployee() {
            interioremployee_datagrid.datagrid('load', {
              name: $("#name").val()
            });
          }
          var addInterioremployeeDialog;
          var add_interioremployee = function() {
            addInterioremployeeDialog = parent.qujan.modalDialog({
              title: '新增内部用户',
              url: '/admin/systemManagement/administratorForm.jsp',
              buttons: [{
                text: '返回',
                handler: function() {
                  addInterioremployeeDialog.dialog('close');
                }
              },
              {
                text: '确定',
                handler: function() {
                  addInterioremployeeDialog.find('iframe')[0].contentWindow.submitForm(addInterioremployeeDialog, interioremployee_datagrid, parent.$);
                }
              }]
            });
          };
          var editInterioremployeeDialog;
          var edit_employee = function(id) {
            editInterioremployeeDialog = parent.qujan.modalDialog({
              title: '修改内部用户',
              url: '/admin/systemManagement/administratorForm.jsp?id=' + id,
              buttons: [{
                text: '返回',
                handler: function() {
                  editInterioremployeeDialog.dialog('close');
                }
              },
              {
                text: '确定',
                handler: function() {
                  editInterioremployeeDialog.find('iframe')[0].contentWindow.submitForm(editInterioremployeeDialog, interioremployee_datagrid, parent.$);
                }
              }]
            });
          };
          var delete_employee = function(id) {
            parent.$.messager.confirm('删除确认', '您确认要删除吗?',
            function(r) {
              if (r) {
                $.post('/admin/deleteInterioremployeeById.action', {
                  id: id
                },
                function(msg) {
                  if (msg.success) {
                    interioremployee_datagrid.datagrid('reload');
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

                },
                'json');
              }
            });
          };
          var change_state = function(interioremployeeId, state) {
            $.post('/admin/updateInterioremployeeState.action', {
              'id': interioremployeeId,
              'state': state
            },
            function(msg) {
              if (msg.success) {
                interioremployee_datagrid.datagrid('reload');
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
          <form id="search_interioremployee_form">
            <div class="datagrid-toolbar search_1">
              <span>
                用户名:
              </span>
              <input id="name" type="text" size="20" />
              <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search"
              style="margin-left:30px;" onclick="search_interioremployee();">
                查询
              </a>
              <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-redo"
              style="margin-left:10px;" onclick="reset_interioremployee();">
                清空
              </a>
              <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
              style="margin-left:10px;" onclick="add_interioremployee();">
                新增
              </a>
            </div>
          </form>
        </div>
        <div data-options="region:'center'" border="false" style="overflow:hidden;">
          <table id="interioremployee">
          </table>
        </div>
      </div>
    </body>
  
  </html>