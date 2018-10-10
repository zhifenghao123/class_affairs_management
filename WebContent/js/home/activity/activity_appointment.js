var flag = new Array();
var flag_first = 0 ;
var flag_update = 0 ;
var ueditor;
var tagArray = new Array();
//正整数校验
var positiveIntegerCheck =  /^\d+$/;
var objectId;
$(function(){
	
	$.get("/student/getActivityById.action",{"activityId":$("#activityId").val()},function(data){
		/*if(data.exist){*/
			$("#name").val(data.name);
			$("#responsorNo").val(data.responsorNo);
			$("#starTime").val(data.starTime);
			$("#endTime").val(data.endTime);
	
	   /* }else{
	    	alert("博客不存在！");
	    	
		}	*/	
	},"json");
	
	$.post("/student/getStudentSimpleInfoList.action",{},function(data){
		$.each(data,function(index, value) {
  			//$("#studentList").append("<label style='display:block;'><input type='checkbox'>"+value.name+"</labe");
  			$("#studentList").append("<label'><input type='checkbox' name='studentCheckbox' value='"+value.studentNo+"'>"+value.name+"</labe>");
		});
	},"json");
	
});

function clearTipInfo() {
	if ($('#responsorNo').val() == "负责人学号") {
		$('#responsorNo').css('color', '#000000');
		$('#responsorNo').val("");
	}
}

function ifNull() {
	if ($('#responsorNo').val() == "") {
		$('#responsorNo').css('color', '#ccc');
		$('#responsorNo').val("负责人学号");
	}else{
		$.post("/student/getStudentByStudentNo.action",{"studentNo":$('#responsorNo').val(),"infoType":0},function(data){
			if(data.exist){
				$("#responsorName").val(data.name);
		    }else{
		    	$("#responsorName").val("该用户不存在！");
			}		
		},"json");
	}
}

function submit(){
	var obj=document.getElementsByName('studentCheckbox'); //选择所有name="'test'"的对象，返回数组 
	//取到对象数组后，我们来循环检测它是不是被选中 
	var studentArray = new Array();; 
	for(var i=0; i<obj.length; i++){ 
		if(obj[i].checked) 
			//s+=obj[i].value+','; //如果选中，将value添加到变量s中 
			studentArray.push(obj[i].value);
	} 
	alert(studentArray);
	var activityId = $("#activityId").val();
	$.post(
		"/admin/appointParticipation.action",
		{
			"activityId":activityId,
			"studentParticipants":studentArray,
		},function(data){
			if(data.msg){
				alert("指派成功！");
				window.location="/activity/activityList.action";
		    }else{
		    	alert("新增失败！");
		    	}		
		},"json");
}

