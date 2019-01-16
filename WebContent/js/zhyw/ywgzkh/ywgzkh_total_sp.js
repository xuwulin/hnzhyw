/*********************************************业务工作考核total页面审批js**************************************************************************/

//读取url中的参数   (从下一页返回到这页的时候会用到参数,加载的是搜索的相关信息)
var dwbm;//单位编码
var year;//年份
var paramssp;//待办页面跳转过来的参数
var paramsSpReq = getRequest();
var sign ;//判断是否打开业务工作考核审批div的标示 
var bsprDwbm;//被审批人单位编码
var bsprDwmc;//被审批人单位名称
var bsprBmbm;//被审批人部门编码
var bsprBmmc;//被审批人部门名称
var cljs;//处理角色 
var spLx;//审批类型
var sprDwbm;//审批人单位编码
var sprDwmc;//审批人单位名称
var sprBmbm;//审批人部门编码 
var sprBmmc;//审批人部门名称
var sprGh;//审批人工号
var spzt;//审批状态
var spyj = "";//审批意见
var wbId;//外部id
var lcId;//流程id
var spId;//审批id
var ywkhId = "";//业务考核id
var ssr;//所属人 

//请求下面业务工作考核信息
$(function() {

	//获取上一个页面传递过来的参数
	if(typeof(paramsSpReq)!="undefined"){
		
		//2,发起考核人、指定考核人按钮显示,"yearSp" == sign表示从首页的待办业务跳转过来的，即：业务考核工作的审批页面
		var sign = paramsSpReq.sign;
		var spymBs = paramsSpReq.spym;//审批页面参数为2
		var spYwlxZxSpzt;//业务类型单条信息对应的审批表的最新审批状态
		
//		;
		//yearSp  代表业务工作年度审批的标示
		if("yearSp" == sign){
			
			$.ajax({
				type : "post",
				url : rootPath + "/service/sp/getSpById",
				data : {
					spid : spId
				},
				dataType : 'json',
				success : function(data) {
//					console.log(JSON.parse(JSON.stringify(data)).spzt);
//					console.info("---"+data);
//					console.info("---"+data.spzt);

					if(data){
						spYwlxZxSpzt = (data.spzt);
					}
//					console.info("当前审批信息对应的状态值："+spYwlxZxSpzt);
					
					if(data&&spYwlxZxSpzt&&"1"!=spYwlxZxSpzt){
						$("#button_agree").css("display", "none");
						$("#button_refuse").css("display", "none");
						$("#ywgzSpyjDiv").css("display", "none");
					}
				}
				
			});
			
			$(".logo_sz").css("display","none");
			$(".logo_bz").css("display","none");
			$("#sureKhr").css("display","none");//指定考核人按钮
			$("#fqkhsp").css("display","none");//发起考核审批
			$(".logo_bc").css("display","none");//计算评价得分
			$("#i_button").css("display","none");//确认按钮
			$("#ywgzYysq").css("display","none");//异议申请按钮
//			;
			//获取待办业务传递过来的参数
			if(paramsSpReq.ssrdwbm&&"undefined"!=paramsSpReq.ssrdwbm){
				dwbm = paramsSpReq.ssrdwbm;
			}else{
				dwbm = paramsSpReq.bspdwbm;
			}
			year = paramsSpReq.nf;
			
			$.ajax({
				type : "post",
				url : rootPath + "/service/ywlxkh/getkhzl",
				async : false,
				data : {
					dwbm : dwbm,
					year : year,
					sfby : 'Y'  //是否本院
				},
				dataType : 'json',
				success : function(data) {
					for (var i = 0; i < data.length; i++) {
						paramssp = JSON.stringify(data[i]);
					}
//					;
					$("#text").load("ywgzkh_tree.html", function() {
						if(paramssp){
							paramsTree = JSON.parse(paramssp);
							paramsTree.spym=spymBs;
							paramsTree.spSign=sign;
						}
					});
					$("#right_com").load("ywgzkh_zl.html", function() {
						if(paramssp){
							paramsZl = JSON.parse(paramssp);
							paramsZl.spym=spymBs;
							paramsZl.spSign=sign;
						}
					});
				}
			});		
			
		} else {
			if (paramsSpReq) {
				//首页的点击查询按钮和默认加载的方式 请求URL不一样，请求参数也各不相同 
				var totalPageReqUrl;
				if (paramsSpReq.sfby) {
					totalPageReqUrl = rootPath + "/service/ywlxkh/getkhzl";
				}else{
					totalPageReqUrl = rootPath + "/service/ywlxkh/selectDwkh";
				}
				//取行号
				$.ajax({
					type : "post",
					url : totalPageReqUrl,
					async : false,
					data : paramsSpReq,
					dataType : 'json',
					success : function(resData) {
						if (resData) {
							$("#text").load("ywgzkh_tree.html",function(){
								paramsTree = resData[paramsSpReq.line];
							});
							
							$("#right_com").load("ywgzkh_zl.html",function(){
								paramsZl = resData[paramsSpReq.line];
							});
						}
					}
				});
			}
			
//			$("#text").load("ywgzkh_tree.html",function(){
//				paramsTree=JSON.parse(paramsSpReq.data);
//			});
//	
//			$("#right_com").load("ywgzkh_zl.html",function(){
//				paramsZl = JSON.parse(paramsSpReq.data);
//			});
		} 
	}
});

