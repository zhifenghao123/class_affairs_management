
function publishActivity(){
	window.location="/home/activity/activity_edit.jsp";
}

function detail(activityId){
	window.location="/home/activity/activity_detail.jsp?activityId="+activityId;
}

function update(activityId){
	window.location="/home/activity/activity_update.jsp?activityId="+activityId;
}

function deleteActivity(activityId){
	$.post("/student/deleteActivity.action",{"activityId":activityId},function(data){
		if(data.msg){
			alert("删除成功！");
			window.location="/student/userPublishedActivityList.action";
	    }else{
	    	alert("删除失败！");
	    	
		}		
	},"json");
}

function participateIn(activityId){
	$.post("/admin/applyParticateActivity.action",{"activityId":activityId},function(data){
		if(data.msg){
			alert("报名成功！");
			window.location="/student/activityList.action";
	    }else{
	    	alert("报名失败！");
	    	
		}		
	},"json");
}

function appointParticipateIn(activityId){
	window.location="/home/activity/activity_appointment.jsp?activityId="+activityId;
}



function searchActivity(pageNo){
	//window.location="/activity/activityList.action?page="+pageNo;/*+"&activityNameToSearch="+$("#activityName").val();*/
	
	$("#data_list").empty();
	//$("#paginationFoot").empty();
	$("#paginationUl").empty();
	$("#data_list").append("<thead>"
			+"<tr>"
			+"<th width='40%'>活动名称</th>"
			+"<th width='20%'>活动负责人</th>"
			+"<th width='20%'>发布时间</th>"
			+"<th width='20%'>操作</th>"
			+"</tr>"
			+"</thead>");
	$.ajax({
		type : "post",
		url : "/student/searchUserPublishedActivityList.action",
		data : {
				"keyWordSearch" : $("#activityTitle").val(),
				"page" : pageNo,
				},
		async : false,
		dataType: 'json',
		success : function(data){
			var totalPage = data.totalPage;
			var begin = 0;
			var end = 0;
			if(totalPage <= 5){
				begin = 1;
				end = totalPage;
			}else{
				begin = data.currentPage-2;
				end = data.currentPage+2;
				if(begin<1){
					begin = 1;
					end = 5;
				}
				if(end>data.totalPage){
					begin = data.totalPage-4;
					end = data.totalPage;
				}
			}
			//alert(data.record.length);
			$.each(data.record,function(index,value){
				$("#data_list").append("<tr>"
						+"<td><a href='javascript:void(0)' onclick='detail("+value.activityId+")'>"+value.name+"</a></td>"
						+"<td>"+value.responsorName+"</td>"
						+"<td>"+value.publishTime+"</td>"
						+"<td><button type='button' class='btn btn-info btn-xs' data-toggle='modal' data-target='#findModal' onclick='detail("+value.activityId+")'>查看</button>"
						+"<button type='button' class='btn btn-warning btn-xs' data-toggle='modal' data-target='#updateModal' id='btn_update' onclick='updateBook()'>修改</button>"
						+"<button type='button' class='btn btn-danger btn-xs' onclick='deleteBook()'>删除</button>"
						+"<button type='button' class='btn btn-success btn-xs' onclick='appointParticipateIn("+value.activityId+")'  data-toggle='modal' data-target='#addNumModal'>批量指派</button>"
						);
				});
			
			//alert(data.currentPage);
			var headPage = "<li class='disabled'><a href='#'>第"+data.currentPage+"页/共"+data.totalPage+"页</a></li>";
			var pageFirst = "<li><a href='javascript:searchActivity(1)'>首页</a></li>";
			var upPage = " ";
			var nextPage = " ";
			if(data.currentPage == 1){
				upPage = "<li><a href='javascript:searchActivity(1)'>&laquo;</a></li>";
			}else{
				upPage = "<li><a href='javascript:searchActivity(" + (data.currentPage-1) + ")'>&laquo;</a></li>";
			}
			
			if(data.currentPage == data.totalPage){
				nextPage = "<li><a href='javascript:searchActivity(" + (data.currentPage) +")'>&raquo;</a></li>";
			}else{
				nextPage = "<li><a href='javascript:searchActivity(" + (data.currentPage+1) +")'>&raquo;</a></li>";
			}
			
			var pageMedia =" ";
			var pageLast = "<li><a href='javascript:searchActivity("+data.totalPage+")'>尾页</a></li>";
			//var exportExcel = "<li><a href='javascript:exportSearchedActivityList();'>导出Excel</a></li>";
			for(var i = begin;i <= end;i++){
				pageMedia = pageMedia + "<li><a href='javascript:searchActivity("+i+")'>"+i+"</a></li>";
			}
			
			/*$("#paginationFoot").append("<ul class='pagination'>" + headPage + pageFirst + upPage+pageMedia
					+ nextPage + pageLast + exportExcel + "</ul>");*/
			$("#paginationUl").append(headPage + pageFirst + upPage+pageMedia
					+ nextPage + pageLast);
	
		}
	});
}