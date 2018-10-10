<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%>
  <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
  <html>
    
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>
      </title>
      <%@include file="/admin/included.jsp" %>
        <script>
          var region_datagrid;
          $(function() {
            region_datagrid = $("#region").datagrid({
              url: "/admin/getRegion.action",
              fit: true,
              pagination: true,
              pageSize:20,
              fitColumns: true,
              nowrap: false,
              border: false,
              singleSelect: true,
              idField: 'regionId',
              columns: [[{
                field: 'code',
                title: '区域编码',
                width: 150
              },
              {
                field: 'name',
                title: '区域名',
                width: 100
              },
               {
                 field: 'englishName',
                 title: '区域英文名',
                 width: 100
               },
             {
                 field: 'state',
                 title: '状态',
                 width: 100,
                 formatter: function(value, row, index) {
                	 if(value == 0){
                		 return '未开放';
                	 }else{
                		 return '已开放';
                	 }
                 }
               },
              {
                field: 'regionId',
                title: '操作',
                width: 100,
                formatter: function(value, row, index) {
                	var str = '';
                	if(row.state == 0){
                  		str= '<a href="javascript:void(0)" class="open" onclick="changeState(\'' + value + '\',1);"></a>';
                	}else{
                		str= '<a href="javascript:void(0)" class="close" onclick="changeState(\'' + value + '\',0);"></a>';
                	}
                  str += '<a href="javascript:void(0)" class="delete" onclick="delete_region(\'' + value + '\');"></a>';
                  return str;
                }

              }]],
              onLoadSuccess: function(data) {
                
           	    $('.open').linkbutton({
                     text: '开放',
                     iconCls: 'icon-ok',
                     plain: true
                  });
                $('.close').linkbutton({
                     text: '关闭',
                     iconCls: 'icon-undo',
                     plain: true
                   });
                $('.delete').linkbutton({
                  text: '删除',
                  iconCls: 'icon-cancel',
                  plain: true
                });
              }
            });
            region_datagrid.datagrid('getPager').pagination({
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
            
            $.get("/getProvinceAll.action",{"date":Math.random()},function(provinces){
        		
        			 $(provinces).each(function(index,province){
        	    		 $("<option value='"+province.id+"'>"+province.name+"</option>").appendTo("#province");		        		
        	    	 }); 		        	 
        	},"json");
            
          });
          function changeProvice(){	
        		if($("#province").val()=="-1"){
        			$("#city").empty();
        			$("<option value='-1'>所有城市</option>").appendTo("#city");
        			region_datagrid.datagrid('load',{});
        		}else{
        			$.get("/getRegiterallCity.action",{"date":Math.random(),"parentId":$("#province").val()},function(citys){
        					$("#city").empty();
        					$("<option value='-1'>所有城市</option>").appendTo("#city");
        		    		$(citys).each(function(index,city){
        		     			$("<option value='"+city.id+"'>"+city.name+"</option>").appendTo("#city");
        		     		});
        			},"json");
        			region_datagrid.datagrid('load',{parentId:$("#province").val()});
        		}
        	}
          function changeCity(){
        	  if($("#city").val()=="-1"){
        		  region_datagrid.datagrid('load',{parentId:$("#province").val()});
        	  }else{
        		  region_datagrid.datagrid('load',{parentId:$("#city").val()});
        	  }
          }
          var addRegionDialog;
          var add_region= function(id) {
        	  addRegionDialog = parent.qujan.modalDialog({
              fit:true,
              title: '新增区域',
              url: '/admin/operation/addRegion.jsp?id=' + id,
              buttons: [{
                text: '返回',
                handler: function() {
                	addRegionDialog.dialog('close');
                }
              },
              {
                  text: '确定',
                  handler: function() {
                	  addRegionDialog.find('iframe')[0].contentWindow.submitForm(addRegionDialog, region_datagrid, parent.$);
                  }
                }]
            });
          };
          var delete_region = function(id) {
            parent.$.messager.confirm('删除确认', '您确认要删除吗?',
            function(r) {
              if (r) {
                $.post('/admin/deleteRegionById.action', {
                  id: id
                },
                function(msg) {

                    if (msg.success) {
                      region_datagrid.datagrid('reload');
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
          var changeState = function(id,state){
        	  var message = '';
        	  if(state ==1)
        		  message='您确认要开放吗?';
        	  else
        		  message='您确认要关闭吗?'; 
        	     parent.$.messager.confirm('确认', message,
        	            function(r) {
        	              if (r) {
        	                $.post('/admin/updateRegionState.action', {
        	                  id: id,
        	                  state:state
        	                },
        	                function(msg) {

        	                    if (msg.success) {
        	                      region_datagrid.datagrid('reload');
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
        	              }
        	            });
          }
        </script>
    </head>
    
    <body>
      <div class="easyui-layout" fit="true" border="false">
      <div data-options="region:'north'" border="false" style="height:40px;overflow:hidden;">
            <div class="datagrid-toolbar search_1">
              <span>
                省/直辖市:
              </span>
              <select id="province"  onchange="changeProvice()" style="width:100px;">
             	<option value="-1">所有省</option>
              </select>
              <span style="margin-left:10px;">
                市:
              </span>
              <select id="city"  onchange="changeCity()" style="width:100px;">
              <option value="-1">所有城市</option>
              </select>
              <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
              style="margin-left:10px;" onclick="add_region();">
                新增
              </a>
            </div>
        </div>
        <div data-options="region:'center'"  border="false" style="overflow:hidden;">
          <table id="region">
          </table>
        </div>
      </div>
    </body>
  
  </html>