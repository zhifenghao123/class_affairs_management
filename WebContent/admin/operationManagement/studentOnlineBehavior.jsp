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
          var onlineBehavior_datagrid;
          $(function() {
            onlineBehavior_datagrid = $("#onlineBehavior").datagrid({
              url: "/admin/getOnlineBehaviorList.action",
              fit: true,
              pagination: true,
              fitColumns: true,
              nowrap: false,
              border: false,
              singleSelect: true,
              idField: 'onlineBehaviorId',
              columns: [[{
                field: 'userNo',
                title: '学生账号',
                width: 150
              },
              {
                field: 'userName',
                title: '学生姓名',
                width: 150
              },
              {
                  field: 'userIp',
                  title: '操作Ip',
                  width: 150
                },
                {
                    field: 'onlineBehaviorName',
                    title: '操作名称',
                    width: 150
                  },
                  {
                      field: 'recordTime',
                      title: '记录时间',
                      width: 150
                    },
             

              ]],
              onBeforeLoad: function(data) {
                parent.$.messager.progress({
                  text: '数据加载中...',
                  interval: 100
                });
              },
              onLoadSuccess: function(data) {
                parent.$.messager.progress('close');
              }

            });
            onlineBehavior_datagrid.datagrid('getPager').pagination({
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

          var search_onlineBehavior = function() {
            onlineBehavior_datagrid.datagrid('load', {
            	userNo: $("#userNo").val(),
              type: $("#type").val()
            });
          };

        
        </script>
    </head>
    
    <body>
      <div class="easyui-layout" fit="true" border="false">
        <div data-options="region:'north'" border="false" style="height:40px;overflow:hidden;">
          <form id="search_onlineBehavior_form">
            <div class="datagrid-toolbar search_1">
              <span>
                用户学号:
              </span>
              <input id="userNo" type="text" size="20" onkeyup="search_onlineBehavior()"/>
              <span>在线操作类型:</span>
              <select class="select1 black" id="type" style="width:140px;" onchange="search_onlineBehavior();">
              	<option value ='0'>— — —</option>
              	<option value ='1'>登录</option>
              	<option value ='2'>修改信息</option>
              </select>
              <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search"
              style="margin-left:30px;" onclick="search_onlineBehavior();">
                查询
              </a>
            </div>
          </form>
        </div>
        <div data-options="region:'center',border:false" style="overflow:hidden;">
          <table id="onlineBehavior" data-options="border:false">
          </table>
        </div>
      </div>
    </body>
  
  </html>