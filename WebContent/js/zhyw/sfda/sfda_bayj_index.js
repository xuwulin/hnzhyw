var datas = null;

var myChart = null;

var params = null;

var totalScore = null;
var jdlx = "";// 节点类型
var jdzt = "";// 节点状态
var lclx = "";// 流程类型
var ifysfjnsetI;

var lcmbArray = null;

var lcid = null;

var ditailshowable = false;  //是否可以显示遮罩层
var daSfSptg=false;
var lcmb = [// 流程模板数组
{
	id : "111",
	jdlx : "100",
	lcjd : "10001",
	xyjd : "101",
	xycljs : "SP",
	xyclbm : "",
	xycldw : "",
	jdzt : "0",
	sm : "档案审批开始",
	xycldwlx : "2",
	lclx : "2",
	ztbg : "0"
} ];

$(function() {
	initcontentstree($(".contents_tree"), $(".tree_ul"), $(".dagztitle"));
	// 柱状图初始化
	try {
		var myChart = echarts.init(document.getElementById('main'),
				macaronsTheme);
		myChart.setOption(option);
	} catch (e) {

	}
	params = getRequest();
	params.dwbm = params.ssrdwbm;
	params.ssr = params.ssr;
	params.gdid = params.wbid;
	params.kssj = params.kssj;
	params.jssj = params.jssj;
	params.sffc = params.sffc;
	params.spym = params.spym;
	var gdid = params.gdid;

	// 获取流程模板
	$.ajax({
		url : rootPath + "/service/sp/getmb",
		type : 'post',
		async : false,
		data : {
			'jdlx' : jdlx,
			"jdzt" : jdzt,
			"lclx" : lclx
		},
		dataType : 'json',
		success : function(data1) {
			lcmbArray = data1;
		},
		error : function() {
		}
	});

	// 获取最新审批实例信息
	$.ajax({
		url : rootPath + "/service/sp/selectNewSpsl",
		type : 'post',
		async : false,
		data : {
			"spdw" : params.dwbm,
			"spbm" : "",
			"spr" : params.ssr,
			"spstid" : params.gdid,
			"splx" : lcmb[0].lclx
		},
		dataType : 'json',
		success : function(data) {
			if (data.length == 0 || data[0].spzt == '5') {
				$('#button_fqsp').show();
			} else {
				lcjd = data[0].lcjd;
			}
		}
	});
	if (parent.currentUserInfo.spymtype == 333) {
		$("#button_sc").css("display", "none");
	}
	// 判断是否隐藏撤回按钮
	$.ajax({
		url : rootPath + "/service/sp/getjdlx",
		type : 'post',
		async : false,
		data : {
			spstid : params.gdid,
			splx : lcmb[0].lclx
		},
		dataType : 'json',
		success : function(data1) {

			if (parent.currentUserInfo.spymtype || !data1 || data1 == '0'
					|| data1 != lcmb[0].jdlx) {
				$('#button_recall').hide();
			} else {
				$('#button_recall').show();
			}
		}
	});

	// 撤回绑定事件
	$('#button_recall').on('click', function() {
		$.ajax({
			url : rootPath + "/service/sp/recall",
			type : 'post',
			async : false,
			data : {
				spstid : params.gdid,
				splx : lcmb[0].lclx
			},
			dataType : 'json',
			success : function(data1) {
				if (data1 == '1') {
					$.messager.alert("提示", "成功撤回", "info");
					$('#button_recall').hide();
					$('#button_fqsp').show();
					window.location.reload();
				} else {
					$.messager.alert("提示", "撤回失败", "info");
				}
			}
		});
	});

	// 如果当前登录人不是档案所属人，则隐藏发起审批按钮
	if (top.currentUserInfo.gh != params.ssr) {
		$('#button_fqsp').hide();
		$('#button_recall').hide();
	}

	// 点击查看个人绩效
	$("#divconnection")
			.click(
					function() {
						var urlstr = "?ssrdwbm=" + params.ssrdwbm + "&ssr="
								+ params.ssr + "&wbid=" + params.wbid
								+ "&personal=1"
						if (params.sfgs != null
								&& typeof (params.sfgs) != 'undefined') {
							urlstr = urlstr + "&sfgs=" + params.sfgs;
						}
						if (params.tjnf != null
								&& typeof (params.tjnf) != 'undefined') {
							urlstr = urlstr + "&tjnf=" + params.tjnf;
						}
						if (params.nf != null
								&& typeof (params.nf) != 'undefined') {
							urlstr = urlstr + "&nf=" + params.nf;
						}
						history.pushState(null, null, urlstr);
						location.href = "personal.html?daid=" + zcyzywbid
								+ "&totalScore=" + totalScore;
					});
	if (gdid == null || typeof (gdid) == 'undefined') {
		$.getJSON(rootPath + "/service/sfdacj/selectdassrdwbm", {
			ssr : params.ssr,
			dwbm : params.dwbm,
		}, function(result) {
			initpasspage(result[0].id);
		});
	} else {
		initpasspage(gdid);
	}
	
	// 档案所有sp过程中目前的状态
	allspspzt();

	var tjnf = contentstree_params.tjnf;
	var sfgs = contentstree_params.sfgs;

	params.tjnf = params.tjnf;
	params.personal = params.personal;

	$.post(rootPath + "/service/syCount/countSum?gdid=" + gdid, {}, function(data) {
//		$("#pjajblsc").text(judgeIsNull(data.countPjajblsc) + "天");
//		$("#qyajpjajblsc").text(judgeIsNull(data.countQypjajBlsc) + "天");
		$("#grjx").text(judgeIsNull(data.countGrjx) + "分");
		$("#grjxdf").text(judgeIsNull(data.countGrjx) + "分");
		totalScore = data.countGrjx;
//		$("#ghdryjl").text(judgeIsNull(data.countSfjn) + "次");
		//共获得荣誉奖励：
		$.getJSON(rootPath + '/service/sfdaRyjn/getSfdaRyjnPageList', {
			gdid : gdid,
			_timestamp : new Date().getTime()
		}, function(result) {
			$("#ghdryjl").text(judgeIsNull(result.total) + "次");
		});
		
//		$("#bzjsfzr").text(judgeIsNull(data.countSfzr) + "次");
		//被追究司法责任：
		$.getJSON(rootPath + '/service/sfdaZrzj/getSfdaZrzjPageList', {
			gdid : gdid,
			_timestamp : new Date().getTime()
		}, function(result) {
			$("#bzjsfzr").text(judgeIsNull(result.total) + "次");
		});
		
		//接受监督次数：
		$.getJSON(rootPath + '/service/sfdaJdqk/getSfdaJdqkPageList', {
			gdid : gdid,
			_timestamp : new Date().getTime()
		}, function(result) {
			$("#jsjdCount").text(judgeIsNull(result.total) + "次");
		});
		
		
//		$("#zycssum").text(judgeIsNull(data.countZycs) + "次");
		//共进行业务研修
		$.getJSON(rootPath + '/service/sfdaZycs/getSfdaZycsPageList', {
			gdid : gdid,
			_timestamp : new Date().getTime()
		}, function(result) {
			$("#zycssum").text(judgeIsNull(result.total) + "次");
		});
		
//		$("#zbajsl").text(judgeIsNull(data.zbajsl) + "件");
//		$("#cbajsl").text(judgeIsNull(data.cbajsl) + "件");

		//公示且审批通过的档案
	    if (params.sfgs == 1&&daSfSptg) {
			if ("" != data.gsxx && null != data.gsxx&& typeof (data.gsxx) != 'undefined'&& params.sffc != "1") {
						$("#gsxx").css("display", "block");
						$("#gsxxspan").text(data.gsxx);
						if (data.gsJzsj != null && data.gsJzsj != ""&& typeof (data.gsJzsj) != 'undefined') {
							$("#gsjzsj").show();
							$("#gsjzsjspan").text(data.gsJzsj);
						}
					} else {
						$("#gsxx").remove();
					}
				}
	    //未公示、审批通过的档案
		if (params.sfgs == 2&&daSfSptg) {
		if ("" != data.qxGsxx && null != data.qxGsxx&& typeof (data.qxGsxx) != 'undefined'&& params.sffc != "1") {
				$("#gsjzsj").hide();
				$("#span_gsxx").text("取消公示理由:");
				$("#gsxx").css("display", "block");
				$("#gsxxspan").text(data.qxGsxx);
		  } else {
			    $("#gsxx").remove();
		  }		
		}
  
	}, "json");

	
	
});

