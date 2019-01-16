var dwjb;//单位级别
if (top.currentUserInfo) {
	if (top.currentUserInfo.dwjb) {
		dwjb = top.currentUserInfo.dwjb;
	}
}

$(function(){
	// 初始化司法档案柱状图显示
	initSfdaEchart();
	initSfdaWhcdChart();//文化程度
	initSfdaOthersChart();//司法责任、荣誉技能
//	initDwbmTree();//单位树
});

function initSfdaEchart(){
	
//	var ldSign = getDlrJs();//角色标示
	var year =$("#sfda_tj_year").val();//参数年份
	
	$.ajax({ // 该代办业务是否已经被处理 最新时间的 档案的状态 判断
		url:rootPath+"/service/sfda/getSfdaCountBySign",
		type:'get',
		dataType:'json',
		async:'false',
		data:{
			//ldSign : ldSign
			year : year,
			dwjb : dwjb
		},
		success:function(res){
//			debugger;
			if (res) {
				// 柱状图显示
//				$("#show_sfda_tj_div_p_title").text("司法档案的数量-"+top.currentUserInfo.bmmc);
				//判断角色显示数据
				showChartByJs(res);
			}
		 }
	});
}
//对档案显示柱状图赋值
function getEcharOfDa(title,name,maxWidth,nameArray,valueArray){
	var option_zzt = {
            tooltip: {
                show: true
            },
            title : {
	        	  text : title,
	        	  x : 'center',
	        	  textStyle:{
	        		  fontSize: 18,
	        		  fontFamily:'微软雅黑',
	        		  fontWeight: 'bold',
	        		  color: '#FF7F50'
	        	  } 
	          },
            xAxis : [
                {
                    type : 'category',
                    data : nameArray//["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    "name":name,
                    barWidth : maxWidth,
                    "type":"bar",
                    "data":valueArray,//[5, 20, 40, 10, 10, 20],
                    markPoint : {
                        data : [
                            {type : 'max', name: '最大值'},
                            {type : 'min', name: '最小值'}
                        ]
                    }
                }
            ]
        };
	return option_zzt;
}

function showChartByJs(res){
	
	var minWidth = 15;//柱状图的最小宽度
	var nameArray = [];//柱状图的列名数组
  	var valueArray = [];//柱状图的值数组
		
	var title = "";
	var name = "";
//   	debugger;
	if (res[res.length-1].sign!="1"&&res[res.length-1].sign!="2"
			&&res[res.length-1].sign!="3"&&res[res.length-1].sign!="5") {
		title = ['检察官档案数量统计'];
		name = "检察官档案数量";
		for (var i = 0; i < res.length-1; i++) {
		   	nameArray.push(res[i].year);
		   	valueArray.push(res[i].countDa);
		}
	}else{
		
		if ("2"==res[res.length-1].sign) {
			title = ['检察院部门档案数量统计'];
			name = "年份-检察院-部门-档案数量";
			for (var i = 0; i < res.length-1; i++) {
			   	nameArray.push(res[i].year+"-"+res[i].dwjc+"-"+res[i].bmmc);
			   	valueArray.push(res[i].countDa);
			}
		}else{
			
			title = ['检察院档案数量统计'];
			name = "年份-检察院档案数量";
			for (var i = 0; i < res.length-1; i++) {
			   	nameArray.push(res[i].year+"-"+res[i].dwjc);
			   	valueArray.push(res[i].countDa);
			}
			//开启DIV获取最近十年
		   	$("#show_sfda_tj_div_p_year").css("display","block");
		   	var tempYear =$("#sfda_tj_year").val();//上一次点击的年份值
		   	 	
		   	//显示下拉年份
		   	var selectId = "sfda_tj_year";
		   	showOptionYear(selectId,tempYear);
		}
		
	}
//	valueArray.sort();
//	nameArray.sort();
   	
	
	var zztChart = echarts.init(document.getElementById('show_sfda_tj_div_zzt'));
	var charData;
	if (nameArray.length>0) {
		charData = getEcharOfDa(title,name,minWidth,nameArray,valueArray);//柱状图显示的档案数据
	}else{//无数据的情况
		charData = getEcharOfDa(title,name,minWidth,["暂无数据"],["暂无数据"]);//柱状图显示的档案数据
	}
	zztChart.setOption(charData);
}

