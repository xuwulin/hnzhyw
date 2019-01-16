//页面li标签点击事件
$(".right_ul li:last").click(function() {
	$('.right_li').removeClass('right_li');
	$(this).addClass('right_li');
	$('.right_com').css('display', ' none');
	$('.right_coml').css('display', ' block');
	$("#updatefgld").css('display', 'none');
	$("#addDict_li").text("添加数据字典");
	$('#addfgld').css('display', ' block');
	$('#zx').css('display', ' block');
	clearbox();
	$("#dataDictTable").datagrid("unselectAll");
});
$(".right_ul li:first").click(function() {
	$('.right_li').removeClass('right_li');
	$(this).addClass('right_li');
	$('.right_coml').css('display', ' none');
	$('.right_com').css('display', ' block');
	$('#addfgld').css('display', ' block');
	clearbox();
});
//清空添加数据
function clearbox(){
	$("#lxbm").val("");
	$("#lxz").val("");
	$("#mssm").val("");
	$("#mc").val("");
	$("#identifying").val("");
}
var setting = {
	data : {
		key : {
			id : 'id',
			name : "text",
			source : 'source'
		}
	},
	view : {
		showIcon : true,
		dblClickExpand : false,
		selectedMulti : false
	},
	callback : {
		beforeClick : beforeClick,
		onClick : onClick
	}
};

function beforeClick(treeId, treeNode) {
	var id = treeNode.id;
	showFirstDict(id);
}

function onClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	nodes = zTree.getSelectedNodes();
	var text = nodes[0].text;
	var id = nodes[0].id;
	$("#citySel1").val(text);
	xzfjid = id;
}
function beforeClick(treeId, treeNode) {
	var check = (treeNode && treeNode.valueSelect);
	if (check)
		hideMenu();
	return check;
}

// **********************************************数据字典树************************************************************/
var xzfjid = "";// 新增父级id
var isShowMenu = false;

