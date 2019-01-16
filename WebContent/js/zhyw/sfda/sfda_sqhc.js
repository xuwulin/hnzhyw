var hcZtreeData = {};
var dahcOrdabg = "档案核查";
var hckssj = null;
var hcjssj = null;
var isshowHcMenu = false
var hcparam = getRequest();
var hcsfgs = null; // 是否公示
var hcDaSpzt = null; // 档案审批状态
var hcSpzt = null; // 档案核查审批状态
var isYcChAn=true; //是否影藏撤回按钮

var ddd = {
	dagzid : hcparam.wbid
};

$(document).ready(function() {
			// 获取档案归总信息
			$.ajax({
				url : rootPath + "/service/sfdacj/dagz",
				type : 'post',
				async : false,
				data : {
					gdid : hcparam.wbid
				},
				dataType : 'json',
				success : function(data) {
					if (data.length > 0) {
						hckssj = data[0].kssj;
						hcjssj = data[0].jssj;
						hcsfgs = data[0].sfgs;
					}
				}
			});

			if (hcparam.ssrdwbm == top.currentUserInfo.dwbm) {
				if (hcparam.ssr == top.currentUserInfo.gh) { // 如果单位编码 工号 一样
					// 则绑定单机函数

					// 获取档案、核查（变更）的审批状态
					$.ajax({
								url : rootPath + "/service/dahc/getHcSpzt",
								type : 'post',
								async : false,
								data : {
									"spstid" : ddd.dagzid
								},
								dataType : 'json',
								success : function(data) {

									if (typeof (data.hcDaSpzt) != 'undefined'
											&& data.hcDaSpzt != null
											&& data.hcDaSpzt != "") {
										hcDaSpzt = data.hcDaSpzt;
									}
									if (typeof (data.hcSpzt) != 'undefined'
											&& data.hcSpzt != null
											&& data.hcSpzt != "") {
										hcSpzt = data.hcSpzt;
									}

								}
							});

					//档案已公示
					if (hcsfgs == 2) {
						$("#sqhc").text("档案变更申请");
					}
					//档案审批通过可以发起核查
					if (hcDaSpzt == 4) {
						$("#sqhc").show();
					}
					
					if (hcSpzt == 1 && hcsfgs == 2) {
						$("#sqhc").hide();
						$("#qxhcsq").text("撤回变更申请");
						$("#qxhcsq").show();
					} else if (hcSpzt == 1 && hcsfgs == 1) {
						$("#sqhc").hide();
						$("#qxhcsq").show();
					}
                    
					//档案已封存 、从待办业务进去审批时隐藏申请核查和撤回核查按钮
					if (hcparam.sffc == 1 || hcparam.spym == 1
							|| hcparam.spym == 2) {
						$("#sqhc").hide();
						$("#qxhcsq").hide();
					}
					//是否隐藏撤回按钮
					if(isXsChAn()){
						$("#qxhcsq").hide();
					}

				}
			}

		});

// 显示填写核查信息对话框
function showHcForm(index) {
	parent.ifeblick();
	
	//显示附件信息
	showDabgsqFj();
	
	//审批人下拉树（方法引用于common.js）
	var input_id = "xzHcspr"; 
	var dwbm = null;
	var gh = null;
	if(top.currentUserInfo){
		dwbm = top.currentUserInfo.dwbm;
		gh = top.currentUserInfo.gh;
	}
	getAllRyOfDwBmTree(input_id,dwbm,gh,null,null,"2");//getAllRyOfDwBmTree(input_id,dwbm,gh,null,null);
	
	var eee = {
		wbid : hcparam.wbid
	}
	// 这里判断下能否申请核查
	if (hcsfgs == 1) { // 如果已经公示
		// 判断是否过了核查期限
		$.post(rootPath + '/service/dahc/sfgq', eee, function(data) { 
			if (data.sfgq == 1 || "1" == data.sfgq) { // 没有过核查期限
				// 是否正在核查
				$.post(rootPath + '/service/dahc/sfzzhc', eee, function(data) { 
					if (data.sfzzhc == 1) { // 可以核查
						$("#hcinfotitle").text("档案核查申请");
						$("#hcinfo").fadeIn("fast");
					}
				}, 'json');
			} else {
				top.msgAlertError("该档案已经过了核查期限，不能发起核查申请");
			}
		}, 'json');

	} else {
		// 判断是否过了变更期限
		$.post(rootPath + '/service/dahc/sfgq', eee, function(data) {
			if (data.sfgq == 1 || "1" == data.sfgq) {
				$.post(rootPath + '/service/dahc/sfzzhc', eee, function(data) {
					if (data.sfzzhc == 1) { // 可以变更
						$("#hcinfotitle").text("档案变更申请");
						$("#hcinfo").fadeIn("fast");
					}
				}, 'json');
			}else{
				top.msgAlertError("该档案已经过了变更期限，不能发起变更申请");
			}
		}, 'json');
		
	}
}

