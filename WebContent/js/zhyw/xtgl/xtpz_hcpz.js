var page = 1;
var hcpzdg = $("#hcpz_dg");
var thedata;
var hcpzDwbm = ''; //登录人单位编码
var hcpzDwmc = ''; //登录人单位名称
$(function() {
	//初始化datagrid
    $("#hcpz_dg").datagrid({
		// fit:true,
        rownumbers:true,
        checkbox: true,
        fitColumns: true,
        border: true,
        striped: true,
        loadMsg: '正在处理，请稍后。。。',
        url:rootPath + '/service/hcpz/gethcpz',
        toolbar:'#hcpz_tb',
        autoRowHeight:true,
        pagination:true,
        selectOnCheck:true,
        // singleSelect:false,
        checkOnSelect:true,
        pageSize:10,
        pageList: [10,15,20],
        columns:[[
				  {field:'ck',align:'center',checkbox:true},
                  {field:'ywmc',title:'业务类型',align:'center',width:'10%'},
                  {field:'ywlx',title:'业务类型',align:'center',width:'0%'}, //hidden
                  {field:'hcdwbm',title:'核查单位',align:'center',width:'0%'},//hidden
                  {field:'hcdwmc',title:'核查单位',align:'center',width:'22.5%'},
                  {field:'bhcdwbm',title:'被核查单位',align:'center',width:'0%'},//hidden
                  {field:'bhcdwmc',title:'被核查单位',align:'center',width:'22.5%'},
                  {field:'kssj',title:'开始时间',align:'center',width:'0%'},//hidden
                  {field:'jssj',title:'结束时间',align:'center',width:'0%'},//hidden
                  {field:'nd',title:'交叉核查年份',align:'center',width:'20%'},
                  {field:'jd',title:'交叉核查季度',align:'center',width:'20%'}
		      ]],
		onLoadSuccess:function(data){
			//隐藏列
			hcpzdg.datagrid("hideColumn","kssj");
			hcpzdg.datagrid("hideColumn","jssj");
			hcpzdg.datagrid("hideColumn","ywlx");
			hcpzdg.datagrid("hideColumn","hcdwbm");
			hcpzdg.datagrid("hideColumn","bhcdwbm");
            // clearLoad();
		},
		loadFilter:function(data){
			//把时间转化为年度+季度的格式
			var jd = [2,5,8,11];
			thedata = data;
			var rows = data.rows;
			for(var i = 0 ; i < rows.length; i++){
				var sj = rows[i].kssj.split('-');
				rows[i].nd = sj[0]+"年";
				for(var j = 0 ; j < jd.length ; j++){
					if(jd[j] == sj[1]){
						j++;
						rows[i].jd = "第"+ j +"季度";
					}
				}
			}
			return data;
		}
    });
    clearLoad();
	//加载单位选择列表
	$.getJSON(rootPath + '/service/tree/dwtree?dwbm='+sdwbm+'&showall=true',
			function(res) {
				var result = [];
				result.push(res);
				$(document).ready(function() {
					$.fn.zTree.init($("#treeDemo"), setting, result);
					$.fn.zTree.init($("#treeDemo1"), setting1, result);
					$.fn.zTree.init($("#treeDemo2"), setting2, result);
				});
				$(".textbox-text").css("padding-top", '1px');
			});
	//得到业务类型.
	$.getJSON(rootPath + "/service/hcpz/getywlxlist",function(data) {
		$("#ywlxcombobox").combobox({
			data:data,
			valueField:'ywlx',
			textField:'ywmc'
		});
	});
	//加载新增核查配置年份框的年份列表
	$.getJSON(rootPath + '/service/hcpz/getdqnf',function(data){
		$("#hcnd").combobox({
			data:data,
			valueField:'id',
			textField:'text'
		});
	});
	//根据登录人信息隐藏或显示功能
	$.post(rootPath + '/service/hcpz/getdwjb',{dwbm:top.currentUserInfo.dwbm},function(data){
		data = JSON.parse(data);
		//2 : 省院,判断是否能核查配置（部署）
		if(data.p_dwjb != "2"){
			hcpzDwbm = data.p_dwbm;
			hcpzDwmc = data.p_dwmc;
			$("#addButton").hide();
			$("#editButton").hide();
			$("#delButton").hide();
			$("#citySel2").attr("value",hcpzDwmc);
			clearLoad();
		}
	});
});



/**
 * 根据选择的年度和季度生成 季度的开始时间和结束时间
 */
