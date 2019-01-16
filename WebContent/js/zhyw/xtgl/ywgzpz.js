var isadd = false;
var saveData = null;
var selectedRow = -1;
var isscan = false;
var topid = null;
var lastRow = -1;
var mdwjb = "";
var mkhlx = "";

var tableid = "";
var dataid = "";


$(function(){
	
	//获取最近五年的年份赋值到下拉框的值 number等于数字几就获取最近几年
	$("#ywkhKhzbYear").combobox({
		url : rootPath + "/service/grjxZbpz/getRecentYear?number=7&getType=1",
		method : 'get',
		valueField:'id', 
		editable : false,
	    textField:'text',
	    onLoadSuccess:function(){
//	    	var selectDatas = $("#ywkhKhzbYear").combobox("getData");
//	    	if(selectDatas&&selectDatas.length>0){
//	    		$("#ywkhKhzbYear").combobox("setValue",selectDatas[0].id);
//	    	}
	    	//默认只读
//			$("#ywkhKhzbYear").combobox("readonly",true);
	    }
	});
	
	//状态
/*	$("#ywkhKhzbStatus").combobox({
		editable : false,
		valueField:'id', 
	    textField:'text',
	    data:[{
	    	id : ' ',
	    	text : '==请选择=='
	    },{
	    	id : '0',
	    	text : '启用'
	    },{
	    	id : '1',
	    	text : '禁用'
	    }]
	});*/
	
	var params = getRequest();
	isadd = params.isadd=='1';
	
//	if(!isadd){
//		khlxdata = khlxdata1;
//	}
	
	if(params.isCk==2){
		$("#ywlxDiv").show();
		$("#scan").show();
		$("#scDiv").show();
	}
	
	ywgzpz2 = ywgzpz;
	ywgzkh2 = ywgzkh;
	
	$("#khlxselect").combobox({
		valueField:'khlx',    
	    textField:'khmc',
	    editable : false,
	    url:rootPath+'/service/ywkhkhlxpz/getZhyw',
//		data:khlxdata,
	    onLoadSuccess:function(){
		    var selectDatas = $("#khlxselect").combobox("getData");
	    	/*if(selectDatas&&selectDatas.length>0){
	    		$("#khlxselect").combobox("setValue",selectDatas[0].khlx);
	    	}*/
	    },
		onChange:function(newValue, oldValue){
			//替换表头
			if(newValue == '13'&&isadd){
				ywgzpz = ywgzpz1;
				ywgzkh = ywgzkh1;
				loadTable(ywgzpz);
			}if(oldValue == '13'&&isadd){
				ywgzpz = ywgzpz2;
				ywgzkh = ywgzkh2;
				loadTable(ywgzpz);
			}
		}
	});
	var sy = document.getElementById("sylxsy");
	var jcy = document.getElementById("sylxjcy");
	sy.checked = false;
	jcy.checked = false;

	$('#dg').datagrid({
		onSelect: function(index,rowData){
			if(isscan){
				if(top.currentUserInfo.dwjb==2){
					top.msgAlertWarning("预览模式下不能编辑表格");
				}
				return;
			}
			$(this).datagrid('endEdit', lastRow);
			selectedRow = index;
			lastRow = index;
		}
	});
	//新增页面初始化
	if(isadd){
		loadTable(ywgzpz);  ///  ywgzpz
		$('#dg').datagrid('insertRow',{
			index: 0,	// 索引从0开始
			row: {
				xmfz: 0,
				cxfz: 0
			}
		});
	}else{
		
		//开启日期下拉框
		var selectDatas = $("#ywkhKhzbYear").combobox("getData");
    	if(selectDatas&&selectDatas.length>0){
    		$("#ywkhKhzbYear").combobox("readonly",false);
    	}
		initywgzpzpage(params.id);
	}
});
//修改页面初始化
function initywgzpzpage(pid){
	
	//查询业务考核指标表并填充页面数据
    $.ajax({
        url : rootPath + "/service/ywgzpz/selectbyid",
        type : 'post',
        async : false,
        data : {
        	"id":pid
        },
        dataType : 'json',
        success : function(result) {
        	saveData = JSON.parse(result.pznr);
        	tableid = result.pzbtid;
        	dataid = result.id;
        	mdwjb = result.dwjb;
        	mkhlx = result.khlx;
        	if(mkhlx =='13'){
				ywgzpz = ywgzpz1;
				ywgzkh = ywgzkh1;
        	}
        	checkboxc(result.dwjb);
        	$("#khlxselect").combobox("select",result.khlx);  //考核类型
        	$("#ywkhKhzbYear").combobox("select",checkStrReturnEmpty(result.ssrq));  //年份
//        	$("#ywkhKhzbStatus").combobox("select",checkStrReturnEmpty(result.status));  //状态
        }
    });
	//从基本数据中拿到表头id获取表头数据
/*    $.ajax({
        url : rootPath + "/service/ywgzpz/selectbyidbt",
        type : 'post',
        async : false,
        data : {
        	"id":tableid
        },
        dataType : 'json',
        success : function(result) {
        	//使用默认数据
        	//ywgzpz = JSON.parse(result.pzbtsj);
        	//ywgzkh = JSON.parse(result.btsj);
        }
    });*/
    
    
    //初始化datagrid【这儿的表头数据是直接由页面写死的，方便调整；之前是直接取表头表的数据】
    loadTable(ywgzpz);
	
}


