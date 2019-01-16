/** *******************************************业务工作考核首页按钮js************************************************************************* */
$(function(){
	//区分省院的案管/部门领导
	if (top.currentUserInfo) {
		isOrNotAjgl(top.currentUserInfo.ryjs, top.currentUserInfo.dwjb);
	}
	
	//初始化首页日期显示格式
	setDateBoxOnlyMonth("ywgzqxStartDate");  //引用与common.js
	setDateBoxOnlyMonth("ywgzqxEndDate");  //引用与common.js
	
	$.post(rootPath + '/service/ywlxkh/getKhSj',{},function (result) {
        if(result){
            var startDate = result.split(",")[0];
            var endDate = result.split(",")[1];
            $("#ywgzqxStartDate").datebox("setValue",startDate);
            $("#ywgzqxEndDate").datebox("setValue",endDate);
        }
    });
	
	 getDwbmComboTree("ywgzCxDwbm",null,null); //引用于common.js
});


//开启汇总对院考核div
$("#ywgzkhhzdykhBt").click(function(){
	parent.ifeblick();
	$(".ywgzkhhzbounced").css("display","block");	
	
	//起始日期
	setDateBoxOnlyMonth("startDates");  //引用与common.js
	setDateBoxOnlyMonth("endDates");  //引用与common.js
	
    $.post(rootPath + '/service/ywlxkh/getKhSj',{},function (result) {
        if(result){
            var startDate = result.split(",")[0];
            var endDate = result.split(",")[1];
            $("#startDates").datebox("setValue",startDate);
            $("#endDates").datebox("setValue",endDate);
        }
    });
	 gethzkhdw();
	 gethzkhlx();
});

//获取考核的单位
function gethzkhdw() {
    var dwbm = "";
    if (top.currentUserInfo) {
        dwbm = top.currentUserInfo.dwbm;
        var dwjb = top.currentUserInfo.dwjb;
        if ("2" == dwjb) {
            $("#ywkhhzDwbm").combotree({
                width: '340px',
                method: 'post',
                checkbox: true,
                multiple: true,
                height: '40px',
                url: rootPath + '/service/ywkhkhdw/getKhdwTree?dwbm=' + dwbm,
            });                  
        }
    }
}


//业务考核汇总弹窗关闭
$("#qx").click(function(){
    parent.ifenone();
	$('#ywkhhzDwbm').combotree('clear');
	$('#ywkhhzkhlx').combotree('clear');
	$(".ywgzkhhzbounced").css("display","none");	
});

//业务考核汇总按钮取消
$("#hzkhqx").click(function(){
    parent.ifenone();
	$('#ywkhhzDwbm').combotree('clear');
	$('#ywkhhzkhlx').combotree('clear');
	$(".ywgzkhhzbounced").css("display","none");	
});

//确认汇总信息
$("#hzkhqr").click(function(){
	// debugger;
	//单位名称 
	 var dwmc=[];
	//单位编码
	 var dwbm=[];
	//考核类型
	 var khlx=[];
	//考核类型名称
	 var khlxmc=[];
	  var startDates = $('#startDates').datebox('getValue');
	  var endDates = $('#endDates').datebox('getValue');
	   var hzkhdw = $("#ywkhhzDwbm").combotree('tree');
	   var hzkhdwdx = hzkhdw.tree("getChecked");
	   var hzkhlx= $("#ywkhhzkhlx").combotree('tree');
	   var hzkhlxdx = hzkhlx.tree("getChecked");
	   if(!startDates){
           top.msgAlertInfo("选择起始年月");
           return;
       }
	   if(!endDates){
           top.msgAlertInfo("选择起始年月");
           return;
       }
	   if( hzkhdwdx.length==0){
           top.msgAlertInfo("请选择考核单位");
           return;
       }
       if(hzkhlxdx.length==0){
           top.msgAlertInfo("请选择考核类型");
           return;
       }
		$.each( hzkhdwdx,function(index,check1){
			dwmc.push(check1.text);
			dwbm.push(check1.id);
		});		
       $.each(hzkhlxdx,function(index,check2){
			khlx.push(check2.khlx);
			khlxmc.push(check2.text);
		});
       //选择字符串
       var strdwmc=dwmc.join();
       var strdwbm=dwbm.join();
       var strkhlx=khlx.join();
       var strkhlxmc=khlxmc.join();

    //设置考核时间
    $.post(rootPath + '/service/ywlxkh/dcndkh',{
        dz:"",
        strdwmc:strdwmc,
        kwdwbm:strdwbm,
        strkhlxmc:strkhlxmc ,
        khlx:strkhlx ,
        starDate:startDates,
        endDate:endDates
    },function (result) {
    	// console.log(result)
    	if(result.indexOf("fileName")!=-1){
    		var data = JSON.parse(result);
            $(".ywgzkhhzbounced").css("display","none");
            parent.ifenone();
            var ss =rootPath + "/service/ydkh/getExcel?filename="+data.fileName+"&filepath="+data.filePath;
            location.href=encodeURI(ss);
            top.msgAlertInfo("汇总对院考核成功!");
		}else {
            top.msgAlertInfo(result);
		}

    })


});

