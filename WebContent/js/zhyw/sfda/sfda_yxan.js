var paramsy = null;
var kssj = null;
var jssj = null;
var lcmb = [// 档案流程模板数组【对应数据库表YX_SFDA_JDLC中lclx = '2'】
    {
        id : "111",
        jdlx : "100",
        lcjd : "10001",
        xyjd : "101",
        xycljs : "SP",
        xyclbm : "",
        xycldw : "",
        jdzt : "0",
        sm : "档案-个人信息审批开始",
        xycldwlx : "2",
        lclx : "2",
        ztbg : "0"
    } ];

$(function(){
    paramsy = getRequest();
    paramsy.gdid = paramsy.wbid;
    paramsy.dwbm = paramsy.ssrdwbm;
    if(paramsy.sffc!=undefined&&paramsy.sffc!=null&&paramsy.sffc!="")
    {
        paramsy.sffc=paramsy.sffc.substring(0,1);
    }
    var gdid = paramsy.gdid;
    $.ajax({
        url : rootPath + "/service/sfdacj/dagz",
        type : 'post',
        async : false,
        data : {
            gdid : paramsy.gdid
        },
        dataType : 'json',
        success : function(data) {
            kssj = data[0].kssj;
            jssj = data[0].jssj;
            paramsy.sffc=data[0].sffc;
            $("#fcsmt").text(data[0].fcly);
        }
    });
//	if (paramsy.splx == 4) {
//		ifysfjnsetI = window.setInterval(function() {
//			ifysfjn()
//		}, 100);
//	}

    try {
        // 审核窗口的树形列表展示人员
        /*$.getJSON(rootPath + '/service/tree/zzjgtree', function(res) {
            var result = [];
            result.push(res);
            $(document).ready(function() {
                $.fn.zTree.init($("#treeFcDemo"), fcsetting, result);
            });
        });*/
        // 档案发起审批窗口的树形列表展示人员------------------->已经修改为后台组装为combotree。详情见common.js中方法：getAllRyOfDwBmTree
        /*$.getJSON(rootPath + '/service/tree/headertree', function(res) {
            var result = [];
            result.push(res);
            $(document).ready(function() {
                $.fn.zTree.init($("#treeDaDemo"), dasetting, result);
            });
        });*/
    } catch (e) {
        // TODO: handle exception
    }
    showfcsj();// 展示封存时间
    sfkysc();// 判断档案是否可以删除
    dagzfcsfxs();// 司法档案封存是否显示
    fcrecallsfxs()// 判断撤回封存是否显示
    dagzfqspsfxs();// 司法档案发起审批是否显示
    dagzrecallsfxs();// 撤回档案审批是否显示

});

//点击返回按钮事件
function clickBack() {
    goBackPage();
}

//展示封存信息
function showfcsj() {
    $.ajax({
        url : rootPath + "/service/dagz/dagzdata",
        type : 'post',
        async : false,
        data : {
            dagzid : paramsy.gdid
        },
        dataType : 'json',
        success : function(data) {
            if (data != null && data[0].sffc == "1") {
                fcsjl = data[0].fcsj;
                if (fcsjl == undefined || fcsjl == null)
                    fcsjl = "";
                $("#fcsj").html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;封存时间：" + fcsjl);
                $("#fcsj").show();
                $("#button_fcsm").show();
            }
        }
    });
}
//function ifysfjn()// tree中是否有荣誉技能
//{
//	var ryjnhref = $("#ryjna0").attr("href");
//	if ($("#ryjna0") && ryjnhref != undefined) {
//		window.location = String(ryjnhref);
//		window.clearInterval(ifysfjnsetI);// 终止interval
//	}
//}
function sfkysc() { // 判断档案是否可以删除
    $.ajax({
        url : rootPath + "/service/dagz/sfkyscda",
        type : 'post',
        async : false,
        data : {
            wbid : paramsy.gdid
        },
        dataType : 'json',
        success : function(data) {
            if (data == true && top.currentUserInfo.dwbm == paramsy.dwbm
                && top.currentUserInfo.gh == paramsy.ssr
                && paramsy.sffc != "1"
                && (paramsy.spym != "1" || paramsy.spym == undefined))
                $('#button_sc').show();
            else
                $('#button_sc').hide();
        },
        error : function(data) {
            top.msgProgressTipClose();
            top.msgAlertError("操作失败!");
        }
    });
}

