var initdalx = null;
var gdid = null;
var datas = null;
var paramssss = null;
var g_wbid = null; // 保存外部id,修改时文件下载用
var currentListType = "sfjn"; // 全局变量 当前点击列表的类型 null
var isUpData = true; // 是否是修改
var jlIndex=null;   //修改时修改的第几条记录
var xzwjArray=new Array();  //记录上传文件的id数组
var xzWjJlTs=null;  //点击新增按钮时上传文件个数
var spzt = 0;// 此时档案的审批状态
var lcid = "";// 流程id
var spbzcount = 0;// 审批步骤数量
var spbztycount = 0;// 审批步骤同意数量
var spbzs = [10];// 审批步骤的对象
var nowPage=1;//全局变量用于存储当前页数-荣誉奖励
var pageCount;//计算页数
var id = null;
var currentId = null;//上传文件当前行id
var fjDatas = null;
var fjCounts = null;
var isShowFj;//点击附件数量时不能上传，删除，只能下载
var zrzjsl = 0;
var jsjdsl = 0;

var dlr_dwbm = top.currentUserInfo.dwbm; //当前登录人单位编码
var dlr_bmbm = top.currentUserInfo.bmbm;//当前登录人部门编码
var dlr_gh = top.currentUserInfo.gh;//当前登录人工号

var dassr_dwbm = getRequest().ssrdwbm;// 档案所属人单位编码
var dassr_gh = getRequest().ssr;// 档案所属人的工号
var dassr_daId = getRequest().wbid;// 档案所属人的id
var da_kssj = getRequest().kssj;
var da_jssj = getRequest().jssj;

//获取复选框选中行的id,多个中间用,隔开
var selectList = '';

var allInfoOfdassrArr = getPersonInfoByDwbmAndGh(dassr_dwbm,dassr_gh,"2");// 获取档案所属人的所有信息

for (var i = 0; i < 10; i++) {
	spbzs[i] = { // 审批步骤对象
		spdw : "", // 审批单位
		spr : "" // 审批人
	};
}

var jdlx = '';//节点类型 400 荣誉技能审批开始；401 荣誉技能人事部审批同意/荣誉技能人事部审批驳回；402 荣誉技能案管审批同意/荣誉技能案管审批驳回
var jdzt = '';//节点状态 0:开始 1:过程中 2:结束
var lclx = '';////流程类型：1：公示 ，2：档案，3：个人绩效，4：荣誉奖励，5：问题反馈，6：取消公示，7：档案封存审批   9:年度工作考核(市院) 10:年度工作考核(区院)

var isRyjn = false;
var lcmbArray = null;
//流程模板数组，此页面默认为荣誉技能发起审批流程
var lcmb = [{
	id : "007",//节点流程id
	jdlx : "400",//节点类型 400 荣誉技能审批开始；401 荣誉技能人事部审批同意/荣誉技能人事部审批驳回；402 荣誉技能案管审批同意/荣誉技能案管审批驳回
	lcjd : "40001",//流程节点
	xyjd : "401",//下一节点
	xycljs : "RSB",//下一处理角色
	xyclbm : "100",//下一处理部门 0:当前部门
	xycldw : "",//下一处理单位 0:当前单位
	jdzt : "0",//节点状态 0:开始 1:过程中 2:结束
	sm : "荣誉技能审批开始",//说明
	xycldwlx : "2",//下一步处理单位类型 1：上级 2：当前
	lclx : "4",//流程类型：1：公示 ，2：档案，3：个人绩效，4：荣誉奖励，5：问题反馈，6：取消公示，7：档案封存审批   9:年度工作考核(市院) 10:年度工作考核(区院)
	ztbg : "0"//此节点操作后状态变更
}];

var sfdaTypeNameArr = ["sfjn","sfzr","zycs","qt","jdqk"];  //对应司法档案的各个类型名称缩写
var sfdaTypeZwNameArr = ["荣誉奖励审批","责任追究异议审批","接受监督情况异议审批","业务研修审批","其他情况审批"];
var sfda_splx = ["4","15","14","16","17"];  //司法档案的审批类型【对应节点流程表的审批类型】
var sfda_type_spzt = ["0","1","2","3","4","5"]; //司法档案的审批各个审批类型的审批状态【对应审批表的审批状态值】


/**=======================================构建表格头=====================================================*/
//荣誉奖励
var jnTitle = '<li style="text-align: center;" class="ul_fost">'
					+ '<ul>'
						+ '<li style="margin-left: -40px;"><input type="checkbox" name="checkAll" onclick="allCheck(this)"></li>'
						+ '<li style="margin-left: -40px;" id="lb1">荣誉名称</li>'
						+ '<li id="lb2">获奖等级</li>'
						+ '<li id="lb3">获奖时间</li>'
						+ '<li id="lb4">颁奖单位</li>'
						+ '<li id="lb5">附件数量</li>'
						+ '<li id="lb6" style="display: none">获奖事由</li>'
						+ '<li id="lb7" style="display: none">其他情况</li>'
						+ '<li id="lb8" style="width:12.5%">操作</li>'
					+'</ul>'
			  +'</li>';

//责任追究
var jnTitle2 = '<li style="text-align: center;" class="ul_fost">'
					+ '<ul>'
						+'<li style="margin-left: -40px;"><input type="checkbox" name="checkAll" onclick="allCheck(this)"></li>'
						+'<li style="margin-left: -30px;" id="lb1">责任种类</li>'
						+'<li id="lb2">处理部门(单位)</li>'
						+'<li id="lb3">处理时间</li>'
						+'<li id="lb4">处理落实情况</li>'
						+'<li id="lb5">附件数量</li>'
						+'<li id="lb6" style="width:12.5%">操作</li>'
					+'</ul>'
				+'</li>';

//业务研修
var jnTitle3 = '<li style="text-align: center;" class="ul_fost3">'
					+ '<ul>'
    					+'<li style="margin-left: -37px;"><input type="checkbox" name="checkAll" onclick="allCheck(this)"></li>'
						+'<li style="margin-left: -40px;" id="lb1">研修名称</li>'
						+'<li id="lb2">研修类型</li>'
						+'<li id="lb3">开始时间</li>'
						+'<li id="lb4">结束时间</li>'
						+'<li id="lb5">研修地点</li>'
						+'<li id="lb6">组织部门</li>'
						+'<li id="lb7">审批部门</li>'
						+'<li id="lb8">主要收获</li>'
						+'<li id="lb9">研究成果</li>'
						+'<li id="lb10">附件数量</li>'
						+'<li id="lb11" style="width:98px;">操作</li>'
					+'</ul>'
				+'</li>';

//其他情况
var jnTitle4 = '<li style="text-align: center;" class="ul_fost_qtjk">'
					+'<ul>'
    					+'<li style="margin-left: -60px;"><input type="checkbox" name="checkAll" onclick="allCheck(this)"></li>'
						+'<li style="margin-left: -50px;" id="lb1">录入部门</li>'
						+'<li id="lb3">录入人</li>'
						+'<li id="lb4">录入时间</li>'
						+'<li id="lb5">附件数量</li>'
						+'<li id="lb6">操作</li>'
					+'</ul>'
				+'</li>';

//接受监督
var jnTitle5 = '<li style="text-align: center;" class="ul_fost5">'
					+'<ul>'
						+'<li style="margin-left: -40px;"><input type="checkbox" name="checkAll" onclick="allCheck(this)"></li>'
						+'<li style="margin-left: -50px;" id="lb1">监督种类</li>'
						+'<li id="lb2">监督部门</li>'
						+'<li id="lb3">监督时间</li>'
						+'<li id="lb4">监督处理</li>'
						+'<li id="lb5">落实整改情况</li>'
						+'<li id="lb6">附件数量</li>'
						+'<li id="lb7">操作</li>'
					+'</ul>'
				+'</li>';

/**========================================页面加载时执行=============================*/

$(function () {
	initdalx = getRequest().dalx;//档案类型（荣誉技能为1）
	paramssss = getRequest();//得到一个对象
	paramssss.dwbm = paramssss.ssrdwbm;//所属人单位编码(460000)
	paramssss.ssr = paramssss.ssr;//所属人(0059)
	paramssss.gdid = paramssss.wbid;//档案id(E5F7E54D59AE4DF89091EA309C495B2C)
	paramssss.sffc = paramssss.sffc;//是否封存 0：未封存 1：已封存 (0)

	// 获取流程模板
	$.ajax({
		url : rootPath + "/service/sp/getmb",
		type : 'post',
		async : false,
		data : {
			'jdlx' : lcmb[0].jdlx,//节点类型 （400 荣誉技能审批开始）,后台只接受了这一个参数
			"jdzt" : jdzt,
			"lclx" : lclx
		},
		dataType : 'json',
		success : function(data1) {
			lcmbArray = data1;//lcmbArray中有一个cljsxz:"005" 下一节点处理角色限制（角色编码）
		},
		error : function() {
		}
	});

	// 判断是否隐藏撤回按钮
	$.ajax({
		url : rootPath + "/service/sp/getjdlx",
		type : 'post',
		async : false,
		data : {
			spstid : paramssss.wbid,
			splx : lcmb[0].lclx
		},
		dataType : 'json',
		success : function(data1) {
			if (!data1 || data1 == '0' || data1 != lcmb[0].jdlx||paramssss.sffc=="1"||paramssss.spym=="2") {
				$('#button_recall').hide();
			}
		}
	});

	isHideStart();//判断是否隐藏发起审批按钮

	refrehmain(paramssss.gdid);

	//重新计算本页面的高度
	resizeParentIframe();

//	if (top.currentUserInfo.gh != paramssss.ssr||paramssss.sffc=="1"||paramssss.spym=="2") {
//		$('#button_ryjn').hide();//发起审批按钮
//		$('#button_recall').hide();//撤回申请按钮
//		$("#button_sfzr").hide();
//		$("#button_sfzr_recall").hide();
//		$("#button_jsjd").hide();
//		$("#button_jsjd_recall").hide();
//		$("#button_ywyx").hide();
//		$("#button_ywyx_recall").hide();
//		$("#button_qtqk").hide();
//		$("#button_qtqk_recall").hide();
//	}



});

function refrehmain(p_gdid) {
	//加载左侧树形图
	initcontentstree($(".contents_tree"), $(".tree_ul"),$(".dagztitle"));

    $('#startDate').datebox({
        value: da_kssj +'-01',
        width: 110,
		height: 30,
        panelHeight : 260,
        required: true,
        editable:false
    });
    $('#endDate').datebox({
        value: da_jssj + '-31',
        width: 110,
        height: 30,
        panelHeight : 260,
        required: true,
        editable:false
    });

	gdid = p_gdid;
	switch (initdalx) {
		case '1':
			$("#sfjn").show(); // 荣誉奖励情况标题
			sfjn();
			$("#sfjn").addClass("sftitle");//点击荣誉奖励时，右侧荣誉奖励标题添加下滑线
			$("#dataTitle").html("获奖时间：");
			break;
		case '2':
			$("#sfzr").show();
			sfzr();
			$("#sfzr").addClass("sftitle");
            $("#dataTitle").html("处理时间：");
            break;
		case '3':
			$("#zycs").show();
			zycs();
			$("#zycs").addClass("sftitle");
            $("#dataTitle").html("开始时间：");
            break;
		case '4':
			$("#qt").show();
			qt();
			$("#qt").addClass("sftitle");
            $("#dataTitle").html("录入时间：");
            break;
		case '5':
			$("#jdqk").show();
			jdqk();
			$("#jdqk").addClass("sftitle");
            $("#dataTitle").html("监督时间：");
			$("#archives_div_oldData").hide(); // 监督情况没有旧数据
			$("#archives_div_border").hide();
            break;
		}
	selectOldData(initdalx,gdid);
}
/**
 * 旧数据datagrid
 */
function selectOldData (dalx,gdid) {
	$("#archives_datagrid_oldData").datagrid({
//		  title: '<b>试运行数据</b>',
//		  fit: true,
          fitColumns: true,
		  scrollbarSize: 0,
          rownumbers: true,
          singleSelect: false,
          ctrlSelect: true,
          striped: true,
          nowrap: false,
          loadMsg: '数据加载中，请稍等。。。',
          pagination: true,
          pageNumber: 1,
          pageSize: 10,
          pageList: [5, 10, 15, 20],
          method: 'post',
          url: rootPath + '/service/sfda/selectOldData',
          queryParams: {
        	  dalx: dalx,
        	  gdid: gdid
          },
        loadFilter: function (data) {
        	return data.data;
        },
        columns: [[
        	{field: 'ID', title: '编号', align: 'center', hidden: true, width: '10%',
                formatter: function (value, row, index) {
                    return "<span title='"+ value +"'>" + value + "</span>";
                }
			},
			{field: 'LB', title: '类别 ', align: 'center',width: '15%',
                formatter: function (value, row, index) {
                    return "<span title='"+ value +"'>" + value + "</span>";
                }
			},
			{field: 'CJR', title: '创建人', align: 'center', width: '15%',
                formatter: function (value, row, index) {
                    return "<span title='"+ value +"'>" + value + "</span>";
                }
			},
			{field: 'CJSJ', title: '创建时间', align: 'center', width: '15%',
                formatter: function (value, row, index) {
                    return "<span title='"+ value +"'>" + value + "</span>";
                }
			},
			{field: 'MS', title: '描述', align: 'center', width: '44%',
                formatter: function (value, row, index) {
                    return "<span title='"+ value +"'>" + value + "</span>";
                }
			},
			{field: 'FJZS', title: '附件数量', align: 'center', width: '10%',
				formatter: function (value, row, index) {
					if (value > 0) {
						datas = $("#archives_datagrid_oldData").datagrid("getRows");
						var isShowFj = 'true';
						return '<a href="javascript:void(0)" onclick="showUploadFile(' + index + ',' + isShowFj + ')">' + value + '</a>'
					} else {
						return '0';
					}
				}
			}
		]],
        onLoadSuccess: function (data) {
			if (data.rows.length == 0) {
                $("#archives_div_oldData").hide(); // 如果没有旧数据，则隐藏datagrid
                $("#archives_div_border").hide();
			}
        }
    });
}


/**===================================判断是否隐藏发起审批按钮==============================*/
function isHideStart() {
	//审批页面与非审批页面的按钮显示控制
	var splxParam = sfda_splx[0];  //默认荣誉技能
	var sfdaTypeName = checkStrReturnEmpty(currentListType);
	if(sfdaTypeNameArr[1]==sfdaTypeName){  //责任追究【原司法责任】
		splxParam = sfda_splx[3];
	}else if(sfdaTypeNameArr[4]==sfdaTypeName){  //接受监督
		splxParam = sfda_splx[4];
	}else if(sfdaTypeNameArr[2]==sfdaTypeName){  //业务研修【原职业操守】
		splxParam = sfda_splx[1];
	}else if(sfdaTypeNameArr[3]==sfdaTypeName){  //其他
		splxParam = sfda_splx[2];
	}else{
		closeAllSfdaTypeAuditButton();
	}
//	console.log(sfdaTypeName+"----"+splxParam);

	// 获取最新审批实例信息
	$.ajax({
		url : rootPath + "/service/sp/selectNewSpsl",
		type : 'post',
		async : false,
		data : {
			"spdw" : paramssss.dwbm,
			"spbm" : "",
			"spr" : paramssss.ssr,
			"spstid" : paramssss.wbid,
			"splx" : splxParam   //lcmb[0].lclx
		},
		dataType : 'json',
		success : function(data) {
			//spzt'审批状态 0：发起审批 1：待审批 2.同意 3.不同意 4.同意结束 5.已读(不同意结束)';
			if(paramssss.spym=="2"||paramssss.spym=="1"){
				hiddenSfdaTypeButton();
			}else{
				if (top.currentUserInfo.gh != paramssss.ssr||paramssss.sffc=="1" || getRequest().sfgs == '1') {
					closeAllSfdaTypeAuditButton();
				}else{
					if(data&&data.length != 0){
						if(sfda_type_spzt[1]==data[0].spzt||sfda_type_spzt[2]==data[0].spzt||sfda_type_spzt[3]==data[0].spzt||sfda_type_spzt[4]==data[0].spzt) {  //审批过程中 sfda_type_spzt[4]!=data[0].spzt||sfda_type_spzt[5]!=data[0].spzt
                            if (sfdaTypeNameArr[0] == sfdaTypeName) { //荣誉技能
                                $('#button_ryjn').hide();
                                $("#xz").hide();
                                if (sfda_type_spzt[4] == data[0].spzt || sfda_type_spzt[5] == data[0].spzt) {
                                    $("#button_recall").hide();
                                } else {
                                    $("#button_recall").show();
                                }
                            } else if (sfdaTypeNameArr[1] == sfdaTypeName) {  //责任追究【原司法责任】
                                $("#button_sfzr").hide();
                                $("#sfzr_xz").hide();
                                if (sfda_type_spzt[4] == data[0].spzt || sfda_type_spzt[5] == data[0].spzt) {
                                    $("#button_sfzr_recall").hide();
                                } else {
                                    $("#button_sfzr_recall").show();
                                }
                            } else if (sfdaTypeNameArr[4] == sfdaTypeName) {  //接受监督
                                $("#button_jsjd").hide();
                                $("#jsjd_xz").hide();
                                if (sfda_type_spzt[4] == data[0].spzt || sfda_type_spzt[5] == data[0].spzt) {
                                    $("#button_jsjd_recall").hide();
                                } else {
                                    $("#button_jsjd_recall").show();
                                }
                            } else if (sfdaTypeNameArr[2] == sfdaTypeName) {  //业务研修【原职业操守】
                                $("#button_ywyx").hide();
                                $("#ywyx_xz").hide();
                                if (sfda_type_spzt[4] == data[0].spzt || sfda_type_spzt[5] == data[0].spzt) {
                                    $("#button_ywyx_recall").hide();
                                } else {
                                    $("#button_ywyx_recall").show();
                                }
                            } else if (sfdaTypeNameArr[3] == sfdaTypeName) {  //其他
                                $("#button_qtqk").hide();
                                $("#qt_xz").hide();
                                if (sfda_type_spzt[4] == data[0].spzt || sfda_type_spzt[5] == data[0].spzt) {
                                    $("#button_qtqk_recall").hide();
                                } else {
                                    $("#button_qtqk_recall").show();
                                }
                            } else {  //关闭所有发起审批、撤回审批按钮
                                closeAllSfdaTypeAuditButton();
                            }
                        }else if(sfda_type_spzt[5] == data[0].spzt){
                            if (sfdaTypeNameArr[0] == sfdaTypeName) { //荣誉技能
                                $('#button_ryjn').show();
                                $("#xz").hide();
                            } else if (sfdaTypeNameArr[1] == sfdaTypeName) {  //责任追究【原司法责任】
                                $("#button_sfzr").show();
                                $("#sfzr_xz").hide();
                            } else if (sfdaTypeNameArr[4] == sfdaTypeName) {  //接受监督
                                $("#button_jsjd").show();
                                $("#jsjd_xz").hide();
                            } else if (sfdaTypeNameArr[2] == sfdaTypeName) {  //业务研修【原职业操守】
                                $("#button_ywyx").show();
                                $("#ywyx_xz").hide();
                            } else if (sfdaTypeNameArr[3] == sfdaTypeName) {  //其他
                                $("#button_qtqk").show();
                                $("#qt_xz").hide();
                            } else {  //关闭所有发起审批、撤回审批按钮
                                closeAllSfdaTypeAuditButton();
                            }
						}else{
							if(sfdaTypeNameArr[0]==sfdaTypeName){
								$('#button_ryjn').show();
							}else if(sfdaTypeNameArr[1]==sfdaTypeName){  //责任追究【原司法责任】
								$("#button_sfzr").show();
							}else if(sfdaTypeNameArr[4]==sfdaTypeName){  //接受监督
								$("#button_jsjd").show();
							}else if(sfdaTypeNameArr[2]==sfdaTypeName){  //业务研修【原职业操守】
								$("#button_ywyx").show();
							}else if(sfdaTypeNameArr[3]==sfdaTypeName){  //其他
								$("#button_qtqk").show();
							}else{  //关闭所有发起审批、撤回审批按钮
								closeAllSfdaTypeAuditButton();
							}
						}
					}else{
						if(sfdaTypeNameArr[0]==sfdaTypeName){ //荣誉技能
							$('#button_ryjn').show();
							$("#xz").show();
						}else if(sfdaTypeNameArr[1]==sfdaTypeName){  //责任追究【原司法责任】
							$("#button_sfzr").show();
							$("#sfzr_xz").show();
						}else if(sfdaTypeNameArr[4]==sfdaTypeName){  //接受监督
							$("#button_jsjd").show();
							$("#jsjd_xz").show();
						}else if(sfdaTypeNameArr[2]==sfdaTypeName){  //业务研修【原职业操守】
							$("#button_ywyx").show();
							$("#ywyx_xz").show();
						}else if(sfdaTypeNameArr[3]==sfdaTypeName){  //其他
							$("#button_qtqk").show();
							$("#qt_xz").show();
						}else{  //关闭所有发起审批、撤回审批按钮
							closeAllSfdaTypeAuditButton();
						}
					}
				}
			}

//			if (!isRyjn || (data.length != 0 && data[0].spzt != '5')||paramssss.sffc=="1"||paramssss.spym=="2") {
//				$('#button_ryjn').hide();//隐藏发起审批按钮
//			} else {
//				$('#button_ryjn').show();//显示发起审批按钮
//			}
//			if (top.currentUserInfo.gh != paramssss.ssr||paramssss.sffc=="1"||paramssss.spym=="2") {
//				$('#button_ryjn').hide();
//				$('#button_recall').hide();
//				$("#button_sfzr").hide();
//				$("#button_sfzr_recall").hide();
//				$("#button_jsjd").hide();
//				$("#button_jsjd_recall").hide();
//				$("#button_ywyx").hide();
//				$("#button_ywyx_recall").hide();
//				$("#button_qtqk").hide();
//				$("#button_qtqk_recall").hide();
//			}
		}
	});
}

