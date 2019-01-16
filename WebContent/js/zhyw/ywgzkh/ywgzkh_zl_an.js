/*********************************************业务工作考核总览按钮js**************************************************************************/

//考核活动id
var yykhId;
var dwkhzt;//单位考核状态
var lxkhzt;//类型考核状态
var dwKhStatus;//单位考核状态参数
var lxKhStatus;//类型考核状态参数

$(function () {
	
	
//	console.info("paramsZl:" + paramsZl);
	
	if(paramsZl){
		
		
		if("yearSp" == paramsZl.spSign){
			$("#openYwgzkh").css("display", "none");//公布按钮
			$("#ywgzYysq").css("display","none");//异议申请按钮
		}
		
		//1,从业务工作考核总览界面跳转过来
		var $title = $("#ywgzkhZlTitle");//给标题赋值
		$title.text(paramsZl[0].dwmc+" "+new Date(paramsZl[0].startDate).format("yyyy-MM-dd")+"至"+new Date(paramsZl[0].endDate).format("yyyy-MM-dd")+"业务工作考核");

		//给Ul下的li标签赋值
		var liArrayLength = paramsZl.khsj.length;//li标签个数
		ywlxArray = paramsZl.khsj;//li标签的数组
		yykhId = paramsZl.khid;
		
		var spym = (isNull(paramsZl.spym) ? "":paramsZl.spym );//是否审批页面
		
		//根据登录人信息以及状态值判断是否显示按钮
		isOrNotShowButton(paramsZl);
		
//		debugger;
		
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
		showFzDivByDwjbAndBmbm(liArrayLength,ywlxArray,paramsZl,khlxJsonData);
		
	}
	
	
});

//查询当前登录人是否为省院的案件管理处（案管部门）\\\\\\\\\\\省院的单位编码=460000 、单位级别为2  且 ryjs=7时表示省院的案件管理处
function isOrNotAjgl(dwKhStatus,lxKhStatus){
//	console.info(paramsZl);
//	debugger;
	
	var dwjb = top.currentUserInfo.dwjb;
	var anjs = top.currentUserInfo.ryjs.toString().match("7"); //案管角色
//	alert(ryjs+"========="+ryjs.toString().match("7"));
	//如果不是省院案管并且所有指标项的考核都完成时 显示公布按钮
	if (dwKhStatus) {
		if(dwjb == '2'){
			if("7"==dwKhStatus&&"7"==lxKhStatus&&"7" == anjs){
				$("#ywgzYysq").css("display","none");//异议申请按钮
			}else{
				$("#ywgzYysq").css("display","none");//异议申请按钮
				$("#openYwgzkh").css("display","none");//公布按钮
			}
		}else{
			if("7"==dwKhStatus&&"7"==lxKhStatus){
				
				if("7" == anjs){
	//				alert("所有类型已经通过且我是市院或者区院案管!");
					$("#openYwgzkh").css("display","none");//公布按钮
				}else{
					$("#ywgzYysq").css("display","none");//异议申请按钮
					$("#openYwgzkh").css("display","none");//公布按钮
				}
			}else{
				
				if ("9"==dwKhStatus) {
					//省院公布单位的年度业务考核以后 5天之内异议按钮可出现
					//查询当前考核单位的活动表状态、类型考核表状态，取出期限、修改时间,最终期限时间=修改时间+期限，获取当前时间与之对比确定是否出现异议申请按钮
					if (paramsZl&&paramsZl.gbsj&&paramsZl.yysjqx) {
						var sjqx = parseInt(paramsZl.yysjqx);//时间期限
						var gbsj = paramsZl.gbsj;//公布时间
						var tempSjqx = new Date(gbsj).format("yyyy-MM-dd");//格式化公布时间
	
						//当前时间
						var nowDate = new Date().getFullYear()+'-'+ (new Date().getMonth()+1)+"-"+new Date().getDate();
						var tempDays = dateDiff(nowDate,tempSjqx);
	//					debugger;
						//"当前日期"与公布时间作对比
						if ((tempDays>0&&tempDays<=sjqx)||tempDays==0) {
							var ztTempArr = [];
							ztTempArr.push(paramsZl.khsj[0].dwkhzt);
							for (var i = 0; i < paramsZl.khsj.length; i++) {
								ztTempArr.push(paramsZl.khsj[i].lxkhzt);
							}
							var ztTempString = "9,9,9,9,9,9,9,9,9,9,9,9,9,9";
	//						console.info(ztTempArr.toString()+"-----"+ztTempString==(ztTempArr.toString()));
							if (ztTempString==(ztTempArr.toString())) {
								$("#openYwgzkh").css("display","none");//公布按钮
							}
						}else{
							$("#ywgzYysq").css("display","none");//异议申请按钮   ////////////////
							$("#openYwgzkh").css("display","none");//公布按钮
						}
					}else{
						$("#ywgzYysq").css("display","none");//异议申请按钮   ////////////////
						$("#openYwgzkh").css("display","none");//公布按钮
					}
				}else{
					$("#ywgzYysq").css("display","none");//异议申请按钮   ////////////////
					$("#openYwgzkh").css("display","none");//公布按钮
				}
				
			}
		}
	}
}

