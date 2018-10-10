var infoType = 0;
$(document).ready(function() {
	infoType = $("#info_type").val();
	$.ajax({
		url : "/student/getStudentByStudentNo.action",
		type : "post",
		async : false,
		dataType : "json",
		data : {
			"studentNo" : $("#studentNo").val(),
			"infoType" : infoType,
		},
		success : function(studentInfo) {
			$("#name").val(studentInfo.name);
			switch(infoType){
			case "1":
				$("#sex").val(studentInfo.sexText);
				$("#birthday").val(studentInfo.birthday);
				$("#birthplace").val(studentInfo.birthplace);
				$("#ethnic").val(studentInfo.ethnic);
				$("#politicalLandscape").val(studentInfo.politicalLandscapeText);
				$("#IdCardNo").val(studentInfo.IdCardNo);
				$("#undergraduateCollege").val(studentInfo.undergraduateCollege);
				$("#undergraduateEnddate").val(studentInfo.undergraduateEnddate);
				break;
			case "2":
				$("#school").val(studentInfo.schoolName);
				$("#laboratoryNo").val(studentInfo.laboratoryNo);
				$("#tutorName").val(studentInfo.tutorName);
				break;
			case "3":
				break;
			case "4":
				$("#telephoneNoFrequse").val(studentInfo.telephoneNoFrequse);
				$("#telephoneNoBackup").val(studentInfo.telephoneNoBackup);
				$("#emaiNo").val(studentInfo.emaiNo);
				$("#qqNo").val(studentInfo.qqNo);
				$("#wechatNo").val(studentInfo.wechatNo);
				$("#sinaweiboNo").val(studentInfo.sinaweiboNo);
				break;
			default:
				break;
			}
		}
	});
});
