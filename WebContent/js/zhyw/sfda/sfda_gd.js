
$(function(){
	console.log("---进入档案-归档的流程JS--");
	initData();
});

function openSfdaGdPage(){
	parent.ifeblick();
	$("#sfda_gd_div").fadeIn("fast");
	//装载审批人信息,组装审批人的树（方法引用于common.js）
	var input_id = "sfda_dagd_spr";  //
	var dwbm = null;
	var gh = null;
	if(top.currentUserInfo){
		dwbm = top.currentUserInfo.dwbm;
		gh = top.currentUserInfo.gh;
	}
	getAllRyOfDwBmTree(input_id,dwbm,gh,null,null);
	
	//获取最新公示列表信息
	showSfdaGsxx();
	
	
	//获取异议信息列表
}

function commonCloseDiv(div_id) {
	parent.ifenone();
	$("#"+div_id).fadeOut("fast");
}

function initData(){
	
	
}


function getDataTable(table_id,queryUrl,method,toolbar,queryParams,columnsData){
	$('#'+table_id).datagrid({
		url : queryUrl,
		method : method,
		toolbar : toolbar,
		striped : true,
		selectOnCheck : true,
		checkOnSelect : true,
		queryParams : queryParams,
		columns : columnsData
	});
}

function showSfdaGsxx(){
	
	var table_id = "sfda_gs_table";
	var queryUrl = "";
	var method = "get";
	var queryParams = {};
	var toolbar = "<div><span style='font-size:14px;font-family:'微软雅黑';'>档案-公示信息</span></div>";
	var columnsData = [[
		{field : 'id',title : '公示id',hidden : true},
		{field : 'dagzid',title : '档案id',hidden : true},
		{field : 'kssj',title : '公示开始日期',width : '30%',align : 'center'},
		{field : 'jssj',title : '公示结束日期',width : '30%',align : 'center'},
		{field : 'czrgh',title : '公示人工号',hidden : true,align : 'center'},
		{field : 'gsxx',title : '备注',width : '40%',align : 'center'}]];
	
	getDataTable(table_id,queryUrl,method,toolbar,queryParams,columnsData);
}