/**=====================================荣誉技能撤回审批能否显示========================*/
function ryjnchspxs() {
	var dlxx = top.currentUserInfo;// 登录信息
	var cardxx = getRequest();// card信息
	for (var i = 0; i < dlxx.ryjs.length; i++) {
		if (cardxx.ssrdwbm == dlxx.dwbm && cardxx.ssr == dlxx.gh && spbzcount == 2 && spzt == "1") {
			return true;
		}
	}
	return false;
}

/**=============================判断是否是自己、人事部、案管=============================*/
/*function iscsa(){
	var dlxx = top.currentUserInfo;// 登录信息
	var cardxx = getRequest();// card信息
	for (var i = 0; i < dlxx.ryjs.length; i++) {
		var ryjscopy = dlxx.ryjs[i];
		if (ryjscopy == 0 || ryjscopy == 7 || (cardxx.ssrdwbm == dlxx.dwbm && cardxx.ssr == dlxx.gh)) {
			return true;
		}
	}
	return false;
}*/

/**=============================================判断是否是纪检==================================================*/
function isld() {
	for (var i = 0; i < top.currentUserInfo.ryjs.length; i++) {
		var ryjscopy = top.currentUserInfo.ryjs[i];
        var bmys = top.currentUserInfo.bmyss[i];
		if (bmys == "0101") { // ryjscopy == 6
			return true;
		}
	}
	return false;
}

/**==============================================判断是否是案管================================================*/
function isAg() {
	var isag = top.currentUserInfo.isag;
	if(isag == "1"){
		return true;
	}else{
		return false;
	}
}

/**==========================================判断是否是"本部门"的负责人===========================================*/
function isBmFzr() {
	var dassr_bmbm = [];//档案所属人可能在多个部门
	for (var i = 0; i < allInfoOfdassrArr.length; i++){
		dassr_bmbm.push(allInfoOfdassrArr[i].BMBM);
	}
	for (var j = 0; j < dassr_bmbm.length; j++){
		for (var i = 0; i < top.currentUserInfo.ryjs.length; i++) {
			var ryjscopy = top.currentUserInfo.ryjs[i];
			if (dassr_bmbm[j] == dlr_bmbm && ryjscopy == 3) {
				return true;
			}
		}
	}
	return false;
}

/**=============================检察长、副检察长、分管院领导=======================================**/
function isJcz() {
	var dlr_info = getPersonInfoByDwbmAndGh(dlr_dwbm,dlr_gh,"2");
	var isYld = null;
	var isJcz = null;
	for(var i = 0;i < dlr_info.length;i++){
		if(dlr_info[i].JSMC == "检察长"){
			isJcz = true;
		}
		if(dlr_info[i].BMMC == "院领导"){
			isYld = true;
		}
	}
	if(isYld == true || isJcz == true){
		return true;
	}else {
		return false;
	}
}

/**===============================荣誉奖励，业务研修，其他情况都只能由自己进行增加、修改，其他人只能查看================*/
function isOneself() {
	for(var i=0;i<allInfoOfdassrArr.length;i++){//审批状态  0：发起审批  1：待审批  2.同意  3.不同意  4.同意结束  5.已读(不同意结束)';
		if (allInfoOfdassrArr[i].DWBM == dlr_dwbm && allInfoOfdassrArr[i].GH == dlr_gh && spzt != "1" && spzt != "2" && spzt != "3" && spzt != "4") {
			return true;
		}
	}
	return false;
}

/*****====================================判断是否是政治部人员，荣誉奖励，业务研修先改为由政治部和检察官自己添加=======================================================**/
function isZzbRy(){ ;
    var result = false;
    var dlrDwbm = "";
    var dlrGh = "";
    if(top.currentUserInfo){
        dlrDwbm = top.currentUserInfo.dwbm;
        dlrGh = top.currentUserInfo.gh;
    }

    var dlrData = getPersonInfoByDwbmAndGh(dlrDwbm,dlrGh,"2");
    console.log(dlrData);
    if(dlrData||dlrData.length>0){
        for(var i=0;i<dlrData.length;i++){
            if(dlrData[i].BMYS == "4001" && spzt != "1" && spzt != "2" && spzt != "3" && spzt != "4"){
                result = true;
            }
        }
    }
    return result;
}

/*
*//**================================荣誉技能自己能否修改=================================*//*
function ryjnzjxg() {
	var dlxx = top.currentUserInfo;// 当前登录人信息信息
	var cardxx = getRequest();// card信息
	for (var i = 0; i < dlxx.ryjs.length; i++) {
		var ryjscopy = dlxx.ryjs[i];
		if (cardxx.ssrdwbm == dlxx.dwbm && cardxx.ssr == dlxx.gh && spzt != "1"
				&& spzt != "2" && spzt != "3" && spzt != "4") {
			return true;
		}
	}
	return false;
}

*//**====================================荣誉技能人事部能否修改============================*//*
function ryjnrsbxg(sp_splx) {
	var dlxx = top.currentUserInfo;// 登录信息
	var cardxx = getRequest();// card信息
	for (var i = 0; i < dlxx.ryjs.length; i++) {
		var ryjscopy = dlxx.ryjs[i];
		if (ryjscopy == 0 && spbzcount == 2 && spzt == "1") {
			return true;
		}
	}
	return false;
}

*//**====================================荣誉技能案管能否修改==============================*//*
function ryjnagxg() {
	var dlxx = top.currentUserInfo;// 登录信息
	var cardxx = getRequest();// card信息
	for (var i = 0; i < dlxx.ryjs.length; i++) {
		var ryjscopy = dlxx.ryjs[i];
		if (ryjscopy == 7 && spbzcount == 3 && spzt == "1") {
			return true;
		}
	}
	return false;
}

*//**====================================档案归总其他档案自己能否修改==========================*//*
function dagzqtzjxg() {
	var dlxx = top.currentUserInfo;// 登录信息
	var cardxx = getRequest();// card信息
	for (var i = 0; i < dlxx.ryjs.length; i++) {
		var ryjscopy = dlxx.ryjs[i];
		if (cardxx.ssrdwbm == dlxx.dwbm && cardxx.ssr == dlxx.gh && spzt != "1"
				&& spzt != "2" && spzt != "3" && spzt != "4") {
			return true;
		}
	}
	return false;
}

*//**===================================档案归总其他档案审批步骤2的人能否修改===============*//*
function dagzqtbz2xg() {
	var dlxx = top.currentUserInfo;// 登录信息
	if (spbzs[1].spdw == dlxx.dwbm && spbzs[1].spr == dlxx.gh && spbzcount == 2
			&& spzt == "1") {
		return true;
	}
	return false;
}
*/

/**================================判断审批是否通过=====================================*/
function issptg(splx, spstid) {
	$.ajax({
		url : rootPath + "/service/sp/issptg",
		type : 'post',
		async : false,
		data : {
			"spstid" : spstid,
			"splx" : splx,
			"lcid" : paramssss.lcid
		},
		dataType : 'json',
		success : function(data) {debugger
			if (data != '' && data != null && typeof (data) != 'undefined') {
                spzt = data.spzt;
                lcid = data.lcid;
            }
		}
	});
}

if(parent.currentUserInfo.spymtype == 333){
	$('#button_ryjn').hide();
	$('#button_recall').hide();
}else{
	if (parent.currentUserInfo.spymtype != '0' || paramssss.sffc == "1" || paramssss.spym == "2") {
		$('#button_ryjn').hide();
		$('#button_recall').hide();
	}
}

//根据档案类型和归档id查询司法档案数量（此处是荣誉技能）
function selectGdidSfdaCount() {
	$.ajax({
		url : rootPath + "/service/sfda/selectgdidsfdacount",
		type : 'post',
		async : false,
		data : {
			'gdid' : paramssss.wbid,
			"dalx" : '1'
		},
		dataType : 'json',
		success : function(data) {
			lcmbArray = data1;
		}
	});
}


/**==================================审批流程实例步骤====================================*/
function splcslbz(sp_splx) {
	var dlxx = top.currentUserInfo;// 登录信息
	var cardxx = getRequest();// card信息
	spbzcount = 0;//审批步骤总数
	spbztycount = 0;//审批步骤同意总数
	$.ajax({
		url : rootPath + "/service/sfdasp/showsplc",
		type : 'post',
		async : false,
		data : {
			'wbid' : getRequest().wbid,
			'splx' : sp_splx,//4
			'lcid' : lcid
		},
		dataType : 'json',
		success : function(data) {
			spbzcount = data.length;
			spbztycount = 0;
			for (var i = 0; i < data.length; i++) {
				spbzs[data.length - i - 1].spdw = data[i].spdw;//spbzs 审批步骤的对象
				spbzs[data.length - i - 1].spr = data[i].spr;
				if (data[i].spztbm == '2')
					spbztycount++;
			}
		}
	});
}

/**=============================================显示各项总数============================================*/
function refrushcount(p_gdid1) {
	//荣誉技能总数
	$.getJSON(rootPath + '/service/sfdaRyjn/getSfdaRyjnPageList', {
		gdid : p_gdid1,
		_timestamp : new Date().getTime()
	}, function(result) {
		$("#sfjnzs").text(isEmptyto0(result.total) + "个");
	});

	//责任追究总数
	$.getJSON(rootPath + '/service/sfdaZrzj/getSfdaZrzjPageList', {
		gdid : p_gdid1,
		_timestamp : new Date().getTime()
	}, function(result) {
		zrzjsl = result.total;
		$("#sfzrzs").text(isEmptyto0(result.total) + "个");
	});

	//职业操守总数
	$.getJSON(rootPath + '/service/sfdaZycs/getSfdaZycsPageList', {
		gdid : p_gdid1,
		_timestamp : new Date().getTime()
	}, function(result) {
		$("#zycszs").text(isEmptyto0(result.total) + "个");
	});

	//其他情况总数
	$.getJSON(rootPath + '/service/sfdaQtqk/getSfdaQtqkPageList', {
		gdid : p_gdid1,
		_timestamp : new Date().getTime()
	}, function(result) {
		$("#qtzs").text(isEmptyto0(result.total) + "个");
	});

	//监督情况总数
	$.getJSON(rootPath + '/service/sfdaJdqk/getSfdaJdqkPageList', {
		gdid : p_gdid1,
		_timestamp : new Date().getTime()
	}, function(result) {
		jsjdsl = result.total;
		$("#jdqkzs").text(isEmptyto0(result.total) + "个");
	});
}

$('.textbo').textbox({
	width : 330,
	height : 28
});
$('.combo').combobox({
	width : 330,
	height : 28
});
$('.filebo').filebox({
	width : 330,
	height : 28,
	buttonText : '选择文件'
});

/**
 * *Util：
 *  1.时间控件
 *  2.text->textarea
 */
//荣誉奖励
function ryjnUtil(){
	//获奖时间控件
	$('#sfjnhjsj').datebox({
		value: ' ',
	    required: true,
	    editable:false
	});

	//修改司法技能获奖事由text->textarea
	$("#sfjnhjsy").textbox({
		multiline: true,
        validType: ['String', 'length[0,2000]'],
        height:"50px",
	});

	//修改司法技能获奖其他情况text->textarea
	$("#sfjnhjqtqk").textbox({
		multiline: true,
        validType: ['String', 'length[0,2000]'],
        height:"50px",
	});
}

//责任追究
function sfzrUtil(){
	//处理时间控件
	$('#zrzjclsj').datebox({
		value: ' ',
	    required: true,
	    editable:false
	});

	//责任追究处理事由text->textarea
	$("#zrzjclsy").textbox({
		multiline: true,
        validType: ['String', 'length[0,2000]'],
        height:"50px",
	});

	//责任追究处理内容text->textarea
	$("#zrzjclnr").textbox({
		multiline: true,
        validType: ['String', 'length[0,2000]'],
        height:"50px",
	});

	//责任追究其他情况text->textarea
	$("#zrzjzrqtqk").textbox({
		multiline: true,
        validType: ['String', 'length[0,2000]'],
        height:"50px",
	});
}

//业务研修
function zycsUtil(){
	//业务开始/结束时间控件
	$('#zycskssj').datebox({
		value: ' ',
	    required: true,
	    editable:false
	});
	$('#zycsjssj').datebox({
		value: ' ',
	    required: true,
	    editable:false
	});

	//业务研修主要收获text->textarea
	$("#zycszysh").textbox({
		multiline: true,
        validType: ['String', 'length[0,2000]'],
        height:"50px",
	});

	//职业操守研修成果text->textarea
	$("#zycsyjcg").textbox({
		multiline: true,
        validType: ['String', 'length[0,2000]'],
        height:"50px",
	});

	//职业操守主要内容text->textarea
	$("#zycszynr").textbox({
		multiline: true,
        validType: ['String', 'length[0,2000]'],
        height:"50px",
	});

	//职业操守其他情况text->textarea
	$("#zycsywqtqk").textbox({
		multiline: true,
        validType: ['String', 'length[0,2000]'],
        height:"50px",
	});
}

//其他情况
function qtUtil(){
	//时间控件
	$('#qtlrsj').datebox({
		value: ' ',
	    required: true,
	    editable:false
	});

	//修改其他情况录入内容text->textarea
	$("#qtlrnr").textbox({
		multiline: true,
        validType: ['String', 'length[0,2000]'],
        height:"50px",
	});
}

//接受监督情况
function jdqkUtil(){
	//时间控件
	$('#jdqkjdsj').datebox({
		value: ' ',
	    required: true,
	    editable:false
	});

	//监督事由text->textarea
	$("#jdqkjdsy").textbox({
		multiline: true,
        validType: ['String', 'length[0,2000]'],
        height:"50px",
	});

	//接受监督其他情况text->textarea
	$("#jdqkjdqtqk").textbox({
		multiline: true,
        validType: ['String', 'length[0,2000]'],
        height:"50px",
	});
}

/**========================================展示各项功能==========================================*/
/**
 * 荣誉奖励表格切换/初始化
 */
function sfjn(ulObj) {
	currentListType = "sfjn";
	isRyjn = true;
	buttonShowOrHidden(); //隐藏其他类型的按钮
	isHideStart();//判断是否隐藏
	issptg(sfda_splx[0], paramssss.wbid);// 将spzt和lcid赋值
	splcslbz(sfda_splx[0]);// 审批流程实例步骤
//	if (ryjnchspxs()&&paramssss.sffc!="1"&&paramssss.spym!="2"){  // 撤回申请按钮能否显示
//		$('#button_recall').show();
//	}
////	if (((ryjnzjxg() || ryjnrsbxg('4') || ryjnagxg()) && spzt != "4")&&paramssss.sffc!="1"&&paramssss.spym!="2") {
	if((isOneself() || isZzbRy()) && getRequest().sfgs == '2' && paramssss.sffc !="1" && paramssss.spym !="2" && spzt !="5"){//只要是自己的就能新增，修改
		$("#xz").slideDown();
	} else {
		$("#xz").hide();
	}
	if (ulObj != null || typeof (ulObj) != 'undefined') {
		$(".sftitle").removeClass("sftitle");
		$(ulObj).addClass("sftitle");
	}

	// 后台处理程序--查询司法档案下的荣誉技能 参数说明：第一位表示当前页  第二位表示档案类型，这儿荣誉技能为1
	// getRyjnOfSfda(1,"1");
    selectAllSkill(initdalx,gdid);
	refrushcount(gdid);//刷新技能总数
}

//查询司法档案下的荣誉奖励
function getRyjnOfSfda(pageNum,dalx){
	$.getJSON(rootPath + '/service/sfdaRyjn/getSfdaRyjnPageList',{
		pageNumber : pageNum, // 页数
		dalx : dalx,
		gdid : gdid,
		_timestamp : new Date().getTime()
	}, callbackFun);// 回传函数(这里是函数名)
}

//荣誉奖励展示
function callbackFun(result) {
//	if (((ryjnzjxg() || ryjnrsbxg('4') || ryjnagxg()) && spzt != "4")&&paramssss.sffc!="1"&&paramssss.spym!="2") {
//	if(isOneself() && paramssss.sffc !="1" && paramssss.spym !="2"){//暂时未考虑是否审批，只要是自己的就能新增，修改)
//		$("#xz").slideDown();
//	} else {
//		$("#xz").hide();
//	}
	$("#dalbUL").empty();
	$("#dalbUL").append(jnTitle);
	$("#dalb_div").css("display","block");//分页div
	pageCount = result.total;
	datas = result.data;

	//加上分页的按钮---如果荣誉技能数据多于6条显示分页按钮
	if (datas && pageCount>=6) {
		$("#dalb_div").empty();
		var $li = getPageLi("_ryjn","1");
		$("#dalb_div").append($li);
		$("#countText"+"_ryjn").text("总条数："+pageCount);
	}

	$.each(datas,function(i, item) {
		var li = $("<li style='text-align: center;' class='ul_last'/>");
		var ul = $("<ul/>");
		li.append(ul);

		var subLi0 = $("<li style='margin-left: -40px;'><input type='checkbox' name='subBox' onclick='chose(this)' value='"+item.id+"'></li>");
		ul.append(subLi0);

		var subLi1 = $("<li style='margin-left: -40px;' title='"+item.rymc+"'/>");
		subLi1.html(item.rymc ? item.rymc : "&nbsp;");
		ul.append(subLi1);

		var subLi2 = $("<li/>");
		var hjdj_sign = "hjdj";
		var hjdj_value = item.hjdj ? item.hjdj : "";
		var hjName = returnDataDictStr(hjdj_sign,hjdj_value); //调用数据字典查询方法
		subLi2.html(hjName ? hjName : "&nbsp;");
		ul.append(subLi2);

		var subLi3 = $("<li/>");
		subLi3.html(item.hjsj ? item.hjsj : "&nbsp;");
		ul.append(subLi3);

		var subLi4 = $("<li title='"+item.bjdw+"'/>");
		subLi4.html(item.bjdw ? item.bjdw : "&nbsp;");
		ul.append(subLi4);

		var subLi5 = $("<li/>");
		var id = item.id;
		//查询附件条数
		showFjCount(id);
		isShowFj = "true";//只能查看、下载附件
		subLi5.html(fjsl ? "<a href='javascript:void(0)' onclick=\"showUploadFile('" + i + "','" + isShowFj +"')\">" + fjsl + "</a>" : 0);
		ul.append(subLi5);

		var subLi6 = $("<li/>");
//		if ((ryjnzjxg() || ryjnrsbxg('4') || ryjnagxg())&& spzt != "4"&&paramssss.sffc!="1"&&paramssss.spym!="2") {
		if((isOneself() || isZzbRy()) && getRequest().sfgs == '2' && paramssss.sffc !="1" && paramssss.spym !="2"){//只要是自己的就能新增，修改
			isShowFj = "false";//因为点击附件数量和点击上传按钮都是调用showUploadFile()方法，在点击附件数量时需要隐藏附件上传框，和删除按钮，所以需要此标识
			ul.append("<li style='width:12.5%'>"
							+ "<a class='table_czan_ys' style='padding: 0 3px;margin-right: 4px;' href='javascript:void(0)' onclick=\"showUploadFile('"+ i + "','" + isShowFj +"')\">上传</a>"
//							+ "&nbsp;"
							+ "<a class='table_czan_ys' style='padding: 0 3px;margin-right: 4px;' href='javascript:void(0)' onclick='updateDaInfo("+ i + ")'>修改</a>"
//							+ "&nbsp;"
							+ "<a class='table_czan_ys' style='padding: 0 3px;margin-right: 4px;' href='javascript:void(0)' onclick=\"deleteDaInfo('"+ currentListType +"','"+ item.id + "')\">删除</a></li>");

		}else {
				ul.append("<li style='width:12.5%'><a class='table_czan_ys' style='padding: 0 5px;' href='javascript:void(0)' onclick='updateDaInfo("+ i + ")'>查看</a></li>");
				//设置表单不可修改
				//荣誉名称
				$("#sfjnrymc").textbox({
					disabled: true
				});

				//获奖等级
				$("#sfjnhjdj").combobox({
					disabled: true
				});
				//获奖时间
				$("#sfjnhjsj").textbox({
					disabled: true
				});
				//颁奖单位
				$("#sfjnbjdw").textbox({
					disabled: true
				});
				//获奖事由
				$("#sfjnhjsy").textbox({
					disabled: true
				});
				//其他情况
				$("#sfjnhjqtqk").textbox({
					disabled: true
				});
		}
		$("#dalbUL").append(li);
	});
}