function initpasspage(gdid) {
	if (typeof (gdid) == 'undefined'){
		return;
	}
	//判断当前档案是否是登录人的，如果不是则根据审批状态来查询显示且添加按钮不显示
	var zbUrl;
	var cbUrl;
	if (top.currentUserInfo) {
		//根据档案id和状态查询列表
		zbUrl = rootPath + '/service/sfdaAjxx/getSfdaZbAjxxPageList?daId='+gdid+'&status='+'1';
		cbUrl = rootPath + '/service/sfdaAjxx/getSfdaCbAjxxPageList?daId='+gdid+'&status='+'1';
	}
	
	$.ajax({
		url : zbUrl,
		type : 'post',
		data :{
			page : 1,
			rows : 10
		},
		async : false,
		dataType : 'json',
		success : function(res){
			if (res) {
				$("#zbajsl").text(judgeIsNull(res.total)+"件");
				$("#pjajblsc").text(judgeIsNull(res.avgBasc) + "天");
				$("#qyajpjajblsc").text(judgeIsNull(res.allAvgBasc) + "天");
				var list = [];
				list.push(res.ywlxsl.JDB);
	            list.push(res.ywlxsl.ZJ);
	            list.push(res.ywlxsl.GS);
	            list.push(res.ywlxsl.FT);
	            list.push(res.ywlxsl.FD);
	            list.push(res.ywlxsl.JS);
	            list.push(res.ywlxsl.MX);
	            list.push(res.ywlxsl.KG);
	            list.push(res.ywlxsl.SS);
	            list.push(res.ywlxsl.YF);
	            list.push(res.ywlxsl.JWB);
	            list.push(res.ywlxsl.FHB);
	            list.push(res.ywlxsl.JJ);
	            list.push(res.ywlxsl.AG);
	            list.push(res.ywlxsl.TJ);
			}
			refresh(list);
		}
	});
	
	//参办案件信息查询
	$.ajax({
		url : cbUrl,
		type : 'post',
		data :{
			page : 1,
			rows : 10
		},
		async : false,
		dataType : 'json',
		success : function(res){
			if (res) {
				$("#cbajsl").text(judgeIsNull(res.total)+"件");
			}
		}
	});
	
//	$.get(rootPath + "/service/ajywlxtj/getcount", {
//		daid : gdid
//	}, function(result) {
//		result = JSON.parse(result);
//		result = result[0];
//		var list = [];
//		list.push(result.jdb);
//		list.push(result.zj);
//		list.push(result.gs);
//		list.push(result.ft);
//		list.push(result.fd);
//		list.push(result.js);
//		list.push(result.mx);
//		list.push(result.kg);
//		list.push(result.ss);
//		list.push(result.yf);
//		list.push(result.jwb);
//		list.push(result.fhb);
//		list.push(result.jj);
//		list.push(result.ag);
//		list.push(result.tj);
//		refresh(list);
//	})
}