function creatshijian(){
	//季度
	var newValue = $("#hcjd").combobox("getValues");
	//年度
	var nd = $("#hcnd").combobox("getValues");
	//根据选择的季度生成月和日的信息
	if(newValue>4||newValue<1)
		return ;
	var day = '31';
	if(newValue == 1){
		day = '30';
	}
	newValue --;
	var jd = [2,5,8,11];

	var yue = jd[newValue]*1;
	yue += 2;
	var mkssj = nd + "-" + jd[newValue] +"-1"; 
	var mjssj = nd + "-" + yue +"-"+day;
	// 生成的值赋值到隐藏的日期选择框 供传输数据到后台用
	$("#kssj").datebox("setValue",mkssj);
	$("#jssj").datebox("setValue",mjssj);
}

/**
* 判断单位级别是否相等,不能是相同单位或者不同级别的单位
*/
function compareLevel(dw1,dw2) {
	//判断是否是相同的单位
	if(dw1 == dw2)
		return false;
	//判断单位级别级别是否相同
	var jc1 = dw1%100;
	var jc2 = dw2%100;
	if(jc1 * jc2 == 0){
		if(jc1 == jc2){
			if((dw1%10000)*(dw2%10000) != 0)
				return true;
		}
		return false;
	}
	return true;
}

//新增交叉核查配置
function Insert(bhcdwbm,ywlx,hcdwbm,kssj,jssj) {
	$.post(rootPath + "/service/hcpz/insert",{
		ywlx:ywlx,
		bhcdwbm : bhcdwbm,
		hcdwbm:hcdwbm,
		kssj:kssj,
		jssj:jssj},function(data) {
			if(data == "1"){
				closeDounced();		
				top.msgAlertInfo("添加交叉核查配置成功!");
			}
			else if(data == "-1"){
				top.msgAlertInfo("已配置相同交叉核查配置!");
			}else if(data == "0"){
				top.msgAlertInfo("对应部门不存在,添加交叉核查配置失败!");
			}
			hcpzdg.datagrid("reload");
	});
}
/**
 * 修改交叉核查配置时间,需要额外参数修改前的时间来确定被修改的交叉核查配置
 * @param bhcdwbm
 * @param ywlx
 * @param hcdwbm
 * @param kssj
 * @param jssj
 */
function Updata(bhcdwbm,ywlx,hcdwbm,kssj,jssj) {
	$.post(rootPath + "/service/hcpz/update",{
		ywlx:ywlx,
		bhcdwbm : bhcdwbm,
		hcdwbm:hcdwbm,
		kssj:kssj,
		jssj:jssj,
		oldkssj:updatarowsdata.kssj},function(data) {
			if(data == "1"){
				closeDounced();
				top.msgAlertInfo("修改核查配置成功!");
			}
			else if(data =="-1"){
				top.msgAlertInfo("已配置相同交叉核查配置!");
			}else if(data =="0"){
				top.msgAlertInfo("对应部门不存在,修改交叉核查配置失败!");
			}
			hcpzdg.datagrid("reload");
		});
}


var isShowMenu = false;
var isUpdata = false;

////////////////////////////////被核查单位树
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
////////////////////////////////核查单位树
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
		showIcon : false,
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
	var zTree = $.fn.zTree.getZTreeObj("treeDemo1");
	nodes = zTree.getSelectedNodes();
	v = "";
	var name = nodes[0].text;
	var dwbm = nodes[0].id;
	ztreedata1.name = name;
	ztreedata1.dwbm = dwbm;
	v = name;
	var cityObj = $("#citySel1");
	cityObj.attr("value", v);
}

function showMenu1() {
	// console.log(isShowMenu);
	if (isShowMenu == false) {
		isShowMenu = true;
		// console.log('show: ' +isShowMenu);
		var cityObj = $("#citySel1");
		var cityOffset = $("#citySel1").offset();
		$("#menuContent1").fadeIn("fast");
		$("body").bind("mousedown", onBodyDown1);
	} else
		hideMenu1();
}
function hideMenu1() {
	isShowMenu = false;
	// console.log('hide: ' +isShowMenu);
	$("#menuContent1").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown1(event) {
	if (!(event.target.id == "menuBtn1" || event.target.id == "menuContent1" || $(
			event.target).parents("#menuContent1").length > 0)) {
		hideMenu1();
	}
}
////////////////////////////////datagrid toolbar 单位树
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
		showIcon : false,
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
	var zTree = $.fn.zTree.getZTreeObj("treeDemo2");
	nodes = zTree.getSelectedNodes();
	v = "";
	var name = nodes[0].text;
	var dwbm = nodes[0].id;
	ztreedata2.name = name;
	ztreedata2.dwbm = dwbm;
	v = name;
	var cityObj = $("#citySel2");
	cityObj.attr("value", v);
	
	//修改datagrid的请求参数
	hcpzdg.datagrid({
		queryParams: {
			bhcdwbm: dwbm
		}
	});
}

