
var obj = getRequest();
// console.info(obj);
var ssrdwbm = obj.ssrdwbm;//月度绩效所属人的单位编码
var ssrgh = obj.ssrgh;//"0051";//'0004';////月度绩效所属人的单位编码
var ssryear = obj.ssryear; //"2014";//'2015';////月度绩效对应的年份
var ssrseason = obj.ssrseason;//"3";//'12';////月度绩效对应的月份
var ssrywlx = "";//月度绩效的业务类型 02侦监 03公诉 10预防  默认为02
var clnr = obj.clnr;//处理内容
var spzt = obj.spzt;//审批状态
var spstid  = obj.spstid;//"7697DE578CDC4E95A32FA9947C424D18";//审批实体id
var jdlx = obj.jdlx; //节点类型
var spzt = obj.spzt;//审批状态
var cljs = obj.cljs;//处理角色
var lcjd = '';//流程节点
var splx = obj.splx;//审批类型

var spjgStatus = '';
if (spjgStatus == "1") {
    $("#button_agree").hide();
    $("#button_refuse").hide();
    $("#spyj_div").hide();
    $("#button_read").hide();
}

// var spymtype = pdymtype(spzt,cljs);
var spymtype = '';
judgeSpymType();

//被审批人的单位名称、被审批人部门名称、审批人单位名称、审批人部门名称
var bsprdwmc = obj.bspdwmc;
var bsprbmmc = obj.bspbmmc;
var sprdwmc = obj.sprdwmc;
var sprbmmc = obj.sprbmmc;
var bsprdwbm = obj.bspdwbm;
var bsprbmbm = obj.bspbmbm;
var sprdwbm = obj.sprdwbm;
var sprbmbm = obj.sprbmbm;

var lcmbarray = null;//流程模板数组
var lcmb = null;

var istableeditable = isTableEditable(spzt);

var selectshowbutton = selectShowButton(spzt,cljs);

var currentUserInfo ={}; //给子页面传递的参数列表

//人员选择树
var param_id = "grjx_spr_select";
var param_dwbm = null;
var param_gh = null;
var param_treeId = null;
var param_treeText = null;
var param_paramType = "2"; //获取全院的人员信息,1:获取本部门的人员树（默认）
getAllRyOfDwBmTree(param_id,param_dwbm,param_gh,param_treeId,param_treeText,param_paramType);   //引用于common.js

//为评定等级设置值
var pddjJson = [{
    "id" : 1,
    "text" : "一等次"
},{
    "id" : 2,
    "text" : "二等次"
},{
    "id" : 3,
    "text" : "三等次"
},{
    "id" : 4,
    "text" : "四等次"
}];

$("#grjx_pddj_select").combobox({
    data : pddjJson,
    valueField : "id",
    textField : "text",
    editable : false
});

//获取流程模板
$.ajax({
    url : rootPath +"/service/sp/getmb",
    type : 'post',
    async : false,
    data:{'jdlx':jdlx,"jdzt":'',"lclx":''},
    dataType : 'json',
    success : function(data1) {
        lcmbarray=data1;
    }
});
currentUserInfo = {
    dwbm:ssrdwbm,
    gh:ssrgh,
    year:ssryear,
    season:ssrseason,
    clnr:clnr,
    wbid:spstid,
    istableeditable:istableeditable,
    selectshowbutton:selectshowbutton,
    spymtype:spymtype,
    lcid:obj.lcid,
    lcmb:lcmbarray.jdlcList[0],
    bsprdwmc:bsprdwmc,
    bsprbmmc:bsprbmmc,
    sprdwmc:sprdwmc,
    sprbmmc:sprbmmc,
    bsprdwbm : bsprdwbm,
    bsprbmbm : bsprbmbm,
    sprdwbm : sprdwbm,
    sprbmbm : sprbmbm
};

$.ajax({
    url : rootPath + "/service/sfdasp/showsplc",
    type : 'post',
    async : false,
    data:{'wbid':spstid,'splx':splx,'lcid':obj.lcid},
    dataType:'json',
    success : function(data) {debugger
        for(var i=data.length-1;i>=0;i--){
            //为审批详情页面Table下的tr、td赋值
            addRow(data[i]);
        }
    }
});

$("#goback").on("click",goBackPage);
//获取最新流程实例信息
//$.ajax({
//	url:rootPath +"/service/sp/getrecentspsl",
//	type:'post',
//	async:false,
//	data:{
//		spdw:ssrdwbm,
//		spbm:'',
//		spr:ssrgh,
//		spstid:spstid
//	},
//	success:function(data1){
//		console.log(data1);
//		var cljs = data1.cljs;
//	}
//});