// 开启创建年度业务考核DIV【下面删除也调用了这个】
function bounced_a() {
	parent.ifeblick();
	$(".ywgzkh_bounced").css("display", " block");
	$("#bounced_tot_span").text("创建年度考核");
	$("#cjywkh_sure_btn").show();  //创建业务考核的确定按钮
	$("#delywkh_sure_btn").hide(); 
	
	//起始年月
	setDateBoxOnlyMonth("startDate");  //引用与common.js
	setDateBoxOnlyMonth("endDate");  //引用与common.js
	
    //设置考核时间
    $.post(rootPath + '/service/ywlxkh/getKhSj',{},function (result) {
        if(result){
            var startDate = result.split(",")[0];
            var endDate = result.split(",")[1];
            $("#startDate").datebox("setValue",startDate);
            $("#endDate").datebox("setValue",endDate);
        }
    })

	//查询参与考核的单位信息
	getKhdwInfo();   //引用与common.js
	//查询参与考核的类型信息
    getZhyw();     //引用与common.js
};


//查询参与考核的类型信息
function getZhyw() {
    $.post(rootPath + '/service/ywkhkhlxpz/getZhyw',{},function (result) {
        var zhywArr = result;
        if(zhywArr.length!=0){
            var ywArr = new Array();
            var lcmbArr = new Array();
            for (var i in zhywArr) {
                if (!zhywArr[i]['ywmc']) {
                    continue;
                }
                var haveYw = false;
                for (var n in ywArr) {
                    if (ywArr[n] == zhywArr[i]['ywmc']) {
                        haveYw = true;
                        break;
                    }
                }
                if (haveYw) {
                    continue;
                } else {
                    ywArr.push(zhywArr[i]['ywmc']);
                    var childrenValue = new Array();
                    for (var j in zhywArr) {
                        if (zhywArr[j]['ywmc'] == zhywArr[i]['ywmc']) {
                            childrenValue.push({
                                "id": zhywArr[j]['id'],
                                "text": zhywArr[j]['khmc'],
                                "khlx": zhywArr[j]['khlx'],
                            });
                        }
                    }
                    lcmbArr.push({
                        "id": zhywArr[i]['ywlx'],
                        "text": zhywArr[i]['ywmc'],
                        "ywbm": zhywArr[i]['ywbm'],
                        "ywjc": zhywArr[i]['ywjc'],
                        "state": "open",//closed
                        "checked": "true",
                        "children": childrenValue
                    })
                }
            }
            for (var i in zhywArr) {
                if (!zhywArr[i]['ywmc']) {
                    lcmbArr.push({
                        "id": lcmb[i]['id'],
                        "text": lcmb[i]['khmc'],
                        "khlx": lcmb[i]['khlx'],
                    })

                }
            }
            $("#ywkhZbx").combotree({
                width : '336px',
                height : '30px',
                checkbox : true,
                multiple : true,
                data:lcmbArr,
                // height : '28px;',
               /* onCheck : function(node, checked) {
                    $("#ywkhZbx").combotree(node.target);
                }*/
            });
        }
    })
}


// 关闭创建年度业务考核DIV
function bounced_b() {
    parent.ifenone();
	$("#ywgzkhYear").val("");
	$('#ywkhDwbm').combotree('clear');
	$(".ywgzkh_bounced").css("display", " none");
	$("#delywkh_sure_btn").hide();
	$("#cjywkh_sure_btn").show();
};