/**
 * 责任追究表格切换/初始化
 */
function sfzr(ulObj) {;
	currentListType = "sfzr";
	isRyjn = false;
	buttonShowOrHidden(); //隐藏其他类型的按钮
	isHideStart();
	issptg('2', paramssss.wbid);  //查询审批状态
	if (!isOneself() && getRequest().sfgs == '2' && (isld() == true || isAg() == true || isBmFzr() == true || isJcz() == true) &&paramssss.sffc!="1"&&paramssss.spym!="2"&&paramssss.spym!="1") {
		$("#sfzr_xz").slideDown();
	} else {
		$("#sfzr_xz").hide();
	}
	if (ulObj != null || typeof (ulObj) != 'undefined') {
		$(".sftitle").removeClass("sftitle");
		$(ulObj).addClass("sftitle");
	}

	//查询司法档案下的责任追究
	// getSfzrOfSfda(1,"2");
    selectAllSkill(initdalx,gdid);

	refrushcount(gdid);//刷新各项总数
}

//查询司法档案下的责任追究
function getSfzrOfSfda(pageNum,dalx){
	$.getJSON(rootPath + '/service/sfdaZrzj/getSfdaZrzjPageList',{
		pageNumber : pageNum,
		dalx : dalx,
		gdid : gdid,
		_timestamp : new Date().getTime()
	}, callbackFunZr);// 回传函数(这里是函数名)
}

//责任追究列表展示
function callbackFunZr(result) {
	$("#dalbUL").empty();
	$("#dalbUL").append(jnTitle2);
	datas = result.data;
	$("#dalb_div").css("display","block");//分页div

	pageCount = result.total;
	datas = result.data;

	//加上分页的按钮---如果数据多于6条显示分页按钮
	if (datas && pageCount>=6) {
		$("#dalb_div").empty();
		var $li = getPageLi("_sfzr","2");
		$("#dalb_div").append($li);
		$("#countText"+"_sfzr").text("总条数："+pageCount);
	}else{
		$("#dalb_div").css("display","none");//分页div
	}

	$.each(datas,function(i, item) {
		var li = $("<li style='text-align: center;' class='ul_last'/>");
		var ul = $("<ul/>");
		li.append(ul);

        var subLi0 = $("<li style='margin-left: -40px;'><input type='checkbox' name='subBox' onclick='chose(this)' value='"+item.id+"'></li>");
        ul.append(subLi0);

		var subLi2 = $("<li style='margin-left: -30px;'/>");
		var zrzl_sign = "zrzl";
		var zrzl_value = item.zrzl ? item.zrzl : "";
		var zrzlName = returnDataDictStr(zrzl_sign,zrzl_value); //调用数据字典查询方法
		subLi2.html(zrzlName ? zrzlName : "&nbsp;");
		ul.append(subLi2);

		var clbmName = returnBmmcByDwBm(dlr_dwbm,item.clbm);
		var subLi3 = $("<li title='"+clbmName+"'/>");
		subLi3.html(clbmName ? clbmName : "&nbsp;");
		ul.append(subLi3);

		var subLi4 = $("<li/>");
		subLi4.html(item.clsj ? item.clsj : "&nbsp;");
		ul.append(subLi4);

		var subLi5 = $("<li/>");
		var cljg_sign = "lsqk";
		var cljg_value = item.cljg ? item.cljg : "";
		var cljgName = returnDataDictStr(cljg_sign,cljg_value); //调用数据字典查询方法
		subLi5.html(cljgName ? cljgName : "&nbsp;");
		ul.append(subLi5);

		var subLi6 = $("<li/>");
		var id = item.id;
		//查询附件条数
		showFjCount(id);
		isShowFj = "true";//只能查看、下载附件
		subLi6.html(fjsl ? "<a href='javascript:void(0)' onclick=\"showUploadFile('" + i + "','" + isShowFj +"')\">" + fjsl + "</a>" : 0);
		ul.append(subLi6);

		var subLi7 = $("<li/>");
//		if (isld() == true && spzt != "4"&&paramssss.sffc!="1"&&paramssss.spym!="2") {
		if (!isOneself() && getRequest().sfgs == '2' && (isld() == true || isAg() == true || isBmFzr() == true || isJcz() == true) && spzt != "4"&&paramssss.sffc!="1"&&paramssss.spym!="2") {
//			$("#xz").slideDown();
			isShowFj = "false";
			ul.append("<li style='width:12.5%'>"
						+ "<a class='table_czan_ys' style='padding: 0 3px;margin-right: 4px;' href='javascript:void(0)' onclick=\"showUploadFile('"+ i + "','" + isShowFj +"')\">上传</a>"
//						+ "&nbsp;"
						+ "<a class='table_czan_ys' style='padding: 0 3px;margin-right: 4px;' href='javascript:void(0)' onclick='updateDaInfo("+ i+ ")'>修改</a>"
//						+ "&nbsp;"
						+ "<a class='table_czan_ys' style='padding: 0 3px;margin-right: 4px;' href='javascript:void(0)' onclick=\"deleteDaInfo('"+currentListType+"','"+ item.id + "')\">删除</a></li>");
		} else {
//			$("#xz").hide();
			ul.append("<li style='width:12.5%'><a class='table_czan_ys' style='padding: 0 5px;' href='javascript:void(0)' onclick='updateDaInfo("+ i + ")'>查看</a></li>");
			//责任种类
			$("#zrzjzrzl").combobox({
				disabled: true
			});
			//处理部门
			$("#zrzjclbm").combobox({
				disabled: true
			});
			//处理结果
			$("#zrzjcljg").combobox({
				disabled: true
			});
			//处理时间
			$("#zrzjclsj").textbox({
				disabled: true
			});
			//处理事由
			$("#zrzjclsy").textbox({
				disabled: true
			});
			//处理内容
			$("#zrzjclnr").textbox({
				disabled: true
			});
			//责任追究其他情况
			$("#zrzjzrqtqk").textbox({
				disabled: true
			});
		}
		$("#dalbUL").append(li);
	});
}

/**
 * 业务研修表格切换/初始化【原职业操守】
 */
function zycs(ulObj) {
	currentListType = "zycs";
	isRyjn = false;//为true则显示发起审批按钮（和荣誉奖励一样）
	buttonShowOrHidden(); //隐藏其他类型的按钮
	isHideStart();
	issptg(sfda_splx[1], paramssss.wbid);//spzt'审批状态 0：发起审批 1：待审批 2.同意 3.不同意 4.同意结束 5.已读(不同意结束)';
//	splcslbz(sfda_splx[1]);
	 // 撤回申请按钮能否显示（和荣誉奖励一样） ryjnchspxs()&&
//	if (paramssss.sffc!="1"&&paramssss.spym!="2"){
//		$('#button_ywyx_recall').show();
//	}
	if((isOneself() || isZzbRy()) && getRequest().sfgs == '2' && paramssss.sffc !="1" && paramssss.spym !="2" && spzt !="5"){//只要是自己的就能新增，修改
		$("#ywyx_xz").slideDown();
	} else {
		$("#ywyx_xz").hide();
	}
	if (ulObj != null || typeof (ulObj) != 'undefined') {
		$(".sftitle").removeClass("sftitle");
		$(ulObj).addClass("sftitle");
	}

	// getZycsOfSfda(1,"3");
    selectAllSkill(initdalx,gdid);

	refrushcount(gdid);//刷新技能总数
}

//获取司法档案下的业务研修
function getZycsOfSfda(pageNum,dalx){
	$.getJSON(rootPath + '/service/sfdaZycs/getSfdaZycsPageList',{
		pageNumber : pageNum,
		dalx : dalx,
		gdid : gdid,
		_timestamp : new Date().getTime()
	}, callbackFunCs);// 回传函数(这里是函数名)
}

//业务研修列表展示
function callbackFunCs(result) {
	$("#dalbUL").empty();
	$("#dalbUL").append(jnTitle3);
	$("li.ul_last").remove();
	$("#dalb_div").css("display","block");//分页div

	pageCount = result.total;
	datas = result.data;

	//加上分页的按钮---如果数据多于6条显示分页按钮
	if (datas && pageCount>=6) {
		$("#dalb_div").empty();
		var $li = getPageLi("_zycs","3");
		$("#dalb_div").append($li);
		$("#countText"+"_zycs").text("总条数："+pageCount);
	}else{
		$("#dalb_div").css("display","none");//分页div
	}

	$.each(datas,function(i, item) {
		var li = $("<li style='text-align: center;' class='ul_last3'/>");
		var ul = $("<ul/>");
		li.append(ul);

        var subLi0 = $("<li style='margin-left: -37px;'><input type='checkbox' name='subBox' onclick='chose(this)' value='"+item.id+"'></li>");
        ul.append(subLi0);

		var subLi1 = $("<li style='margin-left: -40px;' title='"+item.xmmc+"'/>");
		subLi1.html(item.xmmc ? item.xmmc : "&nbsp;");
		ul.append(subLi1);

		var subLi2 = $("<li/>");
		var yxlx_sign = "yxlx";
		var yxlx_value = item.xmlx ? item.xmlx : "";
		var yxlxName = returnDataDictStr(yxlx_sign,yxlx_value); //调用数据字典查询方法
		subLi2.html(yxlxName ? yxlxName : "&nbsp;");
		ul.append(subLi2);

		var subLi3 = $("<li/>");
		subLi3.html(item.kssj ? item.kssj : "&nbsp;");
		ul.append(subLi3);

		var subLi4 = $("<li/>");
		subLi4.html(item.jssj ? item.jssj : "&nbsp;");
		ul.append(subLi4);

		var subLi5 = $("<li title='"+item.ywdd+"'/>");
		subLi5.html(item.ywdd ? item.ywdd : "&nbsp;");
		ul.append(subLi5);

		var zzbmName = returnBmmcByDwBm(dlr_dwbm,item.zzbm);
		var subLi6 = $("<li title='"+zzbmName+"'/>");
		subLi6.html(zzbmName ? zzbmName : "&nbsp;");
		ul.append(subLi6);

		var spbmName = returnBmmcByDwBm(dlr_dwbm,item.spbm);
		var subLi7 = $("<li title='"+spbmName+"'/>");
		subLi7.html(spbmName ? spbmName : "&nbsp;");
		ul.append(subLi7);

		var subLi8 = $("<li title='"+item.zysh+"'/>");
		subLi8.html(item.zysh ? item.zysh : "&nbsp;");
		ul.append(subLi8);

		var subLi9 = $("<li title='"+item.yjcg+"'/>");
		subLi9.html(item.yjcg ? item.yjcg : "&nbsp;");
		ul.append(subLi9);

		var subLi10 = $("<li/>");
		var id = item.id;
		//查询附件条数
		showFjCount(id);
		isShowFj = "true";//只能查看、下载附件
		subLi10.html(fjsl ? "<a href='javascript:void(0)' onclick=\"showUploadFile('" + i + "','" + isShowFj +"')\">" + fjsl + "</a>" : 0);
		ul.append(subLi10);

		var subLi11 = $("<li/>");
//		if (isld() == true && spzt != "4"&&paramssss.sffc!="1"&&paramssss.spym!="2") {
		if((isOneself() || isZzbRy()) && getRequest().sfgs == '2' && paramssss.sffc !="1" && paramssss.spym !="2"){//只要是自己的就能新增，修改
//			$("#xz").slideDown();
			isShowFj = "false";
			ul.append("<li style='width:98px;'>"
						+ "<a class='table_czan_ys' style='padding: 0 0.3px;margin-right: 2px;' href='javascript:void(0)' onclick=\"showUploadFile('"+ i + "','" + isShowFj +"')\">上传</a>"
//						+ "&nbsp;"
						+ "<a class='table_czan_ys' style='padding: 0 0.3px;margin-right: 2px;' href='javascript:void(0)' onclick='updateDaInfo("+ i+ ")'>修改</a>"
//						+ "&nbsp;"
						+ "<a class='table_czan_ys' style='padding: 0 0.3px;margin-right: 2px;' href='javascript:void(0)' onclick=\"deleteDaInfo('"+currentListType+"','"+ item.id + "')\">删除</a></li>");
		} else {
//			$("#xz").hide();
			ul.append("<li style='width:98px;'>"
						+"<a class='table_czan_ys' style='padding: 0 5px;' href='javascript:void(0)' onclick='updateDaInfo("+ i + ")'>查看</a>"
					    +"</li>");
			//研修名称
			$("#zycsxmmc").textbox({
				disabled: true
			});
			//研修类型
			$("#zycsxmlx").combobox({
				disabled: true
			});
			//业务开始时间
			$("#zycskssj").textbox({
				disabled: true
			});
			//业务结束时间
			$("#zycsjssj").textbox({
				disabled: true
			});
			//组织部门
			$("#zycszzbm").combobox({
				disabled: true
			});
			//审批部门
			$("#zycsspbm").combobox({
				disabled: true
			});
			//主要收获
			$("#zycszysh").textbox({
				disabled: true
			});
			//研修成果
			$("#zycsyjcg").textbox({
				disabled: true
			});
			//研修地点
			$("#zycsywdd").textbox({
				disabled: true
			});
			//主要内容
			$("#zycszynr").textbox({
				disabled: true
			});
			//其他情况
			$("#zycsywqtqk").textbox({
				disabled: true
			});
		}

		$("#dalbUL").append(li);
	});
}

/**
 * 其他情况表格切换/初始化
 */
function qt(ulObj) {
//	$('#button_recall').hide();// 撤回申请隐藏
	currentListType = "qt";
	isRyjn = false;
	buttonShowOrHidden(); //隐藏其他类型的按钮
	isHideStart();//判断是否隐藏
	issptg(sfda_splx[2], paramssss.wbid);//将spzt和lcid赋值
//	splcslbz(sfda_splx[2]);// 审批流程实例步骤
//	if (ryjnchspxs()&&paramssss.sffc!="1"&&paramssss.spym!="2")// 撤回申请按钮能否显示（和荣誉奖励一样）
//		$('#button_qtqk_recall').show();
////	if ((dagzqtzjxg() || dagzqtbz2xg()) && spzt != "4"&&paramssss.sffc!="1"&&paramssss.spym!="2") {
	if(isOneself() && getRequest().sfgs == '2' && paramssss.sffc !="1" && paramssss.spym !="2" && spzt !="5"){//只要是自己的就能新增，修改
		$("#qt_xz").slideDown();
	} else {
		$("#qt_xz").hide();
	}
	if (ulObj != null || typeof (ulObj) != 'undefined') {
		$(".sftitle").removeClass("sftitle");
		$(ulObj).addClass("sftitle");
	}

	//查询司法档案下的其他情况
	// getOtherOfSfda(1,"4");
    selectAllSkill(initdalx,gdid);

	refrushcount(gdid);//刷新技能总数
}

//查询司法档案下的其他情况
function getOtherOfSfda(pageNum,dalx){
	$.getJSON(rootPath + '/service/sfdaQtqk/getSfdaQtqkPageList',{
		pageNumber : pageNum,
		dalx : dalx,
		gdid : gdid,
		_timestamp : new Date().getTime()
	}, callbackFunQt);// 回传函数(这里是函数名)
}

//其他情况列表展示
function callbackFunQt(result) {

	$("#dalbUL").empty();
	$("#dalbUL").append(jnTitle4);
	$("li.ul_last").remove();
	$("#dalb_div").css("display","block");//分页div

	pageCount = result.total;
	datas = result.data;

	//加上分页的按钮---如果数据多于6条显示分页按钮
	if (datas && pageCount>=6) {
		$("#dalb_div").empty();
		var $li = getPageLi("_other","4");
		$("#dalb_div").append($li);
		$("#countText"+"_other").text("总条数："+pageCount);//分页总条数
	}else{
		$("#dalb_div").css("display","none");//分页div
	}

	$.each(result.data,function(i, item) {
		var li = $("<li style='text-align: center;' class='ul_last_qtjk'/>");
		var ul = $("<ul/>");
		li.append(ul);

        var subLi0 = $("<li style='margin-left: -60px;'><input type='checkbox' name='subBox' onclick='chose(this)' value='"+item.id+"'></li>");
        ul.append(subLi0);

		var lrbmName = returnBmmcByDwBm(dlr_dwbm,item.lrbm);
		var subLi2 = $("<li style='margin-left: -50px;' title='"+lrbmName+"'/>");
		subLi2.html(lrbmName ? lrbmName : "&nbsp;");
		ul.append(subLi2);

		var subLi3 = $("<li/>");
		subLi3.html(item.lrr ? item.lrr : "&nbsp;");
		ul.append(subLi3);

		var subLi4 = $("<li/>");
		subLi4.html(item.lrsj ? item.lrsj : "&nbsp;");
		ul.append(subLi4);

		var subLi5 = $("<li/>");
		var id = item.id;
		//查询附件条数
		showFjCount(id);
		isShowFj = "true";//只能查看、下载附件
		subLi5.html(fjsl ? "<a href='javascript:void(0)' onclick=\"showUploadFile('" + i + "','" + isShowFj +"')\">" + fjsl + "</a>" : 0);
		ul.append(subLi5);

		var subLi6 = $("<li/>");
//		if ((dagzqtzjxg() || dagzqtbz2xg()) && spzt != "4"&&paramssss.sffc!="1"&&paramssss.spym!="2") {
		if(isOneself() && getRequest().sfgs == '2' && paramssss.sffc !="1" && paramssss.spym !="2"){//只要是自己的就能新增，修改
			isShowFj = "false";
			ul.append("<li>"
						+ "<a class='table_czan_ys' style='padding: 0 5px;margin-right: 5px;' href='javascript:void(0)' onclick=\"showUploadFile('"+ i + "','" + isShowFj +"')\">上传</a>"
//						+ "&nbsp;"
						+ "<a class='table_czan_ys' style='padding: 0 5px;margin-right: 5px;' href='javascript:void(0)' onclick='updateDaInfo("+ i+ ")'>修改</a>"
//						+ "&nbsp;"
						+ "<a class='table_czan_ys' style='padding: 0 5px;margin-right: 5px;' href='javascript:void(0)' onclick=\"deleteDaInfo('"+currentListType+"','"+ item.id + "')\">删除</a></li>");
		} else {
			ul.append("<li><a class='table_czan_ys' style='padding: 0 5px;' href='javascript:void(0)' onclick='updateDaInfo("+ i + ")'>查看</a></li>");
			//录入部门
			$("#qtlrbm").combobox({
				disabled : true
			})
			//录入人
			$("#qtlrr").textbox({
				disabled : true
			})
			//录入时间
			$("#qtlrsj").textbox({
				disabled : true
			})
			//录入内容
			$("#qtlrnr").textbox({
				disabled : true
			})
		}

		$("#dalbUL").append(li);
	});
}

/**
 * 接受监督情况表格切换/初始化
 */
function jdqk(ulObj) {
	currentListType = "jdqk";
	isRyjn = false;
	buttonShowOrHidden();
	isHideStart();
	issptg('2', paramssss.wbid);
//	if (isld() == true && spzt != "4"&&paramssss.sffc!="1"&&paramssss.spym!="2") {
	if (!isOneself() && getRequest().sfgs == '2' && (isld() == true || isAg() == true || isBmFzr() == true || isJcz() == true)&&paramssss.sffc!="1"&&paramssss.spym!="2"&&paramssss.spym!="1") {//&& spzt != "4"
		$("#jsjd_xz").slideDown();
	} else {
		$("#jsjd_xz").hide();
	}
	if (ulObj != null || typeof (ulObj) != 'undefined') {
		$(".sftitle").removeClass("sftitle");
		$(ulObj).addClass("sftitle");
	}

	//查询司法档案下的接受监督情况
	// getJdqkOfSfda(1,"5");
    selectAllSkill(initdalx,gdid);

	refrushcount(gdid);//刷新技能总数
}

//查询司法档案下的接受监督情况
function getJdqkOfSfda(pageNum,dalx){
	$.getJSON(rootPath + '/service/sfdaJdqk/getSfdaJdqkPageList',{
		pageNumber : pageNum,
		dalx : dalx,
		gdid : gdid,
		_timestamp : new Date().getTime()
	}, callbackFunjdqk);// 回传函数(这里是函数名)
}