/**
 * 数据入库
 * @param tabletop 配置部分的表头
 * @param tableData 表格中的数据
 * @param khbtdata 考核使用的表头
 */
function savealldata(tabletop,tableData,khbtdata){
	//判断信息是否正确
	var tdwjb = checkboxc();
	if(tdwjb.length < 3){
		top.msgAlertInfo("请至少选择一个适用院!");
		return ;
	}
	var chenge  =  false;
	var select = $("#khlxselect").combobox("getValue");
	if(select != mkhlx || tdwjb != mdwjb){
		chenge = true;
	}
	
	var ssYear = $("#ywkhKhzbYear").combobox("getValue");  //所属年份
//	var status = $("#ywkhKhzbStatus").combobox("getValue"); //状态【启用/禁用】
	
	var tempp = "1";
	//关键信息发生改变,判断是否存在相同配置
	if(chenge){
		$.ajax({
			url : rootPath + "/service/ywgzpz/search",
	        type : 'post',
	        async : false,
	        data : {
	        	"dwjb":tdwjb,
	        	"khlx":select,
	        	"id":dataid,
	        	"ssrq" : ssYear
	        },
	        success : function(result) {
	        	if(result != "0"){
	        		top.msgAlertInfo("该类型的配置已存在!");
	        		tempp = "0";
	        	}
	        }
		});
	}
	if(tempp == "0"){
		return ;
	}
    $.ajax({
        url : rootPath + "/service/ywgzpz/insertbt",
        type : 'post',
        async : false,
        data : {
        	"id":tableid,
        	"pzbtsj":tabletop,
        	"btsj":khbtdata
        },
        success : function(result) {
        	if(result != '0' && result.length > 1){
        		tableid = result;
        	}else{
        		top.msgAlertInfo("保存失败!");
        		return ;
        	}
        }
    });
	//表格中的数据和表头id 一起存入
    $.ajax({
        url : rootPath + "/service/ywgzpz/insert",
        type : 'post',
        async : false,
        data : {
        	"id":dataid,
        	"dwjb":tdwjb,
        	"khlx":select,
        	"pznr":tableData,
        	"pzbtid":tableid,
        	"ssrq" : ssYear,
        	"status" : ""
        },
        success : function(result) {
        	if(result != '0' && result.length > 1){
        		dataid = result;
        		top.msgAlertInfo("保存成功!");
        	}else{
        		top.msgAlertInfo("保存失败!");
        	}
        }
    });
}

/**
 * 修改返回dwjb
 * @param dwjb
 */
function checkboxc(dwjb){
	
	var sy = document.getElementById("sylxsy");
	var jcy = document.getElementById("sylxjcy");
	
	
	if(typeof(dwjb) != 'undefined'){
		sy.checked = false;
		jcy.checked = false;
		
		if(dwjb.indexOf("3") >=0){
			sy.checked = true;
		}
		if(dwjb.indexOf("4") >=0){
			jcy.checked = true;
		}
		
	}
	var temp='[';
	
	if(sy.checked == true){
		temp += "3";
		if(jcy.checked == true){
			temp += ",4";
		}
	}else if(jcy.checked == true){
		temp += "4";
	}
	temp +=']';
	
	return temp;
}