function deletedagz()// 档案归总删除
{
    $.messager.confirm('删除提示','删除后该档案记录将不再存在，您确认想要删除记录吗？',
        function(r) {
            if (r) {
                $.ajax({
                    url : rootPath + "/service/dagz/deletedagz",
                    type : 'post',
                    async : false,
                    data : {
                        wbid : paramsy.gdid,
                        dwbm : paramsy.dwbm,
                        gh : paramsy.ssr,
                        sffc : paramsy.sffc,
                        grjbxxId : $("#grjbxxId").val()
                    },
                    dataType : 'json',
                    success : function(data) {
                        if (data == 0) {
                            window.location.href = "sfda_cx.html?dwbm="+ top.currentUserInfo.dwbm+ "&gh="+ top.currentUserInfo.gh;
                        } else {
                            var sgaid = null;
                            $.ajax({
                                url : rootPath + "/service/syCount/getGdid",
                                type : 'post',
                                async : false,
                                data : {
                                    sffc : paramsy.sffc
                                },
                                success : function(data) {
                                    sgdid = data;
                                    window.location.href = rootPath + "/view/sfda/sfda_grjbxx.html?"
                                        + "ssr="+ paramsy.ssr
                                        + "&ssrdwbm="+ paramsy.dwbm
                                        + "&wbid="+ sgdid;
                                }
                            });
                        }
                    },
                    error : function(data) {
                        top.msgProgressTipClose();
                        top.msgAlertError("操作失败!");
                    }
                });
            }
        });
}
// 撤回封存申请
function recallfcsq() {
    top.msgProgressTip("撤回中……");
    $.ajax({
        url : rootPath + "/service/sp/recall",
        type : 'post',
        async : false,
        data : {
            spstid : paramsy.gdid,
            splx : "7"
        },
        dataType : 'json',
        success : function(data1) {
            if (data1 == '1') {
                $.messager.alert("提示", "成功撤回", "info");
                top.msgProgressTipClose();
                $('#button_recall_fc').hide();
                window.location.reload();
            } else {
                $.messager.alert("提示", "撤回失败", "info");
                top.msgProgressTipClose();
            }
        }
    });
}
// 撤回档案-个人信息审批申请
function recalldasq() {
    top.msgProgressTip("撤回中……");
    $.ajax({
        url : rootPath + "/service/sp/recall",
        type : 'post',
        async : false,
        data : {
            spstid : paramsy.gdid,
            splx : lcmb[0].lclx
        },
        dataType : 'json',
        success : function(data1) {
            if (data1 == '1') {
                $.messager.alert("提示", "成功撤回", "info");
                top.msgProgressTipClose();
                window.location.reload();
            } else {
                $.messager.alert("提示", "撤回失败", "info");
                top.msgProgressTipClose();
            }
        }
    });
}
function dagzfcsfxs() {// 司法档案封存是否显示
    var gdid = paramsy.gdid;
    $.ajax({
        url : rootPath + "/service/jcgSfdaCx/dasfsp",
        type : 'post',
        async : false,
        data : {
            'gdid' : gdid
        },
        dataType : 'json',
        success : function(data) {
            if (data.y == true && isag() == true && paramsy.sffc != "1"
                && paramsy.spym != "1" && paramsy.spym != "2") {
                $("#button_fc").show();
            } else {
                $("#button_fc").hide();
            }
        }
    });
}
function fcrecallsfxs() {// 撤回封存是否显示
    $.ajax({
        url : rootPath + "/service/dagz/showSplcsl",
        type : 'post',
        async : false,
        data : {
            spstid : paramsy.gdid,
            splx : "7"
        },
        dataType : 'json',
        success : function(data) {
            $
                .ajax({
                    url : rootPath + "/service/sp/getjdlx",
                    type : 'post',
                    async : false,
                    data : {
                        spstid : paramsy.gdid,
                        splx : "7"
                    },
                    dataType : 'json',
                    success : function(data1) {

                        if (!data1|| data1 == '0'|| top.currentUserInfo.gh != data[data.length - 1].spr
                            || paramsy.spym == "1"|| paramsy.spym == "2") {
                            $('#button_recall_fc').hide();
                        } else {
                            $('#button_recall_fc').show();
                        }
                    }
                });
        }
    });
}

