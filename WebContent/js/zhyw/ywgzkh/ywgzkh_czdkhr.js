//显示指定考核人div		
$("#ywgzkhzdkhr").click(function(){
	parent.ifeblick();
	$(".ywgzkhzdkhrbounced").css("display", "block");
	//装配审批人员下拉框（方法引用于common.js）
	var input_id = "xzYwkhSpr";	
	var dwbm = null;
	var gh = null;
	if(top.currentUserInfo){
		dwbm = top.currentUserInfo.dwbm;
		gh = top.currentUserInfo.gh;
	}
	getAllRyOfDwBmTree(input_id,dwbm,gh,null,null,"1");
});

//关闭指定考核人div
$("#zdkhrqx").click(function(){
     close();
});
$("#khrqx").click(function(){
	 close();
});
function close(){
	parent.ifenone();
	//$('#ywkhzdkhdw').combotree('clear');
	$('#ywkhzdkhlx').combotree('clear');
	$("#xzYwkhSpr").val("");
	$(".ywgzkhzdkhrbounced").css("display", " none");
}

//点击业务工作指定考核人确认按钮触发事件
var lcmb = [{      // 档案流程模板数组
	id: "0921",
	jdlx: "920",
	lcjd: "92001",
	xyjd: "921",
    xycljs: "SP",
	xyclbm: "",
    xycldw: "",
	jdzt: "1",
	sm: "年度考核审批开始",
	xycldwlx: "2",
	lclx: "9",
    ztbg: "2"
}];

/**
 * 添加业务考核审批人
 */
$("#khrqr").click(function(){
	//单位名称 
	 var dwmc=[];
	//单位编码
	 var dwbm=[];
	//考核类型
	 var khlx=[];
	//考核类型名称
	 var khlxmc=[];
	 //业务类型考核id
	 //考核id
	 var khid=[];
	  var curdwbm= "";
	   var dwjb = "";
	   if (top.currentUserInfo) {
	        curdwbm = top.currentUserInfo.dwbm;
	        dwjb = top.currentUserInfo.dwjb;
	    }
	   var zdkhdw = $("#ywkhzdkhdw").combotree('tree');
	   var zdkhdwdx = zdkhdw.tree("getChecked");
	   var zdkhlx= $("#ywkhzdkhlx").combotree('tree');
	   var zdkhlxdx = zdkhlx.tree("getChecked");

       if(zdkhlxdx.length==0){
           top.msgAlertInfo("请选择考核类型");
           return;
       }
		$.each( zdkhdwdx,function(index,check1){
			dwmc.push(check1.text);
			dwbm.push(check1.id);
			khid.push(check1.khid);
		});		
       $.each(zdkhlxdx,function(index,check2){
			khlx.push(check2.khlx);
			khlxmc.push(check2.text);
		});
       var strdwmc=dwmc.join();
       var strdwbm=dwbm.join();
       var strkhlx=khlx.join();
       var strkhlxmc=khlxmc.join();
       var strywlxkhid=$("#ywlxkhid").val();
	 //分配人部门编码
    var fprBmbm = top.currentUserInfo.bmbm;
    //分配人工号
    var fprGh = top.currentUserInfo.gh;
    //分配人姓名
    var fprMc = top.currentUserInfo.mc;
    //考核人部门编码、工号、考核人
	var khrObj = getCombotreeSprObj("xzYwkhSpr");  //调用common.js
	if(!khrObj){
		return;
	}
	var khrBmbm = khrObj.sprbmbm;
	var khrGh = khrObj.sprgh;
	var khrMc = khrObj.sprmc;
	
    //审批意见
    var spyj = ""; 
    //处理内容
    var clnr = "";
    //1、更新业务考核表发起人、考核人信息	成功后发起业务考核审批
    var khidstr=khid.join();
    updateinfo(khidstr,strywlxkhid,strkhlx,fprBmbm,fprGh,fprMc,khrBmbm,khrGh,khrMc,curdwbm,spyj,clnr);     
});

