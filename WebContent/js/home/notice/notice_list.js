
function publishNotice(){
	window.location="/home/student/notice_edit.jsp";
}

function detail(noticeId){
	window.location="/home/notice/notice_detail.jsp?noticeId="+noticeId;
}

function searchNotice(pageNo){
	//window.location="/notice/noticeList.action?page="+pageNo;/*+"&noticeNameToSearch="+$("#noticeName").val();*/
	
	$("#data_list").empty();
	//$("#paginationFoot").empty();
	$("#paginationUl").empty();
	$("#data_list").append("<thead>"
			+"<tr>"
			+"<th width='40%'>通知名称</th>"
			+"<th width='20%'>发布者</th>"
			+"<th width='20%'>发布时间</th>"
			+"<th width='20%'>操作</th>"
			+"</tr>"
			+"</thead>");
	$.ajax({
		type : "post",
		url : "/student/searchNoticeList.action",
		data : {
				"keyWordSearch" : $("#noticeTitle").val(),
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
						+"<td><a href='javascript:void(0)' onclick='detail("+value.noticeId+")'>"+value.title+"</a></td>"
						+"<td>"+value.publisher+"</td>"
						+"<td>"+value.publishTime+"</td>"
						+"<td><button type='button' class='btn btn-info btn-xs' data-toggle='modal' data-target='#findModal' onclick='detail("+value.noticeId+")'>查看</button></td>"
						);
				});
			//alert(data.currentPage);
			var headPage = "<li class='disabled'><a href='#'>第"+data.currentPage+"页/共"+data.totalPage+"页</a></li>";
			var pageFirst = "<li><a href='javascript:searchNotice(1)'>首页</a></li>";
			var upPage = " ";
			var nextPage = " ";
			if(data.currentPage == 1){
				upPage = "<li><a href='javascript:searchNotice(1)'>&laquo;</a></li>";
			}else{
				upPage = "<li><a href='javascript:searchNotice(" + (data.currentPage-1) + ")'>&laquo;</a></li>";
			}
			
			if(data.currentPage == data.totalPage){
				nextPage = "<li><a href='javascript:searchNotice(" + (data.currentPage) +")'>&raquo;</a></li>";
			}else{
				nextPage = "<li><a href='javascript:searchNotice(" + (data.currentPage+1) +")'>&raquo;</a></li>";
			}
			
			var pageMedia =" ";
			var pageLast = "<li><a href='javascript:searchNotice("+data.totalPage+")'>尾页</a></li>";
			//var exportExcel = "<li><a href='javascript:exportSearchedNoticeList();'>导出Excel</a></li>";
			for(var i = begin;i <= end;i++){
				pageMedia = pageMedia + "<li><a href='javascript:searchNotice("+i+")'>"+i+"</a></li>";
			}
			
			/*$("#paginationFoot").append("<ul class='pagination'>" + headPage + pageFirst + upPage+pageMedia
					+ nextPage + pageLast + exportExcel + "</ul>");*/
			$("#paginationUl").append(headPage + pageFirst + upPage+pageMedia
					+ nextPage + pageLast);
	
		}
	});
}