function isNull(obj){
	return obj==null||obj==undefined||obj=="undefined";
}


////////////////////////////////////////////////////////////////////////////////异议流程 start\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

if(top.currentUserInfo){
	
	var sprDwbm = top.currentUserInfo.dwbm;//异议审批人单位编码
	
	//点击异议确认按钮触发事件
	var yy_lcmb = [// 档案流程模板数组
		{
			id : "0930",
			jdlx : "930",
			lcjd : "93000",
			xyjd : "931",
			xycljs : "SP",
			xyclbm : "",
			xycldw : "",
			jdzt : "0",
			sm : "异议审批开始",
			xycldwlx : "2",
			lclx : "11",
			ztbg : "1"
		} ];


	//异议div确定按钮
	function ywkhYySure(){
		
//		debugger;

		var selectNode = $.fn.zTree.getZTreeObj("treeYyYwgzkhDemo").getSelectedNodes();
		var sprBmbm = top.currentUserInfo.bmbm;
		var sprGh = selectNode[0].id;
//		return ;
		
		top.msgProgressTip("正在处理……");
		
		$.ajax({
			url : rootPath + '/service/ywgzkh/yyAudit',
			type : 'post',
			async : false,
			data : {
				jdlx : yy_lcmb[0].jdlx,
				lcjd : yy_lcmb[0].lcjd,
				lclx : yy_lcmb[0].lclx,
				sprdwbm : sprDwbm,
				sprbmbm : sprBmbm,
				sprgh : sprGh,
				spstid : yykhId,//异议考核id==业务活动考核id
				spyj : "",
				clnr : "",
				yyzt : "1",
				yysm : $("#yysqAgSm").val() //异议说明
			},
			dataType : 'json',
			success : function(data) {
				
				top.msgProgressTipClose();
				
				$(".yysq_bounced").css("display","none");
				
				$("#ywgzYysq").css("display","none");
				top.msgAlertInfo("操作成功！");
			}
		});
		
		
	}
}


//////////////////////////////////////////////////////////////////////////////异议流程 end\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\




////////////////////////////////////////////////////////////////////////////////异议指定考核人Ztree start\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//异议考核人的Ztree
var isYyYwgzkhShowMenu = false;
var yyYwgzkhztreedata = {
	gh : "",
	name : ""
}
var yyYwgzkhsetting = {
	data : {
		key : {
			name : "name"
		},
		simpleData: {
			enable: true
		}
	},
	view : {
		showIcon : false,
		dblClickExpand : false,
		selectedMulti : false
	},
	callback : {
		beforeClick : beforeYyYwgzkhClick,
		onClick : onYyYwgzkhClick
	}
};

function beforeYyYwgzkhClick(treeId, treeNode) {
	var check = (treeNode && "ry"==treeNode.type);
	if (check)
		hideYyYwgzkhMenu();
	return check;
}

function onYyYwgzkhClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeYyYwgzkhDemo"), nodes = zTree.getSelectedNodes();
	var v = "";
	var name = nodes[0].name;
	var gh = nodes[0].id;
	yyYwgzkhztreedata.name = name;
	yyYwgzkhztreedata.gh = gh;
	v = name;
	var xzYyYwkhSprObj = $("#xzYyYwkhSpr");
	xzYyYwkhSprObj.attr("value", v);
}

function showYyYwgzkhMenu(){
	if (isYyYwgzkhShowMenu == false) {
		isYyYwgzkhShowMenu = true;
		var xzYyYwkhSprObj = $("#xzYyYwkhSpr");
		var xzYyYwkhSprOffset = $("#xzYyYwkhSpr").offset();
		$("#menuYyYwgzkhContent").fadeIn("fast");

		$("body").bind("mousedown", onYyYwgzkhBodyDown);
	} else{
		hideYyYwgzkhMenu();
	}
} 