/**
 * 合并单元格
 */
function hbdatagrid(){
	var rowDatas = $('#dg').datagrid('getRows');
	var len = rowDatas.length;
	
	var premc = '';
	var start = 0; //合并开始列
	var count = 0; //合并类数量
	
	for(var i = 0 ; i < len ; i++ ){
		var tempmc = rowDatas[i].khzxm;
		if(premc == tempmc){
			count++;
		}else{
			//合并单元格
			// 控制 项目名称 列合并
			$("#dg").datagrid('mergeCells', {
				index : start,
				field : 'khzxm',
				rowspan : count
			});
			//合并单元格
			// 控制 项目名称 列合并
			$("#dg").datagrid('mergeCells', {
				index : start,
				field : 'xmfz',
				rowspan : count
			});
			premc = tempmc;
			count = 1;
			start = i;
		}
	}
	//合并单元格
	// 控制 项目名称 列合并
	$("#dg").datagrid('mergeCells', {
		index : start,
		field : 'khzxm',
		rowspan : count
	});
	//合并单元格
	// 控制 项目名称 列合并
	$("#dg").datagrid('mergeCells', {
		index : start,
		field : 'xmfz',
		rowspan : count
	});
}




//////////////////copy

function save_data(){				//保存数据
	beforeSvae();
	var tableTop = JSON.stringify(ywgzpz);				//表头入库
	var data = $('#dg').datagrid("getRows");		
	var tableData = JSON.stringify(data);					//表体入库
	var khbtdata=JSON.stringify(ywgzkh);
	savealldata(tableTop, tableData,khbtdata);
}
function addfile(wjms){
	showDetail(wjms);
}
function showDetail(wjms) {
	$("#addfile").fadeIn("fast");
	$("#wjms").text(wjms);
}
function closeDetail() {
	$("#addfile").fadeOut("fast");
}

function SubmitForm(){		//上传文件
	
	var filepath = $("input[name='file']").val();
	var file = filepath.split(".");
	var filetype = file[1];
	if("class"!=filetype){
		top.msgAlertInfo("上传文件格式不对，请上传class类文件!");
	}else{
		var url = rootPath + "/service/grjxZbpz/upload";
		$("#addfileform").form('submit',{
			url : url,
			onSubmit : function(param) {
			},
			success : function(data) {
				var successdate = eval('('+data+')');
				// console.log(successdate.id);
				$('#dg').datagrid('updateRow',{
					index: curentindex,
					row: {
						wjid: successdate.id,
						wjms:successdate.message
					}
				});
				top.msgAlertInfo("文件上传成功!");
				$("#wjms").text(successdate.message);
			}
		});
	}
}



//返回到上一个页面
function goBackPage() {// 参考history.js
    location.href = "../ywkh/ywkh_khdwpz.html?type=4";
    // if(!history) {
    //     return
    // }
    //
    // history.back();
}

//请求装载表格
function loadTable(column){
	$('#dg').datagrid({
		data:saveData,
		columns :column,
		autoRowHeight:true,
		singleSelect : true,
		showFooter : true,
		//onLoadSuccess : mergeCellsAndCalTotal,
		onDblClickRow : editCell
	});
	
	$("#dg").datagrid('fixRowHeight',1);
}


function addRow(){
	
	if (selectedRow == -1){
		top.msgAlertWarning("请先选中一行记录，我们将在选中记录下面新增记录");
		return
		}
	
	
	
	$('#dg').datagrid('insertRow',{
		index: selectedRow+1,	// 索引从0开始
		row: {
			/*name: '新名称',
			age: 30,
			note: '新消息'*/
			xmfz: 0,
			cxfz: 0
		}
	});
	
	
	//重新计算高度
	parent.iFrameHeight();
	
//	selectedRow = -1;
}
function removeit(){			//删除行
	if (selectedRow == -1){
		top.msgAlertWarning("请先选中您要删除的记录");
		return}
	$('#dg').datagrid('cancelEdit', selectedRow)
			.datagrid('deleteRow', selectedRow);
	selectedRow = -1;
}



