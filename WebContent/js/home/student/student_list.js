function searchStudent(pageNo){
	//window.location="/student/studentList.action?page="+pageNo;/*+"&studentNameToSearch="+$("#studentName").val();*/
	
	$("#data_list").empty();
	$("#paginationUl").empty();
	$("#data_list").append("<thead>"
			+"<tr>"
			+"<th width='11%'>学号</th>"
			+"<th width='9%'>姓名</th>"
			+"<th width='6%'>性别</th>"
			+"<th width='26%'>专业</th>"
			+"<th width='12%'>手机号</th>"
			+"<th width='12%'>QQ</th>"
			+"<th width='12%'>微信</th>"
			+"<th width='12%'>微博</th>"
			+"</tr>"
			+"</thead>");
	$.ajax({
		type : "post",
		url : "/student/searchStudentList.action",
		data : {
				"studentNameToSearch" : $("#studentName").val(),
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
			
			$.each(data.record,function(index,value){
				$("#data_list").append("<tr>"
						+"<td>"+value.studentNo+"</td>"
						+"<td>"+value.name+"</td>"
						+"<td>"+value.sex+"</td>"
						+"<td>"+value.majorName+"</td>"
						+"<td>"+value.telephone+"</td>"
						+"<td>"+value.qq+"</td>"
						+"<td>"+value.wechat+"</td>"
						+"<td>"+value.sinaweibo+"</td>");
				});
			//alert(data.currentPage);
			var headPage = "<li class='disabled'><a href='#'>第"+data.currentPage+"页/共"+data.totalPage+"页</a></li>";
			var pageFirst = "<li><a href='javascript:searchStudent(1)'>首页</a></li>";
			var upPage = " ";
			var nextPage = " ";
			if(data.currentPage == 1){
				upPage = "<li><a href='javascript:searchStudent(1)'>&laquo;</a></li>";
			}else{
				upPage = "<li><a href='javascript:searchStudent(" + (data.currentPage-1) + ")'>&laquo;</a></li>";
			}
			
			if(data.currentPage == data.totalPage){
				nextPage = "<li><a href='javascript:searchStudent(" + (data.currentPage) +")'>&raquo;</a></li>";
			}else{
				nextPage = "<li><a href='javascript:searchStudent(" + (data.currentPage+1) +")'>&raquo;</a></li>";
			}
			
			var pageMedia =" ";
			var pageLast = "<li><a href='javascript:searchStudent("+data.totalPage+")'>尾页</a></li>";
			var exportExcel = "<li><a href='javascript:exportSearchedStudentList();'>导出Excel</a></li>";
			for(var i = begin;i <= end;i++){
				pageMedia = pageMedia + "<li><a href='javascript:searchStudent("+i+")'>"+i+"</a></li>";
			}
			
			/*$("#paginationFoot").append("<ul class='pagination'>" + headPage + pageFirst + upPage+pageMedia
					+ nextPage + pageLast + exportExcel + "</ul>");*/
			/*$("#paginationFoot").append("<ul class='pagination'>" + headPage + pageFirst + upPage+pageMedia
					+ nextPage + pageLast + exportExcel + "</ul>");*/
			$("#paginationUl").append(headPage + pageFirst + upPage+pageMedia
					+ nextPage + pageLast + exportExcel);
	
		}
	});
}

function exportSearchedStudentList(){
	location.href="/student/exportSearchedStudentList.action?studentNameToSearch="+$("#studentName").val();
}