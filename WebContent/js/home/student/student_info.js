var infoType = "0";
/*$(document).ready(function() {
	if($("#info_type").val() == 2){
		$.post("/admin/getAllSchoolList.action", "",
          		function(data) {
           			 $.each(data,function(index, value) {
              			//$("#schoolId").append("<li><input  type='checkbox' value='" + value.authorityId + "' />" +'&nbsp;&nbsp;'+  value.name + "</li>");
              			$("#school").append("<option value ="+value.schoolNo+">" + value.name + "</option>");
            		});
          		},
          "json");
		
		$.post("/admin/getAllGradeList.action", "",
          		function(data) {
           			 $.each(data,function(index, value) {
              			//$("#schoolId").append("<li><input  type='checkbox' value='" + value.authorityId + "' />" +'&nbsp;&nbsp;'+  value.name + "</li>");
              			$("#gradeNo").append("<option value ="+value.gradeNo+">" + value.gradeNo + "</option>");
            		});
          		},
          "json");
		
		$.post("/admin/getAllExecutiveClassList.action", "",
          		function(data) {
           			 $.each(data,function(index, value) {
              			//$("#schoolId").append("<li><input  type='checkbox' value='" + value.authorityId + "' />" +'&nbsp;&nbsp;'+  value.name + "</li>");
              			$("#executiveClassName").append("<option value ="+value.name+">" + value.name + "</option>");
            		});
          		},
          "json");
	}
	if($("#info_type").val() == 3){
		$.post("/admin/getAllApartmentList.action", "",
          		function(data) {
           			 $.each(data,function(index, value) {
              			//$("#apartmentId").append("<li><input  type='checkbox' value='" + value.authorityId + "' />" +'&nbsp;&nbsp;'+  value.name + "</li>");
              			$("#apartmentNo").append("<option value ="+value.apartmentId+">"+value.apartmentNo+"</option>");
            		});
          		},
          "json");
	}
});*/

function infoPreparations(){

	if($("#info_type").val() == 2){
		$.ajax({
			url : "/admin/getAllSchoolList.action",
			type : "post",
			async : false,
			dataType : "json",
			data : {},
			success : function(allSchool) {
				$.each(allSchool,function(index, value) {
          			$("#school").append("<option value ="+value.schoolNo+">" + value.name + "</option>");
        		});
			}
		});
		$.ajax({
			url : "/admin/getAllGradeList.action",
			type : "post",
			async : false,
			dataType : "json",
			data : {},
			success : function(allGrade) {
				 $.each(allGrade,function(index, value) {
           			$("#gradeNo").append("<option value ="+value.gradeNo+">" + value.gradeNo + "</option>");
         		});
			}
		});
	}
	if($("#info_type").val() == 4){
		$.ajax({
			url : "/admin/getAllApartmentList.action",
			type : "post",
			async : false,
			dataType : "json",
			data : {},
			success : function(allApartment) {
				$.each(allApartment,function(index, value) {
	      			$("#apartmentNo").append("<option value ="+value.apartmentNo+">"+value.apartmentNo+"</option>");
	    		});
			}
		});
	}
}