//接受监督情况列表展示
function callbackFunjdqk(result) { // 监督情况列表展示
	$("#dalbUL").empty();
	$("#dalbUL").append(jnTitle5);
	$("#dalb_div").css("display","block");//分页div

	pageCount = result.total;
	datas = result.data;

	//加上分页的按钮---如果数据多于6条显示分页按钮
	if (datas && pageCount>=6) {
		$("#dalb_div").empty();
		var $li = getPageLi("_jdqk","5");
		$("#dalb_div").append($li);
		$("#countText"+"_jdqk").text("总条数：" + pageCount);
	}else{
		$("#dalb_div").css("display","none");//分页div
	}

	$.each(datas,function(i, item) {
		var li = $("<li style='text-align: center;' class='ul_last5'/>");
		var ul = $("<ul/>");
		li.append(ul);

        var subLi0 = $("<li style='margin-left: -40px;'><input type='checkbox' name='subBox' onclick='chose(this)' value='"+item.id+"'></li>");
        ul.append(subLi0);

		var subLi2 = $("<li style='margin-left: -50px;'/>");
		var jdzl_sign = "jdzl";
		var jdzl_value = item.jdzl ? item.jdzl : "";
		var jdzlName = returnDataDictStr(jdzl_sign,jdzl_value); //调用数据字典查询方法
		subLi2.html(jdzlName ? jdzlName : "&nbsp;");
		ul.append(subLi2);

		var jdbmName = returnBmmcByDwBm(dlr_dwbm,item.jdbm);
		var subLi3 = $("<li title='"+jdbmName+"'/>");
		subLi3.html(jdbmName ? jdbmName : "&nbsp;");
		ul.append(subLi3);

		var subLi4 = $("<li/>");
		subLi4.html(item.jdsj ? item.jdsj : "&nbsp;");
		ul.append(subLi4);

		var subLi5 = $("<li/>");
		var jdcl_sign = "jdcl";
		var jdcl_value = item.jdcl ? item.jdcl : "";
		var jdclName = returnDataDictStr(jdcl_sign,jdcl_value); //调用数据字典查询方法
		subLi5.html(jdclName ? jdclName : "&nbsp;");
		ul.append(subLi5);

		var subLi6 = $("<li/>");
		var zgqk_sign = "zgqk";
		var zgqk_value = item.zgqk ? item.zgqk : "";
		var zgqkName = returnDataDictStr(zgqk_sign,zgqk_value); //调用数据字典查询方法
		subLi6.html(zgqkName ? zgqkName : "&nbsp;");
		ul.append(subLi6);

		var subLi7 = $("<li/>");
		var id = item.id;
		//查询附件条数
		showFjCount(id);
		isShowFj = "true";//只能查看、下载附件
		subLi7.html(fjsl ? "<a href='javascript:void(0)' onclick=\"showUploadFile('" + i + "','" + isShowFj +"')\">" + fjsl + "</a>" : 0);
		ul.append(subLi7);

		var subLi8 = $("<li/>");
//		if (isld() == true && spzt != "4"&&paramssss.sffc!="1"&&paramssss.spym!="2") {
		if (!isOneself() && getRequest().sfgs == '2' && (isld() == true || isAg() == true || isBmFzr() == true || isJcz() == true) && spzt != "4"&&paramssss.sffc!="1"&&paramssss.spym!="2") {
//			$("#xz").slideDown();
			isShowFj = "false";
			ul.append("<li>"
						+ "<a class='table_czan_ys' style='padding: 0 2px;margin-right: 2px;' href='javascript:void(0)' onclick=\"showUploadFile('"+ i + "','" + isShowFj +"')\">上传</a>"
//						+ "&nbsp;"
						+"<a class='table_czan_ys' style='padding: 0 2px;margin-right: 2px;' href='javascript:void(0)' onclick='updateDaInfo("+ i+ ")'>修改</a>"
//						+ "&nbsp;"
						+"<a class='table_czan_ys' style='padding: 0 2px;margin-right: 2px;' href='javascript:void(0)' onclick=\"deleteDaInfo('"+currentListType+"','"+ item.id + "')\">删除</a></li>");
		} else {
//			$("#xz").hide();
			ul.append("<li><a class='table_czan_ys' style='padding: 0 5px;' href='javascript:void(0)' onclick='updateDaInfo("+ i + ")'>查看</a></li>");
			//监督种类
			$("#jdqkjdzl").combobox({
				disabled: true
			});
			//监督部门
			$("#jdqkjdbm").combobox({
				disabled: true
			});
			//接受监督处理
			$("#jdqkjdcl").combobox({
				disabled: true
			});
			//整改情况
			$("#jdqkzgqk").combobox({
				disabled: true
			});
			//监督时间
			$("#jdqkjdsj").textbox({
				disabled: true
			});
			//监督事由
			$("#jdqkjdsy").textbox({
				disabled: true
			});
			//监督其他情况
			$("#jdqkjdqtqk").textbox({
				disabled: true
			});
		}
		$("#dalbUL").append(li);
	});
}
/**================================各项司法档案新增弹出框=================================*/
function addSubmit() { // 点击新增时
	parent.ifeblick();
	isUpData = false;
	switch (currentListType) {
		case "sfjn": // 荣誉奖励
			$('#bcButton1').text("保存"); // 新增页面保存按钮
			$("#sfjnform").form('clear');//清空表单
			$("#sfjnwj").form('clear');
			$("#upfile1").val("");
			$("#filediv1").hide();
			$("#sfjntitle").text("新增荣誉奖励情况"); //新增荣誉技能页面标题

			$("#filediv1").text('');
			xzWjJlTs=0;
			$("#sfjnwjbutton").attr("onclick","xzUpLoadFile()");
			$("#sfjnDetail").fadeIn("fast");

			//从数据字典中获取新增荣誉技能获奖等级下拉框，默认选中第一个值
			var hjdj_id = "sfjnhjdj";
			var hjdj_sign = "hjdj";
			getDataDictSelectVlaues(hjdj_id,hjdj_sign,null,null);

			ryjnUtil();
			break;
		case "sfzr":
			$('#bcButton2').text("保存");
			$("#sfzrform").form('clear');
			$("#sfzrwj").form('clear');
			$("#upfile2").val("");
			$("#filediv2").hide();
			$("#sfzrtitle").text("新增责任追究情况");

			$("#filediv2").text('');
			xzWjJlTs=0;
			$("#sfzrwjbutton").attr("onclick","xzUpLoadFile()");
			$("#sfzrDetail").fadeIn("fast");

			//从数据字典中获取责任种类下拉框值，默认选中第一个值
			var zrzl_id = "zrzjzrzl";
			var zrzl_sign = "zrzl";
			getDataDictSelectVlaues(zrzl_id,zrzl_sign,null,null);

			//处理部门，默认选中当前登录人所属部门
	    	var zrzjclbm_id = "zrzjclbm";
	    	getBmbmSelectTree(zrzjclbm_id,dlr_dwbm,dlr_bmbm,null,null,null,"1");

			//从数据字典中获取处理落实情况下拉框值，默认选中第一个值
			var lsqk_id = "zrzjcljg";
			var lsqk_sign = "lsqk";
			getDataDictSelectVlaues(lsqk_id,lsqk_sign,null,null);

			sfzrUtil();
			break;
		case "zycs":
			$('#bcButton3').text("保存");
			$("#zycsform").form('clear');
			$("#zycswj").form('clear');
			$("#upfile3").val("");
			$("#filediv3").hide();
			$("#zycstitle").text("新增业务研修情况");

			$("#filediv3").text('');
			xzWjJlTs=0;
			$("#zycswjbutton").attr("onclick","xzUpLoadFile()");
			$("#zycsDetail").fadeIn("fast");

			//组织部门
	    	var zycszzbm_id = "zycszzbm";
	    	getBmbmSelectTree(zycszzbm_id,dlr_dwbm,dlr_bmbm,null,null,null,"1");

	    	//审批部门
	    	var zycsspbm_id = "zycsspbm";
	    	getBmbmSelectTree(zycsspbm_id,dlr_dwbm,dlr_bmbm,null,null,null,"1");

			//从数据字典中获取职业操守业务研修类型下拉框，默认选中第一个值
			var yxlx_id = "zycsxmlx";
			var yxlx_sign = "yxlx";
			getDataDictSelectVlaues(yxlx_id,yxlx_sign,null,null);

			zycsUtil();
			break;
		case "qt":
			$('#bcButton4').text("保存");// 新增页面保存按钮
			$("#qtform").form('clear');//清空表单
			$("#qtwj").form('clear');//清空文件
			$("#upfile4").val("");
			$("#filediv4").hide();
			$("#qttitle").text("新增其他情况");

			$("#filediv4").text('');
			xzWjJlTs=0;
			$("#qtwjbutton").attr("onclick","xzUpLoadFile()");
			$("#qtDetail").fadeIn("fast");

			//录入部门
	    	var qtlrbm_id = "qtlrbm";
	    	getBmbmSelectTree(qtlrbm_id,dlr_dwbm,dlr_bmbm,null,null,null,"1");

			qtUtil();
			break;
		case "jdqk":
			$('#bcButton5').text("保存");
			$("#jdqkform").form('clear');
			$("#jdqkwj").form('clear');
			$("#upfile5").val("");
			$("#filediv5").hide();
			$("#jdqktitle").text("新增接受监督情况");

			$("#filediv5").text('');
			xzWjJlTs=0;
			$("#jdqkwjbutton").attr("onclick","xzUpLoadFile()");
			$("#jdqkDetail").fadeIn("fast");

			//从数据字典中获取监督种类下拉框，默认选中第一个值
			var jdzl_id = "jdqkjdzl";
			var jdzl_sign = "jdzl";
			getDataDictSelectVlaues(jdzl_id,jdzl_sign,null,null);

			//从数据字典中获取接受监督处理下拉框，默认选中第一个值
			var jdcl_id = "jdqkjdcl";
			var jdcl_sign = "jdcl";
			getDataDictSelectVlaues(jdcl_id,jdcl_sign,null,null);

			//从数据字典中获取整改落实情况下拉框，默认选中第一个值
			var zgqk_id = "jdqkzgqk";
			var zgqk_sign = "zgqk";
			getDataDictSelectVlaues(zgqk_id,zgqk_sign,null,null);

			//监督部门
	    	var jdqkjdbm_id = "jdqkjdbm";
	    	getBmbmSelectTree(jdqkjdbm_id,dlr_dwbm,dlr_bmbm,null,null,null,"1");

			jdqkUtil();
			break;
		default:
			break;
	}
}

/**=======================================上传/展示附件弹出框==================================================*/
function showUploadFile(id,flag){
	parent.ifeblick();
	currentId = id;//!!datas[index].id ? datas[index].id : datas[index].ID;//拿到当前行的id，作为附件表中的wbid
	$("#fjDetail").fadeIn("fast");//弹出上传文件框
	$("#fjForm").css("display","block");//显示附件上传表单
	$("#fjwjbutton").css("display","block");//显示附件上传按钮
	$("#fjForm").form('clear');
	$("#fjUpFile").filebox('setText','');
	//datagrid展示附件详情
	showFjDatagrid(currentId,flag);

	//点击上传按钮执行上传操作
	$("#fjwjbutton").attr("onclick","uploadFile()");
}

/**===========================================分页展示附件详情datagrid==========================================*/
function showFjDatagrid(wbid,flag){
	var id = "";
	var wjdz = "";
	var i = "";
	$("#fjDatagridTable").datagrid({
		url : rootPath + "/service/uploader/getFjPageList",
		width : 605,
		height : 200,
		singleSelect: true,//只允许选择一行
//        striped: false,//是否显示斑马线效果。
        loadMsg: '数据加载中，请稍等。。。',
		rownumbers : true,//如果为true，则显示一个行号列。
		pagination: true,//在底部显示分页条
		pageNumber : 1,//在设置分页属性的时候初始化页码。
		pageSize : 5,//每页显示多少条，如果有pageList数组，在pageList中必须要有值与之对应
		pageList : [5,10,15,20],//每页显示的条数可选
		fitColumns : true,//真正的自动展开/收缩列的大小，以适应网格的宽度，防止水平滚动。列比较少的时候最好为true
//		border : false,//定义是否显示面板边框。
		queryParams: {//在请求远程数据的时候发送额外的参数。
			wbid : wbid
        },
		columns : [ [
			{field : 'id',title : '文件id',width : 0,align : 'center',hidden: true,},
			{field : 'wbid',title : '外部id',width : 0,align : 'center',hidden: true,},
			{field : 'wjdz',title : '文件地址',width : 0,align : 'center',hidden: true,},
			{field : 'wjmc',title : '文件名称',width : '50%',align : 'center',},
			{field : 'fjlx',title : '附件类型',width : '24%',align : 'center',
				formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
					//YX_FJ表 -->附件类型:11代表荣誉技能附件；12代表责任追究附件；13代表被监督情况附件；14代表职业操守附件；15代表其他情况附件
					var resultStr = "";
					if("11"==value){
						resultStr = "荣誉技能附件";
					}else if("12"==value){
						resultStr = "责任追究附件";
					}else if("13"==value){
						resultStr = "被监督情况附件";
					}else if("14"==value){
						resultStr = "职业操守附件";
					}else if("15"==value){
						resultStr = "其他情况附件";
					}
					return resultStr;
				},
			},
			{field : 'cz',title : '操作',width : '24%',align : 'center',
				formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
					var id = row.id;
					var wjdz = row.wjdz;
					var xzUrl = rootPath + "/service/uploader/download?wbid=" + wbid+"&id="+id;
					if(flag == "false"){
						$("#fjForm").css("display","block");//显示附件上传表单
						$("#fjwjbutton").css("display","block");//显示附件上传按钮
						return '<a class="table_czan_ys" href="'+xzUrl+'" style="margin-left:10px;">下载</a>'
						+"&nbsp;"
						+'<a class="table_czan_ys" href="javascript:void(0)" onclick='+'fileDelete("'+id+'","'+wjdz+'","'+index+'")'+' style="margin-left:10px;">删除</a>';
					}else{
						$("#fjForm").css("display","none");//隐藏附件上传表单
						$("#fjwjbutton").css("display","none");//隐藏附件上传按钮
						return '<a class="table_czan_ys" href="'+xzUrl+'" style="margin-left:10px;">下载</a>';
					}
				}
			} ] ]
	});
}

/**===========================================查询附件条数============================================*/
function showFjCount(id){
	$.ajax({url : rootPath + "/service/uploader/selectbywbid",
		type : 'post',
		async : false,
		data : {
			wbid : id
		},
		dataType : 'json',
		success : function(result){
			fjsl=result.length;
		}
	});
}

/**=========================================点击开始上传执行操作================================================*/
function uploadFile(){
	var fileForm = $("#fjForm");
	var fileinput = $("#fjUpFile");
	var filemessage = fileinput.filebox('getText');//getText 获取显示的文本值。
    var fileSize = $("#filebox_file_id_1")[0].files[0].size;
    var maxSize = 50*1024*1024;//50M
    if(fileSize > maxSize) {
        top.msgAlertInfo("文件大小超过50M，无法上传！请联系管理员进行修改！");
        return;
    }

	if (filemessage != '' && typeof (filemessage) != "undefined") {
		top.msgProgressTip("正在保存……");
		fileForm.form('submit', {
			url : rootPath + '/service/uploader/uploadAndId',
			onSubmit : function(param) {
				//YX_FJ表 -->附件类型:11代表荣誉技能附件；12代表责任追究附件；13代表被监督情况附件；14代表职业操守附件；15代表其他情况附件
				if(currentListType=="sfjn"){
					param.fjlx = '11';
				}else if(currentListType=="sfzr"){
					param.fjlx = '12';
				}else if(currentListType=="zycs"){
					param.fjlx = '13';
				}else if(currentListType=="qt"){
					param.fjlx = '14';
				}else if(currentListType=="jdqk"){
					param.fjlx = '15';
				}
				param.id = currentId;
			},
			success : function(data) {
				xzwjArray.push(data);
				top.msgProgressTipClose();
				top.msgAlertInfo("保存成功");
				fileForm.form('clear');
				fileinput.filebox('setText', '');
				//重新加载附件详情，刷新
				$('#fjDatagridTable').datagrid('load',{
					wbid: currentId,
				});
//				$("#fjDetail").fadeOut("fast");
				$("#fjForm").form('clear');
				$("#fjUpFile").filebox('setText','');
				refrushtable();//刷新
			}
		});
	}
}

/**====================================附件列表中的删除方法=============================================*/
function fileDelete(wjid,wjdz,item) { // 删除提示消息
	$.messager.confirm('删除提示', '您确认想要删除此文件吗？', function(r) {
		if (r) {
			var data = {
					id:wjid,
					dz:wjdz
			};
			$.post(rootPath + "/service/uploader/delete", data, function(data) {
				if(data=="1"){
					top.msgAlertInfo("删除成功");
					
					refrushtable();//刷新各项档案显示列表
					
					//重新加载附件详情，刷新
					$('#fjDatagridTable').datagrid('load',{
						wbid: currentId,
					});
				}
			});
		}
	});
}

/**=====================荣誉奖励，责任追究，监督情况，业务研修，其他档案修改弹出框===============*/
function updateDaInfo(data) {
	parent.ifeblick();
	isUpData = true;//修改标志
	g_wbid = data.id;
	// jlIndex=index;//jlIndex 修改时修改的第几条记录
	
	// datas[index].lbSelect = datas[index].lb;
	switch (currentListType) {
	case "sfjn": 
		//从数据字典中获取新增荣誉技能获奖等级下拉框，不会默认选中第一个值，而是显示原来的值
		var hjdj_id = "sfjnhjdj";
		var hjdj_sign = "hjdj";
		getDataDictSelectVlaues(hjdj_id,hjdj_sign,(data.hjdj),null);
		ryjnUtil();
		
//		if ((ryjnzjxg() || ryjnrsbxg('4') || ryjnagxg()) && spzt != "4"&&paramssss.sffc!="1"&&paramssss.spym!="2") {
		if(isOneself() && paramssss.sffc !="1" && paramssss.spym !="2"){//只要是自己的就能新增，修改
			//修改
			$('#bcButton1').text("修改");//原为保存，替换成修改
			$("#sfjnform").form('clear');//清空表单数据
			$('#sfjnform').form('load', data);//表单回显
			$("#sfjntitle").text("修改荣誉奖励情况");//标题
			$("#sfjnDetail").fadeIn("fast");//点击修改弹出修改表单
		} else {//查看
			$("#sfjnform").form('clear');
			$('#sfjnform').form('load', data);
			$("#sfjntitle").text("荣誉奖励情况");
			$("#sfjnDetail").fadeIn("fast");
			$('#bcButton1').hide();
		}
		break;
	case "sfzr":
		//修改 从数据字典中获取责任种类下拉框值，不会默认选中第一个值，而是显示原来的值
		var zrzl_id = "zrzjzrzl";
		var zrzl_sign = "zrzl";
		getDataDictSelectVlaues(zrzl_id,zrzl_sign,(data.zrzl),null);
		
		//修改 从数据字典中获取处理落实情况下拉框值，不会默认选中第一个值，而是显示原来的值
		var lsqk_id = "zrzjcljg";
		var lsqk_sign = "lsqk";
		getDataDictSelectVlaues(lsqk_id,lsqk_sign,(data.cljg),null);
		
		//处理部门
    	var zrzjclbm_id = "zrzjclbm";
    	getBmbmSelectTree(zrzjclbm_id,dlr_dwbm,(data.clbm),null,null,null,"1");
    	
		sfzrUtil();
		
//		if (isld() == true && spzt != "4"&&paramssss.sffc!="1"&&paramssss.spym!="2") {
		if ((isld() == true || isAg() == true || isBmFzr() == true || isJcz() == true) && spzt != "4"&&paramssss.sffc!="1"&&paramssss.spym!="2") {
			//修改
			$('#bcButton2').text("修改");
			$("#sfzrform").form('clear');
			$('#sfzrform').form('load', data);//表单回显
			$("#sfzrtitle").text("修改责任追究情况");
			$("#sfzrDetail").fadeIn("fast");
		} else {
			//查看
			$("#sfzrform").form('clear');
			$('#sfzrform').form('load', data);
			$("#sfzrtitle").text("责任追究情况");
			$("#sfzrDetail").fadeIn("fast");
			$('#bcButton2').hide();
			$('#xzwj').hide();
		}
		break;
	case "zycs":
		//修改 从数据字典中获取职业操守业务研修类型下拉框，不会默认选中第一个值，而是显示原来的值
		var yxlx_id = "zycsxmlx";
		var yxlx_sign = "yxlx";
		getDataDictSelectVlaues(yxlx_id,yxlx_sign,(data.yxlx),null);
		
		//组织部门
    	var zycszzbm_id = "zycszzbm";
    	getBmbmSelectTree(zycszzbm_id,dlr_dwbm,(data.zzbm),null,null,null,"1");
    	
    	//审批部门
    	var zycsspbm_id = "zycsspbm";
    	getBmbmSelectTree(zycsspbm_id,dlr_dwbm,(data.spbm),null,null,null,"1");
		
		zycsUtil();
		
//		if (isld() == true && spzt != "4"&&paramssss.sffc!="1"&&paramssss.spym!="2") {
		if(isOneself() && paramssss.sffc !="1" && paramssss.spym !="2"){//只要是自己的就能新增，修改
			$('#bcButton3').text("修改");//原为保存，替换成修改
			$("#zycsform").form('clear');//清空表单数据
			$('#zycsform').form('load', data);//表单回显
			$("#zycstitle").text("修改业务研修情况");//标题
			$("#zycsDetail").fadeIn("fast");//点击修改弹出修改表单
		} else {
			$('#bcButton3').hide();
			$("#zycsform").form('clear');
			$('#zycsform').form('load', data);
			$("#zycstitle").text("业务研修情况");
			$("#zycsDetail").fadeIn("fast");
		}
		break;
	case "qt":
		
		//录入部门
    	var qtlrbm_id = "qtlrbm";
    	getBmbmSelectTree(qtlrbm_id,dlr_dwbm,(data.lrbm),null,null,null,"1");
    	
		qtUtil();
		
//		if ((dagzqtzjxg() || dagzqtbz2xg()) && spzt != "4"&&paramssss.sffc!="1"&&paramssss.spym!="2") {
		if(isOneself() && paramssss.sffc !="1" && paramssss.spym !="2"){//只要是自己的就能新增，修改
			$('#bcButton4').text("修改");//原为保存，替换成修改
			$("#qtform").form('clear');//清空表单
			$('#qtform').form('load', data);
			$("#qttitle").text("修改其他情况");
			$("#qtDetail").fadeIn("fast");//点击修改弹出修改表单
		} else {//查看
			$('#bcButton4').hide();
			$("#qtform").form('clear');
			$('#qtform').form('load', data);
			$("#qttitle").text("其他情况");
			$("#qtDetail").fadeIn("fast");
		}
		break;
	case "jdqk":
		//修改 从数据字典中获取监督种类下拉框，不会默认选中第一个值，而是显示原来的值
		var jdzl_id = "jdqkjdzl";
		var jdzl_sign = "jdzl";
		getDataDictSelectVlaues(jdzl_id,jdzl_sign,(data.jdzl),null);
		
		//修改 从数据字典中获取接受监督处理下拉框，不会默认选中第一个值，而是显示原来的值
		var jdcl_id = "jdqkjdcl";
		var jdcl_sign = "jdcl";
		getDataDictSelectVlaues(jdcl_id,jdcl_sign,(data.jdcl),null);
		
		//修改 从数据字典中获取整改落实情况下拉框，不会默认选中第一个值，而是显示原来的值
		var zgqk_id = "jdqkzgqk";
		var zgqk_sign = "zgqk";
		getDataDictSelectVlaues(zgqk_id,zgqk_sign,(data.zgqk),null);
		
		//监督部门
    	var jdqkjdbm_id = "jdqkjdbm";
    	getBmbmSelectTree(jdqkjdbm_id,dlr_dwbm,(data.jdbm),null,null,null,"1");
    	
		jdqkUtil();
		
//		if (isld() == true && spzt != "4"&&paramssss.sffc!="1"&&paramssss.spym!="2") {
		if ((isld() == true || isAg() == true || isBmFzr() == true || isJcz() == true) && spzt != "4"&&paramssss.sffc!="1"&&paramssss.spym!="2") {
			//修改
			$('#bcButton5').text("修改");
			$("#jdqkform").form('clear');
			$('#jdqkform').form('load', data);//表单回显
			$("#jdqktitle").text("修改接受监督情况");
			$("#jdqkDetail").fadeIn("fast");
		} else {
			//查看
			$("#jdqkform").form('clear');
			$('#jdqkform').form('load', data);
			$("#jdqktitle").text("接受监督情况");
			$("#jdqkDetail").fadeIn("fast");
			$('#bcButton5').hide();
		}
		break;
	default:
		break;
	}
}