function updateinfo(khidstr,strywlxkhid,strkhlx,fprBmbm,fprGh,fprMc,khrBmbm,khrGh,khrMc,curdwbm,spyj,clnr){
	 top.msgProgressTip("正在处理……");
	    $.ajax({
	        type: "post",
	        url: rootPath + "/service/ywgzkh/addYwgzkhSpr",
	        async: false,
	        data: {
	            ywlxkhId: khidstr,
	            khlxstr:strkhlx,
	            fprBmbm: fprBmbm,
	            fprGh: fprGh,
	            fprMc: fprMc,
	            khrBmbm: khrBmbm,
	            khrGh: khrGh,
	            khrMc: khrMc
	        },
	        dataType: 'json',
	        success: function (result) { 
	            fqywkhsp(khidstr,curdwbm,khrBmbm,khrGh,strywlxkhid,spyj,clnr);   
	        }
	    });
}

function fqywkhsp(khidstr,curdwbm,khrBmbm,khrGh,strywlxkhid,spyj,clnr){
	 $.ajax({
	        url: rootPath + "/service/ywgzkh/audit",
	        type: 'post',
	        async: false,
	        data: {
	            jdlx: lcmb[0].jdlx,
	            lcjd: lcmb[0].lcjd,
	            sprdwbm: curdwbm,
	            sprbmbm:khrBmbm,
	            sprgh: khrGh,
	            spstid: khidstr,
	            spyj: spyj,
	            clnr: clnr
	        },
	        dataType: 'json',
	        success: function (resultMap) {
	        	   top.msgAlertInfo("指定考核人员成功");     
	        	   top.msgProgressTipClose();
	        	   close();
	         }
	        });
}

























////////////////////////////////////////////////////////////////////////////////指定考核人Ztree start\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//考核人的Ztree
var isYwgzkhShowMenu = false;
var ywgzkhztreedata = {
    gh: "",
    name: ""
}
var ywgzkhsetting = {
    data: {
        key: {
            name: "name"
        },
        simpleData: {
            enable: true
        }
    },
    view: {
        showIcon: false,
        dblClickExpand: false,
        selectedMulti: false
    },
    callback: {
        beforeClick: beforeYwgzkhClick,
        onClick: onYwgzkhClick
    }
};

function beforeYwgzkhClick(treeId, treeNode) {
    var check = (treeNode && "ry" == treeNode.type);
    if (check)
        hideYwgzkhMenu();
    return check;
}

function onYwgzkhClick(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeYwgzkhDemo"), nodes = zTree.getSelectedNodes();
    var v = "";
    var name = nodes[0].name;
    var gh = nodes[0].id;
    ywgzkhztreedata.name = name;
    ywgzkhztreedata.gh = gh;
    v = name;
    var xzYwkhSprObj = $("#xzYwkhSpr");
    xzYwkhSprObj.attr("value", v);
}
function showYwgzkhMenu() {
    if (isYwgzkhShowMenu == false) {
        isYwgzkhShowMenu = true;
        var xzYwkhSprObj = $("#xzYwkhSpr");
        var xzYwkhSprOffset = $("#xzYwkhSpr").offset();
        $("#menuYwgzkhContent").fadeIn("fast");

        $("body").bind("mousedown", onYwgzkhBodyDown);
    } else {
        hideYwgzkhMenu();
    }
}

function hideYwgzkhMenu() {
    isYwgzkhShowMenu = false;
    $("#menuYwgzkhContent").fadeOut("fast");
    $("body").unbind("mousedown", onYwgzkhBodyDown);
}
function onYwgzkhBodyDown(event) {
    if (!(event.target.id == "menuYwgzkhBtn" || event.target.id == "menuYwgzkhContent" ||
        $(event.target).parents("#menuYwgzkhContent").length > 0)) {
        hideYwgzkhMenu();
    }
}