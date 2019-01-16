var gdid=null;
var sfgs = null;
var sffc=null;
var daSpzt = null;
var kssj=null;
var jssj=null;
var ssrmc=null;
var SfdaHc=null;     //档案核查审批状态
var daObj = {};  //档案对象



$(function() {
    //只能选择当前日期之后的时间
    $("#gsEndDate").datebox('calendar').calendar({
        validator:function(date){
            if(date>new Date()){
                return true;
            }else{
                return false;
            }
        }
    });

    gsParams = getRequest();
    gdid = gsParams.wbid;
    var gsSpym=gsParams.spym;

    var gsSpzt=null;  //公示审批状态
    var qxGsSpzt=null;  //取消公示审批状态
    var DafcSpzt=null;   //档案封存审批状态

    var dqdadwbm=null; //当前档案单位编码
    var dqdagh=null;   //当前档案工号

    //获得档案的信息
    $.ajax({
        url : rootPath + "/service/sfdacj/dagz",
        type : 'post',
        async : false,
        data : {
            "gdid" : gdid,
        },
        dataType : 'json',
        success : function(data) {
            if (data.length > 0) {
                sfgs = data[0].sfgs;
                sffc = data[0].sffc;
                kssj=data[0].kssj;
                jssj=data[0].jssj;
                dqdadwbm=data[0].ssrdwbm;
                dqdagh=data[0].ssr;
                daObj = data[0];
            }
        }
    });
    //获得档案所属人名称
    $.ajax({
        url : rootPath + "/service/xtgl/getmc",
        type : 'post',
        async : false,
        data : {
            "ssrdwbm" :dqdadwbm ,
            "ssrgh" : dqdagh
        },
        dataType : 'json',
        success : function(data) {
            ssrmc=data.mc;
        }
    });

    //获取档案、公示、取消公示的审批状态
    $.ajax({
        url : rootPath + "/service/sfdacj/getAllGsSpzt",
        type : 'post',
        async : false,
        data : {
            "spstid" : gdid
        },
        dataType : 'json',
        success : function(data) {

            if (typeof(data.Sfda)!='undefined'&&data.Sfda!=null&&data.Sfda!=""){
                daSpzt=data.Sfda;
            }
            if(typeof(data.Gs)!='undefined'&&data.Gs!=null&&data.Gs!=""){
                gsSpzt=data.Gs;
            }
            if(typeof(data.Qxgs)!='undefined'&&data.Qxgs!=null&&data.Qxgs!=""){
                qxGsSpzt=data.Qxgs;
            }
            if(typeof(data.Dafc)!='undefined'&&data.Dafc!=null&&data.Dafc!=""){
                DafcSpzt=data.Dafc;
            }
            if(typeof(data.SfdaHc)!='undefined'&&data.SfdaHc!=null&&data.SfdaHc!=""){
                SfdaHc=data.SfdaHc;
            }

        }
    });
    //未公示且已通过审核的档案可以发起公示
    if (4 == daSpzt && 2 == sfgs) {
        $("#fqgsBtn").slideDown("fast");
    } else {
        $("#fqgsBtn").hide();
    }

    //已公示的档案可以取消公示
    if (1 == sfgs) {
        $("#qxgsBtn").slideDown("fast");
        if("1"!=sffc){
            //开启归档按钮
            $("#auditDagd").show();
        }
    } else {
        $("#qxgsBtn").hide();
    }
    //不是案管的人员的档案影藏公示功能
    $.ajax({
        url : rootPath + "/service/jcgSfdaCx/isadmin",
        type : 'post',
        async : false,
        dataType : 'json',
        success : function(data) {
            if (!data.isadmin) {
                $("#fqgsBtn").hide();
                $("#qxgsBtn").hide();
            }
        }
    });

    // 发起公示的不能再次发起公示
    if (gsSpzt == 1) {
        $("#fqgsBtn").hide();
    }

    // 已经发起取消公示申请的不能再次取消公示

    if (qxGsSpzt == 1) {
        $("#qxgsBtn").hide();
    }

    if (gsSpym == 1||gsSpym==2) {
        $("#fqgsBtn").hide();
        $("#qxgsBtn").hide();
    }
    //已封存的档案影藏公示功能
    if (sffc == 1) {
        $("#fqgsBtn").hide();
        $("#qxgsBtn").hide();
    }
    //发起封存审批后，不能显示发起公示按钮
    if(DafcSpzt==1){
        $("#fqgsBtn").hide();
    }

});

