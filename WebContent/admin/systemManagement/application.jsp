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
          var application_datagrid;
          $(function() {
            application_datagrid = $("#application").datagrid({
              url: "/admin/application.action",
              fit: true,
              pagination: true,
              fitColumns: true,
              nowrap: false,
              border: false,
              singleSelect: true,
              idField: 'applicationId',
              columns: [[{
                field: 'displayName',
                title: '名称',
                width: 100
              },
              {
                field: 'url',
                title: '链接',
                width: 200
              },
              {
                field: 'entryAuthorityIds',
                title: '权限种类',
                width: 150
              },
              {
                  field: 'type',
                  title: '类别',
                  width: 50
               },
              {
                field: 'applicationId',
                title: '操作',
                width: 100,
                formatter: function(value, row, index) {
                  var str = '<a href="javascript:void(0)" class="edit" onclick="edit_application(\'' + value + '\');"></a>';
                  str += '<a href="javascript:void(0)" class="delete" onclick="delete_application(\'' + value + '\');">|</a>';
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
              }
            });
            application_datagrid.datagrid('getPager').pagination({
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
          function keydown(event){
        	  if(event.keyCode == 13) {
        	   	application_datagrid.datagrid('load', {
                    displayName: $("#displayName").val()
                  });
        	  }
          }
          function reset_application() {
        	  $("#displayName").val('');
            application_datagrid.datagrid('load', {});
          }
          function search_application() {
            application_datagrid.datagrid('load', {
              displayName: $("#displayName").val()
            });
          }
          var addApplicationDialog;
          var add_application = function() {
            addApplicationDialog = parent.qujan.modalDialog({
              height: 500,
              title: '新增应用',
              url: '/admin/systemManagement/applicationForm.jsp',
              buttons: [{
                text: '返回',
                handler: function() {
                  addApplicationDialog.dialog('close');
                }
              },
              {
                text: '确定',
                handler: function() {
                  addApplicationDialog.find('iframe')[0].contentWindow.submitForm(addApplicationDialog, application_datagrid, parent.$);
                }
              }]
            });
          };
          var editApplicationDialog;
          var edit_application = function(id) {
            editApplicationDialog = parent.qujan.modalDialog({
              height: 500,
              title: '修改应用',
              url: '/admin/systemManagement/applicationForm.jsp?id=' + id,
              buttons: [{
                text: '返回',
                handler: function() {
                  editApplicationDialog.dialog('close');
                }
              },
              {
                text: '确定',
                handler: function() {
                  editApplicationDialog.find('iframe')[0].contentWindow.submitForm(editApplicationDialog, application_datagrid, parent.$);
                }
              }]
            });
          };
          var delete_application = function(id) {
            parent.$.messager.confirm('删除确认', '您确认要删除吗?',
            function(r) {
              if (r) {
                $.post('/admin/deleteApplicationById.action', {
                  id: id
                },
                function(msg) {
                  if (msg.haveChild) {
                    parent.$.messager.alert('提示', '对不起,不能删除有下级的菜单', 'info');
                  } else {

                    if (msg.success) {
                      application_datagrid.datagrid('reload');
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
        </script>
    </head>
    
    <body>
      <div class="easyui-layout" fit="true" border="false">
        <div data-options="region:'north'" border="false" style="height:40px;overflow:hidden;">
            <div class="datagrid-toolbar search_1">
              <span>
                应用名称:
              </span>
              <input id="displayName" type="textbox" size="20" onkeydown="keydown(event)"/>
              <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search"
              style="margin-left:30px;" onclick="search_application();">
                查询
              </a>
              <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-redo"
              style="margin-left:10px;" onclick="reset_application();">
                清空
              </a>
              <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
              style="margin-left:10px;" onclick="add_application();">
                新增
              </a>
            </div>
        </div>
        <div data-options="region:'center'" border="false" style="overflow:hidden;">
          <table id="application">
          </table>
        </div>
      </div>
    </body>
  
  </html>