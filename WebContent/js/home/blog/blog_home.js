
function writeBlog(){
	window.location="/home/blog/blogEdit.jsp";
}

function toMyBlogHome(){
	window.location="/blog/myBlogList.action";
}

function deleteBlog(blogId){
	alert(blogId);
	$.post("/admin/deleteBlog.action",{"blogId":blogId},function(data){
		if(data.msg){
			alert("删除成功！");
			window.location="/blog/myBlogList.action";
	    }else{
	    	alert("删除失败！");
	    	
		}		
	},"json");
}

function editBlog(blogId){
	window.location="/home/blog/blog_update.jsp?blogId="+blogId;
}

function detail(blogId){
	window.location="/home/blog/blog_detail.jsp?blogId="+blogId;
}