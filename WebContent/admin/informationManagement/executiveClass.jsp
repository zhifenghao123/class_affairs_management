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
          var executiveClass_datagrid;
          $(function() {
            executiveClass_datagrid = $("#executiveClass").datagrid({
              url: "/admin/getExecutiveClass.action",
              fit: true,
              pagination: true,
              fitColumns: true,
              nowrap: false,
              border: false,
              singleSelect: true,
              idField: 'executiveClassId',
              columns: [[{
                field: 'name',
                title: '行政班级名称',
                width: 100
              },
              {
                  field: 'gradeNo',
                  title: '年级',
                  width: 70
                },
              {
                field: 'schoolNo',
                title: '所在学院代码',
                width: 65
              },
                {
                    field: 'schoolName',
                    title: '所在学院名称',
                    width: 100
                  },
                  {
                      field: 'executiveClassGroup',
                      title: '学院行政班级管理组号',
                      width: 100
                    },
                {
                    field: 'monotorName',
                    title: '班长姓名',
                    width: 65
                  },
                  {
                      field: 'monotorNo',
                      title: '班长学号',
                      width: 100
                    },

              {
                field: 'executiveClassId',
                title: '操作',
                width: 150,
                formatter: function(value, row, index) {
                  var stateButton;
                  if (row.state == 1) {
                    stateButton = '<a href="javascript:void(0)" class="forbid" onclick="change_state(\'' + value + '\',2);"></a>';
                  } else {
                    stateButton = '<a href="javascript:void(0)" class="open" onclick="change_state(\'' + value + '\',1);"></a>';
                  }
                  var str = '<a href="javascript:void(0)" class="edit" onclick="edit_executiveClass(\'' + value + '\');"></a>';
                  str += '<a href="javascript:void(0)" class="delete" onclick="delete_executiveClass(\'' + value + '\');">|</a>';
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
            executiveClass_datagrid.datagrid('getPager').pagination({
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
          var reset_executiveClass = function() {
            $("#search_executiveClass_form")[0].reset();
            executiveClass_datagrid.datagrid('load', {});
          };
          var search_executiveClass = function() {
            executiveClass_datagrid.datagrid('load', {
              name: $("#name").val()
            });
          };
          var addExecutiveClassDialog;
          var add_executiveClass = function() {
            addExecutiveClassDialog = parent.qujan.modalDialog({
              title: '新增专业',
              url: '/admin/informationManagement/executiveClassForm.jsp',
              height: 450,
              width: 600,
              buttons: [{
                text: '返回',
                handler: function() {
                  addExecutiveClassDialog.dialog('close');
                }
              },
              {
                text: '确定',
                handler: function() {
                  addExecutiveClassDialog.find('iframe')[0].contentWindow.submitForm(addExecutiveClassDialog, executiveClass_datagrid, parent.$);
                }
              }]
            });
          };
          var editExecutiveClassDialog;
          var edit_executiveClass = function(id) {
            editExecutiveClassDialog = parent.qujan.modalDialog({
              title: '修改专业',
              url: '/admin/informationManagement/executiveClassForm.jsp?id=' + id,
              height: 450,
              width: 600,
              buttons: [{
                text: '返回',
                handler: function() {
                  editExecutiveClassDialog.dialog('close');
                }
              },
              {
                text: '确定',
                handler: function() {
                  editExecutiveClassDialog.find('iframe')[0].contentWindow.submitForm(editExecutiveClassDialog, executiveClass_datagrid, parent.$);
                }
              }]
            });
          };
          var delete_executiveClass = function(id) {
            parent.$.messager.confirm('删除确认', '您确认要删除吗?',
            function(r) {
              if (r) {
                $.post('/admin/deleteExecutiveClassById.action', {
                	executiveClassId: id
                },
                function(msg) {
                    if (msg.success) {
                      executiveClass_datagrid.datagrid('reload');
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
          var change_state = function(executiveClassId, state) {
            $.post('/admin/updateExecutiveClassState.action', {
              'id': executiveClassId,
              'state': state
            },
            function(msg) {
              if (msg.success) {
                executiveClass_datagrid.datagrid('reload');
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
          <form id="search_executiveClass_form">
            <div class="datagrid-toolbar search_1">
              <span>
                专业名称:
              </span>
              <input id="name" type="text" size="20" />
              <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search"
              style="margin-left:30px;" onclick="search_executiveClass();">
                查询
              </a>
              <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-redo"
              style="margin-left:10px;" onclick="reset_executiveClass();">
                清空
              </a>
              <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
              style="margin-left:10px;" onclick="add_executiveClass();">
                新增
              </a>
            </div>
          </form>
        </div>
        <div data-options="region:'center',border:false" style="overflow:hidden;">
          <table id="executiveClass" data-options="border:false">
          </table>
        </div>
      </div>
    </body>
  
  </html>