var option = {
	title : {
		text : '主办案件情况'
	},
	tooltip : {
		axisPointer : {
			type : 'none'
		}
	},
	legend : {
		data : [ '案件数量' ]
	},
	xAxis : [ {
		type : 'category',
		data : [ '监督办', '侦监', '公诉', '反贪', '反渎', '监所', '民行', '控告', '申诉', '预防',
				'检委办', '复核办', '检技', '案管', '铁检' ]
	} ],
	yAxis : [ {
		type : 'value'
	} ],
	series : [ {
		name : '案件数量',
		type : 'bar',
		data : [],
		itemStyle : {
			normal : {
				label : {
					show : true,
					position : 'top',
					textStyle : {
						color : "#007CB5"
					}
				}
			}
		}
	} ]
};

/**
 * 刷新图表
 */
function refresh(array) {
	if (myChart && myChart.dispose) {
		myChart.dispose();
	}
	try {
		myChart = echarts.init(document.getElementById('main'), macaronsTheme);
		option.series[0].data = array;
		myChart.setOption(option);
	} catch (e) {

	}
}
function showDetail() // 显示审批窗口
{
	parent.ifeblick();
	$("#shDetail").fadeIn("fast");
}
function closeDetail() { // 关闭审批窗口
	parent.ifenone();
	$("#shDetail").fadeOut("fast");
}
// 判断是否为空
function judgeIsNull(data) {
	if (data == "" || data == null || typeof (data) == "undefined") {
		var newData = 0;
		return newData;
	} else {
		return data
	}
}
// 审核窗口的树形列表展示人员
$.getJSON(rootPath + '/service/tree/zzjgtree', function(res) {
	var result = [];
	result.push(res);
	$(document).ready(function() {
		try {
			$.fn.zTree.init($("#treeDemo"), setting, result);
		} catch (e) {

		}
	});
	// $('#xzspr').combotree({
	// data : result,
	// onHidePanel : isSelectPerson
	// });
});

