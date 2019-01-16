// parent.min_width2();
var thform = $("#bazlform");
var ul = $("#xyrlist");
var ajxx;
var spzt;// 审批状态
var params=null;
// 判断审批是否通过
function issptg(splx, spstid) {
	$.ajax({
		url : rootPath + "/service/sp/issptg",
		type : 'post',
		async : false,
		data : {
			"spstid" : spstid,
			"splx" : splx
		},
		dataType : 'json',
		success : function(data) {
			spzt = data.spzt;
		}
	});
}
function gettemplate(index, data) {
	var template = null;
	if (index % 2 == 1) {
		template = "<ul class='content_tab2'><li>" + isEmpty(data.xm)
				+ "</li><li>" + isEmpty(data.xb) + "</li><li>"
				+ isEmpty(data.zw) + "</li><li>" + isEmpty(data.zj)
				+ "</li><li>" + isEmpty(data.csrq) + "</li><li>"
				+ isEmpty(data.zjhm)
				+ "</li><li><a href='sfda_xyrxxxx.html?bmsah="
				+ encodeURI(data.bmsah) + "&xyrbh=" + data.xyrbh+"&wbid="+params.wbid
				+ "'>查看</a></li></ul>";
	} else {
		template = "<ul class='content_tab3'><li>" + isEmpty(data.xm)
				+ "</li><li>" + isEmpty(data.xb) + "</li><li>"
				+ isEmpty(data.zw) + "</li><li>" + isEmpty(data.zj)
				+ "</li><li>" + isEmpty(data.csrq) + "</li><li>"
				+ isEmpty(data.zjhm)
				+ "</li><li><a href='sfda_xyrxxxx.html?bmsah="
				+ encodeURI(data.bmsah) + "&xyrbh=" + data.xyrbh+"&wbid="+params.wbid
				+ "'>查看</a></li></ul>";
	}
	return template;
}
var dates = null;
$(function() {
	params = getRequest();
	var bmsah = params.bmsah;
	var tysah = params.tysah;
	var sffc = params.sffc;
	issptg("2", params.wbid);
	$.post(rootPath + "/service/sfdacj/getajxxbytysah", {
		tysah : encodeURI(bmsah)
	}, function(result) {
		result = JSON.parse(result);
		result = result[0];
		ajxx = result;
		$("#ajmc").text(isEmpty(result.ajmc));
		$("#ajlb").text(isEmpty(result.ajlb));
		$("#cbdw").text(isEmpty(result.cbdw));
		$("#cbbm").text(isEmpty(result.cbbm));
		$("#cbr").text(isEmpty(result.cbr));
		$("#ajzt").text(isEmpty(result.ajzt));
		$("#bmsah").text(isEmpty(result.bmsah));
		$("#slrq").text(isEmpty(result.slrq));
		$("#aqzy").text(isEmpty(result.aqzy));
		resizeParentIframe();
	});
	$.ajax({
		url : rootPath + "/service/sfdacj/dagz",
		type : 'post',
		async : false,
		data : {
			gdid : params.wbid
		},
		dataType : 'json',
		success : function(data) {
			data = data[0];
			$.getJSON(rootPath + "/service/xtgl/getmc", {
				ssrdwbm : data.ssrdwbm,
				ssrgh : data.ssr
			}, function(thresult) {
				nametiemtile = thresult.mc + zdydateFormat(data.kssj) + "-"
						+ zdydateFormat(data.jssj) + "的司法档案";
				$(".dagztitle").text(nametiemtile);
			})
		}
	});
	$.post(rootPath + "/service/sfdacj/ajxyr", {
		tysah : tysah
	}, function(result) {
		result = JSON.parse(result);
		dates = result;
		var length = result.length;
		length %= 3;
		for (var i = 0; i < length; i++) {
			ul.append(gettemplate(i, result[i]));
		}

		resizeParentIframe();
	});
	if (isld() == true && spzt != "4" && sffc != "1") {
		$("#baocun").show();
		$("#baxg").attr("disabled", false);
		$("#wfxszqqk").attr("disabled", false);
		$("#baaq").attr("disabled", false);
		$("#sfxc").attr("disabled", false);
		$("#ajzlpcjg").attr("disabled", false);
	} else {
		$("#baocun").hide();
		$("#baxg").attr("disabled", true);
		$("#wfxszqqk").attr("disabled", true);
		$("#baaq").attr("disabled", true);
		$("#sfxc").attr("disabled", true);
		$("#ajzlpcjg").attr("disabled", true);
	}
	$.getJSON(rootPath + "/service/sfdacj/getbazl", {
		bmsah : encodeURI(bmsah)
	}, function(result) {
		result = result[0];
		if (typeof (result) != 'undefined'
				&& typeof (result.bmsah) != 'undefined') {
			$("#baxg").val(result.baxg);
			$("#wfxszqqk").val(result.wfxszqqk);
			$("#baaq").val(result.baaq);
			$("#sfxc").val(result.sfxc);
			$("#ajzlpcjg").val(result.ajzlpcjg);
		}

	});
});
function isld()// 判断是否是领导
{
	for (var i = 0; i < top.currentUserInfo.ryjs.length; i++) {
		var ryjscopy = top.currentUserInfo.ryjs[i];
		if (ryjscopy == 1 || ryjscopy == 2 || ryjscopy == 3 || ryjscopy == 7) {
			return true;
		}
	}
	return false;
};
function submitform() {

	top.msgProgressTip("正在保存……");
	$.post(rootPath + '/service/sfdacj/bazl', {
		bmsah : encodeURI(ajxx.bmsah),
		dwbm : ajxx.dwbm,
		cbrgh : ajxx.cbrgh,
		baxg : $("#baxg").val(),
		wfxszqqk : $("#wfxszqqk").val(),
		baaq : $("#baaq").val(),
		sfxc : $("#sfxc").val(),
		ajzlpcjg : $("#ajzlpcjg").val()
	}, function(result) {
		top.msgProgressTipClose();
		top.msgAlertInfo("保存成功");
		// $.messager.alert("提示", "成功", "info");
	});
}
function isEmpty(obj) {
	if (typeof (obj) == 'undefined')
		return obj = '无';
	return obj;
}

function clickBack() {
	goBackPage();
}
//日期格式转化
function zdydateFormat(date) {
	var newDate = date.substring(0, 4) + "年" + date.substring(5, 7) + "月";
	return newDate;
}
