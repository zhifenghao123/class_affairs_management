var flag = new Array();
var flag_first = 0 ;
var flag_update = 0 ;
var ueditor;
var tagArray = new Array();
//正整数校验
var positiveIntegerCheck =  /^\d+$/;
//var priceTest = /^(\d{1,9})(\.\d{1,2})?$/;
var objectId;
$(function(){
	objectId=$("#blogId").val();
	UEDITOR_CONFIG.imageUrl += "type=2&objectId=" + objectId;
    UEDITOR_CONFIG.catcherUrl += "type=2&objectId=" + objectId;   
	ueditor =  UE.getEditor('content',{autoHeightEnabled:false});
});

function addTag(){
	//$("#selectedCon").append("<li name='"+iname+"' id='"+regionId+"_selected' value='"+regionId+"'><a href='javascript:void(0)'>"+regionName+"&nbsp;&nbsp;<img src='/img/manage/close2.jpg' width='10' height='10' onclick='removeSelected(\"#regionCondition\","+regionId+",\""+iname+"\");'/></a></li>");
	var tag= $("#tagInput").val();
	if(tag != ""){
		//var str = " <div style = 'float:left'>"+ $("#tagInput").val() +"</div>";
		//$("#tagHasInputed").append(str);
		var str = "<li id='"+tag+"_Li' value='"+tag+"' style='float:left;list-style-type:none;margin:5px 3px; white-space:nowrap; border:1px solid #ccc; padding:2px;'><a href='javascript:void(0)'>"+tag+"&nbsp;&nbsp;<img src='/img/manage/close2.jpg' width='10' height='10' onclick='removeTag(\""+tag+"\");'/></a></li>";
		$("#tagHasInputedUl").append(str);
	}else{
		alert("请输入标签内容");
	}	
}

function removeTag(tag){
	$("#tagHasInputedUl li[value="+tag+"][id="+tag+"_Li]").remove();
}

function submit(){
	   var content = ueditor.getContent();
		$("#content").val(content);
		//alert(ueditor.getContent());
		//alert(ueditor.getContent().replace(/(<\/?(?!img)[^>\/]*)\/?>/gi,""));
	    var contentSimple =$.trim(ueditor.getContent().replace(/(<\/?(?!img)[^>\/]*)\/?>/gi,"").replace(/&nbsp;/g,""));		
	    //alert(contentSimple);
	    contentSimple = removeHTMLTag(contentSimple);
	    //alert(contentSimple);
		var title = $("#title").val();
		var tags = new Array();
		var tagslist = $("#tagHasInputedUl li");
		/*for(var i = 0; i < tagslist.length; i++) {
			var ob = tagslist[i];
			alert(ob.attr("value"));
			//tags.push(args)
		}
		return;*/
		//tagArray = tags.split(",");
		var referrenceBlogUrl = $("#referrenceBlogUrl").val();
		//alert(contentSimple);
		$("#buttonSave").hide();
		//alert(tagArray);
		var blog = {
				"blogId":objectId,
			"title" : title,
           //"tags":tagArray,
           "content" : contentSimple,
           "referrenceBlogUrl" : referrenceBlogUrl,
		}; 
		//return;
		$.post("/admin/addBlog.action",blog,function(data){
			if(data.msg){
				alert("新增成功！");
				window.location="/blog/myBlogList.action";
		    }else{
		    	alert("新增失败！");
		    	$("#buttonSave").show();
				$("#buttonPublish").show();
			}		
		},"json");
}

function showResult(){
	    var content = ueditor.getContent();
		$("#content").val(content);
	    var contentSimple =$.trim(ueditor.getContent().replace(/(<\/?(?!img)[^>\/]*)\/?>/gi,"").replace(/&nbsp;/g,""));		
	    contentSimple = removeHTMLTag(contentSimple);

		var title = $("#title").val();
		var linkUrl = $("#linkUrl").val();
		
		$("#buttonSave").hide();
		var blog = {
			"title" : title,
            "linkUrl" : linkUrl,
            "content" : content,
		}; 
		$.post("/admin/addBlog.action",blog,function(data){
			if(data.success == "1"){
				alert("新增成功！");
			//	window.location="/cardList.action";
		    }else if(data.success == "0"){
		    	alert("新增失败！");
		    	$("#buttonSave").show();
				$("#buttonPublish").show();
			}		
		},"json");
}