parent.iFrameHeight();

//;

if(typeof(paramsSpReq)!="undefined"){
	
	sign = (typeof(paramsSpReq.sign)=="undefined")?"":paramsSpReq.sign;
	// 关闭指定考核人按钮、发起考核审批按钮、异议申请按钮,开启审批列表
	if ("yearSp" == sign) {
		$("#judicialfilesmanage").css("display", "block");
		$(".top_logo").css("display","none");
	}
	
	//被审批人信息
	bsprDwbm = paramsSpReq.bspdwbm;
	bsprDwmc = paramsSpReq.bspdwmc;
	bsprBmbm = paramsSpReq.bspbmbm;
	bsprBmmc = paramsSpReq.bspbmmc;
	cljs = paramsSpReq.cljs;
	spLx = paramsSpReq.splx;
	spzt = paramsSpReq.spzt;
	spyj = paramsSpReq.spyj;
	sprDwmc = paramsSpReq.sprdwmc;
	sprBmmc = paramsSpReq.sprbmmc;
	spzt = paramsSpReq.spzt;
	wbId = paramsSpReq.wbid;
	lcId = paramsSpReq.lcid;
	spId = paramsSpReq.id;
	ssr = paramsSpReq.ssr;
	
//	;
	//审批人信息
	if (top.currentUserInfo) {
		sprDwbm = top.currentUserInfo.dwbm;
		sprBmbm = top.currentUserInfo.bmbm;
		sprGh = top.currentUserInfo.gh;
//		debugger;
		if ("1"==spzt) {
			$("#button_read").css("display", "none");
			$("#button_recall").css("display", "none");
		}
		//按钮显示的控制   这儿注意要用工号来判断
		/*if(sprBmbm == bsprBmbm && ssr==top.currentUserInfo.mc){
			$("#button_agree").css("display", "none");
			$("#button_recall").css("display", "none");
			$("#button_refuse").css("display", "none");
		}else{
			$("#button_read").css("display", "none");
			$("#button_recall").css("display", "none");
		}*/
		
	}
//	console.info(paramsSpReq);
	
	
$.ajax({
	url : rootPath + "/service/sfdasp/showsplc",
	type : 'post',
	async : false,
	data : {
		'wbid' : wbId,
		'splx' : spLx,
		'lcid' : lcId
	},
	dataType : 'json',
	success : function(data) {
//		console.info(data);
		for(var i = data.length-1;i >= 0; i--){
			addRow(data[i]);
		}
	}
});
	

//给列表赋值
//为审批Table下的tr、td赋值
function addRow(object){
	
	if (typeof (object.spyj) == "undefined") {
		spyj = "";
	} else {
		spyj = object.spyj;
	}
	
	$("#ywgzSpTr").after("<tr><td></td>"+
			"<td>"+object.spdwmc+"</td>"+
			"<td>"+object.spbmmc+"</td>"+
			"<td>"+object.sprmc+"</td>"+
			"<td>"+spyj+"</td>"+
			"<td>"+object.spsj+"</td>"+
			"<td>"+object.spzt+"</td>"+
			"<input type='hidden' id='ywgzSprdwbm' value='"+object.spdw+"'>"+
			"<input type='hidden' id='ywgzSprbmbm' value='"+object.spbm+"'>"+
			"<input type='hidden' id='ywgzSprgh' value='"+object.spr+"'>"+
	"</tr>");
}


//点击审批确认按钮
function ywgzAgree(){
/*console.info(JSON.parse(paramsSpReq.data));
return ;*/
//	;
	//审批意见
	var ywgzSpjy = $("#ywgzSpjy").val();
	if(ywgzSpjy==""){
		top.msgAlertInfo("请填写审批意见！");
		return;
	}
	
	top.msgProgressTip("正在处理……");
	
	//如果审批类型为11表示异议审批
	if("11"==spLx){
		//异议申请审批开始
		$.ajax({
			url : rootPath + "/service/ywgzkh/yyAuditStart",
			type : 'post',
			async : false,
			data : {
				'wbid' : wbId,
				'splx' : spLx,
				'lcid' : lcId,
				'spyj' : ywgzSpjy,
				'spjl' : 'Y',
				'spid' : spId,
				'ywkhId' : ywkhId
			},
			dataType : 'json',
			success : function(data) {
//				;
			
				//审批成功后获取审批意见、审批结果并更改页面的值
				top.msgProgressTipClose();
				top.msgAlertInfo(data.msg);
				
				//审批类型为13时 不计算考评得分
				if(paramsSpReq.ywlx!='undefined'&&"13"!=paramsSpReq.ywlx){
					//计算评价得分
					countPjdf(paramsSpReq);
				}
				
				
				window.location.reload();
				$("#i_button").css("display","none");
				$("#i_button_rjbas").css("display","none");
			}
		});
		
	}else if("10"==spLx){
		//异议申请成功后的异议发起审批
		$.ajax({
			url : rootPath + "/service/ywgzkh/yyFqAuditLc",
			type : 'post',
			async : false,
			data : {
				'wbid' : wbId,
				'splx' : spLx,
				'lcid' : lcId,
				'spyj' : ywgzSpjy,
				'spjl' : 'Y',
				'spid' : spId
			},
			dataType : 'json',
			success : function(data) {
//				;
			
				//审批成功后获取审批意见、审批结果并更改页面的值
				top.msgProgressTipClose();
				top.msgAlertInfo(data.msg);
				
				//审批类型为13时 不计算考评得分
				if(paramsSpReq.ywlx!='undefined'&&"13"!=paramsSpReq.ywlx){
					//计算评价得分
					countPjdf(paramsSpReq);
				}
				
				window.location.reload();
				$("#i_button").css("display","none");
				$("#i_button_rjbas").css("display","none");
			}
		});
		
	}else{
		
		//年度考核审批开始
		$.ajax({
			url : rootPath + "/service/ywgzkh/auditStart",
			type : 'post',
			async : false,
			data : {
				'wbid' : wbId,
				'splx' : spLx,
				'lcid' : lcId,
				'spyj' : ywgzSpjy,
				'spjl' : 'Y',
				'spid' : spId,
				'ywkhId' : ywkhId
			},
			dataType : 'json',
			success : function(data) {
//				;
			
				//审批成功后获取审批意见、审批结果并更改页面的值
				top.msgProgressTipClose();
				top.msgAlertInfo(data.msg);
				
				//审批类型为13时 不计算考评得分
				if(paramsSpReq.ywlx!='undefined'&&"13"!=paramsSpReq.ywlx){
					//计算评价得分
					countPjdf(paramsSpReq);
				}
				
				window.location.reload();
				$("#i_button").css("display","none");
				$("#i_button_rjbas").css("display","none");
			}
		});
		
	}
	
	
	
}


//退回按钮触发
function ywgzRefuse(){
	
	//审批意见
	var ywgzSpjy = $("#ywgzSpjy").val();
	if(ywgzSpjy==""){
		top.msgAlertInfo("请填写退回审批意见！");
		return;
	}
	
	top.msgProgressTip("正在处理……");
	
	$.ajax({
		url : rootPath + "/service/ywgzkh/auditStart",
		type : 'post',
		async : false,
		data : {
			'wbid' : wbId,
			'splx' : spLx,
			'lcid' : lcId,
			'spyj' : ywgzSpjy,
			'spjl' : 'N',
			'spid' : spId,
			'ywkhId' : ywkhId
		},
		dataType : 'json',
		success : function(data) {
			
			//计算评价得分
//			countPjdf(JSON.parse(paramsSpReq.data));
			
			
			//审批成功后获取审批意见、审批结果并更改页面的值
			top.msgProgressTipClose();
			top.msgAlertInfo(data.msg);
			
			window.location.reload();
			$("#i_button").css("display","none");
			$("#i_button_rjbas").css("display","none");
		}
	});
}


}

