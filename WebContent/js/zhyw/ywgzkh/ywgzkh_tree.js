/*********************************************业务工作考核total页面左边的菜单树js**************************************************************************/


//上一页面传递过来的参数
var ywlxArray;//li标签的数组
var paramsTreeReq = getRequest();
//var paramsTree;
$(function() {
	
	if(paramsTreeReq && "yearSp" == paramsTreeReq.spSign && paramsTreeReq.data) {
		paramsTree = JSON.parse(paramsTreeReq.data);
	} 
	
	if (paramsTree){
		//给Ul下的li标签赋值
		var liArrayLength = paramsTree.khsj.length;//li标签个数
		ywlxArray = paramsTree.khsj;//li标签的数组
		var khlx;//考核类型
		var spywlx;//业务类型
		var param;
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
//		debugger;
		//根据当前登录人的单位级别、单位编码、部门编码显示不同的树
		//如果是省院以及当前院案管可以看全部
		showLeftTreeEchartByDwjbAndBmbm(liArrayLength,ywlxArray,paramsTree,paramsTreeReq,khlxJsonData);
		
//		zl_page();
		//控制li标签的选中状态
		$(".con_tree>ul>li").click(function(){ 
			$(".li_hove").removeClass("li_hove");
			$(this).addClass("li_hove");
		 });
	}
});


//点中总览li标签跳转页面
function zl_page() {
	$("#right_com").load("ywgzkh_zl.html", function() {
		paramsZl = paramsTree;
		/*{
			dwbm : paramsTree.dwbm,
			year : paramsTree.year
		}*/
	});
};

//点中li标签跳转页面
function zcjdgz_page(lxbh,mc,ywlxkhid,dwkhzt,lxkhzt,khrbm,khrgh,khrdw,khfzid,spym,spSign,sprdwbm,sprgh,ywlx) { 
	
	$(".li_hove").removeClass("li_hove");
	$("#"+khfzid+"").addClass("li_hove");
	
	$("#right_com").load("ywgzkh_zcjdgz.html", function() {
		paramsZcjd = {
			dwbm : (paramsTree.dwbm),
			khlx : lxbh,
			ywlxkhid : ywlxkhid,
			dwkhzt : dwkhzt,
			lxkhzt : lxkhzt,
			khrbm : khrbm,
			khrgh : khrgh,
			khrdw : khrdw,
			khfzid : khfzid,
			spym : spym,
			spSign : spSign,
			sprdwbm : sprdwbm,
			sprgh : sprgh,
			ywlx : ywlx,
			yyzt : paramsTree.yyzt,
			year : paramsTree.year,
			title : mc
		};

		if (paramsTreeReq) {
			var sign = paramsTreeReq.sign;
			if ("yearSp" == sign) {
				paramsTreeReq.spym = '2';
				$(".logo_sp").css("display", "none");
				$(".logo_bc").css("display", "none");
			}
		}
	});
	//location.href="ywgzkh_zcjdgz.html";
}


function isNull(obj){
	return obj==null||obj==undefined||obj=="undefined";
}


