/*********************************************系统管理在编人数js(隶属于业务工作考核)**************************************************************************/

//加载dataGrid 并用jquery EasyUI进行显示

$(function(){
	if (top.currentUserInfo) {
		if("2"!=top.currentUserInfo.dwjb){
			$("#addZbrs").css("display","none");
		}
	}

    $(".addZbrsIcon").linkbutton({
        iconCls : "icon-add"
    });

    $("#zbrsReload").linkbutton({
        iconCls : "icon-reload"
    });
});

var url = rootPath + '/service/zbrsController/zbrsSelectPageList';

if (top.currentUserInfo) {
	if ("2"==top.currentUserInfo.dwjb) {
		$("#ywgzZbrsTable").datagrid({
			// width:'100%',
			toolbar: '#zbrsToolbar',
			rownumbers:true,
			loadMsg:'数据加载中，请稍等。。。',
			pagination:true,
            fitColumns: true,
			border: true,
            striped: true,
			pageNumber:1,
			pageSize:15,
			pageList:[5,10,15,20],
			method:'get',
			url:url,
			columns:[[
			          {field:'dwmc',title:'单位名称',align:'center',width:'30%'},
			          {field:'zbrs',title:'在编干警人数（人）',align:'center',width:'30%'},
			          {field:'formatter',title:'操作',align:'center',width:'36%',formatter:function(value,row,index){
						   var data = JSON.stringify(row);
					       return "<a class='table_czan_ys' id='zbrsEdit' onclick='editData("+data+")'>修改</a>"
							    + "<a class='table_czan_ys' id='zbrsDel' onclick='delData("+data+")'>删除</a>";
						}
			          }
			      ]]
		});
	}else{
		$("#ywgzZbrsTable").datagrid({
			// width:'100%',
            toolbar: '#zbrsToolbar',
			rownumbers:true,
			loadMsg:'数据加载中，请稍等。。。',
			pagination:true,
            fitColumns: true,
            striped: true,
			pageNumber:1,
			pageSize:15,
			pageList:[5,10,15,20],
			method:'get',
			url:url,
			columns:[[
			          {field:'dwmc',title:'单位名称',align:'center',width:'55%'},
			          {field:'zbrs',title:'在编干警人数(人)',align:'center',width:'42%'}
			      ]]
		});
	}
}


//添加确认按钮点击
function addYwgzZbrs(){
	
	var dwbm = $("#ywgzZbrsDwbm").val();
	var zbrs = $("#ywgzZbrsNum").val();
//	alert(dwbm+"---------"+zbrs);
//	return ;
	
	if(dwbm==""||zbrs==""){
		top.msgAlertInfo("请选择单位，填写在编干警人数！");
		return;
	}
	
	top.msgProgressTip("正在处理……");
	
	$.ajax({
		type : "post",
		url : rootPath + '/service/zbrsController/zbrsInsertData',
		async : false,
		data : {
			dwbm : dwbm,
			zbrs : zbrs
		},
		dataType : 'json',
		success : function(data) {
			
			top.msgProgressTipClose();
			$("#ywgzZbrsDwbm").val("");
			$("#ywgzZbrsNum").val("");
			top.msgAlertInfo(data.msg);
            $('.right_com_zbrs').css('display', ' block');
            $('.right_coml_zbrs').css('display', ' none');
            clearLoad();
		}
		
	});
}


//修改按钮点击
function editData(data){
	
	//清空输入框的值，然后再赋值 
	// $('.right_li').removeClass('right_li');
	// $(this).addClass('right_li');
	$('.right_com_zbrs').css('display', ' none');
	$('.right_coml_zbrs').css('display', ' block');
//	debugger;
// 	$("#addZbrs").text("修改在编干警");
// 	$("#addZbrs").addClass('right_li')
	$("#addfgld_zbrs").css('display', 'none');
	$("#updatefgld_zbrs").css('display', 'block');
	
//	alert(data.dwbm+"-----"+data.zbrs);
	$("#ywgzZbrsNum").val(data.zbrs);//在编人数赋值
	//在编单位赋值
	ztreedata1.dwbm = data.dwbm;
	ztreedata1.name = data.dwmc;
	$("#citySel1_zbrs").attr("value", data.dwmc);
	$("#ywgzZbrsDwbm").val(data.dwbm);
	$("#ywgzZbrsId").val(data.id);
}


//修改确定的点击 
function updateYwgzZbrs(){
//	debugger;
	var dwbm = $("#ywgzZbrsDwbm").val();
	var ywgzZbrsNum = $("#ywgzZbrsNum").val();
	
	if(dwbm==""||ywgzZbrsNum==""){
		top.msgAlertInfo("请选择单位，填写在编人数！");
		return;
	}
	
	top.msgProgressTip("正在处理……");
	
	$.ajax({
		type : "post",
		url : rootPath + '/service/zbrsController/zbrsUpdateData',
		async : false,
		data : {
			id : $("#ywgzZbrsId").val(),
			dwbm : dwbm,
			zbrs : ywgzZbrsNum
		},
		dataType : 'json',
		success : function(data) {
			
			top.msgProgressTipClose();
			$("#ywgzZbrsDwbm").val("");
			$("#ywgzZbrsNum").val("");
			top.msgAlertInfo(data.msg);
            $('.right_com_zbrs').css('display', 'block');
            $('.right_coml_zbrs').css('display', 'none');
            clearLoad();
		}
		
	});
}