//审批方法同意为1,退回为2
function addspyj(type){
    spstid = obj.spstid;
    var splx="3";
    var spyj=$("#spyj").val();
    var paranm_spSign = "";
    var param_lczz = true;
    var param_dwbm = "";
    var param_bmbm = "";
    var param_gh = "";

    if (type != '3') {
        if (type == '1') { // 同意
            lcmb = lcmbarray.jdlcList[0];
            var grjxSpChecked = $('#grjx_sp_ckbox').prop('checked');
            var grjxSprObj = null;
            if(grjxSpChecked){
                var param_input_id = "grjx_spr_select";
                grjxSprObj= getCombotreeSprObj(param_input_id);  //引用于common.js
                if(!grjxSprObj){
                    return;
                }
                param_dwbm = checkStrReturnEmpty(grjxSprObj.sprdwbm);
                param_bmbm = checkStrReturnEmpty(grjxSprObj.sprbmbm);
                param_gh = checkStrReturnEmpty(grjxSprObj.sprgh);
            }else{
                param_lczz = false;
            }
            paranm_spSign = "1";
        }

        if (type == '2') {  //不同意/退回
            lcmb = lcmbarray.jdlcList[1];
            paranm_spSign = "2";
        }

        top.msgProgressTip("正在处理……");
        $.ajax({
            url:rootPath + '/service/sp/selectNewSpslByLcid',
            type:'post',
            dataType:'json',
            async:'false',
            data:{
                lcid:obj.lcid
            },
            success:function(data){
                if(data[0].jdlx == lcmb.jdlx){
                    $.ajax({
                        url : rootPath +"/service/sp/grjxAudit",
                        type : 'post',
                        async : false,
                        data:{
                            jdlx : lcmb.jdlx,
                            lcjd : lcmb.lcjd,
                            splx : "3", //代表个人绩效
                            sprdwbm : param_dwbm,//type=='2'?ssrdwbm:'',
                            sprbmbm : param_bmbm,
                            sprgh :  param_gh,//type=='2'?ssrgh:'',
                            spstid : spstid,
                            spyj : spyj,
                            clnr : clnr,
                            lcid : obj.lcid,
                            spSign : paranm_spSign,
                            lczz : param_lczz,
                            pddj : $("#grjx_pddj_select").combobox("getValue"),
                            pddjmc : $("#grjx_pddj_select").combobox("getText"),
                            spymtype:spymtype,
                        },
                        dataType:'json',
                        success: function(resultMap){
                            top.msgProgressTipClose();
                            spjgStatus = resultMap.status;
                            if (type == '1') {
                                if (spjgStatus == 1) {
                                    top.msgAlertInfo("审批成功");
                                    updatesplc(1);
                                    hideOk();
                                    judgeSpymType();
                                    currentUserInfo.spymtype = spymtype;
                                    var flag = '1';
                                    window.frames[0].updateGrjx1(flag);
                                    window.frames[0].addPfr();
                                } else if (spjgStatus==0) {
                                    top.msgAlertWarning("审批失败");
                                }
                            } else if(type == '2') {
                                if (spjgStatus == 1) {
                                    top.msgAlertInfo("退回操作成功");
                                    updatesplc(0);
                                    hideOk();
                                    judgeSpymType();
                                    currentUserInfo.spymtype = spymtype;
                                    window.frames[0].loadTable();
                                }else if(spjgStatus==0){
                                    top.msgAlertWarning("退回操作失败");
                                }
                            }
                        },
                        error:function(data){
                            top.msgProgressTipClose();
                            top.msgAlertWarning("失败");
                        }
                    });
                }else{
                    top.msgProgressTip("该业务已经处理完成");
                }
            }
        });
    }else{
        top.msgProgressTip("正在处理……");
        $.ajax({
            url:rootPath +'/service/sp/setread',
            type : 'post',
            async : false,
            data:{
                spstid:spstid,
                splx:splx
            },
            dataType:'json',
            success:function(data){
                top.msgProgressTipClose();
                if(data.status=='1'){
                    top.msgAlertInfo("标记成功");
                    $("#button_read").hide();
                    updatesplc(1);
                }else if(data.status=='0'){
                    top.msgAlertError("标记失败");
                }
            }
        });
    }
}

//判断表格是否可编辑
function isTableEditable(spzt){
    if(spzt=='1') {
        return true;
    } else {
        return false;
    }
}