//JS判断空值 未定义的通用函数
function isNull(obj){
	return obj==null||obj==undefined||obj=="undefined";
}


//计算评价得分
// 先判断评价得分是否存在，不存在则进行计算 
//
//
function countPjdf(ywgzData){
//	console.info(ywgzData);
	//ywgzData.bspdwbm=zbxDwbm  ywgzData.nf=zbxYear  ywgzData.ywlx=zbxKhlx
	jspjdf(ywgzData.bspdwbm,ywgzData.nf,ywgzData.ywlx);
}

///**
// * 计算评价得分:根据单位编码、年份、考核类型去计算该类型的评价得分
// * zbxDwbm:选中的指标项dwbm
// * zbxYear:选中的年份
//* zbxKhlx:选中的考核类型
// *
//*/
function jspjdf(zbxDwbm, zbxYear, zbxKhlx) {
//	alert(zbxDwbm+"---"+zbxYear+"===="+zbxKhlx);
//	return;
	
    $.ajax({
        url: rootPath + '/service/ywlxkh/updatezjcftoinsertpjf',
        type: 'post',
        async: false,
        data: {
            dwbm: zbxDwbm, //'460001',//paramsZcjd.dwbm
            year: zbxYear, //'2017',//paramsZcjd.year
            khlx: zbxKhlx //'12' //paramsZcjd.khlx
        },
        dataType: 'json',
        success: function (data) {
//            if (data == "0") {
//                top.msgAlertInfo("计算失败!");
//            } else {
//                top.msgAlertInfo("计算成功!");
//                onclicktree(paramsZcjd.khlx);
//            }
        }
    })
}


//当业务工作审批不同意时 发起人将审批表状态设为已读"5"
//考虑 是9 10 11 被不用意的状态设为已读
function ywgzRead(){
	alert("审批表状态应该为：5");
}



