


$('#logging_table').datagrid({
	url:rootPath + '/service/log/searchlog',
	method:'post',
	fit:true,
	fitColumns:true,
	rownumbers:true,
	border:false,
	autoRowHeight:true,
	pagination:true,
	singleSelect:false,
	checkOnSelect:true,
	nowrap:false,
	toolbar:'#logging_toolbar',
	pageSize:10,
	columns:[[
	          {field:'_ck',align:'center',checkbox:true},
//	          {field:'dwbm',title:'单位编码',align:'center',width:''},
	          {field:'dwmc',title:'单位名称',align:'center',width:150},
	          {field:'gh',title:'工号',align:'center',width:''},
	          {field:'mc',title:'姓名',align:'center',width:80},
	          {field:'rzlx',title:'日志类型',align:'center',width:'',formatter:formatterFn},
	          {field:'rznr',title:'内容',align:'left',width:500},
	          {field:'time',title:'时间',align:'center',width:150}
	          ]]
});


function deleteSome(){
	
	var checked = $('#logging_table').datagrid('getChecked');
	var idArray = "";
	if(checked.length < 1){
		top.msgAlertInfo("请选择需要删除的日志!");
		return ;
	}
	
	$.messager.confirm('确认','您确认想要删除记录吗?',function(r){
		if(!r){
			return ;
		}
		for(i=0;i<checked.length;i++){
			idArray = idArray+checked[i].id+",";
		}
		$.ajax({
			url:rootPath + '/service/log/deletelog',
			method:'post',
			dataType:'json',
			data:{
				idArray:idArray
			},
			success:function (data){
				if(data.msg==1){
					top.msgAlertInfo("删除成功！");
				}
				$('#logging_table').datagrid('reload');
			}
		});
	});
}

function deleteAll(){
	$.messager.confirm('确认','您确认想要删除吗?',function(r){
		if(!r){
			return ;
		}
		$.ajax({
			url:rootPath + '/service/log/deleteall',
			method:'post',
			dataType:'json',
			success:function(data){
				if(data.msg==1){
					top.msgAlertInfo("删除成功！");
				}
				$('#logging_table').datagrid('reload');
			}
		});
	});

}

function formatterFn(value,row,index){
	if(row.rzlx=='1'){
		return '<a>操作日志</a>';
	}else if(row.rzlx=='2'){
		return '<a style="color:red">错误日志</a>';
	}
}

///////////////////////////////////////搜索

var mdwbm = '';
var mrznr = '';
var mrzlx = '';
var mczr = '';

function clearLoad(){
	ztreedata.dwbm = "";
	$("#citySel").attr("value", "");
	$("#srznr").val("");
	$("#sczr").val("");
	$("#srzlx").combobox('setValue',"");
	mfuzhi();
	$('#logging_table').datagrid({
		queryParams: {
		}
	});
}

function mfuzhi(){
	mdwbm = ztreedata.dwbm;
	mrznr = $("#srznr").val();
	mczr = $("#sczr").val();
	mrzlx = $("#srzlx").combobox('getValue');
}

function msearch(){
	
	mfuzhi();
	
	$('#logging_table').datagrid({
		queryParams: {
			'dwbm': mdwbm,
			'rznr': mrznr,
			'xm'  : mczr,
			'rzlx': mrzlx
		}
	});
}





////////////////////////////////////////单位树

//加载单位选择列表
$.getJSON(rootPath + '/service/tree/dwtree?dwbm='+top.currentUserInfo.dwbm+'&showall=false',
		function(res) {
			var result = [];
			result.push(res);
			$(document).ready(function() {
				$.fn.zTree.init($("#treeDemo"), setting, result);
			});
			$(".textbox-text").css("padding-top", '1px');
			$("#srzlx").combobox({
				onChange : msearch
			});
			$("#srzlx").combobox('setValue',"");
		});


var isShowMenu = false;
var isUpdata = false;
var hcpzdg = $("#hcpz_dg");
var ztreedata = {
	dwbm : "",
	name : ""
}
var setting = {
	data : {
		key : {
			name : "text"
		}
	},
	view : {
		showIcon : false,
		dblClickExpand : false,
		selectedMulti : false
	},
	callback : {
		beforeClick : beforeClick,
		onClick : onClick
	}
};

function beforeClick(treeId, treeNode) {
	var check = (treeNode && treeNode.valueSelect);
	if (check)
		hideMenu();
	return check;
}

function onClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree
			.getSelectedNodes();
	v = "";
	var name = nodes[0].text;
	var dwbm = nodes[0].id;
	ztreedata.name = name;
	ztreedata.dwbm = dwbm;
	v = name;
	var cityObj = $("#citySel");
	cityObj.attr("value", v);
	
	
	msearch();
}

function showMenu() {
	// console.log(isShowMenu);
	if (isShowMenu == false) {
		isShowMenu = true;
		// console.log('show: ' +isShowMenu);
		var cityObj = $("#citySel");
		var cityOffset = $("#citySel").offset();
		$("#menuContent").fadeIn("fast");
		$("body").bind("mousedown", onBodyDown);
	} else
		hideMenu();
}
function hideMenu() {
	isShowMenu = false;
	// console.log('hide: ' +isShowMenu);
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(
			event.target).parents("#menuContent").length > 0)) {
		hideMenu();
	}
}