function beforeSvae(){							//在提交保存之前 记录每条记录的行号
	var data = $('#dg').datagrid("getRows");
	var datalength = data.length;
	for(var i=0;i<datalength;i++){
		$('#dg').datagrid('endEdit', i);
	}
	
	$('#dg').datagrid('acceptChanges');
}

function editCell(index, field, value) {	//点击单元格 并修改数据
	beforeSvae();
	if(top.currentUserInfo.dwjb==2){
		$('#dg').datagrid('selectRow', index).datagrid('beginEdit', index);
	}
}



function scan(){//浏览模式
	$("#edit").css("display","inline");
	$("#scan").css("display","none");
	$("#addRow").css("display","none");
	$("#removeit").css("display","none");
	endedit();
	hbdatagrid();
	isscan = true;
}
function endedit(){
	$('#dg').datagrid('endEdit', lastRow);
}
function edit(){
	$("#scan").css("display","inline");
	$("#addRow").css("display","inline");
	$("#removeit").css("display","inline");
	$("#edit").css("display","none");
	endedit();
	isscan = false;
	var rowDatas = $('#dg').datagrid('getRows');
	$('#dg').datagrid({
		data: rowDatas
	});
}







///////////////////






//业务考核的考核指标表头数据。
var ywgzpz = [[ {
	"field" : "khzxm",
	"title" : "考核子项目",
	"width" : 80,
	"align" : "center",
	"editor" : "textbox"
}, {
	"field" : "xmfz",
	"title" : "最高评价分",
	"width" : 80,
	"align" : "center",
	"editor" : "numberbox"
}, {
	"field" : "pfbf",
	"title" : "评分办法",
	"width" : 900,
	"align" : "left",
	"editor" : "textarea"
}, {
	"field" : "cxfz",
	"title" : "此项得分",
	"width" : 80,
	"align" : "center",
	"editor" : {
		type : "numberbox",
		options : { 
			value : 0,
			precision:1
	    } 
	}
},{
	"field" : "cxType",
	"title" : "查询编号/类型",
	"width" : 90,
	"align" : "center",
	"editor" : {
		type : "numberbox",
		options : { 
			value : 0,
			precision:0
	    } 
	}
},{
	"field" : "bz",
	"title" : "备注信息",
	"width" : 100,
	"align" : "center",
	"editor" : "textarea"
},{
	"field" : "sfxtpf",
	"title" : "是否系统评分",
	"width" : 100,
	"align" : "center",
	"editor" : {
		"type" : "combobox",
		"options" : {
			"data" : [ {
				"text" : "手动录入",
				"value" : "0"
			}, {
				"text" : "系统计算",
				"value" : "1"
			} ]
		}
	},
	"formatter" : function(value, row, index) {
		if (value == "1") {
			curentindex = index;
			/*if(isinit){
				addfile();
			}*/
			return "自动计算";
		} 
		return "";
	}
},{
	"field" : "wjid",
	"title" : "文件id",
	"hidden" : true
}, {
	"field" : "fj",
	"title" : "附件",
	"width" : 90,
	"align" : "center",
	"formatter" : function(value, row, index) {
		if (row.sfxtpf == "1") {
			var wjms = JSON.stringify(row.wjms);
			return "<input class='addfilebt' type='button' value='上传附件' onclick=addfile("+wjms+")>";
		} 
		return "";
	}
}]];
var  ywgzpz2 = '';

//人均办案数
var ywgzpz1 = [[ {
	"field" : "khzxm",
	"title" : "考核子项目",
	"width" : 80,
	"align" : "center",
	"editor" : "textbox"
}, {
	"field" : "xmfz",
	"title" : "最高评价分",
	"width" : 80,
	"align" : "center",
	"editor" : "numberbox",
	"hidden": true
}, {
	"field" : "sfxtpf",
	"title" : "是否系统评分",
	"width" : 80,
	"align" : "center",
	"editor" : {
		"type" : "combobox",
		"options" : {
			"data" : [ {
				"text" : "手动输入",
				"value" : "0"
			}, {
				"text" : "自动计算",
				"value" : "1"
			} ]
		}
	},
	"formatter" : function(value, row, index) {
		if (value == "1") {
			curentindex = index;
			/*if(isinit){
				addfile();
			}*/
			return "自动计算";
		} 
		return "";
	}
},{
	"field" : "wjid",
	"title" : "文件id",
	"hidden" : true
}, {
	"field" : "fj",
	"title" : "附件",
	"width" : 90,
	"formatter" : function(value, row, index) {
		if (row.sfxtpf == "1") {
			var wjms = JSON.stringify(row.wjms);
			return "<input class='addfilebt' type='button' value='上传附件' onclick=addfile("+wjms+")>";
		} 
		return "";
	}
}]];


