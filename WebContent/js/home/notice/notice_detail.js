$(function(){
	$.post("/admin/getNoticeByNoticeId.action",{"noticeId":$("#noticeId").val()},function(data){
		if(data.msg){
			$("#title").text(data.title);
			$("#publishTime").text(data.publishTime);
			$("#publisherName").text(data.publisherName);
			$("#content").text(data.content);
			if(data.enclosureFile != "null" && data.enclosureFile != null){
				$("#enclosureFile").text(data.enclosureFile);
			}else{
				$("#enclosureFile").text("");
			}
			
	    }else{
	    	alert("获取通知信息失败！");
		}		
	},"json");
});