//司法档案-个人信息发起审批是否显示
function dagzfqspsfxs() {
    // 获取最新审批实例信息
//	var grxx_id = checkStrReturnEmpty($("#grjbxxId").val()); //引用于common.js中的方法
    $.ajax({
        url : rootPath + "/service/sp/selectNewSpsl",
        type : 'post',
        async : false,
        data : {
            "spdw" : paramsy.dwbm,
            "spbm" : "",
            "spr" : paramsy.ssr,
            "spstid" : paramsy.gdid,  //grxx_id,
            "splx" : lcmb[0].lclx
        },
        dataType : 'json',
        success : function(data) {
            if (data.length == 0 || data[0].spzt == '5') {
                $('#button_fqsp').show();
            } else {
                $('#button_fqsp').hide();
                //隐藏编辑按钮
//				hideedit();
                $(".lxxx_btn").hide();
                $(".liczs").hide();
                $(".lxxx_uls_cz").hide();
                lcjd = data[0].lcjd;
            }
            // 如果当前登录人不是档案所属人，则隐藏发起审批按钮
            if (top.currentUserInfo.gh != paramsy.ssr || paramsy.sffc == "1"
                || paramsy.spym == "1" || paramsy.spym == "2") {
                $('#button_fqsp').hide();
                $('#button_recall').hide();
                //隐藏编辑按钮
//				hideedit();
//				$(".lxxx_btn").hide();
//				$(".liczs").hide();
//				$(".lxxx_uls_cz").hide();
            }
        }
    });
}

//撤回档案审批是否显示
function dagzrecallsfxs() {
//	var grxx_id = checkStrReturnEmpty($("#grjbxxId").val()); //引用于common.js中的方法
    $.ajax({
        url : rootPath + "/service/sp/getjdlx",
        type : 'post',
        async : false,
        data : {
            spstid : paramsy.gdid, //grxx_id,
            splx : lcmb[0].lclx
        },
        dataType : 'json',
        success : function(data1) {
            if ( !data1 || data1 == '0'
                || paramsy.spym == "1" || paramsy.spym == "2") {
                $('#button_recall').hide();


            } else {
                $('#button_recall').show();
            }
            // 如果当前登录人不是档案所属人，则隐藏发起审批按钮
            if (top.currentUserInfo.gh != paramsy.ssr || paramsy.sffc == "1"
                || paramsy.spym == "1" || paramsy.spym == "2") {
                $('#button_fqsp').hide();
                $('#button_recall').hide();
                //隐藏编辑按钮
//				hideedit();
//				$(".lxxx_btn").hide();
//				$(".liczs").hide();
//				$(".lxxx_uls_cz").hide();
            }
        }
    });
}
function isag()// 判断是否是案管
{
    var dlxx = top.currentUserInfo;// 登录信息
    for (var i = 0; i < dlxx.ryjs.length; i++) {
        var ryjscopy = dlxx.ryjs[i];
        if (ryjscopy == 7) {
            return true;
        }
    }
    return false;
}
// 显示填写封存信息对话框
function showFcDetail() {
    parent.ifeblick();
    $('#fctext').val('');
    $("#fcqdButton1").off('click');
    $("#fcqdButton1").on('click', fcsp);
    $("#fcinfo").fadeIn("fast");

    //装载审批人列表（方法引用于common.js）
    var input_id = "xzfcspr";  //
    var dwbm = null;
    var gh = null;
    if(top.currentUserInfo){
        dwbm = top.currentUserInfo.dwbm;
        gh = top.currentUserInfo.gh;
    }
    getAllRyOfDwBmTree(input_id,dwbm,gh,null,null,"1");

}