/*
 * function isSelectPerson() { var t = $('#xzspr').combotree('tree'); var node =
 * t.tree('getSelected'); if (!node || node.valueSelect) { return; }
 * 
 * $('#xzspr').combotree("showPanel"); $('#xzspr').combotree("clear"); }
 */
function addspyj(type) {
	var sprgh = ztreedata.gh;
	var selectNode = $.fn.zTree.getZTreeObj("treeDemo").getSelectedNodes();
	var pNode = selectNode[0].getParentNode().getParentNode();
	var spyj = $("#spyj").val();
	var lx = "2";
	var type = type;
	var isStart = "1";
	var ss = $("#spr").val();
	top.msgProgressTip("正在处理……");

	$.ajax({
		url : rootPath + "/service/sp/audit",
		type : 'post',
		async : false,
		data : {
			jdlx : lcmb[0].jdlx,
			lcjd : lcmb[0].lcjd,
			sprdwbm : params.ssrdwbm,
			sprbmbm : pNode.id,
			sprgh : sprgh,
			spstid : zcyzywbid,
			spyj : spyj,
			clnr : nametiemtile
		},
		dataType : 'json',
		success : function(resultMap) {
			var status = resultMap.status;
			top.msgProgressTipClose();
			if (status == 1) {
				top.msgAlertInfo("发起审批成功!");
				// $.messager.alert("提示", "发起审批成功!", "info");
				closeDetail();
				$('#button_fqsp').hide();
				window.location.reload();
			} else if (status == 0) {
				top.msgAlertError("发起审批失败!");
				// $.messager.alert("提示", "发起审批失败!", "error");
				closeDetail();
				window.location.reload();
			}
		},
		error : function(data) {
			top.msgProgressTipClose();
			top.msgAlertError("操作失败!");
			// $.messager.alert("提示", "操作失败!", "error");
		}
	});
}
/**
 * 档案所有sp过程中目前的状态
 */
function allspspzt() {
	$.ajax({
		url : rootPath + "/service/dagz/showAllFirstSplcsl",
		type : 'post',
		async : false,
		data : {
			spstid : params.gdid
		},
		dataType : 'json',
		success : function(da) {
			if (da != undefined) {
				if (da.data1 != undefined) {
					if (da.data1.spzt == 1) {
						$("#gszt").show();
						$("#gszt").css({
							background : 'url(../../image/gssp.png)'
						});
						ditailshowable = true;
					}
				}
				if (da.data2 != undefined) {
					if(da.data2.spzt==4){
						daSfSptg=true;
					}
					if (da.data2.spzt == 1) {
						$("#dazt").show();
						$("#dazt").css({
							background : 'url(../../image/dasp.png)'
						});
						ditailshowable = true;
					}
				}
				if (da.data4 != undefined) {
					if (da.data4.spzt == 1) {
						$("#ryzt").show();
						$("#ryzt").css({
							background : 'url(../../image/rysp.png)'
						});
						ditailshowable = true;
					}
				}
				if (da.data6 != undefined) {
					if (da.data6.spzt == 1) {
						$("#qxgszt").show();
						$("#qxgszt").css({
							background : 'url(../../image/qxsp.png)'
						});
						ditailshowable = true;
					}
				}
				if (da.data7 != undefined) {
					if (da.data7.spzt == 1) {
						$("#fczt").show();
						$("#fczt").css({
							background : 'url(../../image/fcsp.png)'
						});
						ditailshowable = true;
					}
				}
				if (da.data8 != undefined) {
					if (da.data8.spzt == 1) {
                    	$("#dabghhczt").show();
						$.ajax({
							url : rootPath + "/service/sfdacj/dagz",
							type : 'post',
							async : false,
							data : {
								gdid : params.gdid
							},
							dataType : 'json',
							success : function(result) {
                                if(result[0].sfgs==1){
            						$("#dabghhczt").css({
            							background : 'url(../../image/hcsp.png)'
            						});
                                }else{
            						$("#dabghhczt").css({
            							background : 'url(../../image/bgsp.png)'
            						});
                                }
                                
							}
						})
						ditailshowable = true;
					}
				}
			}
		}
	});
}
// ztree
var isShowMenu = false;
var ztreedata = {
	gh : "",
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
	var gh = nodes[0].id;
	ztreedata.name = name;
	ztreedata.gh = gh;
	v = name;
	var xzsprObj = $("#xzspr");
	xzsprObj.attr("value", v);
}