function hideYyYwgzkhMenu() {
	isYyYwgzkhShowMenu = false;
	$("#menuYyYwgzkhContent").fadeOut("fast");
	$("body").unbind("mousedown", onYyYwgzkhBodyDown);
}
function onYyYwgzkhBodyDown(event) {
	if (!(event.target.id == "menuYyYwgzkhBtn" || event.target.id == "menuYyYwgzkhContent" ||
			$(event.target).parents("#menuYyYwgzkhContent").length > 0)) {
		hideYyYwgzkhMenu();
	}
} 

////////////////////////////////////////////////////////////////////////////////异议考核人Ztree end\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

//公布点击事件
function updateYwgzkhGbZt(){
	//或取业务考核活动表id
	if(yykhId&&yykhId!="undefined"){
//		debugger;
		top.msgProgressTip("正在处理……");
		$.ajax({
			url : rootPath + '/service/ywlxkh/updateGbZtById',
			type : 'post',
			async : false,
			data : {
				ywkhid : yykhId
			},
			dataType : 'json',
			success : function(data) {
				
				top.msgProgressTipClose();
				top.msgAlertInfo(data.msg);
				$("#openYwgzkh").css("display","none");
			}
		});
	}
}



function isOrNotShowButton(data){
	if(top.currentUserInfo){
		
		var anjs = top.currentUserInfo.ryjs.toString().match("7"); //案管角色
//		debugger;
		if("0"!=data.yyzt){
			$("#ywgzYysq").css("display","none");//异议申请按钮
			
			if("yearSp" == data.spSign){
				$("#ywgzYysq").css("display","none");//异议申请按钮
			}else{
				if("7"==anjs&&"2"==data.yyzt){
					if("3"==data.khsj[0].dwkhzt||"5"==data.khsj[0].dwkhzt){
						$("#ywgzYyfqsp").css("display","block");//异议发起审批按钮
					}
				}
			}
			
		}
	}
	
}


//异议发起审批的点击事件
function ywkhYyfqSpSure(){
	if(paramsZl){
		
		//点击异议发起审批确认按钮触发事件
		var yyfqsp_lcmb = [// 档案流程模板数组
			{
				id : "0950",
				jdlx : "950",
				lcjd : "95000",
				xyjd : "951",
				xycljs : "SP",
				xyclbm : "",
				xycldw : "",
				jdzt : "0",
				sm : "异议修改后审批开始",
				xycldwlx : "1",
				lclx : "10",
				ztbg : "5"
			} ];
		
		top.msgProgressTip("正在处理……");
		$.ajax({
			url : rootPath + '/service/ywgzkh/yyFqAuditStart',
			type : 'post',
			async : false,
			data : {
				jdlx : yyfqsp_lcmb[0].jdlx,
				lcjd : yyfqsp_lcmb[0].lcjd,
				lclx : yyfqsp_lcmb[0].lclx,
				sprdwbm : sprDwbm,
				sprbmbm : sprBmbm,
				sprgh : sprGh,
				spyj : "",
				clnr : "",
				ywkhid : paramsZl.khid
			},
			dataType : 'json',
			success : function(data) {
				
				top.msgProgressTipClose();
				top.msgAlertInfo("操作成功！");
				$("#ywgzYyfqsp").css("display","none");
			}
		});
	}
	
}