function closeFcDetail() {
    parent.ifenone();
    $("#fcinfo").fadeOut("fast");
}
// 显示填写档案发起审批信息对话框
function showDaDetail() // 显示档案审批窗口
{
    //个人基本信息的id,没有这个不能发起审批
    var hasid = checkStrReturnEmpty($("#grjbxxId").val()); //引用于common.js中的方法
    if(hasid){
        if(hasid.length > 0){
            parent.ifeblick();
            $("#shDaDetail").fadeIn("fast");

            //装载审批人员树（方法引用于common.js）
            var input_id = "xzdaspr";  //
            var dwbm = null;
            var gh = null;
            if(top.currentUserInfo){
                dwbm = top.currentUserInfo.dwbm;
                gh = top.currentUserInfo.gh;
            }
            getAllRyOfDwBmTree(input_id,dwbm,gh,null,null,"1");

        }
    }else{
        top.msgAlertError("请先完善个人信息后,再发起审批!");
    }
}
function closeDaDetail() { // 关闭档案审批窗口
    parent.ifenone();
    $("#shDaDetail").fadeOut("fast");
}
// 发起封存审批
function fcsp() {
    //审批人信息
    var sprObj = getCombotreeSprObj("xzfcspr");  //调用common.js
    if(!sprObj){
        return;
    }

//	var sprgh = fcztreedata.gh;
//	var selectNode = $.fn.zTree.getZTreeObj("treeFcDemo").getSelectedNodes();
//	var pNode = selectNode[0].getParentNode().getParentNode();

    var spyj = $("#fctext").val();
    var lx = "7";
    top.msgProgressTip("正在处理……");
    $.ajax({
        url : rootPath + "/service/jcgSfdaCx/dasfsp",
        type : 'post',
        async : false,
        data : {
            'gdid' : paramsy.gdid
        },
        dataType : 'json',
        success : function(da) {
            if (da.dsp == "1") {
                top.msgAlertInfo("该档案有其他审批操作，不能封存!");
                closeFcDetail();
            } else {

                $.ajax({
                    url : rootPath + "/service/sp/audit",
                    type : 'post',
                    async : false,
                    data : {
                        jdlx : '801',
                        lcjd : '80101',
                        sprdwbm : sprObj.sprdwbm,
                        sprbmbm : sprObj.sprbmbm,
                        sprgh : sprObj.sprgh,
                        spstid : paramsy.gdid,
                        spyj : spyj,
                        clnr : encodeURI(dassrmc + zdydateFormat(kssj) + "-" + zdydateFormat(jssj) + '的档案封存审批')
                    },
                    dataType : 'json',
                    success : function(resultMap) {
                        var status = resultMap.status;
                        top.msgProgressTipClose();
                        if (status == 1) {
                            top.msgAlertInfo("发起审批成功!");
                            // $.messager.alert("提示", "发起审批成功!", "info");
                            closeFcDetail();
                            $('#button_fc').hide();
                            window.location.reload();
                        } else if (status == 0) {
                            top.msgAlertError("发起审批失败!");
                            // $.messager.alert("提示", "发起审批失败!", "error");
                            closeFcDetail();
                            window.location.reload();
                        }
                    },
                    error : function(data) {
                        top.msgProgressTipClose();
                        top.msgAlertError("操作失败!");
                        // $.messager.alert("提示", "操作失败!", "error");
                    }
                });
            }
        }
    });
    top.msgProgressTipClose();
}

