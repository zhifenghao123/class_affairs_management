/**
 * 系统管理公共服务方法
 */

var qujan = qujan|| {};

(function($){  
    // 保存原有的jquery ajax;  
    var $_ajax = $.ajax;  
    $.ajax = function(options){  
        var originalSuccess,  
            mySuccess,  
            success_context;  
        if (options.success) {  
                        // save reference to original success callback  
            originalSuccess = options.success;  
            success_context = options.context ? options.context : $;  
                        // 自定义callback  
            mySuccess = function(data) {  
                            if (data['togym-access-denied']) { 
                            	if(data['togym-no-right']){
                            		if(data['togym-admin']){//跳转到后台登陆页面
                            			window.top.location.href ="/admin/login.jsp";
                            		}
                            	}
                                   return;  
                            }    
                               
                                // call original success callback                             
                originalSuccess.apply(success_context, arguments);  
            };  
                        // override success callback with custom implementation  
            options.success = mySuccess;  
        }  
          
                // call original ajax function with modified arguments  
        $_ajax.apply($, arguments);  
    };  
      
})(jQuery);  

$.fn.panel.defaults.onBeforeDestroy = function() {/* tab关闭时回收内存 */
    var frame = $('iframe', this);
    try {
        if (frame.length > 0) {
            frame[0].contentWindow.document.write('');
            frame[0].contentWindow.close();
            frame.remove();
            if ($.browser.msie) {
                CollectGarbage();
            }
        } else {
            $(this).find('.combo-f').each(function() {
                var panel = $(this).data().combo.panel;
                panel.panel('destroy');
            });
        }
    } catch (e) {
    }
};
$.fn.dialog.defaults.onBeforeDestroy = function() {/* dialog关闭时回收内存 */
    var frame = $('iframe', this);
    try {
        if (frame.length > 0) {
            frame[0].contentWindow.document.write('');
            frame[0].contentWindow.close();
            frame.remove();
            if ($.browser.msie) {
                CollectGarbage();
            }
        } else {
            $(this).find('.combo-f').each(function() {
                var panel = $(this).data().combo.panel;
                panel.panel('destroy');
            });
        }
    } catch (e) {
    }
};
//检查浏览器是否支持placeholder属性
function supports_placeholder(){
	var i = document.createElement("input");
	return "placeholder" in i;
}
// 让不支持placeholder的浏览器实现此属性
$(function(){
	
	var input_placeholder=  $("input[placeholder],textarea[placeholder]");
	
	if (input_placeholder.length !== 0 && !supports_placeholder()) {
	
		var color_place = "#A9A9A9";	
		
		$.each(input_placeholder, function(i){
			var isUserEnter = 0; // 是否为用户输入内容,placeholder允许用户的输入和默认内容一样
			var ph = $(this).attr("placeholder");
			var curColor = $(this).css("color");
			
			$(this).val(ph).css("color", color_place);
		
			$(this).focus(function(){
				if ($(this).val() == ph && !isUserEnter) $(this).val("").css("color", curColor);
			})
			.blur(function(){
				if ($(this).val() == "") {
					$(this).val(ph).css("color", color_place);
					isUserEnter = 0;
				}
			})
			.keyup(function(){
				if ($(this).val() !== ph) isUserEnter = 1;
			});
			
		});
	}
});
	//输入框提示信息（需要在节点设置tip属性）
	$(function(){
		var input_tip = $("input[tip],textarea[tip]");
		
		$.each(input_tip, function(i){
			var inUser = 0;
			var tip = $(this).attr("tip");
			var tipEle = $("<span class='blue tip'>"+tip+"</span>");
		
			$(this).focus(function(){
				if($(this).val()=="" && inUser==0){
					  $(this).after(tipEle);
					  inUser = 1;
			     }else if($(this).val()!=""){
			 			$(".tip").remove();
			 			inUser = 0;
			     }
			}).blur(function(){
				$(".tip").remove();
				inUser = 0;
			}).keyup(function(){
				if($(this).val()=="" && inUser==0){
					  $(this).after(tipEle);
					  inUser = 1;
			     }else if($(this).val()!=""){
			 		  $(".tip").remove();
			 		  inUser = 0;
			     }
			});
			
		});
	});	