function showMenu() {
	if (isShowMenu == false) {
		isShowMenu = true;
		var xzsprObj = $("#xzspr");
		var xzsprOffset = $("#xzspr").offset();
		$("#menuContent").fadeIn("fast");

		$("body").bind("mousedown", onBodyDown);
	} else
		hideMenu();

}
function hideMenu() {
	isShowMenu = false;
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(
			event.target).parents("#menuContent").length > 0)) {
		hideMenu();
	}
} // ztree

function isag()// 判断是否是案管
{
	var dlxx = top.currentUserInfo;// 登录信息
	for (var i = 0; i < dlxx.ryjs.length; i++) {
		var ryjscopy = dlxx.ryjs[i];
		if (ryjscopy == 7) {
			return true;
		}
	}
	return false;
}

//////////////////////////////遮罩层

//显示对话框
function showDetail(splx) {
	if(ditailshowable == false)
		return ;
	
	parent.ifeblick();
	$("#xjtjnfDetail").fadeIn("fast");
	updatesplc(splx);
}
function closeDetail() {
	parent.ifenone();
	$("#xjtjnfDetail").fadeOut("fast");
}



function updatesplc(splx){
	
	var params = getRequest();
	var spstid = params.wbid;
	var lcid = null;
	//默认为档案变更或核查
	if(typeof(splx) == 'undefined'){
		return;
	}
	
	$.ajax({
		url :rootPath + "/service/dagz/showSplcsl",
		type : 'post',
		async : false,
		data:{'spstid':spstid,'splx':splx},
		dataType:'json',
		success : function(data) {
			if(data.length > 0){				
				lcid = data[0].lcid;
			}
		}
	})
	//不存在lcid
	if(typeof(lcid) == 'undefined'||lcid ==null){
		return ;
	}
	$.ajax({
		url : rootPath + "/service/sfdasp/showsplc",
		type : 'post',
		async : false,
		data:{'wbid':spstid,'splx':splx,'lcid':lcid},
		dataType:'json',
		success : function(data) {
			$('#splc').empty();
			$('#splc').append(" <tr  id='splctr1' style="+"'"+"background: #d8ecff;height: 31px;line-height: 31px;text-align: left;color: #134671"+"'"+"><td style="+"'"+"width: 40px"+"'"+"></td><td>单位名称</td><td>部门名称</td><td>处理人</td><td>处理意见</td><td>处理时间</td><td>处理结果</td></tr>");
			for(var i=data.length-1;i>=0;i--){
				
				//为审批详情页面Table下的tr、td赋值
				addRow(data[i]);		
			}
				
		}
	});
}


//为审批Table赋值
function addRow(object){
	var sj=(object.spsj).split('-');
	var spyj;
	
	if(typeof(object.spyj)=="undefined"){
		spyj="";
	}else{
		var data = object.spyj;
		var dd = data.split("fqrspyj");
		if(dd.length>2){
			spyj="";
		}else{
			spyj=object.spyj;
		}
	}
	//为审批Table下的tr、td赋值
	$("#splctr1").after("<tr><td></td>"+
							"<td>"+object.spdwmc+"</td>"+
							"<td>"+object.spbmmc+"</td>"+
							"<td>"+object.sprmc+"</td>"+
							"<td>"+spyj+"</td>"+
							"<td>"+sj[0]+"."+sj[1]+"."+sj[2]+"</td>"+
							"<td>"+object.spzt+"</td>"+
						"</tr>");
	
}