/**=======================================执行添加和修改的操作=================================================*/
function SubmitForm() { 
	
	parent.ifenone();
	var url = null;
	
	var theform = null;
	var fileform = null;
	var dalx = null;
	var fileinput = null
	switch (currentListType) {
	case "sfjn": // 荣誉奖励
		theform = $("#sfjnform");//技能表单
		$("#dalx1").val(1);
		
		//简单验证，荣誉名称和颁奖单位为必填项
		if (""==$("#sfjnrymc").val() || ""==$("#sfjnbjdw").val()) {
			top.msgAlertInfo("请填写荣誉名称或颁奖单位再操作！");
			return;
		}
		
		if (isUpData) {//判断是否为更新
			url = rootPath + "/service/sfdaRyjn/updateSfdaRyjn";//更新
			top.msgProgressTip("正在修改……");
			var wbid = null;
			theform.form('submit', {
				url : url,
				onSubmit : function(param) {//onSubmit：提交之前的回调函数
					param.dalx = dalx;
					param.gdid = gdid;//档案id
				},
				success : function(data) {//提交之后的回调函数
					data = JSON.parse(data);
					if(data.success == "1"){
						top.msgProgressTipClose();
						top.msgAlertInfo("修改成功");
						refrushtable();//刷新表单
						closeDetail();//关闭窗口
					}else{
						top.msgProgressTipClose();
						top.msgAlertInfo("修改失败");
						refrushtable();//刷新表单
						closeDetail();//关闭窗口
					}
				}
			});
		} else {
			url = rootPath + "/service/sfdaRyjn/addSfdaRyjn";//新增
			top.msgProgressTip("正在处理……");
			var wbid = null;
			theform.form('submit', {
				url : url,
				async : false,
				onSubmit : function(param) {
					param.dalx = dalx;
					param.gdid = gdid;
				},
				success : function(data) {
					data = JSON.parse(data);//接受一个JSON字符串，返回解析后的对象。在jQuery 3.0 及以后版本中，该方法（jQuery.parseJSON(str)）已被删除，请使用原生方法 JSON.parse(str)
					if(data.Y == "1"){
						top.msgProgressTipClose();
						top.msgAlertInfo("保存成功！");
						closeDetail();
						refrushtable();
					} else {
						top.msgProgressTipClose();
						top.msgAlertInfo("保存失败！");
						closeDetail();
						refrushtable();
					}
				}
			});
		}
		break;
	case "sfzr":
		theform = $("#sfzrform");
		$("#dalx3").val(2);
	
		//简单验证，处理部门为必填项
		if (""==$("#zrzjclbm").combobox("getText")) {
			top.msgAlertInfo("请填写处理部门再操作！");
			return;
		}
		
		if (isUpData) {//判断是否为更新
			url = rootPath + "/service/sfdaZrzj/updateSfdaZrzj";//更新
			top.msgProgressTip("正在修改……");
			var wbid = null;
			theform.form('submit', {
				url : url,
				onSubmit : function(param) {//onSubmit：提交之前的回调函数
					param.dalx = dalx;
					param.gdid = gdid;//档案id
				},
				success : function(data) {//提交之后的回调函数
					data = JSON.parse(data);
					if(data.success == "1"){
						top.msgProgressTipClose();
						top.msgAlertInfo("修改成功");
						refrushtable();//刷新表单
						closeDetail();//关闭窗口
					}else{
						top.msgProgressTipClose();
						top.msgAlertInfo("修改失败");
						refrushtable();//刷新表单
						closeDetail();//关闭窗口
					}
				}
			});
		} else {
			url = rootPath + "/service/sfdaZrzj/addSfdaZrzj";//新增
			
			top.msgProgressTip("正在处理……");
			var wbid = null;
			theform.form('submit', {
				url : url,
				async : false,
				onSubmit : function(param) {
					param.dalx = dalx;
					param.gdid = gdid;
				},
				success : function(data) {
					data = JSON.parse(data);
					if(data.Y == "1"){
						top.msgProgressTipClose();
						top.msgAlertInfo("保存成功！");
						closeDetail();
						refrushtable();
					} else {
						top.msgProgressTipClose();
						top.msgAlertInfo("保存失败！");
						closeDetail();
						refrushtable();
					}
				}
			});
		}
		break;
	case "zycs":
		theform = $("#zycsform");
		$("#dalx4").val(3);
		
		//简单验证，研修名称为必填项
		if (""==$("#zycsxmmc").val() || ""==$("#zycszzbm").combobox("getText") || ""==$("#zycsspbm").combobox("getText")) {
			top.msgAlertInfo("请填写研修名称或组织部门、审批部门再操作！");
			return;
		}
		
		if (isUpData) {//判断是否为更新
			url = rootPath + "/service/sfdaZycs/updateSfdaZycs";//更新
			top.msgProgressTip("正在修改……");
			var wbid = null;
			theform.form('submit', {
				url : url,
				onSubmit : function(param) {//onSubmit：提交之前的回调函数
					param.dalx = dalx;
					param.gdid = gdid;//档案id
				},
				success : function(data) {//提交之后的回调函数
					data = JSON.parse(data);
					if(data.success == "1"){
						top.msgProgressTipClose();
						top.msgAlertInfo("修改成功");
						refrushtable();//刷新表单
						closeDetail();//关闭窗口
					}else{
						top.msgProgressTipClose();
						top.msgAlertInfo("修改失败");
						refrushtable();//刷新表单
						closeDetail();//关闭窗口
					}
				}
			});
		} else {
			url = rootPath + "/service/sfdaZycs/addSfdaZycs";//新增
			top.msgProgressTip("正在处理……");
			var wbid = null;
			theform.form('submit', {
				url : url,
				async : false,
				onSubmit : function(param) {
					param.dalx = dalx;
					param.gdid = gdid;
				},
				success : function(data) {
					data = JSON.parse(data);
					if(data.Y == "1"){
						top.msgProgressTipClose();
						top.msgAlertInfo("保存成功！");
						closeDetail();
						refrushtable();
					} else {
						top.msgProgressTipClose();
						top.msgAlertInfo("保存失败！");
						closeDetail();
						refrushtable();
					}
				}
			});
		}
		break;
	case "qt":
		theform = $("#qtform");
		$("#dalx5").val(4);
		
		//简单验证，录入部门录入人为必填项
		if (""==$("#qtlrr").val() || ""==$("#qtlrbm").combobox("getText")) {
			top.msgAlertInfo("请填写录入人或录入部门再操作！");
			return;
		}
		
		if (isUpData) {
			url = rootPath + "/service/sfdaQtqk/updateSfdaQtqk";//更新
			
			top.msgProgressTip("正在修改……");
			var wbid = null;
			theform.form('submit', {
				url : url,
				onSubmit : function(param) {
					param.dalx = dalx;
					param.gdid = gdid;
				},
				success : function(data) {
					data = JSON.parse(data);
					if(data.success == "1"){
						top.msgProgressTipClose();
						top.msgAlertInfo("修改成功");
						refrushtable();//刷新表单
						closeDetail();//关闭窗口
					}else{
						top.msgProgressTipClose();
						top.msgAlertInfo("修改失败");
						refrushtable();//刷新表单
						closeDetail();//关闭窗口
					}
				}
			});
		} else {
			url = rootPath + "/service/sfdaQtqk/addSfdaQtqk";//新增
			
			top.msgProgressTip("正在处理……");
			var wbid = null;
			theform.form('submit', {
				url : url,
				async : false,
				onSubmit : function(param) {
					param.dalx = dalx;
					param.gdid = gdid;
				},
				success : function(data) {
					data = JSON.parse(data);
					if(data.Y == "1"){
						top.msgProgressTipClose();
						top.msgAlertInfo("保存成功！");
						closeDetail();
						refrushtable();
					} else {
						top.msgProgressTipClose();
						top.msgAlertInfo("保存失败！");
						closeDetail();
						refrushtable();
					}
				}
			});
		}
		break;
	case "jdqk":
		theform = $("#jdqkform");
		$("#dalx6").val(5);
		
		//简单验证，监督部门为必填项
		if (""==$("#jdqkjdbm").combobox("getText")) {
			top.msgAlertInfo("请填写监督部门再操作！");
			return;
		}
		
		if (isUpData) {//判断是否为更新
			url = rootPath + "/service/sfdaJdqk/updateSfdaJdqk";//更新
			
			top.msgProgressTip("正在修改……");
			var wbid = null;
			theform.form('submit', {
				url : url,
				onSubmit : function(param) {//onSubmit：提交之前的回调函数
					param.dalx = dalx;
					param.gdid = gdid;//档案id
				},
				success : function(data) {//提交之后的回调函数
					data = JSON.parse(data);
					if(data.success == "1"){
						top.msgProgressTipClose();
						top.msgAlertInfo("修改成功");
						refrushtable();//刷新表单
						closeDetail();//关闭窗口
					}else{
						top.msgProgressTipClose();
						top.msgAlertInfo("修改失败");
						refrushtable();//刷新表单
						closeDetail();//关闭窗口
					}
				}
			});
		} else {
			url = rootPath + "/service/sfdaJdqk/addSfdaJdqk";//新增
			
			top.msgProgressTip("正在处理……");
			var wbid = null;
			theform.form('submit', {
				url : url,
				async : false,
				onSubmit : function(param) {
					param.dalx = dalx;
					param.gdid = gdid;
				},
				success : function(data) {
					data = JSON.parse(data);
					if(data.Y == "1"){
						top.msgProgressTipClose();
						top.msgAlertInfo("保存成功！");
						closeDetail();
						refrushtable();
					} else {
						top.msgProgressTipClose();
						top.msgAlertInfo("保存失败！");
						closeDetail();
						refrushtable();
					}
				}
			});
		}
		break;
	default:
		break;
	}
}

/**======================================删除各项档案===============================================*/
function deleteDaInfo(currentType,id) { // 根据传入的标识和id删除
	
	var query_url = "";
	if("sfjn" == currentType){
		query_url = rootPath + '/service/sfdaRyjn/deleteSfdaRyjn?id='+id;
	}else if("sfzr" == currentType){
		query_url = rootPath + '/service/sfdaZrzj/deleteSfdaZrzj?id='+id;
	}else if("zycs" == currentType){
		query_url = rootPath + '/service/sfdaZycs/deleteSfdaZycs?id='+id;
	}else if("qt" == currentType){
		query_url = rootPath + '/service/sfdaQtqk/deleteSfdaQtqk?id='+id;
	}else if("jdqk" == currentType){
		query_url = rootPath + '/service/sfdaJdqk/deleteSfdaJdqk?id='+id;
	}
	
	$.messager.confirm('删除提示', '您确认想要删除记录吗？', function(r) {
		if (r) {
			$.get(query_url, function(result) {
				top.msgAlertInfo("删除成功");
				refrushtable();
			});
		}
	});
}

/**
 * 刷新table
 */
function refrushtable() {
	switch (currentListType) {
	case "sfjn": // 荣誉奖励
		sfjn();
		break;
	case "sfzr":
		sfzr();
		break;
	case "zycs":
		zycs();
		break;
	case "qt":
		qt();
		break;
	case "jdqk":
		jdqk();
		break;
	default:
		break;
	}
}

// 关闭弹出的新增/修改窗口
function closeDetail() {
	parent.ifenone();
	$("#sfjnDetail").fadeOut("fase");
	$("#sfzrDetail").fadeOut("fase");
	$("#zycsDetail").fadeOut("fase");
	$("#qtDetail").fadeOut("fase");
	$("#jdqkDetail").fadeOut("fase");
	$("#fjDetail").fadeOut("fase");
	xzwjArray.splice(0,xzWjJlTs);
	xzWjJlTs=0;   //记录条数初始化为零
}

/**
 * 得到当前时间
 * @returns {string}
 */
function getNowFormatDate() {
	var date = new Date();
	var seperator1 = "-";
	var seperator2 = ":";
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	var currentdate = date.getFullYear() + seperator1 + month + seperator1
			+ strDate + " " + date.getHours() + seperator2 + date.getMinutes()
			+ seperator2 + date.getSeconds();
	return currentdate;
}

function isEmptyto0(obj) {
	if (typeof (obj) == 'undefined')
		return obj = '0';
	return obj;
}

/**====================通用分页按钮:传入标签id标示,eg:_ryjn、_sfzr====================*/
function getPageLi(sign,paramLx){
	
	var li = "<div class='entertain_foot_foot'>"
        +"<div class='r_page'>"
            +"<a id='firstPage"+sign+"' class='ju_btn_white' style='width: 45px' onclick='goToFirstPage("+paramLx+");'>首页</a>"
            +"<a id='lastPage"+sign+"' class='ju_btn_white' onclick='goToLastPage("+paramLx+");'>上一页</a>"
            +"<a id='nextPage"+sign+"' class='ju_btn_white' onclick='goToNextPage("+paramLx+");'>下一页</a>"
            +"<a id='finalPage"+sign+"' class='ju_btn_white' style='width: 50px' onclick='goToFinalPage("+paramLx+");'>尾页</a>"
        +"</div>"
        +"<div>"
        	+"<span style='line-height:30px;margin-left:10px;'>每页条数：6</span>"
    		+"<span id ='countText"+sign+"' style='line-height:30px;margin-left:10px;'>总条数：</span>"
    	+"</div>"
    +"</div>";
	
	return li;
}

//点击首页事件
function goToFirstPage(paramLx){
	nowPage = "1";
	getInfoOfSfdaByType(nowPage,paramLx);
}

//点击尾页事件
function goToFinalPage(paramLx){
	
	nowPage = (pageCount%6==0?pageCount/6:Math.ceil(pageCount/6));
	
	getInfoOfSfdaByType(nowPage,paramLx);
}

//点击上一页
function goToLastPage(paramLx){
	--nowPage;
    if(nowPage<1){
    	nowPage=1;
        return;
    }
   
    getInfoOfSfdaByType(nowPage,paramLx);
}

//点击下一页
function goToNextPage(paramLx){
	++nowPage;
    if(nowPage>Math.ceil(pageCount/6)){
    	nowPage=(pageCount%6==0?pageCount/6:Math.ceil(pageCount/6));
        return;
    }
    
	getInfoOfSfdaByType(nowPage,paramLx);
}

//根据类型查询不同类型的司法档案下的对应类型信息
function getInfoOfSfdaByType(page,paramLx){
	
	$("#dalbUL").empty("");//清空列表
	$("#dalbUL_fj").empty("");
	
	if ("1"==paramLx) { //荣誉奖励
		getRyjnOfSfda(page,paramLx);
	}else if("2"==paramLx){ //司法责任
		getSfzrOfSfda(page,paramLx);
	}else if("3"==paramLx){ //业务研修
		getZycsOfSfda(page,paramLx);
	}else if("4"==paramLx){ //其他
		getOtherOfSfda(page,paramLx);
	}else if("5"==paramLx){ //　被监督情况
		getJdqkOfSfda(page,paramLx);
	}else{
		getRyjnOfSfda(page,paramLx);//默认跳第一个
	}
}