//选中年份查询档案事件
$("#sfda_tj_year").change(function(){
//	var ldSign = getDlrJs();
	var year =$("#sfda_tj_year").val();
	
	$.ajax({ // 该代办业务是否已经被处理 最新时间的 档案的状态 判断
		 url:rootPath+"/service/sfda/getSfdaCountBySign",
		 type:'get',
		 dataType:'json',
		 async:'false',
		 data:{
			 /*ldSign : ldSign,
			 year : year*/
			 year : year,
			 dwjb : dwjb
		 },
	     success:function(res){
//	    	 console.info(res);
//	    	 debugger;
	    	 if (res) {
	    		 // 柱状图显示
	    		 $("#show_sfda_tj_div_p_year").css("display","block");
	    		 //判断角色显示数据
	    		 showChartByJs(res);
			}
	 	 }
	 });
	
});

//初始化司法档案下的司法责任、荣誉技能个数
function initSfdaOthersChart(){
	//调用角色
	//传入参数调用请求
	//柱状图显示
	
//	var ldSign = getDlrJs();
	var year = $("#show_sfda_ryzr_count_year").val();
	
	$.ajax({ // 该代办业务是否已经被处理 最新时间的 档案的状态 判断
		 url:rootPath+"/service/sfda/getRyjnSfzrCount",
		 type:'get',
		 dataType:'json',
		 async:'false',
		 data:{
//			 ldSign : ldSign,
			 year : year,
			 dwjb :dwjb
		 },
	     success:function(res){
//	    	 console.info(res);
	    	 if (res) {
				showCharByJs(res);
			}
	 	 }
	 });
	
	
}

//
function showCharByJs(res){
	var zztChart = echarts.init(document.getElementById('show_sfda_ryzr_count_zzt'));
	var title = ['责任追究','荣誉奖励'];
	var minWidth = 15;
//	if (res.length<10) {
//		minWidth = 20;
//	}
	var xAxisArray = [];//柱状图下面的显示名称数组
	var ryjnArray = [];//荣誉技能显示数据数组
	var sfzrArray = [];//司法责任显示数据数组
	var titleText = "";
//	debugger;
	if (res[res.length-1].sign!="1"&&res[res.length-1].sign!="2"
			&&res[res.length-1].sign!="3"&&res[res.length-1].sign!="5") {
		titleText= "检察官荣誉奖励/责任追究数量统计";//标题文本
		
		for (var i = 0; i < res.length-1; i++) {
			xAxisArray.push(res[i].year);
			ryjnArray.push(res[i].ryjnCount);
			sfzrArray.push(res[i].sfzrCount);
		}
	}else{
		
		if ("2"==res[res.length-1].sign) {
			titleText= "年份-检察院-部门-荣誉奖励/责任追究数量统计";//标题文本
			for (var i = 0; i < res.length-1; i++) {
				xAxisArray.push(res[i].year+"-"+res[i].dwjc+"-"+res[i].bmmc);
				ryjnArray.push(res[i].ryjnCount);
				sfzrArray.push(res[i].sfzrCount);
			}
		}else{
			
			$("#show_sfda_ryzr_count_p_year").css("display","block");
			var tempYear = $("#show_sfda_ryzr_count_year").val();//上一次点击的年份值
			var selectId = "show_sfda_ryzr_count_year";
			showOptionYear(selectId,tempYear);
			
			titleText= "检察院荣誉奖励/责任追究数量统计";//标题文本
			for (var i = 0; i < res.length-1; i++) {
				xAxisArray.push(res[i].year+"-"+res[i].dwjc);
				ryjnArray.push(res[i].ryjnCount);
				sfzrArray.push(res[i].sfzrCount);
			}
		}
	}
	
	var seriesArray = [
        {
            name:'荣誉奖励',
            type:'bar',
            barWidth : minWidth,
            data:ryjnArray
        },
        {
            name:'责任追究',
            type:'bar',
            barWidth : minWidth,
            stack: '广告',
            data:sfzrArray
        }
    ];
	
//	 xAxisArray = ['三检','五检','海秀检','琼检','琼检一分','琼海检','院领导'];
	
	var charData = showSfdaOthersChart(titleText,title,xAxisArray,seriesArray);//柱状图显示的档案数据
	zztChart.setOption(charData);
} 