// 点击首页标题事件
function kh_page(id,ywlxStr) {
	 location.href = "ywgzkh_total.html?khdwId=" +id+"&ywlxStr="+ywlxStr;
	 //审批页面----非审批页面
	/* var jsonData = JSON.parse(data); 
	 if ("1" == jsonData.requestWay) {
		 location.href = "ywgzkh_total.html?dwbm=" + jsonData.dwbm + "&year=" +jsonData.year + "&sfby=" + jsonData.sfby + "&line=" + jsonData.line; 
	 } 
	 if("2" == jsonData.requestWay) { 
		 location.href = "ywgzkh_total.html?year=" +jsonData.year + "&dlrdwbm=" + jsonData.dlrdwbm + "&cxdwbm=" +jsonData.cxdwbm + "&zt=" + jsonData.zt + "&line=" + jsonData.line;
	 }*/
}


// 查询当前登录人是否为省院的案件管理处（案管部门）\\\\\\\\\\\省院的单位编码=460000 且 ryjs=7时表示省院的案件管理处
function isOrNotAjgl(ryjs, dwjb) {

	// alert(ryjs+"========="+ryjs.toString().match("7"));
	if (dwjb == '2') {
		//1、登录人员为部门领导时，显示指定考核人按钮
		if (ryjs.toString().match("3") == "3") {
			$("#ywgzkhzdkhr").css("display", "block");// 指定考核人
		}
		//2、登录人员为案管且是部门领导则显示创建年度业务考核按钮
		if (ryjs.toString().match("7") == "7") {
			if (ryjs.toString().match("3") == "3") {
				$("#ywgzkhCjndkhBt").css("display", "block");// 创建年度业务考核
				$("#ywgzkh_a_del").show();
			}else{
				//查看部门指定审批配置表是否存在，存在则显示按钮【创建年度业务考核】、【指定考核人】
				var isExistBmzdkhr = getBmzdkhrInfo(); 
				if(isExistBmzdkhr){
					$("#ywgzkhCjndkhBt").css("display", "block");// 创建年度业务考核
					$("#ywgzkhzdkhr").css("display", "block");// 指定考核人
					$("#ywgzkh_a_del").show();
				}
			}
		}
	} else {
//		$("#ywgzkhCkby").css("display", "block");// 查看本院考核信息
	}

}

/***
 * 点击创建年度考核的点击事件
 * 
 * 注意说明：这儿并没有获取页面参与考核的单位编码而是从数据库的存储过程直接读取并为参与考核的单位添加考核指标项
 * 
 */
function addNdYwgzkh() {
	if (top.currentUserInfo) {
		// 获取考核单位树
		var tree = $("#ywkhDwbm").combotree('tree');
		// 获取根节点
		var root = tree.tree('getRoots');
//		console.info(root);
		if(!root){ 
			top.msgAlertInfo("请联系管理员在系统配置中配置参与业务考核的单位信息！");
		    parent.ifenone();
			return;
		}
		if ($("#startDate").datebox('getValue') == ""
			|| $('#endDate').datebox('getValue') == "") {
			top.msgAlertInfo("请先填写数据再操作！");
			return;
		}
		
		var startDate = $('#startDate').datebox('getValue');
		var endDate = $('#endDate').datebox('getValue');
		var year = endDate.substring(0,4); //获取结束年
		var addData = {
				dwbm : top.currentUserInfo.dwbm,
				gh : top.currentUserInfo.gh,
				bmbm : top.currentUserInfo.bmbm,
				year : year,
				createDate : startDate, 
				endDate : endDate
		};
		// 获取起止时间字符串转为数字后作对比并判断开始时间不能小于等于结束时间
		var seDateFlag = returnStartOrEndDateMsg(startDate, endDate);
		
		if (seDateFlag) {
			top.msgProgressTip("正在处理……");
			$.ajax({
				type : "get",
				url : rootPath + "/service/ywgzkh/insertNdYwgzkh",
				async : false,
				data : addData,
				dataType : 'json',
				success : function(data) {
					// console.info(data);
					top.msgProgressTipClose();
					$(".ywgzkh_bounced").css("display", " none");
					if (data) {						
						top.msgAlertInfo(data.msg);
					    parent.ifenone();
					}else{
						top.msgAlertInfo("操作失败！");
					}
				}
			});
		}
	}
}


