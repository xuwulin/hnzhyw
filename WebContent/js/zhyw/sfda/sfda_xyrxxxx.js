var params = getRequest();

$(function() {
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
});
$.post(rootPath + "/service/sfdacj/selectxyrdetail", {
	"bmsah" : params.bmsah,
	"xyrbh" : params.xyrbh
}, function(data) {
	document.getElementById('xm').innerHTML = data.xm;
	$("#gyxx").append(
			"<p> 姓别 : " + isEmpty(data.xb) + "</p><br/><p> 民族 : "
					+ isEmpty(data.mz_mc) + "</p><br/><p> 出生日期 : "
					+ isEmpty(data.csrq) + "</p><br/><p> 户籍所在地 : "
					+ isEmpty(data.hjszd_mc) + "</p><br/><p> 证件类型 : "
					+ isEmpty(data.zjlx_mc) + "</p><br/><p> 证件号码 : "
					+ isEmpty(data.zjhm) + "</p>");
	$("#xxgrxx").append(
			" <tr><td><span>曾用名 : " + isEmpty(data.cym)
					+ "</span></td><td><span>绰号 : " + isEmpty(data.ch)
					+ "</span></td><td><span>作案时年龄 : " + isEmpty(data.zasnl)
					+ "</span></td><td><span>年龄 : " + isEmpty(data.nl)
					+ "</span></td></tr><tr><td><span>出生地 : "
					+ isEmpty(data.csd_mc) + "</span></td><td><span>国籍 : "
					+ isEmpty(data.gj_mc) + "</span></td><td><span>住所地 : "
					+ isEmpty(data.zsd_mc) + "</span></td><td><span>住所地详细地址 : "
					+ isEmpty(data.zsdxxdz)
					+ "</span></td></tr><tr><td><span>工作单位所在地 : "
					+ isEmpty(data.gzdwszd_mc)
					+ "</span></td><td><span>工作单位 : " + isEmpty(data.gzdw)
					+ "</span></td><td><span>身份 : " + isEmpty(data.sf_mc)
					+ "</span></td><td><span>受教育情况 : " + isEmpty(data.sjyzk_mc)
					+ "</span></td></tr>");
	$("#zzmmxx").append(
			"<tr><td><span>政治面貌 : " + isEmpty(data.zzmc_mc)
					+ "</span></td><td><span>职务 : " + isEmpty(data.zw)
					+ "</span></td><td><span>职级 : " + isEmpty(data.zj)
					+ "</span></td><td><span>职业 : " + isEmpty(data.zy_mc)
					+ "</span></td></tr><tr><td><span>是否担任实职 : "
					+ isEmpty(data.sfdrsz) + "</span></td><td><span>是否单位领导 : "
					+ isEmpty(data.sfdwld)
					+ "</span></td><td><span>是否农村基层组织人员 : "
					+ isEmpty(data.sfncjczzry)
					+ "</span></td><td><span>是否党委政府领导 : "
					+ isEmpty(data.sfdwzfld)
					+ "</span></td></tr><tr><td><span>是否为两级以上人大代表 : "
					+ isEmpty(data.sfwljysrddb)
					+ "</span></td><td><span>是否为两级以上政协委员 : "
					+ isEmpty(data.sfwljyszxwy)
					+ "</span></td><td><span>法定代理人 : " + isEmpty(data.fddlr)
					+ "</span></td></tr>");
	$("#jyxx").append(
			"<tr><td><span>是否是主犯 : " + isEmpty(data.sfzf)
					+ "</span></td><td><span>是否在逃 : " + isEmpty(data.sfzt)
					+ "</span></td><td><span>羁押开始日期 : " + isEmpty(data.jyksrq)
					+ "</span></td><td><span>羁押到期日期 : " + isEmpty(data.jydqrq)
					+ "</span></td></tr><tr><td><span>羁押预警状态 : "
					+ isEmpty(data.jyyjzt) + "</span></td><td><span>羁押天数 : "
					+ isEmpty(data.jyts) + "</span></td></tr>");
}, "json");
function isEmpty(obj) {
	if (typeof (obj) == 'undefined')
		return obj = '无';
	if (typeof (obj) == "number" && obj == 0)
		return '无';
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