//档案公示发起审核
function fqsh() {

    //判断是否选择审批人
    /*	var selectTree = $("#gsxzspr").combotree("tree");
        var ryNode = selectTree.tree("getSelected");
        var dwbmRoot = selectTree.tree("getRoot");
        var sprdwbm = checkStrReturnEmpty(dwbmRoot.id);
        var jsNode = "";
        var bmNode = "";
        var sprgh = "";
        var spbm = "";
        if(ryNode){
            jsNode = selectTree.tree("getParent", ryNode.target);
            bmNode = selectTree.tree("getParent", jsNode.target);
            sprgh = checkStrReturnEmpty(ryNode.id);
            spbm = checkStrReturnEmpty(bmNode.id);
            $("input[name='xzdaspr']").val(checkStrReturnEmpty(ryNode.text));
        }
        var ss = $("input[name='gsxzspr']").val();
        if(!ss||ss==""){
            top.msgAlertInfo("请选择审批人!");
            return;
        }*/

    //公示截止时间
    var endSj = $("#gsEndDate").datebox("getValue");
    var dateArray=endSj.split("-");
    var jzsj=new Date(dateArray[0],parseInt(dateArray[1])-1,dateArray[2]);
    if(endSj==null||endSj==""||typeof (endSj) == 'undefined'){
        top.msgAlertInfo("请选择公示截止日期时间！");
        return;
    }

    var startSj = $("#gsStartDate").datebox("getValue");
    var ksrq_dateArray=startSj.split("-");
    var dqsj=new Date(ksrq_dateArray[0],parseInt(ksrq_dateArray[1])-1,ksrq_dateArray[2]);

    if(jzsj<=dqsj){
        top.msgAlertInfo("公示截止日期必须大于当前时间！");
        return;
    }

    var gsxx = $('#gstext').val();
    if(gsxx.length>500){
        top.msgAlertInfo("公示说明过长，最多不超过500字！");
        return;
    }

    /*	var fqclnr = ssrmc+kssj+"-"+jssj+" 档案公示审核";
        var data2 = {
                jdlx : '601',
                lcjd : '60101',
                sprdwbm : top.currentUserInfo.dwbm,
                sprbmbm:spbm,
                sprgh : sprgh,
                spstid : gdid,
                splx : '1',
                clnr : encodeURI(fqclnr)
            };*/
    var dagsData = {
        dagzid : gdid,
        dwbm : top.currentUserInfo.dwbm,
        czrgh: top.currentUserInfo.gh,
        czlx : '1',   //“1”公示  “2”取消公示
        gsJzsj : endSj,
        gsxx : gsxx
    };

    top.msgProgressTip("正在处理……");
    //	添加公示记录
    $.post(rootPath + "/service/jcgSfdaCx/addgsjl", dagsData, function(data) {
        top.msgProgressTipClose();
        console.log(data);
        $('#gsinfo').hide();
        top.msgAlertInfo(data.msg);

        window.location.reload();
        resizeParentIframe();
        parent.ifenone();
    }, 'json');

    //发起审批
    /*$.post(rootPath + "/service/sp/audit", data2, function(data) {
        if (data.status == '1') {
            top.msgProgressTipClose();
            top.msgAlertInfo("档案公示申请已发送，等待领导审批!");
            $('#gsinfo').hide();
            window.location.reload();
        } else {
            top.msgProgressTipClose();
            top.msgAlertInfo("档案公示申请发送失败!");
        }

    }, 'json');*/

}

//取消公示
function qxgs() {
    var endSj = $("#gsEndDate").datebox("getValue");
    var startSj = $("#gsStartDate").datebox("getValue");
//	var sprObj = getCombotreeSprObj("gsxzspr"); //common.js
//	if(!sprObj){
//		return;
//	}

//	var gsxx = $('#gstext').val();
//	if(gsxx.length>500){
//		top.msgAlertInfo("公示说明过长，最多不超过500字！");
//		return;
//	}

    /*	var fqclnr = ssrmc+kssj+"-"+jssj+" 档案取消公示审核";
        var data2 = {
                jdlx : '701',
                lcjd : '70101',
                sprbmbm:sprObj.sprbmbm,
                sprdwbm : top.currentUserInfo.dwbm,
                sprgh : sprObj.sprgh,
                spstid : gdid,
                splx : '6',
                clnr : encodeURI(fqclnr)
            };*/

    var qxgsData = {
        dagzid : gdid,
        dwbm : top.currentUserInfo.dwbm,
        czrgh:top.currentUserInfo.gh,
        czlx : '2',  //1 公示  2 取消公示
        gsJzsj : startSj,
        gsxx : ""
    };

    top.msgProgressTip("正在处理……");
    //	添加公示记录【主要是怕后续反查公示记录信息，所以这儿并不是直接删除之前的公示信息，而是做添加】
    $.post(rootPath + "/service/jcgSfdaCx/addgsjl", qxgsData, function(data) {
        // console.log(data);
        top.msgProgressTipClose();
        $('#gsinfo').hide();
        if(data&&data.msg){
            top.msgAlertInfo(data.msg);
        }else{
            top.msgAlertInfo("操作失败！");
        }
        window.location.reload();
        resizeParentIframe();
        parent.ifenone();
    }, 'json');

    //  发起审批
    /*$.post(rootPath + "/service/sp/audit", data2, function(data) {

        if (data.status == '1') {
            top.msgAlertInfo("取消公示申请已发送，等待领导审批!");
            top.msgProgressTipClose();
            $('#gsinfo').hide();
            window.location.reload();
        } else {
            top.msgAlertInfo("取消公示申请发送失败!");
            top.msgProgressTipClose();
        }

    }, 'json');*/
}

