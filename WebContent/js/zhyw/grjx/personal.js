var myChart = null;
var gdid=null;   //归档id全局变量
$(function() {
	var obj = getRequest();
	var daid = obj.daid;
	gdid=daid;
	var totalScore = obj.totalScore;
	updateGrjxdf=totalScore;
	$("#grjxdf").text(totalScore||0+"分"); //个人绩效得分

	//请求装载表格
	$('#dg').datagrid({
		url : 'service/grjx/getgrjxbydaid?daid=' + daid + "&time=" + new Date().getTime(),
		fitColumns : true,
		columns : [ [ {
			field : 'xmmc',
			title : '指标类别',
			width : 40
		}, {
			field : 'xmfz',
			title : '分数',
			width : 30
		}, {
			field : 'zxmmc',
			title : '指标项',
			width : 300,
			styler : function(value, row, index) {
				if (row.editable) {
					return 'background-color:#aae7ab;';
				}
			},
			nowrap : true

		}, {
			field : 'fz',
			title : '分值',
			width : 30,
			styler : function(value, row, index) {
				if (row.editable) {
					return 'background-color:#aae7ab;';
				}
			}
		}, {
			field : 'df',
			title : '得分',
			width : 40,
			editor : {
				type : 'numberbox',
				options : {
					precision : 1
				}
			},
			styler : function(value, row, index) {
				if (row.editable) {
					return 'background-color:#aae7ab;';
				}
			}
		} ] ],
		singleSelect : true,
		showFooter : true,
		onLoadSuccess : mergeCellsAndCalTotal,
		onClickCell : editCell,
	});

	$("#i_button").click(updateGrjx);
	$("#goback").click(goBackPage);	
	
});

var scoreArray = [];
var count = 0;
//单元格合并
function mergeCellsAndCalTotal(data) {
	var rowDatas = $('#dg').datagrid('getRows');
	var len = rowDatas.length;

	// 合并单元格----开始------
	
	//算法改变测试
	var rowspanF1 = 0;//跨行数
	var indexF1 = 0;//跨行起始行
	scoreArray = [];//项目分数数组
	
	for(i= 0 ;i<len;i=i+rowspanF1){
		var score = 0;//一项总分
		var sourceF1 = rowDatas[i].xmmc;
		rowspanF1 = 0;
		indexF1 = i;
		for(j=i;j<len;j++){
			if(sourceF1 == rowDatas[j].xmmc){
				rowspanF1++;
				if(rowDatas[j].df==null||typeof(rowDatas[j].df)=='undefined'||rowDatas[j].df==''){
					rowDatas[j].df = '0';
				}
				score = score + parseFloat(rowDatas[j].df);
			}else{
				//合并单元格
				// 控制 项目名称 列合并
				$("#dg").datagrid('mergeCells', {
 				index : indexF1,
 				field : 'xmmc',
 				rowspan : rowspanF1
 			});
				// 控制 项目分值 列合并
				$('#dg').datagrid('mergeCells', {
					index : indexF1,
					field : 'xmfz',
					rowspan : rowspanF1
				});
				
				scoreArray.push(score.toFixed(1));
				break;
			}
		}
	}
	//最后一次循环后需要做最后的合并处理。
	$("#dg").datagrid('mergeCells', {
		index : indexF1,
		field : 'xmmc',
		rowspan : rowspanF1
	});
	$('#dg').datagrid('mergeCells', {
		index : indexF1,
		field : 'xmfz',
		rowspan : rowspanF1
	});
	scoreArray.push(score.toFixed(1));
	
	//老版本合并单元格
//	var rowspanF1 = 1;
//	var indexF1 = 0;
//	var sourceF1 = '';
//	var score = 0;
//	scoreArray = [];
//
//	if (len > 0) {
//		sourceF1 = rowDatas[0].xmmc;
//		score = parseFloat(rowDatas[0].df);
//	}
//
//	for (i = 1; i < len; i++) {
//		// 合并单元格----第三列【子项】------
//		// Code3:修改这里的colspan值，增加判断。
//		if (sourceF1 == rowDatas[i].xmmc) {
//
//			rowspanF1++;
//			score = score + parseFloat(rowDatas[i].df);
//		} else {
//			scoreArray.push(score);
//			score = parseFloat(rowDatas[i].df);
//			// 控制 项目名称 列合并
//			$('#dg').datagrid('mergeCells', {
//				index : indexF1,
//				field : 'xmmc',
//				rowspan : rowspanF1
//			});
//			// 控制 项目分值 列合并
//			$('#dg').datagrid('mergeCells', {
//				index : indexF1,
//				field : 'xmfz',
//				rowspan : rowspanF1
//			});
//			indexF1 = i;
//			sourceF1 = rowDatas[i].xmmc;
//			if(i == len-1){
//				scoreArray.push(rowDatas[i].df);
//			}
//			rowspanF1 = 1;
//		}
//	}

//控制点击，只有在得分列点击才会刷新柱状图
	if (count == 0) {
		count++;
		refresh(scoreArray);
	}

	// 最后一次循环后需要做最后的合并处理。
//	$('#dg').datagrid('mergeCells', {
//		index : indexF1,
//		field : 'xmmc',
//		rowspan : rowspanF1
//	});
//
//	$('#dg').datagrid('mergeCells', {
//		index : indexF1,
//		field : 'xmfz',
//		rowspan : rowspanF1
//	});

	// 初始化计算总分
	computeScore();
}

