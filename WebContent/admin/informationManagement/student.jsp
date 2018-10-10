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
          var student_datagrid;
          $(function() {
            student_datagrid = $("#student").datagrid({
              url: "/admin/getStudentList.action",
              fit: true,
              pagination: true,
              fitColumns: true,
              nowrap: false,
              border: false,
              singleSelect: true,
              idField: 'studentId',
              columns: [[
              {
	             field: 'studentId',
	             title: '',
	             width: 40,
	             align:'center',
	             formatter : function(value,row,index) {
					var str="";
					//if(row.status=="2"){
						str="<input type='checkbox' name='studentCheckbox'" +"onclick='checke(\""+row.studentId+"\")'>";
					//}
					return str;
				}
	          },
	          {
                field: 'studentNo',
                title: '学号',
                width: 40
              },
              {
                field: 'name',
                title: '姓名',
                width: 40
              },
              {
                field: 'sex',
                title: '性别',
                width: 40
              },
              {
                 field: 'gradeNo',
                 title: '年级',
                 width: 40
               },
               {
                   field: 'schoolName',
                   title: '学院',
                   width: 80
                 },
               {
                 field: 'majorName',
                 title: '专业',
                 width: 70
               },
               {
                  field: 'executiveClassName',
                  title: '班级',
                  width: 40
               },
               {
  	             field: 'stateText',
  	             title: '在学状态',
  	             width: 40,
  	          },
			   {
                field: 'operation',
                title: '操作',
                width: 150,
                formatter: function(value, row, index) {
                  var stateButton;
                  if (row.stateText == "") {
                    stateButton = '<a href="javascript:void(0)" class="forbid" onclick="change_state(\'' + row.studentId + '\',2);"></a>';
                  } else {
                    stateButton = '<a href="javascript:void(0)" class="open" onclick="change_state(\'' + row.studentId + '\',1);"></a>';
                  }
                  var str = '<a href="javascript:void(0)" class="edit" onclick="edit_student(\'' + row.studentId + '\');"></a>';
                  str += '<a href="javascript:void(0)" class="delete" onclick="delete_student(\'' + row.studentId + '\');">|</a>';
                  str += '<a href="javascript:void(0)" class="reset" onclick="reset_password(\'' + row.studentId + '\');">|</a>';
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
                $('.reset').linkbutton({
                    text: '重置密码',
                    iconCls: 'icon-no',
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
            student_datagrid.datagrid('getPager').pagination({
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
          
         $(function() {
			$.post("/admin/getAllSchoolList.action", "",
            	function(data) {
                     $.each(data,function(index, value) {
                        $("#schoolNo").append("<option value ="+value.schoolNo+">"+value.schoolNo+"   "+value.name+"</option>");
                     });
                 },
            "json");	
			$.post("/admin/getAllGradeList.action", "",
	            	function(data) {
	                     $.each(data,function(index, value) {
	                        $("#gradeNo").append("<option value ="+value.gradeNo+">"+value.gradeNo+"</option>");
	                     });
	                 },
	            "json");
			
			$.post("/admin/getExecutiveClassesBySchoolNo.action", {gradeNo:"",schoolNo:""},
                 	function(data) {
                          $.each(data,function(index, value) {
                             $("#executiveClassNo").append("<option value ="+value.name+">"+value.name+"</option>");
                          });
                      },
                 "json");
		});
          
         function changeMajorAndExcutiveClassBySelectedSchool(selectedSchoolNo){
        	 $("#majorNo option").remove();
        	 $("#executiveClassNo option").remove();
        	 $("#majorNo").append("<option value =''>— — —</option>");
        	 $.post("/admin/getMajorsBySchoolNo.action", {schoolNo:selectedSchoolNo},
                 	function(data) {
                          $.each(data,function(index, value) {
                             $("#majorNo").append("<option value ="+value.majorNo+">"+value.majorNo+"   "+value.name+"</option>");
                          });
                      },
                 "json");
        	 $("#executiveClassNo").append("<option value =''>— — —</option>");
        	 $.post("/admin/getExecutiveClassesBySchoolNo.action", {gradeNo:$("#gradeNo").val(),schoolNo:selectedSchoolNo},
                 	function(data) {
                          $.each(data,function(index, value) {
                             $("#executiveClassNo").append("<option value ="+value.name+">"+value.name+"</option>");
                          });
                      },
                 "json");
        	 
        	 search_student();
         } 
         
         function changeExcutiveClassBySelectedGrade(selectedGradeNo){
        	 $("#executiveClassNo option").remove();
        	 $("#executiveClassNo").append("<option value =''>— — —</option>");
        	 $.post("/admin/getExecutiveClassesBySchoolNo.action", {gradeNo:selectedGradeNo,schoolNo:$("#schoolNo").val()},
                 	function(data) {
                          $.each(data,function(index, value) {
                             $("#executiveClassNo").append("<option value ="+value.name+">"+value.name+"</option>");
                          });
                      },
                 "json");
        	 
        	 search_student();
         }
          
          
          var reset_student = function() {
            $("#search_student_form")[0].reset();
            student_datagrid.datagrid('load', {});
          };
          var search_student = function() {
            student_datagrid.datagrid('load', {
              studentNoToSearch: $("#studentNo").val(),
              studentNameToSearch: $("#studentName").val(),
              gradeToSearch: $("#gradeNo").val(),
              schoolToSearch: $("#schoolNo").val(),
              majorToSearch: $("#majorNo").val(),
              executiveClassToSearch: $("#executiveClassNo").val(),
            });
          };
          var addStudentDialog;
          var add_student = function() {
            addStudentDialog = parent.qujan.modalDialog({
              title: '新增专业',
              url: '/admin/informationManagement/studentForm.jsp',
              height: 450,
              width: 600,
              buttons: [{
                text: '返回',
                handler: function() {
                  addStudentDialog.dialog('close');
                }
              },
              {
                text: '确定',
                handler: function() {
                  addStudentDialog.find('iframe')[0].contentWindow.submitForm(addStudentDialog, student_datagrid, parent.$);
                }
              }]
            });
          };
          var editStudentDialog;
          var edit_student = function(studentId) {
            editStudentDialog = parent.qujan.modalDialog({
              title: '修改专业',
              url: '/admin/informationManagement/studentForm.jsp?studentId=' + studentId,
              height: 450,
              width: 600,
              buttons: [{
                text: '返回',
                handler: function() {
                  editStudentDialog.dialog('close');
                }
              },
              {
                text: '确定',
                handler: function() {
                  editStudentDialog.find('iframe')[0].contentWindow.submitForm(editStudentDialog, student_datagrid, parent.$);
                }
              }]
            });
          };
          var delete_student = function(id) {
            parent.$.messager.confirm('删除确认', '您确认要删除吗?',
            function(r) {
              if (r) {
                $.post('/admin/deleteStudentById.action', {
                  id: id
                },
                function(msg) {
                  if (msg.exsit) {
                    parent.$.messager.alert('提示', '对不起，用户正在使用，请先解除关联关系！', 'info');
                  } else {

                    if (msg.success) {
                      student_datagrid.datagrid('reload');
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
          var change_state = function(studentId, state) {
            $.post('/admin/updateStudentState.action', {
              'id': studentId,
              'state': state
            },
            function(msg) {
              if (msg.success) {
                student_datagrid.datagrid('reload');
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
          var reset_password = function(studentId) {
              $.post('/admin/resetStudentPassword.action', {
                'studentId': studentId,
              },
              function(data) {
                if (data.msg) {
                  student_datagrid.datagrid('reload');
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
          
          var batchAddStudentDialog;
          var batch_add_student = function(){
        	  editStudentDialog = parent.qujan.modalDialog({
                  title: '批量导入学生',
                  url: '/admin/informationManagement/studentBatchAddForm.jsp',
                  height: 250,
                  width: 500,
                  buttons: [{
                    text: '返回',
                    handler: function() {
                      editStudentDialog.dialog('close');
                    }
                  },
                  {
                    text: '执行批量导入',
                    handler: function() {
                      editStudentDialog.find('iframe')[0].contentWindow.submitForm(editStudentDialog, student_datagrid, parent.$);
                    }
                  }]
                });
          };
          
		function allSelect(ifAllSelected){
			if(ifAllSelected)
				$("input[name='studentCheckbox']").prop("checked",true);
			else
				$("input[name='studentCheckbox']").prop("checked",false);
         }
        </script>
    </head>
    
    <body>
      <div class="easyui-layout" fit="true" border="false">
        <div data-options="region:'north'" border="false" style="height:80px;overflow:hidden;">
          <form id="search_student_form">
            <div class="datagrid-toolbar search_1">
              <span>学号:</span>
              <input id="studentNo" type="text" size="7" onkeyup="search_student()"/>
              <span>姓名:</span>
              <input id="studentName" type="text" size="7" onkeyup="search_student()"/>
              <span>年级:</span>
              <select class="select1 black" id="gradeNo" style="width:70px;" onchange="changeExcutiveClassBySelectedGrade($(this).val())">
              	<option value =''>— — —</option>
              </select>
              <span>所在学院:</span>
              <select class="select1 black" id="schoolNo" style="width:140px;" onchange="changeMajorAndExcutiveClassBySelectedSchool($(this).val())">
              	<option value =''>— — —</option>
              </select>
              <span>所在专业:</span>
              <select class="select1 black" id="majorNo" style="width:140px;" onchange="search_student()"> 
              	<option value =''>— — —</option>
              </select>
               <span>所在班级:</span>
              <select class="select1 black" id="executiveClassNo" style="width:100px;" onchange="search_student()"> 
              	<option value =''>— —</option>
              </select>
              
              
              <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search"
              style="margin-left:30px;" onclick="search_student();">
                查询
              </a>
              <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-redo"
              style="margin-left:10px;" onclick="reset_student();">
                清空
              </a>
              <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
              style="margin-left:10px;" onclick="add_student();">
                新增
              </a>
               <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
              style="margin-left:10px;" onclick="batch_add_student();">
                批量新增
              </a>
            </div>
            
            <div class="datagrid-toolbar search_1">
              <input type="checkbox" name="allChecked" id="allChecked" onchange="allSelect($(this).is(':checked'))"/><span>全选/全不选</span>
            </div>
            
          </form>
        </div>
        <div data-options="region:'center',border:false" style="overflow:hidden;">
          <table id="student" data-options="border:false">
          </table>
          
                批量新增
             
        </div>
      </div>
    </body>
  
  </html>