function showSfdaOthersChart(titleText,title,xAxisArray,seriesArray){
	var sfdaOthersOption = {
			title : {
			       text: titleText,
			       x : 'center',
			       textStyle:{
		        		  fontSize: 18,
		        		  fontFamily:'微软雅黑',
		        		  fontWeight: 'bold',
		        		  color: '#FF7F50'
		        	  }
			    },
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    legend: {
		        data:title,//['司法责任','荣誉技能']
		        x:'right'
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data : xAxisArray//['三检','五检','海秀检','琼检','琼检一分','琼海检','院领导']
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : seriesArray/*[
		        {
		            name:'司法责任',
		            type:'bar',
		            barWidth : 30,
		            data:[320, 332, 301, 334, 390, 330, 320]
		        },
		        {
		            name:'荣誉技能',
		            type:'bar',
		            barWidth : 30,
		            stack: '广告',
		            data:[120, 132, 101, 134, 90, 230, 210]
		        }
		    ]*/
		};
	return sfdaOthersOption;
}


//初始化司法档案下的文化程度个数
function initSfdaWhcdChart(){
	//调用角色
	var year = "";
	//传入参数调用请求
	$.ajax({ // 该代办业务是否已经被处理 最新时间的 档案的状态 判断
		 url:rootPath+"/service/sfda/getRyWhcdByDwGh",
		 type:'get',
		 dataType:'json',
		 async:'false',
		 data:{
//			 ldSign : ldSign,
			 year : year,
			 dwjb :dwjb
		 },
	     success:function(res){
	    	 console.info(res);
	    	 if (res) {
				showCharWhcd(res);
			}
	 	 }
	 });
	
}

function showOptionYear(selectId,tempYear){
	
	$("#"+selectId).empty();//清空下拉框
	var year = new Date().getFullYear();
	
	for (var i = 0; i < 10; i++) {
		var $option = "<option value='"+(year-i)+"'>"+(year-i)+"</option>";
		$("#"+selectId).append($option);
	}
	
	if (tempYear) {
		$("#"+selectId).val(tempYear);
	}
	
	resizeParentIframe();
}

//选中年份查询档案事件
$("#show_sfda_ryzr_count_year").change(function(){
//	var ldSign = getDlrJs();
	var year =$("#show_sfda_ryzr_count_year").val();
	
	$.ajax({ // 该代办业务是否已经被处理 最新时间的 档案的状态 判断
		 url:rootPath+"/service/sfda/getRyjnSfzrCount",
		 type:'get',
		 dataType:'json',
		 async:'false',
		 data:{
//			 ldSign : ldSign,
			 year : year,
			 dwjb : dwjb
		 },
	     success:function(res){
//	    	 console.info(res);
	    	 if (res) {
	    		 // 柱状图显示
	    		 //判断角色显示数据
	    		 showCharByJs(res);
			}
	 	 }
	 });
	
});

/*function initDwbmTree(){
	if (top.currentUserInfo) {
		var dwbm = top.currentUserInfo.dwbm;
		$.getJSON(rootPath + '/service/tree/dwtree?dwbm=' + dwbm, function(res) {
			var result = [];
			$(document).ready(function() {
				$.fn.zTree.init($("#treeDemo"), setting, result);
			});
			$('#citySel').attr("value", ztreedata.name);
		});
	}
}*/

function showCharWhcd(res){
	
	var zztChart = echarts.init(document.getElementById('show_sfda_whcd_count_zzt'));
	//柱状图显示
	var title = "";
	var name = "";
	var minWidth = 15;
	var nameArray = [];
	var valueArray = [];
//	debugger;
	if (res[res.length-1].sign!="1"&&res[res.length-1].sign!="2"
			&&res[res.length-1].sign!="3"&&res[res.length-1].sign!="5") {
		title = "检察官文化程度统计";
		name = "检察官文化程度";
		for (var i = 0; i < res.length-1; i++) {
//			console.log("--===="+res[i].dwbm);
			nameArray.push(res[i].whcd);
			valueArray.push(res[i].dwmc);
		}
//		nameArray.sort();
//		valueArray.sort();
	}else{
		$("#sfda_whcd_count_div").css("display","block");
		title = "检察院文化程度人数统计";
		name = "检察院-文化程度人数";
		for (var i = 0; i < res.length-1; i++) {
//			console.log("--===="+res[i].dwbm);
			nameArray.push(res[i].dwjc+"-"+res[i].whcd);
			valueArray.push(res[i].whcdCount);
		}
	}
	
	var charData = "";
	if (nameArray.length>0) {
		charData = getEcharOfDa(title,name,minWidth,nameArray,valueArray);//柱状图显示的档案数据
	}else{
		charData = getEcharOfDa(title,name,minWidth,["暂无数据"],["暂无数据"]);//柱状图显示的档案数据
	}
	 
	zztChart.setOption(charData);
}