function showLeftTreeEchartByDwjbAndBmbm(liArrayLength,ywlxArray,paramsTree,paramsTreeReq,khlxJsonData){
	
	if (top.currentUserInfo) {
		var anjs = top.currentUserInfo.ryjs.toString().match("7"); //案管角色
		spywlx = (isNull(paramsTreeReq.ywlx) ? "":paramsTreeReq.ywlx );//审批的业务类型
		var dwjb = ",'"+(isNull(paramsTreeReq.dwjb) ? "":paramsTreeReq.dwjb )+"'";;
		if("2"==top.currentUserInfo.dwjb||"7"==anjs){
			for (var i = 0; i < liArrayLength; i++) {
				
				//在最后一个li标签前加入li标签 
				var lxbh_str="'"+ywlxArray[i].khlx+"'";//考核类型编号
				var lxmc_str=",'"+ywlxArray[i].khmc+"'";//考核名称
				khlx = ywlxArray[i].khlx;
				var ywlxkhid_str=",'"+ywlxArray[i].ywlxkhid+"'";//业务类型考核id
				var dwkhzt = ",'"+(isNull(ywlxArray[i].dwkhzt) ? "":ywlxArray[i].dwkhzt )+"'";//单位考核状态
				var lxkhzt = ",'"+(isNull(ywlxArray[i].lxkhzt) ? "":ywlxArray[i].lxkhzt )+"'";//类型考核状态
				var khrbm = ",'"+(isNull(ywlxArray[i].khrbm) ? "":ywlxArray[i].khrbm )+"'";//考核人部门
				var khrgh = ",'"+(isNull(ywlxArray[i].khrgh) ? "":ywlxArray[i].khrgh )+"'";//考核人工号
				var khrdw = ",'"+(isNull(ywlxArray[i].khrdw) ? "":ywlxArray[i].khrdw )+"'";//考核人单位编码
				var ywkhfzid = ",'"+(isNull(ywlxArray[i].khfzid) ? "":ywlxArray[i].khfzid )+"'";//考核分值id
				var spym = ",'"+(isNull(paramsTree.spym) ? "":paramsTree.spym )+"'";//审批页面
				var spSign = ",'"+(isNull(paramsTree.spSign) ? "":paramsTree.spSign )+"'";
				var sprdwbm = ",'"+(isNull(paramsTreeReq.sprdwbm) ? "":paramsTreeReq.sprdwbm )+"'";//审批人单位编码 paramsTreeReq
				var sprgh = ",'"+(isNull(paramsTreeReq.sprgh) ? "":paramsTreeReq.sprgh )+"'";//审批人工号
				var ywlx = ",'"+(isNull(paramsTreeReq.ywlx) ? "":paramsTreeReq.ywlx )+"'";//业务类型
				param=lxbh_str+lxmc_str+ywlxkhid_str+dwkhzt+lxkhzt+khrbm+khrgh+khrdw+ywkhfzid+spym+spSign+sprdwbm+sprgh+ywlx+dwjb;

				$(".li_hove").after("<li id='"+(ywlxArray[i].khfzid)+"'  onclick=zcjdgz_page("+param+")>"+ywlxArray[i].khmc+"</li>");
				
//				debugger;		
				//审批页面当前审批类型选中且触发点击事件
				if("yearSp"==paramsTreeReq.sign){
					if(spywlx==khlx){
						$(".li_hove").removeClass("li_hove");
						$("#"+(isNull(ywlxArray[i].khfzid) ? "":ywlxArray[i].khfzid )+"").addClass("li_hove");
						$("#"+(isNull(ywlxArray[i].khfzid) ? "":ywlxArray[i].khfzid )+"").click();
					}
				}
				
			}
		}else{
			for (var j = 0; j < khlxJsonData.length; j++) {
//				console.info(khlxJsonData[j].KHLX+" 当前登录人的业务考核类型  data !");
				for (var i = 0; i < liArrayLength; i++) {
					
					//在最后一个li标签前加入li标签 
					var lxbh_str="'"+ywlxArray[i].khlx+"'";//考核类型编号
					var lxmc_str=",'"+ywlxArray[i].khmc+"'";//考核名称
					khlx = ywlxArray[i].khlx;
					var ywlxkhid_str=",'"+ywlxArray[i].ywlxkhid+"'";//业务类型考核id
					var dwkhzt = ",'"+(isNull(ywlxArray[i].dwkhzt) ? "":ywlxArray[i].dwkhzt )+"'";//单位考核状态
					var lxkhzt = ",'"+(isNull(ywlxArray[i].lxkhzt) ? "":ywlxArray[i].lxkhzt )+"'";//类型考核状态
					var khrbm = ",'"+(isNull(ywlxArray[i].khrbm) ? "":ywlxArray[i].khrbm )+"'";//考核人部门
					var khrgh = ",'"+(isNull(ywlxArray[i].khrgh) ? "":ywlxArray[i].khrgh )+"'";//考核人工号
					var khrdw = ",'"+(isNull(ywlxArray[i].khrdw) ? "":ywlxArray[i].khrdw )+"'";//考核人单位编码
					var ywkhfzid = ",'"+(isNull(ywlxArray[i].khfzid) ? "":ywlxArray[i].khfzid )+"'";//考核分值id
					var spym = ",'"+(isNull(paramsTree.spym) ? "":paramsTree.spym )+"'";//审批页面
					var spSign = ",'"+(isNull(paramsTree.spSign) ? "":paramsTree.spSign )+"'";
					var sprdwbm = ",'"+(isNull(paramsTreeReq.sprdwbm) ? "":paramsTreeReq.sprdwbm )+"'";//审批人单位编码 paramsTreeReq
					var sprgh = ",'"+(isNull(paramsTreeReq.sprgh) ? "":paramsTreeReq.sprgh )+"'";//审批人工号
					var ywlx = ",'"+(isNull(paramsTreeReq.ywlx) ? "":paramsTreeReq.ywlx )+"'";//业务类型
					param=lxbh_str+lxmc_str+ywlxkhid_str+dwkhzt+lxkhzt+khrbm+khrgh+khrdw+ywkhfzid+spym+spSign+sprdwbm+sprgh+ywlx+dwjb;
//					debugger;
					if(khlxJsonData[j].KHLX==khlx){
						$(".li_hove").after("<li id='"+(ywlxArray[i].khfzid)+"'  onclick=zcjdgz_page("+param+")>"+ywlxArray[i].khmc+"</li>");
						//审批页面当前审批类型选中且触发点击事件
						if("yearSp"==paramsTreeReq.sign){
							if(spywlx==khlx){
								$(".li_hove").removeClass("li_hove");
								$("#"+(isNull(ywlxArray[i].khfzid) ? "":ywlxArray[i].khfzid )+"").addClass("li_hove");
								$("#"+(isNull(ywlxArray[i].khfzid) ? "":ywlxArray[i].khfzid )+"").click();
							}
						}
					}
					
//					debugger;		
				}
			}
		}
	}
	
	
}



parent.iFrameHeight();

