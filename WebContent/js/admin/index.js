/**
 * 后台管理主页javascript
 */
// 左侧导航
var west_tree;
$(function() {
	west_tree = $('#tree').tree(
			{
				url : '/admin/baseControlAction.action?menu',
				animate : false,
				lines : true,
				onClick : function(node) {
					if (node.attributes && node.attributes.url && node.attributes.url.trim() != '') {
						var url = node.attributes.url;
						$.messager.progress({
							text : '数据加载中...',
							interval : 100
						});
						addTabFun({
							url : url,
							title : node.text
						});
						$.messager.progress('close');
					} else {
						if (node.state == 'closed') {
							west_tree.tree('expandAll', node.target);
						} else {
							west_tree.tree('collapseAll', node.target);
						}
					}

				},
			/*
			 * onLoadSuccess : function(node, data) { var t = $(this); if (data) {
			 * $(data).each(function(index, d) { if (this.state == 'closed') {
			 * t.tree('expandAll'); } }); } }
			 */

			});

});

function collapseAll() {
	var node = west_tree.tree('getSelected');
	if (node) {
		west_tree.tree('collapseAll', node.target);
	} else {
		west_tree.tree('collapseAll');
	}
}
function expandAll() {
	var node = west_tree.tree('getSelected');
	if (node) {
		west_tree.tree('expandAll', node.target);
	} else {
		west_tree.tree('expandAll');
	}
}
function flush() {
	west_tree.tree('reload');
}

//中部面板
var centerTabs;
$(function() {
	centerTabs = $('#centerTabs').tabs({
		border : false,
		fit : true
	});
});

function addTabFun(opts) {
	var options = $
			.extend(
					{
						title : '',
						content : '<iframe src="'
								+ opts.url
								+ '?N='
								+ Math.random()
								+ '" frameborder="0" style="border:0;width:100%;height:99.2%;"></iframe>',
						closable : true,
						iconCls : ''
					}, opts);
	if (centerTabs.tabs('exists', options.title)) {
		centerTabs.tabs('close', options.title);
	}
	centerTabs.tabs('add', options);
};

//退出系统
function logout() {
	$.post("/j_spring_security_logout", {}, function(data) {
		top.location.href = "/admin/login.jsp";
	});
}

//修改密码
function changePwd() {
	addTabFun({
		url : "/admin/changepwd.jsp",
		title : "修改密码"
	});
}