$(document).ready(function() {
	infoPreparations();
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
				$("#sex").val(studentInfo.sexCode);
				$("#birthday").val(studentInfo.birthday);
				$("#birthplace").val(studentInfo.birthplace);
				$("#homeAddress").val(studentInfo.homeAddress);
				$("#ethnic").val(studentInfo.ethnic);
				$("#politicalLandscape").val(studentInfo.politicalLandscapeCode);
				$("#IdCardNo").val(studentInfo.IdCardNo);
				$("#undergraduateCollege").val(studentInfo.undergraduateCollege);
				$("#undergraduateEnddate").val(studentInfo.undergraduateEnddate);
				$("#accessType").val(studentInfo.accessTypeCode);
				$("#cultivationType").val(studentInfo.cultivationTypeCode);
				break;
			case "2":
				$("#school").val(studentInfo.schoolNo);
				$("#gradeNo").val(studentInfo.gradeNo);
				getExecutiveClassListBySchoolNo(studentInfo.schoolNo,studentInfo.gradeNo)
				$("#executiveClassName").val(studentInfo.executiveClassName);
				getMajorListBySchoolNo(studentInfo.schoolNo);
				$("#majorNo").val(studentInfo.majorNo);
				getDepartmentsBySchoolNo(studentInfo.schoolNo);
				$("#departmentNo").val(studentInfo.departmentNo);
				getLaboratorysBySchoolNo(studentInfo.schoolNo);
				$("#laboratoryNo").val(studentInfo.laboratoryNo);
				$("#tutorName").val(studentInfo.tutorName);
				break;
			case "3"://alert(studentInfo.schoolNo);
				$("#degreeCourseScore").val(studentInfo.degreeCourseScore);
				$("#degreeCourseRank").val(studentInfo.degreeCourseRank);
				$("#allCourseScore").val(studentInfo.allCourseScore);
				$("#allCourseRank").val(studentInfo.allCourseRank);
				$("#cet4Score").val(studentInfo.cet4Score);
				$("#cet6Score").val(studentInfo.cet6Score);
				break;
			case "4":
				$("#apartmentNo").val(studentInfo.apartmentNo);
				$("#roomNo").val(studentInfo.roomNo);
				break;
			case "5":
				$("#telephoneNoFrequse").val(studentInfo.telephoneNoFrequse);
				$("#telephoneNoFrequseIsPublic").val(studentInfo.telephoneNoFrequseIsPublic);
				$("#telephoneNoBackup").val(studentInfo.telephoneNoBackup);
				$("#telephoneNoBackupIsPublic").val(studentInfo.telephoneNoBackupIsPublic);
				$("#emaiNo").val(studentInfo.emaiNo);
				$("#emaiNoIspublic").val(studentInfo.emaiNoIspublic);
				$("#qqNo").val(studentInfo.qqNo);
				$("#qqNoIsPublic").val(studentInfo.qqNoIsPublic);
				$("#wechatNo").val(studentInfo.wechatNo);
				$("#wechatNoIsPublic").val(studentInfo.wechatNoIsPublic);
				$("#sinaweiboNo").val(studentInfo.sinaweiboNo);
				$("#sinaweiboNoIsPublic").val(studentInfo.sinaweiboNoIsPublic);
				break;
			default:
				break;
			}
		}
	});
});

function updateInfo(){
	$("#saveUpdatedStudentInfoBtn" + infoType).css("display","block");
	switch(infoType){
	case "1":
		/*$("#sex").removeAttr("disabled");
		$("#birthday").removeAttr("disabled");
		$("#birthday").removeAttr("disabled");*/
		$("#birthplace").removeAttr("disabled");
		$("#homeAddress").removeAttr("disabled");
		/*$("#ethnic").removeAttr("disabled");*/
		$("#politicalLandscape").removeAttr("disabled");
		/*$("#IdCardNo").removeAttr("disabled");*/
		$("#undergraduateCollege").removeAttr("disabled");
		$("#undergraduateEnddate").removeAttr("disabled");
		/*$("#cultivationType").removeAttr("disabled");
		$("#accessType").removeAttr("disabled");*/
		break;
	case "2":
		/*$("#school").removeAttr("disabled");
		$("#gradeNo").removeAttr("disabled");
		$("#executiveClassName").removeAttr("disabled");
		$("#majorNo").removeAttr("disabled");*/
		$("#departmentNo").removeAttr("disabled");
		$("#laboratoryNo").removeAttr("disabled");
		$("#tutorName").removeAttr("disabled");
		break;
	case "3":
		/*$("#degreeCourseScore").removeAttr("disabled");
		$("#degreeCourseRank").removeAttr("disabled");
		$("#allCourseScore").removeAttr("disabled");
		$("#allCourseRank").removeAttr("disabled");*/
		$("#cet4Score").removeAttr("disabled");
		$("#cet6Score").removeAttr("disabled");
		break;
	case "4":
		$("#apartmentNo").removeAttr("disabled");
		$("#roomNo").removeAttr("disabled");
		break;
	case "5":
		$("#telephoneNoFrequse").removeAttr("disabled");
		$("#telephoneNoFrequseIsPublic").removeAttr("disabled");
		$("#telephoneNoBackup").removeAttr("disabled");
		$("#telephoneNoBackupIsPublic").removeAttr("disabled");
		$("#emaiNo").removeAttr("disabled");
		$("#emaiNoIspublic").removeAttr("disabled");
		$("#qqNo").removeAttr("disabled");
		$("#qqNoIsPublic").removeAttr("disabled");
		$("#wechatNo").removeAttr("disabled");
		$("#wechatNoIsPublic").removeAttr("disabled");
		$("#sinaweiboNo").removeAttr("disabled");
		$("#sinaweiboNoIsPublic").removeAttr("disabled");
		break;
	default:
		break;
	}
	$("#editStudentInfoBtn" + infoType).css("display","none");
}