function closeHcDetail() {
	parent.ifenone();
	$("#hcinfo").fadeOut("fast");
}

function addHcda() {
	var selectTree = $("#xzHcspr").combotree("tree");
	var ryNode = selectTree.tree("getSelected");
	var dwbmRoot = selectTree.tree("getRoot");
	var sprdwbm = checkStrReturnEmpty(dwbmRoot.id);
	var jsNode = "";
	var bmNode = "";
	var sprgh = "";
	var sprbmbm = "";
	if(ryNode){
		jsNode = selectTree.tree("getParent", ryNode.target);
		bmNode = selectTree.tree("getParent", jsNode.target);
		sprgh = checkStrReturnEmpty(ryNode.id);
		sprbmbm = checkStrReturnEmpty(bmNode.id);
		$("input[name='xzHcspr']").val(checkStrReturnEmpty(ryNode.text));
	}
	var rymc = $("input[name='xzHcspr']").val();
	if(!rymc||rymc==""){
		top.msgAlertInfo("请选择审批人!");
		return;
	}
	if (null != hcparam.wbid) {
		if (hcsfgs == 1) {
			dahcOrdabg = "档案核查"
		} else if (hcsfgs == 2) {
			dahcOrdabg = "档案变更"
		}
		top.msgProgressTip("正在处理……");
		$.ajax({
			url : rootPath + "/service/sp/audit",
			type : 'post',
			async : false,
			data : {
				jdlx : '900',
				lcjd : '90001',
				sprdwbm : sprdwbm, // 审批人的dwbm
				sprbmbm : sprbmbm, // 审批人的bmbm
				sprgh : sprgh, // 审批人的工号
				spstid : hcparam.wbid,
				spyj : $("#wtfl").val() + "fqrspyj" + $("#dafl").val()
						+ "fqrspyj" + $("#wtms").val(),
				clnr : encodeURI(top.currentUserInfo.mc + hckssj + "到" + hcjssj + "的"
						+ dahcOrdabg + "审批")
			},
			dataType : 'json',
			success : function(resultMap) {
				var status = resultMap.status;
				if (status == 1) {
					addFile(resultMap.fqhcshlcid);
				} else if (status == 0) {
					top.msgAlertError("发起审批失败!");
					closeHcDetail();
				}
			},
			error : function(data) {
				top.msgProgressTipClose();
				top.msgAlertError("操作失败!");
				// $.messager.alert("提示", "操作失败!", "error");
			}
		});
	}
}
function addFile(wbid) { // 上传附件
	var s = $("#addfileform").find("input");
	var filepath = $(s[1]).val();
	if ("" != filepath) {
		var url = rootPath + "/service/uploader/upload";
		$("#addfileform").form('submit', {
			url : url,
			onSubmit : function(hcparam) {
				hcparam.wbid = wbid;
				hcparam.fjlx = "4"; // 附件类型 4为 档案核查申请附件
			},
			success : function(data) {
				closeHcDetail();
				top.msgAlertInfo("核查申请成功!");
				top.msgProgressTipClose();
				window.location.reload();

			}
		});
	} else {
		closeHcDetail();
		top.msgAlertInfo("核查申请成功!");
		top.msgProgressTipClose();
		window.location.reload();
	}
}
// 撤回核查(变更)申请
function qxHcForm() {
	top.msgProgressTip("撤回中……");
	$.ajax({
		url : rootPath + "/service/sp/recall",
		type : 'post',
		async : false,
		data : {
			spstid : ddd.dagzid,
			splx : "8"
		},
		dataType : 'json',
		success : function(data1) {
			if (data1 == '1') {
				top.msgAlertInfo("成功撤回!");
				top.msgProgressTipClose();
				window.location.reload();
			} else {
				top.msgAlertInfo("撤回失败!");
				top.msgProgressTipClose();
			}
		}
	});
}
//判断是否显示撤回按钮
function isXsChAn(){
	var chspzt=null;
	$.ajax({
		url : rootPath + "/service/dagz/showSplcsl",
		type : 'post',
		async : false,
		data : {
			"spstid" : ddd.dagzid,
			"splx" : "8"
		},
		dataType : 'json',
		success : function(data) {
			if(data.length>0){
				 $.each(data,function(i, item){
					 if(item.jdlx=='901'){
						 chspzt=item.spzt;
					 }
				 });
			}	
		}
	});	
	if(chspzt=="1"){
		return false;
	}
	else
	{
		return true;
	}
}