function cardTypeElse(type){
	var flag=true;
	switch(type){
	case "2"://次卡
		var timeCard = $("#timeCard").val();
		var timeLength = /^.{1,5}$/;
		if(timeCard==""){
			var tips = "<span class='picture_invalid'><span class='word_invalid'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次卡卡型不能为空</span></span>";
			$("#errorMsg").empty();
			$("#errorMsg").append(tips);
			flag=false;
			return flag;
		}else if(!positiveIntegerCheck.test(timeCard)){
			var tips = "<span class='picture_invalid'><span class='word_invalid'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;卡型请输入大于0的正整数!</span></span>";
			$("#errorMsg").empty();
			$("#errorMsg").append(tips);
			flag=false;
			return flag;
		}else if(!timeLength.test(timeCard)){
			var tips = "<span class='picture_invalid'><span class='word_invalid'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;卡型长度超长!</span></span>";
			$("#errorMsg").empty();
			$("#errorMsg").append(tips);
			flag=false;
			return flag;
		}else{
			$("#errorMsg").empty();
		}
		return flag;
		break;
	case "3"://时段卡
		var end = $("#end").val();
		if(end=="请选择"){
			var tips = "<span class='picture_invalid'><span class='word_invalid'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时段卡结束时间不能为空</span></span>";
			$("#errorMsg").empty();
			$("#errorMsg").append(tips);
			flag=false;
			return flag;
		}else{
			$("#errorMsg").empty();
		}
		var price = $("#price").val();
		var priceTest = /^((\d{1,5})|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
		
		if(price==""){
			var tips = "<span class='picture_invalid'><span class='word_invalid'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计费单位不能为空</span></span>";
			$("#errorMsg").empty();
			$("#errorMsg").append(tips);
			flag=false;
			return flag;
		}else if(!priceTest.test(price)){
			var tips = "<span class='picture_invalid'><span class='word_invalid'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计费单位请输入一个大于0的正整数或者小数(小数点后最多为两位)</span></span>";
			$("#errorMsg").empty();
			$("#errorMsg").append(tips);
			flag=false;
			return flag;
		}else if(!priceTest.test(price)){
			var tips = "<span class='picture_invalid'><span class='word_invalid'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计费单位在5位数字以内！</span></span>";
			$("#errorMsg").empty();
			$("#errorMsg").append(tips);
			flag=false;
			return flag;
		}else{
			$("#errorMsg").empty();
		}
		return flag;
		break;
	case "5"://课时卡
		var pariodCardSelect = $("#pariod_card_select").val();
		var pariodCardSelectLength = /^.{1,5}$/;
		if(pariodCardSelect==""){
			var tips = "<span class='picture_invalid'><span class='word_invalid'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;课时卡卡型不能为空</span></span>";
			$("#errorMsg").empty();
			$("#errorMsg").append(tips);
			flag=false;
			return flag;
		}else if(!positiveIntegerCheck.test(pariodCardSelect)){
			var tips = "<span class='picture_invalid'><span class='word_invalid'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;卡型请输入大于0的正整数!</span></span>";
			$("#errorMsg").empty();
			$("#errorMsg").append(tips);
			flag=false;
			return flag;
		}else if(!pariodCardSelectLength.test(pariodCardSelect)){
			var tips = "<span class='picture_invalid'><span class='word_invalid'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;卡型长度超长!</span></span>";
			$("#errorMsg").empty();
			$("#errorMsg").append(tips);
			flag=false;
			return flag;
		}else{
			$("#errorMsg").empty();
		}
		return flag;
		break;
	}
}

function sportV(sport){
	var flag=true;
	if(sport.length==0){
			var tips = "<span class='picture_invalid'><span class='word_invalid'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请选择运动项目</span></span>";
			$("#sportTip1").empty();
			$("#sportTip1").append(tips);
			flag=false;
			return flag;
	}else{
		$("#sportTip1").empty();
	}
	return flag;
}

function nameV(name){
	var flag=true;
	var nameLength = /^.{1,20}$/;
	var nameBlank = /^\S+$/;
	if(name==""){
		var tips = "<span class='picture_invalid'><span class='word_invalid'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;卡片名称不能为空</span></span>";
		$("#nameTip").empty();
		$("#nameTip").append(tips);
		flag=false;
		return flag;
	}else if(!nameLength.test(name)){
		var tips = "<span class='picture_invalid'><span class='word_invalid'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请输入1-20个字符!</span></span>";
		$("#nameTip").empty();
		$("#nameTip").append(tips);
		flag=false;
		return flag;
	}else if(!nameBlank.test(name)){
		var tips = "<span class='picture_invalid'><span class='word_invalid'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;不能以空格符开头或结尾!</span></span>";
		$("#nameTip").empty();
		$("#nameTip").append(tips);
		flag=false;
		return flag;
	}else{
		$("#nameTip").empty();
	}
	return flag;
}

function telV(tel){
	var flag=true;
	var telFormat = /^((0\d{2,3}-\d{7,8})|(1[35847]\d{9}))$/;
	if(tel==""){
		var tips = "<span class='picture_invalid'><span class='word_invalid'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;电话不能为空</span></span>";
		$("#telTip").empty();
		$("#telTip").append(tips);
		flag=false;
		return flag;
	}else if(!telFormat.test(tel)){
		var tips = "<span class='picture_invalid'><span class='word_invalid'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;输入不合法,格式为：13812348888或者029-88888888</span></span>";
		$("#telTip").empty();
		$("#telTip").append(tips);
		flag=false;
		return flag;
	}else{
		$("#telTip").empty();
	}
	return flag;
}

function contentV(content){
	var flag=true;
	if(content==""){
		var tips = "<span class='picture_invalid'><span class='word_invalid'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;卡片简介不能为空</span></span>";
		$("#contentTip").empty();
		$("#contentTip").append(tips);
		flag=false;
		return flag;
	}else{
		$("#contentTip").empty();
	}
	return flag;
}

function promptsV(prompts){
	var flag=true;
	if(prompts==""){
		var tips = "<span class='picture_invalid'><span class='word_invalid'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商家提示不能为空</span></span>";
		$("#promptsTip").empty();
		$("#promptsTip").append(tips);
		flag=false;
		return flag;
	}else{
		$("#promptsTip").empty();
	}
	return flag;
}

function criterionPriceV(criterion_price){
	var price = $("#price").val();
	var flag=true;
	var criterionPriceTest = /^((\d{1,5})|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
	if(criterion_price==""){
		var tips = "<span class='picture_invalid'><span class='word_invalid'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门市价不能为空</span></span>";
		$("#criterion_priceTip").empty();
		$("#criterion_priceTip").append(tips);
		flag=false;
		return flag;
	}else if(!criterionPriceTest.test(criterion_price)){
		var tips = "<span class='picture_invalid'><span class='word_invalid'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门市价为5位数字以内的正整数！</span></span>";
		$("#criterion_priceTip").empty();
		$("#criterion_priceTip").append(tips);
		flag=false;
		return flag;
	}else if(criterion_price==0){
		var tips = "<span class='picture_invalid'><span class='word_invalid'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门市价不能为0！</span></span>";
		$("#criterion_priceTip").empty();
		$("#criterion_priceTip").append(tips);
		flag=false;
		return flag;
	}else if(price>criterion_price){
		var tips = "<span class='picture_invalid'><span class='word_invalid'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门市价不能小于计费单位价！</span></span>";
		$("#criterion_priceTip").empty();
		$("#criterion_priceTip").append(tips);
		flag=false;
		return flag;
	}else{
		$("#criterion_priceTip").empty();
	}
	return flag;
}
function togymPriceV(togym_price){
	var flag= true;
	var togymPriceTest = /^((\d{1,5})|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
	if(togym_price==""){
		var tips = "<span class='picture_invalid'><span class='word_invalid'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;健友价不能为空</span></span>";
		$("#togym_priceTip").empty();
		$("#togym_priceTip").append(tips);
		flag=false;
		return flag;
	}else if(!togymPriceTest.test(togym_price)){
		var tips = "<span class='picture_invalid'><span class='word_invalid'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;健友价为5位数字以内的正整数！</span></span>";
		$("#togym_priceTip").empty();
		$("#togym_priceTip").append(tips);
		flag=false;
		return flag;
	}else if(togym_price==0){
		var tips = "<span class='picture_invalid'><span class='word_invalid'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;健友价不能为0！</span></span>";
		$("#togym_priceTip").empty();
		$("#togym_priceTip").append(tips);
		flag=false;
		return flag;
	}else{
		$("#togym_priceTip").empty();
	}
	return flag;
}
function discountV(discount){
	var flag= true;
	if(discount>10){
		var tips = "<span class='picture_invalid'><span class='word_invalid'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;健友价不能大于门市价！</span></span>";
		$("#discountTip").empty();
		$("#discountTip").append(tips);
		flag=false;
		return flag;
	}else{
		$("#discountTip").empty();
	}
	return flag;
}
//function photoArrayV(){
//	var flag = true;
//	if($("#SWFUPLOAD_PHOTO_LIST_DIV input[name='SWFUPLOAD_PHOTO_LIST_Array']:eq(0)").val()==null){
//		var tips = "<span class='picture_invalid'><span class='word_invalid'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;健身卡图片不能为空！</span></span>";
//		$("#photoArrayTip").empty();
//		$("#photoArrayTip").append(tips);
//		flag=false;
//		return flag;
//	}else{
//		$("#photoArrayTip").empty();
//	}
//	return flag;
//}
function alertMessage(content){
	var tipsContent="<span>"+content+"</span>";
	$("#tipsContent").append(tipsContent);
	openRemindDiv();
}
function removeHTMLTag(str) {
    str = str.replace(/<\/?[^>]*>/g,''); //去除HTML tag
    str = str.replace(/[ | ]*\n/g,'\n'); //去除行尾空白
    str = str.replace(/\n[\s| | ]*\r/g,'\n'); //去除多余空行
    str=str.replace(/&nbsp;/ig,'');//去掉&nbsp;
    return str;
}
function applyscopeV(applyscope){
	var flag= true;
	if(applyscope==undefined||applyscope==""||null==applyscope){
		var tips = "<span class='picture_invalid'><span class='word_invalid'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;适用范围不能为空！</span></span>";
		$("#applyscopeTip").empty();
		$("#applyscopeTip").append(tips);
		flag=false;
		return flag;
	}else{
		$("#applyscopeTip").empty();
	}
	return flag;
}