//这儿分为省院人员、案管角色可查看全部信息，其他角色只能查看自己部门对应的业务类型
function showFzDivByDwjbAndBmbm(liArrayLength,ywlxArray,paramsZl,khlxJsonData){
	if (top.currentUserInfo) {
		var anjs = top.currentUserInfo.ryjs.toString().match("7"); //案管角色
		if("2"==top.currentUserInfo.dwjb||"7"==anjs){
			for (var i = 0; i < liArrayLength; i++) {
				
				//点击标题的传递参数：类型编号、标题名、业务类型考核id
				var lxbh_str="'"+ywlxArray[i].khlx+"'";//考核类型编号
				var lxmc_str= ",'"+ywlxArray[i].khmc+"'";//考核名称
				var ywlxkhid_str=",'"+ywlxArray[i].ywlxkhid+"'";//业务类型考核id
				dwkhzt = ",'"+(isNull(ywlxArray[i].dwkhzt) ? "":ywlxArray[i].dwkhzt )+"'";//单位考核状态
				lxkhzt = ",'"+(isNull(ywlxArray[i].lxkhzt) ? "":ywlxArray[i].lxkhzt )+"'";//类型考核状态
				var khrbm = ",'"+(isNull(ywlxArray[i].khrbm) ? "":ywlxArray[i].khrbm )+"'";//考核人部门
				var khrgh = ",'"+(isNull(ywlxArray[i].khrgh) ? "":ywlxArray[i].khrgh )+"'";//考核人工号
				var khrdw = ",'"+(isNull(ywlxArray[i].khrdw) ? "":ywlxArray[i].khrdw )+"'";//考核人单位编码
				var khfzid = ",'"+(isNull(ywlxArray[i].khfzid) ? "":ywlxArray[i].khfzid )+"'";//考核分值id
				var spym = ",'"+(isNull(paramsZl.spym) ? "":paramsZl.spym )+"'";//审批页面
				var spSign = ",'"+(isNull(paramsZl.spSign) ? "":paramsZl.spSign )+"'";
				var sprdwbm = ",'"+(isNull(paramsTreeReq.sprdwbm) ? "":paramsTreeReq.sprdwbm )+"'";//审批人单位编码 paramsTreeReq
				var sprgh = ",'"+(isNull(paramsTreeReq.sprgh) ? "":paramsTreeReq.sprgh )+"'";//审批人工号
				var ywlx = ",'"+(isNull(paramsTreeReq.ywlx) ? "":paramsTreeReq.ywlx )+"'";//业务类型
				var param=lxbh_str+lxmc_str+ywlxkhid_str+dwkhzt+lxkhzt+khrbm+khrgh+khrdw+khfzid+spym+spSign+sprdwbm+sprgh+ywlx;
				dwKhStatus = (isNull(ywlxArray[i].dwkhzt) ? "":ywlxArray[i].dwkhzt );
				lxKhStatus = (isNull(ywlxArray[i].lxkhzt) ? "":ywlxArray[i].lxkhzt );
				var mainId;
				if(ywlxArray[i].khlx<10){
					mainId = parseInt(ywlxArray[i].khlx);
				}else{
					mainId = (ywlxArray[i].khlx);
				}
				
				if(ywlxArray[i].khf&&"0"!=ywlxArray[i].khf){
					//赋值Li标签
					var $Li = "<li>"
							+"<div class='con_top1'>"
								+"<div class='con_jc1' style='cursor: pointer;' onclick=zcjdgz_page("+param+")>"+ywlxArray[i].khmc+"</div>"
								+"<button id='pjf"+(ywlxArray[i].khlx)+"' class='con_jcf1' >"+float2Zero(((ywlxArray[i].khf)==""?0:(ywlxArray[i].khf))).toFixed(2)+"分</button>"
							+"</div>"
							+"<div style='height: 500px; width: 100%;margin: auto;'>"
								+"<div id='main"+mainId+"' style='height: 100%; width: 100%;margin: auto;'></div>"
							+"</div>"
							+"</li>";
					$(".con_ul").append($Li);
				}
				
				//判断当前登录人指定考核的指标项
//		        clickKhlxLiByKhlx(paramsZl);//debugger;
				
			}
		}else{
			for (var j = 0; j < khlxJsonData.length; j++) {
				for (var i = 0; i < liArrayLength; i++) {
					
					//点击标题的传递参数：类型编号、标题名、业务类型考核id
					var lxbh_str="'"+ywlxArray[i].khlx+"'";//考核类型编号
					var lxmc_str=",'"+ywlxArray[i].khmc+"'";//考核名称
					var ywlxkhid_str=",'"+ywlxArray[i].ywlxkhid+"'";//业务类型考核id
					dwkhzt = ",'"+(isNull(ywlxArray[i].dwkhzt) ? "":ywlxArray[i].dwkhzt )+"'";//单位考核状态
					lxkhzt = ",'"+(isNull(ywlxArray[i].lxkhzt) ? "":ywlxArray[i].lxkhzt )+"'";//类型考核状态
					var khrbm = ",'"+(isNull(ywlxArray[i].khrbm) ? "":ywlxArray[i].khrbm )+"'";//考核人部门
					var khrgh = ",'"+(isNull(ywlxArray[i].khrgh) ? "":ywlxArray[i].khrgh )+"'";//考核人工号
					var khrdw = ",'"+(isNull(ywlxArray[i].khrdw) ? "":ywlxArray[i].khrdw )+"'";//考核人单位编码
					var khfzid = ",'"+(isNull(ywlxArray[i].khfzid) ? "":ywlxArray[i].khfzid )+"'";//考核分值id
					var spym = ",'"+(isNull(paramsZl.spym) ? "":paramsZl.spym )+"'";//审批页面
					var spSign = ",'"+(isNull(paramsZl.spSign) ? "":paramsZl.spSign )+"'";
					var sprdwbm = ",'"+(isNull(paramsTreeReq.sprdwbm) ? "":paramsTreeReq.sprdwbm )+"'";//审批人单位编码 paramsTreeReq
					var sprgh = ",'"+(isNull(paramsTreeReq.sprgh) ? "":paramsTreeReq.sprgh )+"'";//审批人工号
					var ywlx = ",'"+(isNull(paramsTreeReq.ywlx) ? "":paramsTreeReq.ywlx )+"'";//业务类型
					var param=lxbh_str+lxmc_str+ywlxkhid_str+dwkhzt+lxkhzt+khrbm+khrgh+khrdw+khfzid+spym+spSign+sprdwbm+sprgh+ywlx;
					dwKhStatus = (isNull(ywlxArray[i].dwkhzt) ? "":ywlxArray[i].dwkhzt );
					lxKhStatus = (isNull(ywlxArray[i].lxkhzt) ? "":ywlxArray[i].lxkhzt );
					var mainId;
					if(ywlxArray[i].khlx<10){
						mainId = parseInt(ywlxArray[i].khlx);
					}else{
						mainId = (ywlxArray[i].khlx);
					}
					var khlx = (isNull(ywlxArray[i].khlx) ? "":ywlxArray[i].khlx);
					
//					debugger;
					if(ywlxArray[i].khf&&"0"!=ywlxArray[i].khf){
						if(khlxJsonData[j].KHLX==khlx){
							//赋值Li标签
							var $Li = "<li>"
									+"<div class='con_top1'>"
										+"<div class='con_jc1' style='cursor: pointer;' onclick=zcjdgz_page("+param+")>"+ywlxArray[i].khmc+"</div>"
										+"<button id='pjf"+(ywlxArray[i].khlx)+"' class='con_jcf1' >"+float2Zero(((ywlxArray[i].khf)==""?0:(ywlxArray[i].khf))).toFixed(2)+"分</button>"
									+"</div>"
									+"<div style='height: 500px; width: 100%;margin: auto;'>"
										+"<div id='main"+mainId+"' style='height: 100%; width: 100%;margin: auto;'></div>"
									+"</div>"
									+"</li>";
							$(".con_ul").append($Li);
						}
					}
					
					//判断当前登录人指定考核的指标项
//			        clickKhlxLiByKhlx(paramsZl);//debugger;
				}
			}
		}
		
		//是否是省院？？？是否为案管？？？是否为部门领导？？
		isOrNotAjgl(dwKhStatus,lxKhStatus);
	}
	
}