//修改时文件列表展示
/*function xgWjLbZs(){
	var index=jlIndex;
	$("#filediv1").hide();
	$("#filediv2").hide();
	$("#filediv3").hide();
	$("#filediv4").hide();
	$("#filediv5").hide();
	var wjTs=null; //文件条数
	$.ajax({url : rootPath + "/service/uploader/selectbywbid",
		type : 'post',
		async : false,
		data : {
			wbid : g_wbid
		},
		dataType : 'json',
		success : function(result){
		wjTs=result.length;
		if(currentListType=="sfjn"){
			
			$("#filediv1").text('');
			$.each(result,function(i, item){
				var wjmcP='<P id="filename'+i+'" style="display: inline-block;">'+item.wjmc+'</P>';
				var xzckA='<a id="filexiazai'+i+'" href="javascript:void(0)" style="margin-left:10px;">下载查看</a>';
				var scA='<a id="filesanchu'+i+'" href="javascript:void(0)" style="margin-left:10px;">删除</a></br>';
				$("#filediv1").append(wjmcP);
				$("#filediv1").append(xzckA);
				$("#filediv1").append(scA);
				$("#filexiazai"+i).attr("href",rootPath + "/service/uploader/download?wbid=" + item.wbid+"&id="+item.id);
				$("#filesanchu"+i).attr('onclick','fileDelete("'+item.id+'","'+item.wjdz+'","'+index+'")');			
			 });
			$("#filediv1").show();
		}else if(currentListType=="sfzr"){
			$("#filediv2").text('');
			$.each(result,function(i, item){
				var wjmcP='<P id="filename'+i+'" style="display: inline-block;">'+item.wjmc+'</P>';
				var xzckA='<a id="filexiazai'+i+'" href="javascript:void(0)" style="margin-left:10px;">下载查看</a>';
				var scA='<a id="filesanchu'+i+'" href="javascript:void(0)" style="margin-left:10px;">删除</a></br>';
				$("#filediv2").append(wjmcP);
				$("#filediv2").append(xzckA);
				$("#filediv2").append(scA);
				$("#filexiazai"+i).attr("href",rootPath + "/service/uploader/download?wbid=" + item.wbid+"&id="+item.id);
				$("#filesanchu"+i).attr('onclick','fileDelete("'+item.id+'","'+item.wjdz+'","'+index+'")');			
			 });
			$("#filediv2").show();
		}else if(currentListType=="zycs"){
			$("#filediv3").text('');
			$.each(result,function(i, item){
				var wjmcP='<P id="filename'+i+'" style="display: inline-block;">'+item.wjmc+'</P>';
				var xzckA='<a id="filexiazai'+i+'" href="javascript:void(0)" style="margin-left:10px;">下载查看</a>';
				var scA='<a id="filesanchu'+i+'" href="javascript:void(0)" style="margin-left:10px;">删除</a></br>';
				$("#filediv3").append(wjmcP);
				$("#filediv3").append(xzckA);
				$("#filediv3").append(scA);
				$("#filexiazai"+i).attr("href",rootPath + "/service/uploader/download?wbid=" + item.wbid+"&id="+item.id);
				$("#filesanchu"+i).attr('onclick','fileDelete("'+item.id+'","'+item.wjdz+'","'+index+'")');			
			 });
			$("#filediv3").show();
		}else if(currentListType=="qt"){
			$("#filediv4").text('');
			$.each(result,function(i, item){
				var wjmcP='<P id="filename'+i+'" style="display: inline-block;">'+item.wjmc+'</P>';
				var xzckA='<a id="filexiazai'+i+'" href="javascript:void(0)" style="margin-left:10px;">下载查看</a>';
				var scA='<a id="filesanchu'+i+'" href="javascript:void(0)" style="margin-left:10px;">删除</a></br>';
				$("#filediv4").append(wjmcP);
				$("#filediv4").append(xzckA);
				$("#filediv4").append(scA);
				$("#filexiazai"+i).attr("href",rootPath + "/service/uploader/download?wbid=" + item.wbid+"&id="+item.id);
				$("#filesanchu"+i).attr('onclick','fileDelete("'+item.id+'","'+item.wjdz+'","'+index+'")');			
			 });
			$("#filediv4").show();
		}else if(currentListType=="jdqk"){
			$("#filediv5").text('');
			$.each(result,function(i, item){
				var wjmcP='<P id="filename'+i+'" style="display: inline-block;">'+item.wjmc+'</P>';
				var xzckA='<a id="filexiazai'+i+'" href="javascript:void(0)" style="margin-left:10px;">下载查看</a>';
				var scA='<a id="filesanchu'+i+'" href="javascript:void(0)" style="margin-left:10px;">删除</a></br>';
				$("#filediv5").append(wjmcP);
				$("#filediv5").append(xzckA);
				$("#filediv5").append(scA);
				$("#filexiazai"+i).attr("href",rootPath + "/service/uploader/download?wbid=" + item.wbid+"&id="+item.id);
				$("#filesanchu"+i).attr('onclick','fileDelete("'+item.id+'","'+item.wjdz+'","'+index+'")');			
			 });
			$("#filediv5").show();
		}
	  }
	});
	return wjTs;
}*/

//修改时文件上传
/*function xgUpLoadFile() {
	var fileform = null;
	var fileinput = null;
	switch (currentListType) {
	case "sfjn": // 荣誉奖励
		fileform = $("#sfjnwj");
		fileinput = $("#upfile1");
		break;
	case "sfzr":
		fileform = $("#sfzrwj");
		fileinput = $("#upfile2");
		break;
	case "zycs":
		fileform = $("#zycswj");
		fileinput = $("#upfile3");
		break;
	case "qt":
		fileform = $("#qtwj");
		fileinput = $("#upfile4");
		break;
	case "jdqk":
		fileform = $("#jdqkwj");
		fileinput = $("#upfile5");
		break;
	default:
		break;
	}
	var filemessage = fileinput.filebox('getText');
	if (filemessage != '' && typeof (filemessage) != "undefined") {
		top.msgProgressTip("正在保存……");
		fileform.form('submit', {
			url : rootPath + '/service/uploader/upload',
			onSubmit : function(param) {
				param.wbid = g_wbid;
				param.fjlx = '1';
			},
			success : function(data) {
				top.msgProgressTipClose();
				top.msgAlertInfo("保存成功");
				fileform.form('clear');
				fileinput.filebox('setText', '');
				xgWjLbZs();
			}
		});
	}
}*/

//新增时上传文件
/*function xzUpLoadFile(){
	var fileform = null;
	var fileinput = null;
	switch (currentListType) {
	case "sfjn": // 荣誉奖励
		fileform = $("#sfjnwj");
		fileinput = $("#upfile1");
		break;
	case "sfzr":
		fileform = $("#sfzrwj");
		fileinput = $("#upfile2");
		break;
	case "zycs":
		fileform = $("#zycswj");
		fileinput = $("#upfile3");
		break;
	case "qt":
		fileform = $("#qtwj");
		fileinput = $("#upfile4");
		break;
	case "jdqk":
		fileform = $("#jdqkwj");
		fileinput = $("#upfile5");
		break;
	default:
		break;
	}
	var filemessage = fileinput.filebox('getText');//getText 获取显示的文本值。
	if (filemessage != '' && typeof (filemessage) != "undefined") {
		top.msgProgressTip("正在保存……");
		fileform.form('submit', {
			url : rootPath + '/service/uploader/uploadAndId',
			onSubmit : function(param) {
				param.fjlx = '1';
			},
			success : function(data) {
				xzwjArray.push(data);
				
				top.msgProgressTipClose();
				top.msgAlertInfo("保存成功");
				fileform.form('clear');
				fileinput.filebox('setText', '');
				xzScWjZs(data);
			}
		});
	}
}*/

//新增时上传文件展示（√）
/*function xzScWjZs(id){

	$.ajax({url : rootPath + "/service/uploader/selectbyid",
		type : 'post',
		async : false,
		data : {
			id : id
		},
		dataType : 'json',
		success : function(result){
			if(currentListType=="sfjn"){
	
					var wjmcP='<P id="filename'+xzWjJlTs+'" style="display: inline-block;">'+result.wjmc+'</P>';
					var xzckA='<a id="filexiazai'+xzWjJlTs+'" href="javascript:void(0)" style="margin-left:10px;">下载查看</a>';
					var scA='<a id="filesanchu'+xzWjJlTs+'" href="javascript:void(0)" style="margin-left:10px;">删除</a></br>';
					$("#filediv1").append(wjmcP);
					$("#filediv1").append(xzckA);
					$("#filediv1").append(scA);
					$("#filexiazai"+xzWjJlTs).attr("href",rootPath + "/service/uploader/downloadById?id=" + result.id);
					$("#filesanchu"+xzWjJlTs).attr('onclick','xzFileDelete("'+result.id+'","'+result.wjdz+'","'+xzWjJlTs+'")');			
					$("#filediv1").show();
			}else if(currentListType=="sfzr"){
				
					var wjmcP='<P id="filename'+xzWjJlTs+'" style="display: inline-block;">'+result.wjmc+'</P>';
					var xzckA='<a id="filexiazai'+xzWjJlTs+'" href="javascript:void(0)" style="margin-left:10px;">下载查看</a>';
					var scA='<a id="filesanchu'+xzWjJlTs+'" href="javascript:void(0)" style="margin-left:10px;">删除</a></br>';
					$("#filediv2").append(wjmcP);
					$("#filediv2").append(xzckA);
					$("#filediv2").append(scA);
					$("#filexiazai"+xzWjJlTs).attr("href",rootPath + "/service/uploader/downloadById?id=" + result.id);
					$("#filesanchu"+xzWjJlTs).attr('onclick','xzFileDelete("'+result.id+'","'+result.wjdz+'","'+xzWjJlTs+'")');			
				
					$("#filediv2").show();
			}else if(currentListType=="zycs"){
		
					var wjmcP='<P id="filename'+xzWjJlTs+'" style="display: inline-block;">'+result.wjmc+'</P>';
					var xzckA='<a id="filexiazai'+xzWjJlTs+'" href="javascript:void(0)" style="margin-left:10px;">下载查看</a>';
					var scA='<a id="filesanchu'+xzWjJlTs+'" href="javascript:void(0)" style="margin-left:10px;">删除</a></br>';
					$("#filediv3").append(wjmcP);
					$("#filediv3").append(xzckA);
					$("#filediv3").append(scA);
					$("#filexiazai"+xzWjJlTs).attr("href",rootPath + "/service/uploader/downloadById?id=" + result.id);
					$("#filesanchu"+xzWjJlTs).attr('onclick','xzFileDelete("'+result.id+'","'+result.wjdz+'","'+xzWjJlTs+'")');			
				
					$("#filediv3").show();
			}else if(currentListType=="qt"){
			
					var wjmcP='<P id="filename'+xzWjJlTs+'" style="display: inline-block;">'+result.wjmc+'</P>';
					var xzckA='<a id="filexiazai'+xzWjJlTs+'" href="javascript:void(0)" style="margin-left:10px;">下载查看</a>';
					var scA='<a id="filesanchu'+xzWjJlTs+'" href="javascript:void(0)" style="margin-left:10px;">删除</a></br>';
					$("#filediv4").append(wjmcP);
					$("#filediv4").append(xzckA);
					$("#filediv4").append(scA);
					$("#filexiazai"+xzWjJlTs).attr("href",rootPath + "/service/uploader/downloadById?id=" + result.id);
					$("#filesanchu"+xzWjJlTs).attr('onclick','xzFileDelete("'+result.id+'","'+result.wjdz+'","'+xzWjJlTs+'")');			
					$("#filediv4").show();
			}else if(currentListType=="jdqk"){
			
					var wjmcP='<P id="filename'+xzWjJlTs+'" style="display: inline-block;">'+result.wjmc+'</P>';
					var xzckA='<a id="filexiazai'+xzWjJlTs+'" href="javascript:void(0)" style="margin-left:10px;">下载查看</a>';
					var scA='<a id="filesanchu'+xzWjJlTs+'" href="javascript:void(0)" style="margin-left:10px;">删除</a></br>';
					$("#filediv5").append(wjmcP);
					$("#filediv5").append(xzckA);
					$("#filediv5").append(scA);
					$("#filexiazai"+xzWjJlTs).attr("href",rootPath + "/service/uploader/downloadById?id=" + result.id);
					$("#filesanchu"+xzWjJlTs).attr('onclick','xzFileDelete("'+result.id+'","'+result.wjdz+'","'+xzWjJlTs+'")');			
				
					$("#filediv5").show();
			}
	
		}
	});
		
	xzWjJlTs++;
}*/

//新增时列表刷新
/*function xzScWjLbSx(){
	
	$("#filediv1").text('');
	$("#filediv2").text('');
	$("#filediv3").text('');
	$("#filediv4").text('');
	$("#filediv5").text('');
	
	if(xzwjArray.length==0){
		return;
	}
	var newXzwjArray = JSON.stringify(xzwjArray);
	
			$.ajax({url : rootPath + "/service/uploader/selectAllFjxx",
				type : 'post',
				async : false,
				data : {
					idArray : newXzwjArray
				},
				dataType : 'json',
				success : function(data) {	
				var fjxxList=data.fjxxList;	
				$.each(fjxxList,function(i, result){
					if(currentListType=="sfjn"){
						var wjmcP='<P id="filename'+i+'" style="display: inline-block;">'+result.wjmc+'</P>';
						var xzckA='<a id="filexiazai'+i+'" href="javascript:void(0)" style="margin-left:10px;">下载查看</a>';
						var scA='<a id="filesanchu'+i+'" href="javascript:void(0)" style="margin-left:10px;">删除</a></br>';
						$("#filediv1").append(wjmcP);
						$("#filediv1").append(xzckA);
						$("#filediv1").append(scA);
						$("#filexiazai"+i).attr("href",rootPath + "/service/uploader/downloadById?id=" + result.id);
						$("#filesanchu"+i).attr('onclick','xzFileDelete("'+result.id+'","'+result.wjdz+'","'+i+'")');			
						$("#filediv1").show();
					}else if(currentListType=="sfzr"){
						var wjmcP='<P id="filename'+i+'" style="display: inline-block;">'+result.wjmc+'</P>';
						var xzckA='<a id="filexiazai'+i+'" href="javascript:void(0)" style="margin-left:10px;">下载查看</a>';
						var scA='<a id="filesanchu'+i+'" href="javascript:void(0)" style="margin-left:10px;">删除</a></br>';
						$("#filediv2").append(wjmcP);
						$("#filediv2").append(xzckA);
						$("#filediv2").append(scA);
						$("#filexiazai"+i).attr("href",rootPath + "/service/uploader/downloadById?id=" + result.id);
						$("#filesanchu"+i).attr('onclick','xzFileDelete("'+result.id+'","'+result.wjdz+'","'+i+'")');			
						$("#filediv2").show();
					}else if(currentListType=="zycs"){
						var wjmcP='<P id="filename'+i+'" style="display: inline-block;">'+result.wjmc+'</P>';
						var xzckA='<a id="filexiazai'+i+'" href="javascript:void(0)" style="margin-left:10px;">下载查看</a>';
						var scA='<a id="filesanchu'+i+'" href="javascript:void(0)" style="margin-left:10px;">删除</a></br>';
						$("#filediv3").append(wjmcP);
						$("#filediv3").append(xzckA);
						$("#filediv3").append(scA);
						$("#filexiazai"+i).attr("href",rootPath + "/service/uploader/downloadById?id=" + result.id);
						$("#filesanchu"+i).attr('onclick','xzFileDelete("'+result.id+'","'+result.wjdz+'","'+i+'")');			
						$("#filediv3").show();
					}else if(currentListType=="qt"){
						var wjmcP='<P id="filename'+i+'" style="display: inline-block;">'+result.wjmc+'</P>';
						var xzckA='<a id="filexiazai'+i+'" href="javascript:void(0)" style="margin-left:10px;">下载查看</a>';
						var scA='<a id="filesanchu'+i+'" href="javascript:void(0)" style="margin-left:10px;">删除</a></br>';
						$("#filediv4").append(wjmcP);
						$("#filediv4").append(xzckA);
						$("#filediv4").append(scA);
						$("#filexiazai"+i).attr("href",rootPath + "/service/uploader/downloadById?id=" + result.id);
						$("#filesanchu"+i).attr('onclick','xzFileDelete("'+result.id+'","'+result.wjdz+'","'+i+'")');			
						$("#filediv4").show();
					}else if(currentListType=="jdqk"){
						var wjmcP='<P id="filename'+i+'" style="display: inline-block;">'+result.wjmc+'</P>';
						var xzckA='<a id="filexiazai'+i+'" href="javascript:void(0)" style="margin-left:10px;">下载查看</a>';
						var scA='<a id="filesanchu'+i+'" href="javascript:void(0)" style="margin-left:10px;">删除</a></br>';
						$("#filediv5").append(wjmcP);
						$("#filediv5").append(xzckA);
						$("#filediv5").append(scA);
						$("#filexiazai"+i).attr("href",rootPath + "/service/uploader/downloadById?id=" + result.id);
						$("#filesanchu"+i).attr('onclick','xzFileDelete("'+result.id+'","'+result.wjdz+'","'+i+'")');			
						$("#filediv5").show();
				    }	
			    });
		}
	});
}
*/

//新增时删除文件
/*function xzFileDelete(wjid,wjdz,index) { // 删除提示消息

	$.messager.confirm('删除提示', '您确认想要删除此文件吗？', function(r) {
		if (r) {
			var data = {
					id:wjid,
					dz:wjdz
			};
			$.post(rootPath + "/service/uploader/delete", data, function(data) {
				if(data=="1"){
					top.msgAlertInfo("删除成功");
					xzwjArray.splice(index,1);
					xzWjJlTs--;
					xzScWjLbSx();
				}
			   });
		 	}
	});

}*/


function buttonShowOrHidden(){
	var sfdaTypeName = checkStrReturnEmpty(currentListType);
	
	if(sfdaTypeNameArr[0]==sfdaTypeName){
		$("#xz").show();
		$("#button_ryjn").show();
		$("#button_recall").hide();
		$("#sfzr_xz").hide();
		$("#button_sfzr").hide();
		$("#button_sfzr_recall").hide();
		$("#jsjd_xz").hide();
		$("#button_jsjd").hide();
		$("#button_jsjd_recall").hide();
		$("#ywyx_xz").hide();
		$("#button_ywyx").hide();
		$("#button_ywyx_recall").hide();
		$("#qt_xz").hide();
		$("#button_qtqk").hide();
		$("#button_qtqk_recall").hide();
	}else if(sfdaTypeNameArr[1]==sfdaTypeName){
		$("#xz").hide();
		$("#button_ryjn").hide();
		$("#button_recall").hide();
		$("#sfzr_xz").show();
		$("#button_sfzr").show();
		$("#button_sfzr_recall").hide();
		$("#jsjd_xz").hide();
		$("#button_jsjd").hide();
		$("#button_jsjd_recall").hide();
		$("#ywyx_xz").hide();
		$("#button_ywyx").hide();
		$("#button_ywyx_recall").hide();
		$("#qt_xz").hide();
		$("#button_qtqk").hide();
		$("#button_qtqk_recall").hide();
	}else if(sfdaTypeNameArr[2]==sfdaTypeName){
		$("#ywyx_xz").show();
		$("#button_ywyx").show();
		$("#button_ywyx_recall").hide();
		$("#xz").hide();
		$("#button_ryjn").hide();
		$("#button_recall").hide();
		$("#sfzr_xz").hide();
		$("#button_sfzr").hide();
		$("#button_sfzr_recall").hide();
		$("#jsjd_xz").hide();
		$("#button_jsjd").hide();
		$("#button_jsjd_recall").hide();
		$("#qt_xz").hide();
		$("#button_qtqk").hide();
		$("#button_qtqk_recall").hide();
	}else if(sfdaTypeNameArr[3]==sfdaTypeName){
		$("#xz").hide();
		$("#button_ryjn").hide();
		$("#button_recall").hide();
		$("#sfzr_xz").hide();
		$("#button_sfzr").hide();
		$("#button_sfzr_recall").hide();
		$("#jsjd_xz").hide();
		$("#button_jsjd").hide();
		$("#button_jsjd_recall").hide();
		$("#ywyx_xz").hide();
		$("#button_ywyx").hide();
		$("#button_ywyx_recall").hide();
		$("#qt_xz").show();
		$("#button_qtqk").show();
		$("#button_qtqk_recall").hide();
	}else if(sfdaTypeNameArr[4]==sfdaTypeName){
		$("#xz").hide();
		$("#button_ryjn").hide();
		$("#button_recall").hide();
		$("#sfzr_xz").hide();
		$("#button_sfzr").hide();
		$("#button_sfzr_recall").hide();
		$("#jsjd_xz").show();
		$("#button_jsjd").show();
		$("#button_jsjd_recall").hide();
		$("#ywyx_xz").hide();
		$("#button_ywyx").hide();
		$("#button_ywyx_recall").hide();
		$("#qt_xz").hide();
		$("#button_qtqk").hide();
		$("#button_qtqk_recall").hide();
	}
	
}

/*********************************************************责任追究、其他情况、业务研修情况添加******************************************************************************************/

/**
 * 【原司法责任】责任追究添加
 * @returns
 */
function sfzrAddSubmit(){
	parent.ifeblick();
	isUpData = false;
	$('#bcButton2').text("保存");
	$("#sfzrform").form('clear');
	$("#sfzrwj").form('clear');
	$("#upfile2").val("");
	$("#filediv2").hide();
	$("#sfzrtitle").text("新增责任追究情况");
	
	$("#filediv2").text('');
	xzWjJlTs=0;
	$("#sfzrwjbutton").attr("onclick","xzUpLoadFile()");
	$("#sfzrDetail").fadeIn("fast");
	
	//从数据字典中获取责任种类下拉框值，默认选中第一个值
	var zrzl_id = "zrzjzrzl";
	var zrzl_sign = "zrzl";
	getDataDictSelectVlaues(zrzl_id,zrzl_sign,null,null);
	
	//处理部门，默认选中当前登录人所属部门
	var zrzjclbm_id = "zrzjclbm";
	getBmbmSelectTree(zrzjclbm_id,dlr_dwbm,dlr_bmbm,null,null,null,"1");
	
	//从数据字典中获取处理落实情况下拉框值，默认选中第一个值
	var lsqk_id = "zrzjcljg";
	var lsqk_sign = "lsqk";
	getDataDictSelectVlaues(lsqk_id,lsqk_sign,null,null);
	
	sfzrUtil();
}


/**
 * 接受监督情况添加
 */