function showMenu2() {
	
	if(hcpzDwbm != ''){
		return ;
	}
	
	if (isShowMenu == false) {
		isShowMenu = true;
		// console.log('show: ' +isShowMenu);
		var cityObj = $("#citySel2");
		var cityOffset = $("#citySel2").offset();
		$("#menuContent2").fadeIn("fast");
		$("body").bind("mousedown", onBodyDown2);
	} else
		hideMenu2();
}
function hideMenu2() {
	isShowMenu = false;
	// console.log('hide: ' +isShowMenu);
	$("#menuContent2").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown2(event) {
	if (!(event.target.id == "menuBtn2" || event.target.id == "menuContent2" || $(
			event.target).parents("#menuContent2").length > 0)) {
		hideMenu2();
	}
}


/**
 * 判断提交数据中的错误,并提示
 */
function jccuowu(){
	var errmsg;
	if(!compareLevel(ztreedata1.dwbm,ztreedata.dwbm)){
		errmsg = "请选择相同级别的单位!";
	}
	var ywlx = $("#ywlxcombobox").combobox("getValue");
	if(typeof(ywlx) == undefined || ywlx == ""){
		errmsg = "请选择业务类型!";
	}
	creatshijian();
	var kssj = $("#kssj").datebox("getValue");
	var jssj = $("#jssj").datebox("getValue");
	if(kssj > jssj){
		errmsg = "开始时间不能大于结束时间!";
	}
	if(typeof(errmsg) != "undefined"){
		$("#msg").text(errmsg);
		$("#msg").show();
	}else{
		$("#msg").hide();
		if(isUpData == '1')
			Updata(ztreedata.dwbm,ywlx,ztreedata1.dwbm,kssj,jssj);
		else
			Insert(ztreedata.dwbm,ywlx,ztreedata1.dwbm,kssj,jssj);
	}
}
/**
 * 删除核查配置
 */
function hcpzDel(){
	var rows = hcpzdg.datagrid("getSelections");
	var bhcdwbm = '';
	var ywlx = '';
	var kssj = '';
	if(rows.length == 0){
		top.msgAlertInfo("请选择要删除的配置!");
		return;
	}
	//如果选择多个,拼接数据
	for(var i = 0; i < rows.length ; i++){
		bhcdwbm+=rows[i].bhcdwbm + ',';
		ywlx+=rows[i].ywlx + ',';
		kssj+=rows[i].kssj+',';
	}
	$.messager.confirm('确认','您确认想要删除记录吗?',function(r){
		if(r){
			$.post(rootPath + "/service/hcpz/delete",{
				bhcdwbm:bhcdwbm,
				ywlx:ywlx,
				kssj:kssj
			},function(data){
				top.msgAlertInfo("选中"+rows.length+"个,成功删除"+data+"个!");
				hcpzdg.datagrid("reload");
			});
		}
	});

}

var isUpata;
/**
 * 显示修改窗口
 */
var updatarowsdata;
function showDouncedB(){
	isUpData = '1';
	var rows = hcpzdg.datagrid("getSelections");
	if(rows.length == 0){
		top.msgAlertInfo("请选择要修改的配置!");
		return;
	}
	if(rows.length > 1){
		top.msgAlertInfo("请选中一个配置修改!");
		hcpzdg.datagrid("reload");
		return;
	}
	updatarowsdata = rows[0];
	$("#citySel").attr("value", rows[0].bhcdwmc);
	ztreedata.dwbm = rows[0].bhcdwbm;
	ztreedata.name = rows[0].bhcdwmc;
	$("#citySel1").attr("value", rows[0].hcdwmc);
	ztreedata1.dwbm = rows[0].hcdwbm;
	ztreedata1.name = rows[0].hcdwmc;
	$("#ywlxcombobox").combobox("select", rows[0].ywlx);
	$("#kssj").datebox("setValue",rows[0].kssj);
	$("#jssj").datebox("setValue",rows[0].jssj);
	$("#hcjd").combobox("setValue","");
	var sj = rows[0].kssj.split('-');
	$("#hcnd").combobox("select",sj[0]);
	var jd = ++sj[1]/3;
	$("#hcjd").combobox("select",jd);
	showDounced();
}
/**
 * 显示添加窗口
 */
function showDouncedA(){
	isUpData = '0';
	$("#citySel").attr("value", "");
	$("#citySel1").attr("value", "");
	$("#ywlxcombobox").combobox("select", "");
	$("#kssj").datebox("setValue","");
	$("#jssj").datebox("setValue","");
	$("#hcjd").combobox("setValue","");
	showDounced();
}

/**
 * 清除参数,重新加载
 */
function clearLoad(){
	$("#citySel2").attr("value", hcpzDwmc);
	ztreedata2.dwbm = hcpzDwbm;
	ztreedata2.name = hcpzDwmc;
	//修改datagrid的请求参数
	hcpzdg.datagrid({
		queryParams: {
			bhcdwbm: hcpzDwbm
		}
	});
}