//转化为日期格式
Date.prototype.format = function(fmt){
	var o = { 
	        "M+" : this.getMonth()+1,                 //月份 
	        "d+" : this.getDate(),                    //日 
	        "h+" : this.getHours(),                   //小时 
	        "m+" : this.getMinutes(),                 //分 
	        "s+" : this.getSeconds(),                 //秒 
	        "q+" : Math.floor((this.getMonth()+3)/3), //季度 
	        "S"  : this.getMilliseconds()             //毫秒 
	    }; 
	
	if(/(y+)/.test(fmt)) {
	    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	}
	
	for(var k in o) {
	   if(new RegExp("("+ k +")").test(fmt)){
	     fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
	    }
	}
	return fmt; 
};

//两个日期的差值(d1 - d2).
function dateDiff(d1,d2){
    var day = 24 * 60 * 60 *1000;
	try{    
	   var dateArr = d1.split("-");
	   var checkDate = new Date();
	   checkDate.setFullYear(dateArr[0], dateArr[1]-1, dateArr[2]);
	   var checkTime = checkDate.getTime();
	  
	   var dateArr2 = d2.split("-");
	   var checkDate2 = new Date();
	   checkDate2.setFullYear(dateArr2[0], dateArr2[1]-1, dateArr2[2]);
	   var checkTime2 = checkDate2.getTime();
	    
	   var cha = (checkTime - checkTime2)/day;  
	        return cha;
	    }catch(e){
	   return false;
	}
}