//判断应该选择展示哪些按钮
function selectShowButton(spzt,cljs){
    if(spzt=='1'){
        if(cljs=='SP'||cljs=='RSB'){
            return '2';//展示两个按钮，同意、退回
        }
        if(cljs=='CJR'){
            return '1';//展示一个按钮
        }
    }else {
        return '0';//隐藏所有按钮
    }
}

//判断页面类型
function pdymtype(spzt,cljs){
    if(spzt=='1'){//待审批状态
        if(cljs=='SP'){//处理角色是审批人
            return '1';
        }else if (cljs=='RSB'){//处理角色是人事部
            return '2';
        }else if(cljs=='CJR'){//处理角色是创建人
            return '3';
        } else if (cljs == 'YLD') { // 处理角色是分管院领导
            return '7';
        }
    }else{
        return '4';
    }
}

//选择隐藏按钮
if(selectshowbutton=='0'){
    $("#button_agree").hide();
    $("#button_refuse").hide();
    $("#spyj_div").hide();
    $("#button_read").hide();
}else if(selectshowbutton=='1'){
    $("#button_agree").hide();
    $("#button_refuse").hide();
    $("#spyj_div").hide();
}else{
    $("#button_read").hide();
}

function updatesplc(ty){
    $.ajax({
        url : rootPath + "/service/sfdasp/showsplc",
        type : 'post',
        async : false,
        data:{'wbid':spstid,'splx':splx,'lcid':obj.lcid},
        dataType:'json',
        success : function(data) {debugger
            $('#splc').empty();
            $('#splc').append(" <tr  id='splctr1' style="+"'"+"background: #d8ecff;height: 31px;line-height: 31px;text-align: left;color: #134671"+"'"+"><td style="+"'"+"width: 40px"+"'"+"></td><td>单位名称</td><td>部门名称</td><td>处理人</td><td>处理意见</td><td>处理时间</td><td>处理结果</td></tr>");
            for(var i=data.length-1;i>=0;i--){

                //为审批详情页面Table下的tr、td赋值
                addRow(data[i]);
            }

        }
    });
}

//为审批Table赋值
function addRow(object){
    var sj=(object.spsj).split('-');
    var spyj;

    if(typeof(object.spyj)=="undefined"){
        spyj="";
    }else{
        spyj=object.spyj;
    }

    //为审批Table下的tr、td赋值
    $("#splctr1").after("<tr><td></td>"+
        "<td>"+object.spdwmc+"</td>"+
        "<td>"+object.spbmmc+"</td>"+
        "<td>"+object.sprmc+"</td>"+
        "<td>"+spyj+"</td>"+
        "<td>"+sj[0]+"."+sj[1]+"."+sj[2]+"</td>"+
        "<td>"+object.spzt+"</td>"+
        "</tr>");

}

// 隐藏页面的确认/同意/退回等按钮
function hideOk(){
    var ss = window.frames;
    ss[0].hideOkButton();

    $("#button_agree").hide();
    $("#button_refuse").hide();
    $("#spyj_div").hide();
    $("#button_read").hide();
}


//判断是否隐藏撤回按钮
$.ajax({
    url : rootPath +"/service/sp/getjdlx",
    type : 'post',
    async : false,
    data : {
        spstid:spstid,
        splx:lcmbarray.jdlcList[0].lclx
    },
    dataType : 'json',
    success : function(data1) {
        if(!data1||data1=='0'||data1!=lcmbarray.jdlcList[0].jdlx){
            $('#button_recall').hide();
        }
    }
});

$('#recall').on('click',function(){
    $.ajax({
        url : rootPath +"/service/sp/recall",
        type : 'post',
        async : false,
        data : {
            spstid:spstid,
            splx:lcmbarray.jdlcList[0].lclx
        },
        dataType : 'json',
        success : function(data1) {
            if(data1=='1'){
                top.msgAlertInfo("成功撤回");
                //$.messager.alert("提示", "成功撤回", "info");
                $('#button_recall').hide();
            }else{
                top.msgAlertInfo("撤回失败");
                //$.messager.alert("提示", "撤回失败", "info");
            }
        }
    });
});

var height_a = document.documentElement.clientHeight + 30;
$("#judicialfilesmanage").append("<iframe src='jxkh.html' width='100%' style='height:"+ height_a+ "px;margin-top: 10px;' id='iframe' frameborder='0'></iframe>");

$("#iframe").on('load', function() {

    var intervalTime = setInterval(function() {
        if (window.frames[0].count != 1) {
            return;
        }
        resizeParentIframe();
        clearInterval(intervalTime);
    }, 50);
});

