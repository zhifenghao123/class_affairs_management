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
          var authority_datagrid;
          $(function() {
            authority_datagrid = $("#authority").datagrid({
              url: "/admin/getAuthority.action",
              fit: true,
              pagination: true,
              fitColumns: true,
              nowrap: false,
              border: false,
              singleSelect: true,
              idField: 'authorityId',
              columns: [[{
                field: 'name',
                title: '权限名称',
                width: 200
              },
              {
                field: 'createdate',
                title: '创建时间',
                width: 150
              },
              {
                  field: 'updateDate',
                  title: '最近修改时间',
                  width: 150
                },
              {
                field: 'state',
                title: '状态',
                width: 100,
                formatter: function(value, row, index) {
                  if (value == 1) {
                    return "使用中";
                  } else {
                    return "暂停使用";
                  }
                }
              },
              {
                field: 'authorityId',
                title: '操作',
                width: 100,
                formatter: function(value, row, index) {
                  var stateButton;
                  if (row.state == 1) {
                    stateButton = '<a href="javascript:void(0)" class="forbid" onclick="change_state(\'' + value + '\',2);"></a>';
                  } else {
                    stateButton = '<a href="javascript:void(0)" class="open" onclick="change_state(\'' + value + '\',1);"></a>';
                  }
                  var str = '<a href="javascript:void(0)" class="edit" onclick="edit_authority(\'' + value + '\');"></a>';
                  str += '<a href="javascript:void(0)" class="delete" onclick="delete_authority(\'' + value + '\');"></a>';
                  str += stateButton;
                  return str;
                }

              }]],
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
            authority_datagrid.datagrid('getPager').pagination({
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
          var search_authority = function() {
            authority_datagrid.datagrid('load', {
              name: $("#name").val()
            });
          };
          var reset_authority = function() {
            $("#search_authority_form")[0].reset();
            authority_datagrid.datagrid('load', {});
          };
          var addAuthorityDialog;
          var add_authority = function() {
            addAuthorityDialog = parent.qujan.modalDialog({
              title: '新增权限',
              url: '/admin/systemManagement/authorityForm.jsp',
              height: 300,
              buttons: [{
                text: '返回',
                handler: function() {
                  addAuthorityDialog.dialog('close');
                }
              },
              {
                text: '确定',
                handler: function() {
                  addAuthorityDialog.find('iframe')[0].contentWindow.submitForm(addAuthorityDialog, authority_datagrid, parent.$); //调用字窗口方法
                }
              }]
            });
          };
          var editAuthorityDialog;
          var edit_authority = function(id) {
            editAuthorityDialog = parent.qujan.modalDialog({
              title: '修改权限',
              url: '/admin/systemManagement/authorityForm.jsp?id=' + id,
              height: 300,
              buttons: [{
                text: '返回',
                handler: function() {
                  editAuthorityDialog.dialog('close');
                }
              },
              {
                text: '确定',
                handler: function() {
                  editAuthorityDialog.find('iframe')[0].contentWindow.submitForm(editAuthorityDialog, authority_datagrid, parent.$); //调用字窗口方法
                }
              }]
            });
          };
          var delete_authority = function(id) {
            parent.$.messager.confirm('删除确认', '您确认要删除吗?',
            function(r) {
              if (r) {
                $.post('/admin/deleteAuthorityById.action', {
                  id: id
                },
                function(msg) {
                  if (msg.success) {
                    authority_datagrid.datagrid('reload');
                    parent.$.messager.show({
                      title: '提示',
                      msg: '删除成功',
                      timeout: 2000
                    });
                  }else{
                	  parent.$.messager.show({
                          title: '提示',
                          msg: '应用或角色正在使用，请先解除关联关系！',
                          timeout: 2000
                        });
                  }
                },
                'json');
              }
            });
          };
          var change_state = function(authorityId, state) {
            $.post('/admin/updateAuthorityState.action', {
              'id': authorityId,
              'state': state
            },
            function(msg) {
              if (msg.success) {
                authority_datagrid.datagrid('reload');
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
          <form id="search_authority_form">
            <div class="datagrid-toolbar search_1">
              <span>
                权限名称:
              </span>
              <input id="name" type="text" size="20" />
              <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search"
              style="margin-left:30px;" onclick="search_authority();">
                查询
              </a>
              <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-redo"
              style="margin-left:10px;" onclick="reset_authority();">
                清空
              </a>
              <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
              style="margin-left:10px;" onclick="add_authority();">
                新增
              </a>
            </div>
          </form>
        </div>
        <div data-options="region:'center'" border="false" sytle="overflow:hidden;">
          <div id="authority">
          </div>
        </div>
      </div>
    </body>
  
  </html>