//显示填写公示信息对话框
function showGsDetail(index) {
    parent.ifeblick();
    $('#gstext').val('');
    $("#gsEndDate").datebox("reset");
    $("#gsStartDate").datebox("reset");
    $("#qdButton1").off('click');    //取消绑定点击事件

    var nowSysDate = getSystemDateTime("yyyy-MM-dd");  //引用common.js
    $("#gsStartDate").datebox("setValue",nowSysDate);
    //组装审批人的树（方法引用于common.js）
    /*	var input_id = "gsxzspr";  //
        var dwbm = null;
        var gh = null;
        if(top.currentUserInfo){
            dwbm = top.currentUserInfo.dwbm;
            gh = top.currentUserInfo.gh;
        }
        getAllRyOfDwBmTree(input_id,dwbm,gh,null,null);
        */

    if (1 == index) {
        $("#info").text("取消该档案的公示，请点击确定！");
        $("#gstext").hide();
        $("#xzqsny_1").hide();
        $("#xzqsny_2").hide();
        $("#qdButton1").on('click', qxgs);
    } else {
        //档案正在变更审批中不能发起公示
        if(1==SfdaHc){
            top.msgAlertInfo("档案正在变更审批中不能发起公示");
            return;
        }

        $("#info").text("公示备注:");
        $("#qdButton1").on('click', fqsh);
    }
    $("#gsinfo").fadeIn("fast");
}


function closeGsDetail() {
    parent.ifenone();
    $("#gsinfo").fadeOut("fast");
}


/*function reloadNowPage(data){
	if(data){
		window.location.href = rootPath + "/view/sfda/sfda_grjbxx.html?wbid=" + data.id
								+ "&ssrdwbm=" + data.ssrdwbm
								+ "&ssr=" + data.ssr
								+ "&tjnf=" + data.tjnf
								+ "&jcgsfdacx=1&sffc=0"
								+ "&sfgs=" + data.sfgs
								+ "&sfgd=" + data.sfgd
								+ "&sffc=" + data.sffc
								+ "&kssj=" + data.kssj
								+ "&jssj=" + data.jssj;
	}else{
		window.location.reload();
	}
}*/

//审核窗口的树形列表展示人员
/*var gsztreedata = {
		gh : "",
		name : ""
	}
var isGsShowMenu = false;
var settingGs = {
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
		beforeClick : beforeClickGs,
		onClick : onClickGs
	}
};
$.getJSON(rootPath + '/service/tree/zzjgtree', function(res) {
	var result = [];
	result.push(res);
	$(document).ready(function() {
		if ($.fn.zTree)
			$.fn.zTree.init($("#gstreeDemo"), settingGs, result);
	});
});

function beforeClickGs(treeId, treeNode) {
	var check = (treeNode && treeNode.valueSelect);
	if (check)
		hideMenuGs();
	return check;
}

function onClickGs(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("gstreeDemo"), nodes = zTree
			.getSelectedNodes();
	v = "";
	var name = nodes[0].text;
	var gh = nodes[0].id;
	gsztreedata.name = name;
	gsztreedata.gh = gh;
	v = name;
	var xzsprObj = $("#gsxzspr");
	xzsprObj.attr("value", v);
}

function showMenuGs() {
	if (isGsShowMenu == false) {
		isGsShowMenu = true;
		var xzsprObj = $("#gsxzspr");
		var xzsprOffset = $("#gsxzspr").offset();
		$("#gsmenuContent").fadeIn("fast");

		$("body").bind("mousedown", onBodyDownGs);
	} else
		hideMenuGs();

}
function hideMenuGs() {
	isGsShowMenu = false;
	$("#gsmenuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDownGs);
}
function onBodyDownGs(event) {
	if (!(event.target.id == "gsmenuBtn" || event.target.id == "gsmenuContent" || $(
			event.target).parents("#gsmenuContent").length > 0)) {
		hideMenuGs();
	}
} // ztree
*/