function saveUpdateInfo(){
	var studentInfo;
	switch(infoType){
		case "1":
			studentInfo = {
				"infoType":infoType,
				"studentNo":$("#studentNo").val(),
				"sex":$("#sex").val(),
				"birthday":$("#birthday").val(),
				"birthplace":$("#birthplace").val(),
				"homeAddress":$("#homeAddress").val(),
				"ethnic":$("#ethnic").val(),
				"politicalLandscape":$("#politicalLandscape").val(),
				"IdCardNo":$("#IdCardNo").val(),
				"undergraduateCollege":$("#undergraduateCollege").val(),
				"undergraduateEnddate":$("#undergraduateEnddate").val(),
				"cultivationType":$("#cultivationType").val(),
				"accessType":$("#accessType").val(),
			};
			break;
		case "2":
			studentInfo = {
				"infoType":infoType,
				"studentNo":$("#studentNo").val(),
				"school":$("#school").val(),
				"gradeNo":$("#gradeNo").val(),
				"executiveClassName":$("#executiveClassName").val(),
				"majorNo":$("#majorNo").val(),
				"departmentNo":$("#departmentNo").val(),
				"laboratoryNo":$("#laboratoryNo").val(),
				"tutorName":$("#tutorName").val(),
			};
			break;
		case "3":
			studentInfo = {
				"infoType":infoType,
				"studentNo":$("#studentNo").val(),
				"degreeCourseScore":$("#degreeCourseScore").val(),
				"degreeCourseRank":$("#degreeCourseRank").val(),
				"allCourseScore":$("#allCourseScore").val(),
				"allCourseRank":$("#allCourseRank").val(),
				"cet4Score":$("#cet4Score").val(),
				"cet6Score":$("#cet6Score").val(),
				"tutorName":$("#tutorName").val(),
			};
			break;
		case "4":
			studentInfo = {
				"infoType":infoType,
				"studentNo":$("#studentNo").val(),
				"apartmentNo":$("#apartmentNo").val(),
				"roomNo":$("#roomNo").val(),
			};
			break;
		case "5":
			studentInfo = {
				"infoType":infoType,
				"studentNo":$("#studentNo").val(),
				"telephoneNoFrequse":$("#telephoneNoFrequse").val(),
				"telephoneNoFrequseIsPublic":$("#telephoneNoFrequseIsPublic").val(),
				"telephoneNoBackup":$("#telephoneNoBackup").val(),
				"telephoneNoBackupIsPublic":$("#telephoneNoBackupIsPublic").val(),
				"emaiNo":$("#emaiNo").val(),
				"emaiNoIspublic":$("#emaiNoIspublic").val(),
				"qqNo":$("#qqNo").val(),
				"qqNoIsPublic":$("#qqNoIsPublic").val(),
				"wechatNo":$("#wechatNo").val(),
				"wechatNoIsPublic":$("#wechatNoIsPublic").val(),
				"sinaweiboNo":$("#sinaweiboNo").val(),
				"sinaweiboNoIsPublic":$("#sinaweiboNoIsPublic").val(),
			};
			break;
		default:
			studentInfo = {
				"infoType":infoType,
			};
			break;
	}
	 $.post("/student/updateStudentInfo.action",studentInfo,
		function(result) {
			if (result.msg == true) {
				alert("修改信息成功");
				switch(infoType){
				case "1":
					$("#sex").attr("disabled","disabled");
					$("#birthday").attr("disabled","disabled");
					$("#birthday").attr("disabled","disabled");
					$("#birthplace").attr("disabled","disabled");
					$("#homeAddress").attr("disabled","disabled");
					$("#ethnic").attr("disabled","disabled");
					$("#politicalLandscape").attr("disabled","disabled");
					$("#IdCardNo").attr("disabled","disabled");
					$("#undergraduateCollege").attr("disabled","disabled");
					$("#undergraduateEnddate").attr("disabled","disabled");
					$("#cultivationType").attr("disabled","disabled");
					$("#accessType").attr("disabled","disabled");
					break;
				case "2":
					$("#departmentNo").attr("disabled","disabled");
					$("#laboratoryNo").attr("disabled","disabled");
					$("#tutorName").attr("disabled","disabled");
					break;
				case "3":
					$("#cet4Score").attr("disabled","disabled");
					$("#cet6Score").attr("disabled","disabled");
					break;
				case "4":
					$("#apartmentNo").attr("disabled","disabled");
					$("#roomNo").attr("disabled","disabled");
					break;
				case "5":
					$("#telephoneNoFrequse").attr("disabled","disabled");
					$("#telephoneNoFrequseIsPublic").attr("disabled","disabled");
					$("#telephoneNoBackup").attr("disabled","disabled");
					$("#telephoneNoBackupIsPublic").attr("disabled","disabled");
					$("#emaiNo").attr("disabled","disabled");
					$("#emaiNoIspublic").attr("disabled","disabled");
					$("#qqNo").attr("disabled","disabled");
					$("#qqNoIsPublic").attr("disabled","disabled");
					$("#wechatNo").attr("disabled","disabled");
					$("#wechatNoIsPublic").attr("disabled","disabled");
					$("#sinaweiboNo").attr("disabled","disabled");
					$("#sinaweiboNoIsPublic").attr("disabled","disabled");
					break;
				default:
					break;
				}
				$("#editStudentInfoBtn" + infoType).css("display","none");
				$("#saveUpdatedStudentInfoBtn" + infoType).css("display","none");
			}else{
				alert("修改信息失败");
			}
		},
     'json');
}

