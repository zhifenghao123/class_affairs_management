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
          var dormitoryId = '${param.id}';
          var submitForm = function($dialog, $datagrid, $pjquery) {
            if ($('form').form('validate')) {
            	$pjquery.messager.progress({ text: '数据提交中...', interval: 100});
              var url, checked = [];
              if (dormitoryId.length > 0) {
                url = '/admin/updateDormitory.action';
              } else {
                url = '/admin/addDormitory.action';
              }
              $('input:checkbox:checked').each(function() {
                checked.push($(this).val());
              });
              var data = {
                dormitoryId: dormitoryId,
                roomNo: $("#roomNo").val(),
                apartmentId: $("#apartmentId").val(),
              };
              $.post(url, data,
              	function(result) {
            	  $pjquery.messager.progress('close');
                	//if (result.exsit) {
                  	//	parent.$.messager.alert('提示', '学院名称已存在', 'info');
               		//} else {
                  		if (result.msg) {
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
                	//}
              	},
              'json');
            }
          };
          $(function() {
        	  $.ajax({
  				url : "/admin/getAllApartmentList.action",
  				type : "post",
  				async : false,
  				dataType : "json",
  				data : {},
  				success : function(data) {
  					 $.each(data,function(index, value) {
  						$("#apartmentId").append("<option value ="+value.apartmentId+">"+value.apartmentNo+"</option>");
  	         		});
  				}
  			});
            if (dormitoryId.length > 0) {
              $.ajax({
        			url : "/admin/getDormitoryById.action",
        			type : "post",
        			async : false,
        			dataType : "json",
        			data : {"id": dormitoryId},
        			success : function(result) {
        				 $("#roomNo").val(result.roomNo);
                         $("#gender").val(result.gender);
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
                   宿舍号：
                </td>
                <td colspan="5">
                  <span>
                    <input id="roomNo" type="text" class="putin1 black easyui-validatebox" required="true"
                    validType="length[2,7]" style="width:230px;" />
                  </span>
                </td>
              </tr>
              <tr>
                <td width="77" class="tdleft gray3">
                  所在公寓：
                </td>
                <td colspan="5">
                 <label>
                    <select class="select1 black" id="apartmentId" style="width:237px;">
                     	<!-- <option value="1">男</option>
                     	<option value="2">女</option> -->
                    </select>
                  </label>
                </td>
              </tr>
            </table>
          </form>
        </div>
      </div>
    </body>
  
  </html>