function jsjdAddSubmit(){
	parent.ifeblick();
	isUpData = false;
	$('#bcButton5').text("保存");
	$("#jdqkform").form('clear');
	$("#jdqkwj").form('clear');
	$("#upfile5").val("");
	$("#filediv5").hide();
	$("#jdqktitle").text("新增接受监督情况");
	
	$("#filediv5").text('');
	xzWjJlTs=0;
	$("#jdqkwjbutton").attr("onclick","xzUpLoadFile()");
	$("#jdqkDetail").fadeIn("fast");
	
	//从数据字典中获取监督种类下拉框，默认选中第一个值
	var jdzl_id = "jdqkjdzl";
	var jdzl_sign = "jdzl";
	getDataDictSelectVlaues(jdzl_id,jdzl_sign,null,null);
	
	//从数据字典中获取接受监督处理下拉框，默认选中第一个值
	var jdcl_id = "jdqkjdcl";
	var jdcl_sign = "jdcl";
	getDataDictSelectVlaues(jdcl_id,jdcl_sign,null,null);
	
	//从数据字典中获取整改落实情况下拉框，默认选中第一个值
	var zgqk_id = "jdqkzgqk";
	var zgqk_sign = "zgqk";
	getDataDictSelectVlaues(zgqk_id,zgqk_sign,null,null);
	
	//监督部门
	var jdqkjdbm_id = "jdqkjdbm";
	getBmbmSelectTree(jdqkjdbm_id,dlr_dwbm,dlr_bmbm,null,null,null,"1");
	
	jdqkUtil();
}

/**
 * 业务研修【原职业操守】添加
 */
function ywyxAddSubmit(){
	parent.ifeblick();
	isUpData = false;
	$('#bcButton3').text("保存");
	$("#zycsform").form('clear');
	$("#zycswj").form('clear');
	$("#upfile3").val("");
	$("#filediv3").hide();
	$("#zycstitle").text("新增业务研修情况");
	
	$("#filediv3").text('');
	xzWjJlTs=0;
	$("#zycswjbutton").attr("onclick","xzUpLoadFile()");
	$("#zycsDetail").fadeIn("fast");
	
	//组织部门
	var zycszzbm_id = "zycszzbm";
	getBmbmSelectTree(zycszzbm_id,dlr_dwbm,dlr_bmbm,null,null,null,"1");
	
	//审批部门
	var zycsspbm_id = "zycsspbm";
	getBmbmSelectTree(zycsspbm_id,dlr_dwbm,dlr_bmbm,null,null,null,"1");
	
	//从数据字典中获取职业操守业务研修类型下拉框，默认选中第一个值
	var yxlx_id = "zycsxmlx";
	var yxlx_sign = "yxlx";
	getDataDictSelectVlaues(yxlx_id,yxlx_sign,null,null);
	
	zycsUtil();
}


/**
 * 其他情况的添加
 * @returns
 */
function qtAddSubmit(){
	parent.ifeblick();
	isUpData = false;
	$('#bcButton4').text("保存");// 新增页面保存按钮
	$("#qtform").form('clear');//清空表单
	$("#qtwj").form('clear');//清空文件
	$("#upfile4").val("");
	$("#filediv4").hide();
	$("#qttitle").text("新增其他情况");
	
	$("#filediv4").text('');
	xzWjJlTs=0;
	$("#qtwjbutton").attr("onclick","xzUpLoadFile()");
	$("#qtDetail").fadeIn("fast");
	
	//录入部门
	var qtlrbm_id = "qtlrbm";
	getBmbmSelectTree(qtlrbm_id,dlr_dwbm,dlr_bmbm,null,null,null,"1");
	
	qtUtil();
}

/*************************************************************************司法档案、业务研修、其他情况的审批，责任追究、接受监督情况的异议审批*************************************************************************/
/**
 * 荣誉技能审批、撤回审批事件
 * @returns
 */
$('#button_ryjn').on('click', openSfdaTypeSpDiv);//发起审批  sendAudit  currentListType
$('#button_recall').on('click', recallSfdaTypeAudit);


function openSfdaTypeSpDiv(){
//	console.log(currentListType);
//	alert(selectList);//获取勾选中的id
	
/*	if(!selectList||""==selectList){
		top.msgAlertInfo("请选择要发起审批的数据项！");
		return;
	}*/
	
	var sfdaTypeName = checkStrReturnEmpty(currentListType);
	var count = 0;
	var daType = "";  //档案类型。1：荣誉技能，14：其他情况，15：业务研修 【跟随审批表的】16:责任追究 17：接受监督
	var sfdaTypeSpanName = ""; //前台页面标签名称
	var sfdaTypeSpanNTitle = ""; //前台页面标题名称
	var inputDisplay = "none";
	if(sfdaTypeNameArr[0]==sfdaTypeName){  
		daType = "4";
		sfda_type_title = sfdaTypeZwNameArr[0];
		sfdaTypeSpanName = "选择审批人：";
		inputDisplay = "block";
	}else if(sfdaTypeNameArr[2] == sfdaTypeName){
		daType = "15";
		sfda_type_title = sfdaTypeZwNameArr[3];
		sfdaTypeSpanName = "选择审批人：";
		inputDisplay = "block";
	}else if(sfdaTypeNameArr[3] == sfdaTypeName){ 
		daType = "14";
		sfda_type_title = sfdaTypeZwNameArr[4];
		sfdaTypeSpanName = "选择审批人：";
		inputDisplay = "block";
	}else if(sfdaTypeNameArr[1] == sfdaTypeName){  //责任追究【原司法责任】
		daType = "16";
		sfda_type_title = sfdaTypeZwNameArr[1];
		sfdaTypeSpanName = "选择审批人：";
		inputDisplay = "block";
	}else if(sfdaTypeNameArr[4] == sfdaTypeName){ //接受监督
		daType = "17";
		sfda_type_title = sfdaTypeZwNameArr[2];
		sfdaTypeSpanName = "选择审批人：";
		inputDisplay = "block";
	}
	
	$.ajax({
		url : rootPath + "/service/sfda/selectgdidsfdacount",
		type : 'post',
		async : false,
		data : {
			'gdid' : paramssss.wbid,
			"dalx" : daType
		},
		dataType : 'json',
		success : function(data) {
			count = data;
			if (count <= 0) {
				//这儿异议审批确定不了是不是同部门的人审批，所以审批人下拉框用的全院
				 if(sfdaTypeNameArr[1] == sfdaTypeName||sfdaTypeNameArr[4] == sfdaTypeName){
					if(sfdaTypeNameArr[1] == sfdaTypeName){
						if(zrzjsl <= 0){
							top.msgAlertInfo("当前无数据，无法发起异议!");
							return;
						}
					}else if(sfdaTypeNameArr[4] == sfdaTypeName){
						if(jsjdsl <= 0){
							top.msgAlertInfo("当前无数据，无法发起异议!");
							return;
						}
					}
					parent.ifeblick();
					$("#ryjn_sp_div").fadeIn("fast");
					//标题赋值
					$("#sfda_type_title").text(sfda_type_title);
					$("#sfda_type_span_name").text(sfdaTypeSpanName);
					if("block"==inputDisplay){
	//					$("#sfda_type_spr").css("display",inputDisplay);
						//装载审批人列表（方法引用于common.js）
						var input_id = "sfda_type_spr";  //
						var dwbm = null;
						var gh = null;
						if(top.currentUserInfo){
							dwbm = top.currentUserInfo.dwbm;
							gh = top.currentUserInfo.gh;
						}
						getAllRyOfDwBmTree(input_id,dwbm,gh,null,null,"2");
					}
				 }else{
					top.msgAlertInfo("请添加至少一项数据再发起审批!");
					return;
				 }
				
			}else{
				parent.ifeblick();
				$("#ryjn_sp_div").fadeIn("fast");
				//标题赋值
				$("#sfda_type_title").text(sfda_type_title);
				$("#sfda_type_span_name").text(sfdaTypeSpanName);
				if("block"==inputDisplay){
//					$("#sfda_type_spr").css("display",inputDisplay);
					//装载审批人列表（方法引用于common.js）
					var input_id = "sfda_type_spr";  //
					var dwbm = null;
					var gh = null;
					if(top.currentUserInfo){
						dwbm = top.currentUserInfo.dwbm;
						gh = top.currentUserInfo.gh;
					}
					getAllRyOfDwBmTree(input_id,dwbm,gh,null,null,"1");
				}
			}
		}
	});
}

function closeRyjnSpDiv(){
	parent.ifenone();
	$("#ryjn_sp_div").fadeOut("fast");
//	$(".sfda_type_spr_class").removeClass('class');
}

function startRyjnSp(){
	//调用发起荣誉技能审批方法
	sendAudit();
}

/**========================================发起荣誉技能、业务追究、其他情况的审批==================================*/
/**
//	var sfdaTypeNameArr = ["sfjn","sfzr","zycs","qt","jdqk"];  //对应司法档案的各个类型名称缩写
//	var sfdaTypeZwNameArr = ["荣誉技能审批","责任追究审批","接受监督情况审批","业务研修审批","其他情况审批"];
//	var sfda_splx = ["4","15","14","16","17"];  //司法档案的审批类型【对应节点流程表的审批类型】
 */
function sendAudit() {
//	console.log("被选中审批的id"+selectList);
	var sfda_type_name = checkStrReturnEmpty(currentListType);
	var kssj = checkStrReturnEmpty(getRequest().kssj);
	var jssj = checkStrReturnEmpty(getRequest().jssj);
	var nrName = "";
	var sfdaSpType = "";
	var sfdaTypeSprGh = "";
	var sfdaTypeSprBmbm = "";
	var sfdaTypeJdlx = "";
	var sfdaTypeLcjd = "";
	
	if(sfdaTypeNameArr[0]==sfda_type_name){
		nrName = sfdaTypeZwNameArr[0];
		sfdaSpType = sfda_splx[0];
		sfdaTypeJdlx = lcmbArray.jdlcList[0].jdlx;
		sfdaTypeLcjd = lcmbArray.jdlcList[0].lcjd;
		var sprObj = getCombotreeSprObj("sfda_type_spr");  //调用common.js
		if(!sprObj){
			return;
		}
		sfdaTypeSprGh = sprObj.sprgh;
		sfdaTypeSprBmbm = sprObj.sprbmbm;
	}else if(sfdaTypeNameArr[2] == sfda_type_name){
		nrName = sfdaTypeZwNameArr[3];
		sfdaSpType = sfda_splx[1];
		var sprObj = getCombotreeSprObj("sfda_type_spr");  //调用common.js
		if(!sprObj){
			return;
		}
		sfdaTypeSprGh = sprObj.sprgh;
		sfdaTypeSprBmbm = sprObj.sprbmbm;
		sfdaTypeJdlx = "1500";
		sfdaTypeLcjd = "150001";
	}else if(sfdaTypeNameArr[3] == sfda_type_name){
		nrName = sfdaTypeZwNameArr[4];
		sfdaSpType = sfda_splx[2];
		var sprObj = getCombotreeSprObj("sfda_type_spr");  //调用common.js
		if(!sprObj){
			return;
		}
		sfdaTypeSprGh = sprObj.sprgh;
		sfdaTypeSprBmbm = sprObj.sprbmbm;
		sfdaTypeJdlx = "1400";
		sfdaTypeLcjd = "140001";
	}else if(sfdaTypeNameArr[1] == sfda_type_name){  //责任追究【原司法责任】
		nrName = sfdaTypeZwNameArr[1];
		sfdaSpType = sfda_splx[3];
		var sprObj = getCombotreeSprObj("sfda_type_spr");  //调用common.js
		if(!sprObj){
			return;
		}
		sfdaTypeSprGh = sprObj.sprgh;
		sfdaTypeSprBmbm = sprObj.sprbmbm;
		sfdaTypeJdlx = "1600";
		sfdaTypeLcjd = "160001";
	}else if(sfdaTypeNameArr[4] == sfda_type_name){  //接受监督
		nrName = sfdaTypeZwNameArr[2];
		sfdaSpType = sfda_splx[4];
		var sprObj = getCombotreeSprObj("sfda_type_spr");  //调用common.js
		if(!sprObj){
			return;
		}
		sfdaTypeSprGh = sprObj.sprgh;
		sfdaTypeSprBmbm = sprObj.sprbmbm;
		sfdaTypeJdlx = "1700";
		sfdaTypeLcjd = "170001";
	}
	
	var fqclnr = top.currentUserInfo.mc+"-"+ nrName;//top.currentUserInfo.mc+""+kssj+"-"+jssj+ nrName;

	var data2 = {
		jdlx : sfdaTypeJdlx,
		lcjd : sfdaTypeLcjd,
		sprdwbm : paramssss.dwbm,
		sprbmbm : sfdaTypeSprBmbm,
		sprgh : sfdaTypeSprGh,
		spstid : paramssss.wbid,  //selectList
		splx : sfdaSpType,
		clnr : encodeURI(fqclnr)
	};
	
	top.msgProgressTip("正在发起审批……");

	$.ajax({
		url : rootPath + '/service/sp/selectNewSpslByLcid',
		type : 'post',
		dataType : 'json',
		async : 'false',
		data : {
			lcid : paramssss.lcid
		},
		success : function(data) {
			if(data.length>0){
				if (data[0].jdlx == lcmbArray.jdlcList[0].jdlx) {
				} else {
					top.msgProgressTip("该业务已经处理完成");
					// window.location.reload();
				}
			}
		}
	});

	$.post(rootPath + "/service/sp/audit", data2, function(data) {
		top.msgProgressTipClose();
		if (data.status == '1') {
			top.msgAlertInfo("发起审批成功!");
//			$('#button_ryjn').hide();
//			$('#button_recall').show();
			window.location.reload();
		} else {
			top.msgAlertInfo("发起审批失败!");
		}
		var ryjnhref = $("#ryjna0").attr("href");
		if ($("#ryjna0") && ryjnhref != undefined) {
			window.location = String(ryjnhref);
		}
	}, 'json');
}

//审批窗口
$("#button_ywyx").click(openSfdaTypeSpDiv);
$("#button_qtqk").click(openSfdaTypeSpDiv);
//异议窗口
$("#button_sfzr").click(openSfdaTypeSpDiv);
$("#button_jsjd").click(openSfdaTypeSpDiv);

/**
 * 关闭所有发起审批、撤回按钮的显示
 * @returns
 */
function closeAllSfdaTypeAuditButton(){
	$('#button_ryjn').hide();//发起审批按钮
	$('#button_recall').hide();//撤回申请按钮
	$("#button_sfzr").hide();
	$("#button_sfzr_recall").hide();
	$("#button_jsjd").hide();
	$("#button_jsjd_recall").hide();
	$("#button_ywyx").hide();
	$("#button_ywyx_recall").hide();
	$("#button_qtqk").hide();
	$("#button_qtqk_recall").hide();
}

function hiddenSfdaTypeButton(){
	$("#xz").hide();
	$("#sfzr_xz").hide();
	$("#jsjd_xz").hide();
	$("#ywyx_xz").hide();
	$("#qt_xz").hide();
	$('#button_ryjn').hide();//发起审批按钮
	$('#button_recall').hide();//撤回申请按钮
	$("#button_sfzr").hide();
	$("#button_sfzr_recall").hide();
	$("#button_jsjd").hide();
	$("#button_jsjd_recall").hide();
	$("#button_ywyx").hide();
	$("#button_ywyx_recall").hide();
	$("#button_qtqk").hide();
	$("#button_qtqk_recall").hide();
}

//审批撤回
$("#button_ywyx_recall").click(recallSfdaTypeAudit);
$("#button_qtqk_recall").click(recallSfdaTypeAudit);
//异议撤回
$("#button_sfzr_recall").click(recallSfdaTypeAudit);
$("#button_jsjd_recall").click(recallSfdaTypeAudit);

/**
 * 撤回审批
 * //var sfdaTypeNameArr = ["sfjn","sfzr","zycs","qt","jdqk"];  //对应司法档案的各个类型名称缩写
 * @returns
 */
function recallSfdaTypeAudit(){
	var sfdaTypeName = checkStrReturnEmpty(currentListType);
	var daSpType = lcmb[0].lclx;  //档案审批类型。4：荣誉技能【默认】，14：其他情况，15：业务研修 【跟随审批表的】
	if(sfdaTypeName){
		if(sfdaTypeNameArr[0]==sfdaTypeName){  
			daSpType = sfda_splx[0];
		}else if(sfdaTypeNameArr[2] == sfdaTypeName){ 
			daSpType = sfda_splx[1];
		}else if(sfdaTypeNameArr[3] == sfdaTypeName){ 
			daSpType = sfda_splx[2];
		}else if(sfdaTypeNameArr[1] == sfdaTypeName){ //司法责任/责任追究
			daSpType = sfda_splx[3];
		}else if(sfdaTypeNameArr[4] == sfdaTypeName){ //接受监督
			daSpType = sfda_splx[4];
		}
	}
	
	top.msgProgressTip("正在撤回……");
	$.ajax({
		url : rootPath + "/service/sp/recall",
		type : 'post',
		async : false,
		data : {
			spstid : paramssss.wbid,
			splx : daSpType
		},
		dataType : 'json',
		success : function(data1) {
			top.msgProgressTipClose();
			if (data1 == '1') {
				top.msgAlertInfo("成功撤回");
//				$('#button_recall').hide();
//				$('#button_ryjn').show();
				window.location.reload();
			} else {
				top.msgAlertInfo("撤回失败");
			}
			var ryjnhref = $("#ryjna0").attr("href");
			if ($("#ryjna0") && ryjnhref != undefined) {
				window.location = String(ryjnhref);
			}
		}
	});
}

// 全选
function allCheck(node1) {
	var node = $('input[name="subBox"]'); // document.getElementsByName("subBox");
	for (var i = 0; i< node.length; i++) {
		node[i].checked = node1.checked;
	}
    selectList = $("input[name='subBox']:checked").map(function () {
        return $(this).val();
    }).get().join(',');
	// alert(selectList);
}

// 单选
function chose(node) {
	var flag = true;// 用于遍历是否全部变量设置
	var allCheck = $('input[name="checkAll"]')[0];
	var node = $('input[name="subBox"]');
	for (var i = 0; i< node.length; i++) {
		if (node[i].checked == false) { // 只要有一个没选中，就退出遍历，标记为false
			flag = false;
			break;
		}
	}
	if (flag) {
		allCheck.checked = true;
	} else {
		allCheck.checked = false;
	}
    selectList = $("input[name='subBox']:checked").map(function () {
        return $(this).val();
    }).get().join(',');
//    alert(selectList);
}

function searchRecords() {
    parent.ifeblick();
    switch (currentListType) {
        case "sfjn": // 荣誉奖励
            // alert("类型：荣誉奖励" + "daid:" + gdid);
            serachByType(initdalx, gdid);
            break;
        case "sfzr":
            // alert("类型：责任追究" + "daid:" + gdid);
            serachByType(initdalx, gdid);
            break;
        case "zycs":
            // alert("类型：职业操守" + "daid:" + gdid);
            serachByType(initdalx, gdid);
            break;
        case "qt":
            // alert("类型：其他情况" + "daid:" + gdid);
            serachByType(initdalx, gdid);
            break;
        case "jdqk":
            // alert("类型：监督情况" + "daid:" + gdid);
            serachByType(initdalx, gdid);
            break;
        default:
            break;
    }
}

// 通过档案类型，档案id,时间查询各司法档案
function serachByType(type, id) {
    $('#archives_datagrid').datagrid('load',{//重新加载新增列表datagrid表格
        dalx: type,
        daid: id,
        kssj: $("#startDate").datebox("getText"),
        jssj: $("#endDate").datebox("getText")
    });
}