$.getJSON(rootPath + '/service/tree/zzjgtree', function(res) {

	var result = [];
	result.push(res);
	$(document).ready(function() {
		try {
			$.fn.zTree.init($("#treeDemoDahc"), settingHc, result);
		} catch (e) {

		}
	});
	// $('#xzspr').combotree({
	// data : result,
	// onHidePanel : isSelectPerson
	// });
});

var settingHc = {
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
		beforeClick : beforeHcClick,
		onClick : onHcClick
	}
};

function beforeHcClick(treeId, treeNode) {
	var check = (treeNode && treeNode.valueSelect);
	if (check)
		hideHcMenu();
	return check;
}

function onHcClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemoDahc");
	nodes = zTree.getSelectedNodes();
	var pNode = nodes[0].getParentNode();
	var ppNode = pNode.getParentNode();
	v = "";
	var name = nodes[0].text;
	var gh = nodes[0].id;
	hcZtreeData.name = name;
	hcZtreeData.bmbm = ppNode.id;
	hcZtreeData.gh = gh;
	v = name;
	var xzsprObj = $("#xzHcspr");
	xzsprObj.attr("value", v);
}

function showHcMenu() {
	if (isshowHcMenu == false) {
		isshowHcMenu = true;
		var xzsprObj = $("#xzHcspr");
		var xzsprOffset = $("#xzHcspr").offset();
		$("#menuContent333").fadeIn("fast");

		$("body").bind("mousedown", onBodyHcDown);
	} else
		hideHcMenu();

}

function hideHcMenu() {
	isshowHcMenu = false;
	$("#menuContent333").fadeOut("fast");
	$("body").unbind("mousedown", onBodyHcDown);
}

function onBodyHcDown(event) {
	if (!(event.target.id == "menuHcBtn" || event.target.id == "menuContent333" || $(
			event.target).parents("#menuContent333").length > 0)) {
		hideHcMenu();
	}
}


/**
 * 档案变更申请附件上传
 */
//根据档案id和附件类型查询附件
function showDabgsqFj(){
	$.ajax({
		url : rootPath + "/service/uploader/getFjPageList",
		data : {wbid : daId,fjlx : '22',page : '1',rows : '5'},
		type : "POST",
		success : function(data){
			callbackFun(data);
		}
	});
}

function callbackFun(data){
	$("#fileDetail tbody").empty();
	var fjs = JSON.parse(data).rows;
	$.each(fjs,function(index,item){
		var fjId = $("<td></td>").append(item.id).css("display","none");
		var fjmc = $("<td style='text-align: center;'></td>").append(item.wjmc);
		var lx = $("<td style='text-align: center;'></td>").append(item.fjlx=="22" ? '档案异议' : '');
		var wjdz = item.wjdz;
		var xzUrl = rootPath + "/service/uploader/download?wbid=" + daId+"&id="+item.id;
		var cz = $("<td style='text-align: center;'></td>").append('<a class="table_czan_ys" href="'+xzUrl+'" style="margin-left:10px;">下载</a>' 
										+"&nbsp;" 
										+'<a class="table_czan_ys" href="javascript:void(0)" onclick='+'fileDelete("'+item.id+'","'+item.wjdz+'","'+index+'")'+' style="margin-left:10px;">删除</a>');
		
		$("<tr></tr>").append(fjId)
					  .append(fjmc)
					  .append(lx)
					  .append(cz)
					  .appendTo("#fileDetail tbody");
	});
}

//点击上传按钮执行上传操作
$("#fjscButton").attr("onclick","uploadFile()");

/**=========================================点击开始上传执行操作================================================*/
function uploadFile(){
	var fileForm = $("#addfileform");
	var fileinput = $("#upfile3");
	var filemessage = fileinput.filebox('getText');//getText 获取显示的文本值。
	
	if (filemessage != '' && typeof (filemessage) != "undefined") {
		top.msgProgressTip("正在保存……");
		fileForm.form('submit', {
			url : rootPath + '/service/uploader/uploadAndId',
			onSubmit : function(param) {
				//YX_FJ表 -->附件类型:22代表档案异议（修改/变更）
				param.fjlx = '22';
				param.id = daId;
			},
			success : function(data) {
				top.msgProgressTipClose();
				top.msgAlertInfo("保存成功");
				fileForm.form('clear');
				fileinput.filebox('setText', '');
				showDabgsqFj();
			}
		});
	}
}

/**====================================附件列表中的删除方法=============================================*/
function fileDelete(wjid,wjdz,item) { // 删除提示消息
	$.messager.confirm('删除提示', '您确认想要删除此文件吗？', function(r) {
		if (r) {
			var data = {
					id:wjid,
					dz:wjdz
			};
			$.post(rootPath + "/service/uploader/delete", data, function(data) {
				if(data=="1"){
					top.msgAlertInfo("删除成功");
					showDabgsqFj();
				}
			});
		}
	});
}
