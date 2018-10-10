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
          var dormitory_datagrid;
          $(function() {
            dormitory_datagrid = $("#dormitory").datagrid({
              url: "/admin/getDormitoryList.action",
              fit: true,
              pagination: true,
              fitColumns: true,
              nowrap: false,
              border: false,
              singleSelect: true,
              idField: 'dormitoryId',
              columns: [[{
                field: 'roomNo',
                title: '宿舍号',
                width: 150
              },
              {
                field: 'apartment',
                title: '公寓',
                width: 150
              },
              {
                field: 'dormitoryId',
                title: '操作',
                width: 150,
                formatter: function(value, row, index) {
                  var stateButton;
                  if (row.state == 1) {
                    stateButton = '<a href="javascript:void(0)" class="forbid" onclick="change_state(\'' + value + '\',2);"></a>';
                  } else {
                    stateButton = '<a href="javascript:void(0)" class="open" onclick="change_state(\'' + value + '\',1);"></a>';
                  }
                  var str = '<a href="javascript:void(0)" class="edit" onclick="edit_dormitory(\'' + value + '\');"></a>';
                  str += '<a href="javascript:void(0)" class="delete" onclick="delete_dormitory(\'' + value + '\');">|</a>';
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
            dormitory_datagrid.datagrid('getPager').pagination({
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
          var reset_dormitory = function() {
            $("#search_dormitory_form")[0].reset();
            dormitory_datagrid.datagrid('load', {});
          };
          var search_dormitory = function() {
            dormitory_datagrid.datagrid('load', {
              name: $("#name").val()
            });
          };
          var addDormitoryDialog;
          var add_dormitory = function() {
            addDormitoryDialog = parent.qujan.modalDialog({
              title: '新增学院',
              url: '/admin/informationManagement/dormitoryForm.jsp',
              height: 450,
              width: 600,
              buttons: [{
                text: '返回',
                handler: function() {
                  addDormitoryDialog.dialog('close');
                }
              },
              {
                text: '确定',
                handler: function() {
                  addDormitoryDialog.find('iframe')[0].contentWindow.submitForm(addDormitoryDialog, dormitory_datagrid, parent.$);
                }
              }]
            });
          };
          var editDormitoryDialog;
          var edit_dormitory = function(id) {
            editDormitoryDialog = parent.qujan.modalDialog({
              title: '修改学院',
              url: '/admin/informationManagement/dormitoryForm.jsp?id=' + id,
              height: 450,
              width: 600,
              buttons: [{
                text: '返回',
                handler: function() {
                  editDormitoryDialog.dialog('close');
                }
              },
              {
                text: '确定',
                handler: function() {
                  editDormitoryDialog.find('iframe')[0].contentWindow.submitForm(editDormitoryDialog, dormitory_datagrid, parent.$);
                }
              }]
            });
          };
          var delete_dormitory = function(id) {
            parent.$.messager.confirm('删除确认', '您确认要删除吗?',
            function(r) {
              if (r) {
                $.post('/admin/deleteDormitoryById.action', {
                  id: id
                },
                function(msg) {
                  if (msg.exsit) {
                    parent.$.messager.alert('提示', '对不起，用户正在使用，请先解除关联关系！', 'info');
                  } else {

                    if (msg.success) {
                      dormitory_datagrid.datagrid('reload');
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
          var change_state = function(dormitoryId, state) {
            $.post('/admin/updateDormitoryState.action', {
              'id': dormitoryId,
              'state': state
            },
            function(msg) {
              if (msg.success) {
                dormitory_datagrid.datagrid('reload');
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
          <form id="search_dormitory_form">
            <div class="datagrid-toolbar search_1">
              <span>
                学院名称:
              </span>
              <input id="name" type="text" size="20" />
              <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search"
              style="margin-left:30px;" onclick="search_dormitory();">
                查询
              </a>
              <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-redo"
              style="margin-left:10px;" onclick="reset_dormitory();">
                清空
              </a>
              <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
              style="margin-left:10px;" onclick="add_dormitory();">
                新增
              </a>
            </div>
          </form>
        </div>
        <div data-options="region:'center',border:false" style="overflow:hidden;">
          <table id="dormitory" data-options="border:false">
          </table>
        </div>
      </div>
    </body>
  
  </html>