//人均办案数
var ywgzkh1 = [[ {
		"field" : "khzxm",
		"title" : "考核子项目",
		"width" : 80,
		"rowspan" : 2,
		"align" : "center",
	}, {
		"field" : "xmfz",
		"title" : "最高评价分",
		"width" : 80,
		"rowspan" : 2,
		"align" : "center",
	},{
		"field" : "pjdf",
		"title" : "评价得分",
		"width" : 60,
		"rowspan" : 2,
		"align":"center"
    }]];

var ywgzkh = [[ {
	"field" : "khzxm",
	"title" : "考核子项目",
	"width" : 80,
	"rowspan" : 2,
	"align" : "center",
}, {
	"field" : "xmfz",
	"title" : "最高评价分",
	"width" : 80,
	"rowspan" : 2,
	"align" : "center",
}, {
	"field" : "pfbf",
	"title" : "评分办法",
	"width" : 600,
	"rowspan" : 2,
	"align" : "left",
}, {
	"title":"部门评分",
	"colspan" : 2
},{
	"title":"案管评分",
	"colspan" : 2
},{
	"field" : "pjdf",
	"title" : "评价得分",
	"width" : 60,
	"rowspan" : 2,
	"align":"center"
}],[{ 
    "field" : "bmpf",
	"title" : "得分",
	"width" : 60,
	"align":"center",
	"rowspan":1,
	"editor"  : true
},{
	"field":"bmpfbz",
	"title":"备注",
	"width":60,
	"rowspan":1,
	"formatter":true,
	"align":"center"
},{ 
    "field" : "agpf",
	"title" : "得分",
	"width" : 60,
	"rowspan":1,
	"align":"center",
	"editor"  : true
},{
	"field":"agpfbz",
	"title":"备注",
	"width":60,
	"rowspan":1,
	"formatter":true,
	"align":"center"
}]];

var  ywgzkh12 = '';
// 1.5  2.10  3.2   4.3
// 5.1  6.4   10.6



khlxdata = [ {
	text : '未成年人刑事检察工作',
	value : "01"
}, {
	text : '侦查监督工作',
	value : "02",
	selected : true
}, {
	text : '公诉工作',
	value : "03"
}, {
	text : '刑事执行检察工作',
	value : "04"
}, {
	text : '职务犯罪侦查工作',
	value : "05"
}, {
	text : '乡镇检察室工作',
	value : "06"
}, {
	text : '民事行政检察工作',
	value : "07"
}, {
	text : '控告检察工作',
	value : "08"
}, {
	text : '刑事申诉检察工作',
	value : "09"
}, {
	text : '预防职务犯罪工作',
	value : "10"
}, {
	text : '研究室工作',
	value : "11"
}, {
	text : '案件管理工作',
	value : "12"
}, {
	text : '人均办案数',
	value : "13"
}];

/*khlxdata1 = [ {
	text : '未成年人刑事检察工作',
	value : "01"
}, {
	text : '侦查监督工作',
	value : "02",
	selected : true
}, {
	text : '公诉工作',
	value : "03"
}, {
	text : '刑事执行检察工作',
	value : "04"
}, {
	text : '职务犯罪侦查工作',
	value : "05"
}, {
	text : '乡镇检察室工作',
	value : "06"
}, {
	text : '民事行政检察工作',
	value : "07"
}, {
	text : '控告检察工作',
	value : "08"
}, {
	text : '刑事申诉检察工作',
	value : "09"
}, {
	text : '预防职务犯罪工作',
	value : "10"
}, {
	text : '研究室工作',
	value : "11"
}, {
	text : '案件管理工作',
	value : "12"
}];*/