// 改用datagrid显示各项技能
function selectAllSkill (dalx,gdid) {
	var columns_array = [];
    
	if (dalx == '1') { // 荣誉奖励
        columns_array = [[
            {field: 'num',title:  '<b>序号</b>',align: 'center',checkbox: true },
            {field: 'rymc',title: '<b>荣誉名称</b>',width: 300,align: 'center',
                formatter: function (value, row, index) {
                    return "<span title='"+ value +"'>" + value + "</span>"
                }
			},
            {field: 'hjdj',title: '<b>获奖等级</b>',width: 150,align: 'center',
                formatter: function (value, row, index) {
            		var hjdjText = returnDataDictStr('hjdj', value);
                    return "<span title='"+ hjdjText +"'>" + hjdjText + "</span>"
                },
			},
            {field: 'hjsj',title: '<b>获奖时间</b>',width: 100,align: 'center',
                formatter: function (value, row, index) {
                    return "<span title='"+ value +"'>" + value + "</span>"
                }
			},
            {field: 'bjdw',title: '<b>颁奖单位</b>',width: 180,align: 'center',
                formatter: function (value, row, index) {
                    return "<span title='"+ value +"'>" + value + "</span>"
                }
			},
            {field: 'status',title : '<b>状态</b>',width : 80,align : 'center',
                formatter : function(value, row, index) {
                    if ("1" == value) {
                        return "<span title='已审核'>已审核</span>"; // 已审核的不会显示在新增列表中
                    } else if ("2" == value) {
                        return "<span title='未审核'>未审核</span>";
                    } else if ("3" == value) {
                        return "<span title='审核未通过'>审核未通过</span>";
                    } else if ("4" == value) {
                        return "<span title='审核中'>审核中</span>";
                    }
                }
            },
            {field: 'fjsl', title: '<b>附件数量</b>', width: 80, align: 'center',
                formatter: function (value, row, index) {
                    showFjCount(row.id);
                    isShowFj = true;
                    return fjsl ? "<a href='javascript:void(0)' onclick=\"showUploadFile('" + row.id + "','" + isShowFj +"')\">" + fjsl + "</a>" : 0;
                },
            },
            {field: 'cz', title: '<b>操作</b>', width: 180, align: 'center',
                formatter: function (value, row, index) {
            		var data = JSON.stringify(row);
                    if((isOneself() || isZzbRy()) && getRequest().sfgs == '2' && paramssss.sffc !="1" && paramssss.spym !="2"){//只要是自己的就能新增，修改
                        isShowFj = "false";//因为点击附件数量和点击上传按钮都是调用showUploadFile()方法，在点击附件数量时需要隐藏附件上传框，和删除按钮，所以需要此标识
                        return "<a class='table_czan_ys' style='padding: 0 6px;margin-right: 10px;' href='javascript:void(0)' onclick=\"showUploadFile('"+ row.id + "','" + isShowFj +"')\">上传</a>"
                             + "<a class='table_czan_ys' style='padding: 0 6px;margin-right: 10px;' href='javascript:void(0)' onclick='updateDaInfo("+ data + ")'>修改</a>"
                             + "<a class='table_czan_ys' style='padding: 0 6px;margin-right: 0px;' href='javascript:void(0)' onclick=\"deleteDaInfo('"+ currentListType +"','"+ row.id + "')\">删除</a></li>";

                    }else {
                        //设置表单不可修改
                        //荣誉名称
                        $("#sfjnrymc").textbox({
                            disabled: true
                        });

                        //获奖等级
                        $("#sfjnhjdj").combobox({
                            disabled: true
                        });
                        //获奖时间
                        $("#sfjnhjsj").textbox({
                            disabled: true
                        });
                        //颁奖单位
                        $("#sfjnbjdw").textbox({
                            disabled: true
                        });
                        //获奖事由
                        $("#sfjnhjsy").textbox({
                            disabled: true
                        });
                        //其他情况
                        $("#sfjnhjqtqk").textbox({
                            disabled: true
                        });
                        return "<a class='table_czan_ys' style='padding: 0 6px;' href='javascript:void(0)' onclick='updateDaInfo("+ data + ")'>查看</a></li>";
                    }
                },
            },
		]];
	} else if (dalx == '2') { // 责任追究
        columns_array = [[
            {field: 'num',title:  '<b>序号</b>',align: 'center',checkbox: true },
            {field: 'zrzl',title: '<b>责任种类</b>',width: 200,align: 'center',
                formatter: function (value, row, index) {
                    var zrzlText = returnDataDictStr('zrzl', value);
                    return "<span title='"+ zrzlText +"'>" + zrzlText + "</span>";
                },
			},
            {field: 'clbm',title: '<b>处理部门（单位）</b>',width: 150,align: 'center',
                formatter: function (value, row, index) {
            		var clbmText = returnBmmcByDwBm(dlr_dwbm, value);
                    return "<span title='"+ clbmText +"'>" + clbmText + "</span>";
                },
			},
            {field: 'clsj',title: '<b>处理时间</b>',width: 100,align: 'center',
                formatter: function (value, row, index) {
                    return "<span title='"+ value +"'>" + value + "</span>"
                }
			},
            {field: 'cljg',title: '<b>处理落实情况</b>',width: 150,align: 'center',
                formatter: function (value, row, index) {
            		var lsqkText = returnDataDictStr('lsqk', value);
                    return "<span title='"+ lsqkText +"'>" + lsqkText + "</span>"
                },
			},
            {field: 'status',title : '<b>状态</b>',width : 80,align : 'center',
                formatter : function(value, row, index) {
                    if ("1" == value) {
                        return "<span title='已审核'>已审核</span>"; // 已审核的不会显示在新增列表中
                    } else if ("2" == value) {
                        return "<span title='未审核'>未审核</span>";
                    } else if ("3" == value) {
                        return "<span title='审核未通过'>审核未通过</span>";
                    } else if ("4" == value) {
                        return "<span title='审核中'>审核中</span>";
                    }
                }
            },
            {field: 'fjsl', title: '<b>附件数量</b>', width: 80, align: 'center',
                formatter: function (value, row, index) {
                    showFjCount(row.id);
                    isShowFj = true;
                    return fjsl ? "<a href='javascript:void(0)' onclick=\"showUploadFile('" + row.id + "','" + isShowFj +"')\">" + fjsl + "</a>" : 0;
                },
            },
            {field: 'cz', title: '<b>操作</b>', width: 180, align: 'center',
                formatter: function (value, row, index) {
                    var data = JSON.stringify(row);
                    if (!isOneself() && getRequest().sfgs == '2' && (isld() == true || isAg() == true || isBmFzr() == true || isJcz() == true) && spzt != "4"&&paramssss.sffc!="1"&&paramssss.spym!="2") {
                        isShowFj = "false";//因为点击附件数量和点击上传按钮都是调用showUploadFile()方法，在点击附件数量时需要隐藏附件上传框，和删除按钮，所以需要此标识
                        return "<a class='table_czan_ys' style='padding: 0 6px;margin-right: 10px;' href='javascript:void(0)' onclick=\"showUploadFile('"+ row.id + "','" + isShowFj +"')\">上传</a>"
                             + "<a class='table_czan_ys' style='padding: 0 6px;margin-right: 10px;' href='javascript:void(0)' onclick='updateDaInfo("+ data + ")'>修改</a>"
                             + "<a class='table_czan_ys' style='padding: 0 6px;margin-right: 0px;' href='javascript:void(0)' onclick=\"deleteDaInfo('"+ currentListType +"','"+ row.id + "')\">删除</a></li>";

                    }else {
                        //设置表单不可修改

                        return "<a class='table_czan_ys' style='padding: 0 5px;' href='javascript:void(0)' onclick='updateDaInfo("+ data + ")'>查看</a></li>";
                    }
                },
            },
        ]];
	} else if (dalx == '3') { // 业务研修
        columns_array = [[
            {field: 'num',title:  '<b>序号</b>',align: 'center',checkbox: true},
            {field: 'xmmc',title: '<b>研修名称</b>',width: 150,align: 'center',
				formatter: function (value, row, index) {
					return "<span title='"+ value +"'>" + value + "</span>";
                }
			},
            {field: 'xmlx',title: '<b>研修类型</b>',width: 120,align: 'center',
                formatter: function (value, row, index) {
                   var yslxText = returnDataDictStr('yxlx', value);
                    return "<span title='"+ yslxText +"'>" + yslxText + "</span>"
                },
            },
            {field: 'kssj',title: '<b>开始时间</b>',width: 150,align: 'center',
                formatter: function (value, row, index) {
                    return "<span title='"+ value +"'>" + value + "</span>"
                }
			},
            {field: 'jssj',title: '<b>结束时间</b>',width: 150,align: 'center',
                formatter: function (value, row, index) {
                    return "<span title='"+ value +"'>" + value + "</span>"
                }
			},
            {field: 'ywdd',title: '<b>研修地点</b>',width: 130,align: 'center',
                formatter: function (value, row, index) {
                    return "<span title='"+ value +"'>" + value + "</span>"
                }
			},
            {field: 'zzbm',title: '<b>组织部门</b>',width: 130,align: 'center',
                formatter: function (value, row, index) {
                    return "<span title='"+ returnBmmcByDwBm(dlr_dwbm, value) +"'>" + returnBmmcByDwBm(dlr_dwbm, value) + "</span>"
                },
            },
			{field: 'spbm',title: '<b>审批部门</b>',width: 150,align: 'center',
                formatter: function (value, row, index) {
                    return "<span title='"+ returnBmmcByDwBm(dlr_dwbm, value) +"'>" + returnBmmcByDwBm(dlr_dwbm, value) + "</span>"
                },
            },
            {field: 'zysh',title: '<b>主要收获</b>',width: 130,align: 'center',
                formatter: function (value, row, index) {
                    return "<span title='"+ value +"'>" + value + "</span>"
                }
			},
            {field: 'yjcg',title: '<b>研究成果</b>',width: 130,align: 'center',
                formatter: function (value, row, index) {
                    return "<span title='"+ value +"'>" + value + "</span>"
                }
			},
            {field: 'status',title : '<b>状态</b>',width : 100,align : 'center',
                formatter : function(value, row, index) {
                    if ("1" == value) {
                        return "<span title='已审核'>已审核</span>"; // 已审核的不会显示在新增列表中
                    } else if ("2" == value) {
                        return "<span title='未审核'>未审核</span>";
                    } else if ("3" == value) {
                        return "<span title='审核未通过'>审核未通过</span>";
                    } else if ("4" == value) {
                        return "<span title='审核中'>审核中</span>";
                    }
                }
            },
            {field: 'fjsl', title: '<b>附件数量</b>', width: 100, align: 'center',
                formatter: function (value, row, index) {
                    showFjCount(row.id);
                    isShowFj = true;
                    return fjsl ? "<a href='javascript:void(0)' onclick=\"showUploadFile('" + row.id + "','" + isShowFj +"')\">" + fjsl + "</a>" : 0;
                },
            },
            {field: 'cz', title: '<b>操作</b>', width: 260, align: 'center',
                formatter: function (value, row, index) {
                    var data = JSON.stringify(row);
                    if((isOneself() || isZzbRy()) && getRequest().sfgs == '2' && paramssss.sffc !="1" && paramssss.spym !="2"){
                    	isShowFj = "false";//因为点击附件数量和点击上传按钮都是调用showUploadFile()方法，在点击附件数量时需要隐藏附件上传框，和删除按钮，所以需要此标识
                        return "<a class='table_czan_ys' style='padding: 0 5px;margin-right: 4px;' href='javascript:void(0)' onclick=\"showUploadFile('"+ row.id + "','" + isShowFj +"')\">上传</a>"
                            + "<a class='table_czan_ys' style='padding: 0 5px;margin-right: 4px;' href='javascript:void(0)' onclick='updateDaInfo("+ data + ")'>修改</a>"
                            + "<a class='table_czan_ys' style='padding: 0 5px;margin-right: 4px;' href='javascript:void(0)' onclick=\"deleteDaInfo('"+ currentListType +"','"+ row.id + "')\">删除</a></li>";

                    }else {
                        //设置表单不可修改
                        //研修名称
                        $("#zycsxmmc").textbox({
                            disabled: true
                        });
                        //研修类型
                        $("#zycsxmlx").combobox({
                            disabled: true
                        });
                        //业务开始时间
                        $("#zycskssj").textbox({
                            disabled: true
                        });
                        //业务结束时间
                        $("#zycsjssj").textbox({
                            disabled: true
                        });
                        //组织部门
                        $("#zycszzbm").combobox({
                            disabled: true
                        });
                        //审批部门
                        $("#zycsspbm").combobox({
                            disabled: true
                        });
                        //主要收获
                        $("#zycszysh").textbox({
                            disabled: true
                        });
                        //研修成果
                        $("#zycsyjcg").textbox({
                            disabled: true
                        });
                        //研修地点
                        $("#zycsywdd").textbox({
                            disabled: true
                        });
                        //主要内容
                        $("#zycszynr").textbox({
                            disabled: true
                        });
                        //其他情况
                        $("#zycsywqtqk").textbox({
                            disabled: true
                        });
                        return "<a class='table_czan_ys' style='padding: 0 6px;' href='javascript:void(0)' onclick='updateDaInfo("+ data + ")'>查看</a></li>";
                    }
                },
            },
        ]];
	} else if (dalx == '4') { //其他情况
        columns_array = [[
            {field: 'num',title:  '<b>序号</b>',align: 'center',checkbox: true},
            {field: 'lrbm',title: '<b>录入部门</b>',width: 150,align: 'center',
                formatter: function (value, row, index) {
                    var lrbmText = returnBmmcByDwBm(dlr_dwbm, value);
                    return "<span title='"+ lrbmText +"'>" + lrbmText + "</span>"
                },
            },
            {field: 'lrr',title: '<b>录入人</b>',width: 150,align: 'center',
                formatter: function (value, row, index) {
                    return "<span title='"+ value +"'>" + value + "</span>"
                },
			},
            {field: 'lrsj',title: '<b>录入时间</b>',width: 150,align: 'center',
                formatter: function (value, row, index) {
                    return "<span title='"+ value +"'>" + value + "</span>"
                },
			},
            {field: 'status',title : '<b>状态</b>',width : 80,align : 'center',
                formatter : function(value, row, index) {
                    if ("1" == value) {
                        return "<span title='已审核'>已审核</span>"; // 已审核的不会显示在新增列表中
                    } else if ("2" == value) {
                        return "<span title='未审核'>未审核</span>";
                    } else if ("3" == value) {
                        return "<span title='审核未通过'>审核未通过</span>";
                    } else if ("4" == value) {
                        return "<span title='审核中'>审核中</span>";
                    }
                }
            },
            {field: 'fjsl', title: '<b>附件数量</b>', width: 100, align: 'center',
                formatter: function (value, row, index) {
                    showFjCount(row.id);
                    isShowFj = true;
                    return fjsl ? "<a href='javascript:void(0)' onclick=\"showUploadFile('" + row.id + "','" + isShowFj +"')\">" + fjsl + "</a>" : 0;
                },
            },
            {field: 'cz', title: '<b>操作</b>', width: 200, align: 'center',
                formatter: function (value, row, index) {
                    var data = JSON.stringify(row);
                    if(isOneself() && getRequest().sfgs == '2' && paramssss.sffc !="1" && paramssss.spym !="2"){
                        isShowFj = "false";//因为点击附件数量和点击上传按钮都是调用showUploadFile()方法，在点击附件数量时需要隐藏附件上传框，和删除按钮，所以需要此标识
                        return "<a class='table_czan_ys' style='padding: 0 6px;margin-right: 10px;' href='javascript:void(0)' onclick=\"showUploadFile('"+ row.id + "','" + isShowFj +"')\">上传</a>"
                            + "<a class='table_czan_ys' style='padding: 0 6px;margin-right: 10px;' href='javascript:void(0)' onclick='updateDaInfo("+ data + ")'>修改</a>"
                            + "<a class='table_czan_ys' style='padding: 0 6px;margin-right: 10px;' href='javascript:void(0)' onclick=\"deleteDaInfo('"+ currentListType +"','"+ row.id + "')\">删除</a></li>";

                    }else {
                        //设置表单不可修改
						//录入部门
                        $("#qtlrbm").combobox({
                            disabled : true
                        })
                        //录入人
                        $("#qtlrr").textbox({
                            disabled : true
                        })
                        //录入时间
                        $("#qtlrsj").textbox({
                            disabled : true
                        })
                        //录入内容
                        $("#qtlrnr").textbox({
                            disabled : true
                        })
                        return "<a class='table_czan_ys' style='padding: 0 6px;' href='javascript:void(0)' onclick='updateDaInfo("+ data + ")'>查看</a></li>";
                    }
                },
            },
        ]];
	} else if (dalx == '5') { // 接受监督
        columns_array = [[
            {field: 'num',title:  '<b>序号</b>',align: 'center',checkbox: true},
            {field: 'jdzl',title: '<b>监督种类</b>',width: 150,align: 'center',
                formatter: function (value, row, index) {
                    var jdzlText = returnDataDictStr('jdzl', value);
                    return "<span title='"+ jdzlText +"'>" + jdzlText + "</span>";
                },
            },
            {field: 'jdbm',title: '<b>监督部门</b>',width: 150,align: 'center',
                formatter: function (value, row, index) {
                    var jdbmText = returnBmmcByDwBm(dlr_dwbm, value);
                    return "<span title='"+ jdbmText +"'>" + jdbmText + "</span>";
                },
            },
            {field: 'jdsj',title: '<b>监督时间</b>',width: 150,align: 'center',
                formatter: function (value, row, index) {
                    return "<span title='"+ value +"'>" + value + "</span>";
                },
			},
            {field: 'jdcl',title: '<b>监督处理</b>',width: 150,align: 'center',
                formatter: function (value, row, index) {
                    var jdclText = returnDataDictStr('jdcl', value);
                    return "<span title='"+ jdclText +"'>" + jdclText + "</span>";
                },
			},
            {field: 'zgqk',title: '<b>落实整改情况</b>',width: 150,align: 'center',
                formatter: function (value, row, index) {
                    var zgqkText = returnDataDictStr('zgqk', value);
                    return "<span title='"+ zgqkText +"'>" + zgqkText + "</span>";
                },
			},
            {field: 'status',title : '<b>状态</b>',width : 80,align : 'center',
                formatter : function(value, row, index) {
                    if ("1" == value) {
                        return "<span title='已审核'>已审核</span>"; // 已审核的不会显示在新增列表中
                    } else if ("2" == value) {
                        return "<span title='未审核'>未审核</span>";
                    } else if ("3" == value) {
                        return "<span title='审核未通过'>审核未通过</span>";
                    } else if ("4" == value) {
                        return "<span title='审核中'>审核中</span>";
                    }
                }
            },
            {field: 'fjsl', title: '<b>附件数量</b>', width: 100, align: 'center',
                formatter: function (value, row, index) {
                    showFjCount(row.id);
                    isShowFj = true;
                    return fjsl ? "<a href='javascript:void(0)' onclick=\"showUploadFile('" + row.id + "','" + isShowFj +"')\">" + fjsl + "</a>" : 0;
                },
            },
            {field: 'cz', title: '<b>操作</b>', width: 200, align: 'center',
                formatter: function (value, row, index) {
                    var data = JSON.stringify(row);
                    if (!isOneself() && getRequest().sfgs == '2' && (isld() == true || isAg() == true || isBmFzr() == true || isJcz() == true) && spzt != "4"&&paramssss.sffc!="1"&&paramssss.spym!="2") {
                        isShowFj = "false";//因为点击附件数量和点击上传按钮都是调用showUploadFile()方法，在点击附件数量时需要隐藏附件上传框，和删除按钮，所以需要此标识
                        return "<a class='table_czan_ys' style='padding: 0 6px;margin-right: 10px;' href='javascript:void(0)' onclick=\"showUploadFile('"+ row.id + "','" + isShowFj +"')\">上传</a>"
                            + "<a class='table_czan_ys' style='padding: 0 6px;margin-right: 10px;' href='javascript:void(0)' onclick='updateDaInfo("+ data + ")'>修改</a>"
                            + "<a class='table_czan_ys' style='padding: 0 6px;margin-right: 10px;' href='javascript:void(0)' onclick=\"deleteDaInfo('"+ currentListType +"','"+ row.id + "')\">删除</a></li>";

                    }else {
                        //设置表单不可修改
                        //监督种类
                        $("#jdqkjdzl").combobox({
                            disabled: true
                        });
                        //监督部门
                        $("#jdqkjdbm").combobox({
                            disabled: true
                        });
                        //接受监督处理
                        $("#jdqkjdcl").combobox({
                            disabled: true
                        });
                        //整改情况
                        $("#jdqkzgqk").combobox({
                            disabled: true
                        });
                        //监督时间
                        $("#jdqkjdsj").textbox({
                            disabled: true
                        });
                        //监督事由
                        $("#jdqkjdsy").textbox({
                            disabled: true
                        });
                        //监督其他情况
                        $("#jdqkjdqtqk").textbox({
                            disabled: true
                        });
                        return "<a class='table_czan_ys' style='padding: 0 6px;' href='javascript:void(0)' onclick='updateDaInfo("+ data + ")'>查看</a></li>";
                    }
                },
            },
        ]];
	}

    $("#archives_datagrid").datagrid({

        checkbox: true,
        striped: true,//是否显示斑马线效果。
        loadMsg: '正在处理，请稍后。。。',
        rownumbers : true,//如果为true，则显示一个行号列。
        pagination: true,//在底部显示分页条
        pageNumber : 1,//在设置分页属性的时候初始化页码。
        pageSize : 10,//每页显示多少条，如果有pageList数组，在pageList中必须要有值与之对应
        pageList : [5,10,15,20],//每页显示的条数可选
        fitColumns : true,//真正的自动展开/收缩列的大小，以适应网格的宽度，防止水平滚动。列比较少的时候最好为true
        scrollbarSize: 0,
        border : true,//定义是否显示面板边框。
        selectOnCheck : true,
        checkOnSelect : true,
        ctrlSelect: true,
        nowrap: true, //如果为true，则在同一行中显示数据。设置为true可以提高加载性能。
        method: 'get',
        url: rootPath + '/service/sfdaRyjn/serachByType',
        queryParams: {
            dalx: dalx,
            daid: gdid,
            kssj: $("#startDate").datebox("getText"),
			jssj: $("#endDate").datebox("getText")
        },
        columns: columns_array,
    });
}