function searchStudent(pageNo){
	//window.location="/student/studentList.action?page="+pageNo;/*+"&studentNameToSearch="+$("#studentName").val();*/
	
	$("#data_list").empty();
	$("#paginationUl").empty();
	$("#data_list").append("<thead>"
			+"<tr>"
			+"<th width='11%'>学号</th>"
			+"<th width='12%'>姓名</th>"
			+"<th width='12%'>性别</th>"
			+"<th width='12%'>学位课成绩</th>"
			+"<th width='12%'>学位课排名</th>"
			+"<th width='12%'>全部课程成绩</th>"
			+"<th width='12%'>全部课程排名</th>"
			+"<th width='12%'>CET-4</th>"
			+"<th width='12%'>CET-6</th>"
			+"</tr>"
			+"</thead>");
	$.ajax({
		type : "post",
		url : "/student/searchStudentScoreList.action",
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
						+"<td>"+value.degreeCourseScore+"</td>"
						+"<td>"+value.degreeCourseRank+"</td>"
						+"<td>"+value.allCourseScore+"</td>"
						+"<td>"+value.allCourseRank+"</td>"
						+"<td>"+value.cet4Score+"</td>"
						+"<td>"+value.cet6Score+"</td>");
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

function uploadFile() {
	var file = $("#file").val();
	var now = new Date();
    var year = now.getFullYear();
    var month =(now.getMonth() + 1).toString();
    var day = (now.getDate()).toString();
    var hour = (now.getHours()).toString();
    var minute = (now.getMinutes()).toString();
    var second = (now.getSeconds()).toString();
    if (month.length == 1) {
        month = "0" + month;
    }
    if (day.length == 1) {
        day = "0" + day;
    }
    if (hour.length == 1) {
   	 hour = "0" + hour;
    }
    if (minute.length == 1) {
   	 minute = "0" + minute;
    }
    if (second.length == 1) {
   	 second = "0" + second;
    }
    var obId = year + month +  day+ hour +  minute+ second;
	if(file == ""){
		alert("请选要批量导入的信息文件！");
	}else{
        $.ajaxFileUpload({
			url: '/uploadServlet?type=3&objectId='+obId, //用于文件上传的服务器端请求地址
            secureuri: false, //是否需要安全协议，一般设置为false
            fileElementId: 'file', //文件上传域的ID
            dataType: 'json', //返回值类型 一般设置为json
            success: function (data, status){ //服务器成功响应处理函数
               // $("#img1").attr("alt", data.filename);
            	//uploadedFile = data.ServerFileRelativePath;
            	 var dataImport = {
             			displayname: $('#name').val(),
             	     	studentScoreBatchUpdateFile: data.ServerFileRelativePath,
             	      };
             	      $.post("/admin/batchUpdateStudentScore.action",dataImport,
             	    		function(result) {
             	            	if (result.msg) {
             	                  	alert("成绩导入成功！");
             	                  	window.location="/student/studentScoreList.action";
             	                 } else {
             	                	alert("成绩导入失败！");
             	                }
             	          },'json');
            },
            error: function (data, status, e){//服务器响应失败处理函数
                alert(e);
             }
         });
	}
}