//获取起止时间字符串转为数字后作对比并判断开始时间不能小于等于结束时间
function returnStartOrEndDateMsg(startDate, endDate) {
	
	var startDateTempString = startDate.replace(/-/g, "");
	var startDateNum = parseInt(startDateTempString);
	var endDateTempString = endDate.replace(/-/g, "");
	var endDateNum = parseInt(endDateTempString);

	if (startDateNum > endDateNum) {
		top.msgAlertInfo("开始时间不能大于结束时间！");
		return false;
	} else {
		return true;
	}
}

/**
 * 查询当前登录人是否是被指定的创建、指定考核人员
 * @returns 返回true或者false
 */
function getBmzdkhrInfo(){
	var result = false;
	var paramData = {
		bmys : "",
	    splx : "12",
	    dwbm : top.currentUserInfo.dwbm,
	    gh : top.currentUserInfo.gh
	};
	$.ajax({
		type : "get",
		url : rootPath + "/service/bmzdpz/getzdrxx",
		async : false,
		data : paramData,
		dataType : 'json',
		success : function(data) {
			if(data){
				result = true;
			}
		}
	});
	return result;
}  

//删除年度考核【开启div】
$("#ywgzkh_a_del").click(function(){
	parent.ifeblick();
	$(".ywgzkh_bounced").css("display", " block");
	$("#cjywkh_sure_btn").hide();  //创建业务考核的确定按钮
	$("#delywkh_sure_btn").show(); 
	$("#bounced_tot_span").text("删除业务考核");
	
	//起始年月
	setDateBoxOnlyMonth("startDate");  //引用与common.js
	setDateBoxOnlyMonth("endDate");  //引用与common.js
	
    //设置考核时间
    $.post(rootPath + '/service/ywlxkh/getKhSj',{},function (result) {
        if(result){
            var startDate = result.split(",")[0];
            var endDate = result.split(",")[1];
            $("#startDate").datebox("setValue",startDate);
            $("#endDate").datebox("setValue",endDate);
        }
    })

	//查询参与考核的单位信息
	getKhdwInfo();   //引用与common.js
	//查询参与考核的类型信息
    getZhyw();     //引用与common.js
});

$("#delywkh_sure_btn").click(function(){
	// 获取考核单位树
	var tree = $("#ywkhDwbm").combotree('tree');
	// 获取根节点
	var root = tree.tree('getRoots');
//	console.info(root);
	if(!root){ 
		top.msgAlertInfo("请联系管理员在系统配置中配置参与业务考核的单位信息！");
		parent.ifenone();
		return;
	}
	if ($("#startDate").datebox('getValue') == ""
		|| $('#endDate').datebox('getValue') == "") {
		top.msgAlertInfo("请先填写数据再操作！");
		return;
	}
	var nodes = tree.tree('getChecked');
	var dwbmArr = [];
	if(nodes.length<=0){
		top.msgAlertInfo("请先填写数据再操作！");
		return;
	}
	for(var i = 0;i<nodes.length;i++){
		dwbmArr.push(nodes[i].id);
	}
	//业务类型
	var lxtree = $("#ywkhZbx").combotree('tree');
	var lxNodes = lxtree.tree('getChecked');
	var lxArr = [];
	if(lxNodes.length<=0){
		top.msgAlertInfo("请先填写数据再操作！");
		return;
	}
	for(var i = 0;i<lxNodes.length;i++){
		if(""!=checkStrReturnEmpty(lxNodes[i].khlx)){
			lxArr.push(lxNodes[i].khlx);
		}
	}
	
	var startDate = $('#startDate').datebox('getValue');
	var endDate = $('#endDate').datebox('getValue');
	var year = endDate.substring(0,4); //获取结束年
	var delData = {
		dwbmArr : dwbmArr,
		lxArr : lxArr,
		year : year,
		startDate : startDate, 
		endDate : endDate
	};
	// 获取起止时间字符串转为数字后作对比并判断开始时间不能小于等于结束时间
	var seDateFlag = returnStartOrEndDateMsg(startDate, endDate);
		
	if (seDateFlag) {
//			top.msgProgressTip("正在处理……");
		$.ajax({
			type : "post",
			url : rootPath + "/service/ywgzkh/deleteYwkh",
			async : false,
			data : delData,
			dataType : 'json',
			success : function(data) {
				parent.ifenone();
				// console.info(data);
				top.msgProgressTipClose();
				$(".ywgzkh_bounced").css("display", " none");
				if (data) {						
					top.msgAlertInfo(data.msg);
				}else{
					top.msgAlertInfo("操作失败！");
				}
			}
		});
	}
	
});

