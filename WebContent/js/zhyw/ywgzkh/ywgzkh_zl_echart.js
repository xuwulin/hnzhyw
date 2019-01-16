/*********************************************业务工作考核总览E-cahrt（饼状图）js**************************************************************************/

var myChart=[20];
var option=[20];
$(function() {
//	debugger;

	if (!paramsZl) {
		return;
	}
	
	/*ScoreArray = [ {
		"name" : "人均办案数",
		"value" : "8"
	}, {
		"name" : "预防职务犯罪工作",
		"value" : "9"
	}, {
		"name" : "侦查监督工作",
		"value" : "11"
	}, {
		"name" : "公诉工作",
		"value" : "6"
	}, {
		"name" : "未成年人刑事检察工作",
		"value" : "6"
	}, {
		"name" : "刑事执行检察工作",
		"value" : "8"
	}, {
		"name" : "民事行政检察工作",
		"value" : "8"
	}, {
		"name" : "控告检察工作",
		"value" : "6"
	}, {
		"name" : "刑事申诉检察工作",
		"value" : "8"
	}, {
		"name" : "乡镇检察室工作",
		"value" : "7"
	}, {
		"name" : "研究室工作",
		"value" : "6"
	}, {
		"name" : "案件管理工作",
		"value" : "9"
	} ];
	
	XmArray = [ "人均办案数", "预防职务犯罪工作", "侦查监督工作", "公诉工作", "未成年人刑事检察工作",
			"刑事执行检察工作", "民事行政检察工作", "控告检察工作", "刑事申诉检察工作", "乡镇检察室工作", "研究室工作",
			"案件管理工作" ];*/
	
//	showChart(XmArray, ScoreArray, 0);
	showjhchart();
	
	var khlxJsonData;//考核类型数据
	
	//查询当前登录人的所有业务类别
	$.ajax({
		type : "post",
		url : rootPath + "/service/ywlxkh/selectKhlxByDwbmAndBmbm",
		async : false,
		dataType : 'json',
		success : function(res) {
			khlxJsonData = res.khlxData;
		}
	});
	
	//根据当前登录人的单位级别、单位编码、部门编码显示不同的树
	//如果是省院以及当前院案管可以看全部
	if (top.currentUserInfo) {
		var anjs = top.currentUserInfo.ryjs.toString().match("7"); //案管角色
		if("2"==top.currentUserInfo.dwjb||"7" == anjs){
			for(var i=0;i<paramsZl.khsj.length;i++) {
				if("13"==paramsZl.khsj[i].khlx&&paramsZl.khsj[i].khf!=0){
//					showpjfchart(paramsZl.khsj[i].khlx);
					searchAndSetBaslEchart(paramsZl.khsj[i]);
				}else{
					showpjfchart(paramsZl.khsj[i].khlx,paramsZl.khsj[i].khfzid);
				}
			}
		}else{
//			debugger;console.info(khlxJsonData);
			for (var j = 0; j < khlxJsonData.length; j++) {
				for(var i=0;i<paramsZl.khsj.length;i++) {
					if("13"==paramsZl.khsj[i].khlx&&paramsZl.khsj[i].khf!=0){
		//				showpjfchart(paramsZl.khsj[i].khlx);
						searchAndSetBaslEchart(paramsZl.khsj[i]);
					}else{
						if(khlxJsonData[j].KHLX==paramsZl.khsj[i].khlx){
							showpjfchart(paramsZl.khsj[i].khlx,paramsZl.khsj[i].khfzid);
						}
					}
				}
			}
		}
	}
	
	
	
	parent.iFrameHeight();
});
function showChart(XmArray, ScoreArray, index) {
//	debugger;
//	console.info("首页进来的时候e-chartName数据："+JSON.stringify(XmArray)+"\n\r--分数数组："+JSON.stringify(ScoreArray));
	option[index] = {
		noDataLoadingOption : {
			text : "暂无数据",
			effect : 'bubble',
			effectOption : {
				effect : {
					n : 0
				}
			},
			textStyle : {
				fonSize : 20,
			}
		},
		color : [ '#87cefa', '#6495ed', '#ff69b4', '#ba55d3', '#ff7f50',
				'#cd5c5c', '#1e90ff', '#ff6347', '#7b68ee', '#3cb371',
				'#b8860b' ],
		tooltip : {
			trigger : 'item',
			formatter : "{a} <br/>{b} : {c} ({d}%)"
		},
		legend : {
			orient : 'vertical',
			show : false,
			data : XmArray
		},
		calculable : false,
		series : [ {
			name : '分数',
			type : 'pie',
			radius : '60%',
			center : [ '50%', '50%' ],
			itemStyle : {
				normal : {
					label : {
						show : true,
						formatter : "{b} : {c}"
					},
					labelLine : {
						show : true
					}
				},
				emphasis : {
					label : {
						show : false,
						position : 'center',
						textStyle : {
							fontSize : '20',
							fontWeight : 'bold'
						}
					}
				}
			},
			data : ScoreArray
		} ]
	};
	myChart[index] = echarts.init(document.getElementById('main' + index + ''));
	myChart[index].setOption(option[index]);
}
//展示考核分
function showjhchart(){
	var year = paramsZl.year;//paramszl.year;
	var dwbm=paramsZl.dwbm;//paramszl.dwbm;
	
	$.ajax({
				type : "post",
				url : rootPath + "/service/ywlxkh/getkhzl",
				async : false,
				data : {
					dwbm : dwbm,
					year : year,
					sfby : 'Y'
				},
				dataType : 'json',
				success : function(data) {
					// console.log(data);
					
					for (var i = 0; i < data.length; i++) {
						
						var ScoreArray = data[i].ScoreArray;
						var XmArray = [ 100 ];
						for (var j = 0; j < data[i].khsj.length; j++) {
							XmArray.push(data[i].khsj[j].khmc);
						}
						showChart(XmArray, ScoreArray, 0);
					}
				}
			});
}
//展示评价分额chart
function showpjfchart(index,khfzid) {
	
	$.ajax({
		url : rootPath + '/service/ywlxkh/selectzbkpglzbkpdfzgpjfById',
		type : 'post',
		async : false,
		data : {
			khfzid :khfzid
		},
		dataType : 'json',
		success : function(data) {
			var pjf = 0;
			var pjScoreArray=[20];//new Array();
//			var index=parseInt(index);
			if (data.name != undefined&&data.pjdfvalue!=undefined) {
					for (var i = 0; i < data.name.length; i++) {
						var pjScore = {
							name : data.name[i],
							value : data.pjdfvalue[i]
						};
						pjScoreArray.push(pjScore);
						pjf = pjf + data.pjdfvalue[i] * 1.0;
					}
					pjXmArray = data.name; 
					
					$("#pjf"+index).text(float2Zero(pjf).toFixed(2) + "分");
					//为解决e-chart获取到数字  eg:main01 转为 main1 
					var khlxf=parseInt(Number(index));
					showChart(data.name, pjScoreArray, khlxf);
//					console.info("e-chart："+JSON.stringify(data.name)+"\n\r222--分数数组："+JSON.stringify(pjScoreArray));
				}
			}
			
	})
}