/**
 * 重写easyUI panel加载数据提示信息默认值
 */	
$.extend($.fn.panel.defaults,{
	loadMsg : '数据加载中...'
});	
/**
 * 重写easyUI datagrid加载数据提示信息默认值
 */
$.extend($.fn.datagrid.defaults, {
		loadMsg : '数据加载中...'
	});
$.extend($.fn.validatebox.defaults.rules, {    
    equals: {    
        validator: function(value,param){    
            return value == $(param[0]).val();    
        },    
        message: '密码不一致'   
    }    
});
$.extend($.fn.validatebox.defaults.rules, {    
    high: {    
        validator: function(value,param){    
            return parseInt(value) > parseInt($(param[0]).val());    
        },    
        message: '上界要大于下界'   
    }    
});
$.extend($.fn.validatebox.defaults.rules, {    
    account: {    
        validator: function(value){   
            
            return /^[a-zA-Z0-9_]{1,}$/.test(value);    
        },    
        message: '请输入有效字符'   
    }    
}); 
$.extend($.fn.validatebox.defaults.rules, {    
    number: {    
        validator: function(value){   
            
            return /^\d+$/.test(value);    
        },    
        message: '请输入数字'   
    }    
}); 
$.extend($.fn.validatebox.defaults.rules, {    
    undefinedSport: {    
        validator: function(value,param){  
              
            return undefined != $(param[0]).val();    
        },    
        message: '请选择运动项目'   
    }    
});
$.extend($.fn.validatebox.defaults.rules, {    
    pwd: {    
        validator: function(value){   
            
            return /^[a-zA-Z0-9_]{1,}$/.test(value);    
        },    
        message: '密码只能为数字、英文或下划线'   
    }    
});
$.extend($.fn.validatebox.defaults.rules, {    
    phone: {    
        validator: function(value){   
            
            return /^((0\d{2,3}-\d{7,8})|(1[3584]\d{9})|(400\d{7})|(400-\d{3}-\d{4}))$/.test(value);    
        },    
        message: '请输入正确的号码如:029-88888888或13812348888'   
    }    
});
$.extend($.fn.validatebox.defaults.rules, {    
    videoUrl: {    
        validator: function(value){   
            
            return (value.indexOf("v.youku.com") != -1) || (value.indexOf("www.tudou.com") != -1) ||(value.indexOf("v.ku6.com") != -1);    
        },    
        message: '暂不支持此网站视频'   
    }    
});
qujan.modalDialog = function(opts){
	var options = $.extend({
		title : '',
		height: 400,
		width : 500,
		onClose : function(){
			$(this).dialog('destroy');
		}
	},opts);
	options.modal = true;
	if(opts.url){
		options.content = '<iframe src="' + opts.url + '" allowTransparency="true" scrolling="auto" width="100%" height="98%" frameBorder="0" ></iframe>';
	}
	return $('<div></div>').dialog(options);
};
Array.prototype.indexOf = function (val) {  
    for (var i = 0; i < this.length; i++) {  
        if (this[i] == val) {  
            return i;  
        }  
    }  
    return -1;  
}; 
//中间区域的列表弹出的内容嵌入到中间区域
qujan.addViewTab = function(opts){
              var centerTabs = parent.$('#centerTabs');
              var options = $.extend({
                    title : '',
                    content : '<iframe src="' + opts.url + '" frameborder="0" style="border:0;width:100%;height:99.2%;"></iframe>',
                    closable : true,
                    iconCls : ''
                }, opts);
                if (centerTabs.tabs('exists', options.title)) {
                    centerTabs.tabs('close', options.title);
                }
                centerTabs.tabs('add', options);
          } ;