function getMajorListBySchoolNo(schoolNo){
	$.ajax({
		url : "/admin/getMajorsBySchoolNo.action",
		type : "post",
		async : false,
		dataType : "json",
		data : {"schoolNo":schoolNo},
		success : function(allMajor) {
			$.each(allMajor,function(index, value) {
      			$("#majorNo").append("<option value ="+value.majorNo+">" + value.name + "</option>");
    		});
		}
	});
}

function getExecutiveClassListBySchoolNo(schoolNo,gradeNo){
	$.ajax({
		url : "/admin/getAllExecutiveClassList.action",
		type : "post",
		async : false,
		dataType : "json",
		data : {"gradeNo":gradeNo,
			"schoolNo":schoolNo
			},
		success : function(allExecutiveClass) {
			$.each(allExecutiveClass,function(index, value) {
      			$("#executiveClassName").append("<option value ="+value.name+">" + value.name + "</option>");
    		});
		}
	});
}
function getDepartmentsBySchoolNo(schoolNo){
	$.ajax({
		url : "/admin/getDepartmentsBySchoolNo.action",
		type : "post",
		async : false,
		dataType : "json",
		data : {"schoolNo":schoolNo},
		success : function(allDepartment) {
			 $.each(allDepartment,function(index, value) {
	   			$("#departmentNo").append("<option value ="+value.name+">" + value.name + "</option>");
	 		});
		}
	});
}
function getLaboratorysBySchoolNo(schoolNo){
	$.ajax({
		url : "/admin/getLaboratorysBySchoolNo.action",
		type : "post",
		async : false,
		dataType : "json",
		data : {"schoolNo":schoolNo},
		success : function(allLaboratory) {
			 $.each(allLaboratory,function(index, value) {
	   			$("#laboratoryNo").append("<option value ="+value.laboratoryNo+">" + value.laboratoryNo + "</option>");
	 		});
		}
	});
}