//根据考核分值id查询办案数量信息
function searchAndSetBaslEchart(data){
	
	top.msgProgressTip("正在执行，请稍等……");  

    //请求表格数据
    $.ajax({
        url: rootPath + '/service/ywlxkh/selectYwkhfzById',
        type: 'post',
        async: false,
        data: {
            ywkhfzid: data.khfzid
        },
        dataType: 'json',
        success: function (res) {
        	top.msgProgressTipClose();
        	
        	if (res && res.p_zbkpdf) {
        		var kpdf_data = eval('(' + res.p_zbkpdf + ')');
        		var allBasl = 0;//总办案件数
        		var zbrs = res.p_zbrs;//在编人数
        		//办案数量的echart
	       		var bajsScoreArray=[];//办案件数数组
	       		var bajsArray = [];//办案件数echart数据 
        		
                for (var i = 0; i < kpdf_data.length; i++) {
                	allBasl+=kpdf_data[i].basl;
                	var obj = {};
                    obj.name=kpdf_data[i].khzxm;
                    obj.value=kpdf_data[i].basl;
                    bajsScoreArray[i] = obj;
                    bajsArray[i] = kpdf_data[i].khzxm;
                }
                $("#pjf"+data.khlx).text(float2Zero(allBasl/zbrs).toFixed(2) + "件");//基础分
                showChart(bajsArray, bajsScoreArray, data.khlx);
        	}
		}
	});
}

function float2Zero(i) {
    var n = parseFloat(i);
    if (isNaN(n)||'Infinity'==n) {
        n = 0.0;
    }
    return n;
}