// 计算总扣分(先乘100后除100)
function computeScore() {

	var total = 0; // 评分表总分
	var rowDatas = $('#dg').datagrid('getRows');
	// 循环将df(得分)列中的值相加
	for (i = 0; i < rowDatas.length; i++) {
		total = total + parseFloat(rowDatas[i].df);
	}

	// 将计算所得的总分值放入最后一行的df(得分)列中
	$('#dg').datagrid('reloadFooter', [ {
		"xmmc" : "总分",
		"df" : total.toFixed(1)
	} ]);
}

var editIndex = -1; // 当前编辑行

// datagrid单元格点击事件
function editCell(index, field, value) {

	var rowDatas = $('#dg').datagrid('getRows');

	// 如果选中项不是之前的指标项，将之前的指标项终止编辑
	if (editIndex != -1 && editIndex != index) {
		$('#dg').datagrid("endEdit", editIndex);
		mergeCellsAndCalTotal(rowDatas);
	}

	// 判断当前指标项是否可以编辑 如果点击的不是得分单元格，不进行编辑操作
	if (!rowDatas[index].editable || field != "df") {
		return;
	}

	editIndex = index;

	// 初始化分值输入框
	var editor = initScore(index, rowDatas[index]);
	$(editor.target).focus();
}

// 初始化分值输入框
function initScore(index, row) {
	$('#dg').datagrid('beginEdit', index);

	var editorScore = $('#dg').datagrid('getEditor', {
		index : index,
		field : 'df'
	});

	$(editorScore.target).numberbox({
		min : 0,
		max : row.fz * 1,
		precision:1,
		onChange : function(newVal, oldVal) {// 值更改后数据更新并合并单元格
			row.df = newVal;
			var rowDatas = $('#dg').datagrid('getRows');
			mergeCellsAndCalTotal(rowDatas);
			refresh(scoreArray);
		}
	});

	return editorScore;
}
//点击确认时，更新个人绩效信息
function updateGrjx() {
	var obj = getRequest();
	var daid = obj.daid;

	var rows = $("#dg").datagrid("getRows");
	var rowsStr = JSON.stringify(rows);
	var footrow = $("#dg").datagrid("getFooterRows");
	var df = footrow[0].df;
	var data2 = {
		daid : daid,
		ywlx : '1',
		ywzf : df,
		zbkpgl : rowsStr
	};
	$.post("service/grjx/updategrjx", data2, function(data) {
		top.msgAlertInfo(data);
		//$.messager.alert("提示", data, "info");
		$.post(rootPath + "/service/syCount/grjxDf?gdid="+gdid,function(data){
			$("#grjxdf").text(data||0+"分");                            //更新个人绩效得分
		},"json");
	});
}

//柱状图参数信息
var option = {
	title : {
		text : '个人绩效得分情况'
		
	},
	tooltip : {
		axisPointer : {
			type : 'none'
		}
	},
	legend : {
		data : [ '得分' ]
	},
	yAxis : [ {
		type : 'category',
		data : [ '逮捕质量', '风险评估', '审查', '审查终结', '立案监督', '文书制作', '统一业务办案规范',
				'其他', '办案数量', '办案质量','办案效果', '综合业务', '职业操守', '外部评价' ].reverse()
	} ],
	xAxis : [ {
		type : 'value'
	} ],
	series : [ {
		name : '得分',
		type : 'bar',
		data : scoreArray,
		itemStyle : {
			normal : {
				label : {
					show : true,
					position : 'right',
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
	myChart = echarts.init(document.getElementById('main'), macaronsTheme);
	option.series[0].data = array.reverse();
	myChart.setOption(option);
}