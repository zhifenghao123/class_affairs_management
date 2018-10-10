$(function(){
	$.post("/student/getBlog.action",{"blogId":$("#blogId").val()},function(data){
		if(data.exist){
			$("#blogTitle").text(data.title);
			$("#publishTime").text(data.createTime);
			$("#publisherName").text(data.ownerName);
			$("#content").text(data.content);
			$("#referrenceBlogUrl").attr("href",data.referrenceBlogUrl);
			$("#referrenceBlogUrl").text(data.referrenceBlogUrl);
			
	    }else{
	    	alert("博客不存在！");
	    	
		}		
	},"json");
});