//先判定复选框是否选中
$("#grjx_sp_ckbox").click(function(){
    if ($(this).prop("checked")) {
        // console.log("选中");
        $("#grjx_next_xzspr_div").show();
    } else {
        // console.log("没有选中");
        $("#grjx_next_xzspr_div").hide();
    }
});

// 判断审批页面（spymtype的值）
function judgeSpymType() {
    var ydkhObj = '';
    var ydkhfzObj = '';
    $.ajaxSettings.async = false;
    $.post(rootPath + "/service/ydkh/getydkhbt", {
        "ssrdwbm": ssrdwbm,
        "ssrgh": ssrgh,
        "ssryear": ssryear,
        'time': new Date().getTime()
    }, function(data) {
        var tableHead = data.tableHead;
        ydkhObj = data.ydkh; // YX_GRJX_YDKH表的id,用于获取考核的时间
        ydkhfzObj = data.ydkhfz; // YX_GRJX_YDKHFZ月度考核分值信息
    }, "json");
    $.ajaxSettings.async = true;

    var rylxMc = ''; // 人员类型名称
    var typeOfPer = ydkhfzObj.rylx; // 人员类型（id）
    var roleOfPer = ydkhfzObj.ryjs; // 人员角色
    var bmlbbm = ydkhfzObj.ywlx; // 部门类别编码

    $.ajax({
        url : rootPath + '/service/grjxKhryTypePz/selectByPrimaryKey',
        type : 'post',
        async : false,
        dataType:'json',
        data:{
            id: typeOfPer
        },
        success : function(data) {
            rylxMc = data.name;
        }
    });

    // 暂时不区分省院和下级院，（省院和下级院考核指标一样）
    // if (top.currentUserInfo.dwjb == '2') { // 省院
    if (rylxMc == '检察官') {
        if (roleOfPer == 'jcg_lx-1') { // 一般检察官
            if (spjgStatus != '1' && (ydkhfzObj.bmpfr == null || ydkhfzObj.bmpfr == "" || typeof (ydkhfzObj.bmpfr) == 'undefined')) {
                spymtype = '1';
            } else if (spjgStatus != '1' && (ydkhfzObj.fgyldpfr == null || ydkhfzObj.fgyldpfr == "" || typeof (ydkhfzObj.fgyldpfr) == 'undefined')) {
                spymtype = '7';
            } else if (spjgStatus != '1' && (ydkhfzObj.rsbpfr == null || ydkhfzObj.rsbpfr == "" || typeof (ydkhfzObj.rsbpfr) == 'undefined')) {
                spymtype = '2';
            } else {
                spymtype = '-1'; // 表示审批通过时的页面
            }
        } else if (roleOfPer == 'jcg_lx-2') { // 部门负责人
            if (spjgStatus != '1' && (ydkhfzObj.fgyldpfr == null || ydkhfzObj.fgyldpfr == "" || typeof (ydkhfzObj.fgyldpfr) == 'undefined')) {
                spymtype = '7';
            } else if (spjgStatus != '1' && (ydkhfzObj.rsbpfr == null || ydkhfzObj.rsbpfr == "" || typeof (ydkhfzObj.rsbpfr) == 'undefined')) {
                spymtype = '2';
            } else {
                spymtype = '-1'; // 表示审批通过时的页面
            }
        } else if (roleOfPer == 'jcg_lx-3') { // 院领导
            if (spjgStatus != '1' && (ydkhfzObj.rsbpfr == null || ydkhfzObj.rsbpfr == "" || typeof (ydkhfzObj.rsbpfr) == 'undefined')) {
                spymtype = '2';
            }else {
                spymtype = '-1'; // 表示审批通过时的页面
            }
        }
    } else if (rylxMc == '检察辅助人员') {
        if (roleOfPer == 'jcfzry_lx-1') { // 一般检察辅助人员
            if ("7" == bmlbbm) { // 检技的一般检察辅助人员没有检查官评分这一项
                if (spjgStatus != '1' && (ydkhfzObj.bmpfr == null || ydkhfzObj.bmpfr == "" || typeof (ydkhfzObj.bmpfr) == 'undefined')) {
                    spymtype = '1';
                } else if (spjgStatus != '1' && (ydkhfzObj.fgyldpfr == null || ydkhfzObj.fgyldpfr == "" || typeof (ydkhfzObj.fgyldpfr) == 'undefined')) {
                    spymtype = '7';
                } else if (spjgStatus != '1' && (ydkhfzObj.rsbpfr == null || ydkhfzObj.rsbpfr == "" || typeof (ydkhfzObj.rsbpfr) == 'undefined')) {
                    spymtype = '2';
                } else {
                    spymtype = '-1'; // 表示审批通过时的页面
                }
            } else { // 除检技外的其他部门的一般检查辅助人员
                if (spjgStatus != '1' && (ydkhfzObj.jcgpfr == null || ydkhfzObj.jcgpfr == "" || typeof (ydkhfzObj.jcgpfr) == 'undefined')) {
                    spymtype = '6';
                } else if (spjgStatus != '1' && (ydkhfzObj.bmpfr == null || ydkhfzObj.bmpfr == "" || typeof (ydkhfzObj.bmpfr) == 'undefined')) {
                    spymtype = '1';
                } else if (spjgStatus != '1' && (ydkhfzObj.fgyldpfr == null || ydkhfzObj.fgyldpfr == "" || typeof (ydkhfzObj.fgyldpfr) == 'undefined')) {
                    spymtype = '7';
                } else if (spjgStatus != '1' && (ydkhfzObj.rsbpfr == null || ydkhfzObj.rsbpfr == "" || typeof (ydkhfzObj.rsbpfr) == 'undefined')) {
                    spymtype = '2';
                }else {
                    spymtype = '-1'; // 表示审批通过时的页面
                }
            }
        } else if (roleOfPer == 'jcfzry_lx-2') { // 部门负责人
            if (spjgStatus != '1' && (ydkhfzObj.fgyldpfr == null || ydkhfzObj.fgyldpfr == "" || typeof (ydkhfzObj.fgyldpfr) == 'undefined')) {
                spymtype = '7';
            } else if (spjgStatus != '1' && (ydkhfzObj.rsbpfr == null || ydkhfzObj.rsbpfr == "" || typeof (ydkhfzObj.rsbpfr) == 'undefined')) {
                spymtype = '2';
            } else {
                spymtype = '-1'; // 表示审批通过时的页面
            }
        } else if (roleOfPer == 'jcfzry_lx-3') { // 院领导
            if (spjgStatus != '1' && (ydkhfzObj.rsbpfr == null || ydkhfzObj.rsbpfr == "" || typeof (ydkhfzObj.rsbpfr) == 'undefined')) {
                spymtype = '2';
            }else {
                spymtype = '-1'; // 表示审批通过时的页面
            }
        }
    } else if (rylxMc == '司法行政人员') {
        if (roleOfPer == 'sfxzry_lx-1') { // 一般司法行政人员
            if (spjgStatus != '1' && (ydkhfzObj.bmpfr == null || ydkhfzObj.bmpfr == "" || typeof (ydkhfzObj.bmpfr) == 'undefined')) {
                spymtype = '1';
            } else if (spjgStatus != '1' && (ydkhfzObj.fgyldpfr == null || ydkhfzObj.fgyldpfr == "" || typeof (ydkhfzObj.fgyldpfr) == 'undefined')) {
                spymtype = '7';
            } else if (spjgStatus != '1' && (ydkhfzObj.rsbpfr == null || ydkhfzObj.rsbpfr == "" || typeof (ydkhfzObj.rsbpfr) == 'undefined')) {
                spymtype = '2';
            }else {
                spymtype = '-1'; // 表示审批通过时的页面
            }
        } else if (roleOfPer == 'sfxzry_lx-2') { // 部门负责人
            if (spjgStatus != '1' && (ydkhfzObj.fgyldpfr == null || ydkhfzObj.fgyldpfr == "" || typeof (ydkhfzObj.fgyldpfr) == 'undefined')) {
                spymtype = '7';
            } else if (spjgStatus != '1' && (ydkhfzObj.rsbpfr == null || ydkhfzObj.rsbpfr == "" || typeof (ydkhfzObj.rsbpfr) == 'undefined')) {
                spymtype = '2';
            }else {
                spymtype = '-1'; // 表示审批通过时的页面
            }
        } else if (roleOfPer == 'sfxzry_lx-3') { // 院领导
            if (spjgStatus != '1' && (ydkhfzObj.rsbpfr == null || ydkhfzObj.rsbpfr == "" || typeof (ydkhfzObj.rsbpfr) == 'undefined')) {
                spymtype = '2';
            }else {
                spymtype = '-1'; // 表示审批通过时的页面
            }
        }
    }
    // console.log("spymtype:"+spymtype);
    // }
}