// 档案发起审批
function addspyj(type) {
    //准备审批参数
//	var grxx_id = checkStrReturnEmpty($("#grjbxxId").val()); //引用于common.js中的方法
//	if(!grxx_id){
//		top.msgAlertError("请先完善个人信息后,再发起审批!");
//	}
    var sprInfo = getCombotreeSprObj("xzdaspr");  //调用common.js
    if(!sprInfo){
        return;
    }
    var dbyw_title_params = paramsy;
    var daSsrObj = grjbxxObj;

    var spyj = "";
    var lx = $("input[name='sp_lx']").val();
    var type = type;
    var isStart = "1";
    top.msgProgressTip("正在处理……");
    $.ajax({
        url : rootPath + "/service/sp/audit",
        type : 'post',
        async : false,
        data : {
            jdlx : lcmb[0].jdlx,
            lcjd : lcmb[0].lcjd,
            sprdwbm : sprInfo.sprdwbm,
            sprbmbm : sprInfo.sprbmbm,
            sprgh : sprInfo.sprgh,
            spstid : paramsy.gdid,
            spyj : spyj,
            clnr : encodeURI(daSsrObj.mc+dbyw_title_params.tjnf+"年的司法档案-个人信息")
        },
        dataType : 'json',
        success : function(resultMap) {
            var status = resultMap.status;
            top.msgProgressTipClose();
            if (status == 1) {
                top.msgAlertInfo("发起档案审批成功!");
                // $.messager.alert("提示", "发起审批成功!", "info");
                closeDaDetail();
                window.location.reload();

                //消息推送
                // sendMessage(top.currentUserInfo.mc+"的司法档案审批", top.currentUserInfo.mc+"发起司法档案审批", "/index.html", "",top.currentUserInfo.dwbm + "," + sprgh，true);

            } else if (status == 0) {
                top.msgAlertError("发起档案审批失败!");
                // $.messager.alert("提示", "发起审批失败!", "error");
                closeDaDetail();
                window.location.reload();
            }
        },
        error : function(data) {
            top.msgProgressTipClose();
            top.msgAlertError("操作失败!");
            // $.messager.alert("提示", "操作失败!", "error");
        }
    });

    //走工作流  【时间问题，未修改为走工作流】
    /**
     * 1、准备参数
     * 2、调用工作流的接口
     */

    /*	var selectTree = $("#sfda_xzspr").combotree("tree");
        var ryNode = selectTree.tree("getSelected");
        var dwbmRoot = selectTree.tree("getRoot");
        var jsNode = selectTree.tree("getParent", ryNode.target);
        var bmNode = selectTree.tree("getParent", jsNode.target);
        var spr_dwbm = checkStrReturnEmpty(dwbmRoot.id);
        var spr_bmbm = checkStrReturnEmpty(bmNode.id);
        var spr_gh = checkStrReturnEmpty(ryNode.id);

        $("input[name='spr_dwbm']").val(spr_dwbm);
        $("input[name='spr_bmbm']").val(spr_bmbm);
        $("input[name='spr_gh']").val(spr_gh);
        $("input[name='sp_wbid']").val(paramsy.wbid);

        //将表单数据提交
        var sfda_sp_data = form2Json($("#sfda_splc_form"));
        if(sfda_sp_data){
            top.msgProgressTip("正在处理……");
            $.ajax({
                type : 'post',
                data : sfda_sp_data,
                dataType : 'json',
                url : rootPath + '/service/createAudit/start',
                success : function(data) {
                    top.msgProgressTipClose();
                    top.msgAlertInfo("司法档案审批发起成功!");
                    closeDaDetail();
                    window.location.reload();
                }
            });
        }*/

}
// fcztree
/*var isFcShowMenu = false;
var fcztreedata = {
	gh : "",
	name : ""
}
var fcsetting = {
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
		beforeClick : beforeFcClick,
		onClick : onFcClick
	}
};

function beforeFcClick(treeId, treeNode) {
	var check = (treeNode && treeNode.valueSelect);
	if (check)
		hideFcMenu();
	return check;
}

function onFcClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeFcDemo"), nodes = zTree
			.getSelectedNodes();
	var v = "";
	var name = nodes[0].text;
	var gh = nodes[0].id;
	fcztreedata.name = name;
	fcztreedata.gh = gh;
	v = name;
	var xzsprObj = $("#xzfcspr");
	xzsprObj.attr("value", v);
}

function showFcMenu() {
	if (isFcShowMenu == false) {
		isFcShowMenu = true;
		var xzsprObj = $("#xzfcspr");
		var xzsprOffset = $("#xzfcspr").offset();
		$("#menuFcContent").fadeIn("fast");

		$("body").bind("mousedown", onFcBodyDown);
	} else
		hideFcMenu();

}
function hideFcMenu() {
	isFcShowMenu = false;
	$("#menuFcContent").fadeOut("fast");
	$("body").unbind("mousedown", onFcBodyDown);
}
function onFcBodyDown(event) {
	if (!(event.target.id == "menuFcBtn" || event.target.id == "menuFcContent" || $(
			event.target).parents("#menuFcContent").length > 0)) {
		hideFcMenu();
	}
} */// ztree
/*var isFcShowMenu = false;
var fcztreedata = {
	gh : "",
	name : ""
}
var fcsetting = {
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
		beforeClick : beforeFcClick,
		onClick : onFcClick
	}
};

function beforeFcClick(treeId, treeNode) {
	var check = (treeNode && treeNode.valueSelect);
	if (check)
		hideFcMenu();
	return check;
}

function onFcClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeFcDemo"), nodes = zTree
			.getSelectedNodes();
	var v = "";
	var name = nodes[0].text;
	var gh = nodes[0].id;
	fcztreedata.name = name;
	fcztreedata.gh = gh;
	v = name;
	var xzsprObj = $("#xzfcspr");
	xzsprObj.attr("value", v);
}

function showFcMenu() {
	if (isFcShowMenu == false) {
		isFcShowMenu = true;
		var xzsprObj = $("#xzfcspr");
		var xzsprOffset = $("#xzfcspr").offset();
		$("#menuFcContent").fadeIn("fast");

		$("body").bind("mousedown", onFcBodyDown);
	} else
		hideFcMenu();

}
function hideFcMenu() {
	isFcShowMenu = false;
	$("#menuFcContent").fadeOut("fast");
	$("body").unbind("mousedown", onFcBodyDown);
}
function onFcBodyDown(event) {
	if (!(event.target.id == "menuFcBtn" || event.target.id == "menuFcContent" || $(
			event.target).parents("#menuFcContent").length > 0)) {
		hideFcMenu();
	}
}*/
// daztree
/*var isDaShowMenu = false;
var daztreedata = {
	gh : "",
	name : ""
}
var dasetting = {
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
		beforeClick : beforeDaClick,
		onClick : onDaClick
	}
};

function beforeDaClick(treeId, treeNode) {
	var check = (treeNode && treeNode.valueSelect);
	if (check)
		hideDaMenu();
	return check;
}

function onDaClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDaDemo"), nodes = zTree
			.getSelectedNodes();
	var v = "";
	var name = nodes[0].text;
	var gh = nodes[0].id;
	daztreedata.name = name;
	daztreedata.gh = gh;
	v = name;
	var xzsprObj = $("#xzdaspr");
	xzsprObj.attr("value", v);
}

function showDaMenu() {
	if (isDaShowMenu == false) {
		isDaShowMenu = true;
		var xzsprObj = $("#xzdaspr");
		var xzsprOffset = $("#xzdaspr").offset();
		$("#menuDaContent").fadeIn("fast");

		$("body").bind("mousedown", onDaBodyDown);
	} else
		hideDaMenu();

}
function hideDaMenu() {
	isDaShowMenu = false;
	$("#menuDaContent").fadeOut("fast");
	$("body").unbind("mousedown", onDaBodyDown);
}
function onDaBodyDown(event) {
	if (!(event.target.id == "menuDaBtn" || event.target.id == "menuDaContent" || $(
			event.target).parents("#menuDaContent").length > 0)) {
		hideDaMenu();
	}
} // daztree
*/