//删除点击 
function delData(data){
//	console.info(data);
	var id = data.id;
	$.messager.confirm('确认','删除数据不可恢复，继续请点击确定！',function(r){    
	    if (r){  
	    	$.ajax({
	    		type : "post",
	    		url : rootPath + '/service/zbrsController/zbrsDeleteData',
	    		async : false,
	    		data : {
	    			id : id
	    		},
	    		dataType : 'json',
	    		success : function(data) {
	    			
	    			top.msgAlertInfo(data.msg);
	    			$('#ywgzZbrsTable').datagrid('reload');    // 重新载入当前页面数据
	    		}
	    		
	    	});
	    }    
	});
}


/////***********************************************************************单位Ztree start***********************************************************************************************************************////

var isShowMenu = false;
var isUpdata = false;
var zbrsdg = $("#ywgzZbrsTable");
var zbrsDwbm = ''; //登录人单位编码

//加载单位选择列表
$.getJSON(rootPath + '/service/tree/dwtree?dwbm='+sdwbm+'&showall=true',
		function(res) {
			var result = [];
			result.push(res);
			$(document).ready(function() {
				$.fn.zTree.init($("#treeDemo1_zbrs"), setting1, result);
				$.fn.zTree.init($("#treeDemo2_zbrs"), setting2, result);
			});
			$(".textbox-text").css("padding-top", '1px');
		});



////////////////////////////////单位树
var ztreedata1 = {
	dwbm : "",
	name : ""
}
var setting1 = {
	data : {
		key : {
			name : "text"
		}
	},
	view : {
		showIcon : true,
		dblClickExpand : false,
		selectedMulti : false
	},
	callback : {
		beforeClick : beforeClick1,
		onClick : onClick1
	}
};

function beforeClick1(treeId, treeNode) {
	var check = (treeNode && treeNode.valueSelect);
	if (check)
		hideMenu1();
	return check;
}

function onClick1(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo1_zbrs");
	nodes = zTree.getSelectedNodes();
	v = "";
	var name = nodes[0].text;
	var dwbm = nodes[0].id;
	ztreedata1.name = name;
	ztreedata1.dwbm = dwbm;
	v = name;
	var cityObj = $("#citySel1_zbrs");
	cityObj.attr("value", v);
	//赋值给隐藏标签
	$("#ywgzZbrsDwbm").val(dwbm);
}

function showMenu1() {
//	console.log(isShowMenu);
	if (isShowMenu == false) {
		isShowMenu = true;
//		console.log('show: ' +isShowMenu);
		var cityObj = $("#citySel1_zbrs");
		var cityOffset = $("#citySel1_zbrs").offset();
		$("#menuContent1_zbrs").fadeIn("fast");
		$("body").bind("mousedown", onBodyDown1);
	} else
		hideMenu1();
}
function hideMenu1() {
	isShowMenu = false;
//	console.log('hide: ' +isShowMenu);
	$("#menuContent1_zbrs").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown1);
}
function onBodyDown1(event) {
	if (!(event.target.id == "menuBtn1_zbrs" || event.target.id == "menuContent1_zbrs" || $(
			event.target).parents("#menuContent1_zbrs").length > 0)) {
		hideMenu1();
	}
}



////////////////////////////////datagrid  单位树
var ztreedata2 = {
	dwbm : "",
	name : ""
}
var setting2 = {
	data : {
		key : {
			name : "text"
		}
	},
	view : {
		showIcon : true,
		dblClickExpand : false,
		selectedMulti : false
	},
	callback : {
		beforeClick : beforeClick2,
		onClick : onClick2
	}
};

function beforeClick2(treeId, treeNode) {
	var check = (treeNode && treeNode.valueSelect);
	if (check)
		hideMenu2();
	return check;
}

function onClick2(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo2_zbrs");
	nodes = zTree.getSelectedNodes();
	v = "";
	var name = nodes[0].text;
	var dwbm = nodes[0].id;
	ztreedata2.name = name;
	ztreedata2.dwbm = dwbm;
	v = name;
	var cityObj = $("#citySel2_zbrs");
	cityObj.attr("value", v);
	
	//赋值给隐藏标签
	$("#qxYwgzZbrsDwbm").val(dwbm);
	//修改datagrid的请求参数
	$("#ywgzZbrsTable").datagrid({
		queryParams: {
			dwbm: dwbm
		}
	});
}

function showMenu2() {
	
	if(zbrsDwbm != ''){
		return ;
	}
	
	if (isShowMenu == false) {
		isShowMenu = true;
		// console.log('show: ' +isShowMenu);
		var cityObj = $("#citySel2_zbrs");
		var cityOffset = $("#citySel2_zbrs").offset();
		$("#menuContent2_zbrs").fadeIn("fast");
		$("body").bind("mousedown", onBodyDown2);
	} else
		hideMenu2();
}
function hideMenu2() {
	isShowMenu = false;
	// console.log('hide: ' +isShowMenu);
	$("#menuContent2_zbrs").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown2);
}
function onBodyDown2(event) {
	if (!(event.target.id == "menuBtn2_zbrs" || event.target.id == "menuContent2_zbrs" || $(
			event.target).parents("#menuContent2_zbrs").length > 0)) {
		hideMenu2();
	}
}


/**
 * 清除参数,重新加载
 */
function clearLoad(){
	$("#citySel2_zbrs").attr("value", "");
	$("#ywgzZbrsTable").datagrid({
		queryParams: {
			dwbm: ""
		}
	});
}


/////***********************************************************************单位Ztree end***********************************************************************************************************************////