var setting1 = {
	data : {
		key : {
			id : 'id',
			name : "text",
			source : 'source'
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
	var id = treeNode.id;
	showFirstDict(id);
}

function onClick1(e, treeId, treeNode) {
}

// 请求数据字典树
$(document).ready(function() {
	initDictTree();
});

$(function(){
	  if (top.currentUserInfo) {
	      if("2"!=top.currentUserInfo.dwjb){
	          $("#addDict_li").css("display","none");
	          $("#delButton").css("display","none");
	      }
	  }
	});

function initDictTree() {
	$.post(rootPath + '/service/dataDict/getDictTree', function(res) {
		var result = [];
		result = res.value;
		$.fn.zTree.init($("#dictTreeDemo"), setting1, result);
		var dictFid = "";
		showFirstDict(dictFid);
		$.fn.zTree.init($("#treeDemo"), setting, result);
		$(".textbox-text").css("padding-top", '1px');
	});
}

// 显示
// 根据数据字典父id查询其下所有子节点
function showFirstDict(dictFid) {
	var queryUrl = rootPath + "/service/dataDict/getPageListDict";
	if (top.currentUserInfo) {
	    if ("2"==top.currentUserInfo.dwjb) {
	    	$('#dataDictTable').datagrid({
						url : queryUrl,
						method : 'get',
						toolbar : '#hcpz_tb',
						selectOnCheck : true,
						checkOnSelect : true,
						rownumbers : true,
						pagination : true,
						pageNumber : 1,
						pageSize : 15,
						pageList : [ 5, 10, 15, 20 ],
						queryParams : {
							dictFid : dictFid
						},
						columns : [ [
								{field : 'dictFid',title : '父id编码',hidden : true},
								{field : 'ck',align : 'center',checkbox : true,width : '10%'},
								{field : 'dictName',title : '名称',width : '15%',align : 'center'},
								{field : 'dictIdentifying',title : '标识',width : '5%',align : 'center'},
								{field : 'dictType',title : '类型',width : '10%',align : 'center'},
								{field : 'dictTypeValue',title : '类型值',width : '10%',align : 'center'},
								{field : 'dictExplain',title : '说明',width : '20%',align : 'center'},
								{field : 'dictOperator',title : '操作人',width : '10%',align : 'center'},
								{field : 'createTime',title : '操作时间',width : '10%',align : 'center',
									formatter : function(value, row, index) {
										if (value) {
											return (new Date(value).format("yyyy-MM-dd"));
										}
									}
								},
								{field : 'cz',title : '操作',width : '15%',align : 'center',
									formatter : function(value, row, index) {
										var rowdata = JSON.stringify(row);
										return "<a class='table_czan_ys' onclick='modData("+ rowdata+ ")'>修改</a>"
											 + "<a class='table_czan_ys' onclick='delData("+ rowdata+ ")'>删除</a>";
									}
								} ] ]
					});
	      }
	    else{
	    	$('#dataDictTable').datagrid({
						url : queryUrl,
						method : 'get',
						toolbar : '#hcpz_tb',
						selectOnCheck : true,
						checkOnSelect : true,
						rownumbers : true,
						pagination : true,
						pageNumber : 1,
						pageSize : 15,
						pageList : [ 5, 10, 15, 20 ],
						queryParams : {
							dictFid : dictFid
						},
						columns : [ [
								{field : 'dictFid',title : '父id编码',hidden : true},
								{field : 'dictName',title : '名称',width : '20%',align : 'center'},
								{field : 'dictIdentifying',title : '标识',width : '5%',align : 'center'},
								{field : 'dictType',title : '类型',width : '10%',align : 'center'},
								{field : 'dictTypeValue',title : '类型值',width : '10%',align : 'center'},
								{field : 'dictExplain',title : '说明',width : '30%',align : 'center'},
								{field : 'dictOperator',title : '操作人',width : '10%',align : 'center'},
								{field : 'createTime',title : '操作时间',width : '10%',align : 'center',
									formatter : function(value, row, index) {
										if (value) {
											return (new Date(value).format("yyyy-MM-dd"));
										}
									}
								}]]
					});
	    }
  }
}

function toModifyPage(data) {
	var dictId = data.dictId;
	if (!dictId) {
		top.msgAlertInfo("请选择数据字典再进行修改！");
		return;
	}
	// 打开添加页面进行赋值

}

function showMenu() {
	$("#menuContent1").fadeIn("fast");
	$("body").bind("mousedown", onBodyDown1);
	if (isShowMenu == false) {
		isShowMenu = true;
		$("#menuContent1").fadeIn("fast");
		$("body").bind("mousedown", onBodyDown1);
	} else
		hideMenu();
}
function hideMenu() {
	isShowMenu = false;
	$("#menuContent1").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown1);
}

function onBodyDown1(event) {
	if (!(event.target.id == "menuBtn1" || event.target.id == "menuContent1" || $(
			event.target).parents("#menuContent1").length > 0)) {
		hideMenu();
	}
}

// 新增数据字典
function addDataDict() {
	var dwbm = "";
	var mc = "";
	if (top.currentUserInfo) {
		dwbm = top.currentUserInfo.dwbm;
		mc = top.currentUserInfo.mc;
	}
	var dictFid = xzfjid;
	var dictName = $("#mc").val();
	var dictType = $("#lxbm").val();
	var dictTypeValue = $("#lxz").val();
	var dictExplain = $("#mssm").val();
	var dictIdentifying = $("#identifying").val();
	// 验证非空
	if ( "" == dictExplain || dictTypeValue == ""
			|| dictType == "" || dictName == ""||dictIdentifying=="") {
		top.msgAlertWarning("请选择/填写相关数据再操作！");
		return;
	}
	var dwbm = dwbm;
	var mc = mc;
	$.ajax({
		type : 'post',
		url : rootPath + '/service/dataDict/addDict',
		dataType : 'json',
		data : {
			dictFid : dictFid,
			dictName : dictName,
			dictIdentifying:dictIdentifying,
			dictType : dictType,
			dictTypeValue : dictTypeValue,
			dictExplain : dictExplain,
			dwbm : dwbm,
			mc : mc
		},
		async : false,
		success : function(data) {
			top.msgProgressTipClose();
			if (data) {
				top.msgAlertInfo(data.msg);
			}
			initDictTree();
			$('#dataDictTable').datagrid('reload');
			location.href = 'data_dict.html';
		}
	});
}

// 删除数据字典
function delData(data) {
	var dictId = data.dictId;
	var data = {
		dictId : dictId
	};
	$.post(rootPath + '/service/dataDict/selectidcount', data,
			function(result) {
				if (result == true) {
					top.msgAlertInfo("主字典下存在下级字典，请先删除下级字典！");
				} else {
					$.messager.confirm('删除', '删除数据不可恢复，继续请点击确定！', function(r) {
						if (r) {
							delfun(dictId);
						}
					});
				}
			});
}


function delfun(dictId){
	$.ajax({
		type : 'get',
		url : rootPath + '/service/dataDict/delDict',
		dataType : 'json',
		data : {
			dictId : dictId
		},
		async : false,
		success : function(data) {
			top.msgProgressTipClose();
			if (data) {
				top.msgAlertInfo(data.msg);
			}
			$('#dataDictTable').datagrid('reload');
			// 重新加载树
			initDictTree();
		}
	});
}
// 修改数据字典
function modData(data) {
	// 组装数据字典数据
	$(".right_ul li:last").click();
	$("#addDict_li").text("修改数据字典");
	var dictId = data.dictId;
	var dictFid = data.dictFid;
	var dictName = data.dictName;
	$("#lxbm").val(data.dictType);
	$("#lxz").val(data.dictTypeValue);
	$("#mssm").val(data.dictExplain);
	$("#mc").val(data.dictName);
	$("#identifying").val(data.dictIdentifying);
	$("#identifying").attr('readonly','readonly');
	$('.right_com').css('display', ' none');
	$('#zx').css('display', ' none');
	$('.right_coml').css('display', ' block');
	$('#addfgld').css('display', ' none');
	$("#updatefgld").css('display', 'block');
	var dwbm = "";
	var mc = "";
	if (top.currentUserInfo) {
		dwbm = top.currentUserInfo.dwbm;
		mc = top.currentUserInfo.mc;
	}
	$("#updatefgld").click(function() {
		var datas = {
			dictId : dictId,
			dictFid : dictFid,
			dictName : $("#mc").val(),
			dictIdentifying:$("#identifying").val(),
			dictType : $("#lxbm").val(),
			dictTypeValue : $("#lxz").val(),
			dictExplain : $("#mssm").val(),
			dwbm : dwbm,
			mc : mc
		};
		$.ajax({
			type : 'post',
			url : rootPath + '/service/dataDict/modifyDict',
			dataType : 'json',
			data : datas,
			async : false,
			success : function(data) {
				top.msgProgressTipClose();
				if (data) {
					top.msgAlertInfo(data.msg);
				}
				$('#dataDictTable').datagrid('reload');
			}
		});
		location.href = 'data_dict.html';
	});
}


//批量刪除
$("#delButton").click(function() {
	var rows =$('#dataDictTable').datagrid("getSelections");
	var dictid="";
	if(rows.length == 0){
		top.msgAlertInfo("请选择要删除的数字字典!"); 
		return;
	}
	for(var i = 0; i < rows.length ; i++){
		dictid+=rows[i].dictId + ',';
	}
	if (dictid.length > 0){
		dictid =dictid.substring(0, dictid.length - 1);
	}	
	$.messager.confirm('批量删除字典', '删除数据不可恢复，继续请点击确定！', function(r) {
		if (r) {
			delfun(dictid);
		}
	});
});